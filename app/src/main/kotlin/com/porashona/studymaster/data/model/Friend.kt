package com.porashona.studymaster.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "friends")
data class Friend(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val friendId: String,
    val userName: String,
    val displayName: String,
    val profileImageUrl: String?,
    val friendStatus: FriendStatus = FriendStatus.PENDING,
    val xp: Int = 0,
    val level: Int = 1,
    val joinedAt: Long = System.currentTimeMillis()
)

enum class FriendStatus {
    PENDING,
    ACCEPTED,
    BLOCKED,
    REQUESTED
}

@Entity(tableName = "activity_shares")
data class ActivityShare(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: String,
    val activityType: String,
    val activityData: String,
    val sharedWith: String,
    val sharedAt: Long = System.currentTimeMillis(),
    val isPublic: Boolean = false
)

@Entity(tableName = "social_notifications")
data class SocialNotification(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: String,
    val notificationType: String,
    val title: String,
    val message: String,
    val data: String,
    val isRead: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "leaderboard")
data class LeaderboardEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: String,
    val userName: String,
    val displayName: String,
    val profileImageUrl: String?,
    val totalXp: Int,
    val level: Int,
    val totalTime: Long,
    val totalSessions: Int,
    val rank: Int,
    val isCurrentUser: Boolean = false
)