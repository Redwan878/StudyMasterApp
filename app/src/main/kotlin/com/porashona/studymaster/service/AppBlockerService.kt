package com.porashona.studymaster.service

import android.app.*
import android.app.usage.UsageEvents
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.porashona.studymaster.R
import com.porashona.studymaster.StudyMasterApplication
import com.porashona.studymaster.ui.MainActivity
import com.porashona.studymaster.ui.blocker.BlockOverlayActivity
import com.porashona.studymaster.utils.RootUtils
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import java.util.concurrent.TimeUnit

class AppBlockerService : Service() {

    private val serviceScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private var monitoringJob: Job? = null
    private var blockedPackages: Set<String> = emptySet()
    private var isBlockingActive = false
    private var useRootBlocking = false
    private var strictModeEnabled = false
    private var sessionEndTime: Long = 0

    companion object {
        const val ACTION_START_BLOCKING = "com.porashona.studymaster.START_BLOCKING"
        const val ACTION_STOP_BLOCKING = "com.porashona.studymaster.STOP_BLOCKING"
        const val ACTION_UPDATE_BLOCKED_APPS = "com.porashona.studymaster.UPDATE_BLOCKED"
        const val EXTRA_SESSION_DURATION = "session_duration"
        const val EXTRA_BLOCKED_PACKAGES = "blocked_packages"

        private const val NOTIFICATION_ID = 3001
        private const val CHECK_INTERVAL = 500L // Check every 500ms

        var isRunning = false
            private set
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        isRunning = true
        loadSettings()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START_BLOCKING -> {
                val duration = intent.getLongExtra(EXTRA_SESSION_DURATION, 25 * 60 * 1000L)
                val packages = intent.getStringArrayListExtra(EXTRA_BLOCKED_PACKAGES)
                packages?.let { blockedPackages = it.toSet() }
                sessionEndTime = System.currentTimeMillis() + duration
                startBlocking()
            }
            ACTION_STOP_BLOCKING -> {
                if (!strictModeEnabled || System.currentTimeMillis() >= sessionEndTime) {
                    stopBlocking()
                }
            }
            ACTION_UPDATE_BLOCKED_APPS -> {
                val packages = intent.getStringArrayListExtra(EXTRA_BLOCKED_PACKAGES)
                packages?.let { blockedPackages = it.toSet() }
            }
        }
        return START_STICKY
    }

    private fun startBlocking() {
        isBlockingActive = true
        startForeground(NOTIFICATION_ID, createNotification())
        startMonitoring()

        // Update accessibility service if available
        AppBlockerAccessibilityService.instance?.updateBlockedApps(blockedPackages)
        AppBlockerAccessibilityService.instance?.enableBlocking()
    }

    private fun stopBlocking() {
        isBlockingActive = false
        monitoringJob?.cancel()

        // Disable accessibility service blocking
        AppBlockerAccessibilityService.instance?.disableBlocking()

        // Re-enable any disabled apps if using root
        if (useRootBlocking) {
            serviceScope.launch {
                blockedPackages.forEach { packageName ->
                    RootUtils.enableApp(packageName)
                    RootUtils.unhideApp(packageName)
                }
            }
        }

        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    private fun startMonitoring() {
        monitoringJob?.cancel()
        monitoringJob = serviceScope.launch {
            while (isBlockingActive) {
                checkForegroundApp()
                delay(CHECK_INTERVAL)

                // Check if session ended
                if (sessionEndTime > 0 && System.currentTimeMillis() >= sessionEndTime) {
                    stopBlocking()
                    break
                }
            }
        }
    }

    private suspend fun checkForegroundApp() {
        val foregroundApp = getForegroundApp() ?: return

        // Don't block our own app or system
        if (foregroundApp == packageName ||
            foregroundApp == "com.android.systemui" ||
            foregroundApp.startsWith("com.android.launcher")) {
            return
        }

        if (blockedPackages.contains(foregroundApp)) {
            blockApp(foregroundApp)
        }
    }

    private fun getForegroundApp(): String? {
        val usageStatsManager = getSystemService(Context.USAGE_STATS_SERVICE) as? UsageStatsManager
            ?: return null

        val endTime = System.currentTimeMillis()
        val startTime = endTime - TimeUnit.SECONDS.toMillis(5)

        val usageEvents = usageStatsManager.queryEvents(startTime, endTime)
        var lastEvent: UsageEvents.Event? = null

        while (usageEvents.hasNextEvent()) {
            val event = UsageEvents.Event()
            usageEvents.getNextEvent(event)

            if (event.eventType == UsageEvents.Event.ACTIVITY_RESUMED ||
                event.eventType == UsageEvents.Event.MOVE_TO_FOREGROUND) {
                lastEvent = event
            }
        }

        return lastEvent?.packageName
    }

    private suspend fun blockApp(packageName: String) {
        // Record block attempt
        recordBlockAttempt(packageName)

        if (useRootBlocking) {
            // Force stop app using root
            RootUtils.forceStopApp(packageName)
        }

        // Show block overlay
        showBlockOverlay(packageName)
    }

    private fun showBlockOverlay(packageName: String) {
        val appName = getAppName(packageName)
        val timeRemaining = if (sessionEndTime > 0) {
            sessionEndTime - System.currentTimeMillis()
        } else 0L

        val intent = Intent(this, BlockOverlayActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                    Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra(BlockOverlayActivity.EXTRA_PACKAGE_NAME, packageName)
            putExtra(BlockOverlayActivity.EXTRA_APP_NAME, appName)
            putExtra(BlockOverlayActivity.EXTRA_TIME_REMAINING, timeRemaining)
        }
        startActivity(intent)
    }

    private fun getAppName(packageName: String): String {
        return try {
            val pm = packageManager
            val appInfo = pm.getApplicationInfo(packageName, 0)
            pm.getApplicationLabel(appInfo).toString()
        } catch (e: PackageManager.NameNotFoundException) {
            packageName
        }
    }

    private suspend fun recordBlockAttempt(packageName: String) {
        try {
            val app = (application as StudyMasterApplication)
            val blockedAppDao = app.database.blockedAppDao()
            blockedAppDao.incrementBlockAttempt(packageName)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun loadSettings() {
        serviceScope.launch {
            try {
                val app = (application as StudyMasterApplication)
                val prefs = app.preferencesManager

                launch {
                    prefs.useRootBlocking.collect { useRoot ->
                        useRootBlocking = useRoot
                    }
                }

                launch {
                    prefs.strictModeEnabled.collect { strict ->
                        strictModeEnabled = strict
                    }
                }

                // Load blocked apps
                launch {
                    app.database.blockedAppDao().getActiveBlockedApps().collect { apps ->
                        if (blockedPackages.isEmpty()) {
                            blockedPackages = apps.map { it.packageName }.toSet()
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun createNotification(): Notification {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val stopIntent = Intent(this, AppBlockerService::class.java).apply {
            action = ACTION_STOP_BLOCKING
        }
        val stopPendingIntent = PendingIntent.getService(
            this, 1, stopIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(this, StudyMasterApplication.ALERT_CHANNEL_ID)
            .setContentTitle(getString(R.string.blocker_enabled))
            .setContentText(getString(R.string.blocking_active))
            .setSmallIcon(R.drawable.ic_blocker)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .apply {
                if (!strictModeEnabled) {
                    addAction(R.drawable.ic_stop, getString(R.string.reset), stopPendingIntent)
                }
            }
            .build()
    }

    override fun onDestroy() {
        super.onDestroy()
        isRunning = false
        serviceScope.cancel()
    }
}