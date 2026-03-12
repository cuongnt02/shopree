package com.ntc.service

import com.ntc.data.CategoryRepository
import com.ntc.domain.model.Category
import com.ntc.service.impl.CategoryServiceImpl
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.UUID

class CategoryServiceTest {
    private lateinit var categoryRepository: CategoryRepository
    private lateinit var categoryService: CategoryServiceImpl

    @BeforeEach
    fun setUp() {
        categoryRepository = mockk()
        categoryService = CategoryServiceImpl(categoryRepository)
    }

    private fun makeCategory(
        id: UUID = UUID.randomUUID(),
        name: String = "Test Category",
        slug: String = "test-category",
        parent: Category? = null
    ): Category {
        val category = Category(name = name, slug = slug, parent = parent)
        category.id = id
        return category
    }

    @Test
    fun `getCategories - returns list of CategoryResponse when categories exist`() {
        val category1 = makeCategory(name = "Cat 1", slug = "cat-1")
        val category2 = makeCategory(name = "Cat 2", slug = "cat-2")
        every { categoryRepository.findAll() } returns listOf(category1, category2)

        val result = categoryService.getCategories()

        assertEquals(2, result.size)
        assertEquals("Cat 1", result[0].name)
        assertEquals("cat-1", result[0].slug)
        assertEquals("Cat 2", result[1].name)
        assertEquals("cat-2", result[1].slug)
    }

    @Test
    fun `getCategories - returns empty list when no categories exist`() {
        every { categoryRepository.findAll() } returns emptyList()

        val result = categoryService.getCategories()

        assertTrue(result.isEmpty())
    }

    @Test
    fun `getCategory - returns CategoryResponse when category exists`() {
        val slug = "test-category"
        val category = makeCategory(slug = slug)
        every { categoryRepository.findCategoryBySlug(slug) } returns category

        val result = categoryService.getCategory(slug)

        assertNotNull(result)
        assertEquals(slug, result?.slug)
    }

    @Test
    fun `getCategory - returns null when category does not exist`() {
        val slug = "non-existent"
        every { categoryRepository.findCategoryBySlug(slug) } returns null

        val result = categoryService.getCategory(slug)

        assertNull(result)
    }
}
