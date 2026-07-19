package com.porashona.studymaster.widget

import android.appwidget.AppWidgetProvider
import android.appwidget.AppWidgetManager
import android.content.Context
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.glance.appwidget.widget
import androidx.glance.appwidget.widgetSurface
import com.porashona.studymaster.R

class StudyMasterWidgetProvider : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // Update each widget instance
        appWidgetIds.forEach { widgetId ->
            updateWidget(context, appWidgetManager, widgetId)
        }
    }

    override fun onEnabled(context: Context) {
        super.onEnabled(context)
        // Called when the first widget is created
    }

    override fun onDisabled(context: Context) {
        super.onDisabled(context)
        // Called when the last widget is removed
    }

    fun updateWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        widgetId: Int
    ) {
        // Get widget data
        val widgetData = getWidgetData(context)

        // Create RemoteViews based on widget type/size
        val remoteViews = when (getWidgetSize(widgetId)) {
            WidgetSize.SMALL -> createSmallWidgetViews(context, widgetData)
            WidgetSize.MEDIUM -> createMediumWidgetViews(context, widgetData)
            WidgetSize.LARGE -> createLargeWidgetViews(context, widgetData)
        }

        // Apply tap-to-focus functionality
        setupTapToFocus(context, remoteViews, widgetId)

        // Update the widget
        appWidgetManager.updateAppWidget(widgetId, remoteViews)
    }

    private fun createSmallWidgetViews(
        context: Context,
        widgetData: WidgetData
    ): RemoteViews {
        return RemoteViews(context.packageName, R.layout.widget_small)
    }

    private fun createMediumWidgetViews(
        context: Context,
        widgetData: WidgetData
    ): RemoteViews {
        return RemoteViews(context.packageName, R.layout.widget_compose_stats)
    }

    private fun createLargeWidgetViews(
        context: Context,
        widgetData: WidgetData
    ): RemoteViews {
        return RemoteViews(context.packageName, R.layout.widget_large)
    }

    private fun setupTapToFocus(
        context: Context,
        remoteViews: RemoteViews,
        widgetId: Int
    ) {
        // Setup tap-to-focus functionality
        remoteViews.setOnClickPendingIntent(
            R.id.widget_root,
            getPendingIntentForTapToFocus(context, widgetId)
        )
    }

    private fun getPendingIntentForTapToFocus(
        context: Context,
        widgetId: Int
    ): android.app.PendingIntent {
        val intent = Intent(context, WidgetTapActivity::class.java).apply {
            putExtra("widget_id", widgetId)
            action = "TOGGLE_FOCUS"
        }
        return android.app.PendingIntent.getActivity(
            context,
            widgetId,
            intent,
            android.app.PendingIntent.FLAG_UPDATE_CURRENT or android.app.PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun getWidgetSize(widgetId: Int): WidgetSize {
        // Determine widget size based on widget ID
        // This could be based on the widget configuration or layout used
        return WidgetSize.MEDIUM // Default to medium size
    }

    private fun getWidgetData(context: Context): WidgetData {
        // Get real-time widget data
        return WidgetData(
            todayStudyTime = getTodaysStudyTime(context),
            dailyGoal = getDailyGoal(context),
            currentStreak = getCurrentStreak(context),
            nextRoutine = getNextRoutine(context),
            focusScore = getFocusScore(context),
            isTimerRunning = isTimerRunning(context),
            remainingTime = getRemainingTime(context)
        )
    }
}

class WidgetTapActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val widgetId = intent.getIntExtra("widget_id", 0)
        val action = intent.action

        when (action) {
            "TOGGLE_FOCUS" -> {
                // Toggle focus mode through widget
                toggleFocusMode()
                updateWidgets()
            }
            "OPEN_APP" -> {
                // Open main app
                openMainApp()
            }
        }

        finish() // Close widget tap activity
    }

    private fun toggleFocusMode() {
        // Toggle focus mode via shared preferences
        val prefs = getSharedPreferences("study_prefs", Context.MODE_PRIVATE)
        val isFocusMode = prefs.getBoolean("focus_mode_enabled", false)

        prefs.edit()
            .putBoolean("focus_mode_enabled", !isFocusMode)
            .apply()

        // Send broadcast to notify other components
        LocalBroadcastManager.getInstance(this)
            .sendBroadcast(Intent("FOCUS_MODE_TOGGLE"))
    }

    private fun updateWidgets() {
        // Update all widgets
        AppWidgetManager.getInstance(this)
            .getAppWidgetIds(ComponentName(this, StudyMasterWidgetProvider::class.java))
            .forEach { widgetId ->
                StudyMasterWidgetProvider().updateWidget(this, AppWidgetManager.getInstance(this), widgetId)
            }
    }

    private fun openMainApp() {
        // Open main app
        val intent = packageManager.getLaunchIntentForPackage(packageName)
        startActivity(intent)
    }
}

data class WidgetData(
    val todayStudyTime: String = "0h 0m",
    val dailyGoal: String = "2h 0m",
    val currentStreak: Int = 0,
    val nextRoutine: String = "—",
    val focusScore: Int = 0,
    val isTimerRunning: Boolean = false,
    val remainingTime: String = "25:00"
)

enum class WidgetSize {
    SMALL,
    MEDIUM,
    LARGE
}

class WidgetRemoteViewsFactory : RemoteViewsService.RemoteViewsFactory {
    private var context: Context? = null
    private var widgetData: WidgetData = WidgetData()

    override fun onCreate(context: Context) {
        this.context = context
    }

    override fun onDataSetChanged() {
        // Refresh data
        context?.let {
            widgetData = getWidgetData(it)
        }
    }

    override fun getCount(): Int = 1

    override fun getViewAt(position: Int): RemoteViews {
        val remoteViews = RemoteViews(context?.packageName, R.layout.widget_item)

        // Set text values
        remoteViews.setTextViewText(R.id.tvWidgetStudyTime, widgetData.todayStudyTime)
        remoteViews.setTextViewText(R.id.tvWidgetDailyGoal, widgetData.dailyGoal)
        remoteViews.setTextViewText(R.id.tvWidgetStreak, widgetData.currentStreak.toString())
        remoteViews.setTextViewText(R.id.tvWidgetRoutine, widgetData.nextRoutine)

        // Set button text and click listener
        remoteViews.setTextViewText(R.id/btnWidgetStartTimer, if (widgetData.isTimerRunning) "暂停" else "开始")

        // Set remaining time
        remoteViews.setTextViewText(R.id.tvWidgetRemainingTime, widgetData.remainingTime)

        return remoteViews
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount(): Int = 1

    override fun onDestroy() {
        this.context = null
    }

    private fun getWidgetData(context: Context): WidgetData {
        // TODO: Implement data fetching from repository
        return WidgetData(
            todayStudyTime = "1h 30m",
            dailyGoal = "2h 0m",
            currentStreak = 7,
            nextRoutine = "English Reading",
            focusScore = 85,
            isTimerRunning = true,
            remainingTime = "12:30"
        )
    }
}

// Broadcast receiver to listen for data changes
class WidgetDataUpdateReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "STUDY_DATA_UPDATED") {
            // Update all widgets
            AppWidgetManager.getInstance(context)
                .getAppWidgetIds(ComponentName(context, StudyMasterWidgetProvider::class.java))
                .forEach { widgetId ->
                    StudyMasterWidgetProvider().updateWidget(
                        context,
                        AppWidgetManager.getInstance(context),
                        widgetId
                    )
                }
        }
    }
}