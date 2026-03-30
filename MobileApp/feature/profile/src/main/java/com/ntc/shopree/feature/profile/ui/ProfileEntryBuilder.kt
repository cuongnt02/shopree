package com.ntc.shopree.feature.profile.ui

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

fun EntryProviderScope<NavKey>.profileEntryBuilder(backStack: NavBackStack<NavKey>) {
    entry<ProfileScreen> {
        ProfileScreen(onBack = { backStack.removeLastOrNull() })
    }
}
