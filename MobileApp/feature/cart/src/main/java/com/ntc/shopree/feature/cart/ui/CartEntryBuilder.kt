package com.ntc.shopree.feature.cart.ui

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

fun EntryProviderScope<NavKey>.cartEntryBuilder(backStack: NavBackStack<NavKey>) {
    entry<CartScreen> {
        CartScreen(onBack = {
            backStack.removeLastOrNull()
        }, onCheckout = {})
    }
}