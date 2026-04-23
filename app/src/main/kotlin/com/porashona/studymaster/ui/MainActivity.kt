package com.porashona.studymaster.ui

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.snackbar.Snackbar
import com.porashona.studymaster.R
import com.porashona.studymaster.StudyMasterApplication
import com.porashona.studymaster.data.repository.StudyRepository
import com.porashona.studymaster.databinding.ActivityMainBinding
import com.porashona.studymaster.ui.focus.FocusModeActivity
import com.porashona.studymaster.ui.settings.SettingsActivity
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var repository: StudyRepository
    private lateinit var navController: NavController

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { _ ->
        // Permission result is not acted upon here; absence of notifications just
        // means reminders silently no-op instead of crashing.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        setupRepository()
        setupNavigation()
        setupToolbarMenu()
        requestNotificationPermission()
        observeAchievementUnlocks()
    }

    private fun observeAchievementUnlocks() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                repository.achievementUnlocks.collect { a ->
                    Snackbar.make(
                        binding.root,
                        getString(R.string.achievement_unlocked, a.title),
                        Snackbar.LENGTH_LONG
                    ).show()
                    vibrateForAchievement()
                }
            }
        }
    }

    private fun vibrateForAchievement() {
        val vibrator: Vibrator? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vm = getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
            vm?.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
        }
        vibrator?.takeIf { it.hasVibrator() }?.let {
            // Short two-pulse pattern so the phone "double-taps" — distinctive
            // enough to stand out from a normal notification buzz.
            val effect = VibrationEffect.createWaveform(
                longArrayOf(0, 80, 60, 80),
                intArrayOf(0, 255, 0, 255),
                -1
            )
            runCatching { it.vibrate(effect) }
        }
    }

    private fun setupRepository() {
        repository = (application as StudyMasterApplication).studyRepository
        val extended = (application as StudyMasterApplication).extendedRepository

        lifecycleScope.launch {
            repository.initializeProfile()
            repository.initializeAchievements()
            extended.initializeQuotes()
            extended.initializeDailyChallenges()
        }
    }

    private fun setupNavigation() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        binding.bottomNavigation.setupWithNavController(navController)
    }

    private fun setupToolbarMenu() {
        binding.toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menu_tasks -> navigate(R.id.tasksFragment)
                R.id.menu_notes -> navigate(R.id.notesFragment)
                R.id.menu_goals -> navigate(R.id.goalsFragment)
                R.id.menu_exams -> navigate(R.id.examsFragment)
                R.id.menu_blocker -> navigate(R.id.blockerFragment)
                R.id.menu_calendar -> navigate(R.id.calendarFragment)
                R.id.menu_randomizer -> navigate(R.id.randomizerFragment)
                R.id.menu_resources -> navigate(R.id.resourcesFragment)
                R.id.menu_assistant -> navigate(R.id.assistantFragment)
                R.id.menu_quotes -> navigate(R.id.quotesFragment)
                R.id.menu_challenges -> navigate(R.id.challengesFragment)
                R.id.menu_achievements -> navigate(R.id.achievementsFragment)
                R.id.menu_focus_mode -> {
                    startActivity(Intent(this, FocusModeActivity::class.java))
                    true
                }
                R.id.menu_settings -> {
                    startActivity(Intent(this, SettingsActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }

    private fun navigate(destinationId: Int): Boolean {
        if (navController.currentDestination?.id == destinationId) return true
        val options = NavOptions.Builder()
            .setLaunchSingleTop(true)
            .setEnterAnim(R.anim.nav_enter)
            .setExitAnim(R.anim.nav_exit)
            .setPopEnterAnim(R.anim.nav_pop_enter)
            .setPopExitAnim(R.anim.nav_pop_exit)
            .build()
        return runCatching { navController.navigate(destinationId, null, options) }.isSuccess
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val granted = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
            if (!granted) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
}
