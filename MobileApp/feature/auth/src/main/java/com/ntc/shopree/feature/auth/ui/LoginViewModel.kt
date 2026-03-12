package com.ntc.shopree.feature.auth.ui

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ntc.shopree.core.ui.utils.SnackbarController
import com.ntc.shopree.core.ui.utils.SnackbarEvent
import com.ntc.shopree.feature.auth.domain.CheckCurrentUserUseCase
import com.ntc.shopree.feature.auth.domain.CheckSessionUseCase
import com.ntc.shopree.feature.auth.domain.LoginUseCase
import com.ntc.shopree.feature.auth.ui.data.LoginFormInput
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val checkCurrentUserUseCase: CheckCurrentUserUseCase,
    private val checkSessionUseCase: CheckSessionUseCase,
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    private val _authenticated = MutableStateFlow(false)
    val authenticated: StateFlow<Boolean> = _authenticated.asStateFlow()

    private val _validation = MutableStateFlow(LoginFormErrors())
    val validation: StateFlow<LoginFormErrors> = _validation.asStateFlow()

    val _loginUiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val loginUiState: StateFlow<LoginUiState> = _loginUiState.asStateFlow()

    private val _rememberMe = MutableStateFlow(false)
    val rememberMe: StateFlow<Boolean> = _rememberMe.asStateFlow()

    fun checkCurrentUser() {
        viewModelScope.launch {
            val sessionResult = checkSessionUseCase()
            sessionResult.onSuccess { isAuthenticated ->
                _authenticated.update { isAuthenticated }
                return@launch
            }
            sessionResult.onFailure {
                Log.e("LoginViewModel", "checkSession: Error checking session")
                _authenticated.update { false }
                return@launch
            }
            val result = checkCurrentUserUseCase()
            result.onSuccess { isAuthenticated ->
                _authenticated.update { isAuthenticated }
            }
            result.onFailure {
                Log.e("LoginViewModel", "checkCurrentUser: Error authenticating user")
                _authenticated.update { false }
            }
        }
    }

    fun validateLoginForm(inputs: LoginFormInput): LoginFormErrors {
        val emailOrPhoneError =
            if (inputs.emailOrPhone.isBlank()) "Email or phone number is required"
            else null

        val invalidEmailFormatError = if (!Patterns.EMAIL_ADDRESS.matcher(inputs.emailOrPhone)
                .matches() && !Patterns.PHONE.matcher(inputs.emailOrPhone).matches()
        ) "Invalid email or phone number"
        else null

        val passwordError = if (inputs.password.isBlank()) "Password is required"
        else if (inputs.password.length < 8) "Password must be at least 8 characters"
        else null

        return LoginFormErrors(
            blankEmailOrPhoneError = emailOrPhoneError,
            invalidEmailFormatError = invalidEmailFormatError,
            passwordError = passwordError
        )
    }

    fun updateRememberMe(checked: Boolean) {
        _rememberMe.update { checked }
    }
    fun validateInput(inputs: LoginFormInput) {
        _validation.update { validateLoginForm(inputs) }
    }



    fun logUserIn(username: String, password: String) {
        if (_loginUiState.value is LoginUiState.Loading) return
        viewModelScope.launch {
            _loginUiState.value = LoginUiState.Loading
            val loginResult = loginUseCase(username, password, _rememberMe.value)
            loginResult.onSuccess {
                _loginUiState.value = LoginUiState.Success("Logged in successfully")
                _authenticated.update { true }
            }
            loginResult.onFailure { message ->
                _loginUiState.value = LoginUiState.Error("Error logging in user: $message")
                SnackbarController.sendEvent(SnackbarEvent(message = "Error logging in user $message"))
                _authenticated.update { false }
            }
        }
    }

    fun resetLoginState() {
        _loginUiState.value = LoginUiState.Idle
    }
}


