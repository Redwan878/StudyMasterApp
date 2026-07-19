package com.porashona.studymaster.ui.compose.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.porashona.studymaster.data.dao.ExamDao
import com.porashona.studymaster.data.dao.PracticeTestDao
import com.porashona.studymaster.data.dao.SyllabusChapterDao
import com.porashona.studymaster.data.dao.TaskDao
import com.porashona.studymaster.data.model.ChapterStatus
import com.porashona.studymaster.data.model.Exam
import com.porashona.studymaster.data.model.SyllabusChapter
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
import java.util.Locale
import javax.inject.Inject

// ─── Data Classes ─────────────────────────────────────────────────────────

data class ExamCountdown(
    val exam: Exam,
    val daysRemaining: Long,
    val hoursRemaining: Long,
    val minutesRemaining: Long
)

data class ExamPrepChecklist(
    val chapterId: Long,
    val chapterName: String,
    val chapterNumber: Int,
    val status: ChapterStatus,
    val topicsCompleted: Int,
    val topicsTotal: Int
)

data class SubjectGPA(
    val subjectName: String,
    val examCount: Int,
    val averagePercentage: Double
)

// ─── ViewModel ────────────────────────────────────────────────────────────

@HiltViewModel
class ExamsViewModel @Inject constructor(
    private val examDao: ExamDao,
    private val syllabusChapterDao: SyllabusChapterDao,
    private val taskDao: TaskDao,
    private val practiceTestDao: PracticeTestDao
) : ViewModel() {

    // ─── Exams ───────────────────────────────────────────────────────────
    val exams: StateFlow<List<Exam>> = examDao.getAllExams()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _upcomingExams = MutableStateFlow<List<ExamCountdown>>(emptyList())
    val upcomingExams: StateFlow<List<ExamCountdown>> = _upcomingExams.asStateFlow()

    // ─── Syllabus Chapters ───────────────────────────────────────────────
    private val _syllabusChapters = MutableStateFlow<List<SyllabusChapter>>(emptyList())
    val syllabusChapters: StateFlow<List<SyllabusChapter>> = _syllabusChapters.asStateFlow()

    private val _examPrepChecklist = MutableStateFlow<List<ExamPrepChecklist>>(emptyList())
    val examPrepChecklist: StateFlow<List<ExamPrepChecklist>> = _examPrepChecklist.asStateFlow()

    // ─── Countdown Data ──────────────────────────────────────────────────
    private val _countdownData = MutableStateFlow<List<ExamCountdown>>(emptyList())
    val countdownData: StateFlow<List<ExamCountdown>> = _countdownData.asStateFlow()

    // ─── GPA ─────────────────────────────────────────────────────────────
    private val _gpaData = MutableStateFlow<List<SubjectGPA>>(emptyList())
    val gpaData: StateFlow<List<SubjectGPA>> = _gpaData.asStateFlow()

    private val _overallGPA = MutableStateFlow(0.0)
    val overallGPA: StateFlow<Double> = _overallGPA.asStateFlow()

    // ─── Selected Exam ───────────────────────────────────────────────────
    private val _selectedExamId = MutableStateFlow<Long?>(null)
    val selectedExamId: StateFlow<Long?> = _selectedExamId.asStateFlow()

    // ─── Events ──────────────────────────────────────────────────────────
    private val _events = MutableStateFlow<ExamEvent?>(null)
    val events: StateFlow<ExamEvent?> = _events.asStateFlow()

    init {
        loadUpcomingExams()
        loadCountdowns()
    }

    // ═══════════════════════════════════════════════════════════════════════
    // CRUD
    // ═══════════════════════════════════════════════════════════════════════

    fun addExam(
        name: String,
        subjectId: Long? = null,
        subjectName: String? = null,
        examDate: Long,
        examTime: String? = null,
        venue: String = "",
        notes: String = ""
    ) {
        viewModelScope.launch {
            val exam = Exam(
                name = name,
                subjectId = subjectId,
                subjectName = subjectName,
                examDate = examDate,
                examTime = examTime,
                venue = venue,
                notes = notes
            )
            examDao.insert(exam)
            loadUpcomingExams()
            loadCountdowns()
            _events.value = ExamEvent.ExamCreated
        }
    }

    fun updateExam(exam: Exam) {
        viewModelScope.launch {
            examDao.update(exam)
            loadCountdowns()
        }
    }

    fun completeExam(examId: Long, result: String?, reflection: String?) {
        viewModelScope.launch {
            examDao.markAsCompleted(examId, result, reflection)
            loadUpcomingExams()
            loadCountdowns()
            _events.value = ExamEvent.ExamCompleted(examId)
        }
    }

    fun deleteExam(exam: Exam) {
        viewModelScope.launch {
            examDao.delete(exam)
            loadUpcomingExams()
            loadCountdowns()
            _events.value = ExamEvent.ExamDeleted(exam.id)
        }
    }

    // ═══════════════════════════════════════════════════════════════════════
    // Syllabus & Prep
    // ═══════════════════════════════════════════════════════════════════════

    fun getSyllabusChecklist(subjectId: Long) {
        viewModelScope.launch {
            _selectedExamId.value = null
            val chapters = syllabusChapterDao.getBySubject(subjectId).first()
            _syllabusChapters.value = chapters

            _examPrepChecklist.value = chapters.map { chapter ->
                ExamPrepChecklist(
                    chapterId = chapter.id,
                    chapterName = chapter.chapterName,
                    chapterNumber = chapter.chapterNumber,
                    status = ChapterStatus.valueOf(chapter.status),
                    topicsCompleted = chapter.completedTopics,
                    topicsTotal = chapter.totalTopics
                )
            }
        }
    }

    fun getSyllabusChecklistForExam(examId: Long) {
        viewModelScope.launch {
            _selectedExamId.value = examId
            val exam = examDao.getExamById(examId) ?: return@launch
            val subjectId = exam.subjectId ?: return@launch

            val chapters = syllabusChapterDao.getBySubject(subjectId).first()
            _syllabusChapters.value = chapters

            _examPrepChecklist.value = chapters.map { chapter ->
                ExamPrepChecklist(
                    chapterId = chapter.id,
                    chapterName = chapter.chapterName,
                    chapterNumber = chapter.chapterNumber,
                    status = ChapterStatus.valueOf(chapter.status),
                    topicsCompleted = chapter.completedTopics,
                    topicsTotal = chapter.totalTopics
                )
            }

            // Update exam preparation progress based on chapter completion
            val totalChapters = chapters.size
            if (totalChapters > 0) {
                val completedChapters = chapters.count { it.status == ChapterStatus.COMPLETED.name }
                val progress = (completedChapters * 100) / totalChapters
                examDao.updateProgress(examId, progress)
            }
        }
    }

    fun updateChapterProgress(chapterId: Long, newStatus: ChapterStatus) {
        viewModelScope.launch {
            syllabusChapterDao.updateStatus(chapterId, newStatus.name)

            // Refresh checklist
            val chapters = _syllabusChapters.value
            _examPrepChecklist.value = chapters.map { chapter ->
                if (chapter.chapterId == chapterId) {
                    val newTopicsCompleted = if (newStatus == ChapterStatus.COMPLETED) {
                        chapter.topicsTotal
                    } else {
                        chapter.topicsCompleted
                    }
                    chapter.copy(
                        status = newStatus,
                        topicsCompleted = newTopicsCompleted
                    )
                } else {
                    chapter
                }
            }

            // Update exam progress if viewing an exam
            _selectedExamId.value?.let { examId ->
                val total = _examPrepChecklist.value.size
                if (total > 0) {
                    val completed = _examPrepChecklist.value.count { it.status == ChapterStatus.COMPLETED }
                    examDao.updateProgress(examId, (completed * 100) / total)
                }
            }
        }
    }

    // ═══════════════════════════════════════════════════════════════════════
    // GPA
    // ═══════════════════════════════════════════════════════════════════════

    fun getGPA() {
        viewModelScope.launch {
            val completedExams = examDao.getCompletedExams().first()
            val resultsBySubject = completedExams
                .filter { it.result != null && it.subjectName != null }
                .groupBy { it.subjectName!! }
                .map { (subjectName, exams) ->
                    val avgResult = exams.mapNotNull { it.result?.toDoubleOrNull() }.average()
                    SubjectGPA(
                        subjectName = subjectName,
                        examCount = exams.size,
                        averagePercentage = if (avgResult.isNaN()) 0.0 else avgResult
                    )
                }
            _gpaData.value = resultsBySubject

            // Overall GPA on a 5.0 scale (Bangladesh SSC/HSC grading)
            _overallGPA.value = if (resultsBySubject.isNotEmpty()) {
                resultsBySubject.map { percentageToGPA(it.averagePercentage) }.average()
            } else {
                0.0
            }
        }
    }

    // ═══════════════════════════════════════════════════════════════════════
    // Helpers
    // ═══════════════════════════════════════════════════════════════════════

    fun clearEvent() {
        _events.value = null
    }

    private fun loadUpcomingExams() {
        viewModelScope.launch {
            val now = System.currentTimeMillis()
            val upcoming = examDao.getUpcomingExams(now).first()
            _upcomingExams.value = upcoming.map { exam ->
                createCountdown(exam)
            }
        }
    }

    private fun loadCountdowns() {
        viewModelScope.launch {
            val now = System.currentTimeMillis()
            val allUpcoming = examDao.getUpcomingExams(now).first()
            _countdownData.value = allUpcoming.map { createCountdown(it) }
        }
    }

    private fun createCountdown(exam: Exam): ExamCountdown {
        val diff = exam.examDate - System.currentTimeMillis()
        val days = diff / (1000 * 60 * 60 * 24)
        val hours = (diff % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60)
        val minutes = (diff % (1000 * 60 * 60)) / (1000 * 60)
        return ExamCountdown(exam, days.coerceAtLeast(0), hours.coerceAtLeast(0), minutes.coerceAtLeast(0))
    }

    /**
     * Convert percentage to Bangladesh SSC/HSC GPA scale (1.0 - 5.0)
     * 80-100: 5.0 (A+)
     * 70-79:  4.0 (A)
     * 60-69:  3.5 (A-)
     * 50-59:  3.0 (B)
     * 40-49:  2.0 (C)
     * 33-39:  1.0 (D)
     * <33:    0.0 (F)
     */
    private fun percentageToGPA(percentage: Double): Double = when {
        percentage >= 80 -> 5.0
        percentage >= 70 -> 4.0
        percentage >= 60 -> 3.5
        percentage >= 50 -> 3.0
        percentage >= 40 -> 2.0
        percentage >= 33 -> 1.0
        else -> 0.0
    }
}

// ─── Events ───────────────────────────────────────────────────────────────

sealed class ExamEvent {
    object ExamCreated : ExamEvent()
    data class ExamCompleted(val examId: Long) : ExamEvent()
    data class ExamDeleted(val examId: Long) : ExamEvent()
}