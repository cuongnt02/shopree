package com.ntc.domain.model

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
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.time.Instant
import java.util.UUID


@Entity
class Product(
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

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    var status: Status = Status.DRAFT,

    var tags: MutableList<String>? = null,

    var pickupAvailable: Boolean = false,

    var createdAt: Instant = Instant.now(),

    var updatedAt: Instant = Instant.now(),

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    var variants: MutableList<ProductVariant> = mutableListOf()
) {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null

    enum class Status {
        DRAFT, PUBLISHED, DISABLED, PENDING_APPROVAL
    }
}