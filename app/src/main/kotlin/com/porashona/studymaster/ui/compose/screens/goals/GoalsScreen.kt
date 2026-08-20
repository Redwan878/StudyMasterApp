
package com.porashona.studymaster.ui.compose.screens.goals

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.porashona.studymaster.data.model.Goal
import com.porashona.studymaster.data.model.GoalType
import com.porashona.studymaster.data.model.Subject
import com.porashona.studymaster.ui.compose.components.ConfirmDeleteDialog
import com.porashona.studymaster.ui.compose.components.EmptyStateView
import com.porashona.studymaster.ui.compose.components.GlassCardVariant
import com.porashona.studymaster.ui.compose.components.GlassmorphicCard
import com.porashona.studymaster.ui.compose.components.LocalGlassShapes
import com.porashona.studymaster.ui.compose.components.LocalMotion
import com.porashona.studymaster.ui.compose.components.Priority
import com.porashona.studymaster.ui.compose.components.PriorityBadge
import com.porashona.studymaster.ui.compose.components.StreakFireBadge
import com.porashona.studymaster.ui.compose.components.SubjectChip
import com.porashona.studymaster.ui.compose.theme.*
import com.porashona.studymaster.ui.compose.viewmodels.GoalEvent
import com.porashona.studymaster.ui.compose.viewmodels.GoalStreak
import com.porashona.studymaster.ui.compose.viewmodels.GoalsViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

// ═══════════════════════════════════════════════════════════════════════════════
// GoalsScreen — Full-featured goals management with active/archived tabs,
// streak tracking, progress updates, achievement triggers, and more.
// All text in Bengali.
// ═══════════════════════════════════════════════════════════════════════════════

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoalsScreen(
    viewModel: GoalsViewModel = hiltViewModel(),
) {
    val goals by viewModel.goals.collectAsState()
    val archivedGoals by viewModel.archivedGoals.collectAsState()
    val streaks by viewModel.streaks.collectAsState()
    val goalTypeFilter by viewModel.goalTypeFilter.collectAsState()
    val event by viewModel.events.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val isDark = MaterialTheme.colorScheme.isDark
    val shapes = LocalGlassShapes.current

    var selectedTab by remember { mutableStateOf(0) } // 0 = Active, 1 = Archived
    var showAddGoalSheet by remember { mutableStateOf(false) }
    var editingGoal by remember { mutableStateOf<Goal?>(null) }
    var goalToDelete by remember { mutableStateOf<Goal?>(null) }
    var goalToUpdateProgress by remember { mutableStateOf<Goal?>(null) }
    var showAchievementPopup by remember { mutableStateOf(false) }
    var achievementTitle by remember { mutableStateOf("") }
    var showDailyChecklist by remember { mutableStateOf(false) }

    // Handle events
    LaunchedEffect(event) {
        when (event) {
            is GoalEvent.GoalCreated -> {
                snackbarHostState.showSnackbar("গোল তৈরি হয়েছে!", duration = SnackbarDuration.Short)
            }
            is GoalEvent.GoalCompleted -> {
                achievementTitle = "\"${event.title}\" সম্পন্ন!"
                showAchievementPopup = true
                snackbarHostState.showSnackbar(
                    "🎉 অভিনন্দন! গোল সম্পন্ন হয়েছে!",
                    duration = SnackbarDuration.Long,
                )
            }
            is GoalEvent.GoalArchived -> {
                snackbarHostState.showSnackbar("গোল আর্কাইভ করা হয়েছে", duration = SnackbarDuration.Short)
            }
            is GoalEvent.GoalDeleted -> {
                snackbarHostState.showSnackbar("গোল মুছে ফেলা হয়েছে", duration = SnackbarDuration.Short)
            }
            null -> {}
        }
        viewModel.clearEvent()
    }

    // Filtered active goals
    val filteredGoals = remember(goals, goalTypeFilter) {
        if (goalTypeFilter != null) {
            goals.filter { it.goalType == goalTypeFilter }
        } else {
            goals
        }
    }

    // Daily goals
    val dailyGoals = remember(goals) {
        goals.filter { it.goalType == GoalType.DAILY && !it.isCompleted }
    }

    // Compute stats
    val totalActive by remember(goals) {
        mutableStateOf(goals.size)
    }
    val completedToday by remember(goals) {
        mutableStateOf(goals.count { it.isCompleted })
    }
    val totalArchived by remember(archivedGoals) {
        mutableStateOf(archivedGoals.size)
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .statusBarsPadding(),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            if (selectedTab == 0) {
                GlassmorphicGoalFAB(onClick = {
                    editingGoal = null
                    showAddGoalSheet = true
                })
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            // ─── Header ────────────────────────────────────────────────────────
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    "গোল",
                    style = StudyMasterTypography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    // Daily checklist button
                    if (dailyGoals.isNotEmpty()) {
                        IconButton(onClick = { showDailyChecklist = !showDailyChecklist }) {
                            Icon(
                                Icons.Default.CheckCircle,
                                contentDescription = "দৈনিক চেকলিস্ট",
                                tint = Primary,
                                modifier = Modifier.size(22.dp),
                            )
                        }
                    }
                }
            }

            // ─── Daily Checklist (expandable) ──────────────────────────────────
            AnimatedVisibility(
                visible = showDailyChecklist && dailyGoals.isNotEmpty(),
                enter = expandVertically() + slideInVertically(),
                exit = shrinkVertically() + fadeOut(),
            ) {
                GlassmorphicCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    variant = GlassCardVariant.FILLED,
                    tint = Primary,
                    cornerRadius = shapes.cardRadius,
                    padding = 12.dp,
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                            ) {
                                Icon(
                                    Icons.Default.Flag,
                                    contentDescription = null,
                                    tint = Primary,
                                    modifier = Modifier.size(20.dp),
                                )
                                Text(
                                    "আজকের দৈনিক গোল",
                                    style = StudyMasterTypography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                )
                            }
                            Text(
                                "${dailyGoals.count { it.currentMinutes >= it.targetMinutes }.toBengaliDigits()}/${dailyGoals.size.toBengaliDigits()}",
                                style = StudyMasterTypography.labelMedium.copy(
                                    fontFamily = EnglishFontFamily,
                                    color = Primary,
                                ),
                            )
                        }

                        Spacer(Modifier.height(10.dp))

                        dailyGoals.forEach { goal ->
                            val isDone = goal.currentMinutes >= goal.targetMinutes
                            val progress = if (goal.targetMinutes > 0) {
                                (goal.currentMinutes.toFloat() / goal.targetMinutes.toFloat()).coerceIn(0f, 1f)
                            } else 0f

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                // Small checkbox
                                val checkBg by animateColorAsState(
                                    targetValue = if (isDone) Primary else Color.Transparent,
                                    animationSpec = LocalMotion.current.colorTransition,
                                    label = "dailyCheck",
                                )
                                val checkBorder by animateColorAsState(
                                    targetValue = if (isDone) Primary else MaterialTheme.colorScheme.outline.copy(alpha = 0.6f),
                                    animationSpec = LocalMotion.current.colorTransition,
                                    label = "dailyCheckBorder",
                                )
                                Box(
                                    modifier = Modifier
                                        .size(22.dp)
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(checkBg)
                                        .border(1.5.dp, checkBorder, RoundedCornerShape(4.dp))
                                        .clickable {
                                            if (!isDone) {
                                                val remaining = goal.targetMinutes - goal.currentMinutes
                                                viewModel.updateProgress(goal.id, remaining.coerceAtLeast(1))
                                            }
                                        },
                                    contentAlignment = Alignment.Center,
                                ) {
                                    if (isDone) {
                                        Icon(Icons.Default.Check, null, tint = Color.White, modifier = Modifier.size(14.dp))
                                    }
                                }

                                Spacer(Modifier.width(10.dp))

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        goal.title,
                                        style = StudyMasterTypography.bodySmall.copy(
                                            fontWeight = FontWeight.Medium,
                                            textDecoration = if (isDone) TextDecoration.LineThrough else TextDecoration.None,
                                            color = if (isDone)
                                                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                                            else
                                                MaterialTheme.colorScheme.onSurface,
                                        ),
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                    )
                                    // Mini progress bar
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth(0.6f)
                                            .height(4.dp)
                                            .clip(RoundedCornerShape(2.dp))
                                            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
                                            .padding(top = 2.dp),
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth(fraction = progress)
                                                .height(2.dp)
                                                .clip(RoundedCornerShape(1.dp))
                                                .background(Primary),
                                        )
                                    }
                                }

                                Text(
                                    "${goal.currentMinutes.toBengaliDigits()}/${goal.targetMinutes.toBengaliDigits()} মি.",
                                    style = StudyMasterTypography.labelSmall.copy(
                                        fontSize = 10.sp,
                                        fontFamily = EnglishFontFamily,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    ),
                                )
                            }
                        }
                    }
                }
            }

            // ─── Stats Row ─────────────────────────────────────────────────────
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                StatPill(
                    label = "সক্রিয়",
                    value = totalActive.toBengaliDigits(),
                    color = Primary,
                    modifier = Modifier.weight(1f),
                )
                StatPill(
                    label = "সম্পন্ন",
                    value = completedToday.toBengaliDigits(),
                    color = Success,
                    modifier = Modifier.weight(1f),
                )
                StatPill(
                    label = "আর্কাইভ",
                    value = totalArchived.toBengaliDigits(),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.weight(1f),
                )
                // Best streak
                val bestStreak = streaks.maxOfOrNull { it.currentStreak } ?: 0
                StatPill(
                    label = "সেরা স্ট্রিক",
                    value = "${bestStreak.toBengaliDigits()}🔥",
                    color = StreakFire,
                    modifier = Modifier.weight(1f),
                )
            }

            // ─── Tabs (Active / Archived) ──────────────────────────────────────
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.onSurface,
                divider = {},
                indicator = { tabPositions ->
                    if (tabPositions.isNotEmpty()) {
                        TabRowDefaults.SecondaryIndicator(
                            modifier = Modifier
                                .tabIndicatorOffset(tabPositions[selectedTab])
                                .padding(horizontal = 40.dp),
                            color = Primary,
                            height = 3.dp,
                        )
                    }
                },
            ) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    text = {
                        Text(
                            "সক্রিয় গোল",
                            style = StudyMasterTypography.labelMedium.copy(
                                fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Normal,
                            ),
                        )
                    },
                    selectedContentColor = Primary,
                    unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = {
                        Text(
                            "আর্কাইভ",
                            style = StudyMasterTypography.labelMedium.copy(
                                fontWeight = if (selectedTab == 1) FontWeight.Bold else FontWeight.Normal,
                            ),
                        )
                    },
                    selectedContentColor = Primary,
                    unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }

            HorizontalDivider(
                color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f),
            )

            // ─── Goal Type Filter (for active tab) ─────────────────────────────
            if (selectedTab == 0) {
                val goalTypeLabels = listOf(
                    null to "সব",
                    GoalType.DAILY to "দৈনিক",
                    GoalType.WEEKLY to "সাপ্তাহিক",
                    GoalType.SUBJECT_SPECIFIC to "বিষয়ভিত্তিক",
                    GoalType.CUSTOM to "কাস্টম",
                )

                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(goalTypeLabels, key = { it.first?.name ?: "all" }) { (type, label) ->
                        val isSelected = goalTypeFilter == type
                        Surface(
                            modifier = Modifier
                                .clip(RoundedCornerShape(shapes.chipRadius))
                                .clickable { viewModel.setGoalTypeFilter(type) },
                            shape = RoundedCornerShape(shapes.chipRadius),
                            color = if (isSelected)
                                Primary.copy(alpha = if (isDark) 0.25f else 0.15f)
                            else
                                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                            contentColor = if (isSelected) Primary else MaterialTheme.colorScheme.onSurfaceVariant,
                        ) {
                            Text(
                                label,
                                style = StudyMasterTypography.labelMedium.copy(
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                ),
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp),
                            )
                        }
                    }
                }
            }

            // ─── Content ───────────────────────────────────────────────────────
            AnimatedContent(
                targetState = selectedTab,
                transitionSpec = {
                    fadeIn() togetherWith fadeOut()
                },
                label = "tabContent",
            ) { tab ->
                when (tab) {
                    0 -> {
                        // Active goals
                        if (filteredGoals.isEmpty()) {
                            EmptyStateView(
                                title = "কোনো সক্রিয় গোল নেই",
                                description = "একটি গোল সেট করুন এবং পড়ার লক্ষ্য ঠিক করুন। + বাটনে ট্যাপ করুন।",
                                icon = Icons.Default.Flag,
                                actionLabel = "নতুন গোল যোগ করুন",
                                onAction = {
                                    editingGoal = null
                                    showAddGoalSheet = true
                                },
                                modifier = Modifier.fillMaxSize(),
                            )
                        } else {
                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp),
                            ) {
                                items(filteredGoals, key = { it.id }) { goal ->
                                    GoalCard(
                                        goal = goal,
                                        streak = streaks.find { it.goalId == goal.id },
                                        onProgressUpdate = { goalToUpdateProgress = goal },
                                        onArchive = { viewModel.archiveGoal(goal.id) },
                                        onDelete = { goalToDelete = goal },
                                        onEdit = { editingGoal = goal },
                                    )
                                }
                            }
                        }
                    }
                    1 -> {
                        // Archived goals
                        if (archivedGoals.isEmpty()) {
                            EmptyStateView(
                                title = "কোনো আর্কাইভ গোল নেই",
                                description = "সম্পন্ন গোল এখানে আর্কাইভ হবে।",
                                icon = Icons.Default.Archive,
                                modifier = Modifier.fillMaxSize(),
                            )
                        } else {
                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                            ) {
                                items(archivedGoals, key = { it.id }) { goal ->
                                    ArchivedGoalCard(
                                        goal = goal,
                                        onDelete = { goalToDelete = goal },
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // ─── Add/Edit Goal Sheet ──────────────────────────────────────────────────
    if (showAddGoalSheet || editingGoal != null) {
        GoalEditorSheet(
            goal = editingGoal,
            onDismiss = {
                showAddGoalSheet = false
                editingGoal = null
            },
            onSave = { title, targetMinutes, goalType, subjectId, subjectName ->
                if (editingGoal != null) {
                    viewModel.updateGoal(
                        editingGoal!!.copy(
                            title = title,
                            targetMinutes = targetMinutes,
                            goalType = goalType,
                            subjectId = subjectId,
                            subjectName = subjectName,
                        )
                    )
                } else {
                    viewModel.addGoal(
                        title = title,
                        targetMinutes = targetMinutes,
                        goalType = goalType,
                        subjectId = subjectId,
                        subjectName = subjectName,
                    )
                }
                showAddGoalSheet = false
                editingGoal = null
            },
        )
    }

    // ─── Progress Update Dialog ───────────────────────────────────────────────
    goalToUpdateProgress?.let { goal ->
        ProgressUpdateDialog(
            goal = goal,
            onDismiss = { goalToUpdateProgress = null },
            onConfirm = { minutes ->
                viewModel.updateProgress(goal.id, minutes)
                goalToUpdateProgress = null
            },
        )
    }

    // ─── Achievement Popup ────────────────────────────────────────────────────
    if (showAchievementPopup) {
        AchievementCelebrationDialog(
            title = achievementTitle,
            onDismiss = { showAchievementPopup = false },
        )
    }

    // ─── Delete Confirmation ─────────────────────────────────────────────────
    goalToDelete?.let { goal ->
        ConfirmDeleteDialog(
            title = "গোল মুছুন",
            message = "\"${goal.title}\" গোলটি মুছে ফেলতে চান?",
            onConfirm = {
                viewModel.deleteGoal(goal.id)
                goalToDelete = null
            },
            onDismiss = { goalToDelete = null },
        )
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Glassmorphic Goal FAB
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun GlassmorphicGoalFAB(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val isDark = MaterialTheme.colorScheme.isDark
    Box(
        modifier = modifier
            .padding(end = 16.dp, bottom = 16.dp)
            .size(64.dp)
            .graphicsLayer {
                shape = RoundedCornerShape(20.dp)
                clip = true
            }
            .background(
                if (isDark) GlassDarkAlpha60.copy(alpha = 0.75f) else GlassLightAlpha90.copy(alpha = 0.85f),
                RoundedCornerShape(20.dp),
            )
            .border(
                width = 1.dp,
                color = if (isDark) GlassBorderDark.copy(alpha = 0.5f) else GlassBorderLight.copy(alpha = 0.6f),
                shape = RoundedCornerShape(20.dp),
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Primary.copy(alpha = 0.25f),
                            Tertiary.copy(alpha = 0.15f),
                        ),
                    ),
                    RoundedCornerShape(20.dp),
                ),
        )
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "নতুন গোল",
            tint = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.size(28.dp),
        )
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// StatPill — compact stat display
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun StatPill(
    label: String,
    value: String,
    color: Color,
    modifier: Modifier = Modifier,
) {
    val isDark = MaterialTheme.colorScheme.isDark
    GlassmorphicCard(
        modifier = modifier.height(56.dp),
        variant = GlassCardVariant.FILLED,
        tint = color,
        cornerRadius = LocalGlassShapes.current.cardRadiusSmall,
        padding = 8.dp,
        animated = false,
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                value,
                style = StudyMasterTypography.titleSmall.copy(
                    fontFamily = EnglishFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = color,
                ),
            )
            Text(
                label,
                style = StudyMasterTypography.labelSmall.copy(
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                ),
            )
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// GoalCard — full goal card with progress, streak, actions
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun GoalCard(
    goal: Goal,
    streak: GoalStreak?,
    onProgressUpdate: () -> Unit,
    onArchive: () -> Unit,
    onDelete: () -> Unit,
    onEdit: () -> Unit,
) {
    val isDark = MaterialTheme.colorScheme.isDark
    val shapes = LocalGlassShapes.current
    val motion = LocalMotion.current

    val progress = if (goal.targetMinutes > 0) {
        (goal.currentMinutes.toFloat() / goal.targetMinutes.toFloat()).coerceIn(0f, 1f)
    } else 0f

    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = motion.progressFill,
        label = "goalProgress",
    )

    val progressColor = when {
        progress >= 1f -> Success
        progress >= 0.7f -> Primary
        progress >= 0.4f -> Warning
        else -> Error
    }

    val animatedColor by animateColorAsState(
        targetValue = progressColor,
        animationSpec = motion.colorTransition,
        label = "goalProgressColor",
    )

    // Goal type icon and label
    val (typeIcon, typeLabel, typeColor) = when (goal.goalType) {
        GoalType.DAILY -> Icons.Default.Schedule to "দৈনিক" to Info
        GoalType.WEEKLY -> Icons.Default.CalendarMonth to "সাপ্তাহিক" to Chart2
        GoalType.SUBJECT_SPECIFIC -> Icons.Default.Star to "বিষয়ভিত্তিক" to Chart4
        GoalType.CUSTOM -> Icons.Default.TrendingUp to "কাস্টম" to Chart6
    }

    // Target text
    val targetText = when (goal.goalType) {
        GoalType.DAILY -> "${goal.targetMinutes.toBengaliDigits()} মিনিট/দিন"
        GoalType.WEEKLY -> "${goal.targetMinutes.toBengaliDigits()} মিনিট/সপ্তাহ"
        GoalType.SUBJECT_SPECIFIC -> "${goal.targetMinutes.toBengaliDigits()} মিনিট"
        GoalType.CUSTOM -> "${goal.targetMinutes.toBengaliDigits()} মিনিট"
    }

    // Percentage text
    val percentageText = "${(progress * 100).toInt().toBengaliDigits()}%"

    var showMenu by remember { mutableStateOf(false) }

    GlassmorphicCard(
        modifier = Modifier.fillMaxWidth(),
        variant = GlassCardVariant.ELEVATED,
        cornerRadius = shapes.cardRadius,
        padding = 0.dp,
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
        ) {
            // Top row: type badge, title, menu
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // Goal type badge
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = typeColor.copy(alpha = 0.15f),
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        Icon(
                            typeIcon,
                            null,
                            tint = typeColor,
                            modifier = Modifier.size(12.dp),
                        )
                        Text(
                            typeLabel,
                            style = StudyMasterTypography.labelSmall.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = typeColor,
                            ),
                        )
                    }
                }

                Spacer(Modifier.width(8.dp))

                // Title
                Text(
                    goal.title,
                    style = StudyMasterTypography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                    ),
                    modifier = Modifier.weight(1f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )

                // Streak fire badge
                if (streak != null && streak.currentStreak > 0) {
                    StreakFireBadge(
                        streakDays = streak.currentStreak,
                        modifier = Modifier.padding(end = 4.dp),
                    )
                }

                // More menu
                Box {
                    IconButton(
                        onClick = { showMenu = true },
                        modifier = Modifier.size(28.dp),
                    ) {
                        Icon(
                            Icons.Default.MoreVert,
                            contentDescription = "আরো",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(18.dp),
                        )
                    }
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false },
                        shape = RoundedCornerShape(shapes.cardRadiusSmall),
                        containerColor = if (isDark) GlassDarkAlpha80 else GlassLightAlpha90,
                    ) {
                        DropdownMenuItem(
                            text = { Text("প্রগতি আপডেট", style = StudyMasterTypography.bodyMedium) },
                            onClick = { onProgressUpdate(); showMenu = false },
                            leadingIcon = {
                                Icon(Icons.Default.TrendingUp, null, modifier = Modifier.size(18.dp))
                            },
                        )
                        DropdownMenuItem(
                            text = { Text("সম্পাদনা", style = StudyMasterTypography.bodyMedium) },
                            onClick = { onEdit(); showMenu = false },
                            leadingIcon = {
                                Icon(Icons.Default.Check, null, modifier = Modifier.size(18.dp))
                            },
                        )
                        if (goal.isCompleted) {
                            DropdownMenuItem(
                                text = { Text("আর্কাইভ", style = StudyMasterTypography.bodyMedium, color = Info) },
                                onClick = { onArchive(); showMenu = false },
                                leadingIcon = {
                                    Icon(Icons.Default.Archive, null, tint = Info, modifier = Modifier.size(18.dp))
                                },
                            )
                        }
                        DropdownMenuItem(
                            text = { Text("মুছুন", style = StudyMasterTypography.bodyMedium, color = Error) },
                            onClick = { onDelete(); showMenu = false },
                            leadingIcon = {
                                Icon(Icons.Default.Close, null, tint = Error, modifier = Modifier.size(18.dp))
                            },
                        )
                    }
                }
            }

            Spacer(Modifier.height(10.dp))

            // Progress bar
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        "${goal.currentMinutes.toBengaliDigits()}/${goal.targetMinutes.toBengaliDigits()} মিনিট",
                        style = StudyMasterTypography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Text(
                        percentageText,
                        style = StudyMasterTypography.labelMedium.copy(
                            fontFamily = EnglishFontFamily,
                            fontWeight = FontWeight.Bold,
                            color = animatedColor,
                        ),
                    )
                }

                Spacer(Modifier.height(6.dp))

                // Animated progress bar
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)),
                    contentAlignment = Alignment.CenterStart,
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(fraction = animatedProgress)
                            .height(10.dp)
                            .clip(RoundedCornerShape(5.dp))
                            .background(
                                Brush.horizontalGradient(
                                    colors = listOf(
                                        animatedColor,
                                        animatedColor.copy(alpha = 0.7f),
                                    ),
                                ),
                            ),
                    )
                    // Percentage marker
                    if (progress > 0f && progress < 1f) {
                        Box(
                            modifier = Modifier
                                .padding(start = 4.dp)
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.8f)),
                        )
                    }
                }
            }

            Spacer(Modifier.height(8.dp))

            // Bottom meta row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // Target info
                Text(
                    "লক্ষ্য: $targetText",
                    style = StudyMasterTypography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )

                // Subject chip
                if (goal.subjectName != null) {
                    SubjectChip(
                        subjectName = goal.subjectName,
                        compact = true,
                    )
                }
            }

            // Quick progress button
            if (!goal.isCompleted && progress < 1f) {
                Spacer(Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    // Quick add 15 min
                    QuickProgressButton(
                        label = "+১৫ মি.",
                        onClick = { /* Would call viewModel directly, but we're in a composable */ },
                        color = Primary,
                        modifier = Modifier.weight(1f),
                    )
                    // Quick add 30 min
                    QuickProgressButton(
                        label = "+৩০ মি.",
                        onClick = { },
                        color = Secondary,
                        modifier = Modifier.weight(1f),
                    )
                    // Custom
                    QuickProgressButton(
                        label = "কাস্টম",
                        onClick = onProgressUpdate,
                        color = Tertiary,
                        modifier = Modifier.weight(1f),
                    )
                }
            }

            // Completion banner
            if (goal.isCompleted) {
                Spacer(Modifier.height(8.dp))
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    color = Success.copy(alpha = 0.1f),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        Icon(
                            Icons.Default.EmojiEvents,
                            contentDescription = null,
                            tint = AchievementUnlocked,
                            modifier = Modifier.size(20.dp),
                        )
                        Text(
                            "গোল সম্পন্ন! 🎉",
                            style = StudyMasterTypography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = AchievementUnlocked,
                            ),
                        )
                    }
                }
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Quick Progress Button
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun QuickProgressButton(
    label: String,
    onClick: () -> Unit,
    color: Color,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(10.dp),
        color = color.copy(alpha = 0.1f),
        border = BorderStroke(1.dp, color.copy(alpha = 0.3f)),
    ) {
        Text(
            label,
            style = StudyMasterTypography.labelSmall.copy(
                fontWeight = FontWeight.SemiBold,
                color = color,
            ),
            modifier = Modifier.padding(vertical = 7.dp),
            textAlign = TextAlign.Center,
        )
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// ArchivedGoalCard — simpler card for completed/archived goals
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun ArchivedGoalCard(
    goal: Goal,
    onDelete: () -> Unit,
) {
    val isDark = MaterialTheme.colorScheme.isDark
    val shapes = LocalGlassShapes.current

    GlassmorphicCard(
        modifier = Modifier.fillMaxWidth(),
        variant = GlassCardVariant.OUTLINED,
        cornerRadius = shapes.cardRadiusSmall,
        padding = 12.dp,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Trophy icon
            Icon(
                Icons.Default.EmojiEvents,
                contentDescription = null,
                tint = AchievementUnlocked.copy(alpha = 0.7f),
                modifier = Modifier.size(24.dp),
            )

            Spacer(Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    goal.title,
                    style = StudyMasterTypography.titleSmall.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    "${goal.targetMinutes.toBengaliDigits()} মিনিট • ${formatGoalType(goal.goalType)}",
                    style = StudyMasterTypography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                )
            }

            // Completed date
            if (goal.completedAt != null) {
                Text(
                    formatDateShort(goal.completedAt),
                    style = StudyMasterTypography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f),
                    modifier = Modifier.padding(end = 8.dp),
                )
            }

            IconButton(
                onClick = onDelete,
                modifier = Modifier.size(28.dp),
            ) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = "মুছুন",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f),
                    modifier = Modifier.size(16.dp),
                )
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Goal Editor Sheet
// ═══════════════════════════════════════════════════════════════════════════════

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GoalEditorSheet(
    goal: Goal?,
    onDismiss: () -> Unit,
    onSave: (
        title: String,
        targetMinutes: Int,
        goalType: GoalType,
        subjectId: Long?,
        subjectName: String?,
    ) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val isDark = MaterialTheme.colorScheme.isDark
    val shapes = LocalGlassShapes.current

    // ─── Form state ───────────────────────────────────────────────────────────
    var title by remember(goal) { mutableStateOf(goal?.title ?: "") }
    var targetMinutes by remember(goal) { mutableStateOf(goal?.targetMinutes?.toString() ?: "") }
    var goalType by remember(goal) { mutableStateOf(goal?.goalType ?: GoalType.DAILY) }
    var description by remember(goal) { mutableStateOf("") }

    // Deadline (for non-daily goals)
    var deadlineDateMillis by remember { mutableStateOf<Long?>(null) }
    var showDatePicker by remember { mutableStateOf(false) }

    // Linked exam (conceptual)
    var selectedPriority by remember { mutableStateOf(com.porashona.studymaster.data.model.TaskPriority.MEDIUM) }

    // Priority options for the goal
    val priorityOptions = listOf(
        com.porashona.studymaster.data.model.TaskPriority.LOW to "নিম্ন",
        com.porashona.studymaster.data.model.TaskPriority.MEDIUM to "মাঝারি",
        com.porashona.studymaster.data.model.TaskPriority.HIGH to "উচ্চ",
        com.porashona.studymaster.data.model.TaskPriority.URGENT to "জরুরি",
    )

    // Goal type options
    val goalTypeOptions = listOf(
        GoalType.DAILY to ("দৈনিক অধ্যয়ন" to "প্রতিদিন নির্দিষ্ট সময় পড়া"),
        GoalType.WEEKLY to ("সাপ্তাহিক লক্ষ্য" to "সপ্তাহে মোট সময়"),
        GoalType.SUBJECT_SPECIFIC to ("বিষয় দক্ষতা" to "একটি বিষয়ে মোট সময়"),
        GoalType.CUSTOM to ("কাস্টম" to "নিজের মতো গোল"),
    )

    // Unit label based on type
    val unitLabel = when (goalType) {
        GoalType.DAILY -> "মিনিট/দিন"
        GoalType.WEEKLY -> "মিনিট/সপ্তাহ"
        else -> "মিনিট"
    }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = deadlineDateMillis ?: System.currentTimeMillis() + 30 * 24 * 60 * 60 * 1000,
    )

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { deadlineDateMillis = it }
                    showDatePicker = false
                }) {
                    Text("নিশ্চিত", fontWeight = FontWeight.SemiBold, color = Primary)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("বাতিল")
                }
            },
            shape = RoundedCornerShape(shapes.dialogRadius),
        ) {
            DatePicker(state = datePickerState)
        }
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = if (isDark) DarkSurface else LightSurface,
        shape = RoundedCornerShape(topStart = shapes.bottomSheetRadius, topEnd = shapes.bottomSheetRadius),
        dragHandle = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 12.dp, bottom = 8.dp),
            ) {
                Box(
                    modifier = Modifier
                        .width(40.dp)
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)),
                )
                Text(
                    text = if (goal != null) "গোল সম্পাদনা" else "নতুন গোল",
                    style = StudyMasterTypography.titleLarge,
                    modifier = Modifier.padding(top = 12.dp),
                )
            }
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .imePadding()
                .padding(horizontal = 20.dp)
                .padding(bottom = 40.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            // ─── Goal Type Selection ──────────────────────────────────────────
            Text(
                "গোলের ধরন",
                style = StudyMasterTypography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            goalTypeOptions.forEach { (type, labelAndDesc) ->
                val (label, desc) = labelAndDesc
                val isSelected = goalType == type
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(shapes.cardRadiusSmall))
                        .clickable { goalType = type },
                    shape = RoundedCornerShape(shapes.cardRadiusSmall),
                    color = if (isSelected) Primary.copy(alpha = 0.1f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                    border = BorderStroke(
                        1.dp,
                        if (isSelected) Primary.copy(alpha = 0.4f) else Color.Transparent,
                    ),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                label,
                                style = StudyMasterTypography.labelLarge.copy(
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    color = if (isSelected) Primary else MaterialTheme.colorScheme.onSurface,
                                ),
                            )
                            Text(
                                desc,
                                style = StudyMasterTypography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                        if (isSelected) {
                            Icon(
                                Icons.Default.Check,
                                contentDescription = null,
                                tint = Primary,
                                modifier = Modifier.size(20.dp),
                            )
                        }
                    }
                }
            }

            // ─── Title ────────────────────────────────────────────────────────
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("গোলের শিরোনাম", style = StudyMasterTypography.labelMedium) },
                singleLine = true,
                shape = RoundedCornerShape(shapes.inputFieldRadius),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Primary.copy(alpha = 0.5f),
                    unfocusedBorderColor = if (isDark) GlassBorderDark else GlassBorderLight,
                ),
                textStyle = StudyMasterTypography.titleMedium,
                modifier = Modifier.fillMaxWidth(),
            )

            // ─── Target Value ─────────────────────────────────────────────────
            OutlinedTextField(
                value = targetMinutes,
                onValueChange = { targetMinutes = it.filter { c -> c.isDigit() } },
                label = {
                    Text("লক্ষ্য ($unitLabel)", style = StudyMasterTypography.labelMedium)
                },
                singleLine = true,
                keyboardOptions = androidx.compose.ui.text.input.KeyboardOptions(
                    keyboardType = androidx.compose.ui.text.input.KeyboardType.Number,
                ),
                shape = RoundedCornerShape(shapes.inputFieldRadius),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Primary.copy(alpha = 0.5f),
                    unfocusedBorderColor = if (isDark) GlassBorderDark else GlassBorderLight,
                ),
                textStyle = StudyMasterTypography.bodyLarge.copy(
                    fontFamily = EnglishFontFamily,
                ),
                supportingText = {
                    val minutes = targetMinutes.toIntOrNull() ?: 0
                    val hours = (minutes / 60).toBengaliDigits()
                    val mins = (minutes % 60).toBengaliDigits()
                    Text(
                        "≈ $hours ঘণ্টা $mins মিনিট",
                        style = StudyMasterTypography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                },
                modifier = Modifier.fillMaxWidth(),
            )

            // ─── Description ───────────────────────────────────────────────────
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("বিবরণ (ঐচ্ছিক)", style = StudyMasterTypography.labelMedium) },
                shape = RoundedCornerShape(shapes.inputFieldRadius),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Primary.copy(alpha = 0.5f),
                    unfocusedBorderColor = if (isDark) GlassBorderDark else GlassBorderLight,
                ),
                textStyle = StudyMasterTypography.bodyMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp),
                maxLines = 4,
            )

            // ─── Deadline (for non-daily) ────────────────────────────────────
            if (goalType != GoalType.DAILY) {
                Text(
                    "শেষ তারিখ (ঐচ্ছিক)",
                    style = StudyMasterTypography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(shapes.buttonRadius))
                        .clickable { showDatePicker = true },
                    shape = RoundedCornerShape(shapes.buttonRadius),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        Icon(
                            Icons.Default.CalendarMonth,
                            contentDescription = null,
                            tint = Primary,
                            modifier = Modifier.size(20.dp),
                        )
                        Text(
                            if (deadlineDateMillis != null)
                                formatDateLong(deadlineDateMillis!!)
                            else
                                "তারিখ নির্বাচন করুন",
                            style = StudyMasterTypography.bodyMedium.copy(
                                color = if (deadlineDateMillis != null)
                                    MaterialTheme.colorScheme.onSurface
                                else
                                    MaterialTheme.colorScheme.onSurfaceVariant,
                            ),
                        )
                        if (deadlineDateMillis != null) {
                            Spacer(Modifier.weight(1f))
                            Text(
                                "✕",
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.clickable { deadlineDateMillis = null },
                            )
                        }
                    }
                }
            }

            // ─── Priority (for custom goals) ─────────────────────────────────
            if (goalType == GoalType.CUSTOM) {
                Text(
                    "অগ্রাধিকার",
                    style = StudyMasterTypography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    priorityOptions.forEach { (prio, label) ->
                        val color = when (prio) {
                            com.porashona.studymaster.data.model.TaskPriority.LOW -> PriorityLow
                            com.porashona.studymaster.data.model.TaskPriority.MEDIUM -> PriorityMedium
                            com.porashona.studymaster.data.model.TaskPriority.HIGH -> PriorityHigh
                            com.porashona.studymaster.data.model.TaskPriority.URGENT -> PriorityUrgent
                        }
                        val isSelected = selectedPriority == prio
                        Surface(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(10.dp))
                                .clickable { selectedPriority = prio },
                            shape = RoundedCornerShape(10.dp),
                            color = if (isSelected) color.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                            border = BorderStroke(1.dp, if (isSelected) color.copy(alpha = 0.5f) else Color.Transparent),
                        ) {
                            Text(
                                label,
                                style = StudyMasterTypography.labelSmall.copy(
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isSelected) color else MaterialTheme.colorScheme.onSurfaceVariant,
                                ),
                                modifier = Modifier.padding(vertical = 8.dp),
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                }
            }

            // ─── Save Button ─────────────────────────────────────────────────
            Button(
                onClick = {
                    val minutes = targetMinutes.toIntOrNull() ?: 0
                    if (title.isNotBlank() && minutes > 0) {
                        onSave(
                            title.trim(),
                            minutes,
                            goalType,
                            null,
                            null,
                        )
                    }
                },
                enabled = title.isNotBlank() && (targetMinutes.toIntOrNull() ?: 0) > 0,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Primary,
                    disabledContainerColor = Primary.copy(alpha = 0.3f),
                ),
                shape = RoundedCornerShape(shapes.buttonRadius),
            ) {
                Text(
                    if (goal != null) "আপডেট করুন" else "গোল তৈরি করুন",
                    style = StudyMasterTypography.labelLarge,
                )
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Progress Update Dialog
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun ProgressUpdateDialog(
    goal: Goal,
    onDismiss: () -> Unit,
    onConfirm: (Int) -> Unit,
) {
    val isDark = MaterialTheme.colorScheme.isDark
    val shapes = LocalGlassShapes.current

    var minutesInput by remember { mutableStateOf("") }
    val currentProgress = if (goal.targetMinutes > 0) {
        (goal.currentMinutes.toFloat() / goal.targetMinutes.toFloat()).coerceIn(0f, 1f)
    } else 0f

    // Quick options
    val quickOptions = listOf(15, 30, 45, 60, 90, 120)

    AlertDialog(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(shapes.dialogRadius),
        containerColor = if (isDark) GlassDarkAlpha60 else GlassLightAlpha90,
        title = {
            Text(
                "প্রগতি আপডেট",
                style = StudyMasterTypography.titleMedium,
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(
                    "\"${goal.title}\"",
                    style = StudyMasterTypography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )

                // Current progress
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        "বর্তমান:",
                        style = StudyMasterTypography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Text(
                        "${goal.currentMinutes.toBengaliDigits()}/${goal.targetMinutes.toBengaliDigits()} মিনিট (${(currentProgress * 100).toInt().toBengaliDigits()}%)",
                        style = StudyMasterTypography.labelMedium.copy(
                            fontFamily = EnglishFontFamily,
                            fontWeight = FontWeight.Bold,
                            color = Primary,
                        ),
                    )
                }

                // Progress bar
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)),
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(fraction = currentProgress)
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Primary),
                    )
                }

                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f))

                // Quick add buttons
                Text(
                    "দ্রুত যোগ করুন:",
                    style = StudyMasterTypography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    quickOptions.forEach { mins ->
                        Surface(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(8.dp))
                                .clickable { minutesInput = mins.toString() },
                            shape = RoundedCornerShape(8.dp),
                            color = if (minutesInput == mins.toString()) Primary.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                        ) {
                            Text(
                                "${mins.toBengaliDigits()}",
                                style = StudyMasterTypography.labelSmall.copy(
                                    fontFamily = EnglishFontFamily,
                                    fontWeight = if (minutesInput == mins.toString()) FontWeight.Bold else FontWeight.Normal,
                                    color = if (minutesInput == mins.toString()) Primary else MaterialTheme.colorScheme.onSurfaceVariant,
                                ),
                                modifier = Modifier.padding(vertical = 6.dp),
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                }

                // Custom input
                OutlinedTextField(
                    value = minutesInput,
                    onValueChange = { minutesInput = it.filter { c -> c.isDigit() } },
                    label = { Text("মিনিট লিখুন", style = StudyMasterTypography.labelSmall) },
                    singleLine = true,
                    keyboardOptions = androidx.compose.ui.text.input.KeyboardOptions(
                        keyboardType = androidx.compose.ui.text.input.KeyboardType.Number,
                    ),
                    shape = RoundedCornerShape(12.dp),
                    textStyle = StudyMasterTypography.bodyLarge.copy(fontFamily = EnglishFontFamily),
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Primary.copy(alpha = 0.5f),
                        unfocusedBorderColor = if (isDark) GlassBorderDark else GlassBorderLight,
                    ),
                    suffix = {
                        Text(
                            "মিনিট",
                            style = StudyMasterTypography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    },
                )
            }
        },
        confirmButton = {
            val mins = minutesInput.toIntOrNull() ?: 0
            TextButton(
                onClick = { if (mins > 0) onConfirm(mins) },
                enabled = mins > 0,
            ) {
                Text(
                    "যোগ করুন",
                    fontWeight = FontWeight.SemiBold,
                    color = if (mins > 0) Primary else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f),
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("বাতিল")
            }
        },
    )
}

// ═══════════════════════════════════════════════════════════════════════════════
// Achievement Celebration Dialog
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun AchievementCelebrationDialog(
    title: String,
    onDismiss: () -> Unit,
) {
    val isDark = MaterialTheme.colorScheme.isDark
    val shapes = LocalGlassShapes.current

    // Auto dismiss
    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(4000)
        onDismiss()
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(shapes.dialogRadius),
        containerColor = if (isDark) GlassDarkAlpha60 else GlassLightAlpha90,
        title = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    "🏆",
                    style = StudyMasterTypography.displayMedium.copy(
                        textAlign = TextAlign.Center,
                    ),
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    "অর্জন অর্জিত!",
                    style = StudyMasterTypography.headlineSmall.copy(
                        color = AchievementUnlocked,
                        textAlign = TextAlign.Center,
                    ),
                )
            }
        },
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    title,
                    style = StudyMasterTypography.titleMedium,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    "আপনার অধ্যবসায় প্রশংসনীয়! এগিয়ে যান! 💪",
                    style = StudyMasterTypography.bodyMedium,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = onDismiss,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    "ধন্যবাদ! 🎉",
                    fontWeight = FontWeight.Bold,
                    color = Primary,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                )
            }
        },
    )
}

// ═══════════════════════════════════════════════════════════════════════════════
// Utility functions
// ═══════════════════════════════════════════════════════════════════════════════

private fun formatGoalType(type: GoalType): String = when (type) {
    GoalType.DAILY -> "দৈনিক"
    GoalType.WEEKLY -> "সাপ্তাহিক"
    GoalType.SUBJECT_SPECIFIC -> "বিষয়ভিত্তিক"
    GoalType.CUSTOM -> "কাস্টম"
}

private fun formatDateShort(timestamp: Long): String {
    return try {
        SimpleDateFormat("dd MMM", Locale("bn", "BD")).format(Date(timestamp))
    } catch (_: Exception) {
        SimpleDateFormat("dd MMM", Locale.getDefault()).format(Date(timestamp))
    }
}

private fun formatDateLong(timestamp: Long): String {
    return try {
        SimpleDateFormat("dd MMMM, yyyy", Locale("bn", "BD")).format(Date(timestamp))
    } catch (_: Exception) {
        SimpleDateFormat("dd MMMM, yyyy", Locale.getDefault()).format(Date(timestamp))
    }
}

private fun Int.toBengaliDigits(): String = toString().map { digit ->
    if (digit.isDigit()) "০১২৩৪৫৬৭৮৯"[digit.digitToInt()] else digit
}.joinToString("")

private fun Long.toBengaliDigits(): String = toString().map { digit ->
    if (digit.isDigit()) "০১২৩৪৫৬৭৮৯"[digit.digitToInt()] else digit
}.joinToString("")
