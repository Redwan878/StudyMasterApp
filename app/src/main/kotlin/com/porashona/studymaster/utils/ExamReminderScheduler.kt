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
import com.porashona.studymaster.data.model.Exam
import com.porashona.studymaster.ui.MainActivity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 * Schedules D-7 / D-3 / D-1 countdown notifications for each upcoming
 * exam. Request codes are derived deterministically from the exam id so
 * re-scheduling is idempotent and cancels any stale alarms.
 */
object ExamReminderScheduler {
    const val ACTION_FIRE = "com.porashona.studymaster.EXAM_REMINDER_FIRE"
    const val EXTRA_EXAM_ID = "exam_id"
    const val EXTRA_DAYS_LEFT = "days_left"
    const val EXTRA_EXAM_NAME = "exam_name"
    const val EXTRA_EXAM_DATE = "exam_date"

    /** Days-out offsets for reminders (ordered most-advance first). */
    private val OFFSETS = intArrayOf(7, 3, 1)
    private const val HOUR_OF_DAY = 9

    private fun requestCode(examId: Long, offset: Int): Int =
        (examId * 10 + offset).toInt() xor 0x00_4C_00_00

    fun scheduleForAll(context: Context, exams: List<Exam>) {
        val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val now = System.currentTimeMillis()
        for (exam in exams) {
            for (offset in OFFSETS) {
                val target = Calendar.getInstance().apply {
                    timeInMillis = exam.examDate
                    set(Calendar.HOUR_OF_DAY, HOUR_OF_DAY)
                    set(Calendar.MINUTE, 0); set(Calendar.SECOND, 0); set(Calendar.MILLISECOND, 0)
                    add(Calendar.DAY_OF_YEAR, -offset)
                }
                if (target.timeInMillis <= now) continue

                val intent = Intent(context, ExamReminderReceiver::class.java).apply {
                    action = ACTION_FIRE
                    putExtra(EXTRA_EXAM_ID, exam.id)
                    putExtra(EXTRA_DAYS_LEFT, offset)
                    putExtra(EXTRA_EXAM_NAME, exam.name)
                    putExtra(EXTRA_EXAM_DATE, exam.examDate)
                }
                val pi = PendingIntent.getBroadcast(
                    context, requestCode(exam.id, offset), intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
                )
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    am.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, target.timeInMillis, pi)
                } else {
                    @Suppress("DEPRECATION")
                    am.set(AlarmManager.RTC_WAKEUP, target.timeInMillis, pi)
                }
            }
        }
    }

    fun cancelForExam(context: Context, examId: Long) {
        val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        for (offset in OFFSETS) {
            val intent = Intent(context, ExamReminderReceiver::class.java).apply { action = ACTION_FIRE }
            val pi = PendingIntent.getBroadcast(
                context, requestCode(examId, offset), intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
            )
            am.cancel(pi)
        }
    }
}

class ExamReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != ExamReminderScheduler.ACTION_FIRE) return
        val daysLeft = intent.getIntExtra(ExamReminderScheduler.EXTRA_DAYS_LEFT, 0)
        val name = intent.getStringExtra(ExamReminderScheduler.EXTRA_EXAM_NAME).orEmpty()
        val date = intent.getLongExtra(ExamReminderScheduler.EXTRA_EXAM_DATE, 0L)
        val examId = intent.getLongExtra(ExamReminderScheduler.EXTRA_EXAM_ID, 0L)

        val dateStr = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date(date))
        val title = context.getString(R.string.exam_reminder_title, daysLeft)
        val body = context.getString(R.string.exam_reminder_body, name, dateStr)

        val notifIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pi = PendingIntent.getActivity(
            context, examId.toInt(), notifIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )
        val n = NotificationCompat.Builder(context, StudyMasterApplication.ALERT_CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(body)
            .setStyle(NotificationCompat.BigTextStyle().bigText(body))
            .setSmallIcon(R.drawable.ic_notification)
            .setAutoCancel(true)
            .setContentIntent(pi)
            .build()
        (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
            .notify((examId * 10 + daysLeft).toInt(), n)
    }
}
