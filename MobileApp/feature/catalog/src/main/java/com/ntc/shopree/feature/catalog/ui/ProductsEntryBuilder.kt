package com.ntc.shopree.feature.catalog.ui

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.ntc.shopree.feature.cart.ui.CartScreen

fun EntryProviderScope<NavKey>.productsEntryBuilder(backstack: NavBackStack<NavKey>) {
    entry<ProductsScreen> {
        ProductsScreen(onProductClick = {
            backstack.add(ProductDetails(it))
        }, onCart = {
            backstack.add(CartScreen)
        })
    }
    entry<ProductDetails> { key ->
        ProductDetailsScreen(navKey = key, onBack = {
            backstack.removeLastOrNull()
        }, onCheckout = {}, onCart = {
            backstack.add(CartScreen)
        })
    }
}
