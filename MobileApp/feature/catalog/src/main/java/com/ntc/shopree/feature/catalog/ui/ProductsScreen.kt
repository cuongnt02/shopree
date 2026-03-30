package com.ntc.shopree.feature.catalog.ui

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.NavKey
import com.ntc.shopree.core.model.AsyncState
import com.ntc.shopree.core.model.Category
import com.ntc.shopree.core.model.Product
import com.ntc.shopree.core.ui.components.SimpleSearchBar
import com.ntc.shopree.core.ui.icons.Icons
import com.ntc.shopree.core.ui.theme.spacing1
import com.ntc.shopree.core.ui.utils.ObserveAsEvents
import com.ntc.shopree.core.ui.utils.SnackbarController
import com.ntc.shopree.core.ui.utils.SnackbarEvent
import com.ntc.shopree.core.ui.theme.spacing2
import com.ntc.shopree.feature.cart.ui.CartButton
import com.ntc.shopree.feature.cart.ui.CartViewModel
import kotlinx.serialization.Serializable

@Serializable
data object ProductsScreen : NavKey

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsScreen(
    onProductClick: (String) -> Unit,
    onCart: () -> Unit,
    onLogout: () -> Unit,
    onProfile: () -> Unit
) {
    val productsViewModel: ProductsViewModel = hiltViewModel()
    val cartViewModel: CartViewModel = hiltViewModel()
    val state: ProductsUiState by productsViewModel.uiState.collectAsStateWithLifecycle()
    val cartQuantity by cartViewModel.quantity.collectAsStateWithLifecycle()

    ObserveAsEvents(productsViewModel.events) { event ->
        when (event) {
            is ProductsEvent.ShowSnackbar -> SnackbarController.sendEvent(SnackbarEvent(event.message))
        }
    }

    Column {
        CenterAlignedTopAppBar(title = { Text(text = "Shopree") }, navigationIcon = {
            Icon(
                imageVector = Icons.Outlined.Logout,
                contentDescription = "logout icon",
                modifier = Modifier
                    .clickable { onLogout() }
                    .size(32.dp)
                    .padding(start = spacing2)
            )
        }, actions = {
            CartButton(
                quantity = cartQuantity,
                onNavigate = onCart
            )
            Spacer(Modifier.width(spacing1))
            Icon(
                imageVector = Icons.Filled.CheckCircle,
                contentDescription = "profile",
                modifier = Modifier
                    .clickable { onProfile() }
                    .size(32.dp)
            )
            Spacer(Modifier.width(spacing2))
        })
        SearchSection(
            searchState = state.searchState,
            productsState = state.productsState,
            productsViewModel = productsViewModel,
            modifier = Modifier
        )
        CategorySection(
            categoriesState = state.categoriesState,
            onCategoryClick = { productsViewModel.filterByCategory(it) }
        )
        ProductSection(
            productsState = state.productsState,
            onProductClick = onProductClick
        )
    }
}

@Composable
fun CategorySection(categoriesState: AsyncState<CategoriesState>, onCategoryClick: (Category) -> Unit) {
    when (categoriesState) {
        is AsyncState.Loading -> {
            CategorySectionSkeleton()
        }

        is AsyncState.Success -> {
            Categories(
                categories = categoriesState.data.categories,
                selectedCategory = categoriesState.data.selectedCategory,
                onCategoryClick = onCategoryClick
            )
        }

        is AsyncState.Error -> {
            Text(text = categoriesState.message, color = MaterialTheme.colorScheme.error)
            Log.e("ProductsScreen", "CategorySection: ", Exception(categoriesState.message))
        }
    }
}

@Composable
fun ProductSection(
    productsState: AsyncState<List<Product>>,
    onProductClick: (String) -> Unit
) {
    when (productsState) {
        is AsyncState.Loading -> {
            ProductSectionSkeleton(8)
        }

        is AsyncState.Success -> {
            ProductsGrid(products = productsState.data, onProductClick = onProductClick)
        }

        is AsyncState.Error -> {
            Text(text = productsState.message, color = MaterialTheme.colorScheme.error)
        }
    }
}

@Composable
fun SearchSection(
    searchState: AsyncState<String>,
    productsState: AsyncState<List<Product>>,
    productsViewModel: ProductsViewModel,
    modifier: Modifier = Modifier
) {
    when (searchState) {
        is AsyncState.Loading -> {
            SearchSectionSkeleton()
        }

        is AsyncState.Success -> {
            val textFieldState = rememberTextFieldState(searchState.data)
            LaunchedEffect(searchState.data) {
                if (searchState.data != textFieldState.text.toString()) {
                    textFieldState.edit { replace(0, length, searchState.data) }
                }
            }
            val searchResults = (productsState as? AsyncState.Success)?.data?.map { it.title } ?: emptyList()
            Row(modifier = modifier.wrapContentHeight()) {
                SimpleSearchBar(
                    textFieldState = textFieldState,
                    onSearch = { query -> productsViewModel.searchProducts(query) },
                    searchResults = searchResults
                )
                Filter()
            }
        }

        is AsyncState.Error -> {
            Text(text = searchState.message, color = MaterialTheme.colorScheme.error)
        }
    }
}
