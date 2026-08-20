/*
package com.porashona.studymaster.ui.focus

import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.porashona.studymaster.databinding.ActivityFocusModeBinding
import com.porashona.studymaster.data.preferences.PreferencesManager
import com.porashona.studymaster.service.MusicService
import kotlinx.coroutines.launch

class FocusModeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFocusModeBinding
    private var timer: CountDownTimer? = null
    private var isRunning = false
    private var musicService: MusicService? = null
    private var isBound = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Immersive fullscreen. `systemUiVisibility` is deprecated in favour of
        // WindowInsetsController on R+.
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

        val duration = intent.getLongExtra("duration", 25 * 60 * 1000L)
        startTimer(duration)

        bindMusicService()

        binding.btnExit.setOnClickListener {
            timer?.cancel()
            musicService?.pause()
            if (isBound) {
                unbindMusicService()
            }
            finish()
        }

        // Require the user to tap the Exit button instead of letting a back
        // gesture immediately kill the session.
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Intentionally empty — block the default back action.
            }
        })
    }

    private fun bindMusicService() {
        val intent = Intent(this, MusicService::class.java)
        bindService(intent, musicServiceConnection, Context.BIND_AUTO_CREATE)
    }

    private val musicServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as MusicService.MusicBinder
            musicService = binder.getService()
            isBound = true
            observeMusicSettings()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            musicService = null
            isBound = false
        }
    }

    private fun observeMusicSettings() {
        lifecycleScope.launch {
            val prefs = (application as com.porashona.studymaster.StudyMasterApplication).preferencesManager
            prefs.musicEnabled.collectLatest { musicEnabled ->
                if (musicEnabled && isRunning) {
                    musicService?.play()
                } else if (!musicEnabled) {
                    musicService?.pause()
                }
            }
        }
    }

    private fun startTimer(duration: Long) {
        timer = object : CountDownTimer(duration, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minutes = (millisUntilFinished / 1000) / 60
                val seconds = (millisUntilFinished / 1000) % 60
                binding.tvTimer.text = String.format("%02d:%02d", minutes, seconds)
            }

            override fun onFinish() {
                binding.tvTimer.text = "00:00"
                binding.tvStatus.text = "Session Complete"
            }
        }.start()
        isRunning = true
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
        if (isBound) {
            unbindMusicService()
        }
    }

    private fun unbindMusicService() {
        if (isBound) {
            unbindService(musicServiceConnection)
            isBound = false
        }
    }
}

*/