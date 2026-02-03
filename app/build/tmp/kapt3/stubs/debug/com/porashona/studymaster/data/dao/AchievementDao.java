package com.porashona.studymaster.data.dao;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0010\t\n\u0002\b\u0006\bg\u0018\u00002\u00020\u0001J\u001b\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J\u0014\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\t0\bH\'J\u0014\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\t0\bH\'J\u000e\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\bH\'J\u0019\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0003H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0010J\u001f\u0010\u0011\u001a\u00020\u000e2\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00030\tH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0013J#\u0010\u0014\u001a\u00020\u000e2\u0006\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0015\u001a\u00020\u0016H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0017J\u0019\u0010\u0018\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0003H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0010J!\u0010\u0019\u001a\u00020\u000e2\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u001a\u001a\u00020\fH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001b\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u001c"}, d2 = {"Lcom/porashona/studymaster/data/dao/AchievementDao;", "", "getAchievementById", "Lcom/porashona/studymaster/data/model/Achievement;", "id", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllAchievements", "Lkotlinx/coroutines/flow/Flow;", "", "getUnlockedAchievements", "getUnlockedCount", "", "insert", "", "achievement", "(Lcom/porashona/studymaster/data/model/Achievement;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertAll", "achievements", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "unlockAchievement", "unlockedAt", "", "(Ljava/lang/String;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "update", "updateProgress", "progress", "(Ljava/lang/String;ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@androidx.room.Dao
public abstract interface AchievementDao {
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object insert(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.Achievement achievement, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object insertAll(@org.jetbrains.annotations.NotNull
    java.util.List<com.porashona.studymaster.data.model.Achievement> achievements, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Update
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object update(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.Achievement achievement, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM achievements ORDER BY isUnlocked DESC, id ASC")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.Achievement>> getAllAchievements();
    
    @androidx.room.Query(value = "SELECT * FROM achievements WHERE id = :id")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getAchievementById(@org.jetbrains.annotations.NotNull
    java.lang.String id, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.porashona.studymaster.data.model.Achievement> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM achievements WHERE isUnlocked = 1")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.Achievement>> getUnlockedAchievements();
    
    @androidx.room.Query(value = "UPDATE achievements SET isUnlocked = 1, unlockedAt = :unlockedAt WHERE id = :id")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object unlockAchievement(@org.jetbrains.annotations.NotNull
    java.lang.String id, long unlockedAt, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE achievements SET progress = :progress WHERE id = :id")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object updateProgress(@org.jetbrains.annotations.NotNull
    java.lang.String id, int progress, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM achievements WHERE isUnlocked = 1")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Integer> getUnlockedCount();
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}