package com.ntc.shopree.feature.auth.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavKey
import coil3.compose.SubcomposeAsyncImage
import com.ntc.shopree.core.ui.components.CheckBox
import com.ntc.shopree.core.ui.components.PrimaryButton
import com.ntc.shopree.core.ui.components.TextInput
import com.ntc.shopree.core.ui.icons.Icons
import com.ntc.shopree.core.ui.theme.ColorGrey100
import com.ntc.shopree.core.ui.theme.ColorGrey400
import com.ntc.shopree.core.ui.theme.ColorGrey500
import com.ntc.shopree.core.ui.theme.ColorGrey700
import com.ntc.shopree.core.ui.theme.Outfit
import com.ntc.shopree.core.ui.theme.fontSize3
import com.ntc.shopree.core.ui.theme.fontSize4
import com.ntc.shopree.core.ui.theme.fontSize6
import com.ntc.shopree.core.ui.theme.spacing1
import com.ntc.shopree.core.ui.theme.spacing2
import com.ntc.shopree.core.ui.theme.spacing3
import com.ntc.shopree.core.ui.theme.spacing4
import com.ntc.shopree.core.ui.theme.spacing5
import com.ntc.shopree.core.ui.theme.spacing6
import com.ntc.shopree.core.ui.utils.ObserveAsEvents
import com.ntc.shopree.core.ui.utils.SnackbarController
import com.ntc.shopree.core.ui.utils.SnackbarEvent
import com.ntc.shopree.feature.auth.ui.data.LoginFormInput
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.serialization.Serializable

@Serializable
data object LoginScreen : NavKey

@Serializable
data object PostLogin : NavKey

@Composable
fun LoginScreen(onLoggedIn: () -> Unit) {
    val loginViewModel: LoginViewModel = hiltViewModel()
    val state by loginViewModel.uiState.collectAsStateWithLifecycle()
    val emailState = rememberTextFieldState()
    val passwordState = rememberTextFieldState()

    // Navigation and snackbar driven by ViewModel events — no direct SnackbarController calls here.
    ObserveAsEvents(loginViewModel.events) { event ->
        when (event) {
            is LoginEvent.NavigateToHome -> onLoggedIn()
            is LoginEvent.ShowSnackbar -> SnackbarController.sendEvent(SnackbarEvent(event.message))
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = spacing5, vertical = spacing4),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SubcomposeAsyncImage(
            modifier = Modifier
                .size(240.dp)
                .padding(bottom = spacing1),
            model = "https://s3.ap-southeast-1.amazonaws.com/com.ntc.shopree/Login.jpg",
            contentDescription = "login image",
            loading = { CircularProgressIndicator() })
        Spacer(Modifier.height(spacing1))
        Text(
            text = "Welcome",
            fontSize = fontSize6,
            fontWeight = FontWeight.SemiBold,
            color = ColorGrey700,
            fontFamily = Outfit
        )
        Spacer(Modifier.height(spacing1))
        Text(
            text = "Sign in to continue shopping",
            fontSize = fontSize4,
            color = ColorGrey400,
            fontWeight = FontWeight.Normal,
            fontFamily = Outfit
        )
        Spacer(Modifier.height(spacing4))
        LoginForm(
            emailState = emailState,
            passwordState = passwordState,
            validation = state.validation,
            rememberMe = state.rememberMe,
            viewModel = loginViewModel
        )
        Spacer(Modifier.height(spacing4))
        Row(horizontalArrangement = Arrangement.spacedBy(spacing2)) {
            LoginMethod(methodIcon = Icons.Filled.Google)
            LoginMethod(methodIcon = Icons.Filled.Facebook)
        }
        Spacer(Modifier.height(spacing4))
        // TODO: Handles fast clicker problem
        PrimaryButton(
            onclick = {
                loginViewModel.logUserIn(
                    emailState.text.toString(),
                    passwordState.text.toString()
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            text = "Sign in",
            fontSize = fontSize6,
            loading = state.isLoading,
            loadingIndicator = {
                CircularProgressIndicator(modifier = Modifier.size(32.dp), color = ColorGrey100)
            }
        )
        Spacer(Modifier.height(spacing3))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Don't have an account?", fontSize = fontSize3, color = ColorGrey400)
            Spacer(Modifier.width(spacing1))
            Text(
                text = "Sign Up",
                fontSize = fontSize3,
                textDecoration = TextDecoration.Underline,
                fontStyle = FontStyle.Italic,
                modifier = Modifier.clickable {
                    // TODO: handle signup navigation
                },
                color = ColorGrey500,
                fontFamily = Outfit
            )
        }
    }
}

@OptIn(FlowPreview::class)
@Composable
fun LoginForm(
    emailState: TextFieldState,
    passwordState: TextFieldState,
    validation: LoginFormErrors,
    rememberMe: Boolean,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel
) {
    var emailTouched by remember { mutableStateOf(false) }
    var passwordTouched by remember { mutableStateOf(false) }

    val emailErrorText = validation.blankEmailOrPhoneError ?: validation.invalidEmailFormatError
    val passwordErrorText = validation.passwordError

    LaunchedEffect(emailState, passwordState) {
        snapshotFlow {
            LoginFormInput(
                emailOrPhone = emailState.text.toString(), password = passwordState.text.toString()
            )
        }.debounce { 800L }.collect { input ->
            if (input.emailOrPhone.isNotEmpty()) emailTouched = true
            if (input.password.isNotEmpty()) passwordTouched = true
            viewModel.validateInput(input)
        }
    }

    Column(modifier = modifier.fillMaxWidth()) {
        TextInput(
            state = emailState,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            error = emailTouched && emailErrorText != null,
            errorText = emailErrorText ?: "",
            placeholder = "Mobile or Email",
            leading = {
                Icon(
                    imageVector = Icons.Outlined.Mail,
                    contentDescription = "email input icon",
                    modifier = Modifier.padding(end = spacing1)
                )
            },
        )
        Spacer(Modifier.height(spacing2))
        TextInput(
            state = passwordState,
            modifier = Modifier.fillMaxWidth(),
            placeholder = "Password",
            error = passwordTouched && passwordErrorText != null,
            errorText = passwordErrorText ?: "",
            singleLine = true,
            leading = {
                Icon(
                    imageVector = Icons.Filled.Password,
                    contentDescription = "email input icon",
                    modifier = Modifier.padding(end = spacing1)
                )
            },
            trailing = {
                Icon(
                    imageVector = Icons.Filled.Eye,
                    contentDescription = "password input icon",
                    modifier = Modifier.padding(end = spacing1)
                )
            },
        )
        Spacer(Modifier.height(spacing2))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.height(spacing6), verticalAlignment = Alignment.CenterVertically
            ) {
                CheckBox(selected = rememberMe, onChecked = {
                    viewModel.updateRememberMe(it)
                })
                Spacer(modifier = Modifier.width(spacing2))
                Text(
                    text = "Remember me",
                    fontSize = fontSize4,
                    color = ColorGrey400,
                    fontWeight = FontWeight.Normal,
                    fontFamily = Outfit
                )
            }
            Text(
                text = "Forgot password?",
                fontSize = fontSize4,
                color = ColorGrey400,
                fontWeight = FontWeight.Normal,
                fontFamily = Outfit
            )
        }
    }
}

@Composable
fun LoginMethod(
    methodIcon: ImageVector,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(
                MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(10.dp)
            )
            .padding(horizontal = spacing2, vertical = spacing1)
    ) {
        Icon(
            imageVector = methodIcon,
            contentDescription = "login method icon",
            modifier = Modifier.size(spacing5)
        )
    }
}

@Preview
@Composable
fun LoginMethodPreview() {
    LoginMethod(methodIcon = Icons.Filled.Google)
}
