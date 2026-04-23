package com.porashona.studymaster

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.porashona.studymaster.data.database.StudyDatabase
import com.porashona.studymaster.data.preferences.PreferencesManager

class StudyMasterApplication : Application() {

    val database: StudyDatabase by lazy {
        StudyDatabase.getDatabase(this)
    }

    val preferencesManager: PreferencesManager by lazy {
        PreferencesManager(this)
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannels()
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