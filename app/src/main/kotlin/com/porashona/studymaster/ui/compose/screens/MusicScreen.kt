package com.porashona.studymaster.ui.compose.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material.icons.filled.VolumeOff
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.porashona.studymaster.data.model.MusicCategory
import com.porashona.studymaster.data.model.MusicTrack
import com.porashona.studymaster.data.model.StudyMusicLibrary
import com.porashona.studymaster.ui.compose.components.GlassElevatedCard
import com.porashona.studymaster.ui.compose.components.GlassFilledCard
import com.porashona.studymaster.ui.compose.components.GlassOutlinedCard
import com.porashona.studymaster.ui.compose.theme.AchievementUnlocked
import com.porashona.studymaster.ui.compose.theme.Chart1
import com.porashona.studymaster.ui.compose.theme.Chart2
import com.porashona.studymaster.ui.compose.theme.Chart3
import com.porashona.studymaster.ui.compose.theme.Chart4
import com.porashona.studymaster.ui.compose.theme.Chart5
import com.porashona.studymaster.ui.compose.theme.EnglishFontFamily
import com.porashona.studymaster.ui.compose.theme.Primary
import com.porashona.studymaster.ui.compose.theme.Secondary
import com.porashona.studymaster.ui.compose.theme.Success
import com.porashona.studymaster.ui.compose.theme.TimerShortBreak
import com.porashona.studymaster.ui.compose.theme.Warning
import com.porashona.studymaster.ui.compose.theme.XpBarBg
import com.porashona.studymaster.ui.compose.theme.toBengaliDigits

// ═══════════════════════════════════════════════════════════════════════════════
// MusicScreen — Study music player with Bengali UI
// ═══════════════════════════════════════════════════════════════════════════════

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MusicScreen() {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    // ── State ─────────────────────────────────────────────────────────────────
    var selectedCategory by remember { mutableStateOf<MusicCategory?>(null) }
    var currentTrack by remember { mutableStateOf(StudyMusicLibrary.tracks.first()) }
    var isPlaying by remember { mutableStateOf(false) }
    var playbackSpeed by remember { mutableFloatStateOf(1.0f) }
    var volume by remember { mutableFloatStateOf(0.7f) }
    var sleepTimerMinutes by remember { mutableIntStateOf(0) }
    var showSpeedSelector by remember { mutableStateOf(false) }
    var showSleepTimerSelector by remember { mutableStateOf(false) }

    val filteredTracks = remember(selectedCategory) {
        if (selectedCategory == null) StudyMusicLibrary.tracks
        else StudyMusicLibrary.tracks.filter { it.category == selectedCategory }
    }

    // Simulated playback position
    var playbackPosition by remember { mutableFloatStateOf(0.35f) }
    val infiniteTransition = rememberInfiniteTransition(label = "playback")
    val animatedPosition by infiniteTransition.animateFloat(
        initialValue = playbackPosition,
        targetValue = if (isPlaying) 1f else playbackPosition,
        animationSpec = infiniteRepeatable(
            animation = tween(60000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart,
        ),
        label = "playbackPos",
    )

    // Category data
    val categories = listOf(
        Triple(MusicCategory.LOFI, "লো-ফাই", "🎧", Chart1),
        Triple(MusicCategory.CLASSICAL, "ক্লাসিকাল", "🎻", Chart2),
        Triple(MusicCategory.NATURE, "প্রকৃতি", "🌿", Success),
        Triple(MusicCategory.AMBIENT, "অ্যাম্বিয়েন্ট", "✨", Chart3),
        Triple(MusicCategory.JAZZ, "জ্যাজ", "🎷", AchievementUnlocked),
    )

    val speedOptions = listOf(0.5f, 0.75f, 1.0f, 1.25f, 1.5f, 2.0f)
    val sleepTimerOptions = listOf(0, 15, 30, 45, 60, 90)

    // ── Scaffold ──────────────────────────────────────────────────────────────
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "স্টাডি মিউজিক",
                        style = MaterialTheme.typography.titleLarge,
                    )
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.MusicNote,
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
        bottomBar = {
            // ── Now Playing Bar ────────────────────────────────────────────────
            if (isPlaying || true) { // always show for demo
                NowPlayingBar(
                    track = currentTrack,
                    isPlaying = isPlaying,
                    onPlayPause = { isPlaying = !isPlaying },
                    onNext = {
                        val idx = StudyMusicLibrary.tracks.indexOf(currentTrack)
                        currentTrack = StudyMusicLibrary.tracks[(idx + 1) % StudyMusicLibrary.tracks.size]
                        playbackPosition = 0f
                    },
                    onPrevious = {
                        val idx = StudyMusicLibrary.tracks.indexOf(currentTrack)
                        currentTrack = StudyMusicLibrary.tracks[(idx - 1 + StudyMusicLibrary.tracks.size) % StudyMusicLibrary.tracks.size]
                        playbackPosition = 0f
                    },
                )
            }
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(bottom = 8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {

            // ══════════════════════════════════════════════════════════════════
            // SECTION: Now Playing (expanded)
            // ══════════════════════════════════════════════════════════════════
            item {
                val trackColor = when (currentTrack.category) {
                    MusicCategory.LOFI -> Chart1
                    MusicCategory.CLASSICAL -> Chart2
                    MusicCategory.NATURE -> Success
                    MusicCategory.AMBIENT -> Chart3
                    MusicCategory.JAZZ -> AchievementUnlocked
                }
                GlassElevatedCard(
                    modifier = Modifier.fillMaxWidth(),
                    padding = 24.dp,
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        // Album art placeholder
                        Box(
                            modifier = Modifier
                                .size(160.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .background(
                                    Brush.linearGradient(
                                        colors = listOf(trackColor, trackColor.copy(alpha = 0.4f)),
                                        start = Offset.Zero,
                                        end = Offset(160.dp, 160.dp),
                                    )
                                ),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                "🎵",
                                style = MaterialTheme.typography.displayLarge,
                            )
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        // Track info
                        Text(
                            currentTrack.title,
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                            textAlign = TextAlign.Center,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            currentTrack.artist,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center,
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Progress bar
                        Slider(
                            value = playbackPosition,
                            onValueChange = { playbackPosition = it },
                            colors = SliderDefaults.colors(
                                thumbColor = trackColor,
                                activeTrackColor = trackColor,
                                inactiveTrackColor = XpBarBg,
                            ),
                            modifier = Modifier.fillMaxWidth(),
                        )

                        // Playback controls
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                        ) {
                            // Previous
                            IconButton(onClick = {
                                val idx = StudyMusicLibrary.tracks.indexOf(currentTrack)
                                currentTrack = StudyMusicLibrary.tracks[(idx - 1 + StudyMusicLibrary.tracks.size) % StudyMusicLibrary.tracks.size]
                                playbackPosition = 0f
                            }) {
                                Icon(
                                    imageVector = Icons.Default.SkipPrevious,
                                    contentDescription = "আগে",
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.size(32.dp),
                                )
                            }

                            Spacer(modifier = Modifier.width(16.dp))

                            // Play/Pause
                            FilledTonalIconButton(
                                onClick = { isPlaying = !isPlaying },
                                modifier = Modifier.size(56.dp),
                            ) {
                                Icon(
                                    imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                    contentDescription = if (isPlaying) "বিরতি" else "চালু",
                                    tint = MaterialTheme.colorScheme.onPrimary,
                                    modifier = Modifier.size(32.dp),
                                )
                            }

                            Spacer(modifier = Modifier.width(16.dp))

                            // Next
                            IconButton(onClick = {
                                val idx = StudyMusicLibrary.tracks.indexOf(currentTrack)
                                currentTrack = StudyMusicLibrary.tracks[(idx + 1) % StudyMusicLibrary.tracks.size]
                                playbackPosition = 0f
                            }) {
                                Icon(
                                    imageVector = Icons.Default.SkipNext,
                                    contentDescription = "পরে",
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.size(32.dp),
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Volume control
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                imageVector = if (volume < 0.01f) Icons.Default.VolumeOff else Icons.Default.VolumeUp,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(20.dp),
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Slider(
                                value = volume,
                                onValueChange = { volume = it },
                                colors = SliderDefaults.colors(
                                    thumbColor = Primary,
                                    activeTrackColor = Primary,
                                    inactiveTrackColor = XpBarBg,
                                ),
                                modifier = Modifier.weight(1f),
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                "${(volume * 100).toInt().toBengaliDigits()}%",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontFamily = EnglishFontFamily,
                                ),
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Speed & Sleep Timer row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                        ) {
                            // Speed control
                            FilledTonalButton(
                                onClick = {
                                    showSpeedSelector = !showSpeedSelector
                                    showSleepTimerSelector = false
                                },
                                modifier = Modifier.weight(1f),
                            ) {
                                Icon(
                                    imageVector = Icons.Default.MusicNote,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp),
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    "গতি: ${playbackSpeed}x",
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        fontFamily = EnglishFontFamily,
                                    ),
                                )
                            }

                            // Sleep timer
                            FilledTonalButton(
                                onClick = {
                                    showSleepTimerSelector = !showSleepTimerSelector
                                    showSpeedSelector = false
                                },
                                modifier = Modifier.weight(1f),
                                colors = if (sleepTimerMinutes > 0) {
                                    androidx.compose.material3.ButtonDefaults.filledTonalButtonColors(
                                        containerColor = Warning.copy(alpha = 0.2f),
                                        contentColor = Warning,
                                    )
                                } else {
                                    androidx.compose.material3.ButtonDefaults.filledTonalButtonColors()
                                },
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Alarm,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp),
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    if (sleepTimerMinutes > 0) "ঘুম: ${sleepTimerMinutes.toBengaliDigits()} মি."
                                    else "ঘুমের টাইমার",
                                    style = MaterialTheme.typography.labelMedium,
                                )
                            }
                        }

                        // Speed selector dropdown
                        if (showSpeedSelector) {
                            Spacer(modifier = Modifier.height(8.dp))
                            GlassFilledCard(
                                modifier = Modifier.fillMaxWidth(),
                                tint = Primary,
                                padding = 12.dp,
                            ) {
                                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Text(
                                        "প্লেব্যাক গতি নির্বাচন করুন",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    ) {
                                        speedOptions.forEach { speed ->
                                            val isSelected = playbackSpeed == speed
                                            FilledTonalButton(
                                                onClick = { playbackSpeed = speed },
                                                modifier = Modifier.weight(1f),
                                                colors = androidx.compose.material3.ButtonDefaults.filledTonalButtonColors(
                                                    containerColor = if (isSelected) Primary
                                                    else MaterialTheme.colorScheme.surfaceVariant,
                                                    contentColor = if (isSelected) MaterialTheme.colorScheme.onPrimary
                                                    else MaterialTheme.colorScheme.onSurfaceVariant,
                                                ),
                                            ) {
                                                Text(
                                                    "${speed}x",
                                                    style = MaterialTheme.typography.labelSmall.copy(
                                                        fontFamily = EnglishFontFamily,
                                                    ),
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        // Sleep timer selector dropdown
                        if (showSleepTimerSelector) {
                            Spacer(modifier = Modifier.height(8.dp))
                            GlassFilledCard(
                                modifier = Modifier.fillMaxWidth(),
                                tint = Warning,
                                padding = 12.dp,
                            ) {
                                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Text(
                                        "ঘুমের টাইমার সেট করুন",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    ) {
                                        sleepTimerOptions.forEach { mins ->
                                            val isSelected = sleepTimerMinutes == mins
                                            FilledTonalButton(
                                                onClick = { sleepTimerMinutes = mins },
                                                modifier = Modifier.weight(1f),
                                                colors = androidx.compose.material3.ButtonDefaults.filledTonalButtonColors(
                                                    containerColor = if (isSelected) Warning
                                                    else MaterialTheme.colorScheme.surfaceVariant,
                                                    contentColor = if (isSelected) MaterialTheme.colorScheme.onSurface
                                                    else MaterialTheme.colorScheme.onSurfaceVariant,
                                                ),
                                            ) {
                                                Text(
                                                    if (mins == 0) "বন্ধ"
                                                    else "${mins.toBengaliDigits()} মি.",
                                                    style = MaterialTheme.typography.labelSmall,
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // ══════════════════════════════════════════════════════════════════
            // SECTION: Genre / Mood Categories
            // ══════════════════════════════════════════════════════════════════
            item {
                Text(
                    "ধরন ও মেজাজ",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                )
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    // "All" chip
                    val isAllSelected = selectedCategory == null
                    FilledTonalButton(
                        onClick = { selectedCategory = null },
                        modifier = Modifier.weight(1f),
                        colors = androidx.compose.material3.ButtonDefaults.filledTonalButtonColors(
                            containerColor = if (isAllSelected) Primary
                            else MaterialTheme.colorScheme.surfaceVariant,
                            contentColor = if (isAllSelected) MaterialTheme.colorScheme.onPrimary
                            else MaterialTheme.colorScheme.onSurfaceVariant,
                        ),
                    ) {
                        Text("সব", style = MaterialTheme.typography.labelMedium)
                    }

                    categories.forEach { (cat, label, emoji, color) ->
                        val isSelected = selectedCategory == cat
                        FilledTonalButton(
                            onClick = { selectedCategory = cat },
                            modifier = Modifier.weight(1f),
                            colors = androidx.compose.material3.ButtonDefaults.filledTonalButtonColors(
                                containerColor = if (isSelected) color
                                else MaterialTheme.colorScheme.surfaceVariant,
                                contentColor = if (isSelected) MaterialTheme.colorScheme.onSurface
                                else MaterialTheme.colorScheme.onSurfaceVariant,
                            ),
                        ) {
                            Text(
                                "$emoji $label",
                                style = MaterialTheme.typography.labelSmall,
                            )
                        }
                    }
                }
            }

            // ══════════════════════════════════════════════════════════════════
            // SECTION: Track List
            // ══════════════════════════════════════════════════════════════════
            item {
                Text(
                    "ট্র্যাক তালিকা (${filteredTracks.size.toBengaliDigits()})",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                )
            }

            items(filteredTracks, key = { it.id }) { track ->
                TrackListItem(
                    track = track,
                    isPlaying = isPlaying && currentTrack.id == track.id,
                    onClick = {
                        currentTrack = track
                        isPlaying = true
                        playbackPosition = 0f
                    },
                )
            }

            item { Spacer(modifier = Modifier.height(32.dp)) }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Track list item
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun TrackListItem(
    track: MusicTrack,
    isPlaying: Boolean,
    onClick: () -> Unit,
) {
    val trackColor = when (track.category) {
        MusicCategory.LOFI -> Chart1
        MusicCategory.CLASSICAL -> Chart2
        MusicCategory.NATURE -> Success
        MusicCategory.AMBIENT -> Chart3
        MusicCategory.JAZZ -> AchievementUnlocked
    }

    val bgColor by animateColorAsState(
        targetValue = if (isPlaying) trackColor.copy(alpha = 0.12f) else Color.Transparent,
        animationSpec = tween(300),
        label = "trackBg",
    )

    GlassOutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        padding = 14.dp,
        tint = if (isPlaying) trackColor else null,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Track number / playing indicator
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(
                        if (isPlaying) trackColor.copy(alpha = 0.2f)
                        else MaterialTheme.colorScheme.surfaceVariant
                    ),
                contentAlignment = Alignment.Center,
            ) {
                if (isPlaying) {
                    // Playing bars animation
                    Row(
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.spacedBy(2.dp),
                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp),
                    ) {
                        val infiniteTransition = rememberInfiniteTransition(label = "bars")
                        listOf(0, 1, 2).forEach { i ->
                            val height by infiniteTransition.animateFloat(
                                initialValue = 4f,
                                targetValue = 14f,
                                animationSpec = infiniteRepeatable(
                                    animation = tween(
                                        durationMillis = 400 + i * 150,
                                        easing = FastOutSlowInEasing,
                                    ),
                                    repeatMode = RepeatMode.Reverse,
                                ),
                                label = "bar$i",
                            )
                            Box(
                                modifier = Modifier
                                    .width(3.dp)
                                    .height(height.dp)
                                    .clip(RoundedCornerShape(1.dp))
                                    .background(trackColor),
                            )
                        }
                    }
                } else {
                    Text(
                        "${track.id.toBengaliDigits()}",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontFamily = EnglishFontFamily,
                        ),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Track info
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    track.title,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = if (isPlaying) FontWeight.SemiBold else FontWeight.Normal,
                    ),
                    color = if (isPlaying) trackColor else MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    track.artist,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }

            // Category badge
            val categoryLabel = when (track.category) {
                MusicCategory.LOFI -> "লো-ফাই"
                MusicCategory.CLASSICAL -> "ক্লাসিকাল"
                MusicCategory.NATURE -> "প্রকৃতি"
                MusicCategory.AMBIENT -> "অ্যাম্বিয়েন্ট"
                MusicCategory.JAZZ -> "জ্যাজ"
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(100.dp))
                    .background(trackColor.copy(alpha = 0.15f))
                    .padding(horizontal = 10.dp, vertical = 4.dp),
            ) {
                Text(
                    categoryLabel,
                    style = MaterialTheme.typography.labelSmall,
                    color = trackColor,
                )
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Now Playing Bar (bottom bar)
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun NowPlayingBar(
    track: MusicTrack,
    isPlaying: Boolean,
    onPlayPause: () -> Unit,
    onNext: () -> Unit,
    onPrevious: () -> Unit,
) {
    val trackColor = when (track.category) {
        MusicCategory.LOFI -> Chart1
        MusicCategory.CLASSICAL -> Chart2
        MusicCategory.NATURE -> Success
        MusicCategory.AMBIENT -> Chart3
        MusicCategory.JAZZ -> AchievementUnlocked
    }

    GlassElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        cornerRadius = MaterialTheme.shapes.extraLarge.bottom,
        padding = 12.dp,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Mini album art
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(
                        Brush.linearGradient(
                            colors = listOf(trackColor, trackColor.copy(alpha = 0.4f)),
                        )
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Text("🎵", style = MaterialTheme.typography.labelLarge)
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Track info
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    track.title,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    track.artist,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }

            // Controls
            IconButton(onClick = onPrevious) {
                Icon(
                    imageVector = Icons.Default.SkipPrevious,
                    contentDescription = "আগে",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(28.dp),
                )
            }

            // Play/Pause
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(trackColor)
                    .clickable(onClick = onPlayPause),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                    contentDescription = if (isPlaying) "বিরতি" else "চালু",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(24.dp),
                )
            }

            IconButton(onClick = onNext) {
                Icon(
                    imageVector = Icons.Default.SkipNext,
                    contentDescription = "পরে",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(28.dp),
                )
            }
        }
    }
}