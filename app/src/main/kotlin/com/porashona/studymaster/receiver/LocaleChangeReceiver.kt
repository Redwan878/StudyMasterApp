package com.porashona.studymaster.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * Handles `ACTION_LOCALE_CHANGED` so the app can refresh any cached locale state
 * when the user flips the device language. Declared in the manifest; without
 * this class the broadcast would fail to dispatch and crash the app at first
 * locale change.
 *
 * Most locale propagation is handled automatically by the framework — this
 * receiver is intentionally lightweight.
 */
class LocaleChangeReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != Intent.ACTION_LOCALE_CHANGED) return
        // No-op: activities re-read resources automatically after a locale change.
    }
}
