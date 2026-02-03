package com.porashona.studymaster.ui.blocker

import android.os.Bundle
import android.os.CountDownTimer
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.porashona.studymaster.R
import com.porashona.studymaster.databinding.ActivityBlockOverlayBinding
import com.porashona.studymaster.ui.MainActivity
import android.content.Intent

class BlockOverlayActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBlockOverlayBinding
    private var countDownTimer: CountDownTimer? = null

    companion object {
        const val EXTRA_PACKAGE_NAME = "package_name"
        const val EXTRA_APP_NAME = "app_name"
        const val EXTRA_TIME_REMAINING = "time_remaining"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Make it a full-screen overlay
        window.addFlags(
            WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON or
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
        )

        binding = ActivityBlockOverlayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
    }

    private fun setupUI() {
        val appName = intent.getStringExtra(EXTRA_APP_NAME) ?: "App"
        val timeRemaining = intent.getLongExtra(EXTRA_TIME_REMAINING, 0L)

        // Set blocked app name
        binding.tvBlockedAppName.text = appName
        binding.tvBlockMessage.text = getString(R.string.app_blocked_message)

        // Show time remaining if available
        if (timeRemaining > 0) {
            binding.tvTimeRemaining.visibility = android.view.View.VISIBLE
            startCountdown(timeRemaining)
        } else {
            binding.tvTimeRemaining.visibility = android.view.View.GONE
        }

        // Go back to study button
        binding.btnGoBack.setOnClickListener {
            goBackToStudy()
        }

        // Motivational text
        binding.tvMotivation.text = getRandomMotivation()
    }

    private fun startCountdown(duration: Long) {
        countDownTimer?.cancel()
        countDownTimer = object : CountDownTimer(duration, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minutes = (millisUntilFinished / 1000) / 60
                val seconds = (millisUntilFinished / 1000) % 60
                binding.tvTimeRemaining.text = String.format(
                    "%s: %02d:%02d",
                    getString(R.string.time_remaining),
                    minutes,
                    seconds
                )
            }

            override fun onFinish() {
                binding.tvTimeRemaining.text = getString(R.string.session_complete)
            }
        }.start()
    }

    private fun goBackToStudy() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        startActivity(intent)
        finish()
    }

    private fun getRandomMotivation(): String {
        val motivations = listOf(
            "পড়াশোনায় মনোযোগ দিন! 📚",
            "সফলতা কঠোর পরিশ্রমে আসে! 💪",
            "আজকের পড়া আজ শেষ করুন! ⏰",
            "বিরতি নিন, কিন্তু ছেড়ে দেবেন না! 🎯",
            "আপনি পারবেন! 🌟",
            "Stay Focused! 🧠",
            "Your future self will thank you! 🙏",
            "One step at a time! 👣",
            "Knowledge is power! ⚡",
            "Keep pushing! 🚀"
        )
        return motivations.random()
    }

    override fun onBackPressed() {
        // Prevent going back, redirect to main app
        goBackToStudy()
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer?.cancel()
    }
}