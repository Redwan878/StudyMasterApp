package com.porashona.studymaster.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.porashona.studymaster.R
import com.porashona.studymaster.StudyMasterApplication
import com.porashona.studymaster.data.model.SessionType
import com.porashona.studymaster.data.model.StudySession
import com.porashona.studymaster.databinding.FragmentStatisticsDashboardBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class StatisticsDashboardFragment : Fragment() {

    private var _binding: FragmentStatisticsDashboardBinding? = null
    private val binding get() = _binding!!

    private val viewModel: StatisticsDashboardViewModel by viewModels {
        StatisticsDashboardViewModelFactory(
            (requireActivity().application as StudyMasterApplication).studyRepository
        )
    }

    private lateinit var sessionAdapter: SessionAnalyticsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatisticsDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBarChart()
        setupLineChart()
        setupPieChart()
        setupRecyclerView()
        setupRefreshButton()
        observeViewModel()
    }

    private fun setupBarChart() {
        val barChart = binding.barChart
        val description = Description().apply {
            text = "Weekly Study Hours"
            textSize = 10f
        }
        barChart.description = description
        barChart.setDrawGridBackground(false)
        barChart.setDrawBarShadow(false)
        barChart.setPinchZoom(false)
        barChart.setDoubleTapToZoomEnabled(false)
        barChart.axisLeft.setDrawGridLines(false)
        barChart.axisRight.isEnabled = false
        barChart.xAxis.setDrawGridLines(false)
        barChart.xAxis.position = BarChart.XAxisPosition.BOTTOM
        barChart.xAxis.granularity = 1f
        barChart.xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                val days = arrayOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
                return days.getOrElse(value.toInt() % days.size) { "" }
            }
        }
    }

    private fun setupLineChart() {
        val lineChart = binding.lineChart
        val description = Description().apply {
            text = "Study Trend"
            textSize = 10f
        }
        lineChart.description = description
        lineChart.setDrawGridBackground(false)
        lineChart.setPinchZoom(false)
        lineChart.setDoubleTapToZoomEnabled(false)
        lineChart.legend.isEnabled = true
        lineChart.axisLeft.setDrawGridLines(false)
        lineChart.axisRight.isEnabled = false
        lineChart.xAxis.setDrawGridLines(false)
        lineChart.xAxis.granularity = 1f
    }

    private fun setupPieChart() {
        val pieChart = binding.pieChart
        val description = Description().apply {
            text = "Session Distribution"
            textSize = 10f
        }
        pieChart.description = description
        pieChart.setDrawCenterText(true)
        pieChart.centerTextSize = 14f
        pieChart.rotationEnabled = true
        pieChart.legend.isEnabled = true
        pieChart.setHoleColor(android.graphics.Color.TRANSPARENT)
    }

    private fun setupRecyclerView() {
        sessionAdapter = SessionAnalyticsAdapter()
        binding.recyclerViewRecentSessions.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewRecentSessions.adapter = sessionAdapter
    }

    private fun setupRefreshButton() {
        binding.btnRefresh.setOnClickListener {
            viewModel.refreshStatistics()
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.sessionStatistics.collect { stats ->
                    updateDashboard(stats)
                }
            }
        }
    }

    private fun updateDashboard(stats: SessionStatistics) {
        binding.tvTotalSessions.text = stats.totalSessions.toString()
        binding.tvTotalTime.text = formatHours(stats.totalTimeMinutes)
        binding.tvTodayTime.text = "${stats.todayMinutes}m"
        binding.tvStreak.text = stats.streakDays.toString()

        updateBarChart(stats.weeklyHours)
        updateLineChart(stats.dailyMinutes)
        updatePieChart(stats.sessionTypeBreakdown)
        sessionAdapter.submitList(stats.sessionDates.take(10))
    }

    private fun updateBarChart(weeklyHours: List<Double>) {
        val entries = weeklyHours.mapIndexed { index, value ->
            BarEntry(index.toFloat(), value.toFloat())
        }
        val dataSet = BarDataSet(entries, "Weekly Hours").apply {
            colors = listOf(requireContext().getColor(R.color.primary_purple))
        }
        val barData = BarData(dataSet)
        binding.barChart.data = barData
        binding.barChart.description.text = "Average Hours/Day (Last 13 Weeks)"
        binding.barChart.invalidate()
    }

    private fun updateLineChart(dailyMinutes: Map<String, Long>) {
        val sortedEntries = dailyMinutes.entries.sortedBy { it.key }.takeLast(7)
        val entries = sortedEntries.mapIndexed { index, (_, value) ->
            Entry(index.toFloat(), value.toFloat())
        }
        val dataSet = LineDataSet(entries, "Daily Minutes").apply {
            setColor(requireContext().getColor(R.color.primary_purple))
            circleColor = requireContext().getColor(R.color.primary_purple)
            lineWidth = 2f
            setCircleRadius(4f)
            setDrawValues(false)
        }
        val lineData = LineData(dataSet)
        binding.lineChart.data = lineData
        binding.lineChart.description.text = "Daily Study Minutes"
        binding.lineChart.invalidate()
    }

    private fun updatePieChart(typeBreakdown: Map<SessionType, Long>) {
        val entries = typeBreakdown.map { (type, minutes) ->
            PieEntry(minutes.toFloat(), type.name)
        }
        if (entries.isEmpty()) {
            binding.pieChart.clear()
            return
        }
        val dataSet = PieDataSet(entries, "Session Types").apply {
            colors = listOf(
                requireContext().getColor(R.color.primary_purple),
                requireContext().getColor(R.color.secondary_yellow),
                requireContext().getColor(R.color.success_green)
            )
        }
        val pieData = PieData(dataSet)
        pieData.valueFormatter = DecimalFormatFormatter()
        pieData.setValueTextColor(requireContext().getColor(R.color.on_surface))
        pieData.setValueTextSize(12f)
        binding.pieChart.data = pieData
        binding.pieChart.description.text = "Session Type Distribution"
        binding.pieChart.invalidate()
    }

    private fun formatHours(minutes: Long): String {
        return if (minutes >= 60) "${minutes / 60}h ${minutes % 60}m" else "${minutes}m"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

class DecimalFormatFormatter : com.github.mikephil.charting.formatter.ValueFormatter() {
    override fun getFormattedValue(value: Float): String {
        return String.valueOf(value.toInt())
    }
}