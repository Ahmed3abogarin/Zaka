package com.vtol.zaka.presentation.graph

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vtol.zaka.domain.models.auth.AuthState
import com.vtol.zaka.presentation.onboarding.OnboardingScreen
import com.vtol.zaka.presentation.splash.SplashScreen
import kotlinx.serialization.Serializable

@Composable
fun AppHost() {

    val authViewModel: AuthViewModel = hiltViewModel()
    val authState by authViewModel.authState.collectAsState()

    val navController = rememberNavController()

    var splashFinished by remember {
        mutableStateOf(false)
    }


    LaunchedEffect(splashFinished, authState) {

        if (!splashFinished) return@LaunchedEffect

        when (authState) {

            is AuthState.Authenticated -> {
                navController.navigate(MainScreenRoute) {
                    popUpTo<SplashRoute> {
                        inclusive = true
                    }
                }
            }

            is AuthState.Unauthenticated -> {
                navController.navigate(AuthRoute) {
                    popUpTo<SplashRoute> {
                        inclusive = true
                    }
                }
            }

            is AuthState.OnBoarding -> {
                navController.navigate(OnboardingRoute) {
                    popUpTo<SplashRoute> {
                        inclusive = true
                    }
                }
            }

            is AuthState.Error -> {
                navController.navigate(ErrorRoute) {
                    popUpTo<SplashRoute> {
                        inclusive = true
                    }
                }
            }

            AuthState.Loading -> Unit
        }
    }


    NavHost(
        navController = navController,
        startDestination = SplashRoute
    ) {

        composable<SplashRoute> {

            SplashScreen(
                onFinished = {
                    splashFinished = true
                }
            )
        }


        composable<OnboardingRoute> {
            OnboardingScreen {
                authViewModel.completeOnBoarding()
            }
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