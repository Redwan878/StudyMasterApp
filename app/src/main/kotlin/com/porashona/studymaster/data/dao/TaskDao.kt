package com.porashona.studymaster.data.dao

import androidx.room.*
import com.porashona.studymaster.data.model.Task
import com.porashona.studymaster.data.model.TaskPriority
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task): Long

    @Update
    suspend fun update(task: Task)

    @Delete
    suspend fun delete(task: Task)

    @Query("SELECT * FROM tasks WHERE parentTaskId IS NULL ORDER BY isCompleted ASC, priority DESC, dueDate ASC")
    fun getAllTasks(): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE id = :id")
    suspend fun getTaskById(id: Long): Task?

    @Query("SELECT * FROM tasks WHERE parentTaskId = :parentId ORDER BY isCompleted ASC")
    fun getSubtasks(parentId: Long): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE isCompleted = 0 AND parentTaskId IS NULL ORDER BY priority DESC, dueDate ASC")
    fun getPendingTasks(): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE isCompleted = 1 ORDER BY completedAt DESC")
    fun getCompletedTasks(): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE dueDate <= :date AND isCompleted = 0")
    fun getOverdueTasks(date: Long): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE dueDate BETWEEN :startDate AND :endDate AND isCompleted = 0")
    fun getTasksForDateRange(startDate: Long, endDate: Long): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE subjectId = :subjectId AND isCompleted = 0")
    fun getTasksBySubject(subjectId: Long): Flow<List<Task>>

    @Query("UPDATE tasks SET isCompleted = 1, completedAt = :completedAt WHERE id = :taskId")
    suspend fun markAsCompleted(taskId: Long, completedAt: Long = System.currentTimeMillis())

    @Query("UPDATE tasks SET isCompleted = 0, completedAt = NULL WHERE id = :taskId")
    suspend fun markAsIncomplete(taskId: Long)

    @Query("SELECT COUNT(*) FROM tasks WHERE isCompleted = 0")
    fun getPendingTasksCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM tasks WHERE isCompleted = 1 AND completedAt >= :since")
    fun getCompletedTasksCountSince(since: Long): Flow<Int>

    @Query("DELETE FROM tasks WHERE isCompleted = 1 AND completedAt < :before")
    suspend fun deleteOldCompletedTasks(before: Long)
}