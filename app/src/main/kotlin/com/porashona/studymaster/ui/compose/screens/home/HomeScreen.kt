package com.porashona.studymaster.ui.compose.screens.home

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.outlined.Casino
import androidx.compose.material.icons.outlined.FlashOn
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material.icons.outlined.Notes
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.porashona.studymaster.data.model.DailyChallenge
import com.porashona.studymaster.data.model.SessionType
import com.porashona.studymaster.data.model.StudySession
import com.porashona.studymaster.data.model.Task
import com.porashona.studymaster.data.model.TaskPriority
import com.porashona.studymaster.ui.compose.components.ActivityTagChip
import com.porashona.studymaster.ui.compose.components.ActivityType
import com.porashona.studymaster.ui.compose.components.CircularProgress
import com.porashona.studymaster.ui.compose.components.ExamCountdownBanner
import com.porashona.studymaster.ui.compose.components.GlassCardVariant
import com.porashona.studymaster.ui.compose.components.GlassmorphicCard
import com.porashona.studymaster.ui.compose.components.LocalGlassShapes
import com.porashona.studymaster.ui.compose.components.LocalMotion
import com.porashona.studymaster.ui.compose.components.Priority
import com.porashona.studymaster.ui.compose.components.PriorityBadge
import com.porashona.studymaster.ui.compose.components.StreakFireBadge
import com.porashona.studymaster.ui.compose.components.SubjectChip
import com.porashona.studymaster.ui.compose.components.XPProgressIndicator
import com.porashona.studymaster.ui.compose.components.XPGainPopup
import com.porashona.studymaster.ui.compose.theme.*
import com.porashona.studymaster.ui.compose.viewmodels.HomeEvent
import com.porashona.studymaster.ui.compose.viewmodels.HomeViewModel
import com.porashona.studymaster.ui.compose.viewmodels.StudySuggestion
import com.porashona.studymaster.ui.compose.viewmodels.WeeklyGoalProgress
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

// ═══════════════════════════════════════════════════════════════════════════════
// HomeScreen — Full-featured dashboard
//
// Primary landing screen: greeting, exam countdown, study suggestion,
// progress ring, XP bar, daily challenge, quick actions, tasks, sessions,
// and weekly goal. All text is Bengali-first. Cards use staggered entrance
// animations. Supports pull-to-refresh.
// ═══════════════════════════════════════════════════════════════════════════════

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateToTimer: () -> Unit = {},
    onNavigateToNotes: () -> Unit = {},
    onNavigateToPractice: () -> Unit = {},
    onNavigateToFlashcards: () -> Unit = {},
    onNavigateToExams: () -> Unit = {},
    onNavigateToTasks: () -> Unit = {},
    onNavigateToAssistant: () -> Unit = {},
    onNavigateToRoutine: () -> Unit = {},
    onNavigateToCalendar: () -> Unit = {},
    onNavigateToGoals: () -> Unit = {},
) {
    // ── Collect state from ViewModel ─────────────────────────────────────────
    val todayStudyMinutes by viewModel.todayStudyMinutes.collectAsState(initial = 0L)
    val currentStreak by viewModel.currentStreak.collectAsState(initial = 0)
    val userLevel by viewModel.userLevel.collectAsState(initial = null)
    val totalXP by viewModel.totalXP.collectAsState(initial = 0)
    val currentLevel by viewModel.currentLevel.collectAsState(initial = 1)
    val nextExam by viewModel.nextExam.collectAsState(initial = null)
    val upcomingTasks by viewModel.upcomingTasks.collectAsState(initial = emptyList())
    val todaySessions by viewModel.todaySessions.collectAsState(initial = emptyList())
    val dailyChallenge by viewModel.dailyChallenge.collectAsState(initial = null)
    val studySuggestion by viewModel.studySuggestion.collectAsState(initial = StudySuggestion.empty())
    val weeklyProgress by viewModel.weeklyGoalProgress.collectAsState(initial = WeeklyGoalProgress())
    val homeEvent by viewModel.events.collectAsState(initial = null)

    // ── Pull-to-refresh state ────────────────────────────────────────────────
    var isRefreshing by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    // ── Handle one-shot events ───────────────────────────────────────────────
    var showXpPopup by remember { mutableStateOf(false) }
    var xpPopupAmount by remember { mutableStateOf(0) }

    LaunchedEffect(homeEvent) {
        when (homeEvent) {
            is HomeEvent.ChallengeCompleted -> {
                xpPopupAmount = (homeEvent as HomeEvent.ChallengeCompleted).xpEarned
                showXpPopup = true
                viewModel.clearEvent()
            }
            is HomeEvent.Error -> {
                viewModel.clearEvent()
            }
            null -> { /* no event */ }
        }
    }

    // ── Time-based greeting ──────────────────────────────────────────────────
    val greetingBn = remember {
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        when {
            hour < 6  -> "শুভ রাত্রি"
            hour < 12 -> "সুপ্রভাত"
            hour < 17 -> "শুভ দুপুর"
            hour < 20 -> "শুভ সন্ধ্যা"
            else     -> "শুভ রাত্রি"
        }
    }

    val staggerDelayMs = 60L
    val dailyGoalMinutes = 120

    // ══════════════════════════════════════════════════════════════════════════
    // Pull-to-refresh container
    // ══════════════════════════════════════════════════════════════════════════
    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = {
            isRefreshing = true
            viewModel.refreshHome()
            scope.launch {
                delay(800)
                isRefreshing = false
            }
        },
        modifier = Modifier.fillMaxSize(),
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(
                top = 8.dp,
                bottom = 100.dp,
            ),
        ) {
            // ── 1. Greeting ─────────────────────────────────────────────────
            item(key = "greeting") {
                StaggeredEntry(index = 0, staggerDelayMs = staggerDelayMs) {
                    GreetingSection(
                        greetingBn = greetingBn,
                        streakDays = currentStreak,
                    )
                }
            }

            // ── 2. XP Progress ─────────────────────────────────────────────
            item(key = "xp_streak") {
                StaggeredEntry(index = 1, staggerDelayMs = staggerDelayMs) {
                    XPProgressRow(
                        currentXP = userLevel?.currentXP ?: 0,
                        requiredXP = userLevel?.xpForNextLevel ?: 1000,
                        level = currentLevel,
                    )
                }
            }

            // ── 3. Exam Countdown ──────────────────────────────────────────
            if (nextExam != null) {
                item(key = "exam_countdown") {
                    StaggeredEntry(index = 2, staggerDelayMs = staggerDelayMs) {
                        ExamCountdownBanner(
                            examName = nextExam!!.name,
                            examDate = Date(nextExam!!.examDate),
                            subjectColorHex = nextExam!!.subjectId?.let {
                                SubjectPalette.hexForIndex(it.toInt())
                            },
                            onClick = onNavigateToExams,
                        )
                    }
                }
            }

            // ── 4. Study Suggestion ───────────────────────────────────────
            item(key = "suggestion") {
                StaggeredEntry(index = 3, staggerDelayMs = staggerDelayMs) {
                    StudySuggestionCard(
                        suggestion = studySuggestion,
                        onNavigateToAssistant = onNavigateToAssistant,
                    )
                }
            }

            // ── 5. Today's Progress ───────────────────────────────────────
            item(key = "today_progress") {
                StaggeredEntry(index = 4, staggerDelayMs = staggerDelayMs) {
                    TodayStudyProgressCard(
                        studiedMinutes = todayStudyMinutes.toInt(),
                        goalMinutes = dailyGoalMinutes,
                        sessionCount = todaySessions.size,
                    )
                }
            }

            // ── 6. Quick Actions ───────────────────────────────────────────
            item(key = "quick_actions") {
                StaggeredEntry(index = 5, staggerDelayMs = staggerDelayMs) {
                    QuickActionsSection(
                        onNavigateToTimer = onNavigateToTimer,
                        onNavigateToNotes = onNavigateToNotes,
                        onNavigateToPractice = onNavigateToPractice,
                        onNavigateToFlashcards = onNavigateToFlashcards,
                        onNavigateToAssistant = onNavigateToAssistant,
                        onNavigateToRoutine = onNavigateToRoutine,
                    )
                }
            }

            // ── 7. Daily Challenge ─────────────────────────────────────────
            if (dailyChallenge != null) {
                item(key = "daily_challenge") {
                    StaggeredEntry(index = 6, staggerDelayMs = staggerDelayMs) {
                        DailyChallengeCard(
                            challenge = dailyChallenge!!,
                            onMarkComplete = { viewModel.markChallengeComplete() },
                        )
                    }
                }
            }

            // ── 8. Upcoming Tasks ──────────────────────────────────────────
            if (upcomingTasks.isNotEmpty()) {
                item(key = "tasks_header") {
                    StaggeredEntry(index = 7, staggerDelayMs = staggerDelayMs) {
                        SectionHeader(
                            title = "আসন্ন টাস্ক",
                            subtitle = "${upcomingTasks.size.toBengaliDigits()}টি বাকি",
                            onSeeAll = onNavigateToTasks,
                        )
                    }
                }
                items(
                    items = upcomingTasks.take(3),
                    key = { "task_${it.id}" },
                ) { task ->
                    StaggeredEntry(
                        index = 8 + upcomingTasks.indexOf(task),
                        staggerDelayMs = staggerDelayMs,
                    ) {
                        TaskPreviewItem(task = task, onClick = onNavigateToTasks)
                    }
                }
            }

            // ── 9. Recent Sessions ────────────────────────────────────────
            if (todaySessions.isNotEmpty()) {
                item(key = "sessions_header") {
                    StaggeredEntry(index = 12, staggerDelayMs = staggerDelayMs) {
                        SectionHeader(
                            title = "আজকের সেশন",
                            subtitle = "${todaySessions.size.toBengaliDigits()}টি সেশন",
                        )
                    }
                }
                items(
                    items = todaySessions.take(5),
                    key = { "session_${it.id}" },
                ) { session ->
                    StaggeredEntry(
                        index = 13 + todaySessions.indexOf(session),
                        staggerDelayMs = staggerDelayMs,
                    ) {
                        SessionPreviewItem(session = session)
                    }
                }
            }

            // ── 10. Weekly Goal ────────────────────────────────────────────
            item(key = "weekly_goal") {
                StaggeredEntry(index = 18, staggerDelayMs = staggerDelayMs) {
                    WeeklyGoalCard(weeklyProgress = weeklyProgress)
                }
            }

            item(key = "bottom_spacer") {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        // XP Gain popup
        XPGainPopup(
            xpAmount = xpPopupAmount,
            visible = showXpPopup,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 16.dp),
            onFinished = { showXpPopup = false },
        )
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// StaggeredEntry — delayed fade+slide entrance for each dashboard card
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun StaggeredEntry(
    index: Int,
    staggerDelayMs: Long,
    content: @Composable () -> Unit,
) {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(index * staggerDelayMs)
        visible = true
    }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(tween(300)) + slideInVertically(
            initialOffsetY = { it / 4 },
            animationSpec = tween(300),
        ),
        exit = fadeOut(tween(150)) + slideOutVertically(
            targetOffsetY = { it / 6 },
        ),
    ) {
        content()
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// 1. Greeting — time-of-day + streak badge + formatted date
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun GreetingSection(
    greetingBn: String,
    streakDays: Int,
    modifier: Modifier = Modifier,
) {
    val todayFormatted = remember {
        val dayNames = listOf("রবিবার", "সোমবার", "মঙ্গলবার", "বুধবার", "বৃহস্পতিবার", "শুক্রবার", "শনিবার")
        val cal = Calendar.getInstance()
        val dayName = dayNames[(cal.get(Calendar.DAY_OF_WEEK) - 1).coerceIn(0, 6)]
        val sdf = SimpleDateFormat("dd MMM, yyyy", Locale.getDefault())
        "$dayName \u2022 ${sdf.format(Date())}"
    }

    Column(modifier = modifier.padding(top = 4.dp, bottom = 4.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = greetingBn,
                    style = StudyMasterTypography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text = "শিক্ষার্থী",
                    style = StudyMasterTypography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            if (streakDays > 0) {
                StreakFireBadge(streakDays = streakDays)
            }
        }
        Spacer(Modifier.height(4.dp))
        Text(
            text = todayFormatted,
            style = StudyMasterTypography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
        )
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// 2. XP Progress — level bar inside a glass card
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun XPProgressRow(
    currentXP: Int,
    requiredXP: Int,
    level: Int,
    modifier: Modifier = Modifier,
) {
    GlassmorphicCard(
        modifier = modifier.fillMaxWidth(),
        variant = GlassCardVariant.OUTLINED,
        padding = 16.dp,
    ) {
        XPProgressIndicator(
            currentXp = currentXP,
            requiredXp = requiredXP,
            level = level,
        )
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// 3. Study Suggestion — contextual AI recommendation card
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun StudySuggestionCard(
    suggestion: StudySuggestion,
    onNavigateToAssistant: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val priorityColor = when (suggestion.priority) {
        "high"   -> PriorityHigh
        "medium" -> PriorityMedium
        "low"    -> PriorityLow
        "rest"   -> MaterialTheme.colorScheme.onSurfaceVariant
        else     -> MaterialTheme.colorScheme.primary
    }

    GlassmorphicCard(
        modifier = modifier.fillMaxWidth(),
        variant = GlassCardVariant.FILLED,
        tint = priorityColor,
        cornerRadius = LocalGlassShapes.current.cardRadiusLarge,
        padding = 16.dp,
        onClick = if (suggestion.priority != "rest") onNavigateToAssistant else null,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top,
        ) {
            Text(
                text = suggestion.icon,
                fontSize = 28.sp,
                modifier = Modifier.padding(end = 12.dp),
            )
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text(
                        text = "এখন কী পড়বেন?",
                        style = StudyMasterTypography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .background(priorityColor, CircleShape),
                    )
                }
                Spacer(Modifier.height(4.dp))
                Text(
                    text = suggestion.title,
                    style = StudyMasterTypography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = suggestion.description,
                    style = StudyMasterTypography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 18.sp,
                )
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// 4. Today's Study Progress — circular progress ring + stats
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun TodayStudyProgressCard(
    studiedMinutes: Int,
    goalMinutes: Int,
    sessionCount: Int,
    modifier: Modifier = Modifier,
) {
    val progress = remember(studiedMinutes, goalMinutes) {
        if (goalMinutes > 0) (studiedMinutes.toFloat() / goalMinutes.toFloat()).coerceIn(0f, 1f)
        else 0f
    }

    val hoursStudied = studiedMinutes / 60
    val remainingMinutes = studiedMinutes % 60
    val progressColor = when {
        progress >= 1.0f  -> Success
        progress >= 0.7f  -> Primary
        progress >= 0.3f  -> Warning
        else              -> Error
    }

    GlassmorphicCard(
        modifier = modifier.fillMaxWidth(),
        variant = GlassCardVariant.ELEVATED,
        padding = 20.dp,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Circular progress
            CircularProgress(
                progress = progress,
                modifier = Modifier.size(100.dp),
                strokeWidth = 8.dp,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
                progressColor = progressColor,
                centerContent = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "${(progress * 100).toInt().toBengaliDigits()}%",
                            style = StudyMasterTypography.titleMedium.copy(
                                fontFamily = EnglishFontFamily,
                                fontWeight = FontWeight.Bold,
                                color = progressColor,
                            ),
                        )
                        Text(
                            text = "সম্পন্ন",
                            style = StudyMasterTypography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                },
            )

            Spacer(Modifier.width(20.dp))

            // Stats
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                // Time studied
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.AccessTime,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp),
                        tint = MaterialTheme.colorScheme.primary,
                    )
                    Spacer(Modifier.width(8.dp))
                    Column {
                        Text(
                            text = buildString {
                                if (hoursStudied > 0) append("${hoursStudied.toBengaliDigits()} ঘণ্টা ")
                                append("${remainingMinutes.toBengaliDigits()} মিনিট")
                            },
                            style = StudyMasterTypography.bodyMedium.copy(
                                fontFamily = EnglishFontFamily,
                                fontWeight = FontWeight.SemiBold,
                            ),
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                        Text(
                            text = "আজ পড়েছেন",
                            style = StudyMasterTypography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }

                // Goal
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Bolt,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp),
                        tint = XpBarFill,
                    )
                    Spacer(Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "${goalMinutes.toBengaliDigits()} মিনিট",
                            style = StudyMasterTypography.bodyMedium.copy(
                                fontFamily = EnglishFontFamily,
                                fontWeight = FontWeight.SemiBold,
                            ),
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                        Text(
                            text = "দৈনিক লক্ষ্য",
                            style = StudyMasterTypography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }

                // Sessions
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp),
                        tint = Tertiary,
                    )
                    Spacer(Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "${sessionCount.toBengaliDigits()}টি সেশন",
                            style = StudyMasterTypography.bodyMedium.copy(
                                fontFamily = EnglishFontFamily,
                                fontWeight = FontWeight.SemiBold,
                            ),
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                        Text(
                            text = "সম্পন্ন হয়েছে",
                            style = StudyMasterTypography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// 5. Quick Actions — 4 primary + 2 secondary action buttons
// ═══════════════════════════════════════════════════════════════════════════════

private data class QuickAction(
    val labelBn: String,
    val icon: ImageVector,
    val color: Color,
    val onClick: () -> Unit,
)

@Composable
private fun QuickActionsSection(
    onNavigateToTimer: () -> Unit,
    onNavigateToNotes: () -> Unit,
    onNavigateToPractice: () -> Unit,
    onNavigateToFlashcards: () -> Unit,
    onNavigateToAssistant: () -> Unit,
    onNavigateToRoutine: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val primaryActions = remember {
        listOf(
            QuickAction("টাইমার শুরু", Icons.Outlined.Timer, TimerWork, onNavigateToTimer),
            QuickAction("নোট যোগ করুন", Icons.Outlined.Notes, Primary, onNavigateToNotes),
            QuickAction("MCQ প্র্যাকটিস", Icons.Outlined.Casino, Tertiary, onNavigateToPractice),
            QuickAction("ফ্ল্যাশকার্ড", Icons.Outlined.FlashOn, Success, onNavigateToFlashcards),
        )
    }

    val secondaryActions = remember {
        listOf(
            QuickAction("সহকারী", Icons.Outlined.MenuBook, Secondary, onNavigateToAssistant),
            QuickAction("রুটিন", Icons.Default.School, Chart3, onNavigateToRoutine),
        )
    }

    Column(modifier = modifier) {
        Text(
            text = "দ্রুত অ্যাকশন",
            style = SpecialTextStyles.sectionHeader,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 10.dp),
        )

        // Primary 2×2 grid
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            primaryActions.forEach { action ->
                QuickActionCard(modifier = Modifier.weight(1f), action = action)
            }
        }

        Spacer(Modifier.height(10.dp))

        // Secondary row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            secondaryActions.forEach { action ->
                QuickActionSmall(
                    modifier = Modifier.weight(1f),
                    label = action.labelBn,
                    icon = action.icon,
                    color = action.color,
                    onClick = action.onClick,
                )
            }
        }
    }
}

@Composable
private fun QuickActionCard(
    action: QuickAction,
    modifier: Modifier = Modifier,
) {
    val interactionSource = remember { MutableInteractionSource() }
    GlassmorphicCard(
        modifier = modifier.clickable(
            interactionSource = interactionSource,
            indication = null,
            onClick = action.onClick,
        ),
        variant = GlassCardVariant.FILLED,
        tint = action.color,
        cornerRadius = LocalGlassShapes.current.cardRadiusSmall,
        padding = 0.dp,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Surface(
                modifier = Modifier.size(40.dp),
                shape = CircleShape,
                color = action.color.copy(alpha = 0.15f),
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Icon(
                        imageVector = action.icon,
                        contentDescription = action.labelBn,
                        tint = action.color,
                        modifier = Modifier.size(22.dp),
                    )
                }
            }
            Text(
                text = action.labelBn,
                style = StudyMasterTypography.labelSmall,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Composable
private fun QuickActionSmall(
    label: String,
    icon: ImageVector,
    color: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val interactionSource = remember { MutableInteractionSource() }
    GlassmorphicCard(
        modifier = modifier.clickable(
            interactionSource = interactionSource,
            indication = null,
            onClick = onClick,
        ),
        variant = GlassCardVariant.FILLED,
        tint = color,
        cornerRadius = LocalGlassShapes.current.cardRadiusSmall,
        padding = 0.dp,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Surface(
                modifier = Modifier.size(36.dp),
                shape = CircleShape,
                color = color.copy(alpha = 0.15f),
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = label,
                        tint = color,
                        modifier = Modifier.size(20.dp),
                    )
                }
            }
            Text(
                text = label,
                style = StudyMasterTypography.labelMedium,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// 6. Daily Challenge Card — with pulse animation + completion state
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun DailyChallengeCard(
    challenge: DailyChallenge,
    onMarkComplete: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val infiniteTransition = rememberInfiniteTransition(label = "challengePulse")
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.08f,
        targetValue = 0.18f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "challengeGlow",
    )

    val isCompleted = challenge.isCompleted

    GlassmorphicCard(
        modifier = modifier.fillMaxWidth(),
        variant = GlassCardVariant.FILLED,
        tint = if (isCompleted) Success else Tertiary,
        cornerRadius = LocalGlassShapes.current.cardRadius,
        padding = 16.dp,
        onClick = if (!isCompleted) onMarkComplete else null,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Surface(
                modifier = Modifier.size(44.dp),
                shape = RoundedCornerShape(12.dp),
                color = if (isCompleted) {
                    Success.copy(alpha = 0.15f)
                } else {
                    Tertiary.copy(alpha = glowAlpha + 0.1f)
                },
            ) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    Text(text = if (isCompleted) "✅" else "🎯", fontSize = 22.sp)
                }
            }

            Spacer(Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "দৈনিক চ্যালেঞ্জ",
                    style = StudyMasterTypography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text = challenge.challengeText,
                    style = StudyMasterTypography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "+${challenge.rewardXP}",
                    style = StudyMasterTypography.labelMedium.copy(
                        fontFamily = EnglishFontFamily,
                        fontWeight = FontWeight.Bold,
                        color = if (isCompleted) Success else XpGain,
                    ),
                )
                Text(
                    text = "XP",
                    style = StudyMasterTypography.labelSmall.copy(
                        fontFamily = EnglishFontFamily,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    ),
                )
            }
        }

        if (isCompleted) {
            Spacer(Modifier.height(8.dp))
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                color = Success.copy(alpha = 0.1f),
            ) {
                Text(
                    text = "🎉 চ্যালেঞ্জ সম্পন্ন! XP পেয়েছেন।",
                    style = StudyMasterTypography.labelSmall,
                    color = Success,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// 7. Section Header
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun SectionHeader(
    title: String,
    subtitle: String? = null,
    onSeeAll: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = title,
                style = SpecialTextStyles.sectionHeader,
                color = MaterialTheme.colorScheme.onSurface,
            )
            if (subtitle != null) {
                Text(
                    text = subtitle,
                    style = StudyMasterTypography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }

        if (onSeeAll != null) {
            TextButton(onClick = onSeeAll, modifier = Modifier.padding(0.dp)) {
                Text(
                    text = "সব দেখুন",
                    style = StudyMasterTypography.labelMedium,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// 8. Task Preview Item
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun TaskPreviewItem(
    task: Task,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val priority = when (task.priority) {
        TaskPriority.LOW    -> Priority.LOW
        TaskPriority.MEDIUM -> Priority.MEDIUM
        TaskPriority.HIGH   -> Priority.HIGH
        TaskPriority.URGENT -> Priority.URGENT
    }

    val dueDateText = remember(task.dueDate) {
        if (task.dueDate == null) {
            "কোনো সময়সীমা নেই"
        } else {
            val diffDays = TimeUnit.MILLISECONDS.toDays(task.dueDate - System.currentTimeMillis())
            when {
                diffDays < 0  -> "${(-diffDays).toInt().toBengaliDigits()} দিন পার হয়েছে"
                diffDays == 0L -> "আজ"
                diffDays == 1L -> "আগামীকাল"
                else           -> "${diffDays.toInt().toBengaliDigits()} দিন বাকি"
            }
        }
    }

    GlassmorphicCard(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick,
            ),
        variant = GlassCardVariant.OUTLINED,
        cornerRadius = LocalGlassShapes.current.cardRadiusSmall,
        padding = 12.dp,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (!task.subjectName.isNullOrBlank()) {
                SubjectChip(subjectName = task.subjectName, compact = true)
            } else {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(MaterialTheme.colorScheme.primary, CircleShape),
                )
            }

            Spacer(Modifier.width(10.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = task.title,
                    style = StudyMasterTypography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                if (task.description.isNotBlank()) {
                    Text(
                        text = task.description,
                        style = StudyMasterTypography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }

            Column(horizontalAlignment = Alignment.End) {
                PriorityBadge(priority = priority, showLabel = true)
                Spacer(Modifier.height(4.dp))
                Text(
                    text = dueDateText,
                    style = StudyMasterTypography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// 9. Session Preview Item
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun SessionPreviewItem(
    session: StudySession,
    modifier: Modifier = Modifier,
) {
    val durationMinutes = remember(session.durationInSeconds) {
        (session.durationInSeconds / 60).toInt()
    }
    val durationText = remember(durationMinutes) {
        val hours = durationMinutes / 60
        val mins = durationMinutes % 60
        buildString {
            if (hours > 0) append("${hours.toBengaliDigits()} ঘণ্টা ")
            append("${mins.toBengaliDigits()} মিনিট")
        }
    }

    val sessionTypeLabel = remember(session.sessionType) {
        when (session.sessionType) {
            SessionType.WORK        -> ActivityType.STUDY
            SessionType.SHORT_BREAK -> ActivityType.REVISION
            SessionType.LONG_BREAK  -> ActivityType.REVISION
        }
    }

    val startTimeFormatted = remember(session.startTime) {
        SimpleDateFormat("hh:mm a", Locale.getDefault()).format(session.startTime)
    }

    GlassmorphicCard(
        modifier = modifier.fillMaxWidth(),
        variant = GlassCardVariant.OUTLINED,
        cornerRadius = LocalGlassShapes.current.cardRadiusSmall,
        padding = 12.dp,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    if (session.subjectName.isNotBlank()) {
                        SubjectChip(subjectName = session.subjectName, compact = true)
                    }
                    ActivityTagChip(type = sessionTypeLabel, compact = true, useBengali = true)
                }
                Spacer(Modifier.height(4.dp))
                Text(
                    text = durationText,
                    style = StudyMasterTypography.bodyMedium.copy(
                        fontFamily = EnglishFontFamily,
                        fontWeight = FontWeight.SemiBold,
                    ),
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = startTimeFormatted,
                    style = StudyMasterTypography.labelSmall.copy(fontFamily = EnglishFontFamily),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                if (session.xpEarned > 0) {
                    Spacer(Modifier.height(2.dp))
                    Text(
                        text = "+${session.xpEarned} XP",
                        style = StudyMasterTypography.labelSmall.copy(
                            fontFamily = EnglishFontFamily,
                            fontWeight = FontWeight.Bold,
                            color = XpGain,
                        ),
                    )
                }
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// 10. Weekly Goal Card — progress bar + weak subjects
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun WeeklyGoalCard(
    weeklyProgress: WeeklyGoalProgress,
    modifier: Modifier = Modifier,
) {
    val progressFraction = weeklyProgress.progressPercentage / 100f

    val animatedProgress by animateFloatAsState(
        targetValue = progressFraction,
        animationSpec = LocalMotion.current.progressFill,
        label = "weeklyProgress",
    )

    val hoursStudied = weeklyProgress.studiedMinutes / 60
    val remainingMinutes = (weeklyProgress.studiedMinutes % 60).toInt()
    val hoursGoal = weeklyProgress.targetMinutes / 60

    GlassmorphicCard(
        modifier = modifier.fillMaxWidth(),
        variant = GlassCardVariant.OUTLINED,
        padding = 16.dp,
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "সাপ্তাহিক লক্ষ্য",
                    style = StudyMasterTypography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    text = "${weeklyProgress.progressPercentage.toInt().toBengaliDigits()}%",
                    style = StudyMasterTypography.labelMedium.copy(
                        fontFamily = EnglishFontFamily,
                        fontWeight = FontWeight.Bold,
                        color = if (progressFraction >= 1f) Success else MaterialTheme.colorScheme.primary,
                    ),
                )
            }

            Spacer(Modifier.height(10.dp))

            // Progress bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
                    .clip(RoundedCornerShape(100.dp))
                    .background(XpBarBg),
                contentAlignment = Alignment.CenterStart,
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(animatedProgress)
                        .height(10.dp)
                        .clip(RoundedCornerShape(100.dp))
                        .background(Brush.horizontalGradient(listOf(Primary, PrimaryLight))),
                )
            }

            Spacer(Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = buildString {
                        if (hoursStudied > 0) append("${hoursStudied.toBengaliDigits()} ঘণ্টা ")
                        append("${remainingMinutes.toBengaliDigits()} মিনিট পড়েছেন")
                    },
                    style = StudyMasterTypography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Text(
                    text = "লক্ষ্য: ${hoursGoal.toBengaliDigits()} ঘণ্টা",
                    style = StudyMasterTypography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }

            // Weak subjects
            if (weeklyProgress.weakSubjects.isNotEmpty()) {
                Spacer(Modifier.height(10.dp))
                Text(
                    text = "দুর্বল বিষয়:",
                    style = StudyMasterTypography.labelSmall,
                    color = Warning,
                )
                Spacer(Modifier.height(6.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    weeklyProgress.weakSubjects.forEachIndexed { index, subject ->
                        SubjectChip(subjectName = subject, compact = true, colorIndex = index)
                    }
                }
            }
        }
    }
}