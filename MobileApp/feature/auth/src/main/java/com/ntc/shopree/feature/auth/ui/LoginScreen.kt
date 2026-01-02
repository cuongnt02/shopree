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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.LifecycleStartEffect
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.NavKey
import coil3.compose.SubcomposeAsyncImage
import com.ntc.shopree.core.ui.components.CheckBox
import com.ntc.shopree.core.ui.components.PrimaryButton
import com.ntc.shopree.core.ui.components.TextInput
import com.ntc.shopree.core.ui.icons.Icons
import com.ntc.shopree.core.ui.theme.ColorGrey200
import com.ntc.shopree.core.ui.theme.ColorGrey500
import com.ntc.shopree.core.ui.theme.ColorSecondary300
import kotlinx.serialization.Serializable

@Serializable
data object LoginScreen : NavKey

@Composable
fun LoginScreen(onLogin: () -> Unit) {
    val loginViewModel: LoginViewModel = hiltViewModel()
    val authenticated by loginViewModel.authenticated.collectAsState()
    val inputs by loginViewModel.inputs.collectAsState()
    val validation by loginViewModel.validation.collectAsState()
    LifecycleStartEffect(lifecycleOwner = LocalLifecycleOwner.current, key1 = authenticated) {
        loginViewModel.checkCurrentUser()
        if (authenticated) {
            onLogin()
        }
        onStopOrDispose {
            // Intentionally do nothing
        }
    }

    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        SubcomposeAsyncImage(
            modifier = Modifier.size(305.dp),
            model = "https://s3.ap-southeast-1.amazonaws.com/com.ntc.shopree/Login.jpg",
            contentDescription = "login image",
            loading = { CircularProgressIndicator() })
        Spacer(Modifier.height(2.dp))
        Text(
            text = "Welcome",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(Modifier.height(2.dp))
        Text(
            text = "Please login to continue",
            style = MaterialTheme.typography.labelMedium,
            color = ColorGrey500,
            fontWeight = FontWeight.Normal
        )
        Spacer(Modifier.height(12.dp))
        LoginForm(email = inputs.emailOrPhone, onEmailChange = {
            loginViewModel.updateEmailOrPhone(it)
        }, password = inputs.password, onPasswordChange = {
            loginViewModel.updatePassword(it)
        }, loginFormErrors = validation)
        Spacer(Modifier.height(24.dp))
        Row {
            LoginMethod(methodIcon = Icons.Filled.Google)
            Spacer(Modifier.width(12.dp))
            LoginMethod(methodIcon = Icons.Filled.Facebook)
        }
        Spacer(Modifier.height(12.dp))
        PrimaryButton(onclick = onLogin, modifier = Modifier.fillMaxWidth(.9f)) {
            Text(
                text = "Login",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
        Spacer(Modifier.height(12.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Dont have an account?", style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.width(4.dp))
            Text(
                text = "Sign Up",
                style = MaterialTheme.typography.bodyMedium,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable {
                    // TODO handle signup navigation
                },
                color = ColorSecondary300
            )
        }

    }
}

@Composable
fun LoginForm(
    email: String,
    onEmailChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    password: String,
    onPasswordChange: (String) -> Unit,
    loginFormErrors: LoginFormErrors
) {
    val emailErrorText =
        loginFormErrors.blankEmailOrPhoneError ?: loginFormErrors.invalidEmailFormatError
    val passwordErrorText = loginFormErrors.passwordError

    Column(
        modifier = modifier.fillMaxWidth(.9f), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextInput(
            value = email,
            onValueChanged = onEmailChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = "Mobile or Email",
            leading = {
                Icon(imageVector = Icons.Outlined.Mail, contentDescription = "email input icon")
            },
            validatorHasErrors = emailErrorText != null,
            errorText = emailErrorText ?: ""
        )
        Spacer(Modifier.height(20.dp))
        TextInput(
            modifier = Modifier.fillMaxWidth(),
            value = password,
            onValueChanged = onPasswordChange,
            placeholder = "Password",
            leading = {
                Icon(imageVector = Icons.Filled.Password, contentDescription = "email input icon")
            },
            trailing = {
                Icon(imageVector = Icons.Filled.Eye, contentDescription = "password input icon")
            },
            validatorHasErrors = passwordErrorText != null,
            errorText = passwordErrorText ?: ""
        )
        Spacer(Modifier.height(16.dp))
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Row(modifier = Modifier.height(24.dp), verticalAlignment = Alignment.CenterVertically) {
                CheckBox(selected = false, onChecked = {})
                Text(
                    modifier = Modifier.wrapContentHeight(),
                    text = "Remember me",
                    style = MaterialTheme.typography.labelMedium.copy(fontSize = 16.sp),
                    color = ColorSecondary300,
                    fontWeight = FontWeight.Normal
                )
            }
            Box(contentAlignment = Alignment.Center, modifier = Modifier.height(24.dp)) {
                Text(
                    text = "Forgot password?",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontSize = 16.sp, textDecoration = TextDecoration.Underline
                    ),
                    color = Color.Red,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.wrapContentHeight()
                )
            }

        }


    }
}

@Composable
fun LoginMethod(methodIcon: ImageVector, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(ColorGrey200, shape = RoundedCornerShape(8.dp))
            .padding(8.dp)
    ) {
        Icon(
            imageVector = methodIcon,
            contentDescription = "login method icon",
            modifier = Modifier.size(24.dp)
        )
    }
}

@Preview
@Composable
fun LoginMethodPreview() {
    LoginMethod(methodIcon = Icons.Filled.Google)
}
