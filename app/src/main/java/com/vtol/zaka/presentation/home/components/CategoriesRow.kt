package com.vtol.zaka.presentation.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vtol.zaka.R
import com.vtol.zaka.ui.theme.Purple500
import com.vtol.zaka.ui.theme.TextPrimary
import com.vtol.zaka.ui.theme.ZakaTheme
import com.vtol.zaka.util.showToast

@Composable
fun CategoriesRow(
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "أو اختر موضوعاً للبدء فوراً",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary,
            )

            Text(
                modifier = Modifier.clickable { context.showToast() },
                text = "عرض الكل",
                fontSize = 14.sp,
                color = Purple500,
                fontWeight = FontWeight.Bold,
            )
        }
        Spacer(Modifier.height(14.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            CategoryCard(
                modifier = Modifier.weight(1f),
                label = "علوم",
                color = Color(0xFF16A34A),
                icon = R.drawable.ic_science
            )
            CategoryCard(
                modifier = Modifier.weight(1f),
                label = "لغات",
                color = Purple500,
                icon = R.drawable.ic_language
            )

        }
        Spacer(Modifier.height(14.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            CategoryCard(
                modifier = Modifier.weight(1f),
                label = "رياضيات",
                color = Color(0xFF2563EB),
                icon = R.drawable.ic_math
            )
            CategoryCard(
                modifier = Modifier.weight(1f),
                label = "تاريخ",
                color = Color(0xFFD97706),
                icon = R.drawable.ic_history
            )
        }
    }
}

@Preview
@Composable
fun CategoriesRowPreview() {
    ZakaTheme {
        CategoriesRow()
    }
}