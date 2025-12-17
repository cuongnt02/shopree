package com.ntc.shopree.data.remote.service
import com.ntc.shopree.domain.models.Product


interface ProductService {
    fun getProducts(): List<Product>
}

