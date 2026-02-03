package com.porashona.studymaster.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "routines")
data class Routine(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val subjectId: Long = 0,
    val subjectName: String = "",
    val title: String = "",
    val hour: Int = 9,
    val minute: Int = 0,
    val durationMinutes: Int = 25,
    val repeatType: RepeatType = RepeatType.DAILY,
    val repeatDays: List<Int> = listOf(0, 1, 2, 3, 4, 5, 6),
    val isEnabled: Boolean = true,
    val createdAt: Long = System.currentTimeMillis()
)

enum class RepeatType {
    ONCE,
    DAILY,
    WEEKLY,
    CUSTOM
}