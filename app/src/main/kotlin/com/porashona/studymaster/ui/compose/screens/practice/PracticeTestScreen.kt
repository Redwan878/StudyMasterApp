
package com.porashona.studymaster.ui.compose.screens.practice

import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.NavigateBefore
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.porashona.studymaster.data.model.PracticeTest
import com.porashona.studymaster.data.model.QuestionDifficulty
import com.porashona.studymaster.data.model.QuestionBank
import com.porashona.studymaster.ui.compose.components.GlassCardVariant
import com.porashona.studymaster.ui.compose.components.GlassmorphicCard
import com.porashona.studymaster.ui.compose.components.SubjectChip
import com.porashona.studymaster.ui.compose.theme.DarkSurfaceVariant
import com.porashona.studymaster.ui.compose.theme.EnglishFontFamily
import com.porashona.studymaster.ui.compose.theme.Error
import com.porashona.studymaster.ui.compose.theme.GlassBorderDark
import com.porashona.studymaster.ui.compose.theme.GlassBorderLight
import com.porashona.studymaster.ui.compose.theme.GlassDarkAlpha60
import com.porashona.studymaster.ui.compose.theme.GlassDarkAlpha80
import com.porashona.studymaster.ui.compose.theme.GlassLightAlpha90
import com.porashona.studymaster.ui.compose.theme.Info
import com.porashona.studymaster.ui.compose.theme.LocalGlassShapes
import com.porashona.studymaster.ui.compose.theme.LocalMotion
import com.porashona.studymaster.ui.compose.theme.Primary
import com.porashona.studymaster.ui.compose.theme.PrimaryLight
import com.porashona.studymaster.ui.compose.theme.Secondary
import com.porashona.studymaster.ui.compose.theme.StudyMasterTypography
import com.porashona.studymaster.ui.compose.theme.Success
import com.porashona.studymaster.ui.compose.theme.Tertiary
import com.porashona.studymaster.ui.compose.theme.Warning
import com.porashona.studymaster.ui.compose.theme.XpBarBg
import com.porashona.studymaster.ui.compose.theme.XpBarFill
import com.porashona.studymaster.ui.compose.theme.isDark
import com.porashona.studymaster.ui.compose.theme.toBengaliDigits
import com.porashona.studymaster.ui.compose.viewmodels.ChapterBreakdownEntry
import com.porashona.studymaster.ui.compose.viewmodels.PracticeTestViewModel
import com.porashona.studymaster.ui.compose.viewmodels.TestEvent
import com.porashona.studymaster.ui.compose.viewmodels.TestQuestion
import com.porashona.studymaster.ui.compose.viewmodels.TestState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// ═══════════════════════════════════════════════════════════════════════════════
// Practice Test Screen — full practice test system:
//   1. Test Selection (filters, list, create)
//   2. Test-Taking (timer, questions, navigation)
//   3. Results (score, grade, breakdown, trends)
// ═══════════════════════════════════════════════════════════════════════════════

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PracticeTestScreen(
    onBack: () -> Unit = {},
    viewModel: PracticeTestViewModel = hiltViewModel(),
) {
    val testState by viewModel.testState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Handle events
    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is TestEvent.TestCreated -> {
                    snackbarHostState.showSnackbar("টেস্ট তৈরি হয়েছে!")
                }
                is TestEvent.TestCompleted -> {
                    // Navigated to results automatically via state
                }
                is TestEvent.TestDeleted -> {
                    snackbarHostState.showSnackbar("টেস্ট মুছে ফেলা হয়েছে।")
                }
                null -> {}
            }
        }
    }

    when (testState) {
        TestState.NOT_STARTED -> TestSelectionScreen(
            viewModel = viewModel,
            onBack = onBack,
            snackbarHostState = snackbarHostState,
        )
        TestState.IN_PROGRESS -> TestTakingScreen(
            viewModel = viewModel,
            onBack = { viewModel.resetTest() },
        )
        TestState.COMPLETED -> TestResultsScreen(
            viewModel = viewModel,
            onBack = { viewModel.resetTest() },
        )
        TestState.REVIEWING -> TestReviewScreen(
            viewModel = viewModel,
            onBack = { viewModel.resetTest() },
        )
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// 1. TEST SELECTION SCREEN
// Filters, test list, create test button
// ═══════════════════════════════════════════════════════════════════════════════

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TestSelectionScreen(
    viewModel: PracticeTestViewModel,
    onBack: () -> Unit,
    snackbarHostState: SnackbarHostState,
) {
    val isDark = MaterialTheme.colorScheme.isDark
    val scope = rememberCoroutineScope()

    val availableTests by viewModel.availableTests.collectAsState()
    val completedCount by viewModel.completedTestCount.collectAsState()

    var selectedSubjectId by remember { mutableStateOf<Long?>(null) }
    var selectedTestType by remember { mutableStateOf(TestTypeFilter.ALL) }
    var selectedDifficulty by remember { mutableStateOf(DifficultyFilter.ALL) }
    var selectedDuration by remember { mutableStateOf(DurationFilter.ALL) }
    var showCreateDialog by remember { mutableStateOf(false) }
    var showDeleteConfirmId by remember { mutableStateOf<Long?>(null) }

    // Filter tests
    val filteredTests = remember(availableTests, selectedSubjectId, selectedTestType) {
        availableTests.filter { test ->
            val subjectMatch = selectedSubjectId == null || test.subjectId == selectedSubjectId
            val typeMatch = when (selectedTestType) {
                TestTypeFilter.ALL -> true
                TestTypeFilter.SUBJECT_WISE -> !test.isMixedSubject && test.subjectId != null
                TestTypeFilter.MIXED -> test.isMixedSubject
                TestTypeFilter.CUSTOM -> false // Custom are shown separately
            }
            subjectMatch && typeMatch
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "অনুশীলনী পরীক্ষা",
                        style = StudyMasterTypography.titleLarge,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "ফিরুন",
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showCreateDialog = true },
                containerColor = Primary,
                contentColor = Color.White,
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "টেস্ট তৈরি")
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            // ── Stats Bar ──────────────────────────────────────────────────
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                shape = RoundedCornerShape(LocalGlassShapes.current.cardRadiusSmall),
                color = if (isDark) DarkSurfaceVariant else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    StatItem(
                        value = availableTests.size.toBengaliDigits(),
                        label = "মোট টেস্ট",
                    )
                    StatItem(
                        value = completedCount.collectAsState(initial = 0).value.toBengaliDigits(),
                        label = "সম্পন্ন",
                        valueColor = Success,
                    )
                    StatItem(
                        value = (availableTests.size - completedCount.collectAsState(initial = 0).value).toBengaliDigits(),
                        label = "বাকি",
                        valueColor = Warning,
                    )
                }
            }

            // ── Test Type Filter ───────────────────────────────────────────
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
            ) {
                items(TestTypeFilter.entries) { type ->
                    FilterChip(
                        label = type.bengaliLabel,
                        selected = selectedTestType == type,
                        onClick = { selectedTestType = type },
                    )
                }
            }

            // ── Difficulty Filter ──────────────────────────────────────────
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
            ) {
                items(DifficultyFilter.entries) { diff ->
                    FilterChip(
                        label = diff.bengaliLabel,
                        selected = selectedDifficulty == diff,
                        onClick = { selectedDifficulty = diff },
                        color = when (diff) {
                            DifficultyFilter.EASY -> Success
                            DifficultyFilter.MEDIUM -> Warning
                            DifficultyFilter.HARD -> Error
                            DifficultyFilter.ALL -> Primary
                        },
                    )
                }
            }

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                color = if (isDark) GlassBorderDark else GlassBorderLight,
            )

            // ── Test List ──────────────────────────────────────────────────
            if (filteredTests.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "📝",
                            fontSize = 48.sp,
                        )
                        Spacer(Modifier.height(12.dp))
                        Text(
                            text = "কোনো টেস্ট পাওয়া যায়নি",
                            style = StudyMasterTypography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = "নতুন টেস্ট তৈরি করতে + বোতামে ক্লিক করুন",
                            style = StudyMasterTypography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    items(filteredTests, key = { it.id }) { test ->
                        TestListItem(
                            test = test,
                            onStart = { viewModel.startTest(test.id) },
                            onDelete = { showDeleteConfirmId = test.id },
                        )
                    }
                }
            }
        }
    }

    // ── Create Test Dialog ────────────────────────────────────────────────
    if (showCreateDialog) {
        CreateTestDialog(
            onDismiss = { showCreateDialog = false },
            onCreate = { title, subjectId, subjectName, questionCount, duration, negEnabled, negValue, isMixed ->
                viewModel.createTest(
                    title = title,
                    subjectId = subjectId,
                    subjectName = subjectName,
                    totalQuestions = questionCount,
                    durationMinutes = duration,
                    negativeMarkingEnabled = negEnabled,
                    negativeMarkValue = negValue,
                    isMixedSubject = isMixed,
                )
                showCreateDialog = false
            },
        )
    }

    // ── Delete Confirmation ───────────────────────────────────────────────
    showDeleteConfirmId?.let { testId ->
        AlertDialog(
            onDismissRequest = { showDeleteConfirmId = null },
            title = { Text("টেস্ট মুছুন", style = StudyMasterTypography.titleMedium) },
            text = { Text("আপনি কি এই টেস্টটি মুছে ফেলতে চান?", style = StudyMasterTypography.bodyMedium) },
            confirmButton = {
                Button(
                    onClick = {
                        val test = availableTests.find { it.id == testId }
                        test?.let { viewModel.deleteTest(it) }
                        showDeleteConfirmId = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Error),
                ) {
                    Text("মুছুন", style = StudyMasterTypography.labelLarge)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteConfirmId = null }) {
                    Text("বাতিল", style = StudyMasterTypography.labelLarge)
                }
            },
        )
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// 2. TEST-TAKING SCREEN
// Timer, question, options, navigation, question grid
// ═══════════════════════════════════════════════════════════════════════════════

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TestTakingScreen(
    viewModel: PracticeTestViewModel,
    onBack: () -> Unit,
) {
    val isDark = MaterialTheme.colorScheme.isDark
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val currentTest by viewModel.currentTest.collectAsState()
    val testQuestions by viewModel.testQuestions.collectAsState()
    val currentIndex by viewModel.currentQuestionIndex.collectAsState()
    val currentQuestion by viewModel.currentQuestion.collectAsState()
    val timeRemaining by viewModel.timeRemaining.collectAsState()
    val answers by viewModel.answers.collectAsState()

    var showQuestionGrid by remember { mutableStateOf(false) }
    var showFinishConfirm by remember { mutableStateOf(false) }
    var flaggedQuestions by remember { mutableStateOf<Set<Int>>(emptySet()) }

    val totalQuestions = testQuestions.size
    val answeredCount = answers.size
    val progress = if (totalQuestions > 0) answeredCount.toFloat() / totalQuestions else 0f

    // Warning state when timer < 5 minutes
    val isWarning = timeRemaining < 300

    // Timer display
    val minutes = (timeRemaining / 60).toInt()
    val seconds = (timeRemaining % 60).toInt()
    val timerText = String.format("%02d:%02d", minutes, seconds)

    // Current question data
    val question = currentQuestion?.question
    val selectedOption = question?.let { answers[it.id] ?: 0 } ?: 0

    // Option labels
    val optionLabels = listOf("ক", "খ", "গ", "ঘ")

    // Completion bar animation
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(400, easing = FastOutSlowInEasing),
        label = "testProgress",
    )

    // Timer pulse animation for warning
    val infiniteTransition = rememberInfiniteTransition(label = "timerPulse")
    val timerPulse by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = if (isWarning) 1.15f else 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "timerPulse",
    )

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding(),
        topBar = {
            // Timer bar
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = if (isWarning) Error.copy(alpha = 0.1f) else Color.Transparent,
            ) {
                Column {
                    // Progress bar
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(animatedProgress)
                                .height(4.dp)
                                .background(
                                    color = if (isWarning) Error else Primary,
                                    shape = RoundedCornerShape(2.dp),
                                ),
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .statusBarsPadding()
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        // Back button
                        IconButton(onClick = onBack) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "বাতিল",
                            )
                        }

                        // Timer
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = Modifier.graphicsLayer {
                                scaleX = timerPulse
                                scaleY = timerPulse
                            },
                        ) {
                            Icon(
                                imageVector = Icons.Default.Timer,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp),
                                tint = if (isWarning) Error else MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                            Text(
                                text = timerText,
                                style = StudyMasterTypography.labelLarge.copy(
                                    fontFamily = EnglishFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isWarning) Error else MaterialTheme.colorScheme.onSurface,
                                ),
                            )
                        }

                        // Question grid button
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                            modifier = Modifier
                                .clickable { showQuestionGrid = true }
                                .padding(4.dp),
                        ) {
                            Text(
                                text = "${(currentIndex + 1).toBengaliDigits()}/${totalQuestions.toBengaliDigits()}",
                                style = StudyMasterTypography.labelMedium.copy(
                                    fontFamily = EnglishFontFamily,
                                    fontWeight = FontWeight.Bold,
                                ),
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            )
                        }

                        // Flag button
                        IconButton(
                            onClick = {
                                flaggedQuestions = if (currentIndex in flaggedQuestions) {
                                    flaggedQuestions - currentIndex
                                } else {
                                    flaggedQuestions + currentIndex
                                }
                            },
                        ) {
                            Icon(
                                imageVector = Icons.Default.Flag,
                                contentDescription = "ফ্ল্যাগ",
                                tint = if (currentIndex in flaggedQuestions) Tertiary
                                else MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                    }
                }
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { innerPadding ->
        if (question != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
            ) {
                // ── Tags: Subject, Difficulty ─────────────────────────────
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(bottom = 8.dp),
                ) {
                    if (currentTest?.subjectName != null) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = Primary.copy(alpha = 0.1f),
                        ) {
                            Text(
                                text = currentTest?.subjectName ?: "",
                                style = StudyMasterTypography.labelSmall,
                                color = Primary,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                            )
                        }
                    }
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = when (question.difficulty) {
                            QuestionDifficulty.EASY.name -> Success.copy(alpha = 0.1f)
                            QuestionDifficulty.HARD.name -> Error.copy(alpha = 0.1f)
                            else -> Warning.copy(alpha = 0.1f)
                        },
                    ) {
                        Text(
                            text = when (question.difficulty) {
                                QuestionDifficulty.EASY.name -> "সহজ"
                                QuestionDifficulty.HARD.name -> "কঠিন"
                                else -> "মাঝারি"
                            },
                            style = StudyMasterTypography.labelSmall,
                            color = when (question.difficulty) {
                                QuestionDifficulty.EASY.name -> Success
                                QuestionDifficulty.HARD.name -> Error
                                else -> Warning
                            },
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                        )
                    }
                    if (question.chapterName != null) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = Secondary.copy(alpha = 0.1f),
                        ) {
                            Text(
                                text = question.chapterName ?: "",
                                style = StudyMasterTypography.labelSmall,
                                color = Secondary,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }
                    }
                }

                // ── Question Number ───────────────────────────────────────
                Text(
                    text = "প্রশ্ন ${(currentIndex + 1).toBengaliDigits()}",
                    style = StudyMasterTypography.titleSmall,
                    color = Primary,
                    modifier = Modifier.padding(bottom = 6.dp),
                )

                // ── Question Text ─────────────────────────────────────────
                GlassmorphicCard(
                    modifier = Modifier.fillMaxWidth(),
                    variant = GlassCardVariant.OUTLINED,
                    cornerRadius = LocalGlassShapes.current.cardRadiusSmall,
                    padding = 16.dp,
                ) {
                    Text(
                        text = question.questionText,
                        style = StudyMasterTypography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }

                Spacer(Modifier.height(16.dp))

                // ── Options ────────────────────────────────────────────────
                val options = listOf(question.optionA, question.optionB, question.optionC, question.optionD)

                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    options.forEachIndexed { index, optionText ->
                        val optionNumber = index + 1
                        val isSelected = selectedOption == optionNumber

                        val optionBg by animateColorAsState(
                            targetValue = when {
                                isSelected -> Primary.copy(alpha = if (isDark) 0.25f else 0.15f)
                                else -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
                            },
                            animationSpec = tween(200),
                            label = "optionBg$index",
                        )
                        val optionBorder by animateColorAsState(
                            targetValue = when {
                                isSelected -> Primary.copy(alpha = 0.6f)
                                else -> if (isDark) GlassBorderDark else GlassBorderLight
                            },
                            animationSpec = tween(200),
                            label = "optionBorder$index",
                        )

                        Surface(
                            shape = RoundedCornerShape(LocalGlassShapes.current.cardRadiusSmall),
                            color = optionBg,
                            border = BorderStroke(
                                width = if (isSelected) 1.5.dp else 1.dp,
                                color = optionBorder,
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.answerQuestion(question.id, optionNumber)
                                },
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(14.dp),
                                verticalAlignment = Alignment.Top,
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                            ) {
                                // Option circle
                                Surface(
                                    shape = CircleShape,
                                    color = if (isSelected) Primary else MaterialTheme.colorScheme.surfaceVariant,
                                    contentColor = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.size(28.dp),
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Text(
                                            text = optionLabels[index],
                                            style = StudyMasterTypography.labelMedium.copy(
                                                fontFamily = BengaliFontFamily,
                                                fontWeight = FontWeight.Bold,
                                            ),
                                        )
                                    }
                                }

                                // Option text
                                Text(
                                    text = optionText,
                                    style = StudyMasterTypography.bodyMedium,
                                    color = if (isSelected) Primary
                                    else MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.weight(1f),
                                )
                            }
                        }
                    }
                }

                Spacer(Modifier.weight(1f))

                // ── Bottom Navigation ──────────────────────────────────────
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    // Previous
                    OutlinedButton(
                        onClick = { viewModel.previousQuestion() },
                        enabled = currentIndex > 0,
                        shape = RoundedCornerShape(LocalGlassShapes.current.buttonRadius),
                    ) {
                        Icon(
                            imageVector = Icons.Default.NavigateBefore,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp),
                        )
                        Spacer(Modifier.width(4.dp))
                        Text("আগে", style = StudyMasterTypography.labelLarge)
                    }

                    // Question grid button
                    OutlinedButton(
                        onClick = { showQuestionGrid = true },
                        shape = RoundedCornerShape(LocalGlassShapes.current.buttonRadius),
                    ) {
                        Text(
                            text = "${(currentIndex + 1).toBengaliDigits()}/${totalQuestions.toBengaliDigits()}",
                            style = StudyMasterTypography.labelLarge,
                        )
                    }

                    // Next / Finish
                    if (currentIndex < totalQuestions - 1) {
                        Button(
                            onClick = { viewModel.nextQuestion() },
                            shape = RoundedCornerShape(LocalGlassShapes.current.buttonRadius),
                        ) {
                            Text("পরবর্তী", style = StudyMasterTypography.labelLarge)
                            Spacer(Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.Default.NavigateNext,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp),
                            )
                        }
                    } else {
                        Button(
                            onClick = { showFinishConfirm = true },
                            shape = RoundedCornerShape(LocalGlassShapes.current.buttonRadius),
                            colors = ButtonDefaults.buttonColors(containerColor = Success),
                        ) {
                            Text("জমা দিন", style = StudyMasterTypography.labelLarge)
                        }
                    }
                }
            }
        }
    }

    // ── Question Grid Overlay ─────────────────────────────────────────────
    if (showQuestionGrid) {
        QuestionGridOverlay(
            testQuestions = testQuestions,
            answers = answers,
            currentIndex = currentIndex,
            flaggedQuestions = flaggedQuestions,
            onQuestionSelect = { index ->
                viewModel.goToQuestion(index)
                showQuestionGrid = false
            },
            onFinishTest = {
                showQuestionGrid = false
                showFinishConfirm = true
            },
            onDismiss = { showQuestionGrid = false },
        )
    }

    // ── Finish Confirmation ───────────────────────────────────────────────
    if (showFinishConfirm) {
        AlertDialog(
            onDismissRequest = { showFinishConfirm = false },
            title = { Text("পরীক্ষা জমা দিন", style = StudyMasterTypography.titleMedium) },
            text = {
                Column {
                    Text(
                        text = "আপনি ${answeredCount.toBengaliDigits()}/${totalQuestions.toBengaliDigits()} প্রশ্নের উত্তর দিয়েছেন।",
                        style = StudyMasterTypography.bodyMedium,
                    )
                    if (answeredCount < totalQuestions) {
                        Text(
                            text = "${(totalQuestions - answeredCount).toBengaliDigits()}টি প্রশ্ন বাকি আছে। তবুও জমা দিতে চান?",
                            style = StudyMasterTypography.bodySmall.copy(color = Warning),
                            modifier = Modifier.padding(top = 4.dp),
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.finishTest()
                        showFinishConfirm = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Success),
                ) {
                    Text("জমা দিন", style = StudyMasterTypography.labelLarge)
                }
            },
            dismissButton = {
                TextButton(onClick = { showFinishConfirm = false }) {
                    Text("ফিরে যান", style = StudyMasterTypography.labelLarge)
                }
            },
        )
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// 3. TEST RESULTS SCREEN
// Grade, score, breakdown, trends, retake, share
// ═══════════════════════════════════════════════════════════════════════════════

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TestResultsScreen(
    viewModel: PracticeTestViewModel,
    onBack: () -> Unit,
) {
    val isDark = MaterialTheme.colorScheme.isDark
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val lastResult by viewModel.lastResult.collectAsState()
    val currentTest by viewModel.currentTest.collectAsState()
    val chapterBreakdown by viewModel.chapterBreakdown.collectAsState()
    val score by viewModel.score.collectAsState()
    val wrongQuestions by viewModel.wrongQuestions.collectAsState()

    val result = lastResult ?: return

    // Grade calculation (Bangladesh Education Scale)
    val gradeInfo = calculateGrade(result.percentage)

    // Time formatting
    val timeTakenMinutes = (result.timeTakenSeconds / 60).toInt()
    val timeTakenSeconds = (result.timeTakenSeconds % 60).toInt()
    val timeTakenText = "${timeTakenMinutes.toBengaliDigits()} মিনিট ${timeTakenSeconds.toBengaliDigits()} সেকেন্ড"

    // Score ring animation
    val scoreAnim = remember { Animatable(0f) }
    LaunchedEffect(result.percentage) {
        scoreAnim.animateTo(
            targetValue = result.percentage.toFloat() / 100f,
            animationSpec = tween(1200, easing = FastOutSlowInEasing),
        )
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding(),
        topBar = {
            TopAppBar(
                title = { Text("ফলাফল", style = StudyMasterTypography.titleLarge) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "ফিরুন",
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
            )
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(vertical = 8.dp),
        ) {
            // ── Score Ring + Grade ────────────────────────────────────────
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    // Score Ring
                    Box(
                        modifier = Modifier
                            .size(180.dp)
                            .padding(8.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            val strokeWidth = 12.dp.toPx()
                            val radius = (size.minDimension - strokeWidth) / 2

                            // Background ring
                            drawCircle(
                                color = if (isDark) DarkSurfaceVariant else Color(0xFFE0E0E0),
                                radius = radius,
                                center = center,
                                style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
                            )

                            // Progress ring
                            val sweep = 360f * scoreAnim.value
                            drawArc(
                                color = gradeInfo.color,
                                startAngle = -90f,
                                sweepAngle = sweep,
                                useCenter = false,
                                style = Stroke(
                                    width = strokeWidth,
                                    cap = StrokeCap.Round,
                                ),
                            )
                        }

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(
                                text = result.percentage.toBengaliDigits(1) + "%",
                                style = StudyMasterTypography.displaySmall.copy(
                                    fontFamily = EnglishFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    color = gradeInfo.color,
                                ),
                            )
                            Text(
                                text = "গ্রেড: $gradeInfo",
                                style = StudyMasterTypography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = gradeInfo.color,
                                ),
                            )
                        }
                    }

                    Spacer(Modifier.height(12.dp))
                    Text(
                        text = currentTest?.title ?: "অনুশীলনী পরীক্ষা",
                        style = StudyMasterTypography.titleMedium,
                        textAlign = TextAlign.Center,
                    )
                }
            }

            // ── Stats Row ─────────────────────────────────────────────────
            item {
                GlassmorphicCard(
                    modifier = Modifier.fillMaxWidth(),
                    variant = GlassCardVariant.OUTLINED,
                    cornerRadius = LocalGlassShapes.current.cardRadius,
                    padding = 16.dp,
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                    ) {
                        ResultStat(
                            value = result.correctCount.toBengaliDigits(),
                            label = "সঠিক",
                            color = Success,
                        )
                        ResultStat(
                            value = result.wrongCount.toBengaliDigits(),
                            label = "ভুল",
                            color = Error,
                        )
                        ResultStat(
                            value = result.skippedCount.toBengaliDigits(),
                            label = "স্কিপ",
                            color = Warning,
                        )
                        ResultStat(
                            value = timeTakenText,
                            label = "সময়",
                            color = Info,
                        )
                    }
                }
            }

            // ── Chapter-wise Breakdown (bar chart) ────────────────────────
            if (chapterBreakdown.isNotEmpty()) {
                item {
                    Text(
                        text = "📊 অধ্যায়ভিত্তিক বিশ্লেষণ",
                        style = StudyMasterTypography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                        ),
                    )
                }

                items(chapterBreakdown) { entry ->
                    ChapterBreakdownBar(
                        entry = entry,
                        isWeak = entry.percentage < 50,
                    )
                }
            }

            // ── Score Trend Graph (mini) ───────────────────────────────────
            item {
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "📈 স্কোর ট্রেন্ড",
                    style = StudyMasterTypography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                    ),
                    modifier = Modifier.padding(bottom = 8.dp),
                )

                GlassmorphicCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp),
                    variant = GlassCardVariant.OUTLINED,
                    cornerRadius = LocalGlassShapes.current.cardRadius,
                    padding = 12.dp,
                ) {
                    // Simple line graph using Canvas
                    val dataPoints = listOf(result.percentage.toFloat())
                    // In production this would load historical data
                    // For now show the current score as a bar
                    if (dataPoints.isNotEmpty()) {
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            val barWidth = size.width / (dataPoints.size * 2)
                            dataPoints.forEachIndexed { index, value ->
                                val barHeight = (value / 100f) * size.height
                                val x = barWidth * (index * 2 + 0.5f)
                                val y = size.height - barHeight

                                // Bar
                                drawRoundRect(
                                    color = gradeInfo.color,
                                    topLeft = Offset(x, y),
                                    size = Size(barWidth, barHeight),
                                    cornerRadius = CornerRadius(4.dp.toPx()),
                                )

                                // Value text (drawn below bar for simplicity)
                            }

                            // Grade line at 80%
                            val gradeLineY = size.height * 0.2f
                            drawLine(
                                color = Success.copy(alpha = 0.5f),
                                start = Offset(0f, gradeLineY),
                                end = Offset(size.width, gradeLineY),
                                strokeWidth = 1.dp.toPx(),
                            )

                            // Pass line at 33%
                            val passLineY = size.height * 0.67f
                            drawLine(
                                color = Error.copy(alpha = 0.3f),
                                start = Offset(0f, passLineY),
                                end = Offset(size.width, passLineY),
                                strokeWidth = 1.dp.toPx(),
                                pathEffect = androidx.compose.ui.graphics.PathEffect.dashPathEffect(
                                    floatArrayOf(8.dp.toPx(), 4.dp.toPx())
                                ),
                            )
                        }
                    } else {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = "পর্যাপ্ত ডেটা নেই",
                                style = StudyMasterTypography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                    }
                }
            }

            // ── Post-Test Analysis: Weak Chapters ──────────────────────────
            val weakChapters = chapterBreakdown.filter { it.percentage < 50 }
            if (weakChapters.isNotEmpty()) {
                item {
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = "⚠️ দুর্বল অধ্যায়",
                        style = StudyMasterTypography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = Error,
                        ),
                        modifier = Modifier.padding(bottom = 8.dp),
                    )

                    GlassmorphicCard(
                        modifier = Modifier.fillMaxWidth(),
                        variant = GlassCardVariant.FILLED,
                        tint = Error,
                        cornerRadius = LocalGlassShapes.current.cardRadiusSmall,
                        padding = 14.dp,
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text(
                                text = "এই অধ্যায়গুলোতে আপনার পারফরম্যান্স ৫০% এর নিচে। বিশেষ মনোযোগ দিন:",
                                style = StudyMasterTypography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface,
                            )
                            weakChapters.forEach { chapter ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                ) {
                                    Surface(
                                        shape = RoundedCornerShape(4.dp),
                                        color = Error.copy(alpha = 0.15f),
                                    ) {
                                        Text(
                                            text = chapter.percentage.toBengaliDigits(0) + "%",
                                            style = StudyMasterTypography.labelSmall.copy(
                                                fontFamily = EnglishFontFamily,
                                                fontWeight = FontWeight.Bold,
                                                color = Error,
                                            ),
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                        )
                                    }
                                    Text(
                                        text = chapter.chapterName,
                                        style = StudyMasterTypography.bodySmall,
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // ── Action Buttons ─────────────────────────────────────────────
            item {
                Spacer(Modifier.height(8.dp))
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    // Retake wrong answers
                    if (wrongQuestions.isNotEmpty()) {
                        Button(
                            onClick = {
                                viewModel.retakeWrongAnswers()
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(LocalGlassShapes.current.buttonRadius),
                            colors = ButtonDefaults.buttonColors(containerColor = Warning),
                        ) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp),
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(
                                text = "ভুল উত্তরগুলো আবার দিন (${wrongQuestions.size.toBengaliDigits()}টি)",
                                style = StudyMasterTypography.labelLarge,
                            )
                        }
                    }

                    // Review answers
                    OutlinedButton(
                        onClick = { viewModel.startReviewing() },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(LocalGlassShapes.current.buttonRadius),
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.HelpOutline,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp),
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = "উত্তর পর্যালোচনা করুন",
                            style = StudyMasterTypography.labelLarge,
                        )
                    }

                    // Share results
                    OutlinedButton(
                        onClick = {
                            val shareText = buildShareText(
                                testTitle = currentTest?.title ?: "অনুশীলনী পরীক্ষা",
                                result = result,
                                grade = gradeInfo.grade,
                            )
                            val sendIntent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_TEXT, shareText)
                            }
                            context.startActivity(Intent.createChooser(sendIntent, "ফলাফল শেয়ার করুন"))
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(LocalGlassShapes.current.buttonRadius),
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp),
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = "ফলাফল শেয়ার করুন",
                            style = StudyMasterTypography.labelLarge,
                        )
                    }

                    // Back to tests
                    TextButton(
                        onClick = onBack,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text(
                            text = "টেস্ট তালিকায় ফিরে যান",
                            style = StudyMasterTypography.labelLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
            }

            // Bottom spacing
            item { Spacer(Modifier.height(16.dp)) }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// 4. TEST REVIEW SCREEN
// Browse through answered questions with correct/wrong highlighting
// ═══════════════════════════════════════════════════════════════════════════════

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TestReviewScreen(
    viewModel: PracticeTestViewModel,
    onBack: () -> Unit,
) {
    val isDark = MaterialTheme.colorScheme.isDark

    val testQuestions by viewModel.testQuestions.collectAsState()
    val currentIndex by viewModel.currentQuestionIndex.collectAsState()
    val answers by viewModel.answers.collectAsState()

    val question = testQuestions.getOrNull(currentIndex) ?: return
    val q = question.question
    val selectedOption = answers[q.id] ?: 0
    val isCorrect = selectedOption == q.correctOption
    val isAnswered = selectedOption != 0

    val optionLabels = listOf("ক", "খ", "গ", "ঘ")
    val options = listOf(q.optionA, q.optionB, q.optionC, q.optionD)

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "পর্যালোচনা (${(currentIndex + 1).toBengaliDigits()}/${testQuestions.size.toBengaliDigits()})",
                        style = StudyMasterTypography.titleLarge,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "ফিরুন",
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 8.dp),
        ) {
            // Status banner
            Surface(
                shape = RoundedCornerShape(LocalGlassShapes.current.cardRadiusSmall),
                color = if (isAnswered) {
                    if (isCorrect) Success.copy(alpha = 0.1f) else Error.copy(alpha = 0.1f)
                } else {
                    Warning.copy(alpha = 0.1f)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = when {
                            !isAnswered -> "⏭️ স্কিপ করা হয়েছে"
                            isCorrect -> "✅ সঠিক উত্তর"
                            else -> "❌ ভুল উত্তর — সঠিক: ${optionLabels.getOrNull(q.correctOption - 1) ?: ""}"
                        },
                        style = StudyMasterTypography.bodyMedium.copy(
                            fontWeight = FontWeight.Medium,
                            color = if (isAnswered) {
                                if (isCorrect) Success else Error
                            } else {
                                Warning
                            },
                        ),
                    )
                }
            }

            // Question text
            Text(
                text = "প্রশ্ন ${(currentIndex + 1).toBengaliDigits()}",
                style = StudyMasterTypography.titleSmall,
                color = Primary,
            )

            GlassmorphicCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp, bottom = 12.dp),
                variant = GlassCardVariant.OUTLINED,
                cornerRadius = LocalGlassShapes.current.cardRadiusSmall,
                padding = 16.dp,
            ) {
                Text(
                    text = q.questionText,
                    style = StudyMasterTypography.bodyLarge,
                )
            }

            // Options with correct/wrong highlighting
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                options.forEachIndexed { index, optionText ->
                    val optionNumber = index + 1
                    val isThisCorrect = optionNumber == q.correctOption
                    val isThisSelected = optionNumber == selectedOption

                    val bgColor = when {
                        isThisCorrect -> Success.copy(alpha = if (isDark) 0.2f else 0.12f)
                        isThisSelected && !isCorrect -> Error.copy(alpha = if (isDark) 0.2f else 0.12f)
                        else -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
                    }
                    val borderColor = when {
                        isThisCorrect -> Success.copy(alpha = 0.5f)
                        isThisSelected && !isCorrect -> Error.copy(alpha = 0.5f)
                        else -> if (isDark) GlassBorderDark else GlassBorderLight
                    }

                    Surface(
                        shape = RoundedCornerShape(LocalGlassShapes.current.cardRadiusSmall),
                        color = bgColor,
                        border = BorderStroke(1.dp, borderColor),
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.Top,
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                        ) {
                            Surface(
                                shape = CircleShape,
                                color = when {
                                    isThisCorrect -> Success
                                    isThisSelected && !isCorrect -> Error
                                    else -> MaterialTheme.colorScheme.surfaceVariant
                                },
                                contentColor = Color.White,
                                modifier = Modifier.size(26.dp),
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        text = optionLabels[index],
                                        style = StudyMasterTypography.labelSmall.copy(
                                            fontFamily = BengaliFontFamily,
                                            fontWeight = FontWeight.Bold,
                                        ),
                                    )
                                }
                            }

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = optionText,
                                    style = StudyMasterTypography.bodyMedium,
                                )
                                when {
                                    isThisCorrect -> Text(
                                        text = "✓ সঠিক উত্তর",
                                        style = StudyMasterTypography.labelSmall.copy(
                                            color = Success,
                                            fontWeight = FontWeight.Bold,
                                        ),
                                    )
                                    isThisSelected && !isCorrect -> Text(
                                        text = "✗ আপনার উত্তর",
                                        style = StudyMasterTypography.labelSmall.copy(
                                            color = Error,
                                            fontWeight = FontWeight.Bold,
                                        ),
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Explanation
            if (q.explanation.isNotBlank()) {
                Spacer(Modifier.height(12.dp))
                GlassmorphicCard(
                    modifier = Modifier.fillMaxWidth(),
                    variant = GlassCardVariant.FILLED,
                    tint = Info,
                    cornerRadius = LocalGlassShapes.current.cardRadiusSmall,
                    padding = 14.dp,
                ) {
                    Text(
                        text = "💡 ব্যাখ্যা:",
                        style = StudyMasterTypography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Info,
                        ),
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = q.explanation,
                        style = StudyMasterTypography.bodyMedium,
                    )
                }
            }

            Spacer(Modifier.weight(1f))

            // Navigation
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                OutlinedButton(
                    onClick = { viewModel.previousQuestion() },
                    enabled = currentIndex > 0,
                    shape = RoundedCornerShape(LocalGlassShapes.current.buttonRadius),
                ) {
                    Icon(Icons.Default.NavigateBefore, null, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(4.dp))
                    Text("আগে", style = StudyMasterTypography.labelLarge)
                }
                if (currentIndex < testQuestions.size - 1) {
                    Button(
                        onClick = { viewModel.nextQuestion() },
                        shape = RoundedCornerShape(LocalGlassShapes.current.buttonRadius),
                    ) {
                        Text("পরবর্তী", style = StudyMasterTypography.labelLarge)
                        Spacer(Modifier.width(4.dp))
                        Icon(Icons.Default.NavigateNext, null, modifier = Modifier.size(18.dp))
                    }
                }
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// COMPOSEABLE COMPONENTS
// ═══════════════════════════════════════════════════════════════════════════════

// ─── Stat Item ────────────────────────────────────────────────────────────

@Composable
private fun StatItem(
    value: String,
    label: String,
    valueColor: Color = MaterialTheme.colorScheme.onSurface,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            style = StudyMasterTypography.headlineMedium.copy(
                fontFamily = EnglishFontFamily,
                fontWeight = FontWeight.Bold,
                color = valueColor,
            ),
        )
        Text(
            text = label,
            style = StudyMasterTypography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

// ─── Result Stat ──────────────────────────────────────────────────────────

@Composable
private fun ResultStat(
    value: String,
    label: String,
    color: Color,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            style = StudyMasterTypography.titleMedium.copy(
                fontFamily = EnglishFontFamily,
                fontWeight = FontWeight.Bold,
                color = color,
            ),
        )
        Text(
            text = label,
            style = StudyMasterTypography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

// ─── Filter Chip ──────────────────────────────────────────────────────────

@Composable
private fun FilterChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    color: Color = Primary,
) {
    val isDark = MaterialTheme.colorScheme.isDark
    Surface(
        shape = RoundedCornerShape(LocalGlassShapes.current.chipRadius),
        color = if (selected) color.copy(alpha = if (isDark) 0.25f else 0.15f)
        else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
        border = BorderStroke(
            width = if (selected) 1.dp else 0.dp,
            color = if (selected) color.copy(alpha = 0.5f) else Color.Transparent,
        ),
        modifier = Modifier.clickable(onClick = onClick),
    ) {
        Text(
            text = label,
            style = StudyMasterTypography.labelMedium.copy(
                fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
                color = if (selected) color else MaterialTheme.colorScheme.onSurfaceVariant,
            ),
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp),
        )
    }
}

// ─── Test List Item ───────────────────────────────────────────────────────

@Composable
private fun TestListItem(
    test: PracticeTest,
    onStart: () -> Unit,
    onDelete: () -> Unit,
) {
    val isDark = MaterialTheme.colorScheme.isDark
    val isCompleted = test.completedAt != null

    GlassmorphicCard(
        modifier = Modifier.fillMaxWidth(),
        variant = GlassCardVariant.ELEVATED,
        cornerRadius = LocalGlassShapes.current.cardRadius,
        padding = 0.dp,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Icon
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = if (isCompleted) Success.copy(alpha = 0.15f) else Primary.copy(alpha = 0.15f),
                modifier = Modifier.size(44.dp),
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = if (isCompleted) Icons.Default.Refresh else Icons.Default.PlayArrow,
                        contentDescription = null,
                        tint = if (isCompleted) Success else Primary,
                        modifier = Modifier.size(22.dp),
                    )
                }
            }

            Spacer(Modifier.width(12.dp))

            // Info
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = test.title,
                    style = StudyMasterTypography.titleSmall.copy(
                        fontWeight = FontWeight.SemiBold,
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(Modifier.height(2.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text(
                        text = "${test.totalQuestions.toBengaliDigits()} প্রশ্ন",
                        style = StudyMasterTypography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Text(
                        text = "•",
                        style = StudyMasterTypography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Text(
                        text = "${test.durationMinutes.toBengaliDigits()} মিনিট",
                        style = StudyMasterTypography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    if (test.negativeMarkingEnabled) {
                        Text(
                            text = "•",
                            style = StudyMasterTypography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                        Text(
                            text = "নেগেটিভ মার্কিং",
                            style = StudyMasterTypography.labelSmall,
                            color = Error,
                        )
                    }
                }
                if (test.subjectName != null) {
                    Text(
                        text = test.subjectName,
                        style = StudyMasterTypography.labelSmall,
                        color = Primary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }

            Spacer(Modifier.width(8.dp))

            // Start button
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = if (isCompleted) Success else Primary,
                modifier = Modifier.clickable(onClick = onStart),
            ) {
                Text(
                    text = if (isCompleted) "আবার" else "শুরু",
                    style = StudyMasterTypography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                    ),
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                )
            }
        }
    }
}

// ─── Chapter Breakdown Bar ────────────────────────────────────────────────

@Composable
private fun ChapterBreakdownBar(
    entry: ChapterBreakdownEntry,
    isWeak: Boolean,
) {
    val isDark = MaterialTheme.colorScheme.isDark
    val barColor = when {
        entry.percentage >= 80 -> Success
        entry.percentage >= 50 -> Warning
        else -> Error
    }
    val animatedPct by animateFloatAsState(
        targetValue = entry.percentage.toFloat() / 100f,
        animationSpec = tween(800, easing = FastOutSlowInEasing),
        label = "chapterBar",
    )

    Surface(
        shape = RoundedCornerShape(LocalGlassShapes.current.cardRadiusSmall),
        color = if (isWeak) Error.copy(alpha = 0.06f) else Color.Transparent,
        border = if (isWeak) BorderStroke(1.dp, Error.copy(alpha = 0.2f)) else null,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = entry.chapterName,
                    style = StudyMasterTypography.bodySmall.copy(
                        fontWeight = FontWeight.Medium,
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f),
                )
                Text(
                    text = "${entry.correctCount.toBengaliDigits()}/${entry.totalQuestions.toBengaliDigits()}",
                    style = StudyMasterTypography.labelSmall.copy(
                        fontFamily = EnglishFontFamily,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    ),
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = "${entry.percentage.toBengaliDigits(0)}%",
                    style = StudyMasterTypography.labelSmall.copy(
                        fontFamily = EnglishFontFamily,
                        fontWeight = FontWeight.Bold,
                        color = barColor,
                    ),
                )
            }
            Spacer(Modifier.height(6.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .background(
                        color = if (isDark) DarkSurfaceVariant else Color(0xFFE0E0E0),
                        shape = RoundedCornerShape(3.dp),
                    ),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(animatedPct)
                        .height(6.dp)
                        .background(
                            color = barColor,
                            shape = RoundedCornerShape(3.dp),
                        ),
                )
            }
        }
    }
}

// ─── Question Grid Overlay ────────────────────────────────────────────────

@Composable
private fun QuestionGridOverlay(
    testQuestions: List<TestQuestion>,
    answers: Map<Long, Int>,
    currentIndex: Int,
    flaggedQuestions: Set<Int>,
    onQuestionSelect: (Int) -> Unit,
    onFinishTest: () -> Unit,
    onDismiss: () -> Unit,
) {
    val isDark = MaterialTheme.colorScheme.isDark
    val answeredCount = answers.size

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(LocalGlassShapes.current.dialogRadius),
            color = MaterialTheme.colorScheme.surface,
            border = BorderStroke(1.dp, if (isDark) GlassBorderDark else GlassBorderLight),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "প্রশ্ন নেভিগেশন",
                        style = StudyMasterTypography.titleMedium,
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "বন্ধ")
                    }
                }

                Spacer(Modifier.height(8.dp))

                // Legend
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    GridLegend(color = Success, label = "উত্তরিত")
                    GridLegend(color = Tertiary, label = "ফ্ল্যাগ")
                    GridLegend(color = MaterialTheme.colorScheme.surfaceVariant, label = "উত্তরহীন")
                }

                Spacer(Modifier.height(12.dp))

                // Grid
                val columns = 5
                val rows = (testQuestions.size + columns - 1) / columns

                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    for (row in 0 until rows) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                        ) {
                            for (col in 0 until columns) {
                                val index = row * columns + col
                                if (index < testQuestions.size) {
                                    val tq = testQuestions[index]
                                    val isAnswered = answers.containsKey(tq.question.id)
                                    val isFlagged = index in flaggedQuestions
                                    val isCurrent = index == currentIndex

                                    val cellColor = when {
                                        isCurrent -> Primary.copy(alpha = 0.4f)
                                        isFlagged -> Tertiary.copy(alpha = 0.3f)
                                        isAnswered -> Success.copy(alpha = 0.25f)
                                        else -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                                    }
                                    val cellBorderColor = when {
                                        isCurrent -> Primary
                                        isFlagged -> Tertiary
                                        isAnswered -> Success.copy(alpha = 0.5f)
                                        else -> Color.Transparent
                                    }

                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = cellColor,
                                        border = BorderStroke(
                                            width = if (isCurrent || isFlagged || isAnswered) 1.dp else 0.dp,
                                            color = cellBorderColor,
                                        ),
                                        modifier = Modifier
                                            .weight(1f)
                                            .aspectRatio(1f)
                                            .clickable { onQuestionSelect(index) },
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Text(
                                                text = (index + 1).toString(),
                                                style = StudyMasterTypography.labelMedium.copy(
                                                    fontFamily = EnglishFontFamily,
                                                    fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal,
                                                    color = when {
                                                        isCurrent -> Primary
                                                        isFlagged -> Tertiary
                                                        isAnswered -> Success
                                                        else -> MaterialTheme.colorScheme.onSurfaceVariant
                                                    },
                                                ),
                                            )
                                        }
                                    }
                                } else {
                                    Spacer(Modifier.weight(1f))
                                }
                            }
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))

                // Summary & Finish
                Text(
                    text = "উত্তরিত: ${answeredCount.toBengaliDigits()}/${testQuestions.size.toBengaliDigits()}",
                    style = StudyMasterTypography.bodyMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                )

                Spacer(Modifier.height(8.dp))

                Button(
                    onClick = onFinishTest,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(LocalGlassShapes.current.buttonRadius),
                    colors = ButtonDefaults.buttonColors(containerColor = Success),
                ) {
                    Text(
                        text = "পরীক্ষা জমা দিন",
                        style = StudyMasterTypography.labelLarge,
                    )
                }
            }
        }
    }
}

// ─── Grid Legend ───────────────────────────────────────────────────────────

@Composable
private fun GridLegend(
    color: Color,
    label: String,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Surface(
            shape = RoundedCornerShape(3.dp),
            color = color.copy(alpha = 0.4f),
            modifier = Modifier.size(14.dp),
        ) {}
        Text(
            text = label,
            style = StudyMasterTypography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

// ─── Create Test Dialog ───────────────────────────────────────────────────

@Composable
private fun CreateTestDialog(
    onDismiss: () -> Unit,
    onCreate: (
        title: String,
        subjectId: Long?,
        subjectName: String?,
        questionCount: Int,
        duration: Int,
        negEnabled: Boolean,
        negValue: Double,
        isMixed: Boolean,
    ) -> Unit,
) {
    val isDark = MaterialTheme.colorScheme.isDark

    var title by remember { mutableStateOf("") }
    var questionCount by remember { mutableIntStateOf(10) }
    var durationMinutes by remember { mutableIntStateOf(15) }
    var negativeMarkingEnabled by remember { mutableStateOf(false) }
    var negativeMarkValue by remember { mutableStateOf("0.25") }
    var isMixedSubject by remember { mutableStateOf(false) }

    // Subject selection (simplified — in production load from ViewModel)
    var selectedSubjectId by remember { mutableStateOf<Long?>(null) }
    var selectedSubjectName by remember { mutableStateOf<String?>(null) }

    val durationOptions = listOf(5, 10, 15, 20, 30, 45, 60, 90, 120)
    val questionOptions = listOf(5, 10, 15, 20, 25, 30, 40, 50)

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(LocalGlassShapes.current.dialogRadius),
            color = MaterialTheme.colorScheme.surface,
            border = BorderStroke(1.dp, if (isDark) GlassBorderDark else GlassBorderLight),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .heightIn(max = 500.dp), // Scrollable via LazyRows inside
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "নতুন টেস্ট তৈরি",
                        style = StudyMasterTypography.titleMedium,
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "বন্ধ")
                    }
                }

                Spacer(Modifier.height(12.dp))

                // Title
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("টেস্টের নাম", style = StudyMasterTypography.bodySmall) },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = StudyMasterTypography.bodyMedium,
                    singleLine = true,
                    shape = RoundedCornerShape(LocalGlassShapes.current.inputFieldRadius),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = if (isDark) GlassBorderDark else GlassBorderLight,
                        focusedBorderColor = Primary,
                    ),
                )

                Spacer(Modifier.height(10.dp))

                // Mixed subject toggle
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = "মিক্সড সাবজেক্ট টেস্ট",
                        style = StudyMasterTypography.bodyMedium,
                    )
                    Switch(
                        checked = isMixedSubject,
                        onCheckedChange = { isMixedSubject = it },
                        colors = SwitchDefaults.colors(checkedTrackColor = Primary),
                    )
                }

                Spacer(Modifier.height(6.dp))

                // Number of questions
                Text(
                    text = "প্রশ্ন সংখ্যা: ${questionCount.toBengaliDigits()}",
                    style = StudyMasterTypography.bodyMedium,
                    modifier = Modifier.padding(bottom = 6.dp),
                )
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    items(questionOptions) { count ->
                        FilterChip(
                            label = count.toBengaliDigits(),
                            selected = questionCount == count,
                            onClick = { questionCount = count },
                        )
                    }
                }

                Spacer(Modifier.height(8.dp))

                // Duration
                Text(
                    text = "সময়কাল: ${durationMinutes.toBengaliDigits()} মিনিট",
                    style = StudyMasterTypography.bodyMedium,
                    modifier = Modifier.padding(bottom = 6.dp),
                )
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    items(durationOptions) { mins ->
                        FilterChip(
                            label = "${mins.toBengaliDigits()} মি.",
                            selected = durationMinutes == mins,
                            onClick = { durationMinutes = mins },
                        )
                    }
                }

                Spacer(Modifier.height(8.dp))

                // Negative marking
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = "নেগেটিভ মার্কিং",
                        style = StudyMasterTypography.bodyMedium,
                    )
                    Switch(
                        checked = negativeMarkingEnabled,
                        onCheckedChange = { negativeMarkingEnabled = it },
                        colors = SwitchDefaults.colors(checkedTrackColor = Error),
                    )
                }

                AnimatedVisibility(visible = negativeMarkingEnabled) {
                    OutlinedTextField(
                        value = negativeMarkValue,
                        onValueChange = { negativeMarkValue = it },
                        label = { Text("নেগেটিভ মান", style = StudyMasterTypography.bodySmall) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        textStyle = StudyMasterTypography.bodyMedium.copy(
                            fontFamily = EnglishFontFamily,
                        ),
                        singleLine = true,
                        shape = RoundedCornerShape(LocalGlassShapes.current.inputFieldRadius),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = if (isDark) GlassBorderDark else GlassBorderLight,
                            focusedBorderColor = Error,
                        ),
                    )
                }

                Spacer(Modifier.height(16.dp))

                // Create button
                Button(
                    onClick = {
                        if (title.isNotBlank()) {
                            onCreate(
                                title,
                                selectedSubjectId,
                                selectedSubjectName,
                                questionCount,
                                durationMinutes,
                                negativeMarkingEnabled,
                                negativeMarkValue.toDoubleOrNull() ?: 0.25,
                                isMixedSubject,
                            )
                        }
                    },
                    enabled = title.isNotBlank(),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(LocalGlassShapes.current.buttonRadius),
                ) {
                    Text("টে스্ট তৈরি করুন", style = StudyMasterTypography.labelLarge)
                }
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// ENUMS & HELPERS
// ═══════════════════════════════════════════════════════════════════════════════

private enum class TestTypeFilter(val bengaliLabel: String) {
    ALL("সব"),
    SUBJECT_WISE("বিষয়ভিত্তিক"),
    MIXED("মিক্সড"),
    CUSTOM("কাস্টম"),
}

private enum class DifficultyFilter(val bengaliLabel: String) {
    ALL("সব ধরন"),
    EASY("সহজ"),
    MEDIUM("মাঝারি"),
    HARD("কঠিন"),
}

private enum class DurationFilter(val bengaliLabel: String) {
    ALL("সব সময়"),
    SHORT("১০ মি. পর্যন্ত"),
    MEDIUM("১৫-৩০ মি."),
    LONG("৩০ মি. উপরে"),
}

// ─── Grade Calculation (Bangladesh Education Scale) ──────────────────────

private data class GradeInfo(
    val grade: String,
    val color: Color,
    val gpa: Double,
    val description: String,
) {
    override fun toString(): String = grade
}

private fun calculateGrade(percentage: Double): GradeInfo {
    return when {
        percentage >= 80 -> GradeInfo("A+", Success, 5.0, "চমৎকার")
        percentage >= 70 -> GradeInfo("A", Primary, 4.0, "অত্যন্ত ভালো")
        percentage >= 60 -> GradeInfo("B", Info, 3.5, "ভালো")
        percentage >= 50 -> GradeInfo("C", Warning, 3.0, "মোটামুটি ভালো")
        percentage >= 40 -> GradeInfo("D", Secondary, 2.5, "গড়")
        percentage >= 33 -> GradeInfo("E", Tertiary, 2.0, "ন্যূনতম")
        else -> GradeInfo("F", Error, 0.0, "অনুত্তীর্ণ")
    }
}

// ─── Share Text Builder ───────────────────────────────────────────────────

private fun buildShareText(
    testTitle: String,
    result: com.porashona.studymaster.data.model.PracticeTestResult,
    grade: String,
): String {
    val percentage = result.percentage.toBengaliDigits(1)
    return buildString {
        append("📊 StudyMaster পরীক্ষার ফলাফল\n")
        append("━━━━━━━━━━━━━━━━━━\n")
        append("📝 পরীক্ষা: $testTitle\n")
        append("🏅 গ্রেড: $grade\n")
        append("🎯 স্কোর: $percentage%\n")
        append("✅ সঠিক: ${result.correctCount.toBengaliDigits()}\n")
        append("❌ ভুল: ${result.wrongCount.toBengaliDigits()}\n")
        append("⏭️ স্কিপ: ${result.skippedCount.toBengaliDigits()}\n")
        append("━━━━━━━━━━━━━━━━━━\n")
        append("📚 StudyMaster দিয়ে পড়াশোনা করুন!")
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Bengali Font Family reference for use in option labels
// ═══════════════════════════════════════════════════════════════════════════════

private val BengaliFontFamily = com.porashona.studymaster.ui.compose.theme.BengaliFontFamily
