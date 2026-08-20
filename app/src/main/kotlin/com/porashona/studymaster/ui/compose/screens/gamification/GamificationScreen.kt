/*
package com.porashona.studymaster.ui.compose.screens.gamification

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
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
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.porashona.studymaster.ui.compose.components.AnimatedCounter
import com.porashona.studymaster.ui.compose.components.GlassCardVariant
import com.porashona.studymaster.ui.compose.components.GlassElevatedCard
import com.porashona.studymaster.ui.compose.components.GlassFilledCard
import com.porashona.studymaster.ui.compose.components.GlassmorphicCard
import com.porashona.studymaster.ui.compose.components.LocalGlassShapes
import com.porashona.studymaster.ui.compose.components.LocalMotion
import com.porashona.studymaster.ui.compose.components.XPProgressIndicator
import com.porashona.studymaster.ui.compose.theme.*
import kotlinx.coroutines.delay
import kotlin.math.min
import kotlin.math.roundToInt

// ═══════════════════════════════════════════════════════════════════════════════
// GamificationScreen — Full gamification dashboard with Bengali UI
// ═══════════════════════════════════════════════════════════════════════════════

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GamificationScreen() {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabTitles = listOf("ওভারভিউ", "ব্যাজ", "লিডারবোর্ড", "চ্যালেঞ্জ")

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "গেমিফিকেশন",
                        style = MaterialTheme.typography.titleLarge,
                    )
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.EmojiEvents,
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                            .padding(start = 16.dp),
                        tint = AchievementUnlocked,
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                ),
                scrollBehavior = scrollBehavior,
            )
        },
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            ScrollableTabRow(
                selectedTabIndex = selectedTabIndex,
                modifier = Modifier.fillMaxWidth(),
                edgePadding = 16.dp,
                indicator = { tabPositions ->
                    if (selectedTabIndex < tabPositions.size) {
                        TabRowDefaults.SecondaryIndicator(
                            modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                            color = Primary,
                        )
                    }
                },
                divider = {},
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface,
            ) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Medium,
                                ),
                            )
                        },
                        selectedContentColor = Primary,
                        unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }

            when (selectedTabIndex) {
                0 -> OverviewTab()
                1 -> BadgesTab()
                2 -> LeaderboardTab()
                3 -> ChallengesTab()
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// TAB 1: Overview
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun OverviewTab() {
    val mockLevel = 7
    val mockCurrentXp = 2450
    val mockRequiredXp = 3500
    val mockTotalXp = 18500
    val mockStudyHours = 42.5f
    val mockSessions = 128
    val mockStreak = 14

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item {
            LevelXPCard(
                level = mockLevel,
                currentXp = mockCurrentXp,
                requiredXp = mockRequiredXp,
                totalXp = mockTotalXp,
            )
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                QuickStatCard(
                    modifier = Modifier.weight(1f),
                    label = "মোট XP",
                    value = mockTotalXp,
                    icon = "⭐",
                    color = AchievementUnlocked,
                )
                QuickStatCard(
                    modifier = Modifier.weight(1f),
                    label = "পড়ার ঘণ্টা",
                    value = mockStudyHours.roundToInt(),
                    icon = "📖",
                    color = Primary,
                    decimalText = mockStudyHours.toBengaliDigits(1),
                )
                QuickStatCard(
                    modifier = Modifier.weight(1f),
                    label = "সেশন",
                    value = mockSessions,
                    icon = "🎯",
                    color = Chart3,
                )
            }
        }

        item {
            StreakCard(streakDays = mockStreak)
        }

        item {
            RecentXPGainsCard()
        }

        item { Spacer(modifier = Modifier.height(32.dp)) }
    }
}

// ─── Level + XP Card ─────────────────────────────────────────────────────────

@Composable
private fun LevelXPCard(
    level: Int,
    currentXp: Int,
    requiredXp: Int,
    totalXp: Int,
) {
    val motion = LocalMotion.current
    val shapes = LocalGlassShapes.current

    val levelRingProgress = remember(level, requiredXp, currentXp) {
        (currentXp.toFloat() / requiredXp.toFloat()).coerceIn(0f, 1f)
    }

    val animatedRingProgress by animateFloatAsState(
        targetValue = levelRingProgress,
        animationSpec = motion.progressFill,
        label = "levelRing",
    )

    GlassmorphicCard(
        modifier = Modifier.fillMaxWidth(),
        variant = GlassCardVariant.FILLED,
        tint = Primary.copy(alpha = 0.08f),
        cornerRadius = shapes.cardRadiusLarge,
        padding = 24.dp,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier.size(100.dp),
                contentAlignment = Alignment.Center,
            ) {
                Canvas(modifier = Modifier.matchParentSize()) {
                    val diameter = size.minDimension
                    val stroke = 6.dp.toPx()

                    drawArc(
                        color = MaterialTheme.colorScheme.surfaceVariant.toArgb(),
                        startAngle = -90f,
                        sweepAngle = 360f,
                        useCenter = false,
                        style = Stroke(width = stroke, cap = StrokeCap.Round),
                        size = Size(diameter, diameter),
                        topLeft = Offset(stroke / 2f, stroke / 2f),
                    )

                    drawArc(
                        brush = Brush.sweepGradient(
                            colors = listOf(Primary, PrimaryLight, AchievementUnlocked),
                        ),
                        startAngle = -90f,
                        sweepAngle = 360f * animatedRingProgress,
                        useCenter = false,
                        style = Stroke(width = stroke, cap = StrokeCap.Round),
                        size = Size(diameter, diameter),
                        topLeft = Offset(stroke / 2f, stroke / 2f),
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = level.toBengaliDigits(),
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontFamily = EnglishFontFamily,
                            fontWeight = FontWeight.Bold,
                            color = Primary,
                        ),
                    )
                    Text(
                        text = "লেভেল",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            XPProgressIndicator(
                currentXp = currentXp,
                requiredXp = requiredXp,
                level = level,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "মোট ${totalXp.toBengaliDigits()} XP অর্জন করেছেন",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

// ─── Quick Stat Card ─────────────────────────────────────────────────────────

@Composable
private fun QuickStatCard(
    modifier: Modifier = Modifier,
    label: String,
    value: Int,
    icon: String,
    color: Color,
    decimalText: String? = null,
) {
    GlassElevatedCard(
        modifier = modifier,
        padding = 14.dp,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            Text(text = icon)
            if (decimalText != null) {
                Text(
                    text = decimalText,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontFamily = EnglishFontFamily,
                        fontWeight = FontWeight.Bold,
                        color = color,
                    ),
                )
            } else {
                AnimatedCounter(
                    target = value,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontFamily = EnglishFontFamily,
                        fontWeight = FontWeight.Bold,
                        color = color,
                    ),
                )
            }
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

// ─── Streak Card ─────────────────────────────────────────────────────────────

@Composable
private fun StreakCard(streakDays: Int) {
    val isDark = MaterialTheme.colorScheme.isDark
    val isAtRisk = streakDays <= 1
    val streakColor = if (isAtRisk) StreakCold else StreakFire

    val infiniteTransition = rememberInfiniteTransition(label = "streakPulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = if (isAtRisk) 1f else 1.08f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "streakPulse",
    )

    GlassFilledCard(
        modifier = Modifier.fillMaxWidth(),
        tint = streakColor,
        padding = 20.dp,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Text(
                    text = "🔥",
                    style = MaterialTheme.typography.displayMedium,
                    modifier = Modifier.graphicsLayer {
                        scaleX = pulseScale
                        scaleY = pulseScale
                    },
                )
                Column {
                    Text(
                        text = "বর্তমান স্ট্রিক",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Row(
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        Text(
                            text = streakDays.toBengaliDigits(),
                            style = MaterialTheme.typography.headlineLarge.copy(
                                fontFamily = EnglishFontFamily,
                                fontWeight = FontWeight.Bold,
                                color = streakColor,
                            ),
                        )
                        Text(
                            text = "দিন",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(bottom = 4.dp),
                        )
                    }
                }
            }

            if (isAtRisk) {
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = Error.copy(alpha = 0.15f),
                ) {
                    Text(
                        text = "⚠️ ঝুঁকিতে!",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Error,
                        ),
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                    )
                }
            }
        }

        if (!isAtRisk && streakDays > 0) {
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                val dayLabels = listOf("সোম", "মঙ্গল", "বুধ", "বৃহ", "শুক্র", "শনি", "রবি")
                val currentDay = remember {
                    java.util.Calendar.getInstance()
                        .get(java.util.Calendar.DAY_OF_WEEK) - 1
                }
                dayLabels.forEachIndexed { index, label ->
                    val isCompleted = index <= min(currentDay, streakDays - 1)
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(
                                    if (isCompleted) streakColor
                                    else MaterialTheme.colorScheme.surfaceVariant
                                ),
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = label,
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 8.sp),
                            color = if (isCompleted) streakColor
                            else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                        )
                    }
                }
            }
        }
    }
}

// ─── Recent XP Gains ─────────────────────────────────────────────────────────

private data class XPGainEntry(
    val description: String,
    val xp: Int,
    val timeAgo: String,
    val icon: String,
)

private val mockXPGains = listOf(
    XPGainEntry("MCQ সম্পন্ন: গণিত অধ্যায় ৩", 25, "৫ মিনিট আগে", "📝"),
    XPGainEntry("পড়াশোনা সেশন: পদার্থবিজ্ঞান", 40, "৩০ মিনিট আগে", "📖"),
    XPGainEntry("মডেল টেস্ট সম্পন্ন", 100, "২ ঘণ্টা আগে", "🎯"),
    XPGainEntry("ফ্ল্যাশকার্ড: রসায়ন", 15, "৩ ঘণ্টা আগে", "🃏"),
    XPGainEntry("দৈনিক চ্যালেঞ্জ সম্পন্ন", 50, "৫ ঘণ্টা আগে", "🏆"),
    XPGainEntry("স্ট্রিক বোনাস", 30, "গতকাল", "🔥"),
    XPGainEntry("টাস্ক সম্পন্ন: নোটস", 20, "গতকাল", "✅"),
    XPGainEntry("পড়াশোনা সেশন: জীববিজ্ঞান", 40, "গতকাল", "📖"),
)

@Composable
private fun RecentXPGainsCard() {
    var showAll by remember { mutableStateOf(false) }
    val displayItems = if (showAll) mockXPGains else mockXPGains.take(5)

    GlassElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        padding = 20.dp,
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "সাম্প্রতিক XP অর্জন",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                if (mockXPGains.size > 5) {
                    TextButton(onClick = { showAll = !showAll }) {
                        Text(
                            text = if (showAll) "কম দেখুন" else "সব দেখুন",
                            style = MaterialTheme.typography.labelMedium,
                            color = Primary,
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            displayItems.forEachIndexed { index, entry ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(text = entry.icon, style = MaterialTheme.typography.bodyLarge)
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = entry.description,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                        Text(
                            text = entry.timeAgo,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                    Text(
                        text = "+${entry.xp.toBengaliDigits()}",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontFamily = EnglishFontFamily,
                            fontWeight = FontWeight.Bold,
                            color = XpGain,
                        ),
                    )
                }

                if (index < displayItems.lastIndex) {
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 2.dp),
                        color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f),
                    )
                }
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// TAB 2: Badges
// ═══════════════════════════════════════════════════════════════════════════════

private data class BadgeData(
    val id: String,
    val name: String,
    val description: String,
    val iconEmoji: String,
    val isEarned: Boolean,
    val earnedDate: String? = null,
    val progress: Float = 0f,
    val progressText: String = "",
    val xpReward: Int = 0,
)

private val allBadges = listOf(
    BadgeData(
        id = "streak_7",
        name = "৭ দিনের স্ট্রিক",
        description = "টানা ৭ দিন পড়াশোনা করুন।",
        iconEmoji = "🔥",
        isEarned = true,
        earnedDate = "২৫/০৫/২০২৫",
        xpReward = 100,
    ),
    BadgeData(
        id = "mcq_100",
        name = "১০০ MCQ সম্পন্ন",
        description = "মোট ১০০টি MCQ সম্পন্ন করুন।",
        iconEmoji = "📝",
        isEarned = true,
        earnedDate = "২০/০৫/২০২৫",
        xpReward = 150,
    ),
    BadgeData(
        id = "study_5h",
        name = "৫ ঘণ্টা পড়াশোনা",
        description = "একদিনে ৫ ঘণ্টা পড়াশোনা করুন।",
        iconEmoji = "📚",
        isEarned = true,
        earnedDate = "১৮/০৫/২০২৫",
        xpReward = 200,
    ),
    BadgeData(
        id = "first_test",
        name = "প্রথম মডেল টেস্ট",
        description = "প্রথম মডেল টেস্ট দিন।",
        iconEmoji = "🎯",
        isEarned = true,
        earnedDate = "১৫/০৪/২০২৫",
        xpReward = 75,
    ),
    BadgeData(
        id = "flashcard_master",
        name = "ফ্ল্যাশকার্ড মাস্টার",
        description = "৫০০টি ফ্ল্যাশকার্ড পর্যালোচনা করুন।",
        iconEmoji = "🃏",
        isEarned = false,
        progress = 0.62f,
        progressText = "৩১০/৫০০",
        xpReward = 250,
    ),
    BadgeData(
        id = "all_chapters",
        name = "সকল অধ্যায় সম্পন্ন",
        description = "কোনো একটি বিষয়ের সকল অধ্যায় সম্পন্ন করুন।",
        iconEmoji = "📖",
        isEarned = false,
        progress = 0.45f,
        progressText = "৯/২০ অধ্যায়",
        xpReward = 300,
    ),
    BadgeData(
        id = "night_warrior",
        name = "রাত্রিকালীন যোদ্ধা",
        description = "রাত ১২টার পরে পড়াশোনা সেশন সম্পন্ন করুন।",
        iconEmoji = "🌙",
        isEarned = false,
        progress = 0.3f,
        progressText = "৩/১০ সেশন",
        xpReward = 100,
    ),
    BadgeData(
        id = "weekly_champion",
        name = "সপ্তাহের চ্যাম্পিয়ন",
        description = "এক সপ্তাহে ৩০ ঘণ্টা পড়াশোনা করুন।",
        iconEmoji = "🏆",
        isEarned = false,
        progress = 0.78f,
        progressText = "২৩.৫/৩০ ঘণ্টা",
        xpReward = 500,
    ),
)

@Composable
private fun BadgesTab() {
    var selectedBadge by remember { mutableStateOf<BadgeData?>(null) }
    var showCelebration by remember { mutableStateOf(false) }

    val earnedBadges = allBadges.filter { it.isEarned }
    val lockedBadges = allBadges.filter { !it.isEarned }

    LaunchedEffect(Unit) {
        delay(1500)
        showCelebration = true
        delay(3000)
        showCelebration = false
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = AchievementUnlocked,
                        modifier = Modifier.size(18.dp),
                    )
                    Text(
                        text = "অর্জিত ব্যাজ (${earnedBadges.size.toBengaliDigits()})",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }

            if (earnedBadges.isEmpty()) {
                item {
                    Text(
                        text = "এখনো কোনো ব্যাজ অর্জন করেননি। পড়াশোনা শুরু করুন!",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(vertical = 16.dp),
                    )
                }
            }

            items(earnedBadges) { badge ->
                BadgeItem(
                    badge = badge,
                    onClick = { selectedBadge = badge },
                    animateIn = true,
                )
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = null,
                        tint = AchievementLocked,
                        modifier = Modifier.size(18.dp),
                    )
                    Text(
                        text = "অবরুদ্ধ ব্যাজ (${lockedBadges.size.toBengaliDigits()})",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }

            items(lockedBadges) { badge ->
                BadgeItem(
                    badge = badge,
                    onClick = { selectedBadge = badge },
                    animateIn = false,
                )
            }

            item { Spacer(modifier = Modifier.height(32.dp)) }
        }

        AnimatedVisibility(
            visible = showCelebration,
            enter = fadeIn(tween(300)) + scaleIn(initialScale = 0.8f, animationSpec = tween(400)),
            exit = fadeOut(tween(500)) + scaleOut(targetScale = 1.2f, animationSpec = tween(400)),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp),
        ) {
            BadgeCelebrationOverlay()
        }
    }

    selectedBadge?.let { badge ->
        BadgeDetailDialog(
            badge = badge,
            onDismiss = { selectedBadge = null },
        )
    }
}

@Composable
private fun BadgeItem(
    badge: BadgeData,
    onClick: () -> Unit,
    animateIn: Boolean,
) {
    val motion = LocalMotion.current
    val isDark = MaterialTheme.colorScheme.isDark

    val bgColor = if (badge.isEarned) {
        AchievementUnlocked.copy(alpha = if (isDark) 0.15f else 0.1f)
    } else {
        AchievementLocked.copy(alpha = if (isDark) 0.2f else 0.1f)
    }

    val borderColor = if (badge.isEarned) {
        AchievementUnlocked.copy(alpha = 0.4f)
    } else {
        MaterialTheme.colorScheme.outlineVariant
    }

    val scale by animateFloatAsState(
        targetValue = if (animateIn) 1f else 0.95f,
        animationSpec = if (animateIn) motion.xpGain else tween(0),
        label = "badgeScale",
    )

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer { scaleX = scale; scaleY = scale }
            .clickable { onClick() },
        shape = RoundedCornerShape(LocalGlassShapes.current.cardRadius),
        color = bgColor,
        border = androidx.compose.foundation.BorderStroke(1.dp, borderColor),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(
                        if (badge.isEarned) AchievementUnlocked.copy(alpha = 0.25f)
                        else AchievementLocked.copy(alpha = 0.3f),
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = if (badge.isEarned) badge.iconEmoji else "🔒",
                    style = MaterialTheme.typography.headlineSmall,
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = badge.name,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = if (badge.isEarned) AchievementUnlocked
                        else MaterialTheme.colorScheme.onSurfaceVariant,
                    ),
                )
                Text(
                    text = badge.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                if (!badge.isEarned && badge.progress > 0f) {
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(4.dp)
                                .clip(RoundedCornerShape(100.dp))
                                .background(MaterialTheme.colorScheme.surfaceVariant),
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(badge.progress)
                                    .height(4.dp)
                                    .clip(RoundedCornerShape(100.dp))
                                    .background(AchievementUnlocked),
                            )
                        }
                        Text(
                            text = badge.progressText,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontFamily = EnglishFontFamily,
                                fontWeight = FontWeight.Bold,
                                color = AchievementUnlocked,
                            ),
                        )
                    }
                }
            }

            Column(horizontalAlignment = Alignment.End) {
                if (badge.isEarned) {
                    Text(
                        text = badge.earnedDate ?: "",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Text(
                        text = "+${badge.xpReward.toBengaliDigits()} XP",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontFamily = EnglishFontFamily,
                            fontWeight = FontWeight.Bold,
                            color = XpGain,
                        ),
                    )
                } else {
                    Text(
                        text = "+${badge.xpReward.toBengaliDigits()} XP",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontFamily = EnglishFontFamily,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        ),
                    )
                }
            }
        }
    }
}

@Composable
private fun BadgeDetailDialog(
    badge: BadgeData,
    onDismiss: () -> Unit,
) {
    val isDark = MaterialTheme.colorScheme.isDark

    AlertDialog(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(LocalGlassShapes.current.dialogRadius),
        containerColor = if (isDark) GlassDarkAlpha60 else GlassLightAlpha90,
        titleContentColor = MaterialTheme.colorScheme.onSurface,
        textContentColor = MaterialTheme.colorScheme.onSurface,
        title = { Text(text = badge.name, style = MaterialTheme.typography.titleLarge, textAlign = TextAlign.Center) },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Box(
                    modifier = Modifier
                        .size(88.dp)
                        .clip(CircleShape)
                        .background(
                            if (badge.isEarned) AchievementUnlocked.copy(alpha = 0.2f)
                            else AchievementLocked.copy(alpha = 0.2f),
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = if (badge.isEarned) badge.iconEmoji else "🔒",
                        style = MaterialTheme.typography.displaySmall,
                    )
                }

                Text(
                    text = badge.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                )

                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = XpGain.copy(alpha = 0.15f),
                ) {
                    Text(
                        text = "পুরস্কার: +${badge.xpReward.toBengaliDigits()} XP",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontFamily = EnglishFontFamily,
                            fontWeight = FontWeight.Bold,
                            color = XpGain,
                        ),
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    )
                }

                if (!badge.isEarned) {
                    if (badge.progress > 0f) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ) {
                                Text(
                                    "অগ্রগতি",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                                Text(
                                    badge.progressText,
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        fontFamily = EnglishFontFamily,
                                        fontWeight = FontWeight.Bold,
                                        color = AchievementUnlocked,
                                    ),
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(8.dp)
                                    .clip(RoundedCornerShape(100.dp))
                                    .background(MaterialTheme.colorScheme.surfaceVariant),
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(badge.progress)
                                        .height(8.dp)
                                        .clip(RoundedCornerShape(100.dp))
                                        .background(AchievementUnlocked),
                                )
                            }
                            Text(
                                text = "${(badge.progress * 100).roundToInt().toBengaliDigits()}% সম্পন্ন",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontFamily = EnglishFontFamily,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                ),
                            )
                        }
                    } else {
                        Text(
                            text = "এখনো শুরু করেননি",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                } else {
                    Text(
                        text = "অর্জন তারিখ: ${badge.earnedDate ?: "অজানা"}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Success,
                    )
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("ঠিক আছে", fontWeight = FontWeight.SemiBold)
            }
        },
    )
}

@Composable
private fun BadgeCelebrationOverlay() {
    val infiniteTransition = rememberInfiniteTransition(label = "celebrationPulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.9f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "celebPulse",
    )

    Surface(
        shape = RoundedCornerShape(20.dp),
        color = AchievementUnlocked.copy(alpha = 0.2f),
        border = androidx.compose.foundation.BorderStroke(1.5.dp, AchievementUnlocked.copy(alpha = 0.5f)),
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Text(
                text = "🏆",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.graphicsLayer { scaleX = scale; scaleY = scale },
            )
            Column {
                Text(
                    text = "নতুন ব্যাজ অর্জন!",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = AchievementUnlocked,
                    ),
                )
                Text(
                    text = "৫ ঘণ্টা পড়াশোনা ব্যাজ পেয়েছেন!",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// TAB 3: Leaderboard
// ═══════════════════════════════════════════════════════════════════════════════

private data class LeaderboardEntry(
    val name: String,
    val xp: Int,
    val level: Int,
    val isCurrentUser: Boolean = false,
    val avatarEmoji: String,
)

private val mockLeaderboard = listOf(
    LeaderboardEntry("রাহুল আহমেদ", 28500, 12, false, "🧑\u200D🎓"),
    LeaderboardEntry("ফাতেমা খাতুন", 26200, 11, false, "👩\u200D🎓"),
    LeaderboardEntry("তানভীর ইসলাম", 22100, 10, false, "🧑\u200D💻"),
    LeaderboardEntry("আপনি", 18500, 7, true, "⭐"),
    LeaderboardEntry("নুসরাত জাহান", 15800, 8, false, "👩\u200D🏫"),
    LeaderboardEntry("সাকিব হাসান", 14200, 7, false, "🧑\u200D🔬"),
    LeaderboardEntry("মারিয়াম বেগম", 12500, 6, false, "👩\u200D⚕️"),
    LeaderboardEntry("আরিফ রহমান", 11800, 6, false, "🧑\u200D🔧"),
    LeaderboardEntry("তাসনিম আক্তার", 10200, 5, false, "👩\u200D🎨"),
    LeaderboardEntry("ইমরান হোসেন", 8900, 5, false, "🧑\u200D🍳"),
)

@Composable
private fun LeaderboardTab() {
    val isDark = MaterialTheme.colorScheme.isDark

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        item {
            TopThreePodium(entries = mockLeaderboard.take(3))
        }

        items(mockLeaderboard.drop(3)) { entry ->
            val rank = mockLeaderboard.indexOf(entry) + 1

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(LocalGlassShapes.current.cardRadiusSmall),
                color = if (entry.isCurrentUser) {
                    Primary.copy(alpha = if (isDark) 0.2f else 0.12f)
                } else {
                    MaterialTheme.colorScheme.surface.copy(alpha = 0.6f)
                },
                border = if (entry.isCurrentUser) {
                    androidx.compose.foundation.BorderStroke(1.5.dp, Primary.copy(alpha = 0.5f))
                } else null,
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = rank.toBengaliDigits(),
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontFamily = EnglishFontFamily,
                            fontWeight = FontWeight.Bold,
                            color = if (entry.isCurrentUser) Primary
                            else MaterialTheme.colorScheme.onSurfaceVariant,
                        ),
                        modifier = Modifier.width(32.dp),
                        textAlign = TextAlign.Center,
                    )

                    Text(
                        text = entry.avatarEmoji,
                        style = MaterialTheme.typography.titleMedium,
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = entry.name,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = if (entry.isCurrentUser) FontWeight.Bold else FontWeight.Normal,
                                color = if (entry.isCurrentUser) Primary
                                else MaterialTheme.colorScheme.onSurface,
                            ),
                        )
                        Text(
                            text = "লেভেল ${entry.level.toBengaliDigits()}",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontFamily = EnglishFontFamily,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            ),
                        )
                    }

                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = entry.xp.toBengaliDigits(),
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontFamily = EnglishFontFamily,
                                fontWeight = FontWeight.Bold,
                                color = AchievementUnlocked,
                            ),
                        )
                        Text(
                            text = "XP",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedButton(
                onClick = { /* invite action */ },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(LocalGlassShapes.current.buttonRadius),
                border = androidx.compose.foundation.BorderStroke(1.dp, Primary.copy(alpha = 0.5f)),
            ) {
                Icon(
                    imageVector = Icons.Default.PersonAdd,
                    contentDescription = null,
                    tint = Primary,
                    modifier = Modifier.size(18.dp),
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "ব্যাচমেটদের আমন্ত্রণ জানান",
                    style = MaterialTheme.typography.labelLarge,
                    color = Primary,
                )
            }
        }

        item { Spacer(modifier = Modifier.height(32.dp)) }
    }
}

@Composable
private fun TopThreePodium(entries: List<LeaderboardEntry>) {
    if (entries.size < 3) return

    val podiumOrder = listOf(entries[1], entries[0], entries[2])
    val heights = listOf(120.dp, 150.dp, 100.dp)
    val medalColors = listOf(Color(0xFFC0C0C0), AchievementUnlocked, Color(0xFFCD7F32))
    val medalLabels = listOf("২য়", "১ম", "৩য়")

    GlassElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        padding = 20.dp,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom,
        ) {
            podiumOrder.forEachIndexed { index, entry ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = entry.avatarEmoji,
                            style = MaterialTheme.typography.headlineSmall,
                        )
                        Text(
                            text = entry.name.split(" ").first(),
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                            ),
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                        Text(
                            text = "${entry.xp.toBengaliDigits()} XP",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontFamily = EnglishFontFamily,
                                color = medalColors[index],
                                fontWeight = FontWeight.Bold,
                            ),
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Surface(
                        modifier = Modifier.width(80.dp),
                        shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
                        color = medalColors[index].copy(alpha = 0.2f),
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(heights[index])
                                .padding(top = 10.dp),
                            contentAlignment = Alignment.TopCenter,
                        ) {
                            Text(
                                text = medalLabels[index],
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = medalColors[index],
                                ),
                            )
                        }
                    }
                }
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// TAB 4: Challenges
// ═══════════════════════════════════════════════════════════════════════════════

private enum class ChallengeType(val bengaliLabel: String, val iconEmoji: String) {
    MCQ_COUNT("MCQ সম্পন্ন", "📝"),
    STUDY_MINUTES("পড়ার মিনিট", "📖"),
    FLASHCARD_COUNT("ফ্ল্যাশকার্ড", "🃏"),
    STREAK_DAY("স্ট্রিক দিন", "🔥"),
    TASK_COMPLETE("টাস্ক সম্পন্ন", "✅"),
}

private data class Challenge(
    val id: String,
    val title: String,
    val description: String,
    val type: ChallengeType,
    val targetValue: Int,
    val currentValue: Int,
    val xpReward: Int,
    val isDaily: Boolean = true,
    val isCompleted: Boolean = false,
    val completedDate: String? = null,
)

private val mockActiveChallenges = listOf(
    Challenge(
        id = "daily_1",
        title = "আজকের MCQ চ্যালেঞ্জ",
        description = "আজ ২০টি MCQ সম্পন্ন করুন।",
        type = ChallengeType.MCQ_COUNT,
        targetValue = 20,
        currentValue = 14,
        xpReward = 50,
        isDaily = true,
    ),
    Challenge(
        id = "daily_2",
        title = "পড়াশোনার লক্ষ্য",
        description = "আজ কমপক্ষে ৯০ মিনিট পড়ুন।",
        type = ChallengeType.STUDY_MINUTES,
        targetValue = 90,
        currentValue = 65,
        xpReward = 40,
        isDaily = true,
    ),
    Challenge(
        id = "daily_3",
        title = "ফ্ল্যাশকার্ড রিভিশন",
        description = "আজ ৩০টি ফ্ল্যাশকার্ড পর্যালোচনা করুন।",
        type = ChallengeType.FLASHCARD_COUNT,
        targetValue = 30,
        currentValue = 30,
        xpReward = 35,
        isDaily = true,
        isCompleted = true,
    ),
)

private val mockChallengeHistory = listOf(
    Challenge(
        id = "hist_1",
        title = "সপ্তাহের স্ট্রিক",
        description = "টানা ৭ দিন পড়াশোনা করুন।",
        type = ChallengeType.STREAK_DAY,
        targetValue = 7,
        currentValue = 7,
        xpReward = 100,
        isDaily = false,
        isCompleted = true,
        completedDate = "২৫/০৫/২০২৫",
    ),
    Challenge(
        id = "hist_2",
        title = "MCQ মাস্টার",
        description = "এক সপ্তাহে ১০০টি MCQ সম্পন্ন করুন।",
        type = ChallengeType.MCQ_COUNT,
        targetValue = 100,
        currentValue = 100,
        xpReward = 200,
        isDaily = false,
        isCompleted = true,
        completedDate = "২৩/০৫/২০২৫",
    ),
    Challenge(
        id = "hist_3",
        title = "ফ্ল্যাশকার্ড ম্যারাথন",
        description = "এক সেশনে ৫০টি ফ্ল্যাশকার্ড পর্যালোচনা করুন।",
        type = ChallengeType.FLASHCARD_COUNT,
        targetValue = 50,
        currentValue = 50,
        xpReward = 75,
        isDaily = false,
        isCompleted = true,
        completedDate = "২০/০৫/২০২৫",
    ),
    Challenge(
        id = "hist_4",
        title = "টাস্ক ক্লিনজার",
        description = "দিনে ৫টি টাস্ক সম্পন্ন করুন।",
        type = ChallengeType.TASK_COMPLETE,
        targetValue = 5,
        currentValue = 5,
        xpReward = 60,
        isDaily = true,
        isCompleted = true,
        completedDate = "১৯/০৫/২০২৫",
    ),
    Challenge(
        id = "hist_5",
        title = "পড়ার ম্যারাথন",
        description = "এক দিনে ৩ ঘণ্টা পড়ুন।",
        type = ChallengeType.STUDY_MINUTES,
        targetValue = 180,
        currentValue = 180,
        xpReward = 150,
        isDaily = false,
        isCompleted = true,
        completedDate = "১৭/০৫/২০২৫",
    ),
)

@Composable
private fun ChallengesTab() {
    var showCelebration by remember { mutableStateOf(false) }
    var celebratedChallenge by remember { mutableStateOf<Challenge?>(null) }

    LaunchedEffect(Unit) {
        delay(800)
        celebratedChallenge = mockActiveChallenges.find { it.isCompleted }
        if (celebratedChallenge != null) {
            showCelebration = true
            delay(4000)
            showCelebration = false
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            item {
                Text(
                    text = "সক্রিয় চ্যালেঞ্জ",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }

            items(mockActiveChallenges) { challenge ->
                ActiveChallengeCard(challenge = challenge)
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "চ্যালেঞ্জ ইতিহাস",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }

            items(mockChallengeHistory) { challenge ->
                ChallengeHistoryItem(challenge = challenge)
            }

            item { Spacer(modifier = Modifier.height(32.dp)) }
        }

        AnimatedVisibility(
            visible = showCelebration && celebratedChallenge != null,
            enter = fadeIn(tween(300)) + scaleIn(
                initialScale = 0.5f,
                animationSpec = spring(dampingRatio = 0.4f, stiffness = 200f),
            ),
            exit = fadeOut(tween(800)) + scaleOut(
                targetScale = 1.3f,
                animationSpec = tween(800),
            ),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 48.dp),
        ) {
            ChallengeCelebrationOverlay(challenge = celebratedChallenge!!)
        }
    }
}

@Composable
private fun ActiveChallengeCard(challenge: Challenge) {
    val isDark = MaterialTheme.colorScheme.isDark
    val progress = remember(challenge.currentValue, challenge.targetValue) {
        (challenge.currentValue.toFloat() / challenge.targetValue.toFloat()).coerceIn(0f, 1f)
    }

    val cardColor = if (challenge.isCompleted) {
        Success.copy(alpha = if (isDark) 0.15f else 0.1f)
    } else {
        Primary.copy(alpha = if (isDark) 0.12f else 0.08f)
    }

    val borderColor = if (challenge.isCompleted) {
        Success.copy(alpha = 0.4f)
    } else {
        Primary.copy(alpha = 0.3f)
    }

    val accentColor = if (challenge.isCompleted) Success else Primary

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(LocalGlassShapes.current.cardRadius),
        color = cardColor,
        border = androidx.compose.foundation.BorderStroke(1.dp, borderColor),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text(text = challenge.type.iconEmoji, style = MaterialTheme.typography.titleMedium)
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = challenge.title,
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface,
                            ),
                        )
                        Text(
                            text = challenge.description,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }

                if (challenge.isCompleted) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Success.copy(alpha = 0.2f),
                    ) {
                        Text(
                            text = "✅ সম্পন্ন",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = Success,
                            ),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        )
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(10.dp)
                        .clip(RoundedCornerShape(100.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                ) {
                    val animatedProgress by animateFloatAsState(
                        targetValue = progress,
                        animationSpec = LocalMotion.current.progressFill,
                        label = "challengeProgress",
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(animatedProgress)
                            .height(10.dp)
                            .clip(RoundedCornerShape(100.dp))
                            .background(
                                Brush.horizontalGradient(
                                    colors = listOf(
                                        accentColor,
                                        accentColor.copy(red = 0.8f, green = 0.9f, blue = 1f),
                                    ),
                                )
                            ),
                    )
                }

                Text(
                    text = "${challenge.currentValue.toBengaliDigits()}/${challenge.targetValue.toBengaliDigits()}",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontFamily = EnglishFontFamily,
                        fontWeight = FontWeight.Bold,
                        color = accentColor,
                    ),
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                ) {
                    Text(
                        text = challenge.type.bengaliLabel,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                    )
                }

                Text(
                    text = "পুরস্কার: +${challenge.xpReward.toBengaliDigits()} XP",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontFamily = EnglishFontFamily,
                        fontWeight = FontWeight.Bold,
                        color = if (challenge.isCompleted) XpGain
                        else MaterialTheme.colorScheme.onSurfaceVariant,
                    ),
                )
            }
        }
    }
}

@Composable
private fun ChallengeHistoryItem(challenge: Challenge) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(LocalGlassShapes.current.cardRadiusSmall),
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.4f),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = challenge.type.iconEmoji, style = MaterialTheme.typography.bodyLarge)

            Spacer(modifier = Modifier.width(10.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = challenge.title,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface,
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = "${challenge.type.bengaliLabel} • ${challenge.completedDate ?: ""}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }

            Text(
                text = "+${challenge.xpReward.toBengaliDigits()}",
                style = MaterialTheme.typography.labelMedium.copy(
                    fontFamily = EnglishFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = XpGain,
                ),
            )
        }
    }
}

@Composable
private fun ChallengeCelebrationOverlay(challenge: Challenge) {
    val infiniteTransition = rememberInfiniteTransition(label = "challengePulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "challengePulse",
    )

    Surface(
        shape = RoundedCornerShape(24.dp),
        color = Success.copy(alpha = 0.2f),
        border = androidx.compose.foundation.BorderStroke(1.5.dp, Success.copy(alpha = 0.5f)),
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "🎉", style = MaterialTheme.typography.headlineMedium)
                Text(text = "🎊", style = MaterialTheme.typography.headlineMedium)
                Text(text = "🏆", style = MaterialTheme.typography.headlineMedium)
            }
            Text(
                text = "চ্যালেঞ্জ সম্পন্ন!",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Success,
                ),
                modifier = Modifier.graphicsLayer { scaleX = scale; scaleY = scale },
            )
            Text(
                text = challenge.title,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Text(
                text = "+${challenge.xpReward.toBengaliDigits()} XP অর্জিত!",
                style = MaterialTheme.typography.labelLarge.copy(
                    fontFamily = EnglishFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = XpGain,
                ),
            )
        }
    }
}
*/