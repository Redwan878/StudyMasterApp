package com.porashona.studymaster.ui.compose.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.porashona.studymaster.data.dao.AcademicEventDao
import com.porashona.studymaster.data.dao.ExamDao
import com.porashona.studymaster.data.dao.RoutineDao
import com.porashona.studymaster.data.dao.StudySessionDao
import com.porashona.studymaster.data.dao.TaskDao
import com.porashona.studymaster.data.model.AcademicEvent
import com.porashona.studymaster.data.model.EventType
import com.porashona.studymaster.data.model.Exam
import com.porashona.studymaster.data.model.Routine
import com.porashona.studymaster.data.model.StudySession
import com.porashona.studymaster.data.model.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

// ─── Data Classes ─────────────────────────────────────────────────────────

data class CalendarDayData(
    val dayOfMonth: Int,
    val month: Int,
    val year: Int,
    val hasStudySession: Boolean = false,
    val hasExam: Boolean = false,
    val hasEvent: Boolean = false,
    val hasTask: Boolean = false,
    val isToday: Boolean = false,
    val isCurrentMonth: Boolean = true,
    val timestamp: Long
)

data class DayEvents(
    val sessions: List<StudySession>,
    val exams: List<Exam>,
    val academicEvents: List<AcademicEvent>,
    val tasks: List<Task>,
    val routines: List<Routine>
)

// ─── ViewModel ────────────────────────────────────────────────────────────

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val routineDao: RoutineDao,
    private val examDao: ExamDao,
    private val academicEventDao: AcademicEventDao,
    private val studySessionDao: StudySessionDao,
    private val taskDao: TaskDao
) : ViewModel() {

    // ─── Selected Date ────────────────────────────────────────────────────
    private val _selectedDate = MutableStateFlow(Calendar.getInstance())
    val selectedDate: StateFlow<Calendar> = _selectedDate.asStateFlow()

    // ─── Events for Selected Date ─────────────────────────────────────────
    private val _eventsForDate = MutableStateFlow(DayEvents.empty())
    val eventsForDate: StateFlow<DayEvents> = _eventsForDate.asStateFlow()

    // ─── Month Overview ───────────────────────────────────────────────────
    private val _monthEvents = MutableStateFlow<List<AcademicEvent>>(emptyList())
    val monthEvents: StateFlow<List<AcademicEvent>> = _monthEvents.asStateFlow()

    private val _calendarWeekData = MutableStateFlow<List<CalendarDayData>>(emptyList())
    val calendarWeekData: StateFlow<List<CalendarDayData>> = _calendarWeekData.asStateFlow()

    private val _calendarMonthData = MutableStateFlow<List<CalendarDayData>>(emptyList())
    val calendarMonthData: StateFlow<List<CalendarDayData>> = _calendarMonthData.asStateFlow()

    // ─── Current viewing month ────────────────────────────────────────────
    private val _viewingMonth = MutableStateFlow(Calendar.getInstance())
    val viewingMonth: StateFlow<Calendar> = _viewingMonth.asStateFlow()

    init {
        loadMonthData()
        loadWeekData()
        loadEventsForSelectedDate()
    }

    // ═══════════════════════════════════════════════════════════════════════
    // Public API
    // ═══════════════════════════════════════════════════════════════════════

    fun selectDate(calendar: Calendar) {
        _selectedDate.value = calendar
        loadEventsForSelectedDate()
    }

    fun selectDate(timestamp: Long) {
        val cal = Calendar.getInstance().apply { timeInMillis = timestamp }
        selectDate(cal)
    }

    fun selectToday() {
        selectDate(Calendar.getInstance())
    }

    fun navigateMonth(delta: Int) {
        val cal = _viewingMonth.value.clone() as Calendar
        cal.add(Calendar.MONTH, delta)
        _viewingMonth.value = cal
        loadMonthData()
    }

    fun goToday() {
        _viewingMonth.value = Calendar.getInstance()
        _selectedDate.value = Calendar.getInstance()
        loadMonthData()
        loadWeekData()
        loadEventsForSelectedDate()
    }

    fun addEvent(event: AcademicEvent) {
        viewModelScope.launch {
            academicEventDao.insert(event)
            loadMonthData()
            loadEventsForSelectedDate()
        }
    }

    fun syncWithGoogleCalendar() {
        viewModelScope.launch {
            // Placeholder: Google Calendar sync would require Google Play Services
            // This would involve:
            // 1. Getting Google account credentials
            // 2. Fetching events from Google Calendar API
            // 3. Converting to AcademicEvent and inserting into local DB
            // For now, this is a no-op that can be implemented later.
        }
    }

    // ═══════════════════════════════════════════════════════════════════════
    // Data Loading
    // ═══════════════════════════════════════════════════════════════════════

    private fun loadEventsForSelectedDate() {
        viewModelScope.launch {
            val cal = _selectedDate.value
            val startOfDay = Calendar.getInstance().apply {
                set(Calendar.YEAR, cal.get(Calendar.YEAR))
                set(Calendar.MONTH, cal.get(Calendar.MONTH))
                set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH))
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }.timeInMillis

            val endOfDay = startOfDay + 24 * 60 * 60 * 1000

            val sessions = studySessionDao.getSessionsBetween(startOfDay, endOfDay).first()
            val exams = examDao.getExamsInRange(startOfDay, endOfDay).first()
            val events = academicEventDao.getEventsInRange(startOfDay, endOfDay).first()
            val dayOfWeek = (cal.get(Calendar.DAY_OF_WEEK) + 5) % 7 // Convert to 0=Sun
            val routines = routineDao.getAllRoutines().first()
                .filter { it.repeatDays.contains(dayOfWeek) }
            val tasks = taskDao.getTasksForDateRange(startOfDay, endOfDay).first()

            _eventsForDate.value = DayEvents(
                sessions = sessions,
                exams = exams,
                academicEvents = events,
                tasks = tasks,
                routines = routines
            )
        }
    }

    private fun loadMonthData() {
        viewModelScope.launch {
            val cal = _viewingMonth.value
            val startOfMonth = Calendar.getInstance().apply {
                set(Calendar.YEAR, cal.get(Calendar.YEAR))
                set(Calendar.MONTH, cal.get(Calendar.MONTH))
                set(Calendar.DAY_OF_MONTH, 1)
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }.timeInMillis

            val endOfMonth = Calendar.getInstance().apply {
                set(Calendar.YEAR, cal.get(Calendar.YEAR))
                set(Calendar.MONTH, cal.get(Calendar.MONTH))
                set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH))
                set(Calendar.HOUR_OF_DAY, 23)
                set(Calendar.MINUTE, 59)
                set(Calendar.SECOND, 59)
                set(Calendar.MILLISECOND, 999)
            }.timeInMillis

            _monthEvents.value = academicEventDao.getEventsInRange(startOfMonth, endOfMonth).first()

            // Build month grid data
            val days = mutableListOf<CalendarDayData>()
            val todayCal = Calendar.getInstance()
            val monthCal = _viewingMonth.value.clone() as Calendar

            // Previous month padding
            monthCal.set(Calendar.DAY_OF_MONTH, 1)
            val firstDayOfWeek = (monthCal.get(Calendar.DAY_OF_WEEK) + 5) % 7 // 0=Sun
            for (i in 0 until firstDayOfWeek) {
                val prevDay = monthCal.clone() as Calendar
                prevDay.add(Calendar.DAY_OF_MONTH, -(firstDayOfWeek - i))
                days.add(createDayData(prevDay, isCurrentMonth = false))
            }

            // Current month days
            val daysInMonth = monthCal.getActualMaximum(Calendar.DAY_OF_MONTH)
            val monthExams = examDao.getExamsInRange(startOfMonth, endOfMonth).first()
            val monthSessions = studySessionDao.getSessionsBetween(startOfMonth, endOfMonth).first()
            val monthTasks = taskDao.getTasksForDateRange(startOfMonth, endOfMonth).first()

            for (day in 1..daysInMonth) {
                val dayCal = Calendar.getInstance().apply {
                    set(Calendar.YEAR, monthCal.get(Calendar.YEAR))
                    set(Calendar.MONTH, monthCal.get(Calendar.MONTH))
                    set(Calendar.DAY_OF_MONTH, day)
                    set(Calendar.HOUR_OF_DAY, 0)
                    set(Calendar.MINUTE, 0)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                }
                val nextDay = dayCal.timeInMillis + 24 * 60 * 60 * 1000

                val daySessions = monthSessions.filter {
                    it.startTime.time in dayCal.timeInMillis until nextDay
                }
                val dayExams = monthExams.filter {
                    it.examDate in dayCal.timeInMillis until nextDay
                }
                val dayEvents = _monthEvents.value.filter {
                    it.date in dayCal.timeInMillis until nextDay
                }
                val dayTasks = monthTasks.filter { task ->
                    task.dueDate?.let { it in dayCal.timeInMillis until nextDay } == true
                }

                val isToday = dayCal.get(Calendar.YEAR) == todayCal.get(Calendar.YEAR) &&
                        dayCal.get(Calendar.MONTH) == todayCal.get(Calendar.MONTH) &&
                        dayCal.get(Calendar.DAY_OF_MONTH) == todayCal.get(Calendar.DAY_OF_MONTH)

                days.add(
                    CalendarDayData(
                        dayOfMonth = day,
                        month = monthCal.get(Calendar.MONTH),
                        year = monthCal.get(Calendar.YEAR),
                        hasStudySession = daySessions.isNotEmpty(),
                        hasExam = dayExams.isNotEmpty(),
                        hasEvent = dayEvents.isNotEmpty(),
                        hasTask = dayTasks.isNotEmpty(),
                        isToday = isToday,
                        isCurrentMonth = true,
                        timestamp = dayCal.timeInMillis
                    )
                )
            }

            // Next month padding to complete the grid
            val remaining = 42 - days.size // 6 rows x 7 days
            for (i in 1..remaining) {
                val nextDay = Calendar.getInstance().apply {
                    timeInMillis = days.last().timestamp
                    add(Calendar.DAY_OF_MONTH, 1)
                    // Actually calculate from the last day
                    set(Calendar.YEAR, monthCal.get(Calendar.YEAR))
                    set(Calendar.MONTH, monthCal.get(Calendar.MONTH))
                    set(Calendar.DAY_OF_MONTH, daysInMonth + i)
                }
                days.add(createDayData(nextDay, isCurrentMonth = false))
            }

            _calendarMonthData.value = days
        }
    }

    private fun loadWeekData() {
        viewModelScope.launch {
            val cal = _selectedDate.value.clone() as Calendar
            // Go to start of week (Sunday)
            cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
            cal.set(Calendar.HOUR_OF_DAY, 0)
            cal.set(Calendar.MINUTE, 0)
            cal.set(Calendar.SECOND, 0)
            cal.set(Calendar.MILLISECOND, 0)

            val todayCal = Calendar.getInstance()
            val weekDays = mutableListOf<CalendarDayData>()

            for (i in 0 until 7) {
                val dayCal = cal.clone() as Calendar
                dayCal.add(Calendar.DAY_OF_MONTH, i)
                weekDays.add(createDayData(dayCal, todayCal))
            }

            _calendarWeekData.value = weekDays
        }
    }

    private fun createDayData(
        cal: Calendar,
        todayCal: Calendar = Calendar.getInstance(),
        isCurrentMonth: Boolean = true
    ): CalendarDayData {
        val isToday = cal.get(Calendar.YEAR) == todayCal.get(Calendar.YEAR) &&
                cal.get(Calendar.MONTH) == todayCal.get(Calendar.MONTH) &&
                cal.get(Calendar.DAY_OF_MONTH) == todayCal.get(Calendar.DAY_OF_MONTH)

        return CalendarDayData(
            dayOfMonth = cal.get(Calendar.DAY_OF_MONTH),
            month = cal.get(Calendar.MONTH),
            year = cal.get(Calendar.YEAR),
            isToday = isToday,
            isCurrentMonth = isCurrentMonth,
            timestamp = cal.timeInMillis
        )
    }
}

// ─── Extensions ───────────────────────────────────────────────────────────

fun DayEvents.Companion.empty() = DayEvents(
    sessions = emptyList(),
    exams = emptyList(),
    academicEvents = emptyList(),
    tasks = emptyList(),
    routines = emptyList()
)