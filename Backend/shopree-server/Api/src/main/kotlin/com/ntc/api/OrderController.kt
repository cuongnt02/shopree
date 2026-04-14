package com.ntc.api

import com.ntc.api.payload.error.NotFoundErrorResponse
import com.ntc.api.payload.request.UpdateOrderStatusRequest
import com.ntc.domain.model.User
import com.ntc.service.OrderService
import com.ntc.service.dto.OrderSummaryResponse
import com.ntc.service.dto.PlaceOrderRequest
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/v1", produces = [MediaType.APPLICATION_JSON_VALUE])
class OrderController(
    private val orderService: OrderService
) {
    @PostMapping("/orders")
    fun placeOrder(
        authentication: Authentication,
        @Valid @RequestBody request: PlaceOrderRequest
    ): ResponseEntity<Any> {
        val user = authentication.principal as User

        return try {
            val order = orderService.placeOrder(user.id!!, request)
            ResponseEntity.status(HttpStatus.CREATED).body(order)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().body(NotFoundErrorResponse(e.message ?: "Invalid order request"))
        }

    }

    @GetMapping("/orders")
    fun getOrders(authentication: Authentication): ResponseEntity<List<OrderSummaryResponse>> {
        val user = authentication.principal as User
        return ResponseEntity.ok(orderService.getOrders(user.id!!))
    }

    @GetMapping("/order/{id}")
    fun getOrder(
        authentication: Authentication,
        @PathVariable id: UUID
    ): ResponseEntity<Any> {
        val user = authentication.principal as User
        return try {
            ResponseEntity.ok(orderService.getOrder(user.id!!, id))

        } catch (
            e: IllegalArgumentException
        ) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                NotFoundErrorResponse(e.message ?: "Order not found")
            )
        }
    }

    @GetMapping("/vendor/orders")
    fun getVendorOrders(@RequestParam(required = false) status: String?, authentication: Authentication): ResponseEntity<List<OrderSummaryResponse>> {
        val user = authentication.principal as User
        return ResponseEntity.ok(orderService.getOrdersByVendor(user.id!!, status))
    }

    @PatchMapping("/vendor/orders/{id}/status")
    fun setOrderStatus(
        @Valid @RequestBody request: UpdateOrderStatusRequest,
        @PathVariable id: UUID,
        authentication: Authentication
    ): ResponseEntity<Any> {
        val user = authentication.principal as User
        return try {
            orderService.updateOrderStatus(user.id!!, id, request.status)
            ResponseEntity.noContent().build()
        } catch(e: IllegalArgumentException) {
            ResponseEntity.badRequest().body("Malformed request ${e.message}")
        }
    }

    @GetMapping("/vendor/order/{id}")
    fun getVendorOrder(authentication: Authentication ,@PathVariable id: UUID): ResponseEntity<Any> {
        val user = authentication.principal as User
        return try {
            val order = orderService.getVendorOrder(user.id!!, id)
            ResponseEntity.ok(order)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().body("Malformed request ${e.message}")
        }
    }
}
