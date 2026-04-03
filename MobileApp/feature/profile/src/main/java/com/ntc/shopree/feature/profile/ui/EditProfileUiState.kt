package com.ntc.shopree.feature.profile.ui

data class EditProfileUiState(
    val isSaving: Boolean = false,
    val isEditMode: Boolean = false,
    val showConfirmDialog: Boolean = false,
)

sealed interface EditProfileEvent {
    data object NavigateBack : EditProfileEvent
    data class ShowSnackbar(val message: String) : EditProfileEvent
}
