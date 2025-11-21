package com.ntc.shopree.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.hibernate.type.SqlTypes
import org.locationtech.jts.geom.Point
import java.time.Instant
import java.util.UUID

@Entity
@Table(name = "vendor", indexes = [Index(name = "idx_vendor_status", columnList = "status")])
class Vendor (
    @field:OneToOne
    @field:JoinColumn(name = "owner_user_id")
    @field:OnDelete(action = OnDeleteAction.SET_NULL)
    var ownerUser: User? = null,

    @field:Column(length = 255, nullable = false)
    var vendorName: String,

    @field:Column(length = 255, unique = true)
    var slug : String,

    var description: String? = null,

    @field:Enumerated(EnumType.STRING)
    var status: Status = Status.PENDING,

    @field:Column(columnDefinition = "geography(Point, 4326)")
    var location: Point? = null,

    @field:JdbcTypeCode(value = SqlTypes.JSON)
    @field:Column(columnDefinition = "jsonb")
    var address: Map<String, Any>? = null,

    var pickupAvailable: Boolean = false,

    var localDeliveryPickupKm: Int = 10,

    var createdAt: Instant = Instant.now(),

    var updatedAt: Instant = Instant.now(),
) {
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null

    enum class Status {
        PENDING, APPROVED, REJECTED, SUSPENDED
    }
}