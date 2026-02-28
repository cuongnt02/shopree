package com.ntc.service.dto

import com.ntc.domain.model.Order
import com.ntc.domain.model.Payment
import java.time.Instant
import java.util.*

data class OrderResponse(
    val id: UUID?,
    val orderNumber: String,
    val status: String,
    val totalCents: Long,
    val currency: String,
    val placedAt: Instant,
    val items: List<OrderItemResponse>,
    val payment: PaymentResponse?
)

fun Order.toOrderResponse(payment: Payment?) = OrderResponse(
    id = id,
    orderNumber = orderNumber,
    status = status.name,
    totalCents = totalCents,
    currency = currency,
    placedAt = placedAt,
    items = items.map { it.toOrderItemResponse() },
    payment = payment?.toPaymentResponse()
)


