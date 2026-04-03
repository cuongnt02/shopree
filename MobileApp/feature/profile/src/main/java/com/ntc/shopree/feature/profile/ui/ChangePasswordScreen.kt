package com.ntc.shopree.feature.profile.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavKey
import com.ntc.shopree.core.ui.components.PrimaryButton
import com.ntc.shopree.core.ui.components.TextInput
import com.ntc.shopree.core.ui.icons.Icons
import com.ntc.shopree.core.ui.theme.Neutral400
import com.ntc.shopree.core.ui.theme.Neutral50
import com.ntc.shopree.core.ui.theme.Neutral500
import com.ntc.shopree.core.ui.theme.Neutral600
import com.ntc.shopree.core.ui.theme.Neutral700
import com.ntc.shopree.core.ui.theme.Neutral900
import com.ntc.shopree.core.ui.theme.Outfit
import com.ntc.shopree.core.ui.theme.fontSize2
import com.ntc.shopree.core.ui.theme.fontSize3
import com.ntc.shopree.core.ui.theme.spacing1
import com.ntc.shopree.core.ui.theme.spacing2
import com.ntc.shopree.core.ui.theme.spacing3
import com.ntc.shopree.core.ui.theme.spacing5
import com.ntc.shopree.core.ui.theme.spacing6
import com.ntc.shopree.core.ui.utils.ObserveAsEvents
import com.ntc.shopree.core.ui.utils.SnackbarController
import com.ntc.shopree.core.ui.utils.SnackbarEvent
import kotlinx.serialization.Serializable

@Serializable
data object ChangePasswordScreen : NavKey

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangePasswordScreen(
    onBack: () -> Unit,
    viewModel: ChangePasswordViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val currentPasswordState = rememberTextFieldState()
    val newPasswordState = rememberTextFieldState()
    val confirmPasswordState = rememberTextFieldState()

    var showCurrentPassword by remember { mutableStateOf(false) }
    var showNewPassword by remember { mutableStateOf(false) }
    var showConfirmPassword by remember { mutableStateOf(false) }
    var confirmPasswordError by remember { mutableStateOf(false) }

    val passwordTransformation = PasswordVisualTransformation()

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is ChangePasswordEvent.NavigateBack -> onBack()
            is ChangePasswordEvent.ShowSnackbar -> SnackbarController.sendEvent(SnackbarEvent(event.message))
        }
    }

    val onSubmit: () -> Unit = {
        val current = currentPasswordState.text.toString()
        val new = newPasswordState.text.toString()
        val confirm = confirmPasswordState.text.toString()
        if (new != confirm) {
            confirmPasswordError = true
        } else {
            confirmPasswordError = false
            viewModel.changePassword(current, new)
        }
    }

    Scaffold(
        containerColor = Neutral50,
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Neutral50
                ),
                title = {
                    Text(
                        text = "Shopree",
                        fontFamily = Outfit,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = fontSize3,
                        color = Neutral900
                    )
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Outlined.ArrowBack,
                        contentDescription = "navigate back",
                        tint = Neutral900,
                        modifier = Modifier
                            .clickable { onBack() }
                            .size(32.dp)
                            .padding(start = spacing2)
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
                .padding(horizontal = spacing3)
        ) {
            Spacer(Modifier.height(spacing6))

            Text(
                text = "Security",
                fontFamily = Outfit,
                fontWeight = FontWeight.Medium,
                fontSize = fontSize2,
                color = Neutral500,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = spacing2)
            )

            PasswordField(
                label = "Current Password",
                state = currentPasswordState,
                showPassword = showCurrentPassword,
                onToggleVisibility = { showCurrentPassword = !showCurrentPassword },
                passwordTransformation = passwordTransformation,
            )

            Spacer(Modifier.height(spacing3))

            PasswordField(
                label = "New Password",
                state = newPasswordState,
                showPassword = showNewPassword,
                onToggleVisibility = { showNewPassword = !showNewPassword },
                passwordTransformation = passwordTransformation,
            )

            Spacer(Modifier.height(spacing3))

            PasswordField(
                label = "Confirm New Password",
                state = confirmPasswordState,
                showPassword = showConfirmPassword,
                onToggleVisibility = { showConfirmPassword = !showConfirmPassword },
                passwordTransformation = passwordTransformation,
                error = confirmPasswordError,
                errorText = "Passwords do not match",
            )

            Spacer(Modifier.height(spacing5))

            PrimaryButton(
                onclick = onSubmit,
                modifier = Modifier.fillMaxWidth(),
                text = "Change Password",
                fontSize = fontSize3,
                loading = uiState.isSaving
            )

            Spacer(Modifier.height(spacing6))
        }
    }
}

@Composable
private fun PasswordField(
    label: String,
    state: TextFieldState,
    showPassword: Boolean,
    onToggleVisibility: () -> Unit,
    passwordTransformation: VisualTransformation,
    error: Boolean = false,
    errorText: String = "",
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontFamily = Outfit,
            fontWeight = FontWeight.Medium,
            fontSize = fontSize2,
            color = Neutral600
        )
        Spacer(Modifier.height(spacing1))
        TextInput(
            state = state,
            modifier = Modifier.fillMaxWidth(),
            error = error,
            errorText = errorText,
            visualTransformation = if (!showPassword) passwordTransformation else null,
            trailing = {
                Icon(
                    imageVector = if (showPassword) Icons.Filled.Eye else Icons.Filled.EyeClosed,
                    contentDescription = if (showPassword) "Hide password" else "Show password",
                    tint = if (showPassword) Neutral700 else Neutral400,
                    modifier = Modifier
                        .clickable(onClick = onToggleVisibility)
                        .padding(end = spacing1)
                        .size(20.dp)
                )
            }
        )
    }
}
