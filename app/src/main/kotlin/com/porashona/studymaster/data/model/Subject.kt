package com.porashona.studymaster.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subjects")
data class Subject(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val shortName: String = "",
    val colorHex: String = "#6C63FF",
    val icon: String = "📚",
    val totalTimeInSeconds: Long = 0,
    val totalSessions: Int = 0,
    val difficultyLevel: Int = 3, // 1-5
    val targetHoursPerWeek: Int = 0,
    val chaptersTotal: Int = 0,
    val chaptersCompleted: Int = 0,
    val lastStudiedAt: Long? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val isArchived: Boolean = false
)

// Default SSC Science subjects for Bangladesh
object DefaultSubjects {
    val sscScienceSubjects = listOf(
        Subject(name = "বাংলা ১ম পত্র", shortName = "B1", colorHex = "#4CAF50", icon = "📖"),
        Subject(name = "বাংলা ২য় পত্র", shortName = "B2", colorHex = "#8BC34A", icon = "📝"),
        Subject(name = "ইংরেজি ১ম পত্র", shortName = "E1", colorHex = "#2196F3", icon = "🔤"),
        Subject(name = "ইংরেজি ২য় পত্র", shortName = "E2", colorHex = "#03A9F4", icon = "✍️"),
        Subject(name = "গণিত", shortName = "Math", colorHex = "#FF5722", icon = "🔢"),
        Subject(name = "উচ্চতর গণিত", shortName = "HM", colorHex = "#FF9800", icon = "📐"),
        Subject(name = "পদার্থবিজ্ঞান", shortName = "Phy", colorHex = "#9C27B0", icon = "⚛️"),
        Subject(name = "রসায়ন", shortName = "Che", colorHex = "#E91E63", icon = "🧪"),
        Subject(name = "জীববিজ্ঞান", shortName = "Bio", colorHex = "#4CAF50", icon = "🧬"),
        Subject(name = "তথ্য ও যোগাযোগ প্রযুক্তি", shortName = "ICT", colorHex = "#00BCD4", icon = "💻"),
        Subject(name = "বাংলাদেশ ও বিশ্বপরিচয়", shortName = "BGS", colorHex = "#795548", icon = "🌍"),
        Subject(name = "ইসলাম ও নৈতিক শিক্ষা", shortName = "Islam", colorHex = "#607D8B", icon = "☪️"),
    )
}