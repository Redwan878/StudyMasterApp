package com.porashona.studymaster.ui.compose.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.porashona.studymaster.data.model.BreakActivity
import com.porashona.studymaster.data.model.BreakActivities
import com.porashona.studymaster.data.model.BreakCategory
import com.porashona.studymaster.ui.compose.components.GlassElevatedCard
import com.porashona.studymaster.ui.compose.components.GlassFilledCard
import com.porashona.studymaster.ui.compose.components.GlassOutlinedCard
import com.porashona.studymaster.ui.compose.theme.AchievementUnlocked
import com.porashona.studymaster.ui.compose.theme.Chart1
import com.porashona.studymaster.ui.compose.theme.Chart2
import com.porashona.studymaster.ui.compose.theme.Chart3
import com.porashona.studymaster.ui.compose.theme.Chart5
import com.porashona.studymaster.ui.compose.theme.EnglishFontFamily
import com.porashona.studymaster.ui.compose.theme.Error
import com.porashona.studymaster.ui.compose.theme.Info
import com.porashona.studymaster.ui.compose.theme.Success
import com.porashona.studymaster.ui.compose.theme.TimerShortBreak
import com.porashona.studymaster.ui.compose.theme.Warning
import com.porashona.studymaster.ui.compose.theme.XpBarBg
import com.porashona.studymaster.ui.compose.theme.toBengaliDigits
import kotlinx.coroutines.delay

// ═══════════════════════════════════════════════════════════════════════════════
// BreakCoachScreen — Guided break activities with Bengali UI
// ═══════════════════════════════════════════════════════════════════════════════

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BreakCoachScreen() {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    // ── State ─────────────────────────────────────────────────────────────────
    var selectedActivity by remember { mutableStateOf<BreakActivity?>(null) }
    var timerSeconds by remember { mutableIntStateOf(0) }
    var isTimerRunning by remember { mutableStateOf(false) }
    var showSkipDialog by remember { mutableStateOf(false) }
    var completedActivities by remember { mutableIntStateOf(0) }

    // Timer tick
    LaunchedEffect(isTimerRunning, selectedActivity) {
        if (isTimerRunning && selectedActivity != null && timerSeconds > 0) {
            while (isTimerRunning && timerSeconds > 0) {
                delay(1000L)
                timerSeconds--
            }
            if (timerSeconds <= 0) {
                isTimerRunning = false
                completedActivities++
            }
        }
    }

    // ── Skip break dialog ─────────────────────────────────────────────────────
    if (showSkipDialog) {
        AlertDialog(
            onDismissRequest = { showSkipDialog = false },
            title = {
                Text(
                    "বিরতি বাদ দেবেন?",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                )
            },
            text = {
                Text(
                    "বিরতি নেওয়া আপনার মনোযোগ ও স্বাস্থ্যের জন্য গুরুত্বপূর্ণ। কমপক্ষে একটি ব্যায়াম করুন।",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            },
            confirmButton = {
                FilledTonalButton(
                    onClick = {
                        showSkipDialog = false
                        selectedActivity = null
                        timerSeconds = 0
                        isTimerRunning = false
                    },
                    colors = androidx.compose.material3.ButtonDefaults.filledTonalButtonColors(
                        containerColor = Error.copy(alpha = 0.15f),
                        contentColor = Error,
                    ),
                ) {
                    Text("হ্যাঁ, বাদ দিন")
                }
            },
            dismissButton = {
                TextButton(onClick = { showSkipDialog = false }) {
                    Text("ফিরে যান")
                }
            },
        )
    }

    // ── Categorized activities ─────────────────────────────────────────────────
    val categories = remember {
        listOf(
            BreakCategory.STRETCHING to "স্ট্রেচিং" to "🧘" to Chart1,
            BreakCategory.EYE_EXERCISE to "চোখের ব্যায়াম" to "👁️" to Chart2,
            BreakCategory.BREATHING to "শ্বাস-প্রশ্বাস" to "🌬️" to Success,
            BreakCategory.MEDITATION to "ধ্যান" to "✨" to Chart3,
            BreakCategory.HYDRATION to "হাইড্রেশন" to "💧" to Info,
            BreakCategory.WALK to "হাঁটাচলা" to "🚶" to AchievementUnlocked,
            BreakCategory.SNACK to "স্ন্যাক" to "🍎" to Chart5,
        )
    }

    // ── Scaffold ──────────────────────────────────────────────────────────────
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "বিরতি কোচ",
                        style = MaterialTheme.typography.titleLarge,
                    )
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                            .padding(start = 16.dp),
                        tint = TimerShortBreak,
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                ),
                scrollBehavior = scrollBehavior,
            )
        },
        bottomBar = {
            // Skip break button
            if (selectedActivity == null) {
                GlassElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    padding = 12.dp,
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            "সম্পন্ন: ${completedActivities.toBengaliDigits()}টি ব্যায়াম",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                        FilledTonalButton(
                            onClick = { showSkipDialog = true },
                            colors = androidx.compose.material3.ButtonDefaults.filledTonalButtonColors(
                                containerColor = Error.copy(alpha = 0.12f),
                                contentColor = Error,
                            ),
                        ) {
                            Icon(
                                imageVector = Icons.Default.SkipNext,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("বিরতি বাদ দিন")
                        }
                    }
                }
            }
        },
    ) { innerPadding ->

        // ── Active exercise timer (full screen) ──────────────────────────────
        AnimatedVisibility(
            visible = selectedActivity != null,
            enter = fadeIn(tween(300)) + scaleIn(tween(300)),
            exit = fadeOut(tween(200)) + scaleOut(tween(200)),
            modifier = Modifier.padding(innerPadding),
        ) {
            selectedActivity?.let { activity ->
                ActiveExerciseView(
                    activity = activity,
                    timerSeconds = timerSeconds,
                    totalSeconds = activity.duration,
                    isRunning = isTimerRunning,
                    onPlayPause = { isTimerRunning = !isTimerRunning },
                    onRestart = {
                        timerSeconds = activity.duration
                        isTimerRunning = false
                    },
                    onFinish = {
                        selectedActivity = null
                        timerSeconds = 0
                        isTimerRunning = false
                        completedActivities++
                    },
                    onSkip = {
                        showSkipDialog = true
                    },
                )
            }
        }

        // ── Activity list (when no activity selected) ────────────────────────
        AnimatedVisibility(
            visible = selectedActivity == null,
            enter = fadeIn(tween(300)),
            exit = fadeOut(tween(200)),
            modifier = Modifier.padding(innerPadding),
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {

                // ══════════════════════════════════════════════════════════════════
                // SECTION: Welcome / tip
                // ══════════════════════════════════════════════════════════════════
                item {
                    GlassFilledCard(
                        modifier = Modifier.fillMaxWidth(),
                        tint = TimerShortBreak,
                        padding = 20.dp,
                    ) {
                        Row(verticalAlignment = Alignment.Top) {
                            Text("🌿", style = MaterialTheme.typography.headlineMedium)
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    "বিরতি নিন, মন তাজা রাখুন!",
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    "প্রতি ২৫-৩০ মিনিট পড়াশোনার পর ৫ মিনিট বিরতি নেওয়া মস্তিষ্কের জন্য উপকারী। নিচের যেকোনো একটি কার্যকলাপ বেছে নিন।",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                            }
                        }
                    }
                }

                // ══════════════════════════════════════════════════════════════════
                // SECTION: Quick actions
                // ══════════════════════════════════════════════════════════════════
                item {
                    Text(
                        "দ্রুত কার্যকলাপ",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    )
                }

                // Hydration (always visible as quick action)
                item {
                    QuickActionCard(
                        activity = BreakActivities.activities.find { it.id == "drink_water" }!!,
                        color = Info,
                        onClick = {
                            selectedActivity = it
                            timerSeconds = it.duration
                            isTimerRunning = true
                        },
                    )
                }

                // 20-20-20 rule
                item {
                    QuickActionCard(
                        activity = BreakActivities.activities.find { it.id == "20_20_20" }!!,
                        color = Chart2,
                        onClick = {
                            selectedActivity = it
                            timerSeconds = it.duration
                            isTimerRunning = true
                        },
                    )
                }

                // Box breathing
                item {
                    QuickActionCard(
                        activity = BreakActivities.activities.find { it.id == "box_breathing" }!!,
                        color = Success,
                        onClick = {
                            selectedActivity = it
                            timerSeconds = it.duration
                            isTimerRunning = true
                        },
                    )
                }

                // ══════════════════════════════════════════════════════════════════
                // SECTION: All activities by category
                // ══════════════════════════════════════════════════════════════════
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "সকল কার্যকলাপ",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    )
                }

                categories.forEach { (category, pair) ->
                    val (label, emoji, color) = pair
                    val activities = BreakActivities.getByCategory(category)

                    if (activities.isNotEmpty()) {
                        item {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(emoji, style = MaterialTheme.typography.titleMedium)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    label,
                                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                    color = color,
                                )
                            }
                        }

                        items(activities, key = { it.id }) { activity ->
                            ActivityListItem(
                                activity = activity,
                                color = color,
                                onClick = {
                                    selectedActivity = activity
                                    timerSeconds = activity.duration
                                    isTimerRunning = true
                                },
                            )
                        }

                        item { Spacer(modifier = Modifier.height(4.dp)) }
                    }
                }

                item { Spacer(modifier = Modifier.height(32.dp)) }
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Active exercise view (full-screen timer with description)
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun ActiveExerciseView(
    activity: BreakActivity,
    timerSeconds: Int,
    totalSeconds: Int,
    isRunning: Boolean,
    onPlayPause: () -> Unit,
    onRestart: () -> Unit,
    onFinish: () -> Unit,
    onSkip: () -> Unit,
) {
    val progress = if (totalSeconds > 0) {
        timerSeconds.toFloat() / totalSeconds
    } else 1f

    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(300),
        label = "exProgress",
    )

    val isFinished = timerSeconds <= 0 && !isRunning

    // Breathing phase indicator for breathing exercises
    val breathPhase = remember(timerSeconds, isRunning) {
        if (!isRunning || activity.category != BreakCategory.BREATHING) null
        else {
            val cyclePos = (totalSeconds - timerSeconds) % 16 // 16s per cycle
            when {
                cyclePos < 4 -> "শ্বাস নিন..." to 0.25f
                cyclePos < 8 -> "ধরে রাখুন..." to 0.5f
                cyclePos < 12 -> "ছাড়ুন..." to 0.75f
                else -> "ধরে রাখুন..." to 1.0f
            }
        }
    }

    val categoryColor = when (activity.category) {
        BreakCategory.STRETCHING -> Chart1
        BreakCategory.EYE_EXERCISE -> Chart2
        BreakCategory.BREATHING -> Success
        BreakCategory.MEDITATION -> Chart3
        BreakCategory.HYDRATION -> Info
        BreakCategory.WALK -> AchievementUnlocked
        BreakCategory.SNACK -> Chart5
    }

    LaunchedEffect(isFinished) {
        if (isFinished) {
            delay(1500L)
            onFinish()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        if (isFinished) {
            // Completed state
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = null,
                tint = Success,
                modifier = Modifier.size(80.dp),
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "সম্পন্ন! 🎉",
                style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                color = Success,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                activity.titleBn,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        } else {
            // Timer circle
            Box(
                modifier = Modifier.size(220.dp),
                contentAlignment = Alignment.Center,
            ) {
                Canvas(modifier = Modifier.size(220.dp)) {
                    // Background arc
                    drawArc(
                        color = XpBarBg,
                        startAngle = -90f,
                        sweepAngle = 360f,
                        useCenter = false,
                        style = Stroke(width = 8.dp.toPx(), cap = StrokeCap.Round),
                    )
                    // Progress arc (remaining time)
                    if (progress > 0f) {
                        drawArc(
                            color = categoryColor,
                            startAngle = -90f,
                            sweepAngle = 360f * animatedProgress,
                            useCenter = false,
                            style = Stroke(width = 8.dp.toPx(), cap = StrokeCap.Round),
                        )
                    }
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    // Exercise emoji
                    Text(
                        activity.icon,
                        style = MaterialTheme.typography.displayMedium,
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Timer display
                    val minutes = timerSeconds / 60
                    val seconds = timerSeconds % 60
                    Text(
                        "${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}",
                        style = MaterialTheme.typography.displaySmall.copy(
                            fontFamily = EnglishFontFamily,
                            fontWeight = FontWeight.Bold,
                            color = categoryColor,
                        ),
                    )

                    // Breathing phase
                    breathPhase?.let { (phase, _) ->
                        Spacer(modifier = Modifier.height(8.dp))
                        AnimatedVisibility(
                            visible = true,
                            enter = fadeIn(tween(300)),
                        ) {
                            Text(
                                phase,
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                ),
                                color = categoryColor,
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Activity title
            Text(
                activity.titleBn,
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Description
            GlassOutlinedCard(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .padding(horizontal = 16.dp),
                padding = 16.dp,
            ) {
                Text(
                    activity.descriptionBn,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                )
            }

            // Animated exercise demonstration (descriptive text)
            Spacer(modifier = Modifier.height(16.dp))
            ExerciseDescription(activity = activity, color = categoryColor)

            Spacer(modifier = Modifier.height(24.dp))

            // Controls
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // Restart
                IconButton(onClick = onRestart) {
                    Icon(
                        imageVector = Icons.Default.RestartAlt,
                        contentDescription = "পুনরায় শুরু",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(28.dp),
                    )
                }

                // Play/Pause
                FilledTonalIconButton(
                    onClick = onPlayPause,
                    modifier = Modifier.size(64.dp),
                ) {
                    Icon(
                        imageVector = if (isRunning) Icons.Default.Pause else Icons.Default.PlayArrow,
                        contentDescription = if (isRunning) "বিরতি" else "শুরু",
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(36.dp),
                    )
                }

                // Skip
                IconButton(onClick = onSkip) {
                    Icon(
                        imageVector = Icons.Default.SkipNext,
                        contentDescription = "বাদ দিন",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(28.dp),
                    )
                }
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Exercise description (step-by-step animated text)
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun ExerciseDescription(activity: BreakActivity, color: Color) {
    val steps = remember(activity.id) {
        when (activity.id) {
            "neck_stretch" -> listOf(
                "১. সোজা বসুন, কাঁধ শিথিল রাখুন",
                "২. মাথা ধীরে ডানদিকে কাত করুন",
                "৩. ১৫ সেকেন্ড ধরে রাখুন",
                "৪. বামদিকে কাত করে ১৫ সেকেন্ড ধরুন",
                "৫. সামনের দিকে মাথা নামান",
                "৬. ৩ বার পুনরাবৃত্তি করুন",
            )
            "shoulder_roll" -> listOf(
                "১. সোজা দাঁড়ান বা বসুন",
                "২. কাঁধ সামনের দিকে ঘোরান",
                "৩. বৃত্তাকারে ১০ বার ঘোরান",
                "৪. এবার পেছনের দিকে ১০ বার ঘোরান",
                "৫. শিথিল অনুভব করুন",
            )
            "back_stretch" -> listOf(
                "১. দাঁড়ান, পা কাঁধের সমান দূরত্বে",
                "২. হাত উপরে সিলিংয়ের দিকে বাড়ান",
                "৩. ৫ সেকেন্ড চেষ্টা করুন",
                "৪. ধীরে নিচু হয়ে পায়ের আঙুল স্পর্শ করুন",
                "৫. ১০ সেকেন্ড ধরে রাখুন",
            )
            "20_20_20" -> listOf(
                "১. পড়াশোনা থেকে চোখ তুলুন",
                "২. ২০ ফুট (৬ মিটার) দূরে তাকান",
                "৩. ২০ সেকেন্ড ধরে তাকিয়ে থাকুন",
                "৪. চোখ ব্লিংক করুন কয়েকবার",
                "৫. আবার পড়া শুরু করুন",
            )
            "box_breathing" -> listOf(
                "১. ৪ সেকেন্ড ধরে নাক দিয়ে শ্বাস নিন",
                "২. ৪ সেকেন্ড শ্বাস ধরে রাখুন",
                "৩. ৪ সেকেন্ড ধরে মুখ দিয়ে ছাড়ুন",
                "৪. ৪ সেকেন্ড শূন্য রাখুন",
                "৫. ৪ বার পুনরাবৃত্তি করুন",
            )
            "drink_water" -> listOf(
                "১. উঠে দাঁড়ান",
                "২. এক গ্লাস পানি নিন",
                "৩. ধীরে ধীরে খান, একবারে নয়",
                "৪. বসে পড়া শুরু করুন",
            )
            "short_walk" -> listOf(
                "১. উঠে দাঁড়ান",
                "২. কয়েক মিনিট হাঁটুন",
                "৩. জানালার কাছে গিয়ে তাকান",
                "৪. হাত-পা নাড়ুন",
                "৫. ফিরে বসুন",
            )
            else -> listOf(
                "১. নির্দেশনা অনুসরণ করুন",
                "২. নিজের গতিতে করুন",
                "৩. শিথিল থাকুন",
            )
        }
    }

    GlassFilledCard(
        modifier = Modifier.fillMaxWidth(0.85f),
        tint = color,
        padding = 16.dp,
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                "ধাপে ধাপে নির্দেশনা:",
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                color = color,
            )
            Spacer(modifier = Modifier.height(4.dp))
            steps.forEach { step ->
                Text(
                    step,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Quick action card (prominent, single-click to start)
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun QuickActionCard(
    activity: BreakActivity,
    color: Color,
    onClick: (BreakActivity) -> Unit,
) {
    GlassElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(activity) },
        padding = 18.dp,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(color.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    activity.icon,
                    style = MaterialTheme.typography.titleLarge,
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    activity.titleBn,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                )
                Text(
                    activity.descriptionBn,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.AccessTime,
                        contentDescription = null,
                        tint = color,
                        modifier = Modifier.size(14.dp),
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        "${activity.duration.toBengaliDigits()} সে.",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontFamily = EnglishFontFamily,
                        ),
                        color = color,
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "শুরু করুন ▶",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = color,
                    ),
                )
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Activity list item (in category sections)
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun ActivityListItem(
    activity: BreakActivity,
    color: Color,
    onClick: (BreakActivity) -> Unit,
) {
    GlassOutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(activity) },
        padding = 14.dp,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                activity.icon,
                style = MaterialTheme.typography.titleMedium,
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    activity.titleBn,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    activity.descriptionBn,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(100.dp))
                    .background(color.copy(alpha = 0.12f))
                    .padding(horizontal = 10.dp, vertical = 4.dp),
            ) {
                Text(
                    "${activity.duration.toBengaliDigits()} সে.",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontFamily = EnglishFontFamily,
                        fontWeight = FontWeight.Bold,
                    ),
                    color = color,
                )
            }
        }
    }
}