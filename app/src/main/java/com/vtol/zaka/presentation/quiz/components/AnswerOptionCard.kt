package com.vtol.zaka.presentation.quiz.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vtol.zaka.presentation.quiz.model.AnswerState
import com.vtol.zaka.presentation.quiz.model.QuizOption
import com.vtol.zaka.presentation.quiz.model.answerColors
import com.vtol.zaka.ui.theme.ZakaTheme

@Composable
fun AnswerOptionCard(
    option: QuizOption,
    onClick: () -> Unit,
) {
    val colors = answerColors(option.state)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .border(1.5.dp, colors.border, RoundedCornerShape(14.dp))
            .background(colors.background)
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        // Answer text
        // Could add an end padding to give more space between the text and the radio button
        Text(
            modifier = Modifier.weight(1f),
            text = option.text,
            fontSize = 16.sp,
            fontWeight = if (option.state != AnswerState.IDLE) FontWeight.SemiBold else FontWeight.Normal,
            color = colors.text,
        )
        // Leading indicator (radio / check / cross)
        AnswerIndicator(state = option.state, activeColor = colors.accent)
    }
}

@Preview
@Composable
fun AnswerPreview() {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        ZakaTheme {
            AnswerOptionCard(
                QuizOption(
                    text = " للا بحيب ان يتجاوز رز الرادي الشاشةازرهذا هو نص الخيار يمكن المستخدم من اختيار اجاباته، يمكن للمستخدم فقط"

                )
            ) { }
        }
    }
}