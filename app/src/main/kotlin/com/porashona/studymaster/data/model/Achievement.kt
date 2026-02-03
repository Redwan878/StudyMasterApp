package com.porashona.studymaster.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "achievements")
data class Achievement(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String,
    val iconName: String,
    val xpReward: Int,
    val isUnlocked: Boolean = false,
    val unlockedAt: Long? = null,
    val progress: Int = 0,
    val targetProgress: Int = 1
)

object AchievementTypes {
    const val STREAK_7 = "streak_7"
    const val STREAK_30 = "streak_30"
    const val HOURS_10 = "hours_10"
    const val HOURS_100 = "hours_100"
    const val HOURS_500 = "hours_500"
    const val SESSIONS_10 = "sessions_10"
    const val SESSIONS_100 = "sessions_100"
    const val PERFECT_WEEK = "perfect_week"
    const val EARLY_BIRD = "early_bird"
    const val NIGHT_OWL = "night_owl"
}