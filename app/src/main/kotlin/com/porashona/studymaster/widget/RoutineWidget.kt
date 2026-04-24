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
import java.util.Locale

/**
 * Widget that shows the next routine slot for today (subject + start time).
 * Falls back to an empty-state message if no routine entries exist for the
 * current weekday.
 */
class RoutineWidget : AppWidgetProvider() {

    override fun onUpdate(context: Context, manager: AppWidgetManager, ids: IntArray) {
        ids.forEach { render(context, manager, it) }
    }

    companion object {
        fun requestUpdate(context: Context) {
            val manager = AppWidgetManager.getInstance(context)
            val ids = manager.getAppWidgetIds(ComponentName(context, RoutineWidget::class.java))
            if (ids.isEmpty()) return
            val intent = Intent(context, RoutineWidget::class.java).apply {
                action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
                putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
            }
            context.sendBroadcast(intent)
        }

        private fun render(context: Context, manager: AppWidgetManager, id: Int) {
            val views = RemoteViews(context.packageName, R.layout.widget_routine)
            val openApp = PendingIntent.getActivity(
                context, 0, Intent(context, MainActivity::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
            )
            views.setOnClickPendingIntent(R.id.routineWidgetRoot, openApp)

            CoroutineScope(Dispatchers.IO).launch {
                val db = StudyDatabase.getDatabase(context)
                // Calendar.DAY_OF_WEEK is 1..7 (Sun..Sat); the Routine model
                // stores 0..6 with 0 == Sunday, so subtract one.
                val todayIdx = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1
                val nowMinutes = Calendar.getInstance().let {
                    it.get(Calendar.HOUR_OF_DAY) * 60 + it.get(Calendar.MINUTE)
                }
                val routines = runCatching { db.routineDao().getEnabledRoutines().first() }
                    .getOrDefault(emptyList())
                    .filter { it.repeatDays.contains(todayIdx) }
                    .sortedBy { it.hour * 60 + it.minute }

                val upcoming = routines.firstOrNull { it.hour * 60 + it.minute >= nowMinutes }
                    ?: routines.firstOrNull()

                if (upcoming == null) {
                    views.setTextViewText(R.id.tvRoutineWidgetTitle,
                        context.getString(R.string.widget_routine_empty))
                    views.setTextViewText(R.id.tvRoutineWidgetSubtitle, "")
                } else {
                    val start = String.format(Locale.US, "%02d:%02d", upcoming.hour, upcoming.minute)
                    val endMin = upcoming.hour * 60 + upcoming.minute + upcoming.durationMinutes
                    val end = String.format(Locale.US, "%02d:%02d", endMin / 60 % 24, endMin % 60)
                    val title = if (upcoming.title.isNotBlank()) upcoming.title else upcoming.subjectName
                    views.setTextViewText(R.id.tvRoutineWidgetTitle, title)
                    views.setTextViewText(R.id.tvRoutineWidgetSubtitle, "$start — $end")
                }
                manager.updateAppWidget(id, views)
            }
        }
    }
}
