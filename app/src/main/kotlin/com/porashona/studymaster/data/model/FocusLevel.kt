package com.porashona.studymaster.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "focus_levels")
data class FocusLevel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val level: Int,
    val levelName: String,
    val minScore: Double,
    val maxScore: Double,
    val description: String,
    val recommendations: String
)

object FocusLevels {
    fun getAllLevels(): List<FocusLevel> {
        return listOf(
            FocusLevel(
                level = 1,
                levelName = "Unfocused",
                minScore = 0.0,
                maxScore = 0.2,
                description = "Low concentration, distracted",
                recommendations = "Find a quiet place, eliminate distractions"
            ),
            FocusLevel(
                level = 2,
                levelName = "Semi-Focused",
                minScore = 0.21,
                maxScore = 0.4,
                description = "Frequent distractions",
                recommendations = "Use focus techniques, short regular breaks"
            ),
            FocusLevel(
                level = 3,
                levelName = "Moderately Focused",
                minScore = 0.41,
                maxScore = 0.6,
                description = "Average concentration",
                recommendations = "Good consistency, keep maintaining"
            ),
            FocusLevel(
                level = 4,
                levelName = "Focused",
                minScore = 0.61,
                maxScore = 0.8,
                description = "Strong concentration",
                recommendations = "Excellent focus, maintain this rhythm"
            ),
            FocusLevel(
                level = 5,
                levelName = "Deep Flow",
                minScore = 0.81,
                maxScore = 1.0,
                description = "Complete focus, peak productivity",
                recommendations = "Amazing! Maintain this flow state"
            )
        )
    }

    fun getFocusLevel(score: Double): FocusLevel {
        val levels = getAllLevels()
        return levels.find { score >= it.minScore && score <= it.maxScore } ?: levels.last()
    }

    fun getFocusLevelName(score: Double): String {
        return getFocusLevel(score).levelName
    }

    fun getFocusLevelForSession(sessionMinutes: Long, distractions: Int = 0, pomodoros: Int = 0): FocusLevel {
        val score = calculateScore(sessionMinutes, distractions, pomodoros)
        return getFocusLevel(score)
    }

    fun calculateScore(sessionMinutes: Long, distractions: Int = 0, pomodoros: Int = 0): Double {
        if (sessionMinutes <= 0) return 0.0

        // Base score: session duration contributes 60%
        val durationScore = (sessionMinutes.toDouble() / 60.0).coerceAtMost(1.0) * 0.6

        // Distraction penalty: each distraction reduces score
        val distractionPenalty = (distractions.toDouble() / 5.0).coerceAtMost(0.5) * 0.3

        // Pomodoro bonus: completed pomodoros increase score
        val pomodoroBonus = (pomodoros.toDouble() / 5.0).coerceAtMost(0.5) * 0.1

        val score = durationScore - distractionPenalty + pomodoroBonus
        return score.coerceIn(0.0, 1.0)
    }
}

@Entity(tableName = "focus_history")
data class FocusHistory(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val sessionId: Long,
    val focusScore: Double,
    val focusLevel: Int,
    val sessionMinutes: Long,
    val distractions: Int,
    val pomodoros: Int,
    val recordedAt: Long = System.currentTimeMillis()
)
