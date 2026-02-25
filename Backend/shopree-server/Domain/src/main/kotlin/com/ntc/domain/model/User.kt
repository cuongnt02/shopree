package com.ntc.domain.model

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
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.Instant
import java.util.UUID


@Entity
@Table(name = "shopree_user", indexes = [Index(name = "idx_user_email", columnList = "email")])
class User (
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null,

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
): UserDetails {



    override fun getAuthorities(): Collection<GrantedAuthority> {
        return listOf(role)
    }

    override fun getPassword(): String {
        return passwordHash
    }

    override fun getUsername(): String {
        return email
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

    enum class Role: GrantedAuthority {
        BUYER, VENDOR_USER, ADMIN;


        override fun getAuthority(): String {
            return name
        }
    }
}