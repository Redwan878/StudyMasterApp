package com.porashona.studymaster.data.repository

import com.porashona.studymaster.data.dao.*
import com.porashona.studymaster.data.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import java.util.*

class StudyRepository(
    private val sessionDao: StudySessionDao,
    private val subjectDao: SubjectDao,
    private val routineDao: RoutineDao,
    private val achievementDao: AchievementDao,
    private val profileDao: UserProfileDao
) {
    // Sessions
    val allSessions: Flow<List<StudySession>> = sessionDao.getAllSessions()
    val totalStudyTime: Flow<Long?> = sessionDao.getTotalStudyTime()
    val totalSessionCount: Flow<Int> = sessionDao.getTotalSessionCount()
    val timeBySubject: Flow<List<SubjectTime>> = sessionDao.getTimeBySubject()

    suspend fun insertSession(session: StudySession): Long {
        val id = sessionDao.insert(session)
        
        // Update profile
        profileDao.addStudyTime(session.durationInSeconds)
        profileDao.addXp(session.xpEarned)
        
        // Update subject time
        if (session.subjectId > 0) {
            subjectDao.addTimeToSubject(session.subjectId, session.durationInSeconds)
        }
        
        // Update streak
        updateStreak()
        
        // Check achievements
        checkAchievements()
        
        return id
    }

    fun getSessionsForToday(): Flow<List<StudySession>> {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val startOfDay = calendar.timeInMillis
        val endOfDay = startOfDay + 24 * 60 * 60 * 1000
        return sessionDao.getSessionsBetween(startOfDay, endOfDay)
    }

    fun getSessionsForWeek(): Flow<List<StudySession>> {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            add(Calendar.WEEK_OF_YEAR, -1)
        }
        val startOfWeek = calendar.timeInMillis
        return sessionDao.getSessionsBetween(startOfWeek, System.currentTimeMillis())
    }

    fun getTodayStudyTime(): Flow<Long?> {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        return sessionDao.getTotalStudyTimeSince(calendar.timeInMillis)
    }

    fun getWeekStudyTime(): Flow<Long?> {
        val calendar = Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, -7)
        }
        return sessionDao.getTotalStudyTimeSince(calendar.timeInMillis)
    }

    // Subjects
    val allSubjects: Flow<List<Subject>> = subjectDao.getAllSubjects()

    suspend fun insertSubject(subject: Subject): Long = subjectDao.insert(subject)
    suspend fun updateSubject(subject: Subject) = subjectDao.update(subject)
    suspend fun deleteSubject(subject: Subject) = subjectDao.delete(subject)
    suspend fun getSubjectById(id: Long): Subject? = subjectDao.getSubjectById(id)

    // Routines
    val allRoutines: Flow<List<Routine>> = routineDao.getAllRoutines()
    val enabledRoutines: Flow<List<Routine>> = routineDao.getEnabledRoutines()

    suspend fun insertRoutine(routine: Routine): Long = routineDao.insert(routine)
    suspend fun updateRoutine(routine: Routine) = routineDao.update(routine)
    suspend fun deleteRoutine(routine: Routine) = routineDao.delete(routine)
    suspend fun setRoutineEnabled(id: Long, enabled: Boolean) = routineDao.setEnabled(id, enabled)

    // Profile
    val userProfile: Flow<UserProfile?> = profileDao.getProfile()

    suspend fun initializeProfile() {
        val existing = profileDao.getProfileSync()
        if (existing == null) {
            profileDao.insert(UserProfile())
        }
    }

    suspend fun updateProfileName(name: String) = profileDao.updateName(name)

    private suspend fun updateStreak() {
        val profile = profileDao.getProfileSync() ?: return
        val lastStudyDate = profile.lastStudyDate ?: return
        
        val today = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        
        val lastDate = Calendar.getInstance().apply {
            timeInMillis = lastStudyDate
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        
        val daysDiff = ((today.timeInMillis - lastDate.timeInMillis) / (24 * 60 * 60 * 1000)).toInt()
        
        val newStreak = when {
            daysDiff == 0 -> profile.currentStreak
            daysDiff == 1 -> profile.currentStreak + 1
            else -> 1
        }
        
        profileDao.updateStreak(newStreak)
    }

    // Achievements
    val allAchievements: Flow<List<Achievement>> = achievementDao.getAllAchievements()
    val unlockedAchievements: Flow<List<Achievement>> = achievementDao.getUnlockedAchievements()
    val unlockedCount: Flow<Int> = achievementDao.getUnlockedCount()

    /** Fires the newly-unlocked achievement after each successful `insertSession`. */
    private val _achievementUnlocks = MutableSharedFlow<Achievement>(extraBufferCapacity = 4)
    val achievementUnlocks: SharedFlow<Achievement> = _achievementUnlocks.asSharedFlow()

    private suspend fun unlockIfNew(id: String) {
        val a = achievementDao.getAchievementById(id) ?: return
        if (a.isUnlocked) return
        achievementDao.unlockAchievement(id)
        profileDao.addXp(a.xpReward)
        _achievementUnlocks.tryEmit(a.copy(isUnlocked = true))
    }

    suspend fun initializeAchievements() {
        val existing = achievementDao.getAllAchievements().first()
        if (existing.isEmpty()) {
            val achievements = listOf(
                Achievement(AchievementTypes.STREAK_7, "৭ দিনের স্ট্রিক", "পরপর ৭ দিন পড়াশোনা করুন", "streak", 500, targetProgress = 7),
                Achievement(AchievementTypes.STREAK_30, "৩০ দিনের স্ট্রিক", "পরপর ৩০ দিন পড়াশোনা করুন", "streak", 2000, targetProgress = 30),
                Achievement(AchievementTypes.HOURS_10, "১০ ঘণ্টা", "মোট ১০ ঘণ্টা পড়াশোনা করুন", "time", 200, targetProgress = 10),
                Achievement(AchievementTypes.HOURS_100, "১০০ ঘণ্টা", "মোট ১০০ ঘণ্টা পড়াশোনা করুন", "time", 1000, targetProgress = 100),
                Achievement(AchievementTypes.HOURS_500, "৫০০ ঘণ্টা", "মোট ৫০০ ঘণ্টা পড়াশোনা করুন", "time", 5000, targetProgress = 500),
                Achievement(AchievementTypes.SESSIONS_10, "১০টি সেশন", "১০টি সেশন সম্পূর্ণ করুন", "session", 100, targetProgress = 10),
                Achievement(AchievementTypes.SESSIONS_100, "১০০টি সেশন", "১০০টি সেশন সম্পূর্ণ করুন", "session", 500, targetProgress = 100),
                Achievement(AchievementTypes.PERFECT_WEEK, "পারফেক্ট সপ্তাহ", "এক সপ্তাহে প্রতিদিন পড়ুন", "week", 300, targetProgress = 7),
                Achievement(AchievementTypes.EARLY_BIRD, "সকালের পাখি", "সকাল ৬টার আগে পড়া শুরু করুন", "early", 150, targetProgress = 1),
                Achievement(AchievementTypes.NIGHT_OWL, "রাতের পেঁচা", "রাত ১২টার পরে পড়া শুরু করুন", "night", 150, targetProgress = 1)
            )
            achievementDao.insertAll(achievements)
        }
    }

    private suspend fun checkAchievements() {
        val profile = profileDao.getProfileSync() ?: return
        val totalHours = (profile.totalStudyTimeSeconds / 3600).toInt()
        val totalSessions = profile.totalSessions
        val currentStreak = profile.currentStreak

        // Streak
        if (currentStreak >= 7) unlockIfNew(AchievementTypes.STREAK_7)
        achievementDao.updateProgress(AchievementTypes.STREAK_7, minOf(currentStreak, 7))
        if (currentStreak >= 30) unlockIfNew(AchievementTypes.STREAK_30)
        achievementDao.updateProgress(AchievementTypes.STREAK_30, minOf(currentStreak, 30))

        // Hours
        if (totalHours >= 10) unlockIfNew(AchievementTypes.HOURS_10)
        achievementDao.updateProgress(AchievementTypes.HOURS_10, minOf(totalHours, 10))
        if (totalHours >= 100) unlockIfNew(AchievementTypes.HOURS_100)
        achievementDao.updateProgress(AchievementTypes.HOURS_100, minOf(totalHours, 100))
        if (totalHours >= 500) unlockIfNew(AchievementTypes.HOURS_500)
        achievementDao.updateProgress(AchievementTypes.HOURS_500, minOf(totalHours, 500))

        // Sessions
        if (totalSessions >= 10) unlockIfNew(AchievementTypes.SESSIONS_10)
        achievementDao.updateProgress(AchievementTypes.SESSIONS_10, minOf(totalSessions, 10))
        if (totalSessions >= 100) unlockIfNew(AchievementTypes.SESSIONS_100)
        achievementDao.updateProgress(AchievementTypes.SESSIONS_100, minOf(totalSessions, 100))

        // Time-of-day
        val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        if (currentHour < 6) unlockIfNew(AchievementTypes.EARLY_BIRD)
        if (currentHour in 0 until 4) unlockIfNew(AchievementTypes.NIGHT_OWL)
    }
}