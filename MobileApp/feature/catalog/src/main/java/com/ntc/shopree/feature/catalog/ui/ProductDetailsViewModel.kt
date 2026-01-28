package com.ntc.shopree.feature.catalog.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ntc.shopree.core.model.Product
import com.ntc.shopree.core.ui.utils.SnackbarController
import com.ntc.shopree.core.ui.utils.SnackbarEvent
import com.ntc.shopree.feature.cart.domain.AddToCartUseCase
import com.ntc.shopree.feature.catalog.domain.GetSingleProductUseCase
import com.ntc.shopree.feature.catalog.mappers.toCartItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface ProductDetailsUiState {
    data object Loading : ProductDetailsUiState
    data class Success(val product: Product) : ProductDetailsUiState
    data class Error(val message: String) : ProductDetailsUiState
}

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val getSingleProductUseCase: GetSingleProductUseCase,
    private val addToCartUseCase: AddToCartUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow<ProductDetailsUiState>(ProductDetailsUiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun loadProductDetails(slug: String) {
        viewModelScope.launch {
            _uiState.value = ProductDetailsUiState.Loading

            val result = getSingleProductUseCase(slug)
            result.onSuccess { product ->
                _uiState.value = ProductDetailsUiState.Success(product)
            }
            result.onFailure {
                _uiState.value = ProductDetailsUiState.Error(it.message ?: "Unknown error")
            }
        }
    }

    fun addProductToCart(product: Product) {
        viewModelScope.launch {
            val cartItem = product.toCartItem()
            val result = addToCartUseCase(cartItem)
            result.onSuccess {
                SnackbarController.sendEvent(SnackbarEvent(message = "Product added to cart"))
            }
            result.onFailure {
                SnackbarController.sendEvent(SnackbarEvent(message = "Error adding product to cart"))
            }

        }
    }
}