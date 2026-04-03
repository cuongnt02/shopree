package com.ntc.shopree.feature.profile.ui

import androidx.lifecycle.viewModelScope
import com.ntc.shopree.core.ui.ShopreeViewModel
import com.ntc.shopree.feature.profile.domain.UpdateProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val updateProfileUseCase: UpdateProfileUseCase
) : ShopreeViewModel<EditProfileUiState, EditProfileEvent>(EditProfileUiState()) {

    fun enterEditMode() {
        _uiState.update { it.copy(isEditMode = true) }
    }

    fun exitEditMode() {
        _uiState.update { it.copy(isEditMode = false) }
    }

    fun showConfirmDialog() {
        _uiState.update { it.copy(showConfirmDialog = true) }
    }

    fun dismissConfirmDialog() {
        _uiState.update { it.copy(showConfirmDialog = false) }
    }

    fun emitNoChanges() {
        emitEvent(EditProfileEvent.ShowSnackbar("No changes to save"))
    }

    fun updateProfile(name: String, phone: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true, showConfirmDialog = false) }
            updateProfileUseCase(name, phone)
                .onSuccess {
                    _uiState.update { it.copy(isSaving = false) }
                    emitEvent(EditProfileEvent.ShowSnackbar("Profile updated successfully"))
                    emitEvent(EditProfileEvent.NavigateBack)
                }
                .onFailure {
                    _uiState.update { it.copy(isSaving = false) }
                    emitEvent(EditProfileEvent.ShowSnackbar("Failed to update profile"))
                }
        }
    }
}
