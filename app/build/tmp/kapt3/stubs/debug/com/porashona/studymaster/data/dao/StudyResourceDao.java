package com.porashona.studymaster.data.dao;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\bg\u0018\u00002\u00020\u0001J\u0019\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J\u0014\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\t0\bH\'J\u0014\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\t0\bH\'J\u001b\u0010\u000b\u001a\u0004\u0018\u00010\u00052\u0006\u0010\f\u001a\u00020\rH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000eJ\u001c\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\t0\b2\u0006\u0010\u0010\u001a\u00020\rH\'J\u001c\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\t0\b2\u0006\u0010\u0012\u001a\u00020\u0013H\'J#\u0010\u0014\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u0015\u001a\u00020\rH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0016J\u0019\u0010\u0017\u001a\u00020\r2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J\u001c\u0010\u0018\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\t0\b2\u0006\u0010\u0019\u001a\u00020\u001aH\'J!\u0010\u001b\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u001c\u001a\u00020\u001dH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001eJ\u0019\u0010\u001f\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006 "}, d2 = {"Lcom/porashona/studymaster/data/dao/StudyResourceDao;", "", "delete", "", "resource", "Lcom/porashona/studymaster/data/model/StudyResource;", "(Lcom/porashona/studymaster/data/model/StudyResource;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllResources", "Lkotlinx/coroutines/flow/Flow;", "", "getFavoriteResources", "getResourceById", "id", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getResourcesBySubject", "subjectId", "getResourcesByType", "type", "Lcom/porashona/studymaster/data/model/ResourceType;", "incrementVisitCount", "time", "(JJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insert", "searchResources", "query", "", "setFavorite", "isFavorite", "", "(JZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "update", "app_debug"})
@androidx.room.Dao
public abstract interface StudyResourceDao {
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object insert(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.StudyResource resource, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Update
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object update(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.StudyResource resource, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Delete
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object delete(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.StudyResource resource, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM study_resources ORDER BY lastVisitedAt DESC, createdAt DESC")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.StudyResource>> getAllResources();
    
    @androidx.room.Query(value = "SELECT * FROM study_resources WHERE id = :id")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getResourceById(long id, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.porashona.studymaster.data.model.StudyResource> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM study_resources WHERE subjectId = :subjectId ORDER BY visitCount DESC")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.StudyResource>> getResourcesBySubject(long subjectId);
    
    @androidx.room.Query(value = "SELECT * FROM study_resources WHERE type = :type ORDER BY visitCount DESC")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.StudyResource>> getResourcesByType(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.ResourceType type);
    
    @androidx.room.Query(value = "SELECT * FROM study_resources WHERE isFavorite = 1 ORDER BY visitCount DESC")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.StudyResource>> getFavoriteResources();
    
    @androidx.room.Query(value = "UPDATE study_resources SET visitCount = visitCount + 1, lastVisitedAt = :time WHERE id = :id")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object incrementVisitCount(long id, long time, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE study_resources SET isFavorite = :isFavorite WHERE id = :id")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object setFavorite(long id, boolean isFavorite, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM study_resources WHERE title LIKE \'%\' || :query || \'%\' OR description LIKE \'%\' || :query || \'%\'")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.StudyResource>> searchResources(@org.jetbrains.annotations.NotNull
    java.lang.String query);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}