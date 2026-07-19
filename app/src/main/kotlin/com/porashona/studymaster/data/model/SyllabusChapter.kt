package com.porashona.studymaster.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

// ─── Enums ───────────────────────────────────────────────────────────────

enum class ChapterStatus {
    NOT_STARTED,
    IN_PROGRESS,
    COMPLETED
}

enum class ExamType {
    SSC,
    HSC
}

// ─── SyllabusChapter ────────────────────────────────────────────────────

@Entity(
    tableName = "syllabus_chapters",
    indices = [
        Index(value = ["subjectId"]),
        Index(value = ["status"]),
        Index(value = ["examType"])
    ]
)
data class SyllabusChapter(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val subjectId: Long? = null,
    val subjectName: String? = null,
    val chapterName: String,
    val chapterNumber: Int = 0,
    val totalTopics: Int = 0,
    val completedTopics: Int = 0,
    val status: String = ChapterStatus.NOT_STARTED.name,
    val isShortSyllabus: Boolean = false,
    val examType: String = ExamType.SSC.name,
    val notes: String = ""
)