package com.ntc.shopree.model

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
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


    @Column(length = 150)
    var name: String,

    @Column(length = 150, unique = true)
    var slug: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JsonManagedReference
    var parent: Category? = null,

) {
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null

    @OneToMany(mappedBy = "parent")
    @JsonBackReference
    var subcategories: MutableList<Category> = mutableListOf()
}