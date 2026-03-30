package com.ntc.service.dto

data class UpdateProfileRequest(
    val name: String?,
    val phone: String?,
    val metadata: Map<String, Any>?
)