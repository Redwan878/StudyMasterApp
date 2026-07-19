package com.porashona.studymaster.data.dao

import androidx.room.*
import com.porashona.studymaster.data.model.Routine
import kotlinx.coroutines.flow.Flow

@Dao
interface RoutineDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(routine: Routine): Long

    @Update
    suspend fun update(routine: Routine)

    @Delete
    suspend fun delete(routine: Routine)

    @Query("SELECT * FROM routines ORDER BY hour ASC, minute ASC")
    fun getAllRoutines(): Flow<List<Routine>>

    @Query("SELECT * FROM routines WHERE id = :id")
    suspend fun getRoutineById(id: Long): Routine?

    @Query("SELECT * FROM routines WHERE isEnabled = 1 ORDER BY hour ASC, minute ASC")
    fun getEnabledRoutines(): Flow<List<Routine>>

    @Query("UPDATE routines SET isEnabled = :enabled WHERE id = :id")
    suspend fun setEnabled(id: Long, enabled: Boolean)

    @Query("DELETE FROM routines")
    suspend fun deleteAll()
}