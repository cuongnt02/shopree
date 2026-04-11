package com.ntc.api.payload.request;

import jakarta.validation.constraints.NotBlank

data class UpdateOrderStatusRequest(
    @field:NotBlank val status: String
)
