/*
package com.porashona.studymaster.ui.routine.templates

import android.content.Context
import com.porashona.studymaster.data.model.Routine
import com.porashona.studymaster.data.model.Subject
import com.porashona.studymaster.StudyMasterApplication
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class RoutineTemplateManager(private val context: Context) {
    private val _templates = MutableStateFlow<List<RoutineTemplate>>(emptyList())
    val templates: StateFlow<List<RoutineTemplate>> = _templates

    init {
        loadDefaultTemplates()
    }

    fun loadDefaultTemplates() {
        val defaultTemplates = listOf(
            // Study Block Template
            RoutineTemplate(
                id = "study_block_basic",
                name = "Study Block",
                description = "Basic 25-minute study session",
                category = "Basic",
                estimatedDuration = 25,
                icon = "📚",
                color = "#E3F2FD",
                subjects = listOf("Math", "English", "Science"),
                isDefault = true,
                structure = listOf(
                    RoutineItem("Study", 25, listOf("Math", "English", "Science")),
                    RoutineItem("Break", 5, emptyList())
                )
            ),
            // Exam Preparation Template
            RoutineTemplate(
                id = "exam_prep_daily",
                name = "Daily Exam Prep",
                description = "Comprehensive exam preparation routine",
                category = "Exam Prep",
                estimatedDuration = 120,
                icon = "📝",
                color = "#FFEBEE",
                subjects = listOf("Math", "English", "Science", "Social Studies"),
                isDefault = true,
                structure = listOf(
                    RoutineItem("Warmup", 10, listOf("Math")),
                    RoutineItem("Main Topic", 45, listOf("English", "Science")),
                    RoutineItem("Practice", 30, listOf("Math")),
                    RoutineItem("Review", 20, listOf("Social Studies")),
                    RoutineItem("Break", 15, emptyList())
                )
            ),
            // Weekend Study Template
            RoutineTemplate(
                id = "weekend_study",
                name = "Weekend Study",
                description = "Relaxed weekend study schedule",
                category = "Weekend",
                estimatedDuration = 180,
                icon = "🌅",
                color = "#E8F5E9",
                subjects = listOf("Advanced Math", "Research", "Creative Writing", "Programming"),
                isDefault = true,
                structure = listOf(
                    RoutineItem("Morning Session", 60, listOf("Advanced Math", "Research")),
                    RoutineItem("Afternoon Break", 15, emptyList()),
                    RoutineItem("Afternoon Session", 60, listOf("Creative Writing", "Programming")),
                    RoutineItem("Evening Review", 45, emptyList())
                )
            ),
            // Quick Revision Template
            RoutineTemplate(
                id = "quick_revision",
                name = "Quick Revision",
                description = "30-minute focused revision session",
                category = "Quick",
                estimatedDuration = 30,
                icon = "⚡",
                color = "#FFF3E0",
                subjects = listOf("Today Lesson", "Yesterday Missed"),
                isDefault = true,
                structure = listOf(
                    RoutineItem("Concept Review", 15, listOf("Today Lesson")),
                    RoutineItem("Question Practice", 10, listOf("Yesterday Missed")),
                    RoutineItem("Quick Break", 5, emptyList())
                )
            ),
            // Deep Focus Template
            RoutineTemplate(
                id = "deep_focus",
                name = "Deep Focus",
                description = "3-hour intensive study session",
                category = "Deep Focus",
                estimatedDuration = 180,
                icon = "🎯",
                color = "#F3E5F5",
                subjects = listOf("Advanced Topic", "Problem Solving", "Application"),
                isDefault = true,
                structure = listOf(
                    RoutineItem("Deep Work", 75, listOf("Advanced Topic")),
                    RoutineItem("Practice Session", 60, listOf("Problem Solving")),
                    RoutineItem("Application Project", 45, listOf("Application")),
                    RoutineItem("Long Break", 30, emptyList())
                )
            )
        )
        _templates.value = defaultTemplates
    }

    fun getTemplate(templateId: String): RoutineTemplate? {
        return _templates.value.find { it.id == templateId }
    }

    fun getTemplatesByCategory(category: String): List<RoutineTemplate> {
        return _templates.value.filter { it.category == category }
    }

    fun getDefaultTemplates(): List<RoutineTemplate> {
        return _templates.value.filter { it.isDefault }
    }

    fun createCustomTemplate(
        name: String,
        description: String,
        category: String,
        estimatedDuration: Int,
        icon: String,
        color: String,
        subjects: List<String>,
        structure: List<RoutineItem>
    ): RoutineTemplate {
        val id = "custom_${System.currentTimeMillis()}"
        return RoutineTemplate(
            id = id,
            name = name,
            description = description,
            category = category,
            estimatedDuration = estimatedDuration,
            icon = icon,
            color = color,
            subjects = subjects,
            isDefault = false,
            structure = structure,
            creator = "user"
        )
    }

    fun convertTemplateToRoutine(
        template: RoutineTemplate,
        startDate: Long,
        customDate: String? = null
    ): Routine {
        // Convert a template to a routine with specific dates
        val date = customDate ?: android.text.format.DateFormat.format("yyyy-MM-dd", java.util.Date(startDate))
        val title = "${template.name} - ${date}"

        val routineItems = template.structure.mapIndexed { index, item ->
            RoutineItem(
                title = item.title,
                duration = item.duration,
                subjectIds = getSubjectIdsForSubjects(item.subjects),
                order = index,
                isBreak = item.title.lowercase().contains("break"),
                breakType = if (item.title.lowercase().contains("break")) {
                    when {
                        item.title.contains("short", ignoreCase = true) -> "SHORT_BREAK"
                        item.title.contains("long", ignoreCase = true) -> "LONG_BREAK"
                        else -> "SHORT_BREAK"
                    }
                } else {
                    "WORK"
                }
            )
        }

        return Routine(
            title = title,
            scheduledDate = startDate,
            duration = template.estimatedDuration * 60,
            routineItems = routineItems,
            isRecurring = false,
            repeatCount = 0,
            type = "custom"
        )
    }

    private fun getSubjectIdsForSubjects(subjectNames: List<String>): List<Long> {
        val app = context.applicationContext as StudyMasterApplication
        val subjectDao = app.database.subjectDao()
        return subjectNames.map { subjectName ->
            subjectDao.getSubjectByName(subjectName)?.id ?: 0L
        }.filter { it > 0 }
    }
}

data class RoutineTemplate(
    val id: String,
    val name: String,
    val description: String,
    val category: String,
    val estimatedDuration: Int,
    val icon: String,
    val color: String,
    val subjects: List<String>,
    val isDefault: Boolean,
    val structure: List<RoutineItem>,
    val creator: String = "system"
)

data class RoutineItem(
    val title: String,
    val duration: Int,
    val subjects: List<String>,
    val order: Int = 0,
    val isBreak: Boolean = false,
    val breakType: String = "SHORT_BREAK"
)

// Pre-defined template categories
enum class TemplateCategory {
    BASIC,
    QUICK,
    WEEKEND,
    EXAM_PREP,
    DEEP_FOCUS,
    CUSTOM
}
*/