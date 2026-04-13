package com.ntc.service.impl

import com.ntc.data.CategoryRepository
import com.ntc.data.ProductRepository
import com.ntc.data.ProductVariantRepository
import com.ntc.data.VendorRepository
import com.ntc.domain.model.Product
import com.ntc.domain.model.ProductVariant
import com.ntc.service.ProductService
import com.ntc.service.dto.CreateProductRequest
import com.ntc.service.dto.ProductResponse
import com.ntc.service.dto.ProductVariantResponse
import com.ntc.service.dto.UpdateProductRequest
import com.ntc.service.dto.VariantRequest
import com.ntc.service.dto.VendorProductResponse
import com.ntc.service.dto.toProductResponse
import com.ntc.service.dto.toProductVariantResponse
import com.ntc.service.dto.toVendorProductResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional(readOnly = true)
class ProductServiceImpl(
    private val productRepository: ProductRepository,
    private val vendorRepository: VendorRepository,
    private val categoryRepository: CategoryRepository,
    private val productVariantRepository: ProductVariantRepository
) : ProductService {
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

    override fun getProductsByCategory(categorySlug: String): List<ProductResponse> {
        return productRepository.findByCategorySlug(categorySlug).map { it.toProductResponse() }
    }

    override fun getVendorProducts(userId: UUID): List<VendorProductResponse> {
        val vendor = vendorRepository.findByOwnerUserId(userId)
            ?: throw IllegalArgumentException("Vendor not found from user")
        val products = productRepository.findByVendorId(vendor.id!!)
        return products.map { product -> product.toVendorProductResponse() }
    }

    @Transactional
    override fun createProduct(
        userId: UUID,
        request: CreateProductRequest
    ): VendorProductResponse {
        val vendor = vendorRepository.findByOwnerUserId(userId)
            ?: throw IllegalArgumentException("User not found for vendor")
        val category = request.categorySlug?.let {
            categoryRepository.findCategoryBySlug(it)
        }
        val product = Product(
            vendor = vendor,
            title = request.title,
            slug = request.slug,
            description = request.description,
            category = category,
            mainImage = request.mainImage,
            status = Product.Status.valueOf(request.status),
            pickupAvailable = request.pickupAvailable
        )
        productRepository.save(product)
        val productVariant = ProductVariant(product = product, priceCents = request.priceCents)
        productVariantRepository.save(productVariant)
        return product.copy(variants = mutableListOf(productVariant)).toVendorProductResponse()
    }

    @Transactional
    override fun updateProduct(
        userId: UUID,
        productId: UUID,
        request: UpdateProductRequest
    ): VendorProductResponse {
        val vendor = vendorRepository.findByOwnerUserId(userId)
            ?: throw IllegalArgumentException("User not found for vendor")
        val product = productRepository.findByIdAndVendorId(productId, vendor.id!!)
            ?: throw IllegalArgumentException("Unable to find product respective to vendor")
        val category = request.categorySlug?.let { categoryRepository.findCategoryBySlug(it) }
        val updatedProduct = product.copy(
            vendor = vendor,
            title = request.title,
            slug = request.slug,
            description = request.description,
            mainImage = request.mainImage ?: product.mainImage,
            status = Product.Status.valueOf(request.status),
            pickupAvailable = request.pickupAvailable
        )
        if (category != null) updatedProduct.category = category
        productRepository.save(updatedProduct)
        val variant = productVariantRepository.findFirstByProductId(productId)
        if (variant != null) {
            variant.priceCents = request.priceCents
            productVariantRepository.save(variant)
        }
        return updatedProduct.toVendorProductResponse()
    }

    override fun getProductVariants(
        userId: UUID,
        productId: UUID
    ): List<ProductVariantResponse> {
        val vendor = vendorRepository.findByOwnerUserId(userId)
            ?: throw IllegalArgumentException("Vendor not found for user")
        productRepository.findByIdAndVendorId(productId, vendor.id!!)
            ?: throw IllegalArgumentException("Product not found for vendor")
        return productVariantRepository.findAllByProductId(productId)
            .map { it.toProductVariantResponse() }
    }

    @Transactional
    override fun addVariant(
        userId: UUID,
        productId: UUID,
        request: VariantRequest
    ): ProductVariantResponse {
        val vendor = vendorRepository.findByOwnerUserId(userId)
            ?: throw IllegalArgumentException("Vendor not found for user")
        val product = productRepository.findByIdAndVendorId(productId, vendor.id!!)
            ?: throw IllegalArgumentException("Product not found for vendor")
        val variant = ProductVariant(
            product = product,
            title = request.title,
            sku = request.sku,
            priceCents = request.priceCents,
            compareAtCents = request.compareAtCents,
            inventoryCount = request.inventoryCount,
        )
        productVariantRepository.save(variant)
        return variant.toProductVariantResponse()
    }

    @Transactional
    override fun updateVariant(
        userId: UUID,
        productId: UUID,
        variantId: UUID,
        request: VariantRequest
    ): ProductVariantResponse {
        val vendor = vendorRepository.findByOwnerUserId(userId)
            ?: throw IllegalArgumentException("Vendor not found for user")
        productRepository.findByIdAndVendorId(productId, vendor.id!!)
            ?: throw IllegalArgumentException("Product not found for vendor")
        val variant = productVariantRepository.findByIdAndProductId(variantId, productId)
            ?: throw IllegalArgumentException("Variant not found for product")
        val updatedVariant = variant.copy(
            title = request.title,
            sku = request.sku,
            priceCents = request.priceCents,
            compareAtCents = request.compareAtCents,
            inventoryCount = request.inventoryCount,
        )
        productVariantRepository.save(updatedVariant)
        return updatedVariant.toProductVariantResponse()
    }

    @Transactional
    override fun deleteVariant(userId: UUID, productId: UUID, variantId: UUID) {
        val vendor = vendorRepository.findByOwnerUserId(userId)
            ?: throw IllegalArgumentException("Vendor not found for user")
        productRepository.findByIdAndVendorId(productId, vendor.id!!)
            ?: throw IllegalArgumentException("Product not found for vendor")
        productVariantRepository.findByIdAndProductId(variantId, productId)
            ?: throw IllegalArgumentException("Variant not found for product")
        productVariantRepository.deleteById(variantId)
    }
}