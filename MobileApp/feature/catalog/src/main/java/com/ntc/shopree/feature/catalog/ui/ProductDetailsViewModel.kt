package com.ntc.shopree.feature.catalog.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ntc.shopree.core.model.Product
import com.ntc.shopree.feature.catalog.domain.GetSingleProductUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface ProductDetailsUiState {
    data object Loading : ProductDetailsUiState
    data class Success(val product: Product) : ProductDetailsUiState
    data class Error(val message: String) : ProductDetailsUiState
}

class ProductDetailsViewModel @Inject constructor(
    private val getSingleProductUseCase: GetSingleProductUseCase
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
}