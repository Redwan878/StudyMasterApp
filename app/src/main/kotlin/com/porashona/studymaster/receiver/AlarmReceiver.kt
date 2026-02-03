package com.porashona.studymaster.receiver

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.porashona.studymaster.R
import com.porashona.studymaster.StudyMasterApplication
import com.porashona.studymaster.ui.MainActivity

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            Intent.ACTION_BOOT_COMPLETED -> {
                // Reschedule alarms after boot
                rescheduleAlarms(context)
            }
            ACTION_ROUTINE_ALARM -> {
                val title = intent.getStringExtra(EXTRA_TITLE) ?: "পড়াশোনার সময়!"
                val subject = intent.getStringExtra(EXTRA_SUBJECT) ?: ""
                showNotification(context, title, subject)
            }
        }
    }

    private fun rescheduleAlarms(context: Context) {
        // In production, load routines from database and reschedule alarms
    }

    private fun showNotification(context: Context, title: String, subject: String) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val message = if (subject.isNotEmpty()) {
            "$subject পড়ার সময় হয়েছে!"
        } else {
            "পড়াশোনা শুরু করার সময়!"
        }

        val notification = NotificationCompat.Builder(context, StudyMasterApplication.ROUTINE_CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.ic_notification)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }

    companion object {
        const val ACTION_ROUTINE_ALARM = "com.porashona.studymaster.ROUTINE_ALARM"
        const val EXTRA_TITLE = "title"
        const val EXTRA_SUBJECT = "subject"
        const val EXTRA_ROUTINE_ID = "routine_id"
    }
}