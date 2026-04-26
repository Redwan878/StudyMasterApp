package com.porashona.studymaster.utils

import android.content.Context
import android.content.res.Configuration

/**
 * Applies accessibility preferences (font scale, high contrast) before any
 * view is inflated. Activities should call [wrap] from their
 * attachBaseContext and [shouldUseHighContrast] before super.onCreate to
 * pick the right theme.
 */
object AppearanceUtils {

    private const val PREFS = "appearance_sync"
    private const val KEY_FONT_SIZE = "font_size"
    private const val KEY_HIGH_CONTRAST = "high_contrast"

    fun cacheFontSize(context: Context, size: String) {
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit().putString(KEY_FONT_SIZE, size).apply()
    }

    fun cacheHighContrast(context: Context, enabled: Boolean) {
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit().putBoolean(KEY_HIGH_CONTRAST, enabled).apply()
    }

    fun shouldUseHighContrast(context: Context): Boolean =
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .getBoolean(KEY_HIGH_CONTRAST, false)

    /** Returns a context with the user-picked fontScale applied. */
    fun wrap(base: Context): Context {
        val size = base.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .getString(KEY_FONT_SIZE, "medium") ?: "medium"
        val scale = when (size) {
            "small" -> 0.85f
            "large" -> 1.20f
            else -> 1.0f
        }
        val current = base.resources.configuration
        if (current.fontScale == scale) return base
        val config = Configuration(current).apply { fontScale = scale }
        return base.createConfigurationContext(config)
    }
}
