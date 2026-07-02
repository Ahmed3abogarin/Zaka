package com.vtol.zaka.presentation.register.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.vtol.zaka.ui.theme.PurpleLink
import com.vtol.zaka.ui.theme.TextSecond

@Composable
fun TermsText() {
    Text(
        text = buildAnnotatedTermsString(),
        fontSize = 12.sp,
        color = TextSecond,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun buildAnnotatedTermsString() = buildAnnotatedString {
    append("من خلال إنشاء حساب، فإنك توافق على ")
    withStyle(
        style = SpanStyle(
            color = PurpleLink,
            fontWeight = FontWeight.Bold,
            textDecoration = TextDecoration.Underline
        )
    ) {
        append("الشروط والأحكام")
    }
    append(" و ")
    withStyle(
        style = SpanStyle(
            color = PurpleLink,
            fontWeight = FontWeight.Bold,
            textDecoration = TextDecoration.Underline
        )
    ) {
        append("سياسة الخصوصية")
    }
    append(".")
}