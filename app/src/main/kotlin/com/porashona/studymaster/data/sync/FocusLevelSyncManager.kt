package com.porashona.studymaster.data.sync

import com.porashona.studymaster.data.dao.FocusLevelDao
import com.porashona.studymaster.data.model.FocusHistory
import com.porashona.studymaster.data.preferences.PreferencesManager

class FocusLevelSyncManager(private val focusLevelDao: FocusLevelDao, private val preferencesManager: PreferencesManager) {

    suspend fun recordFocusSession(sessionId: Long, sessionMinutes: Long, distractions: Int) {
        val score = com.porashona.studymaster.data.model.FocusLevels.calculateScore(sessionMinutes, distractions)
        val level = com.porashona.studymaster.data.model.FocusLevels.getFocusLevel(score).level
        val pomodoros = sessionMinutes.toInt() / 25 // Approximate pomodoro count

        val history = FocusHistory(
            sessionId = sessionId,
            focusScore = score,
            focusLevel = level,
            sessionMinutes = sessionMinutes,
            distractions = distractions,
            pomodoros = pomodoros
        )

        focusLevelDao.insertFocusHistory(history)

        // Update stats
        val totalFocusedTime = preferencesManager.getTotalFocusTime() + sessionMinutes
        preferencesManager.setTotalFocusTime(totalFocusedTime)

        val totalDistractions = preferencesManager.getTotalDistractions() + distractions
        preferencesManager.setTotalDistractions(totalDistractions)

        // TODO: Sync with cloud
    }

    suspend fun getCurrentFocusLevel(): Pair<Int, Double>? {
        val last = focusLevelDao.getLastFocusHistory() ?: return null
        return Pair(last.focusLevel, last.focusScore)
    }

    fun getFocusInsights(): FocusInsights {
        return FocusInsights()
    }
}

data class FocusInsights(
    val focusTrend: Int = 0,
    val averagePower: Double = 0.55,
    val bestFocusDay: String = "Today",
    val focusScoreHistory: List<Int> = emptyList(),
    val distractionsCount: Int = 0,
    val timeSuggestions: List<String> = emptyList()
)
