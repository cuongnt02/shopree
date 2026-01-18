package com.ntc.shopree.feature.cart.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ntc.shopree.core.model.CartItem
import com.ntc.shopree.core.ui.utils.SnackbarController
import com.ntc.shopree.core.ui.utils.SnackbarEvent
import com.ntc.shopree.feature.cart.domain.AddToCartUseCase
import com.ntc.shopree.feature.cart.domain.ClearCartItemUseCase
import com.ntc.shopree.feature.cart.domain.ObserveCartQuantityUseCase
import com.ntc.shopree.feature.cart.domain.ObserveCartUseCase
import com.ntc.shopree.feature.cart.domain.ObserveTotalPriceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface CartUiState {
    data object Loading : CartUiState
    data class Success(
        val cartItems: List<CartItem>,
        val isUpdating: Boolean = false,
        val errorMessage: String? = null,
    ) : CartUiState

    data class Error(val message: String) : CartUiState
}

@HiltViewModel
class CartViewModel @Inject constructor(
    private val observeCartUseCase: ObserveCartUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    private val observeCartQuantityUseCase: ObserveCartQuantityUseCase,
    private val observeTotalPriceUseCase: ObserveTotalPriceUseCase,
    private val clearCartItemUseCase: ClearCartItemUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<CartUiState>(CartUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _quantity = MutableStateFlow(0)
    val quantity = _quantity.asStateFlow()

    private val _totalPrice = MutableStateFlow(0.0)
    val totalPrice = _totalPrice.asStateFlow()

    init {
        observeCart()
        observeCartQuantity()
        observeTotalPrice()
    }

    private fun observeTotalPrice() {
        viewModelScope.launch {
            val result = observeTotalPriceUseCase()
            result.onSuccess { flow ->
                flow.collect { totalPrice ->
                    _totalPrice.value = totalPrice
                }

            }
            result.onFailure {
                _uiState.value = CartUiState.Error(it.message ?: "Unable to observe total price")
            }
        }
    }

    private fun observeCart() {
        _uiState.value = CartUiState.Loading
        viewModelScope.launch {
            val result = observeCartUseCase()
            result.onSuccess { flow ->
                flow.collect { cartItems ->
                    _uiState.value = CartUiState.Success(cartItems = cartItems)
                }
            }
            result.onFailure {
                _uiState.value = CartUiState.Error(it.message ?: "Unable to observe cart items")
            }

        }
    }

    private fun observeCartQuantity() {
        viewModelScope.launch {
            val result = observeCartQuantityUseCase()
            result.onSuccess { flow ->
                flow.collect { quantity ->
                    _quantity.value = quantity
                }
            }
            result.onFailure {
                _uiState.value = CartUiState.Error(it.message ?: "Unable to observe cart Quantity")
            }
        }
    }

    fun addToCart(cartItem: CartItem) {
        if ( _uiState.value !is CartUiState.Success) {
            return
        }
        _uiState.update {
            (it as CartUiState.Success).copy(isUpdating = true)
        }
        viewModelScope.launch {
            val result = addToCartUseCase(
               cartItem
            )
            result.onSuccess {
                _uiState.update {
                    (it as CartUiState.Success).copy(isUpdating = false, errorMessage = null)
                }
                SnackbarController.sendEvent(SnackbarEvent(message = "Added to cart"))
            }
            result.onFailure {
                SnackbarController.sendEvent(SnackbarEvent(message = "Failed to add to cart"))
            }
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            val result = clearCartItemUseCase()
            result.onSuccess {
                SnackbarController.sendEvent(SnackbarEvent(message = "Cart cleared"))
            }
            result.onFailure {
                SnackbarController.sendEvent(SnackbarEvent(message = "Failed to clear cart"))
            }
        }
    }

}