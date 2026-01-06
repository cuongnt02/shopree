package com.ntc.shopree.feature.catalog.ui

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
fun EntryProviderScope<NavKey>.productsEntryBuilder() {
    entry<ProductsScreen> { ProductsScreen() }
}
