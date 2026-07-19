package com.porashona.studymaster.ui.compose.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.porashona.studymaster.ui.compose.theme.AchievementLocked
import com.porashona.studymaster.ui.compose.theme.AchievementUnlocked
import com.porashona.studymaster.ui.compose.theme.toComposeColor
import com.porashona.studymaster.ui.compose.theme.DarkSurfaceVariant
import com.porashona.studymaster.ui.compose.theme.EnglishFontFamily
import com.porashona.studymaster.ui.compose.theme.Error
import com.porashona.studymaster.ui.compose.theme.GlassBorderDark
import com.porashona.studymaster.ui.compose.theme.GlassBorderLight
import com.porashona.studymaster.ui.compose.theme.GlassDarkAlpha60
import com.porashona.studymaster.ui.compose.theme.GlassLightAlpha90
import com.porashona.studymaster.ui.compose.theme.HexColors
import com.porashona.studymaster.ui.compose.theme.LevelUp
import com.porashona.studymaster.ui.compose.theme.LocalGlassShapes
import com.porashona.studymaster.ui.compose.theme.LocalMotion
import com.porashona.studymaster.ui.compose.theme.PriorityHigh
import com.porashona.studymaster.ui.compose.theme.PriorityLow
import com.porashona.studymaster.ui.compose.theme.PriorityMedium
import com.porashona.studymaster.ui.compose.theme.PriorityUrgent
import com.porashona.studymaster.ui.compose.theme.StreakFire
import com.porashona.studymaster.ui.compose.theme.StudyMasterTypography
import com.porashona.studymaster.ui.compose.theme.SubjectPalette
import com.porashona.studymaster.ui.compose.theme.Success
import com.porashona.studymaster.ui.compose.theme.TimerWork
import com.porashona.studymaster.ui.compose.theme.Warning
import com.porashona.studymaster.ui.compose.theme.XpBarBg
import com.porashona.studymaster.ui.compose.theme.XpBarFill
import com.porashona.studymaster.ui.compose.theme.XpGain
import com.porashona.studymaster.ui.compose.theme.isDark
import androidx.compose.ui.unit.sp
import com.porashona.studymaster.ui.compose.theme.toBengaliDigits
import com.porashona.studymaster.ui.compose.theme.toHexString
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

// ═══════════════════════════════════════════════════════════════════════════════
// 1. AnimatedCounter
// Smoothly animates a number from 0 → [target] with spring physics.
// Used for XP totals, streak counts, study hours, etc.
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
fun AnimatedCounter(
    target: Int,
    modifier: Modifier = Modifier,
    useBengali: Boolean = false,
    style: androidx.compose.ui.text.TextStyle = StudyMasterTypography.headlineMedium,
    color: Color = MaterialTheme.colorScheme.onSurface,
    durationMillis: Int = 600,
) {
    var displayValue by remember { mutableIntStateOf(0) }
    val animatedValue by animateFloatAsState(
        targetValue = target.toFloat(),
        animationSpec = tween(durationMillis, easing = FastOutSlowInEasing),
        label = "animatedCounter",
    )

    LaunchedEffect(animatedValue) {
        displayValue = animatedValue.toInt()
    }

    Text(
        text = if (useBengali) displayValue.toBengaliDigits() else displayValue.toString(),
        style = style.copy(
            color = color,
            fontFamily = if (useBengali) style.fontFamily else EnglishFontFamily,
        ),
        modifier = modifier,
        textAlign = TextAlign.Center,
        maxLines = 1,
    )
}

// ═══════════════════════════════════════════════════════════════════════════════
// 2. StreakCalendarGrid  (GitHub-style contribution heat-map)
//
// Displays the last [totalWeeks] weeks × 7 days as a grid of small cells.
// Each cell is coloured by study minutes on that day (0 → empty,
// low → light, high → vivid).
// ═══════════════════════════════════════════════════════════════════════════════

data class StreakDayData(
    val date: Date,
    val minutesStudied: Int,
)

@Composable
fun StreakCalendarGrid(
    data: List<StreakDayData>,
    modifier: Modifier = Modifier,
    totalWeeks: Int = 20,
    cellSize: Dp = 14.dp,
    cellSpacing: Dp = 3.dp,
) {
    val isDark = MaterialTheme.isDark
    val surfaceColor = if (isDark) DarkSurfaceVariant else Color(0xFFE8E8E8)

    // Build a date → minutes lookup
    val dayMap = remember(data) {
        val fmt = SimpleDateFormat("yyyyMMdd", Locale.US)
        data.associateBy { fmt.format(it.date) }
    }

    // Generate grid cells: column = week, row = day-of-week (0=Mon)
    val gridCells = remember(totalWeeks) {
        val cal = Calendar.getInstance().apply {
            // go back to the Monday of (totalWeeks - 1) weeks ago
            add(Calendar.WEEK_OF_YEAR, -(totalWeeks - 1))
            set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val fmt = SimpleDateFormat("yyyyMMdd", Locale.US)
        buildList {
            for (week in 0 until totalWeeks) {
                for (day in 0 until 7) {
                    val key = fmt.format(cal.time)
                    add(Triple(week, day, dayMap[key]?.minutesStudied ?: 0))
                    cal.add(Calendar.DAY_OF_MONTH, 1)
                }
            }
        }
    }

    // Colour for a given minute count
    fun colorForMinutes(m: Int): Color = when {
        m == 0    -> surfaceColor
        m < 30    -> XpBarFill.copy(alpha = 0.2f)
        m < 60    -> XpBarFill.copy(alpha = 0.45f)
        m < 120   -> XpBarFill.copy(alpha = 0.7f)
        else      -> XpBarFill
    }

    Column(modifier = modifier) {
        // Day-of-week labels
        Row(
            modifier = Modifier.padding(start = 24.dp, end = cellSpacing),
            horizontalArrangement = Arrangement.spacedBy(0.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            listOf("সোম", "", "বুধ", "", "শুক্র", "", "রবি").forEachIndexed { index, label ->
                Box(
                    modifier = Modifier
                        .height(cellSize + cellSpacing)
                        .width(24.dp),
                    contentAlignment = Alignment.BottomCenter,
                ) {
                    if (label.isNotEmpty()) {
                        Text(
                            text = label,
                            style = StudyMasterTypography.labelSmall.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = (cellSize.value * 0.6).sp,
                            ),
                        )
                    }
                }
            }
        }

        // Grid
        Row(
            horizontalArrangement = Arrangement.spacedBy(cellSpacing),
        ) {
            // Month labels column — show month name on the first day of each month
            Column(verticalArrangement = Arrangement.spacedBy(cellSpacing)) {
                for (week in 0 until totalWeeks) {
                    val idx = week * 7
                    if (idx < gridCells.size) {
                        val (_, _, minutes) = gridCells[idx]
                        Box(
                            modifier = Modifier.size(cellSize),
                        ) {
                            // Month label could go here; simplified: empty
                        }
                    }
                }
            }

            // Weeks columns
            for (week in 0 until totalWeeks) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(cellSpacing),
                ) {
                    for (day in 0 until 7) {
                        val idx = week * 7 + day
                        if (idx < gridCells.size) {
                            val (_, _, minutes) = gridCells[idx]
                            Box(
                                modifier = Modifier
                                    .size(cellSize)
                                    .clip(RoundedCornerShape(3.dp))
                                    .background(colorForMinutes(minutes)),
                            )
                        }
                    }
                }
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// 3. ExamCountdownBanner
// Animated banner showing days remaining until the nearest exam.
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
fun ExamCountdownBanner(
    examName: String,
    examDate: Date,
    modifier: Modifier = Modifier,
    subjectColorHex: String? = null,
    onClick: (() -> Unit)? = null,
) {
    val isDark = MaterialTheme.isDark
    val accentColor = subjectColorHex?.toComposeColor()
        ?: ActivityColors.accent(ActivityType.EXAM)

    val daysLeft = remember(examDate) {
        val diff = examDate.time - System.currentTimeMillis()
        maxOf(0L, (diff / (1000 * 60 * 60 * 24))).toInt()
    }

    val hoursLeft = remember(examDate) {
        val diff = examDate.time - System.currentTimeMillis()
        maxOf(0L, ((diff % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60))).toInt()
    }

    val isUrgent = daysLeft <= 7

    val glowAlpha by rememberInfiniteTransition(label = "countdownGlow").animateFloat(
        initialValue = 0.1f,
        targetValue = 0.3f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "glow"
    )

    val bgGradient = Brush.horizontalGradient(
        colors = listOf(
            accentColor.copy(alpha = if (isDark) 0.2f + glowAlpha else 0.1f + glowAlpha),
            accentColor.copy(alpha = if (isDark) 0.1f + glowAlpha else 0.05f + glowAlpha),
        ),
    )


    GlassmorphicCard(
        modifier = modifier
            .fillMaxWidth()
            .then(
                if (onClick != null) Modifier.clickable(onClick = onClick)
                else Modifier
            ),
        variant = GlassCardVariant.FILLED,
        tint = accentColor,
        cornerRadius = LocalGlassShapes.current.cardRadiusLarge,
        padding = 16.dp,
        animated = true,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(bgGradient, RoundedCornerShape(LocalGlassShapes.current.cardRadiusLarge))
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "পরবর্তী পরীক্ষা",
                    style = StudyMasterTypography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = examName,
                    style = StudyMasterTypography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                Row(
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    Text(
                        text = daysLeft.toBengaliDigits(),
                        style = StudyMasterTypography.displaySmall.copy(
                            fontFamily = EnglishFontFamily,
                            fontWeight = FontWeight.Bold,
                            color = if (isUrgent) Error else accentColor,
                        ),
                        modifier = if (isUrgent) Modifier.graphicsLayer { scaleX = pulse; scaleY = pulse }
                                   else Modifier,
                    )
                    Text(
                        text = "দিন",
                        style = StudyMasterTypography.bodySmall,
                        color = if (isUrgent) Error else MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(bottom = 6.dp),
                    )
                }
                Text(
                    text = "${hoursLeft.toBengaliDigits()} ঘণ্টা",
                    style = StudyMasterTypography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// 4. SubjectChip
// A colour-coded pill showing a subject name. Colour comes from
// [SubjectPalette] based on a consistent index or stored hex.
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
fun SubjectChip(
    subjectName: String,
    modifier: Modifier = Modifier,
    colorIndex: Int = 0,
    colorHex: String? = null,
    compact: Boolean = false,
    selected: Boolean = false,
    onClick: (() -> Unit)? = null,
) {
    val resolvedColor = remember(colorHex, colorIndex) {
        colorHex?.toComposeColor() ?: SubjectPalette.colorForIndex(colorIndex)
    }

    val isDark = MaterialTheme.isDark
    val shape = RoundedCornerShape(LocalGlassShapes.current.chipRadius)

    val bgColor = if (selected) {
        resolvedColor.copy(alpha = if (isDark) 0.3f else 0.2f)
    } else {
        resolvedColor.copy(alpha = if (isDark) 0.15f else 0.1f)
    }
    val textColor = if (selected) resolvedColor else resolvedColor.copy(alpha = 0.9f)
    val borderColor = if (selected) resolvedColor.copy(alpha = 0.5f) else Color.Transparent

    val hPad = if (compact) 8.dp else 12.dp
    val vPad = if (compact) 3.dp else 6.dp
    val textStyle = if (compact) StudyMasterTypography.labelSmall else StudyMasterTypography.labelMedium

    Surface(
        modifier = modifier
            .border(
                width = if (selected) 1.dp else 0.dp,
                color = borderColor,
                shape = shape,
            )
            .then(
                if (onClick != null) Modifier.clip(shape).clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onClick,
                ) else Modifier
            ),
        shape = shape,
        color = bgColor,
        contentColor = textColor,
    ) {
        Text(
            text = subjectName,
            style = textStyle.copy(fontWeight = FontWeight.Medium),
            modifier = Modifier.padding(horizontal = hPad, vertical = vPad),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// 5. PriorityBadge
// Visual badge for task priority (LOW / MEDIUM / HIGH / URGENT).
// ═══════════════════════════════════════════════════════════════════════════════

enum class Priority(val dbValue: String) {
    LOW("LOW"),
    MEDIUM("MEDIUM"),
    HIGH("HIGH"),
    URGENT("URGENT");

    companion object {
        fun fromDb(value: String?): Priority =
            entries.firstOrNull { it.dbValue.equals(value, ignoreCase = true) }
                ?: MEDIUM
    }
}

@Composable
fun PriorityBadge(
    priority: Priority,
    modifier: Modifier = Modifier,
    showLabel: Boolean = true,
) {
    val (color, bengaliLabel) = when (priority) {
        Priority.LOW    -> PriorityLow to "নিম্ন"
        Priority.MEDIUM -> PriorityMedium to "মাঝারি"
        Priority.HIGH   -> PriorityHigh to "উচ্চ"
        Priority.URGENT -> PriorityUrgent to "জরুরি"
    }

    val shape = RoundedCornerShape(6.dp)
    val animatedColor by animateColorAsState(
        targetValue = color,
        animationSpec = LocalMotion.current.colorTransition,
        label = "priorityColor",
    )

    Surface(
        modifier = modifier,
        shape = shape,
        color = animatedColor.copy(alpha = 0.15f),
        contentColor = animatedColor,
    ) {
        if (showLabel) {
            Row(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                if (priority == Priority.URGENT) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = null,
                        modifier = Modifier.size(12.dp),
                        tint = animatedColor,
                    )
                }
                Text(
                    text = bengaliLabel,
                    style = StudyMasterTypography.labelSmall.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = animatedColor,
                    ),
                    maxLines = 1,
                )
            }
        } else {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .padding(2.dp)
                    .background(animatedColor, CircleShape),
            )
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// 6. XPProgressIndicator
// Animated horizontal XP bar showing current XP / required XP for next level.
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
fun XPProgressIndicator(
    currentXp: Int,
    requiredXp: Int,
    level: Int,
    modifier: Modifier = Modifier,
    showLevel: Boolean = true,
    animate: Boolean = true,
) {
    val fraction = remember(currentXp, requiredXp) {
        if (requiredXp <= 0) 1f else (currentXp.toFloat() / requiredXp.toFloat()).coerceIn(0f, 1f)
    }

    val animatedFraction by animateFloatAsState(
        targetValue = fraction,
        animationSpec = if (animate) LocalMotion.current.progressFill else tween(0),
        label = "xpProgress",
    )

    val isDark = MaterialTheme.isDark

    Column(modifier = modifier) {
        // Level label + XP text
        if (showLevel) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = LevelUp.copy(alpha = 0.15f),
                    contentColor = LevelUp,
                ) {
                    Text(
                        text = "LV $level",
                        style = StudyMasterTypography.labelSmall.copy(
                            fontFamily = EnglishFontFamily,
                            fontWeight = FontWeight.Bold,
                        ),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                    )
                }
                Text(
                    text = "$currentXp / $requiredXp XP",
                    style = StudyMasterTypography.labelSmall.copy(
                        fontFamily = EnglishFontFamily,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    ),
                )
            }
        }

        // Progress bar track
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(100.dp))
                .background(XpBarBg),
            contentAlignment = Alignment.CenterStart,
        ) {
            // Animated fill
            Box(
                modifier = Modifier
                    .fillMaxWidth(animatedFraction)
                    .height(8.dp)
                    .clip(RoundedCornerShape(100.dp))
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                XpBarFill,
                                XpBarFill.copy(red = 0.5f, green = 0.85f, blue = 1f),
                            ),
                        )
                    ),
            )
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// 7. EmptyStateView
// Illustrated placeholder shown when a list / screen has no data.
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
fun EmptyStateView(
    title: String,
    description: String,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    actionLabel: String? = null,
    onAction: (() -> Unit)? = null,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        // Icon circle
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(
                    MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f),
                    CircleShape,
                ),
            contentAlignment = Alignment.Center,
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(36.dp),
                    tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                )
            } else {
                // Default placeholder: book icon built from Material symbols
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = null,
                    modifier = Modifier.size(36.dp),
                    tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                )
            }
        }

        Spacer(Modifier.height(20.dp))

        Text(
            text = title,
            style = StudyMasterTypography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = description,
            style = StudyMasterTypography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
        )

        if (actionLabel != null && onAction != null) {
            Spacer(Modifier.height(20.dp))

            Button(
                onClick = onAction,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                ),
                shape = RoundedCornerShape(LocalGlassShapes.current.buttonRadius),
            ) {
                Text(
                    text = actionLabel,
                    style = StudyMasterTypography.labelLarge,
                )
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// 8. LoadingAnimation — shimmer skeleton (no Lottie dependency required)
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
fun LoadingAnimation(
    modifier: Modifier = Modifier,
    shimmerEnabled: Boolean = true,
) {
    val isDark = MaterialTheme.isDark
    val baseColor = if (isDark) DarkSurfaceVariant else Color(0xFFE0E0E0)
    val highlightColor = if (isDark) Color(0xFF3A3D4A) else Color(0xFFF5F5F5)

    val infiniteTransition = rememberInfiniteTransition(label = "shimmer")
    val shimmerOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1200f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
        ),
        label = "shimmerOffset",
    )

    val brush = if (shimmerEnabled) {
        Brush.linearGradient(
            colors = listOf(baseColor, highlightColor, baseColor),
            start = Offset(shimmerOffset - 400f, 0f),
            end = Offset(shimmerOffset, 0f),
        )
    } else {
        Brush.horizontalGradient(colors = listOf(baseColor, baseColor))
    }

    val shapes = LocalGlassShapes.current

    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        // Fake card skeleton
        repeat(3) { index ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(shapes.cardRadius))
                    .background(brush)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                // Title line
                Box(
                    modifier = Modifier
                        .fillMaxWidth(if (index == 1) 0.7f else 0.5f)
                        .height(16.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(brush),
                )
                // Subtitle line
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(12.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(brush),
                )
                // Body lines
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .height(12.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(brush),
                )
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// 9. ConfirmDeleteDialog — glassmorphic dialog for destructive actions
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
fun ConfirmDeleteDialog(
    title: String,
    message: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    confirmLabel: String = "মুছে ফেলুন",
    dismissLabel: String = "বাতিল",
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(LocalGlassShapes.current.dialogRadius),
        containerColor = if (MaterialTheme.isDark) GlassDarkAlpha60
                         else GlassLightAlpha90,
        titleContentColor = MaterialTheme.colorScheme.onSurface,
        textContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        title = {
            Text(
                text = title,
                style = StudyMasterTypography.titleMedium,
            )
        },
        text = {
            Text(
                text = message,
                style = StudyMasterTypography.bodyMedium,
            )
        },
        confirmButton = {
            TextButton(
                onClick = onConfirm,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.textButtonColors(
                    contentColor = Error,
                ),
            ) {
                Text(text = confirmLabel, fontWeight = FontWeight.SemiBold)
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                shape = RoundedCornerShape(12.dp),
            ) {
                Text(text = dismissLabel)
            }
        },
    )
}

// ═══════════════════════════════════════════════════════════════════════════════
// 10. StreakFireBadge — small circular badge showing streak count with fire
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
fun StreakFireBadge(
    streakDays: Int,
    modifier: Modifier = Modifier,
    showFireEmoji: Boolean = true,
) {
    val isAtRisk = streakDays <= 1
    val fireColor = if (isAtRisk) StreakFire.copy(alpha = 0.4f) else StreakFire
    val bgColor = if (isAtRisk) fireColor.copy(alpha = 0.1f) else fireColor.copy(alpha = 0.15f)

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(100.dp),
        color = bgColor,
        contentColor = fireColor,
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            if (showFireEmoji) {
                Text("🔥", style = StudyMasterTypography.labelMedium)
            }
            Text(
                text = streakDays.toBengaliDigits(),
                style = StudyMasterTypography.labelMedium.copy(
                    fontFamily = EnglishFontFamily,
                    fontWeight = FontWeight.Bold,
                ),
            )
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// 11. XPGainPopup — floating "+10 XP" animation that fades out
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
fun XPGainPopup(
    xpAmount: Int,
    visible: Boolean,
    modifier: Modifier = Modifier,
    onFinished: () -> Unit = {},
) {
    val motion = LocalMotion.current

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(motion.fadeIn),
        exit = fadeOut(tween(600)),
        modifier = modifier,
    ) {
        LaunchedEffect(Unit) {
            delay(1200)
            onFinished()
        }

        Text(
            text = "+$xpAmount XP",
            style = StudyMasterTypography.titleMedium.copy(
                fontFamily = EnglishFontFamily,
                fontWeight = FontWeight.Bold,
                color = XpGain,
            ),
        )
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// 12. CircularProgress — reusable circular progress indicator
// Used for timer, daily goal, etc.
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
fun CircularProgress(
    progress: Float,
    modifier: Modifier = Modifier,
    strokeWidth: Dp = 6.dp,
    trackColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    progressColor: Color = MaterialTheme.colorScheme.primary,
    centerContent: @Composable () -> Unit = {},
) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress.coerceIn(0f, 1f),
        animationSpec = LocalMotion.current.progressFill,
        label = "circularProgress",
    )

    Box(
        modifier = modifier.aspectRatio(1f),
        contentAlignment = Alignment.Center,
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val diameter = size.minDimension
            val radius = diameter / 2f
            val stroke = strokeWidth.toPx()

            // Track
            drawArc(
                color = trackColor,
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = stroke, cap = StrokeCap.Round),
                size = Size(diameter, diameter),
                topLeft = Offset(0f, 0f),
            )

            // Progress
            drawArc(
                color = progressColor,
                startAngle = -90f,
                sweepAngle = 360f * animatedProgress,
                useCenter = false,
                style = Stroke(width = stroke, cap = StrokeCap.Round),
                size = Size(diameter, diameter),
                topLeft = Offset(0f, 0f),
            )
        }
        centerContent()
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// 13. TimerStateIndicator — shows current Pomodoro session state
// ═══════════════════════════════════════════════════════════════════════════════

enum class TimerState {
    WORK, SHORT_BREAK, LONG_BREAK, IDLE
}

@Composable
fun TimerStateIndicator(
    state: TimerState,
    modifier: Modifier = Modifier,
) {
    val (color, bengaliLabel) = when (state) {
        TimerState.WORK        -> TimerWork to "কাজ"
        TimerState.SHORT_BREAK -> com.porashona.studymaster.ui.compose.theme.TimerShortBreak to "ছোট বিরতি"
        TimerState.LONG_BREAK  -> com.porashona.studymaster.ui.compose.theme.TimerLongBreak to "দীর্ঘ বিরতি"
        TimerState.IDLE        -> MaterialTheme.colorScheme.onSurfaceVariant to "প্রস্তুত"
    }

    val animatedColor by animateColorAsState(
        targetValue = color,
        animationSpec = LocalMotion.current.colorTransition,
        label = "timerStateColor",
    )

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(100.dp),
        color = animatedColor.copy(alpha = 0.15f),
        contentColor = animatedColor,
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(animatedColor, CircleShape),
            )
            Text(
                text = bengaliLabel,
                style = StudyMasterTypography.labelMedium.copy(
                    fontWeight = FontWeight.Medium,
                ),
            )
        }
    }
}