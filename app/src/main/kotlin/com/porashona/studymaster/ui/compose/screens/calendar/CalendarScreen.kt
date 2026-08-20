
package com.porashona.studymaster.ui.compose.screens.calendar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CalendarViewDay
import androidx.compose.material.icons.filled.CalendarViewWeek
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.FileUpload
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Today
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.porashona.studymaster.data.model.AcademicEvent
import com.porashona.studymaster.data.model.EventType
import com.porashona.studymaster.data.model.Exam
import com.porashona.studymaster.data.model.Routine
import com.porashona.studymaster.data.model.StudySession
import com.porashona.studymaster.data.model.Task
import com.porashona.studymaster.ui.compose.components.GlassCardVariant
import com.porashona.studymaster.ui.compose.components.GlassmorphicCard
import com.porashona.studymaster.ui.compose.theme.*
import com.porashona.studymaster.ui.compose.viewmodels.CalendarDayData
import com.porashona.studymaster.ui.compose.viewmodels.CalendarViewModel
import com.porashona.studymaster.ui.compose.viewmodels.DayEvents
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

// ═══════════════════════════════════════════════════════════════════════════════
// CalendarScreen — Full calendar with month/week views, event details, and
// study suggestions. All text in Bengali. Glassmorphic cards. Material 3.
// ═══════════════════════════════════════════════════════════════════════════════

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(
    viewModel: CalendarViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit = {},
) {
    val monthData by viewModel.calendarMonthData.collectAsState()
    val weekData by viewModel.calendarWeekData.collectAsState()
    val selectedDate by viewModel.selectedDate.collectAsState()
    val viewingMonth by viewModel.viewingMonth.collectAsState()
    val dayEvents by viewModel.eventsForDate.collectAsState()

    var viewMode by rememberSaveable { mutableStateOf("month") } // "month" or "week"
    var showAddEventDialog by remember { mutableStateOf(false) }
    var showSuggestionPanel by remember { mutableStateOf(false) }

    val bengaliMonths = listOf("জানুয়ারি", "ফেব্রুয়ারি", "মার্চ", "এপ্রিল", "মে", "জুন", "জুলাই", "আগস্ট", "সেপ্টেম্বর", "অক্টোবর", "নভেম্বর", "ডিসেম্বর")
    val bengaliDays = listOf("রবি", "সোম", "মঙ্গল", "বুধ", "বৃহ", "শুক্র", "শনি")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("ক্যালেন্ডার", style = MaterialTheme.typography.headlineSmall) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "ফিরুন")
                    }
                },
                actions = {
                    // Google Calendar sync button
                    IconButton(onClick = { viewModel.syncWithGoogleCalendar() }) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.CloudOff, contentDescription = "Google Calendar সিঙ্ক", modifier = Modifier.size(18.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text("শীঘ্রই", fontSize = 8.sp, color = Warning)
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background),
            )
        },
        floatingActionButton = {
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                // Study suggestion FAB
                FloatingActionButton(
                    onClick = { showSuggestionPanel = !showSuggestionPanel },
                    containerColor = StreakFire,
                    modifier = Modifier.size(48.dp),
                ) {
                    Icon(Icons.Default.Lightbulb, contentDescription = "অধ্যয়ন পরামর্শ", modifier = Modifier.size(20.dp))
                }

                // Add event FAB
                FloatingActionButton(
                    onClick = { showAddEventDialog = true },
                    containerColor = Primary,
                ) {
                    Icon(Icons.Default.Add, contentDescription = "ইভেন্ট যোগ করুন")
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
        ) {
            // ── Month navigation ──────────────────────────────────────────────
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                IconButton(onClick = { viewModel.navigateMonth(-1) }) {
                    Icon(Icons.Default.ChevronLeft, contentDescription = "আগের মাস")
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        "${bengaliMonths[viewingMonth.get(Calendar.MONTH)]} ${viewingMonth.get(Calendar.YEAR)}",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                    )
                }
                Row {
                    IconButton(onClick = { viewModel.goToday() }) {
                        Icon(Icons.Default.Today, contentDescription = "আজ")
                    }
                    IconButton(onClick = { viewModel.navigateMonth(1) }) {
                        Icon(Icons.Default.ChevronRight, contentDescription = "পরের মাস")
                    }
                }
            }

            // ── View toggle ───────────────────────────────────────────────────
            SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)) {
                listOf("month" to "মাস", "week" to "সপ্তাহ").forEachIndexed { index, (key, label) ->
                    SegmentedButton(
                        selected = viewMode == key,
                        onClick = { viewMode = key },
                        shape = SegmentedButtonDefaults.itemShape(index, 2),
                    ) {
                        Icon(
                            if (key == "month") Icons.Default.CalendarMonth else Icons.Default.CalendarViewWeek,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(label)
                    }
                }
            }

            // ── Study Suggestion Panel ────────────────────────────────────────
            AnimatedVisibility(visible = showSuggestionPanel, enter = expandVertically(), exit = shrinkVertically()) {
                GlassmorphicCard(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                    variant = GlassCardVariant.FILLED,
                    tint = StreakFire,
                    padding = 14.dp,
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Lightbulb, contentDescription = null, tint = StreakFire, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("এখন কী পড়বেন?", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = StreakFire)
                        }
                        val suggestions = listOf(
                            "পরীক্ষার কাউন্টডাউন অনুযায়ী অগ্রাধিকার বিষয় পড়ুন",
                            "আজকের রুটিন অনুযায়ী পরবর্তী সেশন শুরু করুন",
                            "দুর্বল বিষয়ে অধিক সময় দিন — পরিসংখ্যান অনুযায়ী",
                            "MCQ অনুশীলন দিয়ে পড়া শেষ করুন",
                        )
                        val dayOfWeek = (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) + 5) % 7
                        val suggestion = suggestions[dayOfWeek % suggestions.size]
                        Text(suggestion, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }

            // ── Calendar Grid ─────────────────────────────────────────────────
            if (viewMode == "month") {
                // Day headers
                Row(modifier = Modifier.fillMaxWidth()) {
                    bengaliDays.forEach { day ->
                        Text(
                            day,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.weight(1f),
                        )
                    }
                }

                // Day grid
                LazyVerticalGrid(
                    columns = GridCells.Fixed(7),
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(2.dp),
                    verticalArrangement = Arrangement.spacedBy(2.dp),
                ) {
                    items(monthData, key = { "${it.year}_${it.month}_${it.dayOfMonth}" }) { day ->
                        CalendarDayCell(
                            dayData = day,
                            isSelected = day.dayOfMonth == selectedDate.get(Calendar.DAY_OF_MONTH)
                                    && day.month == selectedDate.get(Calendar.MONTH)
                                    && day.year == selectedDate.get(Calendar.YEAR),
                            onClick = {
                                val cal = Calendar.getInstance().apply {
                                    set(Calendar.YEAR, day.year)
                                    set(Calendar.MONTH, day.month)
                                    set(Calendar.DAY_OF_MONTH, day.dayOfMonth)
                                }
                                viewModel.selectDate(cal)
                            },
                        )
                    }

                    // Event dots legend
                    item(span = { GridItemSpan(7) }) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                        ) {
                            LegendDot(color = ActivityTagColors.StudyColor, label = "অধ্যয়ন")
                            LegendDot(color = ActivityTagColors.ExamColor, label = "পরীক্ষা")
                            LegendDot(color = ActivityTagColors.AssignmentColor, label = "টাস্ক")
                            LegendDot(color = ActivityTagColors.RevisionColor, label = "রুটিন")
                        }
                    }
                }
            } else {
                // Week view
                Row(modifier = Modifier.fillMaxWidth()) {
                    bengaliDays.forEachIndexed { index, day ->
                        val dayData = weekData.getOrNull(index)
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(12.dp))
                                .then(
                                    if (dayData?.isToday == true) Modifier.background(Primary.copy(alpha = 0.15f))
                                    else Modifier
                                )
                                .clickable {
                                    if (dayData != null) viewModel.selectDate(dayData.timestamp)
                                }
                                .padding(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(day, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text(
                                dayData?.dayOfMonth?.toString() ?: "",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = if (dayData?.isToday == true) FontWeight.Bold else FontWeight.Normal,
                                color = if (dayData?.isToday == true) Primary else MaterialTheme.colorScheme.onSurface,
                            )
                            // Event dots
                            if (dayData != null) {
                                Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                                    if (dayData.hasStudySession) Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(ActivityTagColors.StudyColor))
                                    if (dayData.hasExam) Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(ActivityTagColors.ExamColor))
                                    if (dayData.hasTask) Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(ActivityTagColors.AssignmentColor))
                                    if (dayData.hasEvent) Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(ActivityTagColors.RevisionColor))
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

            // ── Day Detail Panel ──────────────────────────────────────────────
            Text(
                "নির্বাচিত দিনের ইভেন্ট",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp),
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize(),
            ) {
                // Study sessions
                if (dayEvents.sessions.isNotEmpty()) {
                    item {
                        SectionLabel("অধ্যয়ন সেশন", dayEvents.sessions.size, ActivityTagColors.StudyColor)
                    }
                    items(dayEvents.sessions, key = { it.id }) { session ->
                        EventCard(
                            title = session.subjectName ?: "অধ্যয়ন",
                            subtitle = formatDuration(session.durationSeconds),
                            color = ActivityTagColors.StudyColor,
                            icon = Icons.Default.School,
                        )
                    }
                }

                // Exams
                if (dayEvents.exams.isNotEmpty()) {
                    item {
                        SectionLabel("পরীক্ষা", dayEvents.exams.size, ActivityTagColors.ExamColor)
                    }
                    items(dayEvents.exams, key = { it.id }) { exam ->
                        EventCard(
                            title = exam.subjectName ?: "পরীক্ষা",
                            subtitle = exam.examDate.toDateString(),
                            color = ActivityTagColors.ExamColor,
                            icon = Icons.Default.CalendarViewDay,
                            isExam = true,
                        )
                    }
                }

                // Tasks
                if (dayEvents.tasks.isNotEmpty()) {
                    item {
                        SectionLabel("টাস্ক", dayEvents.tasks.size, ActivityTagColors.AssignmentColor)
                    }
                    items(dayEvents.tasks, key = { it.id }) { task ->
                        EventCard(
                            title = task.title,
                            subtitle = if (task.isCompleted) "সম্পন্ন ✓" else "অসম্পন্ন",
                            color = if (task.isCompleted) Success else ActivityTagColors.AssignmentColor,
                            icon = Icons.Default.Event,
                        )
                    }
                }

                // Academic events
                if (dayEvents.academicEvents.isNotEmpty()) {
                    item {
                        SectionLabel("একাডেমিক ইভেন্ট", dayEvents.academicEvents.size, Chart1)
                    }
                    items(dayEvents.academicEvents, key = { it.id }) { event ->
                        EventCard(
                            title = event.title,
                            subtitle = event.description.ifBlank { eventTypeToBn(event.eventType) },
                            color = event.color.toComposeColor(),
                            icon = Icons.Default.Event,
                        )
                    }
                }

                // Routines
                if (dayEvents.routines.isNotEmpty()) {
                    item {
                        SectionLabel("রুটিন", dayEvents.routines.size, ActivityTagColors.RevisionColor)
                    }
                    items(dayEvents.routines, key = { it.id }) { routine ->
                        EventCard(
                            title = routine.subjectName ?: "রুটিন",
                            subtitle = "${routine.startTime} - ${routine.endTime}",
                            color = ActivityTagColors.RevisionColor,
                            icon = Icons.Default.CalendarViewWeek,
                        )
                    }
                }

                // Import PDF routine button
                item {
                    OutlinedButton(
                        onClick = { /* In production: launch PDF picker */ },
                        modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                    ) {
                        Icon(Icons.Default.FileUpload, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("কোচিং PDF রুটিন ইম্পোর্ট করুন")
                    }
                }

                // Empty state
                if (dayEvents.sessions.isEmpty() && dayEvents.exams.isEmpty() && dayEvents.tasks.isEmpty() && dayEvents.academicEvents.isEmpty() && dayEvents.routines.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                "এই দিনে কোনো ইভেন্ট নেই",
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        }
                    }
                }

                item { Spacer(modifier = Modifier.height(80.dp)) }
            }
        }
    }

    // ── Add Event Dialog ──────────────────────────────────────────────────
    if (showAddEventDialog) {
        AddEventDialog(
            selectedDate = selectedDate,
            onDismiss = { showAddEventDialog = false },
            onConfirm = { title, description, eventType ->
                val cal = selectedDate
                val event = com.porashona.studymaster.data.model.AcademicEvent(
                    title = title,
                    description = description,
                    eventType = eventType,
                    date = cal.timeInMillis,
                )
                viewModel.addEvent(event)
                showAddEventDialog = false
            },
        )
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Calendar Day Cell
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun CalendarDayCell(
    dayData: CalendarDayData,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    val hasAnyEvent = dayData.hasStudySession || dayData.hasExam || dayData.hasEvent || dayData.hasTask

    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(8.dp))
            .then(
                if (isSelected) Modifier.background(Primary.copy(alpha = 0.15f)).border(1.5.dp, Primary, RoundedCornerShape(8.dp))
                else if (dayData.isToday) Modifier.border(1.5.dp, Secondary, RoundedCornerShape(8.dp))
                else Modifier
            )
            .clickable(onClick = onClick)
            .padding(2.dp),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            Text(
                dayData.dayOfMonth.toString(),
                style = MaterialTheme.typography.bodySmall,
                fontWeight = if (dayData.isToday || isSelected) FontWeight.Bold else FontWeight.Normal,
                color = when {
                    isSelected -> Primary
                    dayData.isToday -> Secondary
                    !dayData.isCurrentMonth -> MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)
                    else -> MaterialTheme.colorScheme.onSurface
                },
            )
            // Event dots
            if (hasAnyEvent && dayData.isCurrentMonth) {
                Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                    if (dayData.hasExam) {
                        // Exam day indicator - special icon
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(ActivityTagColors.ExamColor),
                        )
                    }
                    if (dayData.hasStudySession) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(ActivityTagColors.StudyColor),
                        )
                    }
                    if (dayData.hasTask) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(ActivityTagColors.AssignmentColor),
                        )
                    }
                    if (dayData.hasEvent) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(ActivityTagColors.RevisionColor),
                        )
                    }
                }
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Event Card
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun EventCard(
    title: String,
    subtitle: String,
    color: Color,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isExam: Boolean = false,
) {
    GlassmorphicCard(
        modifier = Modifier.fillMaxWidth(),
        variant = GlassCardVariant.OUTLINED,
        padding = 12.dp,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(color.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center,
            ) {
                if (isExam) {
                    Text("📝", fontSize = 16.sp)
                } else {
                    Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(18.dp))
                }
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(title, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Text(subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 1, overflow = TextOverflow.Ellipsis)
            }
        }
    }
}

@Composable
private fun SectionLabel(title: String, count: Int, color: Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp),
    ) {
        Box(modifier = Modifier.size(8.dp).clip(RoundedCornerShape(2.dp)).background(color))
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            "$title (${count.toBengaliDigits()})",
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            color = color,
        )
    }
}

@Composable
private fun LegendDot(color: Color, label: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(color))
        Spacer(modifier = Modifier.width(4.dp))
        Text(label, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Add Event Dialog
// ═══════════════════════════════════════════════════════════════════════════════

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddEventDialog(
    selectedDate: Calendar,
    onDismiss: () -> Unit,
    onConfirm: (String, String, EventType) -> Unit,
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedEventType by remember { mutableStateOf("OTHER") }
    var expanded by remember { mutableStateOf(false) }

    val eventTypes = listOf(
        "EXAM" to "পরীক্ষা",
        "ASSIGNMENT_DUE" to "অ্যাসাইনমেন্ট",
        "CLASS" to "ক্লাস",
        "HOLIDAY" to "ছুটি",
        "OTHER" to "অন্যান্য",
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("ইভেন্ট যোগ করুন") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(
                    SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(selectedDate.time),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )

                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("শিরোনাম") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                )

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("বিবরণ") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    minLines = 2,
                )

                // Event type dropdown
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded },
                ) {
                    OutlinedTextField(
                        value = eventTypes.firstOrNull { it.first == selectedEventType }?.second ?: "",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("ইভেন্ট টাইপ") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = Modifier.fillMaxWidth().menuAnchor(MenuAnchorType.PrimaryNotEditable),
                        shape = RoundedCornerShape(12.dp),
                    )
                    ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                        eventTypes.forEach { (type, label) ->
                            DropdownMenuItem(
                                text = { Text(label) },
                                onClick = { selectedEventType = type; expanded = false },
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { onConfirm(title, description, EventType.valueOf(selectedEventType)) },
                enabled = title.isNotBlank(),
                colors = ButtonDefaults.buttonColors(containerColor = Primary),
            ) {
                Text("যোগ করুন")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("বাতিল") }
        },
    )
}

// ═══════════════════════════════════════════════════════════════════════════════
// Helpers
// ═══════════════════════════════════════════════════════════════════════════════

private fun formatDuration(seconds: Long): String {
    val hours = seconds / 3600
    val minutes = (seconds % 3600) / 60
    return when {
        hours > 0 -> "${hours.toBengaliDigits()} ঘণ্টা ${minutes.toBengaliDigits()} মিনিট"
        minutes > 0 -> "${minutes.toBengaliDigits()} মিনিট"
        else -> "${seconds.toBengaliDigits()} সেকেন্ড"
    }
}

private fun eventTypeToBn(type: EventType): String = when (type) {
    EventType.EXAM -> "পরীক্ষা"
    EventType.ASSIGNMENT_DUE -> "অ্যাসাইনমেন্ট"
    EventType.CLASS -> "ক্লাস"
    EventType.HOLIDAY -> "ছুটি"
    EventType.SEMESTER_START -> "সেমিস্টার শুরু"
    EventType.SEMESTER_END -> "সেমিস্টার শেষ"
    EventType.RESULT -> "ফলাফল"
    EventType.OTHER -> "অন্যান্য"
}
