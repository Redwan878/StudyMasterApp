package com.porashona.studymaster.data.dao

import androidx.room.*
import com.porashona.studymaster.data.model.Subject
import kotlinx.coroutines.flow.Flow

@Dao
interface SubjectDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(subject: Subject): Long

    @Update
    suspend fun update(subject: Subject)

    @Delete
    suspend fun delete(subject: Subject)

    @Query("SELECT * FROM subjects ORDER BY name ASC")
    fun getAllSubjects(): Flow<List<Subject>>

    @Query("SELECT * FROM subjects WHERE id = :id")
    suspend fun getSubjectById(id: Long): Subject?

    @Query("SELECT * FROM subjects WHERE name = :name LIMIT 1")
    suspend fun getSubjectByName(name: String): Subject?

    @Query("UPDATE subjects SET totalTimeInSeconds = totalTimeInSeconds + :seconds WHERE id = :subjectId")
    suspend fun addTimeToSubject(subjectId: Long, seconds: Long)

    @Query("UPDATE subjects SET totalTimeInSeconds = CASE WHEN totalTimeInSeconds - :seconds < 0 THEN 0 ELSE totalTimeInSeconds - :seconds END WHERE id = :subjectId")
    suspend fun subtractTimeFromSubject(subjectId: Long, seconds: Long)

    @Query("DELETE FROM subjects")
    suspend fun deleteAll()
}