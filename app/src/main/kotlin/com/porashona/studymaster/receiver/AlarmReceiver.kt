package com.porashona.studymaster.receiver

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.porashona.studymaster.R
import com.porashona.studymaster.StudyMasterApplication
import com.porashona.studymaster.data.model.RepeatType
import com.porashona.studymaster.data.model.Routine
import com.porashona.studymaster.ui.MainActivity
import com.porashona.studymaster.utils.NotificationHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.Calendar

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            Intent.ACTION_BOOT_COMPLETED,
            Intent.ACTION_MY_PACKAGE_REPLACED,
            Intent.ACTION_PACKAGE_REPLACED -> {
                rescheduleAlarms(context)
            }
            ACTION_ROUTINE_ALARM -> {
                val routineId = intent.getLongExtra(EXTRA_ROUTINE_ID, -1L)
                val title = intent.getStringExtra(EXTRA_TITLE) ?: "পড়াশোনার সময়!"
                val subject = intent.getStringExtra(EXTRA_SUBJECT) ?: ""
                // Go async so we can check DB state + re-arm the exact alarm.
                val pending = goAsync()
                scope.launch {
                    try {
                        if (shouldFireToday(context, routineId)) {
                            showNotification(context, title, subject)
                        }
                        rearmRoutine(context, routineId)
                    } finally {
                        pending.finish()
                    }
                }
            }
        }
    }

    /**
     * Load all enabled routines from Room and re-arm their alarms. Called
     * after boot, package replace, or an exact-alarm fire.
     */
    private fun rescheduleAlarms(context: Context) {
        val pending = goAsync()
        scope.launch {
            try {
                val app = context.applicationContext as StudyMasterApplication
                val helper = NotificationHelper(context.applicationContext)
                val routines = app.database.routineDao().getEnabledRoutines().first()
                routines.forEach { helper.scheduleRoutineAlarm(it) }

                // Re-arm daily reminder / quote / weekly summary if the user had them on.
                val ctx = context.applicationContext
                if (app.preferencesManager.dailyReminderEnabled.first()) {
                    val hhmm = app.preferencesManager.dailyReminderTime.first()
                    com.porashona.studymaster.utils.DailyReminderScheduler.schedule(ctx, hhmm)
                }
                if (app.preferencesManager.quoteNotificationEnabled.first()) {
                    com.porashona.studymaster.utils.QuoteNotificationScheduler.schedule(ctx)
                }
                if (app.preferencesManager.weeklySummaryEnabled.first()) {
                    com.porashona.studymaster.utils.WeeklySummaryScheduler.schedule(ctx)
                }
                if (app.preferencesManager.overdueTaskReminderEnabled.first()) {
                    com.porashona.studymaster.utils.OverdueTaskScheduler.schedule(ctx)
                }
                if (app.preferencesManager.examCountdownEnabled.first()) {
                    val exams = runCatching { app.database.examDao().getAllExams().first() }
                        .getOrDefault(emptyList())
                    if (exams.isNotEmpty()) {
                        com.porashona.studymaster.utils.ExamReminderScheduler.scheduleForAll(ctx, exams)
                    }
                }
            } finally {
                pending.finish()
            }
        }
    }

    private suspend fun shouldFireToday(context: Context, routineId: Long): Boolean {
        if (routineId < 0) return true
        val app = context.applicationContext as StudyMasterApplication
        val routine: Routine = app.database.routineDao().getRoutineById(routineId) ?: return false
        if (!routine.isEnabled) return false
        return when (routine.repeatType) {
            RepeatType.DAILY, RepeatType.ONCE -> true
            RepeatType.WEEKLY, RepeatType.CUSTOM -> {
                // Calendar.DAY_OF_WEEK is 1 (Sun)..7 (Sat); repeatDays uses 0..6
                val today = (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1)
                    .coerceIn(0, 6)
                routine.repeatDays.contains(today)
            }
        }
    }

    private suspend fun rearmRoutine(context: Context, routineId: Long) {
        if (routineId < 0) return
        val app = context.applicationContext as StudyMasterApplication
        val routine = app.database.routineDao().getRoutineById(routineId) ?: return
        if (routine.repeatType == RepeatType.ONCE) {
            // Don't reschedule one-shot routines.
            app.database.routineDao().setEnabled(routineId, false)
            return
        }
        NotificationHelper(context.applicationContext).scheduleRoutineAlarm(routine)
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

        private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    }
}
