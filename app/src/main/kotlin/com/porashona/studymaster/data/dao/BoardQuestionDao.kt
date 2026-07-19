package com.porashona.studymaster.data.dao

import androidx.room.*
import com.porashona.studymaster.data.model.BoardQuestion
import kotlinx.coroutines.flow.Flow

@Dao
interface BoardQuestionDao {

    // ─── Insert ──────────────────────────────────────────────────────────

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(question: BoardQuestion): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(questions: List<BoardQuestion>): List<Long>

    // ─── Update ──────────────────────────────────────────────────────────

    @Update
    suspend fun update(question: BoardQuestion)

    // ─── Delete ──────────────────────────────────────────────────────────

    @Delete
    suspend fun delete(question: BoardQuestion)

    @Query("DELETE FROM board_questions WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("DELETE FROM board_questions WHERE subjectId = :subjectId")
    suspend fun deleteBySubject(subjectId: Long)

    @Query("DELETE FROM board_questions WHERE year = :year")
    suspend fun deleteByYear(year: Int)

    @Query("DELETE FROM board_questions")
    suspend fun deleteAll()

    // ─── Read: All ───────────────────────────────────────────────────────

    @Query("SELECT * FROM board_questions ORDER BY year DESC, createdAt DESC")
    fun getAllQuestions(): Flow<List<BoardQuestion>>

    @Query("SELECT * FROM board_questions WHERE id = :id")
    suspend fun getById(id: Long): BoardQuestion?

    // ─── Read: By Year ───────────────────────────────────────────────────

    @Query("SELECT * FROM board_questions WHERE year = :year ORDER BY createdAt DESC")
    fun getByYear(year: Int): Flow<List<BoardQuestion>>

    @Query("SELECT DISTINCT year FROM board_questions ORDER BY year DESC")
    fun getAvailableYears(): Flow<List<Int>>

    // ─── Read: By Board ──────────────────────────────────────────────────

    @Query("SELECT * FROM board_questions WHERE board = :board ORDER BY year DESC, createdAt DESC")
    fun getByBoard(board: String): Flow<List<BoardQuestion>>

    @Query("SELECT DISTINCT board FROM board_questions ORDER BY board ASC")
    fun getAvailableBoards(): Flow<List<String>>

    // ─── Read: By Subject ────────────────────────────────────────────────

    @Query("SELECT * FROM board_questions WHERE subjectId = :subjectId ORDER BY year DESC, createdAt DESC")
    fun getBySubject(subjectId: Long): Flow<List<BoardQuestion>>

    // ─── Read: By Chapter ────────────────────────────────────────────────

    @Query("SELECT * FROM board_questions WHERE chapterName = :chapterName ORDER BY year DESC, createdAt DESC")
    fun getByChapter(chapterName: String): Flow<List<BoardQuestion>>

    @Query("SELECT * FROM board_questions WHERE subjectId = :subjectId AND chapterName = :chapterName ORDER BY year DESC")
    fun getBySubjectAndChapter(subjectId: Long, chapterName: String): Flow<List<BoardQuestion>>

    // ─── Previous Year Questions ─────────────────────────────────────────

    @Query("""
        SELECT * FROM board_questions
        WHERE year = :year
          AND (:board IS NULL OR board = :board)
          AND (:subjectId IS NULL OR subjectId = :subjectId)
        ORDER BY questionNumber ASC
    """)
    fun getPreviousYearQuestions(
        year: Int,
        board: String? = null,
        subjectId: Long? = null
    ): Flow<List<BoardQuestion>>

    @Query("""
        SELECT * FROM board_questions
        WHERE year BETWEEN :startYear AND :endYear
          AND (:board IS NULL OR board = :board)
          AND (:subjectId IS NULL OR subjectId = :subjectId)
        ORDER BY year DESC, questionNumber ASC
    """)
    fun getPreviousYearQuestionsInRange(
        startYear: Int,
        endYear: Int,
        board: String? = null,
        subjectId: Long? = null
    ): Flow<List<BoardQuestion>>

    // ─── Search ──────────────────────────────────────────────────────────

    @Query("""
        SELECT * FROM board_questions
        WHERE questionText LIKE '%' || :query || '%'
           OR chapterName LIKE '%' || :query || '%'
        ORDER BY year DESC, createdAt DESC
    """)
    fun search(query: String): Flow<List<BoardQuestion>>

    @Query("""
        SELECT * FROM board_questions
        WHERE subjectId = :subjectId
          AND (questionText LIKE '%' || :query || '%'
               OR chapterName LIKE '%' || :query || '%')
        ORDER BY year DESC
    """)
    fun searchBySubject(subjectId: Long, query: String): Flow<List<BoardQuestion>>

    // ─── Count ───────────────────────────────────────────────────────────

    @Query("SELECT COUNT(*) FROM board_questions")
    fun getCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM board_questions WHERE subjectId = :subjectId")
    suspend fun getCountBySubject(subjectId: Long): Int

    @Query("SELECT COUNT(*) FROM board_questions WHERE year = :year")
    suspend fun getCountByYear(year: Int): Int

    @Query("SELECT COUNT(*) FROM board_questions WHERE board = :board")
    suspend fun getCountByBoard(board: String): Int

    @Query("SELECT COUNT(*) FROM board_questions WHERE chapterName = :chapterName")
    suspend fun getCountByChapter(chapterName: String): Int

    @Query("""
        SELECT COUNT(*) FROM board_questions
        WHERE year = :year AND board = :board AND subjectId = :subjectId
    """)
    suspend fun getCountByYearBoardSubject(year: Int, board: String, subjectId: Long): Int

    // ─── By Question Type ────────────────────────────────────────────────

    @Query("SELECT * FROM board_questions WHERE questionType = :questionType ORDER BY year DESC")
    fun getByQuestionType(questionType: String): Flow<List<BoardQuestion>>

    @Query("""
        SELECT * FROM board_questions
        WHERE subjectId = :subjectId AND questionType = :questionType
        ORDER BY year DESC
    """)
    fun getBySubjectAndType(subjectId: Long, questionType: String): Flow<List<BoardQuestion>>
}