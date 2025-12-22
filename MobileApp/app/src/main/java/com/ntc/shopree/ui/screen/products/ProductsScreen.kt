package com.ntc.shopree.ui.screen.products

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ntc.shopree.data.app.AppContainer
import com.ntc.shopree.ui.components.Avatar
import com.ntc.shopree.ui.components.Search
import com.ntc.shopree.ui.viewmodels.ProductsUiState
import com.ntc.shopree.ui.viewmodels.ProductsViewModel

@Composable
fun ProductsScreen(appContainer: AppContainer) {
    val productsViewModel: ProductsViewModel = viewModel(factory = appContainer.productsViewModelFactory)
    val state: ProductsUiState by productsViewModel.uiState.collectAsState()

    Column {
        ProfileSection()
        SearchSection()
        CategorySection(state = state)
        ProductSection(state = state)
    }
}
@Composable
fun CategorySection(state: ProductsUiState) {
    when (state) {
        is ProductsUiState.Loading -> {
            // TODO: Show loading indicator
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
fun ProductSection(state: ProductsUiState) {
    when (state) {
        is ProductsUiState.Loading -> {
            // TODO: Show loading indicator
        }
        is ProductsUiState.Success -> {
            val products = state.products
            ProductsGrid(products = products)
        }
        is ProductsUiState.Error -> {
            // TODO: Show error message
        }
    }
}

@Composable
fun ProfileSection(modifier: Modifier = Modifier) {
    Row() {
        Avatar()
        Column {
            Text(text = "Hello John")
            Text(text = "Welcome back")
        }
    }
}

@Composable
fun SearchSection(modifier: Modifier = Modifier) {
    Row() {
        Search()
        Filter()
    }
}