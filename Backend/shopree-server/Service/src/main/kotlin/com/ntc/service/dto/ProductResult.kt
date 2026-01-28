package com.ntc.service.dto

import com.ntc.domain.model.Product

sealed class ProductResult {
    data class Success(val product: Product): ProductResult()
    data class Error(val message: String): ProductResult()
}