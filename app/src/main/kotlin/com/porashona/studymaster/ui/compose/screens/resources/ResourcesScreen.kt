/*
package com.porashona.studymaster.ui.compose.screens.resources

import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.FilePresent
import androidx.compose.material.icons.filled.HighQuality
import androidx.compose.material.icons.filled.Highlight
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.material.icons.filled.Underline
import androidx.compose.material.icons.filled.VideoCameraBack
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.filled.ZoomIn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.porashona.studymaster.ui.compose.components.GlassCardVariant
import com.porashona.studymaster.ui.compose.components.GlassmorphicCard
import com.porashona.studymaster.ui.compose.theme.*

// ═══════════════════════════════════════════════════════════════════════════════
// ResourcesScreen — PDFs, Videos, Audio, Diagrams tabs.
// All text in Bengali. Glassmorphic cards. Material 3.
// ═══════════════════════════════════════════════════════════════════════════════

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResourcesScreen(
    onNavigateBack: () -> Unit = {},
) {
    var selectedTab by rememberSaveable { mutableIntStateOf(0) }
    val tabs = listOf("PDFs", "ভিডিও", "অডিও", "ডায়াগ্রাম")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("রিসোর্স", style = MaterialTheme.typography.headlineSmall) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "ফিরুন")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background),
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* In production: context-dependent action */ },
                containerColor = Primary,
            ) {
                Icon(
                    when (selectedTab) {
                        0 -> Icons.Default.FilePresent
                        1 -> Icons.Default.Link
                        2 -> Icons.Default.Add
                        3 -> Icons.Default.Image
                        else -> Icons.Default.Add
                    },
                    contentDescription = "যোগ করুন",
                )
            }
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
                0 -> PdfsTab()
                1 -> VideosTab()
                2 -> AudioTab()
                3 -> DiagramsTab()
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// PDFs Tab
// ═══════════════════════════════════════════════════════════════════════════════

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun PdfsTab() {
    var searchQuery by rememberSaveable { mutableStateOf("") }
    var subjectFilter by rememberSaveable { mutableStateOf<String?>(null) }
    var showPdfViewer by remember { mutableStateOf<String?>(null) }
    var showAddPdfDialog by remember { mutableStateOf(false) }
    var showAnnotationTools by remember { mutableStateOf(false) }
    var annotationMode by remember { mutableStateOf("none") } // "highlight", "underline", "note"

    // Sample PDFs
    val pdfs = listOf(
        PdfItem("পদার্থবিজ্ঞান ১ম অধ্যায়", "পদার্থবিজ্ঞান", "24 পৃষ্ঠা", true),
        PdfItem("রসায়ন সংক্ষিপ্ত সিলেবাস", "রসায়ন", "18 পৃষ্ঠা", false),
        PdfItem("উচ্চতর গণিত সূত্রাবলী", "উচ্চতর গণিত", "12 পৃষ্ঠা", true),
        PdfItem("সাধারণ গণিত ট্রিগনোমেট্রি", "সাধারণ গণিত", "15 পৃষ্ঠা", false),
        PdfItem("জীববিজ্ঞান কোষ বিভাজন", "জীববিজ্ঞান", "10 পৃষ্ঠা", false),
    )

    val subjects = pdfs.map { it.subject }.distinct()
    val filtered = pdfs.filter {
        (subjectFilter == null || it.subject == subjectFilter) &&
        (searchQuery.isBlank() || it.title.contains(searchQuery, ignoreCase = true))
    }

    if (showPdfViewer != null) {
        // PDF Viewer (basic page view)
        AlertDialog(
            onDismissRequest = { showPdfViewer = null },
            title = { Text(showPdfViewer ?: "") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    // Annotation toolbar
                    Row(
                        modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        AnnotationButton(
                            icon = Icons.Default.Highlight,
                            label = "হাইলাইট",
                            isActive = annotationMode == "highlight",
                            onClick = { annotationMode = if (annotationMode == "highlight") "none" else "highlight" },
                            color = Chart4,
                        )
                        AnnotationButton(
                            icon = Icons.Default.Underline,
                            label = "আন্ডারলাইন",
                            isActive = annotationMode == "underline",
                            onClick = { annotationMode = if (annotationMode == "underline") "none" else "underline" },
                            color = Primary,
                        )
                        AnnotationButton(
                            icon = Icons.Default.TextFields,
                            label = "মার্জিন নোট",
                            isActive = annotationMode == "note",
                            onClick = { annotationMode = if (annotationMode == "note") "none" else "note" },
                            color = Success,
                        )
                    }

                    // Page view placeholder
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant),
                        contentAlignment = Alignment.Center,
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.Description, contentDescription = null, modifier = Modifier.size(48.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("পৃষ্ঠা ১", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text("PDF ভিউয়ার লোড হচ্ছে...", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }

                    // Auto-split info
                    Text(
                        "ℹ️ অধ্যায় অনুযায়ী অটো-স্প্লিট: ML প্রয়োজন (শীঘ্রই আসছে)",
                        style = MaterialTheme.typography.bodySmall,
                        color = Warning,
                    )
                }
            },
            confirmButton = { TextButton(onClick = { showPdfViewer = null }) { Text("বন্ধ করুন") } },
        )
    }

    if (showAddPdfDialog) {
        AlertDialog(
            onDismissRequest = { showAddPdfDialog = false },
            title = { Text("PDF ইম্পোর্ট করুন") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        label = { Text("ফাইল নির্বাচন করুন") },
                        readOnly = true,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        leadingIcon = { Icon(Icons.Default.FilePresent, null) },
                    )
                    Text("ফাইল পিকার খোলা হবে", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            },
            confirmButton = {
                Button(onClick = { showAddPdfDialog = false }, colors = ButtonDefaults.buttonColors(containerColor = Primary)) {
                    Text("ইম্পোর্ট করুন")
                }
            },
            dismissButton = { TextButton(onClick = { showAddPdfDialog = false }) { Text("বাতিল") } },
        )
    }

    Column(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
        // Search and import
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("PDF খুঁজুন") },
                leadingIcon = { Icon(Icons.Default.Search, null) },
                singleLine = true,
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp),
            )
            OutlinedButton(
                onClick = { showAddPdfDialog = true },
                modifier = Modifier.height(56.dp),
            ) {
                Icon(Icons.Default.Download, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("ইম্পোর্ট", fontSize = 12.sp)
            }
        }

        // Subject filter
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(vertical = 4.dp),
        ) {
            FilterChip(
                selected = subjectFilter == null,
                onClick = { subjectFilter = null },
                label = { Text("সব", fontSize = 12.sp) },
            )
            subjects.forEach { subject ->
                FilterChip(
                    selected = subjectFilter == subject,
                    onClick = { subjectFilter = if (subjectFilter == subject) null else subject },
                    label = { Text(subject, fontSize = 12.sp) },
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // PDF list
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(filtered) { pdf ->
                GlassmorphicCard(
                    modifier = Modifier.fillMaxWidth(),
                    variant = GlassCardVariant.OUTLINED,
                    padding = 14.dp,
                    onClick = { showPdfViewer = pdf.title },
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(Primary.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text("PDF", style = MaterialTheme.typography.labelSmall, color = Primary, fontWeight = FontWeight.Bold)
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(pdf.title, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium, maxLines = 1, overflow = TextOverflow.Ellipsis)
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Card(
                                    colors = CardDefaults.cardColors(containerColor = Primary.copy(alpha = 0.15f)),
                                    shape = RoundedCornerShape(6.dp),
                                ) {
                                    Text(pdf.subject, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp), style = MaterialTheme.typography.labelSmall, color = Primary)
                                }
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(pdf.pageCount, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                        Icon(
                            Icons.Default.Description,
                            contentDescription = "খুলুন",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
            }

            // OCR button
            item {
                Spacer(modifier = Modifier.height(8.dp))
                GlassmorphicCard(
                    modifier = Modifier.fillMaxWidth(),
                    variant = GlassCardVariant.FILLED,
                    tint = Chart6,
                    padding = 14.dp,
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(Icons.Default.CameraAlt, contentDescription = null, tint = Chart6, modifier = Modifier.size(24.dp))
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text("হাতের লেখা OCR", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = Chart6)
                            Text("ক্যামেরা দিয়ে লেখা স্ক্যান করুন → ML Kit টেক্সট রিকগনিশন", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AnnotationButton(
    icon: ImageVector,
    label: String,
    isActive: Boolean,
    onClick: () -> Unit,
    color: Color,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(if (isActive) color.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surfaceVariant)
            .clickable(onClick = onClick)
            .padding(8.dp),
    ) {
        Icon(icon, contentDescription = null, tint = if (isActive) color else MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(18.dp))
        Text(label, fontSize = 10.sp, color = if (isActive) color else MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

private data class PdfItem(
    val title: String,
    val subject: String,
    val pageCount: String,
    isFavorite: Boolean,
)

// ═══════════════════════════════════════════════════════════════════════════════
// Videos Tab
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun VideosTab() {
    var searchQuery by rememberSaveable { mutableStateOf("") }
    var showAddVideoDialog by remember { mutableStateOf(false) }
    var videoLinkInput by remember { mutableStateOf("") }

    val videos = listOf(
        VideoItem("ভেক্টর পরিচিতি", "পদার্থবিজ্ঞান", "১ম অধ্যায়", "https://youtube.com/watch?v=example1", "25:30", false),
        VideoItem("সূক্ষ্মকোণী ত্রিকোণমিতি", "সাধারণ গণিত", "১১শ অধ্যায়", "https://youtube.com/watch?v=example2", "18:45", true),
        VideoItem("যোগাযোগ বিক্রিয়া", "রসায়ন", "৭ম অধ্যায়", "https://youtube.com/watch?v=example3", "32:10", false),
        VideoItem("কোষ বিভাজন", "জীববিজ্ঞান", "৪র্থ অধ্যায়", "https://youtube.com/watch?v=example4", "22:00", true),
        VideoItem("সমাকলন", "উচ্চতর গণিত", "৯ম অধ্যায়", "https://youtube.com/watch?v=example5", "40:15", false),
    )

    val filtered = videos.filter {
        searchQuery.isBlank() || it.title.contains(searchQuery, ignoreCase = true) || it.subject.contains(searchQuery, ignoreCase = true)
    }

    if (showAddVideoDialog) {
        AlertDialog(
            onDismissRequest = { showAddVideoDialog = false },
            title = { Text("ভিডিও লিংক যোগ করুন") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedTextField(
                        value = videoLinkInput,
                        onValueChange = { videoLinkInput = it },
                        label = { Text("YouTube / ভিডিও URL") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        leadingIcon = { Icon(Icons.Default.Link, null) },
                    )
                    Text("ভিডিও ইন-অ্যাপ প্লেব্যাক: WebView স্টাব (শীঘ্রই)", style = MaterialTheme.typography.bodySmall, color = Warning)
                }
            },
            confirmButton = {
                Button(
                    onClick = { showAddVideoDialog = false; videoLinkInput = "" },
                    enabled = videoLinkInput.isNotBlank(),
                    colors = ButtonDefaults.buttonColors(containerColor = Primary),
                ) { Text("যোগ করুন") }
            },
            dismissButton = { TextButton(onClick = { showAddVideoDialog = false }) { Text("বাতিল") } },
        )
    }

    Column(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("ভিডিও খুঁজুন") },
                leadingIcon = { Icon(Icons.Default.Search, null) },
                singleLine = true,
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp),
            )
            OutlinedButton(onClick = { showAddVideoDialog = true }, modifier = Modifier.height(56.dp)) {
                Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("যোগ", fontSize = 12.sp)
            }
        }

        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(filtered) { video ->
                GlassmorphicCard(
                    modifier = Modifier.fillMaxWidth(),
                    variant = GlassCardVariant.OUTLINED,
                    padding = 14.dp,
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        // Thumbnail placeholder
                        Box(
                            modifier = Modifier
                                .size(80.dp, 56.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(MaterialTheme.colorScheme.surfaceVariant),
                            contentAlignment = Alignment.Center,
                        ) {
                            Icon(
                                Icons.Default.VideoCameraBack,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(28.dp),
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(video.title, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium, maxLines = 1, overflow = TextOverflow.Ellipsis, modifier = Modifier.weight(1f))
                                // Watched indicator
                                Box(
                                    modifier = Modifier
                                        .size(20.dp)
                                        .clip(CircleShape)
                                        .background(if (video.isWatched) Success.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surfaceVariant),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    if (video.isWatched) {
                                        Text("✓", fontSize = 10.sp, color = Success)
                                    }
                                }
                            }
                            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                Card(
                                    colors = CardDefaults.cardColors(containerColor = Primary.copy(alpha = 0.15f)),
                                    shape = RoundedCornerShape(6.dp),
                                ) {
                                    Text(video.subject, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp), style = MaterialTheme.typography.labelSmall, color = Primary)
                                }
                                Text(video.chapter, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                            Text("⏱ ${video.duration}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            }
        }
    }
}

private data class VideoItem(
    val title: String,
    val subject: String,
    val chapter: String,
    val url: String,
    val duration: String,
    val isWatched: Boolean,
)

// ═══════════════════════════════════════════════════════════════════════════════
// Audio Tab
// ═════════════════════════════════════════════════════════════════════════════════

@Composable
private fun AudioTab() {
    var currentlyPlaying by remember { mutableStateOf<Int?>(null) }
    var playbackSpeed by rememberSaveable { mutableFloatStateOf(1.0f) }
    var progress by remember { mutableFloatStateOf(0f) }
    val speedOptions = listOf(0.5f, 0.75f, 1.0f, 1.25f, 1.5f, 1.75f, 2.0f)

    val audioLectures = listOf(
        AudioLectureItem("পদার্থবিজ্ঞান — ভেক্টর ব্যাখ্যা", "পদার্থবিজ্ঞান", "25:30", 1530L, 645),
        AudioLectureItem("রসায়ন — ইলেকট্রন বিন্যাস", "রসায়ন", "18:20", 1100L, 412),
        AudioLectureItem("গণিত — ত্রিকোণমিতিক অনুপাত", "সাধারণ গণিত", "22:00", 1320L, 890),
        AudioLectureItem("জীববিজ্ঞান — কোষ প্রক্রিয়া", "জীববিজ্ঞান", "30:15", 1815L, 0),
        AudioLectureItem("উচ্চতর গণিত — ম্যাট্রিক্স", "উচ্চতর গণিত", "35:40", 2140L, 2100),
    )

    Column(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
        Spacer(modifier = Modifier.height(8.dp))

        // Now playing bar
        currentlyPlaying?.let { idx ->
            val audio = audioLectures[idx]
            GlassmorphicCard(
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                variant = GlassCardVariant.ELEVATED,
                tint = Primary,
                padding = 14.dp,
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(audio.title, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
                    // Progress bar
                    Column {
                        Slider(
                            value = progress,
                            onValueChange = { progress = it },
                            modifier = Modifier.fillMaxWidth(),
                            colors = SliderDefaults.colors(thumbColor = Primary, activeTrackColor = Primary),
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text(
                                "${(progress * audio.totalSeconds / 60).toInt()}:${((progress * audio.totalSeconds) % 60).toInt().toString().padStart(2, '0')}",
                                style = MaterialTheme.typography.labelSmall,
                                fontFamily = FontFamily.Monospace,
                            )
                            Text(audio.duration, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant, fontFamily = FontFamily.Monospace)
                        }
                    }
                    // Controls
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        IconButton(onClick = { currentlyPlaying = null }) {
                            Icon(Icons.Default.Pause, contentDescription = "বিরতি")
                        }
                        // Speed control
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Speed, contentDescription = null, modifier = Modifier.size(16.dp), tint = Primary)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("${playbackSpeed}x", style = MaterialTheme.typography.labelMedium, color = Primary, fontFamily = FontFamily.Monospace)
                            Spacer(modifier = Modifier.width(8.dp))
                            Slider(
                                value = playbackSpeed,
                                onValueChange = { playbackSpeed = it },
                                valueRange = 0.5f..2.0f,
                                steps = 5,
                                modifier = Modifier.width(120.dp),
                                colors = SliderDefaults.colors(thumbColor = Primary, activeTrackColor = Primary),
                            )
                        }
                        Text("ব্যাকগ্রাউন্ড ✓", style = MaterialTheme.typography.labelSmall, color = Success)
                    }
                }
            }
        }

        // Audio list
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(audioLectures.size) { index ->
                val audio = audioLectures[index]
                val isPlaying = currentlyPlaying == index
                GlassmorphicCard(
                    modifier = Modifier.fillMaxWidth(),
                    variant = if (isPlaying) GlassCardVariant.ELEVATED else GlassCardVariant.OUTLINED,
                    tint = if (isPlaying) Primary else null,
                    padding = 12.dp,
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        // Play/Pause button
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(if (isPlaying) Primary else MaterialTheme.colorScheme.surfaceVariant)
                                .clickable {
                                    currentlyPlaying = if (isPlaying) null else index
                                    if (!isPlaying) progress = (audio.lastPosition.toFloat() / audio.totalSeconds).coerceIn(0f, 1f)
                                },
                            contentAlignment = Alignment.Center,
                        ) {
                            Icon(
                                if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                contentDescription = null,
                                tint = if (isPlaying) Color.White else MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.size(20.dp),
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(audio.title, style = MaterialTheme.typography.bodyMedium, fontWeight = if (isPlaying) FontWeight.Bold else FontWeight.Normal, maxLines = 1, overflow = TextOverflow.Ellipsis)
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Card(
                                    colors = CardDefaults.cardColors(containerColor = Primary.copy(alpha = 0.15f)),
                                    shape = RoundedCornerShape(6.dp),
                                ) {
                                    Text(audio.subject, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp), style = MaterialTheme.typography.labelSmall, color = Primary)
                                }
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(audio.duration, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                    }
                }
            }
        }
    }
}

private data class AudioLectureItem(
    val title: String,
    val subject: String,
    val duration: String,
    val totalSeconds: Long,
    val lastPosition: Long,
)

// ═══════════════════════════════════════════════════════════════════════════════
// Diagrams Tab
// ═══════════════════════════════════════════════════════════════════════════════

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun DiagramsTab() {
    var searchQuery by rememberSaveable { mutableStateOf("") }
    var subjectFilter by rememberSaveable { mutableStateOf<String?>(null) }
    var showFullScreen by remember { mutableStateOf<String?>(null) }

    val diagrams = listOf(
        DiagramItem("কোষ কাঠামো", "জীববিজ্ঞান", "কোষ", Chart1),
        DiagramItem("হার্ট সার্কিট", "পদার্থবিজ্ঞান", "বিদ্যুৎ", Chart2),
        DiagramItem("পরমাণু মডেল", "রসায়ন", "পরমাণু", Chart3),
        DiagramItem("ট্রায়াঙ্গল", "সাধারণ গণিত", "ত্রিকোণমিতি", Chart4),
        DiagramItem("ফটোসিন্থেসিস", "জীববিজ্ঞান", "উদ্ভিদ", Chart5),
        DiagramItem("অণু জ্যামিতি", "রসায়ন", "রাসায়নিক বন্ধন", Chart6),
        DiagramItem("স্নায়ু কোষ", "জীববিজ্ঞান", "স্নায়ুতন্ত্র", Primary),
        DiagramItem("লেন্স সূত্র", "পদার্থবিজ্ঞান", "আলোকবিজ্ঞান", Secondary),
    )

    val subjects = diagrams.map { it.subject }.distinct()
    val filtered = diagrams.filter {
        (subjectFilter == null || it.subject == subjectFilter) &&
        (searchQuery.isBlank() || it.title.contains(searchQuery, ignoreCase = true) || it.tags.contains(searchQuery, ignoreCase = true))
    }

    if (showFullScreen != null) {
        val diagram = diagrams.find { it.title == showFullScreen }
        AlertDialog(
            onDismissRequest = { showFullScreen = null },
            title = { Text(showFullScreen ?: "") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    // Full-screen diagram viewer placeholder
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(diagram?.color?.copy(alpha = 0.1f) ?: MaterialTheme.colorScheme.surfaceVariant)
                            .clickable { /* In production: pinch to zoom */ },
                        contentAlignment = Alignment.Center,
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                Icons.Default.ZoomIn,
                                contentDescription = null,
                                modifier = Modifier.size(48.dp),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("পিঞ্চ-টু-জুম সক্রিয়", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                    diagram?.let {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Card(colors = CardDefaults.cardColors(containerColor = Primary.copy(alpha = 0.15f)), shape = RoundedCornerShape(6.dp)) {
                                Text(it.subject, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp), style = MaterialTheme.typography.labelSmall, color = Primary)
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("অধ্যায়: ${it.chapter}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            },
            confirmButton = { TextButton(onClick = { showFullScreen = null }) { Text("বন্ধ করুন") } },
        )
    }

    Column(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("ডায়াগ্রাম খুঁজুন") },
            leadingIcon = { Icon(Icons.Default.Search, null) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            shape = RoundedCornerShape(12.dp),
        )

        // Subject filter
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(vertical = 4.dp),
        ) {
            FilterChip(selected = subjectFilter == null, onClick = { subjectFilter = null }, label = { Text("সব", fontSize = 12.sp) })
            subjects.forEach { subject ->
                FilterChip(
                    selected = subjectFilter == subject,
                    onClick = { subjectFilter = if (subjectFilter == subject) null else subject },
                    label = { Text(subject, fontSize = 12.sp) },
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Diagram grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            items(filtered) { diagram ->
                GlassmorphicCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp),
                    variant = GlassCardVariant.OUTLINED,
                    tint = diagram.color,
                    onClick = { showFullScreen = diagram.title },
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp),
                        ) {
                            // Diagram preview placeholder
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(diagram.color.copy(alpha = 0.08f)),
                                contentAlignment = Alignment.Center,
                            ) {
                                Icon(
                                    Icons.Default.Image,
                                    contentDescription = null,
                                    tint = diagram.color.copy(alpha = 0.4f),
                                    modifier = Modifier.size(32.dp),
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                diagram.title,
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                            Text(
                                diagram.subject,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                    }
                }
            }
        }
    }

private data class DiagramItem(
    val title: String,
    val subject: String,
    val chapter: String,
    val color: Color,
    val tags: String = "",
)
*/