package com.ntc.shopree.feature.catalog.ui

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ntc.shopree.core.model.Product
import com.ntc.shopree.core.ui.components.ProductCard

@Composable
fun ProductsGrid(modifier: Modifier = Modifier, products: List<Product>) {
    LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = modifier) {
        items(products.size) { index ->
            ProductCard(imageSource = products[index].imageUrl, name = products[index].name, price = products[index].price.toString())
        }
    }
}


