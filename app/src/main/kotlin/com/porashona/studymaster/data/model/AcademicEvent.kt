package com.porashona.studymaster.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "academic_events")
data class AcademicEvent(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String = "",
    val eventType: EventType,
    val date: Long,
    val endDate: Long? = null,
    val time: String? = null,
    val subjectId: Long? = null,
    val subjectName: String? = null,
    val isHoliday: Boolean = false,
    val reminderEnabled: Boolean = true,
    val reminderMinutesBefore: Int = 60,
    val color: String = "#6C63FF",
    val createdAt: Long = System.currentTimeMillis()
)

enum class EventType {
    EXAM,
    ASSIGNMENT_DUE,
    CLASS,
    HOLIDAY,
    SEMESTER_START,
    SEMESTER_END,
    RESULT,
    OTHER
}