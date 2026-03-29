package com.ntc.shopree.feature.checkout.ui

import androidx.lifecycle.viewModelScope
import com.ntc.shopree.core.model.CartItem
import com.ntc.shopree.core.model.dto.OrderResponse
import com.ntc.shopree.core.ui.ShopreeViewModel
import com.ntc.shopree.feature.checkout.domain.ClearCartUseCase
import com.ntc.shopree.feature.checkout.domain.ObserveCheckoutCartUseCase
import com.ntc.shopree.feature.checkout.domain.PlaceOrderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
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

sealed interface CheckoutEvent {
    data class OrderPlaced(val orderNumber: String, val totalCents: Long) : CheckoutEvent
    data class ShowSnackbar(val message: String) : CheckoutEvent
}

@HiltViewModel
class CheckoutViewModel @Inject constructor(
    private val observeCartUseCase: ObserveCheckoutCartUseCase,
    private val placeOrderUseCase: PlaceOrderUseCase,
    private val clearCartUseCase: ClearCartUseCase
) : ShopreeViewModel<CheckoutUiState, CheckoutEvent>(CheckoutUiState.Idle) {

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
                emitEvent(CheckoutEvent.OrderPlaced(order.orderNumber, order.totalCents))
            }.onFailure { e ->
                _uiState.value = CheckoutUiState.Error(e.message ?: "Failed to place order")
                emitEvent(CheckoutEvent.ShowSnackbar(e.message ?: "Failed to place order"))
            }
        }
    }
}
