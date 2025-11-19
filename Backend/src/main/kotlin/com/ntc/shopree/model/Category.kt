package com.ntc.shopree.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.util.UUID

@Entity
class Category(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID,

    @Column(length = 150)
    var name: String,

    @Column(length = 150, unique = true)
    var slug: String,

    @ManyToOne
    @JoinColumn(name = "parent_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    var parent: Category? = null,

    @OneToMany(mappedBy = "parent")
    var subcategories: MutableList<Category> = mutableListOf()
)