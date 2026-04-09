package com.ntc.shopree.seeder

import com.ntc.data.OrderRepository
import com.ntc.data.PaymentRepository
import com.ntc.data.ProductRepository
import com.ntc.data.ProductVariantRepository
import com.ntc.data.UserRepository
import com.ntc.data.VendorRepository
import com.ntc.domain.model.Order
import com.ntc.domain.model.OrderItem
import com.ntc.domain.model.Payment
import com.ntc.domain.model.ProductVariant
import jakarta.transaction.Transactional
import org.springframework.boot.CommandLineRunner
import org.springframework.core.annotation.Order as SeedOrder
import org.springframework.stereotype.Component
import java.time.Instant

@Component
@SeedOrder(5)
class OrdersSeeder(
    private val userRepository: UserRepository,
    private val vendorRepository: VendorRepository,
    private val productRepository: ProductRepository,
    private val productVariantRepository: ProductVariantRepository,
    private val orderRepository: OrderRepository,
    private val paymentRepository: PaymentRepository
) : CommandLineRunner {

    private data class ProductWithVariant(
        val slug: String,
        val title: String,
        val variant: ProductVariant
    )

    @Transactional
    override fun run(vararg args: String) {
        val buyer = userRepository.findByEmail("cuong.ntc02@gmail.com") ?: return
        val vendor1 = vendorRepository.getVendorBySlug("electronics-store") ?: return

        val productsWithVariants = productRepository.findAll()
            .filter { it.vendor.id == vendor1.id }
            .mapNotNull { product ->
                val variant = productVariantRepository.findFirstByProductId(product.id!!) ?: return@mapNotNull null
                ProductWithVariant(product.slug, product.title, variant)
            }

        if (productsWithVariants.isEmpty()) return

        // Payments hold a FK to orders — delete payments first to avoid constraint violation
        paymentRepository.deleteAll()
        orderRepository.deleteAll()

        fun buildOrder(
            number: String,
            status: Order.Status,
            placedAt: Instant,
            lines: List<Pair<ProductWithVariant, Int>>
        ): Order {
            val order = Order(user = buyer, orderNumber = number, totalCents = 0L, status = status, placedAt = placedAt)
            lines.forEach { (pwv, qty) ->
                val lineTotal = pwv.variant.priceCents * qty
                order.items.add(
                    OrderItem(
                        order = order,
                        variant = pwv.variant,
                        productSlug = pwv.slug,
                        productTitle = pwv.title,
                        sku = pwv.variant.sku,
                        quantity = qty,
                        unitPriceCents = pwv.variant.priceCents,
                        totalPriceCents = lineTotal
                    )
                )
                order.totalCents += lineTotal
            }
            return order
        }

        val p = productsWithVariants

        // Seed 4 orders across different statuses so the vendor portal has meaningful data to display
        val orders = listOf(
            buildOrder("SH-DEV-001", Order.Status.PENDING_PAYMENT, Instant.now().minusSeconds(900), listOf(
                p[0 % p.size] to 1
            )),
            buildOrder("SH-DEV-002", Order.Status.PAID, Instant.now().minusSeconds(3_600), listOf(
                p[1 % p.size] to 2,
                p[2 % p.size] to 1
            )),
            buildOrder("SH-DEV-003", Order.Status.READY_FOR_PICKUP, Instant.now().minusSeconds(7_200), listOf(
                p[0 % p.size] to 1,
                p[3 % p.size] to 1
            )),
            buildOrder("SH-DEV-004", Order.Status.PICKED_UP, Instant.now().minusSeconds(86_400), listOf(
                p[2 % p.size] to 3
            )),
        )

        orders.forEach { order ->
            val saved = orderRepository.save(order)
            paymentRepository.save(
                Payment(order = saved, paymentMethod = Payment.Method.CASH, amountCents = saved.totalCents)
            )
        }
    }
}
