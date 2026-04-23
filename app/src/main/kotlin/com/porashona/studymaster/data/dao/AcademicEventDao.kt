package com.porashona.studymaster.data.dao

import androidx.room.*
import com.porashona.studymaster.data.model.AcademicEvent
import com.porashona.studymaster.data.model.EventType
import kotlinx.coroutines.flow.Flow

@Dao
interface AcademicEventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(event: AcademicEvent): Long

    @Update
    suspend fun update(event: AcademicEvent)

    @Delete
    suspend fun delete(event: AcademicEvent)

    @Query("SELECT * FROM academic_events ORDER BY date ASC")
    fun getAllEvents(): Flow<List<AcademicEvent>>

    @Query("SELECT * FROM academic_events WHERE id = :id")
    suspend fun getEventById(id: Long): AcademicEvent?

    @Query("SELECT * FROM academic_events WHERE date >= :today ORDER BY date ASC")
    fun getUpcomingEvents(today: Long): Flow<List<AcademicEvent>>

    @Query("SELECT * FROM academic_events WHERE date BETWEEN :startDate AND :endDate ORDER BY date ASC")
    fun getEventsInRange(startDate: Long, endDate: Long): Flow<List<AcademicEvent>>

    @Query("SELECT * FROM academic_events WHERE eventType = :type ORDER BY date ASC")
    fun getEventsByType(type: EventType): Flow<List<AcademicEvent>>

    @Query("SELECT * FROM academic_events WHERE isHoliday = 1 ORDER BY date ASC")
    fun getHolidays(): Flow<List<AcademicEvent>>

    @Query("SELECT * FROM academic_events WHERE subjectId = :subjectId ORDER BY date ASC")
    fun getEventsBySubject(subjectId: Long): Flow<List<AcademicEvent>>

    @Query("SELECT COUNT(*) FROM academic_events WHERE date >= :today")
    fun getUpcomingEventsCount(today: Long): Flow<Int>

    @Query("DELETE FROM academic_events")
    suspend fun deleteAll()
}