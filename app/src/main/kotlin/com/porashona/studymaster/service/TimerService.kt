package com.porashona.studymaster.service

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.CountDownTimer
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.porashona.studymaster.R
import com.porashona.studymaster.StudyMasterApplication
import com.porashona.studymaster.ui.MainActivity

class TimerService : Service() {

    private val binder = TimerBinder()
    private var countDownTimer: CountDownTimer? = null
    private var timeLeftMillis: Long = 0
    private var isRunning = false

    var onTickListener: ((Long) -> Unit)? = null
    var onFinishListener: (() -> Unit)? = null

    inner class TimerBinder : Binder() {
        fun getService(): TimerService = this@TimerService
    }

    override fun onBind(intent: Intent?): IBinder = binder

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START -> {
                val duration = intent.getLongExtra(EXTRA_DURATION, 25 * 60 * 1000L)
                startTimer(duration)
            }
            ACTION_PAUSE -> pauseTimer()
            ACTION_RESUME -> resumeTimer()
            ACTION_STOP -> stopTimer()
        }
        return START_STICKY
    }

    private fun startTimer(duration: Long) {
        timeLeftMillis = duration
        isRunning = true
        startForeground(NOTIFICATION_ID, createNotification())
        
        countDownTimer = object : CountDownTimer(duration, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftMillis = millisUntilFinished
                onTickListener?.invoke(millisUntilFinished)
                updateNotification()
            }

            override fun onFinish() {
                isRunning = false
                onFinishListener?.invoke()
                stopForeground(STOP_FOREGROUND_REMOVE)
                stopSelf()
            }
        }.start()
    }

    private fun pauseTimer() {
        countDownTimer?.cancel()
        isRunning = false
        updateNotification()
    }

    private fun resumeTimer() {
        startTimer(timeLeftMillis)
    }

    private fun stopTimer() {
        countDownTimer?.cancel()
        isRunning = false
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    private fun createNotification(): Notification {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val minutes = (timeLeftMillis / 1000) / 60
        val seconds = (timeLeftMillis / 1000) % 60

        return NotificationCompat.Builder(this, StudyMasterApplication.TIMER_CHANNEL_ID)
            .setContentTitle("পড়াশোনা চলছে...")
            .setContentText(String.format("%02d:%02d বাকি", minutes, seconds))
            .setSmallIcon(R.drawable.ic_timer)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .setSilent(true)
            .build()
    }

    private fun updateNotification() {
        val notification = createNotification()
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as android.app.NotificationManager
        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer?.cancel()
    }

    companion object {
        const val ACTION_START = "com.porashona.studymaster.START"
        const val ACTION_PAUSE = "com.porashona.studymaster.PAUSE"
        const val ACTION_RESUME = "com.porashona.studymaster.RESUME"
        const val ACTION_STOP = "com.porashona.studymaster.STOP"
        const val EXTRA_DURATION = "duration"
        const val NOTIFICATION_ID = 1001
    }
}