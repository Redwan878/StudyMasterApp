package com.porashona.studymaster.data.dao

import androidx.room.*
import com.porashona.studymaster.data.model.DailyChallenge
import com.porashona.studymaster.data.model.UserLevel
import com.porashona.studymaster.data.model.XPGain
import kotlinx.coroutines.flow.Flow

@Dao
interface GamificationDao {

    // ─── XPGain: Insert ──────────────────────────────────────────────────

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertXPGain(xpGain: XPGain): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertXPGains(xpGains: List<XPGain>): List<Long>

    // ─── XPGain: Read ────────────────────────────────────────────────────

    @Query("SELECT * FROM xp_gains ORDER BY gainedAt DESC")
    fun getAllXPGains(): Flow<List<XPGain>>

    @Query("SELECT * FROM xp_gains WHERE source = :source ORDER BY gainedAt DESC")
    fun getXPGainsBySource(source: String): Flow<List<XPGain>>

    @Query("""
        SELECT * FROM xp_gains
        WHERE gainedAt BETWEEN :startTime AND :endTime
        ORDER BY gainedAt DESC
    """)
    fun getXPGainsByDateRange(startTime: Long, endTime: Long): Flow<List<XPGain>>

    @Query("""
        SELECT SUM(amount) FROM xp_gains
        WHERE gainedAt BETWEEN :startTime AND :endTime
    """)
    suspend fun getXPByDateRange(startTime: Long, endTime: Long): Int?

    // ─── XPGain: Total XP ────────────────────────────────────────────────

    @Query("SELECT COALESCE(SUM(amount), 0) FROM xp_gains")
    suspend fun getTotalXP(): Int

    @Query("""
        SELECT COALESCE(SUM(amount), 0) FROM xp_gains
        WHERE gainedAt >= :startOfDay
    """)
    suspend fun getTodayXP(startOfDay: Long): Int

    @Query("""
        SELECT COALESCE(SUM(amount), 0) FROM xp_gains
        WHERE source = :source
    """)
    suspend fun getTotalXPBySource(source: String): Int

    // ─── XPGain: Delete ──────────────────────────────────────────────────

    @Query("DELETE FROM xp_gains")
    suspend fun deleteAllXPGains()

    // ─── UserLevel: Get ──────────────────────────────────────────────────

    @Query("SELECT * FROM user_level LIMIT 1")
    fun getUserLevel(): Flow<UserLevel?>

    @Query("SELECT * FROM user_level LIMIT 1")
    suspend fun getUserLevelOnce(): UserLevel?

    // ─── UserLevel: Insert / Update ──────────────────────────────────────

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserLevel(userLevel: UserLevel): Long

    @Update
    suspend fun updateUserLevel(userLevel: UserLevel)

    @Transaction
    suspend fun insertOrUpdateUserLevel(userLevel: UserLevel) {
        val existing = getUserLevelOnce()
        if (existing != null) {
            updateUserLevel(userLevel.copy(id = existing.id))
        } else {
            insertUserLevel(userLevel)
        }
    }

    @Query("""
        UPDATE user_level SET
            currentLevel = :level,
            currentXP = :currentXP,
            xpForNextLevel = :xpForNextLevel,
            totalXP = :totalXP,
            updatedAt = :updatedAt
        WHERE id = (SELECT id FROM user_level LIMIT 1)
    """)
    suspend fun updateLevelProgress(
        level: Int,
        currentXP: Int,
        xpForNextLevel: Int,
        totalXP: Int,
        updatedAt: Long = System.currentTimeMillis()
    )

    // ─── DailyChallenge: Get ─────────────────────────────────────────────

    @Query("SELECT * FROM daily_challenges ORDER BY date DESC")
    fun getAllChallenges(): Flow<List<DailyChallenge>>

    @Query("SELECT * FROM daily_challenges WHERE id = :id")
    suspend fun getChallengeById(id: Long): DailyChallenge?

    @Query("SELECT * FROM daily_challenges WHERE date = :date LIMIT 1")
    suspend fun getByDate(date: String): DailyChallenge?

    @Query("SELECT * FROM daily_challenges WHERE isActive = 1 AND isCompleted = 0 AND date = :date LIMIT 1")
    suspend fun getActiveChallenge(date: String): DailyChallenge?

    @Query("SELECT * FROM daily_challenges WHERE isActive = 1 AND isCompleted = 0 ORDER BY date DESC LIMIT 1")
    fun getActiveChallenges(): Flow<List<DailyChallenge>>

    @Query("SELECT * FROM daily_challenges ORDER BY date DESC LIMIT :limit")
    fun getRecentChallenges(limit: Int = 7): Flow<List<DailyChallenge>>

    // ─── DailyChallenge: Insert / Update ─────────────────────────────────

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChallenge(challenge: DailyChallenge): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChallenges(challenges: List<DailyChallenge>): List<Long>

    @Update
    suspend fun updateChallenge(challenge: DailyChallenge)

    // ─── DailyChallenge: Mark Completed ──────────────────────────────────

    @Query("""
        UPDATE daily_challenges
        SET isCompleted = 1, completedAt = :completedAt
        WHERE id = :challengeId
    """)
    suspend fun markCompleted(challengeId: Long, completedAt: Long = System.currentTimeMillis())

    @Query("""
        UPDATE daily_challenges
        SET isActive = 0
        WHERE date < :date AND isCompleted = 0
    """)
    suspend fun deactivateExpiredChallenges(date: String)

    // ─── DailyChallenge: Delete ──────────────────────────────────────────

    @Query("DELETE FROM daily_challenges")
    suspend fun deleteAllChallenges()

    @Query("SELECT COUNT(*) FROM daily_challenges WHERE isCompleted = 1")
    fun getCompletedChallengeCount(): Flow<Int>
}