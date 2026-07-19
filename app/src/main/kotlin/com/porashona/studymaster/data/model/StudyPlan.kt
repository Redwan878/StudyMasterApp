package com.porashona.studymaster.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "study_plans")
data class StudyPlan(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val generatedAt: Long = System.currentTimeMillis(),
    val scheduleJson: String // JSON representation of schedule details
)