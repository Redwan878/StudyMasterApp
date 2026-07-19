package com.porashona.studymaster.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

// ─── Enum ────────────────────────────────────────────────────────────────

enum class FormulaCategory {
    KINEMATICS,
    DYNAMICS,
    WAVES,
    ELECTRICITY,
    MAGNETISM,
    OPTICS,
    THERMODYNAMICS,
    MODERN_PHYSICS,
    ALGEBRA,
    GEOMETRY,
    TRIGONOMETRY,
    CALCULUS,
    STATISTICS,
    ORGANIC,
    INORGANIC,
    PHYSICAL,
    GENERAL_MATH,
    HIGHER_MATH,
    OTHER
}

// ─── Formula ────────────────────────────────────────────────────────────

@Entity(
    tableName = "formulas",
    indices = [
        Index(value = ["subjectId"]),
        Index(value = ["category"])
    ]
)
data class Formula(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val subjectId: Long? = null,
    val subjectName: String? = null,
    val chapterName: String? = null,
    val chapterNumber: Int = 0,
    val formulaText: String,
    val description: String = "",
    val category: String = FormulaCategory.OTHER.name,
    val isFavorite: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)