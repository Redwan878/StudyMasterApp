package com.porashona.studymaster.data.dao

import androidx.room.*
import com.porashona.studymaster.data.model.Exam
import kotlinx.coroutines.flow.Flow

@Dao
interface ExamDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(exam: Exam): Long

    @Update
    suspend fun update(exam: Exam)

    @Delete
    suspend fun delete(exam: Exam)

    @Query("SELECT * FROM exams ORDER BY examDate ASC")
    fun getAllExams(): Flow<List<Exam>>

    @Query("SELECT * FROM exams WHERE id = :id")
    suspend fun getExamById(id: Long): Exam?

    @Query("SELECT * FROM exams WHERE examDate >= :today AND isCompleted = 0 ORDER BY examDate ASC")
    fun getUpcomingExams(today: Long): Flow<List<Exam>>

    @Query("SELECT * FROM exams WHERE isCompleted = 1 ORDER BY examDate DESC")
    fun getCompletedExams(): Flow<List<Exam>>

    @Query("SELECT * FROM exams WHERE subjectId = :subjectId ORDER BY examDate ASC")
    fun getExamsBySubject(subjectId: Long): Flow<List<Exam>>

    @Query("SELECT * FROM exams WHERE examDate BETWEEN :startDate AND :endDate")
    fun getExamsInRange(startDate: Long, endDate: Long): Flow<List<Exam>>

    @Query("UPDATE exams SET preparationProgress = :progress WHERE id = :examId")
    suspend fun updateProgress(examId: Long, progress: Int)

    @Query("UPDATE exams SET isCompleted = 1, result = :result, reflection = :reflection WHERE id = :examId")
    suspend fun markAsCompleted(examId: Long, result: String?, reflection: String?)

    @Query("SELECT COUNT(*) FROM exams WHERE examDate >= :today AND isCompleted = 0")
    fun getUpcomingExamsCount(today: Long): Flow<Int>
}