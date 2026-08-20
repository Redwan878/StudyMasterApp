
package com.porashona.studymaster.ui.compose.screens.settings

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Brightness4
import androidx.compose.material.icons.filled.Brightness7
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.Devices
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Pin
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Smartphone
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VolumeOff
import androidx.compose.material.icons.outlined.BrightnessAuto
import androidx.compose.material.icons.outlined.TextFields
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.porashona.studymaster.data.model.LockType
import com.porashona.studymaster.data.model.NotificationType
import com.porashona.studymaster.ui.compose.components.GlassCardVariant
import com.porashona.studymaster.ui.compose.components.GlassmorphicCard
import com.porashona.studymaster.ui.compose.theme.*
import com.porashona.studymaster.ui.compose.viewmodels.SettingsEvent
import com.porashona.studymaster.ui.compose.viewmodels.SettingsViewModel
import com.porashona.studymaster.ui.compose.viewmodels.ThemeMode
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// ═══════════════════════════════════════════════════════════════════════════════
// SettingsScreen — Complete settings UI for StudyMaster
// All text in Bengali. Glassmorphic section cards. Material 3 components.
// ═══════════════════════════════════════════════════════════════════════════════

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit = {},
) {
    val context = LocalContext.current

    val themeMode by viewModel.themeMode.collectAsState()
    val fontSize by viewModel.fontSize.collectAsState()
    val language by viewModel.language.collectAsState()
    val dailyReminderEnabled by viewModel.dailyReminderEnabled.collectAsState()
    val dailyReminderTime by viewModel.dailyReminderTime.collectAsState()
    val streakReminderEnabled by viewModel.streakReminderEnabled.collectAsState()
    val examCountdownEnabled by viewModel.examCountdownEnabled.collectAsState()
    val weeklySummaryEnabled by viewModel.weeklySummaryEnabled.collectAsState()
    val notificationsEnabled by viewModel.notificationsEnabled.collectAsState()
    val backupState by viewModel.backupState.collectAsState()
    val recentBackups by viewModel.recentBackups.collectAsState()
    val lockConfig by viewModel.lockConfig.collectAsState()
    val pomodoroDuration by viewModel.dailyGoalMinutes.collectAsState()
    val googleDriveStatus by viewModel.googleDriveStatus.collectAsState()

    val events by viewModel.events.collectAsState()
    var showPinDialog by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    var showDeleteConfirmDialog by remember { mutableStateOf(false) }
    var autoBackupSchedule by rememberSaveable { mutableStateOf("off") }
    var calendarColorCoding by rememberSaveable { mutableStateOf(true) }
    var autoStartNextSession by rememberSaveable { mutableStateOf(false) }
    var weakSubjectNudge by rememberSaveable { mutableStateOf(true) }
    var multiDeviceSync by rememberSaveable { mutableStateOf(false) }
    var silentStartHour by rememberSaveable { mutableIntStateOf(22) }
    var silentEndHour by rememberSaveable { mutableIntStateOf(7) }
    var zenModePreset by rememberSaveable { mutableStateOf("light") }
    var pomodoroDurationMin by rememberSaveable { mutableIntStateOf(25) }
    var shortBreakMin by rememberSaveable { mutableIntStateOf(5) }
    var longBreakMin by rememberSaveable { mutableIntStateOf(15) }
    var appWhitelist by rememberSaveable { mutableStateOf(listOf<String>()) }

    LaunchedEffect(events) {
        events?.let { event ->
            when (event) {
                is SettingsEvent.BackupSuccess -> { viewModel.clearEvent() }
                is SettingsEvent.RestoreSuccess -> { viewModel.clearEvent() }
                else -> {}
            }
        }
    }

    val timePickerState = rememberTimePickerState(
        initialHour = dailyReminderTime.split(":").getOrNull(0)?.toIntOrNull() ?: 9,
        initialMinute = dailyReminderTime.split(":").getOrNull(1)?.toIntOrNull() ?: 0,
        is24Hour = true,
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("সেটিংস", style = MaterialTheme.typography.headlineSmall) },
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
            // ── 1. Appearance ────────────────────────────────────────────────
            item {
                SettingsSectionHeader(icon = Icons.Default.Palette, title = "চেহারা")
            }

            item {
                GlassmorphicCard(
                    modifier = Modifier.fillMaxWidth(),
                    variant = GlassCardVariant.OUTLINED,
                    padding = 16.dp,
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                        // Theme selector
                        Text(
                            "থিম",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                        SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                            val options = listOf(
                                ThemeMode.LIGHT to "লাইট",
                                ThemeMode.DARK to "ডার্ক",
                                ThemeMode.AMOLED to "এমোলেড",
                                ThemeMode.SYSTEM to "সিস্টেম",
                            )
                            options.forEachIndexed { index, (mode, label) ->
                                SegmentedButton(
                                    selected = themeMode == mode,
                                    onClick = { viewModel.setTheme(mode) },
                                    shape = SegmentedButtonDefaults.itemShape(index, options.size),
                                ) {
                                    Icon(
                                        imageVector = when (mode) {
                                            ThemeMode.LIGHT -> Icons.Default.Brightness7
                                            ThemeMode.DARK -> Icons.Default.Brightness4
                                            ThemeMode.AMOLED -> Icons.Default.Smartphone
                                            ThemeMode.SYSTEM -> Icons.Outlined.BrightnessAuto
                                        },
                                        contentDescription = null,
                                        modifier = Modifier.size(16.dp),
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(label, fontSize = 12.sp)
                                }
                            }
                        }

                        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

                        // Font size
                        Text(
                            "ফন্ট সাইজ",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                        val fontSizes = listOf("small" to "ছোট", "medium" to "মাঝারি", "large" to "বড়", "extra_large" to "অতি বড়")
                        SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                            fontSizes.forEachIndexed { index, (sizeKey, label) ->
                                SegmentedButton(
                                    selected = fontSize == sizeKey,
                                    onClick = { viewModel.setFontSize(sizeKey) },
                                    shape = SegmentedButtonDefaults.itemShape(index, fontSizes.size),
                                ) {
                                    Text(
                                        label,
                                        fontSize = when (sizeKey) {
                                            "small" -> 11.sp
                                            "medium" -> 13.sp
                                            "large" -> 15.sp
                                            else -> 17.sp
                                        },
                                    )
                                }
                            }
                        }

                        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

                        // Calendar color coding
                        SettingsToggleRow(
                            title = "ক্যালেন্ডার রঙ কোডিং",
                            subtitle = "ইভেন্ট টাইপ অনুযায়ী রঙ দেখান",
                            checked = calendarColorCoding,
                            onCheckedChange = { calendarColorCoding = it },
                        )
                    }
                }
            }

            // ── 2. Language ──────────────────────────────────────────────────
            item {
                SettingsSectionHeader(icon = Icons.Default.Language, title = "ভাষা")
            }

            item {
                GlassmorphicCard(
                    modifier = Modifier.fillMaxWidth(),
                    variant = GlassCardVariant.OUTLINED,
                    padding = 16.dp,
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                        Text(
                            "অ্যাপ ভাষা",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                        SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                            listOf("bn" to "বাংলা", "en" to "English").forEachIndexed { index, (code, label) ->
                                SegmentedButton(
                                    selected = language == code,
                                    onClick = { viewModel.setLanguage(code) },
                                    shape = SegmentedButtonDefaults.itemShape(index, 2),
                                ) {
                                    Text(label)
                                }
                            }
                        }
                        Text(
                            "পুরো অ্যাপটি বাংলায় দেখুন। নোট পর্যায়ে ভাষা পরিবর্তন নোটস স্ক্রিন থেকে করুন।",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
            }

            // ── 3. Notifications ─────────────────────────────────────────────
            item {
                SettingsSectionHeader(icon = Icons.Default.Notifications, title = "নোটিফিকেশন")
            }

            item {
                GlassmorphicCard(
                    modifier = Modifier.fillMaxWidth(),
                    variant = GlassCardVariant.OUTLINED,
                    padding = 16.dp,
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        SettingsToggleRow(
                            title = "দৈনিক রিমাইন্ডার",
                            subtitle = "প্রতিদিন পড়ার কথা মনে করিয়ে দেবে",
                            checked = dailyReminderEnabled,
                            onCheckedChange = {
                                viewModel.toggleNotification(NotificationType.DAILY_REMINDER)
                            },
                            trailing = {
                                if (dailyReminderEnabled) {
                                    OutlinedButton(
                                        onClick = { showTimePicker = true },
                                        modifier = Modifier.height(36.dp),
                                    ) {
                                        Icon(Icons.Default.AccessTime, modifier = Modifier.size(14.dp), contentDescription = null)
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(dailyReminderTime, fontSize = 13.sp)
                                    }
                                }
                            }
                        )

                        SettingsToggleRow(
                            title = "স্ট্রিক ঝুঁকি সতর্কতা",
                            subtitle = "স্ট্রিক ভাঙার ঝুঁকি থাকলে জানাবে",
                            checked = streakReminderEnabled,
                            onCheckedChange = { viewModel.toggleNotification(NotificationType.STREAK_ALERT) },
                        )

                        SettingsToggleRow(
                            title = "পরীক্ষা কাউন্টডাউন",
                            subtitle = "৩০/১৪/৭/১ দিন আগে পুশ নোটিফিকেশন",
                            checked = examCountdownEnabled,
                            onCheckedChange = { viewModel.toggleNotification(NotificationType.EXAM_COUNTDOWN) },
                        )

                        SettingsToggleRow(
                            title = "দুর্বল বিষয় নাডজ",
                            subtitle = "দুর্বল বিষয়ে অধ্যয়ন বাড়াতে অনুপ্রাণিত করবে",
                            checked = weakSubjectNudge,
                            onCheckedChange = { weakSubjectNudge = it },
                        )

                        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

                        // Silent hours
                        Text(
                            "নীরব সময় (কোচিং ক্লাসের সময়)",
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text("শুরু:", style = MaterialTheme.typography.bodySmall)
                            Slider(
                                value = silentStartHour.toFloat(),
                                onValueChange = { silentStartHour = it.toInt() },
                                valueRange = 0f..23f,
                                steps = 22,
                                modifier = Modifier.weight(1f),
                                colors = SliderDefaults.colors(
                                    thumbColor = Primary,
                                    activeTrackColor = Primary,
                                ),
                            )
                            Text(
                                "${silentStartHour.toString().padStart(2, '0')}:00",
                                style = MaterialTheme.typography.bodyMedium,
                                fontFamily = FontFamily.Monospace,
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text("শেষ:", style = MaterialTheme.typography.bodySmall)
                            Slider(
                                value = silentEndHour.toFloat(),
                                onValueChange = { silentEndHour = it.toInt() },
                                valueRange = 0f..23f,
                                steps = 22,
                                modifier = Modifier.weight(1f),
                                colors = SliderDefaults.colors(
                                    thumbColor = Primary,
                                    activeTrackColor = Primary,
                                ),
                            )
                            Text(
                                "${silentEndHour.toString().padStart(2, '0')}:00",
                                style = MaterialTheme.typography.bodyMedium,
                                fontFamily = FontFamily.Monospace,
                            )
                        }

                        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

                        SettingsToggleRow(
                            title = "সাপ্তাহিক রিপোর্ট",
                            subtitle = "প্রতি সপ্তাহে অধ্যয়ন সারসংক্ষেপ পাঠাবে",
                            checked = weeklySummaryEnabled,
                            onCheckedChange = { viewModel.toggleNotification(NotificationType.WEEKLY_REPORT) },
                        )
                    }
                }
            }

            // ── 4. Focus & Timer ─────────────────────────────────────────────
            item {
                SettingsSectionHeader(icon = Icons.Default.Timer, title = "ফোকাস ও টাইমার")
            }

            item {
                GlassmorphicCard(
                    modifier = Modifier.fillMaxWidth(),
                    variant = GlassCardVariant.OUTLINED,
                    padding = 16.dp,
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                        // Pomodoro duration
                        SettingsSliderRow(
                            title = "ডিফল্ট পমোডোরো সময়",
                            value = pomodoroDurationMin,
                            onValueChange = { pomodoroDurationMin = it },
                            valueRange = 15..60,
                            suffix = "মিনিট",
                        )

                        SettingsSliderRow(
                            title = "ছোট বিরতি",
                            value = shortBreakMin,
                            onValueChange = { shortBreakMin = it },
                            valueRange = 3..15,
                            suffix = "মিনিট",
                        )

                        SettingsSliderRow(
                            title = "লং বিরতি",
                            value = longBreakMin,
                            onValueChange = { longBreakMin = it },
                            valueRange = 10..30,
                            suffix = "মিনিট",
                        )

                        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

                        SettingsToggleRow(
                            title = "পরবর্তী সেশন স্বয়ংক্রিয় শুরু",
                            subtitle = "বিরতির পর পরবর্তী সেশন অটো-শুরু",
                            checked = autoStartNextSession,
                            onCheckedChange = { autoStartNextSession = it },
                        )

                        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

                        // Zen Mode preset
                        Text(
                            "জেন মোড প্রিসেট",
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                        SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                            listOf("light" to "লাইট ব্লক", "strict" to "স্ট্রিক্ট ব্লক").forEachIndexed { index, (key, label) ->
                                SegmentedButton(
                                    selected = zenModePreset == key,
                                    onClick = { zenModePreset = key },
                                    shape = SegmentedButtonDefaults.itemShape(index, 2),
                                ) {
                                    Text(label, fontSize = 12.sp)
                                }
                            }
                        }
                        Text(
                            if (zenModePreset == "light") "লাইট: বিজ্ঞপ্তি বন্ধ, কল গ্রহণযোগ্য" else "স্ট্রিক্ট: সব নোটিফিকেশন ও কল ব্লক",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )

                        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

                        // App whitelist for Zen Mode
                        Text(
                            "জেন মোড অ্যাপ হোয়াইটলিস্ট",
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                        if (appWhitelist.isEmpty()) {
                            Text(
                                "কোনো অ্যাপ যোগ করা হয়নি। জেন মোডে সব অ্যাপ ব্লক হবে।",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        } else {
                            appWhitelist.forEach { app ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(MaterialTheme.colorScheme.surfaceVariant)
                                        .padding(horizontal = 12.dp, vertical = 8.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Text(app, style = MaterialTheme.typography.bodyMedium)
                                    Icon(
                                        Icons.Default.VolumeOff,
                                        contentDescription = "সরান",
                                        modifier = Modifier
                                            .size(18.dp)
                                            .clickable { appWhitelist = appWhitelist - app },
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                    )
                                }
                            }
                        }
                        OutlinedButton(
                            onClick = { /* In production: launch app picker */ },
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Text("অ্যাপ যোগ করুন")
                        }
                    }
                }
            }

            // ── 5. Backup & Sync ─────────────────────────────────────────────
            item {
                SettingsSectionHeader(icon = Icons.Default.CloudOff, title = "ব্যাকআপ ও সিঙ্ক")
            }

            item {
                GlassmorphicCard(
                    modifier = Modifier.fillMaxWidth(),
                    variant = GlassCardVariant.OUTLINED,
                    padding = 16.dp,
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        // Local backup
                        Button(
                            onClick = { viewModel.performBackup() },
                            modifier = Modifier.fillMaxWidth(),
                            enabled = !backupState.isBackingUp,
                            colors = ButtonDefaults.buttonColors(containerColor = Primary),
                        ) {
                            Icon(Icons.Default.Upload, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(if (backupState.isBackingUp) "ব্যাকআপ হচ্ছে..." else "এখনই ব্যাকআপ তৈরি করুন")
                        }

                        backupState.message?.let { msg ->
                            Text(msg, style = MaterialTheme.typography.bodySmall, color = Success)
                        }
                        backupState.error?.let { err ->
                            Text(err, style = MaterialTheme.typography.bodySmall, color = Error)
                        }

                        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

                        // Google Drive (Coming Soon)
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(enabled = false),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                            ),
                            shape = RoundedCornerShape(12.dp),
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Icon(
                                    Icons.Default.Devices,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        "Google Drive ব্যাকআপ",
                                        style = MaterialTheme.typography.titleSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    )
                                    Text(
                                        "শীঘ্রই আসছে — OAuth প্রয়োজন",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Warning,
                                    )
                                }
                            }
                        }

                        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

                        // Auto-backup schedule
                        Text(
                            "অটো-ব্যাকআপ শিডিউল",
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                        SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                            listOf("off" to "বন্ধ", "daily" to "দৈনিক", "weekly" to "সাপ্তাহিক").forEachIndexed { index, (key, label) ->
                                SegmentedButton(
                                    selected = autoBackupSchedule == key,
                                    onClick = { autoBackupSchedule = key },
                                    shape = SegmentedButtonDefaults.itemShape(index, 3),
                                ) {
                                    Text(label, fontSize = 12.sp)
                                }
                            }
                        }

                        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

                        // Recent backups
                        if (recentBackups.isNotEmpty()) {
                            Text(
                                "সাম্প্রতিক ব্যাকআপ",
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.onSurface,
                            )
                            recentBackups.forEach { record ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(MaterialTheme.colorScheme.surfaceVariant)
                                        .padding(10.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Column {
                                        Text(
                                            SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault()).format(Date(record.timestamp)),
                                            style = MaterialTheme.typography.bodySmall,
                                        )
                                        Text(
                                            "${record.fileSizeBytes / 1024} KB",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        )
                                    }
                                    OutlinedButton(
                                        onClick = { viewModel.restoreBackup(record.filePath) },
                                        enabled = !backupState.isRestoring,
                                        modifier = Modifier.height(32.dp),
                                    ) {
                                        Text("রিস্টোর", fontSize = 11.sp)
                                    }
                                }
                            }
                        }

                        // Single-file export
                        OutlinedButton(
                            onClick = { /* In production: launch file picker for export */ },
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Icon(Icons.Default.Download, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("সব ডেটা এক্সপোর্ট (একক ফাইল)")
                        }
                    }
                }
            }

            // ── 6. Privacy & Security ────────────────────────────────────────
            item {
                SettingsSectionHeader(icon = Icons.Default.Lock, title = "গোপনীয়তা ও নিরাপত্তা")
            }

            item {
                GlassmorphicCard(
                    modifier = Modifier.fillMaxWidth(),
                    variant = GlassCardVariant.OUTLINED,
                    padding = 16.dp,
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        // App lock
                        Text(
                            "অ্যাপ লক",
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                        SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                            listOf(LockType.NONE to "কোনোটি নয়", LockType.PIN to "PIN", LockType.BIOMETRIC to "বায়োমেট্রিক").forEachIndexed { index, (type, label) ->
                                SegmentedButton(
                                    selected = lockConfig?.lockType == type.name || (lockConfig == null && type == LockType.NONE),
                                    onClick = {
                                        when (type) {
                                            LockType.NONE -> viewModel.disableAppLock()
                                            LockType.PIN -> showPinDialog = true
                                            LockType.BIOMETRIC -> viewModel.enableAppLock(LockType.BIOMETRIC)
                                        }
                                    },
                                    shape = SegmentedButtonDefaults.itemShape(index, 3),
                                ) {
                                    Icon(
                                        when (type) {
                                            LockType.NONE -> Icons.Default.Lock
                                            LockType.PIN -> Icons.Default.Pin
                                            LockType.BIOMETRIC -> Icons.Default.Fingerprint
                                        },
                                        contentDescription = null,
                                        modifier = Modifier.size(16.dp),
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(label, fontSize = 11.sp)
                                }
                            }
                        }

                        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

                        // Data export
                        OutlinedButton(
                            onClick = { /* In production: export all data as JSON */ },
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("ডেটা এক্সপোর্ট করুন")
                        }

                        // Data delete
                        OutlinedButton(
                            onClick = { showDeleteConfirmDialog = true },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = Error),
                            border = BorderStroke(1.dp, Error),
                        ) {
                            Icon(Icons.Default.DeleteForever, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("সব ডেটা মুছুন")
                        }

                        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

                        SettingsToggleRow(
                            title = "মাল্টি-ডিভাইস সিঙ্ক",
                            subtitle = "লোকাল-ফার্স্ট সিঙ্কিং",
                            checked = multiDeviceSync,
                            onCheckedChange = { multiDeviceSync = it },
                        )
                    }
                }
            }

            // ── 7. About ─────────────────────────────────────────────────────
            item {
                SettingsSectionHeader(icon = Icons.Default.Info, title = "সম্পর্কে")
            }

            item {
                GlassmorphicCard(
                    modifier = Modifier.fillMaxWidth(),
                    variant = GlassCardVariant.OUTLINED,
                    padding = 16.dp,
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text("সংস্করণ", style = MaterialTheme.typography.bodyMedium)
                            Text(
                                "১.০.০ (বিল্ড ১)",
                                style = MaterialTheme.typography.bodyMedium,
                                fontFamily = FontFamily.Monospace,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }

                        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

                        // Rate app
                        SettingsActionRow(
                            title = "অ্যাপ রেট করুন",
                            subtitle = "প্লে স্টোরে রেটিং দিন",
                            icon = Icons.Default.Star,
                            onClick = {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.porashona.studymaster"))
                                context.startActivity(intent)
                            },
                        )

                        // Share app
                        SettingsActionRow(
                            title = "অ্যাপ শেয়ার করুন",
                            subtitle = "বন্ধুদের সাথে শেয়ার করুন",
                            icon = Icons.Default.Share,
                            onClick = {
                                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                    type = "text/plain"
                                    putExtra(Intent.EXTRA_TEXT, "StudyMaster অ্যাপটি ডাউনলোড করুন — সেরা অধ্যয়ন সহকারী! https://play.google.com/store/apps/details?id=com.porashona.studymaster")
                                }
                                context.startActivity(Intent.createChooser(shareIntent, "শেয়ার করুন"))
                            },
                        )
                    }
                }
            }

            // Bottom spacer
            item { Spacer(modifier = Modifier.height(32.dp)) }
        }
    }

    // ── Dialogs ────────────────────────────────────────────────────────────

    // PIN setup dialog
    if (showPinDialog) {
        var pinInput by remember { mutableStateOf("") }
        var pinConfirm by remember { mutableStateOf("") }
        var pinError by remember { mutableStateOf<String?>(null) }

        AlertDialog(
            onDismissRequest = { showPinDialog = false },
            title = { Text("PIN সেটআপ") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedTextField(
                        value = pinInput,
                        onValueChange = {
                            if (it.length <= 6 && it.all { c -> c.isDigit() }) pinInput = it
                        },
                        label = { Text("PIN লিখুন (৪-৬ সংখ্যা)") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                    )
                    OutlinedTextField(
                        value = pinConfirm,
                        onValueChange = {
                            if (it.length <= 6 && it.all { c -> c.isDigit() }) pinConfirm = it
                        },
                        label = { Text("PIN নিশ্চিত করুন") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                    )
                    pinError?.let { Text(it, color = Error, style = MaterialTheme.typography.bodySmall) }
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    if (pinInput.length < 4) {
                        pinError = "কমপক্ষে ৪ সংখ্যার PIN দিন"
                    } else if (pinInput != pinConfirm) {
                        pinError = "PIN মিলছে না"
                    } else {
                        viewModel.enableAppLock(LockType.PIN, pinInput)
                        showPinDialog = false
                    }
                }) { Text("সেভ করুন") }
            },
            dismissButton = {
                TextButton(onClick = { showPinDialog = false }) { Text("বাতিল") }
            },
        )
    }

    // Time picker dialog
    if (showTimePicker) {
        AlertDialog(
            onDismissRequest = { showTimePicker = false },
            title = { Text("রিমাইন্ডার সময় নির্বাচন") },
            text = {
                androidx.compose.material3.TimePicker(state = timePickerState)
            },
            confirmButton = {
                TextButton(onClick = {
                    val h = timePickerState.hour.toString().padStart(2, '0')
                    val m = timePickerState.minute.toString().padStart(2, '0')
                    viewModel.setDailyReminderTime("$h:$m")
                    showTimePicker = false
                }) { Text("সেট করুন") }
            },
            dismissButton = {
                TextButton(onClick = { showTimePicker = false }) { Text("বাতিল") }
            },
        )
    }

    // Delete confirmation dialog
    if (showDeleteConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirmDialog = false },
            title = { Text("সব ডেটা মুছুন?") },
            text = {
                Text(
                    "এই কাজটি পুরোপুরি সমস্ত ডেটা মুছে ফেলবে এবং এটি পূর্বাবস্থায় ফেরানো যাবে না। আপনি কি নিশ্চিত?",
                    color = Error,
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.clearAllData()
                        showDeleteConfirmDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Error),
                ) { Text("হ্যাঁ, মুছুন") }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteConfirmDialog = false }) { Text("বাতিল") }
            },
        )
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Reusable Settings Components
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun SettingsSectionHeader(
    icon: ImageVector,
    title: String,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(top = 8.dp, bottom = 4.dp),
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Primary,
            modifier = Modifier.size(20.dp),
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            title,
            style = MaterialTheme.typography.titleMedium,
            color = Primary,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
private fun SettingsToggleRow(
    title: String,
    subtitle: String = "",
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    trailing: @Composable (() -> Unit)? = null,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(title, style = MaterialTheme.typography.bodyMedium)
            if (subtitle.isNotEmpty()) {
                Text(
                    subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
        trailing?.let { it() } ?: run {
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(checkedThumbColor = OnPrimary, checkedTrackColor = Primary),
            )
        }
    }
}

@Composable
private fun SettingsSliderRow(
    title: String,
    value: Int,
    onValueChange: (Int) -> Unit,
    valueRange: IntRange,
    suffix: String,
) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(title, style = MaterialTheme.typography.bodyMedium)
            Text(
                "$value $suffix",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = Primary,
            )
        }
        Slider(
            value = value.toFloat(),
            onValueChange = { onValueChange(it.toInt()) },
            valueRange = valueRange.first.toFloat()..valueRange.last.toFloat(),
            steps = valueRange.last - valueRange.first - 1,
            modifier = Modifier.fillMaxWidth(),
            colors = SliderDefaults.colors(
                thumbColor = Primary,
                activeTrackColor = Primary,
            ),
        )
    }
}

@Composable
private fun SettingsActionRow(
    title: String,
    subtitle: String,
    icon: ImageVector,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(icon, contentDescription = null, tint = Primary, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, style = MaterialTheme.typography.bodyMedium)
            Text(subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

// Extension function for clearing all data in the ViewModel
private fun SettingsViewModel.clearAllData() {
    // In production, this would wipe the Room database and clear all preferences
}
