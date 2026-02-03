package com.porashona.studymaster.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "goals")
data class Goal(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val targetMinutes: Int,
    val currentMinutes: Int = 0,
    val subjectId: Long? = null,
    val subjectName: String? = null,
    val goalType: GoalType = GoalType.DAILY,
    val isCompleted: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val completedAt: Long? = null,
    val date: String = "", // Format: yyyy-MM-dd
    val streakCount: Int = 0
)

enum class GoalType {
    DAILY,
    WEEKLY,
    SUBJECT_SPECIFIC,
    CUSTOM
}