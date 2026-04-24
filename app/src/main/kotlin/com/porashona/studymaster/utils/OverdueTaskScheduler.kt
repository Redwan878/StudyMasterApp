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
 * Daily 09:00 check that counts incomplete tasks with a due date strictly
 * before today and posts a notification. Quiet when there are none.
 */
object OverdueTaskScheduler {
    const val REQUEST_CODE = 0x53_54_4D_04.toInt()
    const val ACTION_FIRE = "com.porashona.studymaster.OVERDUE_TASKS_FIRE"
    private const val HOUR_OF_DAY = 9

    fun schedule(context: Context) {
        val target = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, HOUR_OF_DAY)
            set(Calendar.MINUTE, 0); set(Calendar.SECOND, 0); set(Calendar.MILLISECOND, 0)
            if (timeInMillis <= System.currentTimeMillis()) add(Calendar.DAY_OF_YEAR, 1)
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
        Intent(context, OverdueTaskReceiver::class.java).apply { action = ACTION_FIRE }
}

class OverdueTaskReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != OverdueTaskScheduler.ACTION_FIRE) return
        val app = context.applicationContext as? StudyMasterApplication ?: return

        val startOfToday = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0); set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0); set(Calendar.MILLISECOND, 0)
        }.timeInMillis

        val overdue = runBlocking {
            runCatching {
                app.database.taskDao().getPendingTasks().first()
                    .count { it.dueDate != null && it.dueDate < startOfToday }
            }.getOrDefault(0)
        }

        if (overdue > 0) {
            val title = context.getString(R.string.overdue_title)
            val body = context.resources.getQuantityString(
                R.plurals.overdue_body, overdue, overdue,
            )
            val notifIntent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            val pi = PendingIntent.getActivity(
                context, 0, notifIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
            )
            val n = NotificationCompat.Builder(context, StudyMasterApplication.ALERT_CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.drawable.ic_notification)
                .setAutoCancel(true)
                .setContentIntent(pi)
                .build()
            (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
                .notify(OverdueTaskScheduler.REQUEST_CODE, n)
        }

        // Re-arm for next day regardless.
        OverdueTaskScheduler.schedule(context.applicationContext)
    }
}
