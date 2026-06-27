package com.vtol.zaka.presentation.graph

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem<T : Any>(
    val title: String,
    val icon: ImageVector,
    val route: T
)