package com.porashona.studymaster.data.dao

import androidx.room.*
import com.porashona.studymaster.data.model.DiscussionPost
import com.porashona.studymaster.data.model.SharedNote
import com.porashona.studymaster.data.model.StudyRoom
import kotlinx.coroutines.flow.Flow

@Dao
interface CollaborationDao {

    // ═══════════════════════════════════════════════════════════════════════
    // StudyRoom
    // ═══════════════════════════════════════════════════════════════════════

    // ─── Insert ──────────────────────────────────────────────────────────

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoom(room: StudyRoom): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRooms(rooms: List<StudyRoom>): List<Long>

    // ─── Update ──────────────────────────────────────────────────────────

    @Update
    suspend fun updateRoom(room: StudyRoom)

    // ─── Delete ──────────────────────────────────────────────────────────

    @Delete
    suspend fun deleteRoom(room: StudyRoom)

    @Query("DELETE FROM study_rooms WHERE id = :roomId")
    suspend fun deleteRoomById(roomId: Long)

    @Query("DELETE FROM study_rooms")
    suspend fun deleteAllRooms()

    // ─── Read ────────────────────────────────────────────────────────────

    @Query("SELECT * FROM study_rooms ORDER BY createdAt DESC")
    fun getAllRooms(): Flow<List<StudyRoom>>

    @Query("SELECT * FROM study_rooms WHERE id = :id")
    suspend fun getRoomById(id: Long): StudyRoom?

    @Query("SELECT * FROM study_rooms WHERE isActive = 1 ORDER BY createdAt DESC")
    fun getActiveRooms(): Flow<List<StudyRoom>>

    @Query("SELECT * FROM study_rooms WHERE subjectId = :subjectId AND isActive = 1 ORDER BY createdAt DESC")
    fun getActiveRoomsBySubject(subjectId: Long): Flow<List<StudyRoom>>

    // ─── Utility ─────────────────────────────────────────────────────────

    @Query("UPDATE study_rooms SET isActive = :isActive WHERE id = :roomId")
    suspend fun setRoomActive(roomId: Long, isActive: Boolean)

    @Query("UPDATE study_rooms SET participantCount = participantCount + 1 WHERE id = :roomId")
    suspend fun incrementParticipantCount(roomId: Long)

    @Query("UPDATE study_rooms SET participantCount = MAX(0, participantCount - 1) WHERE id = :roomId")
    suspend fun decrementParticipantCount(roomId: Long)

    @Query("SELECT COUNT(*) FROM study_rooms WHERE isActive = 1")
    fun getActiveRoomCount(): Flow<Int>

    // ═══════════════════════════════════════════════════════════════════════
    // SharedNote
    // ═══════════════════════════════════════════════════════════════════════

    // ─── Insert ──────────────────────────────────────────────────────────

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSharedNote(sharedNote: SharedNote): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSharedNotes(sharedNotes: List<SharedNote>): List<Long>

    // ─── Read ────────────────────────────────────────────────────────────

    @Query("SELECT * FROM shared_notes ORDER BY sharedAt DESC")
    fun getAllSharedNotes(): Flow<List<SharedNote>>

    @Query("SELECT * FROM shared_notes WHERE id = :id")
    suspend fun getSharedNoteById(id: Long): SharedNote?

    @Query("SELECT * FROM shared_notes WHERE noteId = :noteId ORDER BY sharedAt DESC")
    fun getSharedNotesByNoteId(noteId: Long): Flow<List<SharedNote>>

    @Query("SELECT * FROM shared_notes WHERE shareId = :shareId LIMIT 1")
    suspend fun getByShareId(shareId: String): SharedNote?

    // ─── Delete ──────────────────────────────────────────────────────────

    @Query("DELETE FROM shared_notes WHERE id = :id")
    suspend fun deleteSharedNoteById(id: Long)

    @Query("DELETE FROM shared_notes WHERE noteId = :noteId")
    suspend fun deleteSharedNotesByNoteId(noteId: Long)

    @Query("DELETE FROM shared_notes")
    suspend fun deleteAllSharedNotes()

    // ─── Update ──────────────────────────────────────────────────────────

    @Query("UPDATE shared_notes SET sharedWith = :sharedWith WHERE id = :id")
    suspend fun updateSharedWith(id: Long, sharedWith: String)

    // ═══════════════════════════════════════════════════════════════════════
    // DiscussionPost
    // ═══════════════════════════════════════════════════════════════════════

    // ─── Insert ──────────────────────────────────────────────────────────

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPost(post: DiscussionPost): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosts(posts: List<DiscussionPost>): List<Long>

    // ─── Update ──────────────────────────────────────────────────────────

    @Update
    suspend fun updatePost(post: DiscussionPost)

    // ─── Delete ──────────────────────────────────────────────────────────

    @Delete
    suspend fun deletePost(post: DiscussionPost)

    @Query("DELETE FROM discussion_posts WHERE id = :postId")
    suspend fun deletePostById(postId: Long)

    @Query("DELETE FROM discussion_posts WHERE chapterId = :chapterId")
    suspend fun deletePostsByChapter(chapterId: Long)

    @Query("DELETE FROM discussion_posts")
    suspend fun deleteAllPosts()

    // ─── Read ────────────────────────────────────────────────────────────

    @Query("SELECT * FROM discussion_posts ORDER BY createdAt DESC")
    fun getAllPosts(): Flow<List<DiscussionPost>>

    @Query("SELECT * FROM discussion_posts WHERE id = :id")
    suspend fun getPostById(id: Long): DiscussionPost?

    @Query("SELECT * FROM discussion_posts WHERE chapterId = :chapterId ORDER BY createdAt DESC")
    fun getPostsByChapter(chapterId: Long): Flow<List<DiscussionPost>>

    @Query("SELECT * FROM discussion_posts ORDER BY createdAt DESC LIMIT :limit")
    fun getRecentPosts(limit: Int = 20): Flow<List<DiscussionPost>>

    // ─── Utility ─────────────────────────────────────────────────────────

    @Query("UPDATE discussion_posts SET replyCount = replyCount + 1 WHERE id = :postId")
    suspend fun incrementReplyCount(postId: Long)

    @Query("UPDATE discussion_posts SET replyCount = MAX(0, replyCount - 1) WHERE id = :postId")
    suspend fun decrementReplyCount(postId: Long)

    @Query("SELECT COUNT(*) FROM discussion_posts WHERE chapterId = :chapterId")
    suspend fun getPostCountByChapter(chapterId: Long): Int

    @Query("SELECT COUNT(*) FROM discussion_posts")
    fun getTotalPostCount(): Flow<Int>
}