package com.porashona.studymaster.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.widget.RemoteViews
import com.porashona.studymaster.R
import com.porashona.studymaster.data.database.StudyDatabase
import com.porashona.studymaster.data.model.Exam
import com.porashona.studymaster.ui.ComposeMainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/**
 * Lock-screen / home-screen widget that shows a colour-coded countdown
 * to the next upcoming exam.
 *
 * Display:
 * - **Exam subject name** (bold, white)
 * - **Days remaining** (large number, colour-coded by urgency)
 * - **Exam title** (muted subtitle)
 *
 * Colour coding:
 * - **Red**    (`#EF5350`) — ≤ 3 days (urgent!)
 * - **Orange** (`#FFA726`) — ≤ 7 days
 * - **Amber**  (`#FFCA28`) — ≤ 14 days
 * - **Teal**   (`#26C6DA`) — ≤ 30 days
 * - **Green**  (`#66BB6A`) — > 30 days
 * - **Grey**   (`#78909C`) — no upcoming exam
 *
 * Tapping the widget opens the exams screen.
 */
class ExamCountdownWidget : AppWidgetProvider() {

    // ─────────────────────────────────────────────────────────────────────────
    // Lifecycle
    // ─────────────────────────────────────────────────────────────────────────

    override fun onUpdate(
        context: Context,
        manager: AppWidgetManager,
        ids: IntArray,
    ) {
        ids.forEach { updateWidget(context, manager, it) }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Per-instance update
    // ─────────────────────────────────────────────────────────────────────────

    private fun updateWidget(
        context: Context,
        manager: AppWidgetManager,
        widgetId: Int,
    ) {
        val views = RemoteViews(context.packageName, R.layout.widget_exam_countdown)

        // Tap → open exams screen
        val openIntent = Intent(context, ComposeMainActivity::class.java).apply {
            action = ComposeMainActivity.ACTION_NOTIFICATION_TAP
            putExtra(ComposeMainActivity.EXTRA_NAVIGATE_TO, "exams")
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pi = PendingIntent.getActivity(
            context,
            REQUEST_CODE_OPEN,
            openIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )
        views.setOnClickPendingIntent(R.id.exam_widget_root, pi)

        // Load next exam off the main thread
        scope.launch {
            try {
                val db = StudyDatabase.getDatabase(context)
                val now = System.currentTimeMillis()
                val exams = db.examDao().getUpcomingExams(now).first()
                val nextExam = exams.firstOrNull()

                if (nextExam != null) {
                    populateExam(views, nextExam)
                } else {
                    populateNoExam(views)
                }
                manager.updateAppWidget(widgetId, views)
            } catch (_: Exception) {
                views.setTextViewText(R.id.tvExamSubject, "—")
                views.setTextViewText(R.id.tvExamCountdown, "—")
                views.setTextViewText(R.id.tvExamName, "লোড করা যায়নি")
                views.setTextColor(R.id.tvExamCountdown, Color.parseColor("#78909C"))
                manager.updateAppWidget(widgetId, views)
            }
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Data population
    // ─────────────────────────────────────────────────────────────────────────

    private fun populateExam(views: RemoteViews, exam: Exam) {
        val now = System.currentTimeMillis()
        val daysRemaining = ((exam.examDate - now) / (1000 * 60 * 60 * 24)).coerceAtLeast(0L)

        // Subject name (use subjectName field; fall back to exam name)
        val subject = exam.subjectName ?: exam.name
        views.setTextViewText(R.id.tvExamSubject, subject)

        // Days remaining with colour
        val colorHex = when {
            daysRemaining <= 3  -> "#EF5350"   // Red — urgent
            daysRemaining <= 7  -> "#FFA726"   // Orange
            daysRemaining <= 14 -> "#FFCA28"   // Amber
            daysRemaining <= 30 -> "#26C6DA"   // Teal
            else                -> "#66BB6A"   // Green — plenty of time
        }
        views.setTextColor(R.id.tvExamCountdown, Color.parseColor(colorHex))

        val daysText = when (daysRemaining) {
            0L -> "আজ!"
            1L -> "আগামীকাল"
            else -> "${daysRemaining} দিন বাকি"
        }
        views.setTextViewText(R.id.tvExamCountdown, daysText)

        // Exam name (title)
        views.setTextViewText(R.id.tvExamName, exam.name)
    }

    private fun populateNoExam(views: RemoteViews) {
        views.setTextViewText(R.id.tvExamSubject, "কোনো পরীক্ষা নেই")
        views.setTextViewText(R.id.tvExamCountdown, "✅")
        views.setTextColor(R.id.tvExamCountdown, Color.parseColor("#66BB6A"))
        views.setTextViewText(R.id.tvExamName, "সব পরীক্ষা শেষ")
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Public API
    // ─────────────────────────────────────────────────────────────────────────

    companion object {
        private const val REQUEST_CODE_OPEN = 3001

        private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

        /** Trigger a refresh of all pinned instances. */
        fun requestUpdate(context: Context) {
            val manager = AppWidgetManager.getInstance(context)
            val ids = manager.getAppWidgetIds(
                ComponentName(context, ExamCountdownWidget::class.java)
            )
            if (ids.isEmpty()) return
            val intent = Intent(context, ExamCountdownWidget::class.java).apply {
                action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
                putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
            }
            context.sendBroadcast(intent)
        }
    }
}