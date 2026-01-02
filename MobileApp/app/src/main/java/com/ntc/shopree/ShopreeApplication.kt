package com.ntc.shopree

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ShopreeApplication : Application() {
    companion object {
        const val SHOPREE_BASE_URL = "http://192.168.1.7:8080"
    }
}
