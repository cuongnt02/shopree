package com.ntc.shopree.core.network.service.impl

import com.ntc.shopree.core.model.Product
import com.ntc.shopree.core.network.mock.products
import com.ntc.shopree.core.network.service.ProductService
import javax.inject.Inject

class ProductServiceImpl @Inject constructor() : ProductService {
    override suspend fun getProducts(): List<Product> {
        return products
    }
}


