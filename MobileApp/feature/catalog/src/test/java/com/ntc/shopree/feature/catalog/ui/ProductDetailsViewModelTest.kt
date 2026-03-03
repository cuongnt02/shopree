package com.ntc.shopree.feature.catalog.ui

import com.ntc.shopree.core.model.Product
import com.ntc.shopree.feature.cart.domain.AddToCartUseCase
import com.ntc.shopree.feature.catalog.domain.GetSingleProductUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProductDetailsViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var getSingleProductUseCase: GetSingleProductUseCase
    private lateinit var addToCartUseCase: AddToCartUseCase
    private lateinit var viewModel: ProductDetailsViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getSingleProductUseCase = mockk()
        addToCartUseCase = mockk()
        viewModel = ProductDetailsViewModel(
            getSingleProductUseCase,
            addToCartUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadProductDetails success updates uiState`() = runTest {
        val slug = "test-product"
        val product = Product(id = "1", title = "Test Product", slug = slug, price = 100.0, description = "Description", category = "Category", image = "image.png")
        coEvery { getSingleProductUseCase(slug) } returns Result.success(product)

        viewModel.loadProductDetails(slug)

        assertTrue(viewModel.uiState.value is ProductDetailsUiState.Loading)

        advanceUntilIdle()

        assertTrue(viewModel.uiState.value is ProductDetailsUiState.Success)
        assertEquals(product, (viewModel.uiState.value as ProductDetailsUiState.Success).product)
    }

    @Test
    fun `loadProductDetails failure updates uiState`() = runTest {
        val slug = "test-product"
        coEvery { getSingleProductUseCase(slug) } returns Result.failure(Exception("Error"))

        viewModel.loadProductDetails(slug)
        advanceUntilIdle()

        assertTrue(viewModel.uiState.value is ProductDetailsUiState.Error)
        assertEquals("Error", (viewModel.uiState.value as ProductDetailsUiState.Error).message)
    }
}
