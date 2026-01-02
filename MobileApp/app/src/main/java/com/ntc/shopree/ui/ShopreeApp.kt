package com.ntc.shopree.ui

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.LifecycleStartEffect
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.ntc.shopree.feature.auth.ui.LoginScreen
import com.ntc.shopree.feature.auth.ui.authEntryBuilder
import com.ntc.shopree.feature.catalog.ui.productsEntryBuilder

@Composable
fun ShopreeApp() {
    // TODO: Store the back stack in a viewmodel
    val backStack = rememberNavBackStack(LoginScreen)

    Scaffold { innerPadding ->
        Surface(modifier = Modifier.padding(innerPadding)) {
            NavDisplay(
                entryDecorators = listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator()
            ),
                transitionSpec = {
                    slideInHorizontally(
                        initialOffsetX = { it },
                        animationSpec = tween(1000)
                    ) togetherWith slideOutHorizontally(targetOffsetX = { -it })
                },
                popTransitionSpec = {
                    slideInHorizontally(
                        initialOffsetX = { -it },
                        animationSpec = tween(1000)
                    ) togetherWith slideOutHorizontally(targetOffsetX = { it })
                },
                backStack = backStack,
                onBack = { backStack.removeLastOrNull() },
                entryProvider = entryProvider {
                    // TODO: Migrate to DI
                    productsEntryBuilder()
                    authEntryBuilder(backStack)
                })
        }

    }
}
