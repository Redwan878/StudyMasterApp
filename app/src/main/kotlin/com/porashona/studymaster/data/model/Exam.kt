package com.porashona.studymaster.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exams")
data class Exam(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val subjectId: Long? = null,
    val subjectName: String? = null,
    val examDate: Long,
    val examTime: String? = null,
    val venue: String = "",
    val notes: String = "",
    val syllabus: String = "", // JSON array of topics
    val preparationProgress: Int = 0, // 0-100
    val isCompleted: Boolean = false,
    val result: String? = null,
    val reflection: String? = null,
    val reminderEnabled: Boolean = true,
    val createdAt: Long = System.currentTimeMillis()
)