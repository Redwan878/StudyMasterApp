
package com.porashona.studymaster.ui.compose.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// ═══════════════════════════════════════════════════════════════════════════════
// StudyMaster Design System — Typography
//
// Two font families:
//   • Bengali (Hind Siliguri) — for all Bangla text (৯-১২ শ্রেণি)
//   • English (Poppins)       — for numbers, English UI labels, abbreviations
//
// The material Typography class maps Bengali font by default (since the app
// is Bangla-first), and EnglishFontFamily is provided for explicitly English
// passages (timer digits, XP numbers, etc.).
// ═══════════════════════════════════════════════════════════════════════════════

// ─── Font Family Definitions ────────────────────────────────────────────────
// These use the downloadable font XML resources already in res/font/.
// Compose resolves them via R.font at compile time.

val BengaliFontFamily = FontFamily(
    Font(R.font.hind_siliguri, FontWeight.Normal),
    Font(R.font.hind_siliguri_medium, FontWeight.Medium),
    Font(R.font.hind_siliguri_bold, FontWeight.Bold),
)

val EnglishFontFamily = FontFamily(
    Font(R.font.poppins, FontWeight.Normal),
    Font(R.font.poppins_medium, FontWeight.Medium),
    Font(R.font.poppins_bold, FontWeight.Bold),
)

// ─── Monospaced (for timer / code) ─────────────────────────────────────────
private val MonoFontFamily = FontFamily.Monospace

// ═══════════════════════════════════════════════════════════════════════════════
// Material 3 Typography — Bengali (default)
// Display / Headline / Title / Body / Label hierarchy per M3 spec.
// ═══════════════════════════════════════════════════════════════════════════════
val StudyMasterTypography = Typography(

    // ── Display ─────────────────────────────────────────────────────────────
    displayLarge = TextStyle(
        fontFamily = BengaliFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 57.sp,
        lineHeight = 64.sp,
        letterSpacing = (-0.25).sp,
    ),
    displayMedium = TextStyle(
        fontFamily = BengaliFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 45.sp,
        lineHeight = 52.sp,
        letterSpacing = 0.sp,
    ),
    displaySmall = TextStyle(
        fontFamily = BengaliFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp,
        lineHeight = 44.sp,
        letterSpacing = 0.sp,
    ),

    // ── Headline ────────────────────────────────────────────────────────────
    headlineLarge = TextStyle(
        fontFamily = BengaliFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp,
    ),
    headlineMedium = TextStyle(
        fontFamily = BengaliFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp,
    ),
    headlineSmall = TextStyle(
        fontFamily = BengaliFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp,
    ),

    // ── Title ───────────────────────────────────────────────────────────────
    titleLarge = TextStyle(
        fontFamily = BengaliFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = BengaliFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp,
    ),
    titleSmall = TextStyle(
        fontFamily = BengaliFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
    ),

    // ── Body ────────────────────────────────────────────────────────────────
    bodyLarge = TextStyle(
        fontFamily = BengaliFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = BengaliFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = BengaliFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp,
    ),

    // ── Label ───────────────────────────────────────────────────────────────
    labelLarge = TextStyle(
        fontFamily = BengaliFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
    ),
    labelMedium = TextStyle(
        fontFamily = BengaliFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
    ),
    labelSmall = TextStyle(
        fontFamily = BengaliFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
    ),
)

// ═══════════════════════════════════════════════════════════════════════════════
// Material 3 Typography — English variant
// Same hierarchy but uses Poppins. Use this for timer digits, XP values,
// statistics numbers, and any English-only UI sections.
// ═══════════════════════════════════════════════════════════════════════════════
val EnglishTypography = Typography(

    displayLarge = TextStyle(
        fontFamily = EnglishFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 57.sp,
        lineHeight = 64.sp,
        letterSpacing = (-0.25).sp,
    ),
    displayMedium = TextStyle(
        fontFamily = EnglishFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 45.sp,
        lineHeight = 52.sp,
        letterSpacing = 0.sp,
    ),
    displaySmall = TextStyle(
        fontFamily = EnglishFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp,
        lineHeight = 44.sp,
        letterSpacing = 0.sp,
    ),

    headlineLarge = TextStyle(
        fontFamily = EnglishFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp,
    ),
    headlineMedium = TextStyle(
        fontFamily = EnglishFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp,
    ),
    headlineSmall = TextStyle(
        fontFamily = EnglishFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp,
    ),

    titleLarge = TextStyle(
        fontFamily = EnglishFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = EnglishFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp,
    ),
    titleSmall = TextStyle(
        fontFamily = EnglishFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
    ),

    bodyLarge = TextStyle(
        fontFamily = EnglishFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = EnglishFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = EnglishFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp,
    ),

    labelLarge = TextStyle(
        fontFamily = EnglishFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
    ),
    labelMedium = TextStyle(
        fontFamily = EnglishFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
    ),
    labelSmall = TextStyle(
        fontFamily = EnglishFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
    ),
)

// ═══════════════════════════════════════════════════════════════════════════════
// Pre-built "combo" styles that mix Bengali label + English number.
// These are useful when you need "লেভেল ৭" or "XP: 1,250" in one Text.
// ═══════════════════════════════════════════════════════════════════════════════
@Immutable
data class MixedTypographyStyles(
    val bengali: TextStyle,
    val english: TextStyle,
)

// ─── Pre-built mixed pairs ─────────────────────────────────────────────────
object MixedStyles {

    val displayLarge = MixedTypographyStyles(
        bengali = StudyMasterTypography.displayLarge,
        english = EnglishTypography.displayLarge,
    )

    val headlineMedium = MixedTypographyStyles(
        bengali = StudyMasterTypography.headlineMedium,
        english = EnglishTypography.headlineMedium,
    )

    val titleLarge = MixedTypographyStyles(
        bengali = StudyMasterTypography.titleLarge,
        english = EnglishTypography.titleLarge,
    )

    val titleMedium = MixedTypographyStyles(
        bengali = StudyMasterTypography.titleMedium,
        english = EnglishTypography.titleMedium,
    )

    val bodyLarge = MixedTypographyStyles(
        bengali = StudyMasterTypography.bodyLarge,
        english = EnglishTypography.bodyLarge,
    )

    val bodyMedium = MixedTypographyStyles(
        bengali = StudyMasterTypography.bodyMedium,
        english = EnglishTypography.bodyMedium,
    )

    val labelMedium = MixedTypographyStyles(
        bengali = StudyMasterTypography.labelMedium,
        english = EnglishTypography.labelMedium,
    )
}

// ═══════════════════════════════════════════════════════════════════════════════
// Special-purpose TextStyles used across the app
// ═══════════════════════════════════════════════════════════════════════════════
object SpecialTextStyles {

    /** Large timer display (25:00) — monospaced English for fixed-width digits */
    val timerDisplay = TextStyle(
        fontFamily = MonoFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 72.sp,
        lineHeight = 80.sp,
        letterSpacing = 2.sp,
    )

    /** XP counter number — large Poppins bold */
    val xpCounter = TextStyle(
        fontFamily = EnglishFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 48.sp,
        lineHeight = 52.sp,
        letterSpacing = (-1).sp,
    )

    /** Streak fire number — e.g. "🔥 30" */
    val streakNumber = TextStyle(
        fontFamily = EnglishFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp,
    )

    /** Level badge — "LV 7" pill text */
    val levelBadge = TextStyle(
        fontFamily = EnglishFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 1.sp,
    )

    /** Stat card value — "৪২ ঘণ্টা" */
    val statValue = TextStyle(
        fontFamily = BengaliFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 34.sp,
        letterSpacing = 0.sp,
    )

    /** Stat card label — "মোট অধ্যয়ন" */
    val statLabel = TextStyle(
        fontFamily = BengaliFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.3.sp,
    )

    /** Exam countdown days — "১৫ দিন" */
    val countdownDays = TextStyle(
        fontFamily = BengaliFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 40.sp,
        lineHeight = 44.sp,
        letterSpacing = 0.sp,
    )

    /** Countdown unit label — "দিন বাকি" */
    val countdownUnit = TextStyle(
        fontFamily = BengaliFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.2.sp,
    )

    /** Bottom nav label */
    val bottomNavLabel = TextStyle(
        fontFamily = BengaliFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 14.sp,
        letterSpacing = 0.2.sp,
    )

    /** Badge count on nav / notification */
    val badgeCount = TextStyle(
        fontFamily = EnglishFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 10.sp,
        lineHeight = 12.sp,
        letterSpacing = 0.2.sp,
    )

    /** Section header — e.g. "আজকের রুটিন" */
    val sectionHeader = TextStyle(
        fontFamily = BengaliFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp,
    )
}

// ═══════════════════════════════════════════════════════════════════════════════
// Bengali numeral helpers — convert Western digits to Bangla digits
// ═══════════════════════════════════════════════════════════════════════════════
private val bengaliDigits = charArrayOf('০', '১', '২', '৩', '৪', '৫', '৬', '৭', '৮', '৯')

fun Int.toBengaliDigits(): String = toString().map { digit ->
    if (digit.isDigit()) bengaliDigits[digit.digitToInt()] else digit
}.joinToString("")

fun Long.toBengaliDigits(): String = toString().map { digit ->
    if (digit.isDigit()) bengaliDigits[digit.digitToInt()] else digit
}.joinToString("")

fun Float.toBengaliDigits(decimalPlaces: Int = 1): String =
    String.format("%.${decimalPlaces}f", this).map { digit ->
        if (digit.isDigit()) bengaliDigits[digit.digitToInt()] else digit
    }.joinToString("")

fun Double.toBengaliDigits(decimalPlaces: Int = 1): String =
    String.format("%.${decimalPlaces}f", this).map { digit ->
        if (digit.isDigit()) bengaliDigits[digit.digitToInt()] else digit
    }.joinToString("")
