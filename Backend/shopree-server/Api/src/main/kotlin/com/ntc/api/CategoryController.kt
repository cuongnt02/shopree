package com.ntc.api

import com.ntc.data.CategoryRepository
import com.ntc.service.CategoryService
import com.ntc.shopree.model.Category
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1", produces = [MediaType.APPLICATION_JSON_VALUE])
@CrossOrigin(origins = ["*"])
class CategoryController(private val categoryService: CategoryService) {
    @GetMapping("/categories")
    fun getCategories(): List<Category> = categoryService.getCategories()
}