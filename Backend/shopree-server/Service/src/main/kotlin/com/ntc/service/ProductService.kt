package com.ntc.service

import com.ntc.service.dto.CreateProductRequest
import com.ntc.service.dto.ProductResponse
import com.ntc.service.dto.UpdateProductRequest
import com.ntc.service.dto.VendorProductResponse
import java.util.UUID

interface ProductService {
    fun getProductBySlug(slug: String): ProductResponse?
    fun getProducts(): List<ProductResponse>
    fun getProductsByName(name: String): List<ProductResponse>
    fun getProductsByCategory(categorySlug: String): List<ProductResponse>
    fun getVendorProducts(userId: UUID): List<VendorProductResponse>
    fun createProduct(userId: UUID, request: CreateProductRequest): VendorProductResponse
    fun updateProduct(userId: UUID, productId: UUID, request: UpdateProductRequest): VendorProductResponse
}