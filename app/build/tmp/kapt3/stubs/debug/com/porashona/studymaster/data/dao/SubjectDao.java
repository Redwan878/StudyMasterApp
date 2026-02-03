package com.porashona.studymaster.data.dao;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0004\bg\u0018\u00002\u00020\u0001J!\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0007J\u0019\u0010\b\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\nH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000bJ\u0011\u0010\f\u001a\u00020\u0003H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\rJ\u0014\u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\u00100\u000fH\'J\u001b\u0010\u0011\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0012\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0013J\u001b\u0010\u0014\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0015\u001a\u00020\u0016H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0017J\u0019\u0010\u0018\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\nH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000bJ\u0019\u0010\u0019\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\nH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000b\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u001a"}, d2 = {"Lcom/porashona/studymaster/data/dao/SubjectDao;", "", "addTimeToSubject", "", "subjectId", "", "seconds", "(JJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "delete", "subject", "Lcom/porashona/studymaster/data/model/Subject;", "(Lcom/porashona/studymaster/data/model/Subject;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteAll", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllSubjects", "Lkotlinx/coroutines/flow/Flow;", "", "getSubjectById", "id", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getSubjectByName", "name", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insert", "update", "app_debug"})
@androidx.room.Dao
public abstract interface SubjectDao {
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object insert(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.Subject subject, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Update
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object update(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.Subject subject, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Delete
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object delete(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.Subject subject, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM subjects ORDER BY name ASC")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.Subject>> getAllSubjects();
    
    @androidx.room.Query(value = "SELECT * FROM subjects WHERE id = :id")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getSubjectById(long id, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.porashona.studymaster.data.model.Subject> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM subjects WHERE name = :name LIMIT 1")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getSubjectByName(@org.jetbrains.annotations.NotNull
    java.lang.String name, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.porashona.studymaster.data.model.Subject> $completion);
    
    @androidx.room.Query(value = "UPDATE subjects SET totalTimeInSeconds = totalTimeInSeconds + :seconds WHERE id = :subjectId")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object addTimeToSubject(long subjectId, long seconds, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM subjects")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object deleteAll(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}