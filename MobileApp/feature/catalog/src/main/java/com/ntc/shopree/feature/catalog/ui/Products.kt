package com.ntc.shopree.feature.catalog.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ntc.shopree.core.model.Product
import com.ntc.shopree.core.ui.components.ProductCard
import com.ntc.shopree.core.ui.components.ProductCardV2
import com.ntc.shopree.core.ui.theme.spacing1
import com.ntc.shopree.core.ui.utils.formatVnd

@Composable
fun ProductsGrid(modifier: Modifier = Modifier, products: List<Product>, onProductClick: (String) -> Unit) {
    LazyVerticalStaggeredGrid(columns = StaggeredGridCells.Fixed(2), modifier = modifier.padding(horizontal = spacing1)) {
        items(products.size) { index ->
            ProductCardV2(imageSource = products[index].mainImage, name = products[index].title, price = formatVnd(products[index].price), onClick = { onProductClick(products[index].slug)})
        }
    }
}


