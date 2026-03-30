package com.ntc.shopree.core.network.service.impl

import com.ntc.shopree.core.network.dto.UserResponse
import com.ntc.shopree.core.network.service.UserService
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.url
import javax.inject.Inject
import javax.inject.Named

class UserServiceImpl @Inject constructor(
    private val client: HttpClient,
    @Named("baseUrl") private val baseUrl: String
) : UserService {

    override suspend fun getProfile(): UserResponse =
        client.get { url("$baseUrl/api/v1/users/me") }.body()
}
