package com.porashona.studymaster.ui.compose.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.porashona.studymaster.data.dao.RoutineDao
import com.porashona.studymaster.data.dao.StudySessionDao
import com.porashona.studymaster.data.dao.SubjectDao
import com.porashona.studymaster.data.model.SessionType
import com.porashona.studymaster.data.model.StudySession
import com.porashona.studymaster.data.model.Subject
import com.porashona.studymaster.data.model.TimerMode
import com.porashona.studymaster.data.model.TimerModes
import com.porashona.studymaster.data.preferences.PreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

// ─── Enums ────────────────────────────────────────────────────────────────

enum class ComposeTimerState {
    IDLE, RUNNING, PAUSED
}

enum class ComposeTimerPhase {
    WORK, SHORT_BREAK, LONG_BREAK
}

// ─── ViewModel ────────────────────────────────────────────────────────────

@HiltViewModel
class TimerViewModel @Inject constructor(
    private val studySessionDao: StudySessionDao,
    private val subjectDao: SubjectDao,
    private val routineDao: RoutineDao,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    companion object {
        private const val DEFAULT_WORK_MINUTES = 25
        private const val DEFAULT_SHORT_BREAK_MINUTES = 5
        private const val DEFAULT_LONG_BREAK_MINUTES = 15
        private const val POMODOROS_UNTIL_LONG_BREAK = 4
    }

    // ─── Timer Core State ─────────────────────────────────────────────────
    private val _timerState = MutableStateFlow(ComposeTimerState.IDLE)
    val timerState: StateFlow<ComposeTimerState> = _timerState.asStateFlow()

    private val _timerPhase = MutableStateFlow(ComposeTimerPhase.WORK)
    val timerPhase: StateFlow<ComposeTimerPhase> = _timerPhase.asStateFlow()

    private val _elapsedSeconds = MutableStateFlow(0L)
    val elapsedSeconds: StateFlow<Long> = _elapsedSeconds.asStateFlow()

    private val _totalSeconds = MutableStateFlow(DEFAULT_WORK_MINUTES * 60L)
    val totalSeconds: StateFlow<Long> = _totalSeconds.asStateFlow()

    // For work phase: count UP from 0 → totalSeconds
    // For break phase: count DOWN from totalSeconds → 0
    val displaySeconds: StateFlow<Long> = combine(
        _timerPhase, _elapsedSeconds, _totalSeconds
    ) { phase, elapsed, total ->
        when (phase) {
            ComposeTimerPhase.WORK -> elapsed
            else -> (total - elapsed).coerceAtLeast(0L)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0L)

    // ─── Subject & Tag ────────────────────────────────────────────────────
    private val _selectedSubject = MutableStateFlow<Subject?>(null)
    val selectedSubject: StateFlow<Subject?> = _selectedSubject.asStateFlow()

    private val _sessionTag = MutableStateFlow("")
    val sessionTag: StateFlow<String> = _sessionTag.asStateFlow()

    val subjects: StateFlow<List<Subject>> = subjectDao.getAllSubjects()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // ─── Pomodoro Counters ────────────────────────────────────────────────
    private val _completedPomodorosToday = MutableStateFlow(0)
    val completedPomodorosToday: StateFlow<Int> = _completedPomodorosToday.asStateFlow()

    private val _pomodorosInCurrentSet = MutableStateFlow(0)
    val pomodorosInCurrentSet: StateFlow<Int> = _pomodorosInCurrentSet.asStateFlow()

    // ─── Timer Mode (from preferences) ────────────────────────────────────
    private val _currentTimerMode = MutableStateFlow(TimerModes.CLASSIC_POMODORO)
    val currentTimerMode: StateFlow<TimerMode> = _currentTimerMode.asStateFlow()

    val allTimerModes: List<TimerMode> = TimerModes.allModes

    // ─── Weekly Focus Stats ───────────────────────────────────────────────
    val weeklyFocusMinutes: StateFlow<Long> = studySessionDao.getTotalStudyTimeSince(
        Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -7) }.timeInMillis
    ).map { total -> (total ?: 0L) / 60 }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0L)

    val weeklyFocusGoal: StateFlow<Int> = preferencesManager.weeklyGoalMinutes
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 600)

    // ─── Custom Durations per Subject ─────────────────────────────────────
    private val _customDurations = MutableStateFlow<Map<Long, Int>>(emptyMap())
    val customDurations: StateFlow<Map<Long, Int>> = _customDurations.asStateFlow()

    // ─── One-shot events ──────────────────────────────────────────────────
    private val _events = MutableStateFlow<TimerEvent?>(null)
    val events: StateFlow<TimerEvent?> = _events.asStateFlow()

    // ─── Internal ─────────────────────────────────────────────────────────
    private var timerJob: Job? = null
    private var sessionStartTime: Long = 0

    init {
        viewModelScope.launch {
            // Load timer mode preference
            preferencesManager.selectedTimerMode.collect { modeId ->
                val mode = TimerModes.getById(modeId)
                _currentTimerMode.value = mode
                if (_timerState.value == ComposeTimerState.IDLE) {
                    applyTimerMode(mode)
                }
            }
        }
        loadTodayPomodoroCount()
    }

    // ═══════════════════════════════════════════════════════════════════════
    // Public API
    // ═══════════════════════════════════════════════════════════════════════

    fun selectSubject(subject: Subject?) {
        _selectedSubject.value = subject
    }

    fun setSessionTag(tag: String) {
        _sessionTag.value = tag
    }

    fun setTimerMode(mode: TimerMode) {
        viewModelScope.launch {
            preferencesManager.setSelectedTimerMode(mode.id)
            _currentTimerMode.value = mode
            if (_timerState.value == ComposeTimerState.IDLE) {
                applyTimerMode(mode)
            }
        }
    }

    fun startTimer() {
        if (_timerState.value == ComposeTimerState.RUNNING) return

        sessionStartTime = System.currentTimeMillis()
        _timerState.value = ComposeTimerState.RUNNING
        startTicking()
    }

    fun pauseTimer() {
        if (_timerState.value != ComposeTimerState.RUNNING) return
        timerJob?.cancel()
        _timerState.value = ComposeTimerState.PAUSED
    }

    fun resumeTimer() {
        if (_timerState.value != ComposeTimerState.PAUSED) return
        _timerState.value = ComposeTimerState.RUNNING
        startTicking()
    }

    fun stopTimer() {
        timerJob?.cancel()
        if (_timerPhase.value == ComposeTimerPhase.WORK && _elapsedSeconds.value >= 60) {
            saveSession()
        }
        resetToWork()
    }

    fun skipBreak() {
        if (_timerPhase.value != ComposeTimerPhase.WORK) {
            timerJob?.cancel()
            resetToWork()
        }
    }

    fun autoStartNextSession() {
        // Called when timer finishes and auto-start is enabled.
        // Reset to IDLE then start so the UI sees the transition.
        _timerState.value = ComposeTimerState.IDLE
        startTimer()
    }

    fun setCustomDuration(subjectId: Long, minutes: Int) {
        _customDurations.value = _customDurations.value.toMutableMap().apply {
            put(subjectId, minutes)
        }
    }

    fun clearEvent() {
        _events.value = null
    }

    // ═══════════════════════════════════════════════════════════════════════
    // Timer Logic
    // ═══════════════════════════════════════════════════════════════════════

    private fun startTicking() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (true) {
                delay(1000L)
                val newElapsed = _elapsedSeconds.value + 1
                _elapsedSeconds.value = newElapsed

                // Check if timer is done
                if (newElapsed >= _totalSeconds.value) {
                    onTimerComplete()
                    break
                }
            }
        }
    }

    private fun onTimerComplete() {
        timerJob?.cancel()
        _timerState.value = ComposeTimerState.IDLE

        when (_timerPhase.value) {
            ComposeTimerPhase.WORK -> {
                saveSession()
                _completedPomodorosToday.value++
                _pomodorosInCurrentSet.value++

                // Determine next break type
                if (_pomodorosInCurrentSet.value >= POMODOROS_UNTIL_LONG_BREAK) {
                    switchToPhase(ComposeTimerPhase.LONG_BREAK)
                    _pomodorosInCurrentSet.value = 0
                } else {
                    switchToPhase(ComposeTimerPhase.SHORT_BREAK)
                }
                _events.value = TimerEvent.WorkCompleted(
                    pomodoroCount = _completedPomodorosToday.value,
                    durationMinutes = (_elapsedSeconds.value / 60).toInt()
                )
            }
            ComposeTimerPhase.SHORT_BREAK -> {
                switchToPhase(ComposeTimerPhase.WORK)
                _events.value = TimerEvent.BreakCompleted(isLong = false)
            }
            ComposeTimerPhase.LONG_BREAK -> {
                switchToPhase(ComposeTimerPhase.WORK)
                _events.value = TimerEvent.BreakCompleted(isLong = true)
            }
        }
    }

    private fun switchToPhase(phase: ComposeTimerPhase) {
        _timerPhase.value = phase
        _elapsedSeconds.value = 0L
        val mode = _currentTimerMode.value
        val subjectId = _selectedSubject.value?.id ?: -1
        val customMinutes = _customDurations.value[subjectId]

        when (phase) {
            ComposeTimerPhase.WORK -> {
                val minutes = customMinutes ?: mode.workDuration
                if (minutes == 0) {
                    // Stopwatch mode — unlimited, set a very large value
                    _totalSeconds.value = Long.MAX_VALUE / 1000
                } else {
                    _totalSeconds.value = minutes * 60L
                }
            }
            ComposeTimerPhase.SHORT_BREAK -> {
                _totalSeconds.value = (mode.shortBreakDuration * 60L).coerceAtLeast(0L)
                // If 0, auto-skip
                if (mode.shortBreakDuration == 0) {
                    resetToWork()
                    return
                }
            }
            ComposeTimerPhase.LONG_BREAK -> {
                _totalSeconds.value = mode.longBreakDuration * 60L
            }
        }
    }

    private fun resetToWork() {
        _timerState.value = ComposeTimerState.IDLE
        switchToPhase(ComposeTimerPhase.WORK)
    }

    private fun applyTimerMode(mode: TimerMode) {
        _timerPhase.value = ComposeTimerPhase.WORK
        _elapsedSeconds.value = 0L
        val subjectId = _selectedSubject.value?.id ?: -1
        val customMinutes = _customDurations.value[subjectId]
        val minutes = customMinutes ?: mode.workDuration
        _totalSeconds.value = if (minutes == 0) Long.MAX_VALUE / 1000 else minutes * 60L
    }

    // ═══════════════════════════════════════════════════════════════════════
    // Persistence
    // ═══════════════════════════════════════════════════════════════════════

    private fun saveSession() {
        val durationSeconds = _elapsedSeconds.value
        if (durationSeconds < 30) return // Skip very short sessions

        viewModelScope.launch {
            val xpEarned = calculateXP(durationSeconds)
            val session = StudySession(
                subjectId = _selectedSubject.value?.id ?: 0,
                subjectName = _selectedSubject.value?.name ?: "সাধারণ",
                durationInSeconds = durationSeconds,
                startTime = Date(sessionStartTime),
                endTime = Date(),
                sessionType = SessionType.WORK,
                completed = true,
                xpEarned = xpEarned,
                notes = _sessionTag.value.ifBlank { "" }
            )
            studySessionDao.insert(session)

            // Update subject time
            val subjectId = _selectedSubject.value?.id ?: return@launch
            if (subjectId > 0) {
                subjectDao.addTimeToSubject(subjectId, durationSeconds)
            }
        }
    }

    private fun calculateXP(durationSeconds: Long): Int {
        val minutes = durationSeconds / 60
        return (minutes * 10).toInt().coerceAtLeast(5)
    }

    private fun loadTodayPomodoroCount() {
        viewModelScope.launch {
            val calendar = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }
            studySessionDao.getSessionsBetween(calendar.timeInMillis, System.currentTimeMillis())
                .collect { sessions ->
                    val count = sessions.count { it.sessionType == SessionType.WORK && it.completed }
                    _completedPomodorosToday.value = count
                }
        }
    }

    // ─── Cleanup ──────────────────────────────────────────────────────────
    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}

// ─── Events ───────────────────────────────────────────────────────────────

sealed class TimerEvent {
    data class WorkCompleted(val pomodoroCount: Int, val durationMinutes: Int) : TimerEvent()
    data class BreakCompleted(val isLong: Boolean) : TimerEvent()
}