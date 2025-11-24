package com.ntc.shopree

import android.app.Application
import com.ntc.shopree.data.app.AppContainer
import com.ntc.shopree.data.app.AppContainerImpl

class ShopreeApplication : Application() {
    companion object {
        const val SHOPREE_BASE_URL = "http://192.168.1.7:8080"
    }

    lateinit var appContainer: AppContainer

    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainerImpl(this)
    }
}