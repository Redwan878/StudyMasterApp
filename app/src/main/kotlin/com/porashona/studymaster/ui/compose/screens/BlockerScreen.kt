package com.porashona.studymaster.ui.compose.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.AppBlocking
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DoNotDisturbOn
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.outlined.Block
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.porashona.studymaster.data.model.BlockedApp
import com.porashona.studymaster.ui.compose.components.GlassElevatedCard
import com.porashona.studymaster.ui.compose.components.GlassFilledCard
import com.porashona.studymaster.ui.compose.components.GlassOutlinedCard
import com.porashona.studymaster.ui.compose.theme.AchievementUnlocked
import com.porashona.studymaster.ui.compose.theme.EnglishFontFamily
import com.porashona.studymaster.ui.compose.theme.Error
import com.porashona.studymaster.ui.compose.theme.Info
import com.porashona.studymaster.ui.compose.theme.Primary
import com.porashona.studymaster.ui.compose.theme.Secondary
import com.porashona.studymaster.ui.compose.theme.Success
import com.porashona.studymaster.ui.compose.theme.TimerWork
import com.porashona.studymaster.ui.compose.theme.Warning
import com.porashona.studymaster.ui.compose.theme.XpBarBg
import com.porashona.studymaster.ui.compose.theme.XpBarFill
import com.porashona.studymaster.ui.compose.theme.toBengaliDigits
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// ═══════════════════════════════════════════════════════════════════════════════
// BlockerScreen — App blocker & Zen Mode management (Bengali UI)
// ═══════════════════════════════════════════════════════════════════════════════

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BlockerScreen() {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    // ── Mock state ───────────────────────────────────────────────────────────
    var zenModeEnabled by remember { mutableStateOf(false) }
    var blockScheduleEnabled by remember { mutableStateOf(true) }
    var overlayEnabled by remember { mutableStateOf(true) }
    var showAddAppDialog by remember { mutableStateOf(false) }
    var selectedScheduleMode by remember { mutableIntStateOf(0) }
    val scheduleModes = listOf("শুধু সেশনে", "কাস্টম সময়", "সবসময়")
    var customStartHour by remember { mutableIntStateOf(9) }
    var customEndHour by remember { mutableIntStateOf(22) }

    val blockedApps = remember {
        mutableStateListOf(
            BlockedApp(
                packageName = "com.facebook.katana",
                appName = "ফেসবুক",
                isBlocked = true,
                blockAttempts = 23,
                addedAt = System.currentTimeMillis() - 86400000L * 5,
            ),
            BlockedApp(
                packageName = "com.instagram.android",
                appName = "ইনস্টাগ্রাম",
                isBlocked = true,
                blockAttempts = 15,
                addedAt = System.currentTimeMillis() - 86400000L * 3,
            ),
            BlockedApp(
                packageName = "com.zhiliaoapp.musically",
                appName = "টিকটক",
                isBlocked = true,
                blockAttempts = 42,
                addedAt = System.currentTimeMillis() - 86400000L * 7,
            ),
            BlockedApp(
                packageName = "com.twitter.android",
                appName = "এক্স (টুইটার)",
                isBlocked = false,
                blockAttempts = 8,
                addedAt = System.currentTimeMillis() - 86400000L * 1,
            ),
            BlockedApp(
                packageName = "com.whatsapp",
                appName = "হোয়াটসঅ্যাপ",
                isBlocked = false,
                blockAttempts = 5,
                addedAt = System.currentTimeMillis() - 86400000L * 2,
            ),
        )
    }

    val whitelistApps = remember {
        mutableStateListOf(
            BlockedApp(
                packageName = "com.google.android.calculator",
                appName = "ক্যালকুলেটর",
                isBlocked = false,
                isWhitelisted = true,
            ),
            BlockedApp(
                packageName = "com.dictionary.app",
                appName = "অভিধান",
                isBlocked = false,
                isWhitelisted = true,
            ),
        )
    }

    val totalBlocked = blockedApps.count { it.isBlocked }
    val totalAttempts = blockedApps.sumOf { it.blockAttempts }
    val timeSavedHours = (totalAttempts * 5) / 60 // ~5 min per attempt

    // ── Add app dialog ───────────────────────────────────────────────────────
    if (showAddAppDialog) {
        AlertDialog(
            onDismissRequest = { showAddAppDialog = false },
            title = {
                Text(
                    "অ্যাপ ব্লক করুন",
                    style = MaterialTheme.typography.titleMedium,
                )
            },
            text = {
                Text(
                    "ইনস্টল করা অ্যাপের তালিকা থেকে ব্লক করার জন্য অ্যাপ নির্বাচন করুন।",
                    style = MaterialTheme.typography.bodyMedium,
                )
            },
            confirmButton = {
                FilledTonalButton(onClick = { showAddAppDialog = false }) {
                    Text("ঠিক আছে")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddAppDialog = false }) {
                    Text("বাতিল")
                }
            },
        )
    }

    // ── Main Scaffold ────────────────────────────────────────────────────────
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "অ্যাপ ব্লকার",
                        style = MaterialTheme.typography.titleLarge,
                    )
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.Shield,
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                            .padding(start = 16.dp),
                        tint = Primary,
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
            // SECTION: Zen Mode Quick Toggle
            // ══════════════════════════════════════════════════════════════════
            item {
                val zenColor by animateColorAsState(
                    targetValue = if (zenModeEnabled) Success else MaterialTheme.colorScheme.onSurfaceVariant,
                    animationSpec = tween(300, easing = FastOutSlowInEasing),
                    label = "zenColor",
                )
                GlassElevatedCard(
                    modifier = Modifier.fillMaxWidth(),
                    padding = 20.dp,
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.DoNotDisturbOn,
                                contentDescription = null,
                                tint = zenColor,
                                modifier = Modifier.size(32.dp),
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                Text(
                                    "জেন মোড",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                    ),
                                )
                                Text(
                                    if (zenModeEnabled) "সক্রিয় — সকল নোটিফিকেশন বন্ধ"
                                    else "নোটিফিকেশন ও বিঘ্ন ব্লক করুন",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                            }
                        }
                        Switch(
                            checked = zenModeEnabled,
                            onCheckedChange = { zenModeEnabled = it },
                            colors = SwitchDefaults.colors(
                                checkedTrackColor = Success,
                                checkedThumbColor = MaterialTheme.colorScheme.surface,
                            ),
                        )
                    }
                }
            }

            // ══════════════════════════════════════════════════════════════════
            // SECTION: Statistics Cards
            // ══════════════════════════════════════════════════════════════════
            item {
                Text(
                    "পরিসংখ্যান",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                )
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    StatCard(
                        modifier = Modifier.weight(1f),
                        label = "ব্লক করা অ্যাপ",
                        value = totalBlocked.toBengaliDigits(),
                        icon = Icons.Default.Block,
                        color = Primary,
                    )
                    StatCard(
                        modifier = Modifier.weight(1f),
                        label = "মোট ব্লক",
                        value = totalAttempts.toBengaliDigits(),
                        icon = Icons.Default.AppBlocking,
                        color = TimerWork,
                    )
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    StatCard(
                        modifier = Modifier.weight(1f),
                        label = "সময় বাঁচানো",
                        value = "${timeSavedHours.toBengaliDigits()} ঘণ্টা",
                        icon = Icons.Default.Timer,
                        color = Success,
                    )
                    StatCard(
                        modifier = Modifier.weight(1f),
                        label = "সক্রিয় দিন",
                        value = "১২",
                        icon = Icons.Default.Schedule,
                        color = AchievementUnlocked,
                    )
                }
            }

            // ══════════════════════════════════════════════════════════════════
            // SECTION: Block Schedule
            // ══════════════════════════════════════════════════════════════════
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "ব্লক সময়সূচী",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                )
            }

            item {
                GlassOutlinedCard(
                    modifier = Modifier.fillMaxWidth(),
                    padding = 20.dp,
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Schedule,
                                    contentDescription = null,
                                    tint = Info,
                                    modifier = Modifier.size(24.dp),
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    "শিডিউল সক্রিয়",
                                    style = MaterialTheme.typography.titleSmall,
                                )
                            }
                            Switch(
                                checked = blockScheduleEnabled,
                                onCheckedChange = { blockScheduleEnabled = it },
                                colors = SwitchDefaults.colors(
                                    checkedTrackColor = Primary,
                                ),
                            )
                        }

                        if (blockScheduleEnabled) {
                            Spacer(modifier = Modifier.height(16.dp))
                            // Schedule mode selector
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                            ) {
                                scheduleModes.forEachIndexed { index, label ->
                                    val isSelected = selectedScheduleMode == index
                                    FilledTonalButton(
                                        onClick = { selectedScheduleMode = index },
                                        modifier = Modifier.weight(1f),
                                        colors = ButtonDefaults.filledTonalButtonColors(
                                            containerColor = if (isSelected) Primary
                                            else MaterialTheme.colorScheme.surfaceVariant,
                                            contentColor = if (isSelected) MaterialTheme.colorScheme.onPrimary
                                            else MaterialTheme.colorScheme.onSurfaceVariant,
                                        ),
                                    ) {
                                        Text(
                                            label,
                                            style = MaterialTheme.typography.labelSmall,
                                        )
                                    }
                                }
                            }

                            if (selectedScheduleMode == 1) {
                                Spacer(modifier = Modifier.height(16.dp))
                                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                                Spacer(modifier = Modifier.height(12.dp))
                                CustomTimeRange(
                                    startHour = customStartHour,
                                    endHour = customEndHour,
                                    onStartChange = { customStartHour = it },
                                    onEndChange = { customEndHour = it },
                                )
                            }
                        }
                    }
                }
            }

            // ══════════════════════════════════════════════════════════════════
            // SECTION: Block Overlay Settings
            // ══════════════════════════════════════════════════════════════════
            item {
                GlassOutlinedCard(
                    modifier = Modifier.fillMaxWidth(),
                    padding = 20.dp,
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Security,
                                contentDescription = null,
                                tint = Warning,
                                modifier = Modifier.size(24.dp),
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    "ব্লক ওভারলে",
                                    style = MaterialTheme.typography.titleSmall,
                                )
                                Text(
                                    "ব্লক করা অ্যাপ খুললে সতর্কতা দেখান",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                            }
                        }
                        Switch(
                            checked = overlayEnabled,
                            onCheckedChange = { overlayEnabled = it },
                            colors = SwitchDefaults.colors(
                                checkedTrackColor = Warning,
                            ),
                        )
                    }
                }
            }

            // ══════════════════════════════════════════════════════════════════
            // SECTION: Blocked Apps List
            // ══════════════════════════════════════════════════════════════════
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        "ব্লক করা অ্যাপ",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    )
                    FilledTonalButton(
                        onClick = { showAddAppDialog = true },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp),
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("অ্যাপ যোগ করুন")
                    }
                }
            }

            items(blockedApps, key = { it.packageName }) { app ->
                BlockedAppItem(
                    app = app,
                    onToggle = { updated ->
                        val idx = blockedApps.indexOf(app)
                        if (idx >= 0) blockedApps[idx] = updated
                    },
                    onRemove = { blockedApps.remove(app) },
                )
            }

            // ══════════════════════════════════════════════════════════════════
            // SECTION: Break Whitelist
            // ══════════════════════════════════════════════════════════════════
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "বিরতি হোয়াইটলিস্ট",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                )
                Text(
                    "এই অ্যাপগুলো বিরতিতে ব্যবহার করা যাবে",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }

            items(whitelistApps, key = { it.packageName }) { app ->
                GlassFilledCard(
                    modifier = Modifier.fillMaxWidth(),
                    tint = Success,
                    padding = 16.dp,
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        // App icon placeholder
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(MaterialTheme.colorScheme.surfaceVariant),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                app.appName.take(1),
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = Success,
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                app.appName,
                                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
                            )
                            Text(
                                "হোয়াইটলিস্টে আছে",
                                style = MaterialTheme.typography.bodySmall,
                                color = Success,
                            )
                        }
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = Success,
                            modifier = Modifier.size(24.dp),
                        )
                    }
                }
            }

            // Bottom spacing
            item { Spacer(modifier = Modifier.height(32.dp)) }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Helper: Stat card for blocker statistics
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun StatCard(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    icon: ImageVector,
    color: Color,
) {
    GlassElevatedCard(
        modifier = modifier,
        padding = 16.dp,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(24.dp),
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                value,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontFamily = EnglishFontFamily,
                ),
                color = color,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                label,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
            )
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Helper: Blocked app list item with toggle and remove
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun BlockedAppItem(
    app: BlockedApp,
    onToggle: (BlockedApp) -> Unit,
    onRemove: () -> Unit,
) {
    GlassOutlinedCard(
        modifier = Modifier.fillMaxWidth(),
        padding = 16.dp,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // App icon placeholder with initial letter
            val iconBg = if (app.isBlocked) TimerWork.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surfaceVariant
            val iconColor = if (app.isBlocked) TimerWork else MaterialTheme.colorScheme.onSurfaceVariant
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(iconBg),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    app.appName.take(1),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontFamily = EnglishFontFamily,
                    ),
                    color = iconColor,
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // App name + attempt count
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    app.appName,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = if (app.isBlocked) FontWeight.SemiBold else FontWeight.Normal,
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    "ব্লক: ${app.blockAttempts.toBengaliDigits()} বার  •  যোগ: ${formatDateShort(app.addedAt)}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }

            // Toggle switch
            Switch(
                checked = app.isBlocked,
                onCheckedChange = { onToggle(app.copy(isBlocked = it)) },
                colors = SwitchDefaults.colors(
                    checkedTrackColor = TimerWork,
                    uncheckedTrackColor = MaterialTheme.colorScheme.surfaceVariant,
                    checkedThumbColor = MaterialTheme.colorScheme.surface,
                ),
            )

            Spacer(modifier = Modifier.width(8.dp))

            // Remove button
            IconButton(onClick = onRemove) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "সরান",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(20.dp),
                )
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Helper: Custom time range picker (start/end hour sliders)
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun CustomTimeRange(
    startHour: Int,
    endHour: Int,
    onStartChange: (Int) -> Unit,
    onEndChange: (Int) -> Unit,
) {
    Column {
        Text(
            "কাস্টম সময়",
            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
            color = Primary,
        )
        Spacer(modifier = Modifier.height(12.dp))

        // Start hour
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Default.AccessTime,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(18.dp),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                "শুরু:",
                style = MaterialTheme.typography.bodyMedium,
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                "${startHour.toBengaliDigits()}:০০",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontFamily = EnglishFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = Primary,
                ),
            )
        }
        Slider(
            value = startHour.toFloat(),
            onValueChange = { onStartChange(it.toInt()) },
            valueRange = 0f..23f,
            steps = 22,
            colors = SliderDefaults.colors(
                thumbColor = Primary,
                activeTrackColor = Primary,
            ),
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(8.dp))

        // End hour
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Default.Bolt,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(18.dp),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                "শেষ:",
                style = MaterialTheme.typography.bodyMedium,
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                "${endHour.toBengaliDigits()}:০০",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontFamily = EnglishFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = Secondary,
                ),
            )
        }
        Slider(
            value = endHour.toFloat(),
            onValueChange = { onEndChange(it.toInt()) },
            valueRange = 0f..23f,
            steps = 22,
            colors = SliderDefaults.colors(
                thumbColor = Secondary,
                activeTrackColor = Secondary,
            ),
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Helper: Short date formatter
// ═══════════════════════════════════════════════════════════════════════════════

private val shortDateFormat = SimpleDateFormat("dd MMM", Locale("bn", "BD"))

private fun formatDateShort(timestamp: Long): String {
    return try {
        shortDateFormat.format(Date(timestamp))
    } catch (_: Exception) {
        ""
    }
}