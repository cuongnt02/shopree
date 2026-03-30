package com.ntc.shopree.feature.profile.ui

import com.ntc.shopree.core.model.User

data class ProfileUiState(
    val isLoading: Boolean = false,
    val user: User? = null,
)

sealed interface ProfileEvent {
    data object NavigateBack : ProfileEvent
    data class ShowSnackbar(val message: String) : ProfileEvent
}
