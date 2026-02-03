package com.porashona.studymaster.service

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Intent
import android.content.pm.PackageManager
import android.view.accessibility.AccessibilityEvent
import com.porashona.studymaster.StudyMasterApplication
import com.porashona.studymaster.data.model.BlockedApp
import com.porashona.studymaster.ui.blocker.BlockOverlayActivity
import com.porashona.studymaster.utils.RootUtils
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first

class AppBlockerAccessibilityService : AccessibilityService() {

    private val serviceScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private var blockedPackages: Set<String> = emptySet()
    private var isBlockingEnabled = false
    private var useRootBlocking = false
    private var lastBlockedPackage: String? = null
    private var lastBlockTime: Long = 0

    companion object {
        var instance: AppBlockerAccessibilityService? = null
            private set

        var isServiceRunning = false
            private set

        private const val BLOCK_COOLDOWN = 1000L // 1 second cooldown
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        isServiceRunning = true
        loadBlockedApps()
        loadSettings()
    }

    override fun onServiceConnected() {
        super.onServiceConnected()

        val info = AccessibilityServiceInfo().apply {
            eventTypes = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED or
                    AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED
            feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC
            flags = AccessibilityServiceInfo.FLAG_INCLUDE_NOT_IMPORTANT_VIEWS or
                    AccessibilityServiceInfo.FLAG_RETRIEVE_INTERACTIVE_WINDOWS
            notificationTimeout = 100
        }
        serviceInfo = info
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (!isBlockingEnabled || event == null) return

        val packageName = event.packageName?.toString() ?: return

        // Don't block our own app or system UI
        if (packageName == this.packageName ||
            packageName == "com.android.systemui" ||
            packageName == "com.android.launcher" ||
            packageName.startsWith("com.android.launcher")) {
            return
        }

        // Check cooldown to prevent rapid blocking
        val currentTime = System.currentTimeMillis()
        if (packageName == lastBlockedPackage &&
            currentTime - lastBlockTime < BLOCK_COOLDOWN) {
            return
        }

        if (blockedPackages.contains(packageName)) {
            lastBlockedPackage = packageName
            lastBlockTime = currentTime
            blockApp(packageName)
        }
    }

    override fun onInterrupt() {
        // Service interrupted
    }

    override fun onDestroy() {
        super.onDestroy()
        instance = null
        isServiceRunning = false
        serviceScope.cancel()
    }

    private fun blockApp(packageName: String) {
        serviceScope.launch {
            // Record block attempt
            recordBlockAttempt(packageName)

            if (useRootBlocking) {
                // Use root to force stop the app
                RootUtils.forceStopApp(packageName)
            }

            // Show block overlay
            showBlockOverlay(packageName)

            // Bring our app to front
            performGlobalAction(GLOBAL_ACTION_HOME)
        }
    }

    private fun showBlockOverlay(packageName: String) {
        val appName = getAppName(packageName)

        val intent = Intent(this, BlockOverlayActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                    Intent.FLAG_ACTIVITY_CLEAR_TOP or
                    Intent.FLAG_ACTIVITY_SINGLE_TOP
            putExtra(BlockOverlayActivity.EXTRA_PACKAGE_NAME, packageName)
            putExtra(BlockOverlayActivity.EXTRA_APP_NAME, appName)
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

    private fun recordBlockAttempt(packageName: String) {
        serviceScope.launch {
            try {
                val app = (application as StudyMasterApplication)
                val blockedAppDao = app.database.blockedAppDao()
                blockedAppDao.incrementBlockAttempt(packageName)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun loadBlockedApps() {
        serviceScope.launch {
            try {
                val app = (application as StudyMasterApplication)
                val blockedAppDao = app.database.blockedAppDao()

                blockedAppDao.getActiveBlockedApps().collect { apps ->
                    blockedPackages = apps.map { it.packageName }.toSet()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun loadSettings() {
        serviceScope.launch {
            try {
                val app = (application as StudyMasterApplication)
                val prefs = app.preferencesManager

                // Collect blocking enabled state
                launch {
                    prefs.appBlockerEnabled.collect { enabled ->
                        isBlockingEnabled = enabled
                    }
                }

                // Collect root blocking preference
                launch {
                    prefs.useRootBlocking.collect { useRoot ->
                        useRootBlocking = useRoot
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Public methods to control blocking
    fun enableBlocking() {
        isBlockingEnabled = true
    }

    fun disableBlocking() {
        isBlockingEnabled = false
    }

    fun updateBlockedApps(packages: Set<String>) {
        blockedPackages = packages
    }

    fun isBlocking(): Boolean = isBlockingEnabled
}