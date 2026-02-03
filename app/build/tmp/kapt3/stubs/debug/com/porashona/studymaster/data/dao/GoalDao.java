package com.porashona.studymaster.data.dao;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0006\bg\u0018\u00002\u00020\u0001J!\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\bJ\u0019\u0010\t\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\fJ\u0019\u0010\r\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u000fH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0010J\u0014\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\u00130\u0012H\'J\u0014\u0010\u0014\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\u00130\u0012H\'J\u0016\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00070\u00122\u0006\u0010\u0016\u001a\u00020\u000fH\'J\u001c\u0010\u0017\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\u00130\u00122\u0006\u0010\u000e\u001a\u00020\u000fH\'J\u001b\u0010\u0018\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u0019\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001aJ&\u0010\u001b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\u00130\u00122\u0006\u0010\u000e\u001a\u00020\u000f2\b\b\u0002\u0010\u001c\u001a\u00020\u001dH\'J\u0019\u0010\u001e\u001a\u00020\u00052\u0006\u0010\n\u001a\u00020\u000bH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\fJ#\u0010\u001f\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010 \u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010!J\u0019\u0010\"\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\f\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006#"}, d2 = {"Lcom/porashona/studymaster/data/dao/GoalDao;", "", "addMinutesToGoal", "", "goalId", "", "minutes", "", "(JILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "delete", "goal", "Lcom/porashona/studymaster/data/model/Goal;", "(Lcom/porashona/studymaster/data/model/Goal;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteOldCompletedGoals", "date", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getActiveGoals", "Lkotlinx/coroutines/flow/Flow;", "", "getAllGoals", "getCompletedGoalsCount", "startDate", "getDailyGoals", "getGoalById", "id", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getGoalsForDate", "type", "Lcom/porashona/studymaster/data/model/GoalType;", "insert", "markAsCompleted", "completedAt", "(JJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "update", "app_debug"})
@androidx.room.Dao
public abstract interface GoalDao {
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object insert(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.Goal goal, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Update
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object update(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.Goal goal, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Delete
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object delete(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.Goal goal, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM goals ORDER BY createdAt DESC")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.Goal>> getAllGoals();
    
    @androidx.room.Query(value = "SELECT * FROM goals WHERE id = :id")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getGoalById(long id, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.porashona.studymaster.data.model.Goal> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM goals WHERE date = :date AND goalType = :type")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.Goal>> getGoalsForDate(@org.jetbrains.annotations.NotNull
    java.lang.String date, @org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.GoalType type);
    
    @androidx.room.Query(value = "SELECT * FROM goals WHERE date = :date")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.Goal>> getDailyGoals(@org.jetbrains.annotations.NotNull
    java.lang.String date);
    
    @androidx.room.Query(value = "SELECT * FROM goals WHERE isCompleted = 0 ORDER BY createdAt DESC")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.Goal>> getActiveGoals();
    
    @androidx.room.Query(value = "UPDATE goals SET currentMinutes = currentMinutes + :minutes WHERE id = :goalId")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object addMinutesToGoal(long goalId, int minutes, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE goals SET isCompleted = 1, completedAt = :completedAt WHERE id = :goalId")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object markAsCompleted(long goalId, long completedAt, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM goals WHERE isCompleted = 1 AND date >= :startDate")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Integer> getCompletedGoalsCount(@org.jetbrains.annotations.NotNull
    java.lang.String startDate);
    
    @androidx.room.Query(value = "DELETE FROM goals WHERE date < :date AND isCompleted = 1")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object deleteOldCompletedGoals(@org.jetbrains.annotations.NotNull
    java.lang.String date, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}