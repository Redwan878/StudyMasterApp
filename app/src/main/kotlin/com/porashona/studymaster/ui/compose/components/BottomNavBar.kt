package com.porashona.studymaster.ui.compose.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.porashona.studymaster.ui.compose.theme.Error
import com.porashona.studymaster.ui.compose.theme.EnglishFontFamily
import com.porashona.studymaster.ui.compose.theme.SpecialTextStyles
import com.porashona.studymaster.ui.compose.theme.StudyMasterTypography
import com.porashona.studymaster.ui.compose.theme.GlassBorderDark
import com.porashona.studymaster.ui.compose.theme.GlassBorderLight
import com.porashona.studymaster.ui.compose.theme.GlassDarkAlpha80
import com.porashona.studymaster.ui.compose.theme.GlassLightAlpha90
import com.porashona.studymaster.ui.compose.theme.LocalGlassShapes
import com.porashona.studymaster.ui.compose.theme.LocalMotion
import com.porashona.studymaster.ui.compose.theme.Primary
import com.porashona.studymaster.ui.compose.theme.isDark

// ═══════════════════════════════════════════════════════════════════════════════
// StudyMasterBottomNavBar
//
// 5 destinations: Home, Timer, Routine, Tools (menu), Profile
// - Glassmorphic translucent background
// - Animated pill indicator with spring physics
// - Optional badge counts per destination
// - Active state with accent colour glow
// - Full Bengali label support
// ═══════════════════════════════════════════════════════════════════════════════

// ─── Nav destination definition ─────────────────────────────────────────────

enum class BottomNavDestination(
    val route: String,
    val bengaliLabel: String,
    val englishLabel: String,
    val outlinedIcon: ImageVector,
    val filledIcon: ImageVector,
) {
    HOME(
        route = "home",
        bengaliLabel = "হোম",
        englishLabel = "Home",
        outlinedIcon = Icons.Outlined.Home,
        filledIcon = Icons.Filled.Home,
    ),
    TIMER(
        route = "timer",
        bengaliLabel = "টাইমার",
        englishLabel = "Timer",
        outlinedIcon = Icons.Outlined.Timer,
        filledIcon = Icons.Filled.Timer,
    ),
    ROUTINE(
        route = "routine",
        bengaliLabel = "রুটিন",
        englishLabel = "Routine",
        outlinedIcon = Icons.Outlined.Schedule,
        filledIcon = Icons.Filled.Schedule,
    ),
    TOOLS(
        route = "tools",
        bengaliLabel = "টুলস",
        englishLabel = "Tools",
        outlinedIcon = Icons.Outlined.Menu,
        filledIcon = Icons.Filled.Menu,
    ),
    PROFILE(
        route = "profile",
        bengaliLabel = "প্রোফাইল",
        englishLabel = "Profile",
        outlinedIcon = Icons.Outlined.Person,
        filledIcon = Icons.Filled.Person,
    );

    companion object {
        fun fromRoute(route: String?): BottomNavDestination =
            entries.firstOrNull { it.route == route } ?: HOME
    }
}

// ─── Badge data holder ──────────────────────────────────────────────────────

@Immutable
data class NavBadge(
    val count: Int = 0,
    val showDot: Boolean = false,
)

// ─── Main composable ────────────────────────────────────────────────────────

@Composable
fun StudyMasterBottomNavBar(
    currentRoute: String?,
    onDestinationSelected: (BottomNavDestination) -> Unit,
    modifier: Modifier = Modifier,
    useBengali: Boolean = true,
    badges: Map<BottomNavDestination, NavBadge> = emptyMap(),
    accentColor: Color = Primary,
) {
    val isDark = MaterialTheme.isDark
    val glassShapes = LocalGlassShapes.current
    val motion = LocalMotion.current

    // ── Glass background colours ────────────────────────────────────────────
    val bgColor = if (isDark) GlassDarkAlpha80 else GlassLightAlpha90
    val borderColor = if (isDark) GlassBorderDark else GlassBorderLight
    val shape = RoundedCornerShape(
        topStart = glassShapes.bottomNavRadius,
        topEnd = glassShapes.bottomNavRadius,
        bottomStart = 0.dp,
        bottomEnd = 0.dp,
    )

    // ── Active index & indicator position ───────────────────────────────────
    val currentDestination = BottomNavDestination.fromRoute(currentRoute)
    val activeIndex = BottomNavDestination.entries.indexOf(currentDestination)
    val totalItems = BottomNavDestination.entries.size

    // Calculate indicator offset: each item is 1/totalWidth of the bar
    // The indicator width is a fraction of the bar width
    val indicatorWidthFraction = 0.16f  // indicator covers 16% of bar width
    val indicatorWidthDp = 56.dp

    // X offset for the indicator center = (activeIndex + 0.5) / totalItems * barWidth
    // We'll use layout-based calculation via an internal layout, but for simplicity
    // we compute the target offset as a fraction.
    val targetFraction = if (activeIndex >= 0 && activeIndex < totalItems) {
        (activeIndex + 0.5f) / totalItems
    } else {
        0.5f
    }

    // Animated glow behind active icon
    val glowAlpha by animateFloatAsState(
        targetValue = 0.15f,
        animationSpec = motion.fadeIn,
        label = "navGlow",
    )

    // Animated accent for active state
    val animatedAccent by animateColorAsState(
        targetValue = accentColor,
        animationSpec = motion.colorTransition,
        label = "navAccent",
    )

    // ── Glow brush behind the active indicator ──────────────────────────────
    val glowBrush = Brush.radialGradient(
        colors = listOf(
            animatedAccent.copy(alpha = glowAlpha),
            Color.Transparent,
        ),
        radius = 120f,
    )

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .border(
                width = 1.dp,
                color = borderColor.copy(alpha = 0.3f),
                shape = shape,
            ),
        shape = shape,
        color = bgColor,
        shadowElevation = if (isDark) 16.dp else 8.dp,
        tonalElevation = 4.dp,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp),
            contentAlignment = Alignment.Center,
        ) {
            // Subtle glow behind active item
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                BottomNavDestination.entries.forEachIndexed { index, destination ->
                    val isActive = index == activeIndex
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .height(72.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        if (isActive) {
                            // Glow circle behind active icon
                            Box(
                                modifier = Modifier
                                    .size(56.dp)
                                    .drawBehind {
                                        drawCircle(
                                            brush = glowBrush,
                                            radius = size.width / 2f,
                                        )
                                    },
                            )
                        }
                    }
                }
            }

            // Navigation items row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                BottomNavDestination.entries.forEachIndexed { index, destination ->
                    val isActive = index == activeIndex
                    val badge = badges[destination]

                    NavItem(
                        destination = destination,
                        isActive = isActive,
                        useBengali = useBengali,
                        badge = badge,
                        activeColor = animatedAccent,
                        onClick = { onDestinationSelected(destination) },
                        indicatorProgress = animateFloatAsState(
                            targetValue = if (isActive) 1f else 0f,
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                stiffness = Spring.StiffnessMedium,
                            ),
                            label = "navItem$index",
                        ).value,
                    )
                }
            }
        }
    }
}

// ─── Individual nav item ────────────────────────────────────────────────────

@Composable
private fun NavItem(
    destination: BottomNavDestination,
    isActive: Boolean,
    useBengali: Boolean,
    badge: NavBadge?,
    activeColor: Color,
    onClick: () -> Unit,
    indicatorProgress: Float,
) {
    val inactiveColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
    val labelColor by animateColorAsState(
        targetValue = if (isActive) activeColor else inactiveColor,
        animationSpec = LocalMotion.current.colorTransition,
        label = "navLabel${destination.route}",
    )
    val iconColor by animateColorAsState(
        targetValue = if (isActive) activeColor else inactiveColor,
        animationSpec = LocalMotion.current.colorTransition,
        label = "navIcon${destination.route}",
    )

    val label = if (useBengali) destination.bengaliLabel else destination.englishLabel

    // Scale bounce on active
    val scale by animateFloatAsState(
        targetValue = if (isActive) 1f else 0.88f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = 400f,
        ),
        label = "navScale${destination.route}",
    )

    // Vertical offset — active items rise 4 dp
    val yOffset by animateDpAsState(
        targetValue = if (isActive) (-2).dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioNoBouncy,
            stiffness = Spring.StiffnessMedium,
        ),
        label = "navY${destination.route}",
    )

    Column(
        modifier = Modifier
            .offset { IntOffset(0, yOffset.roundToPx()) }
            .clip(RoundedCornerShape(16.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick,
            )
            .padding(horizontal = 8.dp, vertical = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        // Icon with optional badge
        BadgedBox(
            badge = {
                when {
                    badge != null && badge.count > 0 -> {
                        Badge(
                            containerColor = Error,
                            contentColor = Color.White,
                        ) {
                            Text(
                                text = if (badge.count > 99) "৯৯+" else badge.count.toString(),
                                style = StudyMasterTypography.labelSmall.copy(
                                    fontFamily = EnglishFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp,
                                ),
                            )
                        }
                    }
                    badge != null && badge.showDot -> {
                        Badge(
                            containerColor = Error,
                            modifier = Modifier.size(8.dp),
                        ) {}
                    }
                }
            },
        ) {
            Icon(
                imageVector = if (isActive) destination.filledIcon else destination.outlinedIcon,
                contentDescription = destination.englishLabel,
                tint = iconColor,
                modifier = Modifier
                    .size(24.dp)
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                    },
            )
        }

        // Label
        Text(
            text = label,
            style = SpecialTextStyles.bottomNavLabel.copy(
                color = labelColor,
                fontWeight = if (isActive) FontWeight.SemiBold else FontWeight.Normal,
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
        )

        // Active indicator dot
        if (indicatorProgress > 0.01f) {
            Spacer(Modifier.height(1.dp))
            Box(
                modifier = Modifier
                    .width(4.dp * indicatorProgress)
                    .height(2.dp)
                    .background(
                        color = activeColor,
                        shape = RoundedCornerShape(100.dp),
                    ),
            )
        }
    }
}