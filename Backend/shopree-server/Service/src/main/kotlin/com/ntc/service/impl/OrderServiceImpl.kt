package com.ntc.service.impl

import com.ntc.data.OrderItemRepository
import com.ntc.data.OrderRepository
import com.ntc.data.PaymentRepository
import com.ntc.data.ProductRepository
import com.ntc.data.ProductVariantRepository
import com.ntc.data.UserRepository
import com.ntc.domain.model.Order
import com.ntc.domain.model.OrderItem
import com.ntc.domain.model.Payment
import com.ntc.domain.model.Product
import com.ntc.service.OrderService
import com.ntc.service.dto.OrderItemRequest
import com.ntc.service.dto.OrderResponse
import com.ntc.service.dto.OrderSummaryResponse
import com.ntc.service.dto.PlaceOrderRequest
import com.ntc.service.dto.toOrderResponse
import com.ntc.service.dto.toOrderSummaryResponse
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.UUID

@Service
class OrderServiceImpl(
    private val userRepository: UserRepository,
    private val productRepository: ProductRepository,
    private val productVariantRepository: ProductVariantRepository,
    private val paymentRepository: PaymentRepository,
    private val orderRepository: OrderRepository
    ) : OrderService {
    @Transactional
    override fun placeOrder(
        userId: UUID,
        request: PlaceOrderRequest
    ): OrderResponse {
        val user = userRepository.findById(userId).orElseThrow {
            IllegalArgumentException("User is not found in the database, check if it is missing or deleted from the server")
        }

        val order = Order(
            user = user,
            orderNumber = generateOrderNumber(),
            totalCents = 0L
        )

        request.items.forEach { itemRequest ->
            processOrderItemRequest(order, itemRequest)
        }

        val savedOrder = orderRepository.save(order)
        val payment = paymentRepository.save(Payment(
            order = savedOrder,
            paymentMethod = Payment.Method.valueOf(request.paymentMethod.uppercase()),
            amountCents = savedOrder.totalCents
        ))
        return savedOrder.toOrderResponse(payment)
    }

    private fun processOrderItemRequest(
        order: Order,
        itemRequest: OrderItemRequest
    ) {
        val product = productRepository.findBySlug(itemRequest.productSlug) ?: throw IllegalArgumentException("Product not found: ${itemRequest.productSlug}")
        if (product.status != Product.Status.PUBLISHED) throw IllegalArgumentException("Product not available: ${itemRequest.productSlug}")

        val variant = if (itemRequest.variantId != null) productVariantRepository.findByIdAndProductId(itemRequest.variantId, product.id!!)
            ?: throw IllegalArgumentException("Variant does not belong to product: ${itemRequest.productSlug}")
        else productVariantRepository.findFirstByProductId(product.id!!)
            ?: throw IllegalArgumentException("Product has no variants: ${itemRequest.productSlug}")

        val totalPrice = variant.priceCents * itemRequest.quantity
        order.items.add(
            OrderItem(
                order = order,
                variant = variant,
                productSlug = product.slug,
                productTitle = product.title,
                sku = variant.sku,
                quantity = itemRequest.quantity,
                unitPriceCents = variant.priceCents,
                totalPriceCents = totalPrice
            )
        )
        order.totalCents += totalPrice
    }


    override fun getOrders(userId: UUID): List<OrderSummaryResponse> = orderRepository.findAllByUserId(userId).map { it.toOrderSummaryResponse() }

    override fun getOrder(userId: UUID, orderId: UUID): OrderResponse {
        val order = orderRepository.findByIdAndUserId(orderId, userId) ?: throw IllegalArgumentException("Order not found.")
        val payment = paymentRepository.findByOrderId(orderId)
        return order.toOrderResponse(payment)
    }

    private fun generateOrderNumber(): String {
        val date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))
        val suffix = UUID.randomUUID().toString().replace("-", "").take(6).uppercase()
        return "SH-$date-$suffix"
    }
}