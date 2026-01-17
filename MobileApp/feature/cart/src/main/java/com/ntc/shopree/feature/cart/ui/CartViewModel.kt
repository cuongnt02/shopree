package com.ntc.shopree.feature.cart.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ntc.shopree.core.model.CartItem
import com.ntc.shopree.core.ui.utils.SnackbarController
import com.ntc.shopree.core.ui.utils.SnackbarEvent
import com.ntc.shopree.feature.cart.domain.AddToCartUseCase
import com.ntc.shopree.feature.cart.domain.ObserveCartQuantityUseCase
import com.ntc.shopree.feature.cart.domain.ObserveCartUseCase
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
        val errorMessage: String? = null
    ) : CartUiState

    data class Error(val message: String) : CartUiState
}

@HiltViewModel
class CartViewModel @Inject constructor(
    private val observeCartUseCase: ObserveCartUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    private val observeCartQuantityUseCase: ObserveCartQuantityUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<CartUiState>(CartUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _quantity = MutableStateFlow(0)
    val quantity = _quantity.asStateFlow()

    init {
        observeCart()
        observeCartQuantity()
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

}