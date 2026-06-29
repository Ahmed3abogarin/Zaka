package com.vtol.zaka.presentation.graph

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
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
import kotlinx.serialization.Serializable

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val tabs = listOf(
        BottomNavItem("Home", Icons.Default.Home, HomeRoute),
        BottomNavItem("Scan", Icons.Default.Person, ScanRoute),
        BottomNavItem("Progress", Icons.Default.BarChart, ProgressRoute)
    )


    // 2. Extract the current destination route path
    Scaffold(
        bottomBar = {
            // Observe the current back stack to determine which tab is selected
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            val showBottomNav = currentDestination?.hasRoute<HomeRoute>() == true ||
                    currentDestination?.hasRoute<ScanRoute>() == true || currentDestination?.hasRoute<ProgressRoute>() == true

            if (showBottomNav) {
                val selectedIndex = tabs.indexOfFirst { tab ->
                    currentDestination.hierarchy.any { it.hasRoute(tab.route::class) }
                }.coerceAtLeast(0)

                ArabicBottomNavBar(
                    selectedIndex = selectedIndex,
                    onItemSelected = { index ->
                        navController.navigate(tabs[index].route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )

            }
        }
    ) { innerPadding ->
        // The NavHost respects the Scaffold's padding so content isn't hidden behind the bar
        NavHost(
            navController = navController,
            startDestination = HomeRoute,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<HomeRoute> {
                val viewModel: HomeViewModel = hiltViewModel()

                val recentQuizzes by viewModel.recentQuizzes.collectAsState()

                HomeScreen(recentQuizzes) { id ->
                    navController.navigate(QuizDetailsRoute(id))
                }
            }

            navigation<QuizGraphRoute>(startDestination = ScanRoute) {

                composable<ScanRoute> { backStackEntry ->
                    val parentEntry = remember(backStackEntry) {
                        navController.getBackStackEntry<QuizGraphRoute>()
                    }
                    val viewModel: QuizViewModel = hiltViewModel(parentEntry)
                    ScanScreen(viewModel = viewModel) { navController.navigate(QuizRoute) }
                }

                composable<QuizRoute> { backStackEntry ->
                    val parentEntry = remember(backStackEntry) {
                        navController.getBackStackEntry<QuizGraphRoute>()
                    }
                    val viewModel: QuizViewModel = hiltViewModel(parentEntry)
                    QuizScreen(
                        viewModel = viewModel,
                        navigateToResult = { navController.navigate(ResultRoute) })
                }

                composable<ResultRoute> { backStackEntry ->
                    val parentEntry = remember(backStackEntry) {
                        navController.getBackStackEntry<QuizGraphRoute>()
                    }

                    val viewModel: QuizViewModel = hiltViewModel(parentEntry)

                    val state by viewModel.state.collectAsState()

                    ResultScreen(
                        state = state,
                        onHome = { navController.navigate(HomeRoute) },
                        retakeQuiz = {
                            viewModel.retakeQuiz()
                            navController.navigate(QuizRoute)
                        }
                    )

                }

                composable<ProgressRoute> {
                    val viewModel: ProgressViewModel = hiltViewModel()

                    val stats by viewModel.stats.collectAsState()

                    ProgressScreen(stats)
                }

                composable<QuizDetailsRoute> { backStackEntry ->
                    val parentEntry = remember(backStackEntry) {
                        navController.getBackStackEntry<QuizGraphRoute>()
                    }
                    val quizViewModel: QuizViewModel = hiltViewModel(parentEntry)

                    val viewModel: QuizDetailsViewModel = hiltViewModel()
                    val state by viewModel.state.collectAsState()
                    QuizDetailScreen(
                        state = state,
                        onNavigateToQuiz = { id ->
                            quizViewModel.retakeFromHistory(id)
                            navController.navigate(QuizRoute)
                        }
                    )
                }
            }
        }
    }
}


@Serializable
data class QuizDetailsRoute(val sessionId: Int)

@Serializable
object ProgressRoute

@Serializable
object ResultRoute

@Serializable
object QuizGraphRoute

@Serializable
object QuizRoute

@Serializable
object ScanRoute

@Serializable
object HomeRoute