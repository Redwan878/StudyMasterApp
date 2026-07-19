package com.porashona.studymaster.data.dao

import androidx.room.*
import com.porashona.studymaster.data.model.StudySession
import kotlinx.coroutines.flow.Flow

@Dao
interface StudySessionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(session: StudySession): Long

    @Update
    suspend fun update(session: StudySession)

    @Delete
    suspend fun delete(session: StudySession)

    @Query("SELECT * FROM study_sessions ORDER BY startTime DESC")
    fun getAllSessions(): Flow<List<StudySession>>

    @Query("SELECT * FROM study_sessions WHERE id = :id")
    suspend fun getSessionById(id: Long): StudySession?

    @Query("SELECT * FROM study_sessions WHERE startTime >= :startTime AND startTime <= :endTime ORDER BY startTime DESC")
    fun getSessionsBetween(startTime: Long, endTime: Long): Flow<List<StudySession>>

    @Query("SELECT * FROM study_sessions WHERE date(startTime/1000, 'unixepoch', 'localtime') = date(:date/1000, 'unixepoch', 'localtime')")
    fun getSessionsForDate(date: Long): Flow<List<StudySession>>

    @Query("SELECT SUM(durationInSeconds) FROM study_sessions WHERE sessionType = 'WORK'")
    fun getTotalStudyTime(): Flow<Long?>

    @Query("SELECT SUM(durationInSeconds) FROM study_sessions WHERE sessionType = 'WORK' AND startTime >= :startTime")
    fun getTotalStudyTimeSince(startTime: Long): Flow<Long?>

    @Query("SELECT COUNT(*) FROM study_sessions WHERE sessionType = 'WORK'")
    fun getTotalSessionCount(): Flow<Int>

    @Query("SELECT SUM(durationInSeconds) FROM study_sessions WHERE subjectId = :subjectId AND sessionType = 'WORK'")
    fun getTotalTimeForSubject(subjectId: Long): Flow<Long?>

    @Query("SELECT subjectName, SUM(durationInSeconds) as totalTime FROM study_sessions WHERE sessionType = 'WORK' GROUP BY subjectId ORDER BY totalTime DESC")
    fun getTimeBySubject(): Flow<List<SubjectTime>>

    @Query("SELECT DISTINCT date(startTime/1000, 'unixepoch', 'localtime') as studyDate FROM study_sessions WHERE sessionType = 'WORK' ORDER BY studyDate DESC")
    fun getStudyDates(): Flow<List<String>>

    @Query("DELETE FROM study_sessions")
    suspend fun deleteAll()
}

data class SubjectTime(
    val subjectName: String,
    val totalTime: Long
)