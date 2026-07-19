package com.porashona.studymaster.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.porashona.studymaster.data.model.FocusHistory
import com.porashona.studymaster.data.model.FocusLevel
import kotlinx.coroutines.flow.Flow

@Dao
interface FocusLevelDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFocusLevel(level: FocusLevel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFocusHistory(history: FocusHistory)

    @Query("SELECT * FROM focus_levels")
    fun getAllFocusLevels(): Flow<List<FocusLevel>>

    @Query("SELECT * FROM focus_history WHERE sessionId = :sessionId")
    suspend fun getFocusHistoryForSession(sessionId: Long): FocusHistory?

    @Query("SELECT * FROM focus_history ORDER BY recordedAt DESC LIMIT :limit")
    fun getRecentFocusHistory(limit: Int = 50): Flow<List<FocusHistory>>

    @Query("SELECT AVG(focusScore) FROM focus_history WHERE recordedAt >= :startDate")
    suspend fun getAverageFocusScore(startDate: Long): Double?

    @Query("SELECT AVG(focusScore) FROM focus_history")
    suspend fun getOverallAverageFocusScore(): Double?

    @Query("SELECT * FROM focus_history ORDER BY focusScore DESC LIMIT :limit")
    fun getTopFocusSessions(limit: Int = 10): Flow<List<FocusHistory>>

    @Query("SELECT * FROM focus_history WHERE focusLevel = :level ORDER BY recordedAt DESC")
    fun getHistoryByLevel(level: Int): Flow<List<FocusHistory>>

    @Query("DELETE FROM focus_history WHERE id = :id")
    suspend fun deleteFocusHistory(id: Int)

    @Query("DELETE FROM focus_history WHERE recordedAt < :cutoff")
    suspend fun deleteOldHistory(cutoff: Long)

    @Query("SELECT COUNT(*) FROM focus_history WHERE focusLevel = :level")
    suspend fun getCountByLevel(level: Int): Int

    @Query("SELECT * FROM focus_history ORDER BY recordedAt DESC LIMIT 1")
    suspend fun getLastFocusHistory(): FocusHistory?
}