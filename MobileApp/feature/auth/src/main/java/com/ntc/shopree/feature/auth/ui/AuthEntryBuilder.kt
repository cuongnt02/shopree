package com.ntc.shopree.feature.auth.ui

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

fun EntryProviderScope<NavKey>.authEntryBuilder(backStack: NavBackStack<NavKey>) {
    entry<LoginScreen> {
        LoginScreen(onLoggedIn = {
            backStack.clear()
            backStack.add(PostLogin)
        })
    }
    entry<RegisterScreen> { RegisterScreen() }
}


