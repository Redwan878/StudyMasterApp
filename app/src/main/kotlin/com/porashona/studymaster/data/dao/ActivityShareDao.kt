package com.porashona.studymaster.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.porashona.studymaster.data.model.ActivityShare

@Dao
interface ActivityShareDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShare(share: ActivityShare)

    @Query("SELECT * FROM activity_shares WHERE isPublic = 1 ORDER BY sharedAt DESC LIMIT :limit")
    suspend fun getPublicShares(limit: Int = 20): List<ActivityShare>

    @Query("SELECT * FROM activity_shares WHERE userId = :userId ORDER BY sharedAt DESC")
    suspend fun getUserShares(userId: String): List<ActivityShare>

    @Query("SELECT * FROM activity_shares WHERE userId = :userId LIMIT :limit")
    suspend fun getRecentUserShares(userId: String, limit: Int = 10): List<ActivityShare>

    @Query("SELECT * FROM activity_shares WHERE id = :shareId")
    suspend fun getShareById(shareId: Int): ActivityShare?

    @Update
    suspend fun updateShare(share: ActivityShare)

    @Query("DELETE FROM activity_shares WHERE id = :shareId")
    suspend fun deleteShare(shareId: Int)
}