package com.porashona.studymaster.data.dao

import androidx.room.*
import com.porashona.studymaster.data.model.PracticeTest
import com.porashona.studymaster.data.model.PracticeTestResult
import com.porashona.studymaster.data.model.QuestionBank
import kotlinx.coroutines.flow.Flow

@Dao
interface PracticeTestDao {

    // ─── QuestionBank: Insert ────────────────────────────────────────────

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestion(question: QuestionBank): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestions(questions: List<QuestionBank>): List<Long>

    // ─── QuestionBank: Update ────────────────────────────────────────────

    @Update
    suspend fun updateQuestion(question: QuestionBank)

    // ─── QuestionBank: Delete ────────────────────────────────────────────

    @Delete
    suspend fun deleteQuestion(question: QuestionBank)

    @Query("DELETE FROM question_bank WHERE id = :questionId")
    suspend fun deleteQuestionById(questionId: Long)

    @Query("DELETE FROM question_bank WHERE subjectId = :subjectId")
    suspend fun deleteQuestionsBySubject(subjectId: Long)

    @Query("DELETE FROM question_bank")
    suspend fun deleteAllQuestions()

    // ─── QuestionBank: Read ──────────────────────────────────────────────

    @Query("SELECT * FROM question_bank ORDER BY createdAt DESC")
    fun getAllQuestions(): Flow<List<QuestionBank>>

    @Query("SELECT * FROM question_bank WHERE id = :id")
    suspend fun getQuestionById(id: Long): QuestionBank?

    @Query("SELECT * FROM question_bank WHERE subjectId = :subjectId ORDER BY createdAt DESC")
    fun getQuestionsBySubject(subjectId: Long): Flow<List<QuestionBank>>

    @Query("SELECT * FROM question_bank WHERE chapterName = :chapterName ORDER BY createdAt DESC")
    fun getQuestionsByChapter(chapterName: String): Flow<List<QuestionBank>>

    @Query("SELECT * FROM question_bank WHERE difficulty = :difficulty ORDER BY createdAt DESC")
    fun getQuestionsByDifficulty(difficulty: String): Flow<List<QuestionBank>>

    // ─── QuestionBank: Random Selection ──────────────────────────────────

    @Query("""
        SELECT * FROM question_bank
        WHERE subjectId = :subjectId
        AND (:difficulty IS NULL OR difficulty = :difficulty)
        ORDER BY RANDOM()
        LIMIT :count
    """)
    suspend fun getRandomQuestions(subjectId: Long, count: Int, difficulty: String? = null): List<QuestionBank>

    // ─── QuestionBank: Wrong Answer Questions ────────────────────────────

    @Query("SELECT * FROM question_bank WHERE id IN (:questionIds) ORDER BY createdAt DESC")
    fun getWrongAnswerQuestions(questionIds: List<Long>): Flow<List<QuestionBank>>

    @Query("SELECT * FROM question_bank WHERE subjectId = :subjectId AND difficulty = :difficulty ORDER BY RANDOM() LIMIT :count")
    suspend fun getWeakTopicQuestions(subjectId: Long, difficulty: String, count: Int): List<QuestionBank>

    // ─── QuestionBank: Count ─────────────────────────────────────────────

    @Query("SELECT COUNT(*) FROM question_bank")
    fun getQuestionCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM question_bank WHERE subjectId = :subjectId")
    suspend fun getQuestionCountBySubject(subjectId: Long): Int

    @Query("SELECT COUNT(*) FROM question_bank WHERE chapterName = :chapterName")
    suspend fun getQuestionCountByChapter(chapterName: String): Int

    @Query("SELECT COUNT(*) FROM question_bank WHERE difficulty = :difficulty")
    suspend fun getQuestionCountByDifficulty(difficulty: String): Int

    // ─── PracticeTest: Insert ────────────────────────────────────────────

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTest(test: PracticeTest): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTests(tests: List<PracticeTest>): List<Long>

    // ─── PracticeTest: Update ────────────────────────────────────────────

    @Update
    suspend fun updateTest(test: PracticeTest)

    // ─── PracticeTest: Delete ────────────────────────────────────────────

    @Delete
    suspend fun deleteTest(test: PracticeTest)

    @Query("DELETE FROM practice_tests WHERE id = :testId")
    suspend fun deleteTestById(testId: Long)

    @Query("DELETE FROM practice_tests")
    suspend fun deleteAllTests()

    // ─── PracticeTest: Read ──────────────────────────────────────────────

    @Query("SELECT * FROM practice_tests ORDER BY createdAt DESC")
    fun getAllTests(): Flow<List<PracticeTest>>

    @Query("SELECT * FROM practice_tests WHERE id = :id")
    suspend fun getTestById(id: Long): PracticeTest?

    @Query("SELECT * FROM practice_tests WHERE subjectId = :subjectId ORDER BY createdAt DESC")
    fun getTestsBySubject(subjectId: Long): Flow<List<PracticeTest>>

    @Query("SELECT * FROM practice_tests WHERE completedAt IS NOT NULL ORDER BY completedAt DESC")
    fun getCompletedTests(): Flow<List<PracticeTest>>

    @Query("SELECT * FROM practice_tests ORDER BY createdAt DESC LIMIT :limit")
    fun getRecentTests(limit: Int = 10): Flow<List<PracticeTest>>

    @Query("SELECT COUNT(*) FROM practice_tests WHERE completedAt IS NOT NULL")
    fun getCompletedTestCount(): Flow<Int>

    @Query("UPDATE practice_tests SET completedAt = :timestamp WHERE id = :testId")
    suspend fun markTestCompleted(testId: Long, timestamp: Long = System.currentTimeMillis())

    // ─── PracticeTestResult: Insert ──────────────────────────────────────

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResult(result: PracticeTestResult): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResults(results: List<PracticeTestResult>): List<Long>

    // ─── PracticeTestResult: Read ────────────────────────────────────────

    @Query("SELECT * FROM practice_test_results WHERE testId = :testId ORDER BY completedAt DESC")
    fun getResultsByTestId(testId: Long): Flow<List<PracticeTestResult>>

    @Query("SELECT * FROM practice_test_results WHERE id = :id")
    suspend fun getResultById(id: Long): PracticeTestResult?

    @Query("SELECT * FROM practice_test_results ORDER BY completedAt DESC LIMIT :limit")
    fun getRecentResults(limit: Int = 10): Flow<List<PracticeTestResult>>

    @Query("SELECT * FROM practice_test_results ORDER BY completedAt DESC")
    fun getAllResults(): Flow<List<PracticeTestResult>>

    // ─── PracticeTestResult: Score Trends ────────────────────────────────

    data class ScoreTrend(
        val testId: Long,
        val title: String,
        val percentage: Double,
        val completedAt: Long?
    )

    @Query("""
        SELECT ptr.testId, pt.title, ptr.percentage, ptr.completedAt
        FROM practice_test_results ptr
        INNER JOIN practice_tests pt ON ptr.testId = pt.id
        ORDER BY ptr.completedAt DESC
    """)
    fun getScoreTrends(): Flow<List<ScoreTrend>>

    @Query("""
        SELECT ptr.testId, pt.title, ptr.percentage, ptr.completedAt
        FROM practice_test_results ptr
        INNER JOIN practice_tests pt ON ptr.testId = pt.id
        WHERE pt.subjectId = :subjectId
        ORDER BY ptr.completedAt DESC
    """)
    fun getScoreTrendsBySubject(subjectId: Long): Flow<List<ScoreTrend>>

    @Query("""
        SELECT AVG(percentage) FROM practice_test_results
        WHERE testId = :testId
    """)
    suspend fun getAverageScoreByTest(testId: Long): Double?

    // ─── PracticeTestResult: Stats ───────────────────────────────────────

    @Query("SELECT COUNT(*) FROM practice_test_results")
    fun getResultCount(): Flow<Int>

    @Query("DELETE FROM practice_test_results")
    suspend fun deleteAllResults()
}