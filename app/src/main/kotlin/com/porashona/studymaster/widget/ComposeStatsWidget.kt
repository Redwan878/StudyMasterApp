package com.porashona.studymaster.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.RemoteViews
import com.porashona.studymaster.R
import com.porashona.studymaster.data.database.StudyDatabase
import com.porashona.studymaster.data.model.Routine
import com.porashona.studymaster.ui.ComposeMainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Locale

/**
 * Enhanced home-screen widget (AppWidgetProvider) with a glassmorphic dark
 * aesthetic that shows:
 *
 * - **Today's next routine item** — the next enabled routine for today
 * - **Current streak** — fire emoji + day count from user profile
 * - **Study time today** — total work-session seconds since midnight
 * - **Quick-start timer button** — PendingIntent that opens the timer screen
 *
 * Tapping any text area opens the app; the start button opens the timer.
 *
 * Updated automatically by the system (every 30 min) plus on-demand via
 * [requestUpdate].
 */
class ComposeStatsWidget : AppWidgetProvider() {

    // ─────────────────────────────────────────────────────────────────────────
    // AppWidgetProvider lifecycle
    // ─────────────────────────────────────────────────────────────────────────

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray,
    ) {
        appWidgetIds.forEach { id -> updateWidget(context, appWidgetManager, id) }
    }

    override fun onEnabled(context: Context) {
        // Widget was added to the home screen — no special action needed
    }

    override fun onDisabled(context: Context) {
        // Last widget instance removed — clean up if needed
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Per-instance update
    // ─────────────────────────────────────────────────────────────────────────

    private fun updateWidget(
        context: Context,
        manager: AppWidgetManager,
        widgetId: Int,
    ) {
        val views = RemoteViews(context.packageName, R.layout.widget_compose_stats)

        // ── Click handlers ──────────────────────────────────────────────

        // Tap on routine / streak / study-time → open the app
        val openAppIntent = Intent(context, ComposeMainActivity::class.java).apply {
            action = ComposeMainActivity.ACTION_WIDGET_OPEN_APP
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val openAppPi = PendingIntent.getActivity(
            context,
            REQUEST_CODE_OPEN_APP,
            openAppIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )
        views.setOnClickPendingIntent(R.id.tvWidgetRoutineLabel, openAppPi)
        views.setOnClickPendingIntent(R.id.tvWidgetRoutine, openAppPi)
        views.setOnClickPendingIntent(R.id.tvWidgetStreak, openAppPi)
        views.setOnClickPendingIntent(R.id.tvWidgetStudyTime, openAppPi)

        // Tap on start-timer button → open timer screen
        val startTimerIntent = Intent(context, ComposeMainActivity::class.java).apply {
            action = ComposeMainActivity.ACTION_WIDGET_START_TIMER
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val startTimerPi = PendingIntent.getActivity(
            context,
            REQUEST_CODE_START_TIMER,
            startTimerIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )
        views.setOnClickPendingIntent(R.id.btnWidgetStartTimer, startTimerPi)

        // ── Load data from Room (off main thread) ───────────────────────
        scope.launch {
            try {
                val db = StudyDatabase.getDatabase(context)

                // 1. Today's study time
                val todayStart = todayStartMillis()
                val todaySecs = db.studySessionDao()
                    .getTotalStudyTimeSince(todayStart)
                    .first() ?: 0L
                val hours = todaySecs / 3600
                val minutes = (todaySecs % 3600) / 60
                views.setTextViewText(
                    R.id.tvWidgetStudyTime,
                    if (hours > 0) "${hours}h ${minutes}m" else "${minutes}m"
                )

                // 2. Current streak
                val profile = db.userProfileDao().getProfile().first()
                val streak = profile?.currentStreak ?: 0
                views.setTextViewText(R.id.tvWidgetStreak, streak.toString())

                // 3. Next routine for today
                val nextRoutine = findNextRoutine(db)
                if (nextRoutine != null) {
                    val timeStr = String.format(
                        Locale.getDefault(), "%02d:%02d",
                        nextRoutine.hour, nextRoutine.minute
                    )
                    val label = nextRoutine.title.ifBlank { nextRoutine.subjectName }
                    views.setTextViewText(R.id.tvWidgetRoutine, "$timeStr  $label")
                    views.setViewVisibility(R.id.tvWidgetRoutine, View.VISIBLE)
                } else {
                    views.setTextViewText(R.id.tvWidgetRoutine, "কোনো রুটিন নেই")
                    views.setViewVisibility(R.id.tvWidgetRoutine, View.VISIBLE)
                }

                manager.updateAppWidget(widgetId, views)
            } catch (_: Exception) {
                // Fallback: show placeholder text so the widget doesn't go blank
                views.setTextViewText(R.id.tvWidgetStudyTime, "—")
                views.setTextViewText(R.id.tvWidgetStreak, "0")
                views.setTextViewText(R.id.tvWidgetRoutine, "—")
                manager.updateAppWidget(widgetId, views)
            }
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Routine lookup
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Returns the first enabled routine scheduled for *today* that hasn't
     * passed yet (based on its hour:minute). If all today's routines have
     * passed, returns the first enabled routine of the day.
     */
    private suspend fun findNextRoutine(db: StudyDatabase): Routine? {
        val allRoutines = db.routineDao().getEnabledRoutines().first()
        val today = Calendar.getInstance()
        val dayOfWeek = (today.get(Calendar.DAY_OF_WEEK) - 1).coerceIn(0, 6)
        val nowMinutes = today.get(Calendar.HOUR_OF_DAY) * 60 + today.get(Calendar.MINUTE)

        // Filter to routines active today
        val todayRoutines = allRoutines.filter { routine ->
            when (routine.repeatType) {
                com.porashona.studymaster.data.model.RepeatType.DAILY,
                com.porashona.studymaster.data.model.RepeatType.ONCE -> true
                com.porashona.studymaster.data.model.RepeatType.WEEKLY,
                com.porashona.studymaster.data.model.RepeatType.CUSTOM ->
                    routine.repeatDays.contains(dayOfWeek)
            }
        }

        // Prefer routines that haven't passed yet
        val upcoming = todayRoutines
            .filter { (it.hour * 60 + it.minute) > nowMinutes }
            .minByOrNull { it.hour * 60 + it.minute }

        return upcoming ?: todayRoutines.minByOrNull { it.hour * 60 + it.minute }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Public API
    // ─────────────────────────────────────────────────────────────────────────

    companion object {
        private const val REQUEST_CODE_OPEN_APP = 2001
        private const val REQUEST_CODE_START_TIMER = 2002

        private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

        /** Ask the system to redraw all pinned instances of this widget. */
        fun requestUpdate(context: Context) {
            val manager = AppWidgetManager.getInstance(context)
            val ids = manager.getAppWidgetIds(
                ComponentName(context, ComposeStatsWidget::class.java)
            )
            if (ids.isEmpty()) return
            val intent = Intent(context, ComposeStatsWidget::class.java).apply {
                action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
                putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
            }
            context.sendBroadcast(intent)
        }
    }
}

/** Midnight of today in millis, used to query today's study time. */
internal fun todayStartMillis(): Long = Calendar.getInstance().apply {
    set(Calendar.HOUR_OF_DAY, 0)
    set(Calendar.MINUTE, 0)
    set(Calendar.SECOND, 0)
    set(Calendar.MILLISECOND, 0)
}.timeInMillis