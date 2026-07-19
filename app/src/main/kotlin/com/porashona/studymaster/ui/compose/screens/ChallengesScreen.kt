package com.porashona.studymaster.ui.compose.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.porashona.studymaster.data.model.Challenge
import com.porashona.studymaster.data.model.ChallengeType
import com.porashona.studymaster.data.model.DailyChallenges
import com.porashona.studymaster.ui.compose.components.GlassElevatedCard
import com.porashona.studymaster.ui.compose.components.GlassFilledCard
import com.porashona.studymaster.ui.compose.components.GlassOutlinedCard
import com.porashona.studymaster.ui.compose.theme.AchievementUnlocked
import com.porashona.studymaster.ui.compose.theme.Chart1
import com.porashona.studymaster.ui.compose.theme.Chart2
import com.porashona.studymaster.ui.compose.theme.Chart3
import com.porashona.studymaster.ui.compose.theme.Chart4
import com.porashona.studymaster.ui.compose.theme.EnglishFontFamily
import com.porashona.studymaster.ui.compose.theme.Error
import com.porashona.studymaster.ui.compose.theme.Primary
import com.porashona.studymaster.ui.compose.theme.StreakFire
import com.porashona.studymaster.ui.compose.theme.Success
import com.porashona.studymaster.ui.compose.theme.TimerWork
import com.porashona.studymaster.ui.compose.theme.XpBarBg
import com.porashona.studymaster.ui.compose.theme.toBengaliDigits
import kotlin.math.min

// ═══════════════════════════════════════════════════════════════════════════════
// ChallengesScreen — Daily challenges & history with Bengali UI
// ═══════════════════════════════════════════════════════════════════════════════

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChallengesScreen() {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    // ── Mock data ──────────────────────────────────────────────────────────────
    val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

    val todayChallenges = remember {
        DailyChallenges.generateForDate(today).mapIndexed { index, challenge ->
            when (index) {
                0 -> challenge.copy(currentValue = 120) // 120/180 minutes
                1 -> challenge.copy(currentValue = 3, isCompleted = true, completedAt = System.currentTimeMillis() - 3600000) // 3/5 done
                2 -> challenge.copy(currentValue = 1) // 1/2 subjects
                3 -> challenge.copy(currentValue = 7) // started at 7 AM
                4 -> challenge.copy(currentValue = 0) // not started
                else -> challenge
            }
        }
    }

    val historyChallenges = remember {
        listOf(
            Challenge(
                id = "hist_1", title = "৫ ঘণ্টা পড়াশোনা", titleBn = "৫ ঘণ্টা পড়াশোনা",
                description = "৫ ঘণ্টা অধ্যয়ন করুন", descriptionBn = "৫ ঘণ্টা অধ্যয়ন করুন",
                type = ChallengeType.STUDY_HOURS, targetValue = 300, currentValue = 300,
                xpReward = 200, isCompleted = true, isActive = false, date = "2025-01-10",
                completedAt = System.currentTimeMillis() - 86400000L * 3,
            ),
            Challenge(
                id = "hist_2", title = "৭ পোমোডোরো", titleBn = "৭টি পোমোডোরো",
                description = "৭টি পোমোডোরো সম্পন্ন করুন", descriptionBn = "৭টি পোমোডোরো সম্পন্ন করুন",
                type = ChallengeType.POMODORO_COUNT, targetValue = 7, currentValue = 7,
                xpReward = 100, isCompleted = true, isActive = false, date = "2025-01-10",
                completedAt = System.currentTimeMillis() - 86400000L * 3,
            ),
            Challenge(
                id = "hist_3", title = "সকালের পাখি", titleBn = "সকালের পাখি",
                description = "সকাল ৭টার আগে শুরু করুন", descriptionBn = "সকাল ৭টার আগে শুরু করুন",
                type = ChallengeType.EARLY_START, targetValue = 7, currentValue = 7,
                xpReward = 80, isCompleted = true, isActive = false, date = "2025-01-09",
                completedAt = System.currentTimeMillis() - 86400000L * 4,
            ),
            Challenge(
                id = "hist_4", title = "গভীর মনোযোগ", titleBn = "গভীর মনোযোগ",
                description = "৬০ মিনিট বিরতি ছাড়া পড়ুন", descriptionBn = "৬০ মিনিট বিরতি ছাড়া পড়ুন",
                type = ChallengeType.NO_BREAK, targetValue = 60, currentValue = 45,
                xpReward = 90, isCompleted = false, isActive = false, date = "2025-01-08",
            ),
            Challenge(
                id = "hist_5", title = "৩ বিষয় পড়ুন", titleBn = "৩টি বিষয় পড়ুন",
                description = "৩টি ভিন্ন বিষয় পড়ুন", descriptionBn = "৩টি ভিন্ন বিষয় পড়ুন",
                type = ChallengeType.SUBJECT_COUNT, targetValue = 3, currentValue = 3,
                xpReward = 75, isCompleted = true, isActive = false, date = "2025-01-08",
                completedAt = System.currentTimeMillis() - 86400000L * 4,
            ),
            Challenge(
                id = "hist_6", title = "স্ট্রিক ধরে রাখুন", titleBn = "স্ট্রিক ধরে রাখুন",
                description = "৫ দিন ধারাবাহিক পড়াশোনা", descriptionBn = "৫ দিন ধারাবাহিক পড়াশোনা",
                type = ChallengeType.STREAK, targetValue = 5, currentValue = 5,
                xpReward = 150, isCompleted = true, isActive = false, date = "2025-01-07",
                completedAt = System.currentTimeMillis() - 86400000L * 5,
            ),
        )
    }

    val activeChallenges = todayChallenges.filter { !it.isCompleted }
    val completedToday = todayChallenges.filter { it.isCompleted }
    val totalXpAvailable = todayChallenges.sumOf { it.xpReward }
    val totalXpEarnedToday = completedToday.sumOf { it.xpReward }

    // ── Scaffold ───────────────────────────────────────────────────────────────
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "চ্যালেঞ্জ",
                        style = MaterialTheme.typography.titleLarge,
                    )
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.Bolt,
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                            .padding(start = 16.dp),
                        tint = StreakFire,
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                ),
                scrollBehavior = scrollBehavior,
            )
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {

            // ══════════════════════════════════════════════════════════════════
            // SECTION: Daily summary header
            // ══════════════════════════════════════════════════════════════════
            item {
                GlassElevatedCard(
                    modifier = Modifier.fillMaxWidth(),
                    padding = 20.dp,
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Column {
                            Text(
                                "আজকের চ্যালেঞ্জ",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                "${completedToday.size.toBengaliDigits()}/${todayChallenges.size.toBengaliDigits()} সম্পন্ন",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                "+${totalXpEarnedToday.toBengaliDigits()} XP",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontFamily = EnglishFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    color = Primary,
                                ),
                            )
                            Text(
                                "মোট ${totalXpAvailable.toBengaliDigits()} XP",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Overall progress bar
                    val overallProgress = if (todayChallenges.isNotEmpty()) {
                        todayChallenges.count { it.isCompleted }.toFloat() / todayChallenges.size
                    } else 0f

                    val animatedOverall by animateFloatAsState(
                        targetValue = overallProgress,
                        animationSpec = tween(800, easing = FastOutSlowInEasing),
                        label = "overall",
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(100.dp))
                            .background(XpBarBg),
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(animatedOverall)
                                .height(8.dp)
                                .clip(RoundedCornerShape(100.dp))
                                .background(
                                    Brush.horizontalGradient(
                                        colors = listOf(Primary, Secondary),
                                    )
                                ),
                        )
                    }
                }
            }

            // ══════════════════════════════════════════════════════════════════
            // SECTION: Active Daily Challenges (prominent card)
            // ══════════════════════════════════════════════════════════════════
            if (activeChallenges.isNotEmpty()) {
                item {
                    Text(
                        "সক্রিয় চ্যালেঞ্জ",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    )
                }

                items(activeChallenges, key = { it.id }) { challenge ->
                    ActiveChallengeCard(challenge = challenge)
                }
            }

            // ══════════════════════════════════════════════════════════════════
            // SECTION: Completed today
            // ══════════════════════════════════════════════════════════════════
            if (completedToday.isNotEmpty()) {
                item {
                    Text(
                        "আজ সম্পন্ন ✅",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    )
                }

                items(completedToday, key = { it.id }) { challenge ->
                    CompletedChallengeCard(challenge = challenge)
                }
            }

            // ══════════════════════════════════════════════════════════════════
            // SECTION: Challenge history
            // ══════════════════════════════════════════════════════════════════
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "ইতিহাস",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                )
            }

            items(historyChallenges, key = { it.id }) { challenge ->
                HistoryChallengeCard(challenge = challenge)
            }

            // ══════════════════════════════════════════════════════════════════
            // SECTION: Challenge types legend
            // ══════════════════════════════════════════════════════════════════
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "চ্যালেঞ্জের ধরন",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                )
            }

            item {
                val challengeTypes = listOf(
                    Triple(ChallengeType.STUDY_HOURS, "অধ্যয়ন ঘণ্টা", "⏰", Primary),
                    Triple(ChallengeType.POMODORO_COUNT, "পোমোডোরো", "🍅", TimerWork),
                    Triple(ChallengeType.SUBJECT_COUNT, "বিষয় সংখ্যা", "📚", Chart2),
                    Triple(ChallengeType.EARLY_START, "সকালে শুরু", "🌅", AchievementUnlocked),
                    Triple(ChallengeType.NO_BREAK, "গভীর মনোযোগ", "🧠", Chart4),
                    Triple(ChallengeType.STREAK, "স্ট্রিক", "🔥", StreakFire),
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    challengeTypes.forEach { (_, label, emoji, color) ->
                        GlassOutlinedCard(
                            modifier = Modifier.weight(1f),
                            padding = 10.dp,
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                Text(emoji, style = MaterialTheme.typography.titleMedium)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    label,
                                    style = MaterialTheme.typography.labelSmall,
                                    textAlign = TextAlign.Center,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    color = color,
                                )
                            }
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(32.dp)) }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Active challenge card (prominent, with circular progress)
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun ActiveChallengeCard(challenge: Challenge) {
    val progress = if (challenge.targetValue > 0) {
        min(challenge.currentValue.toFloat() / challenge.targetValue, 1f)
    } else 0f

    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(800, easing = FastOutSlowInEasing),
        label = "activeProgress",
    )

    val (typeIcon, typeLabel, typeColor) = getChallengeTypeData(challenge.type)

    GlassElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        padding = 20.dp,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Circular progress
            Box(
                modifier = Modifier.size(64.dp),
                contentAlignment = Alignment.Center,
            ) {
                Canvas(modifier = Modifier.size(64.dp)) {
                    drawArc(
                        color = XpBarBg,
                        startAngle = -90f,
                        sweepAngle = 360f,
                        useCenter = false,
                        style = Stroke(width = 5.dp.toPx(), cap = StrokeCap.Round),
                    )
                    if (progress > 0f) {
                        drawArc(
                            color = typeColor,
                            startAngle = -90f,
                            sweepAngle = 360f * animatedProgress,
                            useCenter = false,
                            style = Stroke(width = 5.dp.toPx(), cap = StrokeCap.Round),
                        )
                    }
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        typeIcon,
                        style = MaterialTheme.typography.titleSmall,
                    )
                    Text(
                        "${(progress * 100).toInt().toBengaliDigits()}%",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontFamily = EnglishFontFamily,
                            fontWeight = FontWeight.Bold,
                        ),
                        color = typeColor,
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Challenge info
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        challenge.titleBn,
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f),
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    challenge.descriptionBn,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Progress bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(100.dp))
                        .background(XpBarBg),
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(animatedProgress)
                            .height(6.dp)
                            .clip(RoundedCornerShape(100.dp))
                            .background(typeColor),
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(100.dp))
                            .background(typeColor.copy(alpha = 0.12f))
                            .padding(horizontal = 8.dp, vertical = 2.dp),
                    ) {
                        Text(
                            typeLabel,
                            style = MaterialTheme.typography.labelSmall,
                            color = typeColor,
                        )
                    }
                    Text(
                        "+${challenge.xpReward.toBengaliDigits()} XP",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontFamily = EnglishFontFamily,
                            fontWeight = FontWeight.Bold,
                        ),
                        color = Primary,
                    )
                }
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Completed challenge card (compact, muted)
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun CompletedChallengeCard(challenge: Challenge) {
    val (_, _, typeColor) = getChallengeTypeData(challenge.type)

    GlassFilledCard(
        modifier = Modifier.fillMaxWidth(),
        tint = Success,
        padding = 16.dp,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = null,
                tint = Success,
                modifier = Modifier.size(28.dp),
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    challenge.titleBn,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Medium,
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    "সম্পন্ন  •  +${challenge.xpReward.toBengaliDigits()} XP অর্জিত",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            Text("🎉", style = MaterialTheme.typography.titleMedium)
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// History challenge card
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun HistoryChallengeCard(challenge: Challenge) {
    val (_, _, typeColor) = getChallengeTypeData(challenge.type)

    GlassOutlinedCard(
        modifier = Modifier.fillMaxWidth(),
        padding = 16.dp,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Status icon
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(
                        if (challenge.isCompleted) Success.copy(alpha = 0.15f)
                        else Error.copy(alpha = 0.15f)
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = if (challenge.isCompleted) Icons.Default.CheckCircle else Icons.Default.Schedule,
                    contentDescription = null,
                    tint = if (challenge.isCompleted) Success else Error,
                    modifier = Modifier.size(20.dp),
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    challenge.titleBn,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = if (challenge.isCompleted) FontWeight.Medium else FontWeight.Normal,
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Row {
                    Text(
                        formatDateShort(challenge.date),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        if (challenge.isCompleted) "+${challenge.xpReward.toBengaliDigits()} XP"
                        else "অসম্পন্ন",
                        style = MaterialTheme.typography.bodySmall,
                        color = if (challenge.isCompleted) Primary else Error,
                        fontFamily = if (challenge.isCompleted) EnglishFontFamily else MaterialTheme.typography.bodySmall.fontFamily,
                    )
                }
            }

            // XP badge
            if (challenge.isCompleted) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(100.dp))
                        .background(Primary.copy(alpha = 0.12f))
                        .padding(horizontal = 8.dp, vertical = 3.dp),
                ) {
                    Text(
                        "+${challenge.xpReward.toBengaliDigits()}",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontFamily = EnglishFontFamily,
                            fontWeight = FontWeight.Bold,
                        ),
                        color = Primary,
                    )
                }
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Helper: Map challenge type to icon, label, color
// ═══════════════════════════════════════════════════════════════════════════════

private fun getChallengeTypeData(type: ChallengeType): Triple<String, String, Color> {
    return when (type) {
        ChallengeType.STUDY_HOURS -> Triple("⏰", "অধ্যয়ন", Primary)
        ChallengeType.POMODORO_COUNT -> Triple("🍅", "পোমোডোরো", TimerWork)
        ChallengeType.SUBJECT_COUNT -> Triple("📚", "বিষয়", Chart2)
        ChallengeType.EARLY_START -> Triple("🌅", "সকাল", AchievementUnlocked)
        ChallengeType.NO_BREAK -> Triple("🧠", "মনোযোগ", Chart4)
        ChallengeType.STREAK -> Triple("🔥", "স্ট্রিক", StreakFire)
        ChallengeType.CUSTOM -> Triple("⭐", "কাস্টম", Chart3)
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Helper: Format date from string
// ═══════════════════════════════════════════════════════════════════════════════

private fun formatDateShort(dateStr: String): String {
    return try {
        val parts = dateStr.split("-")
        if (parts.size == 3) {
            "${parts[2].toInt().toBengaliDigits()} ${getBengaliMonth(parts[1].toInt())}"
        } else dateStr
    } catch (_: Exception) {
        dateStr
    }
}

private fun getBengaliMonth(month: Int): String {
    return when (month) {
        1 -> "জানু"
        2 -> "ফেব্রু"
        3 -> "মার্চ"
        4 -> "এপ্রিল"
        5 -> "মে"
        6 -> "জুন"
        7 -> "জুলা"
        8 -> "আগ"
        9 -> "সেপ্টে"
        10 -> "অক্টো"
        11 -> "নভে"
        12 -> "ডিসে"
        else -> ""
    }
}