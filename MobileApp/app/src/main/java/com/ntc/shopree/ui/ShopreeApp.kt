package com.ntc.shopree.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ntc.shopree.data.app.AppContainer
import com.ntc.shopree.ui.screen.auth.LoginScreen
import com.ntc.shopree.ui.screen.products.ProductsScreen

@Composable
fun ShopreeApp(appContainer: AppContainer) {
    Scaffold { innerPadding ->
        Surface(modifier = Modifier.padding(innerPadding)) {
            //ProductsScreen(appContainer)
            LoginScreen(appContainer)
        }

    }
}