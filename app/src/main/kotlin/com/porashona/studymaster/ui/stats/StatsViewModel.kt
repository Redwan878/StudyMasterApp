package com.porashona.studymaster.ui.stats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.porashona.studymaster.data.repository.StudyRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class StatsViewModel(private val repository: StudyRepository) : ViewModel() {

    val todayTime: StateFlow<Long> = repository.getTodayStudyTime()
        .map { it ?: 0L }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0L)

    val weekTime: StateFlow<Long> = repository.getWeekStudyTime()
        .map { it ?: 0L }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0L)

    val totalTime: StateFlow<Long> = repository.totalStudyTime
        .map { it ?: 0L }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0L)

    val totalSessions: StateFlow<Int> = repository.totalSessionCount
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val currentStreak: StateFlow<Int> = repository.userProfile
        .map { it?.currentStreak ?: 0 }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val longestStreak: StateFlow<Int> = repository.userProfile
        .map { it?.longestStreak ?: 0 }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val subjectTimeData: StateFlow<List<Pair<String, Float>>> = repository.timeBySubject
        .map { list ->
            val total = list.sumOf { it.totalTime }.toFloat()
            if (total == 0f) return@map emptyList()
            list.map { subjectTime ->
                Pair(subjectTime.subjectName, (subjectTime.totalTime / total) * 100)
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val weeklyData: StateFlow<List<Pair<String, Float>>> = repository.getSessionsForWeek()
        .map { sessions ->
            val dayNames = listOf("শনি", "রবি", "সোম", "মঙ্গল", "বুধ", "বৃহ", "শুক্র")
            val calendar = Calendar.getInstance()
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            
            val last7Days = (0..6).map { daysAgo ->
                calendar.timeInMillis = System.currentTimeMillis()
                calendar.add(Calendar.DAY_OF_YEAR, -daysAgo)
                dateFormat.format(calendar.time)
            }.reversed()

            val sessionsByDay = sessions.groupBy { session ->
                dateFormat.format(session.startTime)
            }

            last7Days.mapIndexed { index, date ->
                val dayTotal = sessionsByDay[date]?.sumOf { it.durationInSeconds } ?: 0L
                val hours = dayTotal / 3600f
                Pair(dayNames[(calendar.apply { 
                    time = dateFormat.parse(date) ?: Date() 
                }.get(Calendar.DAY_OF_WEEK) + 5) % 7], hours)
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val productivityScore: StateFlow<Int> = combine(todayTime, currentStreak) { today, streak ->
        // Simple productivity calculation
        val timeScore = minOf((today / 3600f) * 20, 50f).toInt() // Up to 50 points for time
        val streakScore = minOf(streak * 5, 50) // Up to 50 points for streak
        timeScore + streakScore
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)
}

class StatsViewModelFactory(private val repository: StudyRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StatsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return StatsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}