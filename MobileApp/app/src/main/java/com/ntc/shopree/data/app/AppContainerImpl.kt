package com.ntc.shopree.data.app

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.ntc.shopree.data.remote.service.CategoryService
import com.ntc.shopree.data.remote.service.ProductService
import com.ntc.shopree.data.remote.service.impl.CategoryServiceImpl
import com.ntc.shopree.data.remote.service.impl.ProductServiceImpl
import com.ntc.shopree.domain.repository.CategoryRepository
import com.ntc.shopree.domain.repository.ProductRepository
import com.ntc.shopree.domain.repository.impl.CategoryRepositoryImpl
import com.ntc.shopree.domain.repository.impl.ProductRepositoryImpl
import com.ntc.shopree.domain.usecase.GetCategoriesUseCase
import com.ntc.shopree.domain.usecase.GetProductsUseCase
import com.ntc.shopree.ui.viewmodels.ProductsViewModelFactory
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json

class AppContainerImpl(private val applicationContext: Context) : AppContainer {
    private val httpClient = HttpClient(Android) {
        install(Logging) {
            logger = Logger.ANDROID
            level = LogLevel.HEADERS
            filter { request ->
                request.url.host.contains("localhost")
            }
            sanitizeHeader { header -> header == HttpHeaders.Authorization }


        }
        install(ContentNegotiation) {
            json()
        }
    }

    private val categoryService: CategoryService by lazy {
        CategoryServiceImpl(httpClient)
    }

    private val productService: ProductService by lazy {
        ProductServiceImpl()
    }

    override val categoryRepository: CategoryRepository by lazy {
        CategoryRepositoryImpl(categoryService)
    }
    override val productRepository: ProductRepository by lazy {
        ProductRepositoryImpl(productService)
    }

    override val productsViewModelFactory: ViewModelProvider.Factory by lazy {
        val getCategoriesUseCase = GetCategoriesUseCase(categoryRepository)
        val getProductsUseCase = GetProductsUseCase(productRepository)
        ProductsViewModelFactory(getCategoriesUseCase, getProductsUseCase)
    }
}