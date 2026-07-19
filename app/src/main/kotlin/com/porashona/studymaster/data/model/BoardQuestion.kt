package com.porashona.studymaster.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

// ─── Enum ────────────────────────────────────────────────────────────────

enum class BoardName {
    DHAKA,
    RAJSHAHI,
    CHITTAGONG,
    SYLHET,
    BARISHAL,
    KHULNA,
    RANGPUR,
    MYMENSINGH,
    DINAJPUR,
    JESSORE,
    COMBINED
}

// ─── BoardQuestion ──────────────────────────────────────────────────────

@Entity(
    tableName = "board_questions",
    indices = [
        Index(value = ["subjectId"]),
        Index(value = ["year"]),
        Index(value = ["board"])
    ]
)
data class BoardQuestion(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val year: Int,
    val board: String = BoardName.COMBINED.name,
    val subjectId: Long? = null,
    val subjectName: String? = null,
    val questionSet: String? = null, // e.g. "Set-1", "ক-সেট"
    val questionNumber: String = "",
    val questionText: String,
    val marks: Double = 0.0,
    val chapterName: String? = null,
    val questionType: String = "", // MCQ, CQ, etc.
    val createdAt: Long = System.currentTimeMillis()
)