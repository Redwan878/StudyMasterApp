package com.porashona.studymaster.data.repository

import com.porashona.studymaster.data.dao.AchievementDao
import com.porashona.studymaster.data.model.Achievement
import com.porashona.studymaster.data.model.AchievementTypes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AchievementManager(private val achievementDao: AchievementDao, private val extendedRepository: ExtendedRepository) {

    private val _achievements = MutableStateFlow<List<Achievement>>(emptyList())
    val achievements: StateFlow<List<Achievement>> = _achievements.asStateFlow()

    private val _unlockedAchievements = MutableStateFlow<List<Achievement>>(emptyList())
    val unlockedAchievements: StateFlow<List<Achievement>> = _unlockedAchievements.asStateFlow()

    private val _achievementStats = MutableStateFlow<AchievementStats>(AchievementStats())
    val achievementStats: StateFlow<AchievementStats> = _achievementStats.asStateFlow()

    private val _xpMultipliers = MutableStateFlow(mapOf<String, Double>(AchievementTypes.XP_MULTIPLIER to 1.0))
    val xpMultipliers: StateFlow<Map<String, Double>> = _xpMultipliers.asStateFlow()

    init {
        loadAchievements()
    }

    fun loadAchievements() {
        extendedRepository.appCoroutineScope.launch {
            achievementDao.getAllAchievements().collect { list ->
                _achievements.value = list
                _unlockedAchievements.value = list.filter { it.isUnlocked }
                _achievementStats.value = calculateStats(list.filter { it.isUnlocked })
            }
        }
    }

    fun getAchievementById(id: String): Achievement? {
        return _achievements.value.find { it.id == id }
    }

    suspend fun unlockAchievement(id: String) {
        val achievement = achievementDao.getAchievementById(id) ?: return
        if (achievement.isUnlocked) return

        val now = System.currentTimeMillis()
        val unlocked = achievement.copy(
            isUnlocked = true,
            unlockedAt = now
        )

        achievementDao.update(unlocked)

        // Award XP reward
        val prefs = extendedRepository.prefs
        val currentXp = prefs.getXp()
        prefs.setXp(currentXp + achievement.xpReward)

        // Trigger notification
        showAchievementNotification(unlocked)

        loadAchievements()
    }

    suspend fun getAchievementProgress(id: String): Int {
        val achievement = achievementDao.getAchievementById(id) ?: return 0
        return achievement.progress
    }

    suspend fun incrementAchievementProgress(id: String, increment: Int = 1) {
        val achievement = achievementDao.getAchievementById(id) ?: return

        val newProgress = (achievement.progress + increment).coerceAtMost(achievement.targetProgress)
        val newAchievement = achievement.copy(progress = newProgress)

        achievementDao.update(newAchievement)

        if (newProgress >= achievement.targetProgress && !achievement.isUnlocked) {
            unlockAchievement(id)
        }

        loadAchievements()
    }

    suspend fun checkAllAchievements() {
        val prefs = extendedRepository.prefs
        val stats = prefs.getStudyStats()

        // Check streak achievements
        if (stats.streak >= 3) unlockAchievement(AchievementTypes.STREAK_3)
        if (stats.streak >= 7) unlockAchievement(AchievementTypes.STREAK_7)
        if (stats.streak >= 14) unlockAchievement(AchievementTypes.STREAK_14)
        if (stats.streak >= 30) unlockAchievement(AchievementTypes.STREAK_30)
        if (stats.streak >= 100) unlockAchievement(AchievementTypes.STREAK_100)

        // Check hours achievements
        if (stats.totalHours >= 10) unlockAchievement(AchievementTypes.HOURS_10)
        if (stats.totalHours >= 50) unlockAchievement(AchievementTypes.HOURS_50)
        if (stats.totalHours >= 100) unlockAchievement(AchievementTypes.HOURS_100)
        if (stats.totalHours >= 500) unlockAchievement(AchievementTypes.HOURS_500)
        if (stats.totalHours >= 1000) unlockAchievement(AchievementTypes.HOURS_1000)

        // Check session achievements
        if (stats.totalSessions >= 10) unlockAchievement(AchievementTypes.SESSIONS_10)
        if (stats.totalSessions >= 50) unlockAchievement(AchievementTypes.SESSIONS_50)
        if (stats.totalSessions >= 100) unlockAchievement(AchievementTypes.SESSIONS_100)
        if (stats.totalSessions >= 500) unlockAchievement(AchievementTypes.SESSIONS_500)

        // Check early bird achievements
        val earlyHours = stats.earlyMorningSessions
        if (earlyHours >= 5) unlockAchievement(AchievementTypes.EARLY_BIRD_7AM)
        if (earlyHours >= 10) unlockAchievement(AchievementTypes.EARLY_BIRD_6AM)
        if (earlyHours >= 20) unlockAchievement(AchievementTypes.EARLY_BIRD_5AM)

        // Check night owl achievements
        val nightHours = stats.nightSessions
        if (nightHours >= 5) unlockAchievement(AchievementTypes.NIGHT_OWL_11PM)
        if (nightHours >= 10) unlockAchievement(AchievementTypes.NIGHT_OWL_MIDNIGHT)
        if (stats.totalSessions >= 50) unlockAchievement(AchievementTypes.LATE_NIGHT)

        // Check pomodoro achievements
        if (stats.pomodoros >= 10) unlockAchievement(AchievementTypes.POMODORO_10)
        if (stats.pomodoros >= 50) unlockAchievement(AchievementTypes.POMODORO_50)
        if (stats.pomodoros >= 100) unlockAchievement(AchievementTypes.POMODORO_100)

        // Check challenge achievements
        if (stats.challengesCompleted >= 5) unlockAchievement(AchievementTypes.CHALLENGER)
        if (stats.weeklyChampionDays >= 3) unlockAchievement(AchievementTypes.WEEKLY_CHAMPION)
    }

    private fun calculateStats(unlocked: List<Achievement>): AchievementStats {
        val xpEarned = unlocked.sumOf { it.xpReward }
        val progress = unlocked.sumOf { it.progress.toDouble() / it.targetProgress }
        val completed = unlocked.count { it.isUnlocked }

        return AchievementStats(
            totalXP = xpEarned,
            achievementsCompleted = completed,
            achievementsTotal = unlocked.size,
            progressPercentage = (progress / unlocked.size * 100).toInt()
        )
    }

    private suspend fun showAchievementNotification(achievement: Achievement) {
        val context = extendedRepository.appContext
        val notification = android.app.NotificationCompat.Builder(
            context,
            com.porashona.studymaster.StudyMasterApplication.ACHIEVEMENT_CHANNEL_ID
        )
            .setContentTitle("Achievement Unlocked!")
            .setContentText(achievement.title)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setStyle(android.app.NotificationCompat.BigTextStyle()
                .setContentText("${achievement.title}: ${achievement.description}\n\nXP Rewarded: ${achievement.xpReward}"))
            .setAutoCancel(true)
            .build()

        val notificationManager = context.getSystemService(android.app.NotificationManager::class.java)
        notificationManager.notify(achievement.id.hashCode(), notification)
    }

    fun calculateXPMultiplier(): Double {
        val unlocked = _unlockedAchievements.value
        return unlocked.count { it.id == AchievementTypes.XP_MULTIPLIER }.toDouble()
    }

    data class AchievementStats(
        val totalXP: Int = 0,
        val achievementsCompleted: Int = 0,
        val achievementsTotal: Int = 0,
        val progressPercentage: Int = 0
    )
}