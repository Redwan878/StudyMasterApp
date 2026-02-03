package com.porashona.studymaster.data.dao;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000e\n\u0002\b\u0004\bg\u0018\u00002\u00020\u0001J#\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0007J\u0019\u0010\b\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\nH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000bJ\u0010\u0010\f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000e0\rH\'J\u0013\u0010\u000f\u001a\u0004\u0018\u00010\u000eH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0010J\u0019\u0010\u0011\u001a\u00020\u00032\u0006\u0010\u0012\u001a\u00020\u000eH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0013J\u0019\u0010\u0014\u001a\u00020\u00032\u0006\u0010\u0012\u001a\u00020\u000eH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0013J\u0019\u0010\u0015\u001a\u00020\u00032\u0006\u0010\u0016\u001a\u00020\u0017H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0018J\u0019\u0010\u0019\u001a\u00020\u00032\u0006\u0010\u001a\u001a\u00020\nH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000b\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u001b"}, d2 = {"Lcom/porashona/studymaster/data/dao/UserProfileDao;", "", "addStudyTime", "", "seconds", "", "date", "(JJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "addXp", "xp", "", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getProfile", "Lkotlinx/coroutines/flow/Flow;", "Lcom/porashona/studymaster/data/model/UserProfile;", "getProfileSync", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insert", "profile", "(Lcom/porashona/studymaster/data/model/UserProfile;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "update", "updateName", "name", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateStreak", "streak", "app_debug"})
@androidx.room.Dao
public abstract interface UserProfileDao {
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object insert(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.UserProfile profile, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Update
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object update(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.UserProfile profile, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM user_profile WHERE id = 1")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<com.porashona.studymaster.data.model.UserProfile> getProfile();
    
    @androidx.room.Query(value = "SELECT * FROM user_profile WHERE id = 1")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getProfileSync(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.porashona.studymaster.data.model.UserProfile> $completion);
    
    @androidx.room.Query(value = "UPDATE user_profile SET totalXp = totalXp + :xp, level = (totalXp + :xp) / 1000 + 1 WHERE id = 1")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object addXp(int xp, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE user_profile SET currentStreak = :streak, longestStreak = CASE WHEN :streak > longestStreak THEN :streak ELSE longestStreak END WHERE id = 1")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object updateStreak(int streak, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE user_profile SET totalStudyTimeSeconds = totalStudyTimeSeconds + :seconds, totalSessions = totalSessions + 1, lastStudyDate = :date WHERE id = 1")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object addStudyTime(long seconds, long date, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE user_profile SET name = :name WHERE id = 1")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object updateName(@org.jetbrains.annotations.NotNull
    java.lang.String name, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}