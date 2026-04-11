package com.ntc.service.impl

import com.ntc.data.OrderItemRepository
import com.ntc.data.OrderRepository
import com.ntc.data.PaymentRepository
import com.ntc.data.ProductRepository
import com.ntc.data.ProductVariantRepository
import com.ntc.data.UserRepository
import com.ntc.data.VendorRepository
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
    private val orderRepository: OrderRepository,
    private val vendorRepository: VendorRepository,
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

    override fun getOrdersByVendor(userId: UUID): List<OrderSummaryResponse> {
        val vendor = vendorRepository.findByOwnerUserId(userId)
            ?: throw IllegalArgumentException("No vendor found for this user")
        return orderRepository.findAllByVendorId(vendor.id!!).map { it.toOrderSummaryResponse() }
    }

    @Transactional
    override fun updateOrderStatus(userId: UUID, orderId: UUID, newStatus: String) {
        val vendor = vendorRepository.findByOwnerUserId(userId)
            ?: throw IllegalArgumentException("No vendor found for this user")
        val order = orderRepository.findByIdAndVendorId(orderId, vendor.id!!)
            ?: throw IllegalArgumentException("No order found for this vendor")
        val status = Order.Status.valueOf(newStatus)
        val allowedTransitions = mapOf(
            Order.Status.PENDING_PAYMENT to setOf(Order.Status.PAID, Order.Status.CANCELLED),
            Order.Status.PAID to setOf(Order.Status.READY_FOR_PICKUP),
            Order.Status.READY_FOR_PICKUP to setOf(Order.Status.PICKED_UP)
        )
        val allowed = allowedTransitions[order.status] ?: emptySet()
        if (status !in allowed) {
            throw IllegalArgumentException("Cannot transition order from ${order.status} to $status")
        }
        order.status = status
        orderRepository.save(order)
    }

    @Transactional
    override fun getVendorOrder(userId: UUID, orderId: UUID): OrderResponse {
        val vendor = vendorRepository.findByOwnerUserId(userId)
            ?: throw IllegalArgumentException("No vendor found for this user")
        val order = orderRepository.findByIdAndVendorId(orderId, vendor.id!!)
            ?: throw IllegalArgumentException("No order found for vendor")
        val payment = paymentRepository.findByOrderId(order.id!!)
            ?: throw IllegalArgumentException("No payment info found for this order")
        return order.toOrderResponse(payment)
    }

    private fun generateOrderNumber(): String {
        val date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))
        val suffix = UUID.randomUUID().toString().replace("-", "").take(6).uppercase()
        return "SH-$date-$suffix"
    }
}