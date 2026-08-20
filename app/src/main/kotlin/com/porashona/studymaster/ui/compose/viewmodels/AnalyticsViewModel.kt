/*
package com.porashona.studymaster.ui.compose.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.porashona.studymaster.data.dao.GamificationDao
import com.porashona.studymaster.data.dao.PracticeTestDao
import com.porashona.studymaster.data.dao.StudySessionDao
import com.porashona.studymaster.data.dao.SubjectDao
import com.porashona.studymaster.data.dao.SyllabusChapterDao
import com.porashona.studymaster.data.dao.TaskDao
import com.porashona.studymaster.data.model.ChapterStatus
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
import java.util.Calendar
import javax.inject.Inject

// ─── Data Classes ─────────────────────────────────────────────────────────

data class SubjectTimeEntry(
    val subjectName: String,
    val totalTimeSeconds: Long,
    val totalTimeFormatted: String
) {
    val hours: Float get() = totalTimeSeconds / 3600f
}

data class HeatmapDay(
    val date: String, // yyyy-MM-dd
    val studyMinutes: Long,
    val intensity: Float // 0-1 normalized
)

data class WeeklyReport(
    val weekLabel: String,
    val totalMinutes: Long,
    val sessionCount: Int,
    val averageSessionMinutes: Long,
    val topSubject: String?,
    val xpEarned: Int,
    val testsTaken: Int,
    val averageScore: Double,
    val tasksCompleted: Int,
    val dailyBreakdown: List<DailyBreakdown>
)

data class DailyBreakdown(
    val dayName: String,
    val minutes: Long,
    val sessions: Int
)

data class WeekComparison(
    val thisWeekMinutes: Long,
    val lastWeekMinutes: Long,
    val changePercent: Float,
    val thisWeekSessions: Int,
    val lastWeekSessions: Int
)

data class PredictedGrade(
    val subjectName: String,
    val currentLevel: String,
    val predictedGrade: String,
    val confidence: Float,
    val recommendation: String
)

// ─── ViewModel ────────────────────────────────────────────────────────────

@HiltViewModel
class AnalyticsViewModel @Inject constructor(
    private val studySessionDao: StudySessionDao,
    private val subjectDao: SubjectDao,
    private val practiceTestDao: PracticeTestDao,
    private val taskDao: TaskDao,
    private val gamificationDao: GamificationDao,
    private val syllabusChapterDao: SyllabusChapterDao
) : ViewModel() {

    // ─── Time Per Subject ───────────────────────────────────────────────
    val timePerSubject: StateFlow<List<SubjectTimeEntry>> = studySessionDao.getTimeBySubject()
        .map { subjectTimes ->
            subjectTimes.map {
                SubjectTimeEntry(
                    subjectName = it.subjectName,
                    totalTimeSeconds = it.totalTime,
                    totalTimeFormatted = formatDuration(it.totalTime)
                )
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // ─── Weak Chapter Heatmap ───────────────────────────────────────────
    private val _weakChapterHeatmap = MutableStateFlow<List<SyllabusChapter>>(emptyList())
    val weakChapterHeatmap: StateFlow<List<SyllabusChapter>> = _weakChapterHeatmap.asStateFlow()

    // ─── Streak Calendar ────────────────────────────────────────────────
    private val _streakCalendar = MutableStateFlow<List<HeatmapDay>>(emptyList())
    val streakCalendar: StateFlow<List<HeatmapDay>> = _streakCalendar.asStateFlow()

    // ─── Score Trends ───────────────────────────────────────────────────
    val scoreTrends: StateFlow<List<PracticeTestDao.ScoreTrend>> = practiceTestDao.getScoreTrends()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // ─── Predicted Grade ────────────────────────────────────────────────
    private val _predictedGrade = MutableStateFlow<List<PredictedGrade>>(emptyList())
    val predictedGrade: StateFlow<List<PredictedGrade>> = _predictedGrade.asStateFlow()

    // ─── Weekly Report ──────────────────────────────────────────────────
    private val _weeklyReport = MutableStateFlow<WeeklyReport?>(null)
    val weeklyReport: StateFlow<WeeklyReport?> = _weeklyReport.asStateFlow()

    // ─── Week Comparison ────────────────────────────────────────────────
    private val _lastWeekComparison = MutableStateFlow(WeekComparison())
    val lastWeekComparison: StateFlow<WeekComparison> = _lastWeekComparison.asStateFlow()

    // ─── Total Study Time ──────────────────────────────────────────────
    val totalStudyTime: StateFlow<Long> = studySessionDao.getTotalStudyTime()
        .map { it ?: 0L }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0L)

    // ─── Total Sessions ────────────────────────────────────────────────
    val totalSessions: StateFlow<Int> = studySessionDao.getTotalSessionCount()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    // ─── Loading ────────────────────────────────────────────────────────
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        loadAnalytics()
    }

    // ═══════════════════════════════════════════════════════════════════════
    // Public API
    // ═══════════════════════════════════════════════════════════════════════

    fun loadAnalytics() {
        viewModelScope.launch {
            _isLoading.value = true
            loadWeakChapterHeatmap()
            loadStreakCalendar()
            loadWeekComparison()
            getPredictedGrade()
            _isLoading.value = false
        }
    }

    fun generateWeeklyReport() {
        viewModelScope.launch {
            _weeklyReport.value = buildWeeklyReport()
        }
    }

    fun getPredictedGrade() {
        viewModelScope.launch {
            val grades = mutableListOf<PredictedGrade>()

            // For each subject, calculate predicted grade based on:
            // 1. Syllabus completion
            // 2. Practice test scores
            // 3. Study time

            val completions = syllabusChapterDao.getAllCompletionPercentages().first()
            val testTrends = practiceTestDao.getScoreTrends().first()
            val timeBySubject = studySessionDao.getTimeBySubject().first()

            for (completion in completions) {
                val subjectName = completion.subjectName ?: continue

                // Calculate prediction factors
                val syllabusFactor = completion.percentage / 100.0

                // Average test score for this subject
                val subjectScores = testTrends
                    .filter { /* would need subject in trend */ false }
                    .map { it.percentage }
                val testFactor = if (subjectScores.isNotEmpty()) {
                    subjectScores.average() / 100.0
                } else 0.5

                // Study time factor (more time = better prepared)
                val subjectTime = timeBySubject.find { it.subjectName == subjectName }?.totalTime ?: 0
                val timeFactor = minOf(1.0, subjectTime / (50 * 3600.0)) // 50 hours target

                // Weighted prediction
                val predictedScore = (syllabusFactor * 0.4 + testFactor * 0.4 + timeFactor * 0.2) * 100
                val grade = scoreToGrade(predictedScore)
                val confidence = calculateConfidence(syllabusFactor, testFactor, timeFactor)

                val recommendation = when {
                    syllabusFactor < 0.3 -> "সিলেবাস দ্রুত শেষ করুন। প্রতিদিন অধ্যায় পড়ুন।"
                    testFactor < 0.3 -> "প্র্যাকটিস টেস্ট বেশি দিন। MCQ প্র্যাকটিস করুন।"
                    timeFactor < 0.3 -> "এই বিষয়ে বেশি সময় দিন। রুটিনে যোগ করুন।"
                    predictedScore > 80 -> "চমৎকার! রিভিশন চালিয়ে যান।"
                    else -> "ভালো যাচ্ছে। দুর্বল অধ্যায়ে ফোকাস করুন।"
                }

                grades.add(
                    PredictedGrade(
                        subjectName = subjectName,
                        currentLevel = when {
                            predictedScore >= 80 -> "A+ স্তরে"
                            predictedScore >= 70 -> "A স্তরে"
                            predictedScore >= 60 -> "A- স্তরে"
                            predictedScore >= 50 -> "B স্তরে"
                            predictedScore >= 40 -> "C স্তরে"
                            else -> "উন্নতি প্রয়োজন"
                        },
                        predictedGrade = grade,
                        confidence = confidence,
                        recommendation = recommendation
                    )
                )
            }

            _predictedGrade.value = grades.sortedBy { it.confidence }
        }
    }

    // ═══════════════════════════════════════════════════════════════════════
    // Private Helpers
    // ═══════════════════════════════════════════════════════════════════════

    private suspend fun loadWeakChapterHeatmap() {
        val chapters = syllabusChapterDao.getAllChapters().first()
        // Sort by completion: least completed first
        _weakChapterHeatmap.value = chapters
            .filter { it.status != ChapterStatus.COMPLETED.name }
            .sortedBy { it.completedTopics.toFloat() / maxOf(1, it.totalTopics) }
    }

    private suspend fun loadStreakCalendar() {
        val dates = studySessionDao.getStudyDates().first()
        val calendar = Calendar.getInstance()

        val heatmap = mutableListOf<HeatmapDay>()
        val maxMinutes = 300L // 5 hours as max for normalization

        // Last 90 days
        for (i in 89 downTo 0) {
            val cal = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -i) }
            val year = cal.get(Calendar.YEAR)
            val month = cal.get(Calendar.MONTH) + 1
            val day = cal.get(Calendar.DAY_OF_MONTH)
            val dateStr = String.format("%04d-%02d-%02d", year, month, day)

            // Calculate minutes for this date
            val startOfDay = Calendar.getInstance().apply {
                set(Calendar.YEAR, year)
                set(Calendar.MONTH, month - 1)
                set(Calendar.DAY_OF_MONTH, day)
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }.timeInMillis

            val endOfDay = startOfDay + 24 * 60 * 60 * 1000
            val sessions = studySessionDao.getSessionsBetween(startOfDay, endOfDay).first()
            val totalMinutes = sessions.filter { it.sessionType == com.porashona.studymaster.data.model.SessionType.WORK }
                .sumOf { it.durationInSeconds / 60 }

            heatmap.add(
                HeatmapDay(
                    date = dateStr,
                    studyMinutes = totalMinutes,
                    intensity = (totalMinutes.toFloat() / maxMinutes).coerceIn(0f, 1f)
                )
            )
        }

        _streakCalendar.value = heatmap
    }

    private suspend fun loadWeekComparison() {
        val now = System.currentTimeMillis()
        val thisWeekStart = Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, -7)
        }.timeInMillis

        val lastWeekStart = Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, -14)
        }.timeInMillis

        val thisWeekTime = studySessionDao.getTotalStudyTimeSince(thisWeekStart).first() ?: 0L
        val lastWeekTime = studySessionDao.getTotalStudyTimeSince(lastWeekStart).first()?.let {
            (it - (thisWeekTime)) // Approximate: total since 2 weeks ago minus this week
        } ?: 0L

        val thisWeekSessions = studySessionDao.getSessionsBetween(thisWeekStart, now).first()
            .count { it.sessionType == com.porashona.studymaster.data.model.SessionType.WORK }

        val lastWeekSessions = studySessionDao.getSessionsBetween(lastWeekStart, thisWeekStart).first()
            .count { it.sessionType == com.porashona.studymaster.data.model.SessionType.WORK }

        val changePercent = if (lastWeekTime > 0) {
            ((thisWeekTime - lastWeekTime).toFloat() / lastWeekTime * 100)
        } else if (thisWeekTime > 0) {
            100f
        } else {
            0f
        }

        _lastWeekComparison.value = WeekComparison(
            thisWeekMinutes = thisWeekTime / 60,
            lastWeekMinutes = lastWeekTime / 60,
            changePercent = changePercent,
            thisWeekSessions = thisWeekSessions,
            lastWeekSessions = lastWeekSessions
        )
    }

    private suspend fun buildWeeklyReport(): WeeklyReport {
        val now = System.currentTimeMillis()
        val weekAgo = now - 7 * 24 * 60 * 60 * 1000

        val sessions = studySessionDao.getSessionsBetween(weekAgo, now).first()
            .filter { it.sessionType == com.porashona.studymaster.data.model.SessionType.WORK }

        val totalMinutes = sessions.sumOf { it.durationInSeconds / 60 }
        val sessionCount = sessions.size
        val avgSession = if (sessionCount > 0) totalMinutes / sessionCount else 0

        val topSubject = sessions
            .groupingBy { it.subjectName }
            .eachCount()
            .maxByOrNull { it.value }
            ?.key

        val xp = gamificationDao.getXPByDateRange(weekAgo, now) ?: 0

        val recentResults = practiceTestDao.getRecentResults(7).first()
        val avgScore = if (recentResults.isNotEmpty()) {
            recentResults.map { it.percentage }.average()
        } else 0.0

        val completedTasks = taskDao.getCompletedTasksCountSince(weekAgo).first()

        // Daily breakdown
        val dayNames = listOf("রবি", "সোম", "মঙ্গল", "বুধ", "বৃহ", "শুক্র", "শনি")
        val dailyBreakdown = (0 until 7).map { dayOffset ->
            val dayStart = now - (6 - dayOffset) * 24 * 60 * 60 * 1000
            val cal = Calendar.getInstance().apply { timeInMillis = dayStart }
            val dayEnd = dayStart + 24 * 60 * 60 * 1000

            val daySessions = sessions.filter {
                it.startTime.time in dayStart until dayEnd
            }
            DailyBreakdown(
                dayName = dayNames[cal.get(Calendar.DAY_OF_WEEK) - 1],
                minutes = daySessions.sumOf { it.durationInSeconds / 60 },
                sessions = daySessions.size
            )
        }

        val cal = Calendar.getInstance()
        val weekLabel = "${cal.get(Calendar.DAY_OF_MONTH)}/${cal.get(Calendar.MONTH) + 1}"

        return WeeklyReport(
            weekLabel = weekLabel,
            totalMinutes = totalMinutes,
            sessionCount = sessionCount,
            averageSessionMinutes = avgSession,
            topSubject = topSubject,
            xpEarned = xp,
            testsTaken = recentResults.size,
            averageScore = avgScore,
            tasksCompleted = completedTasks,
            dailyBreakdown = dailyBreakdown
        )
    }

    private fun scoreToGrade(score: Double): String = when {
        score >= 80 -> "A+ (5.00)"
        score >= 70 -> "A (4.00)"
        score >= 60 -> "A- (3.50)"
        score >= 50 -> "B (3.00)"
        score >= 40 -> "C (2.00)"
        score >= 33 -> "D (1.00)"
        else -> "F (0.00)"
    }

    private fun calculateConfidence(vararg factors: Double): Float {
        val avg = factors.average()
        // Higher when all factors have data (not defaults)
        val dataPoints = factors.count { it != 0.5 } // 0.5 is our default for missing test data
        return (avg * (dataPoints.toFloat() / factors.size) * 100).coerceIn(0f, 100f)
    }

    private fun formatDuration(seconds: Long): String {
        val hours = seconds / 3600
        val minutes = (seconds % 3600) / 60
        return when {
            hours > 0 -> "${hours}ঘণ্টা ${minutes}মি"
            minutes > 0 -> "${minutes}মিনিট"
            else -> "0মিনিট"
        }
    }
}
*/