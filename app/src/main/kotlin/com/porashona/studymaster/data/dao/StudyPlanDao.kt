package com.porashona.studymaster.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.porashona.studymaster.data.model.StudyPlan
import kotlinx.coroutines.flow.Flow

@Dao
interface StudyPlanDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(plan: StudyPlan)

    @Update
    suspend fun update(plan: StudyPlan)

    @Query("SELECT * FROM study_plans WHERE id = :id")
    suspend fun getPlanById(id: Int): StudyPlan?

    @Query("SELECT * FROM study_plans ORDER BY generatedAt DESC")
    fun getAllPlans(): Flow<List<StudyPlan>>

    @Query("DELETE FROM study_plans WHERE id = :id")
    suspend fun deletePlan(id: Int)

    @Query("DELETE FROM study_plans")
    suspend fun deleteAll()}
