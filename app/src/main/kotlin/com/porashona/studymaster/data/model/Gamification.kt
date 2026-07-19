package com.porashona.studymaster.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

// ─── Enum ────────────────────────────────────────────────────────────────

enum class DailyChallengeType {
    MCQ_COUNT,
    STUDY_MINUTES,
    FLASHCARD_COUNT,
    STREAK_DAY,
    TASK_COMPLETE
}

// ─── XPGain (XP history log) ────────────────────────────────────────────

@Entity(
    tableName = "xp_gains",
    indices = [
        Index(value = ["source"]),
        Index(value = ["gainedAt"])
    ]
)
data class XPGain(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val source: String,
    val sourceId: Long? = null,
    val amount: Int = 0,
    val gainedAt: Long = System.currentTimeMillis()
)

// ─── UserLevel (single row) ────────────────────────────────────────────

@Entity(tableName = "user_level")
data class UserLevel(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val currentLevel: Int = 1,
    val currentXP: Int = 0,
    val xpForNextLevel: Int = 100,
    val totalXP: Int = 0,
    val updatedAt: Long = System.currentTimeMillis()
)

// ─── DailyChallenge ────────────────────────────────────────────────────

@Entity(
    tableName = "daily_challenges",
    indices = [
        Index(value = ["date"], unique = true),
        Index(value = ["challengeType"])
    ]
)
data class DailyChallenge(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val challengeText: String,
    val challengeType: String = DailyChallengeType.MCQ_COUNT.name,
    val targetValue: Int = 0,
    val rewardXP: Int = 0,
    val isActive: Boolean = true,
    val date: String, // yyyy-MM-dd format
    val isCompleted: Boolean = false,
    val completedAt: Long? = null
)