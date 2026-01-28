package com.ntc.api

import com.ntc.domain.model.Product
import com.ntc.service.ProductService
import com.ntc.service.impl.ProductServiceImpl
import org.springframework.http.MediaType
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping

@RestController
@RequestMapping("/api/v1", produces = [MediaType.APPLICATION_JSON_VALUE])
class ProductController(private val productService: ProductService) {
    @GetMapping("/products")
    fun getProducts(authentication: Authentication): List<Product> {
        println("Authorities: ${authentication.authorities}")
        return productService.getProducts()
    }
}
