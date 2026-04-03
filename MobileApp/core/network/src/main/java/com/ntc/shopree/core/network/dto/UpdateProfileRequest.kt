package com.ntc.shopree.core.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class UpdateProfileRequest(
    val name: String?,
    val phone: String?,
)
