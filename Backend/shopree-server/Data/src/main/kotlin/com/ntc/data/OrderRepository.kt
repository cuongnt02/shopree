package com.ntc.data

import com.ntc.domain.model.Order
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface OrderRepository: CrudRepository<Order, UUID> {
    fun findAllByUserId(userId: UUID): List<Order>
    fun findByIdAndUserId(id: UUID, userId: UUID): Order?
    fun findByOrderNumber(orderNumber: String): Order?
    fun existsByOrderNumber(orderNumber: String): Boolean
}