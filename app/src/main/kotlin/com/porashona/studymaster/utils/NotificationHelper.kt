package com.porashona.studymaster.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.porashona.studymaster.data.model.Routine
import com.porashona.studymaster.receiver.AlarmReceiver
import java.util.Calendar

class NotificationHelper(private val context: Context) {
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    fun scheduleDailyReminder(hour: Int, minute: Int) {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            if (before(Calendar.getInstance())) add(Calendar.DATE, 1)
        }

        val intent = Intent(context, AlarmReceiver::class.java).apply {
            action = "DAILY_REMINDER"
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context, 100, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }

    /**
     * Register (or replace) a repeating alarm for a routine. The alarm fires
     * at the next occurrence of `hour:minute` and repeats daily — `repeatDays`
     * filtering is done by [AlarmReceiver] on fire, which is simpler than
     * scheduling one alarm per weekday.
     */
    fun scheduleRoutineAlarm(routine: Routine) {
        if (!routine.isEnabled) {
            cancelRoutineAlarm(routine.id)
            return
        }

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, routine.hour)
            set(Calendar.MINUTE, routine.minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            if (before(Calendar.getInstance())) add(Calendar.DATE, 1)
        }

        val intent = Intent(context, AlarmReceiver::class.java).apply {
            action = AlarmReceiver.ACTION_ROUTINE_ALARM
            putExtra(AlarmReceiver.EXTRA_ROUTINE_ID, routine.id)
            putExtra(AlarmReceiver.EXTRA_TITLE, routine.title.ifBlank { routine.subjectName })
            putExtra(AlarmReceiver.EXTRA_SUBJECT, routine.subjectName)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            routineRequestCode(routine.id),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // setRepeating is coalesced on modern Android; setExactAndAllowWhileIdle
        // needs to be re-armed on every fire (done inside AlarmReceiver), but is
        // the only way to survive Doze. Fall back to inexact on older devices
        // where we don't hold SCHEDULE_EXACT_ALARM.
        val canExact = Build.VERSION.SDK_INT < Build.VERSION_CODES.S || alarmManager.canScheduleExactAlarms()
        if (canExact) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )
        } else {
            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                pendingIntent
            )
        }
    }

    fun cancelRoutineAlarm(routineId: Long) {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            action = AlarmReceiver.ACTION_ROUTINE_ALARM
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            routineRequestCode(routineId),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(pendingIntent)
    }

    private fun routineRequestCode(routineId: Long): Int =
        // PendingIntent request codes must fit in an Int; Routine ids are Longs
        // but in practice fit comfortably, so clip defensively.
        (1_000_000 + routineId).toInt()
}
