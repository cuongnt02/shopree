package com.ntc.shopree.core.network.di

import android.util.Log
import com.ntc.shopree.core.datastore.SessionTokenProvider
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
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Named
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
    fun provideHttpClient(tokenProvider: SessionTokenProvider, @Named("apiHost") apiHost: String): HttpClient = HttpClient(Android) {
        install(Logging) {
            logger = Logger.ANDROID
            level = LogLevel.HEADERS
            filter { request ->
                request.url.host.contains("192.168.1.2")
            }
            sanitizeHeader { header -> header == HttpHeaders.Authorization }
        }
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                ignoreUnknownKeys = true
            })
        }
        install(Auth) {
            bearer {
                loadTokens {
                    val access = tokenProvider.getAccessToken() ?: return@loadTokens null

                    val masked = access.take(6) + "..." + access.takeLast(4)
                    Log.d("AuthToken", "Using access token: $masked")
                    val refresh = tokenProvider.getRefreshToken() ?: return@loadTokens null
                    BearerTokens(access, refresh)
                }

                sendWithoutRequest { request ->
                    request.url.host == apiHost
                }
            }
        }
    }
}
