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
 * Widget with three quick-start buttons (25 / 50 / focus) that boot the
 * timer directly via [TimerService.ACTION_QUICK_START].
 */
class QuickStartWidget : AppWidgetProvider() {

    override fun onUpdate(context: Context, manager: AppWidgetManager, ids: IntArray) {
        ids.forEach { render(context, manager, it) }
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        val minutes = intent.getIntExtra(EXTRA_MINUTES, -1)
        if (intent.action == ACTION_QUICK_START && minutes > 0) {
            runCatching {
                context.startService(
                    Intent(context, TimerService::class.java).apply {
                        action = TimerService.ACTION_QUICK_START
                        putExtra(TimerService.EXTRA_DURATION_MINUTES, minutes)
                    }
                )
            }
        }
    }

    companion object {
        const val ACTION_QUICK_START = "com.porashona.studymaster.widget.QUICK_START"
        const val EXTRA_MINUTES = "minutes"

        private fun render(context: Context, manager: AppWidgetManager, id: Int) {
            val views = RemoteViews(context.packageName, R.layout.widget_quick_start)
            val openApp = PendingIntent.getActivity(
                context, 0, Intent(context, MainActivity::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
            )
            views.setOnClickPendingIntent(R.id.quickStartWidgetRoot, openApp)

            listOf(
                R.id.btnQuickStart25 to 25,
                R.id.btnQuickStart50 to 50,
                R.id.btnQuickStartFocus to 90,
            ).forEachIndexed { idx, (viewId, minutes) ->
                val pi = PendingIntent.getBroadcast(
                    context, 100 + idx,
                    Intent(context, QuickStartWidget::class.java).apply {
                        action = ACTION_QUICK_START
                        putExtra(EXTRA_MINUTES, minutes)
                    },
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
                )
                views.setOnClickPendingIntent(viewId, pi)
            }

            manager.updateAppWidget(id, views)
        }
    }
}
