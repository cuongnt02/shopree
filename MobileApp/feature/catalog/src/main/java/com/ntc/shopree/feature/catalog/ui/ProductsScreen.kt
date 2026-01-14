package com.ntc.shopree.feature.catalog.ui

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.NavKey
import com.ntc.shopree.core.ui.components.Avatar
import com.ntc.shopree.core.ui.components.Search
import com.ntc.shopree.core.ui.components.SimpleSearchBar
import kotlinx.serialization.Serializable

@Serializable
data object ProductsScreen: NavKey
@Composable
fun ProductsScreen(onProductClick: (String) -> Unit) {
    val productsViewModel: ProductsViewModel = hiltViewModel()
    val state: ProductsUiState by productsViewModel.uiState.collectAsState()

    Column {
        ProfileSection(state = state)
        SearchSection(state = state, productsViewModel = productsViewModel, modifier = Modifier)
        CategorySection(state = state)
        ProductSection(state = state, onProductClick = onProductClick)
    }
}
@Composable
fun CategorySection(state: ProductsUiState) {
    when (state) {
        is ProductsUiState.Loading -> {
            CircularProgressIndicator()
        }
        is ProductsUiState.Success -> {
            val categories = state.categories
            Categories(categories = categories.map { it.name })
        }
        is ProductsUiState.Error -> {
            // TODO: Show error message
            val message = state.message
            Log.e("ProductsScreen", "ProductsScreen: ", Exception(message) )
        }
    }
}

@Composable
fun ProductSection(state: ProductsUiState, onProductClick: (String) -> Unit) {
    when (state) {
        is ProductsUiState.Loading -> {
            CircularProgressIndicator()
        }
        is ProductsUiState.Success -> {
            val products = state.products
            ProductsGrid(products = products, onProductClick = onProductClick)
        }
        is ProductsUiState.Error -> {
            // TODO: Show error message
            val message = state.message

        }
    }
}
// TECHDEBT: The avatar should have image, name, greeting and also options to logout or go to settings (profile screen)
@Composable
fun ProfileSection(state: ProductsUiState, modifier: Modifier = Modifier) {
    Row(modifier = modifier.wrapContentHeight()) {
        Avatar()
        Column {
            Text(text = "Hello John")
            Text(text = "Welcome back")
        }
    }
}

@Composable
fun SearchSection(state: ProductsUiState, productsViewModel: ProductsViewModel, modifier: Modifier = Modifier) {
    when(state) {
        is ProductsUiState.Loading -> {
            CircularProgressIndicator()
        }
        is ProductsUiState.Success -> {
            val textFieldState = rememberTextFieldState(state.searchQuery)
            Row(modifier = modifier.wrapContentHeight()) {
                SimpleSearchBar(
                    textFieldState = textFieldState,
                    onSearch = { query ->
                        productsViewModel.searchProducts(query)
                    },
                    searchResults = state.products.map { it.name }
                )
                Filter()
            }
        }
        is ProductsUiState.Error -> {
            // TODO: Show error message
            val message = state.message
        }
    }

}
