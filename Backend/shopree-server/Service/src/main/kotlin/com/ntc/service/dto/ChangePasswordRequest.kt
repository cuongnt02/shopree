package com.ntc.service.dto

data class ChangePasswordRequest(
    val currentPassword: String,
    val newPassword: String
)
