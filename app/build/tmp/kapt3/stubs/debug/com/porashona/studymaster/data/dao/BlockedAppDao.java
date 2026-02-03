package com.porashona.studymaster.data.dao;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0005\bg\u0018\u00002\u00020\u0001J\u0019\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J\u0019\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\tH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\nJ\u0014\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\r0\fH\'J\u0014\u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\r0\fH\'J\u0016\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\f2\u0006\u0010\u0011\u001a\u00020\tH\'J\u001b\u0010\u0012\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0013\u001a\u00020\u0014H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0015J\u001c\u0010\u0016\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00170\r0\f2\u0006\u0010\u0011\u001a\u00020\tH\'J\u001e\u0010\u0018\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00190\r0\f2\b\b\u0002\u0010\u001a\u001a\u00020\u0010H\'J\u0010\u0010\u001b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00100\fH\'J\u0014\u0010\u001c\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\r0\fH\'J#\u0010\u001d\u001a\u00020\u00032\u0006\u0010\u0013\u001a\u00020\u00142\b\b\u0002\u0010\u001e\u001a\u00020\tH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001fJ\u0019\u0010 \u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J\u001f\u0010!\u001a\u00020\u00032\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00050\rH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010#J\u0019\u0010$\u001a\u00020\u00032\u0006\u0010%\u001a\u00020\u0019H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010&J!\u0010\'\u001a\u00020\u00032\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010(\u001a\u00020)H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010*J!\u0010+\u001a\u00020\u00032\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010,\u001a\u00020)H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010*J\u0019\u0010-\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006."}, d2 = {"Lcom/porashona/studymaster/data/dao/BlockedAppDao;", "", "delete", "", "app", "Lcom/porashona/studymaster/data/model/BlockedApp;", "(Lcom/porashona/studymaster/data/model/BlockedApp;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteOldStats", "before", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getActiveBlockedApps", "Lkotlinx/coroutines/flow/Flow;", "", "getAllBlockedApps", "getBlockCountSince", "", "since", "getByPackageName", "packageName", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getMostBlockedApps", "Lcom/porashona/studymaster/data/dao/AppBlockCount;", "getRecentBlockStats", "Lcom/porashona/studymaster/data/model/BlockStatistic;", "limit", "getTotalBlockAttempts", "getWhitelistedApps", "incrementBlockAttempt", "time", "(Ljava/lang/String;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insert", "insertAll", "apps", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertBlockStat", "stat", "(Lcom/porashona/studymaster/data/model/BlockStatistic;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setBlocked", "isBlocked", "", "(Ljava/lang/String;ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setWhitelisted", "isWhitelisted", "update", "app_debug"})
@androidx.room.Dao
public abstract interface BlockedAppDao {
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object insert(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.BlockedApp app, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object insertAll(@org.jetbrains.annotations.NotNull
    java.util.List<com.porashona.studymaster.data.model.BlockedApp> apps, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Update
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object update(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.BlockedApp app, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Delete
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object delete(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.BlockedApp app, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM blocked_apps ORDER BY appName ASC")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.BlockedApp>> getAllBlockedApps();
    
    @androidx.room.Query(value = "SELECT * FROM blocked_apps WHERE isBlocked = 1 AND isWhitelisted = 0")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.BlockedApp>> getActiveBlockedApps();
    
    @androidx.room.Query(value = "SELECT * FROM blocked_apps WHERE packageName = :packageName")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getByPackageName(@org.jetbrains.annotations.NotNull
    java.lang.String packageName, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.porashona.studymaster.data.model.BlockedApp> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM blocked_apps WHERE isWhitelisted = 1")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.BlockedApp>> getWhitelistedApps();
    
    @androidx.room.Query(value = "UPDATE blocked_apps SET isBlocked = :isBlocked WHERE packageName = :packageName")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object setBlocked(@org.jetbrains.annotations.NotNull
    java.lang.String packageName, boolean isBlocked, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE blocked_apps SET isWhitelisted = :isWhitelisted WHERE packageName = :packageName")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object setWhitelisted(@org.jetbrains.annotations.NotNull
    java.lang.String packageName, boolean isWhitelisted, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE blocked_apps SET blockAttempts = blockAttempts + 1, lastBlockedAt = :time WHERE packageName = :packageName")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object incrementBlockAttempt(@org.jetbrains.annotations.NotNull
    java.lang.String packageName, long time, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT SUM(blockAttempts) FROM blocked_apps")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Integer> getTotalBlockAttempts();
    
    @androidx.room.Insert
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object insertBlockStat(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.BlockStatistic stat, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM block_statistics ORDER BY blockedAt DESC LIMIT :limit")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.BlockStatistic>> getRecentBlockStats(int limit);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM block_statistics WHERE blockedAt >= :since")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Integer> getBlockCountSince(long since);
    
    @androidx.room.Query(value = "SELECT packageName, COUNT(*) as count FROM block_statistics WHERE blockedAt >= :since GROUP BY packageName ORDER BY count DESC")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.dao.AppBlockCount>> getMostBlockedApps(long since);
    
    @androidx.room.Query(value = "DELETE FROM block_statistics WHERE blockedAt < :before")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object deleteOldStats(long before, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}