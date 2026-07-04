package com.vtol.zaka.presentation.result.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vtol.zaka.domain.models.quiz.Difficulty
import com.vtol.zaka.domain.models.quiz.Question
import com.vtol.zaka.presentation.quiz.model.QuestionResult
import com.vtol.zaka.ui.theme.BorderColor
import com.vtol.zaka.ui.theme.Green500
import com.vtol.zaka.ui.theme.RedError
import com.vtol.zaka.ui.theme.TextPrimary
import com.vtol.zaka.ui.theme.TextSecond
import com.vtol.zaka.ui.theme.ZakaTheme

@Composable
fun QuestionReviewCard(
    index: Int,
    result: QuestionResult,
    modifier: Modifier = Modifier,
) {
    val accentColor = if (result.isCorrect) Green500 else RedError
//    val bgColor = if (result.isCorrect) GreenLight else RedLight

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, BorderColor),
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            // Left accent bar
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .fillMaxHeight()
                    .background(accentColor)
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                // Header row: badge number + correct/wrong icon
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    // Question badge
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color(0xFFF3F4F6))
                            .padding(horizontal = 12.dp, vertical = 4.dp),
                    ) {
                        Text(
                            text = "سؤال $index",
                            fontSize = 12.sp,
                            color = TextSecond,
                        )
                    }

                    // Correct / wrong circle
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(accentColor),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            imageVector = if (result.isCorrect) Icons.Default.Check else Icons.Default.Close,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(15.dp),
                        )
                    }
                }

                // Question text
                Text(
                    text = result.question.text,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimary,
                    textAlign = TextAlign.Start,
                    lineHeight = 24.sp,
                )

                HorizontalDivider(color = BorderColor, thickness = 0.5.dp)

                // User's answer
                Row(
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = "إجابتك:",
                            fontSize = 13.sp,
                            color = TextSecond,
                        )
                        Text(
                            text = result.question.options[result.selectedIndex],
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = accentColor,
                            textDecoration = if (!result.isCorrect) TextDecoration.LineThrough else TextDecoration.None
                        )
                    }
                }

                // Show correct answer only if wrong
                if (!result.isCorrect) {
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(
                                text = "الإجابة الصحيحة:",
                                fontSize = 13.sp,
                                color = TextSecond,
                            )
                            Text(
                                text = result.question.options[result.question.correctIndex],
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Green500,
                            )
                        }
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun ReviewCardPreview(){
    ZakaTheme {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            QuestionReviewCard(
                index = 2,
                QuestionResult(
                    selectedIndex = 0,
                    question = Question(
                        "Ggs",
                        "ما هو الاسم الاول لترمب؟",
                        listOf("موز", "تفاح"),
                        0,
                        "",
                        "",
                        Difficulty.MEDIUM
                    ),
                    isCorrect = false
                )
            )
        }
    }
}