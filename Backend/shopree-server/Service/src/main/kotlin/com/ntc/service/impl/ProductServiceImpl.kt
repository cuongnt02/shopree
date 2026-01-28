package com.ntc.service.impl

import com.ntc.data.ProductRepository
import com.ntc.domain.model.Product
import com.ntc.service.ProductService
import org.springframework.stereotype.Service

@Service
class ProductServiceImpl(private val productRepository: ProductRepository): ProductService {
    override fun getProducts(): List<Product> = productRepository.findAll().toList()
    override fun getProductBySlug(slug: String): Product = productRepository.findBySlug(slug)
}