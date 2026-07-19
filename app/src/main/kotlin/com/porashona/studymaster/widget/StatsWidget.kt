package com.porashona.studymaster.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.porashona.studymaster.R
import com.porashona.studymaster.data.database.StudyDatabase
import com.porashona.studymaster.ui.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.Calendar

/**
 * Home-screen widget that shows today's study time, current streak and
 * level. Tapping the widget opens the app. Updated every 30 minutes by the
 * system plus whenever the app explicitly calls [requestUpdate].
 */
class StatsWidget : AppWidgetProvider() {

    override fun onUpdate(context: Context, manager: AppWidgetManager, ids: IntArray) {
        ids.forEach { updateAppWidget(context, manager, it) }
    }

    companion object {
        /** Ask the system to redraw all pinned instances of this widget. */
        fun requestUpdate(context: Context) {
            val manager = AppWidgetManager.getInstance(context)
            val ids = manager.getAppWidgetIds(ComponentName(context, StatsWidget::class.java))
            if (ids.isEmpty()) return
            val intent = Intent(context, StatsWidget::class.java).apply {
                action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
                putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
            }
            context.sendBroadcast(intent)
        }
    }

    private fun updateAppWidget(context: Context, manager: AppWidgetManager, widgetId: Int) {
        val views = RemoteViews(context.packageName, R.layout.widget_stats)

        // Tap-through to main activity.
        val openIntent = Intent(context, MainActivity::class.java)
        val pi = PendingIntent.getActivity(
            context, 0, openIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )
        views.setOnClickPendingIntent(R.id.tvWidgetTime, pi)
        views.setOnClickPendingIntent(R.id.tvWidgetStreak, pi)
        views.setOnClickPendingIntent(R.id.tvWidgetLevel, pi)

        CoroutineScope(Dispatchers.IO).launch {
            val db = StudyDatabase.getDatabase(context)

            val todayStart = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, 0); set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0); set(Calendar.MILLISECOND, 0)
            }.timeInMillis
            val todaySecs = db.studySessionDao().getTotalStudyTimeSince(todayStart).first() ?: 0L
            val hours = todaySecs / 3600
            val minutes = (todaySecs % 3600) / 60

            val profile = db.userProfileDao().getProfile().first()
            val streak = profile?.currentStreak ?: 0
            val level = profile?.level ?: 1

            views.setTextViewText(R.id.tvWidgetTime, "${hours}h ${minutes}m")
            views.setTextViewText(R.id.tvWidgetStreak, streak.toString())
            views.setTextViewText(R.id.tvWidgetLevel, level.toString())
            manager.updateAppWidget(widgetId, views)
        }
    }
}
