package com.porashona.studymaster.service

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.porashona.studymaster.R
import com.porashona.studymaster.StudyMasterApplication
import com.porashona.studymaster.data.model.MusicTrack
import com.porashona.studymaster.data.model.StudyMusicLibrary
import com.porashona.studymaster.ui.MainActivity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MusicService : Service() {

    private val binder = MusicBinder()
    private var exoPlayer: ExoPlayer? = null

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    private val _currentTrack = MutableStateFlow<MusicTrack?>(null)
    val currentTrack: StateFlow<MusicTrack?> = _currentTrack.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    inner class MusicBinder : Binder() {
        fun getService(): MusicService = this@MusicService
    }

    override fun onBind(intent: Intent?): IBinder = binder

    override fun onCreate() {
        super.onCreate()
        initializePlayer()
    }

    private fun initializePlayer() {
        exoPlayer = ExoPlayer.Builder(this).build().apply {
            addListener(object : Player.Listener {
                override fun onPlaybackStateChanged(playbackState: Int) {
                    when (playbackState) {
                        Player.STATE_BUFFERING -> {
                            _isLoading.value = true
                            _error.value = null
                        }
                        Player.STATE_READY -> {
                            _isLoading.value = false
                            _error.value = null
                        }
                        Player.STATE_ENDED -> {
                            _isPlaying.value = false
                            _isLoading.value = false
                        }
                        Player.STATE_IDLE -> {
                            _isLoading.value = false
                        }
                    }
                }

                override fun onIsPlayingChanged(isPlaying: Boolean) {
                    _isPlaying.value = isPlaying
                    if (isPlaying) {
                        startForeground(NOTIFICATION_ID, createNotification())
                    }
                }

                override fun onPlayerError(error: androidx.media3.common.PlaybackException) {
                    _error.value = "স্ট্রিম লোড করতে সমস্যা হচ্ছে"
                    _isLoading.value = false
                    _isPlaying.value = false
                }
            })
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_PLAY -> {
                val trackId = intent.getIntExtra(EXTRA_TRACK_ID, 1)
                playTrack(trackId)
            }
            ACTION_PAUSE -> pause()
            ACTION_RESUME -> resume()
            ACTION_STOP -> stop()
            ACTION_NEXT -> playNext()
            ACTION_PREVIOUS -> playPrevious()
        }
        return START_STICKY
    }

    fun playTrack(trackId: Int) {
        val track = StudyMusicLibrary.getTrackById(trackId) ?: return
        _currentTrack.value = track
        _isLoading.value = true
        _error.value = null

        exoPlayer?.apply {
            stop()
            clearMediaItems()
            setMediaItem(MediaItem.fromUri(track.streamUrl))
            prepare()
            play()
        }

        startForeground(NOTIFICATION_ID, createNotification())
    }

    fun play() {
        if (_currentTrack.value == null) {
            playTrack(1) // Play first track if none selected
        } else {
            exoPlayer?.play()
        }
    }

    fun pause() {
        exoPlayer?.pause()
        updateNotification()
    }

    fun resume() {
        exoPlayer?.play()
    }

    fun stop() {
        exoPlayer?.stop()
        _isPlaying.value = false
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    fun playNext() {
        val currentId = _currentTrack.value?.id ?: 1
        val tracks = StudyMusicLibrary.tracks
        val currentIndex = tracks.indexOfFirst { it.id == currentId }
        val nextIndex = (currentIndex + 1) % tracks.size
        playTrack(tracks[nextIndex].id)
    }

    fun playPrevious() {
        val currentId = _currentTrack.value?.id ?: 1
        val tracks = StudyMusicLibrary.tracks
        val currentIndex = tracks.indexOfFirst { it.id == currentId }
        val prevIndex = if (currentIndex <= 0) tracks.size - 1 else currentIndex - 1
        playTrack(tracks[prevIndex].id)
    }

    fun setVolume(volume: Float) {
        exoPlayer?.volume = volume.coerceIn(0f, 1f)
    }

    fun isCurrentlyPlaying(): Boolean = exoPlayer?.isPlaying == true

    private fun createNotification(): Notification {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Pause/Play action
        val playPauseIntent = Intent(this, MusicService::class.java).apply {
            action = if (_isPlaying.value) ACTION_PAUSE else ACTION_RESUME
        }
        val playPausePendingIntent = PendingIntent.getService(
            this, 1, playPauseIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Stop action
        val stopIntent = Intent(this, MusicService::class.java).apply {
            action = ACTION_STOP
        }
        val stopPendingIntent = PendingIntent.getService(
            this, 2, stopIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Next action
        val nextIntent = Intent(this, MusicService::class.java).apply {
            action = ACTION_NEXT
        }
        val nextPendingIntent = PendingIntent.getService(
            this, 3, nextIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val track = _currentTrack.value
        val playPauseIcon = if (_isPlaying.value) R.drawable.ic_pause else R.drawable.ic_play
        val playPauseText = if (_isPlaying.value) "বিরতি" else "চালু"

        return NotificationCompat.Builder(this, StudyMasterApplication.MUSIC_CHANNEL_ID)
            .setContentTitle(track?.title ?: "পড়াশোনার মিউজিক")
            .setContentText(track?.artist ?: "Study Music")
            .setSmallIcon(R.drawable.ic_music)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .setSilent(true)
            .addAction(R.drawable.ic_previous, "আগের", getPreviousPendingIntent())
            .addAction(playPauseIcon, playPauseText, playPausePendingIntent)
            .addAction(R.drawable.ic_next, "পরের", nextPendingIntent)
            .addAction(R.drawable.ic_stop, "বন্ধ", stopPendingIntent)
            .setStyle(androidx.media.app.NotificationCompat.MediaStyle()
                .setShowActionsInCompactView(0, 1, 2))
            .build()
    }

    private fun getPreviousPendingIntent(): PendingIntent {
        val intent = Intent(this, MusicService::class.java).apply {
            action = ACTION_PREVIOUS
        }
        return PendingIntent.getService(
            this, 4, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun updateNotification() {
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID, createNotification())
    }

    override fun onDestroy() {
        super.onDestroy()
        exoPlayer?.release()
        exoPlayer = null
    }

    companion object {
        const val ACTION_PLAY = "com.porashona.studymaster.PLAY"
        const val ACTION_PAUSE = "com.porashona.studymaster.PAUSE_MUSIC"
        const val ACTION_RESUME = "com.porashona.studymaster.RESUME_MUSIC"
        const val ACTION_STOP = "com.porashona.studymaster.STOP_MUSIC"
        const val ACTION_NEXT = "com.porashona.studymaster.NEXT"
        const val ACTION_PREVIOUS = "com.porashona.studymaster.PREVIOUS"
        const val EXTRA_TRACK_ID = "track_id"
        const val NOTIFICATION_ID = 2001
    }
}