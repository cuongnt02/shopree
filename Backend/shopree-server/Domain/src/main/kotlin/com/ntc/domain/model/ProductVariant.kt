package com.ntc.domain.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import org.hibernate.annotations.JdbcType
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.hibernate.type.SqlTypes
import java.sql.SQLType
import java.time.Instant
import java.util.UUID

@Entity
class ProductVariant(
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    var product: Product,

    @Column(length = 255)
    var title: String? = null,

    @Column(length = 100, nullable = true, unique = true)
    var sku: String? = null,

    var priceCents: Long,

    var compareAtCents: Long? = null,

    var inventoryCount: Int = 0,

    @Column(length = 30)
    var inventoryPolicy: String = "deny_over_sell",

    @field:JdbcTypeCode(value = SqlTypes.JSON)
    @field:Column(columnDefinition = "jsonb")
    var metadata: Map<String, Any> = emptyMap(),

    var createdAt: Instant = Instant.now(),

    var updatedAt: Instant = Instant.now()
) {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null
}