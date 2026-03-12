package com.ntc.shopree.feature.checkout.ui

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

fun EntryProviderScope<NavKey>.checkoutEntryBuilder(
    backStack: NavBackStack<NavKey>,
    onBackHome: () -> Unit
) {
    entry<CheckoutScreen> {
        CheckoutScreen(onBack = {
            backStack.removeLastOrNull()
        }, onOrderPlaced = { orderNumber, totalCents ->
            backStack.add(OrderConfirmationScreen(orderNumber, totalCents))
        })
    }
    entry<OrderConfirmationScreen> { key ->
        OrderConfirmationScreen(
            orderNumber = key.orderNumber, totalCents = key.totalCents, onBackHome = {
                onBackHome()
            })
    }
}