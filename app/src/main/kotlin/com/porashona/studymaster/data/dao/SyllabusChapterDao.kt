package com.porashona.studymaster.data.dao

import androidx.room.*
import com.porashona.studymaster.data.model.SyllabusChapter
import kotlinx.coroutines.flow.Flow

@Dao
interface SyllabusChapterDao {

    // ─── Insert ──────────────────────────────────────────────────────────

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(chapter: SyllabusChapter): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(chapters: List<SyllabusChapter>): List<Long>

    // ─── Update ──────────────────────────────────────────────────────────

    @Update
    suspend fun update(chapter: SyllabusChapter)

    // ─── Delete ──────────────────────────────────────────────────────────

    @Delete
    suspend fun delete(chapter: SyllabusChapter)

    @Query("DELETE FROM syllabus_chapters WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("DELETE FROM syllabus_chapters WHERE subjectId = :subjectId")
    suspend fun deleteBySubject(subjectId: Long)

    @Query("DELETE FROM syllabus_chapters")
    suspend fun deleteAll()

    // ─── Read: All ───────────────────────────────────────────────────────

    @Query("SELECT * FROM syllabus_chapters ORDER BY chapterNumber ASC")
    fun getAllChapters(): Flow<List<SyllabusChapter>>

    @Query("SELECT * FROM syllabus_chapters WHERE id = :id")
    suspend fun getById(id: Long): SyllabusChapter?

    // ─── Read: By Subject ────────────────────────────────────────────────

    @Query("SELECT * FROM syllabus_chapters WHERE subjectId = :subjectId ORDER BY chapterNumber ASC")
    fun getBySubject(subjectId: Long): Flow<List<SyllabusChapter>>

    // ─── Read: By Exam Type ──────────────────────────────────────────────

    @Query("SELECT * FROM syllabus_chapters WHERE examType = :examType ORDER BY subjectName ASC, chapterNumber ASC")
    fun getByExamType(examType: String): Flow<List<SyllabusChapter>>

    @Query("""
        SELECT * FROM syllabus_chapters
        WHERE subjectId = :subjectId AND examType = :examType
        ORDER BY chapterNumber ASC
    """)
    fun getBySubjectAndExamType(subjectId: Long, examType: String): Flow<List<SyllabusChapter>>

    // ─── Read: By Status ─────────────────────────────────────────────────

    @Query("SELECT * FROM syllabus_chapters WHERE status = :status ORDER BY chapterNumber ASC")
    fun getByStatus(status: String): Flow<List<SyllabusChapter>>

    @Query("""
        SELECT * FROM syllabus_chapters
        WHERE subjectId = :subjectId AND status = :status
        ORDER BY chapterNumber ASC
    """)
    fun getBySubjectAndStatus(subjectId: Long, status: String): Flow<List<SyllabusChapter>>

    // ─── Completion Percentage ───────────────────────────────────────────

    data class CompletionPercentage(
        val subjectId: Long?,
        val subjectName: String?,
        val totalChapters: Int,
        val completedChapters: Int,
        val percentage: Double
    )

    @Query("""
        SELECT
            subjectId,
            subjectName,
            COUNT(*) AS totalChapters,
            SUM(CASE WHEN status = 'COMPLETED' THEN 1 ELSE 0 END) AS completedChapters,
            CAST(SUM(CASE WHEN status = 'COMPLETED' THEN 1 ELSE 0 END) AS REAL) * 100.0 / COUNT(*) AS percentage
        FROM syllabus_chapters
        WHERE subjectId = :subjectId
        GROUP BY subjectId
    """)
    suspend fun getCompletionPercentage(subjectId: Long): CompletionPercentage?

    @Query("""
        SELECT
            subjectId,
            subjectName,
            COUNT(*) AS totalChapters,
            SUM(CASE WHEN status = 'COMPLETED' THEN 1 ELSE 0 END) AS completedChapters,
            CAST(SUM(CASE WHEN status = 'COMPLETED' THEN 1 ELSE 0 END) AS REAL) * 100.0 / COUNT(*) AS percentage
        FROM syllabus_chapters
        GROUP BY subjectId
        ORDER BY percentage ASC
    """)
    fun getAllCompletionPercentages(): Flow<List<CompletionPercentage>>

    // ─── Short Syllabus ──────────────────────────────────────────────────

    @Query("SELECT * FROM syllabus_chapters WHERE isShortSyllabus = 1 ORDER BY subjectName ASC, chapterNumber ASC")
    fun getShortSyllabusChapters(): Flow<List<SyllabusChapter>>

    @Query("""
        SELECT * FROM syllabus_chapters
        WHERE subjectId = :subjectId AND isShortSyllabus = 1
        ORDER BY chapterNumber ASC
    """)
    fun getShortSyllabusChaptersBySubject(subjectId: Long): Flow<List<SyllabusChapter>>

    // ─── Utility ─────────────────────────────────────────────────────────

    @Query("""
        UPDATE syllabus_chapters
        SET status = :status, completedTopics = CASE WHEN :status = 'COMPLETED' THEN totalTopics ELSE completedTopics END
        WHERE id = :chapterId
    """)
    suspend fun updateStatus(chapterId: Long, status: String)

    @Query("""
        UPDATE syllabus_chapters
        SET completedTopics = MIN(completedTopics + 1, totalTopics),
            status = CASE WHEN completedTopics + 1 >= totalTopics THEN 'COMPLETED' ELSE 'IN_PROGRESS' END
        WHERE id = :chapterId
    """)
    suspend fun incrementCompletedTopics(chapterId: Long)

    @Query("UPDATE syllabus_chapters SET isShortSyllabus = :isShortSyllabus WHERE id = :chapterId")
    suspend fun setShortSyllabus(chapterId: Long, isShortSyllabus: Boolean)

    @Query("SELECT COUNT(*) FROM syllabus_chapters WHERE subjectId = :subjectId")
    suspend fun getCountBySubject(subjectId: Long): Int

    @Query("SELECT COUNT(*) FROM syllabus_chapters WHERE status = 'COMPLETED'")
    fun getCompletedCount(): Flow<Int>
}