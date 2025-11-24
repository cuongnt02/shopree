package com.ntc.shopree.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ntc.shopree.domain.models.Category
import com.ntc.shopree.domain.usecase.GetCategoriesUseCase
import com.ntc.shopree.utils.Factory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed interface ProductsUiState {
    data object Loading: ProductsUiState
    data class Success(val categories: List<Category>): ProductsUiState
    data class Error(val message: String): ProductsUiState
}

class ProductsViewModel(private val getCategoriesUseCase: GetCategoriesUseCase): ViewModel() {
    private val _uiState = MutableStateFlow<ProductsUiState>(ProductsUiState.Loading)
    val uiState: StateFlow<ProductsUiState> = _uiState

    init {
        loadCategories()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            _uiState.value = ProductsUiState.Loading

            val result = getCategoriesUseCase()
            result.onSuccess { categories ->
                _uiState.value = ProductsUiState.Success(categories)
            }.onFailure { exception ->
                _uiState.value = ProductsUiState.Error(exception.message ?: "Unknown error")
            }
        }
    }


}

class ProductsViewModelFactory(private val getCategoriesUseCase: GetCategoriesUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductsViewModel::class.java))
            @Suppress("UNCHECKED_CAST")
            return ProductsViewModel(getCategoriesUseCase) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
