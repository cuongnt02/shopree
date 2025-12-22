package com.ntc.data

import com.ntc.shopree.model.Category
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface CategoryRepository : CrudRepository<Category, UUID> {
}