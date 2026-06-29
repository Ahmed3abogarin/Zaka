package com.vtol.zaka.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material.icons.outlined.UploadFile
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vtol.zaka.ui.theme.Purple500
import com.vtol.zaka.ui.theme.Teal400

@Composable
fun StartNowRow(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        // Camera card
        ActionCard(
            label = "صوّر صفحة",
            icon = Icons.Outlined.CameraAlt,
            tint = Teal400,
            modifier = Modifier.weight(1f),
        )
        // PDF card
        ActionCard(
            label = "رفع PDF",
            icon = Icons.Outlined.UploadFile,
            tint = Purple500,
            modifier = Modifier.weight(1f),
        )
    }
}