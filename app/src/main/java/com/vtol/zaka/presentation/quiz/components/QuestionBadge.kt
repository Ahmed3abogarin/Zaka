package com.vtol.zaka.presentation.quiz.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vtol.zaka.ui.theme.Purple100
import com.vtol.zaka.ui.theme.Purple500

@Composable
fun QuestionBadge(current: Int, total: Int) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(Purple100)
            .padding(horizontal = 14.dp, vertical = 5.dp),
    ) {
        Text(
            text = "السؤال $current من $total",
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = Purple500,
        )
    }
}