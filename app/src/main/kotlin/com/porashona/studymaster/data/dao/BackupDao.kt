package com.porashona.studymaster.data.dao

import androidx.room.*
import com.porashona.studymaster.data.model.BackupRecord
import com.porashona.studymaster.data.model.NotificationPreference
import kotlinx.coroutines.flow.Flow

@Dao
interface BackupDao {

    // ═══════════════════════════════════════════════════════════════════════
    // BackupRecord
    // ═══════════════════════════════════════════════════════════════════════

    // ─── Insert ──────────────────────────────────────────────────────────

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBackup(record: BackupRecord): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBackups(records: List<BackupRecord>): List<Long>

    // ─── Read ────────────────────────────────────────────────────────────

    @Query("SELECT * FROM backup_records ORDER BY timestamp DESC")
    fun getAllBackups(): Flow<List<BackupRecord>>

    @Query("SELECT * FROM backup_records WHERE id = :id")
    suspend fun getBackupById(id: Long): BackupRecord?

    @Query("SELECT * FROM backup_records ORDER BY timestamp DESC LIMIT :limit")
    fun getRecentBackups(limit: Int = 10): Flow<List<BackupRecord>>

    @Query("SELECT * FROM backup_records WHERE backupType = :backupType ORDER BY timestamp DESC")
    fun getBackupsByType(backupType: String): Flow<List<BackupRecord>>

    @Query("SELECT * FROM backup_records WHERE isAutoBackup = 1 ORDER BY timestamp DESC LIMIT :limit")
    fun getRecentAutoBackups(limit: Int = 5): Flow<List<BackupRecord>>

    @Query("SELECT * FROM backup_records WHERE backupType = :backupType ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLatestBackupByType(backupType: String): BackupRecord?

    // ─── Delete ──────────────────────────────────────────────────────────

    @Query("DELETE FROM backup_records WHERE id = :id")
    suspend fun deleteBackupById(id: Long)

    @Query("DELETE FROM backup_records WHERE timestamp < :timestamp")
    suspend fun deleteBackupsOlderThan(timestamp: Long)

    @Query("DELETE FROM backup_records")
    suspend fun deleteAllBackups()

    // ─── Count ───────────────────────────────────────────────────────────

    @Query("SELECT COUNT(*) FROM backup_records")
    fun getBackupCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM backup_records WHERE backupType = :backupType")
    suspend fun getBackupCountByType(backupType: String): Int

    // ═══════════════════════════════════════════════════════════════════════
    // NotificationPreference
    // ═══════════════════════════════════════════════════════════════════════

    // ─── Get ─────────────────────────────────────────────────────────────

    @Query("SELECT * FROM notification_preferences ORDER BY type ASC")
    fun getAllPreferences(): Flow<List<NotificationPreference>>

    @Query("SELECT * FROM notification_preferences WHERE id = :id")
    suspend fun getPreferenceById(id: Long): NotificationPreference?

    @Query("SELECT * FROM notification_preferences WHERE type = :type LIMIT 1")
    suspend fun getPreferenceByType(type: String): NotificationPreference?

    @Query("SELECT * FROM notification_preferences WHERE type = :type LIMIT 1")
    fun getPreferenceByTypeFlow(type: String): Flow<NotificationPreference?>

    // ─── Upsert ──────────────────────────────────────────────────────────

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPreference(preference: NotificationPreference): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPreferences(preferences: List<NotificationPreference>): List<Long>

    @Update
    suspend fun updatePreference(preference: NotificationPreference)

    @Transaction
    suspend fun upsertPreference(preference: NotificationPreference) {
        val existing = getPreferenceByType(preference.type)
        if (existing != null) {
            updatePreference(preference.copy(id = existing.id))
        } else {
            insertPreference(preference)
        }
    }

    // ─── Toggle ──────────────────────────────────────────────────────────

    @Query("UPDATE notification_preferences SET isEnabled = :isEnabled WHERE type = :type")
    suspend fun setEnabled(type: String, isEnabled: Boolean)

    @Query("""
        UPDATE notification_preferences
        SET isEnabled = CASE WHEN isEnabled = 1 THEN 0 ELSE 1 END
        WHERE type = :type
    """)
    suspend fun toggleEnabled(type: String)

    @Query("UPDATE notification_preferences SET time = :time WHERE type = :type")
    suspend fun setReminderTime(type: String, time: String)

    @Query("""
        UPDATE notification_preferences
        SET silentStartHour = :startHour, silentEndHour = :endHour
        WHERE type = :type
    """)
    suspend fun setSilentHours(type: String, startHour: Int, endHour: Int)

    // ─── Delete ──────────────────────────────────────────────────────────

    @Query("DELETE FROM notification_preferences")
    suspend fun deleteAllPreferences()
}