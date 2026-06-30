package com.vtol.zaka.presentation.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.DocumentScanner
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vtol.zaka.ui.theme.BorderColor
import com.vtol.zaka.ui.theme.TextPrimary
import com.vtol.zaka.ui.theme.TextSecond
import com.vtol.zaka.ui.theme.ZakaTheme

@Composable
fun ActionCard(
    label: String,
    description: String,
    icon: ImageVector,
    tint: Color,
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(width = 1.dp, color = BorderColor),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {

            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(tint.copy(alpha = 0.25f))
                    .size(46.dp)
            ) {
                Icon(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    imageVector = icon,
                    contentDescription = null,
                    tint = tint,
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 14.dp, top = 8.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = label,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary,
                )

                Text(
                    text = description,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Medium,
                    color = TextSecond,
                )
            }

            Icon(
                modifier = Modifier
                    .size(20.dp),
                imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = null,
                tint = tint,
            )
        }
    }

}

//.border(width = 0.3.dp, color = Color(0xFFCBC3D9), shape = RoundedCornerShape(16.dp))


@Preview
@Composable
fun CardPreview() {
    ZakaTheme {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            ActionCard(
                label = "مسح مستند",
                description = "التقط صورة من مذكراتك",
                icon = Icons.Default.DocumentScanner,
                tint = Color.Red
            )
        }
    }
}