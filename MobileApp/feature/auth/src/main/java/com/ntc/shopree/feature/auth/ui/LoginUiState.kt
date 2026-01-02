package com.ntc.shopree.feature.auth.ui

sealed interface LoginUiState {
    data object Loading: LoginUiState
    data class Success(val message: String): LoginUiState
    data class Error(val message: String): LoginUiState
}

