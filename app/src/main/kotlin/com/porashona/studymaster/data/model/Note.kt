package com.porashona.studymaster.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val content: String = "",
    val htmlContent: String = "", // For rich text
    val subjectId: Long? = null,
    val subjectName: String? = null,
    val sessionId: Long? = null, // Link to study session
    val isFavorite: Boolean = false,
    val color: String = "#FFFFFF",
    val imagesPaths: String = "", // JSON array of image paths
    val voiceNotePath: String? = null,
    val tags: String = "", // Comma-separated tags
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)