package com.ntc.shopree.controller

import com.ntc.shopree.model.Category
import com.ntc.shopree.repository.CategoryRepository
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping

@RestController
@RequestMapping("/api/v1", produces = [MediaType.APPLICATION_JSON_VALUE])
@CrossOrigin(origins = ["*"])
class CategoryController(private val categoryRepository: CategoryRepository) {
    @GetMapping("/categories")
    fun getCategories(): List<Category> {
        val categories = categoryRepository.findAll()
        return categories.toList()
    }
}
