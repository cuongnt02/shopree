package com.ntc.shopree.feature.profile.ui

import androidx.lifecycle.viewModelScope
import com.ntc.shopree.core.ui.ShopreeViewModel
import com.ntc.shopree.feature.profile.domain.ChangePasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val changePasswordUseCase: ChangePasswordUseCase
) : ShopreeViewModel<ChangePasswordUiState, ChangePasswordEvent>(ChangePasswordUiState()) {

    fun changePassword(currentPassword: String, newPassword: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true) }
            changePasswordUseCase(currentPassword, newPassword)
                .onSuccess {
                    _uiState.update { it.copy(isSaving = false) }
                    emitEvent(ChangePasswordEvent.ShowSnackbar("Password changed successfully"))
                    emitEvent(ChangePasswordEvent.NavigateBack)
                }
                .onFailure {
                    _uiState.update { it.copy(isSaving = false) }
                    emitEvent(ChangePasswordEvent.ShowSnackbar("Failed to change password"))
                }
        }
    }
}
