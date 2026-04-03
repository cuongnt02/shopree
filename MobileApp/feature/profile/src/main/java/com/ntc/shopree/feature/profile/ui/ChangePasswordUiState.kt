package com.ntc.shopree.feature.profile.ui

data class ChangePasswordUiState(
    val isSaving: Boolean = false,
)

sealed interface ChangePasswordEvent {
    data object NavigateBack : ChangePasswordEvent
    data class ShowSnackbar(val message: String) : ChangePasswordEvent
}
