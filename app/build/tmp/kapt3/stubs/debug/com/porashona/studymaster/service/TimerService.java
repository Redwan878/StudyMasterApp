package com.porashona.studymaster.service;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u000b\u0018\u0000 )2\u00020\u0001:\u0002)*B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0018\u001a\u00020\u0019H\u0002J\u0012\u0010\u001a\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0016J\b\u0010\u001e\u001a\u00020\u000bH\u0016J\"\u0010\u001f\u001a\u00020 2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001d2\u0006\u0010!\u001a\u00020 2\u0006\u0010\"\u001a\u00020 H\u0016J\b\u0010#\u001a\u00020\u000bH\u0002J\b\u0010$\u001a\u00020\u000bH\u0002J\u0010\u0010%\u001a\u00020\u000b2\u0006\u0010&\u001a\u00020\u0012H\u0002J\b\u0010\'\u001a\u00020\u000bH\u0002J\b\u0010(\u001a\u00020\u000bH\u0002R\u0012\u0010\u0003\u001a\u00060\u0004R\u00020\u0000X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\"\u0010\t\u001a\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\nX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR(\u0010\u0010\u001a\u0010\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u000b\u0018\u00010\u0011X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u000e\u0010\u0017\u001a\u00020\u0012X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006+"}, d2 = {"Lcom/porashona/studymaster/service/TimerService;", "Landroid/app/Service;", "()V", "binder", "Lcom/porashona/studymaster/service/TimerService$TimerBinder;", "countDownTimer", "Landroid/os/CountDownTimer;", "isRunning", "", "onFinishListener", "Lkotlin/Function0;", "", "getOnFinishListener", "()Lkotlin/jvm/functions/Function0;", "setOnFinishListener", "(Lkotlin/jvm/functions/Function0;)V", "onTickListener", "Lkotlin/Function1;", "", "getOnTickListener", "()Lkotlin/jvm/functions/Function1;", "setOnTickListener", "(Lkotlin/jvm/functions/Function1;)V", "timeLeftMillis", "createNotification", "Landroid/app/Notification;", "onBind", "Landroid/os/IBinder;", "intent", "Landroid/content/Intent;", "onDestroy", "onStartCommand", "", "flags", "startId", "pauseTimer", "resumeTimer", "startTimer", "duration", "stopTimer", "updateNotification", "Companion", "TimerBinder", "app_debug"})
public final class TimerService extends android.app.Service {
    @org.jetbrains.annotations.NotNull
    private final com.porashona.studymaster.service.TimerService.TimerBinder binder = null;
    @org.jetbrains.annotations.Nullable
    private android.os.CountDownTimer countDownTimer;
    private long timeLeftMillis = 0L;
    private boolean isRunning = false;
    @org.jetbrains.annotations.Nullable
    private kotlin.jvm.functions.Function1<? super java.lang.Long, kotlin.Unit> onTickListener;
    @org.jetbrains.annotations.Nullable
    private kotlin.jvm.functions.Function0<kotlin.Unit> onFinishListener;
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String ACTION_START = "com.porashona.studymaster.START";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String ACTION_PAUSE = "com.porashona.studymaster.PAUSE";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String ACTION_RESUME = "com.porashona.studymaster.RESUME";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String ACTION_STOP = "com.porashona.studymaster.STOP";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String EXTRA_DURATION = "duration";
    public static final int NOTIFICATION_ID = 1001;
    @org.jetbrains.annotations.NotNull
    public static final com.porashona.studymaster.service.TimerService.Companion Companion = null;
    
    public TimerService() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable
    public final kotlin.jvm.functions.Function1<java.lang.Long, kotlin.Unit> getOnTickListener() {
        return null;
    }
    
    public final void setOnTickListener(@org.jetbrains.annotations.Nullable
    kotlin.jvm.functions.Function1<? super java.lang.Long, kotlin.Unit> p0) {
    }
    
    @org.jetbrains.annotations.Nullable
    public final kotlin.jvm.functions.Function0<kotlin.Unit> getOnFinishListener() {
        return null;
    }
    
    public final void setOnFinishListener(@org.jetbrains.annotations.Nullable
    kotlin.jvm.functions.Function0<kotlin.Unit> p0) {
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public android.os.IBinder onBind(@org.jetbrains.annotations.Nullable
    android.content.Intent intent) {
        return null;
    }
    
    @java.lang.Override
    public int onStartCommand(@org.jetbrains.annotations.Nullable
    android.content.Intent intent, int flags, int startId) {
        return 0;
    }
    
    private final void startTimer(long duration) {
    }
    
    private final void pauseTimer() {
    }
    
    private final void resumeTimer() {
    }
    
    private final void stopTimer() {
    }
    
    private final android.app.Notification createNotification() {
        return null;
    }
    
    private final void updateNotification() {
    }
    
    @java.lang.Override
    public void onDestroy() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2 = {"Lcom/porashona/studymaster/service/TimerService$Companion;", "", "()V", "ACTION_PAUSE", "", "ACTION_RESUME", "ACTION_START", "ACTION_STOP", "EXTRA_DURATION", "NOTIFICATION_ID", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004\u00a8\u0006\u0005"}, d2 = {"Lcom/porashona/studymaster/service/TimerService$TimerBinder;", "Landroid/os/Binder;", "(Lcom/porashona/studymaster/service/TimerService;)V", "getService", "Lcom/porashona/studymaster/service/TimerService;", "app_debug"})
    public final class TimerBinder extends android.os.Binder {
        
        public TimerBinder() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.porashona.studymaster.service.TimerService getService() {
            return null;
        }
    }
}