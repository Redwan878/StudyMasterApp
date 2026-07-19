package com.porashona.studymaster.ui.exam

import com.porashona.studymaster.data.dao.ExamModeDao
import com.porashona.studymaster.data.model.ExamMode
import com.porashona.studymaster.data.model.Pref
import com.porashona.studymaster.data.preferences.PreferencesManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ExamModeManager(private val examModeDao: ExamModeDao, private val preferencesManager: PreferencesManager) {
    private val _currentMode = MutableStateFlow<ExamMode?>(null)
    val currentMode: StateFlow<ExamMode?> = _currentMode.asStateFlow()

    private val _allModes = MutableStateFlow<List<ExamMode>>(emptyList())
    val allModes: StateFlow<List<ExamMode>> = _allModes.asStateFlow()

    private val _isExamModeActive = MutableStateFlow(false)
    val isExamModeActive: StateFlow<Boolean> = _isExamModeActive.asStateFlow()

    init {
        loadModes()
    }

    fun loadModes() {
        com.porashona.studymaster.StudyMasterApplication.appCoroutineScope.launch {
            examModeDao.getActiveMode().collect { mode ->
                _currentMode.value = mode
                _isExamModeActive.value = mode != null
            }
        }

        com.porashona.studymaster.StudyMasterApplication.appCoroutineScope.launch {
            examModeDao.getAllModes().collect { modes ->
                _allModes.value = modes
            }
        }
    }

    suspend fun activateExamMode(modeId: Int): ExamMode? {
        // Deactivate current mode
        examModeDao.deactivateAll()

        // Activate new mode
        examModeDao.activateModeById(modeId)

        // Save to preferences
        preferencesManager.setExamModeId(modeId)

        // Update state
        val activated = examModeDao.getModeById(modeId)?.copy(isActive = true)
        _currentMode.value = activated
        _isExamModeActive.value = activated != null

        return activated
    }

    suspend fun activateCustomExamMode(mode: ExamMode): ExamMode {
        examModeDao.deactivateAll()
        examModeDao.insert(mode.copy(isActive = true))

        preferencesManager.setExamModeId(mode.id)
        _currentMode.value = mode.copy(isActive = true)
        _isExamModeActive.value = true

        return mode.copy(isActive = true)
    }

    suspend fun deactivateExamMode() {
        examModeDao.deactivateAll()
        preferencesManager.setExamModeId(-1)

        _currentMode.value = null
        _isExamModeActive.value = false
    }

    suspend fun getModeById(id: Int): ExamMode? {
        return examModeDao.getModeById(id)
    }

    suspend fun getCurrentModeSettings(): Pair<Int, Int> {
        // Return (studyMinutes, breakMinutes)
        val mode = _currentMode.value ?: return Pair(25, 5)
        return Pair(mode.studyMinutes, mode.breakMinutes)
    }

    suspend fun saveCustomMode(mode: ExamMode): Int {
        examModeDao.insert(mode)
        return mode.id
    }

    suspend fun updateMode(mode: ExamMode) {
        examModeDao.update(mode)
    }

    suspend fun deleteCustomMode(modeId: Int) {
        if (modeId > 0) { // Don't delete predefined modes
            examModeDao.deleteMode(modeId)
        }
    }

    fun initializeWithPredefinedModes() {
        val predefined = com.porashona.studymaster.data.model.PredefinedExamModes.getAllModes()

        com.porashona.studymaster.StudyMasterApplication.appCoroutineScope.launch {
            predefined.forEach { mode ->
                val exists = examModeDao.getModeById(mode.id) != null
                if (!exists) {
                    examModeDao.insert(mode)
                }
            }
        }
    }

    // Holiday mode helpers
    suspend fun activateHolidayMode(intensity: Int = 3) {
        val mode = when (intensity) {
            1 -> com.porashona.studymaster.data.model.PredefinedExamModes.getAllModes().find { it.name == "Holiday Revision" }
            2 -> com.porashona.studymaster.data.model.PredefinedExamModes.getAllModes().find { it.name == "Quick Holiday Sync" }
            else -> com.porashona.studymaster.data.model.PredefinedExamModes.getAllModes().firstOrNull()
        } ?: return

        activateExamMode(mode.id)
    }

    fun getHolidayModeOptions(): List<Pair<String, String>> {
        return listOf(
            Pair("none", "No Holiday Mode"),
            Pair("light", "Light Holiday Mode (30m/10m)"),
            Pair("normal", "Holiday Revision Mode (30m/10m)"),
            Pair("intensive", "Holiday Intensive Mode (45m/8m)")
        )
    }

    fun getExamModeOptions(): List<Pair<String, String>> {
        return listOf(
            Pair("exam_marathon", "Exam Marathon Mode (50m/5m)"),
            Pair("semester_finals", "Semester Finals Mode (55m/5m)"),
            Pair("weekend_intensive", "Weekend Intensive Mode (45m/8m)")
        )
    }
}
