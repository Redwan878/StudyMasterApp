package com.porashona.studymaster.data.dao

import androidx.room.*
import com.porashona.studymaster.data.model.Goal
import com.porashona.studymaster.data.model.GoalType
import kotlinx.coroutines.flow.Flow

@Dao
interface GoalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(goal: Goal): Long

    @Update
    suspend fun update(goal: Goal)

    @Delete
    suspend fun delete(goal: Goal)

    @Query("SELECT * FROM goals ORDER BY createdAt DESC")
    fun getAllGoals(): Flow<List<Goal>>

    @Query("SELECT * FROM goals WHERE id = :id")
    suspend fun getGoalById(id: Long): Goal?

    @Query("SELECT * FROM goals WHERE date = :date AND goalType = :type")
    fun getGoalsForDate(date: String, type: GoalType = GoalType.DAILY): Flow<List<Goal>>

    @Query("SELECT * FROM goals WHERE date = :date")
    fun getDailyGoals(date: String): Flow<List<Goal>>

    @Query("SELECT * FROM goals WHERE isCompleted = 0 ORDER BY createdAt DESC")
    fun getActiveGoals(): Flow<List<Goal>>

    @Query("UPDATE goals SET currentMinutes = currentMinutes + :minutes WHERE id = :goalId")
    suspend fun addMinutesToGoal(goalId: Long, minutes: Int)

    @Query("UPDATE goals SET isCompleted = 1, completedAt = :completedAt WHERE id = :goalId")
    suspend fun markAsCompleted(goalId: Long, completedAt: Long = System.currentTimeMillis())

    @Query("SELECT COUNT(*) FROM goals WHERE isCompleted = 1 AND date >= :startDate")
    fun getCompletedGoalsCount(startDate: String): Flow<Int>

    @Query("DELETE FROM goals WHERE date < :date AND isCompleted = 1")
    suspend fun deleteOldCompletedGoals(date: String)
}