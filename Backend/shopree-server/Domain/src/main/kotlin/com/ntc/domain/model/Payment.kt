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
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.util.UUID

@Entity
class Payment(
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn("order_id", nullable = false)
    var order: Order,

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    var paymentMethod: Method,

    var amountCents: Long,

    @Column(length = 10)
    var currency: String = "VND",

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    var status: Status = Status.PENDING
){
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null

    enum class Method {MOMO, CASH, CARD}
    enum class Status {PENDING, SUCCEEDED, FAILED, REFUNDED}
}