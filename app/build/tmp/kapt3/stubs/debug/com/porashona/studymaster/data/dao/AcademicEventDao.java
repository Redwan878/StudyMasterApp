package com.porashona.studymaster.data.dao;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0003\bg\u0018\u00002\u00020\u0001J\u0019\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J\u0014\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\t0\bH\'J\u001b\u0010\n\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u000b\u001a\u00020\fH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\rJ\u001c\u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\t0\b2\u0006\u0010\u000f\u001a\u00020\fH\'J\u001c\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\t0\b2\u0006\u0010\u0011\u001a\u00020\u0012H\'J$\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\t0\b2\u0006\u0010\u0014\u001a\u00020\f2\u0006\u0010\u0015\u001a\u00020\fH\'J\u0014\u0010\u0016\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\t0\bH\'J\u001c\u0010\u0017\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\t0\b2\u0006\u0010\u0018\u001a\u00020\fH\'J\u0016\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001a0\b2\u0006\u0010\u0018\u001a\u00020\fH\'J\u0019\u0010\u001b\u001a\u00020\f2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J\u0019\u0010\u001c\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u001d"}, d2 = {"Lcom/porashona/studymaster/data/dao/AcademicEventDao;", "", "delete", "", "event", "Lcom/porashona/studymaster/data/model/AcademicEvent;", "(Lcom/porashona/studymaster/data/model/AcademicEvent;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllEvents", "Lkotlinx/coroutines/flow/Flow;", "", "getEventById", "id", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getEventsBySubject", "subjectId", "getEventsByType", "type", "Lcom/porashona/studymaster/data/model/EventType;", "getEventsInRange", "startDate", "endDate", "getHolidays", "getUpcomingEvents", "today", "getUpcomingEventsCount", "", "insert", "update", "app_debug"})
@androidx.room.Dao
public abstract interface AcademicEventDao {
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object insert(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.AcademicEvent event, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Update
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object update(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.AcademicEvent event, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Delete
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object delete(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.AcademicEvent event, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM academic_events ORDER BY date ASC")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.AcademicEvent>> getAllEvents();
    
    @androidx.room.Query(value = "SELECT * FROM academic_events WHERE id = :id")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getEventById(long id, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.porashona.studymaster.data.model.AcademicEvent> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM academic_events WHERE date >= :today ORDER BY date ASC")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.AcademicEvent>> getUpcomingEvents(long today);
    
    @androidx.room.Query(value = "SELECT * FROM academic_events WHERE date BETWEEN :startDate AND :endDate ORDER BY date ASC")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.AcademicEvent>> getEventsInRange(long startDate, long endDate);
    
    @androidx.room.Query(value = "SELECT * FROM academic_events WHERE eventType = :type ORDER BY date ASC")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.AcademicEvent>> getEventsByType(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.EventType type);
    
    @androidx.room.Query(value = "SELECT * FROM academic_events WHERE isHoliday = 1 ORDER BY date ASC")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.AcademicEvent>> getHolidays();
    
    @androidx.room.Query(value = "SELECT * FROM academic_events WHERE subjectId = :subjectId ORDER BY date ASC")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.AcademicEvent>> getEventsBySubject(long subjectId);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM academic_events WHERE date >= :today")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Integer> getUpcomingEventsCount(long today);
}