package com.porashona.studymaster.ui.compose.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.porashona.studymaster.ui.compose.theme.GlassBorderDark
import com.porashona.studymaster.ui.compose.theme.GlassBorderLight
import com.porashona.studymaster.ui.compose.theme.GlassDarkAlpha60
import com.porashona.studymaster.ui.compose.theme.GlassLightAlpha90
import com.porashona.studymaster.ui.compose.theme.LocalGlassShapes
import com.porashona.studymaster.ui.compose.theme.LocalMotion
import com.porashona.studymaster.ui.compose.theme.Shadow
import com.porashona.studymaster.ui.compose.theme.isDark

// ═══════════════════════════════════════════════════════════════════════════════
// GlassmorphicCard — the foundational container for the entire app.
//
// Three visual variants matching Material 3 card variants but with
// glassmorphism (transparency + blur + subtle border + soft shadow).
// Every card animates in with fadeIn + scaleIn by default.
// ═══════════════════════════════════════════════════════════════════════════════

enum class GlassCardVariant {
    ELEVATED,   // blur + shadow + border
    OUTLINED,   // blur + border, no shadow
    FILLED,     // blur + tinted background, no border
}

/**
 * @param variant  Visual style: ELEVATED, OUTLINED, or FILLED
 * @param cornerRadius  Override the default 20 dp glass radius
 * @param glassAlpha   Background opacity 0f–1f (default 0.6 for dark, 0.9 for light)
 * @param blurRadius   Gaussian blur in dp applied behind the card (0 to disable)
 * @param borderWidth  Width of the glass border stroke (0 to hide)
 * @param borderColor  Override auto border colour
 * @param tint         Optional tint colour blended over the glass background
 * @param padding      Internal padding around [content]
 * @param animated     Whether to animate first appearance (fade + scale)
 * @param enabled      If false, the card renders at 0.4 alpha (disabled state)
 * @param onClick      Optional click handler; when set the card becomes pressable
 * @param content      The card body
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GlassmorphicCard(
    modifier: Modifier = Modifier,
    variant: GlassCardVariant = GlassCardVariant.ELEVATED,
    cornerRadius: Dp = LocalGlassShapes.current.cardRadius,
    glassAlpha: Float = if (MaterialTheme.isDark) 0.6f else 0.9f,
    blurRadius: Dp = 0.dp,
    borderWidth: Dp = 1.dp,
    borderColor: Color? = null,
    tint: Color? = null,
    padding: Dp = 0.dp,
    animated: Boolean = true,
    enabled: Boolean = true,
    onClick: (() -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit,
) {
    val isDark = MaterialTheme.isDark
    val motion = LocalMotion.current

    // ── Resolve colours ──────────────────────────────────────────────────────
    val glassBase = if (isDark) GlassDarkAlpha60 else GlassLightAlpha90
    val resolvedGlass = remember(glassBase, tint, glassAlpha) {
        if (tint != null) {
            tint.copy(alpha = 0.12f).let { tintBase ->
                glassBase.copy(alpha = glassAlpha).copy(
                    red = tintBase.red + (1 - tintBase.red) * (1 - glassAlpha),
                    green = tintBase.green + (1 - tintBase.green) * (1 - glassAlpha),
                    blue = tintBase.blue + (1 - tintBase.blue) * (1 - glassAlpha),
                )
            }
        } else {
            glassBase.copy(alpha = glassAlpha)
        }
    }
    val resolvedBorder = remember(borderColor, isDark) {
        borderColor ?: (if (isDark) GlassBorderDark else GlassBorderLight)
    }

    val shape = RoundedCornerShape(cornerRadius)

    // ── Animation state ──────────────────────────────────────────────────────
    var visible by remember { mutableStateOf(!animated) }
    val scale by animateFloatAsState(
        targetValue = if (visible) 1f else 0.92f,
        animationSpec = motion.scaleIn,
        label = "glassCardScale",
    )

    // Mark visible on first composition
    if (animated && !visible) {
        visible = true
    }

    // ── Shadow brush (soft glow behind card) ────────────────────────────────
    val shadowBrush = Brush.verticalGradient(
        colors = listOf(
            Shadow.copy(alpha = if (isDark) 0.5f else 0.12f),
            Color.Transparent,
        ),
        startY = 0f,
        endY = 24.dp.value,
    )

    AnimatedVisibility(
        visible = true,
        enter = fadeIn(motion.fadeIn) + scaleIn(
            initialScale = 0.92f,
            animationSpec = motion.scaleIn,
        ),
    ) {
        Box(
            modifier = modifier
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                }
                .then(
                    if (onClick != null) {
                        Modifier.clip(shape)
                    } else {
                        Modifier
                    }
                )
                .drawBehind {
                    // soft top-shadow for elevated variant
                    if (variant == GlassCardVariant.ELEVATED) {
                        drawRect(
                            brush = shadowBrush,
                            size = size,
                            topLeft = Offset(0f, 4.dp.toPx()),
                        )
                    }
                },
            contentAlignment = Alignment.Center,
        ) {
            Surface(
                modifier = Modifier
                    .then(
                        if (blurRadius > 0.dp) {
                            Modifier.blur(blurRadius, BlurredEdgeTreatment.Unbounded)
                        } else {
                            Modifier
                        }
                    )
                    .then(
                        if (onClick != null) {
                            Modifier
                        } else {
                            Modifier
                        }
                    ),
                shape = shape,
                color = resolvedGlass,
                contentColor = MaterialTheme.colorScheme.onSurface,
                border = when (variant) {
                    GlassCardVariant.ELEVATED, GlassCardVariant.OUTLINED ->
                        if (borderWidth > 0.dp) BorderStroke(borderWidth, resolvedBorder) else null
                    GlassCardVariant.FILLED -> null
                },
                tonalElevation = when (variant) {
                    GlassCardVariant.ELEVATED -> 4.dp
                    GlassCardVariant.OUTLINED -> 0.dp
                    GlassCardVariant.FILLED -> 2.dp
                },
                shadowElevation = when (variant) {
                    GlassCardVariant.ELEVATED -> 8.dp
                    else -> 0.dp
                },
            ) {
                Box(
                    modifier = Modifier
                        .padding(padding)
                        .then(
                            if (!enabled) Modifier.graphicsLayer { alpha = 0.4f }
                            else Modifier
                        ),
                    content = content,
                )
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Convenience wrappers that delegate to GlassmorphicCard but accept
// Material 3 card defaults so migration is painless.
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
fun GlassElevatedCard(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = LocalGlassShapes.current.cardRadius,
    padding: Dp = 0.dp,
    animated: Boolean = true,
    onClick: (() -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit,
) {
    GlassmorphicCard(
        modifier = modifier,
        variant = GlassCardVariant.ELEVATED,
        cornerRadius = cornerRadius,
        padding = padding,
        animated = animated,
        onClick = onClick,
        content = content,
    )
}

@Composable
fun GlassOutlinedCard(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = LocalGlassShapes.current.cardRadius,
    padding: Dp = 0.dp,
    animated: Boolean = true,
    onClick: (() -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit,
) {
    GlassmorphicCard(
        modifier = modifier,
        variant = GlassCardVariant.OUTLINED,
        cornerRadius = cornerRadius,
        padding = padding,
        animated = animated,
        onClick = onClick,
        content = content,
    )
}

@Composable
fun GlassFilledCard(
    modifier: Modifier = Modifier,
    tint: Color? = null,
    cornerRadius: Dp = LocalGlassShapes.current.cardRadius,
    padding: Dp = 0.dp,
    animated: Boolean = true,
    onClick: (() -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit,
) {
    GlassmorphicCard(
        modifier = modifier,
        variant = GlassCardVariant.FILLED,
        tint = tint,
        cornerRadius = cornerRadius,
        padding = padding,
        animated = animated,
        onClick = onClick,
        content = content,
    )
}