package com.ntc.shopree.feature.catalog.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.NavKey
import com.ntc.shopree.core.ui.components.Avatar
import com.ntc.shopree.core.ui.components.SimpleSearchBar
import com.ntc.shopree.core.ui.icons.Icons
import com.ntc.shopree.core.ui.theme.ColorGrey700
import com.ntc.shopree.feature.cart.ui.CartButton
import kotlinx.serialization.Serializable

@Serializable
data object ProductsScreen : NavKey

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsScreen(
    onProductClick: (String) -> Unit,
    onCart: () -> Unit,
    onLogout: () -> Unit
) {
    val productsViewModel: ProductsViewModel = hiltViewModel()
    val state: ProductsUiState by productsViewModel.uiState.collectAsState()

    Column {
        CenterAlignedTopAppBar(title = { Text(text = "Shopree") }, navigationIcon = {}, actions = {
            Icon(
                imageVector = Icons.Outlined.Home,
                contentDescription = "home icon",
                modifier = Modifier.clickable {
                    onLogout()
                })
            CartButton(
                onNavigate = onCart
            )
        })
        if (state is ProductsUiState.Error) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "An error occurred: ${(state as ProductsUiState.Error).message}")
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { productsViewModel.retry() }) {
                    Text(text = "Retry")
                }
            }
        } else {
            SearchSection(state = state, productsViewModel = productsViewModel, modifier = Modifier)
            CategorySection(state = state)
            ProductSection(state = state, onProductClick = onProductClick)
        }
    }
}

@Composable
fun CategorySection(state: ProductsUiState) {
    when (state) {
        is ProductsUiState.Loading -> {
            CategorySectionSkeleton()
        }

        is ProductsUiState.Success -> {
            val categories = state.categories
            Categories(categories = categories.map { it.name })
        }

        is ProductsUiState.Error -> {
            Text(text = state.message, color = MaterialTheme.colorScheme.error)
            Log.e("ProductsScreen", "ProductsScreen: ", Exception(state.message))
        }
    }
}

@Composable
fun ProductSection(
    state: ProductsUiState,
    onProductClick: (String) -> Unit
) {
    when (state) {
        is ProductsUiState.Loading -> {
            ProductSectionSkeleton(8)
        }

        is ProductsUiState.Success -> {
            val products = state.products
            ProductsGrid(products = products, onProductClick = onProductClick)
        }

        is ProductsUiState.Error -> {
            Text(text = state.message, color = MaterialTheme.colorScheme.error)
        }
    }
}


@Composable
fun SearchSection(
    state: ProductsUiState,
    productsViewModel: ProductsViewModel,
    modifier: Modifier = Modifier
) {
    when (state) {
        is ProductsUiState.Loading -> {
            SearchSectionSkeleton()
        }

        is ProductsUiState.Success -> {
            val textFieldState = rememberTextFieldState(state.searchQuery)
            Row(modifier = modifier.wrapContentHeight()) {
                SimpleSearchBar(textFieldState = textFieldState, onSearch = { query ->
                    productsViewModel.searchProducts(query)
                }, searchResults = state.products.map { it.title })
                Filter()
            }
        }

        is ProductsUiState.Error -> {
            Text(text = state.message, color = MaterialTheme.colorScheme.error)
        }
    }

}
