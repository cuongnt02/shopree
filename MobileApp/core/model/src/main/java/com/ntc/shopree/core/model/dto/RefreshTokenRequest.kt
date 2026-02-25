package com.ntc.shopree.core.model.dto

import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenRequest(
    val refreshToken: String
)