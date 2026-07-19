package com.porashona.studymaster.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

// ─── Enums ───────────────────────────────────────────────────────────────

enum class FlashcardDifficulty {
    AGAIN,
    HARD,
    GOOD,
    EASY
}

// ─── FlashcardDeck ──────────────────────────────────────────────────────

@Entity(
    tableName = "flashcard_decks",
    indices = [Index(value = ["subjectId"])]
)
data class FlashcardDeck(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val subjectId: Long? = null,
    val subjectName: String? = null,
    val colorHex: String = "#6C63FF",
    val cardCount: Int = 0,
    val description: String = "",
    val isArchived: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val lastStudiedAt: Long? = null
)

// ─── Flashcard ──────────────────────────────────────────────────────────

@Entity(
    tableName = "flashcards",
    foreignKeys = [
        ForeignKey(
            entity = FlashcardDeck::class,
            parentColumns = ["id"],
            childColumns = ["deckId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["deckId"])]
)
data class Flashcard(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val deckId: Long,
    val front: String,
    val back: String,
    val imageUrl: String? = null,
    val audioPath: String? = null,
    val isOcclusion: Boolean = false,
    val occlusionImageUrl: String? = null,
    val difficulty: Int = 0, // 0-5
    val nextReviewAt: Long? = null,
    val reviewCount: Int = 0,
    val correctCount: Int = 0,
    val lastReviewAt: Long? = null,
    val createdAt: Long = System.currentTimeMillis()
)