package com.porashona.studymaster.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "challenges")
data class Challenge(
    @PrimaryKey
    val id: String,
    val title: String,
    val titleBn: String, // Bengali title
    val description: String,
    val descriptionBn: String,
    val type: ChallengeType,
    val targetValue: Int,
    val currentValue: Int = 0,
    val xpReward: Int,
    val isCompleted: Boolean = false,
    val isActive: Boolean = true,
    val date: String = "", // For daily challenges
    val completedAt: Long? = null
)

enum class ChallengeType {
    STUDY_HOURS,
    POMODORO_COUNT,
    SUBJECT_COUNT,
    EARLY_START,
    NO_BREAK,
    STREAK,
    CUSTOM
}

object DailyChallenges {
    fun generateForDate(date: String): List<Challenge> {
        return listOf(
            Challenge(
                id = "daily_3hours_$date",
                title = "Study 3 Hours",
                titleBn = "৩ ঘণ্টা পড়াশোনা করুন",
                description = "Study for at least 3 hours today",
                descriptionBn = "আজ কমপক্ষে ৩ ঘণ্টা পড়াশোনা করুন",
                type = ChallengeType.STUDY_HOURS,
                targetValue = 180, // minutes
                xpReward = 100,
                date = date
            ),
            Challenge(
                id = "daily_5pomodoros_$date",
                title = "Complete 5 Pomodoros",
                titleBn = "৫টি পোমোডোরো সম্পূর্ণ করুন",
                description = "Complete 5 pomodoro sessions",
                descriptionBn = "৫টি পোমোডোরো সেশন সম্পূর্ণ করুন",
                type = ChallengeType.POMODORO_COUNT,
                targetValue = 5,
                xpReward = 75,
                date = date
            ),
            Challenge(
                id = "daily_2subjects_$date",
                title = "Study 2 Subjects",
                titleBn = "২টি বিষয় পড়ুন",
                description = "Study at least 2 different subjects",
                descriptionBn = "কমপক্ষে ২টি ভিন্ন বিষয় পড়ুন",
                type = ChallengeType.SUBJECT_COUNT,
                targetValue = 2,
                xpReward = 50,
                date = date
            ),
            Challenge(
                id = "daily_early_$date",
                title = "Early Bird",
                titleBn = "সকালের পাখি",
                description = "Start studying before 8 AM",
                descriptionBn = "সকাল ৮টার আগে পড়া শুরু করুন",
                type = ChallengeType.EARLY_START,
                targetValue = 8, // hour
                xpReward = 60,
                date = date
            ),
            Challenge(
                id = "daily_nobreak_$date",
                title = "Deep Focus",
                titleBn = "গভীর মনোযোগ",
                description = "Study 50 minutes without break",
                descriptionBn = "৫০ মিনিট বিরতি ছাড়া পড়ুন",
                type = ChallengeType.NO_BREAK,
                targetValue = 50, // minutes
                xpReward = 80,
                date = date
            )
        )
    }
}