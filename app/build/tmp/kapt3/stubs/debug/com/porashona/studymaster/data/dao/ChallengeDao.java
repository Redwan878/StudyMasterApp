package com.porashona.studymaster.data.dao;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\t\n\u0002\b\u0006\bg\u0018\u00002\u00020\u0001J\u0019\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J\u0019\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\tH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\nJ\u001b\u0010\u000b\u001a\u0004\u0018\u00010\u00052\u0006\u0010\f\u001a\u00020\tH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\nJ\u0014\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u000f0\u000eH\'J\u0016\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\u000e2\u0006\u0010\b\u001a\u00020\tH\'J\u001c\u0010\u0012\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u000f0\u000e2\u0006\u0010\b\u001a\u00020\tH\'J\u0019\u0010\u0013\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J\u001f\u0010\u0014\u001a\u00020\u00032\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00050\u000fH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0016J#\u0010\u0017\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\t2\b\b\u0002\u0010\u0018\u001a\u00020\u0019H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001aJ\u0019\u0010\u001b\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J!\u0010\u001c\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\t2\u0006\u0010\u001d\u001a\u00020\u0011H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001e\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u001f"}, d2 = {"Lcom/porashona/studymaster/data/dao/ChallengeDao;", "", "delete", "", "challenge", "Lcom/porashona/studymaster/data/model/Challenge;", "(Lcom/porashona/studymaster/data/model/Challenge;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteOldChallenges", "date", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getChallengeById", "id", "getCompletedChallenges", "Lkotlinx/coroutines/flow/Flow;", "", "getCompletedCountForDate", "", "getDailyChallenges", "insert", "insertAll", "challenges", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "markAsCompleted", "completedAt", "", "(Ljava/lang/String;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "update", "updateProgress", "value", "(Ljava/lang/String;ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@androidx.room.Dao
public abstract interface ChallengeDao {
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object insert(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.Challenge challenge, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object insertAll(@org.jetbrains.annotations.NotNull
    java.util.List<com.porashona.studymaster.data.model.Challenge> challenges, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Update
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object update(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.Challenge challenge, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Delete
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object delete(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.Challenge challenge, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM challenges WHERE date = :date AND isActive = 1")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.Challenge>> getDailyChallenges(@org.jetbrains.annotations.NotNull
    java.lang.String date);
    
    @androidx.room.Query(value = "SELECT * FROM challenges WHERE id = :id")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getChallengeById(@org.jetbrains.annotations.NotNull
    java.lang.String id, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.porashona.studymaster.data.model.Challenge> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM challenges WHERE isCompleted = 1 ORDER BY completedAt DESC")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.Challenge>> getCompletedChallenges();
    
    @androidx.room.Query(value = "UPDATE challenges SET currentValue = :value WHERE id = :id")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object updateProgress(@org.jetbrains.annotations.NotNull
    java.lang.String id, int value, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE challenges SET isCompleted = 1, completedAt = :completedAt WHERE id = :id")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object markAsCompleted(@org.jetbrains.annotations.NotNull
    java.lang.String id, long completedAt, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM challenges WHERE isCompleted = 1 AND date = :date")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Integer> getCompletedCountForDate(@org.jetbrains.annotations.NotNull
    java.lang.String date);
    
    @androidx.room.Query(value = "DELETE FROM challenges WHERE date < :date")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object deleteOldChallenges(@org.jetbrains.annotations.NotNull
    java.lang.String date, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}