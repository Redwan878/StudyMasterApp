/*
package com.porashona.studymaster.ui.compose.screens.profile

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.ExportData
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.porashona.studymaster.data.model.Achievement
import com.porashona.studymaster.data.model.UserProfile
import com.porashona.studymaster.ui.compose.components.GlassCardVariant
import com.porashona.studymaster.ui.compose.components.GlassmorphicCard
import com.porashona.studymaster.ui.compose.components.XPProgressIndicator
import com.porashona.studymaster.ui.compose.theme.*
import com.porashona.studymaster.ui.compose.viewmodels.ProfileViewModel
import kotlin.math.roundToInt

// ═══════════════════════════════════════════════════════════════════════════════
// ProfileScreen — Full profile with stats, achievements, board selector.
// All text in Bengali. Glassmorphic cards. Material 3.
// ═══════════════════════════════════════════════════════════════════════════════

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    onNavigateToSettings: () -> Unit = {},
    onNavigateToAchievements: () -> Unit = {},
    onNavigateBack: () -> Unit = {},
) {
    val userProfile by viewModel.userProfile.collectAsState()
    val recentAchievements by viewModel.recentAchievements.collectAsState()
    val isUpdating by viewModel.isUpdating.collectAsState()

    var showEditNameDialog by remember { mutableStateOf(false) }
    var selectedBoard by remember { mutableStateOf(userProfile?.let { inferBoard(it) } ?: "ssc_science") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("প্রোফাইল", style = MaterialTheme.typography.headlineSmall) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "ফিরুন")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                ),
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(vertical = 16.dp),
        ) {
            // ── Profile Header ─────────────────────────────────────────────────
            item {
                GlassmorphicCard(
                    modifier = Modifier.fillMaxWidth(),
                    variant = GlassCardVariant.ELEVATED,
                    padding = 24.dp,
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        // Avatar circle with initials
                        val initial = (userProfile?.name?.take(1) ?: "শ")
                        Box(
                            modifier = Modifier
                                .size(88.dp)
                                .clip(CircleShape)
                                .background(
                                    Brush.linearGradient(
                                        colors = listOf(Primary, Secondary),
                                        start = Offset.Zero,
                                        end = Offset(88f, 88f),
                                    )
                                ),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                initial,
                                style = MaterialTheme.typography.displaySmall,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                            )
                        }

                        // Name (editable)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                userProfile?.name ?: "শিক্ষার্থী",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                            )
                            IconButton(onClick = { showEditNameDialog = true }) {
                                Icon(
                                    Icons.Default.Edit,
                                    contentDescription = "নাম সম্পাদনা",
                                    tint = Primary,
                                    modifier = Modifier.size(20.dp),
                                )
                            }
                        }

                        // Study board info
                        Text(
                            when (selectedBoard) {
                                "ssc_science" -> "SSC বিজ্ঞান বিভাগ — ২০২৬"
                                "hsc_science" -> "HSC বিজ্ঞান বিভাগ — ২০২৬"
                                else -> "বিজ্ঞান বিভাগ"
                            },
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        // XP progress
                        val xpForNext = userProfile?.getXpForNextLevel() ?: 1000
                        val xpProgress = userProfile?.getXpProgress() ?: 0
                        XPProgressIndicator(
                            currentXp = xpProgress,
                            targetXp = xpForNext,
                            level = userProfile?.level ?: 1,
                            modifier = Modifier.fillMaxWidth(0.8f),
                        )
                    }
                }
            }

            // ── Stats Overview ────────────────────────────────────────────────
            item {
                Text(
                    "পরিসংখ্যান",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 4.dp),
                )
            }

            item {
                val totalHours = ((userProfile?.totalStudyTimeSeconds ?: 0L) / 3600.0)
                val totalSessions = userProfile?.totalSessions ?: 0
                val currentStreak = userProfile?.currentStreak ?: 0
                val bestStreak = userProfile?.longestStreak ?: 0
                val totalXp = userProfile?.totalXp ?: 0
                val level = userProfile?.level ?: 1

                val stats = listOf(
                    StatItem("মোট অধ্যয়ন", totalHours.toBengaliDigits(decimalPlaces = 1), "ঘণ্টা", Primary),
                    StatItem("মোট সেশন", totalSessions.toBengaliDigits(), "টি", Secondary),
                    StatItem("বর্তমান স্ট্রিক", currentStreak.toBengaliDigits(), "দিন", StreakFire),
                    StatItem("সেরা স্ট্রিক", bestStreak.toBengaliDigits(), "দিন", Tertiary),
                    StatItem("মোট XP", totalXp.toBengaliDigits(), "XP", XpBarFill),
                    StatItem("বর্তমান লেভেল", level.toBengaliDigits(), "", LevelUp),
                )

                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    items(stats) { stat ->
                        GlassmorphicCard(
                            modifier = Modifier.fillMaxWidth(),
                            variant = GlassCardVariant.FILLED,
                            tint = stat.color,
                            padding = 12.dp,
                        ) {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(4.dp),
                            ) {
                                Text(
                                    stat.value,
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = stat.color,
                                )
                                Text(
                                    stat.title,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    textAlign = TextAlign.Center,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis,
                                )
                                if (stat.unit.isNotEmpty()) {
                                    Text(
                                        stat.unit,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // ── Achievement Showcase (top 3 recent) ───────────────────────────
            if (recentAchievements.isNotEmpty()) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            "সাম্প্রতিক অর্জন",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                        )
                        TextButton(onClick = onNavigateToAchievements) {
                            Text("সব দেখুন")
                        }
                    }
                }

                items(
                    items = recentAchievements.take(3),
                    key = { it.id },
                ) { achievement ->
                    AchievementCard(achievement = achievement)
                }
            }

            // ── Study Board Selector ──────────────────────────────────────────
            item {
                Text(
                    "স্টাডি বোর্ড নির্বাচন",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 4.dp),
                )
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    listOf(
                        "ssc_science" to "SSC বিজ্ঞান ২০২৬",
                        "hsc_science" to "HSC বিজ্ঞান ২০২৬",
                    ).forEach { (key, label) ->
                        val isSelected = selectedBoard == key
                        GlassmorphicCard(
                            modifier = Modifier.weight(1f),
                            variant = if (isSelected) GlassCardVariant.ELEVATED else GlassCardVariant.OUTLINED,
                            tint = if (isSelected) Primary else null,
                            padding = 14.dp,
                            onClick = { selectedBoard = key },
                        ) {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                Text(
                                    label,
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isSelected) Primary else MaterialTheme.colorScheme.onSurface,
                                    textAlign = TextAlign.Center,
                                )
                            }
                        }
                    }
                }
            }

            // ── Quick Links ───────────────────────────────────────────────────
            item {
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                GlassmorphicCard(
                    modifier = Modifier.fillMaxWidth(),
                    variant = GlassCardVariant.OUTLINED,
                    padding = 8.dp,
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                        QuickLinkRow(
                            icon = Icons.Default.Settings,
                            title = "সেটিংস",
                            subtitle = "থিম, নোটিফিকেশন, ব্যাকআপ",
                            onClick = onNavigateToSettings,
                        )
                        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant, modifier = Modifier.padding(horizontal = 12.dp))
                        QuickLinkRow(
                            icon = Icons.Default.ExportData,
                            title = "ডেটা এক্সপোর্ট",
                            subtitle = "সব ডেটা JSON ফাইলে ডাউনলোড",
                            onClick = { /* In production: trigger data export */ },
                        )
                        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant, modifier = Modifier.padding(horizontal = 12.dp))
                        QuickLinkRow(
                            icon = Icons.Default.EmojiEvents,
                            title = "অর্জনসমূহ",
                            subtitle = "আপনার সমস্ত অর্জন দেখুন",
                            onClick = onNavigateToAchievements,
                        )
                    }
                }
            }

            // Bottom spacer
            item { Spacer(modifier = Modifier.height(32.dp)) }
        }
    }

    // ── Edit Name Dialog ───────────────────────────────────────────────────
    if (showEditNameDialog) {
        var nameInput by remember { mutableStateOf(userProfile?.name ?: "") }
        AlertDialog(
            onDismissRequest = { showEditNameDialog = false },
            title = { Text("নাম পরিবর্তন করুন") },
            text = {
                OutlinedTextField(
                    value = nameInput,
                    onValueChange = { nameInput = it },
                    label = { Text("আপনার নাম") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isUpdating,
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (nameInput.isNotBlank()) {
                            viewModel.updateName(nameInput.trim())
                            showEditNameDialog = false
                        }
                    },
                    enabled = nameInput.isNotBlank() && !isUpdating,
                    colors = ButtonDefaults.buttonColors(containerColor = Primary),
                ) {
                    Text(if (isUpdating) "সেভ হচ্ছে..." else "সেভ করুন")
                }
            },
            dismissButton = {
                TextButton(onClick = { showEditNameDialog = false }) {
                    Text("বাতিল")
                }
            },
        )
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Supporting Components
// ═══════════════════════════════════════════════════════════════════════════════

private data class StatItem(
    val title: String,
    val value: String,
    val unit: String,
    val color: Color,
)

@Composable
private fun AchievementCard(achievement: Achievement) {
    GlassmorphicCard(
        modifier = Modifier.fillMaxWidth(),
        variant = GlassCardVariant.ELEVATED,
        tint = if (achievement.isUnlocked) Tertiary else null,
        padding = 14.dp,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Achievement icon
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(
                        if (achievement.isUnlocked) Tertiary.copy(alpha = 0.2f)
                        else MaterialTheme.colorScheme.surfaceVariant,
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    Icons.Default.EmojiEvents,
                    contentDescription = null,
                    tint = if (achievement.isUnlocked) Tertiary else MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(22.dp),
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    achievement.title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    achievement.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                if (achievement.isUnlocked) {
                    Text(
                        "+${achievement.xpReward} XP",
                        style = MaterialTheme.typography.labelSmall,
                        color = XpGain,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        "অর্জিত ✓",
                        style = MaterialTheme.typography.labelSmall,
                        color = Success,
                    )
                } else {
                    Text(
                        "${achievement.progress}/${achievement.targetProgress}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        }
    }
}

@Composable
private fun QuickLinkRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(icon, contentDescription = null, tint = Primary, modifier = Modifier.size(22.dp))
        Spacer(modifier = Modifier.width(14.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, style = MaterialTheme.typography.bodyLarge)
            Text(subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

private fun inferBoard(profile: UserProfile): String {
    // In production, this would be stored in the profile
    return "ssc_science"
}
*/