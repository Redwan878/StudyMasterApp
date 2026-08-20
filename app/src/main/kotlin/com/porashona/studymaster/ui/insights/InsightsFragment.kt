/*
package com.porashona.studymaster.ui.insights

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.porashona.studymaster.R
import com.porashona.studymaster.StudyMasterApplication
import com.porashona.studymaster.data.model.SessionType
import com.porashona.studymaster.data.model.StudySession
import com.porashona.studymaster.data.preferences.PreferencesManager
import com.porashona.studymaster.data.repository.StudyRepository
import com.porashona.studymaster.databinding.FragmentInsightsBinding
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.concurrent.TimeUnit
import kotlin.math.roundToInt

class InsightsFragment : Fragment() {

    private var _binding: FragmentInsightsBinding? = null
    private val binding get() = _binding!!

    private val repo: StudyRepository by lazy {
        (requireActivity().application as StudyMasterApplication).studyRepository
    }
    private val prefs: PreferencesManager by lazy {
        (requireActivity().application as StudyMasterApplication).preferencesManager
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentInsightsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                repo.allSessions.combine(prefs.dailyGoalMinutes) { sessions, goal ->
                    Pair(sessions.filter { it.sessionType == SessionType.WORK }, goal)
                }.collect { (sessions, goal) ->
                    renderFocusScore(sessions, goal)
                    renderHeatmap(sessions)
                    renderBestHour(sessions)
                    renderComparison(sessions)
                }
            }
        }
    }

    private fun renderFocusScore(sessions: List<StudySession>, dailyGoalMinutes: Int) {
        val today = startOfDay(0)
        val weekStart = startOfDay(-6)
        val weekSessions = sessions.filter { it.startTime.time >= weekStart }
        val minutesThisWeek = weekSessions.sumOf { it.durationInSeconds } / 60
        val target = (dailyGoalMinutes.coerceAtLeast(15) * 7).toLong()
        val score = if (target <= 0) 0 else ((minutesThisWeek * 100) / target).toInt().coerceIn(0, 100)
        binding.tvFocusScore.text = "$score"
        binding.progressFocusScore.progress = score
        binding.tvFocusScoreHint.text = getString(
            R.string.focus_score_hint,
            minutesThisWeek, target,
        )
        binding.tvFocusScoreHint.tag = today
    }

    private fun renderHeatmap(sessions: List<StudySession>) {
        val weeks = 13
        val days = weeks * 7
        // Snap the grid so the last column ends on the current week's Saturday.
        // This way the start date always lands on a Sunday (firstDow == 0) and
        // the full 13*7 cells line up with the 91 most-recent days without
        // dropping any.
        val endCal = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0); set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0); set(Calendar.MILLISECOND, 0)
            val daysToSaturday = (Calendar.SATURDAY - get(Calendar.DAY_OF_WEEK) + 7) % 7
            add(Calendar.DAY_OF_YEAR, daysToSaturday)
        }
        val endMs = endCal.timeInMillis + TimeUnit.DAYS.toMillis(1) - 1
        val startMs = endCal.timeInMillis - TimeUnit.DAYS.toMillis((days - 1).toLong())
        val perDayMinutes = LongArray(days)
        sessions.forEach { s ->
            val t = s.startTime.time
            if (t in startMs..endMs) {
                val dayIdx = ((t - startMs) / TimeUnit.DAYS.toMillis(1)).toInt()
                if (dayIdx in 0 until days) {
                    perDayMinutes[dayIdx] += s.durationInSeconds / 60
                }
            }
        }
        val max = perDayMinutes.maxOrNull()?.coerceAtLeast(1) ?: 1
        // Column-major: column 0 = oldest week, row 0 = Sunday. Start is snapped
        // to Sunday so day i goes directly to col = i/7, row = i%7.
        val intensities = FloatArray(days)
        for (i in 0 until days) {
            val col = i / 7
            val row = i % 7
            intensities[col * 7 + row] = perDayMinutes[i].toFloat() / max.toFloat()
        }
        binding.heatmap.submit(intensities, weeks)
        val totalMinutes = perDayMinutes.sum()
        binding.tvHeatmapLegend.text = getString(
            R.string.heatmap_legend,
            totalMinutes, max,
        )
    }

    private fun renderBestHour(sessions: List<StudySession>) {
        if (sessions.isEmpty()) {
            binding.tvBestHour.text = "--"
            binding.tvBestHourHint.text = getString(R.string.best_hour_empty)
            return
        }
        val perHour = LongArray(24)
        sessions.forEach { s ->
            val cal = Calendar.getInstance().apply { time = s.startTime }
            val hour = cal.get(Calendar.HOUR_OF_DAY).coerceIn(0, 23)
            perHour[hour] += s.durationInSeconds / 60
        }
        val bestHour = perHour.indices.maxByOrNull { perHour[it] } ?: 0
        val bestMinutes = perHour[bestHour]
        val range = "${formatHour(bestHour)} – ${formatHour((bestHour + 1) % 24)}"
        binding.tvBestHour.text = range
        binding.tvBestHourHint.text = getString(R.string.best_hour_hint, bestMinutes)
    }

    private fun renderComparison(sessions: List<StudySession>) {
        val thisWeekStart = startOfDay(-6)
        val lastWeekStart = startOfDay(-13)
        // "Last week" must end at the millisecond just before this week begins,
        // otherwise day -7 (startOfDay(-7)..thisWeekStart - 1) falls into
        // neither range and an entire day of study is silently lost.
        val lastWeekEnd = thisWeekStart - 1
        val thisWeekMin = sessions.filter { it.startTime.time >= thisWeekStart }
            .sumOf { it.durationInSeconds } / 60
        val lastWeekMin = sessions.filter {
            val t = it.startTime.time
            t in lastWeekStart..lastWeekEnd
        }.sumOf { it.durationInSeconds } / 60
        binding.tvThisWeek.text = formatMinutes(thisWeekMin)
        binding.tvLastWeek.text = formatMinutes(lastWeekMin)
        val delta = thisWeekMin - lastWeekMin
        val pct = if (lastWeekMin <= 0) {
            if (thisWeekMin > 0) 100 else 0
        } else {
            ((delta.toDouble() / lastWeekMin.toDouble()) * 100).roundToInt()
        }
        binding.tvTrend.text = when {
            delta > 0 -> getString(R.string.trend_up, pct)
            delta < 0 -> getString(R.string.trend_down, -pct)
            else -> getString(R.string.trend_flat)
        }
    }

    private fun formatMinutes(minutes: Long): String {
        return if (minutes >= 60) "${minutes / 60}h ${minutes % 60}m" else "${minutes}m"
    }

    private fun formatHour(h: Int): String {
        val display = if (h == 0) 12 else if (h > 12) h - 12 else h
        val suffix = if (h < 12) "AM" else "PM"
        return "$display $suffix"
    }

    private fun startOfDay(dayOffset: Int): Long {
        val cal = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0); set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0); set(Calendar.MILLISECOND, 0)
        }
        cal.add(Calendar.DAY_OF_YEAR, dayOffset)
        return cal.timeInMillis
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

*/