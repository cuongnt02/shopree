package com.ntc.data

import com.ntc.domain.model.ProductImage
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface ProductImageRepository: CrudRepository<ProductImage, UUID> {
    fun findByProductIdOrderByPosition(productId: UUID): List<ProductImage>
    fun deleteByProductIdAndId(productId: UUID, id: UUID)
}