package com.vtol.zaka.presentation.result.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Speed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vtol.zaka.ui.theme.Green500
import com.vtol.zaka.ui.theme.GreenLight
import com.vtol.zaka.ui.theme.Purple100
import com.vtol.zaka.ui.theme.Purple500
import com.vtol.zaka.ui.theme.RedError
import com.vtol.zaka.ui.theme.RedLight

@Composable
fun StatsRow(
    score: Int,
    wrong: Int,
    correct: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        // Accuracy
        StatCard(
            icon = Icons.Outlined.Speed,
            iconTint = Purple500,
            iconBg = Purple100,
            value = "$score%",
            valueColor = Purple500,
            label = "الدقة",
            modifier = Modifier.weight(1f),
        )

        // Wrong
        StatCard(
            icon = Icons.Default.Close,
            iconTint = RedError,
            iconBg = RedLight,
            value = "خطأ $wrong",
            valueColor = RedError,
            label = "إجابات",
            modifier = Modifier.weight(1f),
        )

        // Correct
        StatCard(
            icon = Icons.Default.Check,
            iconTint = Green500,
            iconBg = GreenLight,
            value = "صحيح $correct",
            valueColor = Green500,
            label = "إجابات",
            modifier = Modifier.weight(1f),
        )
    }
}