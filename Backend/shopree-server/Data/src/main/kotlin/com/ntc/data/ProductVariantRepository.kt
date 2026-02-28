package com.ntc.data

import com.ntc.domain.model.ProductVariant
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface ProductVariantRepository : CrudRepository<ProductVariant, UUID> {
    fun findAllByProductId(productId: UUID): List<ProductVariant>
    fun findFirstByProductId(productId: UUID): ProductVariant?
    fun findByIdAndProductId(id: UUID, productId: UUID): ProductVariant?
}