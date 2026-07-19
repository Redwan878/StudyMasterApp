package com.porashona.studymaster.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.porashona.studymaster.data.model.StudySession
import com.porashona.studymaster.data.model.SessionType
import com.porashona.studymaster.data.repository.StudyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

data class SessionStatistics(
    val totalSessions: Int = 0,
    val totalTimeMinutes: Long = 0,
    val averageSessionMinutes: Double = 0.0,
    val longestSessionMinutes: Long = 0,
    val todayMinutes: Long = 0,
    val weekMinutes: Long = 0,
    val monthMinutes: Long = 0,
    val streakDays: Int = 0,
    val subjectBreakdown: Map<String, Long> = emptyMap(),
    val sessionTypeBreakdown: Map<SessionType, Long> = emptyMap(),
    val weeklyHours: List<Double> = emptyList(),
    val dailyMinutes: Map<String, Long> = emptyMap(),
    val sessionDates: List<Date> = emptyList()
)

class StatisticsDashboardViewModel(private val repository: StudyRepository) : ViewModel() {
    private val _sessionStatistics = MutableStateFlow(SessionStatistics())
    val sessionStatistics: StateFlow<SessionStatistics> = _sessionStatistics

    init {
        loadStatistics()
    }

    private fun loadStatistics() {
        viewModelScope.launch {
            val allSessions = repository.allSessions.collect { sessions ->
                updateStatistics(sessions.filter { it.sessionType == SessionType.WORK })
            }
        }
    }

    private fun updateStatistics(workSessions: List<StudySession>) {
        val totalSessions = workSessions.size
        val totalTime = workSessions.sumOf { it.durationInSeconds }
        val avgTime = if (totalSessions > 0) (totalTime / totalSessions).toDouble() else 0.0
        val longestSession = workSessions.maxOfOrNull { it.durationInSeconds } ?: 0

        val today = startOfDay(0)
        val weekStart = startOfDay(-6)
        val monthStart = startOfMonth(0)

        val todayMinutes = workSessions.filter { it.startTime.time >= today }
            .sumOf { it.durationInSeconds } / 60
        val weekMinutes = workSessions.filter { it.startTime.time >= weekStart }
            .sumOf { it.durationInSeconds } / 60
        val monthMinutes = workSessions.filter { it.startTime.time >= monthStart }
            .sumOf { it.durationInSeconds } / 60

        val subjectBreakdown = workSessions.groupBy { it.subjectName }
            .mapValues { (_, sessions) -> sessions.sumOf { it.durationInSeconds } / 60 }

        val sessionTypeBreakdown = workSessions.groupBy { it.sessionType }
            .mapValues { (_, sessions) -> sessions.sumOf { it.durationInSeconds } / 60 }

        val weeklyHours = getWeeklyHours(workSessions)
        val dailyMinutes = getDailyMinutes(workSessions)
        val streakDays = calculateStreak(workSessions)

        _sessionStatistics.value = SessionStatistics(
            totalSessions = totalSessions,
            totalTimeMinutes = totalTime / 60,
            averageSessionMinutes = avgTime / 60,
            longestSessionMinutes = longestSession / 60,
            todayMinutes = todayMinutes,
            weekMinutes = weekMinutes,
            monthMinutes = monthMinutes,
            streakDays = streakDays,
            subjectBreakdown = subjectBreakdown,
            sessionTypeBreakdown = sessionTypeBreakdown,
            weeklyHours = weeklyHours,
            dailyMinutes = dailyMinutes,
            sessionDates = workSessions.map { it.startTime }.distinct()
        )
    }

    private fun getWeeklyHours(workSessions: List<StudySession>): List<Double> {
        val calendar = Calendar.getInstance()
        val daysToSaturday = (Calendar.SATURDAY - calendar.get(Calendar.DAY_OF_WEEK) + 7) % 7
        calendar.add(Calendar.DAY_OF_YEAR, daysToSaturday - 84) // Last 13 weeks

        val weeklyMinutes = mutableListOf<Double>()
        for (week in 0 until 13) {
            calendar.add(Calendar.DAY_OF_YEAR, 7)
            val weekStart = calendar.timeInMillis
            val weekEnd = weekStart + 7 * 24 * 60 * 60 * 1000

            val weekMinutes = workSessions.filter {
                it.startTime.time in weekStart until weekEnd
            }.sumOf { it.durationInSeconds } / 60.0 / 7.0 // Average hours per day

            weeklyMinutes.add(weekMinutes)
        }
        return weeklyMinutes
    }

    private fun getDailyMinutes(workSessions: List<StudySession>): Map<String, Long> {
        return workSessions.groupBy {
            val sdf = SimpleDateFormat("MMM dd", Locale.getDefault())
            sdf.format(it.startTime)
        }.mapValues { (_, sessions) ->
            sessions.sumOf { it.durationInSeconds } / 60
        }
    }

    private fun calculateStreak(workSessions: List<StudySession>): Int {
        if (workSessions.isEmpty()) return 0

        val distinctDays = workSessions.map {
            Calendar.getInstance().apply { time = it.startTime }.let { cal ->
                cal.set(Calendar.HOUR_OF_DAY, 0)
                cal.set(Calendar.MINUTE, 0)
                cal.set(Calendar.SECOND, 0)
                cal.set(Calendar.MILLISECOND, 0)
                cal.timeInMillis
            }
        }.distinct().sortedDescending()

        var streak = 0
        val today = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis

        var expectedDay = today
        for (day in distinctDays) {
            if (day == expectedDay) {
                streak++
                expectedDay -= 24 * 60 * 60 * 1000
            } else if (day < expectedDay) {
                break
            }
        }
        return streak
    }

    private fun startOfDay(dayOffset: Int): Long {
        val cal = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            add(Calendar.DAY_OF_YEAR, dayOffset)
        }
        return cal.timeInMillis
    }

    private fun startOfMonth(monthOffset: Int): Long {
        val cal = Calendar.getInstance().apply {
            set(Calendar.DAY_OF_MONTH, 1)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            add(Calendar.MONTH, monthOffset)
        }
        return cal.timeInMillis
    }
}

class StatisticsDashboardViewModelFactory(
    private val repository: StudyRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StatisticsDashboardViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return StatisticsDashboardViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}