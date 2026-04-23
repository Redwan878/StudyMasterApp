package com.porashona.studymaster.ui.settings

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.materialswitch.MaterialSwitch
import com.google.android.material.snackbar.Snackbar
import com.porashona.studymaster.R
import com.porashona.studymaster.StudyMasterApplication
import com.porashona.studymaster.data.backup.BackupManager
import com.porashona.studymaster.data.preferences.PreferencesManager
import com.porashona.studymaster.databinding.ActivitySettingsBinding
import com.porashona.studymaster.utils.LanguageManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Real settings screen — every Tier A option from the feature request.
 *
 * Groups:
 *  - Appearance  (theme / accent / font size / language)
 *  - Notifications (master + sound + vibration + per-channel reminders)
 *  - Focus & Zen (auto-block, strict, voice commands, daily goal)
 *  - Accessibility (high contrast, haptic feedback)
 *  - Data (backup export / import / clear all)
 *  - About (version + help/credits)
 *
 * Every toggle is two-way bound to `PreferencesManager` — reads via Flow,
 * writes via the setter in `lifecycleScope`. Theme / accent / font size
 * changes call `recreate()` so the user sees the change immediately.
 */
class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private val prefs: PreferencesManager by lazy {
        (application as StudyMasterApplication).preferencesManager
    }

    // File pickers for backup export / import.
    private val exportLauncher = registerForActivityResult(
        ActivityResultContracts.CreateDocument("application/json")
    ) { uri: Uri? -> if (uri != null) doExport(uri) }

    private val importLauncher = registerForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) { uri: Uri? -> if (uri != null) confirmAndImport(uri) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener { finish() }

        setupAppearance()
        setupNotifications()
        setupFocus()
        setupAccessibility()
        setupData()
        setupAbout()
    }

    // ============================ APPEARANCE ============================

    private fun setupAppearance() {
        // Theme
        observe(prefs.darkMode) { mode -> binding.tvCurrentTheme.text = themeLabel(mode) }
        binding.rowTheme.setOnClickListener {
            lifecycleScope.launch {
                val current = prefs.darkMode.first()
                val modes = listOf("light", "dark", "system", "amoled")
                val labels = arrayOf(
                    getString(R.string.theme_option_light),
                    getString(R.string.theme_option_dark),
                    getString(R.string.theme_option_system),
                    getString(R.string.theme_option_amoled),
                )
                val selected = modes.indexOf(current).let { if (it < 0) 2 else it }
                MaterialAlertDialogBuilder(this@SettingsActivity)
                    .setTitle(R.string.theme_settings)
                    .setSingleChoiceItems(labels, selected) { dialog, which ->
                        dialog.dismiss()
                        lifecycleScope.launch {
                            prefs.setDarkMode(modes[which])
                            applyThemeMode(modes[which])
                            recreate()
                        }
                    }
                    .show()
            }
        }

        // Accent color
        observe(prefs.accentColor) { /* swatch is themed via ?attr/colorPrimary */ }
        binding.rowAccent.setOnClickListener {
            lifecycleScope.launch {
                val current = prefs.accentColor.first()
                val options = accentChoices()
                val selected = options.indexOfFirst { it.second.equals(current, true) }
                    .coerceAtLeast(0)
                val labels = options.map { getString(it.first) }.toTypedArray()
                MaterialAlertDialogBuilder(this@SettingsActivity)
                    .setTitle(R.string.accent_color)
                    .setSingleChoiceItems(labels, selected) { dialog, which ->
                        dialog.dismiss()
                        lifecycleScope.launch {
                            prefs.setAccentColor(options[which].second)
                            recreate()
                        }
                    }
                    .show()
            }
        }

        // Font size
        observe(prefs.fontSize) { size -> binding.tvCurrentFontSize.text = fontSizeLabel(size) }
        binding.rowFontSize.setOnClickListener {
            lifecycleScope.launch {
                val current = prefs.fontSize.first()
                val sizes = listOf("small", "medium", "large")
                val labels = arrayOf(
                    getString(R.string.font_size_small),
                    getString(R.string.font_size_medium),
                    getString(R.string.font_size_large),
                )
                val selected = sizes.indexOf(current).let { if (it < 0) 1 else it }
                MaterialAlertDialogBuilder(this@SettingsActivity)
                    .setTitle(R.string.font_size)
                    .setSingleChoiceItems(labels, selected) { dialog, which ->
                        dialog.dismiss()
                        lifecycleScope.launch {
                            prefs.setFontSize(sizes[which])
                            recreate()
                        }
                    }
                    .show()
            }
        }

        // Language
        binding.tvCurrentLanguage.text =
            if (LanguageManager.isEnglish(this)) "English" else "বাংলা"
        binding.rowLanguage.setOnClickListener {
            val languages = arrayOf(
                getString(R.string.language_bangla),
                getString(R.string.language_english),
            )
            val currentLang = if (LanguageManager.isEnglish(this)) 1 else 0
            MaterialAlertDialogBuilder(this)
                .setTitle(R.string.select_language)
                .setSingleChoiceItems(languages, currentLang) { dialog, which ->
                    dialog.dismiss()
                    val code = if (which == 0) "bn" else "en"
                    LanguageManager.setLanguage(this, code)
                    recreate()
                }
                .show()
        }
    }

    private fun themeLabel(mode: String): String = getString(
        when (mode) {
            "light" -> R.string.theme_option_light
            "dark" -> R.string.theme_option_dark
            "amoled" -> R.string.theme_option_amoled
            else -> R.string.theme_option_system
        }
    )

    private fun fontSizeLabel(size: String): String = getString(
        when (size) {
            "small" -> R.string.font_size_small
            "large" -> R.string.font_size_large
            else -> R.string.font_size_medium
        }
    )

    /** (label res, hex color) */
    private fun accentChoices(): List<Pair<Int, String>> = listOf(
        R.string.accent_option_indigo to "#6C63FF",
        R.string.accent_option_teal to "#009688",
        R.string.accent_option_orange to "#FF7043",
        R.string.accent_option_pink to "#EC407A",
        R.string.accent_option_green to "#43A047",
    )

    private fun applyThemeMode(mode: String) {
        AppCompatDelegate.setDefaultNightMode(
            when (mode) {
                "light" -> AppCompatDelegate.MODE_NIGHT_NO
                "dark", "amoled" -> AppCompatDelegate.MODE_NIGHT_YES
                else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            }
        )
    }

    // ============================ NOTIFICATIONS ============================

    private fun setupNotifications() {
        bindSwitch(binding.switchNotifications, prefs.notificationEnabled) { prefs.setNotificationEnabled(it) }
        bindSwitch(binding.switchSound, prefs.soundEnabled) { prefs.setSoundEnabled(it) }
        bindSwitch(binding.switchVibration, prefs.vibrationEnabled) { prefs.setVibrationEnabled(it) }
        bindSwitch(binding.switchDailyReminder, prefs.dailyReminderEnabled) { enabled ->
            prefs.setDailyReminderEnabled(enabled)
            val hhmm = prefs.dailyReminderTime.first()
            if (enabled) {
                com.porashona.studymaster.utils.DailyReminderScheduler.schedule(applicationContext, hhmm)
            } else {
                com.porashona.studymaster.utils.DailyReminderScheduler.cancel(applicationContext)
            }
        }
        bindSwitch(binding.switchStreakReminder, prefs.streakReminderEnabled) { prefs.setStreakReminderEnabled(it) }
        bindSwitch(binding.switchBreakReminder, prefs.breakReminderEnabled) { prefs.setBreakReminderEnabled(it) }
        bindSwitch(binding.switchQuoteNotification, prefs.quoteNotificationEnabled) { prefs.setQuoteNotificationEnabled(it) }
    }

    // ============================ FOCUS ============================

    private fun setupFocus() {
        bindSwitch(binding.switchAutoBlock, prefs.autoBlockOnTimer) { prefs.setAutoBlockOnTimer(it) }
        bindSwitch(binding.switchStrictMode, prefs.strictModeEnabled) { prefs.setStrictModeEnabled(it) }
        bindSwitch(binding.switchVoiceCommands, prefs.voiceCommandsEnabled) { prefs.setVoiceCommandsEnabled(it) }

        observe(prefs.dailyGoalMinutes) { mins ->
            binding.tvDailyGoal.text = "$mins ${getString(R.string.minutes_suffix)}"
        }
        binding.rowDailyGoal.setOnClickListener {
            lifecycleScope.launch {
                val current = prefs.dailyGoalMinutes.first()
                val options = intArrayOf(15, 30, 45, 60, 90, 120, 150, 180, 240, 300)
                val labels = options.map { "$it ${getString(R.string.minutes_suffix)}" }.toTypedArray()
                val selected = options.indexOf(current).coerceAtLeast(0)
                MaterialAlertDialogBuilder(this@SettingsActivity)
                    .setTitle(R.string.pick_minutes)
                    .setSingleChoiceItems(labels, selected) { dialog, which ->
                        dialog.dismiss()
                        lifecycleScope.launch { prefs.setDailyGoalMinutes(options[which]) }
                    }
                    .show()
            }
        }
    }

    // ============================ ACCESSIBILITY ============================

    private fun setupAccessibility() {
        bindSwitch(binding.switchHighContrast, prefs.highContrastMode) { prefs.setHighContrastMode(it) }
        bindSwitch(binding.switchHapticFeedback, prefs.hapticFeedback) { prefs.setHapticFeedback(it) }
    }

    // ============================ DATA ============================

    private fun setupData() {
        binding.rowBackupExport.setOnClickListener {
            val stamp = SimpleDateFormat("yyyyMMdd-HHmm", Locale.US).format(Date())
            try {
                exportLauncher.launch("studymaster-backup-$stamp.json")
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(
                    this,
                    getString(R.string.backup_failed, getString(R.string.no_file_picker)),
                    Toast.LENGTH_LONG,
                ).show()
            }
        }
        binding.rowBackupImport.setOnClickListener {
            try {
                importLauncher.launch(arrayOf("application/json", "*/*"))
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(
                    this,
                    getString(R.string.backup_failed, getString(R.string.no_file_picker)),
                    Toast.LENGTH_LONG,
                ).show()
            }
        }
        binding.rowClearData.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle(R.string.setting_clear_data)
                .setMessage(R.string.setting_clear_data_confirm)
                .setNegativeButton(R.string.action_cancel, null)
                .setPositiveButton(R.string.action_clear) { _, _ -> doClearAll() }
                .show()
        }
    }

    private fun doExport(uri: Uri) {
        lifecycleScope.launch {
            try {
                val bytes = BackupManager.exportToUri(this@SettingsActivity, uri)
                Snackbar.make(
                    binding.root,
                    getString(R.string.backup_exported_bytes, bytes),
                    Snackbar.LENGTH_LONG,
                ).show()
            } catch (t: Throwable) {
                Snackbar.make(
                    binding.root,
                    getString(R.string.backup_failed, t.message ?: t.javaClass.simpleName),
                    Snackbar.LENGTH_LONG,
                ).show()
            }
        }
    }

    private fun confirmAndImport(uri: Uri) {
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.setting_backup_import)
            .setMessage(R.string.setting_clear_data_confirm)
            .setNegativeButton(R.string.action_cancel, null)
            .setPositiveButton(R.string.action_import) { _, _ -> doImport(uri) }
            .show()
    }

    private fun doImport(uri: Uri) {
        lifecycleScope.launch {
            try {
                val result = BackupManager.importFromUri(this@SettingsActivity, uri)
                Snackbar.make(
                    binding.root,
                    getString(
                        R.string.backup_imported_summary,
                        result.sessions, result.subjects, result.tasks, result.notes,
                    ),
                    Snackbar.LENGTH_LONG,
                ).show()
            } catch (t: Throwable) {
                Snackbar.make(
                    binding.root,
                    getString(R.string.backup_failed, t.message ?: t.javaClass.simpleName),
                    Snackbar.LENGTH_LONG,
                ).show()
            }
        }
    }

    private fun doClearAll() {
        lifecycleScope.launch {
            try {
                val db = (application as StudyMasterApplication).database
                db.studySessionDao().deleteAll()
                db.subjectDao().deleteAll()
                db.routineDao().deleteAll()
                db.achievementDao().deleteAll()
                db.goalDao().deleteAll()
                db.taskDao().deleteAll()
                db.noteDao().deleteAll()
                db.examDao().deleteAll()
                db.challengeDao().deleteAll()
                db.blockedAppDao().deleteAll()
                db.quoteDao().deleteAll()
                db.studyResourceDao().deleteAll()
                db.academicEventDao().deleteAll()
                prefs.clearAllPreferences()
                // Cancel every scheduled alarm — prefs are now default, but
                // the PendingIntents would otherwise keep firing forever.
                com.porashona.studymaster.utils.DailyReminderScheduler.cancel(applicationContext)
                Snackbar.make(binding.root, R.string.data_cleared, Snackbar.LENGTH_LONG).show()
            } catch (t: Throwable) {
                Snackbar.make(
                    binding.root,
                    getString(R.string.backup_failed, t.message ?: t.javaClass.simpleName),
                    Snackbar.LENGTH_LONG,
                ).show()
            }
        }
    }

    // ============================ ABOUT ============================

    private fun setupAbout() {
        val pkgInfo = packageManager.getPackageInfo(packageName, 0)
        binding.tvVersion.text = "${pkgInfo.versionName} (${pkgInfo.longVersionCode})"
        binding.rowAboutHelp.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle(R.string.about_help)
                .setMessage(R.string.about_help_body)
                .setPositiveButton(android.R.string.ok, null)
                .show()
        }
    }

    // ============================ HELPERS ============================

    private fun bindSwitch(
        switch: MaterialSwitch,
        source: Flow<Boolean>,
        setter: suspend (Boolean) -> Unit,
    ) {
        // Suppress listener fires caused by our own programmatic update.
        var suppress = false
        source
            .onEach { value ->
                suppress = true
                switch.isChecked = value
                suppress = false
            }
            .launchIn(lifecycleScope)
        switch.setOnCheckedChangeListener { _, isChecked ->
            if (suppress) return@setOnCheckedChangeListener
            lifecycleScope.launch { setter(isChecked) }
        }
    }

    private fun <T> observe(source: Flow<T>, block: (T) -> Unit) {
        source.onEach(block).launchIn(lifecycleScope)
    }
}
