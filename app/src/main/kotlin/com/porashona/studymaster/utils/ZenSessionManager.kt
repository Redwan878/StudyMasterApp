package com.porashona.studymaster.utils

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.provider.Settings

/**
 * Helpers for the "Zen Mode" focus session:
 *  - start/stop Do Not Disturb (priority filter) if the user granted us notification policy access
 *  - check whether that permission has been granted
 *  - build an intent to send the user to the settings page where they can grant it
 *
 * Keeping this in a single place so the service, the fragment and the overlay
 * all agree on how DND is toggled.
 */
object ZenSessionManager {

    fun isDndAccessGranted(context: Context): Boolean {
        val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
            ?: return false
        return nm.isNotificationPolicyAccessGranted
    }

    fun dndAccessSettingsIntent(): Intent =
        Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

    /**
     * Switch the device to INTERRUPTION_FILTER_PRIORITY so only priority
     * notifications (alarms, starred contacts) can break through during a Zen
     * session. Silently no-ops if the permission was not granted.
     */
    fun enableDnd(context: Context) {
        val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
            ?: return
        if (!nm.isNotificationPolicyAccessGranted) return
        runCatching {
            nm.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_PRIORITY)
        }
    }

    fun disableDnd(context: Context) {
        val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
            ?: return
        if (!nm.isNotificationPolicyAccessGranted) return
        runCatching {
            nm.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_ALL)
        }
    }

    /** Preset session lengths (minutes) shown as chips in the Zen Mode UI. */
    val DURATION_PRESETS_MINUTES = listOf(15, 25, 45, 60, 90, 120)
    const val DEFAULT_DURATION_MINUTES = 25
}
