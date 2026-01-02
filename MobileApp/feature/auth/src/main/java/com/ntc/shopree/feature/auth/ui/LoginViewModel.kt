package com.ntc.shopree.feature.auth.ui

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ntc.shopree.feature.auth.domain.CheckCurrentUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
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
    private val checkCurrentUserUseCase: CheckCurrentUserUseCase
) : ViewModel() {
    private val _authenticated = MutableStateFlow(false)
    val authenticated: StateFlow<Boolean> = _authenticated.asStateFlow()

    private val _inputs = MutableStateFlow(LoginFormUiState())
    val inputs: StateFlow<LoginFormUiState> = _inputs.asStateFlow()


    val validation: StateFlow<LoginFormErrors> =
        inputs.map { input ->
            validateLoginForm(input)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = LoginFormErrors()
        )


    fun checkCurrentUser() {
        viewModelScope.launch {
            val result = checkCurrentUserUseCase()
            result.onSuccess { isAuthenticated ->
                _authenticated.update { isAuthenticated }
            }
            result.onFailure {
                Log.e("LoginViewModel", "checkCurrentUser: Error authenticating user")
            }
        }
    }

    fun validateLoginForm(inputs: LoginFormUiState): LoginFormErrors {
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

    fun updateEmailOrPhone(emailOrPhone: String) {
        _inputs.update {
            it.copy(emailOrPhone = emailOrPhone)
        }
    }

    fun updatePassword(password: String) {
        _inputs.update {
            it.copy(password = password)
        }
    }

    fun logUserIn(username: String, password: String) {

    }
}


