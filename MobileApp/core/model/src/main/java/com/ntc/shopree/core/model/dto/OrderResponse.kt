package com.ntc.shopree.core.model.dto

import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class OrderResponse(
    val id: String,
    val orderNumber: String,
    val status: String,
    val totalCents: Long,
    val currency: String,
    val placedAt: String,
    val items: List<OrderItemResponse>,
    val payment: PaymentResponse?
)

@Serializable
data class OrderItemResponse(
    val id: String,
    val productSlug: String,
    val productTitle: String,
    val quantity: Int,
    val unitPriceCents: Long,
    val totalPriceCents: Long
)

@Serializable
data class PaymentResponse(
    val id: String,
    val paymentMethod: String,
    val status: String,
    val amountCents: Long,
    val currency: String
)

@Serializable
data class OrderSummaryResponse(
    val id: String,
    val orderNumber: String,
    val status: String,
    val totalCents: Long,
    val currency: String,
    val placedAt: String,
    val itemCount: Int
)
