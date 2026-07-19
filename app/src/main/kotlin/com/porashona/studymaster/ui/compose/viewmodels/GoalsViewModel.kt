package com.porashona.studymaster.ui.compose.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.porashona.studymaster.data.dao.ExamDao
import com.porashona.studymaster.data.dao.GoalDao
import com.porashona.studymaster.data.dao.TaskDao
import com.porashona.studymaster.data.model.Goal
import com.porashona.studymaster.data.model.GoalType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

// ─── Data Classes ─────────────────────────────────────────────────────────

data class GoalStreak(
    val goalId: Long,
    val goalTitle: String,
    val currentStreak: Int,
    val longestStreak: Int,
    val isActive: Boolean
)

// ─── ViewModel ────────────────────────────────────────────────────────────

@HiltViewModel
class GoalsViewModel @Inject constructor(
    private val goalDao: GoalDao,
    private val taskDao: TaskDao,
    private val examDao: ExamDao
) : ViewModel() {

    // ─── Goals ────────────────────────────────────────────────────────────
    val goals: StateFlow<List<Goal>> = goalDao.getActiveGoals()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _archivedGoals = MutableStateFlow<List<Goal>>(emptyList())
    val archivedGoals: StateFlow<List<Goal>> = _archivedGoals.asStateFlow()

    // ─── Streaks ──────────────────────────────────────────────────────────
    private val _streaks = MutableStateFlow<List<GoalStreak>>(emptyList())
    val streaks: StateFlow<List<GoalStreak>> = _streaks.asStateFlow()

    // ─── Filter ───────────────────────────────────────────────────────────
    private val _goalTypeFilter = MutableStateFlow<GoalType?>(null)
    val goalTypeFilter: StateFlow<GoalType?> = _goalTypeFilter.asStateFlow()

    // ─── Events ───────────────────────────────────────────────────────────
    private val _events = MutableStateFlow<GoalEvent?>(null)
    val events: StateFlow<GoalEvent?> = _events.asStateFlow()

    init {
        loadArchivedGoals()
        loadStreaks()
    }

    // ═══════════════════════════════════════════════════════════════════════
    // CRUD
    // ═══════════════════════════════════════════════════════════════════════

    fun addGoal(
        title: String,
        targetMinutes: Int,
        goalType: GoalType = GoalType.DAILY,
        subjectId: Long? = null,
        subjectName: String? = null
    ) {
        viewModelScope.launch {
            val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            val goal = Goal(
                title = title,
                targetMinutes = targetMinutes,
                goalType = goalType,
                subjectId = subjectId,
                subjectName = subjectName,
                date = today
            )
            goalDao.insert(goal)
            _events.value = GoalEvent.GoalCreated
        }
    }

    fun updateGoal(goal: Goal) {
        viewModelScope.launch {
            goalDao.update(goal)
        }
    }

    fun updateProgress(goalId: Long, additionalMinutes: Int) {
        viewModelScope.launch {
            goalDao.addMinutesToGoal(goalId, additionalMinutes)
            val goal = goalDao.getGoalById(goalId) ?: return@launch

            if (goal.currentMinutes + additionalMinutes >= goal.targetMinutes && !goal.isCompleted) {
                goalDao.markAsCompleted(goalId)
                _events.value = GoalEvent.GoalCompleted(goalId, goal.title)
            }
        }
    }

    fun archiveGoal(goalId: Long) {
        viewModelScope.launch {
            val goal = goalDao.getGoalById(goalId) ?: return@launch
            if (goal.isCompleted) {
                // Move to archived by deleting from active (in a real app, there'd be an archived flag)
                // For now, we just mark as completed and remove
            }
            // Mark as completed if not already, then treat as archived
            if (!goal.isCompleted) {
                goalDao.markAsCompleted(goalId)
            }
            _events.value = GoalEvent.GoalArchived(goalId)
            loadArchivedGoals()
        }
    }

    fun deleteGoal(goalId: Long) {
        viewModelScope.launch {
            val goal = goalDao.getGoalById(goalId) ?: return@launch
            goalDao.delete(goal)
            _events.value = GoalEvent.GoalDeleted(goalId)
        }
    }

    // ═══════════════════════════════════════════════════════════════════════
    // Streaks
    // ═══════════════════════════════════════════════════════════════════════

    fun getStreaks() {
        viewModelScope.launch {
            loadStreaks()
        }
    }

    // ═══════════════════════════════════════════════════════════════════════
    // Filter
    // ═══════════════════════════════════════════════════════════════════════

    fun setGoalTypeFilter(type: GoalType?) {
        _goalTypeFilter.value = type
    }

    fun clearEvent() {
        _events.value = null
    }

    // ═══════════════════════════════════════════════════════════════════════
    // Private Helpers
    // ═══════════════════════════════════════════════════════════════════════

    private fun loadArchivedGoals() {
        viewModelScope.launch {
            // Archived goals are completed goals
            val allGoals = goalDao.getAllGoals().first()
            _archivedGoals.value = allGoals.filter { it.isCompleted }.sortedByDescending { it.completedAt }
        }
    }

    private fun loadStreaks() {
        viewModelScope.launch {
            val activeGoals = goalDao.getActiveGoals().first()
            val streakList = activeGoals.map { goal ->
                // Calculate streak: how many consecutive days (based on date string)
                // the goal has been worked on. Simple implementation uses streakCount.
                GoalStreak(
                    goalId = goal.id,
                    goalTitle = goal.title,
                    currentStreak = goal.streakCount,
                    longestStreak = goal.streakCount, // Would need historical data for true longest
                    isActive = !goal.isCompleted
                )
            }
            _streaks.value = streakList.sortedByDescending { it.currentStreak }
        }
    }
}

// ─── Events ───────────────────────────────────────────────────────────────

sealed class GoalEvent {
    object GoalCreated : GoalEvent()
    data class GoalCompleted(val goalId: Long, val title: String) : GoalEvent()
    data class GoalArchived(val goalId: Long) : GoalEvent()
    data class GoalDeleted(val goalId: Long) : GoalEvent()
}