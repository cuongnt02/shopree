package com.ntc.domain.dto

data class CategoryResponse(
    var id: String,
    var name: String,
    var slug: String,
    var parentId: String,
)