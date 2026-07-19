package com.porashona.studymaster.ui.compose.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.porashona.studymaster.ui.compose.theme.ActivityTagColors
import com.porashona.studymaster.ui.compose.theme.HexColors
import com.porashona.studymaster.ui.compose.theme.LocalGlassShapes
import com.porashona.studymaster.ui.compose.theme.LocalMotion
import com.porashona.studymaster.ui.compose.theme.StudyMasterTypography
import com.porashona.studymaster.ui.compose.theme.isDark
import com.porashona.studymaster.ui.compose.theme.toComposeColor
import com.porashona.studymaster.ui.compose.theme.toHexString

// ═══════════════════════════════════════════════════════════════════════════════
// ActivityType — the canonical enum used across calendar, routine, tasks,
// analytics, and every other screen that colour-codes activities.
//
// Each type has a vivid accent colour and a matching dim (desaturated)
// background for dark-mode containers.
// ═══════════════════════════════════════════════════════════════════════════════

enum class ActivityType(val dbValue: String) {
    STUDY("STUDY"),
    REVISION("REVISION"),
    EXAM("EXAM"),
    ASSIGNMENT("ASSIGNMENT"),
    MODEL_TEST("MODEL_TEST"),
    ;

    companion object {
        fun fromDb(value: String?): ActivityType =
            entries.firstOrNull { it.dbValue.equals(value, ignoreCase = true) }
                ?: STUDY
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Colour mapping — single source of truth for activity-type colours.
// Every screen MUST use these helpers so colours stay in sync.
// ═══════════════════════════════════════════════════════════════════════════════

@Immutable
data class ActivityColorSet(
    val accent: Color,
    val dim: Color,
    val hexAccent: String,
    val hexDim: String,
)

object ActivityColors {
    private val map = mapOf(
        ActivityType.STUDY to ActivityColorSet(
            accent = ActivityTagColors.StudyColor,
            dim = ActivityTagColors.StudyDim,
            hexAccent = HexColors.TAG_STUDY,
            hexDim = HexColors.TAG_STUDY_DIM,
        ),
        ActivityType.REVISION to ActivityColorSet(
            accent = ActivityTagColors.RevisionColor,
            dim = ActivityTagColors.RevisionDim,
            hexAccent = HexColors.TAG_REVISION,
            hexDim = HexColors.TAG_REVISION_DIM,
        ),
        ActivityType.EXAM to ActivityColorSet(
            accent = ActivityTagColors.ExamColor,
            dim = ActivityTagColors.ExamDim,
            hexAccent = HexColors.TAG_EXAM,
            hexDim = HexColors.TAG_EXAM_DIM,
        ),
        ActivityType.ASSIGNMENT to ActivityColorSet(
            accent = ActivityTagColors.AssignmentColor,
            dim = ActivityTagColors.AssignmentDim,
            hexAccent = HexColors.TAG_ASSIGNMENT,
            hexDim = HexColors.TAG_ASSIGNMENT_DIM,
        ),
        ActivityType.MODEL_TEST to ActivityColorSet(
            accent = ActivityTagColors.ModelTestColor,
            dim = ActivityTagColors.ModelTestDim,
            hexAccent = HexColors.TAG_MODEL_TEST,
            hexDim = HexColors.TAG_MODEL_TEST_DIM,
        ),
    )

    /** Get the full colour set for an activity type */
    fun forType(type: ActivityType): ActivityColorSet = map[type]!!

    /** Quick access to the vivid accent colour */
    fun accent(type: ActivityType): Color = map[type]!!.accent

    /** Quick access to the dimmed background colour */
    fun dim(type: ActivityType): Color = map[type]!!.dim

    /** Get colour set from a stored DB string (e.g. "EXAM") */
    fun forDbValue(value: String?): ActivityColorSet =
        forType(ActivityType.fromDb(value))

    /** Get accent colour from a stored DB string */
    fun accentForDbValue(value: String?): Color = accent(ActivityType.fromDb(value))

    /** Get dim colour from a stored DB string */
    fun dimForDbValue(value: String?): Color = dim(ActivityType.fromDb(value))
}

// ═══════════════════════════════════════════════════════════════════════════════
// Bengali labels for each activity type
// ═══════════════════════════════════════════════════════════════════════════════

val ActivityType.bengaliLabel: String
    get() = when (this) {
        ActivityType.STUDY       -> "পড়া"
        ActivityType.REVISION    -> "রিভিশন"
        ActivityType.EXAM        -> "পরীক্ষা"
        ActivityType.ASSIGNMENT  -> "অ্যাসাইনমেন্ট"
        ActivityType.MODEL_TEST  -> "মডেল টেস্ট"
    }

val ActivityType.englishLabel: String
    get() = when (this) {
        ActivityType.STUDY       -> "Study"
        ActivityType.REVISION    -> "Revision"
        ActivityType.EXAM        -> "Exam"
        ActivityType.ASSIGNMENT  -> "Assignment"
        ActivityType.MODEL_TEST  -> "Model Test"
    }

// ═══════════════════════════════════════════════════════════════════════════════
// ActivityTagChip — the visual chip rendered everywhere
//
// - Colour-coded pill with the activity accent as text + left dot
// - Smooth animated colour transition when type changes
// - Supports both Bengali and English labels
// - Optional selected state with filled background
// - Filter-bar usage via [ActivityTypeFilterRow]
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
fun ActivityTagChip(
    type: ActivityType,
    modifier: Modifier = Modifier,
    useBengali: Boolean = true,
    selected: Boolean = false,
    compact: Boolean = false,
    onClick: (() -> Unit)? = null,
) {
    val colorSet = ActivityColors.forType(type)
    val motion = LocalMotion.current
    val shapes = LocalGlassShapes.current

    val isDark = MaterialTheme.isDark

    // Animated colour transitions
    val animatedAccent by animateColorAsState(
        targetValue = colorSet.accent,
        animationSpec = motion.colorTransition,
        label = "tagAccent",
    )
    val animatedDim by animateColorAsState(
        targetValue = colorSet.dim,
        animationSpec = motion.colorTransition,
        label = "tagDim",
    )

    val label = if (useBengali) type.bengaliLabel else type.englishLabel

    val horizontalPadding = if (compact) 6.dp else 10.dp
    val verticalPadding = if (compact) 2.dp else 4.dp
    val textStyles = if (compact) {
        StudyMasterTypography.labelSmall
    } else {
        StudyMasterTypography.labelMedium
    }

    val shape = RoundedCornerShape(shapes.chipRadius)

    val backgroundColor = if (selected) {
        animatedAccent.copy(alpha = if (isDark) 0.25f else 0.15f)
    } else {
        animatedDim.copy(alpha = if (isDark) 0.8f else 0.6f)
    }

    val borderColor = if (selected) {
        animatedAccent.copy(alpha = 0.6f)
    } else {
        Color.Transparent
    }

    Surface(
        modifier = modifier
            .then(
                if (onClick != null) {
                    Modifier.clip(shape).clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = onClick,
                    )
                } else {
                    Modifier
                }
            )
            .border(
                width = if (selected) 1.dp else 0.dp,
                color = borderColor,
                shape = shape,
            ),
        shape = shape,
        color = backgroundColor,
        contentColor = animatedAccent,
    ) {
        val rowArrangement = if (compact) Arrangement.spacedBy(3.dp) else Arrangement.spacedBy(5.dp)
        androidx.compose.foundation.layout.Row(
            modifier = Modifier.padding(horizontal = horizontalPadding, vertical = verticalPadding),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = rowArrangement,
        ) {
            // Colour dot
            Box(
                modifier = Modifier
                    .size(if (compact) 5.dp else 7.dp)
                    .background(animatedAccent, CircleShape),
            )
            // Label
            Text(
                text = label,
                style = textStyles.copy(
                    color = animatedAccent,
                    fontWeight = FontWeight.Medium,
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
            )
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// ActivityTypeFilterRow — horizontal scrolling filter bar
// Used on calendar, routine, and analytics screens for type filtering.
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
fun ActivityTypeFilterRow(
    selectedType: ActivityType?,
    onTypeSelected: (ActivityType?) -> Unit,
    modifier: Modifier = Modifier,
    useBengali: Boolean = true,
    showAll: Boolean = true,
    allLabel: String = "সব",
) {
    val items = if (showAll) {
        listOf<ActivityType?>(null) + ActivityType.entries
    } else {
        ActivityType.entries.map { it as ActivityType? }
    }

    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        contentPadding = PaddingValues(horizontal = 16.dp),
    ) {
        items(items, key = { it?.dbValue ?: "ALL" }) { type ->
            if (type == null) {
                // "All" chip
                Surface(
                    modifier = Modifier
                        .clip(RoundedCornerShape(LocalGlassShapes.current.chipRadius))
                        .clickable { onTypeSelected(null) },
                    shape = RoundedCornerShape(LocalGlassShapes.current.chipRadius),
                    color = if (selectedType == null) {
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                    } else {
                        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
                    },
                    contentColor = if (selectedType == null) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    },
                ) {
                    androidx.compose.foundation.layout.Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = allLabel,
                            style = StudyMasterTypography.labelMedium,
                            fontWeight = FontWeight.Medium,
                            maxLines = 1,
                        )
                    }
                }
            } else {
                ActivityTagChip(
                    type = type,
                    useBengali = useBengali,
                    selected = selectedType == type,
                    onClick = { onTypeSelected(type) },
                )
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Small inline colour dot — used in lists, calendar cells, etc.
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
fun ActivityColorDot(
    type: ActivityType,
    modifier: Modifier = Modifier,
    size: Dp = 8.dp,
) {
    val accent by animateColorAsState(
        targetValue = ActivityColors.accent(type),
        animationSpec = LocalMotion.current.colorTransition,
        label = "activityDot",
    )
    Box(
        modifier = modifier
            .size(size)
            .background(accent, CircleShape),
    )
}