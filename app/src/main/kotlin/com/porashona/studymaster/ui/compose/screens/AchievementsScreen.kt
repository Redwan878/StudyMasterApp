
package com.porashona.studymaster.ui.compose.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.porashona.studymaster.data.model.Achievement
import com.porashona.studymaster.ui.compose.components.GlassElevatedCard
import com.porashona.studymaster.ui.compose.components.GlassFilledCard
import com.porashona.studymaster.ui.compose.theme.AchievementLocked
import com.porashona.studymaster.ui.compose.theme.AchievementUnlocked
import com.porashona.studymaster.ui.compose.theme.EnglishFontFamily
import com.porashona.studymaster.ui.compose.theme.Primary
import com.porashona.studymaster.ui.compose.theme.Success
import com.porashona.studymaster.ui.compose.theme.XpBarBg
import com.porashona.studymaster.ui.compose.theme.toBengaliDigits
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.min

// Local wrapper that adds a UI-only category tag to the Room Achievement entity
private data class CategorizedAchievement(
    val achievement: Achievement,
    val category: String,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AchievementsScreen() {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val categoryTabs = listOf("সব", "পড়াশোনা", "টাইমার", "কাজ", "পরীক্ষা", "সামাজিক")

    // Mock achievement data with categories
    val allAchievements = remember {
        listOf(
            CategorizedAchievement(
                Achievement(id = "study_1", title = "প্রথম পদক্ষেপ", description = "প্রথম স্টাডি সেশন সম্পন্ন করুন",
                    iconName = "📚", xpReward = 25, isUnlocked = true,
                    unlockedAt = System.currentTimeMillis() - 86400000L * 30,
                    progress = 1, targetProgress = 1), "study"),
            CategorizedAchievement(
                Achievement(id = "study_2", title = "১০ ঘণ্টা অধ্যয়ন", description = "মোট ১০ ঘণ্টা পড়াশোনা করুন",
                    iconName = "⏰", xpReward = 50, isUnlocked = true,
                    unlockedAt = System.currentTimeMillis() - 86400000L * 20,
                    progress = 10, targetProgress = 10), "study"),
            CategorizedAchievement(
                Achievement(id = "study_3", title = "১০০ ঘণ্টা অধ্যয়ন", description = "মোট ১০০ ঘণ্টা পড়াশোনা করুন",
                    iconName = "🎓", xpReward = 500, isUnlocked = false,
                    progress = 42, targetProgress = 100), "study"),
            CategorizedAchievement(
                Achievement(id = "study_4", title = "৫০০ ঘণ্টা অধ্যয়ন", description = "মোট ৫০০ ঘণ্টা পড়াশোনা করুন",
                    iconName = "🏆", xpReward = 2000, isUnlocked = false,
                    progress = 42, targetProgress = 500), "study"),
            CategorizedAchievement(
                Achievement(id = "timer_1", title = "পোমোডোরো শুরু", description = "প্রথম পোমোডোরো সেশন সম্পন্ন করুন",
                    iconName = "🍅", xpReward = 30, isUnlocked = true,
                    unlockedAt = System.currentTimeMillis() - 86400000L * 28,
                    progress = 1, targetProgress = 1), "timer"),
            CategorizedAchievement(
                Achievement(id = "timer_2", title = "১০ পোমোডোরো", description = "১০টি পোমোডোরো সেশন সম্পন্ন করুন",
                    iconName = "🔥", xpReward = 75, isUnlocked = true,
                    unlockedAt = System.currentTimeMillis() - 86400000L * 15,
                    progress = 10, targetProgress = 10), "timer"),
            CategorizedAchievement(
                Achievement(id = "timer_3", title = "১০০ পোমোডোরো", description = "১০০টি পোমোডোরো সেশন সম্পন্ন করুন",
                    iconName = "💪", xpReward = 300, isUnlocked = false,
                    progress = 47, targetProgress = 100), "timer"),
            CategorizedAchievement(
                Achievement(id = "task_1", title = "কাজ সম্পন্ন", description = "প্রথম টাস্ক সম্পন্ন করুন",
                    iconName = "✅", xpReward = 20, isUnlocked = true,
                    unlockedAt = System.currentTimeMillis() - 86400000L * 25,
                    progress = 1, targetProgress = 1), "task"),
            CategorizedAchievement(
                Achievement(id = "task_2", title = "৫০ কাজ সম্পন্ন", description = "৫০টি টাস্ক সম্পন্ন করুন",
                    iconName = "📋", xpReward = 200, isUnlocked = false,
                    progress = 23, targetProgress = 50), "task"),
            CategorizedAchievement(
                Achievement(id = "exam_1", title = "পরীক্ষার প্রস্তুতি", description = "প্রথম পরীক্ষার প্রস্তুতি শুরু করুন",
                    iconName = "📝", xpReward = 40, isUnlocked = true,
                    unlockedAt = System.currentTimeMillis() - 86400000L * 10,
                    progress = 1, targetProgress = 1), "exam"),
            CategorizedAchievement(
                Achievement(id = "exam_2", title = "মডেল টেস্ট মাস্টার", description = "১০টি মডেল টেস্ট দিন",
                    iconName = "📄", xpReward = 250, isUnlocked = false,
                    progress = 3, targetProgress = 10), "exam"),
            CategorizedAchievement(
                Achievement(id = "social_1", title = "সহযোগিতা", description = "প্রথম স্টাডি গ্রুপে যোগ দিন",
                    iconName = "👥", xpReward = 30, isUnlocked = true,
                    unlockedAt = System.currentTimeMillis() - 86400000L * 5,
                    progress = 1, targetProgress = 1), "social"),
            CategorizedAchievement(
                Achievement(id = "social_2", title = "শেয়ার করুন", description = "স্টাডি স্ট্রিক শেয়ার করুন",
                    iconName = "🤝", xpReward = 15, isUnlocked = false,
                    progress = 0, targetProgress = 1), "social"),
        )
    }

    val recentUnlocks = remember {
        allAchievements
            .filter { it.achievement.isUnlocked && it.achievement.unlockedAt != null }
            .sortedByDescending { it.achievement.unlockedAt }
            .take(3)
    }

    val filteredAchievements = remember(selectedTabIndex, allAchievements) {
        if (selectedTabIndex == 0) allAchievements
        else {
            val catMap = mapOf(1 to "study", 2 to "timer", 3 to "task", 4 to "exam", 5 to "social")
            allAchievements.filter { it.category == catMap[selectedTabIndex] }
        }
    }

    val totalUnlocked = allAchievements.count { it.achievement.isUnlocked }
    val totalXpEarned = allAchievements.filter { it.achievement.isUnlocked }.sumOf { it.achievement.xpReward }

    var detailAchievement by remember { mutableStateOf<CategorizedAchievement?>(null) }

    detailAchievement?.let { catAch ->
        val ach = catAch.achievement
        AlertDialog(
            onDismissRequest = { detailAchievement = null },
            title = {
                Text("${ach.iconName} ${ach.title}", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
            },
            text = {
                Column {
                    Text(ach.description, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(Modifier.height(12.dp))
                    if (ach.isUnlocked && ach.unlockedAt != null) {
                        Text("✅ অনলক: ${formatAchievementDate(ach.unlockedAt)}", style = MaterialTheme.typography.bodySmall, color = Success)
                    }
                    Text("XP পুরস্কার: +${ach.xpReward.toBengaliDigits()}", style = MaterialTheme.typography.bodySmall, color = Primary, fontFamily = EnglishFontFamily)
                    Spacer(Modifier.height(8.dp))
                    Text("অগ্রগতি: ${ach.progress.toBengaliDigits()}/${ach.targetProgress.toBengaliDigits()}", style = MaterialTheme.typography.bodySmall)
                }
            },
            confirmButton = {
                FilledTonalButton(onClick = { detailAchievement = null }) { Text("ঠিক আছে") }
            },
        )
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = { Text("অর্জন", style = MaterialTheme.typography.titleLarge) },
                navigationIcon = {
                    Icon(imageVector = Icons.Default.EmojiEvents, contentDescription = null,
                        modifier = Modifier.size(24.dp).padding(start = 16.dp), tint = AchievementUnlocked)
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface),
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
                            modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]), color = Primary)
                    }
                },
                divider = {},
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface,
            ) {
                categoryTabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = { Text(title, style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Medium)) },
                        selectedContentColor = Primary,
                        unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
                contentPadding = PaddingValues(vertical = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                // Header stats
                item(span = { GridItemSpan(2) }) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        GlassElevatedCard(modifier = Modifier.weight(1f), padding = 16.dp) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("${totalUnlocked.toBengaliDigits()}/${allAchievements.size.toBengaliDigits()}",
                                    style = MaterialTheme.typography.headlineSmall.copy(fontFamily = EnglishFontFamily, fontWeight = FontWeight.Bold),
                                    color = AchievementUnlocked)
                                Text("অনলক করা", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                        GlassElevatedCard(modifier = Modifier.weight(1f), padding = 16.dp) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("+${totalXpEarned.toBengaliDigits()}",
                                    style = MaterialTheme.typography.headlineSmall.copy(fontFamily = EnglishFontFamily, fontWeight = FontWeight.Bold),
                                    color = Primary)
                                Text("মোট XP", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                    }
                }

                // Recent unlocks
                if (recentUnlocks.isNotEmpty() && selectedTabIndex == 0) {
                    item(span = { GridItemSpan(2) }) {
                        Spacer(Modifier.height(4.dp))
                        Text("সাম্প্রতিক অনলক", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
                    }
                    items(recentUnlocks, key = { it.achievement.id }) { catAch ->
                        RecentUnlockCard(catAch = catAch, onClick = { detailAchievement = catAch })
                    }
                    item(span = { GridItemSpan(2) }) {
                        Spacer(Modifier.height(8.dp))
                        Text("সকল অর্জন", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
                    }
                }

                // Achievement grid
                items(filteredAchievements, key = { it.achievement.id }) { catAch ->
                    AchievementBadgeCard(catAch = catAch, onClick = { detailAchievement = catAch })
                }

                item(span = { GridItemSpan(2) }) { Spacer(Modifier.height(32.dp)) }
            }
        }
    }
}

@Composable
private fun AchievementBadgeCard(catAch: CategorizedAchievement, onClick: () -> Unit) {
    val ach = catAch.achievement
    val progress = if (ach.targetProgress > 0) min(ach.progress.toFloat() / ach.targetProgress, 1f) else 0f
    val animatedProgress by animateFloatAsState(targetValue = progress, animationSpec = tween(800, easing = FastOutSlowInEasing), label = "achP")
    val badgeColor = if (ach.isUnlocked) AchievementUnlocked else AchievementLocked
    val contentAlpha = if (ach.isUnlocked) 1f else 0.5f

    GlassElevatedCard(modifier = Modifier.fillMaxWidth().clickable(onClick = onClick), padding = 0.dp) {
        Box(modifier = Modifier.fillMaxWidth().aspectRatio(0.85f).padding(12.dp), contentAlignment = Alignment.Center) {
            Column(modifier = Modifier.fillMaxWidth().graphicsLayer { alpha = contentAlpha },
                horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.size(80.dp)) {
                    Canvas(modifier = Modifier.size(80.dp)) {
                        drawArc(color = XpBarBg, startAngle = -90f, sweepAngle = 360f, useCenter = false,
                            style = Stroke(width = 4.dp.toPx(), cap = StrokeCap.Round))
                        if (progress > 0f) {
                            drawArc(color = badgeColor, startAngle = -90f, sweepAngle = 360f * animatedProgress, useCenter = false,
                                style = Stroke(width = 4.dp.toPx(), cap = StrokeCap.Round))
                        }
                    }
                    Text(text = if (ach.isUnlocked) ach.iconName else "🔒", style = MaterialTheme.typography.displaySmall)
                }
                Spacer(Modifier.height(8.dp))
                Text(ach.title, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    textAlign = TextAlign.Center, maxLines = 2, overflow = TextOverflow.Ellipsis)
                Spacer(Modifier.height(4.dp))
                Text(ach.description, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center, maxLines = 2, overflow = TextOverflow.Ellipsis, lineHeight = 14.sp)
                Spacer(Modifier.height(6.dp))
                if (ach.isUnlocked) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.Star, contentDescription = null, tint = AchievementUnlocked, modifier = Modifier.size(12.dp))
                        Spacer(Modifier.width(4.dp))
                        Text("অনলক করা", style = MaterialTheme.typography.labelSmall, color = AchievementUnlocked)
                    }
                } else {
                    Text("${ach.progress.toBengaliDigits()}/${ach.targetProgress.toBengaliDigits()}",
                        style = MaterialTheme.typography.labelSmall.copy(fontFamily = EnglishFontFamily),
                        color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Spacer(Modifier.height(4.dp))
                Box(modifier = Modifier.clip(RoundedCornerShape(100.dp)).background(Primary.copy(alpha = 0.12f)).padding(horizontal = 10.dp, vertical = 3.dp)) {
                    Text("+${ach.xpReward.toBengaliDigits()} XP",
                        style = MaterialTheme.typography.labelSmall.copy(fontFamily = EnglishFontFamily, fontWeight = FontWeight.Bold), color = Primary)
                }
            }
        }
    }
}

@Composable
private fun RecentUnlockCard(catAch: CategorizedAchievement, onClick: () -> Unit) {
    val ach = catAch.achievement
    GlassFilledCard(modifier = Modifier.fillMaxWidth().clickable(onClick = onClick), tint = AchievementUnlocked, padding = 14.dp) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(48.dp).clip(CircleShape).background(AchievementUnlocked.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center) {
                Text(ach.iconName, style = MaterialTheme.typography.titleLarge)
            }
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(ach.title, style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    maxLines = 1, overflow = TextOverflow.Ellipsis)
                Text("${formatAchievementDate(ach.unlockedAt!!)}  •  +${ach.xpReward.toBengaliDigits()} XP",
                    style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Text("✨", style = MaterialTheme.typography.titleMedium)
        }
    }
}

private val achDateFormat = SimpleDateFormat("dd MMM yyyy", Locale("bn", "BD"))

private fun formatAchievementDate(timestamp: Long): String {
    return try { achDateFormat.format(Date(timestamp)) } catch (_: Exception) { "" }
}
