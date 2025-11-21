package com.ntc.shopree.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.Table
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.time.Instant
import java.util.UUID


@Entity
@Table(name = "user", indexes = [Index(name = "idx_user_email", columnList = "email")])
class User (
    @field:Column(length = 255, unique = true, nullable = false)
    var email: String,

    @field:Column(length = 30)
    var phone: String,

    @field:Column(length = 255, nullable = false)
    var passwordHash: String,

    @field:Column(length = 255)
    var name: String,

    @field:Column(length=30, nullable = false)
    @field:Enumerated(value = EnumType.STRING)
    var role: Role = Role.BUYER,

    @field:JdbcTypeCode(value = SqlTypes.JSON)
    @field:Column(columnDefinition = "jsonb")
    var metadata: Map<String, Any> = emptyMap(),

    var verified: Boolean = false,

    var createdAt: Instant = Instant.now(),

    var updatedAt: Instant = Instant.now(),
) {

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null

    enum class Role {
        BUYER, VENDOR_USER, ADMIN
    }
}