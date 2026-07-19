package com.porashona.studymaster.ui.compose.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.porashona.studymaster.data.dao.GoalDao
import com.porashona.studymaster.data.dao.SubjectDao
import com.porashona.studymaster.data.dao.TaskDao
import com.porashona.studymaster.data.model.RecurringType
import com.porashona.studymaster.data.model.Task
import com.porashona.studymaster.data.model.TaskPriority
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

// ─── Enums ────────────────────────────────────────────────────────────────

enum class TaskFilter {
    ALL, PENDING, COMPLETED, OVERDUE, TODAY, THIS_WEEK
}

enum class TaskSortBy {
    PRIORITY, DUE_DATE, CREATED, SUBJECT
}

// ─── Data Classes ─────────────────────────────────────────────────────────

data class TaskWithSubtasks(
    val task: Task,
    val subtasks: List<Task>
)

// ─── ViewModel ────────────────────────────────────────────────────────────

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val taskDao: TaskDao,
    private val goalDao: GoalDao,
    private val subjectDao: SubjectDao
) : ViewModel() {

    // ─── Filter & Sort State ─────────────────────────────────────────────
    private val _filter = MutableStateFlow(TaskFilter.PENDING)
    val filter: StateFlow<TaskFilter> = _filter.asStateFlow()

    private val _sortBy = MutableStateFlow(TaskSortBy.PRIORITY)
    val sortBy: StateFlow<TaskSortBy> = _sortBy.asStateFlow()

    private val _subjectFilter = MutableStateFlow<Long?>(null)
    val subjectFilter: StateFlow<Long?> = _subjectFilter.asStateFlow()

    // ─── Subjects ─────────────────────────────────────────────────────────
    val subjects: StateFlow<List<com.porashona.studymaster.data.model.Subject>> = subjectDao.getAllSubjects()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // ─── Tasks (reactive with filter + sort) ──────────────────────────────
    val tasks: StateFlow<List<Task>> = combine(
        taskDao.getAllTasks(),
        _filter,
        _sortBy,
        _subjectFilter
    ) { allTasks, filter, sortBy, subjectFilter ->
        val now = System.currentTimeMillis()

        val filtered = when (filter) {
            TaskFilter.ALL -> allTasks.filter { it.parentTaskId == null }
            TaskFilter.PENDING -> allTasks.filter { !it.isCompleted && it.parentTaskId == null }
            TaskFilter.COMPLETED -> allTasks.filter { it.isCompleted && it.parentTaskId == null }
            TaskFilter.OVERDUE -> allTasks.filter {
                !it.isCompleted && it.parentTaskId == null &&
                        (it.dueDate ?: Long.MAX_VALUE) < now
            }
            TaskFilter.TODAY -> {
                val startOfDay = now - (now % (24 * 60 * 60 * 1000))
                val endOfDay = startOfDay + 24 * 60 * 60 * 1000
                allTasks.filter {
                    it.parentTaskId == null && it.dueDate != null &&
                            it.dueDate in startOfDay until endOfDay
                }
            }
            TaskFilter.THIS_WEEK -> {
                val weekAgo = now - 7 * 24 * 60 * 60 * 1000
                allTasks.filter {
                    it.parentTaskId == null && !it.isCompleted &&
                            (it.dueDate ?: Long.MAX_VALUE) < now + 7 * 24 * 60 * 60 * 1000
                }
            }
        }

        val bySubject = if (subjectFilter != null) {
            filtered.filter { it.subjectId == subjectFilter }
        } else {
            filtered
        }

        val sorted = when (sortBy) {
            TaskSortBy.PRIORITY -> bySubject.sortedWith(
                compareByDescending<Task> { it.priority.ordinal }
                    .thenBy { it.dueDate ?: Long.MAX_VALUE }
            )
            TaskSortBy.DUE_DATE -> bySubject.sortedBy { it.dueDate ?: Long.MAX_VALUE }
            TaskSortBy.CREATED -> bySubject.sortedByDescending { it.createdAt }
            TaskSortBy.SUBJECT -> bySubject.sortedBy { it.subjectName ?: "" }
        }

        sorted
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // ─── Subtasks ─────────────────────────────────────────────────────────
    private val _expandedTaskId = MutableStateFlow<Long?>(null)
    val expandedTaskId: StateFlow<Long?> = _expandedTaskId.asStateFlow()

    private val _subtasks = MutableStateFlow<List<Task>>(emptyList())
    val subtasks: StateFlow<List<Task>> = _subtasks.asStateFlow()

    // ─── Stats ────────────────────────────────────────────────────────────
    val pendingCount: StateFlow<Int> = taskDao.getPendingTasksCount()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    // ─── One-shot events ──────────────────────────────────────────────────
    private val _events = MutableStateFlow<TaskEvent?>(null)
    val events: StateFlow<TaskEvent?> = _events.asStateFlow()

    // ═══════════════════════════════════════════════════════════════════════
    // CRUD
    // ═══════════════════════════════════════════════════════════════════════

    fun addTask(
        title: String,
        description: String = "",
        subjectId: Long? = null,
        subjectName: String? = null,
        priority: TaskPriority = TaskPriority.MEDIUM,
        dueDate: Long? = null,
        dueTime: String? = null,
        isRecurring: Boolean = false,
        recurringType: RecurringType = RecurringType.NONE
    ) {
        viewModelScope.launch {
            val resolvedSubjectName = if (subjectName == null && subjectId != null) {
                subjectDao.getSubjectById(subjectId)?.name
            } else {
                subjectName
            }
            val task = Task(
                title = title,
                description = description,
                subjectId = subjectId,
                subjectName = resolvedSubjectName,
                priority = priority,
                dueDate = dueDate,
                dueTime = dueTime,
                isRecurring = isRecurring,
                recurringType = recurringType,
                reminderEnabled = dueDate != null
            )
            taskDao.insert(task)
            _events.value = TaskEvent.TaskCreated
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            taskDao.update(task)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            taskDao.delete(task)
            _events.value = TaskEvent.TaskDeleted(task.id)
        }
    }

    fun completeTask(taskId: Long) {
        viewModelScope.launch {
            taskDao.markAsCompleted(taskId)
            _events.value = TaskEvent.TaskCompleted(taskId)
        }
    }

    fun uncompleteTask(taskId: Long) {
        viewModelScope.launch {
            taskDao.markAsIncomplete(taskId)
        }
    }

    // ═══════════════════════════════════════════════════════════════════════
    // Subtasks
    // ═══════════════════════════════════════════════════════════════════════

    fun addSubtask(parentTaskId: Long, title: String) {
        viewModelScope.launch {
            val parent = taskDao.getTaskById(parentTaskId) ?: return@launch
            val subtask = Task(
                title = title,
                parentTaskId = parentTaskId,
                subjectId = parent.subjectId,
                subjectName = parent.subjectName,
                priority = TaskPriority.LOW,
                xpReward = 5
            )
            taskDao.insert(subtask)
            loadSubtasks(parentTaskId)
        }
    }

    fun toggleExpandTask(taskId: Long) {
        val newId = if (_expandedTaskId.value == taskId) null else taskId
        _expandedTaskId.value = newId
        if (newId != null) {
            loadSubtasks(newId)
        } else {
            _subtasks.value = emptyList()
        }
    }

    private fun loadSubtasks(parentId: Long) {
        viewModelScope.launch {
            taskDao.getSubtasks(parentId).collect { subs ->
                _subtasks.value = subs
            }
        }
    }

    // ═══════════════════════════════════════════════════════════════════════
    // Recurring Template
    // ═══════════════════════════════════════════════════════════════════════

    fun createRecurringTemplate(
        title: String,
        description: String = "",
        subjectId: Long? = null,
        subjectName: String? = null,
        priority: TaskPriority = TaskPriority.MEDIUM,
        recurringType: RecurringType
    ) {
        addTask(
            title = title,
            description = description,
            subjectId = subjectId,
            subjectName = subjectName,
            priority = priority,
            isRecurring = true,
            recurringType = recurringType
        )
    }

    // ═══════════════════════════════════════════════════════════════════════
    // Priority & Filter
    // ═══════════════════════════════════════════════════════════════════════

    fun setPriority(taskId: Long, priority: TaskPriority) {
        viewModelScope.launch {
            val task = taskDao.getTaskById(taskId) ?: return@launch
            taskDao.update(task.copy(priority = priority))
        }
    }

    fun setFilter(filter: TaskFilter) {
        _filter.value = filter
    }

    fun setSortBy(sortBy: TaskSortBy) {
        _sortBy.value = sortBy
    }

    fun setSubjectFilter(subjectId: Long?) {
        _subjectFilter.value = subjectId
    }

    fun clearEvent() {
        _events.value = null
    }
}

// ─── Events ───────────────────────────────────────────────────────────────

sealed class TaskEvent {
    object TaskCreated : TaskEvent()
    data class TaskCompleted(val taskId: Long) : TaskEvent()
    data class TaskDeleted(val taskId: Long) : TaskEvent()
}