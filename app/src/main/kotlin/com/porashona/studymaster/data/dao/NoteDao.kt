package com.porashona.studymaster.data.dao

import androidx.room.*
import com.porashona.studymaster.data.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note): Long

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("SELECT * FROM notes ORDER BY updatedAt DESC")
    fun getAllNotes(): Flow<List<Note>>

    @Query("SELECT * FROM notes WHERE id = :id")
    suspend fun getNoteById(id: Long): Note?

    @Query("SELECT * FROM notes WHERE subjectId = :subjectId ORDER BY updatedAt DESC")
    fun getNotesBySubject(subjectId: Long): Flow<List<Note>>

    @Query("SELECT * FROM notes WHERE sessionId = :sessionId")
    fun getNotesBySession(sessionId: Long): Flow<List<Note>>

    @Query("SELECT * FROM notes WHERE isFavorite = 1 ORDER BY updatedAt DESC")
    fun getFavoriteNotes(): Flow<List<Note>>

    @Query("SELECT * FROM notes WHERE title LIKE '%' || :query || '%' OR content LIKE '%' || :query || '%' ORDER BY updatedAt DESC")
    fun searchNotes(query: String): Flow<List<Note>>

    @Query("UPDATE notes SET isFavorite = :isFavorite WHERE id = :noteId")
    suspend fun setFavorite(noteId: Long, isFavorite: Boolean)

    @Query("UPDATE notes SET updatedAt = :updatedAt WHERE id = :noteId")
    suspend fun updateTimestamp(noteId: Long, updatedAt: Long = System.currentTimeMillis())

    @Query("SELECT COUNT(*) FROM notes")
    fun getNotesCount(): Flow<Int>
}