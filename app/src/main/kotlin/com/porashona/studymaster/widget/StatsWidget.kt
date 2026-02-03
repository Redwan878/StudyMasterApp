package com.porashona.studymaster.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import com.porashona.studymaster.R
import com.porashona.studymaster.data.database.StudyDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class StatsWidget : AppWidgetProvider() {
    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    private fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
        val views = RemoteViews(context.packageName, R.layout.widget_stats)

        // Fetch data asynchronously
        CoroutineScope(Dispatchers.IO).launch {
            val db = StudyDatabase.getDatabase(context)
            val todayTime = db.studySessionDao().getTotalStudyTime().first() ?: 0L
            val hours = todayTime / 3600
            val minutes = (todayTime % 3600) / 60

            // Update UI on main thread logic is handled by RemoteViews automatically
            views.setTextViewText(R.id.tvWidgetTime, "${hours}h ${minutes}m")
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}