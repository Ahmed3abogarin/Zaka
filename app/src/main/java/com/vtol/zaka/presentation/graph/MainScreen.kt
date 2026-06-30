package com.vtol.zaka.presentation.graph

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.vtol.zaka.presentation.details.QuizDetailScreen
import com.vtol.zaka.presentation.details.QuizDetailsViewModel
import com.vtol.zaka.presentation.graph.components.ArabicBottomNavBar
import com.vtol.zaka.presentation.home.HomeScreen
import com.vtol.zaka.presentation.home.HomeViewModel
import com.vtol.zaka.presentation.progress.ProgressScreen
import com.vtol.zaka.presentation.progress.ProgressViewModel
import com.vtol.zaka.presentation.quiz.QuizScreen
import com.vtol.zaka.presentation.quiz.QuizViewModel
import com.vtol.zaka.presentation.result.ResultScreen
import com.vtol.zaka.presentation.scan.ScanScreen
import com.vtol.zaka.presentation.viewer.FileViewerScreen
import kotlinx.serialization.Serializable

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    // ── Shared ViewModel scoped to the activity ───────────────────────────
    val quizViewModel: QuizViewModel = hiltViewModel()

    val tabs = listOf(
        BottomNavItem("Home",     Icons.Default.Home,     HomeRoute),
        BottomNavItem("Scan",     Icons.Default.Person,   ScanRoute),
        BottomNavItem("Progress", Icons.Default.BarChart, ProgressRoute),
    )

    Scaffold(
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            val showBottomNav =
                currentDestination?.hasRoute<HomeRoute>() == true ||
                        currentDestination?.hasRoute<ScanRoute>() == true ||
                        currentDestination?.hasRoute<ProgressRoute>() == true

            AnimatedVisibility(
                visible = showBottomNav,
                enter   = slideInVertically(animationSpec = tween(200)) { it },
                exit    = slideOutVertically(animationSpec = tween(200)) { it },
            ) {
                val selectedIndex = tabs.indexOfFirst { tab ->
                    currentDestination?.hierarchy?.any {
                        it.hasRoute(tab.route::class)
                    } == true
                }.coerceAtLeast(0)

                ArabicBottomNavBar(
                    selectedIndex  = selectedIndex,
                    onItemSelected = { index ->
                        navController.navigate(tabs[index].route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState    = true
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController    = navController,
            startDestination = HomeRoute,
            modifier         = Modifier.padding(innerPadding),
        ) {

            // ── Home ──────────────────────────────────────────────────────
            composable<HomeRoute> {
                val homeViewModel: HomeViewModel = hiltViewModel()
                val recentQuizzes by homeViewModel.recentQuizzes.collectAsState()

                HomeScreen(
                    recentQuizzes     = recentQuizzes,
                    navigateToDetails = { id ->
                        navController.navigate(QuizDetailsRoute(id))
                    },
                    onPdfSelected     = { bytes, name ->
                        quizViewModel.generateFromPdf(bytes, name)
                        navController.navigate(QuizRoute)
                    },
                    onImageCaptured   = { bitmap ->
                        quizViewModel.generateFromImage(bitmap)
                        navController.navigate(QuizRoute)
                    },
                )
            }

            // ── Scan ──────────────────────────────────────────────────────
            composable<ScanRoute> {
                ScanScreen(
                    viewModel        = quizViewModel,
                    navigateToViewer = { sessionId ->
                        navController.navigate(FileViewerRoute(sessionId))
                    },
                    navigateToQuiz   = {
                        navController.navigate(QuizRoute)
                    },
                )
            }

            // ── Quiz ──────────────────────────────────────────────────────
            composable<QuizRoute> {
                QuizScreen(
                    viewModel        = quizViewModel,
                    navigateToResult = {
                        navController.navigate(ResultRoute)
                    },
                    navigateUp = {
                        navController.popBackStack()
                    }
                )
            }

            // ── Result ────────────────────────────────────────────────────
            composable<ResultRoute> {
                val state by quizViewModel.state.collectAsState()
                ResultScreen(
                    state      = state,
                    onHome     = {
                        navController.navigate(HomeRoute) {
                            popUpTo(HomeRoute) { inclusive = false }
                        }
                    },
                    retakeQuiz = {
                        quizViewModel.retakeQuiz()
                        navController.navigate(QuizRoute)
                    },
                )
            }

            // ── Progress ──────────────────────────────────────────────────
            composable<ProgressRoute> {
                val progressViewModel: ProgressViewModel = hiltViewModel()
                val stats by progressViewModel.stats.collectAsState()
                ProgressScreen(stats)
            }

            // ── Quiz Details (history) ────────────────────────────────────
            composable<QuizDetailsRoute> {
                val detailViewModel: QuizDetailsViewModel = hiltViewModel()
                val state by detailViewModel.state.collectAsState()

                QuizDetailScreen(
                    state            = state,
                    onNavigateToQuiz = { id ->
                        quizViewModel.retakeFromHistory(id)
                        navController.navigate(QuizRoute)
                    },
                    onBack           = {
                        navController.popBackStack()
                    },
                )
            }

            // ── File Viewer ───────────────────────────────────────────────
            composable<FileViewerRoute> {
                FileViewerScreen {
                    navController.popBackStack()
                }
            }
        }
    }
}

// ── Routes ────────────────────────────────────────────────────────────────────
@Serializable object HomeRoute
@Serializable object ScanRoute
@Serializable object QuizRoute
@Serializable object ResultRoute
@Serializable object ProgressRoute
@Serializable data class QuizDetailsRoute(val sessionId: Int)
@Serializable data class FileViewerRoute(val sessionId: Int)