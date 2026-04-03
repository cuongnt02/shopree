package com.ntc.shopree.feature.profile.ui

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

fun EntryProviderScope<NavKey>.profileEntryBuilder(backStack: NavBackStack<NavKey>) {
    entry<ProfileScreen> {
        ProfileScreen(
            onBack = { backStack.removeLastOrNull() },
            onEditProfile = { user -> backStack.add(EditProfileScreen(user)) },
            onChangePassword = { backStack.add(ChangePasswordScreen) },
            onOrderHistory = { backStack.add(OrderHistoryScreen) }
        )
    }
    entry<EditProfileScreen> { key ->
        EditProfileScreen(
            user = key.user,
            onBack = { backStack.removeLastOrNull() }
        )
    }
    entry<ChangePasswordScreen> {
        ChangePasswordScreen(
            onBack = { backStack.removeLastOrNull() }
        )
    }
    entry<OrderHistoryScreen> {
        OrderHistoryScreen(
            onBack = { backStack.removeLastOrNull() },
            onOrderClick = { orderId -> backStack.add(OrderDetailsScreen(orderId)) }
        )
    }
    entry<OrderDetailsScreen> { key ->
        OrderDetailsScreen(
            orderId = key.orderId,
            onBack = { backStack.removeLastOrNull() }
        )
    }
}
