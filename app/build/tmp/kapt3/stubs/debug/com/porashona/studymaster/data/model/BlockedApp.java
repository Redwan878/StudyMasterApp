package com.porashona.studymaster.data.model;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0002\b\u001b\b\u0087\b\u0018\u00002\u00020\u0001BI\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0006\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000b\u0012\b\b\u0002\u0010\f\u001a\u00020\u000b\u00a2\u0006\u0002\u0010\rJ\t\u0010\u0019\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001a\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001b\u001a\u00020\u0006H\u00c6\u0003J\t\u0010\u001c\u001a\u00020\u0006H\u00c6\u0003J\t\u0010\u001d\u001a\u00020\tH\u00c6\u0003J\u0010\u0010\u001e\u001a\u0004\u0018\u00010\u000bH\u00c6\u0003\u00a2\u0006\u0002\u0010\u0016J\t\u0010\u001f\u001a\u00020\u000bH\u00c6\u0003JV\u0010 \u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00062\b\b\u0002\u0010\b\u001a\u00020\t2\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000b2\b\b\u0002\u0010\f\u001a\u00020\u000bH\u00c6\u0001\u00a2\u0006\u0002\u0010!J\u0013\u0010\"\u001a\u00020\u00062\b\u0010#\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010$\u001a\u00020\tH\u00d6\u0001J\t\u0010%\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\f\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0014R\u0011\u0010\u0007\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\u0014R\u0015\u0010\n\u001a\u0004\u0018\u00010\u000b\u00a2\u0006\n\n\u0002\u0010\u0017\u001a\u0004\b\u0015\u0010\u0016R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0011\u00a8\u0006&"}, d2 = {"Lcom/porashona/studymaster/data/model/BlockedApp;", "", "packageName", "", "appName", "isBlocked", "", "isWhitelisted", "blockAttempts", "", "lastBlockedAt", "", "addedAt", "(Ljava/lang/String;Ljava/lang/String;ZZILjava/lang/Long;J)V", "getAddedAt", "()J", "getAppName", "()Ljava/lang/String;", "getBlockAttempts", "()I", "()Z", "getLastBlockedAt", "()Ljava/lang/Long;", "Ljava/lang/Long;", "getPackageName", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "(Ljava/lang/String;Ljava/lang/String;ZZILjava/lang/Long;J)Lcom/porashona/studymaster/data/model/BlockedApp;", "equals", "other", "hashCode", "toString", "app_debug"})
@androidx.room.Entity(tableName = "blocked_apps")
public final class BlockedApp {
    @androidx.room.PrimaryKey
    @org.jetbrains.annotations.NotNull
    private final java.lang.String packageName = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String appName = null;
    private final boolean isBlocked = false;
    private final boolean isWhitelisted = false;
    private final int blockAttempts = 0;
    @org.jetbrains.annotations.Nullable
    private final java.lang.Long lastBlockedAt = null;
    private final long addedAt = 0L;
    
    public BlockedApp(@org.jetbrains.annotations.NotNull
    java.lang.String packageName, @org.jetbrains.annotations.NotNull
    java.lang.String appName, boolean isBlocked, boolean isWhitelisted, int blockAttempts, @org.jetbrains.annotations.Nullable
    java.lang.Long lastBlockedAt, long addedAt) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getPackageName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getAppName() {
        return null;
    }
    
    public final boolean isBlocked() {
        return false;
    }
    
    public final boolean isWhitelisted() {
        return false;
    }
    
    public final int getBlockAttempts() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Long getLastBlockedAt() {
        return null;
    }
    
    public final long getAddedAt() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component2() {
        return null;
    }
    
    public final boolean component3() {
        return false;
    }
    
    public final boolean component4() {
        return false;
    }
    
    public final int component5() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Long component6() {
        return null;
    }
    
    public final long component7() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.porashona.studymaster.data.model.BlockedApp copy(@org.jetbrains.annotations.NotNull
    java.lang.String packageName, @org.jetbrains.annotations.NotNull
    java.lang.String appName, boolean isBlocked, boolean isWhitelisted, int blockAttempts, @org.jetbrains.annotations.Nullable
    java.lang.Long lastBlockedAt, long addedAt) {
        return null;
    }
    
    @java.lang.Override
    public boolean equals(@org.jetbrains.annotations.Nullable
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public java.lang.String toString() {
        return null;
    }
}