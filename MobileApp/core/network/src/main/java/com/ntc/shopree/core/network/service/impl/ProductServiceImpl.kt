package com.ntc.shopree.core.network.service.impl

import com.ntc.shopree.core.model.Product
import com.ntc.shopree.core.network.mock.products
import com.ntc.shopree.core.network.service.ProductService
import javax.inject.Inject

class ProductServiceImpl @Inject constructor() : ProductService {
    override suspend fun getProducts(): List<Product> {
        return products
    }

    override suspend fun getProductsByName(name: String): List<Product> {
        return products.filter { it.name.contains(name, ignoreCase = true) }
    }

    override fun getProduct(slug: String): Product {
        // TODO: will be replaced by slug
        return products.first { it.name == slug }
    }
}


