package com.porashona.studymaster.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

// ─── Enums ───────────────────────────────────────────────────────────────

enum class QuestionDifficulty {
    EASY,
    MEDIUM,
    HARD
}

enum class QuestionType {
    MCQ,
    CREATIVE
}

// ─── QuestionBank ───────────────────────────────────────────────────────

@Entity(
    tableName = "question_bank",
    indices = [Index(value = ["subjectId"])]
)
data class QuestionBank(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val subjectId: Long? = null,
    val chapterName: String? = null,
    val questionText: String,
    val optionA: String = "",
    val optionB: String = "",
    val optionC: String = "",
    val optionD: String = "",
    val correctOption: Int = 0, // 1=A, 2=B, 3=C, 4=D
    val explanation: String = "",
    val difficulty: String = QuestionDifficulty.MEDIUM.name,
    val questionType: String = QuestionType.MCQ.name,
    val negativeMarking: Double = 0.0,
    val createdAt: Long = System.currentTimeMillis()
)

// ─── PracticeTest ───────────────────────────────────────────────────────

@Entity(
    tableName = "practice_tests",
    indices = [Index(value = ["subjectId"])]
)
data class PracticeTest(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val subjectId: Long? = null,
    val subjectName: String? = null,
    val totalQuestions: Int = 0,
    val durationMinutes: Int = 0,
    val negativeMarkingEnabled: Boolean = false,
    val negativeMarkValue: Double = 0.25,
    val isMixedSubject: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val completedAt: Long? = null
)

// ─── PracticeTestResult ─────────────────────────────────────────────────

@Entity(
    tableName = "practice_test_results",
    foreignKeys = [
        ForeignKey(
            entity = PracticeTest::class,
            parentColumns = ["id"],
            childColumns = ["testId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["testId"])]
)
data class PracticeTestResult(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val testId: Long,
    val score: Double = 0.0,
    val totalMarks: Double = 0.0,
    val percentage: Double = 0.0,
    val timeTakenSeconds: Long = 0,
    val correctCount: Int = 0,
    val wrongCount: Int = 0,
    val skippedCount: Int = 0,
    val completedAt: Long? = null,
    val chapterBreakdown: String = "{}" // JSON string
)