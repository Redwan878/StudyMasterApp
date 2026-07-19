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
    // Streak Achievements
    const val STREAK_3 = "streak_3"
    const val STREAK_7 = "streak_7"
    const val STREAK_14 = "streak_14"
    const val STREAK_30 = "streak_30"
    const val STREAK_100 = "streak_100"

    // Study Hours Achievements
    const val HOURS_10 = "hours_10"
    const val HOURS_50 = "hours_50"
    const val HOURS_100 = "hours_100"
    const val HOURS_500 = "hours_500"
    const val HOURS_1000 = "hours_1000"

    // Study Sessions Achievements
    const val SESSIONS_10 = "sessions_10"
    const val SESSIONS_50 = "sessions_50"
    const val SESSIONS_100 = "sessions_100"
    const val SESSIONS_500 = "sessions_500"

    // Perfect Week Achievements
    const val PERFECT_WEEK_1 = "perfect_week_1"
    const val PERFECT_WEEK_4 = "perfect_week_4"
    const val PERFECT_MONTH = "perfect_month"

    // Time-Based Achievements
    const val EARLY_BIRD_7AM = "early_bird_7am"
    const val EARLY_BIRD_6AM = "early_bird_6am"
    const val EARLY_BIRD_5AM = "early_bird_5am"
    const val NIGHT_OWL_11PM = "night_owl_11pm"
    const val NIGHT_OWL_MIDNIGHT = "night_owl_midnight"
    const val LATE_NIGHT = "late_night"

    // Pomodoro Achievements
    const val POMODORO_10 = "pomodoro_10"
    const val POMODORO_50 = "pomodoro_50"
    const val POMODORO_100 = "pomodoro_100"

    // Subject Achievements
    const val SUBJECT_5 = "subject_5"
    const val SUBJECT_10 = "subject_10"
    const val STUDY_MANY = "study_many"

    // Advanced Achievements
    const val DEEP_FOCUSER_50M = "deep_focuser_50m"
    const val FOCUS_BEAST = "focus_beast"
    const val CHALLENGER = "challenger"
    const val WEEKLY_CHAMPION = "weekly_champion"

    // Secret Achievements
    const val SECRET_FIRST_10K = "secret_first_10k"
    const val SECRET_DAILY_100 = "secret_daily_100"
}