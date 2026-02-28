package com.ntc.service.dto

import com.ntc.domain.model.Payment
import java.util.UUID

data class PaymentResponse(
    val id: UUID?,
    val paymentMethod: String,
    val status: String,
    val amountCents: Long,
    val currency: String
)

fun Payment.toPaymentResponse() = PaymentResponse(
    id = id,
    paymentMethod = paymentMethod.name,
    status = status.name,
    amountCents = amountCents,
    currency = currency
)
