package com.ntc.api

import com.ntc.domain.model.User
import com.ntc.service.ProductService
import com.ntc.service.dto.CreateProductRequest
import com.ntc.service.dto.ProductImageResponse
import com.ntc.service.dto.ProductVariantResponse
import com.ntc.service.dto.UpdateProductRequest
import com.ntc.service.dto.VariantRequest
import com.ntc.service.dto.VendorProductResponse
import jakarta.validation.Valid
import org.springframework.data.repository.query.Param
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.util.UUID
import javax.print.attribute.standard.Media

@RestController
@RequestMapping("/api/v1/vendor", produces = [MediaType.APPLICATION_JSON_VALUE])
class VendorProductController(private val productService: ProductService) {
    @GetMapping("/products")
    fun getProducts(
        @RequestParam(required = false) status: String?,
        authentication: Authentication
    ): ResponseEntity<List<VendorProductResponse>> {
        val user = authentication.principal as User
        return try {
            val products = productService.getVendorProducts(user.id!!, status)
            ResponseEntity.ok(products)
        } catch (_: IllegalArgumentException) {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping("/products")
    fun createProduct(
        authentication: Authentication,
        @Valid @RequestBody request: CreateProductRequest
    ): ResponseEntity<VendorProductResponse> {
        val user = authentication.principal as User
        return try {
            val response = productService.createProduct(user.id!!, request)
            ResponseEntity.status(HttpStatus.CREATED).body(response)
        } catch (_: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        }
    }

    @PutMapping("/products/{id}")
    fun updateProduct(
        authentication: Authentication,
        @PathVariable id: UUID,
        @Valid @RequestBody request: UpdateProductRequest
    ): ResponseEntity<VendorProductResponse> {
        val user = authentication.principal as User
        return try {
            val response = productService.updateProduct(user.id!!, id, request)
            ResponseEntity.ok(response)
        } catch (_: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        }
    }

    @GetMapping("/products/{productId}/variants")
    fun getVariants(
        authentication: Authentication,
        @PathVariable productId: UUID
    ): ResponseEntity<List<ProductVariantResponse>> {
        val user = authentication.principal as User
        return try {
            ResponseEntity.ok(productService.getProductVariants(user.id!!, productId))
        } catch (_: IllegalArgumentException) {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping("/products/{productId}/variants")
    fun addVariant(
        authentication: Authentication,
        @PathVariable productId: UUID,
        @Valid @RequestBody request: VariantRequest
    ): ResponseEntity<ProductVariantResponse> {
        val user = authentication.principal as User
        return try {
            ResponseEntity.ok(productService.addVariant(user.id!!, productId, request))
        } catch (_: IllegalArgumentException) {
            ResponseEntity.notFound().build()
        }
    }

    @PutMapping("/products/{productId}/variants/{variantId}")
    fun updateVariant(
        authentication: Authentication,
        @PathVariable productId: UUID,
        @PathVariable variantId: UUID,
        @Valid @RequestBody request: VariantRequest
    ): ResponseEntity<ProductVariantResponse> {
        val user = authentication.principal as User
        return try {
            ResponseEntity.ok(productService.updateVariant(user.id!!, productId, variantId, request))
        } catch (_: IllegalArgumentException) {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/products/{productId}/variants/{variantId}")
    fun deleteVariant(
        authentication: Authentication,
        @PathVariable productId: UUID,
        @PathVariable variantId: UUID
    ): ResponseEntity<Void> {
        val user = authentication.principal as User
        return try {
            productService.deleteVariant(user.id!!, productId, variantId)
            ResponseEntity.noContent().build()
        } catch (_: IllegalArgumentException) {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping("/products/{productId}/images", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun uploadImage(
        authentication: Authentication,
        @PathVariable productId: UUID,
        @RequestParam("file") file: MultipartFile,
        @RequestParam("altText", required = false) altText: String?
    ): ResponseEntity<ProductImageResponse> {
        val user = authentication.principal as User
        return try {
            val response = productService.uploadProductImage(
                user.id!!, productId,
                file.inputStream, file.contentType ?: "image/jpeg", file.size, altText
            )
            ResponseEntity.status(HttpStatus.CREATED).body(response)
        } catch (_: IllegalArgumentException) {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/products/{productId}/images/{imageId}")
    fun deleteImage(
        authentication: Authentication,
        @PathVariable productId: UUID,
        @PathVariable imageId: UUID
    ): ResponseEntity<Void> {
        val user = authentication.principal as User
        return try {
            productService.deleteProductImage(user.id!!, productId, imageId)
            ResponseEntity.noContent().build()
        } catch (_: IllegalArgumentException) {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/products/{productId}/images")
    fun getImages(
        authentication: Authentication,
        @PathVariable productId: UUID
    ): ResponseEntity<List<ProductImageResponse>> {
        val user = authentication.principal as User
        return try {
            val images = productService.getProductImages(user.id!!, productId)
            ResponseEntity.ok(images)
        } catch (_: IllegalArgumentException) {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/products/{productId}")
    fun getProduct(
        authentication: Authentication,
        @PathVariable productId: UUID
    ): ResponseEntity<VendorProductResponse> {
        val user = authentication.principal as User
        return try {
            ResponseEntity.ok(productService.getVendorProduct(user.id!!, productId))
        } catch (_: IllegalArgumentException) {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping("/products/{productId}/variants/{variantId}/image", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun uploadVariantImage(
        authentication: Authentication,
        @PathVariable productId: UUID,
        @PathVariable variantId: UUID,
        @RequestPart("file") file: MultipartFile,
        @RequestParam("altText", required = false) altText: String?
    ): ResponseEntity<ProductVariantResponse> {
        val user = authentication.principal as User
        return try {
            val response = productService.uploadVariantImage(
                user.id!!, productId, variantId,
                file.inputStream, file.contentType ?: "image/jpeg", file.size, altText
            )
            ResponseEntity.status(HttpStatus.CREATED).body(response)
        } catch (_: IllegalArgumentException) {
            ResponseEntity.notFound().build()
        }
    }
}