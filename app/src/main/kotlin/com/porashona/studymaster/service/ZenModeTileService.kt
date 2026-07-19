package com.porashona.studymaster.service

import android.app.PendingIntent
import android.content.Intent
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import com.porashona.studymaster.StudyMasterApplication
import com.porashona.studymaster.data.preferences.PreferencesManager
import com.porashona.studymaster.ui.ComposeMainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/**
 * Quick Settings tile for one-tap Zen Mode activation.
 *
 * When toggled ON:
 * - Sets [PreferencesManager.zenSessionEndTime] to `now + default duration`
 * - Updates the tile state to active
 *
 * When toggled OFF:
 * - Clears the zen session end time
 * - Updates the tile state to inactive
 *
 * Tapping the tile also opens [ComposeMainActivity] so the user can see
 * the full Zen Mode screen with a timer.
 */
class ZenModeTileService : TileService() {

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onStartListening() {
        super.onStartListening()
        serviceScope.launch {
            val prefs = (application as? StudyMasterApplication)?.preferencesManager
                ?: return@launch
            val endTime = prefs.zenSessionEndTime.first()
            val isActive = endTime > System.currentTimeMillis()
            qsTile?.state = if (isActive) Tile.STATE_ACTIVE else Tile.STATE_INACTIVE
            qsTile?.updateTile()
        }
    }

    override fun onClick() {
        super.onClick()
        serviceScope.launch {
            val prefs = (application as? StudyMasterApplication)?.preferencesManager
                ?: return@launch
            val endTime = prefs.zenSessionEndTime.first()
            val isActive = endTime > System.currentTimeMillis()

            if (isActive) {
                // Deactivate
                prefs.setZenSessionEndTime(0L)
                qsTile?.state = Tile.STATE_INACTIVE
            } else {
                // Activate with default duration (25 min)
                val durationMinutes = prefs.zenLastDurationMinutes.first()
                val newEndTime = System.currentTimeMillis() + durationMinutes * 60_000L
                prefs.setZenSessionEndTime(newEndTime)
                prefs.setZenEnableDnd(true)
                qsTile?.state = Tile.STATE_ACTIVE
            }
            qsTile?.updateTile()

            // Open the app at the timer/Zen Mode screen
            val intent = Intent(this@ZenModeTileService, ComposeMainActivity::class.java).apply {
                action = ComposeMainActivity.ACTION_WIDGET_START_TIMER
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            val pendingIntent = PendingIntent.getActivity(
                this@ZenModeTileService,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
            )
            startActivityAndCollapse(pendingIntent)
        }
    }

    override fun onStopListening() {
        super.onStopListening()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}