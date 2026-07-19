package com.porashona.studymaster.data.repository

import com.porashona.studymaster.data.dao.StudyPlanDao
import com.porashona.studymaster.data.model.StudyPlan
import kotlinx.coroutines.flow.Flow

class StudyPlanRepository(private val studyPlanDao: StudyPlanDao) {
    fun getAllPlans(): Flow<List<StudyPlan>> = studyPlanDao.getAllPlans()

    suspend fun insertPlan(plan: StudyPlan) = studyPlanDao.insert(plan)

    suspend fun generateAndSavePlan(title: String, description: String, scheduleJson: String) {
        val plan = StudyPlan(
            title = title,
            description = description,
            scheduleJson = scheduleJson
        )
        insertPlan(plan)
    }
}