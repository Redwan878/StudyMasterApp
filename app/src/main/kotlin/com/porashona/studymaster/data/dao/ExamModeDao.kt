package com.porashona.studymaster.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.porashona.studymaster.data.model.ExamMode
import kotlinx.coroutines.flow.Flow

@Dao
interface ExamModeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(mode: ExamMode)

    @Update
    suspend fun update(mode: ExamMode)

    @Query("SELECT * FROM exam_modes WHERE id = :id")
    suspend fun getModeById(id: Int): ExamMode?

    @Query("SELECT * FROM exam_modes WHERE isActive = 1")
    fun getActiveMode(): Flow<ExamMode?>

    @Query("SELECT * FROM exam_modes ORDER BY focusIntensity DESC")
    fun getAllModes(): Flow<List<ExamMode>>

    @Query("SELECT * FROM exam_modes WHERE isActive = 1 LIMIT 1")
    suspend fun getCurrentMode(): ExamMode?

    @Query("UPDATE exam_modes SET isActive = 0 WHERE isActive = 1")
    suspend fun deactivateAll()

    @Query("UPDATE exam_modes SET isActive = 1 WHERE id = :id")
    suspend fun activateModeById(id: Int)

    @Query("DELETE FROM exam_modes WHERE id = :id")
    suspend fun deleteMode(id: Int)

    @Query("DELETE FROM exam_modes")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM exam_modes WHERE isActive = 1")
    suspend fun hasActiveMode(): Int
}