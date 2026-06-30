package com.vtol.zaka.presentation.home.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vtol.zaka.R
import com.vtol.zaka.ui.theme.Teal400
import com.vtol.zaka.ui.theme.TextPrimary
import com.vtol.zaka.ui.theme.ZakaTheme

@Composable
fun CategoryCard(
    modifier: Modifier = Modifier,
    label: String,
    color: Color,
    @DrawableRes icon: Int,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .border(
                width = 0.7.dp,
                color = color.copy(alpha = 0.4f),
                shape = RoundedCornerShape(16.dp)
            )
            .background(Color.White)
            .size(150.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(modifier = Modifier.fillMaxSize().background(color.copy(alpha = 0.05f)))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card (
                modifier = Modifier.size(62.dp),
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(1.dp)
            ) {
                Icon(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    painter = painterResource(icon),
                    tint = color,
                    contentDescription = null
                )

            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = label,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary,
            )
        }
    }
}

@Preview
@Composable
fun CategoryCardPreview() {
    ZakaTheme {
        CategoryCard(
            label = "العلوم",
            icon = R.drawable.ic_science,
            color = Teal400
        )
    }
}