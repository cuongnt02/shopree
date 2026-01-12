package com.ntc.shopree.feature.catalog.ui

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

fun EntryProviderScope<NavKey>.productsEntryBuilder(backstack: NavBackStack<NavKey>) {
    entry<ProductsScreen> {
        ProductsScreen(onProductClick = {
            backstack.add(ProductDetails(it))
        })
    }
    entry<ProductDetails> { key ->
        ProductDetailsScreen(navKey = key, onBack = {
            backstack.removeLastOrNull()
        })
    }
}
