package com.porashona.studymaster.ui.compose.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.BrightnessHigh
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.TrendingDown
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.porashona.studymaster.ui.compose.components.GlassElevatedCard
import com.porashona.studymaster.ui.compose.components.GlassFilledCard
import com.porashona.studymaster.ui.compose.components.GlassOutlinedCard
import com.porashona.studymaster.ui.compose.theme.Chart1
import com.porashona.studymaster.ui.compose.theme.Chart2
import com.porashona.studymaster.ui.compose.theme.Chart3
import com.porashona.studymaster.ui.compose.theme.Chart5
import com.porashona.studymaster.ui.compose.theme.EnglishFontFamily
import com.porashona.studymaster.ui.compose.theme.Error
import com.porashona.studymaster.ui.compose.theme.Info
import com.porashona.studymaster.ui.compose.theme.Primary
import com.porashona.studymaster.ui.compose.theme.Success
import com.porashona.studymaster.ui.compose.theme.Warning
import com.porashona.studymaster.ui.compose.theme.XpBarBg
import com.porashona.studymaster.ui.compose.theme.toBengaliDigits
import kotlin.math.roundToInt

// ═══════════════════════════════════════════════════════════════════════════════
// Data class for weak subject insights
// ═══════════════════════════════════════════════════════════════════════════════

private data class WeakSubject(
    val name: String,
    val hours: Float,
    val color: Color,
    val recommendation: String,
)

// ═══════════════════════════════════════════════════════════════════════════════
// InsightsScreen — Study pattern insights with Bengali UI
// ═══════════════════════════════════════════════════════════════════════════════

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsightsScreen() {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val comparisonTabs = listOf("সাপ্তাহিক", "মাসিক")

    // ── Mock insights data ─────────────────────────────────────────────────────
    val bestStudyTime = "সকাল ৮:০০ - ১০:০০"
    val mostProductiveDay = "মঙ্গলবার"
    val consistencyScore = 78 // percentage
    val totalStudyHours = 32.5f
    val avgDailyHours = 4.6f
    val totalSessions = 47

    // Weak subjects (sorted by time spent, least first = weakest)
    val weakSubjects = remember {
        listOf(
            WeakSubject("পদার্থবিজ্ঞান", 3.2f, Chart1, "সপ্তাহে কমপক্ষে ২ ঘণ্টা বেশি দিন"),
            WeakSubject("রসায়ন", 4.1f, Chart2, "সূত্র মুখস্থ করার জন্য ফ্ল্যাশকার্ড ব্যবহার করুন"),
            WeakSubject("উচ্চতর গণিত", 5.5f, Chart3, "প্রতিদিন কমপক্ষে ৩টি সমস্যা সমাধান করুন"),
        )
    }

    // Weekly bar data (hours per day, Mon-Sun)
    val weeklyData = remember {
        listOf(
            Triple("শনি", 2.5f, true),
            Triple("রবি", 1.0f, false),
            Triple("সোম", 4.5f, true),
            Triple("মঙ্গল", 6.0f, true),
            Triple("বুধ", 3.5f, true),
            Triple("বৃহঃ", 5.0f, true),
            Triple("শুক্র", 4.0f, true),
        )
    }

    // Monthly comparison data
    val monthlyThisWeek = 22.5f
    val monthlyLastWeek = 18.0f
    val monthlyChange = ((monthlyThisWeek - monthlyLastWeek) / monthlyLastWeek * 100).roundToInt()

    // ── Animated values ────────────────────────────────────────────────────────
    val animatedConsistency by animateFloatAsState(
        targetValue = consistencyScore / 100f,
        animationSpec = tween(1200, easing = FastOutSlowInEasing),
        label = "consistency",
    )

    val weeklyMax = (weeklyData.maxOfOrNull { it.second } ?: 1f)

    // ── Scaffold ───────────────────────────────────────────────────────────────
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "অন্তর্দৃষ্টি",
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
                        tint = Primary,
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                ),
                scrollBehavior = scrollBehavior,
            )
        },
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            // Comparison tabs
            ScrollableTabRow(
                selectedTabIndex = selectedTabIndex,
                modifier = Modifier.fillMaxWidth(),
                edgePadding = 16.dp,
                indicator = { tabPositions ->
                    if (selectedTabIndex < tabPositions.size) {
                        TabRowDefaults.SecondaryIndicator(
                            modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                            color = Primary,
                        )
                    }
                },
                divider = {},
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface,
            ) {
                comparisonTabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Medium,
                                ),
                            )
                        },
                        selectedContentColor = Primary,
                        unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {

                // ══════════════════════════════════════════════════════════════════
                // SECTION: Study Pattern Insights
                // ══════════════════════════════════════════════════════════════════
                item {
                    Text(
                        "পড়াশোনার প্যাটার্ন",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    )
                }

                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        // Best study time
                        GlassElevatedCard(
                            modifier = Modifier.weight(1f),
                            padding = 16.dp,
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                Icon(
                                    imageVector = Icons.Default.BrightnessHigh,
                                    contentDescription = null,
                                    tint = AchievementUnlocked,
                                    modifier = Modifier.size(28.dp),
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    "সেরা সময়",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    bestStudyTime,
                                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                                    textAlign = TextAlign.Center,
                                )
                            }
                        }

                        // Most productive day
                        GlassElevatedCard(
                            modifier = Modifier.weight(1f),
                            padding = 16.dp,
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CalendarToday,
                                    contentDescription = null,
                                    tint = Primary,
                                    modifier = Modifier.size(28.dp),
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    "সবচেয়ে ভালো দিন",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    mostProductiveDay,
                                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                                    textAlign = TextAlign.Center,
                                )
                            }
                        }
                    }
                }

                // ══════════════════════════════════════════════════════════════════
                // SECTION: Consistency Score (circular gauge)
                // ══════════════════════════════════════════════════════════════════
                item {
                    Text(
                        "ধারাবাহিকতা স্কোর",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    )
                }

                item {
                    GlassElevatedCard(
                        modifier = Modifier.fillMaxWidth(),
                        padding = 24.dp,
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            // Circular gauge
                            Box(
                                modifier = Modifier.size(120.dp),
                                contentAlignment = Alignment.Center,
                            ) {
                                Canvas(modifier = Modifier.size(120.dp)) {
                                    // Background arc
                                    drawArc(
                                        color = XpBarBg,
                                        startAngle = -90f,
                                        sweepAngle = 360f,
                                        useCenter = false,
                                        style = Stroke(width = 10.dp.toPx(), cap = StrokeCap.Round),
                                    )
                                    // Progress arc
                                    val gaugeColor = when {
                                        animatedConsistency >= 0.8f -> Success
                                        animatedConsistency >= 0.6f -> AchievementUnlocked
                                        animatedConsistency >= 0.4f -> Warning
                                        else -> Error
                                    }
                                    drawArc(
                                        color = gaugeColor,
                                        startAngle = -90f,
                                        sweepAngle = 360f * animatedConsistency,
                                        useCenter = false,
                                        style = Stroke(width = 10.dp.toPx(), cap = StrokeCap.Round),
                                    )
                                }
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        "${consistencyScore.toBengaliDigits()}%",
                                        style = MaterialTheme.typography.headlineMedium.copy(
                                            fontFamily = EnglishFontFamily,
                                            fontWeight = FontWeight.Bold,
                                        ),
                                    )
                                    Text(
                                        "স্কোর",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.width(20.dp))

                            // Stats alongside
                            Column(
                                modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.spacedBy(12.dp),
                            ) {
                                InsightStatRow(
                                    label = "মোট অধ্যয়ন",
                                    value = "${totalStudyHours.toBengaliDigits()} ঘণ্টা",
                                    color = Primary,
                                )
                                InsightStatRow(
                                    label = "দৈনিক গড়",
                                    value = "${avgDailyHours.toBengaliDigits()} ঘণ্টা",
                                    color = Chart2,
                                )
                                InsightStatRow(
                                    label = "মোট সেশন",
                                    value = totalSessions.toBengaliDigits(),
                                    color = Chart3,
                                )
                            }
                        }
                    }
                }

                // ══════════════════════════════════════════════════════════════════
                // SECTION: Weekly/Monthly Comparison Bar Chart
                // ══════════════════════════════════════════════════════════════════
                item {
                    Text(
                        if (selectedTabIndex == 0) "সাপ্তাহিক তুলনা" else "মাসিক তুলনা",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    )
                }

                // Comparison header card
                item {
                    GlassElevatedCard(
                        modifier = Modifier.fillMaxWidth(),
                        padding = 20.dp,
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Column {
                                Text(
                                    if (selectedTabIndex == 0) "এই সপ্তাহ" else "এই মাস",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                                Text(
                                    "${monthlyThisWeek.toBengaliDigits()} ঘণ্টা",
                                    style = MaterialTheme.typography.headlineSmall.copy(
                                        fontFamily = EnglishFontFamily,
                                        fontWeight = FontWeight.Bold,
                                    ),
                                    color = Primary,
                                )
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                val isUp = monthlyChange >= 0
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = if (isUp) Icons.Default.TrendingUp else Icons.Default.TrendingDown,
                                        contentDescription = null,
                                        tint = if (isUp) Success else Error,
                                        modifier = Modifier.size(20.dp),
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        "${if (isUp) "+" else ""}${monthlyChange.toBengaliDigits()}%",
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontFamily = EnglishFontFamily,
                                            fontWeight = FontWeight.Bold,
                                        ),
                                        color = if (isUp) Success else Error,
                                    )
                                }
                                Text(
                                    if (selectedTabIndex == 0) "গত সপ্তাহ থেকে"
                                    else "গত মাস থেকে",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                            }
                        }
                    }
                }

                // Bar chart (weekly)
                if (selectedTabIndex == 0) {
                    item {
                        GlassOutlinedCard(
                            modifier = Modifier.fillMaxWidth(),
                            padding = 20.dp,
                        ) {
                            Column {
                                // Bar labels (days)
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(160.dp),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalAlignment = Alignment.Bottom,
                                ) {
                                    weeklyData.forEach { (day, hours, metGoal) ->
                                        val animatedHeight by animateFloatAsState(
                                            targetValue = hours / weeklyMax,
                                            animationSpec = tween(800, easing = FastOutSlowInEasing),
                                            label = "bar$day",
                                        )
                                        Column(
                                            modifier = Modifier.weight(1f),
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.Bottom,
                                        ) {
                                            Text(
                                                "${hours.toBengaliDigits(0)}",
                                                style = MaterialTheme.typography.labelSmall.copy(
                                                    fontFamily = EnglishFontFamily,
                                                    fontSize = MaterialTheme.typography.labelSmall.fontSize,
                                                ),
                                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                            )
                                            Spacer(modifier = Modifier.height(4.dp))
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .fillMaxHeight(animatedHeight)
                                                    .clip(RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp))
                                                    .background(
                                                        if (metGoal) Brush.verticalGradient(
                                                            listOf(Primary, Primary.copy(alpha = 0.6f))
                                                        )
                                                        else Brush.verticalGradient(
                                                            listOf(Warning, Warning.copy(alpha = 0.6f))
                                                        )
                                                    ),
                                            )
                                        }
                                    }
                                }
                                // Day labels
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                ) {
                                    weeklyData.forEach { (day, _, _) ->
                                        Text(
                                            day,
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                                            modifier = Modifier.weight(1f),
                                            textAlign = TextAlign.Center,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center,
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(4.dp))
                                            .background(Primary)
                                            .size(12.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        "লক্ষ্য পূরণ",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    )
                                    Spacer(modifier = Modifier.width(16.dp))
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(4.dp))
                                            .background(Warning)
                                            .size(12.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        "লক্ষ্য অপূরণ",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    )
                                }
                            }
                        }
                    }
                }

                // Monthly comparison bars
                if (selectedTabIndex == 1) {
                    item {
                        GlassOutlinedCard(
                            modifier = Modifier.fillMaxWidth(),
                            padding = 20.dp,
                        ) {
                            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                                // This month
                                MonthlyBarRow(
                                    label = "এই মাস",
                                    value = monthlyThisWeek,
                                    maxValue = 40f,
                                    color = Primary,
                                )
                                // Last month
                                MonthlyBarRow(
                                    label = "গত মাস",
                                    value = monthlyLastWeek,
                                    maxValue = 40f,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                            }
                        }
                    }
                }

                // ══════════════════════════════════════════════════════════════════
                // SECTION: Weak Subject Recommendations
                // ══════════════════════════════════════════════════════════════════
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "দুর্বল বিষয় সুপারিশ",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    )
                    Text(
                        "সবচেয়ে কম সময় দেওয়া বিষয়গুলো",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }

                items(weakSubjects) { subject ->
                    WeakSubjectCard(subject = subject)
                }

                // ══════════════════════════════════════════════════════════════════
                // SECTION: Quick tips
                // ══════════════════════════════════════════════════════════════════
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "দ্রুত পরামর্শ",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    )
                }

                item {
                    GlassFilledCard(
                        modifier = Modifier.fillMaxWidth(),
                        tint = Info,
                        padding = 16.dp,
                    ) {
                        Row(verticalAlignment = Alignment.Top) {
                            Icon(
                                imageVector = Icons.Default.Speed,
                                contentDescription = null,
                                tint = Info,
                                modifier = Modifier.size(24.dp),
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    "পড়াশোনার গতি বাড়ান",
                                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    "আপনার সেরা সময় সকাল ৮-১০টা। এই সময়ে কঠিন বিষয়গুলো পড়ুন এবং বিকেলে সহজ বিষয়গুলো রিভিশন করুন।",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                            }
                        }
                    }
                }

                item {
                    GlassFilledCard(
                        modifier = Modifier.fillMaxWidth(),
                        tint = Warning,
                        padding = 16.dp,
                    ) {
                        Row(verticalAlignment = Alignment.Top) {
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = null,
                                tint = Warning,
                                modifier = Modifier.size(24.dp),
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    "পদার্থবিজ্ঞানে মনোযোগ দিন",
                                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    "গত সপ্তাহে পদার্থবিজ্ঞানে মাত্র ৩.২ ঘণ্টা দিয়েছেন। পরীক্ষার আগে বাড়ানো প্রয়োজন।",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                            }
                        }
                    }
                }

                item { Spacer(modifier = Modifier.height(32.dp)) }
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Insight stat row (small label + value)
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun InsightStatRow(
    label: String,
    value: String,
    color: Color,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(color),
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.weight(1f),
        )
        Text(
            value,
            style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.Bold,
            ),
        )
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Weak subject card
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun WeakSubjectCard(subject: WeakSubject) {
    GlassOutlinedCard(
        modifier = Modifier.fillMaxWidth(),
        padding = 16.dp,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Color dot
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(subject.color.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    "📉",
                    style = MaterialTheme.typography.titleMedium,
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    subject.name,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                )
                Text(
                    "${subject.hours.toBengaliDigits()} ঘণ্টা সপ্তাহে",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontFamily = EnglishFontFamily,
                )
            }

            // Recommendation tag
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(100.dp))
                    .background(Warning.copy(alpha = 0.12f))
                    .padding(horizontal = 10.dp, vertical = 4.dp),
            ) {
                Text(
                    "সুপারিশ",
                    style = MaterialTheme.typography.labelSmall,
                    color = Warning,
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Recommendation text
        GlassFilledCard(
            modifier = Modifier.fillMaxWidth(),
            tint = subject.color,
            padding = 12.dp,
        ) {
            Row(verticalAlignment = Alignment.Top) {
                Text("💡", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    subject.recommendation,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Monthly bar row
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun MonthlyBarRow(
    label: String,
    value: Float,
    maxValue: Float,
    color: Color,
) {
    val animatedWidth by animateFloatAsState(
        targetValue = value / maxValue,
        animationSpec = tween(800, easing = FastOutSlowInEasing),
        label = "monthlyBar",
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            label,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.width(80.dp),
        )
        // Bar background
        Box(
            modifier = Modifier
                .weight(1f)
                .height(24.dp)
                .clip(RoundedCornerShape(100.dp))
                .background(XpBarBg),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(animatedWidth)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(100.dp))
                    .background(color),
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            "${value.toBengaliDigits(1)} ঘণ্টা",
            style = MaterialTheme.typography.bodySmall.copy(
                fontFamily = EnglishFontFamily,
                fontWeight = FontWeight.Bold,
            ),
            modifier = Modifier.width(80.dp),
        )
    }
}