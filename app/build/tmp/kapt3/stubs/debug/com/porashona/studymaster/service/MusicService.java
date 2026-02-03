package com.porashona.studymaster.service;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u000b\n\u0002\u0010\u0007\n\u0002\b\u0005\u0018\u0000 42\u00020\u0001:\u000245B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0017\u001a\u00020\u0018H\u0002J\b\u0010\u0019\u001a\u00020\u001aH\u0002J\b\u0010\u001b\u001a\u00020\u001cH\u0002J\u0006\u0010\u001d\u001a\u00020\tJ\u0012\u0010\u001e\u001a\u00020\u001f2\b\u0010 \u001a\u0004\u0018\u00010!H\u0016J\b\u0010\"\u001a\u00020\u001cH\u0016J\b\u0010#\u001a\u00020\u001cH\u0016J\"\u0010$\u001a\u00020%2\b\u0010 \u001a\u0004\u0018\u00010!2\u0006\u0010&\u001a\u00020%2\u0006\u0010\'\u001a\u00020%H\u0016J\u0006\u0010(\u001a\u00020\u001cJ\u0006\u0010)\u001a\u00020\u001cJ\u0006\u0010*\u001a\u00020\u001cJ\u0006\u0010+\u001a\u00020\u001cJ\u000e\u0010,\u001a\u00020\u001c2\u0006\u0010-\u001a\u00020%J\u0006\u0010.\u001a\u00020\u001cJ\u000e\u0010/\u001a\u00020\u001c2\u0006\u00100\u001a\u000201J\u0006\u00102\u001a\u00020\u001cJ\b\u00103\u001a\u00020\u001cH\u0002R\u0016\u0010\u0003\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0006\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0012\u0010\u000b\u001a\u00060\fR\u00020\u0000X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010\r\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0019\u0010\u0011\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0010R\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u0014X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\t0\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0010R\u0017\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\t0\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0010\u00a8\u00066"}, d2 = {"Lcom/porashona/studymaster/service/MusicService;", "Landroid/app/Service;", "()V", "_currentTrack", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/porashona/studymaster/data/model/MusicTrack;", "_error", "", "_isLoading", "", "_isPlaying", "binder", "Lcom/porashona/studymaster/service/MusicService$MusicBinder;", "currentTrack", "Lkotlinx/coroutines/flow/StateFlow;", "getCurrentTrack", "()Lkotlinx/coroutines/flow/StateFlow;", "error", "getError", "exoPlayer", "Landroidx/media3/exoplayer/ExoPlayer;", "isLoading", "isPlaying", "createNotification", "Landroid/app/Notification;", "getPreviousPendingIntent", "Landroid/app/PendingIntent;", "initializePlayer", "", "isCurrentlyPlaying", "onBind", "Landroid/os/IBinder;", "intent", "Landroid/content/Intent;", "onCreate", "onDestroy", "onStartCommand", "", "flags", "startId", "pause", "play", "playNext", "playPrevious", "playTrack", "trackId", "resume", "setVolume", "volume", "", "stop", "updateNotification", "Companion", "MusicBinder", "app_debug"})
public final class MusicService extends android.app.Service {
    @org.jetbrains.annotations.NotNull
    private final com.porashona.studymaster.service.MusicService.MusicBinder binder = null;
    @org.jetbrains.annotations.Nullable
    private androidx.media3.exoplayer.ExoPlayer exoPlayer;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _isPlaying = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isPlaying = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<com.porashona.studymaster.data.model.MusicTrack> _currentTrack = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.porashona.studymaster.data.model.MusicTrack> currentTrack = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _isLoading = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isLoading = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _error = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> error = null;
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String ACTION_PLAY = "com.porashona.studymaster.PLAY";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String ACTION_PAUSE = "com.porashona.studymaster.PAUSE_MUSIC";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String ACTION_RESUME = "com.porashona.studymaster.RESUME_MUSIC";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String ACTION_STOP = "com.porashona.studymaster.STOP_MUSIC";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String ACTION_NEXT = "com.porashona.studymaster.NEXT";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String ACTION_PREVIOUS = "com.porashona.studymaster.PREVIOUS";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String EXTRA_TRACK_ID = "track_id";
    public static final int NOTIFICATION_ID = 2001;
    @org.jetbrains.annotations.NotNull
    public static final com.porashona.studymaster.service.MusicService.Companion Companion = null;
    
    public MusicService() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isPlaying() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.porashona.studymaster.data.model.MusicTrack> getCurrentTrack() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isLoading() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getError() {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public android.os.IBinder onBind(@org.jetbrains.annotations.Nullable
    android.content.Intent intent) {
        return null;
    }
    
    @java.lang.Override
    public void onCreate() {
    }
    
    private final void initializePlayer() {
    }
    
    @java.lang.Override
    public int onStartCommand(@org.jetbrains.annotations.Nullable
    android.content.Intent intent, int flags, int startId) {
        return 0;
    }
    
    public final void playTrack(int trackId) {
    }
    
    public final void play() {
    }
    
    public final void pause() {
    }
    
    public final void resume() {
    }
    
    public final void stop() {
    }
    
    public final void playNext() {
    }
    
    public final void playPrevious() {
    }
    
    public final void setVolume(float volume) {
    }
    
    public final boolean isCurrentlyPlaying() {
        return false;
    }
    
    private final android.app.Notification createNotification() {
        return null;
    }
    
    private final android.app.PendingIntent getPreviousPendingIntent() {
        return null;
    }
    
    private final void updateNotification() {
    }
    
    @java.lang.Override
    public void onDestroy() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2 = {"Lcom/porashona/studymaster/service/MusicService$Companion;", "", "()V", "ACTION_NEXT", "", "ACTION_PAUSE", "ACTION_PLAY", "ACTION_PREVIOUS", "ACTION_RESUME", "ACTION_STOP", "EXTRA_TRACK_ID", "NOTIFICATION_ID", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004\u00a8\u0006\u0005"}, d2 = {"Lcom/porashona/studymaster/service/MusicService$MusicBinder;", "Landroid/os/Binder;", "(Lcom/porashona/studymaster/service/MusicService;)V", "getService", "Lcom/porashona/studymaster/service/MusicService;", "app_debug"})
    public final class MusicBinder extends android.os.Binder {
        
        public MusicBinder() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.porashona.studymaster.service.MusicService getService() {
            return null;
        }
    }
}