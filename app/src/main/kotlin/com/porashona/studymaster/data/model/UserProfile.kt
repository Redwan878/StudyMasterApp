package com.porashona.studymaster.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
data class UserProfile(
    @PrimaryKey
    val id: Int = 1,
    val name: String = "শিক্ষার্থী",
    val totalXp: Int = 0,
    val level: Int = 1,
    val currentStreak: Int = 0,
    val longestStreak: Int = 0,
    val totalStudyTimeSeconds: Long = 0,
    val totalSessions: Int = 0,
    val lastStudyDate: Long? = null,
    val createdAt: Long = System.currentTimeMillis()
) {
    fun getXpForNextLevel(): Int = level * 1000
    
    fun getXpProgress(): Int = totalXp % 1000
    
    fun calculateLevel(): Int = (totalXp / 1000) + 1
}