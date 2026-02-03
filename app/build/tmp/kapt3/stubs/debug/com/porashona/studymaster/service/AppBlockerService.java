package com.porashona.studymaster.service;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\t\u0018\u0000 *2\u00020\u0001:\u0001*B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0019\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0005H\u0082@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0013J\u0011\u0010\u0014\u001a\u00020\u0011H\u0082@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0015J\b\u0010\u0016\u001a\u00020\u0017H\u0002J\u0010\u0010\u0018\u001a\u00020\u00052\u0006\u0010\u0012\u001a\u00020\u0005H\u0002J\n\u0010\u0019\u001a\u0004\u0018\u00010\u0005H\u0002J\b\u0010\u001a\u001a\u00020\u0011H\u0002J\u0014\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0016J\b\u0010\u001f\u001a\u00020\u0011H\u0016J\b\u0010 \u001a\u00020\u0011H\u0016J\"\u0010!\u001a\u00020\"2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001e2\u0006\u0010#\u001a\u00020\"2\u0006\u0010$\u001a\u00020\"H\u0016J\u0019\u0010%\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0005H\u0082@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0013J\u0010\u0010&\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0005H\u0002J\b\u0010\'\u001a\u00020\u0011H\u0002J\b\u0010(\u001a\u00020\u0011H\u0002J\b\u0010)\u001a\u00020\u0011H\u0002R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006+"}, d2 = {"Lcom/porashona/studymaster/service/AppBlockerService;", "Landroid/app/Service;", "()V", "blockedPackages", "", "", "isBlockingActive", "", "monitoringJob", "Lkotlinx/coroutines/Job;", "serviceScope", "Lkotlinx/coroutines/CoroutineScope;", "sessionEndTime", "", "strictModeEnabled", "useRootBlocking", "blockApp", "", "packageName", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "checkForegroundApp", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "createNotification", "Landroid/app/Notification;", "getAppName", "getForegroundApp", "loadSettings", "onBind", "Landroid/os/IBinder;", "intent", "Landroid/content/Intent;", "onCreate", "onDestroy", "onStartCommand", "", "flags", "startId", "recordBlockAttempt", "showBlockOverlay", "startBlocking", "startMonitoring", "stopBlocking", "Companion", "app_debug"})
public final class AppBlockerService extends android.app.Service {
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.CoroutineScope serviceScope = null;
    @org.jetbrains.annotations.Nullable
    private kotlinx.coroutines.Job monitoringJob;
    @org.jetbrains.annotations.NotNull
    private java.util.Set<java.lang.String> blockedPackages;
    private boolean isBlockingActive = false;
    private boolean useRootBlocking = false;
    private boolean strictModeEnabled = false;
    private long sessionEndTime = 0L;
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String ACTION_START_BLOCKING = "com.porashona.studymaster.START_BLOCKING";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String ACTION_STOP_BLOCKING = "com.porashona.studymaster.STOP_BLOCKING";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String ACTION_UPDATE_BLOCKED_APPS = "com.porashona.studymaster.UPDATE_BLOCKED";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String EXTRA_SESSION_DURATION = "session_duration";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String EXTRA_BLOCKED_PACKAGES = "blocked_packages";
    private static final int NOTIFICATION_ID = 3001;
    private static final long CHECK_INTERVAL = 500L;
    private static boolean isRunning = false;
    @org.jetbrains.annotations.NotNull
    public static final com.porashona.studymaster.service.AppBlockerService.Companion Companion = null;
    
    public AppBlockerService() {
        super();
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public android.os.IBinder onBind(@org.jetbrains.annotations.Nullable
    android.content.Intent intent) {
        return null;
    }
    
    @java.lang.Override
    public void onCreate() {
    }
    
    @java.lang.Override
    public int onStartCommand(@org.jetbrains.annotations.Nullable
    android.content.Intent intent, int flags, int startId) {
        return 0;
    }
    
    private final void startBlocking() {
    }
    
    private final void stopBlocking() {
    }
    
    private final void startMonitoring() {
    }
    
    private final java.lang.Object checkForegroundApp(kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.String getForegroundApp() {
        return null;
    }
    
    private final java.lang.Object blockApp(java.lang.String packageName, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final void showBlockOverlay(java.lang.String packageName) {
    }
    
    private final java.lang.String getAppName(java.lang.String packageName) {
        return null;
    }
    
    private final java.lang.Object recordBlockAttempt(java.lang.String packageName, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final void loadSettings() {
    }
    
    private final android.app.Notification createNotification() {
        return null;
    }
    
    @java.lang.Override
    public void onDestroy() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082T\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\r\u001a\u00020\u000e@BX\u0086\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010\u00a8\u0006\u0011"}, d2 = {"Lcom/porashona/studymaster/service/AppBlockerService$Companion;", "", "()V", "ACTION_START_BLOCKING", "", "ACTION_STOP_BLOCKING", "ACTION_UPDATE_BLOCKED_APPS", "CHECK_INTERVAL", "", "EXTRA_BLOCKED_PACKAGES", "EXTRA_SESSION_DURATION", "NOTIFICATION_ID", "", "<set-?>", "", "isRunning", "()Z", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        public final boolean isRunning() {
            return false;
        }
    }
}