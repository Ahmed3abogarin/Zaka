package com.vtol.zaka.presentation.graph

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vtol.zaka.presentation.register.login.LoginScreen
import com.vtol.zaka.presentation.register.login.LoginViewModel
import com.vtol.zaka.presentation.register.signup.SignUpScreen
import com.vtol.zaka.presentation.register.signup.SignUpViewModel
import kotlinx.serialization.Serializable

@Composable
fun AuthNavGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = SignUpRoute) {
        composable<SignUpRoute> {
            val viewModel: SignUpViewModel = hiltViewModel()
            val state by viewModel.uiState.collectAsState()
            SignUpScreen(
                state = state,
                event = viewModel::onEvent,
                onLoginClick = {
                    Log.d("BUTTONCLICKS", "Navigate to Login screen button is clicked!")
                    navController.navigate(LoginRoute)
                }
            )
        }
        composable<LoginRoute> {
            val viewModel: LoginViewModel = hiltViewModel()
            val state by viewModel.uiState.collectAsState()

            LoginScreen(
                state = state,
                event = viewModel::onEvent,
                onSignUpClick = {
                    Log.d("BUTTONCLICKS", "Navigate to sign up screen button is clicked!")
                    navController.navigate(SignUpRoute)
                }
            )
        }
    }
}

@Serializable
object LoginRoute

@Serializable
object SignUpRoute