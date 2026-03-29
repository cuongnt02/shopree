package com.ntc.shopree.feature.auth.ui

data class LoginUiState(
    val isLoading: Boolean = false,
    val validation: LoginFormErrors = LoginFormErrors(),
    val rememberMe: Boolean = false
)

sealed interface LoginEvent {
    data object NavigateToHome : LoginEvent
    data class ShowSnackbar(val message: String) : LoginEvent
}
