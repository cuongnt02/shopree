package com.ntc.shopree.ui.screen.products

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ntc.shopree.data.app.AppContainer
import com.ntc.shopree.ui.components.Categories
import com.ntc.shopree.ui.viewmodels.ProductsUiState
import com.ntc.shopree.ui.viewmodels.ProductsViewModel

@Composable
fun ProductsScreen(appContainer: AppContainer) {
    val productsViewModel: ProductsViewModel = viewModel(factory = appContainer.productsViewModelFactory)
    val state by productsViewModel.uiState.collectAsStateWithLifecycle()
    when (state) {
        is ProductsUiState.Loading -> {
            // TODO: Show loading indicator
        }
        is ProductsUiState.Success -> {
            val categories = (state as ProductsUiState.Success).categories
            Categories(categories = categories.map { it.name })
        }
        is ProductsUiState.Error -> {
            // TODO: Show error message
            val message = (state as ProductsUiState.Error).message
            Log.e("ProductsScreen", "ProductsScreen: ", Exception(message) )
        }
    }
}