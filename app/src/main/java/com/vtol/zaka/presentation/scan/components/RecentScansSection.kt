package com.vtol.zaka.presentation.scan.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vtol.zaka.domain.models.QuizSession
import com.vtol.zaka.ui.theme.BorderDefault
import com.vtol.zaka.ui.theme.TextPrimary
import com.vtol.zaka.ui.theme.TextSecond

@Composable
fun RecentScansSection(
    modifier: Modifier = Modifier,
    scans: List<QuizSession>,
    onClick: (Int) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .border(1.dp, BorderDefault, RoundedCornerShape(16.dp))
            .background(Color.White),
    ) {
        Text(
            text = "عمليات مسح سابقة",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
        )

        HorizontalDivider(color = BorderDefault, thickness = 0.5.dp)

        if (scans.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text("لا توجد عمليات مسح بعد", fontSize = 13.sp, color = TextSecond)
            }
        } else {
            scans.forEachIndexed { index, scan ->
                RecentScanItem(scan = scan) { onClick(scan.id) }
                if (index < scans.lastIndex) {
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        color = BorderDefault,
                        thickness = 0.5.dp,
                    )
                }
            }
        }
    }
}