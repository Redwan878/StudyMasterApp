package com.porashona.studymaster.ui.compose.screens.collaboration

import android.content.Context
import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonOff
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.porashona.studymaster.ui.compose.components.GlassCardVariant
import com.porashona.studymaster.ui.compose.components.GlassmorphicCard
import com.porashona.studymaster.ui.compose.theme.*
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.roundToInt

// ═══════════════════════════════════════════════════════════════════════════════
// CollaborationScreen — Study rooms, sharing, group goals, discussion threads.
// All text in Bengali. Glassmorphic cards. Material 3.
// ═══════════════════════════════════════════════════════════════════════════════

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollaborationScreen(
    onNavigateBack: () -> Unit = {},
) {
    var selectedTab by rememberSaveable { mutableIntStateOf(0) }
    val tabs = listOf("স্টাডি রুম", "শেয়ার", "গোল ট্র্যাকিং", "আলোচনা")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("সহযোগিতা", style = MaterialTheme.typography.headlineSmall) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "ফিরুন")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background),
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = Primary,
                indicatorColor = Primary,
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(title, fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal) },
                    )
                }
            }

            when (selectedTab) {
                0 -> StudyRoomsTab()
                1 -> ShareTab()
                2 -> GroupGoalTab()
                3 -> DiscussionThreadTab()
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// 1. Study Rooms Tab — Shared-timer co-studying
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun StudyRoomsTab() {
    var showCreateRoomDialog by remember { mutableStateOf(false) }
    var joinedRoomId by remember { mutableStateOf<Long?>(null) }

    val activeRooms = listOf(
        StudyRoomItem(1, "পদার্থবিজ্ঞান পড়া", 4, 10, true, "25:00"),
        StudyRoomItem(2, "রসায়ন মডেল টেস্ট", 2, 8, true, "15:30"),
        StudyRoomItem(3, "গণিত অনুশীলন", 6, 12, true, "40:00"),
    )

    // If user is in a room, show the room view
    if (joinedRoomId != null) {
        val room = activeRooms.find { it.id == joinedRoomId }
        if (room != null) {
            RoomView(
                room = room,
                onLeave = { joinedRoomId = null },
            )
            return
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
        Text(
            "সক্রিয় স্টাডি রুম",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp),
        )

        if (activeRooms.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("কোনো সক্রিয় রুম নেই", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(activeRooms) { room ->
                    GlassmorphicCard(
                        modifier = Modifier.fillMaxWidth(),
                        variant = GlassCardVariant.OUTLINED,
                        padding = 14.dp,
                        onClick = { joinedRoomId = room.id },
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text(room.name, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f), maxLines = 1, overflow = TextOverflow.Ellipsis)
                                // Live indicator
                                Card(
                                    colors = CardDefaults.cardColors(containerColor = Success.copy(alpha = 0.15f)),
                                    shape = RoundedCornerShape(6.dp),
                                ) {
                                    Row(modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp), verticalAlignment = Alignment.CenterVertically) {
                                        Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(Success))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("লাইভ", style = MaterialTheme.typography.labelSmall, color = Success, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(16.dp),
                            ) {
                                // Participants
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Group, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        "${room.participantCount}/${room.maxParticipants}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    )
                                }

                                // Shared timer
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Timer, contentDescription = null, tint = Primary, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        room.timerRemaining,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Primary,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = FontFamily.Monospace,
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Create room FAB
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
        FloatingActionButton(
            onClick = { showCreateRoomDialog = true },
            containerColor = Primary,
            modifier = Modifier.padding(16.dp),
        ) {
            Icon(Icons.Default.Add, contentDescription = "রুম তৈরি করুন")
        }
    }

    // Create room dialog
    if (showCreateRoomDialog) {
        var roomName by remember { mutableStateOf("") }
        var maxParticipants by rememberSaveable { mutableIntStateOf(10) }

        AlertDialog(
            onDismissRequest = { showCreateRoomDialog = false },
            title = { Text("নতুন স্টাডি রুম তৈরি করুন") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedTextField(
                        value = roomName,
                        onValueChange = { roomName = it },
                        label = { Text("রুমের নাম") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                    )
                    Text(
                        "সর্বোচ্চ অংশগ্রহণকারী: ${maxParticipants}",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = { showCreateRoomDialog = false },
                    enabled = roomName.isNotBlank(),
                    colors = ButtonDefaults.buttonColors(containerColor = Primary),
                ) { Text("তৈরি করুন") }
            },
            dismissButton = { TextButton(onClick = { showCreateRoomDialog = false }) { Text("বাতিল") } },
        )
    }
}

@Composable
private fun RoomView(
    room: StudyRoomItem,
    onLeave: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        // Room header
        GlassmorphicCard(
            modifier = Modifier.fillMaxWidth(),
            variant = GlassCardVariant.ELEVATED,
            tint = Primary,
            padding = 20.dp,
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(room.name, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Group, contentDescription = null, tint = MaterialTheme.colorScheme.onSurface, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("${room.participantCount} জন অংশগ্রহণকারী", style = MaterialTheme.typography.bodyMedium)
                    }
                    Card(colors = CardDefaults.cardColors(containerColor = Success.copy(alpha = 0.15f)), shape = RoundedCornerShape(6.dp)) {
                        Row(modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
                            Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(Success))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("লাইভ", style = MaterialTheme.typography.labelMedium, color = Success, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // Shared timer display
        GlassmorphicCard(
            modifier = Modifier.fillMaxWidth().height(200.dp),
            variant = GlassCardVariant.FILLED,
            tint = Primary,
            padding = 24.dp,
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text("ভাগ করা টাইমার", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    room.timerRemaining,
                    style = MaterialTheme.typography.displaySmall.copy(fontFamily = FontFamily.Monospace),
                    color = Primary,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text("সবাই একসাথে পড়ছে...", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }

        // Participants list
        Text("অংশগ্রহণকারী", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)

        val participantNames = listOf(
            "আপনি", "রাহুল", "তানভীর", "ফাতেমা",
        )
        participantNames.forEachIndexed { index, name ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(SubjectPalette.colorForIndex(index).copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        name.take(1),
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold,
                        color = SubjectPalette.colorForIndex(index),
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Text(name, style = MaterialTheme.typography.bodyMedium)
                if (index == 0) {
                    Spacer(modifier = Modifier.weight(1f))
                    Text("(আপনি)", style = MaterialTheme.typography.labelSmall, color = Primary)
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Leave room button
        OutlinedButton(
            onClick = onLeave,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Error),
            border = androidx.compose.foundation.BorderStroke(1.dp, Error),
        ) {
            Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("রুম ছাড়ুন")
        }
    }
}

private data class StudyRoomItem(
    val id: Long,
    val name: String,
    val participantCount: Int,
    val maxParticipants: Int,
    val isActive: Boolean,
    val timerRemaining: String,
)

// ═══════════════════════════════════════════════════════════════════════════════
// 2. Share Tab — One-tap share
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun ShareTab() {
    val context = LocalContext.current

    val shareItems = listOf(
        ShareItem("ভেক্টর সূত্র শিট", "পদার্থবিজ্ঞান", Icons.Default.Share),
        ShareItem("ত্রিকোণমিতিক সূত্র", "সাধারণ গণিত", Icons.Default.Share),
        ShareItem("রাসায়নিক বন্ধন নোটস", "রসায়ন", Icons.Default.Share),
        ShareItem("মডেল টেস্ট প্রস্তুতি", "সব বিষয়", Icons.Default.Share),
        ShareItem("সংক্ষিপ্ত সিলেবাস চেকলিস্ট", "সব বিষয়", Icons.Default.Share),
    )

    Column(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
        Text(
            "এক-ক্লিক শেয়ার",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp),
        )
        Text(
            "ফর্মুলা শিট বা নোট বন্ধুদের সাথে শেয়ার করুন",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 12.dp),
        )

        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(shareItems) { item ->
                GlassmorphicCard(
                    modifier = Modifier.fillMaxWidth(),
                    variant = GlassCardVariant.OUTLINED,
                    padding = 14.dp,
                    onClick = {
                        val shareIntent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_TEXT, "StudyMaster থেকে শেয়ার: ${item.title}\n\nStudyMaster অ্যাপটি ডাউনলোড করুন!")
                        }
                        context.startActivity(Intent.createChooser(shareIntent, "শেয়ার করুন"))
                    },
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(Primary.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center,
                        ) {
                            Icon(item.icon, contentDescription = null, tint = Primary, modifier = Modifier.size(20.dp))
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(item.title, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
                            Text(item.subject, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Icon(Icons.Default.Share, contentDescription = "শেয়ার", tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(20.dp))
                    }
                }
            }
        }
    }
}

private data class ShareItem(
    val title: String,
    val subject: String,
    val icon: ImageVector,
)

// ═══════════════════════════════════════════════════════════════════════════════
// 3. Group Goal Tracking
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun GroupGoalTab() {
    val groupGoals = listOf(
        GroupGoalItem("সপ্তাহে ২০ ঘণ্টা অধ্যয়ন", 14.5f, 20f, 4),
        GroupGoalItem("সব অধ্যায় সম্পন্ন করা", 18f, 30f, 6),
        GroupGoalItem("প্রতিদিন MCQ অনুশীলন", 5f, 7f, 3),
    )

    Column(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
        Text(
            "গ্রুপ গোল ট্র্যাকিং",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp),
        )
        Text(
            "বন্ধুদের সাথে একসাথে গোল অর্জন করুন",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 12.dp),
        )

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(groupGoals) { goal ->
                val progress = (goal.current / goal.target).coerceIn(0f, 1f)
                GlassmorphicCard(
                    modifier = Modifier.fillMaxWidth(),
                    variant = GlassCardVariant.OUTLINED,
                    padding = 16.dp,
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text(goal.title, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f), maxLines = 1, overflow = TextOverflow.Ellipsis)
                            Text(
                                "${(progress * 100).toInt().toBengaliDigits()}%",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = if (progress >= 1f) Success else Primary,
                            )
                        }

                        // Progress bar
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(10.dp)
                                .clip(RoundedCornerShape(5.dp))
                                .background(MaterialTheme.colorScheme.surfaceVariant),
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(progress)
                                    .height(10.dp)
                                    .clip(RoundedCornerShape(5.dp))
                                    .background(if (progress >= 1f) Success else Primary),
                            )
                        }

                        // Member contributions
                        Text(
                            "সদস্য অবদান (${goal.memberCount.toBengaliDigits()} জন):",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            listOf(
                                "আপনি" to 4.5f,
                                "রাহুল" to 3.8f,
                                "তানভীর" to 3.2f,
                                "ফাতেমা" to 3.0f,
                            ).take(goal.memberCount).forEach { (name, contribution) ->
                                Column(
                                    modifier = Modifier.weight(1f),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(28.dp)
                                            .clip(CircleShape)
                                            .background(Primary.copy(alpha = 0.15f)),
                                        contentAlignment = Alignment.Center,
                                    ) {
                                        Text(name.take(1), style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = Primary)
                                    }
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(name, style = MaterialTheme.typography.labelSmall, maxLines = 1, overflow = TextOverflow.Ellipsis, fontSize = 9.sp)
                                    Text(
                                        contribution.toBengaliDigits(decimalPlaces = 1),
                                        style = MaterialTheme.typography.labelSmall,
                                        fontFamily = FontFamily.Monospace,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
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

private data class GroupGoalItem(
    val title: String,
    val current: Float,
    val target: Float,
    val memberCount: Int,
)

// ═══════════════════════════════════════════════════════════════════════════════
// 4. Discussion Thread Tab — Per-chapter discussion
// ═══════════════════════════════════════════════════════════════════════════════

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DiscussionThreadTab() {
    var selectedChapter by rememberSaveable { mutableStateOf("ভেক্টর (অধ্যায় ১)") }
    var showAddPostDialog by remember { mutableStateOf(false) }
    var anonymousMode by rememberSaveable { mutableStateOf(false) }

    val chapters = listOf(
        "ভেক্টর (অধ্যায় ১)",
        "গতি (অধ্যায় ২)",
        "বল (অধ্যায় ৩)",
        "কাজ ও শক্তি (অধ্যায় ৪)",
        "যোগাযোগ বিক্রিয়া (অধ্যায় ৭)",
        "ইলেকট্রন বিন্যাস (অধ্যায় ৪)",
        "ত্রিকোণমিতি (অধ্যায় ১১)",
        "সমাকলন (অধ্যায় ৯)",
    )

    val posts = remember {
        mutableStateListOf(
            DiscussionPostItem("রাহুল", "ভেক্টর যোগফলের সূত্রটি কি কেউ বুঝিয়ে বলতে পারবেন?", false, System.currentTimeMillis() - 3600000, 3),
            DiscussionPostItem("ফাতেমা", "A·B = |A||B|cosθ হলো ডট পণ্য। ক্রস পণ্য হলো A×B = |A||B|sinθ n̂", false, System.currentTimeMillis() - 1800000, 1),
            DiscussionPostItem("বেনামী", "আগামী পরীক্ষায় এই অধ্যায় থেকে নিশ্চয়ই আসবে। সবাই ভালো করে পড়ো!", true, System.currentTimeMillis() - 600000, 5),
        )
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Chapter selector
        ExposedDropdownMenuBox(
            expanded = false,
            onExpandedChange = {},
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        ) {
            OutlinedTextField(
                value = selectedChapter,
                onValueChange = {},
                readOnly = true,
                label = { Text("অধ্যায় নির্বাচন করুন") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = false) },
                modifier = Modifier.fillMaxWidth().menuAnchor(MenuAnchorType.PrimaryNotEditable),
                shape = RoundedCornerShape(12.dp),
            )
            // Dropdown would be implemented with proper state management
        }

        // Anonymous toggle and post count
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                "${posts.size.toBengaliDigits()} টি পোস্ট",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    if (anonymousMode) Icons.Default.VisibilityOff else Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = if (anonymousMode) Warning else MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text("বেনামী", style = MaterialTheme.typography.bodySmall, color = if (anonymousMode) Warning else MaterialTheme.colorScheme.onSurfaceVariant)
                Switch(
                    checked = anonymousMode,
                    onCheckedChange = { anonymousMode = it },
                    colors = SwitchDefaults.colors(checkedTrackColor = Warning),
                )
            }
        }

        HorizontalDivider(
            color = MaterialTheme.colorScheme.outlineVariant,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
        )

        // Posts list
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        ) {
            items(posts) { post ->
                DiscussionPostCard(post = post)
            }

            item { Spacer(modifier = Modifier.height(72.dp)) }
        }

        // FAB for new post
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
            FloatingActionButton(
                onClick = { showAddPostDialog = true },
                containerColor = Primary,
                modifier = Modifier.padding(16.dp),
            ) {
                Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "নতুন পোস্ট")
            }
        }
    }

    // Add post dialog
    if (showAddPostDialog) {
        var postContent by remember { mutableStateOf("") }
        AlertDialog(
            onDismissRequest = { showAddPostDialog = false },
            title = { Text("নতুন পোস্ট") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedTextField(
                        value = postContent,
                        onValueChange = { postContent = it },
                        label = { Text("আপনার প্রশ্ন বা মন্তব্য লিখুন") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        minLines = 3,
                        maxLines = 6,
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(if (anonymousMode) Icons.Default.VisibilityOff else Icons.Default.Person, contentDescription = null, modifier = Modifier.size(16.dp), tint = if (anonymousMode) Warning else MaterialTheme.colorScheme.onSurfaceVariant)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(if (anonymousMode) "বেনামী পোস্ট হবে" else "আপনার নাম দেখাবে", style = MaterialTheme.typography.bodySmall, color = if (anonymousMode) Warning else MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Switch(
                            checked = anonymousMode,
                            onCheckedChange = { anonymousMode = it },
                            colors = SwitchDefaults.colors(checkedTrackColor = Warning),
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (postContent.isNotBlank()) {
                            posts.add(0, DiscussionPostItem(
                                if (anonymousMode) "বেনামী" else "আপনি",
                                postContent,
                                anonymousMode,
                                System.currentTimeMillis(),
                                0,
                            ))
                            postContent = ""
                            showAddPostDialog = false
                        }
                    },
                    enabled = postContent.isNotBlank(),
                    colors = ButtonDefaults.buttonColors(containerColor = Primary),
                ) { Text("পোস্ট করুন") }
            },
            dismissButton = { TextButton(onClick = { showAddPostDialog = false }) { Text("বাতিল") } },
        )
    }
}

@Composable
private fun DiscussionPostCard(post: DiscussionPostItem) {
    GlassmorphicCard(
        modifier = Modifier.fillMaxWidth(),
        variant = GlassCardVariant.OUTLINED,
        padding = 14.dp,
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            // Author row
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(
                            if (post.isAnonymous) MaterialTheme.colorScheme.surfaceVariant
                            else Primary.copy(alpha = 0.15f)
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    if (post.isAnonymous) {
                        Icon(Icons.Default.VisibilityOff, contentDescription = null, modifier = Modifier.size(14.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
                    } else {
                        Text(
                            post.authorName.take(1),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = Primary,
                        )
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    post.authorName,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    color = if (post.isAnonymous) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSurface,
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    formatTimeAgo(post.timestamp),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }

            // Content
            Text(
                post.content,
                style = MaterialTheme.typography.bodyMedium,
                lineHeight = 22.sp,
            )

            // Reply count
            if (post.replyCount > 0) {
                Text(
                    "${post.replyCount.toBengaliDigits()} টি উত্তর",
                    style = MaterialTheme.typography.labelSmall,
                    color = Primary,
                    modifier = Modifier.clickable { /* Navigate to replies */ },
                )
            }
        }
    }
}

private data class DiscussionPostItem(
    val authorName: String,
    val content: String,
    val isAnonymous: Boolean,
    val timestamp: Long,
    val replyCount: Int,
)

private fun formatTimeAgo(timestamp: Long): String {
    val diff = (System.currentTimeMillis() - timestamp) / 1000
    return when {
        diff < 60 -> "এইমাত্র"
        diff < 3600 -> "${(diff / 60).toInt().toBengaliDigits()} মিনিট আগে"
        diff < 86400 -> "${(diff / 3600).toInt().toBengaliDigits()} ঘণ্টা আগে"
        else -> "${(diff / 86400).toInt().toBengaliDigits()} দিন আগে"
    }
}