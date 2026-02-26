package com.ntc.data

import com.ntc.domain.model.OrderItem
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

// Added for consistency
@Repository
interface OrderItemRepository: CrudRepository<OrderItem, UUID> {
}