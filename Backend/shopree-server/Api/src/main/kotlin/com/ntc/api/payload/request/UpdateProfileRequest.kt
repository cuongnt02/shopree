package com.ntc.api.payload.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class UpdateProfileRequest(
    @field:Size(min = 1, max = 255) val name: String?,
    @field:Size(max = 30) val phone: String?,
    val metadata: Map<String, Any>?
)

data class ChangePasswordRequest(
    @field:NotBlank val currentPassword: String,
    @field:Size(min = 8, max = 255) val newPassword: String
)