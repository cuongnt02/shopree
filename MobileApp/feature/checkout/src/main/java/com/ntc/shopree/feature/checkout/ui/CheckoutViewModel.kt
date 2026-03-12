package com.ntc.shopree.feature.checkout.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ntc.shopree.core.model.CartItem
import com.ntc.shopree.core.model.dto.OrderResponse
import com.ntc.shopree.feature.checkout.domain.ClearCartUseCase
import com.ntc.shopree.feature.checkout.domain.ObserveCheckoutCartUseCase
import com.ntc.shopree.feature.checkout.domain.PlaceOrderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface CheckoutUiState {
    data object Idle : CheckoutUiState
    data object Loading : CheckoutUiState
    data class Success(val order: OrderResponse) : CheckoutUiState
    data class Error(val message: String) : CheckoutUiState
}

@HiltViewModel
class CheckoutViewModel @Inject constructor(
    private val observeCartUseCase: ObserveCheckoutCartUseCase,
    private val placeOrderUseCase: PlaceOrderUseCase,
    private val clearCartUseCase: ClearCartUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<CheckoutUiState>(CheckoutUiState.Idle)
    val uiState = _uiState.asStateFlow()

    val cartItems: StateFlow<List<CartItem>> =
        observeCartUseCase().getOrElse { flowOf(emptyList()) }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun placeOrder() {
        val items = cartItems.value
        if (items.isEmpty()) return
        viewModelScope.launch {
            _uiState.value = CheckoutUiState.Loading
            placeOrderUseCase(items).onSuccess { order ->
                clearCartUseCase()
                _uiState.value = CheckoutUiState.Success(order)
            }.onFailure { e ->
                _uiState.value = CheckoutUiState.Error(e.message ?: "Failed to place order")
            }
        }

    }
}