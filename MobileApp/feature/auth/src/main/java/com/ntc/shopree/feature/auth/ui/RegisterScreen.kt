package com.ntc.shopree.feature.auth.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavKey
import com.ntc.shopree.core.ui.components.PrimaryButton
import com.ntc.shopree.core.ui.components.TextInput
import com.ntc.shopree.core.ui.icons.Icons
import com.ntc.shopree.core.ui.theme.ColorGrey100
import com.ntc.shopree.core.ui.theme.ColorGrey200
import com.ntc.shopree.core.ui.theme.ColorGrey400
import com.ntc.shopree.core.ui.theme.ColorGrey500
import com.ntc.shopree.core.ui.theme.ColorGrey700
import com.ntc.shopree.core.ui.theme.Outfit
import com.ntc.shopree.core.ui.theme.fontSize3
import com.ntc.shopree.core.ui.theme.fontSize6
import com.ntc.shopree.core.ui.theme.spacing1
import com.ntc.shopree.core.ui.theme.spacing4
import com.ntc.shopree.core.ui.theme.spacing5
import com.ntc.shopree.core.ui.utils.ObserveAsEvents
import com.ntc.shopree.core.ui.utils.SnackbarController
import com.ntc.shopree.core.ui.utils.SnackbarEvent
import kotlinx.serialization.Serializable

@Serializable
data object RegisterScreen : NavKey

@Composable
fun RegisterScreen(
    onNavigateBack: () -> Unit = {},
    onRegistered: () -> Unit = {}
) {
    val viewModel: RegisterViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is RegisterEvent.NavigateToHome -> onRegistered()
            is RegisterEvent.ShowSnackbar -> SnackbarController.sendEvent(SnackbarEvent(event.message))
        }
    }

    val fullNameState = rememberTextFieldState()
    val emailState = rememberTextFieldState()
    val phoneState = rememberTextFieldState()
    val passwordState = rememberTextFieldState()
    val confirmPasswordState = rememberTextFieldState()
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = spacing5, vertical = spacing4),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(ColorGrey100, CircleShape)
                    .clickable { onNavigateBack() }, contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = "Back",
                    tint = ColorGrey700,
                    modifier = Modifier.size(18.dp)
                )
            }
        }

        Spacer(Modifier.height(spacing4))

        Box(
            modifier = Modifier
                .size(140.dp)
                .background(ColorGrey100, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(ColorGrey200, CircleShape)
            )
        }

        Spacer(Modifier.height(spacing4))

        Text(
            text = "Create Account",
            fontSize = fontSize6,
            fontWeight = FontWeight.SemiBold,
            color = ColorGrey700,
            fontFamily = Outfit,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(spacing1))

        Text(
            text = "Sign up to start shopping",
            fontSize = fontSize3,
            color = ColorGrey400,
            fontWeight = FontWeight.Normal,
            fontFamily = Outfit,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(spacing5))

        TextInput(
            state = fullNameState, modifier = Modifier.fillMaxWidth(), placeholder = "Full Name"
        )

        TextInput(
            state = emailState,
            modifier = Modifier.fillMaxWidth(),
            placeholder = "Email",
            leading = {
                Icon(
                    imageVector = Icons.Outlined.Mail,
                    contentDescription = null,
                    modifier = Modifier.padding(end = spacing1)
                )
            })

        TextInput(
            state = phoneState,
            modifier = Modifier.fillMaxWidth(),
            placeholder = "Phone Number",
            leading = {
                Icon(
                    imageVector = Icons.Outlined.Phone,
                    contentDescription = null,
                    modifier = Modifier.padding(end = spacing1)
                )
            })

        TextInput(
            state = passwordState,
            modifier = Modifier.fillMaxWidth(),
            placeholder = "Password",
            visualTransformation = if (passwordVisible) VisualTransformation.None
            else PasswordVisualTransformation(),
            leading = {
                Icon(
                    imageVector = Icons.Filled.Password,
                    contentDescription = null,
                    modifier = Modifier.padding(end = spacing1)
                )
            },
            trailing = {
                Icon(
                    imageVector = if (passwordVisible) Icons.Filled.EyeClosed else Icons.Filled.Eye,
                    contentDescription = if (passwordVisible) "Hide password" else "Show password",
                    modifier = Modifier
                        .padding(end = spacing1)
                        .clickable { passwordVisible = !passwordVisible })
            })

        TextInput(
            state = confirmPasswordState,
            modifier = Modifier.fillMaxWidth(),
            placeholder = "Confirm Password",
            visualTransformation = if (confirmPasswordVisible) VisualTransformation.None
            else PasswordVisualTransformation(),
            leading = {
                Icon(
                    imageVector = Icons.Filled.Password,
                    contentDescription = null,
                    modifier = Modifier.padding(end = spacing1)
                )
            },
            trailing = {
                Icon(
                    imageVector = if (confirmPasswordVisible) Icons.Filled.EyeClosed else Icons.Filled.Eye,
                    contentDescription = if (confirmPasswordVisible) "Hide password" else "Show password",
                    modifier = Modifier
                        .padding(end = spacing1)
                        .clickable { confirmPasswordVisible = !confirmPasswordVisible })
            })

        Spacer(Modifier.height(spacing5))

        PrimaryButton(
            onclick = {
                viewModel.register(
                    fullNameState.text.toString(),
                    emailState.text.toString(),
                    phoneState.text.toString(),
                    passwordState.text.toString()
                )
            },
            loading = state.isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            text = "Sign Up",
            fontSize = fontSize6
        )

        Spacer(Modifier.height(spacing4))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Already have an account?",
                fontSize = fontSize3,
                color = ColorGrey400,
                fontFamily = Outfit
            )
            Spacer(Modifier.width(spacing1))
            Text(
                text = "Sign In",
                fontSize = fontSize3,
                textDecoration = TextDecoration.Underline,
                fontStyle = FontStyle.Italic,
                color = ColorGrey500,
                fontFamily = Outfit,
                modifier = Modifier.clickable { onNavigateBack() })
        }
    }
}
