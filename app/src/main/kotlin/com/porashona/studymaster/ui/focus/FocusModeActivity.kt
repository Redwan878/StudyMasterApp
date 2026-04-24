package com.porashona.studymaster.ui.focus

import android.app.PictureInPictureParams
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Rational
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.porashona.studymaster.databinding.ActivityFocusModeBinding

/**
 * Fullscreen focus timer with two "ambient" tricks:
 *  - Immersive system-bar hide so nothing but the countdown is visible.
 *  - Picture-in-Picture on Android O+: user can leave the app and keep the
 *    countdown floating above other windows.
 */
class FocusModeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFocusModeBinding
    private var timer: CountDownTimer? = null
    private var isRunning = false
    private var totalDuration: Long = 0L
    private var timeRemaining: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        binding = ActivityFocusModeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        totalDuration = intent.getLongExtra("duration", 25 * 60 * 1000L)
        startTimer(totalDuration)

        binding.btnExit.setOnClickListener {
            timer?.cancel()
            finish()
        }

        // Back gesture shouldn't kill the session; require the Exit button.
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() { /* blocked */ }
        })
    }

    private fun startTimer(duration: Long) {
        timer = object : CountDownTimer(duration, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeRemaining = millisUntilFinished
                val minutes = (millisUntilFinished / 1000) / 60
                val seconds = (millisUntilFinished / 1000) % 60
                binding.tvTimer.text = String.format("%02d:%02d", minutes, seconds)
            }

            override fun onFinish() {
                timeRemaining = 0L
                binding.tvTimer.text = "00:00"
                binding.tvStatus.text = "Session Complete"
            }
        }.start()
        isRunning = true
    }

    /**
     * Home key (or any backgrounding event) triggers PiP on Android O+ so the
     * timer keeps floating on top of whatever the user is doing. We use a 2:3
     * aspect ratio — tall, digits-friendly.
     */
    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && isRunning) {
            runCatching {
                val params = PictureInPictureParams.Builder()
                    .setAspectRatio(Rational(2, 3))
                    .build()
                enterPictureInPictureMode(params)
            }
        }
    }

    override fun onPictureInPictureModeChanged(
        isInPictureInPictureMode: Boolean,
        newConfig: Configuration,
    ) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig)
        // Hide the Give Up button when floating — there isn't room, and
        // the user can restore the activity to get it back.
        binding.btnExit.visibility = if (isInPictureInPictureMode) View.GONE else View.VISIBLE
        binding.tvStatus.visibility = if (isInPictureInPictureMode) View.GONE else View.VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
    }
}
