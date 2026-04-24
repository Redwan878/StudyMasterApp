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
 * Daily quote-of-the-day notification. Fires once per day at 08:00 local
 * time when the `quoteNotificationEnabled` pref is on. Re-arms itself.
 */
object QuoteNotificationScheduler {
    const val REQUEST_CODE = 0x53_54_4D_02.toInt()
    const val ACTION_FIRE = "com.porashona.studymaster.QUOTE_NOTIFICATION_FIRE"
    private const val HOUR_OF_DAY = 8

    fun schedule(context: Context) {
        val now = Calendar.getInstance()
        val target = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, HOUR_OF_DAY)
            set(Calendar.MINUTE, 0); set(Calendar.SECOND, 0); set(Calendar.MILLISECOND, 0)
            if (timeInMillis <= now.timeInMillis) add(Calendar.DAY_OF_YEAR, 1)
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
        Intent(context, QuoteNotificationReceiver::class.java).apply { action = ACTION_FIRE }
}

class QuoteNotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != QuoteNotificationScheduler.ACTION_FIRE) return
        val app = context.applicationContext as? StudyMasterApplication ?: return
        val quote = runBlocking {
            runCatching { app.extendedRepository.getRandomQuote() }.getOrNull()
        }
        val body = quote?.let {
            if (it.author.isBlank()) it.textEn else "${it.textEn} — ${it.author}"
        } ?: context.getString(R.string.quote_notif_fallback)

        val notifIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pi = PendingIntent.getActivity(
            context, 0, notifIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )
        val n = NotificationCompat.Builder(context, StudyMasterApplication.ROUTINE_CHANNEL_ID)
            .setContentTitle(context.getString(R.string.quote_notif_title))
            .setContentText(body)
            .setStyle(NotificationCompat.BigTextStyle().bigText(body))
            .setSmallIcon(R.drawable.ic_notification)
            .setAutoCancel(true)
            .setContentIntent(pi)
            .build()
        (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
            .notify(QuoteNotificationScheduler.REQUEST_CODE, n)

        QuoteNotificationScheduler.schedule(context.applicationContext)
    }
}
