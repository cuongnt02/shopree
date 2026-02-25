package com.ntc.domain.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.time.Instant
import java.util.UUID


@Entity
class Product (
    @ManyToOne
    @JoinColumn(name = "vendor_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    var vendor: Vendor,

    @Column(length = 255, nullable = false)
    var title: String,

    @Column(length = 255, unique = true)
    var slug: String,

    var description: String? = null,

    @ManyToOne
    @JoinColumn(name = "category_id")
    var category: Category? = null,

    @field:Column(length = 1024)
    var mainImage: String? = null,

    var tags: MutableList<String>? = null,

    var pickupAvailable: Boolean = false,

    var createdAt: Instant = Instant.now(),

    var updatedAt: Instant = Instant.now(),

    ) {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null

    enum class Status {
        DRAFT, PUBLISHED, DISABLED, PENDING_APPROVAL
    }
}