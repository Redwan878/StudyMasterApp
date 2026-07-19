package com.porashona.studymaster.ui.compose.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.porashona.studymaster.data.dao.ExamDao
import com.porashona.studymaster.data.dao.GamificationDao
import com.porashona.studymaster.data.dao.RoutineDao
import com.porashona.studymaster.data.dao.SyllabusChapterDao
import com.porashona.studymaster.data.dao.TaskDao
import com.porashona.studymaster.data.model.DailyChallenge
import com.porashona.studymaster.data.model.Exam
import com.porashona.studymaster.data.model.Task
import com.porashona.studymaster.data.model.UserLevel
import com.porashona.studymaster.data.model.XPGain
import com.porashona.studymaster.data.repository.ExtendedRepository
import com.porashona.studymaster.data.repository.StudyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val studyRepository: StudyRepository,
    private val extendedRepository: ExtendedRepository,
    private val gamificationDao: GamificationDao,
    private val examDao: ExamDao,
    private val routineDao: RoutineDao,
    private val taskDao: TaskDao,
    private val syllabusChapterDao: SyllabusChapterDao
) : ViewModel() {

    // ─── Today's Study Sessions ───────────────────────────────────────────
    val todaySessions: StateFlow<List<com.porashona.studymaster.data.model.StudySession>> =
        studyRepository.getSessionsForToday()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // ─── Today Study Minutes ──────────────────────────────────────────────
    val todayStudyMinutes: StateFlow<Long> = studyRepository.getTodayStudyTime()
        .map { (it ?: 0L) / 60 }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0L)

    // ─── Current Streak (from user profile) ───────────────────────────────
    val currentStreak: StateFlow<Int> = studyRepository.userProfile
        .map { it?.currentStreak ?: 0 }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    // ─── Total XP & Level (from gamification) ─────────────────────────────
    val userLevel: StateFlow<UserLevel?> = gamificationDao.getUserLevel()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val totalXP: StateFlow<Int> = userLevel.map { it?.totalXP ?: 0 }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val currentLevel: StateFlow<Int> = userLevel.map { it?.currentLevel ?: 1 }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 1)

    // ─── Next Exam ────────────────────────────────────────────────────────
    val nextExam: StateFlow<Exam?> = examDao.getUpcomingExams(System.currentTimeMillis())
        .map { it.firstOrNull() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    // ─── Upcoming Tasks (pending, sorted by priority then date) ───────────
    val upcomingTasks: StateFlow<List<Task>> = taskDao.getPendingTasks()
        .map { tasks ->
            tasks.sortedWith(
                compareByDescending<Task> { it.priority.ordinal }
                    .thenBy { it.dueDate ?: Long.MAX_VALUE }
            )
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // ─── Daily Challenge ──────────────────────────────────────────────────
    private val _dailyChallenge = MutableStateFlow<DailyChallenge?>(null)
    val dailyChallenge: StateFlow<DailyChallenge?> = _dailyChallenge.asStateFlow()

    // ─── Study Suggestion ─────────────────────────────────────────────────
    private val _studySuggestion = MutableStateFlow(StudySuggestion.empty())
    val studySuggestion: StateFlow<StudySuggestion> = _studySuggestion.asStateFlow()

    // ─── Weekly Goal Progress ─────────────────────────────────────────────
    val weeklyGoalProgress: StateFlow<WeeklyGoalProgress> =
        combine(
            studyRepository.getWeekStudyTime().map { (it ?: 0L) / 60 },
            syllabusChapterDao.getAllCompletionPercentages()
        ) { weeklyMinutes, completions ->
            val weakSubjects = completions
                .filter { it.percentage < 50.0 }
                .sortedBy { it.percentage }
                .take(3)
                .map { it.subjectName ?: "Unknown" }
            WeeklyGoalProgress(
                studiedMinutes = weeklyMinutes,
                weakSubjects = weakSubjects
            )
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), WeeklyGoalProgress())

    // ─── One-shot events ──────────────────────────────────────────────────
    private val _events = MutableStateFlow<HomeEvent?>(null)
    val events: StateFlow<HomeEvent?> = _events.asStateFlow()

    // ═══════════════════════════════════════════════════════════════════════
    // Public API
    // ═══════════════════════════════════════════════════════════════════════

    init {
        refreshHome()
    }

    fun refreshHome() {
        viewModelScope.launch {
            loadDailyChallenge()
            loadStudySuggestion()
        }
    }

    fun loadStudySuggestion() {
        viewModelScope.launch {
            val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
            val suggestion = when {
                hour < 6 -> StudySuggestion(
                    title = "বিশ্রাম নিন",
                    description = "এখন পড়াশোনার সময় নয়। ভালো ঘুম আপনার মেধাকে বাড়ায়।",
                    icon = "\uD83C\uDF19",
                    priority = "rest"
                )
                hour in 6..9 -> StudySuggestion(
                    title = "সকালের ফোকাস সেশন",
                    description = "সকালে মন প্রশান্ত থাকে। কঠিন অধ্যায়গুলো এখন পড়ুন।",
                    icon = "\u2600\uFE0F",
                    priority = "high"
                )
                hour in 10..12 -> StudySuggestion(
                    title = "সকালের সেশন - প্র্যাকটিস",
                    description = "MCQ প্র্যাকটিস ও সূত্র রিভিউ করুন।",
                    icon = "\uD83D\uDCDD",
                    priority = "medium"
                )
                hour in 13..16 -> StudySuggestion(
                    title = "দুপুরের রিভিশন",
                    description = "সকালে পড়া বিষয়গুলো রিভিশন করুন। ছোট টপিক শেষ করুন।",
                    icon = "\uD83D\uDCDA",
                    priority = "medium"
                )
                hour in 17..20 -> StudySuggestion(
                    title = "সন্ধ্যার পড়াশোনা",
                    description = "ফ্ল্যাশকার্ড রিভিউ ও দুর্বল বিষয়ে ফোকাস করুন।",
                    icon = "\uD83D\uDD25",
                    priority = "high"
                )
                else -> StudySuggestion(
                    title = "রাতের লাইট স্টাডি",
                    description = "হালকা পড়াশোনা বা নোট সাজানোর কাজ করুন।",
                    icon = "\uD83C\uDF1F",
                    priority = "low"
                )
            }

            // Enhance suggestion with weak subject data
            val completions = syllabusChapterDao.getAllCompletionPercentages().first()
            val weakest = completions.minByOrNull { it.percentage }
            if (weakest != null && weakest.percentage < 60.0 && hour in 6..20) {
                _studySuggestion.value = suggestion.copy(
                    description = "${suggestion.description}\n\n\u26A0\uFE0F দুর্বল: ${weakest.subjectName} (${weakest.percentage.toInt()}% সম্পন্ন)",
                    subjectHint = weakest.subjectName
                )
            } else {
                _studySuggestion.value = suggestion
            }
        }
    }

    fun markChallengeComplete() {
        viewModelScope.launch {
            val challenge = _dailyChallenge.value ?: return@launch
            if (!challenge.isCompleted) {
                gamificationDao.markCompleted(challenge.id)
                gamificationDao.insertXPGain(
                    XPGain(
                        source = "daily_challenge",
                        sourceId = challenge.id,
                        amount = challenge.rewardXP
                    )
                )
                _dailyChallenge.value = challenge.copy(isCompleted = true)
                _events.value = HomeEvent.ChallengeCompleted(challenge.rewardXP)
            }
        }
    }

    fun clearEvent() {
        _events.value = null
    }

    // ═══════════════════════════════════════════════════════════════════════
    // Private helpers
    // ═══════════════════════════════════════════════════════════════════════

    private suspend fun loadDailyChallenge() {
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        val challenge = gamificationDao.getActiveChallenge(today)
        _dailyChallenge.value = challenge
    }
}

// ─── Data classes ─────────────────────────────────────────────────────────

data class StudySuggestion(
    val title: String = "",
    val description: String = "",
    val icon: String = "\uD83D\uDCDA",
    val priority: String = "medium",
    val subjectHint: String? = null
) {
    companion object {
        fun empty() = StudySuggestion(
            title = "আজকে কী পড়বেন?",
            description = "আপনার পড়াশোনার পরিকল্পনা শুরু করুন।",
            icon = "\uD83D\uDCDA"
        )
    }
}

data class WeeklyGoalProgress(
    val studiedMinutes: Long = 0,
    val targetMinutes: Int = 600,
    val weakSubjects: List<String> = emptyList()
) {
    val progressPercentage: Float
        get() = if (targetMinutes > 0) (studiedMinutes.toFloat() / targetMinutes * 100).coerceIn(0f, 100f) else 0f
}

sealed class HomeEvent {
    data class ChallengeCompleted(val xpEarned: Int) : HomeEvent()
    data class Error(val message: String) : HomeEvent()
}