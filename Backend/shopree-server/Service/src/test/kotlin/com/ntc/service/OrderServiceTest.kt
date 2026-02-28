package com.ntc.service

import com.ntc.data.OrderRepository
import com.ntc.data.PaymentRepository
import com.ntc.data.ProductRepository
import com.ntc.data.ProductVariantRepository
import com.ntc.data.UserRepository
import com.ntc.domain.model.Order
import com.ntc.domain.model.Payment
import com.ntc.domain.model.Product
import com.ntc.domain.model.ProductVariant
import com.ntc.domain.model.User
import com.ntc.service.dto.OrderItemRequest
import com.ntc.service.dto.PlaceOrderRequest
import com.ntc.service.impl.OrderServiceImpl
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.Optional
import java.util.UUID

class OrderServiceTest {
    private lateinit var userRepository: UserRepository
    private lateinit var productRepository: ProductRepository
    private lateinit var productVariantRepository: ProductVariantRepository
    private lateinit var paymentRepository: PaymentRepository
    private lateinit var orderRepository: OrderRepository
    private lateinit var orderService: OrderServiceImpl

    @BeforeEach
    fun setUp() {
        userRepository = mockk()
        productRepository = mockk()
        productVariantRepository = mockk()
        orderRepository = mockk()
        paymentRepository = mockk()
        orderService = OrderServiceImpl(
            userRepository = userRepository,
            productRepository = productRepository,
            productVariantRepository = productVariantRepository,
            paymentRepository = paymentRepository,
            orderRepository = orderRepository
        )
    }

    // Fixtures
    private fun makeUser(id: UUID = UUID.randomUUID()): User = mockk {
        every { this@mockk.id } returns id
    }

    private fun makeProduct(
        slug: String = "test-product",
        status: Product.Status = Product.Status.PUBLISHED
    ): Product = mockk {
        every { this@mockk.id } returns UUID.randomUUID()
        every { this@mockk.slug } returns slug
        every { this@mockk.title } returns "Test Product"
        every { this@mockk.status } returns status
    }

    private fun makeVariant(
        product: Product,
        priceCents: Long = 30000L
    ): ProductVariant = mockk {
        every { this@mockk.id } returns UUID.randomUUID()
        every { this@mockk.priceCents } returns priceCents
        every { this@mockk.sku } returns "SKU-001"
    }

    private fun makeSavedOrder(totalCents: Long = 0L): Order = mockk {
        every { this@mockk.id } returns UUID.randomUUID()
        every { this@mockk.orderNumber } returns "SH-20260226-ABCDEF"
        every { this@mockk.status } returns Order.Status.PENDING_PAYMENT
        every { this@mockk.totalCents } returns totalCents
        every { this@mockk.currency } returns "VND"
        every { this@mockk.placedAt } returns java.time.Instant.now()
        every { this@mockk.items } returns mutableListOf()
    }

    private fun makeSavedPayment(amountCents: Long = 0L): Payment = mockk {
        every { this@mockk.id } returns UUID.randomUUID()
        every { this@mockk.paymentMethod } returns Payment.Method.CASH
        every { this@mockk.status } returns Payment.Status.PENDING
        every { this@mockk.amountCents } returns amountCents
        every { this@mockk.currency } returns "VND"
    }



// --- placeOrder ---

    @Test
    fun `placeOrder - order total is sum of variant price times quantity`() {
        val userId = UUID.randomUUID()
        val product = makeProduct()
        val variant = makeVariant(product, priceCents = 30000L)

        every { userRepository.findById(userId) } returns Optional.of(makeUser(userId))
        every { productRepository.findBySlug("test-product") } returns product
        every { productVariantRepository.findFirstByProductId(any()) } returns variant

        val orderSlot = slot<Order>()
        every { orderRepository.save(capture(orderSlot)) } returns makeSavedOrder()

        val paymentSlot = slot<Payment>()
        every { paymentRepository.save(capture(paymentSlot)) } returns makeSavedPayment()

        val request = PlaceOrderRequest(
            items = listOf(OrderItemRequest(productSlug = "test-product", quantity = 3)),
            paymentMethod = "cash"
        )
        orderService.placeOrder(userId, request)

        // 3 items × 30,000 = 90,000
        assertEquals(90000L, orderSlot.captured.totalCents)
    }

    @Test
    fun `placeOrder - payment amountCents matches order total`() {
        val userId = UUID.randomUUID()
        val product = makeProduct()
        val variant = makeVariant(product, priceCents = 50000L)

        every { userRepository.findById(userId) } returns Optional.of(makeUser(userId))
        every { productRepository.findBySlug("test-product") } returns product
        every { productVariantRepository.findFirstByProductId(any()) } returns variant

        val orderSlot = slot<Order>()
        every { orderRepository.save(capture(orderSlot)) } answers {
            makeSavedOrder(totalCents = orderSlot.captured.totalCents)
        }

        val paymentSlot = slot<Payment>()
        every { paymentRepository.save(capture(paymentSlot)) } returns makeSavedPayment()

        val request = PlaceOrderRequest(
            items = listOf(OrderItemRequest(productSlug = "test-product", quantity = 2)),
            paymentMethod = "cash"
        )
        orderService.placeOrder(userId, request)

        // Verifies the bug fix — payment must not be 0
        assertEquals(orderSlot.captured.totalCents, paymentSlot.captured.amountCents)
    }

    @Test
    fun `placeOrder - order number is generated with SH prefix`() {
        val userId = UUID.randomUUID()
        val product = makeProduct()
        val variant = makeVariant(product)

        every { userRepository.findById(userId) } returns Optional.of(makeUser(userId))
        every { productRepository.findBySlug("test-product") } returns product
        every { productVariantRepository.findFirstByProductId(any()) } returns variant

        val orderSlot = slot<Order>()
        every { orderRepository.save(capture(orderSlot)) } returns makeSavedOrder()
        every { paymentRepository.save(any()) } returns makeSavedPayment()

        val request = PlaceOrderRequest(
            items = listOf(OrderItemRequest(productSlug = "test-product", quantity = 1)),
            paymentMethod = "cash"
        )
        orderService.placeOrder(userId, request)

        assertTrue(orderSlot.captured.orderNumber.startsWith("SH-"))
    }

    @Test
    fun `placeOrder - throws when product is not found`() {
        val userId = UUID.randomUUID()
        every { userRepository.findById(userId) } returns Optional.of(makeUser(userId))
        every { productRepository.findBySlug("ghost-product") } returns null

        val request = PlaceOrderRequest(
            items = listOf(OrderItemRequest(productSlug = "ghost-product", quantity = 1)),
            paymentMethod = "cash"
        )

        assertThrows<IllegalArgumentException> {
            orderService.placeOrder(userId, request)
        }
    }

    @Test
    fun `placeOrder - throws when product is not published`() {
        val userId = UUID.randomUUID()
        val draftProduct = makeProduct(status = Product.Status.DRAFT)

        every { userRepository.findById(userId) } returns Optional.of(makeUser(userId))
        every { productRepository.findBySlug("test-product") } returns draftProduct

        val request = PlaceOrderRequest(
            items = listOf(OrderItemRequest(productSlug = "test-product", quantity = 1)),
            paymentMethod = "cash"
        )

        assertThrows<IllegalArgumentException> {
            orderService.placeOrder(userId, request)
        }
    }

    @Test
    fun `placeOrder - throws when variant does not belong to product`() {
        val userId = UUID.randomUUID()
        val product = makeProduct()
        val wrongVariantId = UUID.randomUUID()

        every { userRepository.findById(userId) } returns Optional.of(makeUser(userId))
        every { productRepository.findBySlug("test-product") } returns product
        every { productVariantRepository.findByIdAndProductId(wrongVariantId, any()) } returns null

        val request = PlaceOrderRequest(
            items = listOf(OrderItemRequest(productSlug = "test-product", variantId = wrongVariantId, quantity = 1)),
            paymentMethod = "cash"
        )

        assertThrows<IllegalArgumentException> {
            orderService.placeOrder(userId, request)
        }
    }

    @Test
    fun `placeOrder - throws when product has no variants`() {
        val userId = UUID.randomUUID()
        val product = makeProduct()

        every { userRepository.findById(userId) } returns Optional.of(makeUser(userId))
        every { productRepository.findBySlug("test-product") } returns product
        every { productVariantRepository.findFirstByProductId(any()) } returns null

        val request = PlaceOrderRequest(
            items = listOf(OrderItemRequest(productSlug = "test-product", quantity = 1)),
            paymentMethod = "cash"
        )

        assertThrows<IllegalArgumentException> {
            orderService.placeOrder(userId, request)
        }
    }

    // --- getOrder ---

    @Test
    fun `getOrder - throws when order does not belong to user`() {
        val userId = UUID.randomUUID()
        val orderId = UUID.randomUUID()
        every { orderRepository.findByIdAndUserId(orderId, userId) } returns null

        assertThrows<IllegalArgumentException> {
            orderService.getOrder(userId, orderId)
        }
    }

    // --- getOrders ---

    @Test
    fun `getOrders - returns empty list when user has no orders`() {
        val userId = UUID.randomUUID()
        every { orderRepository.findAllByUserId(userId) } returns emptyList()

        val result = orderService.getOrders(userId)

        assertTrue(result.isEmpty())
        verify(exactly = 1) { orderRepository.findAllByUserId(userId) }
    }
}