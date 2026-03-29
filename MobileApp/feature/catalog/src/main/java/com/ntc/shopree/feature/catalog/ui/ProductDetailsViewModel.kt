package com.ntc.shopree.feature.catalog.ui

import androidx.lifecycle.viewModelScope
import com.ntc.shopree.core.model.AsyncState
import com.ntc.shopree.core.model.Product
import com.ntc.shopree.core.model.ProductVariant
import com.ntc.shopree.core.ui.ShopreeViewModel
import com.ntc.shopree.feature.cart.domain.AddToCartUseCase
import com.ntc.shopree.core.network.domain.GetSingleProductUseCase
import com.ntc.shopree.feature.catalog.mappers.toCartItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProductDetailsUiState(
    val productState: AsyncState<Product> = AsyncState.Loading,
    val selectedVariant: ProductVariant? = null,
    val isAddingToCart: Boolean = false
)

sealed interface ProductDetailsEvent {
    data class ShowSnackbar(val message: String) : ProductDetailsEvent
}

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val getSingleProductUseCase: GetSingleProductUseCase,
    private val addToCartUseCase: AddToCartUseCase
) : ShopreeViewModel<ProductDetailsUiState, ProductDetailsEvent>(ProductDetailsUiState()) {

    fun loadProductDetails(slug: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(productState = AsyncState.Loading) }
            val result = getSingleProductUseCase(slug)
            result.onSuccess { product ->
                _uiState.update {
                    it.copy(
                        productState = AsyncState.Success(product),
                        selectedVariant = product.variants.firstOrNull()
                    )
                }
            }
            result.onFailure { error ->
                _uiState.update { it.copy(productState = AsyncState.Error(error.message ?: "Unknown error")) }
                emitEvent(ProductDetailsEvent.ShowSnackbar(error.message ?: "Failed to load product"))
            }
        }
    }

    fun selectVariant(variant: ProductVariant) {
        _uiState.update { it.copy(selectedVariant = variant) }
    }

    fun addProductToCart(product: Product, variant: ProductVariant?) {
        viewModelScope.launch {
            if (variant == null) {
                emitEvent(ProductDetailsEvent.ShowSnackbar("Please select a variant"))
                return@launch
            }
            _uiState.update { it.copy(isAddingToCart = true) }
            val cartItem = product.toCartItem(variant)
            val result = addToCartUseCase(cartItem)
            result.onSuccess {
                emitEvent(ProductDetailsEvent.ShowSnackbar("Product added to cart"))
            }
            result.onFailure {
                emitEvent(ProductDetailsEvent.ShowSnackbar("Error adding product to cart"))
            }
            _uiState.update { it.copy(isAddingToCart = false) }
        }
    }
}
