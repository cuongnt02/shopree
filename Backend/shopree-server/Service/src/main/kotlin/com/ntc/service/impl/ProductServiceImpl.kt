package com.ntc.service.impl

import com.ntc.data.ProductRepository
import com.ntc.service.ProductService
import com.ntc.service.dto.ProductResponse
import com.ntc.service.dto.toProductResponse
import org.springframework.stereotype.Service

@Service
class ProductServiceImpl(private val productRepository: ProductRepository): ProductService {
    override fun getProducts(): List<ProductResponse> {
        val products = productRepository.findAll().toList()
        return products.map { it.toProductResponse() }
    }

    override fun getProductsByName(name: String): List<ProductResponse> {
        val products = productRepository.findAllByTitleContainingIgnoreCase(name)
        return products.map { it.toProductResponse() }
    }

    override fun getProductBySlug(slug: String): ProductResponse? {
        val product = productRepository.findBySlug(slug)
        return product?.toProductResponse()
    }
}