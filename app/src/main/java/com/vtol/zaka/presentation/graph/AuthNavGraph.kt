package com.vtol.zaka.presentation.graph

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
                event = viewModel::onEvent
            ) {
                navController.navigate(MainScreenRoute)
            }
        }
        composable<LoginRoute> {
            Box(modifier=  Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                Text("LOGIN SCREEN")
            }
        }
    }
}

@Serializable
object LoginRoute

@Serializable
object SignUpRoute