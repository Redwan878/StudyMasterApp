package com.porashona.studymaster.service

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.provider.Settings
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.core.app.NotificationCompat
import com.porashona.studymaster.R
import com.porashona.studymaster.StudyMasterApplication
import com.porashona.studymaster.ui.MainActivity

/**
 * System overlay that draws a small draggable digital clock on top of other
 * apps. Starts as a foreground service so the window survives Doze. The
 * caller is responsible for checking [Settings.canDrawOverlays] before
 * starting this service; without that permission startup silently no-ops.
 */
class FloatingClockService : Service() {

    private var windowManager: WindowManager? = null
    private var view: View? = null
    private lateinit var tvClock: TextView
    private val handler = Handler(Looper.getMainLooper())
    private val tick = object : Runnable {
        override fun run() {
            refresh()
            handler.postDelayed(this, 1000L)
        }
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        startForeground(NOTIFICATION_ID, buildNotification())
        if (!canDrawOverlays(this)) {
            // Caller should have checked — bail out gracefully so we don't
            // crash in the WindowManager add.
            stopSelf()
            return
        }
        addOverlay()
        handler.post(tick)
    }

    private fun addOverlay() {
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        val inflater = LayoutInflater.from(this)
        val overlay = inflater.inflate(R.layout.view_floating_clock, null)
        tvClock = overlay.findViewById(R.id.tvFloatingClock)

        val type = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        else @Suppress("DEPRECATION") WindowManager.LayoutParams.TYPE_PHONE

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            type,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            PixelFormat.TRANSLUCENT,
        ).apply {
            gravity = Gravity.TOP or Gravity.START
            x = 40
            y = 200
        }

        overlay.setOnTouchListener(object : View.OnTouchListener {
            private var initialX = 0
            private var initialY = 0
            private var touchX = 0f
            private var touchY = 0f

            override fun onTouch(v: View, event: MotionEvent): Boolean = when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    initialX = params.x; initialY = params.y
                    touchX = event.rawX; touchY = event.rawY
                    true
                }
                MotionEvent.ACTION_MOVE -> {
                    params.x = initialX + (event.rawX - touchX).toInt()
                    params.y = initialY + (event.rawY - touchY).toInt()
                    runCatching { windowManager?.updateViewLayout(overlay, params) }
                    true
                }
                else -> false
            }
        })

        runCatching { windowManager?.addView(overlay, params) }
        view = overlay
        refresh()
    }

    private fun refresh() {
        if (!::tvClock.isInitialized) return
        val now = java.util.Calendar.getInstance()
        tvClock.text = String.format(
            "%02d:%02d",
            now.get(java.util.Calendar.HOUR_OF_DAY),
            now.get(java.util.Calendar.MINUTE),
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(tick)
        runCatching { view?.let { windowManager?.removeView(it) } }
        view = null
        windowManager = null
    }

    private fun buildNotification(): Notification {
        val tap = PendingIntent.getActivity(
            this, 0, Intent(this, MainActivity::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )
        return NotificationCompat.Builder(this, StudyMasterApplication.TIMER_CHANNEL_ID)
            .setContentTitle(getString(R.string.floating_clock_notification_title))
            .setContentText(getString(R.string.floating_clock_notification_body))
            .setSmallIcon(R.drawable.ic_timer)
            .setContentIntent(tap)
            .setOngoing(true)
            .setSilent(true)
            .build()
    }

    companion object {
        private const val NOTIFICATION_ID = 9901

        fun canDrawOverlays(context: Context): Boolean {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                Settings.canDrawOverlays(context)
            else true
        }

        fun start(context: Context) {
            if (!canDrawOverlays(context)) return
            val intent = Intent(context, FloatingClockService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(intent)
            } else {
                context.startService(intent)
            }
        }

        fun stop(context: Context) {
            context.stopService(Intent(context, FloatingClockService::class.java))
        }
    }
}
