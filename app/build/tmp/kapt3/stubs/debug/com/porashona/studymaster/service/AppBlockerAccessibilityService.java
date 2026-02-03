package com.porashona.studymaster.service;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u0000 \"2\u00020\u0001:\u0001\"B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0005H\u0002J\u0006\u0010\u0011\u001a\u00020\u000fJ\u0006\u0010\u0012\u001a\u00020\u000fJ\u0010\u0010\u0013\u001a\u00020\u00052\u0006\u0010\u0010\u001a\u00020\u0005H\u0002J\u0006\u0010\u0014\u001a\u00020\u0007J\b\u0010\u0015\u001a\u00020\u000fH\u0002J\b\u0010\u0016\u001a\u00020\u000fH\u0002J\u0012\u0010\u0017\u001a\u00020\u000f2\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0016J\b\u0010\u001a\u001a\u00020\u000fH\u0016J\b\u0010\u001b\u001a\u00020\u000fH\u0016J\b\u0010\u001c\u001a\u00020\u000fH\u0016J\b\u0010\u001d\u001a\u00020\u000fH\u0014J\u0010\u0010\u001e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0005H\u0002J\u0010\u0010\u001f\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0005H\u0002J\u0014\u0010 \u001a\u00020\u000f2\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006#"}, d2 = {"Lcom/porashona/studymaster/service/AppBlockerAccessibilityService;", "Landroid/accessibilityservice/AccessibilityService;", "()V", "blockedPackages", "", "", "isBlockingEnabled", "", "lastBlockTime", "", "lastBlockedPackage", "serviceScope", "Lkotlinx/coroutines/CoroutineScope;", "useRootBlocking", "blockApp", "", "packageName", "disableBlocking", "enableBlocking", "getAppName", "isBlocking", "loadBlockedApps", "loadSettings", "onAccessibilityEvent", "event", "Landroid/view/accessibility/AccessibilityEvent;", "onCreate", "onDestroy", "onInterrupt", "onServiceConnected", "recordBlockAttempt", "showBlockOverlay", "updateBlockedApps", "packages", "Companion", "app_debug"})
public final class AppBlockerAccessibilityService extends android.accessibilityservice.AccessibilityService {
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.CoroutineScope serviceScope = null;
    @org.jetbrains.annotations.NotNull
    private java.util.Set<java.lang.String> blockedPackages;
    private boolean isBlockingEnabled = false;
    private boolean useRootBlocking = false;
    @org.jetbrains.annotations.Nullable
    private java.lang.String lastBlockedPackage;
    private long lastBlockTime = 0L;
    @org.jetbrains.annotations.Nullable
    private static com.porashona.studymaster.service.AppBlockerAccessibilityService instance;
    private static boolean isServiceRunning = false;
    private static final long BLOCK_COOLDOWN = 1000L;
    @org.jetbrains.annotations.NotNull
    public static final com.porashona.studymaster.service.AppBlockerAccessibilityService.Companion Companion = null;
    
    public AppBlockerAccessibilityService() {
        super();
    }
    
    @java.lang.Override
    public void onCreate() {
    }
    
    @java.lang.Override
    protected void onServiceConnected() {
    }
    
    @java.lang.Override
    public void onAccessibilityEvent(@org.jetbrains.annotations.Nullable
    android.view.accessibility.AccessibilityEvent event) {
    }
    
    @java.lang.Override
    public void onInterrupt() {
    }
    
    @java.lang.Override
    public void onDestroy() {
    }
    
    private final void blockApp(java.lang.String packageName) {
    }
    
    private final void showBlockOverlay(java.lang.String packageName) {
    }
    
    private final java.lang.String getAppName(java.lang.String packageName) {
        return null;
    }
    
    private final void recordBlockAttempt(java.lang.String packageName) {
    }
    
    private final void loadBlockedApps() {
    }
    
    private final void loadSettings() {
    }
    
    public final void enableBlocking() {
    }
    
    public final void disableBlocking() {
    }
    
    public final void updateBlockedApps(@org.jetbrains.annotations.NotNull
    java.util.Set<java.lang.String> packages) {
    }
    
    public final boolean isBlocking() {
        return false;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\"\u0010\u0007\u001a\u0004\u0018\u00010\u00062\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006@BX\u0086\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u001e\u0010\u000b\u001a\u00020\n2\u0006\u0010\u0005\u001a\u00020\n@BX\u0086\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f\u00a8\u0006\r"}, d2 = {"Lcom/porashona/studymaster/service/AppBlockerAccessibilityService$Companion;", "", "()V", "BLOCK_COOLDOWN", "", "<set-?>", "Lcom/porashona/studymaster/service/AppBlockerAccessibilityService;", "instance", "getInstance", "()Lcom/porashona/studymaster/service/AppBlockerAccessibilityService;", "", "isServiceRunning", "()Z", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.Nullable
        public final com.porashona.studymaster.service.AppBlockerAccessibilityService getInstance() {
            return null;
        }
        
        public final boolean isServiceRunning() {
            return false;
        }
    }
}