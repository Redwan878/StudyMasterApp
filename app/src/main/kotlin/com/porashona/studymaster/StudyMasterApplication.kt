package com.porashona.studymaster

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import com.porashona.studymaster.data.database.StudyDatabase
import com.porashona.studymaster.data.preferences.PreferencesManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.io.File
import java.io.PrintWriter
import java.io.StringWriter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class StudyMasterApplication : Application() {

    val database: StudyDatabase by lazy {
        StudyDatabase.getDatabase(this)
    }

    val preferencesManager: PreferencesManager by lazy {
        PreferencesManager(this)
    }

    override fun onCreate() {
        super.onCreate()
        installCrashHandler()
        createNotificationChannels()
        applyStoredThemeMode()
    }

    /**
     * Applies the persisted dark-mode preference before any activity is
     * created, so the user's chosen theme is honoured at cold start.
     * Safe to call synchronously — DataStore reads are cached after first hit,
     * and we fire-and-forget the coroutine.
     */
    private fun applyStoredThemeMode() {
        CoroutineScope(Dispatchers.Main).launch {
            val mode = runCatching { preferencesManager.darkMode.first() }.getOrDefault("system")
            AppCompatDelegate.setDefaultNightMode(
                when (mode) {
                    "light" -> AppCompatDelegate.MODE_NIGHT_NO
                    "dark", "amoled" -> AppCompatDelegate.MODE_NIGHT_YES
                    else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                }
            )
        }
    }

    /**
     * Any uncaught exception anywhere in the app is written to
     * `<externalFilesDir>/crashes/crash-<timestamp>.log` before the default
     * handler kills the process. Users can share the file with us for
     * debugging when the crash happens before Logcat is accessible.
     */
    private fun installCrashHandler() {
        val previous = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            runCatching {
                val dir = File(getExternalFilesDir(null) ?: filesDir, "crashes")
                if (!dir.exists()) dir.mkdirs()
                val stamp = SimpleDateFormat("yyyyMMdd-HHmmss", Locale.US).format(Date())
                val file = File(dir, "crash-$stamp.log")
                val sw = StringWriter()
                throwable.printStackTrace(PrintWriter(sw))
                file.writeText(
                    buildString {
                        append("Thread: ${thread.name}\n")
                        append("Time: ${Date()}\n")
                        append("Build: ${Build.MANUFACTURER} ${Build.MODEL} / Android ${Build.VERSION.RELEASE} (SDK ${Build.VERSION.SDK_INT})\n\n")
                        append(sw.toString())
                    }
                )
                Log.e(TAG, "Uncaught exception; crash log written to ${file.absolutePath}", throwable)
            }
            previous?.uncaughtException(thread, throwable)
        }
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val timerChannel = NotificationChannel(
                TIMER_CHANNEL_ID,
                "টাইমার",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "পড়াশোনার টাইমার বিজ্ঞপ্তি"
                setShowBadge(false)
            }

            val alertChannel = NotificationChannel(
                ALERT_CHANNEL_ID,
                "অ্যালার্ট",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "গুরুত্বপূর্ণ বিজ্ঞপ্তি"
                enableVibration(true)
            }

            val routineChannel = NotificationChannel(
                ROUTINE_CHANNEL_ID,
                "রুটিন",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "রুটিন অনুস্মারক"
            }

            // ADD MUSIC CHANNEL
            val musicChannel = NotificationChannel(
                MUSIC_CHANNEL_ID,
                "মিউজিক",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "পড়াশোনার মিউজিক প্লেয়ার"
                setShowBadge(false)
                setSound(null, null)
            }

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannels(
                listOf(timerChannel, alertChannel, routineChannel, musicChannel)
            )
        }
    }

    companion object {
        private const val TAG = "StudyMasterApp"
        const val TIMER_CHANNEL_ID = "timer_channel"
        const val ALERT_CHANNEL_ID = "alert_channel"
        const val ROUTINE_CHANNEL_ID = "routine_channel"
        const val MUSIC_CHANNEL_ID = "music_channel"  //
    }
    //
    val extendedRepository: com.porashona.studymaster.data.repository.ExtendedRepository by lazy {
        com.porashona.studymaster.data.repository.ExtendedRepository(
            database.goalDao(),
            database.taskDao(),
            database.noteDao(),
            database.examDao(),
            database.challengeDao(),
            database.blockedAppDao(),
            database.quoteDao(),
            database.studyResourceDao(),
            database.academicEventDao(),
            database.userProfileDao()
        )
    }

    /**
     * Single shared instance of the "core" study repository so every fragment
     * doesn't spin up its own copy (each one was a tiny DAO-wrapper allocation
     * per navigation — harmless, but wasteful).
     */
    val studyRepository: com.porashona.studymaster.data.repository.StudyRepository by lazy {
        com.porashona.studymaster.data.repository.StudyRepository(
            database.studySessionDao(),
            database.subjectDao(),
            database.routineDao(),
            database.achievementDao(),
            database.userProfileDao()
        )
    }
}