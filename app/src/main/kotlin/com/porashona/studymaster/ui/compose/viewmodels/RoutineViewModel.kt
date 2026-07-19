package com.porashona.studymaster.ui.compose.viewmodels

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.porashona.studymaster.data.dao.AcademicEventDao
import com.porashona.studymaster.data.dao.ExamDao
import com.porashona.studymaster.data.dao.RoutineDao
import com.porashona.studymaster.data.model.AcademicEvent
import com.porashona.studymaster.data.model.EventType
import com.porashona.studymaster.data.model.Exam
import com.porashona.studymaster.data.model.Routine
import com.porashona.studymaster.data.preferences.PreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

// ─── Data Classes ─────────────────────────────────────────────────────────

data class RoutineConflict(
    val routine: Routine,
    val conflictingWith: Routine,
    val overlapMinutes: Int
)

data class MasterRoutineSlot(
    val hour: Int,
    val minute: Int,
    val routines: List<Routine>
)

enum class ExamMode { EXAM_DAY, NON_EXAM_DAY }

data class RoutineTemplate(
    val name: String,
    val routines: List<Routine>,
    val isBuiltIn: Boolean = false
)

// ─── ViewModel ────────────────────────────────────────────────────────────

@HiltViewModel
class RoutineViewModel @Inject constructor(
    private val routineDao: RoutineDao,
    private val academicEventDao: AcademicEventDao,
    private val examDao: ExamDao,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    // ─── Core State ───────────────────────────────────────────────────────
    val routines: StateFlow<List<Routine>> = routineDao.getAllRoutines()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val academicEvents: StateFlow<List<AcademicEvent>> = academicEventDao.getAllEvents()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _conflicts = MutableStateFlow<List<RoutineConflict>>(emptyList())
    val conflicts: StateFlow<List<RoutineConflict>> = _conflicts.asStateFlow()

    private val _masterRoutine = MutableStateFlow<List<MasterRoutineSlot>>(emptyList())
    val masterRoutine: StateFlow<List<MasterRoutineSlot>> = _masterRoutine.asStateFlow()

    private val _weeklyTemplates = MutableStateFlow<List<RoutineTemplate>>(emptyList())
    val weeklyTemplates: StateFlow<List<RoutineTemplate>> = _weeklyTemplates.asStateFlow()

    // ─── Exam Mode ────────────────────────────────────────────────────────
    val examMode: StateFlow<ExamMode> = examDao.getUpcomingExams(System.currentTimeMillis())
        .map { exams ->
            val today = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }
            val tomorrow = today.clone() as Calendar
            tomorrow.add(Calendar.DAY_OF_MONTH, 1)

            val hasExamToday = exams.any { exam ->
                val examDay = Calendar.getInstance().apply { timeInMillis = exam.examDate }
                examDay.set(Calendar.HOUR_OF_DAY, 0)
                examDay.set(Calendar.MINUTE, 0)
                examDay.set(Calendar.SECOND, 0)
                examDay.set(Calendar.MILLISECOND, 0)
                examDay.timeInMillis in today.timeInMillis until tomorrow.timeInMillis
            }
            if (hasExamToday) ExamMode.EXAM_DAY else ExamMode.NON_EXAM_DAY
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ExamMode.NON_EXAM_DAY)

    // ─── One-shot events ──────────────────────────────────────────────────
    private val _events = MutableSharedFlow<RoutineEvent>(extraBufferCapacity = 16)
    val events = _events.asSharedFlow()

    // ─── Filter ───────────────────────────────────────────────────────────
    private val _filterDayOfWeek = MutableStateFlow(-1) // -1 = all
    val filterDayOfWeek: StateFlow<Int> = _filterDayOfWeek.asStateFlow()

    val filteredRoutines: StateFlow<List<Routine>> = combine(
        routines, _filterDayOfWeek
    ) { allRoutines, dayOfWeek ->
        if (dayOfWeek < 0) allRoutines
        else allRoutines.filter { it.repeatDays.contains(dayOfWeek) }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        loadBuiltInTemplates()
    }

    // ═══════════════════════════════════════════════════════════════════════
    // CRUD
    // ═══════════════════════════════════════════════════════════════════════

    fun addRoutine(routine: Routine) {
        viewModelScope.launch {
            routineDao.insert(routine)
            detectConflicts()
            _events.emit(RoutineEvent.RoutineAdded(routine))
        }
    }

    fun updateRoutine(routine: Routine) {
        viewModelScope.launch {
            routineDao.update(routine)
            detectConflicts()
            _events.emit(RoutineEvent.RoutineUpdated(routine))
        }
    }

    fun deleteRoutine(routine: Routine) {
        viewModelScope.launch {
            routineDao.delete(routine)
            detectConflicts()
            _events.emit(RoutineEvent.RoutineDeleted(routine))
        }
    }

    fun toggleRoutineEnabled(id: Long, enabled: Boolean) {
        viewModelScope.launch {
            routineDao.setEnabled(id, enabled)
        }
    }

    // ═══════════════════════════════════════════════════════════════════════
    // Conflict Detection
    // ═══════════════════════════════════════════════════════════════════════

    fun detectConflicts() {
        viewModelScope.launch {
            val allRoutines = routineDao.getAllRoutines().first()
            val enabledRoutines = allRoutines.filter { it.isEnabled }
            val foundConflicts = mutableListOf<RoutineConflict>()

            for (i in enabledRoutines.indices) {
                for (j in i + 1 until enabledRoutines.size) {
                    val a = enabledRoutines[i]
                    val b = enabledRoutines[j]
                    // Check day overlap
                    val dayOverlap = a.repeatDays.any { it in b.repeatDays }
                    if (!dayOverlap) continue

                    // Check time overlap
                    val aStartMinutes = a.hour * 60 + a.minute
                    val aEndMinutes = aStartMinutes + a.durationMinutes
                    val bStartMinutes = b.hour * 60 + b.minute
                    val bEndMinutes = bStartMinutes + b.durationMinutes

                    if (aStartMinutes < bEndMinutes && bStartMinutes < aEndMinutes) {
                        val overlapStart = maxOf(aStartMinutes, bStartMinutes)
                        val overlapEnd = minOf(aEndMinutes, bEndMinutes)
                        foundConflicts.add(
                            RoutineConflict(
                                routine = a,
                                conflictingWith = b,
                                overlapMinutes = overlapEnd - overlapStart
                            )
                        )
                    }
                }
            }
            _conflicts.value = foundConflicts
        }
    }

    // ═══════════════════════════════════════════════════════════════════════
    // PDF Import
    // ═══════════════════════════════════════════════════════════════════════

    fun importPdfRoutine(uri: Uri) {
        viewModelScope.launch {
            // PDF import logic placeholder — in production this would parse
            // the PDF using a PDF library and create routines from the
            // extracted schedule data.
            _events.emit(RoutineEvent.PdfImportStarted)
            // ... parsing logic ...
            _events.emit(RoutineEvent.PdfImportCompleted(0))
        }
    }

    // ═══════════════════════════════════════════════════════════════════════
    // Templates
    // ═══════════════════════════════════════════════════════════════════════

    fun createTemplate(name: String, routines: List<Routine>) {
        val template = RoutineTemplate(name = name, routines = routines)
        _weeklyTemplates.value = _weeklyTemplates.value + template
        viewModelScope.launch { _events.emit(RoutineEvent.TemplateCreated(name)) }
    }

    fun applyTemplate(template: RoutineTemplate) {
        viewModelScope.launch {
            routineDao.deleteAll()
            template.routines.forEach { routineDao.insert(it) }
            detectConflicts()
            _events.emit(RoutineEvent.TemplateApplied(template.name))
        }
    }

    private fun loadBuiltInTemplates() {
        _weeklyTemplates.value = listOf(
            RoutineTemplate(
                name = "SSC রুটিন",
                isBuiltIn = true,
                routines = generateSSCTemplate()
            )
        )
    }

    private fun generateSSCTemplate(): List<Routine> {
        val allDays = listOf(0, 1, 2, 3, 4, 5, 6)
        return listOf(
            Routine(subjectName = "গণিত", title = "গণিত প্র্যাকটিস", hour = 6, minute = 0, durationMinutes = 60, repeatDays = listOf(0, 2, 4)),
            Routine(subjectName = "পদার্থবিজ্ঞান", title = "পদার্থ পড়া", hour = 7, minute = 0, durationMinutes = 50, repeatDays = listOf(1, 3, 5)),
            Routine(subjectName = "রসায়ন", title = "রসায়ন পড়া", hour = 8, minute = 0, durationMinutes = 50, repeatDays = listOf(0, 2, 4)),
            Routine(subjectName = "জীববিজ্ঞান", title = "জীববিজ্ঞান রিভিশন", hour = 9, minute = 0, durationMinutes = 45, repeatDays = listOf(1, 3, 5)),
            Routine(subjectName = "ইংরেজি", title = "ইংরেজি গ্রামার", hour = 10, minute = 0, durationMinutes = 40, repeatDays = listOf(0, 2, 4)),
            Routine(subjectName = "বাংলা", title = "বাংলা রচনা", hour = 14, minute = 0, durationMinutes = 45, repeatDays = listOf(1, 3, 5)),
            Routine(subjectName = "ICT", title = "ICT প্র্যাকটিস", hour = 15, minute = 0, durationMinutes = 35, repeatDays = listOf(0, 3)),
            Routine(subjectName = "উচ্চতর গণিত", title = "উচ্চতর গণিত", hour = 16, minute = 0, durationMinutes = 50, repeatDays = listOf(2, 5)),
            Routine(subjectName = "বাংলাদেশ ও বিশ্বপরিচয়", title = "BGS রিভিশন", hour = 16, minute = 0, durationMinutes = 35, repeatDays = listOf(0, 4))
        )
    }

    // ═══════════════════════════════════════════════════════════════════════
    // Master Routine View
    // ═══════════════════════════════════════════════════════════════════════

    fun getMasterRoutineView(dayOfWeek: Int = -1) {
        viewModelScope.launch {
            val allRoutines = routineDao.getEnabledRoutines().first()
            val filtered = if (dayOfWeek < 0) allRoutines
            else allRoutines.filter { it.repeatDays.contains(dayOfWeek) }

            val slots = filtered
                .sortedBy { it.hour * 60 + it.minute }
                .groupBy { it.hour to it.minute }
                .map { (key, routines) ->
                    MasterRoutineSlot(hour = key.first, minute = key.second, routines = routines)
                }
                .sortedWith(compareBy<MasterRoutineSlot> { it.hour }.thenBy { it.minute })
            _masterRoutine.value = slots
        }
    }

    fun autoSlotTask(subjectName: String, durationMinutes: Int, preferredHour: Int = 9) {
        viewModelScope.launch {
            val allRoutines = routineDao.getEnabledRoutines().first()
            val today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1 // 0=Sun
            val todayRoutines = allRoutines.filter { it.repeatDays.contains(today) }

            // Find the first available slot
            var slotHour = preferredHour
            var slotMinute = 0
            var found = false

            for (hour in preferredHour..22) {
                val hourStart = hour * 60
                val hourEnd = hourStart + 60

                val occupied = todayRoutines
                    .map { r -> r.hour * 60 + r.minute to (r.hour * 60 + r.minute + r.durationMinutes) }
                    .filter { (start, end) -> start < hourEnd && end > hourStart }

                if (occupied.isEmpty() && hourStart + durationMinutes <= 24 * 60) {
                    slotHour = hour
                    found = true
                    break
                }
            }

            if (found) {
                val routine = Routine(
                    subjectName = subjectName,
                    title = "$subjectName অটো-স্লট",
                    hour = slotHour,
                    minute = slotMinute,
                    durationMinutes = durationMinutes,
                    repeatDays = listOf(today)
                )
                routineDao.insert(routine)
                detectConflicts()
                _events.emit(RoutineEvent.AutoSlotDone(routine))
            } else {
                _events.emit(RoutineEvent.AutoSlotFailed("কোনো ফাঁকা স্লট পাওয়া যায়নি"))
            }
        }
    }

    // ═══════════════════════════════════════════════════════════════════════
    // Filtering
    // ═══════════════════════════════════════════════════════════════════════

    fun setFilterDayOfWeek(dayOfWeek: Int) {
        _filterDayOfWeek.value = dayOfWeek
    }

    fun clearFilter() {
        _filterDayOfWeek.value = -1
    }
}

// ─── Events ───────────────────────────────────────────────────────────────

sealed class RoutineEvent {
    data class RoutineAdded(val routine: Routine) : RoutineEvent()
    data class RoutineUpdated(val routine: Routine) : RoutineEvent()
    data class RoutineDeleted(val routine: Routine) : RoutineEvent()
    data class TemplateCreated(val name: String) : RoutineEvent()
    data class TemplateApplied(val name: String) : RoutineEvent()
    object PdfImportStarted : RoutineEvent()
    data class PdfImportCompleted(val routinesAdded: Int) : RoutineEvent()
    data class AutoSlotDone(val routine: Routine) : RoutineEvent()
    data class AutoSlotFailed(val reason: String) : RoutineEvent()
}