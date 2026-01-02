package com.ntc.shopree.core.network.di

import com.ntc.shopree.core.network.service.AuthService
import com.ntc.shopree.core.network.service.CategoryService
import com.ntc.shopree.core.network.service.ProductService
import com.ntc.shopree.core.network.service.impl.AuthServiceImpl
import com.ntc.shopree.core.network.service.impl.CategoryServiceImpl
import com.ntc.shopree.core.network.service.impl.ProductServiceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {

    @Binds
    @Singleton
    abstract fun bindAuthService(impl: AuthServiceImpl): AuthService

    @Binds
    @Singleton
    abstract fun bindCategoryService(impl: CategoryServiceImpl): CategoryService

    @Binds
    @Singleton
    abstract fun bindProductService(impl: ProductServiceImpl): ProductService
}

@Module
@InstallIn(SingletonComponent::class)
object NetworkClientModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient = HttpClient(Android) {
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
        install(Auth) {
            bearer {

            }
        }
    }
}
