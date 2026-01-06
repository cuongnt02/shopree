package com.ntc.shopree.feature.auth.ui

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.ntc.shopree.feature.catalog.ui.ProductsScreen

fun EntryProviderScope<NavKey>.authEntryBuilder(backStack: NavBackStack<NavKey>) {
    entry<LoginScreen> {
        LoginScreen(onLoggedIn = {
            backStack.add(ProductsScreen)
        })
    }
    entry<RegisterScreen> { RegisterScreen() }
}


