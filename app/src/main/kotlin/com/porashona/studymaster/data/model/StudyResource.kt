package com.porashona.studymaster.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "study_resources")
data class StudyResource(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val url: String,
    val type: ResourceType,
    val subjectId: Long? = null,
    val subjectName: String? = null,
    val description: String = "",
    val thumbnail: String = "",
    val isFavorite: Boolean = false,
    val visitCount: Int = 0,
    val lastVisitedAt: Long? = null,
    val createdAt: Long = System.currentTimeMillis()
)

enum class ResourceType {
    WEBSITE,
    YOUTUBE,
    PDF,
    OTHER
}