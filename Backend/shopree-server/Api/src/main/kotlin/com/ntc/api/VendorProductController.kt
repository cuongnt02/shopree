package com.ntc.api

import com.ntc.domain.model.User
import com.ntc.service.ProductService
import com.ntc.service.dto.CreateProductRequest
import com.ntc.service.dto.UpdateProductRequest
import com.ntc.service.dto.VendorProductResponse
import jakarta.validation.Valid
import org.apache.coyote.Response
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/v1/vendor", produces = [MediaType.APPLICATION_JSON_VALUE])
class VendorProductController(private val productService: ProductService) {
    @GetMapping("/products")
    fun getProducts(authentication: Authentication): ResponseEntity<List<VendorProductResponse>> {
        val user = authentication.principal as User
        return try {
            val products = productService.getVendorProducts(user.id!!)
            ResponseEntity.ok(products)
        } catch (_: IllegalArgumentException) {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping("/products")
    fun createProduct(authentication: Authentication, @Valid @RequestBody request: CreateProductRequest): ResponseEntity<VendorProductResponse> {
        val user = authentication.principal as User
        return try {
            val response = productService.createProduct(user.id!!, request)
            ResponseEntity.status(HttpStatus.CREATED).body(response)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        }
    }

    @PutMapping("/products/{id}")
    fun updateProduct(authentication: Authentication, @PathVariable id: UUID, @Valid @RequestBody request: UpdateProductRequest): ResponseEntity<VendorProductResponse> {
        val user = authentication.principal as User
        return try {
            val response = productService.updateProduct(user.id!!, id, request)
            ResponseEntity.ok(response)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        }
    }
}