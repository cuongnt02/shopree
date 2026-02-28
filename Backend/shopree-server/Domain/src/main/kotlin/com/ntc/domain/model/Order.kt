package com.ntc.domain.model

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.time.Instant
import java.util.UUID

@Entity
@Table(name = "shopree_order")
class Order(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User,

    @Column(length = 64, unique = true)
    var orderNumber: String,

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    var status: Status = Status.PENDING_PAYMENT,

    var totalCents: Long,
    @Column(length = 10)
    var currency: String = "VND",

    var placedAt: Instant = Instant.now(),

    @field:JdbcTypeCode(value = SqlTypes.JSON)
    @field:Column(columnDefinition = "jsonb")
    var metadata: Map<String, Any> = emptyMap(),

    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL], orphanRemoval = true)
    var items: MutableList<OrderItem> = mutableListOf()
) {
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null

    enum class Status {
        PENDING_PAYMENT, PAID, READY_FOR_PICKUP, PICKED_UP, CANCELLED, REFUNDED
    }
}