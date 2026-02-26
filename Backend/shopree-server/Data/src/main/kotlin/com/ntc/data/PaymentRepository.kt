package com.ntc.data

import com.ntc.domain.model.Payment
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID


@Repository
interface PaymentRepository: CrudRepository<Payment, UUID> {
    fun findByOrderId(orderId: UUID): Payment?
}