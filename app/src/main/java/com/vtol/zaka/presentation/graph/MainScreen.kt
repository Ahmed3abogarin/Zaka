package com.vtol.zaka.presentation.graph

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShortNavigationBarItem
import androidx.compose.material3.Text
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
import com.vtol.zaka.presentation.home.HomeScreen
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
    )


    // 2. Extract the current destination route path

    Scaffold(
        bottomBar = {
            // Observe the current back stack to determine which tab is selected
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            val showBottomNav = currentDestination?.hasRoute<HomeRoute>() == true ||
                    currentDestination?.hasRoute<ScanRoute>() == true
            if (showBottomNav) {
                NavigationBar {
                    tabs.forEach { tab ->
                        // Navigation 2.8+ way to check if the current route matches the tab
                        val isSelected = currentDestination.hierarchy.any {
                            it.hasRoute(tab.route::class)
                        }

                        ShortNavigationBarItem(
                            modifier = Modifier.weight(1f),
                            selected = isSelected,
                            label = { Text(tab.title) },
                            icon = { Icon(tab.icon, contentDescription = tab.title) },
                            onClick = {
                                navController.navigate(tab.route) {
                                    // Pop up to the start destination of the graph to
                                    // avoid building up a large stack of destinations
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    // Avoid multiple copies of the same destination
                                    launchSingleTop = true
                                    // Restore state when reselecting a previously selected item
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        // The NavHost respects the Scaffold's padding so content isn't hidden behind the bar
        NavHost(
            navController = navController,
            startDestination = HomeRoute,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<HomeRoute> { HomeScreen() }

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
                        onHome = { navController.navigate(HomeRoute)},
                        retakeQuiz = {
                            viewModel.retakeQuiz()
                            navController.navigate(QuizRoute)
                        }
                    )

                }
            }
        }
    }
}

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