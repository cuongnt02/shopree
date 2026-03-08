package com.ntc.shopree.core.network.di

import android.util.Log
import com.ntc.shopree.core.datastore.SessionStore
import com.ntc.shopree.core.datastore.SessionTokenProvider
import com.ntc.shopree.core.model.Session
import com.ntc.shopree.core.model.dto.RefreshTokenRequest
import com.ntc.shopree.core.model.dto.RefreshTokenResponse
import com.ntc.shopree.core.network.service.AuthService
import com.ntc.shopree.core.network.service.CategoryService
import com.ntc.shopree.core.network.service.OrderService
import com.ntc.shopree.core.network.service.ProductService
import com.ntc.shopree.core.network.service.impl.AuthServiceImpl
import com.ntc.shopree.core.network.service.impl.CategoryServiceImpl
import com.ntc.shopree.core.network.service.impl.OrderServiceImpl
import com.ntc.shopree.core.network.service.impl.ProductServiceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
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

    @Binds
    @Singleton
    abstract fun bindOrderService(impl: OrderServiceImpl): OrderService
}

@Module
@InstallIn(SingletonComponent::class)
object NetworkClientModule {

    @Provides
    @Singleton
    fun provideHttpClient(tokenProvider: SessionTokenProvider, @Named("apiHost") apiHost: String, @Named("baseUrl") baseUrl: String, sessionStore: SessionStore): HttpClient = HttpClient(Android) {
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.HEADERS
            filter { request ->
                request.url.host.contains("192.168") ||
                request.url.host.contains("172.16") ||
                request.url.host == "10.0.2.2" ||
                request.url.host == "localhost"
            }
            sanitizeHeader { header -> header == HttpHeaders.Authorization }
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 30_000L
            connectTimeoutMillis = 15_000L
            socketTimeoutMillis = 30_000L
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

                    val refresh = tokenProvider.getRefreshToken() ?: return@loadTokens null
                    BearerTokens(access, refresh)
                }

                refreshTokens {
                    val currentRefreshToken = oldTokens?.refreshToken ?: return@refreshTokens null
                    try {
                        val response = client.post {
                            url("$baseUrl/api/v1/auth/refresh")
                            contentType(ContentType.Application.Json)
                            setBody(RefreshTokenRequest(currentRefreshToken))
                            markAsRefreshTokenRequest()

                        }.body<RefreshTokenResponse>()
                        // TODO: Add userId and role
                        sessionStore.saveSession(
                            accessToken = response.accessToken,
                            refreshToken = response.refreshToken,
                            expiresAt = response.expiresAt,
                        )
                        BearerTokens(response.accessToken, response.refreshToken)
                    } catch (e: Exception) {
                        null
                    }
                }

                sendWithoutRequest { request ->
                    request.url.host == apiHost
                }
            }
        }
    }
}
