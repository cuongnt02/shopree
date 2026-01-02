package com.ntc.shopree.feature.catalog.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ntc.shopree.core.model.Category
import com.ntc.shopree.core.model.Product
import com.ntc.shopree.feature.catalog.domain.GetCategoriesUseCase
import com.ntc.shopree.feature.catalog.domain.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface ProductsUiState {
    data object Loading: ProductsUiState
    data class Success(val categories: List<Category>, val products: List<Product>): ProductsUiState
    data class Error(val message: String): ProductsUiState
}

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getProductsUseCase: GetProductsUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow<ProductsUiState>(ProductsUiState.Loading)
    val uiState: StateFlow<ProductsUiState> = _uiState

    init {
        loadCategories()
    }

    private fun initialLoad() {
        viewModelScope.launch {
            _uiState.value = ProductsUiState.Loading
            try {
                val getCategoriesResult = async { getCategoriesUseCase()}
                val getProductsResult = async { getProductsUseCase()}

                val categoriesResult = getCategoriesResult.await()
                val productsResult = getProductsResult.await()

                val categories = categoriesResult.getOrThrow()
                val products = productsResult.getOrThrow()

                _uiState.value = ProductsUiState.Success(categories, products)
            } catch (e: Exception) {
                _uiState.value = ProductsUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    private fun loadCategories() {
        viewModelScope.launch {
            _uiState.value = ProductsUiState.Loading

            val result = getCategoriesUseCase()
            result.onSuccess { categories ->
                _uiState.update { currentState ->
                    (currentState as? ProductsUiState.Success)?.copy(categories = categories) ?: currentState
                }
            }
        }
    }

    private fun loadProducts() {
        viewModelScope.launch {
            _uiState.value = ProductsUiState.Loading

            val result = getProductsUseCase()
            result.onSuccess { products ->
                _uiState.update { currentState ->
                    (currentState as? ProductsUiState.Success)?.copy(products = products) ?: currentState
                }
            }
        }
    }


}




