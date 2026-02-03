package com.porashona.studymaster.data.dao

import androidx.room.*
import com.porashona.studymaster.data.model.BlockedApp
import com.porashona.studymaster.data.model.BlockStatistic
import kotlinx.coroutines.flow.Flow

@Dao
interface BlockedAppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(app: BlockedApp)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(apps: List<BlockedApp>)

    @Update
    suspend fun update(app: BlockedApp)

    @Delete
    suspend fun delete(app: BlockedApp)

    @Query("SELECT * FROM blocked_apps ORDER BY appName ASC")
    fun getAllBlockedApps(): Flow<List<BlockedApp>>

    @Query("SELECT * FROM blocked_apps WHERE isBlocked = 1 AND isWhitelisted = 0")
    fun getActiveBlockedApps(): Flow<List<BlockedApp>>

    @Query("SELECT * FROM blocked_apps WHERE packageName = :packageName")
    suspend fun getByPackageName(packageName: String): BlockedApp?

    @Query("SELECT * FROM blocked_apps WHERE isWhitelisted = 1")
    fun getWhitelistedApps(): Flow<List<BlockedApp>>

    @Query("UPDATE blocked_apps SET isBlocked = :isBlocked WHERE packageName = :packageName")
    suspend fun setBlocked(packageName: String, isBlocked: Boolean)

    @Query("UPDATE blocked_apps SET isWhitelisted = :isWhitelisted WHERE packageName = :packageName")
    suspend fun setWhitelisted(packageName: String, isWhitelisted: Boolean)

    @Query("UPDATE blocked_apps SET blockAttempts = blockAttempts + 1, lastBlockedAt = :time WHERE packageName = :packageName")
    suspend fun incrementBlockAttempt(packageName: String, time: Long = System.currentTimeMillis())

    @Query("SELECT SUM(blockAttempts) FROM blocked_apps")
    fun getTotalBlockAttempts(): Flow<Int?>

    // Block Statistics
    @Insert
    suspend fun insertBlockStat(stat: BlockStatistic)

    @Query("SELECT * FROM block_statistics ORDER BY blockedAt DESC LIMIT :limit")
    fun getRecentBlockStats(limit: Int = 50): Flow<List<BlockStatistic>>

    @Query("SELECT COUNT(*) FROM block_statistics WHERE blockedAt >= :since")
    fun getBlockCountSince(since: Long): Flow<Int>

    @Query("SELECT packageName, COUNT(*) as count FROM block_statistics WHERE blockedAt >= :since GROUP BY packageName ORDER BY count DESC")
    fun getMostBlockedApps(since: Long): Flow<List<AppBlockCount>>

    @Query("DELETE FROM block_statistics WHERE blockedAt < :before")
    suspend fun deleteOldStats(before: Long)
}

data class AppBlockCount(
    val packageName: String,
    val count: Int
)