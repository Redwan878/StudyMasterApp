package com.porashona.studymaster.data.dao;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\bg\u0018\u00002\u00020\u0001J\u0019\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J\u0011\u0010\u0007\u001a\u00020\u0003H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\bJ\u0014\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u000b0\nH\'J\u001b\u0010\f\u001a\u0004\u0018\u00010\u00052\u0006\u0010\r\u001a\u00020\u000eH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000fJ$\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u000b0\n2\u0006\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\u000eH\'J\u001c\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u000b0\n2\u0006\u0010\u0014\u001a\u00020\u000eH\'J\u0014\u0010\u0015\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00160\u000b0\nH\'J\u0014\u0010\u0017\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00180\u000b0\nH\'J\u000e\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001a0\nH\'J\u0010\u0010\u001b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000e0\nH\'J\u0018\u0010\u001c\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000e0\n2\u0006\u0010\u0011\u001a\u00020\u000eH\'J\u0018\u0010\u001d\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000e0\n2\u0006\u0010\u001e\u001a\u00020\u000eH\'J\u0019\u0010\u001f\u001a\u00020\u000e2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J\u0019\u0010 \u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006!"}, d2 = {"Lcom/porashona/studymaster/data/dao/StudySessionDao;", "", "delete", "", "session", "Lcom/porashona/studymaster/data/model/StudySession;", "(Lcom/porashona/studymaster/data/model/StudySession;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteAll", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllSessions", "Lkotlinx/coroutines/flow/Flow;", "", "getSessionById", "id", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getSessionsBetween", "startTime", "endTime", "getSessionsForDate", "date", "getStudyDates", "", "getTimeBySubject", "Lcom/porashona/studymaster/data/dao/SubjectTime;", "getTotalSessionCount", "", "getTotalStudyTime", "getTotalStudyTimeSince", "getTotalTimeForSubject", "subjectId", "insert", "update", "app_debug"})
@androidx.room.Dao
public abstract interface StudySessionDao {
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object insert(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.StudySession session, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Update
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object update(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.StudySession session, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Delete
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object delete(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.StudySession session, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM study_sessions ORDER BY startTime DESC")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.StudySession>> getAllSessions();
    
    @androidx.room.Query(value = "SELECT * FROM study_sessions WHERE id = :id")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getSessionById(long id, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.porashona.studymaster.data.model.StudySession> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM study_sessions WHERE startTime >= :startTime AND startTime <= :endTime ORDER BY startTime DESC")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.StudySession>> getSessionsBetween(long startTime, long endTime);
    
    @androidx.room.Query(value = "SELECT * FROM study_sessions WHERE date(startTime/1000, \'unixepoch\', \'localtime\') = date(:date/1000, \'unixepoch\', \'localtime\')")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.StudySession>> getSessionsForDate(long date);
    
    @androidx.room.Query(value = "SELECT SUM(durationInSeconds) FROM study_sessions WHERE sessionType = \'WORK\'")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Long> getTotalStudyTime();
    
    @androidx.room.Query(value = "SELECT SUM(durationInSeconds) FROM study_sessions WHERE sessionType = \'WORK\' AND startTime >= :startTime")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Long> getTotalStudyTimeSince(long startTime);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM study_sessions WHERE sessionType = \'WORK\'")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Integer> getTotalSessionCount();
    
    @androidx.room.Query(value = "SELECT SUM(durationInSeconds) FROM study_sessions WHERE subjectId = :subjectId AND sessionType = \'WORK\'")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Long> getTotalTimeForSubject(long subjectId);
    
    @androidx.room.Query(value = "SELECT subjectName, SUM(durationInSeconds) as totalTime FROM study_sessions WHERE sessionType = \'WORK\' GROUP BY subjectId ORDER BY totalTime DESC")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.dao.SubjectTime>> getTimeBySubject();
    
    @androidx.room.Query(value = "SELECT DISTINCT date(startTime/1000, \'unixepoch\', \'localtime\') as studyDate FROM study_sessions WHERE sessionType = \'WORK\' ORDER BY studyDate DESC")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<java.lang.String>> getStudyDates();
    
    @androidx.room.Query(value = "DELETE FROM study_sessions")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object deleteAll(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}