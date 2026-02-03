package com.porashona.studymaster.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subjects")
data class Subject(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val colorHex: String = "#6C63FF",
    val totalTimeInSeconds: Long = 0,
    val createdAt: Long = System.currentTimeMillis()
)