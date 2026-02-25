package com.ntc.api

import com.ntc.api.payload.error.NotFoundErrorResponse
import com.ntc.service.ProductService
import com.ntc.service.dto.ProductResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@RestController
@RequestMapping("/api/v1", produces = [MediaType.APPLICATION_JSON_VALUE])
class ProductController(private val productService: ProductService) {
    @GetMapping("/products")
    fun getProducts(@RequestParam(required = false) name: String?): ResponseEntity<List<ProductResponse>> {
        return name?.let {
            val products = productService.getProductsByName(it)
            ResponseEntity.ok(products)
        } ?: ResponseEntity.ok(productService.getProducts())
    }

    @GetMapping("/product/{slug}")
    fun getProductDetails(@PathVariable slug: String): ResponseEntity<Any> {
        val product = productService.getProductBySlug(slug) ?: return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(NotFoundErrorResponse(message = "Product not found - Check your product slug if it's exist"))
        return ResponseEntity.ok(product)
    }
}
