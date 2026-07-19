package com.porashona.studymaster.ui.compose.theme

import androidx.compose.ui.graphics.Color

// ═══════════════════════════════════════════════════════════════════════════════
// StudyMaster Design System — Color Tokens
// Dark-mode-first with full Material You dynamic color support.
// Every semantic color is exposed as both a Compose Color and a hex string
// so that Room DB entities and SharedPreferences can store colour references.
// ═══════════════════════════════════════════════════════════════════════════════

// ─── Hex-string constants (Room / JSON safe) ────────────────────────────────
object HexColors {
    // Primary palette
    const val PRIMARY         = "#6C63FF"
    const val PRIMARY_DARK    = "#5348E8"
    const val PRIMARY_LIGHT   = "#9D97FF"
    const val ON_PRIMARY      = "#FFFFFF"
    const val PRIMARY_CONTAINER_DARK  = "#2D2678"
    const val ON_PRIMARY_CONTAINER_DARK = "#E0DEFF"
    const val PRIMARY_CONTAINER_LIGHT = "#E7E5FF"
    const val ON_PRIMARY_CONTAINER_LIGHT = "#1A0F57"

    // Secondary palette
    const val SECONDARY       = "#FF6584"
    const val SECONDARY_DARK  = "#E64A6D"
    const val SECONDARY_LIGHT = "#FF8FA3"
    const val ON_SECONDARY    = "#FFFFFF"
    const val SECONDARY_CONTAINER_DARK  = "#7A2A3F"
    const val ON_SECONDARY_CONTAINER_DARK = "#FFD9E0"
    const val SECONDARY_CONTAINER_LIGHT = "#FFE1E6"
    const val ON_SECONDARY_CONTAINER_LIGHT = "#5A0A20"

    // Tertiary (gold / achievement)
    const val TERTIARY        = "#FFC107"
    const val TERTIARY_DARK   = "#C79100"
    const val TERTIARY_LIGHT  = "#FFD54F"

    // ── Background & Surface ────────────────────────────────────────────────
    // Dark theme
    const val DARK_BG            = "#0F1117"
    const val DARK_SURFACE       = "#1A1C24"
    const val DARK_SURFACE_VARIANT = "#252730"
    const val DARK_ON_SURFACE    = "#E4E6F0"
    const val DARK_ON_SURFACE_VARIANT = "#C4C6D0"

    // AMOLED
    const val AMOLED_BG          = "#000000"
    const val AMOLED_SURFACE     = "#0A0A0F"
    const val AMOLED_SURFACE_VARIANT = "#141419"

    // Light theme
    const val LIGHT_BG           = "#F8F9FA"
    const val LIGHT_SURFACE      = "#FFFFFF"
    const val LIGHT_SURFACE_VARIANT = "#EEF0F6"
    const val LIGHT_ON_SURFACE   = "#1C1B1F"
    const val LIGHT_ON_SURFACE_VARIANT = "#43474E"

    // ── Glassmorphism ────────────────────────────────────────────────────────
    const val GLASS_DARK          = "#1A1C24"
    const val GLASS_DARK_ALPHA80  = "#CC1A1C24"
    const val GLASS_DARK_ALPHA60  = "#991A1C24"
    const val GLASS_DARK_ALPHA40  = "#661A1C24"
    const val GLASS_DARK_ALPHA20  = "#331A1C24"
    const val GLASS_BORDER_DARK   = "#3A3D4A"
    const val GLASS_LIGHT         = "#FFFFFF"
    const val GLASS_LIGHT_ALPHA90 = "#E6FFFFFF"
    const val GLASS_LIGHT_ALPHA70 = "#B3FFFFFF"
    const val GLASS_LIGHT_ALPHA50 = "#80FFFFFF"
    const val GLASS_BORDER_LIGHT  = "#D1D5DB"

    // ── Activity-type tag colours ────────────────────────────────────────────
    const val TAG_STUDY           = "#4FC3F7"
    const val TAG_STUDY_DIM       = "#1A3A4A"
    const val TAG_REVISION        = "#4DB6AC"
    const val TAG_REVISION_DIM    = "#1A3A36"
    const val TAG_EXAM            = "#EF5350"
    const val TAG_EXAM_DIM        = "#3A1A1A"
    const val TAG_ASSIGNMENT      = "#FFA726"
    const val TAG_ASSIGNMENT_DIM  = "#3A2E1A"
    const val TAG_MODEL_TEST      = "#AB47BC"
    const val TAG_MODEL_TEST_DIM  = "#2E1A33"

    // ── XP & Gamification ───────────────────────────────────────────────────
    const val XP_GAIN             = "#69F0AE"   // green flash on XP earn
    const val XP_BAR_BG           = "#2A2D38"
    const val XP_BAR_FILL         = "#6C63FF"
    const val LEVEL_UP            = "#FFD740"   // gold glow on level up
    const val STREAK_FIRE         = "#FF6D00"   // orange fire for streaks
    const val STREAK_COLD         = "#78909C"   // grey when streak is at risk
    const val ACHIEVEMENT_LOCKED  = "#424242"
    const val ACHIEVEMENT_UNLOCKED = "#FFC107"

    // ── Timer states ────────────────────────────────────────────────────────
    const val TIMER_WORK          = "#FF6B6B"
    const val TIMER_SHORT_BREAK   = "#4ECDC4"
    const val TIMER_LONG_BREAK    = "#95E1D3"

    // ── Priority ────────────────────────────────────────────────────────────
    const val PRIORITY_LOW        = "#66BB6A"
    const val PRIORITY_MEDIUM     = "#FFA726"
    const val PRIORITY_HIGH       = "#EF5350"
    const val PRIORITY_URGENT     = "#FF1744"

    // ── Chart palette ───────────────────────────────────────────────────────
    const val CHART_1 = "#6C63FF"
    const val CHART_2 = "#FF6584"
    const val CHART_3 = "#4ECDC4"
    const val CHART_4 = "#FFD93D"
    const val CHART_5 = "#6BCF7F"
    const val CHART_6 = "#AB47BC"

    // ── Status / feedback ───────────────────────────────────────────────────
    const val SUCCESS = "#48BB78"
    const val ERROR   = "#F56565"
    const val WARNING = "#ED8936"
    const val INFO    = "#4299E1"

    // ── Outline ─────────────────────────────────────────────────────────────
    const val OUTLINE_DARK   = "#3A3D4A"
    const val OUTLINE_LIGHT  = "#C4C7CF"
    const val OUTLINE_VARIANT_DARK  = "#252730"
    const val OUTLINE_VARIANT_LIGHT = "#E2E4EB"

    // ── Scrim / shadow ──────────────────────────────────────────────────────
    const val SCRIM   = "#000000"
    const val SHADOW  = "#000000"
}

// ═══════════════════════════════════════════════════════════════════════════════
// Compose Color objects — mirrors of the hex strings above for use in @Composable
// ═══════════════════════════════════════════════════════════════════════════════

// ─── Primary ────────────────────────────────────────────────────────────────
val Primary          = Color(0xFF6C63FF)
val PrimaryDark      = Color(0xFF5348E8)
val PrimaryLight     = Color(0xFF9D97FF)
val OnPrimary        = Color(0xFFFFFFFF)

val PrimaryContainerDark  = Color(0xFF2D2678)
val OnPrimaryContainerDark = Color(0xFFE0DEFF)
val PrimaryContainerLight = Color(0xFFE7E5FF)
val OnPrimaryContainerLight = Color(0xFF1A0F57)

// ─── Secondary ──────────────────────────────────────────────────────────────
val Secondary        = Color(0xFFFF6584)
val SecondaryDark    = Color(0xFFE64A6D)
val SecondaryLight   = Color(0xFFFF8FA3)
val OnSecondary      = Color(0xFFFFFFFF)

val SecondaryContainerDark  = Color(0xFF7A2A3F)
val OnSecondaryContainerDark = Color(0xFFFFD9E0)
val SecondaryContainerLight = Color(0xFFFFE1E6)
val OnSecondaryContainerLight = Color(0xFF5A0A20)

// ─── Tertiary (achievement / gold) ─────────────────────────────────────────
val Tertiary         = Color(0xFFFFC107)
val TertiaryDark     = Color(0xFFC79100)
val TertiaryLight    = Color(0xFFFFD54F)

// ─── Dark theme surfaces ───────────────────────────────────────────────────
val DarkBackground        = Color(0xFF0F1117)
val DarkSurface           = Color(0xFF1A1C24)
val DarkSurfaceVariant    = Color(0xFF252730)
val DarkOnSurface         = Color(0xFFE4E6F0)
val DarkOnSurfaceVariant  = Color(0xFFC4C6D0)

// ─── AMOLED surfaces ────────────────────────────────────────────────────────
val AmoledBackground        = Color(0xFF000000)
val AmoledSurface           = Color(0xFF0A0A0F)
val AmoledSurfaceVariant    = Color(0xFF141419)

// ─── Light theme surfaces ──────────────────────────────────────────────────
val LightBackground        = Color(0xFFF8F9FA)
val LightSurface           = Color(0xFFFFFFFF)
val LightSurfaceVariant    = Color(0xFFEEF0F6)
val LightOnSurface         = Color(0xFF1C1B1F)
val LightOnSurfaceVariant  = Color(0xFF43474E)

// ─── Glassmorphism ─────────────────────────────────────────────────────────
val GlassDark            = Color(0xFF1A1C24)
val GlassDarkAlpha80     = Color(0xCC1A1C24)
val GlassDarkAlpha60     = Color(0x991A1C24)
val GlassDarkAlpha40     = Color(0x661A1C24)
val GlassDarkAlpha20     = Color(0x331A1C24)
val GlassBorderDark      = Color(0xFF3A3D4A)
val GlassLight           = Color(0xFFFFFFFF)
val GlassLightAlpha90    = Color(0xE6FFFFFF)
val GlassLightAlpha70    = Color(0xB3FFFFFF)
val GlassLightAlpha50    = Color(0x80FFFFFF)
val GlassBorderLight     = Color(0xFFD1D5DB)

// ─── Activity-type tag colours ──────────────────────────────────────────────
object ActivityTagColors {
    val StudyColor          = Color(0xFF4FC3F7)
    val StudyDim            = Color(0xFF1A3A4A)
    val RevisionColor       = Color(0xFF4DB6AC)
    val RevisionDim         = Color(0xFF1A3A36)
    val ExamColor           = Color(0xFFEF5350)
    val ExamDim             = Color(0xFF3A1A1A)
    val AssignmentColor     = Color(0xFFFFA726)
    val AssignmentDim       = Color(0xFF3A2E1A)
    val ModelTestColor      = Color(0xFFAB47BC)
    val ModelTestDim        = Color(0xFF2E1A33)
}

// ─── XP & Gamification ─────────────────────────────────────────────────────
val XpGain               = Color(0xFF69F0AE)
val XpBarBg              = Color(0xFF2A2D38)
val XpBarFill            = Color(0xFF6C63FF)
val LevelUp              = Color(0xFFFFD740)
val StreakFire           = Color(0xFFFF6D00)
val StreakCold           = Color(0xFF78909C)
val AchievementLocked    = Color(0xFF424242)
val AchievementUnlocked  = Color(0xFFFFC107)

// ─── Timer states ───────────────────────────────────────────────────────────
val TimerWork            = Color(0xFFFF6B6B)
val TimerShortBreak      = Color(0xFF4ECDC4)
val TimerLongBreak       = Color(0xFF95E1D3)

// ─── Priority ───────────────────────────────────────────────────────────────
val PriorityLow          = Color(0xFF66BB6A)
val PriorityMedium       = Color(0xFFFFA726)
val PriorityHigh         = Color(0xFFEF5350)
val PriorityUrgent       = Color(0xFFFF1744)

// ─── Chart palette ──────────────────────────────────────────────────────────
val Chart1 = Color(0xFF6C63FF)
val Chart2 = Color(0xFFFF6584)
val Chart3 = Color(0xFF4ECDC4)
val Chart4 = Color(0xFFFFD93D)
val Chart5 = Color(0xFF6BCF7F)
val Chart6 = Color(0xFFAB47BC)

// ─── Status / feedback ──────────────────────────────────────────────────────
val Success = Color(0xFF48BB78)
val Error   = Color(0xFFF56565)
val Warning = Color(0xFFED8936)
val Info    = Color(0xFF4299E1)

// ─── Outline ────────────────────────────────────────────────────────────────
val OutlineDark          = Color(0xFF3A3D4A)
val OutlineLight         = Color(0xFFC4C7CF)
val OutlineVariantDark   = Color(0xFF252730)
val OutlineVariantLight  = Color(0xFFE2E4EB)

// ─── Scrim / shadow ────────────────────────────────────────────────────────
val Scrim  = Color(0xFF000000)
val Shadow = Color(0xFF000000)

// ═══════════════════════════════════════════════════════════════════════════════
// Subject colour palette — fixed set used for all subjects across the app
// ═══════════════════════════════════════════════════════════════════════════════
object SubjectPalette {
    val colors = listOf(
        Color(0xFF6C63FF), // 0  Indigo
        Color(0xFFFF6584), // 1  Rose
        Color(0xFF4ECDC4), // 2  Teal
        Color(0xFFFFD93D), // 3  Yellow
        Color(0xFF6BCF7F), // 4  Green
        Color(0xFFAB47BC), // 5  Purple
        Color(0xFFFF7043), // 6  Deep Orange
        Color(0xFF42A5F5), // 7  Blue
        Color(0xFFEC407A), // 8  Pink
        Color(0xFF26A69A), // 9  Dark Teal
        Color(0xFF5C6BC0), // 10 Indigo mid
        Color(0xFFFFA726), // 11 Orange
    )

    private val hexList = listOf(
        "#6C63FF", "#FF6584", "#4ECDC4", "#FFD93D", "#6BCF7F", "#AB47BC",
        "#FF7043", "#42A5F5", "#EC407A", "#26A69A", "#5C6BC0", "#FFA726",
    )

    fun colorForIndex(index: Int): Color =
        colors[index % colors.size]

    fun hexForIndex(index: Int): String =
        hexList[index % hexList.size]

    fun indexForHex(hex: String): Int =
        hexList.indexOf(hex.takeIf { it.startsWith("#") && it.length == 7 } ?: "#6C63FF")
            .coerceAtLeast(0)
}

// ═══════════════════════════════════════════════════════════════════════════════
// Helper: parse a hex string into a Compose Color (for Room ↔ UI bridging)
// ═══════════════════════════════════════════════════════════════════════════════
fun String.toComposeColor(): Color {
    return try {
        val cleaned = removePrefix("#")
        val argb = when (cleaned.length) {
            6    -> "FF$cleaned"
            8    -> cleaned
            else -> return Primary
        }.toLong(16)
        Color(argb)
    } catch (_: Exception) {
        Primary
    }
}

fun Color.toHexString(): String {
    return "#${red.toByte2Hex()}${green.toByte2Hex()}${blue.toByte2Hex()}"
}

private fun Float.toByte2Hex(): String =
    (this * 255f).toInt().coerceIn(0, 255).toString(16).padStart(2, '0').uppercase()