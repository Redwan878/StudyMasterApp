package com.porashona.studymaster.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.porashona.studymaster.R
import com.porashona.studymaster.service.TimerService
import com.porashona.studymaster.ui.MainActivity

/**
 * Home-screen widget that shows the running timer state and lets the user
 * start/pause/stop without opening the app. Mirrors the state that
 * [TimerService] broadcasts via its notification.
 */
class TimerWidget : AppWidgetProvider() {

    override fun onUpdate(context: Context, manager: AppWidgetManager, ids: IntArray) {
        ids.forEach { render(context, manager, it) }
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        when (intent.action) {
            ACTION_TOGGLE -> forwardToService(context, TimerService.ACTION_TOGGLE)
            ACTION_STOP -> forwardToService(context, TimerService.ACTION_STOP)
        }
    }

    private fun forwardToService(context: Context, action: String) {
        runCatching {
            context.startService(
                Intent(context, TimerService::class.java).apply { this.action = action }
            )
        }
        requestUpdate(context)
    }

    companion object {
        const val ACTION_TOGGLE = "com.porashona.studymaster.widget.TIMER_TOGGLE"
        const val ACTION_STOP = "com.porashona.studymaster.widget.TIMER_STOP"

        fun requestUpdate(context: Context) {
            val manager = AppWidgetManager.getInstance(context)
            val ids = manager.getAppWidgetIds(ComponentName(context, TimerWidget::class.java))
            if (ids.isEmpty()) return
            manager.notifyAppWidgetViewDataChanged(ids, R.id.tvTimerWidgetTime)
            val intent = Intent(context, TimerWidget::class.java).apply {
                action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
                putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
            }
            context.sendBroadcast(intent)
        }

        private fun render(context: Context, manager: AppWidgetManager, id: Int) {
            val views = RemoteViews(context.packageName, R.layout.widget_timer)

            val openApp = PendingIntent.getActivity(
                context, 0, Intent(context, MainActivity::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
            )
            views.setOnClickPendingIntent(R.id.timerWidgetRoot, openApp)

            val toggle = PendingIntent.getBroadcast(
                context, 1,
                Intent(context, TimerWidget::class.java).setAction(ACTION_TOGGLE),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
            )
            views.setOnClickPendingIntent(R.id.btnTimerWidgetToggle, toggle)

            val stop = PendingIntent.getBroadcast(
                context, 2,
                Intent(context, TimerWidget::class.java).setAction(ACTION_STOP),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
            )
            views.setOnClickPendingIntent(R.id.btnTimerWidgetStop, stop)

            manager.updateAppWidget(id, views)
        }
    }
}
