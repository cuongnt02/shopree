package com.ntc.shopree.repository

import com.ntc.shopree.model.Category
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface CategoryRepository : CrudRepository<Category, UUID> {
}