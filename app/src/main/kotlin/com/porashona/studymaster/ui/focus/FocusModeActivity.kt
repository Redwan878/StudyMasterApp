package com.porashona.studymaster.ui.focus

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.porashona.studymaster.R
import com.porashona.studymaster.databinding.ActivityFocusModeBinding
import com.porashona.studymaster.utils.AppearanceUtils

/**
 * Full-screen, distraction-free countdown screen.
 *
 * Historical note: this screen used to crash on open. The two root causes we
 * are defending against below:
 *  1. [android.view.WindowInsetsController] can legitimately be null before
 *     the decor view is attached on some OEM ROMs. Calling `insetsController?.…`
 *     is safe, but we additionally wrap the entire inset-config block in
 *     [runCatching] so any exotic window-manager exception (seen on some
 *     Huawei / Xiaomi devices) can't bring the activity down before we've
 *     even inflated the layout.
 *  2. The window flag + immersive mode calls were previously running *before*
 *     [setContentView]; on low-memory devices that can race with the window
 *     being torn down and inflated, producing
 *     `WindowManager.BadTokenException`. We now inflate first and only then
 *     ask for fullscreen.
 *
 * We also no longer swallow the back button — the user can leave with either
 * the Exit button or the system back gesture.
 */
class FocusModeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFocusModeBinding
    private var timer: CountDownTimer? = null
    private var isPaused = false
    private var remainingMillis: Long = 0L
    private var totalMillis: Long = 0L

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(AppearanceUtils.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        if (AppearanceUtils.shouldUseHighContrast(this)) {
            setTheme(R.style.Theme_StudyMaster_HighContrast)
        }
        super.onCreate(savedInstanceState)

        // Inflate FIRST. See class kdoc for why.
        binding = ActivityFocusModeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Any window-op failure shouldn't take the activity down. The UI still
        // works at normal (non-immersive) window size.
        runCatching { applyImmersiveFullscreen() }

        totalMillis = intent.getLongExtra(EXTRA_DURATION_MS, 25 * 60 * 1000L)
            .coerceAtLeast(60_000L)
        remainingMillis = totalMillis

        binding.btnExit.setOnClickListener { exit() }
        binding.btnPauseResume.setOnClickListener { togglePause() }

        startTimer(remainingMillis)

        // System back is allowed — users were getting genuinely stuck otherwise.
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                exit()
            }
        })
    }

    private fun applyImmersiveFullscreen() {
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
            window.insetsController?.apply {
                hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                systemBarsBehavior =
                    WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        }
    }

    private fun startTimer(duration: Long) {
        timer?.cancel()
        timer = object : CountDownTimer(duration, 250) {
            override fun onTick(millisUntilFinished: Long) {
                remainingMillis = millisUntilFinished
                updateUi()
            }

            override fun onFinish() {
                remainingMillis = 0L
                updateUi()
                binding.tvStatus.setText(R.string.focus_session_complete)
                binding.btnPauseResume.isEnabled = false
            }
        }.start()
        isPaused = false
        binding.btnPauseResume.setText(R.string.focus_pause)
    }

    private fun togglePause() {
        if (isPaused) {
            startTimer(remainingMillis)
        } else {
            timer?.cancel()
            isPaused = true
            binding.btnPauseResume.setText(R.string.focus_resume)
        }
    }

    private fun updateUi() {
        val totalSeconds = (remainingMillis / 1000).coerceAtLeast(0L)
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        binding.tvTimer.text = String.format("%02d:%02d", minutes, seconds)
        val progress = if (totalMillis > 0L) {
            (((totalMillis - remainingMillis).toFloat() / totalMillis) * 100f).toInt()
        } else 0
        binding.progressFocus.setProgressCompat(progress.coerceIn(0, 100), true)
    }

    private fun exit() {
        timer?.cancel()
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
    }

    companion object {
        const val EXTRA_DURATION_MS = "duration"
    }
}
