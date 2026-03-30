package com.ntc.shopree.feature.profile.ui

import androidx.lifecycle.viewModelScope
import com.ntc.shopree.core.ui.ShopreeViewModel
import com.ntc.shopree.feature.profile.domain.GetProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase
) : ShopreeViewModel<ProfileUiState, ProfileEvent>(ProfileUiState()) {

    init {
        loadProfile()
    }

    private fun loadProfile() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            getProfileUseCase()
                .onSuccess { user ->
                    _uiState.update { it.copy(isLoading = false, user = user) }
                }
                .onFailure {
                    _uiState.update { it.copy(isLoading = false) }
                    emitEvent(ProfileEvent.ShowSnackbar("Failed to load profile"))
                }
        }
    }
}
