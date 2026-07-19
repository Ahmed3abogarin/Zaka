package com.vtol.zaka.presentation.graph

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vtol.zaka.domain.models.auth.AuthState
import com.vtol.zaka.presentation.onboarding.OnboardingScreen
import com.vtol.zaka.ui.theme.Purple700
import kotlinx.serialization.Serializable

@Composable
fun AppHost() {
    val authViewModel: AuthViewModel = hiltViewModel()
    val authState by authViewModel.authState.collectAsState()

    val navController = rememberNavController()

    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Loading -> {} // stay on splash
            is AuthState.Authenticated -> navController.navigate(MainScreenRoute) {
                popUpTo(0) { inclusive = true }
            }

            is AuthState.Unauthenticated -> navController.navigate(AuthRoute) {
                popUpTo(0) { inclusive = true }
            }

            is AuthState.OnBoarding -> navController.navigate(OnboardingRoute) {
                popUpTo(0) { inclusive = true }
            }

            is AuthState.Error -> navController.navigate(ErrorRoute) {
                popUpTo(0) { inclusive = true }
            }
        }
    }

    NavHost(navController = navController, startDestination = SplashRoute) {
        composable<SplashRoute> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Purple700),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "This is Splash"
                )
            }
        }

        composable<OnboardingRoute> {
            OnboardingScreen { authViewModel.completeOnBoarding() }
        }

        composable<AuthRoute> {
            AuthNavGraph()
        }

        composable<MainScreenRoute> {
            MainScreen()
        }

    }
}

@Serializable
object OnboardingRoute

@Serializable
object ErrorRoute

@Serializable
object SplashRoute

@Serializable
object AuthRoute

@Serializable
object MainScreenRoute