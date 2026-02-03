package com.porashona.studymaster.ui.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.porashona.studymaster.R
import com.porashona.studymaster.databinding.ActivitySettingsBinding
import com.porashona.studymaster.utils.LanguageManager

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupLanguageSettings()
        setupThemeSettings()

        binding.btnBack.setOnClickListener { finish() }
    }

    private fun setupLanguageSettings() {
        binding.cardLanguage.setOnClickListener {
            val languages = arrayOf(getString(R.string.language_bangla), getString(R.string.language_english))
            val currentLang = if (LanguageManager.isEnglish(this)) 1 else 0

            MaterialAlertDialogBuilder(this)
                .setTitle(getString(R.string.select_language))
                .setSingleChoiceItems(languages, currentLang) { dialog, which ->
                    val code = if (which == 0) "bn" else "en"
                    LanguageManager.setLanguage(this, code)
                    dialog.dismiss()
                    recreate() // Restart activity to apply
                }
                .show()
        }

        binding.tvCurrentLanguage.text = if (LanguageManager.isEnglish(this)) "English" else "বাংলা"
    }

    private fun setupThemeSettings() {
        binding.cardTheme.setOnClickListener {
            val themes = arrayOf(getString(R.string.light_mode), getString(R.string.dark_mode), getString(R.string.system_default))
            MaterialAlertDialogBuilder(this)
                .setTitle(getString(R.string.theme_settings))
                .setItems(themes) { _, which ->
                    when (which) {
                        0 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                        1 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                        2 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    }
                }
                .show()
        }
    }
}