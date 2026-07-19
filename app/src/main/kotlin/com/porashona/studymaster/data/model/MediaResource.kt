package com.porashona.studymaster.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

// ─── VideoLink ──────────────────────────────────────────────────────────

@Entity(
    tableName = "video_links",
    indices = [
        Index(value = ["subjectId"]),
        Index(value = ["platform"])
    ]
)
data class VideoLink(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val subjectId: Long? = null,
    val chapterName: String? = null,
    val title: String,
    val url: String,
    val platform: String = "YOUTUBE", // YOUTUBE / OTHER
    val duration: String? = null, // e.g. "12:30"
    val isWatched: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)

// ─── AudioLecture ───────────────────────────────────────────────────────

@Entity(
    tableName = "audio_lectures",
    indices = [Index(value = ["subjectId"])]
)
data class AudioLecture(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val subjectId: Long? = null,
    val chapterName: String? = null,
    val title: String,
    val filePath: String,
    val durationSeconds: Long = 0,
    val playbackSpeed: Float = 1.0f,
    val lastPosition: Long = 0,
    val createdAt: Long = System.currentTimeMillis()
)

// ─── DiagramEntry ───────────────────────────────────────────────────────

@Entity(
    tableName = "diagram_entries",
    indices = [Index(value = ["subjectId"])]
)
data class DiagramEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val subjectId: Long? = null,
    val chapterName: String? = null,
    val title: String,
    val imagePath: String,
    val description: String = "",
    val tags: String = "", // Comma-separated tags
    val createdAt: Long = System.currentTimeMillis()
)