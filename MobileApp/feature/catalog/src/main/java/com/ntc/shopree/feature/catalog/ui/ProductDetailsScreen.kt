package com.ntc.shopree.feature.catalog.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.NavKey
import coil3.compose.AsyncImage
import com.ntc.shopree.core.ui.components.PrimaryButton
import com.ntc.shopree.core.ui.icons.Icons
import kotlinx.serialization.Serializable

@Serializable
data class ProductDetails(val slug: String) : NavKey


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailsScreen(
    navKey: ProductDetails, onBack: () -> Unit, onCheckout: () -> Unit, onCart: () -> Unit
) {
    var productLiked by remember { mutableStateOf(false) }
    val productDetailsViewModel: ProductDetailsViewModel = hiltViewModel()
    val state: ProductDetailsUiState by productDetailsViewModel.uiState.collectAsState()

    LaunchedEffect(productDetailsViewModel) {
        productDetailsViewModel.loadProductDetails(navKey.slug)
    }
    Box(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(state = rememberScrollState())) {
        Column(
            modifier = Modifier.matchParentSize()
        ) {
            CenterAlignedTopAppBar(title = { Text(text = "Details") }, navigationIcon = {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = "arrow back icon",
                    modifier = Modifier.clickable {
                        onBack()
                    })
            }, actions = {
                Icon(
                    imageVector = if (productLiked) Icons.Filled.Heart else Icons.Outlined.Heart,
                    contentDescription = "heart icon",
                    modifier = Modifier.clickable {
                        productLiked = !productLiked
                    })
            })

            ProductImage(state = state, modifier = Modifier.fillMaxWidth())
            ProductDescription(state = state)
            Spacer(modifier = Modifier.height(100.dp))

        }
        Row(modifier = Modifier.align(alignment = Alignment.BottomCenter)) {
            IconButton(onClick = onCart) {
                Icon(
                    imageVector = Icons.Outlined.Heart, contentDescription = "buy icon"
                )
            }
            PrimaryButton(onclick = onCheckout) { Text(text = "Check out", style = MaterialTheme.typography.labelMedium) }
        }
    }


}

@Composable
fun ProductImage(state: ProductDetailsUiState, modifier: Modifier = Modifier) {
    when (state) {
        is ProductDetailsUiState.Loading -> {
            CircularProgressIndicator()
        }

        is ProductDetailsUiState.Success -> {
            AsyncImage(
                model = state.product.imageUrl,
                contentDescription = "product image",
                modifier = modifier
            )
        }

        is ProductDetailsUiState.Error -> {
            // TODO: Show error message
            val message = state.message
        }
    }
}

@Composable
fun ProductDescription(
    state: ProductDetailsUiState,
    modifier: Modifier = Modifier,
) {
    when (state) {
        is ProductDetailsUiState.Loading -> {
            CircularProgressIndicator()
        }

        is ProductDetailsUiState.Success -> {
            val product = state.product
            Column(modifier = modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Description", style = MaterialTheme.typography.labelMedium)
                    Text(
                        text = product.price.toString(), style = MaterialTheme.typography.labelLarge
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = product.name, style = MaterialTheme.typography.displayMedium)
                Text(
                    text = product.description,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify
                )
                Spacer(modifier = Modifier.height(8.dp))
                // TODO: Product variants
                Spacer(modifier = Modifier.height(16.dp))

            }

        }

        is ProductDetailsUiState.Error -> {
            // TODO: Show error message
            val message = state.message
        }
    }
}