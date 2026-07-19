package com.porashona.studymaster.ui.compose.screens.timer

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
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
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.porashona.studymaster.ui.compose.theme.*
import com.porashona.studymaster.ui.compose.viewmodels.ComposeTimerState
import com.porashona.studymaster.ui.compose.viewmodels.TimerViewModel
import com.porashona.studymaster.utils.ZenSessionManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.sin

// ═══════════════════════════════════════════════════════════════════════════════
// ZenModeScreen — Full-screen focus overlay for deep study sessions.
//
// Features:
//  - Strict vs Light blocking presets
//  - App whitelist configuration (calculator, dictionary)
//  - Full-screen dark overlay with timer
//  - Breathing exercise animation
//  - Soft ambient background particles
//  - Exit with long-press button (3 seconds)
//  - Session stats on exit
// ═══════════════════════════════════════════════════════════════════════════════

enum class ZenPreset(val labelBn: String, val labelEn: String, val descriptionBn: String) {
    STRICT(
        labelBn = "কঠোর",
        labelEn = "Strict",
        descriptionBn = "সকল নোটিফিকেশন বন্ধ, কোনো অ্যাপ অনুমোদন নয়",
    ),
    LIGHT(
        labelBn = "হালকা",
        labelEn = "Light",
        descriptionBn = "হোয়াইটলিস্ট অ্যাপ অনুমোদন, জরুরি নোটিফিকেশন",
    ),
}

data class WhitelistedApp(
    val packageName: String,
    val name: String,
    val iconEmoji: String,
    var isWhitelisted: Boolean = false,
)

private val defaultWhitelistApps = listOf(
    WhitelistedApp("com.android.calculator2", "ক্যালকুলেটর", "\uD83D\uDD22"),
    WhitelistedApp("com.dictionary", "অভিধান", "\uD83D\uDCD6"),
    WhitelistedApp("com.notion", "নোশন", "\uD83D\uDCDD"),
    WhitelistedApp("com.google.android.calendar", "ক্যালেন্ডার", "\uD83D\uDCC5"),
)

@Composable
fun ZenModeScreen(
    viewModel: TimerViewModel = hiltViewModel(),
    durationMinutes: Int = ZenSessionManager.DEFAULT_DURATION_MINUTES,
    onExit: (totalSeconds: Long, xpEarned: Int) -> Unit = { _, _ -> },
) {
    val timerState by viewModel.timerState.collectAsState()
    val displaySeconds by viewModel.displaySeconds.collectAsState()
    val totalSeconds by viewModel.totalSeconds.collectAsState()
    val selectedSubject by viewModel.selectedSubject.collectAsState()
    val motion = LocalMotion.current
    val shapes = LocalGlassShapes.current
    val coroutineScope = rememberCoroutineScope()

    // Local Zen state
    var zenPreset by remember { mutableStateOf(ZenPreset.STRICT) }
    var whitelistedApps by remember {
        mutableStateOf(defaultWhitelistApps.toMutableList())
    }
    var isSessionActive by remember { mutableStateOf(false) }
    var elapsedSeconds by remember { mutableLongStateOf(0L) }
    var showSetup by remember { mutableStateOf(true) }
    var showExitStats by remember { mutableStateOf(false) }
    var longPressProgress by remember { mutableFloatStateOf(0f) }
    var isLongPressing by remember { mutableStateOf(false) }

    // Breathing animation state
    val infiniteTransition = rememberInfiniteTransition(label = "zenBreathing")
    val breathScale by infiniteTransition.animateFloat(
        initialValue = 0.6f,
        targetValue = 1.4f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "breathScale",
    )
    val breathAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.7f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "breathAlpha",
    )

    // Ambient particle positions
    val particles = remember {
        List(20) { index ->
            AmbientParticle(
                x = (index * 137.508f) % 1f,
                y = (index * 97.31f) % 1f,
                speed = 0.0002f + (index % 5) * 0.0001f,
                size = 2f + (index % 3) * 1.5f,
                alpha = 0.1f + (index % 4) * 0.05f,
                phase = (index * 47) % 360,
            )
        }
    }

    // Timer tick for Zen session
    LaunchedEffect(isSessionActive) {
        if (isSessionActive) {
            while (true) {
                delay(1000L)
                elapsedSeconds++
                if (elapsedSeconds >= durationMinutes * 60L) {
                    isSessionActive = false
                    showExitStats = true
                    break
                }
            }
        }
    }

    // Long press exit animation
    LaunchedEffect(isLongPressing) {
        if (isLongPressing) {
            longPressProgress = 0f
            val steps = 30
            repeat(steps) {
                delay(100L)
                longPressProgress = (it + 1f) / steps
            }
            isSessionActive = false
            showExitStats = true
            isLongPressing = false
            longPressProgress = 0f
        }
    }

    // Background color
    val bgColor by animateColorAsState(
        targetValue = if (showSetup) MaterialTheme.colorScheme.background else AmoledBackground,
        animationSpec = tween(800),
        label = "zenBg",
    )

    val xpEarned = remember(elapsedSeconds) {
        ((elapsedSeconds / 60) * 12).toInt().coerceAtLeast(0)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bgColor),
    ) {
        // ═══ Setup Screen ═══
        AnimatedVisibility(
            visible = showSetup,
            enter = fadeIn(motion.fadeIn) + scaleIn(
                initialScale = 0.9f,
                animationSpec = motion.scaleIn,
            ),
            exit = fadeOut(tween(300)) + scaleOut(
                targetScale = 0.9f,
                animationSpec = tween(300),
            ),
        ) {
            ZenSetupScreen(
                preset = zenPreset,
                onPresetChanged = { zenPreset = it },
                durationMinutes = durationMinutes,
                whitelistedApps = whitelistedApps,
                onWhitelistToggled = { index ->
                    val mutable = whitelistedApps.toMutableList()
                    mutable[index] = mutable[index].copy(
                        isWhitelisted = !mutable[index].isWhitelisted
                    )
                    whitelistedApps = mutable
                },
                onStartSession = {
                    showSetup = false
                    isSessionActive = true
                    elapsedSeconds = 0L
                },
            )
        }

        // ═══ Active Zen Session ═══
        AnimatedVisibility(
            visible = !showSetup && !showExitStats,
            enter = fadeIn(tween(800)),
            exit = fadeOut(tween(500)),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                Color(0xFF0A0A1A),
                                Color(0xFF000000),
                            ),
                            center = Offset(
                                0.5f * 1080f, 0.35f * 1920f
                            ),
                        )
                    ),
                contentAlignment = Alignment.Center,
            ) {
                // Ambient particles
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val w = size.width
                    val h = size.height
                    particles.forEach { p ->
                        val time = System.currentTimeMillis() * p.speed
                        val px = (p.x * w + sin((time + p.phase) * 0.01f) * 30f)
                        val py = (p.y * h + cos((time + p.phase * 1.3f) * 0.01f) * 30f)
                        val alpha = p.alpha * 0.5f
                        drawCircle(
                            color = Primary.copy(alpha = alpha),
                            radius = p.size,
                            center = Offset(px, py),
                        )
                    }
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(32.dp),
                ) {
                    // Breathing exercise animation
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.size(200.dp),
                    ) {
                        Canvas(modifier = Modifier.size(200.dp)) {
                            val currentScale = breathScale
                            val radius = (size.minDimension / 2 * currentScale).coerceAtLeast(1f)
                            drawCircle(
                                brush = Brush.radialGradient(
                                    colors = listOf(
                                        Primary.copy(alpha = breathAlpha * 0.6f),
                                        Primary.copy(alpha = 0f),
                                    ),
                                    center = center,
                                    radius = radius,
                                ),
                                center = center,
                                radius = radius,
                            )
                            // Outer ring
                            drawCircle(
                                color = Primary.copy(alpha = breathAlpha * 0.3f),
                                radius = radius + 8.dp.toPx(),
                                center = center,
                                style = Stroke(width = 1.dp.toPx()),
                            )
                        }

                        // Breathing text
                        val breathText by animateFloatAsState(
                            targetValue = if (breathScale > 1.0f) 1f else 0f,
                            animationSpec = tween(500),
                            label = "breathText",
                        )
                        Text(
                            text = if (breathText > 0.5f) "শ্বাস ছাড়ুন"
                            else "শ্বাস নিন",
                            style = MaterialTheme.typography.titleLarge,
                            color = Primary.copy(alpha = 0.6f),
                            fontWeight = FontWeight.Light,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.alpha(0.7f),
                        )
                    }

                    Spacer(modifier = Modifier.height(48.dp))

                    // Zen timer display
                    val mins = (elapsedSeconds / 60).toInt()
                    val secs = (elapsedSeconds % 60).toInt()
                    Text(
                        text = String.format("%02d:%02d", mins, secs),
                        style = SpecialTextStyles.timerDisplay.copy(
                            fontSize = 64.sp,
                            color = Color.White.copy(alpha = 0.9f),
                        ),
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Subject name
                    if (selectedSubject != null) {
                        Text(
                            text = selectedSubject!!.name,
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White.copy(alpha = 0.6f),
                        )
                    } else {
                        Text(
                            text = "গভীর মনোযোগে পড়াশোনা চলছে",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White.copy(alpha = 0.5f),
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // XP counter
                    Text(
                        text = "XP +${xpEarned.toBengaliDigits()}",
                        style = MaterialTheme.typography.labelLarge,
                        fontFamily = EnglishFontFamily,
                        color = XpGain.copy(alpha = 0.7f),
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    // Duration remaining
                    val remainingMins = durationMinutes - mins
                    Text(
                        text = "${remainingMins.coerceAtLeast(0).toBengaliDigits()} মিনিট বাকি",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.4f),
                    )

                    // Preset indicator
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = if (zenPreset == ZenPreset.STRICT) "\uD83D\uDEE1\uFE0F কঠোর মোড"
                        else "\u2708\uFE0F হালকা মোড",
                        style = MaterialTheme.typography.labelSmall,
                        color = if (zenPreset == ZenPreset.STRICT) Error.copy(alpha = 0.5f)
                        else TimerShortBreak.copy(alpha = 0.5f),
                    )
                }

                // Exit button at bottom with long-press
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 48.dp, start = 64.dp, end = 64.dp),
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(shapes.buttonRadius))
                            .background(Color.White.copy(alpha = 0.08f))
                            .pointerInput(Unit) {
                                detectTapGestures(
                                    onPress = {
                                        isLongPressing = true
                                        tryAwaitRelease()
                                        isLongPressing = false
                                        longPressProgress = 0f
                                    },
                                )
                            }
                            .padding(16.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            // Long-press progress bar
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(3.dp)
                                    .clip(RoundedCornerShape(2.dp))
                                    .background(Color.White.copy(alpha = 0.1f)),
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(longPressProgress)
                                        .height(3.dp)
                                        .clip(RoundedCornerShape(2.dp))
                                        .background(
                                            animateColorAsState(
                                                targetValue = if (longPressProgress > 0.7f) Error else Primary,
                                                label = "exitBarColor",
                                            ).value
                                        ),
                                )
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = if (longPressProgress > 0.01f) {
                                    "ধরে রাখুন... ${((1f - longPressProgress) * 3).toInt().toBengaliDigits()}সে"
                                } else {
                                    "প্রস্থান করতে দীর্ঘ চাপ দিন"
                                },
                                style = MaterialTheme.typography.labelMedium,
                                color = Color.White.copy(
                                    alpha = if (longPressProgress > 0.01f) 0.8f else 0.4f
                                ),
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                }
            }
        }

        // ═══ Exit Stats Screen ═══
        AnimatedVisibility(
            visible = showExitStats,
            enter = fadeIn(motion.fadeIn) + scaleIn(
                initialScale = 0.9f,
                animationSpec = motion.scaleIn,
            ),
            exit = fadeOut(tween(300)),
        ) {
            ZenExitStatsScreen(
                elapsedSeconds = elapsedSeconds,
                xpEarned = xpEarned,
                subjectName = selectedSubject?.name ?: "সাধারণ অধ্যয়ন",
                onDismiss = { onExit(elapsedSeconds, xpEarned) },
            )
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Zen Setup Screen — configure preset, duration, whitelist before session
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun ZenSetupScreen(
    preset: ZenPreset,
    onPresetChanged: (ZenPreset) -> Unit,
    durationMinutes: Int,
    whitelistedApps: List<WhitelistedApp>,
    onWhitelistToggled: (Int) -> Unit,
    onStartSession: () -> Unit,
) {
    val shapes = LocalGlassShapes.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        // Header
        Column {
            Text(
                text = "\uD83C\uDF2C\uFE0F জেন মোড",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Text(
                text = "সম্পূর্ণ মনোযোগে পড়াশোনা করুন",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }

        // Preset selector
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(
                text = "ব্লকিং প্রিসেট",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                ZenPreset.values().forEach { p ->
                    val isSelected = preset == p
                    GlassFilledCard(
                        onClick = { onPresetChanged(p) },
                        modifier = Modifier.weight(1f),
                        tint = if (isSelected) {
                            if (p == ZenPreset.STRICT) Error.copy(alpha = 0.12f)
                            else Primary.copy(alpha = 0.12f)
                        } else null,
                        cornerRadius = shapes.cardRadiusSmall,
                        padding = PaddingValues(16.dp),
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                        ) {
                            Text(
                                text = if (p == ZenPreset.STRICT) "\uD83D\uDEE1\uFE0F"
                                else "\u2708\uFE0F",
                                fontSize = 24.sp,
                            )
                            Text(
                                text = p.labelBn,
                                style = MaterialTheme.typography.titleSmall,
                                color = if (isSelected) MaterialTheme.colorScheme.onSurface
                                else MaterialTheme.colorScheme.onSurfaceVariant,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                textAlign = TextAlign.Center,
                            )
                            Text(
                                text = p.descriptionBn,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                                textAlign = TextAlign.Center,
                                maxLines = 2,
                            )
                        }
                    }
                }
            }
        }

        // Whitelist (visible in Light mode)
        AnimatedVisibility(
            visible = preset == ZenPreset.LIGHT,
            enter = fadeIn() + slideInVertically { it },
            exit = fadeOut() + slideOutVertically { it },
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(
                    text = "\u2705 হোয়াইটলিস্ট অ্যাপ",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    text = "এই অ্যাপগুলো জেন মোডেও ব্যবহার করতে পারবেন",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                whitelistedApps.forEachIndexed { index, app ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(shapes.cardRadiusSmall))
                            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = app.iconEmoji, fontSize = 20.sp)
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = app.name,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface,
                            )
                        }
                        Switch(
                            checked = app.isWhitelisted,
                            onCheckedChange = { onWhitelistToggled(index) },
                            colors = SwitchDefaults.colors(
                                checkedTrackColor = Primary,
                                checkedThumbColor = OnPrimary,
                            ),
                        )
                    }
                }
            }
        }

        // Duration display
        GlassFilledCard(
            tint = Primary.copy(alpha = 0.06f),
            cornerRadius = shapes.cardRadius,
            padding = PaddingValues(24.dp),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    text = "\u23F1\uFE0F সেশন সময়কাল",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Text(
                    text = "${durationMinutes.toBengaliDigits()} মিনিট",
                    style = MaterialTheme.typography.headlineMedium,
                    fontFamily = EnglishFontFamily,
                    color = Primary,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = "~${(durationMinutes * 12).toBengaliDigits()} XP অর্জন সম্ভব",
                    style = MaterialTheme.typography.labelSmall,
                    fontFamily = EnglishFontFamily,
                    color = XpGain.copy(alpha = 0.7f),
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Start button
        GlassFilledCard(
            onClick = onStartSession,
            tint = Primary.copy(alpha = 0.2f),
            cornerRadius = shapes.buttonRadius,
            padding = PaddingValues(horizontal = 32.dp, vertical = 16.dp),
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "\uD83C\uDF2C\uFE0F জেন মোড শুরু করুন",
                    style = MaterialTheme.typography.titleMedium,
                    color = Primary,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Zen Exit Stats Screen — session summary on exit
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun ZenExitStatsScreen(
    elapsedSeconds: Long,
    xpEarned: Int,
    subjectName: String,
    onDismiss: () -> Unit,
) {
    val shapes = LocalGlassShapes.current
    val minutes = (elapsedSeconds / 60).toInt()
    val hours = minutes / 60
    val remainingMins = minutes % 60

    // Confetti-like animation
    val infiniteTransition = rememberInfiniteTransition(label = "exitConfetti")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "exitPulse",
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AmoledBackground),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier
                .padding(32.dp)
                .graphicsLayer {
                    scaleX = pulseScale
                    scaleY = pulseScale
                },
        ) {
            // Celebration icon
            Text(
                text = "\uD83C\uDF1F",
                fontSize = 64.sp,
            )

            Text(
                text = "সেশন সম্পন্ন!",
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White,
            )

            Text(
                text = "দারুণ মনোযোগ দেখিয়েছেন",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.6f),
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Stats cards
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth(),
            ) {
                // Duration
                GlassFilledCard(
                    tint = Primary.copy(alpha = 0.1f),
                    cornerRadius = shapes.cardRadiusSmall,
                    padding = PaddingValues(20.dp),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "\u23F1\uFE0F মোট সময়",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White.copy(alpha = 0.7f),
                        )
                        Text(
                            text = if (hours > 0) {
                                "${hours.toBengaliDigits()} ঘণ্টা ${remainingMins.toBengaliDigits()} মিনিট"
                            } else {
                                "${minutes.toBengaliDigits()} মিনিট"
                            },
                            style = MaterialTheme.typography.titleMedium,
                            fontFamily = EnglishFontFamily,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }

                // XP earned
                GlassFilledCard(
                    tint = XpGain.copy(alpha = 0.1f),
                    cornerRadius = shapes.cardRadiusSmall,
                    padding = PaddingValues(20.dp),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "\u2B50 অর্জিত XP",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White.copy(alpha = 0.7f),
                        )
                        Text(
                            text = "+${xpEarned.toBengaliDigits()}",
                            style = MaterialTheme.typography.titleMedium,
                            fontFamily = EnglishFontFamily,
                            color = XpGain,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }

                // Subject
                GlassFilledCard(
                    tint = Secondary.copy(alpha = 0.1f),
                    cornerRadius = shapes.cardRadiusSmall,
                    padding = PaddingValues(20.dp),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "\uD83D\uDCD6 বিষয়",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White.copy(alpha = 0.7f),
                        )
                        Text(
                            text = subjectName,
                            style = MaterialTheme.typography.titleSmall,
                            color = Secondary,
                            fontWeight = FontWeight.Medium,
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Motivational text
            Text(
                text = "\u201C ধৈর্য ও মনোযোগ সাফল্যের চাবিকাঠি \u201D",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.5f),
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Dismiss button
            GlassFilledCard(
                onClick = onDismiss,
                tint = Primary.copy(alpha = 0.15f),
                cornerRadius = shapes.buttonRadius,
                padding = PaddingValues(horizontal = 48.dp, vertical = 14.dp),
            ) {
                Text(
                    text = "চালিয়ে যান",
                    style = MaterialTheme.typography.titleMedium,
                    color = Primary,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Ambient particle data class for background animation
// ═══════════════════════════════════════════════════════════════════════════════

private data class AmbientParticle(
    val x: Float,
    val y: Float,
    val speed: Float,
    val size: Float,
    val alpha: Float,
    val phase: Float,
)