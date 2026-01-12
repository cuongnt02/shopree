package com.ntc.shopree.feature.catalog.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ntc.shopree.core.datastore.SessionStore
import com.ntc.shopree.core.model.Category
import com.ntc.shopree.core.model.Product
import com.ntc.shopree.core.ui.utils.SnackbarController
import com.ntc.shopree.core.ui.utils.SnackbarEvent
import com.ntc.shopree.feature.catalog.domain.GetCategoriesUseCase
import com.ntc.shopree.feature.catalog.domain.GetProductsUseCase
import com.ntc.shopree.feature.catalog.domain.SearchProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface ProductsUiState {
    data object Loading : ProductsUiState
    data class Success(
        val categories: List<Category>, val products: List<Product>, val searchQuery: String
    ) : ProductsUiState

    data class Error(val message: String) : ProductsUiState
}

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getProductsUseCase: GetProductsUseCase,
    private val searchProductsUseCase: SearchProductsUseCase,
    private val sessionStore: SessionStore,
) : ViewModel() {
    private val _uiState = MutableStateFlow<ProductsUiState>(ProductsUiState.Loading)
    val uiState: StateFlow<ProductsUiState> = _uiState

    init {
        initialLoad()
    }

    private fun initialLoad() {
        viewModelScope.launch {
            _uiState.value = ProductsUiState.Loading
            try {
                val getCategoriesResult = async { getCategoriesUseCase() }
                val getProductsResult = async { getProductsUseCase() }

                val categoriesResult = getCategoriesResult.await()
                val productsResult = getProductsResult.await()

                val categories = categoriesResult.getOrThrow()
                val products = productsResult.getOrThrow()

                // TODO: Get search query from session store
                val searchQuery = ""

                _uiState.value = ProductsUiState.Success(categories, products, searchQuery)
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
                // WARN: This might cause a problem when trying to load products separately, it will load the whole page and call the apis again
                _uiState.value = ProductsUiState.Success(categories, emptyList(), "")
            }
            result.onFailure {
                SnackbarController.sendEvent(SnackbarEvent(message = "Error loading categories"))
            }
        }
    }

    private fun loadProducts() {
        viewModelScope.launch {
            _uiState.value = ProductsUiState.Loading

            val result = getProductsUseCase()
            result.onSuccess { products ->
                // WARN: This might cause a problem when trying to load products separately, it will load the whole page and call the apis again
                _uiState.value = ProductsUiState.Success(emptyList(), products, "")
            }
            result.onFailure {
                SnackbarController.sendEvent(SnackbarEvent(message = "Error loading products"))
            }
        }
    }

    fun searchProducts(query: String) {
        viewModelScope.launch {
            _uiState.value = ProductsUiState.Loading

            val result = searchProductsUseCase(query)
            result.onSuccess { products ->
                _uiState.value = ProductsUiState.Success(emptyList(), products, query)
            }
            result.onFailure {
                SnackbarController.sendEvent(SnackbarEvent(message = "Error searching products"))
            }
        }
    }


}




