package com.vtol.zaka.presentation.register.login.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vtol.zaka.presentation.components.ZakaButton
import com.vtol.zaka.ui.theme.Purple100
import com.vtol.zaka.ui.theme.Purple700
import com.vtol.zaka.R

@Composable
fun ForgotPasswordSheetContent(
    email: String,
    onDismiss: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(bottom = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            modifier = Modifier
                .size(56.dp)
                .background(Purple100, shape = CircleShape)
                .padding(14.dp),
            painter = painterResource(R.drawable.ic_mail),
            tint = Purple700,
            contentDescription = null
        )

        Text(
            text = "Check your email",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            color = Purple700
        )

        Text(
            text = "If an account exists for $email, you'll receive a reset link shortly. Please check your spam folder if you don't see it.",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        ZakaButton(
            text = "Got it",
            onClick = onDismiss
        )
    }
}