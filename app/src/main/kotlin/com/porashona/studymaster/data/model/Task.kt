package com.porashona.studymaster.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String = "",
    val subjectId: Long? = null,
    val subjectName: String? = null,
    val priority: TaskPriority = TaskPriority.MEDIUM,
    val dueDate: Long? = null,
    val dueTime: String? = null,
    val isCompleted: Boolean = false,
    val isRecurring: Boolean = false,
    val recurringType: RecurringType = RecurringType.NONE,
    val parentTaskId: Long? = null, // For subtasks
    val xpReward: Int = 10,
    val createdAt: Long = System.currentTimeMillis(),
    val completedAt: Long? = null,
    val reminderEnabled: Boolean = false,
    val reminderTime: Long? = null
)

enum class TaskPriority {
    LOW,
    MEDIUM,
    HIGH,
    URGENT
}

enum class RecurringType {
    NONE,
    DAILY,
    WEEKLY,
    MONTHLY
}