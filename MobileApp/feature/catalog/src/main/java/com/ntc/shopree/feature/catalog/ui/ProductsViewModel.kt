package com.ntc.shopree.feature.catalog.ui

import androidx.lifecycle.viewModelScope
import com.ntc.shopree.core.datastore.SessionStore
import com.ntc.shopree.core.model.AsyncState
import com.ntc.shopree.core.model.Category
import com.ntc.shopree.core.model.Product
import com.ntc.shopree.core.ui.ShopreeViewModel
import com.ntc.shopree.feature.cart.domain.AddToCartUseCase
import com.ntc.shopree.feature.catalog.domain.GetCategoriesUseCase
import com.ntc.shopree.feature.catalog.domain.GetProductsByCategoryUseCase
import com.ntc.shopree.feature.catalog.domain.GetProductsUseCase
import com.ntc.shopree.feature.catalog.domain.SearchProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CategoriesState(
    val categories: List<Category>,
    val selectedCategory: Category?
)

data class ProductsUiState(
    val searchState: AsyncState<String> = AsyncState.Success(""),
    val categoriesState: AsyncState<CategoriesState> = AsyncState.Loading,
    val productsState: AsyncState<List<Product>> = AsyncState.Loading
)

sealed interface ProductsEvent {
    data class ShowSnackbar(val message: String) : ProductsEvent
}

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getProductsUseCase: GetProductsUseCase,
    private val searchProductsUseCase: SearchProductsUseCase,
    private val getProductsByCategoryUseCase: GetProductsByCategoryUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    private val sessionStore: SessionStore,
) : ShopreeViewModel<ProductsUiState, ProductsEvent>(ProductsUiState()) {

    init {
        initialLoad()
    }

    fun retry() {
        initialLoad()
    }

    private fun initialLoad() {
        _uiState.value = ProductsUiState()
        viewModelScope.launch(Dispatchers.IO) {
            launch {
                val result = getCategoriesUseCase()
                result.onSuccess { categories ->
                    _uiState.update { it.copy(categoriesState = AsyncState.Success(CategoriesState(categories, null))) }
                }
                result.onFailure { e ->
                    _uiState.update { it.copy(categoriesState = AsyncState.Error(e.message ?: "Error loading categories")) }
                }
            }
            launch {
                val result = getProductsUseCase()
                result.onSuccess { products ->
                    _uiState.update { it.copy(productsState = AsyncState.Success(products)) }
                }
                result.onFailure { e ->
                    _uiState.update { it.copy(productsState = AsyncState.Error(e.message ?: "Error loading products")) }
                }
            }
        }
    }

    fun filterByCategory(category: Category) {
        val currentCats = (_uiState.value.categoriesState as? AsyncState.Success)?.data ?: return
        val newSelection = if (currentCats.selectedCategory?.slug == category.slug) null else category

        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { it.copy(productsState = AsyncState.Loading) }
            val result = if (newSelection == null) getProductsUseCase() else getProductsByCategoryUseCase(newSelection.slug)
            result.onSuccess { products ->
                _uiState.update { state ->
                    val cats = (state.categoriesState as? AsyncState.Success)?.data
                    state.copy(
                        productsState = AsyncState.Success(products),
                        categoriesState = if (cats != null) AsyncState.Success(cats.copy(selectedCategory = newSelection)) else state.categoriesState,
                        searchState = AsyncState.Success("")
                    )
                }
            }
            result.onFailure { e ->
                _uiState.update { it.copy(productsState = AsyncState.Error(e.message ?: "Error loading products")) }
                emitEvent(ProductsEvent.ShowSnackbar("Error loading products"))
            }
        }
    }

    fun searchProducts(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { it.copy(productsState = AsyncState.Loading) }
            val result = searchProductsUseCase(query)
            result.onSuccess { products ->
                _uiState.update { state ->
                    val cats = (state.categoriesState as? AsyncState.Success)?.data
                    state.copy(
                        productsState = AsyncState.Success(products),
                        searchState = AsyncState.Success(query),
                        categoriesState = if (cats != null) AsyncState.Success(cats.copy(selectedCategory = null)) else state.categoriesState
                    )
                }
            }
            result.onFailure { e ->
                _uiState.update { it.copy(productsState = AsyncState.Error(e.message ?: "Error searching products")) }
                emitEvent(ProductsEvent.ShowSnackbar("Error searching products"))
            }
        }
    }
}
