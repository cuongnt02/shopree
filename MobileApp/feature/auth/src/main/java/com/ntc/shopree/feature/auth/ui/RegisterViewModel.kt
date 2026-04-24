package com.ntc.shopree.feature.auth.ui

import androidx.lifecycle.viewModelScope
import com.ntc.shopree.core.ui.ShopreeViewModel
import com.ntc.shopree.feature.auth.domain.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
): ShopreeViewModel<RegisterUiState, RegisterEvent>(RegisterUiState()) {
    fun register(name: String, email: String, phone: String, password: String) {
        if (_uiState.value.isLoading) return
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            registerUseCase(name, email, phone, password)
                .onSuccess { emitEvent(RegisterEvent.NavigateToHome) }
                .onFailure { emitEvent(RegisterEvent.ShowSnackbar(it.message ?: "Registration failed")) }
            _uiState.update { it.copy(isLoading = false) }
        }
    }
}
