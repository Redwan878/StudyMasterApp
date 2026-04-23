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
import java.util.concurrent.TimeUnit

/**
 * Fires a weekly recap notification every Sunday at 20:00 local time with the
 * user's total study hours and session count for the last 7 days. Alarm
 * re-arms itself on fire and is re-scheduled after boot by
 * [com.porashona.studymaster.receiver.AlarmReceiver].
 */
object WeeklySummaryScheduler {
    const val REQUEST_CODE = 0x53_54_4D_03.toInt()
    const val ACTION_FIRE = "com.porashona.studymaster.WEEKLY_SUMMARY_FIRE"

    fun schedule(context: Context) {
        val target = Calendar.getInstance().apply {
            firstDayOfWeek = Calendar.SUNDAY
            set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
            set(Calendar.HOUR_OF_DAY, 20)
            set(Calendar.MINUTE, 0); set(Calendar.SECOND, 0); set(Calendar.MILLISECOND, 0)
            if (timeInMillis <= System.currentTimeMillis()) add(Calendar.DAY_OF_YEAR, 7)
        }
        val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val pi = PendingIntent.getBroadcast(
            context, REQUEST_CODE, buildIntent(context),
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
            context, REQUEST_CODE, buildIntent(context),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )
        am.cancel(pi)
    }

    private fun buildIntent(context: Context) =
        Intent(context, WeeklySummaryReceiver::class.java).apply { action = ACTION_FIRE }
}

class WeeklySummaryReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != WeeklySummaryScheduler.ACTION_FIRE) return
        val app = context.applicationContext as? StudyMasterApplication ?: return

        val sevenDaysAgo = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(7)
        val totalSecs = runBlocking {
            runCatching {
                app.database.studySessionDao().getTotalStudyTimeSince(sevenDaysAgo).first() ?: 0L
            }.getOrDefault(0L)
        }
        val sessions = runBlocking {
            runCatching {
                app.database.studySessionDao()
                    .getSessionsBetween(sevenDaysAgo, System.currentTimeMillis())
                    .first().size
            }.getOrDefault(0)
        }
        val hours = totalSecs / 3600
        val minutes = (totalSecs % 3600) / 60

        val body = context.getString(R.string.weekly_summary_body, hours, minutes, sessions)

        val notifIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pi = PendingIntent.getActivity(
            context, 0, notifIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )
        val n = NotificationCompat.Builder(context, StudyMasterApplication.ROUTINE_CHANNEL_ID)
            .setContentTitle(context.getString(R.string.weekly_summary_title))
            .setContentText(body)
            .setStyle(NotificationCompat.BigTextStyle().bigText(body))
            .setSmallIcon(R.drawable.ic_notification)
            .setAutoCancel(true)
            .setContentIntent(pi)
            .build()
        (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
            .notify(WeeklySummaryScheduler.REQUEST_CODE, n)

        WeeklySummaryScheduler.schedule(context.applicationContext)
    }
}
