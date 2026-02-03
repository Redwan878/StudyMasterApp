package com.porashona.studymaster.ui.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.porashona.studymaster.data.model.Note
import com.porashona.studymaster.data.repository.ExtendedRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class NotesViewModel(private val repository: ExtendedRepository) : ViewModel() {
    val notes: Flow<List<Note>> = repository.allNotes
    fun saveNote(note: Note) = viewModelScope.launch {
        if (note.id == 0L) repository.insertNote(note) else repository.updateNote(note)
    }
    fun deleteNote(note: Note) = viewModelScope.launch { repository.deleteNote(note) }
}

class NotesViewModelFactory(private val repository: ExtendedRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = NotesViewModel(repository) as T
}