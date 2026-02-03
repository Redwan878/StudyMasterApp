package com.porashona.studymaster

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.porashona.studymaster.data.database.StudyDatabase

class StudyMasterApplication : Application() {

    val database: StudyDatabase by lazy {
        StudyDatabase.getDatabase(this)
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

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannels(
                listOf(timerChannel, alertChannel, routineChannel)
            )
        }
    }

    companion object {
        const val TIMER_CHANNEL_ID = "timer_channel"
        const val ALERT_CHANNEL_ID = "alert_channel"
        const val ROUTINE_CHANNEL_ID = "routine_channel"
    }
}