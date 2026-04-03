package com.ntc.shopree.core.network.service.impl

import com.ntc.shopree.core.network.dto.ChangePasswordRequest
import com.ntc.shopree.core.network.dto.UpdateProfileRequest
import com.ntc.shopree.core.network.dto.UserResponse
import com.ntc.shopree.core.network.service.UserService
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject
import javax.inject.Named

class UserServiceImpl @Inject constructor(
    private val client: HttpClient,
    @Named("baseUrl") private val baseUrl: String
) : UserService {

    override suspend fun getProfile(): UserResponse =
        client.get { url("$baseUrl/api/v1/users/me") }.body()

    override suspend fun updateProfile(request: UpdateProfileRequest): UserResponse =
        client.patch {
            url("$baseUrl/api/v1/users/me")
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()

    override suspend fun changePassword(request: ChangePasswordRequest) {
        client.post {
            url("$baseUrl/api/v1/users/me/password")
            contentType(ContentType.Application.Json)
            setBody(request)
        }
    }
}
