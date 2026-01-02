package com.ntc.shopree.core.network.service.impl

import com.ntc.shopree.core.network.dto.LoginRequest
import com.ntc.shopree.core.network.dto.LoginResponse
import com.ntc.shopree.core.network.service.AuthService
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject
import javax.inject.Named

class AuthServiceImpl @Inject constructor(
    private val client: HttpClient,
    @Named("baseUrl") private val baseUrl: String
) : AuthService {
    override suspend fun loginWithEmailAndPassword(email: String, password: String): LoginResponse {
        return client.post {
            url("$baseUrl/api/v1/auth/login")
            contentType(ContentType.Application.Json)
            setBody(
                LoginRequest(
                    email = email, password = password
                )
            )
        }.body()
    }
}

