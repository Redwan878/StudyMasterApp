package com.porashona.studymaster.data.dao;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\t\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0007\bg\u0018\u00002\u00020\u0001J\u0019\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J\u0014\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\t0\bH\'J\u0014\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\t0\bH\'J\u001b\u0010\u000b\u001a\u0004\u0018\u00010\u00052\u0006\u0010\f\u001a\u00020\rH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000eJ\u001c\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\t0\b2\u0006\u0010\u0010\u001a\u00020\rH\'J$\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\t0\b2\u0006\u0010\u0012\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\rH\'J\u001c\u0010\u0014\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\t0\b2\u0006\u0010\u0015\u001a\u00020\rH\'J\u0016\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00170\b2\u0006\u0010\u0015\u001a\u00020\rH\'J\u0019\u0010\u0018\u001a\u00020\r2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J-\u0010\u0019\u001a\u00020\u00032\u0006\u0010\u001a\u001a\u00020\r2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001cH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001eJ\u0019\u0010\u001f\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J!\u0010 \u001a\u00020\u00032\u0006\u0010\u001a\u001a\u00020\r2\u0006\u0010!\u001a\u00020\u0017H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\"\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006#"}, d2 = {"Lcom/porashona/studymaster/data/dao/ExamDao;", "", "delete", "", "exam", "Lcom/porashona/studymaster/data/model/Exam;", "(Lcom/porashona/studymaster/data/model/Exam;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllExams", "Lkotlinx/coroutines/flow/Flow;", "", "getCompletedExams", "getExamById", "id", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getExamsBySubject", "subjectId", "getExamsInRange", "startDate", "endDate", "getUpcomingExams", "today", "getUpcomingExamsCount", "", "insert", "markAsCompleted", "examId", "result", "", "reflection", "(JLjava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "update", "updateProgress", "progress", "(JILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@androidx.room.Dao
public abstract interface ExamDao {
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object insert(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.Exam exam, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Update
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object update(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.Exam exam, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Delete
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object delete(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.Exam exam, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM exams ORDER BY examDate ASC")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.Exam>> getAllExams();
    
    @androidx.room.Query(value = "SELECT * FROM exams WHERE id = :id")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getExamById(long id, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.porashona.studymaster.data.model.Exam> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM exams WHERE examDate >= :today AND isCompleted = 0 ORDER BY examDate ASC")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.Exam>> getUpcomingExams(long today);
    
    @androidx.room.Query(value = "SELECT * FROM exams WHERE isCompleted = 1 ORDER BY examDate DESC")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.Exam>> getCompletedExams();
    
    @androidx.room.Query(value = "SELECT * FROM exams WHERE subjectId = :subjectId ORDER BY examDate ASC")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.Exam>> getExamsBySubject(long subjectId);
    
    @androidx.room.Query(value = "SELECT * FROM exams WHERE examDate BETWEEN :startDate AND :endDate")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.Exam>> getExamsInRange(long startDate, long endDate);
    
    @androidx.room.Query(value = "UPDATE exams SET preparationProgress = :progress WHERE id = :examId")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object updateProgress(long examId, int progress, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE exams SET isCompleted = 1, result = :result, reflection = :reflection WHERE id = :examId")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object markAsCompleted(long examId, @org.jetbrains.annotations.Nullable
    java.lang.String result, @org.jetbrains.annotations.Nullable
    java.lang.String reflection, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM exams WHERE examDate >= :today AND isCompleted = 0")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Integer> getUpcomingExamsCount(long today);
}