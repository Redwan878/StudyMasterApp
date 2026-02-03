package com.porashona.studymaster.ui.routine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.porashona.studymaster.data.model.Routine
import com.porashona.studymaster.data.model.Subject
import com.porashona.studymaster.data.repository.StudyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RoutineViewModel(private val repository: StudyRepository) : ViewModel() {

    val routines: StateFlow<List<Routine>> = repository.allRoutines
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val subjects: StateFlow<List<Subject>> = repository.allSubjects
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addRoutine(routine: Routine) {
        viewModelScope.launch {
            repository.insertRoutine(routine)
        }
    }

    fun updateRoutine(routine: Routine) {
        viewModelScope.launch {
            repository.updateRoutine(routine)
        }
    }

    fun deleteRoutine(routine: Routine) {
        viewModelScope.launch {
            repository.deleteRoutine(routine)
        }
    }

    fun toggleRoutine(id: Long, enabled: Boolean) {
        viewModelScope.launch {
            repository.setRoutineEnabled(id, enabled)
        }
    }
}

class RoutineViewModelFactory(private val repository: StudyRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RoutineViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RoutineViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}