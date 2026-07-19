package com.porashona.studymaster.data.dao

import androidx.room.*
import com.porashona.studymaster.data.model.AudioLecture
import com.porashona.studymaster.data.model.DiagramEntry
import com.porashona.studymaster.data.model.VideoLink
import kotlinx.coroutines.flow.Flow

@Dao
interface MediaResourceDao {

    // ═══════════════════════════════════════════════════════════════════════
    // VideoLink
    // ═══════════════════════════════════════════════════════════════════════

    // ─── Insert ──────────────────────────────────────────────────────────

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVideo(video: VideoLink): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVideos(videos: List<VideoLink>): List<Long>

    // ─── Update ──────────────────────────────────────────────────────────

    @Update
    suspend fun updateVideo(video: VideoLink)

    // ─── Delete ──────────────────────────────────────────────────────────

    @Delete
    suspend fun deleteVideo(video: VideoLink)

    @Query("DELETE FROM video_links WHERE id = :id")
    suspend fun deleteVideoById(id: Long)

    @Query("DELETE FROM video_links WHERE subjectId = :subjectId")
    suspend fun deleteVideosBySubject(subjectId: Long)

    @Query("DELETE FROM video_links")
    suspend fun deleteAllVideos()

    // ─── Read ────────────────────────────────────────────────────────────

    @Query("SELECT * FROM video_links ORDER BY createdAt DESC")
    fun getAllVideos(): Flow<List<VideoLink>>

    @Query("SELECT * FROM video_links WHERE id = :id")
    suspend fun getVideoById(id: Long): VideoLink?

    @Query("SELECT * FROM video_links WHERE subjectId = :subjectId ORDER BY createdAt DESC")
    fun getVideosBySubject(subjectId: Long): Flow<List<VideoLink>>

    @Query("SELECT * FROM video_links WHERE chapterName = :chapterName ORDER BY createdAt DESC")
    fun getVideosByChapter(chapterName: String): Flow<List<VideoLink>>

    @Query("SELECT * FROM video_links WHERE platform = :platform ORDER BY createdAt DESC")
    fun getVideosByPlatform(platform: String): Flow<List<VideoLink>>

    @Query("SELECT * FROM video_links WHERE isWatched = 0 ORDER BY createdAt DESC")
    fun getUnwatchedVideos(): Flow<List<VideoLink>>

    @Query("SELECT * FROM video_links WHERE isWatched = 1 ORDER BY createdAt DESC")
    fun getWatchedVideos(): Flow<List<VideoLink>>

    // ─── Utility ─────────────────────────────────────────────────────────

    @Query("UPDATE video_links SET isWatched = :isWatched WHERE id = :id")
    suspend fun setVideoWatched(id: Long, isWatched: Boolean)

    @Query("SELECT COUNT(*) FROM video_links")
    fun getVideoCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM video_links WHERE isWatched = 0")
    suspend fun getUnwatchedCount(): Int

    // ═══════════════════════════════════════════════════════════════════════
    // AudioLecture
    // ═══════════════════════════════════════════════════════════════════════

    // ─── Insert ──────────────────────────────────────────────────────────

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAudio(audio: AudioLecture): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAudios(audios: List<AudioLecture>): List<Long>

    // ─── Update ──────────────────────────────────────────────────────────

    @Update
    suspend fun updateAudio(audio: AudioLecture)

    // ─── Delete ──────────────────────────────────────────────────────────

    @Delete
    suspend fun deleteAudio(audio: AudioLecture)

    @Query("DELETE FROM audio_lectures WHERE id = :id")
    suspend fun deleteAudioById(id: Long)

    @Query("DELETE FROM audio_lectures WHERE subjectId = :subjectId")
    suspend fun deleteAudiosBySubject(subjectId: Long)

    @Query("DELETE FROM audio_lectures")
    suspend fun deleteAllAudios()

    // ─── Read ────────────────────────────────────────────────────────────

    @Query("SELECT * FROM audio_lectures ORDER BY createdAt DESC")
    fun getAllAudios(): Flow<List<AudioLecture>>

    @Query("SELECT * FROM audio_lectures WHERE id = :id")
    suspend fun getAudioById(id: Long): AudioLecture?

    @Query("SELECT * FROM audio_lectures WHERE subjectId = :subjectId ORDER BY createdAt DESC")
    fun getAudiosBySubject(subjectId: Long): Flow<List<AudioLecture>>

    @Query("SELECT * FROM audio_lectures WHERE chapterName = :chapterName ORDER BY createdAt DESC")
    fun getAudiosByChapter(chapterName: String): Flow<List<AudioLecture>>

    // ─── Playback ────────────────────────────────────────────────────────

    @Query("UPDATE audio_lectures SET lastPosition = :position WHERE id = :id")
    suspend fun updatePlaybackPosition(id: Long, position: Long)

    @Query("UPDATE audio_lectures SET playbackSpeed = :speed WHERE id = :id")
    suspend fun updatePlaybackSpeed(id: Long, speed: Float)

    @Query("SELECT COUNT(*) FROM audio_lectures")
    fun getAudioCount(): Flow<Int>

    // ═══════════════════════════════════════════════════════════════════════
    // DiagramEntry
    // ═══════════════════════════════════════════════════════════════════════

    // ─── Insert ──────────────────────────────────────────────────────────

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDiagram(diagram: DiagramEntry): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDiagrams(diagrams: List<DiagramEntry>): List<Long>

    // ─── Update ──────────────────────────────────────────────────────────

    @Update
    suspend fun updateDiagram(diagram: DiagramEntry)

    // ─── Delete ──────────────────────────────────────────────────────────

    @Delete
    suspend fun deleteDiagram(diagram: DiagramEntry)

    @Query("DELETE FROM diagram_entries WHERE id = :id")
    suspend fun deleteDiagramById(id: Long)

    @Query("DELETE FROM diagram_entries WHERE subjectId = :subjectId")
    suspend fun deleteDiagramsBySubject(subjectId: Long)

    @Query("DELETE FROM diagram_entries")
    suspend fun deleteAllDiagrams()

    // ─── Read ────────────────────────────────────────────────────────────

    @Query("SELECT * FROM diagram_entries ORDER BY createdAt DESC")
    fun getAllDiagrams(): Flow<List<DiagramEntry>>

    @Query("SELECT * FROM diagram_entries WHERE id = :id")
    suspend fun getDiagramById(id: Long): DiagramEntry?

    @Query("SELECT * FROM diagram_entries WHERE subjectId = :subjectId ORDER BY createdAt DESC")
    fun getDiagramsBySubject(subjectId: Long): Flow<List<DiagramEntry>>

    @Query("SELECT * FROM diagram_entries WHERE chapterName = :chapterName ORDER BY createdAt DESC")
    fun getDiagramsByChapter(chapterName: String): Flow<List<DiagramEntry>>

    // ─── Search ──────────────────────────────────────────────────────────

    @Query("""
        SELECT * FROM diagram_entries
        WHERE title LIKE '%' || :query || '%'
           OR description LIKE '%' || :query || '%'
           OR tags LIKE '%' || :query || '%'
        ORDER BY createdAt DESC
    """)
    fun searchDiagrams(query: String): Flow<List<DiagramEntry>>

    @Query("""
        SELECT * FROM diagram_entries
        WHERE subjectId = :subjectId
          AND (title LIKE '%' || :query || '%'
               OR description LIKE '%' || :query || '%'
               OR tags LIKE '%' || :query || '%')
        ORDER BY createdAt DESC
    """)
    fun searchDiagramsBySubject(subjectId: Long, query: String): Flow<List<DiagramEntry>>

    // ─── Count ───────────────────────────────────────────────────────────

    @Query("SELECT COUNT(*) FROM diagram_entries")
    fun getDiagramCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM diagram_entries WHERE subjectId = :subjectId")
    suspend fun getDiagramCountBySubject(subjectId: Long): Int
}