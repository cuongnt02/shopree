package com.ntc.shopree.feature.catalog.data

import com.ntc.shopree.core.model.Product
import com.ntc.shopree.core.network.service.ProductService
import com.ntc.shopree.feature.catalog.domain.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productService: ProductService
) : ProductRepository {
    override suspend fun getProducts(): List<Product> {
        return productService.getProducts()
    }

    override suspend fun findProductsByName(name: String): List<Product> {
        return productService.getProductsByName(name)
    }

    override suspend fun getProduct(slug: String): Product {
        return productService.getProduct(slug)
    }


}



