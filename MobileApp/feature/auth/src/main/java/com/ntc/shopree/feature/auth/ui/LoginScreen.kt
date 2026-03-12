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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.LifecycleStartEffect
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation3.runtime.NavKey
import coil3.compose.SubcomposeAsyncImage
import com.ntc.shopree.core.ui.components.CheckBox
import com.ntc.shopree.core.ui.components.PrimaryButton
import com.ntc.shopree.core.ui.components.TextInput
import com.ntc.shopree.core.ui.icons.Icons
import com.ntc.shopree.core.ui.utils.SnackbarController
import com.ntc.shopree.core.ui.utils.SnackbarEvent
import com.ntc.shopree.feature.auth.ui.data.LoginFormInput
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
data object LoginScreen : NavKey

@Serializable
data object PostLogin : NavKey

@Composable
fun LoginScreen(onLoggedIn: () -> Unit) {
    val loginViewModel: LoginViewModel = hiltViewModel()
    val authenticated by loginViewModel.authenticated.collectAsState()
    val validation by loginViewModel.validation.collectAsState()
    val loginUiState by loginViewModel.loginUiState.collectAsState()
    val scope = rememberCoroutineScope()
    val emailState = rememberTextFieldState()
    val passwordState = rememberTextFieldState()
    LifecycleStartEffect(lifecycleOwner = LocalLifecycleOwner.current, key1 = authenticated) {
        loginViewModel.checkCurrentUser()
        if (authenticated) {
            onLoggedIn()
        }
        onStopOrDispose {
            // Intentionally do nothing
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SubcomposeAsyncImage(
            modifier = Modifier
                .size(240.dp)
                .padding(bottom = 8.dp),
            model = "https://s3.ap-southeast-1.amazonaws.com/com.ntc.shopree/Login.jpg",
            contentDescription = "login image",
            loading = { CircularProgressIndicator() })
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Welcome",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = "Sign in to continue shopping",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.Normal
        )
        Spacer(Modifier.height(20.dp))
        LoginForm(
            emailState = emailState, passwordState = passwordState, viewModel = loginViewModel
        )
        Spacer(Modifier.height(20.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            LoginMethod(methodIcon = Icons.Filled.Google)
            LoginMethod(methodIcon = Icons.Filled.Facebook)
        }
        Spacer(Modifier.height(16.dp))
        // TODO: Handles fast clicker problem
        PrimaryButton(
            onclick = {
                val currentInput = LoginFormInput(
                    emailOrPhone = emailState.text.toString(),
                    password = passwordState.text.toString()
                )

                val immediateValidation = loginViewModel.validateLoginForm(currentInput)

                val hasErrors =
                    immediateValidation.blankEmailOrPhoneError != null || immediateValidation.passwordError != null
                if (hasErrors) {
                    scope.launch {
                        SnackbarController.sendEvent(SnackbarEvent(message = "Please fill in all fields"))
                    }
                    return@PrimaryButton
                }
                val invalidEmailFormatError = immediateValidation.invalidEmailFormatError != null
                if (invalidEmailFormatError) {
                    scope.launch {
                        SnackbarController.sendEvent(SnackbarEvent(message = "Invalid email format, please fill in another email address"))
                    }
                    return@PrimaryButton
                }
                loginViewModel.logUserIn(emailState.text.toString(), passwordState.text.toString())
            },
            modifier = Modifier.fillMaxWidth(),
            text = "Sign in",
            fontSize = 24.sp,
            loading = loginUiState is LoginUiState.Loading
        )
        Spacer(Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Don't have an account?", style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.width(4.dp))
            Text(
                text = "Sign Up",
                style = MaterialTheme.typography.bodyMedium,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable {
                    // TODO handle signup navigation
                },
                color = MaterialTheme.colorScheme.primary
            )
        }

    }
}

@OptIn(FlowPreview::class)
@Composable
fun LoginForm(
    emailState: TextFieldState,
    passwordState: TextFieldState,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel
) {
    var emailTouched by remember { mutableStateOf(false) }
    var passwordTouched by remember { mutableStateOf(false) }
    var rememberMe by remember { mutableStateOf(false) }

    val validation by viewModel.validation.collectAsState()

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
                    modifier = Modifier.padding(end = 4.dp)
                )
            },
        )
        Spacer(Modifier.height(16.dp))
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
                    modifier = Modifier.padding(end = 4.dp)
                )
            },
            trailing = {
                Icon(
                    imageVector = Icons.Filled.Eye,
                    contentDescription = "password input icon",
                    modifier = Modifier.padding(end = 4.dp)
                )
            },
        )
        Spacer(Modifier.height(12.dp))
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Row(modifier = Modifier.height(32.dp), verticalAlignment = Alignment.CenterVertically) {
                CheckBox(selected = rememberMe , onChecked = { rememberMe = it })
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Remember me",
                    style = MaterialTheme.typography.labelMedium.copy(fontSize = 14.sp),
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Normal
                )
            }
            Text(
                text = "Forgot password?", style = MaterialTheme.typography.labelMedium.copy(
                    fontSize = 14.sp, textDecoration = TextDecoration.Underline
                ), color = MaterialTheme.colorScheme.error, fontWeight = FontWeight.Normal
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
            .padding(horizontal = 14.dp, vertical = 10.dp)
    ) {
        Icon(
            imageVector = methodIcon,
            contentDescription = "login method icon",
            modifier = Modifier.size(22.dp)
        )
    }
}

@Preview
@Composable
fun LoginMethodPreview() {
    LoginMethod(methodIcon = Icons.Filled.Google)
}
