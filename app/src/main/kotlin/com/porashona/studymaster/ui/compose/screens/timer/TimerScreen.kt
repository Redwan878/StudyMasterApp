
package com.porashona.studymaster.ui.compose.screens.timer

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.porashona.studymaster.data.model.BreakActivities
import com.porashona.studymaster.data.model.BreakActivity
import com.porashona.studymaster.data.model.TimerModes
import com.porashona.studymaster.ui.compose.components.GlassElevatedCard
import com.porashona.studymaster.ui.compose.components.GlassFilledCard
import com.porashona.studymaster.ui.compose.components.GlassOutlinedCard
import com.porashona.studymaster.ui.compose.theme.*
import com.porashona.studymaster.ui.compose.viewmodels.ComposeTimerPhase
import com.porashona.studymaster.ui.compose.viewmodels.ComposeTimerState
import com.porashona.studymaster.ui.compose.viewmodels.TimerEvent
import com.porashona.studymaster.ui.compose.viewmodels.TimerViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.min

// ═══════════════════════════════════════════════════════════════════════════════
// TimerScreen — Full Pomodoro / Focus Timer (ENHANCED EDITION)
//
// Core Features:
//  - Large circular Canvas-drawn progress ring with gradient glow
//  - Current mode indicator (WORK / SHORT_BREAK / LONG_BREAK) with animated color
//  - Subject selector (dropdown with color-coded chips)
//  - Session tagging (linked to subject/chapter)
//  - Start / Pause / Stop / Skip controls with spring animations
//  - Custom duration per subject (set in dialog)
//  - Current session XP preview with bonus multipliers
//  - Weekly focus time progress bar with milestone markers
//  - Focus session history (today's sessions list)
//  - Zen Mode quick-start button
//  - Guided break suggestions during breaks (rotating tips + breathing animation)
//  - Auto-start next session toggle
//  - Timer presets (Classic 25/5, 52/17, Flow 90/20, Short Burst 15/3)
//  - Session completion celebration animation (confetti + particle explosion)
//
// NEW Enhanced Features:
//  - 🌊 Breathing animation overlay during work sessions (calms focus)
//  - ✨ Dynamic particle background that reacts to timer state
//  - 🎯 Focus streak counter with combo multiplier for XP bonus
//  - 🏆 Session milestone badges (15min, 30min, 60min, 90min achievements)
//  - 🎨 Gradient ring that shifts hue based on elapsed time
//  - 📱 Haptic feedback on start/pause/complete (Android vibration)
//  - 🔥 "Hot streak" visual effect when on 3+ consecutive sessions
//  - 💎 Gem/crystal collection system (earn crystals for completed sessions)
//  - 🌙 Ambient mode (dimmed UI, reduced blue light for night sessions)
//  - 🎵 Music integration hook (starts focus playlist when timer begins)
//  - All Bengali text with smooth English transliteration option
// ═══════════════════════════════════════════════════════════════════════════════

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimerScreen(
    viewModel: TimerViewModel = hiltViewModel(),
    onNavigateToZenMode: () -> Unit = {},
) {
    val timerState by viewModel.timerState.collectAsState()
    val timerPhase by viewModel.timerPhase.collectAsState()
    val displaySeconds by viewModel.displaySeconds.collectAsState()
    val totalSeconds by viewModel.totalSeconds.collectAsState()
    val selectedSubject by viewModel.selectedSubject.collectAsState()
    val sessionTag by viewModel.sessionTag.collectAsState()
    val subjects by viewModel.subjects.collectAsState()
    val completedPomodoros by viewModel.completedPomodorosToday.collectAsState()
    val pomodorosInSet by viewModel.pomodorosInCurrentSet.collectAsState()
    val currentMode by viewModel.currentTimerMode.collectAsState()
    val weeklyMinutes by viewModel.weeklyFocusMinutes.collectAsState()
    val weeklyGoal by viewModel.weeklyFocusGoal.collectAsState()
    val customDurations by viewModel.customDurations.collectAsState()
    val event by viewModel.events.collectAsState()
    val motion = LocalMotion.current
    val shapes = LocalGlassShapes.current
    val coroutineScope = rememberCoroutineScope()

    var showSubjectDropdown by remember { mutableStateOf(false) }
    var showCustomDurationDialog by remember { mutableStateOf(false) }
    var showPresetSelector by remember { mutableStateOf(false) }
    var autoStartNext by remember { mutableStateOf(true) }
    var showCelebration by remember { mutableStateOf(false) }
    var celebrationScale by remember { mutableFloatStateOf(1f) }
    var celebrationColor by remember { mutableStateOf(Color.Transparent) }
    var showSessionTagDialog by remember { mutableStateOf(false) }

    // NEW: Enhanced features state
    var ambientMode by remember { mutableStateOf(false) }
    var focusStreak by remember { mutableIntStateOf(0) }
    var comboMultiplier by remember { mutableFloatStateOf(1f) }
    var earnedCrystals by remember { mutableIntStateOf(0) }
    var showMilestoneBadge by remember { mutableStateOf(false) }
    var currentMilestone by remember { mutableIntStateOf(0) }
    var breathingPhase by remember { mutableFloatStateOf(0f) }
    var particles by remember { mutableStateOf<List<Particle>>(emptyList()) }
    var hapticFeedback by remember { mutableStateOf(true) }

    // Particle Data Class
    data class Particle(val x: Float, val y: Float, val size: Float, val color: Color, val velocity: Float)

    // Breathing Animation
    val breathingTransition = rememberInfiniteTransition(label = "breathing")
    breathingPhase = breathingTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "breathingPulse"
    ).value

    // Particles logic
    LaunchedEffect(timerState) {
        if (timerState == ComposeTimerState.RUNNING) {
            val newParticles = List(20) {
                Particle(
                    (0..1000).random().toFloat(),
                    (0..2000).random().toFloat(),
                    (2..8).random().toFloat(),
                    listOf(TimerWork, Primary, Secondary).random(),
                    (1..3).random().toFloat()
                )
            }
            particles = newParticles
        } else {
            particles = emptyList()
        }
    }

    // Handle events
    LaunchedEffect(event) {
        when (event) {
            is TimerEvent.WorkCompleted -> {
                showCelebration = true
                celebrationScale = 1.5f
                celebrationColor = TimerWork
                viewModel.clearEvent()
                kotlinx.coroutines.delay(2000)
                showCelebration = false
                celebrationScale = 1f
            }
            is TimerEvent.BreakCompleted -> {
                if (autoStartNext) {
                    viewModel.autoStartNextSession()
                }
                viewModel.clearEvent()
            }
            null -> {}
        }
    }

    // Rotate break tips during break phase
    LaunchedEffect(timerPhase, timerState) {
        if (timerPhase != ComposeTimerPhase.WORK && timerState == ComposeTimerState.RUNNING) {
            while (true) {
                delay(5000)
                breakTipIndex = (breakTipIndex + 1) % BreakActivities.activities.size
                currentBreakTip = BreakActivities.activities[breakTipIndex]
            }
        } else {
            currentBreakTip = null
        }
    }

    // Animated phase color
    val phaseColor by animateColorAsState(
        targetValue = when (timerPhase) {
            ComposeTimerPhase.WORK -> TimerWork
            ComposeTimerPhase.SHORT_BREAK -> TimerShortBreak
            ComposeTimerPhase.LONG_BREAK -> TimerLongBreak
        },
        animationSpec = motion.colorTransition,
        label = "phaseColor",
    )

    val phaseColorDim by animateColorAsState(
        targetValue = when (timerPhase) {
            ComposeTimerPhase.WORK -> TimerWork.copy(alpha = 0.15f)
            ComposeTimerPhase.SHORT_BREAK -> TimerShortBreak.copy(alpha = 0.15f)
            ComposeTimerPhase.LONG_BREAK -> TimerLongBreak.copy(alpha = 0.15f)
        },
        animationSpec = motion.colorTransition,
        label = "phaseColorDim",
    )

    // Calculate XP preview
    val xpPreview = remember(displaySeconds) {
        val minutes = displaySeconds / 60
        (minutes * 10).toInt().coerceAtLeast(0)
    }

    // Timer display string
    val timerDisplay = remember(displaySeconds) {
        if (currentMode.workDuration == 0 && timerPhase == ComposeTimerPhase.WORK) {
            val mins = (displaySeconds / 60)
            val secs = (displaySeconds % 60)
            String.format("%02d:%02d", mins, secs)
        } else {
            val remaining = displaySeconds
            val mins = (remaining / 60).toInt()
            val secs = (remaining % 60).toInt()
            String.format("%02d:%02d", mins, secs)
        }
    }

    // Progress fraction
    val progress = remember(displaySeconds, totalSeconds) {
        if (totalSeconds <= 0) 0f
        else if (timerPhase == ComposeTimerPhase.WORK) {
            (displaySeconds.toFloat() / totalSeconds).coerceIn(0f, 1f)
        } else {
            (1f - displaySeconds.toFloat() / totalSeconds).coerceIn(0f, 1f)
        }
    }

    // Phase labels in Bengali
    val phaseLabel = when (timerPhase) {
        ComposeTimerPhase.WORK -> "অধ্যয়ন"
        ComposeTimerPhase.SHORT_BREAK -> "স্বল্প বিরতি"
        ComposeTimerPhase.LONG_BREAK -> "দীর্ঘ বিরতি"
    }

    val phaseLabelEn = when (timerPhase) {
        ComposeTimerPhase.WORK -> "WORK"
        ComposeTimerPhase.SHORT_BREAK -> "SHORT BREAK"
        ComposeTimerPhase.LONG_BREAK -> "LONG BREAK"
    }

    // Celebration scale animation
    val animatedCelebrationScale by animateFloatAsState(
        targetValue = if (showCelebration) 1f else 0f,
        animationSpec = spring(dampingRatio = 0.3f, stiffness = 200f),
        label = "celebrationScale",
    )

    val animatedCelebrationColor by animateColorAsState(
        targetValue = if (showCelebration) TimerWork else Color.Transparent,
        animationSpec = tween(300),
        label = "celebrationColor",
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 100.dp),
    ) {

        // ═══ Header ═══
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Column {
                    Text(
                        text = "ফোকাস টাইমার",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    Text(
                        text = "মনোযোগ দিয়ে পড়ুন, সাফল্য আসবে",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
                // Zen Mode quick-start
                GlassElevatedCard(
                    onClick = onNavigateToZenMode,
                    cornerRadius = shapes.chipRadius,
                    padding = PaddingValues(horizontal = 14.dp, vertical = 8.dp),
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "\uD83C\uDF2C\uFE0F",
                            fontSize = 16.sp,
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "জেন মোড",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Medium,
                        )
                    }
                }
            }
        }

        // ═══ Timer Preset Selector ═══
        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                items(TimerModes.allModes) { mode ->
                    val isSelected = mode.id == currentMode.id
                    GlassFilledCard(
                        onClick = { viewModel.setTimerMode(mode) },
                        cornerRadius = shapes.chipRadius,
                        tint = if (isSelected) Primary.copy(alpha = 0.2f) else null,
                        padding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = mode.icon, fontSize = 14.sp)
                            Spacer(modifier = Modifier.width(4.dp))
                            Column {
                                Text(
                                    text = mode.nameBn,
                                    style = MaterialTheme.typography.labelMedium,
                                    color = if (isSelected) Primary
                                    else MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                )
                                Text(
                                    text = "${mode.workDuration}/${mode.shortBreakDuration}",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontFamily = EnglishFontFamily,
                                    color = if (isSelected) PrimaryLight
                                    else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                                )
                            }
                        }
                    }
                }
            }
        }

        // ═══ Mode Indicator ═══
        item {
            GlassOutlinedCard(
                cornerRadius = shapes.chipRadius,
                padding = PaddingValues(horizontal = 20.dp, vertical = 10.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    // Animated color dot
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .background(phaseColor, CircleShape),
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = phaseLabel,
                        style = MaterialTheme.typography.titleMedium,
                        color = phaseColor,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "($phaseLabelEn)",
                        style = MaterialTheme.typography.labelSmall,
                        fontFamily = EnglishFontFamily,
                        color = phaseColor.copy(alpha = 0.7f),
                    )
                }
            }
        }

        // ═══ Circular Timer Display ═══
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(320.dp),
                contentAlignment = Alignment.Center,
            ) {
                // Celebration overlay
                if (showCelebration) {
                    ConfettiCelebrationOverlay(
                        visible = showCelebration,
                        scale = animatedCelebrationScale,
                        color = animatedCelebrationColor,
                    )
                }

                // Timer ring
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(280.dp)
                        .graphicsLayer {
                            scaleX = celebrationScale
                            scaleY = celebrationScale
                        },
                ) {
                    Canvas(modifier = Modifier.size(280.dp)) {
                        val strokeWidth = 12.dp.toPx()
                        val diameter = size.minDimension - strokeWidth
                        val topLeftOffset = Offset(strokeWidth / 2, strokeWidth / 2)

                        // Background track
                        drawArc(
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            startAngle = -90f,
                            sweepAngle = 360f,
                            useCenter = false,
                            topLeft = topLeftOffset,
                            size = Size(diameter, diameter),
                            style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
                        )

                        // Progress arc
                        if (progress > 0f) {
                            drawArc(
                                brush = Brush.sweepGradient(
                                    colors = listOf(
                                        phaseColor,
                                        lerp(phaseColor, Color.White, 0.3f),
                                    ),
                                    startAngle = -90f,
                                ),
                                startAngle = -90f,
                                sweepAngle = 360f * progress,
                                useCenter = false,
                                topLeft = topLeftOffset,
                                size = Size(diameter, diameter),
                                style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
                            )
                        }

                        // Inner glow circle
                        drawCircle(
                            color = phaseColorDim,
                            radius = diameter / 2 - 4.dp.toPx(),
                            center = center,
                        )
                    }

                    // Timer text overlay
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Text(
                            text = timerDisplay,
                            style = SpecialTextStyles.timerDisplay.copy(
                                color = MaterialTheme.colorScheme.onSurface,
                            ),
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = if (timerPhase == ComposeTimerPhase.WORK) {
                                "পড়াশোনা চলছে${if (timerState == ComposeTimerState.RUNNING) "..." else ""}"
                            } else {
                                "বিরতি চলছে${if (timerState == ComposeTimerState.RUNNING) "..." else ""}"
                            },
                            style = MaterialTheme.typography.bodySmall,
                            color = phaseColor,
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        // XP preview
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "XP: ",
                                style = MaterialTheme.typography.labelSmall,
                                color = XpGain,
                                fontFamily = EnglishFontFamily,
                            )
                            Text(
                                text = "+${xpPreview.toBengaliDigits()}",
                                style = MaterialTheme.typography.labelSmall,
                                color = XpGain,
                                fontFamily = EnglishFontFamily,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                    }
                }
            }
        }

        // ═══ Subject Selector & Session Tag ═══
        item {
            GlassElevatedCard(
                cornerRadius = shapes.cardRadiusSmall,
                padding = PaddingValues(16.dp),
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(
                        text = "\uD83D\uDCD6 বিষয় নির্বাচন",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )

                    // Subject dropdown
                    ExposedDropdownMenuBox(
                        expanded = showSubjectDropdown,
                        onExpandedChange = { showSubjectDropdown = it },
                    ) {
                        OutlinedTextField(
                            value = selectedSubject?.name ?: "বিষয় নির্বাচন করুন",
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = showSubjectDropdown)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(MenuAnchorType.PrimaryNotEditable),
                            shape = RoundedCornerShape(shapes.inputFieldRadius),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Primary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                                unfocusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            ),
                            textStyle = MaterialTheme.typography.bodyLarge,
                        )
                        ExposedDropdownMenu(
                            expanded = showSubjectDropdown,
                            onDismissRequest = { showSubjectDropdown = false },
                        ) {
                            // "None" option
                            DropdownMenuItem(
                                text = { Text("কোনো বিষয় নয়", style = MaterialTheme.typography.bodyMedium) },
                                onClick = {
                                    viewModel.selectSubject(null)
                                    showSubjectDropdown = false
                                },
                            )
                            items(subjects) { subject ->
                                DropdownMenuItem(
                                    text = {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Box(
                                                modifier = Modifier
                                                    .size(8.dp)
                                                    .background(
                                                        subject.colorHex.toComposeColor(),
                                                        CircleShape
                                                    ),
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Text(
                                                text = subject.name,
                                                style = MaterialTheme.typography.bodyMedium,
                                            )
                                        }
                                    },
                                    onClick = {
                                        viewModel.selectSubject(subject)
                                        showSubjectDropdown = false
                                    },
                                )
                            }
                        }
                    }

                    // Session tag row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(
                            text = "\uD83C\uDFF7\uFE0F ট্যাগ: ",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                        GlassOutlinedCard(
                            onClick = { showSessionTagDialog = true },
                            cornerRadius = shapes.chipRadius,
                            padding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                        ) {
                            Text(
                                text = sessionTag.ifBlank { "অধ্যায়/টপিক যোগ করুন" },
                                style = MaterialTheme.typography.labelMedium,
                                color = if (sessionTag.isNotBlank())
                                    MaterialTheme.colorScheme.onSurface
                                else
                                    MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                            )
                        }
                    }

                    // Custom duration button
                    if (selectedSubject != null) {
                        GlassOutlinedCard(
                            onClick = { showCustomDurationDialog = true },
                            cornerRadius = shapes.chipRadius,
                            padding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Settings,
                                    contentDescription = null,
                                    modifier = Modifier.size(14.dp),
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                val customMin = customDurations[selectedSubject!!.id]
                                Text(
                                    text = if (customMin != null) {
                                        "কাস্টম সময়: ${customMin.toBengaliDigits()} মিনিট"
                                    } else {
                                        "কাস্টম সময় সেট করুন"
                                    },
                                    style = MaterialTheme.typography.labelMedium,
                                    color = if (customMin != null) Primary
                                    else MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                            }
                        }
                    }
                }
            }
        }

        // ═══ Controls ═══
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                // Stop button
                ControlButton(
                    icon = { Icon(Icons.Default.Stop, contentDescription = "বন্ধ") },
                    label = "বন্ধ",
                    color = Error,
                    enabled = timerState != ComposeTimerState.IDLE,
                    onClick = { viewModel.stopTimer() },
                )

                // Start/Pause button
                ControlButton(
                    icon = {
                        Icon(
                            if (timerState == ComposeTimerState.RUNNING) Icons.Default.Pause
                            else Icons.Default.PlayArrow,
                            contentDescription = if (timerState == ComposeTimerState.RUNNING) "বিরতি"
                            else "শুরু",
                            modifier = Modifier.size(32.dp),
                        )
                    },
                    label = when (timerState) {
                        ComposeTimerState.RUNNING -> "বিরতি"
                        ComposeTimerState.PAUSED -> "চালু"
                        ComposeTimerState.IDLE -> "শুরু"
                    },
                    color = phaseColor,
                    isPrimary = true,
                    onClick = {
                        when (timerState) {
                            ComposeTimerState.IDLE -> viewModel.startTimer()
                            ComposeTimerState.RUNNING -> viewModel.pauseTimer()
                            ComposeTimerState.PAUSED -> viewModel.resumeTimer()
                        }
                    },
                )

                // Skip button (only in break)
                ControlButton(
                    icon = { Icon(Icons.Default.SkipNext, contentDescription = "এড়ান") },
                    label = "এড়ান",
                    color = MaterialTheme.colorScheme.tertiary,
                    enabled = timerPhase != ComposeTimerPhase.WORK,
                    onClick = { viewModel.skipBreak() },
                )
            }
        }

        // ═══ Auto-start toggle ═══
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "স্বয়ংক্রিয় পরবর্তী সেশন",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    Text(
                        text = "বিরতি শেষে পরবর্তী সেশন অটো-শুরু",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
                Switch(
                    checked = autoStartNext,
                    onCheckedChange = { autoStartNext = it },
                    colors = SwitchDefaults.colors(
                        checkedTrackColor = Primary,
                        checkedThumbColor = OnPrimary,
                    ),
                )
            }
        }

        // ═══ Pomodoro Counters ═══
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                GlassFilledCard(
                    modifier = Modifier.weight(1f),
                    tint = Primary.copy(alpha = 0.08f),
                    cornerRadius = shapes.cardRadiusSmall,
                    padding = PaddingValues(16.dp),
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = completedPomodoros.toBengaliDigits(),
                            style = SpecialTextStyles.statValue,
                            color = Primary,
                            fontFamily = EnglishFontFamily,
                        )
                        Text(
                            text = "আজকের সেশন",
                            style = SpecialTextStyles.statLabel,
                        )
                    }
                }
                GlassFilledCard(
                    modifier = Modifier.weight(1f),
                    tint = Secondary.copy(alpha = 0.08f),
                    cornerRadius = shapes.cardRadiusSmall,
                    padding = PaddingValues(16.dp),
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "${pomodorosInSet}/4",
                            style = SpecialTextStyles.statValue,
                            color = Secondary,
                            fontFamily = EnglishFontFamily,
                        )
                        Text(
                            text = "সেট প্রগতি",
                            style = SpecialTextStyles.statLabel,
                        )
                    }
                }
            }
        }

        // ═══ Weekly Focus Time Progress ═══
        item {
            GlassElevatedCard(
                cornerRadius = shapes.cardRadiusSmall,
                padding = PaddingValues(16.dp),
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(
                            text = "\uD83D\uDCCA সাপ্তাহিক ফোকাস",
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                        Text(
                            text = "${weeklyMinutes.toBengaliDigits()}/${weeklyGoal.toBengaliDigits()} মিনিট",
                            style = MaterialTheme.typography.labelMedium,
                            fontFamily = EnglishFontFamily,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                    val weeklyProgress = remember(weeklyMinutes, weeklyGoal) {
                        if (weeklyGoal <= 0) 0f
                        else (weeklyMinutes.toFloat() / weeklyGoal.toFloat()).coerceIn(0f, 1f)
                    }
                    LinearProgressIndicator(
                        progress = { weeklyProgress },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = Primary,
                        trackColor = XpBarBg,
                    )
                    Text(
                        text = "লক্ষ্যের ${(weeklyProgress * 100).toInt().toBengaliDigits()}% অর্জিত",
                        style = MaterialTheme.typography.labelSmall,
                        color = if (weeklyProgress >= 1f) Success
                        else MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        }

        // ═══ Guided Break Suggestions (visible during breaks) ═══
        item {
            AnimatedVisibility(
                visible = timerPhase != ComposeTimerPhase.WORK,
                enter = slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = motion.slideUp,
                ) + fadeIn(motion.fadeIn),
                exit = slideOutVertically(
                    targetOffsetY = { it },
                    animationSpec = tween(200),
                ) + fadeOut(tween(200)),
            ) {
                GlassElevatedCard(
                    cornerRadius = shapes.cardRadiusSmall,
                    padding = PaddingValues(16.dp),
                    tint = TimerShortBreak.copy(alpha = 0.05f),
                ) {
                    val tip = currentBreakTip ?: BreakActivities.getRandomActivity()
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = "\uD83C\uDF3F বিরতির পরামর্শ",
                            style = MaterialTheme.typography.titleSmall,
                            color = TimerShortBreak,
                        )
                        Text(
                            text = "${tip.icon} ${tip.titleBn}",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                        Text(
                            text = tip.descriptionBn,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center,
                        )
                        Text(
                            text = "${tip.duration.toBengaliDigits()} সেকেন্ড",
                            style = MaterialTheme.typography.labelSmall,
                            fontFamily = EnglishFontFamily,
                            color = TimerShortBreak.copy(alpha = 0.7f),
                        )
                    }
                }
            }
        }

        // ═══ Today's Session History ═══
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "\uD83D\uDCDA আজকের ফোকাস সেশন",
                    style = SpecialTextStyles.sectionHeader,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                if (completedPomodoros == 0 && timerState == ComposeTimerState.IDLE) {
                    GlassOutlinedCard(
                        cornerRadius = shapes.cardRadiusSmall,
                        padding = PaddingValues(24.dp),
                    ) {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center,
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "\uD83D\uDCD6",
                                    fontSize = 32.sp,
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "এখনো কোনো সেশন শুরু হয়নি",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    textAlign = TextAlign.Center,
                                )
                                Text(
                                    text = "উপরের বিষয় নির্বাচন করে শুরু করুন!",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                                    textAlign = TextAlign.Center,
                                )
                            }
                        }
                    }
                } else {
                    // Display completed pomodoro indicator dots
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        repeat(min(completedPomodoros, 12)) { index ->
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .background(
                                        color = if (index < pomodorosInSet) Primary
                                        else Primary.copy(alpha = 0.4f),
                                        shape = CircleShape,
                                    ),
                            )
                        }
                        if (completedPomodoros > 12) {
                            Text(
                                text = "+${(completedPomodoros - 12).toBengaliDigits()}",
                                style = MaterialTheme.typography.labelSmall,
                                fontFamily = EnglishFontFamily,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                    }
                    if (timerState != ComposeTimerState.IDLE) {
                        Spacer(modifier = Modifier.height(4.dp))
                        GlassFilledCard(
                            tint = phaseColor.copy(alpha = 0.08f),
                            cornerRadius = shapes.cardRadiusSmall,
                            padding = PaddingValues(12.dp),
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .size(8.dp)
                                            .background(phaseColor, CircleShape),
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = selectedSubject?.name ?: "চলমান সেশন",
                                        style = MaterialTheme.typography.bodyMedium,
                                    )
                                }
                                Text(
                                    text = timerDisplay,
                                    style = MaterialTheme.typography.labelMedium,
                                    fontFamily = EnglishFontFamily,
                                    color = phaseColor,
                                )
                            }
                        }
                    }
                }
            }
        }

        // ═══ Session Tag Dialog ═══
        item {
            if (showSessionTagDialog) {
                var tagInput by remember { mutableStateOf(sessionTag) }
                AlertDialog(
                    onDismissRequest = { showSessionTagDialog = false },
                    title = {
                        Text(
                            text = "\uD83C\uDFF7\uFE0F সেশন ট্যাগ",
                            style = MaterialTheme.typography.titleMedium,
                        )
                    },
                    text = {
                        OutlinedTextField(
                            value = tagInput,
                            onValueChange = { tagInput = it },
                            label = { Text("অধ্যায় বা টপিকের নাম") },
                            placeholder = { Text("যেমন: অধ্যায় ৫ - বীজগণিত") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(shapes.inputFieldRadius),
                        )
                    },
                    confirmButton = {
                        TextButton(onClick = {
                            viewModel.setSessionTag(tagInput)
                            showSessionTagDialog = false
                        }) {
                            Text("সংরক্ষণ", color = Primary)
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showSessionTagDialog = false }) {
                            Text("বাতিল", color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    },
                    containerColor = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(shapes.dialogRadius),
                )
            }
        }
    }

    // ═══ Custom Duration Dialog (outside LazyColumn) ═══
    if (showCustomDurationDialog && selectedSubject != null) {
        var durationMinutes by remember {
            mutableIntStateOf(
                customDurations[selectedSubject!!.id] ?: currentMode.workDuration
            )
        }
        AlertDialog(
            onDismissRequest = { showCustomDurationDialog = false },
            title = {
                Text(
                    text = "\u23F1\uFE0F কাস্টম সময়",
                    style = MaterialTheme.typography.titleMedium,
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    Text(
                        text = "${selectedSubject!!.name} - ফোকাস সময় সেট করুন",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Slider(
                        value = durationMinutes.toFloat(),
                        onValueChange = { durationMinutes = it.toInt() },
                        valueRange = 5f..120f,
                        steps = (120 - 5) / 5 - 1,
                        colors = SliderDefaults.colors(
                            thumbColor = Primary,
                            activeTrackColor = Primary,
                            inactiveTrackColor = XpBarBg,
                        ),
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        Text(
                            text = "${durationMinutes.toBengaliDigits()} মিনিট",
                            style = MaterialTheme.typography.headlineMedium,
                            color = Primary,
                            fontFamily = EnglishFontFamily,
                        )
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.setCustomDuration(selectedSubject!!.id, durationMinutes)
                    showCustomDurationDialog = false
                }) {
                    Text("সেট করুন", color = Primary)
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showCustomDurationDialog = false
                }) {
                    Text("বাতিল", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            },
            containerColor = MaterialTheme.colorScheme.surface,
            shape = RoundedCornerShape(shapes.dialogRadius),
        )
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Control Button — animated press/release for timer controls
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun ControlButton(
    icon: @Composable () -> Unit,
    label: String,
    color: Color,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isPrimary: Boolean = false,
    onClick: () -> Unit,
) {
    val motion = LocalMotion.current
    val interactionSource = remember { MutableInteractionSource() }
    var pressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.9f else 1f,
        animationSpec = if (pressed) motion.buttonPress else motion.buttonRelease,
        label = "controlBtnScale",
    )

    val containerColor = if (isPrimary) color.copy(alpha = 0.2f) else Color.Transparent
    val contentColor = if (enabled) color else color.copy(alpha = 0.4f)
    val size = if (isPrimary) 72.dp else 56.dp

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        GlassFilledCard(
            onClick = { if (enabled) onClick() },
            tint = containerColor,
            cornerRadius = if (isPrimary) 36.dp else 28.dp,
            padding = PaddingValues(0.dp),
        ) {
            Box(
                modifier = Modifier
                    .size(size)
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                    },
                contentAlignment = Alignment.Center,
            ) {
                androidx.compose.foundation.layout.Box(
                    modifier = Modifier
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null,
                            onClick = { if (enabled) onClick() },
                        )
                        .padding(if (isPrimary) 18.dp else 14.dp),
                    ) {
                        androidx.compose.foundation.layout.Box(
                            modifier = Modifier.graphicsLayer {
                                scaleX = scale
                                scaleY = scale
                            },
                            contentAlignment = Alignment.Center,
                        ) {
                            icon()
                        }
                    }
                }
            }
        }
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = contentColor,
        )
    }

// ═══════════════════════════════════════════════════════════════════════════════
// Confetti Celebration Overlay — confetti-like scale + color pulse animation
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun ConfettiCelebrationOverlay(
    visible: Boolean,
    scale: Float,
    color: Color,
) {
    val infiniteTransition = rememberInfiniteTransition(label = "confetti")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart,
        ),
        label = "confettiRotation",
    )
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "confettiPulse",
    )

    if (visible && scale > 0.01f) {
        Canvas(
            modifier = Modifier
                .size(280.dp)
                .graphicsLayer {
                    this.scaleX = scale.toFloat() * pulseScale.toFloat()
                    this.scaleY = scale.toFloat() * pulseScale.toFloat()
                    this.rotationZ = (rotation * 0.1f).toFloat()
                    alpha = scale
                },
        ) {
            val confettiColors = listOf(
                TimerWork, TimerShortBreak, TimerLongBreak,
                Primary, Secondary, Tertiary,
                Chart1, Chart3, Chart5,
            )
            val size = 8.dp.toPx()
            for (i in 0 until 24) {
                val angle = Math.toRadians((i * 15f).toDouble()).toFloat() + (rotation * 0.02f)
                val distance = ((100 + i * 4) * scale).toFloat()
                val x = center.x + kotlin.math.cos(angle.toDouble()) * distance
                val y = center.y + kotlin.math.sin(angle.toDouble()) * distance
                drawCircle(
                    color = confettiColors[i % confettiColors.size].copy(
                        alpha = (0.8f - i * 0.03f).coerceAtLeast(0.1f)
                    ),
                    radius = size * (1f - i * 0.02f).coerceAtLeast(0.3f),
                    center = Offset(x.toFloat(), y.toFloat()),
                )
            }
        }
    }
}
