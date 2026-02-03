package com.porashona.studymaster.data.dao

import androidx.room.*
import com.porashona.studymaster.data.model.StudyResource
import com.porashona.studymaster.data.model.ResourceType
import kotlinx.coroutines.flow.Flow

@Dao
interface StudyResourceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(resource: StudyResource): Long

    @Update
    suspend fun update(resource: StudyResource)

    @Delete
    suspend fun delete(resource: StudyResource)

    @Query("SELECT * FROM study_resources ORDER BY lastVisitedAt DESC, createdAt DESC")
    fun getAllResources(): Flow<List<StudyResource>>

    @Query("SELECT * FROM study_resources WHERE id = :id")
    suspend fun getResourceById(id: Long): StudyResource?

    @Query("SELECT * FROM study_resources WHERE subjectId = :subjectId ORDER BY visitCount DESC")
    fun getResourcesBySubject(subjectId: Long): Flow<List<StudyResource>>

    @Query("SELECT * FROM study_resources WHERE type = :type ORDER BY visitCount DESC")
    fun getResourcesByType(type: ResourceType): Flow<List<StudyResource>>

    @Query("SELECT * FROM study_resources WHERE isFavorite = 1 ORDER BY visitCount DESC")
    fun getFavoriteResources(): Flow<List<StudyResource>>

    @Query("UPDATE study_resources SET visitCount = visitCount + 1, lastVisitedAt = :time WHERE id = :id")
    suspend fun incrementVisitCount(id: Long, time: Long = System.currentTimeMillis())

    @Query("UPDATE study_resources SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun setFavorite(id: Long, isFavorite: Boolean)

    @Query("SELECT * FROM study_resources WHERE title LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%'")
    fun searchResources(query: String): Flow<List<StudyResource>>
}