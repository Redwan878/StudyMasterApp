package com.porashona.studymaster.data.dao;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0006\bg\u0018\u00002\u00020\u0001J\u0019\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J\u0014\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\t0\bH\'J\u0014\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\t0\bH\'J\u001b\u0010\u000b\u001a\u0004\u0018\u00010\u00052\u0006\u0010\f\u001a\u00020\rH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000eJ\u001c\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\t0\b2\u0006\u0010\u0010\u001a\u00020\rH\'J\u001c\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\t0\b2\u0006\u0010\u0012\u001a\u00020\rH\'J\u000e\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\bH\'J\u0019\u0010\u0015\u001a\u00020\r2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J\u001c\u0010\u0016\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\t0\b2\u0006\u0010\u0017\u001a\u00020\u0018H\'J!\u0010\u0019\u001a\u00020\u00032\u0006\u0010\u001a\u001a\u00020\r2\u0006\u0010\u001b\u001a\u00020\u001cH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001dJ\u0019\u0010\u001e\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J#\u0010\u001f\u001a\u00020\u00032\u0006\u0010\u001a\u001a\u00020\r2\b\b\u0002\u0010 \u001a\u00020\rH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010!\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\""}, d2 = {"Lcom/porashona/studymaster/data/dao/NoteDao;", "", "delete", "", "note", "Lcom/porashona/studymaster/data/model/Note;", "(Lcom/porashona/studymaster/data/model/Note;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllNotes", "Lkotlinx/coroutines/flow/Flow;", "", "getFavoriteNotes", "getNoteById", "id", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getNotesBySession", "sessionId", "getNotesBySubject", "subjectId", "getNotesCount", "", "insert", "searchNotes", "query", "", "setFavorite", "noteId", "isFavorite", "", "(JZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "update", "updateTimestamp", "updatedAt", "(JJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@androidx.room.Dao
public abstract interface NoteDao {
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object insert(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.Note note, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Update
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object update(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.Note note, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Delete
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object delete(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.Note note, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM notes ORDER BY updatedAt DESC")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.Note>> getAllNotes();
    
    @androidx.room.Query(value = "SELECT * FROM notes WHERE id = :id")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getNoteById(long id, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.porashona.studymaster.data.model.Note> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM notes WHERE subjectId = :subjectId ORDER BY updatedAt DESC")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.Note>> getNotesBySubject(long subjectId);
    
    @androidx.room.Query(value = "SELECT * FROM notes WHERE sessionId = :sessionId")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.Note>> getNotesBySession(long sessionId);
    
    @androidx.room.Query(value = "SELECT * FROM notes WHERE isFavorite = 1 ORDER BY updatedAt DESC")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.Note>> getFavoriteNotes();
    
    @androidx.room.Query(value = "SELECT * FROM notes WHERE title LIKE \'%\' || :query || \'%\' OR content LIKE \'%\' || :query || \'%\' ORDER BY updatedAt DESC")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.Note>> searchNotes(@org.jetbrains.annotations.NotNull
    java.lang.String query);
    
    @androidx.room.Query(value = "UPDATE notes SET isFavorite = :isFavorite WHERE id = :noteId")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object setFavorite(long noteId, boolean isFavorite, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE notes SET updatedAt = :updatedAt WHERE id = :noteId")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object updateTimestamp(long noteId, long updatedAt, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM notes")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Integer> getNotesCount();
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}