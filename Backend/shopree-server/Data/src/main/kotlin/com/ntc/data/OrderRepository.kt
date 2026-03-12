package com.ntc.data

import com.ntc.domain.model.Order
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface OrderRepository: CrudRepository<Order, UUID> {
    @Query("SELECT DISTINCT o FROM Order o LEFT JOIN FETCH o.items WHERE o.user.id = :userId")
    fun findAllByUserId(userId: UUID): List<Order>
    fun findByIdAndUserId(id: UUID, userId: UUID): Order?
    fun findByOrderNumber(orderNumber: String): Order?
    fun existsByOrderNumber(orderNumber: String): Boolean
}