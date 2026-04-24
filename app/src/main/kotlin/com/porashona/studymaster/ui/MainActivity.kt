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
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
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
        setupDrawer()
        setupToolbarMenu()
        requestNotificationPermission()
        observeAchievementUnlocks()
    }

    private fun setupDrawer() {
        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.drawer_open,
            R.string.drawer_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.isDrawerIndicatorEnabled = true
        toggle.syncState()

        binding.navDrawer.setNavigationItemSelectedListener { item ->
            val handled = when (item.itemId) {
                R.id.drawer_timer -> navigate(R.id.timerFragment)
                R.id.drawer_stats -> navigate(R.id.statsFragment)
                R.id.drawer_music -> navigate(R.id.musicFragment)
                R.id.drawer_routine -> navigate(R.id.routineFragment)
                R.id.drawer_profile -> navigate(R.id.profileFragment)
                R.id.drawer_tasks -> navigate(R.id.tasksFragment)
                R.id.drawer_notes -> navigate(R.id.notesFragment)
                R.id.drawer_goals -> navigate(R.id.goalsFragment)
                R.id.drawer_exams -> navigate(R.id.examsFragment)
                R.id.drawer_blocker -> navigate(R.id.blockerFragment)
                R.id.drawer_calendar -> navigate(R.id.calendarFragment)
                R.id.drawer_randomizer -> navigate(R.id.randomizerFragment)
                R.id.drawer_resources -> navigate(R.id.resourcesFragment)
                R.id.drawer_assistant -> navigate(R.id.assistantFragment)
                R.id.drawer_quotes -> navigate(R.id.quotesFragment)
                R.id.drawer_challenges -> navigate(R.id.challengesFragment)
                R.id.drawer_achievements -> navigate(R.id.achievementsFragment)
                R.id.drawer_history -> navigate(R.id.sessionHistoryFragment)
                R.id.drawer_insights -> navigate(R.id.insightsFragment)
                R.id.drawer_break_coach -> navigate(R.id.breakCoachFragment)
                R.id.drawer_focus -> {
                    startActivity(Intent(this, FocusModeActivity::class.java))
                    true
                }
                R.id.drawer_settings -> {
                    startActivity(Intent(this, SettingsActivity::class.java))
                    true
                }
                else -> false
            }
            if (handled) {
                item.isChecked = true
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            }
            handled
        }

        // Back button closes drawer when open instead of exiting the app.
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                } else {
                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                    isEnabled = true
                }
            }
        })

        // Dim scrim for a modern subtle-overlay feel.
        binding.drawerLayout.setScrimColor(0x66000000)
        binding.drawerLayout.setDrawerElevation(24f)
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
                R.id.menu_session_history -> navigate(R.id.sessionHistoryFragment)
                R.id.menu_insights -> navigate(R.id.insightsFragment)
                R.id.menu_break_coach -> navigate(R.id.breakCoachFragment)
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

    /**
     * Hardware keyboard shortcuts. Lets power users (and tablet users with a
     * Bluetooth keyboard) jump between the main tabs without touching the
     * screen. Mapped to `Ctrl + <number>` for the five bottom-nav screens
     * plus a few letter shortcuts for drawer items.
     */
    override fun onKeyShortcut(keyCode: Int, event: android.view.KeyEvent): Boolean {
        val handled = when (keyCode) {
            android.view.KeyEvent.KEYCODE_1 -> navigate(R.id.timerFragment)
            android.view.KeyEvent.KEYCODE_2 -> navigate(R.id.statsFragment)
            android.view.KeyEvent.KEYCODE_3 -> navigate(R.id.musicFragment)
            android.view.KeyEvent.KEYCODE_4 -> navigate(R.id.routineFragment)
            android.view.KeyEvent.KEYCODE_5 -> navigate(R.id.profileFragment)
            android.view.KeyEvent.KEYCODE_T -> navigate(R.id.tasksFragment)
            android.view.KeyEvent.KEYCODE_N -> navigate(R.id.notesFragment)
            android.view.KeyEvent.KEYCODE_G -> navigate(R.id.goalsFragment)
            android.view.KeyEvent.KEYCODE_E -> navigate(R.id.examsFragment)
            android.view.KeyEvent.KEYCODE_Q -> navigate(R.id.quotesFragment)
            android.view.KeyEvent.KEYCODE_COMMA -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                true
            }
            else -> false
        }
        return handled || super.onKeyShortcut(keyCode, event)
    }
}
