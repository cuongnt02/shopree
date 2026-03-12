package com.ntc.shopree.core.model.dto

import kotlinx.serialization.Serializable

@Serializable
data class PlaceOrderRequest(
    val items: List<OrderItemRequest>,
    val paymentMethod: String = "cash"
)

@Serializable
data class OrderItemRequest(
    val productSlug: String,
    val quantity: Int
)


