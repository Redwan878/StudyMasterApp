package com.porashona.studymaster.ui.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.porashona.studymaster.data.model.AcademicEvent
import com.porashona.studymaster.data.repository.ExtendedRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class CalendarViewModel(private val repository: ExtendedRepository) : ViewModel() {

    val events: Flow<List<AcademicEvent>> = repository.allEvents

    fun save(event: AcademicEvent) {
        viewModelScope.launch {
            if (event.id == 0L) repository.insertEvent(event) else repository.updateEvent(event)
        }
    }

    fun delete(event: AcademicEvent) {
        viewModelScope.launch { repository.deleteEvent(event) }
    }
}

class CalendarViewModelFactory(private val repository: ExtendedRepository) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = CalendarViewModel(repository) as T
}
