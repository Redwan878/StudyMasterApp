package com.porashona.studymaster.ui.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.porashona.studymaster.data.model.Note
import com.porashona.studymaster.data.repository.ExtendedRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class NotesViewModel(private val repository: ExtendedRepository) : ViewModel() {

    private val query = MutableStateFlow("")

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val notes: Flow<List<Note>> = query
        .debounce(150)
        .map { it.trim() }
        .flatMapLatest { q ->
            if (q.isEmpty()) repository.allNotes else repository.searchNotes(q)
        }

    fun setQuery(text: String) {
        query.value = text
    }

    fun saveNote(note: Note) = viewModelScope.launch {
        if (note.id == 0L) repository.insertNote(note) else repository.updateNote(note)
    }

    fun deleteNote(note: Note) = viewModelScope.launch { repository.deleteNote(note) }

    fun toggleFavorite(note: Note) = viewModelScope.launch {
        repository.toggleNoteFavorite(note.id)
    }
}

class NotesViewModelFactory(private val repository: ExtendedRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = NotesViewModel(repository) as T
}
