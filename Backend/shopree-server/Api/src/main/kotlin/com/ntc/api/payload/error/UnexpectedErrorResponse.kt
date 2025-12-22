package com.ntc.api.payload.error

import com.ntc.api.payload.ResponseBase

data class UnexpectedErrorResponse(
    val message: String
): ResponseBase