package com.ntc.service.dto

import com.ntc.domain.model.Order
import java.time.Instant
import java.util.UUID

data class OrderSummaryResponse(
    val id: UUID?,
    val orderNumber: String,
    val status: String,
    val totalCents: Long,
    val currency: String,
    val placedAt: Instant,
    val itemCount: Int
)

fun Order.toOrderSummaryResponse() = OrderSummaryResponse(
    id = id,
    orderNumber = orderNumber,
    status = status.name,
    totalCents = totalCents,
    currency = currency,
    placedAt = placedAt,
    itemCount = items.size
)
