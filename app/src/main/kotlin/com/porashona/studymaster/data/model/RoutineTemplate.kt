package com.porashona.studymaster.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "routine_templates")
data class RoutineTemplate(
    @PrimaryKey
    val id: String,
    val name: String,
    val description: String,
    val category: String,
    val estimatedDuration: Int,
    val icon: String,
    val color: String,
    val subjects: List<String>,
    val isDefault: Boolean = false,
    val structureJson: String,
    val creator: String = "system",
    val createdAt: Date = Date(),
    val usageCount: Int = 0
) {
    // Helper function to convert structure JSON to RoutineItem list
    fun getStructureList(): List<RoutineItem> {
        return try {
            // Parse JSON structure - for now return empty list
            emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }
}

// Supporting data class for routine structure
@Entity(tableName = "routine_items")
data class RoutineItem(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val templateId: String,
    val title: String,
    val duration: Int,
    val subjects: List<String>,
    val order: Int = 0,
    val isBreak: Boolean = false,
    val breakType: String = "SHORT_BREAK"
) {
    constructor() : this(0, "", "", 0, emptyList(), 0, false, "")

    companion object {
        fun fromTemplateItem(item: RoutineTemplate.RoutineItem): RoutineItem {
            return RoutineItem(
                id = 0,
                templateId = "temp",
                title = item.title,
                duration = item.duration,
                subjects = item.subjects,
                order = item.order,
                isBreak = item.isBreak,
                breakType = item.breakType
            )
        }
    }
}

// Extension function for RoutineTemplate to support structure items
fun RoutineTemplate.getStructureItems(): List<RoutineTemplate.RoutineItem> {
    // For backward compatibility, return basic structure
    return listOf(RoutineTemplate.RoutineItem("Study", 25, listOf("Math")), RoutineTemplate.RoutineItem("Break", 5, emptyList()))
}

// Extension function for RoutineTemplate to get routine items
fun RoutineTemplate.toRoutineItems(): List<RoutineTemplate.RoutineItem> {
    return this.getStructureItems()
}