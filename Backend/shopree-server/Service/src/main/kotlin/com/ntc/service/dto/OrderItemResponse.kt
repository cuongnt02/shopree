package com.ntc.service.dto

import com.ntc.domain.model.OrderItem
import java.util.UUID

data class OrderItemResponse(
    val id: UUID?,
    val productSlug: String,
    val productTitle: String,
    val variantId: UUID?,
    val sku: String?,
    val quantity: Int,
    val unitPriceCents: Long,
    val totalPriceCents: Long
)

fun OrderItem.toOrderItemResponse() = OrderItemResponse(
    id = id,
    productSlug = productSlug,
    productTitle = productTitle,
    variantId = variant?.id,
    sku = sku,
    quantity = quantity,
    unitPriceCents = unitPriceCents,
    totalPriceCents = totalPriceCents
)