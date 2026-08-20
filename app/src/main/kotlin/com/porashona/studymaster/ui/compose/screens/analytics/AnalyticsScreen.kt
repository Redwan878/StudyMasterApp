/*
package com.porashona.studymaster.ui.compose.screens.analytics

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.porashona.studymaster.ui.compose.components.CircularProgress
import com.porashona.studymaster.ui.compose.components.GlassCardVariant
import com.porashona.studymaster.ui.compose.components.GlassElevatedCard
import com.porashona.studymaster.ui.compose.components.GlassmorphicCard
import com.porashona.studymaster.ui.compose.components.LocalGlassShapes
import com.porashona.studymaster.ui.compose.components.LoadingAnimation
import com.porashona.studymaster.ui.compose.theme.*
import com.porashona.studymaster.ui.compose.viewmodels.AnalyticsViewModel
import com.porashona.studymaster.ui.compose.viewmodels.HeatmapDay
import com.porashona.studymaster.ui.compose.viewmodels.PredictedGrade
import com.porashona.studymaster.ui.compose.viewmodels.SubjectTimeEntry
import com.porashona.studymaster.ui.compose.viewmodels.WeekComparison
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt

// ═══════════════════════════════════════════════════════════════════════════════
// AnalyticsScreen — Full analytics dashboard with Bengali UI
// ═══════════════════════════════════════════════════════════════════════════════

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalyticsScreen(
    viewModel: AnalyticsViewModel = hiltViewModel(),
) {
    val timePerSubject by viewModel.timePerSubject.collectAsState()
    val streakCalendar by viewModel.streakCalendar.collectAsState()
    val scoreTrends by viewModel.scoreTrends.collectAsState()
    val predictedGrades by viewModel.predictedGrade.collectAsState()
    val weeklyReport by viewModel.weeklyReport.collectAsState()
    val weekComparison by viewModel.lastWeekComparison.collectAsState()
    val totalStudyTime by viewModel.totalStudyTime.collectAsState()
    val totalSessions by viewModel.totalSessions.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    var selectedPeriod by remember { mutableIntStateOf(0) }

    val periodLabels = listOf("এই সপ্তাহ", "এই মাস", "সব সময়")

    LaunchedEffect(Unit) {
        viewModel.loadAnalytics()
        viewModel.generateWeeklyReport()
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "বিশ্লেষণ",
                        style = MaterialTheme.typography.titleLarge,
                    )
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.BarChart,
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                            .padding(start = 16.dp),
                        tint = MaterialTheme.colorScheme.onSurface,
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                ),
                scrollBehavior = scrollBehavior,
            )
        },
    ) { innerPadding ->
        if (isLoading) {
            LoadingAnimation(modifier = Modifier.padding(innerPadding))
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                // 1. Period Selector
                item {
                    PeriodSelector(
                        labels = periodLabels,
                        selectedIndex = selectedPeriod,
                        onSelect = { selectedPeriod = it },
                    )
                }

                // 2. Time per Subject Breakdown
                item {
                    val displayData = remember(timePerSubject, selectedPeriod) {
                        when (selectedPeriod) {
                            0 -> timePerSubject.take(5)
                            1 -> timePerSubject
                            2 -> timePerSubject
                            else -> timePerSubject
                        }.ifEmpty { mockSubjectTimeData }
                    }
                    TimePerSubjectCard(
                        data = displayData,
                        totalHours = displayData.sumOf { it.hours.toDouble() }.toFloat(),
                    )
                }

                // 3. Weak Chapter Heatmap
                item {
                    WeakChapterHeatmapCard(
                        onChapterClick = { chapter ->
                            // Handled inside
                        }
                    )
                }

                // 4. GitHub-style Streak Calendar
                item {
                    StreakCalendarCard(
                        heatmapData = streakCalendar.ifEmpty { generateMockCalendarData() },
                    )
                }

                // 5. Score Trend Graph
                item {
                    ScoreTrendCard(
                        scoreTrends = scoreTrends.ifEmpty { mockScoreTrendData },
                    )
                }

                // 6. Predicted Grade Estimator
                item {
                    PredictedGradeCard(
                        grades = predictedGrades.ifEmpty { mockPredictedGrades },
                    )
                }

                // 7. Weekly Progress Report
                item {
                    WeeklyProgressCard(
                        weekComparison = weekComparison,
                        totalStudySeconds = totalStudyTime,
                        totalSessions = totalSessions,
                    )
                }

                // 8. Motivational Nudge Card
                item {
                    MotivationalNudgeCard()
                }

                item { Spacer(modifier = Modifier.height(32.dp)) }
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// 1. Period Selector
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun PeriodSelector(
    labels: List<String>,
    selectedIndex: Int,
    onSelect: (Int) -> Unit,
) {
    val isDark = MaterialTheme.colorScheme.isDark
    val shapes = LocalGlassShapes.current

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        labels.forEachIndexed { index, label ->
            val isSelected = index == selectedIndex
            val bgColor by animateColorAsState(
                targetValue = if (isSelected) Primary else (if (isDark) DarkSurfaceVariant else LightSurfaceVariant),
                animationSpec = tween(300), label = "periodBg",
            )
            val textColor by animateColorAsState(
                targetValue = if (isSelected) OnPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                animationSpec = tween(300), label = "periodText",
            )

            Surface(
                modifier = Modifier
                    .weight(1f)
                    .clickable { onSelect(index) },
                shape = RoundedCornerShape(shapes.chipRadius),
                color = bgColor,
                contentColor = textColor,
            ) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                    ),
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                )
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// 2. Time per Subject — Horizontal Bar Chart (Canvas-drawn)
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun TimePerSubjectCard(
    data: List<SubjectTimeEntry>,
    totalHours: Float,
) {
    val maxHours = remember(data) {
        data.maxOfOrNull { it.hours } ?: 1f
    }

    GlassElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        padding = 20.dp,
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "বিষয় অনুযায়ী সময়",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    text = "মোট: ${totalHours.toBengaliDigits()} ঘণ্টা",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontFamily = EnglishFontFamily,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    ),
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            data.forEachIndexed { index, entry ->
                val color = SubjectPalette.colorForIndex(index)
                val barFraction = remember(entry, maxHours) {
                    if (maxHours <= 0f) 0f else (entry.hours / maxHours).coerceIn(0f, 1f)
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    // Color dot
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .background(color, CircleShape),
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    // Subject name
                    Text(
                        text = entry.subjectName,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.width(100.dp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    // Canvas-drawn horizontal bar
                    Canvas(
                        modifier = Modifier
                            .weight(1f)
                            .height(20.dp),
                    ) {
                        val barHeight = 16.dp.toPx()
                        val cornerRadius = 8.dp.toPx()
                        val barWidth = size.width * barFraction

                        // Track
                        drawRoundRect(
                            color = MaterialTheme.colorScheme.surfaceVariant.toArgb(),
                            size = Size(size.width, barHeight),
                            cornerRadius = CornerRadius(cornerRadius, cornerRadius),
                            topLeft = Offset(0f, (size.height - barHeight) / 2f),
                        )

                        // Fill
                        if (barWidth > 0f) {
                            drawRoundRect(
                                color = color.toArgb(),
                                size = Size(barWidth, barHeight),
                                cornerRadius = CornerRadius(cornerRadius, cornerRadius),
                                topLeft = Offset(0f, (size.height - barHeight) / 2f),
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    // Percentage + time
                    val percentage = if (totalHours > 0f) ((entry.hours / totalHours) * 100f) else 0f
                    Text(
                        text = "${percentage.roundToInt().toBengaliDigits()}%",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontFamily = EnglishFontFamily,
                            fontWeight = FontWeight.Bold,
                            color = color,
                        ),
                    )
                }
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// 3. Weak Chapter Heatmap — Grid colored by performance
// ═══════════════════════════════════════════════════════════════════════════════

private data class MockChapter(
    val name: String,
    val subject: String,
    val completionPercent: Float,
)

private val mockWeakChapters = listOf(
    MockChapter("অধ্যায় ১: বীজগণিত", "গণিত", 0.25f),
    MockChapter("অধ্যায় ৩: জ্যামিতি", "গণিত", 0.40f),
    MockChapter("অধ্যায় ৫: ত্রিকোণমিতি", "গণিত", 0.15f),
    MockChapter("অধ্যায় ২: গতিবিদ্যা", "পদার্থবিজ্ঞান", 0.55f),
    MockChapter("অধ্যায় ৪: তাপবিদ্যা", "পদার্থবিজ্ঞান", 0.30f),
    MockChapter("অধ্যায় ১: জৈব রসায়ন", "রসায়ন", 0.65f),
    MockChapter("অধ্যায় ৩: ধাতুবিদ্যা", "রসায়ন", 0.20f),
    MockChapter("অধ্যায় ২: কোষ বিভাজন", "জীববিজ্ঞান", 0.80f),
    MockChapter("অধ্যায় ৫: জিনতত্ত্ব", "জীববিজ্ঞান", 0.35f),
    MockChapter("অধ্যায় ১: বাক্যতত্ত্ব", "বাংলা", 0.70f),
    MockChapter("অধ্যায় ৪: প্রবন্ধ রচনা", "বাংলা", 0.45f),
    MockChapter("অধ্যায় ৩: অপশন", "ইংরেজি", 0.50f),
)

@Composable
private fun WeakChapterHeatmapCard(
    onChapterClick: (MockChapter) -> Unit,
) {
    var selectedChapter by remember { mutableStateOf<MockChapter?>(null) }

    GlassElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        padding = 20.dp,
    ) {
        Column {
            Text(
                text = "দুর্বল অধ্যায়ের মানচিত্র",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "লাল = দুর্বল  |  হলুদ = মাঝারি  |  সবুজ = শক্তিশালী",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            Spacer(modifier = Modifier.height(12.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.height(320.dp),
            ) {
                items(mockWeakChapters) { chapter ->
                    val cellColor = performanceColor(chapter.completionPercent)
                    val isDark = MaterialTheme.colorScheme.isDark

                    Surface(
                        modifier = Modifier
                            .clickable { selectedChapter = chapter },
                        shape = RoundedCornerShape(12.dp),
                        color = cellColor.copy(alpha = if (isDark) 0.25f else 0.18f),
                        contentColor = cellColor,
                    ) {
                        Column(
                            modifier = Modifier.padding(8.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                        ) {
                            Text(
                                text = chapter.name,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    color = cellColor,
                                ),
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                lineHeight = 14.sp,
                            )
                            Text(
                                text = "${(chapter.completionPercent * 100).roundToInt().toBengaliDigits()}%",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontFamily = EnglishFontFamily,
                                    fontWeight = FontWeight.Bold,
                                ),
                            )
                        }
                    }
                }
            }
        }
    }

    // Chapter detail dialog
    selectedChapter?.let { chapter ->
        AlertDialog(
            onDismissRequest = { selectedChapter = null },
            shape = RoundedCornerShape(LocalGlassShapes.current.dialogRadius),
            containerColor = if (MaterialTheme.colorScheme.isDark) GlassDarkAlpha60 else GlassLightAlpha90,
            title = {
                Text(
                    text = chapter.name,
                    style = MaterialTheme.typography.titleMedium,
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "বিষয়: ${chapter.subject}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Text(
                        text = "সম্পন্নতা: ${(chapter.completionPercent * 100).roundToInt().toBengaliDigits()}%",
                        style = MaterialTheme.typography.bodyMedium,
                        color = performanceColor(chapter.completionPercent),
                    )
                    CircularProgress(
                        progress = chapter.completionPercent,
                        modifier = Modifier
                            .size(80.dp)
                            .align(Alignment.CenterHorizontally),
                        strokeWidth = 6.dp,
                        progressColor = performanceColor(chapter.completionPercent),
                    ) {
                        Text(
                            text = "${(chapter.completionPercent * 100).roundToInt().toBengaliDigits()}%",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontFamily = EnglishFontFamily,
                                fontWeight = FontWeight.Bold,
                            ),
                            color = performanceColor(chapter.completionPercent),
                        )
                    }
                    Text(
                        text = when {
                            chapter.completionPercent < 0.3f -> "এই অধ্যায়ে দ্রুত মনোযোগ দিন। ফ্ল্যাশকার্ড তৈরি করুন।"
                            chapter.completionPercent < 0.6f -> "ভালো অগ্রগতি হচ্ছে। আরও প্র্যাকটিস করুন।"
                            else -> "চমৎকার! এই অধ্যায়ে রিভিশন চালিয়ে যান।"
                        },
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = { selectedChapter = null }) {
                    Text("ঠিক আছে", fontWeight = FontWeight.SemiBold)
                }
            },
        )
    }
}

private fun performanceColor(completion: Float): Color = when {
    completion >= 0.7f -> Success
    completion >= 0.4f -> Warning
    else -> Error
}

// ═══════════════════════════════════════════════════════════════════════════════
// 4. GitHub-style Contribution/Streak Calendar Grid — 12 weeks × 7 days
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun StreakCalendarCard(
    heatmapData: List<HeatmapDay>,
) {
    val isDark = MaterialTheme.colorScheme.isDark
    val surfaceColor = if (isDark) DarkSurfaceVariant else Color(0xFFE8E8E8)

    val totalWeeks = 12
    val dayLabels = listOf("সোম", "মঙ্গল", "বুধ", "বৃহ", "শুক্র", "শনি", "রবি")

    // Build the 12×7 grid from data
    val gridData = remember(heatmapData) {
        val dataMap = heatmapData.associateBy { it.date }
        val cal = Calendar.getInstance().apply {
            set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
            add(Calendar.WEEK_OF_YEAR, -(totalWeeks - 1))
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val fmt = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        buildList {
            for (week in 0 until totalWeeks) {
                val weekDays = mutableListOf<Long>()
                for (day in 0 until 7) {
                    val dateStr = fmt.format(cal.time)
                    weekDays.add(dataMap[dateStr]?.studyMinutes ?: 0L)
                    cal.add(Calendar.DAY_OF_MONTH, 1)
                }
                add(weekDays)
            }
        }
    }

    GlassElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        padding = 20.dp,
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "অধ্যয়ন ক্যালেন্ডার",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text("কম", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    listOf(0L, 15L, 45L, 90L, 150L).forEach { minutes ->
                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .clip(RoundedCornerShape(3.dp))
                                .background(colorForMinutes(minutes, surfaceColor)),
                        )
                    }
                    Text("বেশি", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Draw the grid using Canvas for efficiency
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
            ) {
                val cellSpacingPx = 3.dp.toPx()
                val labelWidthPx = 36.dp.toPx()
                val availableWidth = size.width - labelWidthPx
                val cellSize = ((availableWidth - (cellSpacingPx * (totalWeeks - 1))) / totalWeeks)
                    .coerceAtMost(20.dp.toPx())
                val totalGridWidth = (cellSize * totalWeeks) + (cellSpacingPx * (totalWeeks - 1))
                val startX = labelWidthPx + (availableWidth - totalGridWidth) / 2f
                val startY = 0f

                // Draw day labels
                val labelPaint = android.graphics.Paint().apply {
                    color = MaterialTheme.colorScheme.onSurfaceVariant.toArgb()
                    textSize = 10.dp.toPx()
                    typeface = android.graphics.Typeface.DEFAULT
                    isAntiAlias = true
                    textAlign = android.graphics.Paint.Align.RIGHT
                }

                dayLabels.forEachIndexed { index, label ->
                    val y = startY + index * (cellSize + cellSpacingPx) + cellSize / 2f
                    drawContext.canvas.nativeCanvas.drawText(
                        label,
                        labelWidthPx - 4.dp.toPx(),
                        y + (labelPaint.textSize / 3f),
                        labelPaint,
                    )
                }

                // Draw grid cells
                gridData.forEachIndexed { weekIdx, weekDays ->
                    weekDays.forEachIndexed { dayIdx, minutes ->
                        val x = startX + weekIdx * (cellSize + cellSpacingPx)
                        val y = startY + dayIdx * (cellSize + cellSpacingPx)
                        val color = colorForMinutes(minutes, surfaceColor)

                        drawRoundRect(
                            color = color.toArgb(),
                            size = androidx.compose.ui.geometry.Size(cellSize, cellSize),
                            cornerRadius = CornerRadius(3.dp.toPx(), 3.dp.toPx()),
                            topLeft = Offset(x, y),
                        )
                    }
                }
            }
        }
    }
}

private fun colorForMinutes(minutes: Long, surfaceColor: Color): Color = when {
    minutes == 0L -> surfaceColor
    minutes <= 30L -> XpBarFill.copy(alpha = 0.2f)
    minutes <= 60L -> XpBarFill.copy(alpha = 0.45f)
    minutes <= 120L -> XpBarFill.copy(alpha = 0.7f)
    else -> XpBarFill
}

// ═══════════════════════════════════════════════════════════════════════════════
// 5. Score Trend Graph — Line chart (Canvas-drawn)
// ═══════════════════════════════════════════════════════════════════════════════

private data class ScoreTrendPoint(
    val label: String,
    val score: Float,
)

private val mockScoreTrendData = listOf(
    ScoreTrendPoint("টেস্ট ১", 45f),
    ScoreTrendPoint("টেস্ট ২", 52f),
    ScoreTrendPoint("টেস্ট ৩", 48f),
    ScoreTrendPoint("টেস্ট ৪", 61f),
    ScoreTrendPoint("টেস্ট ৫", 58f),
    ScoreTrendPoint("টেস্ট ৬", 72f),
    ScoreTrendPoint("টেস্ট ৭", 68f),
    ScoreTrendPoint("টেস্ট ৮", 78f),
    ScoreTrendPoint("টেস্ট ৯", 75f),
    ScoreTrendPoint("টেস্ট ১০", 82f),
)

@Composable
private fun ScoreTrendCard(
    scoreTrends: List<com.porashona.studymaster.data.dao.PracticeTestDao.ScoreTrend>,
) {
    val points = remember(scoreTrends) {
        if (scoreTrends.isEmpty()) {
            mockScoreTrendData
        } else {
            scoreTrends.mapIndexed { index, trend ->
                ScoreTrendPoint("টেস্ট ${index.toBengaliDigits()}", trend.percentage.toFloat())
            }
        }
    }

    val passThreshold = 40f
    val average = remember(points) {
        if (points.isEmpty()) 0f else points.map { it.score }.average().toFloat()
    }

    GlassElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        padding = 20.dp,
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "স্কোর ট্রেন্ড",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "গড়: ${average.toBengaliDigits(1)}%",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontFamily = EnglishFontFamily,
                            color = Primary,
                            fontWeight = FontWeight.Bold,
                        ),
                    )
                    Text(
                        text = "পাস: ${passThreshold.toBengaliDigits(0)}%",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontFamily = EnglishFontFamily,
                            color = Warning,
                        ),
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
            ) {
                if (points.isEmpty()) return@Canvas

                val padding = 40.dp.toPx()
                val chartWidth = size.width - (padding * 2)
                val chartHeight = size.height - (padding * 1.5f)
                val maxScore = 100f
                val minScore = 0f
                val scoreRange = maxScore - minScore

                // Y-axis grid lines and labels
                val gridPaint = android.graphics.Paint().apply {
                    color = MaterialTheme.colorScheme.outlineVariant.toArgb()
                    textSize = 10.dp.toPx()
                    typeface = android.graphics.Typeface.DEFAULT
                    isAntiAlias = true
                    textAlign = android.graphics.Paint.Align.RIGHT
                }

                for (i in 0..4) {
                    val value = minScore + (scoreRange * i / 4f)
                    val y = size.height - padding - (chartHeight * i / 4f)

                    drawLine(
                        color = MaterialTheme.colorScheme.outlineVariant,
                        start = Offset(padding, y),
                        end = Offset(size.width - padding, y),
                        strokeWidth = 1f,
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(4.dp.toPx(), 4.dp.toPx())),
                    )

                    drawContext.canvas.nativeCanvas.drawText(
                        "${value.roundToInt()}",
                        padding - 8.dp.toPx(),
                        y + (gridPaint.textSize / 3f),
                        gridPaint,
                    )
                }

                // Pass threshold line
                val passY = size.height - padding - (chartHeight * (passThreshold - minScore) / scoreRange)
                drawLine(
                    color = Warning,
                    start = Offset(padding, passY),
                    end = Offset(size.width - padding, passY),
                    strokeWidth = 1.5f,
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(8.dp.toPx(), 4.dp.toPx())),
                )

                // Average line
                val avgY = size.height - padding - (chartHeight * (average - minScore) / scoreRange)
                drawLine(
                    color = Primary,
                    start = Offset(padding, avgY),
                    end = Offset(size.width - padding, avgY),
                    strokeWidth = 1.5f,
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(2.dp.toPx(), 4.dp.toPx())),
                )

                // Score line path
                val linePath = androidx.compose.ui.graphics.Path()
                val fillPath = androidx.compose.ui.graphics.Path()

                val xStep = if (points.size > 1) chartWidth / (points.size - 1) else 0f

                points.forEachIndexed { index, point ->
                    val x = padding + index * xStep
                    val y = size.height - padding - (chartHeight * (point.score - minScore) / scoreRange)

                    if (index == 0) {
                        linePath.moveTo(x, y)
                        fillPath.moveTo(x, size.height - padding)
                        fillPath.lineTo(x, y)
                    } else {
                        linePath.lineTo(x, y)
                        fillPath.lineTo(x, y)
                    }

                    // Draw point dot
                    val pointColor = if (point.score >= passThreshold) Primary else Error
                    drawCircle(
                        color = pointColor,
                        radius = 4.dp.toPx(),
                        center = Offset(x, y),
                    )
                    drawCircle(
                        color = MaterialTheme.colorScheme.surface.toArgb(),
                        radius = 2.dp.toPx(),
                        center = Offset(x, y),
                    )
                }

                fillPath.lineTo(padding + (points.size - 1) * xStep, size.height - padding)
                fillPath.close()

                // Gradient fill under line
                drawPath(
                    path = fillPath,
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Primary.copy(alpha = 0.2f),
                            Primary.copy(alpha = 0.02f),
                        ),
                        startY = 0f,
                        endY = size.height,
                    ),
                )

                // Draw the line
                drawPath(
                    path = linePath,
                    color = Primary,
                    style = Stroke(
                        width = 2.5.dp.toPx(),
                        cap = StrokeCap.Round,
                        join = StrokeJoin.Round,
                    ),
                )
            }

            // Legend
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Box(modifier = Modifier.size(12.dp).clip(RoundedCornerShape(2.dp)).background(Primary))
                    Text("স্কোর", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Box(modifier = Modifier.size(12.dp).clip(RoundedCornerShape(2.dp)).background(Primary.copy(alpha = 0.4f)))
                    Text("গড়", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Box(modifier = Modifier.size(12.dp).clip(RoundedCornerShape(2.dp)).background(Warning))
                    Text("পাস মান", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// 6. Predicted Grade Estimator
// ═══════════════════════════════════════════════════════════════════════════════

private val mockPredictedGrades = listOf(
    PredictedGrade("গণিত", "A- স্তরে", "A- (৩.৫০)", 0.72f, "ভালো যাচ্ছে। দুর্বল অধ্যায়ে ফোকাস করুন।"),
    PredictedGrade("পদার্থবিজ্ঞান", "A স্তরে", "A (৪.০০)", 0.81f, "চমৎকার! রিভিশন চালিয়ে যান।"),
    PredictedGrade("রসায়ন", "B স্তরে", "B (৩.০০)", 0.55f, "প্র্যাকটিস টেস্ট বেশি দিন। MCQ প্র্যাকটিস করুন।"),
    PredictedGrade("জীববিজ্ঞান", "A+ স্তরে", "A+ (৫.০০)", 0.88f, "চমৎকার! রিভিশন চালিয়ে যান।"),
    PredictedGrade("বাংলা", "A স্তরে", "A (৪.০০)", 0.76f, "ভালো যাচ্ছে। দুর্বল অধ্যায়ে ফোকাস করুন।"),
    PredictedGrade("ইংরেজি", "A- স্তরে", "A- (৩.৫০)", 0.65f, "এই বিষয়ে বেশি সময় দিন। রুটিনে যোগ করুন।"),
)

private val gradeColors: Map<String, Color> = mapOf(
    "A+" to Color(0xFF4CAF50),
    "A " to Primary,
    "A-" to Color(0xFF2196F3),
    "B " to Warning,
    "C " to Color(0xFFFF9800),
    "D " to Error,
    "F " to Color(0xFFF44336),
)

@Composable
private fun PredictedGradeCard(
    grades: List<PredictedGrade>,
) {
    val overallGpa = remember(grades) {
        grades.mapNotNull { grade ->
            grade.predictedGrade
                .substringAfter("(")
                .substringBefore(")")
                .trim()
                .toFloatOrNull()
        }.average().toFloat()
    }

    val avgConfidence = remember(grades) {
        if (grades.isEmpty()) 0f
        else grades.map { it.confidence }.average().toFloat()
    }

    GlassElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        padding = 20.dp,
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "প্রত্যাশিত গ্রেড",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "সামগ্রিক GPA",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Text(
                        text = overallGpa.toBengaliDigits(2),
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontFamily = EnglishFontFamily,
                            fontWeight = FontWeight.Bold,
                            color = Primary,
                        ),
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Confidence bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    text = "বিশ্বাসযোগ্যতা:",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(6.dp)
                        .clip(RoundedCornerShape(100.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(avgConfidence / 100f)
                            .height(6.dp)
                            .clip(RoundedCornerShape(100.dp))
                            .background(
                                if (avgConfidence > 70f) Success
                                else if (avgConfidence > 40f) Warning
                                else Error,
                            ),
                    )
                }
                Text(
                    text = "${avgConfidence.toBengaliDigits(0)}%",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontFamily = EnglishFontFamily,
                        fontWeight = FontWeight.Bold,
                        color = if (avgConfidence > 70f) Success
                        else if (avgConfidence > 40f) Warning
                        else Error,
                    ),
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            grades.forEach { grade ->
                val gradeKey = grade.predictedGrade.take(2)
                val gradeColor = gradeColors[gradeKey] ?: MaterialTheme.colorScheme.onSurface

                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 3.dp),
                    shape = RoundedCornerShape(10.dp),
                    color = gradeColor.copy(alpha = 0.08f),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = grade.subjectName,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface,
                                fontWeight = FontWeight.Medium,
                            )
                            Text(
                                text = grade.recommendation,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = grade.predictedGrade,
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontFamily = EnglishFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    color = gradeColor,
                                ),
                            )
                            Text(
                                text = grade.currentLevel,
                                style = MaterialTheme.typography.labelSmall,
                                color = gradeColor.copy(alpha = 0.7f),
                            )
                        }
                    }
                }
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// 7. Weekly Progress Report
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun WeeklyProgressCard(
    weekComparison: WeekComparison,
    totalStudySeconds: Long,
    totalSessions: Int,
) {
    val isDark = MaterialTheme.colorScheme.isDark
    val shapes = LocalGlassShapes.current

    val thisWeekHours = weekComparison.thisWeekMinutes / 60f
    val lastWeekHours = weekComparison.lastWeekMinutes / 60f
    val changePercent = weekComparison.changePercent
    val isImproved = changePercent >= 0f

    val arrow = if (isImproved) "↑" else "↓"
    val changeColor = if (isImproved) Success else Error
    val changeText = "${arrow} ${kotlin.math.abs(changePercent).roundToInt().toBengaliDigits()}%"

    val avgSessionMinutes = if (weekComparison.thisWeekSessions > 0) {
        weekComparison.thisWeekMinutes / weekComparison.thisWeekSessions
    } else 0L

    val mockTasksCompleted = 12
    val mockSessionsThisWeek = weekComparison.thisWeekSessions.coerceAtLeast(5)

    GlassElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        padding = 20.dp,
    ) {
        Column {
            Text(
                text = "সাপ্তাহিক প্রতিবেদন",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Study time comparison
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column {
                    Text(
                        text = "এই সপ্তাহ",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Row(verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = thisWeekHours.toBengaliDigits(1),
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontFamily = EnglishFontFamily,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface,
                            ),
                        )
                        Text(
                            text = "ঘণ্টা",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(bottom = 4.dp),
                        )
                    }
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = changeText,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontFamily = EnglishFontFamily,
                            fontWeight = FontWeight.Bold,
                            color = changeColor,
                        )
                    )
                    Text(
                        text = "গত সপ্তাহ: ${lastWeekHours.toBengaliDigits(1)} ঘণ্টা",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Comparison bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    text = "গত",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(8.dp)
                        .clip(RoundedCornerShape(100.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    contentAlignment = Alignment.CenterStart,
                ) {
                    val maxH = maxOf(thisWeekHours, lastWeekHours, 1f)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(lastWeekHours / maxH)
                            .height(8.dp)
                            .clip(RoundedCornerShape(100.dp))
                            .background(MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)),
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "এই",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(8.dp)
                        .clip(RoundedCornerShape(100.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    contentAlignment = Alignment.CenterStart,
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(thisWeekHours / maxH)
                            .height(8.dp)
                            .clip(RoundedCornerShape(100.dp))
                            .background(Primary),
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Stats row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                StatChip(
                    label = "সেশন সম্পন্ন",
                    value = mockSessionsThisWeek.toBengaliDigits(),
                    icon = "📋",
                    color = Primary,
                )
                StatChip(
                    label = "গড় সেশন",
                    value = "${avgSessionMinutes.toBengaliDigits()}মি",
                    icon = "⏱️",
                    color = Info,
                )
                StatChip(
                    label = "টাস্ক সম্পন্ন",
                    value = mockTasksCompleted.toBengaliDigits(),
                    icon = "✅",
                    color = Success,
                )
            }
        }
    }
}

@Composable
private fun StatChip(
    label: String,
    value: String,
    icon: String,
    color: Color,
) {
    val isDark = MaterialTheme.colorScheme.isDark
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = color.copy(alpha = if (isDark) 0.12f else 0.08f),
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(text = icon, style = MaterialTheme.typography.bodyMedium)
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontFamily = EnglishFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = color,
                ),
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// 8. Motivational Nudge Card
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun MotivationalNudgeCard() {
    val hourOfDay = remember {
        Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    }

    val (emoji, title, message, bgColor) = when {
        hourOfDay in 0..5 -> "🌙" to "বিশ্রামের সময়!" to
            "এখন ঘুমান। ভালো ঘুম আপনার মেমোরি উন্নত করে। আগামীকাল তাজা মনে পড়াশোনা শুরু করুন।" to
            Secondary.copy(alpha = 0.12f)
        hourOfDay in 6..11 -> "🌅" to "সকালের শুভেচ্ছা!" to
            "সকালে মস্তিষ্ক সবচেয়ে সক্রিয়। আজকে কঠিন অধ্যায় পড়ার সেরা সময়!" to
            Primary.copy(alpha = 0.12f)
        hourOfDay in 12..16 -> "☀️" to "দুপুরের স্টাডি সেশন!" to
            "দুপুরে পড়ার পর ছোট বিরতি নিন। পমোডোরো টেকনিক ব্যবহার করুন সেরা ফলাফলের জন্য।" to
            Tertiary.copy(alpha = 0.12f)
        hourOfDay in 17..20 -> "🌆" to "সন্ধ্যার অধ্যয়ন!" to
            "সারাদিন ক্লান্ত? হালকা অধ্যায় দিয়ে শুরু করুন। ফ্ল্যাশকার্ড রিভিশন ভালো কাজ করে!" to
            Color(0xFF4ECDC4).copy(alpha = 0.12f)
        else -> "🌙" to "রাতের যোদ্ধা!" to
            "রাতে পড়াশোনা করছেন? দীর্ঘ সময় না পড়ে ফোকাসড সেশন করুন। কাল সকালে রিভিশন করবেন না ভুলে!" to
            Color(0xFFAB47BC).copy(alpha = 0.12f)
    }

    val quotes = listOf(
        "সফলতার কোনো শর্টকাট নেই। ধৈর্য ধরুন।",
        "প্রতিদিন একটু একটু করে অগ্রগতি হচ্ছে।",
        "আজকের পরিশ্রম কালকের সাফল্য তৈরি করবে।",
        "বড় পরীক্ষার জন্য ছোট পদক্ষেপেই প্রস্তুত হচ্ছেন।",
    )
    val randomQuote = remember { quotes.random() }

    GlassmorphicCard(
        modifier = Modifier.fillMaxWidth(),
        variant = GlassCardVariant.FILLED,
        tint = bgColor,
        cornerRadius = LocalGlassShapes.current.cardRadiusLarge,
        padding = 20.dp,
    ) {
        Row(verticalAlignment = Alignment.Top) {
            Text(
                text = emoji,
                style = MaterialTheme.typography.headlineMedium,
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                    ),
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 18.sp,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                ) {
                    Text(
                        text = "\"$randomQuote\"",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                        ),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                    )
                }
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Mock Data Helpers
// ═══════════════════════════════════════════════════════════════════════════════

private val mockSubjectTimeData = listOf(
    SubjectTimeEntry("গণিত", 18 * 3600, "১৮ঘণ্টা ০মি"),
    SubjectTimeEntry("পদার্থবিজ্ঞান", 12 * 3600, "১২ঘণ্টা ০মি"),
    SubjectTimeEntry("রসায়ন", 9 * 3600, "৯ঘণ্টা ০মি"),
    SubjectTimeEntry("জীববিজ্ঞান", 15 * 3600, "১৫ঘণ্টা ০মি"),
    SubjectTimeEntry("বাংলা", 7 * 3600, "৭ঘণ্টা ০মি"),
    SubjectTimeEntry("ইংরেজি", 6 * 3600, "৬ঘণ্টা ০মি"),
)

private fun generateMockCalendarData(): List<HeatmapDay> {
    val data = mutableListOf<HeatmapDay>()
    val cal = Calendar.getInstance()
    val fmt = SimpleDateFormat("yyyy-MM-dd", Locale.US)

    for (i in 83 downTo 0) {
        val c = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -i) }
        val dateStr = fmt.format(c.time)
        val dayOfWeek = c.get(Calendar.DAY_OF_WEEK)
        // Simulate study patterns: more on weekdays, less on weekends
        val baseMinutes = when (dayOfWeek) {
            Calendar.FRIDAY, Calendar.SATDAY -> 20L
            else -> 60L
        }
        val minutes = if (i < 30) {
            // Recent days: more active
            (baseMinutes + (0..90).random()).toLong()
        } else if (i < 60) {
            // Mid period
            (baseMinutes / 2 + (0..50).random()).toLong()
        } else {
            // Older days: less active
            (0..40).random().toLong()
        }
        data.add(
            HeatmapDay(
                date = dateStr,
                studyMinutes = minutes,
                intensity = (minutes.toFloat() / 180f).coerceIn(0f, 1f),
            )
        )
    }
    return data
}
*/