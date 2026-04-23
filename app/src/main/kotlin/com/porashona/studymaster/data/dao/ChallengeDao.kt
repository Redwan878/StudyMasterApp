package com.porashona.studymaster.data.dao

import androidx.room.*
import com.porashona.studymaster.data.model.Challenge
import kotlinx.coroutines.flow.Flow

@Dao
interface ChallengeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(challenge: Challenge)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(challenges: List<Challenge>)

    @Update
    suspend fun update(challenge: Challenge)

    @Delete
    suspend fun delete(challenge: Challenge)

    @Query("SELECT * FROM challenges WHERE date = :date AND isActive = 1")
    fun getDailyChallenges(date: String): Flow<List<Challenge>>

    @Query("SELECT * FROM challenges WHERE id = :id")
    suspend fun getChallengeById(id: String): Challenge?

    @Query("SELECT * FROM challenges WHERE isCompleted = 1 ORDER BY completedAt DESC")
    fun getCompletedChallenges(): Flow<List<Challenge>>

    @Query("UPDATE challenges SET currentValue = :value WHERE id = :id")
    suspend fun updateProgress(id: String, value: Int)

    @Query("UPDATE challenges SET isCompleted = 1, completedAt = :completedAt WHERE id = :id")
    suspend fun markAsCompleted(id: String, completedAt: Long = System.currentTimeMillis())

    @Query("SELECT COUNT(*) FROM challenges WHERE isCompleted = 1 AND date = :date")
    fun getCompletedCountForDate(date: String): Flow<Int>

    @Query("DELETE FROM challenges WHERE date < :date")
    suspend fun deleteOldChallenges(date: String)

    @Query("SELECT * FROM challenges ORDER BY date DESC")
    fun getAllChallenges(): Flow<List<Challenge>>
}