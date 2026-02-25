package com.ntc.domain.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.Instant
import java.util.UUID

@Entity
@Table(name = "refresh_token")
class RefreshToken(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @Column(nullable = true, unique = true, length = 255)
    val token: String,

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne
    val user: User,

    @Column(nullable = false)
    val expiresAt: Instant,

    @Column(nullable = false)
    var revoked: Boolean = false,

    @Column(nullable = true, length = 255)
    val deviceInfo: String? = null,

    @Column(nullable = true, length = 45)
    val ipAddress: String? = null
)