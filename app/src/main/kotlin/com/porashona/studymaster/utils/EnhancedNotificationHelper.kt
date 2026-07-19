package com.porashona.studymaster.utils

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.porashona.studymaster.R
import com.porashona.studymaster.StudyMasterApplication
import com.porashona.studymaster.data.database.StudyDatabase
import com.porashona.studymaster.data.model.Exam
import com.porashona.studymaster.ui.ComposeMainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 * Enhanced notification helper that provides intelligent, context-aware
 * notification types beyond the basic routine / daily-reminder system.
 *
 * Capabilities:
 * - **Free-block-aware daily reminder** — looks at what routines are
 *   upcoming and what free time slots exist today.
 * - **Streak-at-risk alert** — fires in the evening if no study has been
 *   done yet today and the user has an active streak.
 * - **Exam countdown pushes** — at 30 / 14 / 7 / 1 days before each exam.
 * - **Weak subject nudge** — identifies the subject with the lowest
 *   syllabus-completion percentage.
 * - **Silent hours** — skips all notifications during configured coaching
 *   class time ranges.
 * - **XP gain notification** — shown when the user earns XP.
 * - **Achievement unlock notification** — shown when an achievement unlocks.
 * - **Flashcard review reminder** — reminds about due flashcards.
 * - **Practice test reminder** — nudges the user to take a practice test.
 *
 * All notifications respect [isInSilentHours] and the master
 * [notificationEnabled] preference.
 */
object EnhancedNotificationHelper {

    // ── Channel IDs (must match those in StudyMasterApplication) ───────────
    private val CHANNEL_INSIGHTS get() = StudyMasterApplication.INSIGHTS_CHANNEL_ID

    // ── Unique notification IDs (high range to avoid collisions) ──────────
    private const val ID_STREAK_AT_RISK       = 50_001
    private const val ID_FREE_BLOCK_REMINDER  = 50_002
    private const val ID_WEAK_SUBJECT_NUDGE   = 50_003
    private const val ID_XP_GAIN              = 50_010
    private const val ID_ACHIEVEMENT_UNLOCK   = 50_011
    private const val ID_FLASHCARD_REMINDER   = 50_020
    private const val ID_PRACTICE_TEST_REMINDER = 50_021
    // Exam countdown IDs: 50_100 + (examId % 900)

    // ── Silent-hours configuration ────────────────────────────────────────
    /** Default coaching-class hours during which we suppress notifications. */
    private const val SILENT_HOUR_START_DEFAULT = 16   // 4:00 PM
    private const val SILENT_HOUR_END_DEFAULT   = 18   // 6:00 PM

    // ── Scope ─────────────────────────────────────────────────────────────
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    // ══════════════════════════════════════════════════════════════════════
    // Public API — fire individual notification types
    // ══════════════════════════════════════════════════════════════════════

    // ─────────────────────────────────────────────────────────────────────
    // 1. Free-block-aware daily reminder
    // ─────────────────────────────────────────────────────────────────────

    /**
     * Shows a notification that tells the user what free study blocks they
     * have remaining today, and what subject to focus on based on their
     * upcoming routines and weak areas.
     *
     * Call from [DailyReminderReceiver] after the basic reminder, or from a
     * WorkManager worker.
     */
    fun sendFreeBlockReminder(context: Context) {
        scope.launch {
            if (!shouldSend(context)) return@launch

            val db = StudyDatabase.getDatabase(context)
            val prefs = (context.applicationContext as? StudyMasterApplication)
                ?.preferencesManager ?: return@launch

            // Find the next upcoming routine for today
            val routines = db.routineDao().getEnabledRoutines().first()
            val cal = Calendar.getInstance()
            val dayOfWeek = (cal.get(Calendar.DAY_OF_WEEK) - 1).coerceIn(0, 6)
            val nowMinutes = cal.get(Calendar.HOUR_OF_DAY) * 60 + cal.get(Calendar.MINUTE)

            val todayRoutines = routines.filter { r ->
                when (r.repeatType) {
                    com.porashona.studymaster.data.model.RepeatType.DAILY,
                    com.porashona.studymaster.data.model.RepeatType.ONCE -> true
                    com.porashona.studymaster.data.model.RepeatType.WEEKLY,
                    com.porashona.studymaster.data.model.RepeatType.CUSTOM ->
                        r.repeatDays.contains(dayOfWeek)
                }
            }

            val nextRoutine = todayRoutines
                .filter { (it.hour * 60 + it.minute) > nowMinutes }
                .minByOrNull { it.hour * 60 + it.minute }

            val subjectName = nextRoutine?.subjectName ?: nextRoutine?.title ?: ""
            val timeStr = if (nextRoutine != null) {
                String.format("%02d:%02d", nextRoutine.hour, nextRoutine.minute)
            } else ""

            val title = "📝 আজকের পড়াশোনার পরিকল্পনা"
            val body = if (nextRoutine != null) {
                "পরবর্তী রুটিন: $timeStr — $subjectName"
            } else {
                "আজকে আর কোনো রুটিন নেই। ফ্রি সময়ে দুর্বল বিষয়ে ফোকাস করুন!"
            }

            notify(context, ID_FREE_BLOCK_REMINDER, CHANNEL_INSIGHTS, title, body)
        }
    }

    // ─────────────────────────────────────────────────────────────────────
    // 2. Streak-at-risk alert
    // ─────────────────────────────────────────────────────────────────────

    /**
     * If the user has an active streak (>= 2 days) but hasn't studied at
     * all today and it's past a configurable "risk hour" (default 20:00),
     * send an urgent notification.
     *
     * Call from a WorkManager periodic worker (e.g. every 30 min after 18:00).
     */
    fun checkAndAlertStreakAtRisk(context: Context) {
        scope.launch {
            if (!shouldSend(context)) return@launch

            val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
            // Only alert between 20:00 and 23:59
            if (hour < STREAK_RISK_HOUR_START) return@launch

            val db = StudyDatabase.getDatabase(context)
            val profile = db.userProfileDao().getProfile().first() ?: return@launch
            if (profile.currentStreak < 2) return@launch

            // Check if any study was done today
            val todayStart = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }.timeInMillis

            val todaySecs = db.studySessionDao()
                .getTotalStudyTimeSince(todayStart)
                .first() ?: 0L
            if (todaySecs > 0) return@launch // Already studied today

            val title = "🔥 স্ট্রিক ঝুঁকিতে!"
            val body = "আজকে এখনো কোনো পড়াশোনা হয়নি। " +
                    "আপনার ${profile.currentStreak} দিনের স্ট্রিক নষ্ট হতে পারে! " +
                    "এখনই শুরু করুন।"

            notify(
                context,
                ID_STREAK_AT_RISK,
                StudyMasterApplication.ALERT_CHANNEL_ID,
                title,
                body,
            )
        }
    }

    // ─────────────────────────────────────────────────────────────────────
    // 3. Exam countdown pushes at 30 / 14 / 7 / 1 days
    // ─────────────────────────────────────────────────────────────────────

    /**
     * Schedules enhanced exam countdown notifications at the four
     * milestone thresholds: 30, 14, 7, and 1 day(s) before each exam.
     *
     * This *extends* (does not replace) the existing [ExamReminderScheduler]
     * which fires at 7/3/1 days. Call this from the same places where
     * [ExamReminderScheduler.scheduleForAll] is called.
     */
    fun scheduleEnhancedExamCountdowns(context: Context, exams: List<Exam>) {
        val am = context.getSystemService(Context.ALARM_SERVICE) as android.app.AlarmManager
        val now = System.currentTimeMillis()
        val offsets = intArrayOf(30, 14)

        for (exam in exams) {
            for (offset in offsets) {
                val target = Calendar.getInstance().apply {
                    timeInMillis = exam.examDate
                    set(Calendar.HOUR_OF_DAY, 9)
                    set(Calendar.MINUTE, 0)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                    add(Calendar.DAY_OF_YEAR, -offset)
                }
                if (target.timeInMillis <= now) continue

                val intent = Intent(context, EnhancedNotifReceiver::class.java).apply {
                    action = ACTION_EXAM_COUNTDOWN
                    putExtra(EXTRA_EXAM_ID, exam.id)
                    putExtra(EXTRA_EXAM_NAME, exam.name)
                    putExtra(EXTRA_EXAM_SUBJECT, exam.subjectName ?: "")
                    putExtra(EXTRA_DAYS_LEFT, offset)
                    putExtra(EXTRA_EXAM_DATE, exam.examDate)
                }
                val rc = examCountdownRequestCode(exam.id, offset)
                val pi = PendingIntent.getBroadcast(
                    context, rc, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
                )
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    am.setAndAllowWhileIdle(
                        android.app.AlarmManager.RTC_WAKEUP,
                        target.timeInMillis,
                        pi,
                    )
                } else {
                    @Suppress("DEPRECATION")
                    am.set(android.app.AlarmManager.RTC_WAKEUP, target.timeInMillis, pi)
                }
            }
        }
    }

    /**
     * Called by [EnhancedNotifReceiver] when an exam countdown fires.
     */
    fun showExamCountdownNotification(
        context: Context,
        examName: String,
        subjectName: String,
        daysLeft: Int,
        examDate: Long,
        examId: Long,
    ) {
        scope.launch {
            if (!shouldSend(context)) return@launch

            val dateStr = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date(examDate))
            val title = when (daysLeft) {
                1  -> "⚠️ কাল পরীক্ষা!"
                7  -> "📌 সপ্তাহে পরীক্ষা"
                14 -> "📅 ২ সপ্তাহে পরীক্ষা"
                30 -> "📆 মাসে পরীক্ষা"
                else -> "📅 পরীক্ষার অনুস্মারক"
            }

            val body = buildString {
                if (subjectName.isNotEmpty()) append("$subjectName — ")
                append(examName)
                append("\n$dateStr")
                if (daysLeft > 1) append(" ($daysLeft দিন বাকি)")
            }

            notify(
                context,
                examCountdownRequestCode(examId, daysLeft),
                StudyMasterApplication.ALERT_CHANNEL_ID,
                title,
                body,
                navigateTo = "exams",
            )
        }
    }

    // ─────────────────────────────────────────────────────────────────────
    // 4. Weak subject nudge
    // ─────────────────────────────────────────────────────────────────────

    /**
     * Identifies the subject with the lowest syllabus completion percentage
     * and nudges the user to study it. Only fires if there are subjects
     * with completion < 50%.
     *
     * Call from a WorkManager periodic worker (e.g. once daily at 19:00).
     */
    fun sendWeakSubjectNudge(context: Context) {
        scope.launch {
            if (!shouldSend(context)) return@launch

            val db = StudyDatabase.getDatabase(context)
            val completions = db.syllabusChapterDao().getAllCompletionPercentages().first()
            val weakest = completions
                .filter { it.percentage < 50.0 && it.subjectName != null }
                .minByOrNull { it.percentage } ?: return@launch

            val title = "📉 দুর্বল বিষয়ে ফোকাস করুন"
            val body = "${weakest.subjectName} — মাত্র ${weakest.percentage.toInt()}% সম্পন্ন। " +
                    "আজকে এই বিষয়ে কিছু পড়াশোনা করুন!"

            notify(context, ID_WEAK_SUBJECT_NUDGE, CHANNEL_INSIGHTS, title, body)
        }
    }

    // ─────────────────────────────────────────────────────────────────────
    // 5. Silent hours
    // ─────────────────────────────────────────────────────────────────────

    /**
     * Returns true if the current time falls within the "silent hours"
     * window (typically coaching-class time). Notifications should be
     * suppressed during this period.
     *
     * The window is read from [PreferencesManager] if available; falls back
     * to [SILENT_HOUR_START_DEFAULT]–[SILENT_HOUR_END_DEFAULT].
     */
    suspend fun isInSilentHours(context: Context): Boolean {
        val prefs = (context.applicationContext as? StudyMasterApplication)?.preferencesManager
        // Silent hours config could be extended to DataStore; for now use defaults.
        val startHour = SILENT_HOUR_START_DEFAULT
        val endHour = SILENT_HOUR_END_DEFAULT
        val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)

        return if (startHour < endHour) {
            currentHour in startHour until endHour
        } else {
            // Wraps midnight (e.g. 22:00 – 06:00)
            currentHour >= startHour || currentHour < endHour
        }
    }

    // ─────────────────────────────────────────────────────────────────────
    // 6. XP gain notification
    // ─────────────────────────────────────────────────────────────────────

    /**
     * Shows a brief notification when the user earns XP. Uses a low-priority
     * channel so it's non-intrusive.
     *
     * @param source   Human-readable source description (e.g. "25m পড়াশোনা")
     * @param amount   XP amount earned
     */
    fun showXPGain(context: Context, source: String, amount: Int) {
        scope.launch {
            if (!shouldSend(context)) return@launch

            val title = "⭐ +$amount XP"
            val body = source

            val notification = NotificationCompat.Builder(
                context,
                StudyMasterApplication.GAMIFICATION_CHANNEL_ID,
            )
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.drawable.ic_notification)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setTimeoutAfter(5_000) // Auto-dismiss after 5 seconds
                .setContentIntent(openAppPendingIntent(context))
                .build()

            val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nm.notify(ID_XP_GAIN + (amount % 100), notification)
        }
    }

    // ─────────────────────────────────────────────────────────────────────
    // 7. Achievement unlock notification
    // ─────────────────────────────────────────────────────────────────────

    /**
     * Shows a prominent notification when the user unlocks an achievement.
     *
     * @param achievementId Unique ID of the achievement
     * @param title         Achievement title
     * @param description   Achievement description
     * @param xpReward      XP reward for the achievement
     */
    fun showAchievementUnlock(
        context: Context,
        achievementId: String,
        title: String,
        description: String,
        xpReward: Int,
    ) {
        scope.launch {
            if (!shouldSend(context)) return@launch

            val notifTitle = "🏆 অর্জন আনলক!"
            val body = "$title (+$xpReward XP)\n$description"

            notify(
                context,
                ID_ACHIEVEMENT_UNLOCK + achievementId.hashCode().mod(1000),
                StudyMasterApplication.GAMIFICATION_CHANNEL_ID,
                notifTitle,
                body,
                navigateTo = "achievements",
            )
        }
    }

    // ─────────────────────────────────────────────────────────────────────
    // 8. Flashcard review reminder
    // ─────────────────────────────────────────────────────────────────────

    /**
     * Checks if there are flashcards due for review and sends a reminder.
     *
     * Call from a WorkManager periodic worker (e.g. twice daily).
     */
    fun sendFlashcardReviewReminder(context: Context) {
        scope.launch {
            if (!shouldSend(context)) return@launch

            val db = StudyDatabase.getDatabase(context)
            val dueCards = db.flashcardDao().getAllDueCardsForReview().first()
            if (dueCards.isEmpty()) return@launch

            val title = "🃏 ফ্ল্যাশকার্ড রিভিউ বাকি"
            val body = "${dueCards.size}টি কার্ড রিভিউ করার সময় হয়েছে!"

            notify(
                context,
                ID_FLASHCARD_REMINDER,
                StudyMasterApplication.FLASHCARD_REVIEW_CHANNEL_ID,
                title,
                body,
                navigateTo = "flashcards",
                action = ComposeMainActivity.ACTION_FLASHCARD_REMINDER,
            )
        }
    }

    // ─────────────────────────────────────────────────────────────────────
    // 9. Practice test reminder
    // ─────────────────────────────────────────────────────────────────────

    /**
     * Nudges the user to take a practice test, especially for weak subjects.
     *
     * Call from a WorkManager periodic worker (e.g. once daily).
     */
    fun sendPracticeTestReminder(context: Context) {
        scope.launch {
            if (!shouldSend(context)) return@launch

            val db = StudyDatabase.getDatabase(context)
            val completions = db.syllabusChapterDao().getAllCompletionPercentages().first()
            val weakSubject = completions
                .filter { it.percentage < 60.0 && it.subjectName != null }
                .minByOrNull { it.percentage }

            val title = "📝 প্র্যাকটিস টেস্ট দিন!"
            val body = if (weakSubject != null) {
                "${weakSubject.subjectName} বিষয়ে আপনার প্রস্তুতি পরীক্ষা করুন।"
            } else {
                "নিয়মিত প্র্যাকটিস টেস্ট দিলে পরীক্ষায় ভালো করবেন!"
            }

            notify(
                context,
                ID_PRACTICE_TEST_REMINDER,
                StudyMasterApplication.PRACTICE_TEST_CHANNEL_ID,
                title,
                body,
                navigateTo = "practice",
                action = ComposeMainActivity.ACTION_PRACTICE_REMINDER,
            )
        }
    }

    // ══════════════════════════════════════════════════════════════════════
    // Internal helpers
    // ══════════════════════════════════════════════════════════════════════

    /**
     * Master gate: returns false if notifications are globally disabled,
     * or if we're currently in silent hours.
     */
    private suspend fun shouldSend(context: Context): Boolean {
        val prefs = (context.applicationContext as? StudyMasterApplication)
            ?.preferencesManager ?: return false

        val enabled = prefs.notificationEnabled.first()
        if (!enabled) return false

        // Skip during silent hours
        if (isInSilentHours(context)) return false

        return true
    }

    /**
     * Posts a notification to the system. All public methods funnel through
     * here for consistent styling and pending-intent creation.
     *
     * @param navigateTo Optional route to navigate to on tap (via [ComposeMainActivity])
     * @param action     Optional action string for the pending intent
     */
    private fun notify(
        context: Context,
        id: Int,
        channelId: String,
        title: String,
        body: String,
        navigateTo: String? = null,
        action: String? = null,
    ) {
        val intent = Intent(context, ComposeMainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            if (action != null) {
                this.action = action
            } else if (navigateTo != null) {
                this.action = ComposeMainActivity.ACTION_NOTIFICATION_TAP
                putExtra(ComposeMainActivity.EXTRA_NAVIGATE_TO, navigateTo)
            }
        }
        val pi = PendingIntent.getActivity(
            context, id, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )

        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle(title)
            .setContentText(body)
            .setStyle(NotificationCompat.BigTextStyle().bigText(body))
            .setSmallIcon(R.drawable.ic_notification)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pi)
            .build()

        val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        nm.notify(id, notification)
    }

    /** Simple PendingIntent that opens the app at home. */
    private fun openAppPendingIntent(context: Context): PendingIntent {
        val intent = Intent(context, ComposeMainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        return PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )
    }

    /** Deterministic request code for exam countdown alarms. */
    private fun examCountdownRequestCode(examId: Long, offset: Int): Int =
        (50_100 + (examId % 900) + offset).toInt()

    // ── Constants for broadcast intent extras ────────────────────────────
    const val ACTION_EXAM_COUNTDOWN = "com.porashona.studymaster.ENHANCED_EXAM_COUNTDOWN"
    const val EXTRA_EXAM_ID = "exam_id"
    const val EXTRA_EXAM_NAME = "exam_name"
    const val EXTRA_EXAM_SUBJECT = "exam_subject"
    const val EXTRA_DAYS_LEFT = "days_left"
    const val EXTRA_EXAM_DATE = "exam_date"

    /** Hour of day after which the streak-at-risk alert can fire. */
    private const val STREAK_RISK_HOUR_START = 20
}

// ══════════════════════════════════════════════════════════════════════════
// Broadcast receiver for enhanced exam countdown notifications
// ══════════════════════════════════════════════════════════════════════════

/**
 * Lightweight [android.content.BroadcastReceiver] that handles the
 * enhanced exam countdown alarm fires and delegates to
 * [EnhancedNotificationHelper].
 */
class EnhancedNotifReceiver : android.content.BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != EnhancedNotificationHelper.ACTION_EXAM_COUNTDOWN) return

        val examId = intent.getLongExtra(EnhancedNotificationHelper.EXTRA_EXAM_ID, 0L)
        val name = intent.getStringExtra(EnhancedNotificationHelper.EXTRA_EXAM_NAME).orEmpty()
        val subject = intent.getStringExtra(EnhancedNotificationHelper.EXTRA_EXAM_SUBJECT).orEmpty()
        val daysLeft = intent.getIntExtra(EnhancedNotificationHelper.EXTRA_DAYS_LEFT, 0)
        val date = intent.getLongExtra(EnhancedNotificationHelper.EXTRA_EXAM_DATE, 0L)

        EnhancedNotificationHelper.showExamCountdownNotification(
            context = context,
            examName = name,
            subjectName = subject,
            daysLeft = daysLeft,
            examDate = date,
            examId = examId,
        )
    }
}