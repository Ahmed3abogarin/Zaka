package com.vtol.zaka.presentation.scan.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vtol.zaka.ui.theme.BorderColor
import com.vtol.zaka.ui.theme.Purple100
import com.vtol.zaka.ui.theme.Purple500
import com.vtol.zaka.ui.theme.Teal100
import com.vtol.zaka.ui.theme.Teal400
import com.vtol.zaka.ui.theme.TextSecond

@Composable
fun AiTipCard(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .border(1.dp, Purple100, RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(16.dp),
    ) {
        // Header row: sparkle icon + label
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            Icon(
                imageVector = Icons.Outlined.AutoAwesome,
                contentDescription = null,
                tint = Purple500,
                modifier = Modifier.size(18.dp),
            )
            Text(
                text       = "نصيحة الذكاء الاصطناعي",
                fontSize   = 14.sp,
                fontWeight = FontWeight.Bold,
                color      = Purple500,
            )
        }

        Spacer(Modifier.height(10.dp))

        Text(
            text      = "تأكد من أن الإضاءة جيدة عند استخدام الكاميرا. الذكاء الاصطناعي يمكنه قراءة خط اليد الواضح وتحويله إلى ملخصات ذكية.",
            fontSize  = 13.sp,
            color     = TextSecond,
            lineHeight = 21.sp,
        )

        Spacer(Modifier.height(14.dp))

        HorizontalDivider(color = BorderColor, thickness = 0.5.dp)

        Spacer(Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text     = "الدقة الحالية للكاميرا",
                fontSize = 12.sp,
                color    = TextSecond,
            )
            // Accuracy badge
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(Teal100)
                    .padding(horizontal = 12.dp, vertical = 4.dp),
            ) {
                Text(
                    text       = "عالية جداً",
                    fontSize   = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color      = Teal400,
                )
            }

        }
    }
}