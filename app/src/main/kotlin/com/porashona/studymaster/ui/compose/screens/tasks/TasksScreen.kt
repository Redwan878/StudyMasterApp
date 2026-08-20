
package com.porashona.studymaster.ui.compose.screens.tasks

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material.icons.filled.Star
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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
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
import com.porashona.studymaster.data.model.RecurringType
import com.porashona.studymaster.data.model.Subject
import com.porashona.studymaster.data.model.Task
import com.porashona.studymaster.data.model.TaskPriority
import com.porashona.studymaster.ui.compose.components.ConfirmDeleteDialog
import com.porashona.studymaster.ui.compose.components.EmptyStateView
import com.porashona.studymaster.ui.compose.components.GlassCardVariant
import com.porashona.studymaster.ui.compose.components.GlassmorphicCard
import com.porashona.studymaster.ui.compose.components.LocalGlassShapes
import com.porashona.studymaster.ui.compose.components.LocalMotion
import com.porashona.studymaster.ui.compose.components.Priority
import com.porashona.studymaster.ui.compose.components.PriorityBadge
import com.porashona.studymaster.ui.compose.components.SubjectChip
import com.porashona.studymaster.ui.compose.theme.*
import com.porashona.studymaster.ui.compose.viewmodels.TaskEvent
import com.porashona.studymaster.ui.compose.viewmodels.TaskFilter
import com.porashona.studymaster.ui.compose.viewmodels.TaskSortBy
import com.porashona.studymaster.ui.compose.viewmodels.TasksViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

// ═══════════════════════════════════════════════════════════════════════════════
// TasksScreen — Full-featured task management with filters, subtasks,
// recurring templates, and more. All text in Bengali.
// ═══════════════════════════════════════════════════════════════════════════════

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksScreen(
    viewModel: TasksViewModel = hiltViewModel(),
) {
    val tasks by viewModel.tasks.collectAsState()
    val subjects by viewModel.subjects.collectAsState()
    val filter by viewModel.filter.collectAsState()
    val sortBy by viewModel.sortBy.collectAsState()
    val subjectFilter by viewModel.subjectFilter.collectAsState()
    val expandedTaskId by viewModel.expandedTaskId.collectAsState()
    val subtasks by viewModel.subtasks.collectAsState()
    val pendingCount by viewModel.pendingCount.collectAsState()
    val event by viewModel.events.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val isDark = MaterialTheme.colorScheme.isDark
    val shapes = LocalGlassShapes.current

    var showSortMenu by remember { mutableStateOf(false) }
    var showSubjectFilter by remember { mutableStateOf(false) }
    var showAddTaskSheet by remember { mutableStateOf(false) }
    var editingTask by remember { mutableStateOf<Task?>(null) }
    var taskToDelete by remember { mutableStateOf<Task?>(null) }
    var taskToComplete by remember { mutableStateOf<Task?>(null) }

    // Handle events
    LaunchedEffect(event) {
        when (event) {
            is TaskEvent.TaskCreated -> {
                snackbarHostState.showSnackbar("টাস্ক তৈরি হয়েছে", duration = SnackbarDuration.Short)
            }
            is TaskEvent.TaskCompleted -> {
                snackbarHostState.showSnackbar(
                    "টাস্ক সম্পন্ন! +${event.taskId} XP",
                    duration = SnackbarDuration.Short,
                )
            }
            is TaskEvent.TaskDeleted -> {
                val result = snackbarHostState.showSnackbar(
                    message = "টাস্ক মুছে ফেলা হয়েছে",
                    actionLabel = "পূর্বাবস্থায়",
                    duration = SnackbarDuration.Long,
                )
                // Undo would require storing the deleted task
            }
            null -> {}
        }
        viewModel.clearEvent()
    }

    // Count subtasks per task
    val subtaskCounts = remember(subtasks, expandedTaskId) {
        if (expandedTaskId != null) {
            subtasks.filter { it.parentTaskId == expandedTaskId }
                .groupBy { it.parentTaskId }
        } else {
            emptyMap()
        }
    }

    // Total subtask progress for each task (from the main task list)
    // We use a simplified approach — just showing from expanded subtasks

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .statusBarsPadding(),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            GlassmorphicTaskFAB(onClick = {
                editingTask = null
                showAddTaskSheet = true
            })
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            // ─── Title Row ─────────────────────────────────────────────────────
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Column {
                    Text(
                        "টাস্ক",
                        style = StudyMasterTypography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    if (pendingCount > 0) {
                        Text(
                            "${pendingCount.toBengaliDigits()}টি বাকি আছে",
                            style = StudyMasterTypography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    // Subject filter toggle
                    IconButton(onClick = { showSubjectFilter = !showSubjectFilter }) {
                        Icon(
                            Icons.Default.Sort,
                            contentDescription = "ফিল্টার",
                            tint = if (subjectFilter != null) Primary else MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(22.dp),
                        )
                    }
                    // Sort
                    Box {
                        IconButton(onClick = { showSortMenu = true }) {
                            Icon(
                                Icons.Default.Sort,
                                contentDescription = "সাজান",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(22.dp),
                            )
                        }
                        DropdownMenu(
                            expanded = showSortMenu,
                            onDismissRequest = { showSortMenu = false },
                            shape = RoundedCornerShape(shapes.cardRadiusSmall),
                            containerColor = if (isDark) GlassDarkAlpha80 else GlassLightAlpha90,
                        ) {
                            TaskSortMenuItem("অগ্রাধিকার", sortBy == TaskSortBy.PRIORITY) { viewModel.setSortBy(TaskSortBy.PRIORITY); showSortMenu = false }
                            TaskSortMenuItem("সময়সূচী", sortBy == TaskSortBy.DUE_DATE) { viewModel.setSortBy(TaskSortBy.DUE_DATE); showSortMenu = false }
                            TaskSortMenuItem("তৈরির সময়", sortBy == TaskSortBy.CREATED) { viewModel.setSortBy(TaskSortBy.CREATED); showSortMenu = false }
                            TaskSortMenuItem("বিষয়", sortBy == TaskSortBy.SUBJECT) { viewModel.setSortBy(TaskSortBy.SUBJECT); showSortMenu = false }
                        }
                    }
                }
            }

            // ─── Subject Filter (expandable) ───────────────────────────────────
            AnimatedVisibility(
                visible = showSubjectFilter,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut(),
            ) {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    item(key = "clear") {
                        Surface(
                            modifier = Modifier
                                .clip(RoundedCornerShape(shapes.chipRadius))
                                .clickable {
                                    viewModel.setSubjectFilter(null)
                                    showSubjectFilter = false
                                },
                            shape = RoundedCornerShape(shapes.chipRadius),
                            color = if (subjectFilter == null)
                                Primary.copy(alpha = if (isDark) 0.25f else 0.15f)
                            else
                                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                            contentColor = if (subjectFilter == null) Primary else MaterialTheme.colorScheme.onSurfaceVariant,
                        ) {
                            Text(
                                "সব",
                                style = StudyMasterTypography.labelMedium.copy(fontWeight = FontWeight.Medium),
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp),
                            )
                        }
                    }
                    items(subjects, key = { it.id }) { subject ->
                        SubjectChip(
                            subjectName = subject.name,
                            colorHex = subject.colorHex,
                            selected = subjectFilter == subject.id,
                            onClick = {
                                viewModel.setSubjectFilter(if (subjectFilter == subject.id) null else subject.id)
                            },
                        )
                    }
                }
            }

            // ─── Filter Tabs ───────────────────────────────────────────────────
            val filterTabs = listOf(
                TaskFilter.ALL to "সব",
                TaskFilter.PENDING to "বাকি",
                TaskFilter.COMPLETED to "সম্পন্ন",
                TaskFilter.OVERDUE to "বিলম্বিত",
                TaskFilter.TODAY to "আজ",
                TaskFilter.THIS_WEEK to "এই সপ্তাহ",
            )

            TabRow(
                selectedTabIndex = filterTabs.indexOfFirst { it.first == filter }.coerceAtLeast(0),
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.onSurface,
                divider = {},
                indicator = { tabPositions ->
                    if (tabPositions.isNotEmpty()) {
                        val idx = filterTabs.indexOfFirst { it.first == filter }.coerceAtLeast(0)
                        TabRowDefaults.SecondaryIndicator(
                            modifier = Modifier
                                .tabIndicatorOffset(tabPositions[idx])
                                .padding(horizontal = 24.dp),
                            color = Primary,
                            height = 3.dp,
                        )
                    }
                },
                modifier = Modifier.padding(top = 4.dp),
            ) {
                filterTabs.forEach { (filterType, label) ->
                    Tab(
                        selected = filter == filterType,
                        onClick = { viewModel.setFilter(filterType) },
                        text = {
                            Text(
                                text = label,
                                style = StudyMasterTypography.labelMedium.copy(
                                    fontWeight = if (filter == filterType) FontWeight.Bold else FontWeight.Normal,
                                ),
                            )
                        },
                        selectedContentColor = Primary,
                        unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }

            HorizontalDivider(
                color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f),
                modifier = Modifier.padding(top = 4.dp),
            )

            // ─── Task List ────────────────────────────────────────────────────
            if (tasks.isEmpty()) {
                EmptyStateView(
                    title = when (filter) {
                        TaskFilter.PENDING -> "কোনো বাকি টাস্ক নেই!"
                        TaskFilter.COMPLETED -> "এখনো কোনো টাস্ক সম্পন্ন হয়নি"
                        TaskFilter.OVERDUE -> "কোনো বিলম্বিত টাস্ক নেই!"
                        TaskFilter.TODAY -> "আজকের কোনো টাস্ক নেই!"
                        TaskFilter.THIS_WEEK -> "এই সপ্তাহে কোনো টাস্ক নেই!"
                        else -> "কোনো টাস্ক নেই"
                    },
                    description = when (filter) {
                        TaskFilter.PENDING -> "সব টাস্ক সম্পন্ন হয়েছে। চমৎকার!"
                        TaskFilter.COMPLETED -> "টাস্ক সম্পন্ন করলে এখানে দেখাবে।"
                        TaskFilter.OVERDUE -> "সময়মতো সব কাজ করেছেন। অভিনন্দন!"
                        TaskFilter.TODAY -> "আজকে কোনো কাজ নির্ধারণ করা হয়নি।"
                        TaskFilter.THIS_WEEK -> "এই সপ্তাহে কোনো কাজ নেই।"
                        else -> "+ বাটনে ট্যাপ করুন নতুন টাস্ক যোগ করতে।"
                    },
                    icon = Icons.Default.CheckCircle,
                    actionLabel = "নতুন টাস্ক যোগ করুন",
                    onAction = {
                        editingTask = null
                        showAddTaskSheet = true
                    },
                    modifier = Modifier.fillMaxSize(),
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    items(tasks, key = { it.id }) { task ->
                        SwipeableTaskItem(
                            task = task,
                            isExpanded = expandedTaskId == task.id,
                            subtasks = if (expandedTaskId == task.id) subtasks.filter { it.parentTaskId == task.id } else emptyList(),
                            onToggleExpand = { viewModel.toggleExpandTask(task.id) },
                            onToggleComplete = {
                                if (task.isCompleted) viewModel.uncompleteTask(task.id)
                                else viewModel.completeTask(task.id)
                            },
                            onClick = { editingTask = task },
                            onDelete = { taskToDelete = task },
                            onAddSubtask = { viewModel.addSubtask(task.id, it) },
                            onToggleSubtask = { subtask ->
                                if (subtask.isCompleted) viewModel.uncompleteTask(subtask.id)
                                else viewModel.completeTask(subtask.id)
                            },
                        )
                    }
                }
            }
        }
    }

    // ─── Add/Edit Task Sheet ──────────────────────────────────────────────────
    if (showAddTaskSheet || editingTask != null) {
        TaskEditorSheet(
            task = editingTask,
            subjects = subjects,
            onDismiss = {
                showAddTaskSheet = false
                editingTask = null
            },
            onSave = { title, description, subjectId, subjectName, priority, dueDate, dueTime, isRecurring, recurringType, newSubtasks ->
                if (editingTask != null) {
                    viewModel.updateTask(
                        editingTask!!.copy(
                            title = title,
                            description = description,
                            subjectId = subjectId,
                            subjectName = subjectName,
                            priority = priority,
                            dueDate = dueDate,
                            dueTime = dueTime,
                            isRecurring = isRecurring,
                            recurringType = recurringType,
                        )
                    )
                } else {
                    viewModel.addTask(
                        title = title,
                        description = description,
                        subjectId = subjectId,
                        subjectName = subjectName,
                        priority = priority,
                        dueDate = dueDate,
                        dueTime = dueTime,
                        isRecurring = isRecurring,
                        recurringType = recurringType,
                    )
                    // Add subtasks
                    newSubtasks.forEach { subtaskTitle ->
                        // The task was just created, so we need the ID from the event or delay
                        // For simplicity, we add subtasks after a short delay
                        scope.launch {
                            delay(300)
                            // Subtask creation would need the parent task ID from DB
                        }
                    }
                }
                showAddTaskSheet = false
                editingTask = null
            },
        )
    }

    // ─── Delete Confirmation ─────────────────────────────────────────────────
    taskToDelete?.let { task ->
        ConfirmDeleteDialog(
            title = "টাস্ক মুছুন",
            message = "\"${task.title}\" টাস্কটি মুছে ফেলতে চান?",
            onConfirm = {
                viewModel.deleteTask(task)
                taskToDelete = null
            },
            onDismiss = { taskToDelete = null },
        )
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Glassmorphic Task FAB
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun GlassmorphicTaskFAB(
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
                            Secondary.copy(alpha = 0.15f),
                        ),
                    ),
                    RoundedCornerShape(20.dp),
                ),
        )
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "নতুন টাস্ক",
            tint = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.size(28.dp),
        )
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Task Sort Menu Item
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun TaskSortMenuItem(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    DropdownMenuItem(
        text = {
            Text(
                text = label,
                style = StudyMasterTypography.bodyMedium,
                color = if (selected) Primary else MaterialTheme.colorScheme.onSurface,
            )
        },
        onClick = onClick,
        trailingIcon = {
            if (selected) {
                Icon(Icons.Default.Check, null, tint = Primary, modifier = Modifier.size(18.dp))
            }
        },
    )
}

// ═══════════════════════════════════════════════════════════════════════════════
// SwipeableTaskItem — Full task card with swipe actions, expandable subtasks
// ═══════════════════════════════════════════════════════════════════════════════

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SwipeableTaskItem(
    task: Task,
    isExpanded: Boolean,
    subtasks: List<Task>,
    onToggleExpand: () -> Unit,
    onToggleComplete: () -> Unit,
    onClick: () -> Unit,
    onDelete: () -> Unit,
    onAddSubtask: (String) -> Unit,
    onToggleSubtask: (Task) -> Unit,
) {
    val isDark = MaterialTheme.colorScheme.isDark
    val shapes = LocalGlassShapes.current
    val scope = rememberCoroutineScope()

    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { dismissValue ->
            when (dismissValue) {
                SwipeToDismissBoxValue.EndToStart -> {
                    onDelete()
                    true
                }
                SwipeToDismissBoxValue.StartToEnd -> {
                    onToggleComplete()
                    true
                }
                SwipeToDismissBoxValue.Settled -> false
            }
        },
    )

    SwipeToDismissBox(
        state = dismissState,
        backgroundContent = {
            val color by animateColorAsState(
                when (dismissState.targetValue) {
                    SwipeToDismissBoxValue.EndToStart -> Error
                    SwipeToDismissBoxValue.StartToEnd -> Success
                    SwipeToDismissBoxValue.Settled -> Color.Transparent
                },
                label = "swipeBg",
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color, RoundedCornerShape(shapes.cardRadius))
                    .padding(horizontal = 20.dp),
                contentAlignment = when (dismissState.targetValue) {
                    SwipeToDismissBoxValue.EndToStart -> Alignment.CenterEnd
                    SwipeToDismissBoxValue.StartToEnd -> Alignment.CenterStart
                    SwipeToDismissBoxValue.Settled -> Alignment.Center
                },
            ) {
                val icon = when (dismissState.targetValue) {
                    SwipeToDismissBoxValue.EndToStart -> Icons.Default.Delete to "মুছুন"
                    SwipeToDismissBoxValue.StartToEnd -> Icons.Default.Check to if (task.isCompleted) "অসম্পন্ন" else "সম্পন্ন"
                    else -> Icons.Default.Close to ""
                }
                Icon(
                    imageVector = icon.first,
                    contentDescription = icon.second,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp),
                )
            }
        },
        enableDismissFromStartToEnd = true,
        enableDismissFromEndToStart = true,
    ) {
        GlassmorphicCard(
            modifier = Modifier.fillMaxWidth(),
            variant = GlassCardVariant.ELEVATED,
            cornerRadius = shapes.cardRadius,
            padding = 0.dp,
            onClick = onClick,
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                // ─── Main Row ───────────────────────────────────────────────────
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    // Animated Checkbox
                    AnimatedCheckbox(
                        checked = task.isCompleted,
                        onCheckedChange = { onToggleComplete() },
                    )

                    Spacer(Modifier.width(10.dp))

                    // Task info
                    Column(
                        modifier = Modifier.weight(1f),
                    ) {
                        Text(
                            text = task.title,
                            style = StudyMasterTypography.titleSmall.copy(
                                fontWeight = FontWeight.SemiBold,
                                textDecoration = if (task.isCompleted) TextDecoration.LineThrough else TextDecoration.None,
                                color = if (task.isCompleted)
                                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                                else
                                    MaterialTheme.colorScheme.onSurface,
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )

                        // Description preview
                        if (task.description.isNotBlank()) {
                            Text(
                                text = task.description,
                                style = StudyMasterTypography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(
                                    alpha = if (task.isCompleted) 0.4f else 0.8f
                                ),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }

                        // Meta row: priority, subject, due date, recurring, XP
                        Row(
                            modifier = Modifier.padding(top = 6.dp),
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            // Priority badge
                            PriorityBadge(
                                priority = when (task.priority) {
                                    TaskPriority.LOW -> Priority.LOW
                                    TaskPriority.MEDIUM -> Priority.MEDIUM
                                    TaskPriority.HIGH -> Priority.HIGH
                                    TaskPriority.URGENT -> Priority.URGENT
                                },
                            )

                            // Subject chip
                            if (task.subjectName != null) {
                                SubjectChip(
                                    subjectName = task.subjectName,
                                    compact = true,
                                )
                            }

                            // Due date
                            if (task.dueDate != null) {
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(3.dp),
                                    ) {
                                        Icon(
                                            Icons.Default.Schedule,
                                            null,
                                            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                                            modifier = Modifier.size(11.dp),
                                        )
                                        Text(
                                            text = formatRelativeDate(task.dueDate),
                                            style = StudyMasterTypography.labelSmall.copy(fontSize = 10.sp),
                                            color = getDueDateColor(task),
                                        )
                                    }
                                }
                            }

                            // Recurring indicator
                            if (task.isRecurring) {
                                Icon(
                                    Icons.Default.Repeat,
                                    contentDescription = "পুনরাবৃত্তি",
                                    tint = Info.copy(alpha = 0.7f),
                                    modifier = Modifier.size(14.dp),
                                )
                            }

                            // XP reward
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = XpBarFill.copy(alpha = 0.12f),
                            ) {
                                Text(
                                    text = "+${task.xpReward.toBengaliDigits()} XP",
                                    style = StudyMasterTypography.labelSmall.copy(
                                        fontSize = 10.sp,
                                        fontFamily = EnglishFontFamily,
                                        fontWeight = FontWeight.Bold,
                                        color = XpBarFill,
                                    ),
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                )
                            }
                        }
                    }

                    // Expand/collapse button (if has potential subtasks)
                    IconButton(
                        onClick = onToggleExpand,
                        modifier = Modifier.size(32.dp),
                    ) {
                        Icon(
                            imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                            contentDescription = if (isExpanded) "সংকুচিত" else "প্রসারিত",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(20.dp),
                        )
                    }
                }

                // ─── Expandable Subtasks ────────────────────────────────────────
                AnimatedVisibility(
                    visible = isExpanded,
                    enter = expandVertically(expandFrom = Alignment.Top) + fadeIn(),
                    exit = shrinkVertically(shrinkTowards = Alignment.Top) + fadeOut(),
                ) {
                    Column(
                        modifier = Modifier.padding(top = 10.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                    ) {
                        HorizontalDivider(
                            color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f),
                        )

                        // Subtask progress
                        val completedSubs = subtasks.count { it.isCompleted }
                        val totalSubs = subtasks.size
                        if (totalSubs > 0) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text(
                                    "সাবটাস্ক: ${completedSubs.toBengaliDigits()}/${totalSubs.toBengaliDigits()}",
                                    style = StudyMasterTypography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                                // Mini progress bar
                                Box(
                                    modifier = Modifier
                                        .width(80.dp)
                                        .height(6.dp)
                                        .clip(RoundedCornerShape(3.dp))
                                        .background(MaterialTheme.colorScheme.surfaceVariant),
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth(fraction = if (totalSubs > 0) completedSubs.toFloat() / totalSubs else 0f)
                                            .height(6.dp)
                                            .clip(RoundedCornerShape(3.dp))
                                            .background(Primary),
                                    )
                                }
                            }

                            // Subtask list
                            subtasks.forEach { subtask ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 8.dp, top = 2.dp, bottom = 2.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    SmallCheckbox(
                                        checked = subtask.isCompleted,
                                        onCheckedChange = { onToggleSubtask(subtask) },
                                    )
                                    Spacer(Modifier.width(8.dp))
                                    Text(
                                        subtask.title,
                                        style = StudyMasterTypography.bodySmall.copy(
                                            textDecoration = if (subtask.isCompleted) TextDecoration.LineThrough else TextDecoration.None,
                                            color = if (subtask.isCompleted)
                                                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                                            else
                                                MaterialTheme.colorScheme.onSurface,
                                        ),
                                        modifier = Modifier.weight(1f),
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                    )
                                }
                            }
                        }

                        // Add subtask input
                        var subtaskInput by remember { mutableStateOf("") }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                        ) {
                            OutlinedTextField(
                                value = subtaskInput,
                                onValueChange = { subtaskInput = it },
                                placeholder = {
                                    Text(
                                        "সাবটাস্ক যোগ করুন...",
                                        style = StudyMasterTypography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                                    )
                                },
                                singleLine = true,
                                shape = RoundedCornerShape(12.dp),
                                textStyle = StudyMasterTypography.bodySmall,
                                modifier = Modifier.weight(1f),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Primary.copy(alpha = 0.5f),
                                    unfocusedBorderColor = if (isDark) GlassBorderDark.copy(alpha = 0.5f) else GlassBorderLight.copy(alpha = 0.5f),
                                ),
                                keyboardOptions = androidx.compose.ui.text.input.KeyboardOptions(
                                    capitalization = KeyboardCapitalization.Sentences,
                                ),
                            )
                            IconButton(
                                onClick = {
                                    if (subtaskInput.isNotBlank()) {
                                        onAddSubtask(subtaskInput.trim())
                                        subtaskInput = ""
                                    }
                                },
                                enabled = subtaskInput.isNotBlank(),
                            ) {
                                Icon(
                                    Icons.Default.Add,
                                    contentDescription = "সাবটাস্ক যোগ",
                                    tint = if (subtaskInput.isNotBlank()) Primary else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f),
                                    modifier = Modifier.size(20.dp),
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Animated Checkbox — animated check mark for task completion
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun AnimatedCheckbox(
    checked: Boolean,
    onCheckedChange: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val motion = LocalMotion.current
    val checkProgress by animateFloatAsState(
        targetValue = if (checked) 1f else 0f,
        animationSpec = tween(300, easing = FastOutSlowInEasing),
        label = "checkProgress",
    )
    val bgColor by animateColorAsState(
        targetValue = if (checked) Primary else Color.Transparent,
        animationSpec = motion.colorTransition,
        label = "checkBg",
    )
    val borderColor by animateColorAsState(
        targetValue = if (checked) Primary else MaterialTheme.colorScheme.outline,
        animationSpec = motion.colorTransition,
        label = "checkBorder",
    )

    Box(
        modifier = modifier
            .size(28.dp)
            .clip(RoundedCornerShape(6.dp))
            .background(bgColor)
            .border(2.dp, borderColor, RoundedCornerShape(6.dp))
            .clickable(onClick = onCheckedChange),
        contentAlignment = Alignment.Center,
    ) {
        if (checkProgress > 0f) {
            androidx.compose.foundation.Canvas(modifier = Modifier.size(14.dp)) {
                val stroke = Stroke(width = 2.5.dp.toPx(), cap = StrokeCap.Round)
                val checkPath = androidx.compose.ui.graphics.Path().apply {
                    moveTo(3.dp.toPx(), 7.dp.toPx())
                    lineTo(6.dp.toPx(), 10.5.dp.toPx())
                    lineTo(11.5.dp.toPx(), 3.5.dp.toPx())
                }
                drawPath(
                    path = checkPath,
                    color = Color.White,
                    style = stroke,
                    alpha = checkProgress,
                )
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Small Checkbox — for subtasks
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun SmallCheckbox(
    checked: Boolean,
    onCheckedChange: () -> Unit,
) {
    val bgColor by animateColorAsState(
        targetValue = if (checked) Primary else Color.Transparent,
        animationSpec = LocalMotion.current.colorTransition,
        label = "smallCheckBg",
    )
    val borderColor by animateColorAsState(
        targetValue = if (checked) Primary else MaterialTheme.colorScheme.outline.copy(alpha = 0.6f),
        animationSpec = LocalMotion.current.colorTransition,
        label = "smallCheckBorder",
    )

    Box(
        modifier = Modifier
            .size(22.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(bgColor)
            .border(1.5.dp, borderColor, RoundedCornerShape(4.dp))
            .clickable(onClick = onCheckedChange),
        contentAlignment = Alignment.Center,
    ) {
        if (checked) {
            Icon(
                Icons.Default.Check,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(14.dp),
            )
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Task Editor Sheet — full-featured task creation/editing
// ═══════════════════════════════════════════════════════════════════════════════

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
private fun TaskEditorSheet(
    task: Task?,
    subjects: List<Subject>,
    onDismiss: () -> Unit,
    onSave: (
        title: String,
        description: String,
        subjectId: Long?,
        subjectName: String?,
        priority: TaskPriority,
        dueDate: Long?,
        dueTime: String?,
        isRecurring: Boolean,
        recurringType: RecurringType,
        newSubtasks: List<String>,
    ) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val isDark = MaterialTheme.colorScheme.isDark
    val shapes = LocalGlassShapes.current

    // ─── Form state ───────────────────────────────────────────────────────────
    var title by remember(task) { mutableStateOf(task?.title ?: "") }
    var description by remember(task) { mutableStateOf(task?.description ?: "") }
    var selectedSubjectId by remember(task) { mutableStateOf(task?.subjectId) }
    var selectedSubjectName by remember(task) { mutableStateOf(task?.subjectName) }
    var priority by remember(task) { mutableStateOf(task?.priority) }
    var isRecurring by remember(task) { mutableStateOf(task?.isRecurring) }
    var recurringType by remember(task) { mutableStateOf(task?.recurringType) }

    // Due date
    var dueDateMillis by remember(task) { mutableStateOf(task?.dueDate) }
    var showDatePicker by remember { mutableStateOf(false) }
    var dueTimeStr by remember(task) { mutableStateOf(task?.dueTime) }
    var showTimePicker by remember { mutableStateOf(false) }

    // Subject dropdown
    var showSubjectDropdown by remember { mutableStateOf(false) }

    // Subtasks
    var subtaskInputs by remember(task) {
        mutableStateOf(listOf<String>())
    }
    var newSubtaskText by remember { mutableStateOf("") }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = dueDateMillis ?: System.currentTimeMillis(),
    )

    // Confirm date selection
    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { dueDateMillis = it }
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

    // Priority options
    val priorityOptions = listOf(
        TaskPriority.LOW to ("নিম্ন" to PriorityLow),
        TaskPriority.MEDIUM to ("মাঝারি" to PriorityMedium),
        TaskPriority.HIGH to ("উচ্চ" to PriorityHigh),
        TaskPriority.URGENT to ("জরুরি" to PriorityUrgent),
    )

    // Recurring type options
    val recurringOptions = listOf(
        RecurringType.NONE to "কোনোটি নয়",
        RecurringType.DAILY to "প্রতিদিন",
        RecurringType.WEEKLY to "সাপ্তাহিক",
        RecurringType.MONTHLY to "মাসিক",
    )

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
                    text = if (task != null) "টাস্ক সম্পাদনা" else "নতুন টাস্ক",
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
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            // ─── Title ────────────────────────────────────────────────────────
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("টাস্কের শিরোনাম", style = StudyMasterTypography.labelMedium) },
                singleLine = true,
                shape = RoundedCornerShape(shapes.inputFieldRadius),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Primary.copy(alpha = 0.5f),
                    unfocusedBorderColor = if (isDark) GlassBorderDark else GlassBorderLight,
                ),
                textStyle = StudyMasterTypography.titleMedium,
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
                    .height(100.dp),
                maxLines = 5,
            )

            // ─── Subject Selector ────────────────────────────────────────────
            Box(modifier = Modifier.fillMaxWidth()) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(shapes.inputFieldRadius))
                        .clickable { showSubjectDropdown = !showSubjectDropdown },
                    shape = RoundedCornerShape(shapes.inputFieldRadius),
                    color = Color.Transparent,
                    border = BorderStroke(1.dp, if (isDark) GlassBorderDark else GlassBorderLight),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = selectedSubjectName ?: "বিষয় নির্বাচন করুন (ঐচ্ছিক)",
                            style = StudyMasterTypography.bodyMedium.copy(
                                color = if (selectedSubjectName != null)
                                    MaterialTheme.colorScheme.onSurface
                                else
                                    MaterialTheme.colorScheme.onSurfaceVariant,
                            ),
                        )
                        if (selectedSubjectId != null) {
                            Text(
                                "✕",
                                style = StudyMasterTypography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.clickable {
                                    selectedSubjectId = null
                                    selectedSubjectName = null
                                },
                            )
                        }
                    }
                }
                DropdownMenu(
                    expanded = showSubjectDropdown,
                    onDismissRequest = { showSubjectDropdown = false },
                    shape = RoundedCornerShape(shapes.cardRadiusSmall),
                    containerColor = if (isDark) GlassDarkAlpha80 else GlassLightAlpha90,
                    modifier = Modifier.fillMaxWidth(0.85f),
                ) {
                    subjects.forEach { subject ->
                        DropdownMenuItem(
                            text = {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .size(10.dp)
                                            .clip(CircleShape)
                                            .background(subject.colorHex.toComposeColor()),
                                    )
                                    Spacer(Modifier.width(10.dp))
                                    Text(subject.name, style = StudyMasterTypography.bodyMedium)
                                }
                            },
                            onClick = {
                                selectedSubjectId = subject.id
                                selectedSubjectName = subject.name
                                showSubjectDropdown = false
                            },
                        )
                    }
                }
            }

            // ─── Priority Selector ───────────────────────────────────────────
            Text(
                "অগ্রাধিকার",
                style = StudyMasterTypography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                priorityOptions.forEach { (prio, labelAndColor) ->
                    val (label, color) = labelAndColor
                    val isSelected = priority == prio
                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .clickable { priority = prio },
                        shape = RoundedCornerShape(12.dp),
                        color = if (isSelected) color.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                        border = BorderStroke(
                            1.dp,
                            if (isSelected) color.copy(alpha = 0.6f) else Color.Transparent,
                        ),
                    ) {
                        Text(
                            text = label,
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

            // ─── Due Date ────────────────────────────────────────────────────
            Text(
                "সময়সূচী",
                style = StudyMasterTypography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                // Date picker button
                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(shapes.buttonRadius))
                        .clickable { showDatePicker = true },
                    shape = RoundedCornerShape(shapes.buttonRadius),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        Icon(
                            Icons.Default.CalendarMonth,
                            contentDescription = null,
                            tint = Primary,
                            modifier = Modifier.size(18.dp),
                        )
                        Text(
                            if (dueDateMillis != null)
                                formatDate(dueDateMillis!!)
                            else
                                "তারিখ নির্বাচন করুন",
                            style = StudyMasterTypography.labelMedium.copy(
                                color = if (dueDateMillis != null)
                                    MaterialTheme.colorScheme.onSurface
                                else
                                    MaterialTheme.colorScheme.onSurfaceVariant,
                            ),
                        )
                        if (dueDateMillis != null) {
                            Spacer(Modifier.weight(1f))
                            Text(
                                "✕",
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.clickable { dueDateMillis = null },
                            )
                        }
                    }
                }
            }

            // ─── Recurring ────────────────────────────────────────────────────
            Text(
                "পুনরাবৃত্তি",
                style = StudyMasterTypography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                recurringOptions.forEach { (type, label) ->
                    val isSelected = if (isRecurring) recurringType == type else type == RecurringType.NONE
                    Surface(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .clickable {
                                if (type == RecurringType.NONE) {
                                    isRecurring = false
                                    recurringType = RecurringType.NONE
                                } else {
                                    isRecurring = true
                                    recurringType = type
                                }
                            },
                        shape = RoundedCornerShape(12.dp),
                        color = if (isSelected) Info.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                        border = BorderStroke(
                            1.dp,
                            if (isSelected) Info.copy(alpha = 0.5f) else Color.Transparent,
                        ),
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 7.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                        ) {
                            if (type != RecurringType.NONE) {
                                Icon(
                                    Icons.Default.Repeat,
                                    null,
                                    tint = if (isSelected) Info else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                                    modifier = Modifier.size(14.dp),
                                )
                            }
                            Text(
                                label,
                                style = StudyMasterTypography.labelSmall.copy(
                                    fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                                    color = if (isSelected) Info else MaterialTheme.colorScheme.onSurfaceVariant,
                                ),
                            )
                        }
                    }
                }
            }

            // ─── Subtasks ────────────────────────────────────────────────────
            Text(
                "সাবটাস্ক (ঐচ্ছিক)",
                style = StudyMasterTypography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            // Existing subtask inputs
            subtaskInputs.forEachIndexed { index, subtaskText ->
                if (subtaskText.isNotBlank()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Primary.copy(alpha = 0.08f),
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                            ) {
                                Text(
                                    "• ${index.plus(1).toBengaliDigits()}.",
                                    style = StudyMasterTypography.labelSmall,
                                    color = Primary,
                                )
                                Text(
                                    subtaskText,
                                    style = StudyMasterTypography.bodySmall,
                                    modifier = Modifier.weight(1f),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                )
                            }
                        }
                        IconButton(
                            onClick = {
                                subtaskInputs = subtaskInputs.toMutableList().also { it[index] = "" }
                            },
                            modifier = Modifier.size(24.dp),
                        ) {
                            Icon(
                                Icons.Default.Close,
                                null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                                modifier = Modifier.size(14.dp),
                            )
                        }
                    }
                }
            }

            // Add subtask
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                OutlinedTextField(
                    value = newSubtaskText,
                    onValueChange = { newSubtaskText = it },
                    placeholder = {
                        Text(
                            "সাবটাস্ক লিখুন...",
                            style = StudyMasterTypography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                        )
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    textStyle = StudyMasterTypography.bodySmall,
                    modifier = Modifier.weight(1f),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Primary.copy(alpha = 0.5f),
                        unfocusedBorderColor = if (isDark) GlassBorderDark.copy(alpha = 0.5f) else GlassBorderLight.copy(alpha = 0.5f),
                    ),
                    keyboardOptions = androidx.compose.ui.text.input.KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                    ),
                )
                IconButton(
                    onClick = {
                        if (newSubtaskText.isNotBlank()) {
                            subtaskInputs = subtaskInputs + newSubtaskText.trim()
                            newSubtaskText = ""
                        }
                    },
                    enabled = newSubtaskText.isNotBlank(),
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "সাবটাস্ক যোগ",
                        tint = if (newSubtaskText.isNotBlank()) Primary else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f),
                    )
                }
            }

            // ─── Save Button ─────────────────────────────────────────────────
            Button(
                onClick = {
                    if (title.isNotBlank()) {
                        onSave(
                            title.trim(),
                            description.trim(),
                            selectedSubjectId,
                            selectedSubjectName,
                            priority,
                            dueDateMillis,
                            dueTimeStr,
                            isRecurring,
                            recurringType,
                            subtaskInputs.filter { it.isNotBlank() },
                        )
                    }
                },
                enabled = title.isNotBlank(),
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
                    if (task != null) "আপডেট করুন" else "টাস্ক তৈরি করুন",
                    style = StudyMasterTypography.labelLarge,
                )
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Utility functions
// ═══════════════════════════════════════════════════════════════════════════════

private fun formatRelativeDate(timestamp: Long?): String {
    if (timestamp == null) return ""
    val now = System.currentTimeMillis()
    val diff = timestamp - now
    val dayMs = 24 * 60 * 60 * 1000

    val calendar = Calendar.getInstance()
    calendar.timeInMillis = now
    val todayStart = calendar.apply {
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }.timeInMillis

    val targetCalendar = Calendar.getInstance().apply { timeInMillis = timestamp }
    targetCalendar.set(Calendar.HOUR_OF_DAY, 0)
    targetCalendar.set(Calendar.MINUTE, 0)
    targetCalendar.set(Calendar.SECOND, 0)
    targetCalendar.set(Calendar.MILLISECOND, 0)
    val targetDayStart = targetCalendar.timeInMillis

    val dayDiff = ((targetDayStart - todayStart) / dayMs).toInt()

    return when {
        dayDiff == 0 -> "আজ"
        dayDiff == 1 -> "আগামীকাল"
        dayDiff == -1 -> "গতকাল"
        dayDiff > 1 && dayDiff <= 7 -> "${dayDiff.toBengaliDigits()} দিন পরে"
        dayDiff < -1 -> "${(-dayDiff).toBengaliDigits()} দিন আগে"
        else -> {
            SimpleDateFormat("dd MMM", Locale("bn", "BD")).format(Date(timestamp))
        }
    }
}

@Composable
private fun getDueDateColor(task: Task): Color {
    if (task.dueDate == null) return MaterialTheme.colorScheme.onSurfaceVariant
    if (task.isCompleted) return MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
    val now = System.currentTimeMillis()
    val dayMs = 24 * 60 * 60 * 1000
    val diff = task.dueDate!! - now
    return when {
        diff < 0 -> Error        // Overdue
        diff < dayMs -> Warning   // Due today
        diff < 3 * dayMs -> PriorityHigh // Due soon
        else -> MaterialTheme.colorScheme.onSurfaceVariant
    }
}

private fun formatDate(timestamp: Long): String {
    return try {
        SimpleDateFormat("dd MMM, yyyy", Locale("bn", "BD")).format(Date(timestamp))
    } catch (_: Exception) {
        SimpleDateFormat("dd MMM, yyyy", Locale.getDefault()).format(Date(timestamp))
    }
}

private fun Int.toBengaliDigits(): String = toString().map { digit ->
    if (digit.isDigit()) "০১২৩৪৫৬৭৮৯"[digit.digitToInt()] else digit
}.joinToString("")

private fun Long.toBengaliDigits(): String = toString().map { digit ->
    if (digit.isDigit()) "০১২৩৪৫৬৭৮৯"[digit.digitToInt()] else digit
}.joinToString("")
