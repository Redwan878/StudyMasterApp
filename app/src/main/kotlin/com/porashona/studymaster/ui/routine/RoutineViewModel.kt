package com.porashona.studymaster.ui.routine

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.porashona.studymaster.data.model.Routine
import com.porashona.studymaster.data.model.Subject
import com.porashona.studymaster.data.repository.StudyRepository
import com.porashona.studymaster.utils.NotificationHelper
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RoutineViewModel(
    private val repository: StudyRepository,
    private val notificationHelper: NotificationHelper
) : ViewModel() {

    val routines: StateFlow<List<Routine>> = repository.allRoutines
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val subjects: StateFlow<List<Subject>> = repository.allSubjects
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addRoutine(routine: Routine) {
        viewModelScope.launch {
            val id = repository.insertRoutine(routine)
            notificationHelper.scheduleRoutineAlarm(routine.copy(id = id))
        }
    }

    fun updateRoutine(routine: Routine) {
        viewModelScope.launch {
            repository.updateRoutine(routine)
            notificationHelper.scheduleRoutineAlarm(routine)
        }
    }

    fun deleteRoutine(routine: Routine) {
        viewModelScope.launch {
            notificationHelper.cancelRoutineAlarm(routine.id)
            repository.deleteRoutine(routine)
        }
    }

    fun toggleRoutine(id: Long, enabled: Boolean) {
        viewModelScope.launch {
            repository.setRoutineEnabled(id, enabled)
            val routine = routines.value.firstOrNull { it.id == id } ?: return@launch
            val updated = routine.copy(isEnabled = enabled)
            if (enabled) notificationHelper.scheduleRoutineAlarm(updated)
            else notificationHelper.cancelRoutineAlarm(id)
        }
    }
}

class RoutineViewModelFactory(
    private val repository: StudyRepository,
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RoutineViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RoutineViewModel(repository, NotificationHelper(context.applicationContext)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
