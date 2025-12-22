package com.ntc.api

import com.ntc.data.ProductRepository
import com.ntc.shopree.model.Product
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping

@RestController
@RequestMapping("/api/v1", produces = [MediaType.APPLICATION_JSON_VALUE])
class ProductController(private val productRepository: ProductRepository) {
    @GetMapping("/products")
    fun getProducts(authentication: org.springframework.security.core.Authentication): List<Product> {
        println("Authorities: ${authentication.authorities}")
        return productRepository.findAll().toList()
    }
}
