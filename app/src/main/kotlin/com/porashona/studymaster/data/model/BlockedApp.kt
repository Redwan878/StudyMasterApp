package com.porashona.studymaster.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "blocked_apps")
data class BlockedApp(
    @PrimaryKey
    val packageName: String,
    val appName: String,
    val isBlocked: Boolean = true,
    val isWhitelisted: Boolean = false,
    val blockAttempts: Int = 0,
    val lastBlockedAt: Long? = null,
    val addedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "block_statistics")
data class BlockStatistic(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val packageName: String,
    val appName: String,
    val blockedAt: Long = System.currentTimeMillis(),
    val sessionId: Long? = null
)