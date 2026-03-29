package com.ntc.shopree.core.network.repository

import com.ntc.shopree.core.model.Product
import com.ntc.shopree.core.model.repository.ProductRepository
import com.ntc.shopree.core.network.dto.toProduct
import com.ntc.shopree.core.network.service.ProductService
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productService: ProductService
) : ProductRepository {
    override suspend fun getProducts(): List<Product> {
        return productService.getProducts().map { it.toProduct() }
    }

    override suspend fun findProductsByName(name: String): List<Product> {
        return productService.getProductsByName(name).map { it.toProduct() }
    }

    override suspend fun getProduct(slug: String): Product {
        return productService.getProduct(slug).toProduct()
    }

    override suspend fun getProductsByCategory(categorySlug: String): List<Product> {
        return productService.getProductsByCategory(categorySlug).map { it.toProduct() }
    }
}
