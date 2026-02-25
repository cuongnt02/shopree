package com.ntc.shopree.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ntc.shopree.feature.auth.domain.CheckSessionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed class AppState {
    object Loading: AppState()
    object Authenticated: AppState()
    object Unauthenticated: AppState()
}

@HiltViewModel
class AppViewModel @Inject constructor(
    private val checkSessionUseCase: CheckSessionUseCase
): ViewModel() {
    private val _state = MutableStateFlow<AppState>(AppState.Loading)
    val state = _state.asStateFlow()

    init {
        checkSession()
    }

    private fun checkSession() {
        viewModelScope.launch {
            val result = checkSessionUseCase()
            result.onSuccess { isAuthenticated ->
                _state.update { if(isAuthenticated) AppState.Authenticated else AppState.Unauthenticated }
            }
        }
    }
}