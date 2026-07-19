package com.porashona.studymaster.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

// ─── Enums ───────────────────────────────────────────────────────────────

enum class BackupType {
    LOCAL,
    GOOGLE_DRIVE
}

enum class NotificationType {
    DAILY_REMINDER,
    STREAK_ALERT,
    EXAM_COUNTDOWN,
    WEAK_SUBJECT,
    WEEKLY_REPORT
}

// ─── BackupRecord ───────────────────────────────────────────────────────

@Entity(
    tableName = "backup_records",
    indices = [Index(value = ["backupType"])]
)
data class BackupRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val backupType: String = BackupType.LOCAL.name,
    val filePath: String = "",
    val fileSizeBytes: Long = 0,
    val timestamp: Long = System.currentTimeMillis(),
    val isAutoBackup: Boolean = false
)

// ─── NotificationPreference ─────────────────────────────────────────────

@Entity(
    tableName = "notification_preferences",
    indices = [Index(value = ["type"], unique = true)]
)
data class NotificationPreference(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val type: String,
    val isEnabled: Boolean = true,
    val time: String? = null, // e.g. "08:00" for daily reminder
    val silentStartHour: Int = 23, // 0-23
    val silentEndHour: Int = 7    // 0-23
)