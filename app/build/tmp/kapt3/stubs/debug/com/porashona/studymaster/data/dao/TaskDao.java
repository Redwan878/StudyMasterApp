package com.porashona.studymaster.data.dao;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0016\bg\u0018\u00002\u00020\u0001J\u0019\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J\u0019\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\tH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\nJ\u0014\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\r0\fH\'J\u0014\u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\r0\fH\'J\u0016\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\f2\u0006\u0010\u0011\u001a\u00020\tH\'J\u001c\u0010\u0012\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\r0\f2\u0006\u0010\u0013\u001a\u00020\tH\'J\u0014\u0010\u0014\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\r0\fH\'J\u000e\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00100\fH\'J\u001c\u0010\u0016\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\r0\f2\u0006\u0010\u0017\u001a\u00020\tH\'J\u001b\u0010\u0018\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0019\u001a\u00020\tH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\nJ\u001c\u0010\u001a\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\r0\f2\u0006\u0010\u001b\u001a\u00020\tH\'J$\u0010\u001c\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\r0\f2\u0006\u0010\u001d\u001a\u00020\t2\u0006\u0010\u001e\u001a\u00020\tH\'J\u0019\u0010\u001f\u001a\u00020\t2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J#\u0010 \u001a\u00020\u00032\u0006\u0010!\u001a\u00020\t2\b\b\u0002\u0010\"\u001a\u00020\tH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010#J\u0019\u0010$\u001a\u00020\u00032\u0006\u0010!\u001a\u00020\tH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\nJ\u0019\u0010%\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006&"}, d2 = {"Lcom/porashona/studymaster/data/dao/TaskDao;", "", "delete", "", "task", "Lcom/porashona/studymaster/data/model/Task;", "(Lcom/porashona/studymaster/data/model/Task;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteOldCompletedTasks", "before", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllTasks", "Lkotlinx/coroutines/flow/Flow;", "", "getCompletedTasks", "getCompletedTasksCountSince", "", "since", "getOverdueTasks", "date", "getPendingTasks", "getPendingTasksCount", "getSubtasks", "parentId", "getTaskById", "id", "getTasksBySubject", "subjectId", "getTasksForDateRange", "startDate", "endDate", "insert", "markAsCompleted", "taskId", "completedAt", "(JJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "markAsIncomplete", "update", "app_debug"})
@androidx.room.Dao
public abstract interface TaskDao {
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object insert(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.Task task, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Update
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object update(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.Task task, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Delete
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object delete(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.Task task, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM tasks WHERE parentTaskId IS NULL ORDER BY isCompleted ASC, priority DESC, dueDate ASC")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.Task>> getAllTasks();
    
    @androidx.room.Query(value = "SELECT * FROM tasks WHERE id = :id")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getTaskById(long id, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.porashona.studymaster.data.model.Task> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM tasks WHERE parentTaskId = :parentId ORDER BY isCompleted ASC")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.Task>> getSubtasks(long parentId);
    
    @androidx.room.Query(value = "SELECT * FROM tasks WHERE isCompleted = 0 AND parentTaskId IS NULL ORDER BY priority DESC, dueDate ASC")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.Task>> getPendingTasks();
    
    @androidx.room.Query(value = "SELECT * FROM tasks WHERE isCompleted = 1 ORDER BY completedAt DESC")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.Task>> getCompletedTasks();
    
    @androidx.room.Query(value = "SELECT * FROM tasks WHERE dueDate <= :date AND isCompleted = 0")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.Task>> getOverdueTasks(long date);
    
    @androidx.room.Query(value = "SELECT * FROM tasks WHERE dueDate BETWEEN :startDate AND :endDate AND isCompleted = 0")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.Task>> getTasksForDateRange(long startDate, long endDate);
    
    @androidx.room.Query(value = "SELECT * FROM tasks WHERE subjectId = :subjectId AND isCompleted = 0")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.Task>> getTasksBySubject(long subjectId);
    
    @androidx.room.Query(value = "UPDATE tasks SET isCompleted = 1, completedAt = :completedAt WHERE id = :taskId")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object markAsCompleted(long taskId, long completedAt, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE tasks SET isCompleted = 0, completedAt = NULL WHERE id = :taskId")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object markAsIncomplete(long taskId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM tasks WHERE isCompleted = 0")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Integer> getPendingTasksCount();
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM tasks WHERE isCompleted = 1 AND completedAt >= :since")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Integer> getCompletedTasksCountSince(long since);
    
    @androidx.room.Query(value = "DELETE FROM tasks WHERE isCompleted = 1 AND completedAt < :before")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object deleteOldCompletedTasks(long before, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}