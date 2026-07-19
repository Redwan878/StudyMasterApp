package com.porashona.studymaster.data.dao

import androidx.room.*
import com.porashona.studymaster.data.model.Formula
import kotlinx.coroutines.flow.Flow

@Dao
interface FormulaDao {

    // ─── Insert ──────────────────────────────────────────────────────────

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(formula: Formula): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(formulas: List<Formula>): List<Long>

    // ─── Update ──────────────────────────────────────────────────────────

    @Update
    suspend fun update(formula: Formula)

    // ─── Delete ──────────────────────────────────────────────────────────

    @Delete
    suspend fun delete(formula: Formula)

    @Query("DELETE FROM formulas WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("DELETE FROM formulas WHERE subjectId = :subjectId")
    suspend fun deleteBySubject(subjectId: Long)

    @Query("DELETE FROM formulas")
    suspend fun deleteAll()

    // ─── Read: All ───────────────────────────────────────────────────────

    @Query("SELECT * FROM formulas ORDER BY createdAt DESC")
    fun getAllFormulas(): Flow<List<Formula>>

    @Query("SELECT * FROM formulas WHERE id = :id")
    suspend fun getById(id: Long): Formula?

    // ─── Read: By Subject ────────────────────────────────────────────────

    @Query("SELECT * FROM formulas WHERE subjectId = :subjectId ORDER BY chapterNumber ASC, createdAt DESC")
    fun getBySubject(subjectId: Long): Flow<List<Formula>>

    // ─── Read: By Chapter ────────────────────────────────────────────────

    @Query("SELECT * FROM formulas WHERE chapterName = :chapterName ORDER BY createdAt DESC")
    fun getByChapter(chapterName: String): Flow<List<Formula>>

    @Query("SELECT * FROM formulas WHERE subjectId = :subjectId AND chapterName = :chapterName ORDER BY createdAt DESC")
    fun getBySubjectAndChapter(subjectId: Long, chapterName: String): Flow<List<Formula>>

    // ─── Read: By Category ───────────────────────────────────────────────

    @Query("SELECT * FROM formulas WHERE category = :category ORDER BY createdAt DESC")
    fun getByCategory(category: String): Flow<List<Formula>>

    @Query("SELECT * FROM formulas WHERE subjectId = :subjectId AND category = :category ORDER BY chapterNumber ASC")
    fun getBySubjectAndCategory(subjectId: Long, category: String): Flow<List<Formula>>

    // ─── Search ──────────────────────────────────────────────────────────

    @Query("""
        SELECT * FROM formulas
        WHERE formulaText LIKE '%' || :query || '%'
           OR description LIKE '%' || :query || '%'
           OR chapterName LIKE '%' || :query || '%'
        ORDER BY createdAt DESC
    """)
    fun search(query: String): Flow<List<Formula>>

    @Query("""
        SELECT * FROM formulas
        WHERE subjectId = :subjectId
          AND (formulaText LIKE '%' || :query || '%'
               OR description LIKE '%' || :query || '%')
        ORDER BY createdAt DESC
    """)
    fun searchBySubject(subjectId: Long, query: String): Flow<List<Formula>>

    // ─── Favorites ───────────────────────────────────────────────────────

    @Query("SELECT * FROM formulas WHERE isFavorite = 1 ORDER BY createdAt DESC")
    fun getFavorites(): Flow<List<Formula>>

    @Query("SELECT * FROM formulas WHERE subjectId = :subjectId AND isFavorite = 1 ORDER BY createdAt DESC")
    fun getFavoritesBySubject(subjectId: Long): Flow<List<Formula>>

    @Query("UPDATE formulas SET isFavorite = CASE WHEN isFavorite = 1 THEN 0 ELSE 1 END WHERE id = :id")
    suspend fun toggleFavorite(id: Long)

    @Query("UPDATE formulas SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun setFavorite(id: Long, isFavorite: Boolean)

    // ─── Count ───────────────────────────────────────────────────────────

    @Query("SELECT COUNT(*) FROM formulas")
    fun getCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM formulas WHERE subjectId = :subjectId")
    suspend fun getCountBySubject(subjectId: Long): Int

    @Query("SELECT COUNT(*) FROM formulas WHERE isFavorite = 1")
    fun getFavoriteCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM formulas WHERE category = :category")
    suspend fun getCountByCategory(category: String): Int
}