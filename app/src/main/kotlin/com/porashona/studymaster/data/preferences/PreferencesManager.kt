package com.porashona.studymaster.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "study_master_settings")

class PreferencesManager(private val context: Context) {

    companion object {
        // Music Settings
        val MUSIC_ENABLED = booleanPreferencesKey("music_enabled")
        val MUSIC_VOLUME = floatPreferencesKey("music_volume")
        val SELECTED_TRACK_ID = intPreferencesKey("selected_track_id")
        val AUTO_PLAY_MUSIC = booleanPreferencesKey("auto_play_music")

        // Timer Settings
        val POMODORO_DURATION = intPreferencesKey("pomodoro_duration")
        val SHORT_BREAK_DURATION = intPreferencesKey("short_break_duration")
        val LONG_BREAK_DURATION = intPreferencesKey("long_break_duration")
        val SELECTED_TIMER_MODE = stringPreferencesKey("selected_timer_mode")
        val AUTO_START_BREAKS = booleanPreferencesKey("auto_start_breaks")
        val AUTO_START_POMODOROS = booleanPreferencesKey("auto_start_pomodoros")

        // Notification Settings
        val SOUND_ENABLED = booleanPreferencesKey("sound_enabled")
        val VIBRATION_ENABLED = booleanPreferencesKey("vibration_enabled")
        val NOTIFICATION_ENABLED = booleanPreferencesKey("notification_enabled")
        val DAILY_REMINDER_ENABLED = booleanPreferencesKey("daily_reminder_enabled")
        val DAILY_REMINDER_TIME = stringPreferencesKey("daily_reminder_time")
        val STREAK_REMINDER_ENABLED = booleanPreferencesKey("streak_reminder_enabled")
        val BREAK_REMINDER_ENABLED = booleanPreferencesKey("break_reminder_enabled")
        val QUOTE_NOTIFICATION_ENABLED = booleanPreferencesKey("quote_notification_enabled")

        // Theme Settings
        val DARK_MODE = stringPreferencesKey("dark_mode") // "light", "dark", "system", "amoled"
        val ACCENT_COLOR = stringPreferencesKey("accent_color")
        val FONT_SIZE = stringPreferencesKey("font_size") // "small", "medium", "large"

        // App Blocker Settings
        val APP_BLOCKER_ENABLED = booleanPreferencesKey("app_blocker_enabled")
        val STRICT_MODE_ENABLED = booleanPreferencesKey("strict_mode_enabled")
        val AUTO_BLOCK_ON_TIMER = booleanPreferencesKey("auto_block_on_timer")
        val USE_ROOT_BLOCKING = booleanPreferencesKey("use_root_blocking")

        // Zen Mode Settings
        val ZEN_SESSION_END_TIME = longPreferencesKey("zen_session_end_time")
        val ZEN_LAST_DURATION_MINUTES = intPreferencesKey("zen_last_duration_minutes")
        val ZEN_ENABLE_DND = booleanPreferencesKey("zen_enable_dnd")

        // Goals Settings
        val DAILY_GOAL_MINUTES = intPreferencesKey("daily_goal_minutes")
        val WEEKLY_GOAL_MINUTES = intPreferencesKey("weekly_goal_minutes")
        val SHOW_GOAL_ON_HOME = booleanPreferencesKey("show_goal_on_home")

        // Feature Toggles
        val STUDY_RESOURCES_ENABLED = booleanPreferencesKey("study_resources_enabled")
        val VOICE_COMMANDS_ENABLED = booleanPreferencesKey("voice_commands_enabled")
        val BREAK_ACTIVITIES_ENABLED = booleanPreferencesKey("break_activities_enabled")
        val FOCUS_MODE_ENABLED = booleanPreferencesKey("focus_mode_enabled")

        // Accessibility
        val HIGH_CONTRAST_MODE = booleanPreferencesKey("high_contrast_mode")
        val SCREEN_READER_MODE = booleanPreferencesKey("screen_reader_mode")
        val HAPTIC_FEEDBACK = booleanPreferencesKey("haptic_feedback")
        val COLORBLIND_MODE = stringPreferencesKey("colorblind_mode") // "none", "protanopia", "deuteranopia", "tritanopia"

        // User Data
        val FIRST_LAUNCH = booleanPreferencesKey("first_launch")
        val ONBOARDING_COMPLETED = booleanPreferencesKey("onboarding_completed")
        val LAST_SYNC_TIME = longPreferencesKey("last_sync_time")
    }

    // ==================== MUSIC ====================
    val musicEnabled: Flow<Boolean> = context.dataStore.data
        .catch { if (it is IOException) emit(emptyPreferences()) else throw it }
        .map { it[MUSIC_ENABLED] ?: false }

    val musicVolume: Flow<Float> = context.dataStore.data
        .map { it[MUSIC_VOLUME] ?: 0.5f }

    val selectedTrackId: Flow<Int> = context.dataStore.data
        .map { it[SELECTED_TRACK_ID] ?: 1 }

    val autoPlayMusic: Flow<Boolean> = context.dataStore.data
        .map { it[AUTO_PLAY_MUSIC] ?: false }

    suspend fun setMusicEnabled(enabled: Boolean) {
        context.dataStore.edit { it[MUSIC_ENABLED] = enabled }
    }

    suspend fun setMusicVolume(volume: Float) {
        context.dataStore.edit { it[MUSIC_VOLUME] = volume }
    }

    suspend fun setSelectedTrackId(trackId: Int) {
        context.dataStore.edit { it[SELECTED_TRACK_ID] = trackId }
    }

    suspend fun setAutoPlayMusic(autoPlay: Boolean) {
        context.dataStore.edit { it[AUTO_PLAY_MUSIC] = autoPlay }
    }

    // ==================== TIMER ====================
    val pomodoroDuration: Flow<Int> = context.dataStore.data
        .map { it[POMODORO_DURATION] ?: 25 }

    val shortBreakDuration: Flow<Int> = context.dataStore.data
        .map { it[SHORT_BREAK_DURATION] ?: 5 }

    val longBreakDuration: Flow<Int> = context.dataStore.data
        .map { it[LONG_BREAK_DURATION] ?: 15 }

    val selectedTimerMode: Flow<String> = context.dataStore.data
        .map { it[SELECTED_TIMER_MODE] ?: "classic" }

    val autoStartBreaks: Flow<Boolean> = context.dataStore.data
        .map { it[AUTO_START_BREAKS] ?: false }

    val autoStartPomodoros: Flow<Boolean> = context.dataStore.data
        .map { it[AUTO_START_POMODOROS] ?: false }

    suspend fun setPomodoroDuration(minutes: Int) {
        context.dataStore.edit { it[POMODORO_DURATION] = minutes }
    }

    suspend fun setShortBreakDuration(minutes: Int) {
        context.dataStore.edit { it[SHORT_BREAK_DURATION] = minutes }
    }

    suspend fun setLongBreakDuration(minutes: Int) {
        context.dataStore.edit { it[LONG_BREAK_DURATION] = minutes }
    }

    suspend fun setSelectedTimerMode(modeId: String) {
        context.dataStore.edit { it[SELECTED_TIMER_MODE] = modeId }
    }

    suspend fun setAutoStartBreaks(enabled: Boolean) {
        context.dataStore.edit { it[AUTO_START_BREAKS] = enabled }
    }

    suspend fun setAutoStartPomodoros(enabled: Boolean) {
        context.dataStore.edit { it[AUTO_START_POMODOROS] = enabled }
    }

    // ==================== NOTIFICATIONS ====================
    val soundEnabled: Flow<Boolean> = context.dataStore.data
        .map { it[SOUND_ENABLED] ?: true }

    val vibrationEnabled: Flow<Boolean> = context.dataStore.data
        .map { it[VIBRATION_ENABLED] ?: true }

    val notificationEnabled: Flow<Boolean> = context.dataStore.data
        .map { it[NOTIFICATION_ENABLED] ?: true }

    val dailyReminderEnabled: Flow<Boolean> = context.dataStore.data
        .map { it[DAILY_REMINDER_ENABLED] ?: false }

    val dailyReminderTime: Flow<String> = context.dataStore.data
        .map { it[DAILY_REMINDER_TIME] ?: "09:00" }

    val streakReminderEnabled: Flow<Boolean> = context.dataStore.data
        .map { it[STREAK_REMINDER_ENABLED] ?: true }

    val breakReminderEnabled: Flow<Boolean> = context.dataStore.data
        .map { it[BREAK_REMINDER_ENABLED] ?: true }

    val quoteNotificationEnabled: Flow<Boolean> = context.dataStore.data
        .map { it[QUOTE_NOTIFICATION_ENABLED] ?: false }

    suspend fun setSoundEnabled(enabled: Boolean) {
        context.dataStore.edit { it[SOUND_ENABLED] = enabled }
    }

    suspend fun setVibrationEnabled(enabled: Boolean) {
        context.dataStore.edit { it[VIBRATION_ENABLED] = enabled }
    }

    suspend fun setNotificationEnabled(enabled: Boolean) {
        context.dataStore.edit { it[NOTIFICATION_ENABLED] = enabled }
    }

    suspend fun setDailyReminderEnabled(enabled: Boolean) {
        context.dataStore.edit { it[DAILY_REMINDER_ENABLED] = enabled }
    }

    suspend fun setDailyReminderTime(time: String) {
        context.dataStore.edit { it[DAILY_REMINDER_TIME] = time }
    }

    suspend fun setStreakReminderEnabled(enabled: Boolean) {
        context.dataStore.edit { it[STREAK_REMINDER_ENABLED] = enabled }
    }

    suspend fun setBreakReminderEnabled(enabled: Boolean) {
        context.dataStore.edit { it[BREAK_REMINDER_ENABLED] = enabled }
    }

    suspend fun setQuoteNotificationEnabled(enabled: Boolean) {
        context.dataStore.edit { it[QUOTE_NOTIFICATION_ENABLED] = enabled }
    }

    // ==================== THEME ====================
    val darkMode: Flow<String> = context.dataStore.data
        .map { it[DARK_MODE] ?: "system" }

    val accentColor: Flow<String> = context.dataStore.data
        .map { it[ACCENT_COLOR] ?: "#6C63FF" }

    val fontSize: Flow<String> = context.dataStore.data
        .map { it[FONT_SIZE] ?: "medium" }

    suspend fun setDarkMode(mode: String) {
        context.dataStore.edit { it[DARK_MODE] = mode }
    }

    suspend fun setAccentColor(color: String) {
        context.dataStore.edit { it[ACCENT_COLOR] = color }
    }

    suspend fun setFontSize(size: String) {
        context.dataStore.edit { it[FONT_SIZE] = size }
    }

    // ==================== APP BLOCKER ====================
    val appBlockerEnabled: Flow<Boolean> = context.dataStore.data
        .map { it[APP_BLOCKER_ENABLED] ?: false }

    val strictModeEnabled: Flow<Boolean> = context.dataStore.data
        .map { it[STRICT_MODE_ENABLED] ?: false }

    val autoBlockOnTimer: Flow<Boolean> = context.dataStore.data
        .map { it[AUTO_BLOCK_ON_TIMER] ?: true }

    val useRootBlocking: Flow<Boolean> = context.dataStore.data
        .map { it[USE_ROOT_BLOCKING] ?: false }

    suspend fun setAppBlockerEnabled(enabled: Boolean) {
        context.dataStore.edit { it[APP_BLOCKER_ENABLED] = enabled }
    }

    suspend fun setStrictModeEnabled(enabled: Boolean) {
        context.dataStore.edit { it[STRICT_MODE_ENABLED] = enabled }
    }

    suspend fun setAutoBlockOnTimer(enabled: Boolean) {
        context.dataStore.edit { it[AUTO_BLOCK_ON_TIMER] = enabled }
    }

    suspend fun setUseRootBlocking(enabled: Boolean) {
        context.dataStore.edit { it[USE_ROOT_BLOCKING] = enabled }
    }

    // ==================== ZEN MODE ====================
    /**
     * Wall-clock end time (millis since epoch) of the currently running Zen
     * session, or 0 if no session is active. Reading this as a Flow lets the
     * UI update in real time when a session starts/ends from the service.
     */
    val zenSessionEndTime: Flow<Long> = context.dataStore.data
        .catch { if (it is IOException) emit(emptyPreferences()) else throw it }
        .map { it[ZEN_SESSION_END_TIME] ?: 0L }

    val zenLastDurationMinutes: Flow<Int> = context.dataStore.data
        .map { it[ZEN_LAST_DURATION_MINUTES] ?: 25 }

    val zenEnableDnd: Flow<Boolean> = context.dataStore.data
        .map { it[ZEN_ENABLE_DND] ?: true }

    suspend fun setZenSessionEndTime(endTime: Long) {
        context.dataStore.edit { it[ZEN_SESSION_END_TIME] = endTime }
    }

    suspend fun setZenLastDurationMinutes(minutes: Int) {
        context.dataStore.edit { it[ZEN_LAST_DURATION_MINUTES] = minutes }
    }

    suspend fun setZenEnableDnd(enabled: Boolean) {
        context.dataStore.edit { it[ZEN_ENABLE_DND] = enabled }
    }

    // ==================== GOALS ====================
    val dailyGoalMinutes: Flow<Int> = context.dataStore.data
        .map { it[DAILY_GOAL_MINUTES] ?: 120 } // Default 2 hours

    val weeklyGoalMinutes: Flow<Int> = context.dataStore.data
        .map { it[WEEKLY_GOAL_MINUTES] ?: 600 } // Default 10 hours

    val showGoalOnHome: Flow<Boolean> = context.dataStore.data
        .map { it[SHOW_GOAL_ON_HOME] ?: true }

    suspend fun setDailyGoalMinutes(minutes: Int) {
        context.dataStore.edit { it[DAILY_GOAL_MINUTES] = minutes }
    }

    suspend fun setWeeklyGoalMinutes(minutes: Int) {
        context.dataStore.edit { it[WEEKLY_GOAL_MINUTES] = minutes }
    }

    suspend fun setShowGoalOnHome(show: Boolean) {
        context.dataStore.edit { it[SHOW_GOAL_ON_HOME] = show }
    }

    // ==================== FEATURES ====================
    val studyResourcesEnabled: Flow<Boolean> = context.dataStore.data
        .map { it[STUDY_RESOURCES_ENABLED] ?: false }

    val voiceCommandsEnabled: Flow<Boolean> = context.dataStore.data
        .map { it[VOICE_COMMANDS_ENABLED] ?: false }

    val breakActivitiesEnabled: Flow<Boolean> = context.dataStore.data
        .map { it[BREAK_ACTIVITIES_ENABLED] ?: true }

    val focusModeEnabled: Flow<Boolean> = context.dataStore.data
        .map { it[FOCUS_MODE_ENABLED] ?: false }

    suspend fun setStudyResourcesEnabled(enabled: Boolean) {
        context.dataStore.edit { it[STUDY_RESOURCES_ENABLED] = enabled }
    }

    suspend fun setVoiceCommandsEnabled(enabled: Boolean) {
        context.dataStore.edit { it[VOICE_COMMANDS_ENABLED] = enabled }
    }

    suspend fun setBreakActivitiesEnabled(enabled: Boolean) {
        context.dataStore.edit { it[BREAK_ACTIVITIES_ENABLED] = enabled }
    }

    suspend fun setFocusModeEnabled(enabled: Boolean) {
        context.dataStore.edit { it[FOCUS_MODE_ENABLED] = enabled }
    }

    // ==================== ACCESSIBILITY ====================
    val highContrastMode: Flow<Boolean> = context.dataStore.data
        .map { it[HIGH_CONTRAST_MODE] ?: false }

    val screenReaderMode: Flow<Boolean> = context.dataStore.data
        .map { it[SCREEN_READER_MODE] ?: false }

    val hapticFeedback: Flow<Boolean> = context.dataStore.data
        .map { it[HAPTIC_FEEDBACK] ?: true }

    val colorblindMode: Flow<String> = context.dataStore.data
        .map { it[COLORBLIND_MODE] ?: "none" }

    suspend fun setHighContrastMode(enabled: Boolean) {
        context.dataStore.edit { it[HIGH_CONTRAST_MODE] = enabled }
    }

    suspend fun setScreenReaderMode(enabled: Boolean) {
        context.dataStore.edit { it[SCREEN_READER_MODE] = enabled }
    }

    suspend fun setHapticFeedback(enabled: Boolean) {
        context.dataStore.edit { it[HAPTIC_FEEDBACK] = enabled }
    }

    suspend fun setColorblindMode(mode: String) {
        context.dataStore.edit { it[COLORBLIND_MODE] = mode }
    }

    // ==================== USER DATA ====================
    val firstLaunch: Flow<Boolean> = context.dataStore.data
        .map { it[FIRST_LAUNCH] ?: true }

    val onboardingCompleted: Flow<Boolean> = context.dataStore.data
        .map { it[ONBOARDING_COMPLETED] ?: false }

    suspend fun setFirstLaunch(isFirst: Boolean) {
        context.dataStore.edit { it[FIRST_LAUNCH] = isFirst }
    }

    suspend fun setOnboardingCompleted(completed: Boolean) {
        context.dataStore.edit { it[ONBOARDING_COMPLETED] = completed }
    }

    // ==================== UTILITY ====================
    suspend fun clearAllPreferences() {
        context.dataStore.edit { it.clear() }
    }
}