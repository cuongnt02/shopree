package com.ntc.shopree.data.remote.service.impl

import com.ntc.shopree.data.remote.mock.products
import com.ntc.shopree.data.remote.service.ProductService
import com.ntc.shopree.domain.models.Product

class ProductServiceImpl : ProductService {
    // TODO: Call API to get products
    override fun getProducts(): List<Product> {
        return products
    }
}