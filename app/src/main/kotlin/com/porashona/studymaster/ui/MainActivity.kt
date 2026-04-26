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
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
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
import com.porashona.studymaster.utils.AppearanceUtils
import kotlinx.coroutines.launch

/**
 * Hosts the side drawer + bottom-nav scaffold. Profile and every secondary
 * destination live in the drawer (opened from the toolbar hamburger). The
 * bottom nav is reserved for the four primary destinations: Timer, Stats,
 * Music, Routine.
 */
class MainActivity : AppCompatActivity() {

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(AppearanceUtils.wrap(newBase))
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var repository: StudyRepository
    private lateinit var navController: NavController

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { _ ->
        // Permission result is not acted upon here; absence of notifications
        // just means reminders silently no-op instead of crashing.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        if (AppearanceUtils.shouldUseHighContrast(this)) {
            setTheme(R.style.Theme_StudyMaster_HighContrast)
        }
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        setupRepository()
        setupNavigation()
        setupDrawer()
        requestNotificationPermission()
        observeAchievementUnlocks()
    }

    private fun setupDrawer() {
        binding.toolbar.setNavigationOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }

        binding.navigationView.setNavigationItemSelectedListener { item ->
            // Close drawer first so transitions don't visually compete.
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            handleDrawerSelection(item.itemId)
            true
        }

        // Close drawer on back gesture before falling through to nav back-stack.
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
    }

    private fun handleDrawerSelection(id: Int) {
        when (id) {
            R.id.drawer_profile -> navigate(R.id.profileFragment)
            R.id.drawer_assistant -> navigate(R.id.assistantFragment)
            R.id.drawer_tasks -> navigate(R.id.tasksFragment)
            R.id.drawer_notes -> navigate(R.id.notesFragment)
            R.id.drawer_goals -> navigate(R.id.goalsFragment)
            R.id.drawer_exams -> navigate(R.id.examsFragment)
            R.id.drawer_session_history -> navigate(R.id.sessionHistoryFragment)
            R.id.drawer_insights -> navigate(R.id.insightsFragment)
            R.id.drawer_resources -> navigate(R.id.resourcesFragment)
            R.id.drawer_calendar -> navigate(R.id.calendarFragment)
            R.id.drawer_focus_mode ->
                startActivity(Intent(this, FocusModeActivity::class.java))
            R.id.drawer_break_coach -> navigate(R.id.breakCoachFragment)
            R.id.drawer_blocker -> navigate(R.id.blockerFragment)
            R.id.drawer_randomizer -> navigate(R.id.randomizerFragment)
            R.id.drawer_quotes -> navigate(R.id.quotesFragment)
            R.id.drawer_challenges -> navigate(R.id.challengesFragment)
            R.id.drawer_achievements -> navigate(R.id.achievementsFragment)
            R.id.drawer_settings ->
                startActivity(Intent(this, SettingsActivity::class.java))
        }
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
