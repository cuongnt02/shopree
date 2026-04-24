package com.ntc.shopree.feature.auth.ui

data class RegisterUiState(val isLoading: Boolean = false)

sealed interface RegisterEvent {
    data object NavigateToHome : RegisterEvent
    data class ShowSnackbar(val message: String) : RegisterEvent
}
