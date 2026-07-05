package com.vtol.zaka.presentation.register.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vtol.zaka.presentation.components.ZakaButton
import com.vtol.zaka.presentation.register.components.GoogleLoginButton
import com.vtol.zaka.presentation.register.components.LabeledField
import com.vtol.zaka.ui.theme.BorderColor
import com.vtol.zaka.ui.theme.Purple700
import com.vtol.zaka.ui.theme.PurpleLink
import com.vtol.zaka.ui.theme.TextSecond
import com.vtol.zaka.util.showToast
import com.vtol.zaka.R
import com.vtol.zaka.presentation.components.LoadingIndicator
import com.vtol.zaka.presentation.components.rememberShiningBrush
import com.vtol.zaka.presentation.register.login.components.ForgotPasswordSheetContent


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    state: LoginUiState,
    onSignUpClick: () -> Unit = {},
    event: (LoginEvent) -> Unit
) {
    val context = LocalContext.current
    var passwordVisible by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current

    LaunchedEffect(state.isLoading) {
        if (state.isLoading) focusManager.clearFocus()
    }

    LaunchedEffect(state.error) {
        state.error?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            event(LoginEvent.ErrorShown)
        }
    }

    state.forgotPasswordError?.let { error ->
        LaunchedEffect(error) {
            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
            event(LoginEvent.ClearForgotPasswordState)
        }
    }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)


    if (state.forgetPasswordSuccess) {
        ModalBottomSheet(
            onDismissRequest = { event(LoginEvent.ClearForgotPasswordState) },
            sheetState = sheetState
        ) {
            ForgotPasswordSheetContent(email = state.email) {
                event(LoginEvent.ClearForgotPasswordState)
            }
        }
    }

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
            painter = painterResource(R.drawable.ic_app),
            contentDescription = null,
            modifier = Modifier.height(92.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "ذكــاء",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontSize = 42.sp,
                fontWeight = FontWeight.SemiBold,
                brush = rememberShiningBrush()
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "أهلاً بعودتك",
            color = Purple700,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "سجّل الدخول لمتابعة رحلتك التعليمية",
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
                label = "البريد الإلكتروني",
                value = state.email,
                errorTxt = state.emailError,
                onValueChange = { event(LoginEvent.EmailChanged(it)) },
                placeholder = "example@domain.com",
                icon = Icons.Filled.Email,
                keyboardType = KeyboardType.Email
            )

            Spacer(modifier = Modifier.height(18.dp))

            LabeledField(
                label = "كلمة المرور",
                value = state.password,
                errorTxt = state.passwordError,
                onValueChange = { event(LoginEvent.PasswordChanged(it)) },
                placeholder = "••••••••",
                icon = Icons.Filled.Lock,
                keyboardType = KeyboardType.Password,
                isPassword = true,
                passwordVisible = passwordVisible,
                onTogglePasswordVisibility = { passwordVisible = !passwordVisible }
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Forgot password, right-aligned under the field (renders on
            // the left visually thanks to RTL layout direction)
            Text(
                text = "نسيت كلمة المرور؟",
                color = PurpleLink,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() },
                        onClick = {
                        event(LoginEvent.RestPasswordClicked)
                    }),
                textAlign = TextAlign.Start
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Login button
            ZakaButton(
                text = "تسجيل الدخول"
            ) { event(LoginEvent.LoginClicked) }

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

            // Google login button
            GoogleLoginButton { context.showToast() }
        }

        Spacer(modifier = Modifier.height(28.dp))

        // Sign-up link
        Row {
            Text(
                text = "ليس لديك حساب؟ ",
                color = Color.Black,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = "أنشئ حساباً",
                color = PurpleLink,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable(onClick = onSignUpClick)
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
    }

    if (state.isLoading) {
        LoadingIndicator()
    }
}

@Preview(showBackground = true, widthDp = 412, heightDp = 915)
@Composable
private fun LoginScreenPreview() {
    MaterialTheme {
        LoginScreen(state = LoginUiState(), event = {})
    }
}