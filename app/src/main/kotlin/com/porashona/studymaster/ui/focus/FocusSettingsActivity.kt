package com.porashona.studymaster.ui.focus

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.porashona.studymaster.R
import com.porashona.studymaster.databinding.ActivityFocusSettingsBinding

class FocusSettingsActivity : AppCompatActivity() {
    private var _binding: ActivityFocusSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFocusSettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupThemeSpinner()
        setupFocusDurationSeekBar()
        setupBreakDurationSeekBar()
        setupSessionCountSpinner()
        setupPauseBehaviorSpinner()
        setupAutoStartSwitch()
        setupNotificationSwitch()
        setupSaveButton()
        setupBackButton()

        loadCurrentSettings()
    }

    private fun setupThemeSpinner() {
        val themes = arrayOf("Default", "Light", "Dark", "System Default")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, themes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerTheme.adapter = adapter

        binding.spinnerTheme.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // Apply theme setting
                when (position) {
                    0 -> applyAppTheme(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    1 -> applyAppTheme(AppCompatDelegate.MODE_NIGHT_NO)
                    2 -> applyAppTheme(AppCompatDelegate.MODE_NIGHT_YES)
                    3 -> applyAppTheme(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun setupFocusDurationSeekBar() {
        binding.seekBarFocusDuration.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val minutes = progress + 5
                binding.tvFocusDurationValue.text = "$minutes min"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                saveFocusSettings()
            }
        })
    }

    private fun setupBreakDurationSeekBar() {
        binding.seekBarBreakDuration.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val minutes = progress + 1
                binding.tvBreakDurationValue.text = "$minutes min"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                saveFocusSettings()
            }
        })
    }

    private fun setupSessionCountSpinner() {
        val sessionCounts = listOf(1, 2, 3, 4, 5, 6, 7, 8)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, sessionCounts)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerSessionCount.adapter = adapter

        binding.spinnerSessionCount.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                saveFocusSettings()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun setupPauseBehaviorSpinner() {
        val pauseBehaviors = listOf("Soft Pause", "Hard Pause", "Allow Breaks", "Interrupt Allowed")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, pauseBehaviors)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerPauseBehavior.adapter = adapter

        binding.spinnerPauseBehavior.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                saveFocusSettings()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun setupAutoStartSwitch() {
        binding.switchAutoStart.setOnCheckedChangeListener { _, isChecked ->
            saveFocusSettings()
        }
    }

    private fun setupNotificationSwitch() {
        binding.switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            saveFocusSettings()
        }
    }

    private fun setupSaveButton() {
        binding.btnSave.setOnClickListener {
            saveFocusSettings()
            showToast("Focus settings saved")
            finish()
        }
    }

    private fun setupBackButton() {
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun loadCurrentSettings() {
        val prefs = getSharedPreferences("focus_prefs", Context.MODE_PRIVATE)

        // Load theme setting
        val currentTheme = prefs.getInt("theme_setting", 0)
        binding.spinnerTheme.setSelection(currentTheme)

        // Load focus duration
        val focusDuration = prefs.getInt("focus_duration", 25)
        binding.seekBarFocusDuration.progress = focusDuration - 5
        binding.tvFocusDurationValue.text = "$focusDuration min"

        // Load break duration
        val breakDuration = prefs.getInt("break_duration", 5)
        binding.seekBarBreakDuration.progress = breakDuration - 1
        binding.tvBreakDurationValue.text = "$breakDuration min"

        // Load session count
        val sessionCount = prefs.getInt("session_count", 4)
        binding.spinnerSessionCount.setSelection(sessionCount - 1)

        // Load pause behavior
        val pauseBehavior = prefs.getInt("pause_behavior", 0)
        binding.spinnerPauseBehavior.setSelection(pauseBehavior)

        // Load auto start
        binding.switchAutoStart.isChecked = prefs.getBoolean("auto_start", false)

        // Load notifications
        binding.switchNotifications.isChecked = prefs.getBoolean("notifications", true)
    }

    private fun saveFocusSettings() {
        val prefs = getSharedPreferences("focus_prefs", Context.MODE_PRIVATE)

        with(prefs.edit()) {
            // Save theme setting
            val themePosition = binding.spinnerTheme.selectedItemPosition
            putInt("theme_setting", themePosition)

            // Save focus duration
            val focusDuration = binding.seekBarFocusDuration.progress + 5
            putInt("focus_duration", focusDuration)

            // Save break duration
            val breakDuration = binding.seekBarBreakDuration.progress + 1
            putInt("break_duration", breakDuration)

            // Save session count
            val sessionCount = binding.spinnerSessionCount.selectedItem as Int
            putInt("session_count", sessionCount)

            // Save pause behavior
            val pauseBehavior = binding.spinnerPauseBehavior.selectedItemPosition
            putInt("pause_behavior", pauseBehavior)

            // Save auto start
            putBoolean("auto_start", binding.switchAutoStart.isChecked)

            // Save notifications
            putBoolean("notifications", binding.switchNotifications.isChecked)

            // Save timestamp
            putLong("settings_last_updated", System.currentTimeMillis())

            apply()
        }
    }

    private fun applyAppTheme(themeMode: Int) {
        AppCompatDelegate.setDefaultNightMode(themeMode)
        // Recreate activity to apply theme changes
        recreate()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}