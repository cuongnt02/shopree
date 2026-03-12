package com.ntc.shopree.ui

import androidx.lifecycle.ViewModel
import com.ntc.shopree.core.datastore.SessionStore
import androidx.lifecycle.viewModelScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.ntc.shopree.core.datastore.SessionToken
import com.ntc.shopree.feature.auth.domain.CheckSessionUseCase
import com.ntc.shopree.feature.auth.domain.LogoutUseCase
import com.ntc.shopree.feature.auth.ui.LoginScreen
import com.ntc.shopree.feature.catalog.ui.ProductsScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed class AppState {
    object Loading: AppState()
    object Authenticated: AppState()
    object Unauthenticated: AppState()
}

@HiltViewModel
class AppViewModel @Inject constructor(
    private val checkSessionUseCase: CheckSessionUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val sessionStore: SessionStore
): ViewModel() {

    val backStack = NavBackStack<NavKey>(LoginScreen)
    private val _state = MutableStateFlow<AppState>(AppState.Loading)
    val state = _state.asStateFlow()

    init {
        checkSession()
    }

    private fun checkSession() {
        viewModelScope.launch {
            val tokens = sessionStore.tokens.first()
            if (tokens.rememberMe) {
                val result = checkSessionUseCase()
                result.onSuccess { isAuthenticated ->
                    _state.update { if(isAuthenticated) AppState.Authenticated else AppState.Unauthenticated }

                    if (isAuthenticated) {
                        backStack.clear()
                        backStack.add(ProductsScreen)
                    } else {
                        backStack.clear()
                        backStack.add(LoginScreen)
                    }
                }
            } else {
                _state.update { AppState.Unauthenticated }
                //backStack.clear()
                //backStack.add(LoginScreen)
            }

        }
    }

    fun logout() {
        backStack.clear()
        backStack.add(LoginScreen)
        _state.update { AppState.Unauthenticated }
        viewModelScope.launch {
            logoutUseCase()
        }
    }
}