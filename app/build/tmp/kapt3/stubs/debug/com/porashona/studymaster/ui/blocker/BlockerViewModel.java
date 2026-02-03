package com.porashona.studymaster.ui.blocker;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\t\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u000e\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u000eJ\u000e\u0010\u001c\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u000eJ\u000e\u0010\u001d\u001a\u00020\u001a2\u0006\u0010\u001e\u001a\u00020\tJ\u000e\u0010\u001f\u001a\u00020\u001a2\u0006\u0010\u001e\u001a\u00020\tJ\u000e\u0010 \u001a\u00020\u001a2\u0006\u0010\u001e\u001a\u00020\tJ\u000e\u0010!\u001a\u00020\u001a2\u0006\u0010\u001e\u001a\u00020\tJ\u0016\u0010\"\u001a\u00020\u001a2\u0006\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020\tR\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000bR\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\t0\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000bR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\t0\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u000bR\u0019\u0010\u0014\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00150\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u000bR\u0017\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\t0\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u000b\u00a8\u0006&"}, d2 = {"Lcom/porashona/studymaster/ui/blocker/BlockerViewModel;", "Landroidx/lifecycle/ViewModel;", "blockedAppDao", "Lcom/porashona/studymaster/data/dao/BlockedAppDao;", "preferencesManager", "Lcom/porashona/studymaster/data/preferences/PreferencesManager;", "(Lcom/porashona/studymaster/data/dao/BlockedAppDao;Lcom/porashona/studymaster/data/preferences/PreferencesManager;)V", "autoBlock", "Lkotlinx/coroutines/flow/Flow;", "", "getAutoBlock", "()Lkotlinx/coroutines/flow/Flow;", "blockedApps", "", "Lcom/porashona/studymaster/data/model/BlockedApp;", "getBlockedApps", "blockerEnabled", "getBlockerEnabled", "strictMode", "getStrictMode", "totalBlockAttempts", "", "getTotalBlockAttempts", "useRoot", "getUseRoot", "addBlockedApp", "", "app", "removeBlockedApp", "setAutoBlock", "enabled", "setBlockerEnabled", "setStrictMode", "setUseRoot", "toggleAppBlocked", "packageName", "", "isBlocked", "app_debug"})
public final class BlockerViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull
    private final com.porashona.studymaster.data.dao.BlockedAppDao blockedAppDao = null;
    @org.jetbrains.annotations.NotNull
    private final com.porashona.studymaster.data.preferences.PreferencesManager preferencesManager = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.BlockedApp>> blockedApps = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.Flow<java.lang.Integer> totalBlockAttempts = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.Flow<java.lang.Boolean> blockerEnabled = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.Flow<java.lang.Boolean> strictMode = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.Flow<java.lang.Boolean> autoBlock = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.Flow<java.lang.Boolean> useRoot = null;
    
    public BlockerViewModel(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.dao.BlockedAppDao blockedAppDao, @org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.preferences.PreferencesManager preferencesManager) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.BlockedApp>> getBlockedApps() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.lang.Integer> getTotalBlockAttempts() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.lang.Boolean> getBlockerEnabled() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.lang.Boolean> getStrictMode() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.lang.Boolean> getAutoBlock() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.lang.Boolean> getUseRoot() {
        return null;
    }
    
    public final void addBlockedApp(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.BlockedApp app) {
    }
    
    public final void removeBlockedApp(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.BlockedApp app) {
    }
    
    public final void toggleAppBlocked(@org.jetbrains.annotations.NotNull
    java.lang.String packageName, boolean isBlocked) {
    }
    
    public final void setBlockerEnabled(boolean enabled) {
    }
    
    public final void setStrictMode(boolean enabled) {
    }
    
    public final void setAutoBlock(boolean enabled) {
    }
    
    public final void setUseRoot(boolean enabled) {
    }
}