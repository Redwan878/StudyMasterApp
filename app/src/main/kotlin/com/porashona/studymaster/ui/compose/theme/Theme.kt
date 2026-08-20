/*
package com.porashona.studymaster.ui.compose.theme

import android.app.Activity
import android.os.Build
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.Spring.DampingRatioNoBouncy
import androidx.compose.animation.core.Spring.StiffnessMediumLow
import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController

// ═══════════════════════════════════════════════════════════════════════════════
// StudyMaster Design System — Theme
//
// Three theme variants: Dark (default), Light, AMOLED Black.
// Supports Material You dynamic colors on Android 12+.
// Provides custom shape system with glassmorphism-friendly radii (16–28 dp)
// and animation tween specs for rewarding transitions.
// ═══════════════════════════════════════════════════════════════════════════════

// ─── Theme mode enum ────────────────────────────────────────────────────────
enum class ThemeMode {
    DARK,       // standard dark
    LIGHT,      // light mode
    AMOLED,     // true-black for OLED screens
    SYSTEM,     // follow system setting (falls back to DARK)
}

// ═══════════════════════════════════════════════════════════════════════════════
// Color Schemes
// ═══════════════════════════════════════════════════════════════════════════════

private val DarkColorScheme = darkColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,
    primaryContainer = PrimaryContainerDark,
    onPrimaryContainer = OnPrimaryContainerDark,
    secondary = Secondary,
    onSecondary = OnSecondary,
    secondaryContainer = SecondaryContainerDark,
    onSecondaryContainer = OnSecondaryContainerDark,
    tertiary = Tertiary,
    background = DarkBackground,
    onBackground = DarkOnSurface,
    surface = DarkSurface,
    onSurface = DarkOnSurface,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = DarkOnSurfaceVariant,
    outline = OutlineDark,
    outlineVariant = OutlineVariantDark,
    error = Error,
    onError = Color.White,
    errorContainer = Color(0xFF4A1A1A),
    onErrorContainer = Color(0xFFFFD9D9),
    inverseSurface = LightSurface,
    inverseOnSurface = LightOnSurface,
    inversePrimary = PrimaryDark,
    scrim = Scrim,
    surfaceTint = Primary,
)

private val LightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,
    primaryContainer = PrimaryContainerLight,
    onPrimaryContainer = OnPrimaryContainerLight,
    secondary = Secondary,
    onSecondary = OnSecondary,
    secondaryContainer = SecondaryContainerLight,
    onSecondaryContainer = OnSecondaryContainerLight,
    tertiary = Tertiary,
    background = LightBackground,
    onBackground = LightOnSurface,
    surface = LightSurface,
    onSurface = LightOnSurface,
    surfaceVariant = LightSurfaceVariant,
    onSurfaceVariant = LightOnSurfaceVariant,
    outline = OutlineLight,
    outlineVariant = OutlineVariantLight,
    error = Error,
    onError = Color.White,
    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = Color(0xFF410002),
    inverseSurface = DarkSurface,
    inverseOnSurface = DarkOnSurface,
    inversePrimary = PrimaryLight,
    scrim = Scrim,
    surfaceTint = Primary,
)

private val AmoledColorScheme = darkColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,
    primaryContainer = Color(0xFF1A1245),
    onPrimaryContainer = PrimaryLight,
    secondary = Secondary,
    onSecondary = OnSecondary,
    secondaryContainer = Color(0xFF3D1525),
    onSecondaryContainer = SecondaryLight,
    tertiary = Tertiary,
    background = AmoledBackground,
    onBackground = Color(0xFFE0E0E0),
    surface = AmoledSurface,
    onSurface = Color(0xFFE0E0E0),
    surfaceVariant = AmoledSurfaceVariant,
    onSurfaceVariant = Color(0xFFB0B0B0),
    outline = Color(0xFF2A2A2A),
    outlineVariant = Color(0xFF1A1A1A),
    error = Error,
    onError = Color.White,
    errorContainer = Color(0xFF2A0A0A),
    onErrorContainer = Color(0xFFFFD9D9),
    inverseSurface = LightSurface,
    inverseOnSurface = LightOnSurface,
    inversePrimary = PrimaryDark,
    scrim = Scrim,
    surfaceTint = Primary,
)

// ═══════════════════════════════════════════════════════════════════════════════
// Glassmorphism-aware Shape System
// Material 3's default shapes are fine, but we add larger radii for our
// glass cards and custom shape tokens.
// ═══════════════════════════════════════════════════════════════════════════════

@Immutable
data class GlassShapes(
    val cardRadius: Dp = 20.dp,
    val cardRadiusLarge: Dp = 28.dp,
    val cardRadiusSmall: Dp = 16.dp,
    val dialogRadius: Dp = 28.dp,
    val bottomSheetRadius: Dp = 28.dp,
    val chipRadius: Dp = 100.dp,   // fully rounded pills
    val buttonRadius: Dp = 16.dp,
    val bottomNavRadius: Dp = 24.dp,
    val indicatorRadius: Dp = 8.dp,
    val inputFieldRadius: Dp = 16.dp,
    val avatarRadius: Dp = 20.dp,
    val badgeRadius: Dp = 12.dp,
    val timerCircleRadius: Dp = 200.dp,
)

val LocalGlassShapes = compositionLocalOf { GlassShapes() }

// ═══════════════════════════════════════════════════════════════════════════════
// Animation Specs — rewarding, snappy, smooth
// ═══════════════════════════════════════════════════════════════════════════════

@Immutable
class StudyMasterMotion(
    /** Standard fade-in for cards (300 ms, ease-out) */
    val fadeIn: TweenSpec<Float> = tween(
        durationMillis = 300,
        easing = CubicBezierEasing(0f, 0f, 0.2f, 1f),
    ),

    /** Scale-in for cards appearing (350 ms, spring-like ease) */
    val scaleIn: TweenSpec<Float> = tween(
        durationMillis = 350,
        easing = CubicBezierEasing(0.34f, 1.56f, 0.64f, 1f),
    ),

    /** Slide up from below (400 ms) */
    val slideUp: TweenSpec<Float> = tween(
        durationMillis = 400,
        easing = CubicBezierEasing(0f, 0f, 0.2f, 1f),
    ),

    /** Slide in from left (300 ms) */
    val slideInLeft: TweenSpec<Float> = tween(
        durationMillis = 300,
        easing = CubicBezierEasing(0f, 0f, 0.2f, 1f),
    ),

    /** XP gain flash (600 ms, overshoot for punch) */
    val xpGain: TweenSpec<Float> = tween(
        durationMillis = 600,
        easing = CubicBezierEasing(0.34f, 1.56f, 0.64f, 1f),
    ),

    /** Streak fire bounce (700 ms, heavy overshoot) */
    val streakBounce: SpringSpec<Float> = spring(
        dampingRatio = 0.4f,
        stiffness = 300f,
    ),

    /** Level-up celebration (800 ms, heavy spring) */
    val levelUp: SpringSpec<Float> = spring(
        dampingRatio = 0.3f,
        stiffness = 200f,
    ),

    /** Bottom nav indicator slide (spring, medium) */
    val navIndicator: SpringSpec<Dp> = spring(
        dampingRatio = Spring.DampingRatioNoBouncy,
        stiffness = Spring.StiffnessMediumLow,
    ),

    /** Standard content fade (200 ms) */
    val contentFade: TweenSpec<Float> = tween(
        durationMillis = 200,
    ),

    /** Color change transition (250 ms) */
    val colorTransition: TweenSpec<Color> = tween(
        durationMillis = 250,
        easing = CubicBezierEasing(0.4f, 0f, 0.2f, 1f),
    ),

    /** Progress bar fill (800 ms, ease-in-out for smooth fill) */
    val progressFill: TweenSpec<Float> = tween(
        durationMillis = 800,
        easing = CubicBezierEasing(0.4f, 0f, 0.2f, 1f),
    ),

    /** Button press scale-down (100 ms) */
    val buttonPress: TweenSpec<Float> = tween(
        durationMillis = 100,
        easing = CubicBezierEasing(0.4f, 0f, 1f, 1f),
    ),

    /** Button release scale-up (200 ms) */
    val buttonRelease: SpringSpec<Float> = spring(
        dampingRatio = Spring.DampingRatioNoBouncy,
        stiffness = 400f,
    ),

    /** Dialog appear (250 ms scale + fade) */
    val dialogEnter: TweenSpec<Float> = tween(
        durationMillis = 250,
        easing = CubicBezierEasing(0f, 0f, 0.2f, 1f),
    ),

    /** Shimmer sweep (1200 ms, linear for infinite repeat) */
    val shimmer: TweenSpec<Float> = tween(
        durationMillis = 1200,
        easing = CubicBezierEasing(0.4f, 0f, 0.6f, 1f),
    ),
)

val LocalMotion = compositionLocalOf { StudyMasterMotion() }

// ═══════════════════════════════════════════════════════════════════════════════
// Theme wrapper — call this at the root of your Compose tree
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
fun StudyMasterTheme(
    themeMode: ThemeMode = ThemeMode.DARK,
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit,
) {
    val context = LocalContext.current
    val isSystemDark = isSystemInDarkTheme()
    val useDark = when (themeMode) {
        ThemeMode.DARK -> true
        ThemeMode.LIGHT -> false
        ThemeMode.AMOLED -> true
        ThemeMode.SYSTEM -> isSystemDark
    }

    // Material You dynamic color on Android 12+ (API 31+)
    val supportsDynamicColor = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
    val colorScheme = when {
        dynamicColor && supportsDynamicColor && useDark -> {
            val dynamicDark = android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
            if (dynamicDark) {
                @Suppress("DEPRECATION")
                darkColorScheme(
                    primary = Primary,
                    onPrimary = OnPrimary,
                    primaryContainer = PrimaryContainerDark,
                    onPrimaryContainer = OnPrimaryContainerDark,
                    secondary = Secondary,
                    onSecondary = OnSecondary,
                    secondaryContainer = SecondaryContainerDark,
                    onSecondaryContainer = OnSecondaryContainerDark,
                    tertiary = Tertiary,
                    background = DarkBackground,
                    onBackground = DarkOnSurface,
                    surface = DarkSurface,
                    onSurface = DarkOnSurface,
                    surfaceVariant = DarkSurfaceVariant,
                    onSurfaceVariant = DarkOnSurfaceVariant,
                    outline = OutlineDark,
                    outlineVariant = OutlineVariantDark,
                    error = Error,
                    onError = Color.White,
                    surfaceTint = Primary,
                )
            } else {
                DarkColorScheme
            }
        }
        dynamicColor && supportsDynamicColor && !useDark -> {
            @Suppress("DEPRECATION")
            lightColorScheme(
                primary = Primary,
                onPrimary = OnPrimary,
                primaryContainer = PrimaryContainerLight,
                onPrimaryContainer = OnPrimaryContainerLight,
                secondary = Secondary,
                onSecondary = OnSecondary,
                secondaryContainer = SecondaryContainerLight,
                onSecondaryContainer = OnSecondaryContainerLight,
                tertiary = Tertiary,
                background = LightBackground,
                onBackground = LightOnSurface,
                surface = LightSurface,
                onSurface = LightOnSurface,
                surfaceVariant = LightSurfaceVariant,
                onSurfaceVariant = LightOnSurfaceVariant,
                outline = OutlineLight,
                outlineVariant = OutlineVariantLight,
                error = Error,
                onError = Color.White,
                surfaceTint = Primary,
            )
        }
        themeMode == ThemeMode.AMOLED -> AmoledColorScheme
        useDark -> DarkColorScheme
        else -> LightColorScheme
    }

    // System bar colour management
    val view = LocalView.current
    val systemUiController = rememberSystemUiController()

    SideEffect {
        val window = (view.context as? Activity)?.window
        window?.statusBarColor = colorScheme.surface.toArgb()
        window?.navigationBarColor = colorScheme.surface.toArgb()

        val isLight = !useDark
        systemUiController.setStatusBarColor(
            color = colorScheme.surface,
            darkIcons = isLight,
        )
        systemUiController.setNavigationBarColor(
            color = colorScheme.surface.copy(alpha = 0.95f),
            darkIcons = isLight,
        )
    }

    val glassShapes = remember { GlassShapes() }
    val motion = remember { StudyMasterMotion() }

    CompositionLocalProvider(
        LocalGlassShapes provides glassShapes,
        LocalMotion provides motion,
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = StudyMasterTypography,
            content = content,
        )
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Convenience — check if currently in dark / AMOLED
// ═══════════════════════════════════════════════════════════════════════════════

val MaterialTheme.isDark: Boolean
    get() = !isSystemInDarkTheme().not()

val MaterialTheme.isAmoled: Boolean
    get() = isSystemInDarkTheme() && colorScheme.background == Color.Black

/** Returns the appropriate glass surface colour for the current theme */
val MaterialTheme.glassSurface: Color
    get() = if (isSystemInDarkTheme()) GlassDark else GlassLight

/** Returns the appropriate glass border colour for the current theme */
val MaterialTheme.glassBorder: Color
    get() = if (isSystemInDarkTheme()) GlassBorderDark else GlassBorderLight

/** Returns the glass surface with 80 % opacity */
val MaterialTheme.glassSurface80: Color
    get() = if (isSystemInDarkTheme()) GlassDarkAlpha80 else GlassLightAlpha90
*/