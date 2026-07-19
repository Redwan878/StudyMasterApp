package com.porashona.studymaster.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exam_modes")
data class ExamMode(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val description: String,
    val studyMinutes: Int = 45,
    val breakMinutes: Int = 5,
    val totalSessions: Int = 8,
    val focusIntensity: Int = 5,
    val musicEnabled: Boolean = true,
    val musicVolume: Int = 40,
    val notificationsEnabled: Boolean = false,
    val theme: String = "Dark",
    val isActive: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)

object HolidayExamModes {
    fun getPredefinedModes(): List<ExamMode> {
        return listOf(
            ExamMode(
                name = "Exam Marathon",
                description = "High-intensity study mode for final exam preparation with minimal breaks",
                studyMinutes = 50,
                breakMinutes = 5,
                totalSessions = 12,
                focusIntensity = 8,
                musicEnabled = false,
                notificationsEnabled = false,
                theme = "Dark"
            ),
            ExamMode(
                name = "Holiday Revision",
                description = "Relaxed but consistent study mode for holiday revision",
                studyMinutes = 30,
                breakMinutes = 10,
                totalSessions = 6,
                focusIntensity = 3,
                musicEnabled = true,
                musicVolume = 50,
                notificationsEnabled = true,
                theme = "Light"
            ),
            ExamMode(
                name = "Weekend Intensive",
                description = "Intensive weekend study mode with moderate breaks",
                studyMinutes = 45,
                breakMinutes = 8,
                totalSessions = 8,
                focusIntensity = 6,
                musicEnabled = true,
                musicVolume = 30,
                notificationsEnabled = false,
                theme = "System Default"
            ),
            ExamMode(
                name = "Semester Finals",
                description = "Extended high-focus mode for semester final preparation",
                studyMinutes = 55,
                breakMinutes = 5,
                totalSessions = 15,
                focusIntensity = 9,
                musicEnabled = false,
                notificationsEnabled = false,
                theme = "Dark"
            ),
            ExamMode(
                name = "Quick Holiday Sync",
                description = "Short focused study sessions for staying on track during holidays",
                studyMinutes = 25,
                breakMinutes = 5,
                totalSessions = 4,
                focusIntensity = 4,
                musicEnabled = true,
                musicVolume = 60,
                notificationsEnabled = true,
                theme = "Light"
            )
        )
    }
}

object PredefinedExamModes {
    fun getAllModes(): List<ExamMode> = HolidayExamModes.getPredefinedModes()
}