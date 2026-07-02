package com.vtol.zaka.presentation.register.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vtol.zaka.R
import com.vtol.zaka.presentation.register.components.LabeledField
import com.vtol.zaka.presentation.register.components.TermsText
import com.vtol.zaka.ui.theme.BorderColor
import com.vtol.zaka.ui.theme.Purple700
import com.vtol.zaka.ui.theme.PurpleAccent
import com.vtol.zaka.ui.theme.PurpleLink
import com.vtol.zaka.ui.theme.TextSecond

private val ButtonGradient = Brush.horizontalGradient(
    colors = listOf(Purple700, PurpleAccent)
)

@Composable
fun SignUpScreen(
    onCreateAccount: (name: String, email: String, password: String) -> Unit = { _, _, _ -> },
    onGoogleSignUp: () -> Unit = {},
    onLoginClick: () -> Unit = {}
) {
    // The design is Arabic, so the whole screen is laid out right-to-left.
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(48.dp))

        // Logo
        Image(
            painter = painterResource(R.drawable.ic_app_icon),
            contentDescription = null,
            modifier = Modifier.height(122.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "ابدأ رحلة التعلم الذكي",
            color = Purple700,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "انضم إلى آلاف الطلاب المتميزين اليوم",
            color = TextSecond,
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(28.dp))

        // Card
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(elevation = 8.dp, shape = RoundedCornerShape(20.dp), clip = false)
                .clip(RoundedCornerShape(20.dp))
                .background(Color.White)
                .padding(20.dp)
        ) {
            LabeledField(
                label = "الاسم الكامل",
                value = fullName,
                onValueChange = { fullName = it },
                placeholder = "أدخل اسمك الثلاثي",
                icon = Icons.Filled.Person
            )

            Spacer(modifier = Modifier.height(18.dp))

            LabeledField(
                label = "البريد الإلكتروني",
                value = email,
                onValueChange = { email = it },
                placeholder = "example@domain.com",
                icon = Icons.Filled.Email,
                keyboardType = KeyboardType.Email
            )

            Spacer(modifier = Modifier.height(18.dp))

            LabeledField(
                label = "كلمة المرور",
                value = password,
                onValueChange = { password = it },
                placeholder = "••••••••",
                icon = Icons.Filled.Lock,
                keyboardType = KeyboardType.Password,
                isPassword = true,
                passwordVisible = passwordVisible,
                onTogglePasswordVisibility = { passwordVisible = !passwordVisible }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Create account button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(ButtonGradient),
                contentAlignment = Alignment.Center
            ) {
                TextButton(
                    onClick = { onCreateAccount(fullName, email, password) },
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = "إنشاء حساب",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Divider with "أو"
            Row(verticalAlignment = Alignment.CenterVertically) {
                HorizontalDivider(
                    modifier = Modifier.weight(1f),
                    thickness = DividerDefaults.Thickness,
                    color = BorderColor
                )
                Text(
                    text = "أو",
                    color = TextSecond,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
                HorizontalDivider(
                    modifier = Modifier.weight(1f),
                    thickness = DividerDefaults.Thickness,
                    color = BorderColor
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Google sign up button
            OutlinedButton(
                onClick = onGoogleSignUp,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(14.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, BorderColor),
                colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White)
            ) {
                Text(
                    text = "التسجيل بواسطة جوجل",
                    color = Color.Black,
                    fontSize = 15.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                // Replace with an actual Google "G" logo asset in production
                Text(
                    text = "G",
                    color = Color(0xFF4285F4),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Terms text
            TermsText()
        }

        Spacer(modifier = Modifier.height(28.dp))

        // Login link
        Row {
            Text(
                text = "لديك حساب بالفعل؟ ",
                color = Color.Black,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = "سجل دخولك",
                color = PurpleLink,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable(onClick = onLoginClick)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Preview(showBackground = true, widthDp = 412, heightDp = 915)
@Composable
private fun SignUpScreenPreview() {
    MaterialTheme {
        SignUpScreen()
    }
}