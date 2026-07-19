package com.porashona.studymaster.ui.compose.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.porashona.studymaster.data.dao.ExamDao
import com.porashona.studymaster.data.dao.GoalDao
import com.porashona.studymaster.data.dao.NoteDao
import com.porashona.studymaster.data.dao.SubjectDao
import com.porashona.studymaster.data.model.Note
import com.porashona.studymaster.data.model.Subject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.json.JSONArray
import javax.inject.Inject

// ─── Enums ────────────────────────────────────────────────────────────────

enum class NoteSortMode {
    UPDATED_DESC, UPDATED_ASC, TITLE_ASC, TITLE_DESC, SUBJECT
}

// ─── ViewModel ────────────────────────────────────────────────────────────

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteDao: NoteDao,
    private val subjectDao: SubjectDao,
    private val goalDao: GoalDao,
    private val examDao: ExamDao
) : ViewModel() {

    // ─── Search & Filter State ────────────────────────────────────────────
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedSubjectFilter = MutableStateFlow<Long?>(null)
    val selectedSubjectFilter: StateFlow<Long?> = _selectedSubjectFilter.asStateFlow()

    private val _sortMode = MutableStateFlow(NoteSortMode.UPDATED_DESC)
    val sortMode: StateFlow<NoteSortMode> = _sortMode.asStateFlow()

    // ─── Subjects (for filter dropdown) ───────────────────────────────────
    val subjects: StateFlow<List<Subject>> = subjectDao.getAllSubjects()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // ─── Notes (reactive with search, filter, sort) ───────────────────────
    val notes: StateFlow<List<Note>> = combine(
        _searchQuery.debounce(200).map { it.trim() },
        _selectedSubjectFilter,
        _sortMode
    ) { query, subjectFilter, sort ->
        Triple(query, subjectFilter, sort)
    }.flatMapLatest { (query, subjectFilter, sort) ->
        val baseFlow = when {
            query.isNotEmpty() -> noteDao.searchNotes(query)
            subjectFilter != null -> noteDao.getNotesBySubject(subjectFilter)
            else -> noteDao.getAllNotes()
        }
        baseFlow.map { list ->
            val filtered = if (subjectFilter != null && query.isNotEmpty()) {
                list.filter { it.subjectId == subjectFilter }
            } else {
                list
            }
            when (sort) {
                NoteSortMode.UPDATED_DESC -> filtered.sortedByDescending { it.updatedAt }
                NoteSortMode.UPDATED_ASC -> filtered.sortedBy { it.updatedAt }
                NoteSortMode.TITLE_ASC -> filtered.sortedBy { it.title.lowercase() }
                NoteSortMode.TITLE_DESC -> filtered.sortedByDescending { it.title.lowercase() }
                NoteSortMode.SUBJECT -> filtered.sortedBy { it.subjectName ?: "" }
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // ─── One-shot events ──────────────────────────────────────────────────
    private val _events = MutableStateFlow<NoteEvent?>(null)
    val events: StateFlow<NoteEvent?> = _events.asStateFlow()

    // ═══════════════════════════════════════════════════════════════════════
    // Public API
    // ═══════════════════════════════════════════════════════════════════════

    fun addNote(
        title: String,
        content: String = "",
        subjectId: Long? = null,
        subjectName: String? = null,
        htmlContent: String = ""
    ) {
        viewModelScope.launch {
            val subject = if (subjectId != null && subjectName == null) {
                subjectDao.getSubjectById(subjectId)?.name
            } else {
                subjectName
            }
            val note = Note(
                title = title,
                content = content,
                htmlContent = htmlContent,
                subjectId = subjectId,
                subjectName = subject
            )
            noteDao.insert(note)
            _events.value = NoteEvent.NoteCreated
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch {
            noteDao.update(note.copy(updatedAt = System.currentTimeMillis()))
            _events.value = NoteEvent.NoteUpdated
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            noteDao.delete(note)
            _events.value = NoteEvent.NoteDeleted(note.id)
        }
    }

    fun toggleFavorite(noteId: Long) {
        viewModelScope.launch {
            noteDao.setFavorite(noteId, true) // toggle requires checking current state
            // Proper toggle: read first, then set opposite
            val note = noteDao.getNoteById(noteId) ?: return@launch
            noteDao.setFavorite(noteId, !note.isFavorite)
        }
    }

    fun search(query: String) {
        _searchQuery.value = query
    }

    fun setSubjectFilter(subjectId: Long?) {
        _selectedSubjectFilter.value = subjectId
    }

    fun setSortMode(mode: NoteSortMode) {
        _sortMode.value = mode
    }

    fun attachImage(noteId: Long, imagePath: String) {
        viewModelScope.launch {
            val note = noteDao.getNoteById(noteId) ?: return@launch
            val existingPaths = try {
                val arr = JSONArray(note.imagesPaths)
                (0 until arr.length()).map { arr.getString(it) }
            } catch (_: Exception) {
                note.imagesPaths.split(",").filter { it.isNotBlank() }
            }
            val updatedPaths = (existingPaths + imagePath).distinct().joinToString(",")
            noteDao.update(note.copy(imagesPaths = updatedPaths, updatedAt = System.currentTimeMillis()))
        }
    }

    fun removeImage(noteId: Long, imagePath: String) {
        viewModelScope.launch {
            val note = noteDao.getNoteById(noteId) ?: return@launch
            val existingPaths = try {
                val arr = JSONArray(note.imagesPaths)
                (0 until arr.length()).map { arr.getString(it) }
            } catch (_: Exception) {
                note.imagesPaths.split(",").filter { it.isNotBlank() }
            }
            val updatedPaths = existingPaths.filter { it != imagePath }.joinToString(",")
            noteDao.update(note.copy(imagesPaths = updatedPaths, updatedAt = System.currentTimeMillis()))
        }
    }

    fun linkToGoal(noteId: Long, goalId: Long) {
        viewModelScope.launch {
            val note = noteDao.getNoteById(noteId) ?: return@launch
            val goal = goalDao.getGoalById(goalId) ?: return@launch
            val updatedTags = if (note.tags.isNotBlank()) {
                "${note.tags},goal:${goal.id}"
            } else {
                "goal:${goal.id}"
            }
            noteDao.update(note.copy(tags = updatedTags, updatedAt = System.currentTimeMillis()))
            _events.value = NoteEvent.LinkedToGoal(goalId)
        }
    }

    fun linkToExam(noteId: Long, examId: Long) {
        viewModelScope.launch {
            val note = noteDao.getNoteById(noteId) ?: return@launch
            val exam = examDao.getExamById(examId) ?: return@launch
            val updatedTags = if (note.tags.isNotBlank()) {
                "${note.tags},exam:${exam.id}"
            } else {
                "exam:${exam.id}"
            }
            noteDao.update(note.copy(tags = updatedTags, updatedAt = System.currentTimeMillis()))
            _events.value = NoteEvent.LinkedToExam(examId)
        }
    }

    fun clearEvent() {
        _events.value = null
    }
}

// ─── Events ───────────────────────────────────────────────────────────────

sealed class NoteEvent {
    object NoteCreated : NoteEvent()
    object NoteUpdated : NoteEvent()
    data class NoteDeleted(val noteId: Long) : NoteEvent()
    data class LinkedToGoal(val goalId: Long) : NoteEvent()
    data class LinkedToExam(val examId: Long) : NoteEvent()
}