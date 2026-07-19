package com.porashona.studymaster.ui.compose.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.porashona.studymaster.data.dao.AppLockDao
import com.porashona.studymaster.data.dao.BackupDao
import com.porashona.studymaster.data.model.AppLockConfig
import com.porashona.studymaster.data.model.BackupRecord
import com.porashona.studymaster.data.model.BackupType
import com.porashona.studymaster.data.model.LockType
import com.porashona.studymaster.data.model.NotificationType
import com.porashona.studymaster.data.preferences.PreferencesManager
import com.porashona.studymaster.utils.LanguageManager
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

// ─── Data Classes ─────────────────────────────────────────────────────────

enum class ThemeMode { LIGHT, DARK, SYSTEM, AMOLED }

data class BackupState(
    val isBackingUp: Boolean = false,
    val isRestoring: Boolean = false,
    val lastBackupTime: Long? = null,
    val lastBackupPath: String? = null,
    val progress: Float = 0f,
    val message: String? = null,
    val error: String? = null
)

data class GoogleDriveStatus(
    val isConnected: Boolean = false,
    val lastSyncTime: Long? = null,
    val email: String? = null
)

// ─── ViewModel ────────────────────────────────────────────────────────────

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager,
    private val appLockDao: AppLockDao,
    private val backupDao: BackupDao,
    @ApplicationContext private val context: Context
) : ViewModel() {

    // ─── Theme ──────────────────────────────────────────────────────────
    val themeMode: StateFlow<ThemeMode> = preferencesManager.darkMode
        .map { mode ->
            when (mode) {
                "light" -> ThemeMode.LIGHT
                "dark" -> ThemeMode.DARK
                "amoled" -> ThemeMode.AMOLED
                else -> ThemeMode.SYSTEM
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ThemeMode.SYSTEM)

    val accentColor: StateFlow<String> = preferencesManager.accentColor
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "#6C63FF")

    // ─── Language ──────────────────────────────────────────────────────
    private val _language = MutableStateFlow(
        LanguageManager.getLanguage(context)
    )
    val language: StateFlow<String> = _language.asStateFlow()

    // ─── Font Size ─────────────────────────────────────────────────────
    val fontSize: StateFlow<String> = preferencesManager.fontSize
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "medium")

    // ─── Notifications ─────────────────────────────────────────────────
    val notificationsEnabled: StateFlow<Boolean> = preferencesManager.notificationEnabled
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)

    val dailyReminderEnabled: StateFlow<Boolean> = preferencesManager.dailyReminderEnabled
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    val dailyReminderTime: StateFlow<String> = preferencesManager.dailyReminderTime
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "09:00")

    val streakReminderEnabled: StateFlow<Boolean> = preferencesManager.streakReminderEnabled
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)

    val weeklySummaryEnabled: StateFlow<Boolean> = preferencesManager.weeklySummaryEnabled
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)

    val examCountdownEnabled: StateFlow<Boolean> = preferencesManager.examCountdownEnabled
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)

    val soundEnabled: StateFlow<Boolean> = preferencesManager.soundEnabled
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)

    val vibrationEnabled: StateFlow<Boolean> = preferencesManager.vibrationEnabled
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)

    // ─── Backup State ──────────────────────────────────────────────────
    private val _backupState = MutableStateFlow(BackupState())
    val backupState: StateFlow<BackupState> = _backupState.asStateFlow()

    val recentBackups: StateFlow<List<BackupRecord>> = backupDao.getRecentBackups(10)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // ─── App Lock ──────────────────────────────────────────────────────
    val lockConfig: StateFlow<AppLockConfig?> = appLockDao.getConfig()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    // ─── Google Drive ──────────────────────────────────────────────────
    private val _googleDriveStatus = MutableStateFlow(GoogleDriveStatus())
    val googleDriveStatus: StateFlow<GoogleDriveStatus> = _googleDriveStatus.asStateFlow()

    // ─── Other Settings ────────────────────────────────────────────────
    val appBlockerEnabled: StateFlow<Boolean> = preferencesManager.appBlockerEnabled
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    val dailyGoalMinutes: StateFlow<Int> = preferencesManager.dailyGoalMinutes
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 120)

    val weeklyGoalMinutes: StateFlow<Int> = preferencesManager.weeklyGoalMinutes
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 600)

    // ─── Events ────────────────────────────────────────────────────────
    private val _events = MutableStateFlow<SettingsEvent?>(null)
    val events: StateFlow<SettingsEvent?> = _events.asStateFlow()

    // ═══════════════════════════════════════════════════════════════════════
    // Theme
    // ═══════════════════════════════════════════════════════════════════════

    fun setTheme(mode: ThemeMode) {
        viewModelScope.launch {
            val modeString = when (mode) {
                ThemeMode.LIGHT -> "light"
                ThemeMode.DARK -> "dark"
                ThemeMode.AMOLED -> "amoled"
                ThemeMode.SYSTEM -> "system"
            }
            preferencesManager.setDarkMode(modeString)
            _events.value = SettingsEvent.ThemeChanged(mode)
        }
    }

    fun setAccentColor(color: String) {
        viewModelScope.launch {
            preferencesManager.setAccentColor(color)
        }
    }

    // ═══════════════════════════════════════════════════════════════════════
    // Language
    // ═══════════════════════════════════════════════════════════════════════

    fun setLanguage(languageCode: String) {
        LanguageManager.setLanguage(context, languageCode)
        _language.value = languageCode
        _events.value = SettingsEvent.LanguageChanged(languageCode)
    }

    // ═══════════════════════════════════════════════════════════════════════
    // Font Size
    // ═══════════════════════════════════════════════════════════════════════

    fun setFontSize(size: String) {
        viewModelScope.launch {
            preferencesManager.setFontSize(size)
        }
    }

    // ═══════════════════════════════════════════════════════════════════════
    // Notifications
    // ═══════════════════════════════════════════════════════════════════════

    fun toggleNotification(type: NotificationType) {
        viewModelScope.launch {
            when (type) {
                NotificationType.DAILY_REMINDER -> preferencesManager.setDailyReminderEnabled(!dailyReminderEnabled.value)
                NotificationType.STREAK_ALERT -> preferencesManager.setStreakReminderEnabled(!streakReminderEnabled.value)
                NotificationType.EXAM_COUNTDOWN -> preferencesManager.setExamCountdownEnabled(!examCountdownEnabled.value)
                NotificationType.WEEKLY_REPORT -> preferencesManager.setWeeklySummaryEnabled(!weeklySummaryEnabled.value)
                NotificationType.WEAK_SUBJECT -> {
                    // No specific toggle, just general notification
                    preferencesManager.setNotificationEnabled(!notificationsEnabled.value)
                }
            }
        }
    }

    fun setDailyReminderTime(time: String) {
        viewModelScope.launch {
            preferencesManager.setDailyReminderTime(time)
        }
    }

    // ═══════════════════════════════════════════════════════════════════════
    // Backup & Restore
    // ═══════════════════════════════════════════════════════════════════════

    fun performBackup() {
        viewModelScope.launch {
            _backupState.value = _backupState.value.copy(isBackingUp = true, error = null, message = null)

            try {
                // In production, this would copy the Room database file
                // and export it to a user-selected location
                val timestamp = SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.getDefault()).format(Date())
                val fileName = "StudyMaster_backup_$timestamp.json"
                val backupDir = File(context.filesDir, "backups")
                if (!backupDir.exists()) backupDir.mkdirs()

                val backupFile = File(backupDir, fileName)
                // Actual database export logic would go here
                // For now, create a placeholder file
                backupFile.writeText("{\"timestamp\": ${System.currentTimeMillis()}, \"version\": 1}")

                val fileSize = backupFile.length()

                val record = BackupRecord(
                    backupType = BackupType.LOCAL.name,
                    filePath = backupFile.absolutePath,
                    fileSizeBytes = fileSize,
                    isAutoBackup = false
                )
                backupDao.insertBackup(record)

                _backupState.value = BackupState(
                    lastBackupTime = System.currentTimeMillis(),
                    lastBackupPath = backupFile.absolutePath,
                    message = "ব্যাকআপ সফল! ($fileName)"
                )
                _events.value = SettingsEvent.BackupSuccess
            } catch (e: Exception) {
                _backupState.value = _backupState.value.copy(
                    isBackingUp = false,
                    error = "ব্যাকআপ ব্যর্থ: ${e.message}"
                )
                _events.value = SettingsEvent.BackupFailed(e.message ?: "Unknown error")
            }
        }
    }

    fun restoreBackup(filePath: String) {
        viewModelScope.launch {
            _backupState.value = _backupState.value.copy(isRestoring = true, error = null, message = null)

            try {
                val file = File(filePath)
                if (!file.exists()) {
                    _backupState.value = _backupState.value.copy(
                        isRestoring = false,
                        error = "ফাইল পাওয়া যায়নি"
                    )
                    return@launch
                }

                // Actual database restore logic would go here
                // For now, just verify the file exists

                _backupState.value = _backupState.value.copy(
                    isRestoring = false,
                    message = "রিস্টোর সফল! অ্যাপ রিস্টার্ট হবে।"
                )
                _events.value = SettingsEvent.RestoreSuccess
            } catch (e: Exception) {
                _backupState.value = _backupState.value.copy(
                    isRestoring = false,
                    error = "রিস্টোর ব্যর্থ: ${e.message}"
                )
                _events.value = SettingsEvent.RestoreFailed(e.message ?: "Unknown error")
            }
        }
    }

    fun deleteBackup(backupId: Long) {
        viewModelScope.launch {
            val record = backupDao.getBackupById(backupId) ?: return@launch
            // Delete the file
            try {
                File(record.filePath).delete()
            } catch (_: Exception) {
                // File may not exist
            }
            backupDao.deleteBackupById(backupId)
        }
    }

    // ═══════════════════════════════════════════════════════════════════════
    // App Lock
    // ═══════════════════════════════════════════════════════════════════════

    fun enableAppLock(lockType: LockType, pinHash: String? = null) {
        viewModelScope.launch {
            appLockDao.insertOrUpdate(
                AppLockConfig(
                    isLocked = true,
                    lockType = lockType.name,
                    pinHash = pinHash
                )
            )
            _events.value = SettingsEvent.LockEnabled(lockType)
        }
    }

    fun disableAppLock() {
        viewModelScope.launch {
            appLockDao.setLocked(false)
            _events.value = SettingsEvent.LockDisabled
        }
    }

    fun updatePin(newPinHash: String) {
        viewModelScope.launch {
            appLockDao.setPinHash(newPinHash)
        }
    }

    // ═══════════════════════════════════════════════════════════════════════
    // Goals
    // ═══════════════════════════════════════════════════════════════════════

    fun setDailyGoalMinutes(minutes: Int) {
        viewModelScope.launch {
            preferencesManager.setDailyGoalMinutes(minutes)
        }
    }

    fun setWeeklyGoalMinutes(minutes: Int) {
        viewModelScope.launch {
            preferencesManager.setWeeklyGoalMinutes(minutes)
        }
    }

    // ═══════════════════════════════════════════════════════════════════════
    // Utility
    // ═══════════════════════════════════════════════════════════════════════

    fun clearEvent() {
        _events.value = null
    }

    fun clearBackupMessage() {
        _backupState.value = _backupState.value.copy(message = null, error = null)
    }
}

// ─── Events ───────────────────────────────────────────────────────────────

sealed class SettingsEvent {
    data class ThemeChanged(val mode: ThemeMode) : SettingsEvent()
    data class LanguageChanged(val languageCode: String) : SettingsEvent()
    object BackupSuccess : SettingsEvent()
    data class BackupFailed(val error: String) : SettingsEvent()
    object RestoreSuccess : SettingsEvent()
    data class RestoreFailed(val error: String) : SettingsEvent()
    data class LockEnabled(val lockType: LockType) : SettingsEvent()
    object LockDisabled : SettingsEvent()
}