package com.porashona.studymaster.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.porashona.studymaster.data.dao.AchievementDao
import com.porashona.studymaster.data.model.Achievement
import com.porashona.studymaster.data.preferences.PreferencesManager
import com.porashona.studymaster.ui.compose.navigation.StudyMasterApp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Compose-based main activity — the new launcher for StudyMasterApp.
 *
 * Responsibilities:
 * - Edge-to-edge display via [enableEdgeToEdge] + [WindowCompat]
 * - Request POST_NOTIFICATIONS permission on Android 13+
 * - Observe [Achievement] unlocks from Room and expose for snackbar
 * - Handle incoming intents (notification clicks, widget clicks)
 * - Delegates all rendering to the Compose tree via [StudyMasterApp]
 */
@AndroidEntryPoint
class ComposeMainActivity : ComponentActivity() {

    @Inject
    lateinit var preferencesManager: PreferencesManager

    @Inject
    lateinit var achievementDao: AchievementDao

    /**
     * Exposed so the Compose tree can observe freshly-unlocked achievements
     * and show a snackbar. Only the latest unlock is surfaced at a time;
     * once consumed the value resets to null.
     */
    val latestUnlockedAchievement = MutableStateFlow<Achievement?>(null)

    /** Tracks which achievement IDs have already been shown to the user. */
    private val shownAchievementIds = mutableSetOf<String>()

    /** Background scope for DB observations that outlive the Compose lifecycle. */
    private val activityScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    override fun onCreate(savedInstanceState: Bundle?) {
        // ── Install splash screen (API 31+; no-op on older) ──────────────
        installSplashScreen()

        super.onCreate(savedInstanceState)

        // ── Edge-to-edge: draw behind system bars ────────────────────────
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // ── Handle incoming intent extras before Compose tree mounts ─────
        handleIncomingIntent(intent)

        // ── Observe achievement unlocks in the background ────────────────
        observeAchievementUnlocks()

        // ── Compose content ──────────────────────────────────────────────
        setContent {
            // Notification permission request launcher (Android 13+)
            var permissionRequested by remember { mutableStateOf(false) }
            val permissionLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestPermission(),
                onResult = { granted ->
                    if (!granted) {
                        Toast.makeText(
                            this@ComposeMainActivity,
                            "নোটিফিকেশন অনুমতি দেওয়া হয়নি। সেটিংস থেকে সক্রিয় করুন।",
                            Toast.LENGTH_LONG,
                        ).show()
                    }
                },
            )

            // Auto-request notification permission after first frame
            LaunchedEffect(Unit) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && !permissionRequested) {
                    val status = ContextCompat.checkSelfPermission(
                        this@ComposeMainActivity,
                        Manifest.permission.POST_NOTIFICATIONS,
                    )
                    if (status != PackageManager.PERMISSION_GRANTED) {
                        permissionRequested = true
                        permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    }
                }
            }

            // ── Root composable ───────────────────────────────────────────
            StudyMasterApp(
                preferencesManager = preferencesManager,
            )
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Achievement observation
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Watches the achievement table for new unlocks and pushes the most
     * recent one to [latestUnlockedAchievement] so the UI can show a snackbar.
     * Uses an in-memory set of already-seen IDs to avoid re-showing on
     * configuration changes.
     */
    private fun observeAchievementUnlocks() {
        activityScope.launch {
            achievementDao.getUnlockedAchievements()
                .map { list ->
                    list
                        .filter { it.unlockedAt != null && it.id !in shownAchievementIds }
                        .maxByOrNull { it.unlockedAt!! }
                }
                .distinctUntilChanged()
                .collect { fresh ->
                    if (fresh != null) {
                        shownAchievementIds.add(fresh.id)
                        latestUnlockedAchievement.value = fresh
                    }
                }
        }
    }

    /**
     * Called by the UI after it has displayed the achievement snackbar.
     * Resets the flow so the same achievement won't re-trigger.
     */
    fun consumeLatestAchievement(): Achievement? {
        val a = latestUnlockedAchievement.value
        latestUnlockedAchievement.value = null
        return a
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Intent handling
    // ─────────────────────────────────────────────────────────────────────────

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleIncomingIntent(intent)
    }

    /**
     * Centralised intent handler for deep-links and notification taps.
     *
     * Supported extras:
     * - `EXTRA_NAVIGATE_TO`  → String route to navigate to inside the app
     * - `EXTRA_ROUTINE_ID`   → Long routine id (opens timer with subject pre-filled)
     * - `EXTRA_EXAM_ID`      → Long exam id (opens exam detail)
     * - `EXTRA_START_TIMER`  → Boolean — if true, navigate to timer
     */
    private fun handleIncomingIntent(intent: Intent?) {
        if (intent == null) return

        when (intent.action) {
            // Notification tap — extract extras and let the UI navigate
            ACTION_NOTIFICATION_TAP -> {
                val targetRoute = intent.getStringExtra(EXTRA_NAVIGATE_TO)
                if (!targetRoute.isNullOrBlank()) {
                    pendingNavigationRoute = targetRoute
                }
                val startTimer = intent.getBooleanExtra(EXTRA_START_TIMER, false)
                if (startTimer) {
                    pendingNavigationRoute = "timer"
                }
            }

            // Widget quick-start timer
            ACTION_WIDGET_START_TIMER -> {
                pendingNavigationRoute = "timer"
            }

            // Widget tap — open app at home (default behaviour)
            ACTION_WIDGET_OPEN_APP -> {
                // No special navigation; just launch
            }

            // Practice test reminder
            ACTION_PRACTICE_REMINDER -> {
                pendingNavigationRoute = "practice"
            }

            // Flashcard review reminder
            ACTION_FLASHCARD_REMINDER -> {
                pendingNavigationRoute = "flashcards"
            }
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Static helpers — read by the UI layer
    // ─────────────────────────────────────────────────────────────────────────

    companion object {
        // ── Intent actions ───────────────────────────────────────────────
        const val ACTION_NOTIFICATION_TAP =
            "com.porashona.studymaster.NOTIFICATION_TAP"
        const val ACTION_WIDGET_START_TIMER =
            "com.porashona.studymaster.WIDGET_START_TIMER"
        const val ACTION_WIDGET_OPEN_APP =
            "com.porashona.studymaster.WIDGET_OPEN_APP"
        const val ACTION_PRACTICE_REMINDER =
            "com.porashona.studymaster.PRACTICE_REMINDER"
        const val ACTION_FLASHCARD_REMINDER =
            "com.porashona.studymaster.FLASHCARD_REMINDER"

        // ── Intent extras ────────────────────────────────────────────────
        const val EXTRA_NAVIGATE_TO = "navigate_to"
        const val EXTRA_ROUTINE_ID = "routine_id"
        const val EXTRA_EXAM_ID = "exam_id"
        const val EXTRA_START_TIMER = "start_timer"

        /**
         * The route the UI should navigate to after it mounts.
         * Written by [handleIncomingIntent] on the main thread before
         * setContent, and consumed (and cleared) by the navigation graph.
         *
         * This bridge is necessary because the Compose navigation controller
         * doesn't exist yet when the Activity processes the intent.
         */
        @Volatile
        var pendingNavigationRoute: String? = null

        /**
         * Consume and return the pending navigation route (single-use).
         */
        fun consumePendingNavigation(): String? {
            val route = pendingNavigationRoute
            pendingNavigationRoute = null
            return route
        }
    }
}