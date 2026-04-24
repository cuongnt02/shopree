package com.ntc.api.payload.request

import jakarta.validation.constraints.NotBlank

data class LoginRequest(@field:NotBlank val identifier: String, @field:NotBlank val password: String)