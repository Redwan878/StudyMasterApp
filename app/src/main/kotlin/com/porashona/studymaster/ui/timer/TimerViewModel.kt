package com.porashona.studymaster.ui.timer

import android.os.CountDownTimer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.porashona.studymaster.data.model.SessionType
import com.porashona.studymaster.data.model.StudySession
import com.porashona.studymaster.data.model.Subject
import com.porashona.studymaster.data.repository.StudyRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*

enum class TimerState {
    IDLE, RUNNING, PAUSED
}

class TimerViewModel(private val repository: StudyRepository) : ViewModel() {

    companion object {
        private const val WORK_DURATION = 25 * 60 * 1000L // 25 minutes
        private const val SHORT_BREAK_DURATION = 5 * 60 * 1000L // 5 minutes
        private const val LONG_BREAK_DURATION = 15 * 60 * 1000L // 15 minutes
        private const val POMODOROS_UNTIL_LONG_BREAK = 4
    }

    private var countDownTimer: CountDownTimer? = null
    private var sessionStartTime: Long = 0

    private val _timerState = MutableStateFlow(TimerState.IDLE)
    val timerState: StateFlow<TimerState> = _timerState.asStateFlow()

    private val _sessionType = MutableStateFlow(SessionType.WORK)
    val sessionType: StateFlow<SessionType> = _sessionType.asStateFlow()

    private val _timeLeftMillis = MutableStateFlow(WORK_DURATION)
    val timeLeftFormatted: StateFlow<String> = _timeLeftMillis.map { millis ->
        val minutes = (millis / 1000) / 60
        val seconds = (millis / 1000) % 60
        String.format("%02d:%02d", minutes, seconds)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "25:00")

    private val _progress = MutableStateFlow(1f)
    val progress: StateFlow<Float> = _progress.asStateFlow()

    private val _pomodoroCount = MutableStateFlow(0)
    val pomodoroCount: StateFlow<Int> = _pomodoroCount.asStateFlow()

    private val _timerFinished = MutableStateFlow(false)
    val timerFinished: StateFlow<Boolean> = _timerFinished.asStateFlow()

    private val _selectedSubject = MutableStateFlow<Subject?>(null)
    val selectedSubject: StateFlow<Subject?> = _selectedSubject.asStateFlow()

    val subjects: Flow<List<Subject>> = repository.allSubjects

    val todayStudyTime: StateFlow<Long> = repository.getTodayStudyTime()
        .map { it ?: 0L }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0L)

    private var totalDuration: Long = WORK_DURATION
    private var timeWhenPaused: Long = WORK_DURATION

    fun setSessionType(type: SessionType) {
        if (_timerState.value != TimerState.IDLE) return
        
        _sessionType.value = type
        totalDuration = when (type) {
            SessionType.WORK -> WORK_DURATION
            SessionType.SHORT_BREAK -> SHORT_BREAK_DURATION
            SessionType.LONG_BREAK -> LONG_BREAK_DURATION
        }
        _timeLeftMillis.value = totalDuration
        _progress.value = 1f
    }

    fun setSelectedSubject(subject: Subject?) {
        _selectedSubject.value = subject
    }

    fun startTimer() {
        sessionStartTime = System.currentTimeMillis()
        _timerState.value = TimerState.RUNNING
        startCountdown(_timeLeftMillis.value)
    }

    fun pauseTimer() {
        countDownTimer?.cancel()
        timeWhenPaused = _timeLeftMillis.value
        _timerState.value = TimerState.PAUSED
    }

    fun resumeTimer() {
        _timerState.value = TimerState.RUNNING
        startCountdown(timeWhenPaused)
    }

    fun resetTimer() {
        countDownTimer?.cancel()
        _timerState.value = TimerState.IDLE
        _timeLeftMillis.value = totalDuration
        _progress.value = 1f
        timeWhenPaused = totalDuration
    }

    private fun startCountdown(duration: Long) {
        countDownTimer?.cancel()
        countDownTimer = object : CountDownTimer(duration, 100) {
            override fun onTick(millisUntilFinished: Long) {
                _timeLeftMillis.value = millisUntilFinished
                _progress.value = millisUntilFinished.toFloat() / totalDuration
            }

            override fun onFinish() {
                _timeLeftMillis.value = 0
                _progress.value = 0f
                onTimerComplete()
            }
        }.start()
    }

    private fun onTimerComplete() {
        _timerState.value = TimerState.IDLE
        _timerFinished.value = true

        if (_sessionType.value == SessionType.WORK) {
            _pomodoroCount.value += 1
            saveSession()
            
            // Auto-switch to break
            if (_pomodoroCount.value % POMODOROS_UNTIL_LONG_BREAK == 0) {
                setSessionType(SessionType.LONG_BREAK)
            } else {
                setSessionType(SessionType.SHORT_BREAK)
            }
        } else {
            // After break, switch back to work
            setSessionType(SessionType.WORK)
        }
    }

    private fun saveSession() {
        viewModelScope.launch {
            val durationSeconds = (totalDuration / 1000)
            val xpEarned = calculateXp(durationSeconds)
            
            val session = StudySession(
                subjectId = _selectedSubject.value?.id ?: 0,
                subjectName = _selectedSubject.value?.name ?: "সাধারণ",
                durationInSeconds = durationSeconds,
                startTime = Date(sessionStartTime),
                endTime = Date(),
                sessionType = SessionType.WORK,
                completed = true,
                xpEarned = xpEarned
            )
            repository.insertSession(session)
        }
    }

    private fun calculateXp(durationSeconds: Long): Int {
        // 10 XP per minute
        return ((durationSeconds / 60) * 10).toInt()
    }

    fun onTimerFinishedHandled() {
        _timerFinished.value = false
    }

    fun addSubject(name: String, colorHex: String) {
        viewModelScope.launch {
            val subject = Subject(name = name, colorHex = colorHex)
            repository.insertSubject(subject)
        }
    }

    override fun onCleared() {
        super.onCleared()
        countDownTimer?.cancel()
    }
}

class TimerViewModelFactory(private val repository: StudyRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TimerViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TimerViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}