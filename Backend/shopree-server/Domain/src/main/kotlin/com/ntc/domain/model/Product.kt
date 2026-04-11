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

    fun copy(
    vendor: Vendor = this.vendor,
    title: String = this.title,
    slug: String = this.slug,
    description: String? = this.description,
    category: Category? = this.category,
    mainImage: String? = this.mainImage,
    status: Status = this.status,
    tags: MutableList<String>? = this.tags,
    pickupAvailable: Boolean = this.pickupAvailable,
    variants: MutableList<ProductVariant> = this.variants
    ): Product {
        val copy = Product(
            vendor = vendor,
            title = title,
            slug = slug,
            description = description,
            category = category,
            mainImage = mainImage,
            status = status,
            tags = tags,
            pickupAvailable = pickupAvailable,
            variants = variants
        )
        copy.id = this.id
        copy.createdAt = this.createdAt
        copy.updatedAt = this.updatedAt
        return copy
    }
}