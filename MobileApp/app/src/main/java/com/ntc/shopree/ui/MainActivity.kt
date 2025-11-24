package com.ntc.shopree.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.ntc.shopree.ShopreeApplication
import com.ntc.shopree.ui.theme.MobileAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val appContainer = (application as ShopreeApplication).appContainer
        setContent {
            MobileAppTheme {
                ShopreeApp(appContainer = appContainer)
            }
        }
    }
}