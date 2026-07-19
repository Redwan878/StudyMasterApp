package com.porashona.studymaster.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

// ─── StudyRoom ──────────────────────────────────────────────────────────

@Entity(
    tableName = "study_rooms",
    indices = [Index(value = ["subjectId"])]
)
data class StudyRoom(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val subjectId: Long? = null,
    val isActive: Boolean = true,
    val participantCount: Int = 1,
    val maxParticipants: Int = 10,
    val createdAt: Long = System.currentTimeMillis()
)

// ─── SharedNote ─────────────────────────────────────────────────────────

@Entity(
    tableName = "shared_notes",
    indices = [Index(value = ["noteId"])]
)
data class SharedNote(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val noteId: Long,
    val shareId: String,
    val sharedAt: Long = System.currentTimeMillis(),
    val sharedWith: String = "" // JSON array of user identifiers
)

// ─── DiscussionPost ─────────────────────────────────────────────────────

@Entity(
    tableName = "discussion_posts",
    indices = [Index(value = ["chapterId"])]
)
data class DiscussionPost(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val chapterId: Long? = null,
    val authorName: String = "",
    val content: String,
    val isAnonymous: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val replyCount: Int = 0
)