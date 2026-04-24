package com.porashona.studymaster.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.porashona.studymaster.R
import com.porashona.studymaster.StudyMasterApplication
import com.porashona.studymaster.data.database.StudyDatabase
import com.porashona.studymaster.ui.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.Calendar

/**
 * Widget that shows today's goal progress as a determinate progress bar
 * plus the raw minutes studied vs target.
 */
class ProgressWidget : AppWidgetProvider() {

    override fun onUpdate(context: Context, manager: AppWidgetManager, ids: IntArray) {
        ids.forEach { render(context, manager, it) }
    }

    companion object {
        fun requestUpdate(context: Context) {
            val manager = AppWidgetManager.getInstance(context)
            val ids = manager.getAppWidgetIds(ComponentName(context, ProgressWidget::class.java))
            if (ids.isEmpty()) return
            val intent = Intent(context, ProgressWidget::class.java).apply {
                action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
                putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
            }
            context.sendBroadcast(intent)
        }

        private fun render(context: Context, manager: AppWidgetManager, id: Int) {
            val views = RemoteViews(context.packageName, R.layout.widget_progress)
            val openApp = PendingIntent.getActivity(
                context, 0, Intent(context, MainActivity::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
            )
            views.setOnClickPendingIntent(R.id.progressWidgetRoot, openApp)

            CoroutineScope(Dispatchers.IO).launch {
                val app = context.applicationContext as StudyMasterApplication
                val db = StudyDatabase.getDatabase(context)
                val todayStart = Calendar.getInstance().apply {
                    set(Calendar.HOUR_OF_DAY, 0); set(Calendar.MINUTE, 0)
                    set(Calendar.SECOND, 0); set(Calendar.MILLISECOND, 0)
                }.timeInMillis

                val todaySecs = db.studySessionDao().getTotalStudyTimeSince(todayStart).first() ?: 0L
                val goalMin = runCatching { app.preferencesManager.dailyGoalMinutes.first() }
                    .getOrDefault(120)
                val studiedMin = (todaySecs / 60).toInt()
                val pct = if (goalMin <= 0) 0 else ((studiedMin * 100) / goalMin).coerceIn(0, 100)

                views.setProgressBar(R.id.pbProgressWidget, 100, pct, false)
                views.setTextViewText(
                    R.id.tvProgressWidgetLabel,
                    context.getString(R.string.widget_progress_label, studiedMin, goalMin)
                )
                manager.updateAppWidget(id, views)
            }
        }
    }
}
