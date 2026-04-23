package com.porashona.studymaster.utils

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.porashona.studymaster.R
import com.porashona.studymaster.StudyMasterApplication
import com.porashona.studymaster.ui.MainActivity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.util.Calendar

/**
 * Schedules a daily study reminder notification at a user-chosen HH:mm.
 * Uses setAndAllowWhileIdle on Android 6+ so it fires even in Doze.
 *
 * Call [schedule] whenever the reminder-enabled or reminder-time pref changes,
 * and [cancel] to turn it off. The [DailyReminderReceiver] re-arms the next
 * day's alarm on fire and on boot.
 */
object DailyReminderScheduler {

    const val REQUEST_CODE = 0x53_54_4D_01.toInt() // "STM" + 01
    const val ACTION_FIRE = "com.porashona.studymaster.DAILY_REMINDER_FIRE"

    fun schedule(context: Context, hhmm: String) {
        val (hour, minute) = parseHhMm(hhmm)
        val now = Calendar.getInstance()
        val target = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            if (timeInMillis <= now.timeInMillis) add(Calendar.DAY_OF_YEAR, 1)
        }
        val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val pi = PendingIntent.getBroadcast(
            context,
            REQUEST_CODE,
            buildFireIntent(context),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            am.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, target.timeInMillis, pi)
        } else {
            @Suppress("DEPRECATION")
            am.set(AlarmManager.RTC_WAKEUP, target.timeInMillis, pi)
        }
    }

    fun cancel(context: Context) {
        val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val pi = PendingIntent.getBroadcast(
            context,
            REQUEST_CODE,
            buildFireIntent(context),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )
        am.cancel(pi)
    }

    private fun buildFireIntent(context: Context) =
        Intent(context, DailyReminderReceiver::class.java).apply { action = ACTION_FIRE }

    private fun parseHhMm(hhmm: String): Pair<Int, Int> {
        val parts = hhmm.split(":")
        val h = parts.getOrNull(0)?.toIntOrNull()?.coerceIn(0, 23) ?: 9
        val m = parts.getOrNull(1)?.toIntOrNull()?.coerceIn(0, 59) ?: 0
        return h to m
    }
}

class DailyReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != DailyReminderScheduler.ACTION_FIRE) return
        val notifIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pi = PendingIntent.getActivity(
            context, 0, notifIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )
        val n = NotificationCompat.Builder(context, StudyMasterApplication.ROUTINE_CHANNEL_ID)
            .setContentTitle(context.getString(R.string.daily_reminder_notif_title))
            .setContentText(context.getString(R.string.daily_reminder_notif_body))
            .setSmallIcon(R.drawable.ic_notification)
            .setAutoCancel(true)
            .setContentIntent(pi)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
        val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        nm.notify(DailyReminderScheduler.REQUEST_CODE, n)

        // Re-arm for tomorrow at the same HH:mm. The scheduler computes
        // next-day automatically since target <= now after firing.
        val app = context.applicationContext
        val prefs = (app as? StudyMasterApplication)?.preferencesManager
        val current = if (prefs != null) {
            runBlocking { prefs.dailyReminderTime.first() }
        } else {
            "09:00"
        }
        DailyReminderScheduler.schedule(app, current)
    }
}
