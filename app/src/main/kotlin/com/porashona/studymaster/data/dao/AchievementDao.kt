package com.porashona.studymaster.data.dao

import androidx.room.*
import com.porashona.studymaster.data.model.Achievement
import kotlinx.coroutines.flow.Flow

@Dao
interface AchievementDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(achievement: Achievement)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(achievements: List<Achievement>)

    @Update
    suspend fun update(achievement: Achievement)

    @Query("SELECT * FROM achievements ORDER BY isUnlocked DESC, id ASC")
    fun getAllAchievements(): Flow<List<Achievement>>

    @Query("SELECT * FROM achievements WHERE id = :id")
    suspend fun getAchievementById(id: String): Achievement?

    @Query("SELECT * FROM achievements WHERE isUnlocked = 1")
    fun getUnlockedAchievements(): Flow<List<Achievement>>

    @Query("UPDATE achievements SET isUnlocked = 1, unlockedAt = :unlockedAt WHERE id = :id")
    suspend fun unlockAchievement(id: String, unlockedAt: Long = System.currentTimeMillis())

    @Query("UPDATE achievements SET progress = :progress WHERE id = :id")
    suspend fun updateProgress(id: String, progress: Int)

    @Query("SELECT COUNT(*) FROM achievements WHERE isUnlocked = 1")
    fun getUnlockedCount(): Flow<Int>

    @Query("DELETE FROM achievements")
    suspend fun deleteAll()
}