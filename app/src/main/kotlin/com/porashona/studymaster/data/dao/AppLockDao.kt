package com.porashona.studymaster.data.dao

import androidx.room.*
import com.porashona.studymaster.data.model.AppLockConfig
import kotlinx.coroutines.flow.Flow

@Dao
interface AppLockDao {

    // ─── Get ─────────────────────────────────────────────────────────────

    @Query("SELECT * FROM app_lock_config LIMIT 1")
    fun getConfig(): Flow<AppLockConfig?>

    @Query("SELECT * FROM app_lock_config LIMIT 1")
    suspend fun getConfigOnce(): AppLockConfig?

    // ─── Insert ──────────────────────────────────────────────────────────

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(config: AppLockConfig): Long

    // ─── Update ──────────────────────────────────────────────────────────

    @Update
    suspend fun update(config: AppLockConfig)

    // ─── Insert or Update ────────────────────────────────────────────────

    @Transaction
    suspend fun insertOrUpdate(config: AppLockConfig) {
        val existing = getConfigOnce()
        if (existing != null) {
            update(config.copy(id = existing.id))
        } else {
            insert(config)
        }
    }

    // ─── Field-level Updates ─────────────────────────────────────────────

    @Query("UPDATE app_lock_config SET isLocked = :isLocked WHERE id = (SELECT id FROM app_lock_config LIMIT 1)")
    suspend fun setLocked(isLocked: Boolean)

    @Query("UPDATE app_lock_config SET lockType = :lockType WHERE id = (SELECT id FROM app_lock_config LIMIT 1)")
    suspend fun setLockType(lockType: String)

    @Query("UPDATE app_lock_config SET pinHash = :pinHash WHERE id = (SELECT id FROM app_lock_config LIMIT 1)")
    suspend fun setPinHash(pinHash: String?)

    @Query("UPDATE app_lock_config SET lastUnlockedAt = :timestamp WHERE id = (SELECT id FROM app_lock_config LIMIT 1)")
    suspend fun setLastUnlockedAt(timestamp: Long = System.currentTimeMillis())

    @Query("""
        UPDATE app_lock_config SET
            isLocked = :isLocked,
            lockType = :lockType,
            pinHash = :pinHash,
            lastUnlockedAt = :lastUnlockedAt
        WHERE id = (SELECT id FROM app_lock_config LIMIT 1)
    """)
    suspend fun updateAllFields(
        isLocked: Boolean,
        lockType: String,
        pinHash: String? = null,
        lastUnlockedAt: Long? = null
    )

    // ─── Delete ──────────────────────────────────────────────────────────

    @Query("DELETE FROM app_lock_config")
    suspend fun deleteAll()

    // ─── Exists Check ────────────────────────────────────────────────────

    @Query("SELECT COUNT(*) FROM app_lock_config")
    suspend fun getCount(): Int

    @Query("SELECT EXISTS(SELECT 1 FROM app_lock_config LIMIT 1)")
    suspend fun hasConfig(): Boolean
}