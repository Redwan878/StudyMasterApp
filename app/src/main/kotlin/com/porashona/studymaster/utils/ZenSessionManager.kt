package com.porashona.studymaster.utils

/**
 * Constants for the Zen Mode focus session. The app no longer toggles Do Not
 * Disturb — we leave system notifications to the user — so this object is
 * now just a thin holder for the duration presets shown as chips in the UI.
 */
object ZenSessionManager {
    /** Preset session lengths (minutes) shown as chips in the Zen Mode UI. */
    val DURATION_PRESETS_MINUTES = listOf(15, 25, 45, 60, 90, 120)
    const val DEFAULT_DURATION_MINUTES = 25
}
