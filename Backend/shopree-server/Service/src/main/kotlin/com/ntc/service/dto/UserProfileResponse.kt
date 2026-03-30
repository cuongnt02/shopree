package com.ntc.service.dto

data class UserProfileResponse(
    val id: String,
    val name: String,
    val email: String,
    val phone: String,
    val role: String,
    val verified: Boolean,
    val metadata: Map<String, Any>,
    val createdAt: Long
)