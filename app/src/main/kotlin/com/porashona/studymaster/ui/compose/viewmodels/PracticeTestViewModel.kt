package com.porashona.studymaster.ui.compose.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.porashona.studymaster.data.dao.PracticeTestDao
import com.porashona.studymaster.data.model.PracticeTest
import com.porashona.studymaster.data.model.PracticeTestResult
import com.porashona.studymaster.data.model.QuestionBank
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

// ─── Enums ────────────────────────────────────────────────────────────────

enum class TestState {
    NOT_STARTED, IN_PROGRESS, COMPLETED, REVIEWING
}

// ─── Data Classes ─────────────────────────────────────────────────────────

data class TestQuestion(
    val question: QuestionBank,
    val selectedOption: Int = 0, // 0=unanswered, 1=A, 2=B, 3=C, 4=D
    val isCorrect: Boolean? = null,
    val timeSpentSeconds: Long = 0
)

data class ChapterBreakdownEntry(
    val chapterName: String,
    val totalQuestions: Int,
    val correctCount: Int,
    val percentage: Double
)

// ─── ViewModel ────────────────────────────────────────────────────────────

@HiltViewModel
class PracticeTestViewModel @Inject constructor(
    private val practiceTestDao: PracticeTestDao
) : ViewModel() {

    // ─── Available Tests ─────────────────────────────────────────────────
    val availableTests: StateFlow<List<PracticeTest>> = practiceTestDao.getAllTests()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val completedTestCount: StateFlow<Int> = practiceTestDao.getCompletedTestCount()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    // ─── Current Test State ──────────────────────────────────────────────
    private val _currentTest = MutableStateFlow<PracticeTest?>(null)
    val currentTest: StateFlow<PracticeTest?> = _currentTest.asStateFlow()

    private val _testState = MutableStateFlow(TestState.NOT_STARTED)
    val testState: StateFlow<TestState> = _testState.asStateFlow()

    private val _testQuestions = MutableStateFlow<List<TestQuestion>>(emptyList())
    val testQuestions: StateFlow<List<TestQuestion>> = _testQuestions.asStateFlow()

    private val _currentQuestionIndex = MutableStateFlow(0)
    val currentQuestionIndex: StateFlow<Int> = _currentQuestionIndex.asStateFlow()

    val currentQuestion: StateFlow<TestQuestion?> = _currentQuestionIndex
        .map { index -> _testQuestions.value.getOrNull(index) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    // ─── Answers & Score ─────────────────────────────────────────────────
    private val _answers = MutableStateFlow<Map<Long, Int>>(emptyMap()) // questionId → selectedOption
    val answers: StateFlow<Map<Long, Int>> = _answers.asStateFlow()

    private val _score = MutableStateFlow(0.0)
    val score: StateFlow<Double> = _score.asStateFlow()

    // ─── Timer ───────────────────────────────────────────────────────────
    private val _timeRemaining = MutableStateFlow(0L)
    val timeRemaining: StateFlow<Long> = _timeRemaining.asStateFlow()

    private var timerJob: Job? = null

    // ─── Wrong Answer Mode ───────────────────────────────────────────────
    private val _wrongAnswerMode = MutableStateFlow(false)
    val wrongAnswerMode: StateFlow<Boolean> = _wrongAnswerMode.asStateFlow()

    private val _wrongQuestions = MutableStateFlow<List<QuestionBank>>(emptyList())
    val wrongQuestions: StateFlow<List<QuestionBank>> = _wrongQuestions.asStateFlow()

    // ─── Chapter Breakdown ───────────────────────────────────────────────
    private val _chapterBreakdown = MutableStateFlow<List<ChapterBreakdownEntry>>(emptyList())
    val chapterBreakdown: StateFlow<List<ChapterBreakdownEntry>> = _chapterBreakdown.asStateFlow()

    // ─── Result ──────────────────────────────────────────────────────────
    private val _lastResult = MutableStateFlow<PracticeTestResult?>(null)
    val lastResult: StateFlow<PracticeTestResult?> = _lastResult.asStateFlow()

    // ─── Events ─────────────────────────────────────────────────────────
    private val _events = MutableStateFlow<TestEvent?>(null)
    val events: StateFlow<TestEvent?> = _events.asStateFlow()

    // ═══════════════════════════════════════════════════════════════════════
    // Test Creation
    // ═══════════════════════════════════════════════════════════════════════

    fun createTest(
        title: String,
        subjectId: Long? = null,
        subjectName: String? = null,
        totalQuestions: Int = 10,
        durationMinutes: Int = 15,
        negativeMarkingEnabled: Boolean = false,
        negativeMarkValue: Double = 0.25,
        isMixedSubject: Boolean = false
    ) {
        viewModelScope.launch {
            val test = PracticeTest(
                title = title,
                subjectId = subjectId,
                subjectName = subjectName,
                totalQuestions = totalQuestions,
                durationMinutes = durationMinutes,
                negativeMarkingEnabled = negativeMarkingEnabled,
                negativeMarkValue = negativeMarkValue,
                isMixedSubject = isMixedSubject
            )
            practiceTestDao.insertTest(test)
            _events.value = TestEvent.TestCreated
        }
    }

    // ═══════════════════════════════════════════════════════════════════════
    // Test Execution
    // ═══════════════════════════════════════════════════════════════════════

    fun startTest(testId: Long) {
        viewModelScope.launch {
            val test = practiceTestDao.getTestById(testId) ?: return@launch
            _currentTest.value = test
            _testState.value = TestState.IN_PROGRESS
            _currentQuestionIndex.value = 0
            _answers.value = emptyMap()
            _score.value = 0.0
            _wrongAnswerMode.value = false
            _chapterBreakdown.value = emptyList()

            // Get random questions for this test
            val subjectId = test.subjectId ?: return@launch
            val questions = practiceTestDao.getRandomQuestions(subjectId, test.totalQuestions)
            _testQuestions.value = questions.map { TestQuestion(question = it) }

            // Start timer
            _timeRemaining.value = test.durationMinutes * 60L
            startTimer()
        }
    }

    fun startWrongAnswerRetake(testId: Long, wrongQuestionIds: List<Long>) {
        viewModelScope.launch {
            val test = practiceTestDao.getTestById(testId) ?: return@launch
            _currentTest.value = test
            _testState.value = TestState.IN_PROGRESS
            _wrongAnswerMode.value = true
            _currentQuestionIndex.value = 0
            _answers.value = emptyMap()
            _score.value = 0.0

            // Get only the wrong questions
            val questions = practiceTestDao.getWrongAnswerQuestions(wrongQuestionIds).first()
            _testQuestions.value = questions.map { TestQuestion(question = it) }

            _timeRemaining.value = (questions.size * 2L) * 60L // 2 min per question
            startTimer()
        }
    }

    fun answerQuestion(questionId: Long, option: Int) {
        // 1=A, 2=B, 3=C, 4=D
        _answers.value = _answers.value.toMutableMap().apply { put(questionId, option) }

        // Update current question state
        val index = _currentQuestionIndex.value
        val questions = _testQuestions.value.toMutableList()
        val currentQ = questions.getOrNull(index) ?: return
        val isCorrect = option == currentQ.question.correctOption
        questions[index] = currentQ.copy(selectedOption = option, isCorrect = isCorrect)
        _testQuestions.value = questions
    }

    fun nextQuestion() {
        val nextIndex = _currentQuestionIndex.value + 1
        if (nextIndex >= _testQuestions.value.size) {
            finishTest()
        } else {
            _currentQuestionIndex.value = nextIndex
        }
    }

    fun previousQuestion() {
        val prevIndex = _currentQuestionIndex.value - 1
        if (prevIndex >= 0) {
            _currentQuestionIndex.value = prevIndex
        }
    }

    fun goToQuestion(index: Int) {
        if (index in _testQuestions.value.indices) {
            _currentQuestionIndex.value = index
        }
    }

    // ═══════════════════════════════════════════════════════════════════════
    // Test Completion
    // ═══════════════════════════════════════════════════════════════════════

    fun finishTest() {
        timerJob?.cancel()

        val test = _currentTest.value ?: return
        val questions = _testQuestions.value
        val answersMap = _answers.value

        var correctCount = 0
        var wrongCount = 0
        var skippedCount = 0
        var totalMarks = 0.0
        var earnedMarks = 0.0

        val wrongQuestionIds = mutableListOf<Long>()

        for (testQ in questions) {
            val q = testQ.question
            totalMarks += 1.0

            val selected = answersMap[q.id] ?: 0
            when {
                selected == 0 -> skippedCount++
                selected == q.correctOption -> {
                    correctCount++
                    earnedMarks += 1.0
                }
                else -> {
                    wrongCount++
                    wrongQuestionIds.add(q.id)
                    if (test.negativeMarkingEnabled) {
                        earnedMarks -= test.negativeMarkValue
                    }
                }
            }
        }

        val percentage = if (totalMarks > 0) (earnedMarks / totalMarks * 100) else 0.0
        val timeTakenSeconds = (test.durationMinutes * 60L) - _timeRemaining.value

        // Build chapter breakdown
        val chapterGroups = questions.groupBy { it.question.chapterName ?: "অজানা" }
        val breakdown = chapterGroups.map { (chapterName, qList) ->
            val chapterCorrect = qList.count { answersMap[it.question.id] == it.question.correctOption }
            ChapterBreakdownEntry(
                chapterName = chapterName,
                totalQuestions = qList.size,
                correctCount = chapterCorrect,
                percentage = if (qList.isNotEmpty()) (chapterCorrect.toDouble() / qList.size * 100) else 0.0
            )
        }
        _chapterBreakdown.value = breakdown

        // Save result
        viewModelScope.launch {
            val result = PracticeTestResult(
                testId = test.id,
                score = earnedMarks,
                totalMarks = totalMarks,
                percentage = percentage,
                timeTakenSeconds = timeTakenSeconds,
                correctCount = correctCount,
                wrongCount = wrongCount,
                skippedCount = skippedCount,
                chapterBreakdown = JSONObject(breakdown.associate {
                    it.chapterName to mapOf("correct" to it.correctCount, "total" to it.totalQuestions)
                }).toString()
            )
            practiceTestDao.insertResult(result)
            practiceTestDao.markTestCompleted(test.id)

            _lastResult.value = result
            _wrongQuestions.value = practiceTestDao.getWrongAnswerQuestions(wrongQuestionIds).first()
            _testState.value = TestState.COMPLETED
            _score.value = percentage
            _events.value = TestEvent.TestCompleted(percentage, correctCount, wrongCount, skippedCount)
        }
    }

    fun retakeWrongAnswers() {
        val test = _currentTest.value ?: return
        val wrongIds = _wrongQuestions.value.map { it.id }
        if (wrongIds.isEmpty()) return
        startWrongAnswerRetake(test.id, wrongIds)
    }

    fun startReviewing() {
        _testState.value = TestState.REVIEWING
        _currentQuestionIndex.value = 0
    }

    // ═══════════════════════════════════════════════════════════════════════
    // Timer
    // ═══════════════════════════════════════════════════════════════════════

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (true) {
                delay(1000L)
                val remaining = _timeRemaining.value - 1
                if (remaining <= 0) {
                    _timeRemaining.value = 0
                    finishTest()
                    break
                } else {
                    _timeRemaining.value = remaining
                }
            }
        }
    }

    // ═══════════════════════════════════════════════════════════════════════
    // Utility
    // ═══════════════════════════════════════════════════════════════════════

    fun clearEvent() {
        _events.value = null
    }

    fun resetTest() {
        timerJob?.cancel()
        _currentTest.value = null
        _testState.value = TestState.NOT_STARTED
        _testQuestions.value = emptyList()
        _currentQuestionIndex.value = 0
        _answers.value = emptyMap()
        _score.value = 0.0
        _timeRemaining.value = 0L
        _wrongAnswerMode.value = false
        _chapterBreakdown.value = emptyList()
        _lastResult.value = null
    }

    fun deleteTest(test: PracticeTest) {
        viewModelScope.launch {
            practiceTestDao.deleteTest(test)
            _events.value = TestEvent.TestDeleted(test.id)
        }
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}

// ─── Events ───────────────────────────────────────────────────────────────

sealed class TestEvent {
    object TestCreated : TestEvent()
    data class TestCompleted(
        val percentage: Double,
        val correct: Int,
        val wrong: Int,
        val skipped: Int
    ) : TestEvent()
    data class TestDeleted(val testId: Long) : TestEvent()
}