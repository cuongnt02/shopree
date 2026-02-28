package com.ntc.service.dto

import java.util.UUID

data class PlaceOrderRequest(
    val items: List<OrderItemRequest>,
    val paymentMethod: String = "cash",
    val metadata: Map<String, Any> = emptyMap()
)

data class OrderItemRequest(
    val productSlug: String,
    val variantId: UUID? = null,
    val quantity: Int
)
