/*
package com.porashona.studymaster.ui.compose.screens.exams

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.porashona.studymaster.data.model.ChapterStatus
import com.porashona.studymaster.data.model.Exam
import com.porashona.studymaster.ui.compose.components.ActivityTagChip
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.saveable.rememberSaveable
import com.porashona.studymaster.ui.compose.components.ActivityType
import com.porashona.studymaster.ui.compose.components.ActivityColors
import com.porashona.studymaster.ui.compose.components.GlassElevatedCard
import com.porashona.studymaster.ui.compose.components.GlassFilledCard
import com.porashona.studymaster.ui.compose.components.GlassOutlinedCard
import com.porashona.studymaster.ui.compose.theme.*
import com.porashona.studymaster.ui.compose.viewmodels.ExamCountdown
import com.porashona.studymaster.ui.compose.viewmodels.ExamEvent
import com.porashona.studymaster.ui.compose.viewmodels.ExamPrepChecklist
import com.porashona.studymaster.ui.compose.viewmodels.ExamsViewModel
import com.porashona.studymaster.ui.compose.viewmodels.SubjectGPA
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

// ═══════════════════════════════════════════════════════════════════════════════
// Exam types for Bangladesh education system
// ═══════════════════════════════════════════════════════════════════════════════

enum class BdExamType(val bengaliLabel: String) {
    MODEL_TEST("মডেল টেস্ট"),
    BOARD_EXAM("বোর্ড পরীক্ষা"),
    ADMISSION_TEST("ভর্তি পরীক্ষা");

    companion object {
        fun fromLabel(label: String): BdExamType =
            entries.firstOrNull { it.bengaliLabel == label } ?: MODEL_TEST
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// GPA Calculator data class (local UI state)
// ═══════════════════════════════════════════════════════════════════════════════

data class GpaSubjectEntry(
    val subjectName: String = "",
    val marks: String = "",
    val isFourthSubject: Boolean = false,
    val gradePoint: Double = 0.0,
    val gradeLabel: String = ""
)

// ═══════════════════════════════════════════════════════════════════════════════
// SSC 2026 Short Syllabus — sample chapter data for common subjects
// ═══════════════════════════════════════════════════════════════════════════════

data class ShortSyllabusChapter(
    val number: Int,
    val name: String,
    val isCompleted: Boolean = false
)

val sscShortSyllabusMap: Map<String, List<ShortSyllabusChapter>> = mapOf(
    "বাংলা" to listOf(
        ShortSyllabusChapter(1, "সাহিত্য পরিচিতি"),
        ShortSyllabusChapter(2, "রবীন্দ্রনাথ ঠাকুর"),
        ShortSyllabusChapter(3, "কাজী নজরুল ইসলাম"),
        ShortSyllabusChapter(4, "প্রবন্ধ রচনা"),
        ShortSyllabusChapter(5, "নাটক"),
        ShortSyllabusChapter(6, "ব্যাকরণ ও নির্মিতি"),
        ShortSyllabusChapter(7, "চিঠি ও প্রতিবেদন"),
    ),
    "English" to listOf(
        ShortSyllabusChapter(1, "Seen Comprehension"),
        ShortSyllabusChapter(2, "Unseen Comprehension"),
        ShortSyllabusChapter(3, "Paragraph Writing"),
        ShortSyllabusChapter(4, "Essay Writing"),
        ShortSyllabusChapter(5, "Letter / Email"),
        ShortSyllabusChapter(6, "Grammar"),
    ),
    "গণিত" to listOf(
        ShortSyllabusChapter(1, "বীজগণিত"),
        ShortSyllabusChapter(2, "জ্যামিতি"),
        ShortSyllabusChapter(3, "ত্রিকোণমিতি"),
        ShortSyllabusChapter(4, "পরিমিতি"),
        ShortSyllabusChapter(5, "পরিসংখ্যান"),
        ShortSyllabusChapter(6, "সম্ভাবনা"),
    ),
    "পদার্থবিজ্ঞান" to listOf(
        ShortSyllabusChapter(1, "ভৌত রাশি ও পরিমাপ"),
        ShortSyllabusChapter(2, "গতি"),
        ShortSyllabusChapter(3, "বল"),
        ShortSyllabusChapter(4, "কাজ, ক্ষমতা ও শক্তি"),
        ShortSyllabusChapter(5, "শব্দ"),
        ShortSyllabusChapter(6, "আলোর প্রতিফলন ও প্রতিসরণ"),
        ShortSyllabusChapter(7, "তড়িৎ"),
        ShortSyllabusChapter(8, "মাধ্যাকর্ষণ"),
    ),
    "রসায়ন" to listOf(
        ShortSyllabusChapter(1, "রসায়নের ভূমিকা"),
        ShortSyllabusChapter(2, "পরমাণু মডেল"),
        ShortSyllabusChapter(3, "পর্যায় সারণি"),
        ShortSyllabusChapter(4, "রাসায়নিক বন্ধন"),
        ShortSyllabusChapter(5, "রাসায়নিক প্রতিক্রিয়া"),
        ShortSyllabusChapter(6, "অম্ল ও ক্ষার"),
    ),
    "জীববিজ্ঞান" to listOf(
        ShortSyllabusChapter(1, "কোষ ও এর গঠন"),
        ShortSyllabusChapter(2, "কোষ বিভাজন"),
        ShortSyllabusChapter(3, "জীবের শ্রেণিবিন্যাস"),
        ShortSyllabusChapter(4, "মানবদেহের প্রতিরক্ষা"),
        ShortSyllabusChapter(5, "হরমোন"),
        ShortSyllabusChapter(6, "বংশগতি ও বিবর্তন"),
    ),
    "তথ্য ও যোগাযোগ প্রযুক্তি" to listOf(
        ShortSyllabusChapter(1, "যোগাযোগ ব্যবস্থা"),
        ShortSyllabusChapter(2, "ইন্টারনেট"),
        ShortSyllabusChapter(3, "ওয়েব ডিজাইন ও HTML"),
        ShortSyllabusChapter(4, "ডাটাবেস"),
        ShortSyllabusChapter(5, "প্রোগ্রামিং"),
    ),
    "বাংলাদেশ ও বিশ্বপরিচয়" to listOf(
        ShortSyllabusChapter(1, "ভৌগোলিক অবস্থান"),
        ShortSyllabusChapter(2, "প্রাকৃতিক পরিবেশ"),
        ShortSyllabusChapter(3, "অর্থনীতি"),
        ShortSyllabusChapter(4, "ইতিহাস"),
        ShortSyllabusChapter(5, "সংস্কৃতি"),
    ),
)

// ═══════════════════════════════════════════════════════════════════════════════
// Main ExamsScreen
// ═══════════════════════════════════════════════════════════════════════════════

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExamsScreen(
    viewModel: ExamsViewModel = hiltViewModel(),
) {
    val exams by viewModel.exams.collectAsState()
    val upcomingExams by viewModel.upcomingExams.collectAsState()
    val countdownData by viewModel.countdownData.collectAsState()
    val events by viewModel.events.collectAsState()
    val gpaData by viewModel.gpaData.collectAsState()
    val overallGPA by viewModel.overallGPA.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    // Screen state
    var selectedTab by rememberSaveable { mutableIntStateOf(0) }
    var showAddExamDialog by rememberSaveable { mutableStateOf(false) }
    var showGpaCalculator by rememberSaveable { mutableStateOf(false) }
    var hscHeadStartEnabled by rememberSaveable { mutableStateOf(false) }

    // Event handling
    LaunchedEffect(events) {
        val event = events ?: return@LaunchedEffect
        when (event) {
            is ExamEvent.ExamCreated -> {
                snackbarHostState.showSnackbar(
                    message = "পরীক্ষা যোগ হয়েছে!",
                    duration = SnackbarDuration.Short
                )
            }
            is ExamEvent.ExamDeleted -> {
                val result = snackbarHostState.showSnackbar(
                    message = "পরীক্ষা মুছে ফেলা হয়েছে",
                    actionLabel = "বাতিল",
                    duration = SnackbarDuration.Short
                )
                if (result == SnackbarResult.ActionPerformed) { /* undo */ }
            }
            is ExamEvent.ExamCompleted -> {
                snackbarHostState.showSnackbar(
                    message = "পরীক্ষা সম্পন্ন হয়েছে!",
                    duration = SnackbarDuration.Short
                )
            }
        }
        viewModel.clearEvent()
    }

    // Content based on tab
    if (showGpaCalculator) {
        GpaCalculatorScreen(
            viewModel = viewModel,
            onBack = { showGpaCalculator = false }
        )
        return
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .statusBarsPadding()
            .navigationBarsPadding()
            .imePadding(),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            Column {
                androidx.compose.material3.TopAppBar(
                    title = {
                        Text(
                            text = "পরীক্ষা",
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                    },
                    actions = {
                        IconButton(onClick = { showGpaCalculator = true }) {
                            Icon(
                                imageVector = Icons.Default.Calculate,
                                contentDescription = "জিপিএ ক্যালকুলেটর",
                                tint = MaterialTheme.colorScheme.primary,
                            )
                        }
                        IconButton(onClick = { showAddExamDialog = true }) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "পরীক্ষা যোগ করুন",
                                tint = MaterialTheme.colorScheme.primary,
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
                    ),
                    scrollBehavior = scrollBehavior,
                )
                TabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
                    contentColor = MaterialTheme.colorScheme.onSurface,
                    indicator = { tabPositions ->
                        if (selectedTab < tabPositions.size) {
                            TabRowDefaults.SecondaryIndicator(
                                modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                                color = MaterialTheme.colorScheme.primary,
                            )
                        }
                    },
                    divider = {},
                ) {
                    Tab(
                        selected = selectedTab == 0,
                        onClick = { selectedTab = 0 },
                        text = { Text("সব পরীক্ষা") },
                        selectedContentColor = MaterialTheme.colorScheme.primary,
                        unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Tab(
                        selected = selectedTab == 1,
                        onClick = { selectedTab = 1 },
                        text = { Text("সিলেবাস") },
                        selectedContentColor = MaterialTheme.colorScheme.primary,
                        unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Tab(
                        selected = selectedTab == 2,
                        onClick = { selectedTab = 2 },
                        text = { Text("ভর্তি পরীক্ষা") },
                        selectedContentColor = MaterialTheme.colorScheme.primary,
                        unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Tab(
                        selected = selectedTab == 3,
                        onClick = { selectedTab = 3 },
                        text = { Text("আর্কাইভ") },
                        selectedContentColor = MaterialTheme.colorScheme.primary,
                        unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        },
        floatingActionButton = {
            if (selectedTab != 3) {
                FloatingActionButton(
                    onClick = { showAddExamDialog = true },
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = OnPrimary,
                    shape = RoundedCornerShape(LocalGlassShapes.current.buttonRadius),
                ) {
                    Icon(Icons.Default.Add, contentDescription = "পরীক্ষা যোগ করুন")
                }
            }
        },
    ) { innerPadding ->
        when (selectedTab) {
            0 -> ExamListContent(
                modifier = Modifier.padding(innerPadding),
                exams = exams,
                countdownData = countdownData,
                upcomingExams = upcomingExams,
                hscHeadStartEnabled = hscHeadStartEnabled,
                onHscToggle = { hscHeadStartEnabled = it },
                onExamClick = { /* navigate to exam detail */ },
                onAddExam = { showAddExamDialog = true },
                onGpaClick = { showGpaCalculator = true },
                viewModel = viewModel,
            )
            1 -> SyllabusTab(
                modifier = Modifier.padding(innerPadding),
                viewModel = viewModel,
            )
            2 -> AdmissionTestTab(
                modifier = Modifier.padding(innerPadding),
            )
            3 -> BoardQuestionArchive(
                modifier = Modifier.padding(innerPadding),
            )
        }
    }

    if (showAddExamDialog) {
        AddExamDialog(
            onDismiss = { showAddExamDialog = false },
            onAdd = { name, _, subjectName, date, time, examType ->
                viewModel.addExam(
                    name = "$examType: $name",
                    subjectName = subjectName,
                    examDate = date,
                    examTime = time,
                    notes = examType,
                )
                showAddExamDialog = false
            }
        )
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Exam List Content — Tab 0
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun ExamListContent(
    modifier: Modifier = Modifier,
    exams: List<Exam>,
    countdownData: List<ExamCountdown>,
    upcomingExams: List<ExamCountdown>,
    hscHeadStartEnabled: Boolean,
    onHscToggle: (Boolean) -> Unit,
    onExamClick: (Exam) -> Unit,
    onAddExam: () -> Unit,
    onGpaClick: () -> Unit,
    viewModel: ExamsViewModel,
) {
    val nextExam = upcomingExams.firstOrNull()
    val isDark = MaterialTheme.colorScheme.isDark

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        // ── Countdown Banner ───────────────────────────────────────────────
        if (nextExam != null) {
            item(key = "countdown_banner") {
                ExamCountdownBanner(
                    countdown = nextExam,
                    onClick = { onExamClick(nextExam.exam) },
                )
            }
        }

        // ── HSC Head-Start Toggle ──────────────────────────────────────────
        item(key = "hsc_toggle") {
            GlassElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                padding = 16.dp,
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "এইচএসসি হেড-স্টার্ট ট্র্যাক",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                        Text(
                            text = "এসএসসি শেষে HSC প্রস্তুতি শুরু করুন",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                    Switch(
                        checked = hscHeadStartEnabled,
                        onCheckedChange = onHscToggle,
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = OnPrimary,
                            checkedTrackColor = Primary,
                        ),
                    )
                }
            }
        }

        // ── Quick Action: GPA Calculator ──────────────────────────────────
        item(key = "gpa_action") {
            GlassFilledCard(
                modifier = Modifier.fillMaxWidth(),
                tint = Primary,
                padding = 16.dp,
                onClick = onGpaClick,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Icon(
                        imageVector = Icons.Default.Calculate,
                        contentDescription = null,
                        tint = OnPrimary,
                        modifier = Modifier.size(28.dp),
                    )
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "জিপিএ ক্যালকুলেটর",
                            style = MaterialTheme.typography.titleMedium,
                            color = OnPrimary,
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            text = "বাংলাদেশ ৫.০ স্কেলে GPA হিসাব করুন",
                            style = MaterialTheme.typography.bodySmall,
                            color = OnPrimary.copy(alpha = 0.8f),
                        )
                    }
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = null,
                        tint = OnPrimary.copy(alpha = 0.7f),
                        modifier = Modifier.size(20.dp),
                    )
                }
            }
        }

        // ── Section header ─────────────────────────────────────────────────
        item(key = "section_header") {
            Text(
                text = "আসন্ন পরীক্ষা (${upcomingExams.size.toBengaliDigits()})",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(top = 8.dp, bottom = 4.dp),
            )
        }

        // ── Exam cards ────────────────────────────────────────────────────
        if (exams.isEmpty()) {
            item(key = "empty") {
                ExamEmptyState(onAdd = onAddExam)
            }
        } else {
            val countdownMap = countdownData.associateBy { it.exam.id }
            items(exams, key = { it.id }) { exam ->
                val countdown = countdownMap[exam.id]
                ExamCard(
                    exam = exam,
                    countdown = countdown,
                    onClick = { onExamClick(exam) },
                    onDelete = { viewModel.deleteExam(exam) },
                )
            }
        }

        item { Spacer(modifier = Modifier.height(80.dp)) }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Exam Countdown Banner — pulsing when urgent
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun ExamCountdownBanner(
    countdown: ExamCountdown,
    onClick: () -> Unit,
) {
    val isUrgent = countdown.daysRemaining <= 3
    val infiniteTransition = rememberInfiniteTransition(label = "countdown_pulse")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = if (isUrgent) 0.7f else 1f,
        targetValue = if (isUrgent) 1f else 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "pulse",
    )

    val bannerColor = when {
        isUrgent -> Error
        countdown.daysRemaining <= 7 -> Warning
        else -> Primary
    }

    GlassFilledCard(
        modifier = Modifier.fillMaxWidth(),
        tint = bannerColor,
        padding = 20.dp,
        onClick = onClick,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.graphicsLayer { alpha = pulseAlpha },
        ) {
            Text(
                text = "পরবর্তী পরীক্ষা",
                style = MaterialTheme.typography.labelMedium,
                color = OnPrimary.copy(alpha = 0.85f),
                fontWeight = FontWeight.Medium,
            )
            Text(
                text = countdown.exam.name,
                style = MaterialTheme.typography.titleLarge,
                color = OnPrimary,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.Bottom,
            ) {
                CountdownUnit(
                    value = countdown.daysRemaining.toBengaliDigits(),
                    label = "দিন",
                    color = OnPrimary,
                )
                CountdownUnit(
                    value = countdown.hoursRemaining.toBengaliDigits(),
                    label = "ঘণ্টা",
                    color = OnPrimary,
                )
                CountdownUnit(
                    value = countdown.minutesRemaining.toBengaliDigits(),
                    label = "মিনিট",
                    color = OnPrimary,
                )
            }
            if (isUrgent) {
                Text(
                    text = "⚠️ জরুরি! দ্রুত প্রস্তুতি নিন!",
                    style = MaterialTheme.typography.labelMedium,
                    color = OnPrimary.copy(alpha = 0.9f),
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}

@Composable
private fun CountdownUnit(
    value: String,
    label: String,
    color: Color,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            style = MaterialTheme.typography.headlineLarge.copy(
                fontFamily = EnglishFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 36.sp,
            ),
            color = color,
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = color.copy(alpha = 0.8f),
        )
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Exam Card
// ═══════════════════════════════════════════════════════════════════════════════

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ExamCard(
    exam: Exam,
    countdown: ExamCountdown?,
    onClick: () -> Unit,
    onDelete: () -> Unit,
) {
    var showMenu by remember { mutableStateOf(false) }
    val isDark = MaterialTheme.colorScheme.isDark

    val subjectColor = SubjectPalette.colorForIndex(
        (exam.subjectName?.hashCode() ?: 0).let { if (it < 0) -it else it } % SubjectPalette.colors.size
    )

    val daysRemaining = countdown?.daysRemaining ?: 0L
    val progressColor = when {
        daysRemaining <= 3 -> Error
        daysRemaining <= 7 -> Warning
        exam.preparationProgress >= 80 -> Success
        else -> Primary
    }

    val examType = when {
        exam.notes.contains("বোর্ড", ignoreCase = true) -> BdExamType.BOARD_EXAM
        exam.notes.contains("ভর্তি", ignoreCase = true) -> BdExamType.ADMISSION_TEST
        else -> BdExamType.MODEL_TEST
    }

    val activityType = when (examType) {
        BdExamType.BOARD_EXAM -> ActivityType.EXAM
        BdExamType.ADMISSION_TEST -> ActivityType.ASSIGNMENT
        BdExamType.MODEL_TEST -> ActivityType.MODEL_TEST
    }

    GlassElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        padding = 16.dp,
        onClick = onClick,
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            // Top row: subject name + tag + menu
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.weight(1f),
                ) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .background(subjectColor, CircleShape)
                    )
                    Text(
                        text = exam.subjectName ?: "সাধারণ",
                        style = MaterialTheme.typography.titleMedium,
                        color = subjectColor,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    ActivityTagChip(type = ActivityType.EXAM, compact = true)
                    Box {
                        IconButton(onClick = { showMenu = true }) {
                            Icon(
                                Icons.Default.MoreVert,
                                contentDescription = "আরো",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(20.dp),
                            )
                        }
                        DropdownMenu(
                            expanded = showMenu,
                            onDismissRequest = { showMenu = false },
                        ) {
                            DropdownMenuItem(
                                text = { Text("সম্পাদনা") },
                                onClick = { showMenu = false },
                                leadingIcon = { Icon(Icons.Default.Edit, null) },
                            )
                            DropdownMenuItem(
                                text = { Text("মুছুন") },
                                onClick = { onDelete(); showMenu = false },
                                leadingIcon = {
                                    Icon(Icons.Default.Delete, null, tint = Error)
                                },
                            )
                        }
                    }
                }
            }

            // Exam title
            Text(
                text = exam.name,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )

            // Date, time, countdown
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                val examDateStr = SimpleDateFormat("dd MMM yyyy", Locale("bn", "BD"))
                    .format(Date(exam.examDate))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "📅 ",
                        style = MaterialTheme.typography.bodySmall,
                    )
                    Text(
                        text = examDateStr,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
                if (!exam.examTime.isNullOrBlank()) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "🕐 ",
                            style = MaterialTheme.typography.bodySmall,
                        )
                        Text(
                            text = exam.examTime,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
                if (countdown != null) {
                    Text(
                        text = "${countdown.daysRemaining.toBengaliDigits()} দিন বাকি",
                        style = MaterialTheme.typography.labelMedium,
                        color = if (countdown.daysRemaining <= 3) Error else Warning,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }

            // Progress bar
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = "প্রস্তুতি",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Text(
                        text = "${exam.preparationProgress.toBengaliDigits()}%",
                        style = MaterialTheme.typography.labelSmall,
                        color = progressColor,
                        fontWeight = FontWeight.Bold,
                    )
                }
                LinearProgressIndicator(
                    progress = { exam.preparationProgress / 100f },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp)),
                    color = progressColor,
                    trackColor = if (isDark) DarkSurfaceVariant else LightSurfaceVariant,
                    strokeCap = androidx.compose.ui.graphics.StrokeCap.Round,
                )
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Add Exam Dialog
// ═══════════════════════════════════════════════════════════════════════════════

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddExamDialog(
    onDismiss: () -> Unit,
    onAdd: (name: String, subjectId: Long?, subjectName: String?, date: Long, time: String, examType: String) -> Unit,
) {
    var examTitle by remember { mutableStateOf("") }
    var selectedSubject by remember { mutableStateOf("") }
    var selectedExamType by remember { mutableStateOf(BdExamType.MODEL_TEST) }
    var selectedDateMillis by remember { mutableStateOf(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000L) }
    var selectedTimeHour by remember { mutableIntStateOf(10) }
    var selectedTimeMinute by remember { mutableIntStateOf(0) }
    var showTimePicker by remember { mutableStateOf(false) }

    val subjectOptions = listOf("বাংলা", "English", "গণিত", "পদার্থবিজ্ঞান", "রসায়ন", "জীববিজ্ঞান", "তথ্য ও যোগাযোগ প্রযুক্তি", "বাংলাদেশ ও বিশ্বপরিচয়", "উচ্চতর গণিত", "জীববিজ্ঞান (ব্যবহারিক)")

    val isValid by remember {
        derivedStateOf {
            examTitle.isNotBlank() && selectedSubject.isNotBlank()
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(28.dp),
        title = {
            Text(
                text = "নতুন পরীক্ষা যোগ করুন",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface,
            )
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth(),
            ) {
                // Exam title
                OutlinedTextField(
                    value = examTitle,
                    onValueChange = { examTitle = it },
                    label = { Text("পরীক্ষার নাম") },
                    placeholder = { Text("যেমন: ১ম সেমিস্টার মডেল টেস্ট") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Primary,
                        focusedLabelColor = Primary,
                    ),
                    singleLine = true,
                )

                // Subject dropdown
                Text(
                    text = "বিষয় নির্বাচন করুন",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    subjectOptions.forEach { subject ->
                        val isSelected = selectedSubject == subject
                        Surface(
                            modifier = Modifier
                                .clip(RoundedCornerShape(100.dp))
                                .clickable { selectedSubject = subject },
                            shape = RoundedCornerShape(100.dp),
                            color = if (isSelected) {
                                Primary.copy(alpha = 0.2f)
                            } else {
                                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                            },
                        ) {
                            Text(
                                text = subject,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                style = MaterialTheme.typography.labelMedium,
                                color = if (isSelected) Primary else MaterialTheme.colorScheme.onSurfaceVariant,
                                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                            )
                        }
                    }
                }

                // Exam type
                Text(
                    text = "পরীক্ষার ধরন",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                    BdExamType.entries.forEachIndexed { index, type ->
                        SegmentedButton(
                            selected = selectedExamType == type,
                            onClick = { selectedExamType = type },
                            shape = SegmentedButtonDefaults.itemShape(index, BdExamType.entries.size),
                        ) {
                            Text(type.bengaliLabel, style = MaterialTheme.typography.labelSmall)
                        }
                    }
                }

                // Date
                OutlinedTextField(
                    value = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date(selectedDateMillis)),
                    onValueChange = {},
                    label = { Text("তারিখ") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    readOnly = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Primary,
                        focusedLabelColor = Primary,
                    ),
                )

                // Time
                OutlinedTextField(
                    value = "${String.format("%02d", selectedTimeHour)}:${String.format("%02d", selectedTimeMinute)}",
                    onValueChange = {},
                    label = { Text("সময়") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showTimePicker = true },
                    shape = RoundedCornerShape(16.dp),
                    readOnly = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Primary,
                        focusedLabelColor = Primary,
                    ),
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val timeStr = "${String.format("%02d", selectedTimeHour)}:${String.format("%02d", selectedTimeMinute)}"
                    onAdd(examTitle, null, selectedSubject, selectedDateMillis, timeStr, selectedExamType.bengaliLabel)
                },
                enabled = isValid,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Primary,
                    disabledContainerColor = Primary.copy(alpha = 0.3f),
                ),
                shape = RoundedCornerShape(16.dp),
            ) {
                Text("যোগ করুন")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("বাতিল", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        },
    )

    if (showTimePicker) {
        val timePickerState = rememberTimePickerState(
            initialHour = selectedTimeHour,
            initialMinute = selectedTimeMinute,
            is24Hour = true,
        )
        AlertDialog(
            onDismissRequest = { showTimePicker = false },
            containerColor = MaterialTheme.colorScheme.surface,
            shape = RoundedCornerShape(28.dp),
            title = { Text("সময় নির্বাচন করুন") },
            text = {
                TimePicker(state = timePickerState)
            },
            confirmButton = {
                TextButton(onClick = {
                    selectedTimeHour = timePickerState.hour
                    selectedTimeMinute = timePickerState.minute
                    showTimePicker = false
                }) {
                    Text("নিশ্চিত করুন", color = Primary)
                }
            },
            dismissButton = {
                TextButton(onClick = { showTimePicker = false }) {
                    Text("বাতিল")
                }
            }
        )
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Syllabus Tab — Tab 1
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun SyllabusTab(
    modifier: Modifier = Modifier,
    viewModel: ExamsViewModel,
) {
    var selectedSubject by remember { mutableStateOf<String?>(null) }
    val checklist by viewModel.examPrepChecklist.collectAsState()

    val subjects = remember {
        sscShortSyllabusMap.keys.toList()
    }

    Column(modifier = modifier.fillMaxSize()) {
        // Subject selector
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(subjects) { subject ->
                Surface(
                    modifier = Modifier
                        .clip(RoundedCornerShape(100.dp))
                        .clickable {
                            selectedSubject = subject
                            // Load syllabus for this subject (in a real app, this would use subject ID)
                        },
                    shape = RoundedCornerShape(100.dp),
                    color = if (selectedSubject == subject) {
                        Primary.copy(alpha = 0.2f)
                    } else {
                        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                    },
                ) {
                    Text(
                        text = subject,
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                        style = MaterialTheme.typography.labelMedium,
                        color = if (selectedSubject == subject) Primary
                        else MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = if (selectedSubject == subject) FontWeight.SemiBold else FontWeight.Normal,
                    )
                }
            }
        }

        HorizontalDivider(
            color = MaterialTheme.colorScheme.outlineVariant,
            thickness = 1.dp,
        )

        if (selectedSubject != null) {
            val chapters = sscShortSyllabusMap[selectedSubject] ?: emptyList()
            var chapterStates by remember(selectedSubject) {
                mutableStateOf(chapters.map { it.copy() }.toMutableStateList())
            }

            val completedCount by remember {
                derivedStateOf { chapterStates.count { it.isCompleted } }
            }
            val totalCount = chapters.size

            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                // Progress header
                item(key = "syllabus_header") {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text(
                                text = "$selectedSubject — SSC ২০২৬ সংক্ষিপ্ত সিলেবাস",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurface,
                                fontWeight = FontWeight.Bold,
                            )
                            Text(
                                text = "${completedCount.toBengaliDigits()}/${totalCount.toBengaliDigits()}",
                                style = MaterialTheme.typography.titleMedium,
                                color = Primary,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                        LinearProgressIndicator(
                            progress = { if (totalCount > 0) completedCount.toFloat() / totalCount else 0f },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(RoundedCornerShape(4.dp)),
                            color = Primary,
                            trackColor = MaterialTheme.colorScheme.surfaceVariant,
                            strokeCap = androidx.compose.ui.graphics.StrokeCap.Round,
                        )
                    }
                }

                // Chapter checkboxes
                items(chapters.size) { index ->
                    val chapter = chapterStates[index]
                    val chapterColor = SubjectPalette.colorForIndex(index)
                    GlassOutlinedCard(
                        modifier = Modifier.fillMaxWidth(),
                        padding = 14.dp,
                        onClick = {
                            chapterStates[index] = chapter.copy(isCompleted = !chapter.isCompleted)
                        },
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                        ) {
                            // Checkbox visual
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(
                                        if (chapter.isCompleted) chapterColor
                                        else MaterialTheme.colorScheme.surfaceVariant
                                    ),
                                contentAlignment = Alignment.Center,
                            ) {
                                if (chapter.isCompleted) {
                                    Icon(
                                        Icons.Default.Check,
                                        contentDescription = null,
                                        tint = OnPrimary,
                                        modifier = Modifier.size(16.dp),
                                    )
                                }
                            }

                            // Chapter number badge
                            Text(
                                text = "অধ্যায় ${chapter.number.toBengaliDigits()}",
                                style = MaterialTheme.typography.labelMedium,
                                color = chapterColor,
                                fontWeight = FontWeight.Bold,
                            )

                            // Chapter name
                            Text(
                                text = chapter.name,
                                style = MaterialTheme.typography.bodyMedium,
                                color = if (chapter.isCompleted)
                                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                else MaterialTheme.colorScheme.onSurface,
                                fontWeight = if (chapter.isCompleted) FontWeight.Normal else FontWeight.Medium,
                                modifier = Modifier.weight(1f),
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }
                    }
                }

                // Study plan recommendation
                item(key = "study_plan") {
                    Spacer(modifier = Modifier.height(8.dp))
                    GlassFilledCard(
                        modifier = Modifier.fillMaxWidth(),
                        tint = Info,
                        padding = 16.dp,
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    Icons.Default.School,
                                    contentDescription = null,
                                    tint = OnPrimary,
                                    modifier = Modifier.size(20.dp),
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "স্টাডি প্ল্যান সুপারিশ",
                                    style = MaterialTheme.typography.titleSmall,
                                    color = OnPrimary,
                                    fontWeight = FontWeight.Bold,
                                )
                            }
                            val remaining = totalCount - completedCount
                            if (remaining > 0) {
                                val firstIncomplete = chapterStates.firstOrNull { !it.isCompleted }
                                Text(
                                    text = buildString {
                                        append("অবশিষ্ট ${remaining.toBengaliDigits()}টি অধ্যায়। ")
                                        if (firstIncomplete != null) {
                                            append("পরবর্তী: অধ্যায় ${firstIncomplete.number.toBengaliDigits()} — ${firstIncomplete.name}")
                                        }
                                    },
                                    style = MaterialTheme.typography.bodySmall,
                                    color = OnPrimary.copy(alpha = 0.9f),
                                )
                                Text(
                                    text = if (remaining <= 3) "প্রতিদিন ১টি অধ্যায় করলে ${remaining.toBengaliDigits()} দিনে শেষ হবে।"
                                    else "প্রতিদিন ২টি অধ্যায় করলে ${((remaining + 1) / 2).toBengaliDigits()} দিনে শেষ হবে।",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = OnPrimary.copy(alpha = 0.8f),
                                )
                            } else {
                                Text(
                                    text = "🎉 সব অধ্যায় সম্পন্ন! এখন রিভিশন শুরু করুন।",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = OnPrimary,
                                    fontWeight = FontWeight.Bold,
                                )
                            }
                        }
                    }
                }

                item { Spacer(modifier = Modifier.height(40.dp)) }
            }
        } else {
            // Empty state
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Icon(
                        Icons.Default.LibraryBooks,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f),
                        modifier = Modifier.size(64.dp),
                    )
                    Text(
                        text = "একটি বিষয় নির্বাচন করুন",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Text(
                        text = "SSC ২০২৬ সংক্ষিপ্ত সিলেবাস দেখতে\nউপরের বিষয়গুলো থেকে বেছে নিন",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Admission Test Tab — Tab 2
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun AdmissionTestTab(
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item(key = "admission_header") {
            Text(
                text = "ভর্তি পরীক্ষা প্রস্তুতি",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Text(
                text = "নটর ডেম, হলি ক্রস ও অন্যান্য ভর্তি পরীক্ষার জন্য প্রস্তুতি নিন",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }

        // Notre Dame College
        item(key = "notre_dame") {
            AdmissionTestCard(
                institutionName = "নটর ডেম কলেজ",
                description = "বিজ্ঞান, মানবিক ও ব্যবসায় শিক্ষা বিভাগে ভর্তি পরীক্ষা",
                topics = listOf(
                    "বাংলা", "English", "গণিত", "সাধারণ জ্ঞান",
                    "বিজ্ঞান (বিজ্ঞান বিভাগ)", "যুক্তি অনুসন্ধান"
                ),
                accentColor = Color(0xFF1565C0),
                onClick = { /* navigate to practice */ },
            )
        }

        // Holy Cross College
        item(key = "holy_cross") {
            AdmissionTestCard(
                institutionName = "হলি ক্রস কলেজ",
                description = "বিজ্ঞান ও মানবিক বিভাগে ভর্তি পরীক্ষা",
                topics = listOf(
                    "বাংলা", "English", "গণিত", "সাধারণ জ্ঞান", "যুক্তি অনুসন্ধান"
                ),
                accentColor = Color(0xFFAD1457),
                onClick = { /* navigate to practice */ },
            )
        }

        // General tips
        item(key = "tips_header") {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "ভর্তি পরীক্ষার টিপস",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }

        item(key = "tip_1") {
            TipCard(
                title = "টাইম ম্যানেজমেন্ট",
                description = "প্রতিটি প্রশ্নে ১ মিনিটের বেশি সময় দেবেন না। কঠিন প্রশ্ন পরে করুন।",
                icon = Icons.Default.Edit,
            )
        }

        item(key = "tip_2") {
            TipCard(
                title = "যুক্তি অনুসন্ধান অনুশীলন",
                description = "প্রতিদিন কমপক্ষে ১০টি যুক্তি অনুসন্ধান প্রশ্ন সলভ করুন।",
                icon = Icons.Default.Description,
            )
        }

        item(key = "tip_3") {
            TipCard(
                title = "পূর্ববর্তী প্রশ্ন সলভ",
                description = "গত ৫ বছরের ভর্তি পরীক্ষার প্রশ্ন অনুশীলন করুন।",
                icon = Icons.Default.LibraryBooks,
            )
        }

        item { Spacer(modifier = Modifier.height(40.dp)) }
    }
}

@Composable
private fun AdmissionTestCard(
    institutionName: String,
    description: String,
    topics: List<String>,
    accentColor: Color,
    onClick: () -> Unit,
) {
    val isDark = MaterialTheme.colorScheme.isDark
    GlassElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        padding = 16.dp,
        onClick = onClick,
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(accentColor, CircleShape)
                )
                Text(
                    text = institutionName,
                    style = MaterialTheme.typography.titleMedium,
                    color = accentColor,
                    fontWeight = FontWeight.Bold,
                )
            }
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Text(
                text = "বিষয়সমূহ:",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Medium,
            )
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                topics.forEach { topic ->
                    Surface(
                        shape = RoundedCornerShape(100.dp),
                        color = accentColor.copy(alpha = 0.12f),
                    ) {
                        Text(
                            text = topic,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.labelSmall,
                            color = accentColor,
                        )
                    }
                }
            }
            Button(
                onClick = onClick,
                modifier = Modifier.align(Alignment.End),
                colors = ButtonDefaults.buttonColors(containerColor = accentColor),
                shape = RoundedCornerShape(12.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
            ) {
                Text("অনুশীলন শুরু করুন", style = MaterialTheme.typography.labelMedium)
            }
        }
    }
}

@Composable
private fun TipCard(
    title: String,
    description: String,
    icon: ImageVector,
) {
    GlassOutlinedCard(
        modifier = Modifier.fillMaxWidth(),
        padding = 14.dp,
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Primary,
                modifier = Modifier.size(22.dp),
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.SemiBold,
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 18.sp,
                )
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Board Question Archive — Tab 3
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun BoardQuestionArchive(
    modifier: Modifier = Modifier,
) {
    val archiveItems = remember {
        listOf(
            ArchiveItem("SSC ২০২৪ — বাংলা ১ম পত্র", "ঢাকা বোর্ড", "PDF"),
            ArchiveItem("SSC ২০২৪ — বাংলা ২য় পত্র", "ঢাকা বোর্ড", "PDF"),
            ArchiveItem("SSC ২০২৪ — English 1st Paper", "ঢাকা বোর্ড", "PDF"),
            ArchiveItem("SSC ২০২৪ — English 2nd Paper", "ঢাকা বোর্ড", "PDF"),
            ArchiveItem("SSC ২০২৪ — গণিত", "ঢাকা বোর্ড", "PDF"),
            ArchiveItem("SSC ২০২৩ — পদার্থবিজ্ঞান", "ঢাকা বোর্ড", "PDF"),
            ArchiveItem("SSC ২০২৩ — রসায়ন", "ঢাকা বোর্ড", "PDF"),
            ArchiveItem("SSC ২০২৩ — জীববিজ্ঞান", "ঢাকা বোর্ড", "PDF"),
            ArchiveItem("SSC ২০২২ — বাংলা ১ম পত্র", "রাজশাহী বোর্ড", "PDF"),
            ArchiveItem("SSC ২০২২ — গণিত", "চট্টগ্রাম বোর্ড", "PDF"),
        )
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        item(key = "archive_header") {
            Text(
                text = "বোর্ড প্রশ্ন আর্কাইভ",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Text(
                text = "গত বছরের বোর্ড পরীক্ষার প্রশ্ন ব্রাউজ করুন",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }

        // Year filter chips
        item(key = "year_filter") {
            val years = listOf("সব", "২০২৪", "২০২৩", "২০২২", "২০২১")
            var selectedYear by remember { mutableStateOf("সব") }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                years.forEach { year ->
                    Surface(
                        modifier = Modifier
                            .clip(RoundedCornerShape(100.dp))
                            .clickable { selectedYear = year },
                        shape = RoundedCornerShape(100.dp),
                        color = if (selectedYear == year) Primary.copy(alpha = 0.2f)
                        else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                    ) {
                        Text(
                            text = year,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                            style = MaterialTheme.typography.labelMedium,
                            color = if (selectedYear == year) Primary
                            else MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
            }
        }

        items(archiveItems) { item ->
            GlassOutlinedCard(
                modifier = Modifier.fillMaxWidth(),
                padding = 14.dp,
                onClick = { /* open PDF viewer */ },
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Icon(
                        Icons.Default.Description,
                        contentDescription = null,
                        tint = Error,
                        modifier = Modifier.size(28.dp),
                    )
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = item.title,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Medium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                        Text(
                            text = item.board,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = Error.copy(alpha = 0.12f),
                    ) {
                        Text(
                            text = item.format,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.labelSmall,
                            color = Error,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }
            }
        }

        item { Spacer(modifier = Modifier.height(40.dp)) }
    }
}

private data class ArchiveItem(
    val title: String,
    val board: String,
    val format: String,
)

// ═══════════════════════════════════════════════════════════════════════════════
// GPA Calculator Screen
// ═══════════════════════════════════════════════════════════════════════════════

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun GpaCalculatorScreen(
    viewModel: ExamsViewModel,
    onBack: () -> Unit,
) {
    var subjectEntries by remember {
        mutableStateOf(
            mutableListOf(
                GpaSubjectEntry(),
                GpaSubjectEntry(),
                GpaSubjectEntry(),
                GpaSubjectEntry(),
                GpaSubjectEntry(),
                GpaSubjectEntry(),
                GpaSubjectEntry(),
                GpaSubjectEntry(),
            )
        )
    }
    var fourthSubjectIndex by remember { mutableIntStateOf(-1) }

    fun calculateGpa(): Pair<Double, List<GpaSubjectEntry>> {
        val updated = subjectEntries.mapIndexed { index, entry ->
            val marks = entry.marks.toDoubleOrNull() ?: 0.0
            val (gp, grade) = marksToGPA(marks)
            entry.copy(gradePoint = gp, gradeLabel = grade, isFourthSubject = index == fourthSubjectIndex)
        }
        val compulsory = updated.filter { !it.isFourthSubject && it.marks.isNotBlank() }
        val fourth = updated.firstOrNull { it.isFourthSubject && it.marks.isNotBlank() }

        val totalGp = if (compulsory.isNotEmpty()) {
            val sum = compulsory.sumOf { it.gradePoint }
            val fourthBonus = if (fourth != null && fourth.gradePoint > 2.0) {
                (fourth.gradePoint - 2.0)
            } else 0.0
            ((sum + fourthBonus) / 8.0 * 2.0).coerceIn(0.0, 5.0)
        } else 0.0
        return totalGp to updated
    }

    val (calculatedGpa, updatedEntries) = calculateGpa()
    // Update entries with calculated values
    LaunchedEffect(calculatedGpa) {
        subjectEntries = updatedEntries.toMutableList()
    }

    val gpaColor = when {
        calculatedGpa >= 5.0 -> Success
        calculatedGpa >= 4.0 -> Primary
        calculatedGpa >= 3.5 -> Info
        calculatedGpa >= 3.0 -> Warning
        calculatedGpa >= 2.0 -> Error
        else -> Color.Gray
    }

    val sheetState = rememberModalBottomSheetState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding(),
        topBar = {
            androidx.compose.material3.TopAppBar(
                title = {
                    Text(
                        text = "জিপিএ ক্যালকুলেটর",
                        style = MaterialTheme.typography.headlineMedium,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "ফিরে যান")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                ),
            )
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            // GPA Display
            item(key = "gpa_display") {
                GlassFilledCard(
                    modifier = Modifier.fillMaxWidth(),
                    tint = gpaColor,
                    padding = 24.dp,
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        Text(
                            text = "আপনার জিপিএ",
                            style = MaterialTheme.typography.labelLarge,
                            color = OnPrimary.copy(alpha = 0.85f),
                        )
                        Text(
                            text = String.format("%.2f", calculatedGpa),
                            style = MaterialTheme.typography.displayLarge.copy(
                                fontFamily = EnglishFontFamily,
                                fontWeight = FontWeight.Bold,
                            ),
                            color = OnPrimary,
                        )
                        Text(
                            text = "বাংলাদেশ ৫.০ স্কেল",
                            style = MaterialTheme.typography.bodySmall,
                            color = OnPrimary.copy(alpha = 0.8f),
                        )
                    }
                }
            }

            // Subject list header
            item(key = "subject_list_header") {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text(
                        text = "বিষয়",
                        modifier = Modifier.weight(2f),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = "নম্বর",
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = "জিপিএ",
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = "গ্রেড",
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }

            // Subject entries
            items(subjectEntries.size) { index ->
                val entry = subjectEntries[index]
                val isFourth = index == fourthSubjectIndex

                GlassOutlinedCard(
                    modifier = Modifier.fillMaxWidth(),
                    padding = 10.dp,
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        OutlinedTextField(
                            value = entry.subjectName,
                            onValueChange = {
                                subjectEntries[index] = entry.copy(subjectName = it)
                            },
                            placeholder = { Text("বিষয় ${index.toBengaliDigits()}", fontSize = 12.sp) },
                            modifier = Modifier.weight(2f),
                            shape = RoundedCornerShape(10.dp),
                            textStyle = MaterialTheme.typography.bodySmall,
                            singleLine = true,
                        )
                        OutlinedTextField(
                            value = entry.marks,
                            onValueChange = {
                                if (it.isEmpty() || it.all { c -> c.isDigit() || c == '.' }) {
                                    subjectEntries[index] = entry.copy(marks = it)
                                }
                            },
                            placeholder = { Text("০-১০০", fontSize = 12.sp) },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp),
                            textStyle = MaterialTheme.typography.bodySmall.copy(
                                fontFamily = EnglishFontFamily
                            ),
                            singleLine = true,
                            keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            ),
                        )
                        Text(
                            text = if (entry.marks.isNotBlank()) String.format("%.2f", entry.gradePoint) else "—",
                            modifier = Modifier.weight(1f),
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontFamily = EnglishFontFamily,
                                fontWeight = FontWeight.Bold,
                            ),
                            color = when {
                                entry.gradePoint >= 5.0 -> Success
                                entry.gradePoint >= 3.5 -> Primary
                                entry.gradePoint >= 2.0 -> Warning
                                entry.gradePoint > 0 -> Error
                                else -> MaterialTheme.colorScheme.onSurfaceVariant
                            },
                            textAlign = TextAlign.Center,
                        )
                        Text(
                            text = if (entry.marks.isNotBlank()) entry.gradeLabel else "—",
                            modifier = Modifier.weight(1f),
                            style = MaterialTheme.typography.bodySmall,
                            color = when {
                                entry.gradePoint >= 5.0 -> Success
                                entry.gradePoint >= 3.5 -> Primary
                                entry.gradePoint >= 2.0 -> Warning
                                entry.gradePoint > 0 -> Error
                                else -> MaterialTheme.colorScheme.onSurfaceVariant
                            },
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                        )
                    }
                    // 4th subject toggle
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                    ) {
                        Switch(
                            checked = isFourth,
                            onCheckedChange = { checked ->
                                fourthSubjectIndex = if (checked) index else -1
                            },
                            modifier = Modifier.size(18.dp),
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = OnPrimary,
                                checkedTrackColor = Tertiary,
                            ),
                        )
                        Text(
                            text = "৪র্থ বিষয়",
                            style = MaterialTheme.typography.labelSmall,
                            color = if (isFourth) Tertiary else MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                        if (isFourth && entry.gradePoint <= 2.0) {
                            Text(
                                text = " (গ্রেড পয়েন্ট ২.০-এর নিচে হলে গণনা হবে না)",
                                style = MaterialTheme.typography.labelSmall,
                                color = Error,
                            )
                        }
                    }
                }
            }

            // Grading scale reference
            item(key = "grading_scale") {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "বাংলাদেশ জিপিএ স্কেল",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.height(8.dp))
                GradingScaleTable()
            }

            item { Spacer(modifier = Modifier.height(40.dp)) }
        }
    }
}

@Composable
private fun GradingScaleTable() {
    val gradingScale = listOf(
        Triple("৮০-১০০", "A+", "৫.০০"),
        Triple("৭০-৭৯", "A", "৪.০০"),
        Triple("৬০-৬৯", "A-", "৩.৫০"),
        Triple("৫০-৫৯", "B", "৩.০০"),
        Triple("৪০-৪৯", "C", "২.০০"),
        Triple("৩৩-৩৯", "D", "১.০০"),
        Triple("০-৩২", "F", "০.০০"),
    )

    GlassOutlinedCard(
        modifier = Modifier.fillMaxWidth(),
        padding = 12.dp,
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(1.dp)) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    text = "নম্বর সীমা",
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = "গ্রেড",
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                )
                Text(
                    text = "জিপিএ",
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                )
            }
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant, thickness = 1.dp)
            gradingScale.forEach { (range, grade, gpa) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    Text(
                        text = range,
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    Text(
                        text = grade,
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                    )
                    Text(
                        text = gpa,
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.bodySmall.copy(fontFamily = EnglishFontFamily),
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center,
                    )
                }
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f),
                    thickness = 0.5.dp,
                )
            }
        }
    }
}

/**
 * Bangladesh SSC/HSC GPA calculation:
 * 80-100: A+ (5.00), 70-79: A (4.00), 60-69: A- (3.50), 50-59: B (3.00),
 * 40-49: C (2.00), 33-39: D (1.00), <33: F (0.00)
 */
private fun marksToGPA(marks: Double): Pair<Double, String> = when {
    marks >= 80 -> 5.0 to "A+"
    marks >= 70 -> 4.0 to "A"
    marks >= 60 -> 3.5 to "A-"
    marks >= 50 -> 3.0 to "B"
    marks >= 40 -> 2.0 to "C"
    marks >= 33 -> 1.0 to "D"
    else -> 0.0 to "F"
}

// ═══════════════════════════════════════════════════════════════════════════════
// Exam Empty State
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun ExamEmptyState(onAdd: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 48.dp),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Icon(
                Icons.Default.School,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f),
                modifier = Modifier.size(72.dp),
            )
            Text(
                text = "কোনো পরীক্ষা নেই",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
            )
            Text(
                text = "আপনার আসন্ন পরীক্ষা যোগ করুন\nএবং প্রস্তুতি ট্র্যাক করুন",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                textAlign = TextAlign.Center,
            )
            Button(
                onClick = onAdd,
                colors = ButtonDefaults.buttonColors(containerColor = Primary),
                shape = RoundedCornerShape(16.dp),
            ) {
                Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("পরীক্ষা যোগ করুন")
            }
        }
    }
}
*/