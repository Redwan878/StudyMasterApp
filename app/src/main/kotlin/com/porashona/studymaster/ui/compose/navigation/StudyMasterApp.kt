package com.porashona.studymaster.ui.compose.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.porashona.studymaster.data.preferences.PreferencesManager
import com.porashona.studymaster.ui.compose.components.BottomNavDestination
import com.porashona.studymaster.ui.compose.components.NavBadge
import com.porashona.studymaster.ui.compose.components.StreakFireBadge
import com.porashona.studymaster.ui.compose.components.StudyMasterBottomNavBar
import com.porashona.studymaster.ui.compose.theme.EnglishFontFamily
import com.porashona.studymaster.ui.compose.theme.GlassBorderDark
import com.porashona.studymaster.ui.compose.theme.GlassBorderLight
import com.porashona.studymaster.ui.compose.theme.GlassDarkAlpha80
import com.porashona.studymaster.ui.compose.theme.GlassLightAlpha90
import com.porashona.studymaster.ui.compose.theme.Primary
import com.porashona.studymaster.ui.compose.theme.StudyMasterTheme
import com.porashona.studymaster.ui.compose.theme.ThemeMode
import com.porashona.studymaster.ui.compose.theme.isDark
import com.porashona.studymaster.ui.compose.viewmodels.HomeViewModel
import kotlinx.coroutines.flow.map

// ═══════════════════════════════════════════════════════════════════════════════
// StudyMasterApp — Root composable for the Compose UI
//
// Wraps everything in StudyMasterTheme, provides the Scaffold with:
//   - Glassmorphic top bar with app title, streak badge, notifications
//   - Bottom navigation bar with 5 primary destinations
//   - NavHost for all 23 screens
//
// Call this from MainActivity's setContent {}.
// ═══════════════════════════════════════════════════════════════════════════════

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudyMasterApp(
    preferencesManager: PreferencesManager,
    modifier: Modifier = Modifier,
) {
    // ── Theme mode from preferences ──────────────────────────────────────────
    val darkModeString by preferencesManager.darkMode.collectAsState(initial = "system")
    val themeMode = remember(darkModeString) {
        when (darkModeString) {
            "light" -> ThemeMode.LIGHT
            "amoled" -> ThemeMode.AMOLED
            "dark"   -> ThemeMode.DARK
            else     -> ThemeMode.SYSTEM
        }
    }

    StudyMasterTheme(themeMode = themeMode) {
        val isDark = MaterialTheme.isDark

        // ── System UI controller (status/nav bar) ────────────────────────────
        val systemUiController = rememberSystemUiController()
        val surfaceColor = MaterialTheme.colorScheme.surface
        val statusBarColor = MaterialTheme.colorScheme.surface
        val navBarColor = if (isDark) GlassDarkAlpha80 else GlassLightAlpha90
        val isLight = !isDark

        androidx.compose.runtime.SideEffect {
            systemUiController.setStatusBarColor(
                color = statusBarColor,
                darkIcons = isLight,
            )
            systemUiController.setNavigationBarColor(
                color = navBarColor,
                darkIcons = isLight,
            )
        }

        // ── Navigation controller ────────────────────────────────────────────
        val navController = rememberNavController()

        // ── Scroll behaviour for top bar ─────────────────────────────────────
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

        // ── Current route (for bottom bar highlighting) ──────────────────────
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        // ── HomeViewModel for streak badge in top bar ────────────────────────
        val homeViewModel: HomeViewModel = hiltViewModel()
        val currentStreak by homeViewModel.currentStreak
            .collectAsState(initial = 0)

        // ── Pending task count for badge ─────────────────────────────────────
        val pendingTaskCount by homeViewModel.upcomingTasks
            .map { it.size }
            .collectAsState(initial = 0)

        // ── Determine if bottom bar should be visible ────────────────────────
        val showBottomBar by remember(currentRoute) {
            derivedStateOf {
                currentRoute in ScreenRoute.bottomNavRoutes
            }
        }

        // ── Badge map for bottom nav ─────────────────────────────────────────
        val navBadges = remember(pendingTaskCount) {
            mapOf(
                BottomNavDestination.HOME to NavBadge(
                    showDot = currentStreak > 0,
                ),
                BottomNavDestination.TOOLS to NavBadge(
                    showDot = pendingTaskCount > 0,
                ),
            )
        }

        Scaffold(
            modifier = modifier
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .background(MaterialTheme.colorScheme.background),
            topBar = {
                StudyMasterTopBar(
                    currentRoute = currentRoute,
                    scrollBehavior = scrollBehavior,
                    streakDays = currentStreak,
                    onNotificationClick = {
    navController.navigate(ScreenRoute.Notifications.route) {
        popUpTo(ScreenRoute.Home.route) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
},
                    onProfileClick = {
                        navController.navigate(ScreenRoute.Profile.route) {
                            popUpTo(ScreenRoute.Home.route) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                )
            },
            bottomBar = {
                AnimatedVisibility(
                    visible = showBottomBar,
                    enter = fadeIn() + scaleIn(initialScale = 0.95f),
                    exit = fadeOut() + scaleOut(targetScale = 0.95f),
                ) {
                    StudyMasterBottomNavBar(
                        currentRoute = currentRoute,
                        onDestinationSelected = { destination ->
                            navController.navigate(destination.route) {
                                // Pop up to home and save state so back works
                                popUpTo(ScreenRoute.Home.route) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        badges = navBadges,
                    )
                }
            },
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onSurface,
        ) { innerPadding ->
            // ── NavHost fills the remaining space ───────────────────────────
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
            ) {
                StudyMasterNavigation(
                    navController = navController,
                    startDestination = ScreenRoute.Home.route,
                )
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// StudyMasterTopBar — glassmorphic app bar
//
// Shows:
//   - App logo/name (left)
//   - Streak fire badge (when streak > 0)
//   - Notification icon (right)
//   - Profile avatar (right)
// ═══════════════════════════════════════════════════════════════════════════════

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun StudyMasterTopBar(
    currentRoute: String?,
    scrollBehavior: TopAppBarScrollBehavior,
    streakDays: Int,
    onNotificationClick: () -> Unit,
    onProfileClick: () -> Unit,
) {
    val isDark = MaterialTheme.isDark
    val glassBg = if (isDark) GlassDarkAlpha80 else GlassLightAlpha90
    val glassBorder = if (isDark) GlassBorderDark else GlassBorderLight

    // Screen title based on current route
    val screenTitle = remember(currentRoute) {
        when (currentRoute) {
            ScreenRoute.Home.route           -> "StudyMaster"
            ScreenRoute.Timer.route          -> "টাইমার"
            ScreenRoute.Routine.route        -> "রুটিন"
            ScreenRoute.Calendar.route       -> "ক্যালেন্ডার"
            ScreenRoute.Notes.route          -> "নোটস"
            ScreenRoute.Tasks.route          -> "টাস্কস"
            ScreenRoute.Goals.route          -> "গোলস"
            ScreenRoute.Exams.route          -> "পরীক্ষা"
            ScreenRoute.Flashcards.route     -> "ফ্ল্যাশকার্ড"
            ScreenRoute.Assistant.route      -> "সহকারী"
            ScreenRoute.Practice.route       -> "অনুশীলন"
            ScreenRoute.Analytics.route      -> "বিশ্লেষণ"
            ScreenRoute.Gamification.route   -> "গেমিফিকেশন"
            ScreenRoute.Settings.route       -> "সেটিংস"
            ScreenRoute.Profile.route        -> "প্রোফাইল"
            ScreenRoute.Tools.route          -> "টুলস"
            ScreenRoute.Resources.route      -> "রিসোর্স"
            ScreenRoute.Collaboration.route  -> "সহযোগিতা"
            ScreenRoute.Blocker.route        -> "অ্যাপ ব্লকার"
            ScreenRoute.Music.route          -> "সঙ্গীত"
            ScreenRoute.Achievements.route   -> "অর্জনসমূহ"
            ScreenRoute.Challenges.route     -> "চ্যালেঞ্জ"
            ScreenRoute.Insights.route       -> "ইনসাইটস"
            ScreenRoute.BreakCoach.route     -> "বিরতি কোচ"
            else -> "StudyMaster"
        }
    }

    val isHome = currentRoute == ScreenRoute.Home.route

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding(),
        color = glassBg,
        shadowElevation = if (isDark) 8.dp else 4.dp,
        tonalElevation = 2.dp,
    ) {
        TopAppBar(
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    if (isHome) {
                        // Logo icon on home
                        Surface(
                            modifier = Modifier.size(32.dp),
                            shape = CircleShape,
                            color = Primary.copy(alpha = 0.15f),
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.fillMaxSize(),
                            ) {
                                Text(
                                    text = "S",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontFamily = EnglishFontFamily,
                                        fontWeight = FontWeight.Bold,
                                        color = Primary,
                                    ),
                                )
                            }
                        }
                        Spacer(Modifier.width(10.dp))
                        Text(
                            text = "StudyMaster",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontFamily = EnglishFontFamily,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface,
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    } else {
                        Text(
                            text = screenTitle,
                            style = MaterialTheme.typography.titleLarge,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }
            },
            actions = {
                // Streak badge (visible when streak > 0)
                if (streakDays > 0) {
                    StreakFireBadge(
                        streakDays = streakDays,
                        modifier = Modifier.padding(end = 4.dp),
                    )
                    Spacer(Modifier.width(4.dp))
                }

                // Notification icon
                IconButton(
                    onClick = onNotificationClick,
                    modifier = Modifier.size(40.dp),
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Notifications,
                        contentDescription = "নোটিফিকেশন",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }

                // Profile avatar
                IconButton(
                    onClick = onProfileClick,
                    modifier = Modifier.size(40.dp),
                ) {
                    Surface(
                        modifier = Modifier.size(28.dp),
                        shape = CircleShape,
                        color = Primary.copy(alpha = 0.2f),
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize(),
                        ) {
                            Text(
                                text = "শ",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Primary,
                                ),
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                }

                Spacer(Modifier.width(8.dp))
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent,
                scrolledContainerColor = Color.Transparent,
                titleContentColor = MaterialTheme.colorScheme.onSurface,
                actionIconContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
            ),
            scrollBehavior = scrollBehavior,
        )
    }
}