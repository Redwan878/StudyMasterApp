package com.porashona.studymaster.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "study_sessions")
data class StudySession(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val subjectId: Long = 0,
    val subjectName: String = "",
    val durationInSeconds: Long = 0,
    val startTime: Date = Date(),
    val endTime: Date = Date(),
    val sessionType: SessionType = SessionType.WORK,
    val completed: Boolean = true,
    val xpEarned: Int = 0,
    val notes: String = ""
)

enum class SessionType {
    WORK,
    SHORT_BREAK,
    LONG_BREAK
}