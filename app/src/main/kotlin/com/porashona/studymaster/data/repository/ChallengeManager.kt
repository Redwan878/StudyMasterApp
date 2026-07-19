package com.porashona.studymaster.data.repository

import com.porashona.studymaster.data.dao.ChallengeDao
import com.porashona.studymaster.data.model.Challenge
import com.porashona.studymaster.data.model.ChallengeType
import com.porashona.studymaster.data.model.Achievement
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.UUID

class ChallengeManager(private val challengeDao: ChallengeDao, private val extendedRepository: ExtendedRepository) {

    private val _dailyChallenges = MutableStateFlow<List<Challenge>>(emptyList())
    val dailyChallenges: StateFlow<List<Challenge>> = _dailyChallenges.asStateFlow()

    private val _weeklyChallenges = MutableStateFlow<List<Challenge>>(emptyList())
    val weeklyChallenges: StateFlow<List<Challenge>> = _weeklyChallenges.asStateFlow()

    private val _completedChallenges = MutableStateFlow<List<Challenge>>(emptyList())
    val completedChallenges: StateFlow<List<Challenge>> = _completedChallenges.asStateFlow()

    private val _challengeRewards = MutableStateFlow<List<ChallengeReward>>(emptyList())
    val challengeRewards: StateFlow<List<ChallengeReward>> = _challengeRewards.asStateFlow()

    init {
        loadChallenges()
    }

    fun loadChallenges() {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val today = sdf.format(Calendar.getInstance().time)
        val weekAgo = sdf.format(Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -7) })

        extendedRepository.appCoroutineScope.launch {
            // Load today's challenges
            challengeDao.getDailyChallenges(today).collect { challenges ->
                _dailyChallenges.value = challenges.filter { it.isActive }
            }

            // Load this week's challenges
            extendedRepository.appCoroutineScope.launch {
                challengeDao.getAllChallenges().collect { allChallenges ->
                    _weeklyChallenges.value = allChallenges.filter {
                        it.date >= weekAgo && it.isActive
                    }
                    _completedChallenges.value = allChallenges.filter { it.isCompleted }
                }
            }

            // Load completed challenges
            challengeDao.getCompletedChallenges().collect { challenges ->
                _completedChallenges.value = challenges
            }
        }
    }

    fun generateDailyChallenges(): List<Challenge> {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val today = sdf.format(Calendar.getInstance().time)
        return DailyChallenges.generateForDate(today)
    }

    fun generateWeeklyChallenges(): List<Challenge> {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val weekAgo = sdf.format(Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -7) })
        val today = sdf.format(Calendar.getInstance().time)

        val baseChallenges = DailyChallenges.generateForDate(today)
        return baseChallenges.map { it.copy(date = it.date, id = it.id.replace("daily_", "weekly_")) } +
                Challenge(
                    id = "weekly_100hours_$weekAgo",
                    title = "100 Study Hours This Week",
                    titleBn = "এই সপ্তাহে ১০০ ঘণ্টা পড়াশোনা",
                    description = "Complete 100 hours of study this week",
                    descriptionBn = "এই সপ্তাহে ১০০ ঘণ্টা পড়াশোনা করুন",
                    type = ChallengeType.STUDY_HOURS,
                    targetValue = 6000, // minutes
                    xpReward = 500,
                    date = today,
                    isActive = true
                ) + Challenge(
                    id = "weekly_10days_$weekAgo",
                    title = "Study Every Day",
                    titleBn = "প্রতিদিন পড়ুন",
                    description = "Study on 10 days this week",
                    descriptionBn = "এই সপ্তাহে ১০ দিন পড়ুন",
                    type = ChallengeType.STREAK,
                    targetValue = 10,
                    xpReward = 300,
                    date = today,
                    isActive = true
                )
    }

    suspend fun acceptChallenge(challenge: Challenge) {
        val currentChallenge = Challenge(
            id = challenge.id,
            title = challenge.title,
            titleBn = challenge.titleBn,
            description = challenge.description,
            descriptionBn = challenge.descriptionBn,
            type = challenge.type,
            targetValue = challenge.targetValue,
            currentValue = 0,
            xpReward = challenge.xpReward,
            isCompleted = false,
            isActive = true,
            date = challenge.date,
            completedAt = null
        )
        challengeDao.insert(currentChallenge)
        loadChallenges()
    }

    suspend fun updateChallengeProgress(challengeId: String, progress: Int) {
        challengeDao.updateProgress(challengeId, progress)

        // Check if completed
        val challenge = challengeDao.getChallengeById(challengeId)?.copy(currentValue = progress) ?: return

        if (progress >= challenge.targetValue && !challenge.isCompleted) {
            val now = System.currentTimeMillis()
            challengeDao.markAsCompleted(challengeId, now)

            // Award XP reward
            val prefs = extendedRepository.prefs
            val currentXp = prefs.getXp()
            prefs.setXp(currentXp + challenge.xpReward)

            // Check for achievements
            checkAchievements(progress, challenge.targetValue, challenge.type)

            loadChallenges()
        }
    }

    private suspend fun checkAchievements(progress: Int, target: Int, type: ChallengeType) {
        when (type) {
            ChallengeType.STUDY_HOURS -> {
                // First 50 hours achievement
                if (progress >= 50 && progress < 60) {
                    extendedRepository.achievementManager.unlockAchievement("first_50_hours")
                }
                // 100 hours achievement
                if (progress >= 100 && progress < 110) {
                    extendedRepository.achievementManager.unlockAchievement("century_hours")
                }
            }
            ChallengeType.POMODORO_COUNT -> {
                // First 10 pomodoros achievement
                if (progress >= 10 && progress < 11) {
                    extendedRepository.achievementManager.unlockAchievement("first_10_pomodoros")
                }
            }
            ChallengeType.STREAK -> {
                // 7 day streak achievement
                if (progress >= 7 && progress < 8) {
                    extendedRepository.achievementManager.unlockAchievement("week_streak")
                }
            }
            else -> {}
        }
    }

    suspend fun getChallengeRewards(): List<ChallengeReward> {
        val challenges = challengeDao.getAllChallenges().first()
        return challenges
            .filter { it.isCompleted }
            .map { ChallengeReward(it.id, it.xpReward, it.title) }
    }

    data class ChallengeReward(
        val challengeId: String,
        val xpReward: Int,
        val title: String
    )
}