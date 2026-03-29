package com.ntc.shopree.feature.auth.ui

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.ntc.shopree.core.ui.ShopreeViewModel
import com.ntc.shopree.feature.auth.domain.CheckCurrentUserUseCase
import com.ntc.shopree.feature.auth.domain.CheckSessionUseCase
import com.ntc.shopree.feature.auth.domain.LoginUseCase
import com.ntc.shopree.feature.auth.ui.data.LoginFormInput
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val checkCurrentUserUseCase: CheckCurrentUserUseCase,
    private val checkSessionUseCase: CheckSessionUseCase,
    private val loginUseCase: LoginUseCase
) : ShopreeViewModel<LoginUiState, LoginEvent>(LoginUiState()) {

    fun checkCurrentUser() {
        viewModelScope.launch {
            val sessionResult = checkSessionUseCase()
            sessionResult.onSuccess { isAuthenticated ->
                if (isAuthenticated) emitEvent(LoginEvent.NavigateToHome)
                return@launch
            }
            sessionResult.onFailure {
                Log.e("LoginViewModel", "checkSession: Error checking session")
            }
            val result = checkCurrentUserUseCase()
            result.onSuccess { isAuthenticated ->
                if (isAuthenticated) emitEvent(LoginEvent.NavigateToHome)
            }
            result.onFailure {
                Log.e("LoginViewModel", "checkCurrentUser: Error authenticating user")
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
        _uiState.update { it.copy(rememberMe = checked) }
    }

    fun validateInput(inputs: LoginFormInput) {
        _uiState.update { it.copy(validation = validateLoginForm(inputs)) }
    }

    fun logUserIn(username: String, password: String) {
        val input = LoginFormInput(username, password)
        val validation = validateLoginForm(input)
        _uiState.update { it.copy(validation = validation) }

        if (validation.blankEmailOrPhoneError != null || validation.passwordError != null) {
            emitEvent(LoginEvent.ShowSnackbar("Please fill in all fields"))
            return
        }
        if (validation.invalidEmailFormatError != null) {
            emitEvent(LoginEvent.ShowSnackbar("Invalid email format, please fill in another email address"))
            return
        }
        if (_uiState.value.isLoading) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val loginResult = loginUseCase(username, password, _uiState.value.rememberMe)
            loginResult.onSuccess {
                emitEvent(LoginEvent.NavigateToHome)
            }
            loginResult.onFailure { error ->
                emitEvent(LoginEvent.ShowSnackbar("Error logging in: ${error.message}"))
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }
}
