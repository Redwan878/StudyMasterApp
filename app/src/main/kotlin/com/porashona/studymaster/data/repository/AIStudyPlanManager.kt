/*
package com.porashona.studymaster.data.repository

import com.porashona.studymaster.data.model.Subject
import com.porashona.studymaster.data.preferences.PreferencesManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

data class AIStudyPlanConfig(
    val examDate: Long? = null,
    val availableTimePerDay: Int = 240, // minutes
    val difficultyLevel: String = "Medium",
    val subjects: List<String> = emptyList(),
    val focusMinutes: Int = 25,
    val breakMinutes: Int = 5
)

data class AIStudyScheduleItem(
    val date: String,
    val subject: String,
    val durationMinutes: Int,
    val focusSessions: Int,
    val isRevision: Boolean = false,
    val notes: String = ""
)

class AIStudyPlanManager(
    private val studyPlanRepository: StudyPlanRepository,
    private val preferencesManager: PreferencesManager
) {

    private val _generatedPlan = MutableStateFlow<Pair<String, List<AIStudyScheduleItem>>?>(null)
    val generatedPlan: StateFlow<Pair<String, List<AIStudyScheduleItem>>?> = _generatedPlan.asStateFlow()

    suspend fun generateStudyPlan(config: AIStudyPlanConfig, totalTopics: Int, subjects: List<Subject>): Pair<String, List<AIStudyScheduleItem>> {
        val schedule = mutableListOf<AIStudyScheduleItem>()
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val calendar = Calendar.getInstance()

        val today = sdf.format(calendar.time)
        var daysToExam = 7 // Default 7 days

        config.examDate?.let {
            val examDate = Calendar.getInstance().apply { timeInMillis = it }
            daysToExam = ((examDate.timeInMillis - calendar.timeInMillis) / (1000 * 60 * 60 * 24)).toInt().coerceAtLeast(1)
        }

        // Divide topics across days
        val topicsPerDay = (totalTopics.toDouble() / daysToExam).toInt() + 1

        // Subject rotation strategy: cycle through subjects
        val subjectCount = subjects.size.coerceAtLeast(1)

        val availableMinutes = config.availableTimePerDay
        val focusSessionMinutes = config.focusMinutes + config.breakMinutes
        val sessionsPerDay = (availableMinutes / focusSessionMinutes).coerceAtLeast(1)

        for (day in 0 until daysToExam) {
            calendar.add(Calendar.DAY_OF_YEAR, 1)
            val date = sdf.format(calendar.time)

            val selectedSubjects = subjects.shuffled().take((topicsPerDay / 2).coerceAtLeast(1).coerceAtMost(subjectCount))

            var remainingMinutes = availableMinutes
            selectedSubjects.forEachIndexed { index, subject ->
                val sessionCount = ((topicsPerDay * 45) / (focusSessionMinutes * selectedSubjects.size)).coerceAtLeast(1)
                val duration = sessionCount * config.focusMinutes

                if (duration > remainingMinutes) return@forEachIndexed

                schedule.add(AIStudyScheduleItem(
                    date = date,
                    subject = subject.name,
                    durationMinutes = duration,
                    focusSessions = sessionCount,
                    isRevision = (index < selectedSubjects.size / 3) && (day > daysToExam / 2),
                    notes = generateNotes(subject.name, topicsPerDay, day)
                ))

                remainingMinutes -= duration
            }

            // Add revision day for last day
            if (day == daysToExam - 1) {
                schedule.add(AIStudyScheduleItem(
                    date = date,
                    subject = "Revision",
                    durationMinutes = availableMinutes,
                    focusSessions = sessionsPerDay,
                    isRevision = true,
                    notes = "Full revision of all subjects before exam"
                ))
            }
        }

        val planTitle = "Smart Study Plan - ${today} to ${sdf.format(calendar.time)}"
        _generatedPlan.value = Pair(planTitle, schedule)
        return Pair(planTitle, schedule)
    }

    fun saveGeneratedPlan() {
        _generatedPlan.value?.let { (title, schedule) ->
            val scheduleJson = convertScheduleToJson(schedule)
            val description = "AI-generated study schedule from ${schedule.first().date} to ${schedule.last().date}"
            com.porashona.studymaster.StudyMasterApplication.appCoroutineScope?.launch {
                    studyPlanRepository.generateAndSavePlan(title, description, scheduleJson)
                }
        }
    }

    suspend fun regeneratePlan(config: AIStudyPlanConfig, totalTopics: Int, subjects: List<Subject>) = generateStudyPlan(config, totalTopics, subjects)

    fun convertScheduleToJson(schedule: List<AIStudyScheduleItem>): String {
        val json = JSONObject()
        val array = org.json.JSONArray()

        schedule.forEach { item ->
            val obj = JSONObject().apply {
                put("date", item.date)
                put("subject", item.subject)
                put("duration", item.durationMinutes)
                put("sessions", item.focusSessions)
                put("revision", item.isRevision)
                put("notes", item.notes)
            }
            array.put(obj)
        }

        json.put("schedule", array)
        return json.toString()
    }

    fun convertJsonToSchedule(json: String): List<AIStudyScheduleItem> {
        val list = mutableListOf<AIStudyScheduleItem>()
        org.json.JSONObject(json).getJSONArray("schedule").let { array ->
            for (i in 0 until array.length()) {
                array.getJSONObject(i).let { obj ->
                    list.add(AIStudyScheduleItem(
                        date = obj.getString("date"),
                        subject = obj.getString("subject"),
                        durationMinutes = obj.getInt("duration"),
                        focusSessions = obj.getInt("sessions"),
                        isRevision = obj.getBoolean("revision"),
                        notes = obj.getString("notes")
                    ))
                }
            }
        }
        return list
    }

    private fun generateNotes(subject: String, topics: Int, day: Int): String {
        return when (day % 4) {
            0 -> "Focus on core concepts of $subject"
            1 -> "Solve practice problems for $subject"
            2 -> "Review previous topics and practice"
            else -> "Test yourself on $subject concepts"
        }
    }

    fun recommendStudyBreakdown(subjects: List<Subject>, availableTime: Int): Map<String, Int> {
        val total = subjects.size.coerceAtLeast(1)
        val baseTimePerSubject = availableTime / total

        val recommendations = mutableMapOf<String, Int>()
        subjects.forEach { subject ->
            var time = baseTimePerSubject

            // Increase time for difficult subjects
            if (subject.difficultyLevel == "Hard") {
                time = (time * 1.5).toInt()
            } else if (subject.difficultyLevel == "Medium") {
                time = (time * 1.25).toInt()
            }

            recommendations[subject.name] = time
        }

        return recommendations
    }
}

*/