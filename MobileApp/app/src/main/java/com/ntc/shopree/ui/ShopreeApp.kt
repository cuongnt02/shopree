package com.ntc.shopree.ui

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.ntc.shopree.core.ui.utils.ObserveAsEvents
import com.ntc.shopree.feature.auth.ui.LoginScreen
import com.ntc.shopree.feature.auth.ui.authEntryBuilder
import com.ntc.shopree.feature.catalog.ui.productsEntryBuilder
import com.ntc.shopree.core.ui.utils.SnackbarController
import com.ntc.shopree.feature.auth.ui.PostLogin
import com.ntc.shopree.feature.cart.ui.cartEntryBuilder
import com.ntc.shopree.feature.catalog.ui.ProductsScreen

@Composable
fun ShopreeApp(
    viewModel: AppViewModel = hiltViewModel()
) {
    val startupState by viewModel.state.collectAsState()

    val initScreen = when (startupState) {
        AppState.Loading -> null
        AppState.Authenticated -> ProductsScreen
        AppState.Unauthenticated -> LoginScreen
    }

    // TODO: Store the back stack in a viewmodel
    val backStack = initScreen?.let {  rememberNavBackStack(it) } ?: return

    if (startupState == AppState.Loading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }
    val snackbarHostState = remember { SnackbarHostState() }
    ObserveAsEvents(flow = SnackbarController.events, snackbarHostState) { event ->
        snackbarHostState.currentSnackbarData?.dismiss()

        val result = snackbarHostState.showSnackbar(
            message = event.message,
            actionLabel = event.action?.name,
            duration = SnackbarDuration.Short
        )
        if (result == SnackbarResult.ActionPerformed) {
            event.action?.action?.invoke()
        }

    }
    Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { innerPadding ->
        Surface(modifier = Modifier.padding(innerPadding)) {
            NavDisplay(
                entryDecorators = listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator()
            ),
                transitionSpec = {
                    slideInHorizontally(
                        initialOffsetX = { it }, animationSpec = tween(1000)
                    ) togetherWith slideOutHorizontally(targetOffsetX = { -it })
                },
                popTransitionSpec = {
                    slideInHorizontally(
                        initialOffsetX = { -it }, animationSpec = tween(1000)
                    ) togetherWith slideOutHorizontally(targetOffsetX = { it })
                },
                backStack = backStack,
                onBack = { backStack.removeLastOrNull() },
                entryProvider = entryProvider {
                    // TODO: Migrate to DI
                    productsEntryBuilder(backStack)
                    authEntryBuilder(backStack)
                    cartEntryBuilder(backStack)

                    entry<PostLogin> {
                        backStack.clear()
                        backStack.add(ProductsScreen)
                    }
                })
        }

    }
}
