package com.porashona.studymaster.ui.compose.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.porashona.studymaster.ui.ComposeMainActivity
import com.porashona.studymaster.ui.compose.screens.home.HomeScreen
import com.porashona.studymaster.ui.compose.screens.timer.TimerScreen
import com.porashona.studymaster.ui.compose.screens.routine.RoutineScreen
import com.porashona.studymaster.ui.compose.screens.calendar.CalendarScreen
import com.porashona.studymaster.ui.compose.screens.notes.NotesScreen
import com.porashona.studymaster.ui.compose.screens.tasks.TasksScreen
import com.porashona.studymaster.ui.compose.screens.goals.GoalsScreen
import com.porashona.studymaster.ui.compose.screens.exams.ExamsScreen
import com.porashona.studymaster.ui.compose.screens.flashcards.FlashcardsScreen
import com.porashona.studymaster.ui.compose.screens.assistant.AssistantScreen
import com.porashona.studymaster.ui.compose.screens.practice.PracticeTestScreen
import com.porashona.studymaster.ui.compose.screens.analytics.AnalyticsScreen
import com.porashona.studymaster.ui.compose.screens.gamification.GamificationScreen
import com.porashona.studymaster.ui.compose.screens.settings.SettingsScreen
import com.porashona.studymaster.ui.compose.screens.profile.ProfileScreen
import com.porashona.studymaster.ui.compose.screens.tools.ToolsScreen
import com.porashona.studymaster.ui.compose.screens.resources.ResourcesScreen
import com.porashona.studymaster.ui.compose.screens.collaboration.CollaborationScreen
import com.porashona.studymaster.ui.compose.screens.BlockerScreen
import com.porashona.studymaster.ui.compose.screens.MusicScreen
import com.porashona.studymaster.ui.compose.screens.AchievementsScreen
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

// ═══════════════════════════════════════════════════════════════════════════════
// StudyMaster Navigation Routes
// ═══════════════════════════════════════════════════════════════════════════════

sealed interface ScreenRoute {
    val route: String
    val displayNameBn: String get() = ""

    data object Home : ScreenRoute {
        override val route = "home"
        override val displayNameBn = "হোম"
    }

    data object Timer : ScreenRoute {
        override val route = "timer"
        override val displayNameBn = "টাইমার"
    }

    data object Routine : ScreenRoute {
        override val route = "routine"
        override val displayNameBn = "রুটিন"
    }

    data object Calendar : ScreenRoute {
        override val route = "calendar"
        override val displayNameBn = "ক্যালেন্ডার"
    }

    data object Notes : ScreenRoute {
        override val route = "notes"
        override val displayNameBn = "নোটস"
    }

    data object Tasks : ScreenRoute {
        override val route = "tasks"
        override val displayNameBn = "টাস্কস"
    }

    data object Goals : ScreenRoute {
        override val route = "goals"
        override val displayNameBn = "গোলস"
    }

    data object Exams : ScreenRoute {
        override val route = "exams"
        override val displayNameBn = "পরীক্ষা"
        const val EXAM_ID_ARG = "examId"
        val routeWithArg = "$route/{$EXAM_ID_ARG}"
        fun createRoute(examId: Long) = "$route/$examId"
    }

    data object Flashcards : ScreenRoute {
        override val route = "flashcards"
        override val displayNameBn = "ফ্ল্যাশকার্ড"
    }

    data object Assistant : ScreenRoute {
        override val route = "assistant"
        override val displayNameBn = "সহকারী"
    }

    data object Practice : ScreenRoute {
        override val route = "practice"
        override val displayNameBn = "অনুশীলন"
    }

    data object Analytics : ScreenRoute {
        override val route = "analytics"
        override val displayNameBn = "বিশ্লেষণ"
    }

    data object Gamification : ScreenRoute {
        override val route = "gamification"
        override val displayNameBn = "গেমিফিকেশন"
    }

    data object Settings : ScreenRoute {
        override val route = "settings"
        override val displayNameBn = "সেটিংস"
    }

    data object Profile : ScreenRoute {
        override val route = "profile"
        override val displayNameBn = "প্রোফাইল"
    }

    data object Tools : ScreenRoute {
        override val route = "tools"
        override val displayNameBn = "টুলস"
    }

    data object Resources : ScreenRoute {
        override val route = "resources"
        override val displayNameBn = "রিসোর্স"
    }

    data object Collaboration : ScreenRoute {
        override val route = "collaboration"
        override val displayNameBn = "সহযোগিতা"
    }

    data object Blocker : ScreenRoute {
        override val route = "blocker"
        override val displayNameBn = "অ্যাপ ব্লকার"
    }

    data object Music : ScreenRoute {
        override val route = "music"
        override val displayNameBn = "সঙ্গীত"
    }

    data object Achievements : ScreenRoute {
        override val route = "achievements"
        override val displayNameBn = "অর্জনসমূহ"
    }

    data object Challenges : ScreenRoute {
        override val route = "challenges"
        override val displayNameBn = "চ্যালেঞ্জ"
    }

    data object ZenMode : ScreenRoute {
        override val route = "zen_mode"
        override val displayNameBn = "জেন মোড"
    }

    companion object {
        val allRoutes: List<ScreenRoute> = listOf(
            Home, Timer, Routine, Calendar, Notes, Tasks, Goals, Exams,
            Flashcards, Assistant, Practice, Analytics, Gamification, Settings,
            Profile, Tools, Resources, Collaboration, Blocker, Music,
            Achievements, Challenges, ZenMode,
        )

        val bottomNavRoutes = setOf(
            Home.route, Timer.route, Routine.route, Tools.route, Profile.route
        )
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Transition specs — spring physics for rewarding feel
// ═══════════════════════════════════════════════════════════════════════════════

private const val TRANSITION_MS = 350

private val enterTransition: AnimatedContentTransitionScope<*>.() -> EnterTransition = {
    slideInHorizontally(
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioNoBouncy,
            stiffness = Spring.StiffnessMediumLow,
        ),
        initialOffsetX = { it },
    ) + fadeIn(animationSpec = tween(TRANSITION_MS))
}

private val exitTransition: AnimatedContentTransitionScope<*>.() -> ExitTransition = {
    slideOutHorizontally(
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioNoBouncy,
            stiffness = Spring.StiffnessMediumLow,
        ),
        targetOffsetX = { -it / 3 },
    ) + fadeOut(animationSpec = tween(TRANSITION_MS / 2))
}

private val popEnterTransition: AnimatedContentTransitionScope<*>.() -> EnterTransition = {
    slideInHorizontally(
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioNoBouncy,
            stiffness = Spring.StiffnessMediumLow,
        ),
        initialOffsetX = { -it / 3 },
    ) + fadeIn(animationSpec = tween(TRANSITION_MS / 2))
}

private val popExitTransition: AnimatedContentTransitionScope<*>.() -> ExitTransition = {
    slideOutHorizontally(
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioNoBouncy,
            stiffness = Spring.StiffnessMediumLow,
        ),
        targetOffsetX = { it },
    ) + fadeOut(animationSpec = tween(TRANSITION_MS))
}

// ═══════════════════════════════════════════════════════════════════════════════
// Helpers
// ═══════════════════════════════════════════════════════════════════════════════

fun String.urlEncode(): String = URLEncoder.encode(this, StandardCharsets.UTF_8.name())
fun String.urlDecode(): String = URLDecoder.decode(this, StandardCharsets.UTF_8.name())

// ═══════════════════════════════════════════════════════════════════════════════
// StudyMasterNavigation — Full NavHost with all real screen composables
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
fun StudyMasterNavigation(
    navController: NavHostController,
    startDestination: String = ScreenRoute.Home.route,
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
    // ── Consume pending deep-link from ComposeMainActivity ──────────────
    LaunchedEffect(Unit) {
        val pending = ComposeMainActivity.consumePendingNavigation()
        if (!pending.isNullOrBlank()) {
            navController.navigate(pending) {
                popUpTo(ScreenRoute.Home.route) { saveState = true }
                launchSingleTop = true
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = enterTransition,
        exitTransition = exitTransition,
        popEnterTransition = popEnterTransition,
        popExitTransition = popExitTransition,
    ) {
        // ── Home ────────────────────────────────────────────────────────
        composable(ScreenRoute.Home.route) {
            HomeScreen(
                onNavigateToTimer = { navController.navigate(ScreenRoute.Timer.route) },
                onNavigateToNotes = { navController.navigate(ScreenRoute.Notes.route) },
                onNavigateToPractice = { navController.navigate(ScreenRoute.Practice.route) },
                onNavigateToFlashcards = { navController.navigate(ScreenRoute.Flashcards.route) },
                onNavigateToExams = { navController.navigate(ScreenRoute.Exams.route) },
                onNavigateToTasks = { navController.navigate(ScreenRoute.Tasks.route) },
                onNavigateToAssistant = { navController.navigate(ScreenRoute.Assistant.route) },
                onNavigateToRoutine = { navController.navigate(ScreenRoute.Routine.route) },
                onNavigateToCalendar = { navController.navigate(ScreenRoute.Calendar.route) },
                onNavigateToGoals = { navController.navigate(ScreenRoute.Goals.route) },
            )
        }

        // ── Timer / Pomodoro ────────────────────────────────────────────
        composable(ScreenRoute.Timer.route) {
            TimerScreen(
                onNavigateToZenMode = { navController.navigate(ScreenRoute.ZenMode.route) }
            )
        }

        // ── Zen Mode ────────────────────────────────────────────────────
        composable(ScreenRoute.ZenMode.route) {
            com.porashona.studymaster.ui.compose.screens.timer.ZenModeScreen(
                onExit = { _, _ -> navController.popBackStack() }
            )
        }

        // ── Routine ─────────────────────────────────────────────────────
        composable(ScreenRoute.Routine.route) {
            RoutineScreen()
        }

        // ── Calendar ────────────────────────────────────────────────────
        composable(ScreenRoute.Calendar.route) {
            CalendarScreen()
        }

        // ── Notes ───────────────────────────────────────────────────────
        composable(ScreenRoute.Notes.route) {
            NotesScreen()
        }

        // ── Tasks ───────────────────────────────────────────────────────
        composable(ScreenRoute.Tasks.route) {
            TasksScreen()
        }

        // ── Goals ───────────────────────────────────────────────────────
        composable(ScreenRoute.Goals.route) {
            GoalsScreen()
        }

        // ── Exams (list) ────────────────────────────────────────────────
        composable(ScreenRoute.Exams.route) {
            ExamsScreen()
        }

        // ── Exams (detail with ID) ──────────────────────────────────────
        composable(
            route = ScreenRoute.Exams.routeWithArg,
            arguments = listOf(navArgument(ScreenRoute.Exams.EXAM_ID_ARG) {
                type = NavType.LongType
            }),
        ) {
            ExamsScreen()
        }

        // ── Flashcards ──────────────────────────────────────────────────
        composable(ScreenRoute.Flashcards.route) {
            FlashcardsScreen()
        }

        // ── AI Assistant ────────────────────────────────────────────────
        composable(ScreenRoute.Assistant.route) {
            AssistantScreen()
        }

        // ── Practice Tests ──────────────────────────────────────────────
        composable(ScreenRoute.Practice.route) {
            PracticeTestScreen()
        }

        // ── Analytics ───────────────────────────────────────────────────
        composable(ScreenRoute.Analytics.route) {
            AnalyticsScreen()
        }

        // ── Gamification ────────────────────────────────────────────────
        composable(ScreenRoute.Gamification.route) {
            GamificationScreen()
        }

        // ── Settings ────────────────────────────────────────────────────
        composable(ScreenRoute.Settings.route) {
            SettingsScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // ── Profile ─────────────────────────────────────────────────────
        composable(ScreenRoute.Profile.route) {
            ProfileScreen()
        }

        // ── Tools Hub ───────────────────────────────────────────────────
        composable(ScreenRoute.Tools.route) {
            ToolsScreen()
        }

        // ── Resources ───────────────────────────────────────────────────
        composable(ScreenRoute.Resources.route) {
            ResourcesScreen()
        }

        // ── Collaboration ───────────────────────────────────────────────
        composable(ScreenRoute.Collaboration.route) {
            CollaborationScreen()
        }

        // ── App Blocker ─────────────────────────────────────────────────
        composable(ScreenRoute.Blocker.route) {
            BlockerScreen()
        }

        // ── Music ───────────────────────────────────────────────────────
        composable(ScreenRoute.Music.route) {
            MusicScreen()
        }

        // ── Achievements ────────────────────────────────────────────────
        composable(ScreenRoute.Achievements.route) {
            AchievementsScreen()
        }

        // ── Challenges ──────────────────────────────────────────────────
        composable(ScreenRoute.Challenges.route) {
            ChallengesScreen()
        }
    }
}