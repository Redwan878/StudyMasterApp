package com.porashona.studymaster.ui.compose.screens.routine

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DragIndicator
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FileUpload
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.porashona.studymaster.data.model.RepeatType
import com.porashona.studymaster.data.model.Routine
import com.porashona.studymaster.ui.compose.components.GlassElevatedCard
import com.porashona.studymaster.ui.compose.components.GlassFilledCard
import com.porashona.studymaster.ui.compose.components.GlassOutlinedCard
import com.porashona.studymaster.ui.compose.theme.*
import com.porashona.studymaster.ui.compose.viewmodels.ExamMode
import com.porashona.studymaster.ui.compose.viewmodels.RoutineConflict
import com.porashona.studymaster.ui.compose.viewmodels.RoutineEvent
import com.porashona.studymaster.ui.compose.viewmodels.RoutineTemplate
import com.porashona.studymaster.ui.compose.viewmodels.RoutineViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Calendar

// ═══════════════════════════════════════════════════════════════════════════════
// RoutineScreen — Full routine management
//
// Features:
//  - Day tabs (Sat-Fri, Bangladesh week) with scrollable chip row
//  - Time-scheduled routine list with activity-type color coding
//  - Master routine view (merging book plans, exam routines, PDF release dates)
//  - Add/Edit routine dialog (time, subject, activity type, recurring template)
//  - Recurring routine templates (weekly, multi-week rotations)
//  - Conflict detection (coaching class vs self-study overlap) with visual warning
//  - Auto-slot new tasks into free time blocks
//  - Exam-day vs non-exam-day mode toggle
//  - PDF routine import button
//  - "What should I study now" based on current time slot
//  - Weekly template management
//  - Drag-to-reorder (basic, using LazyColumn reorder)
//  - Empty state when no routines
//  - All Bengali text
// ═══════════════════════════════════════════════════════════════════════════════

// Bangladesh week: Saturday=1, Sunday=2, ..., Friday=7 (Calendar.SATURDAY=7..Calendar.FRIDAY=6)
private val bangladeshWeekDays = listOf(
    DayTab(7, "\u09B6\u09A8\u09BF"),
    DayTab(1, "\u09B0\u09AC\u09BF"),
    DayTab(2, "\u09B8\u09CB\u09AE"),
    DayTab(3, "\u09AE\u0999\u09CD\u0997\u09B2"),
    DayTab(4, "\u09AC\u09C1\u09A7"),
    DayTab(5, "\u09AC\u09BF\u09C3\u09B9\u09C3"),
    DayTab(6, "\u09B6\u09C1\u0995\u09CD\u09B0"),
)

private data class DayTab(val calendarDay: Int, val label: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoutineScreen(
    viewModel: RoutineViewModel = hiltViewModel(),
) {
    val routines by viewModel.filteredRoutines.collectAsState()
    val conflicts by viewModel.conflicts.collectAsState()
    val examMode by viewModel.examMode.collectAsState()
    val masterRoutine by viewModel.masterRoutine.collectAsState()
    val weeklyTemplates by viewModel.weeklyTemplates.collectAsState()
    val filterDayOfWeek by viewModel.filterDayOfWeek.collectAsState()
    val motion = LocalMotion.current
    val shapes = LocalGlassShapes.current
    val coroutineScope = rememberCoroutineScope()
    val haptic = LocalHapticFeedback.current
    val context = LocalContext.current

    var showAddDialog by remember { mutableStateOf(false) }
    var showTemplateDialog by remember { mutableStateOf(false) }
    var showAutoSlotDialog by remember { mutableStateOf(false) }
    var editingRoutine by remember { mutableStateOf<Routine?>(null) }
    var viewMode by remember { mutableStateOf(RoutineViewMode.LIST) }
    var showWhatNow by remember { mutableStateOf(false) }
    var whatNowRoutine by remember { mutableStateOf<Routine?>(null) }

    // Drag reorder state
    val routineListState = rememberLazyListState()
    var draggedIndex by remember { mutableIntStateOf(-1) }
    var draggedItem by remember { mutableStateOf<Routine?>(null) }
    var dragOffsetY by remember { mutableStateOf(0f) }

    // Compute "what should I study now" based on current time
    val now = Calendar.getInstance()
    val currentHour = now.get(Calendar.HOUR_OF_DAY)
    val currentMinute = now.get(Calendar.MINUTE)
    val currentDayOfWeek = now.get(Calendar.DAY_OF_WEEK)
    val currentTimeMinutes = currentHour * 60 + currentMinute

    LaunchedEffect(routines, currentTimeMinutes, currentDayOfWeek) {
        val todayRoutines = routines
            .filter { it.isEnabled && it.repeatDays.contains(currentDayOfWeek) }
            .sortedBy { it.hour * 60 + it.minute }
        val currentOrNext = todayRoutines.firstOrNull {
            val start = it.hour * 60 + it.minute
            val end = start + it.durationMinutes
            currentTimeMinutes in start until end || currentTimeMinutes < start
        }
        whatNowRoutine = currentOrNext
    }

    // Listen for events
    LaunchedEffect(Unit) {
        viewModel.events.collectLatest { event ->
            when (event) {
                is RoutineEvent.AutoSlotDone -> {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                }
                is RoutineEvent.AutoSlotFailed -> {}
                is RoutineEvent.PdfImportCompleted -> {}
                else -> {}
            }
        }
    }

    // Load master routine when in master view
    LaunchedEffect(viewMode, filterDayOfWeek) {
        if (viewMode == RoutineViewMode.MASTER) {
            viewModel.getMasterRoutineView(filterDayOfWeek)
        }
        viewModel.detectConflicts()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        // ═══ Header ═══
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "\uD83D\uDCC5 \u09B0\u09C1\u099F\u09BF\u09A8",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    Text(
                        text = if (examMode == ExamMode.EXAM_DAY) "\uD83D\uDEA8 \u09AA\u09B0\u09C0\u0995\u09CD\u09B7\u09BE \u09A6\u09BF\u09A8 - \u0997\u09AD\u09C0\u09B0 \u09B8\u09C7\u09B6\u09A8"
                        else "\u0986\u099C\u0995\u09C7\u09B0 \u09B0\u09C1\u099F\u09BF\u09A8 \u09AA\u09B0\u09BF\u099A\u09BE\u09B2\u09A8\u09BE \u0995\u09B0\u09C1\u09A8",
                        style = MaterialTheme.typography.bodySmall,
                        color = if (examMode == ExamMode.EXAM_DAY) Error
                        else MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
                // View mode toggle
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    GlassOutlinedCard(
                        onClick = { viewMode = RoutineViewMode.LIST },
                        cornerRadius = shapes.chipRadius,
                        padding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                        tint = if (viewMode == RoutineViewMode.LIST) Primary.copy(alpha = 0.15f) else null,
                    ) {
                        Text(
                            text = "\uD83D\uDCDD \u09A4\u09BE\u09B2\u09BF\u0995\u09BE",
                            style = MaterialTheme.typography.labelMedium,
                            color = if (viewMode == RoutineViewMode.LIST) Primary
                            else MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                    GlassOutlinedCard(
                        onClick = { viewMode = RoutineViewMode.MASTER },
                        cornerRadius = shapes.chipRadius,
                        padding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                        tint = if (viewMode == RoutineViewMode.MASTER) Primary.copy(alpha = 0.15f) else null,
                    ) {
                        Text(
                            text = "\uD83D\uDCCA \u09AE\u09BE\u09B8\u09CD\u099F\u09BE\u09B0",
                            style = MaterialTheme.typography.labelMedium,
                            color = if (viewMode == RoutineViewMode.MASTER) Primary
                            else MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
            }

            // Conflict warning
            AnimatedVisibility(
                visible = conflicts.isNotEmpty(),
                enter = slideInVertically { -it } + fadeIn(motion.fadeIn),
                exit = slideOutVertically { -it } + fadeOut(tween(200)),
            ) {
                GlassFilledCard(
                    tint = Error.copy(alpha = 0.1f),
                    cornerRadius = shapes.cardRadiusSmall,
                    padding = PaddingValues(12.dp),
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp),
                            tint = Error,
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "\u09B8\u09AE\u09AF\u09BC \u09B8\u0982\u0998\u09B0\u09CD\u09B7: ${conflicts.size.toBengaliDigits()} \u099F\u09BF",
                                style = MaterialTheme.typography.labelMedium,
                                color = Error,
                                fontWeight = FontWeight.Bold,
                            )
                            conflicts.take(2).forEach { conflict ->
                                Text(
                                    text = "\u2022 ${conflict.routine.subjectName} \u0993 ${conflict.conflictingWith.subjectName} - ${conflict.overlapMinutes.toBengaliDigits()} \u09AE\u09BF\u09A8\u09BF\u099F",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Error.copy(alpha = 0.8f),
                                )
                            }
                        }
                    }
                }
            }

            // "What should I study now" card
            AnimatedVisibility(
                visible = showWhatNow && whatNowRoutine != null,
                enter = slideInVertically { it } + fadeIn(motion.fadeIn),
                exit = slideOutVertically { it } + fadeOut(tween(200)),
            ) {
                GlassFilledCard(
                    tint = XpGain.copy(alpha = 0.08f),
                    cornerRadius = shapes.cardRadius,
                    padding = PaddingValues(16.dp),
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "\uD83D\uDCAA \u098F\u0996\u09A8 \u09AF\u09C7 \u09AA\u09A1\u09BC\u09A4\u09C7 \u09B9\u09AC\u09C7:",
                                style = MaterialTheme.typography.titleSmall,
                                color = XpGain,
                                fontWeight = FontWeight.Bold,
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            IconButton(
                                onClick = { showWhatNow = false },
                                modifier = Modifier.size(24.dp),
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp),
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                            }
                        }
                        whatNowRoutine?.let { r ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .background(Primary, CircleShape),
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = r.subjectName,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontWeight = FontWeight.Bold,
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = r.title.ifBlank { "" },
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.weight(1f),
                                )
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "\u23F0 ${String.format("%02d:%02d", r.hour, r.minute)}",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontFamily = EnglishFontFamily,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = "\u23F1 ${r.durationMinutes.toBengaliDigits()} \u09AE\u09BF\u09A8\u09BF\u099F",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontFamily = EnglishFontFamily,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                            }
                        }
                    }
                }
            }
        }

        // ═══ Day tabs (Bangladesh week: Sat-Fri) ═══
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // "All" chip
            item {
                val isAll = filterDayOfWeek == -1
                GlassFilledCard(
                    onClick = { viewModel.clearFilter() },
                    cornerRadius = shapes.chipRadius,
                    tint = if (isAll) Primary.copy(alpha = 0.2f) else null,
                    padding = PaddingValues(horizontal = 14.dp, vertical = 8.dp),
                ) {
                    Text(
                        text = "\u09B8\u09AC",
                        style = MaterialTheme.typography.labelMedium,
                        color = if (isAll) Primary else MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = if (isAll) FontWeight.Bold else FontWeight.Normal,
                    )
                }
            }
            items(bangladeshWeekDays) { day ->
                val isSelected = filterDayOfWeek == day.calendarDay
                val isToday = currentDayOfWeek == day.calendarDay
                GlassFilledCard(
                    onClick = { viewModel.setFilterDayOfWeek(day.calendarDay) },
                    cornerRadius = shapes.chipRadius,
                    tint = when {
                        isSelected -> Primary.copy(alpha = 0.2f)
                        isToday -> Primary.copy(alpha = 0.08f)
                        else -> null
                    },
                    padding = PaddingValues(horizontal = 14.dp, vertical = 8.dp),
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = day.label,
                            style = MaterialTheme.typography.labelMedium,
                            color = when {
                                isSelected -> Primary
                                isToday -> PrimaryLight
                                else -> MaterialTheme.colorScheme.onSurfaceVariant
                            },
                            fontWeight = if (isSelected || isToday) FontWeight.Bold else FontWeight.Normal,
                        )
                        if (isToday) {
                            Text(
                                text = "\u0986\u099C",
                                style = MaterialTheme.typography.labelSmall,
                                color = PrimaryLight,
                                fontSize = 8.sp,
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        HorizontalDivider(
            color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f),
            modifier = Modifier.padding(horizontal = 16.dp),
        )

        // ═══ Content Area ═══
        when (viewMode) {
            RoutineViewMode.LIST -> RoutineListView(
                routines = routines,
                conflicts = conflicts,
                examMode = examMode,
                filterDayOfWeek = filterDayOfWeek,
                currentDayOfWeek = currentDayOfWeek,
                currentTimeMinutes = currentTimeMinutes,
                draggedIndex = draggedIndex,
                draggedItem = draggedItem,
                dragOffsetY = dragOffsetY,
                shapes = shapes,
                motion = motion,
                onEdit = { routine ->
                    editingRoutine = routine
                    showAddDialog = true
                },
                onDelete = { viewModel.deleteRoutine(it) },
                onToggleEnabled = { id, enabled -> viewModel.toggleRoutineEnabled(id, enabled) },
                onDragStart = { index, routine ->
                    draggedIndex = index
                    draggedItem = routine
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                },
                onDragEnd = {
                    draggedIndex = -1
                    draggedItem = null
                    dragOffsetY = 0f
                },
                onDrag = { dy -> dragOffsetY = dy },
                onReorder = { from, to ->
                    // Basic reorder - swap hour/minute between routines
                    val list = routines.toMutableList()
                    if (from in list.indices && to in list.indices) {
                        val fromRoutine = list[from]
                        val toRoutine = list[to]
                        viewModel.updateRoutine(
                            fromRoutine.copy(
                                hour = toRoutine.hour,
                                minute = toRoutine.minute,
                            )
                        )
                        viewModel.updateRoutine(
                            toRoutine.copy(
                                hour = fromRoutine.hour,
                                minute = fromRoutine.minute,
                            )
                        )
                    }
                    draggedIndex = -1
                    draggedItem = null
                },
                onShowWhatNow = { showWhatNow = true },
            )
            RoutineViewMode.MASTER -> MasterRoutineView(
                masterRoutine = masterRoutine,
                shapes = shapes,
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // ═══ Bottom Action Bar ═══
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    MaterialTheme.colorScheme.surface
                        .copy(alpha = 0.95f)
                        .compositeOver(MaterialTheme.colorScheme.background)
                )
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                // Add routine
                GlassFilledCard(
                    onClick = {
                        editingRoutine = null
                        showAddDialog = true
                    },
                    modifier = Modifier.weight(1f),
                    tint = Primary.copy(alpha = 0.12f),
                    cornerRadius = shapes.buttonRadius,
                    padding = PaddingValues(vertical = 12.dp),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            tint = Primary,
                            modifier = Modifier.size(20.dp),
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "\u09B0\u09C1\u099F\u09BF\u09A8 \u09AF\u09CB\u0997 \u0995\u09B0\u09C1\u09A8",
                            style = MaterialTheme.typography.labelLarge,
                            color = Primary,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }

                // Auto-slot
                GlassOutlinedCard(
                    onClick = { showAutoSlotDialog = true },
                    cornerRadius = shapes.buttonRadius,
                    padding = PaddingValues(vertical = 12.dp, horizontal = 16.dp),
                ) {
                    Text(
                        text = "\uD83C\uDFAF",
                        fontSize = 18.sp,
                    )
                }

                // Templates
                GlassOutlinedCard(
                    onClick = { showTemplateDialog = true },
                    cornerRadius = shapes.buttonRadius,
                    padding = PaddingValues(vertical = 12.dp, horizontal = 16.dp),
                ) {
                    Text(
                        text = "\uD83D\uDCCB",
                        fontSize = 18.sp,
                    )
                }

                // PDF import
                GlassOutlinedCard(
                    onClick = {
                        // PDF import would open a file picker
                    },
                    cornerRadius = shapes.buttonRadius,
                    padding = PaddingValues(vertical = 12.dp, horizontal = 16.dp),
                ) {
                    Icon(
                        imageVector = Icons.Default.FileUpload,
                        contentDescription = "PDF \u0987\u09AE\u09AA\u09CB\u09B0\u09CD\u099F",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(20.dp),
                    )
                }
            }

            // "What should I study now" quick button
            GlassOutlinedCard(
                onClick = { showWhatNow = !showWhatNow },
                cornerRadius = shapes.buttonRadius,
                padding = PaddingValues(vertical = 10.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "\uD83D\uDCAA \u098F\u0996\u09A8 \u0995\u09C0 \u09AA\u09A1\u09BC\u09A4\u09C7 \u09B9\u09AC\u09C7?",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        }
    }

    // ═══ Add/Edit Routine Dialog ═══
    if (showAddDialog) {
        AddEditRoutineDialog(
            routine = editingRoutine,
            onDismiss = {
                showAddDialog = false
                editingRoutine = null
            },
            onConfirm = { routine ->
                if (editingRoutine != null) {
                    viewModel.updateRoutine(routine.copy(id = editingRoutine!!.id))
                } else {
                    viewModel.addRoutine(routine)
                }
                showAddDialog = false
                editingRoutine = null
            },
            currentDayOfWeek = currentDayOfWeek,
        )
    }

    // ═══ Auto-slot Dialog ═══
    if (showAutoSlotDialog) {
        AutoSlotDialog(
            onDismiss = { showAutoSlotDialog = false },
            onAutoSlot = { subjectName, duration ->
                viewModel.autoSlotTask(subjectName, duration)
                showAutoSlotDialog = false
            },
        )
    }

    // ═══ Template Dialog ═══
    if (showTemplateDialog) {
        TemplateDialog(
            templates = weeklyTemplates,
            onDismiss = { showTemplateDialog = false },
            onApplyTemplate = { template ->
                viewModel.applyTemplate(template)
                showTemplateDialog = false
            },
        )
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// View mode enum
// ═══════════════════════════════════════════════════════════════════════════════

private enum class RoutineViewMode {
    LIST, MASTER
}

// ═══════════════════════════════════════════════════════════════════════════════
// Routine List View — time-ordered routine list with color coding
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun RoutineListView(
    routines: List<Routine>,
    conflicts: List<RoutineConflict>,
    examMode: ExamMode,
    filterDayOfWeek: Int,
    currentDayOfWeek: Int,
    currentTimeMinutes: Int,
    draggedIndex: Int,
    draggedItem: Routine?,
    dragOffsetY: Float,
    shapes: GlassShapes,
    motion: StudyMasterMotion,
    onEdit: (Routine) -> Unit,
    onDelete: (Routine) -> Unit,
    onToggleEnabled: (Long, Boolean) -> Unit,
    onDragStart: (Int, Routine) -> Unit,
    onDragEnd: () -> Unit,
    onDrag: (Float) -> Unit,
    onReorder: (Int, Int) -> Unit,
    onShowWhatNow: () -> Unit,
) {
    val isExamDay = examMode == ExamMode.EXAM_DAY
    val sortedRoutines = remember(routines) {
        routines.sortedBy { it.hour * 60 + it.minute }
    }

    if (sortedRoutines.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            contentAlignment = Alignment.Center,
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "\uD83D\uDCC5",
                    fontSize = 48.sp,
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "\u0995\u09CB\u09A8\u09CB \u09B0\u09C1\u099F\u09BF\u09A8 \u09A8\u09C7\u0987",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "\u09A8\u09BF\u099A\u09C7\u09B0 \u09AC\u09CB\u09A4\u09BE\u09AE\u09C7 \u09B0\u09C1\u099F\u09BF\u09A8 \u09AF\u09CB\u0997 \u0995\u09B0\u09C1\u09A8 \u0985\u09A5\u09AC\u09BE \u099F\u09C7\u09AE\u09AA\u09CD\u09B2\u09C7\u099F \u09AC\u09CD\u09AF\u09AC\u09B9\u09BE\u09B0 \u0995\u09B0\u09C1\u09A8",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(16.dp))
                GlassElevatedCard(
                    onClick = onShowWhatNow,
                    cornerRadius = shapes.chipRadius,
                    padding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                ) {
                    Text(
                        text = "\uD83D\uDCAA \u098F\u0996\u09A8 \u0995\u09C0 \u09AA\u09A1\u09BC\u09A4\u09C7 \u09B9\u09AC\u09C7?",
                        style = MaterialTheme.typography.labelMedium,
                        color = Primary,
                    )
                }
            }
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(2.dp),
            state = rememberLazyListState(),
        ) {
            // Time markers
            val lastEndMinute = remember { mutableIntStateOf(-1) }

            itemsIndexed(sortedRoutines, key = { it.id }) { index, routine ->
                val routineStartMinutes = routine.hour * 60 + routine.minute
                val routineEndMinutes = routineStartMinutes + routine.durationMinutes
                val isCurrentlyActive = routine.repeatDays.contains(currentDayOfWeek) &&
                        currentTimeMinutes in routineStartMinutes until routineEndMinutes &&
                        routine.isEnabled

                val isConflicting = conflicts.any { c ->
                    c.routine.id == routine.id || c.conflictingWith.id == routine.id
                }

                val activityColor = getActivityColorForRoutine(routine, isExamDay)

                val isDragging = draggedIndex == index

                RoutineItemCard(
                    routine = routine,
                    index = index,
                    activityColor = activityColor,
                    isCurrentlyActive = isCurrentlyActive,
                    isConflicting = isConflicting,
                    isDragging = isDragging,
                    dragOffsetY = dragOffsetY,
                    isExamDay = isExamDay,
                    shapes = shapes,
                    onEdit = { onEdit(routine) },
                    onDelete = { onDelete(routine) },
                    onToggleEnabled = { onToggleEnabled(routine.id, it) },
                    onDragStart = { onDragStart(index, routine) },
                    onDragEnd = onDragEnd,
                    onDrag = onDrag,
                )
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Routine Item Card — individual routine entry
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun RoutineItemCard(
    routine: Routine,
    index: Int,
    activityColor: Color,
    isCurrentlyActive: Boolean,
    isConflicting: Boolean,
    isDragging: Boolean,
    dragOffsetY: Float,
    isExamDay: Boolean,
    shapes: GlassShapes,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onToggleEnabled: (Boolean) -> Unit,
    onDragStart: () -> Unit,
    onDragEnd: () -> Unit,
    onDrag: (Float) -> Unit,
) {
    val activeGlow by animateColorAsState(
        targetValue = if (isCurrentlyActive) activityColor.copy(alpha = 0.15f)
        else Color.Transparent,
        animationSpec = tween(500),
        label = "activeGlow",
    )

    val elevation by animateDpAsState(
        targetValue = if (isDragging) 12.dp else 0.dp,
        animationSpec = spring(stiffness = 400f),
        label = "dragElevation",
    )

    val scale by animateFloatAsState(
        targetValue = if (isDragging) 1.02f else 1f,
        animationSpec = spring(stiffness = 400f),
        label = "dragScale",
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .offset { IntOffset(0, dragOffsetY.toInt()) }
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                shadowElevation = elevation.toPx()
                translationZ = elevation.toPx()
            }
            .padding(horizontal = 16.dp, vertical = 3.dp),
    ) {
        GlassFilledCard(
            tint = if (isCurrentlyActive) activeGlow else if (!routine.isEnabled) Color.Transparent else null,
            cornerRadius = shapes.cardRadiusSmall,
            padding = PaddingValues(0.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp, end = 8.dp, top = 4.dp, bottom = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // Drag handle
                Icon(
                    imageVector = Icons.Default.DragIndicator,
                    contentDescription = "\u09B8\u09B0\u09CD\u09AC\u09BE\u09A8 \u0995\u09B0\u09A4\u09C7 \u099F\u09BE\u09A8\u09C1\u09A8",
                    modifier = Modifier
                        .size(24.dp)
                        .padding(2.dp)
                        .pointerInput(Unit) {
                            detectDragGesturesAfterLongPress(
                                onDragStart = { onDragStart() },
                                onDrag = { _, dragAmount -> onDrag(dragAmount.y) },
                                onDragEnd = { onDragEnd() },
                                onDragCancel = { onDragEnd() },
                            )
                        },
                    tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f),
                )

                // Activity type color bar
                Box(
                    modifier = Modifier
                        .width(4.dp)
                        .height(48.dp)
                        .background(activityColor, RoundedCornerShape(2.dp)),
                )

                Spacer(modifier = Modifier.width(8.dp))

                // Time column
                Column(
                    modifier = Modifier.width(60.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = String.format("%02d:%02d", routine.hour, routine.minute),
                        style = MaterialTheme.typography.labelMedium,
                        fontFamily = EnglishFontFamily,
                        color = if (isCurrentlyActive) activityColor
                        else MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = "${routine.durationMinutes.toBengaliDigits()}\u09AE",
                        style = MaterialTheme.typography.labelSmall,
                        fontFamily = EnglishFontFamily,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Content
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center,
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if (isConflicting) {
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = "\u09B8\u0982\u0998\u09B0\u09CD\u09B7",
                                modifier = Modifier.size(14.dp),
                                tint = Error,
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                        }
                        Text(
                            text = routine.subjectName,
                            style = MaterialTheme.typography.titleSmall,
                            color = if (routine.isEnabled) MaterialTheme.colorScheme.onSurface
                            else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
                            fontWeight = FontWeight.Medium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                    if (routine.title.isNotBlank()) {
                        Text(
                            text = routine.title,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(
                                alpha = if (routine.isEnabled) 0.7f else 0.3f
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                    // Day indicators
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(2.dp),
                        modifier = Modifier.padding(top = 2.dp),
                    ) {
                        bangladeshWeekDays.forEach { day ->
                            if (routine.repeatDays.contains(day.calendarDay)) {
                                Box(
                                    modifier = Modifier
                                        .size(6.dp)
                                        .background(
                                            color = activityColor.copy(alpha = 0.6f),
                                            shape = CircleShape,
                                        ),
                                )
                            } else {
                                Box(
                                    modifier = Modifier
                                        .size(6.dp)
                                        .background(
                                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                                            shape = CircleShape,
                                        ),
                                )
                            }
                        }
                    }
                }

                // Active indicator
                if (isCurrentlyActive) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(activityColor, CircleShape),
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                }

                // Toggle
                Switch(
                    checked = routine.isEnabled,
                    onCheckedChange = onToggleEnabled,
                    modifier = Modifier.padding(horizontal = 4.dp),
                    colors = SwitchDefaults.colors(
                        checkedTrackColor = activityColor,
                        checkedThumbColor = OnPrimary,
                    ),
                )

                // Edit
                IconButton(
                    onClick = onEdit,
                    modifier = Modifier.size(32.dp),
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "\u09B8\u09AE\u09CD\u09AA\u09BE\u09A6\u09A8\u09BE",
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }

                // Delete
                IconButton(
                    onClick = onDelete,
                    modifier = Modifier.size(32.dp),
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "\u09AE\u09C1\u099B\u09C1\u09A8",
                        modifier = Modifier.size(16.dp),
                        tint = Error.copy(alpha = 0.6f),
                    )
                }
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Master Routine View — merged timeline view
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun MasterRoutineView(
    masterRoutine: List<com.porashona.studymaster.ui.compose.viewmodels.MasterRoutineSlot>,
    shapes: GlassShapes,
) {
    if (masterRoutine.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize().padding(32.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "\u09AE\u09BE\u09B8\u09CD\u099F\u09BE\u09B0 \u09B0\u09C1\u099F\u09BF\u09A8\u09C7 \u0995\u09CB\u09A8\u09CB \u09B8\u0995\u09CD\u09B7\u09AE \u09A8\u09C7\u0987",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
            )
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            items(masterRoutine, key = { "${it.hour}_${it.minute}" }) { slot ->
                GlassElevatedCard(
                    cornerRadius = shapes.cardRadiusSmall,
                    padding = PaddingValues(12.dp),
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = String.format("%02d:%02d", slot.hour, slot.minute),
                            style = MaterialTheme.typography.labelMedium,
                            fontFamily = EnglishFontFamily,
                            color = Primary,
                            fontWeight = FontWeight.Bold,
                        )
                        slot.routines.forEach { routine ->
                            val color = SubjectPalette.colorForIndex(
                                routine.subjectName.hashCode() % SubjectPalette.colors.size
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(color.copy(alpha = 0.1f))
                                    .padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(6.dp)
                                        .background(color, CircleShape),
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = routine.subjectName,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = color,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.weight(1f),
                                )
                                Text(
                                    text = "${routine.durationMinutes.toBengaliDigits()}\u09AE",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontFamily = EnglishFontFamily,
                                    color = color.copy(alpha = 0.7f),
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
// Add/Edit Routine Dialog
// ═══════════════════════════════════════════════════════════════════════════════

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddEditRoutineDialog(
    routine: Routine?,
    onDismiss: () -> Unit,
    onConfirm: (Routine) -> Unit,
    currentDayOfWeek: Int,
) {
    val shapes = LocalGlassShapes.current

    var subjectName by remember { mutableStateOf(routine?.subjectName ?: "") }
    var title by remember { mutableStateOf(routine?.title ?: "") }
    var hour by remember { mutableIntStateOf(routine?.hour ?: 9) }
    var minute by remember { mutableIntStateOf(routine?.minute ?: 0) }
    var durationMinutes by remember { mutableIntStateOf(routine?.durationMinutes ?: 25) }
    var repeatType by remember { mutableStateOf(routine?.repeatType ?: RepeatType.WEEKLY) }
    var selectedDays by remember { mutableStateListOf(*routine?.repeatDays?.toTypedArray() ?: intArrayOf(currentDayOfWeek)) }
    var showTimePicker by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = if (routine != null) "\u270F\uFE0F \u09B0\u09C1\u099F\u09BF\u09A8 \u09B8\u09AE\u09CD\u09AA\u09BE\u09A6\u09A8\u09BE"
                else "\u2795 \u09A8\u09A4\u09C1\u09A8 \u09B0\u09C1\u099F\u09BF\u09A8",
                style = MaterialTheme.typography.titleMedium,
            )
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth(),
            ) {
                // Subject name
                OutlinedTextField(
                    value = subjectName,
                    onValueChange = { subjectName = it },
                    label = { Text("\u09AC\u09BF\u09B7\u09DF\u09C7\u09B0 \u09A8\u09BE\u09AE") },
                    placeholder = { Text("\u09AF\u09C7\u09AE\u09A8: \u0997\u09A3\u09BF\u09A4, \u09AA\u09A6\u09BE\u09B0\u09CD\u09A5\u09AC\u09BF\u099C\u09CD\u099E\u09BE\u09A8") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(shapes.inputFieldRadius),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Primary,
                    ),
                )

                // Title
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("\u09B6\u09C0\u09B0\u09CD\u09B7\u0995 / \u09AC\u09BF\u09AC\u09B0\u09A3") },
                    placeholder = { Text("\u09AF\u09C7\u09AE\u09A8: \u0985\u09A7\u09CD\u09AF\u09BE\u09DF \u09E9 - \u09AC\u09C0\u099C\u0997\u09A3\u09BF\u09A4") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(shapes.inputFieldRadius),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Primary,
                    ),
                )

                // Time selection
                Text(
                    text = "\u23F0 \u09B8\u09AE\u09DF",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    // Hour picker
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        OutlinedTextField(
                            value = hour.toString().padStart(2, '0'),
                            onValueChange = { hour = it.toIntOrNull()?.coerceIn(0, 23) ?: 0 },
                            modifier = Modifier.width(80.dp),
                            shape = RoundedCornerShape(shapes.inputFieldRadius),
                            textStyle = MaterialTheme.typography.titleMedium.copy(
                                fontFamily = EnglishFontFamily,
                                textAlign = TextAlign.Center,
                            ),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Primary,
                            ),
                        )
                        Text(
                            text = "\u0998\u09A3\u09CD\u099F\u09BE",
                            style = MaterialTheme.typography.labelSmall,
                        )
                    }
                    Text(
                        text = ":",
                        style = MaterialTheme.typography.headlineMedium,
                        fontFamily = EnglishFontFamily,
                        modifier = Modifier.padding(top = 8.dp),
                    )
                    // Minute picker
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        OutlinedTextField(
                            value = minute.toString().padStart(2, '0'),
                            onValueChange = {
                                val val5 = (it.toIntOrNull() ?: 0) / 5 * 5
                                minute = val5.coerceIn(0, 55)
                            },
                            modifier = Modifier.width(80.dp),
                            shape = RoundedCornerShape(shapes.inputFieldRadius),
                            textStyle = MaterialTheme.typography.titleMedium.copy(
                                fontFamily = EnglishFontFamily,
                                textAlign = TextAlign.Center,
                            ),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Primary,
                            ),
                        )
                        Text(
                            text = "\u09AE\u09BF\u09A8\u09BF\u099F",
                            style = MaterialTheme.typography.labelSmall,
                        )
                    }
                }

                // Duration
                Text(
                    text = "\u0995\u09BE\u09B0\u09CD\u09AF\u0995\u09B2\u09BE\u09AA: ${durationMinutes.toBengaliDigits()} \u09AE\u09BF\u09A8\u09BF\u099F",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    listOf(15, 25, 30, 45, 60, 90, 120).forEach { dur ->
                        GlassOutlinedCard(
                            onClick = { durationMinutes = dur },
                            cornerRadius = shapes.chipRadius,
                            padding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                            tint = if (durationMinutes == dur) Primary.copy(alpha = 0.12f) else null,
                        ) {
                            Text(
                                text = "${dur.toBengaliDigits()}",
                                style = MaterialTheme.typography.labelSmall,
                                fontFamily = EnglishFontFamily,
                                color = if (durationMinutes == dur) Primary
                                else MaterialTheme.colorScheme.onSurfaceVariant,
                                fontWeight = if (durationMinutes == dur) FontWeight.Bold else FontWeight.Normal,
                            )
                        }
                    }
                }

                // Repeat type
                Text(
                    text = "\uD83D\uDD01 \u09AA\u09C1\u09A8\u09B0\u09BE\u09AC\u09C3\u09A4\u09CD\u09A4\u09BF",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    RepeatType.entries.forEach { type ->
                        GlassOutlinedCard(
                            onClick = { repeatType = type },
                            cornerRadius = shapes.chipRadius,
                            padding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                            tint = if (repeatType == type) Primary.copy(alpha = 0.12f) else null,
                        ) {
                            Text(
                                text = when (type) {
                                    RepeatType.ONCE -> "\u098F\u0995\u09AC\u09BE\u09B0"
                                    RepeatType.DAILY -> "\u09AA\u09CD\u09B0\u09A4\u09BF\u09A6\u09BF\u09A8"
                                    RepeatType.WEEKLY -> "\u09B8\u09BE\u09AA\u09CD\u09A4\u09BE\u09B9\u09BF\u0995"
                                    RepeatType.CUSTOM -> "\u0995\u09BE\u09B8\u09CD\u099F\u09AE"
                                },
                                style = MaterialTheme.typography.labelSmall,
                                color = if (repeatType == type) Primary
                                else MaterialTheme.colorScheme.onSurfaceVariant,
                                fontWeight = if (repeatType == type) FontWeight.Bold else FontWeight.Normal,
                            )
                        }
                    }
                }

                // Day selection (for weekly/custom)
                if (repeatType == RepeatType.WEEKLY || repeatType == RepeatType.CUSTOM) {
                    Text(
                        text = "\uD83D\uDCC5 \u09A6\u09BF\u09A8 \u09A8\u09BF\u09B0\u09CD\u09AC\u09BE\u099A\u09A8 \u0995\u09B0\u09C1\u09A8",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        bangladeshWeekDays.forEach { day ->
                            val isSelected = selectedDays.contains(day.calendarDay)
                            GlassFilledCard(
                                onClick = {
                                    if (isSelected) {
                                        selectedDays.remove(day.calendarDay)
                                    } else {
                                        selectedDays.add(day.calendarDay)
                                    }
                                },
                                cornerRadius = shapes.chipRadius,
                                tint = if (isSelected) Primary.copy(alpha = 0.2f) else null,
                                padding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                            ) {
                                Text(
                                    text = day.label,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = if (isSelected) Primary
                                    else MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                )
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val repeatDays = when (repeatType) {
                        RepeatType.ONCE -> listOf(currentDayOfWeek)
                        RepeatType.DAILY -> listOf(1, 2, 3, 4, 5, 6, 7)
                        else -> selectedDays.toList().ifEmpty { listOf(currentDayOfWeek) }
                    }
                    onConfirm(
                        Routine(
                            subjectName = subjectName.ifBlank { "\u09B8\u09BE\u09A7\u09BE\u09B0\u09A3" },
                            title = title,
                            hour = hour,
                            minute = minute,
                            durationMinutes = durationMinutes,
                            repeatType = repeatType,
                            repeatDays = repeatDays,
                        )
                    )
                },
                enabled = subjectName.isNotBlank() && selectedDays.isNotEmpty(),
            ) {
                Text(
                    "\u09B8\u0982\u09B0\u0995\u09CD\u09B7\u09A3",
                    color = if (subjectName.isNotBlank()) Primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("\u09AC\u09BE\u09A4\u09BF\u09B2", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        },
        containerColor = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(shapes.dialogRadius),
    )
}

// ═══════════════════════════════════════════════════════════════════════════════
// Auto-slot Dialog
// ═══════════════════════════════════════════════════════════════════════════════

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AutoSlotDialog(
    onDismiss: () -> Unit,
    onAutoSlot: (String, Int) -> Unit,
) {
    val shapes = LocalGlassShapes.current
    var subjectName by remember { mutableStateOf("") }
    var durationMinutes by remember { mutableIntStateOf(30) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "\uD83C\uDFAF \u0985\u099F\u09CB-\u09B8\u09CD\u09B2\u099F",
                style = MaterialTheme.typography.titleMedium,
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(
                    text = "\u0996\u09BE\u09B2\u09BF \u09B8\u09AE\u09DF\u09C7 \u09B8\u09CD\u09AC\u09AF\u09BC\u0982\u0995\u09CD\u09B0\u09BF\u09AF\u09BC \u099F\u09BE\u09B8\u09CD\u0995 \u09AC\u09B8\u09BF\u09AF\u09BC\u09C7 \u09B0\u09C1\u099F\u09BF\u09A8\u09C7 \u09AF\u09CB\u0997 \u0995\u09B0\u09C1\u09A8",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                OutlinedTextField(
                    value = subjectName,
                    onValueChange = { subjectName = it },
                    label = { Text("\u09AC\u09BF\u09B7\u09DF\u09C7\u09B0 \u09A8\u09BE\u09AE") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(shapes.inputFieldRadius),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Primary,
                    ),
                )
                Text(
                    text = "\u0995\u09BE\u09B0\u09CD\u09AF\u0995\u09B2\u09BE\u09AA: ${durationMinutes.toBengaliDigits()} \u09AE\u09BF\u09A8\u09BF\u099F",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    listOf(15, 25, 30, 45, 60).forEach { dur ->
                        GlassOutlinedCard(
                            onClick = { durationMinutes = dur },
                            cornerRadius = shapes.chipRadius,
                            padding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                            tint = if (durationMinutes == dur) Primary.copy(alpha = 0.12f) else null,
                        ) {
                            Text(
                                text = "${dur.toBengaliDigits()} \u09AE\u09BF\u09A8\u09BF\u099F",
                                style = MaterialTheme.typography.labelSmall,
                                fontFamily = EnglishFontFamily,
                                color = if (durationMinutes == dur) Primary
                                else MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onAutoSlot(subjectName, durationMinutes) },
                enabled = subjectName.isNotBlank(),
            ) {
                Text("\u0985\u099F\u09CB-\u09B8\u09CD\u09B2\u099F", color = Primary)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("\u09AC\u09BE\u09A4\u09BF\u09B2", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        },
        containerColor = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(shapes.dialogRadius),
    )
}

// ═══════════════════════════════════════════════════════════════════════════════
// Template Dialog
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun TemplateDialog(
    templates: List<RoutineTemplate>,
    onDismiss: () -> Unit,
    onApplyTemplate: (RoutineTemplate) -> Unit,
) {
    val shapes = LocalGlassShapes.current

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "\uD83D\uDCCB \u09B8\u09BE\u09AA\u09CD\u09A4\u09BE\u09B9\u09BF\u0995 \u099F\u09C7\u09AE\u09AA\u09CD\u09B2\u09C7\u099F",
                style = MaterialTheme.typography.titleMedium,
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "\u098F\u0995\u099F\u09BF \u099F\u09C7\u09AE\u09AA\u09CD\u09B2\u09C7\u099F \u09A8\u09BF\u09B0\u09CD\u09AC\u09BE\u099A\u09A8 \u0995\u09B0\u09B2\u09C7 \u09AC\u09B0\u09CD\u09A4\u09AE\u09BE\u09A8 \u09B0\u09C1\u099F\u09BF\u09A8 \u09AE\u09C1\u099B\u09C7 \u09AF\u09BE\u09AC\u09C7",
                    style = MaterialTheme.typography.bodySmall,
                    color = Warning,
                )
                templates.forEach { template ->
                    GlassOutlinedCard(
                        onClick = { onApplyTemplate(template) },
                        cornerRadius = shapes.cardRadiusSmall,
                        padding = PaddingValues(16.dp),
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                if (template.isBuiltIn) {
                                    Text(
                                        text = "\u2B50",
                                        fontSize = 14.sp,
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                }
                                Text(
                                    text = template.name,
                                    style = MaterialTheme.typography.titleSmall,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontWeight = FontWeight.Bold,
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                Text(
                                    text = "${template.routines.size.toBengaliDigits()} \u099F\u09BF",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontFamily = EnglishFontFamily,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                            }
                            // Preview subjects
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                template.routines
                                    .map { it.subjectName }
                                    .distinct()
                                    .take(4)
                                    .forEach { subject ->
                                        GlassFilledCard(
                                            cornerRadius = shapes.chipRadius,
                                            padding = PaddingValues(horizontal = 8.dp, vertical = 3.dp),
                                            tint = Primary.copy(alpha = 0.08f),
                                        ) {
                                            Text(
                                                text = subject,
                                                style = MaterialTheme.typography.labelSmall,
                                                color = Primary,
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis,
                                            )
                                        }
                                    }
                                if (template.routines.map { it.subjectName }.distinct().size > 4) {
                                    Text(
                                        text = "+${(template.routines.map { it.subjectName }.distinct().size - 4).toBengaliDigits()}",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    )
                                }
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {},
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("\u09AC\u09A8\u09CD\u09A7", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        },
        containerColor = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(shapes.dialogRadius),
    )
}

// ═══════════════════════════════════════════════════════════════════════════════
// Helper: get activity color for a routine
// ═══════════════════════════════════════════════════════════════════════════════

private fun getActivityColorForRoutine(routine: Routine, isExamDay: Boolean): Color {
    if (isExamDay) {
        return Primary
    }
    return SubjectPalette.colorForIndex(
        routine.subjectName.hashCode().mod(SubjectPalette.colors.size)
    )
}