package com.porashona.studymaster.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteDatabase
import com.porashona.studymaster.data.model.LeaderboardEntry

@Dao
interface LeaderboardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLeaderboardEntry(entry: LeaderboardEntry)

    @Query("SELECT * FROM leaderboard ORDER BY totalXp DESC, totalSessions DESC, totalTime DESC LIMIT :limit")
    suspend fun getTopEntries(limit: Int = 50): List<LeaderboardEntry>

    @Query("SELECT * FROM leaderboard WHERE userId = :userId")
    suspend fun getUserRankEntry(userId: String): LeaderboardEntry?

    @Query("UPDATE leaderboard SET userName = :userName, displayName = :displayName, profileImageUrl = :profileImageUrl, totalXp = :totalXp, level = :level, totalTime = :totalTime, totalSessions = :totalSessions WHERE userId = :userId")
    suspend fun updateLeaderboardEntry(userId: String, userName: String, displayName: String, profileImageUrl: String?, totalXp: Int, level: Int, totalTime: Long, totalSessions: Int)

    @RawQuery
    suspend fun getUserRankEntries(supportSQLiteQuery: SupportSQLiteDatabase): List<LeaderboardEntry>

    @Query("DELETE FROM leaderboard WHERE userId = :userId")
    suspend fun deleteLeaderboardEntry(userId: String)
}