package com.porashona.studymaster.utils

import android.app.Activity
import android.app.LocaleManager
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.LocaleList
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import java.util.Locale

object LanguageManager {

    const val LANGUAGE_BANGLA = "bn"
    const val LANGUAGE_ENGLISH = "en"

    private const val PREF_NAME = "language_pref"
    private const val KEY_LANGUAGE = "selected_language"

    /**
     * Get current language code
     */
    fun getLanguage(context: Context): String {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getString(KEY_LANGUAGE, LANGUAGE_BANGLA) ?: LANGUAGE_BANGLA
    }

    /**
     * Set language and apply changes
     */
    fun setLanguage(context: Context, languageCode: String) {
        // Save preference
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(KEY_LANGUAGE, languageCode).apply()

        // Apply language based on API level
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Android 13+ uses per-app language
            val localeManager = context.getSystemService(LocaleManager::class.java)
            localeManager?.applicationLocales = LocaleList.forLanguageTags(languageCode)
        } else {
            // For older versions, use AppCompatDelegate
            val localeList = LocaleListCompat.forLanguageTags(languageCode)
            AppCompatDelegate.setApplicationLocales(localeList)
        }
    }

    /**
     * Apply saved language to context (for activities)
     */
    fun applyLanguage(context: Context): Context {
        val languageCode = getLanguage(context)
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)
        config.setLayoutDirection(locale)

        return context.createConfigurationContext(config)
    }

    /**
     * Wrap context with current language configuration
     */
    fun wrapContext(context: Context): Context {
        val languageCode = getLanguage(context)
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)

        return context.createConfigurationContext(config)
    }

    /**
     * Check if current language is Bangla
     */
    fun isBangla(context: Context): Boolean {
        return getLanguage(context) == LANGUAGE_BANGLA
    }

    /**
     * Check if current language is English
     */
    fun isEnglish(context: Context): Boolean {
        return getLanguage(context) == LANGUAGE_ENGLISH
    }

    /**
     * Get display name for language code
     */
    fun getLanguageDisplayName(languageCode: String): String {
        return when (languageCode) {
            LANGUAGE_BANGLA -> "বাংলা"
            LANGUAGE_ENGLISH -> "English"
            else -> "বাংলা"
        }
    }

    /**
     * Restart activity to apply language change
     */
    fun restartActivity(activity: Activity) {
        val intent = activity.intent
        activity.finish()
        activity.startActivity(intent)
        activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    /**
     * Restart app completely
     */
    fun restartApp(context: Context) {
        val packageManager = context.packageManager
        val intent = packageManager.getLaunchIntentForPackage(context.packageName)
        intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(intent)
        if (context is Activity) {
            context.finish()
        }
        Runtime.getRuntime().exit(0)
    }
}