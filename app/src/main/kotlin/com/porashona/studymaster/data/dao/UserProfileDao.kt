package com.porashona.studymaster.data.dao

import androidx.room.*
import com.porashona.studymaster.data.model.UserProfile
import kotlinx.coroutines.flow.Flow

@Dao
interface UserProfileDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(profile: UserProfile)

    @Update
    suspend fun update(profile: UserProfile)

    @Query("SELECT * FROM user_profile WHERE id = 1")
    fun getProfile(): Flow<UserProfile?>

    @Query("SELECT * FROM user_profile WHERE id = 1")
    suspend fun getProfileSync(): UserProfile?

    @Query("UPDATE user_profile SET totalXp = totalXp + :xp, level = (totalXp + :xp) / 1000 + 1 WHERE id = 1")
    suspend fun addXp(xp: Int)

    @Query("UPDATE user_profile SET currentStreak = :streak, longestStreak = CASE WHEN :streak > longestStreak THEN :streak ELSE longestStreak END WHERE id = 1")
    suspend fun updateStreak(streak: Int)

    @Query("UPDATE user_profile SET totalStudyTimeSeconds = totalStudyTimeSeconds + :seconds, totalSessions = totalSessions + 1, lastStudyDate = :date WHERE id = 1")
    suspend fun addStudyTime(seconds: Long, date: Long = System.currentTimeMillis())

    @Query("UPDATE user_profile SET name = :name WHERE id = 1")
    suspend fun updateName(name: String)
}