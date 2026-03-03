package com.ntc.shopree.feature.cart.ui

import com.ntc.shopree.core.model.CartItem
import com.ntc.shopree.feature.cart.domain.AddToCartUseCase
import com.ntc.shopree.feature.cart.domain.ClearCartItemUseCase
import com.ntc.shopree.feature.cart.domain.DecrementCartItemUseCase
import com.ntc.shopree.feature.cart.domain.IncrementCartItemUseCase
import com.ntc.shopree.feature.cart.domain.ObserveCartQuantityUseCase
import com.ntc.shopree.feature.cart.domain.ObserveCartUseCase
import com.ntc.shopree.feature.cart.domain.ObserveTotalPriceUseCase
import com.ntc.shopree.feature.cart.domain.RemoveCartItemUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
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
class CartViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var observeCartUseCase: ObserveCartUseCase
    private lateinit var addToCartUseCase: AddToCartUseCase
    private lateinit var observeCartQuantityUseCase: ObserveCartQuantityUseCase
    private lateinit var observeTotalPriceUseCase: ObserveTotalPriceUseCase
    private lateinit var clearCartItemUseCase: ClearCartItemUseCase
    private lateinit var incrementCartItemUseCase: IncrementCartItemUseCase
    private lateinit var decrementCartItemUseCase: DecrementCartItemUseCase
    private lateinit var removeCartItemUseCase: RemoveCartItemUseCase

    private lateinit var viewModel: CartViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        observeCartUseCase = mockk()
        addToCartUseCase = mockk()
        observeCartQuantityUseCase = mockk()
        observeTotalPriceUseCase = mockk()
        clearCartItemUseCase = mockk()
        incrementCartItemUseCase = mockk()
        decrementCartItemUseCase = mockk()
        removeCartItemUseCase = mockk()

        coEvery { observeCartUseCase() } returns Result.success(flowOf(emptyList()))
        coEvery { observeCartQuantityUseCase() } returns Result.success(flowOf(0))
        coEvery { observeTotalPriceUseCase() } returns Result.success(flowOf(0.0))

        viewModel = CartViewModel(
            observeCartUseCase,
            addToCartUseCase,
            observeCartQuantityUseCase,
            observeTotalPriceUseCase,
            clearCartItemUseCase,
            incrementCartItemUseCase,
            decrementCartItemUseCase,
            removeCartItemUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is Loading and then Success`() = runTest {
        val cartItems = listOf(CartItem(id = "1", title = "Item 1", price = 100.0, quantity = 1, image = "img.png", slug = "slug"))
        coEvery { observeCartUseCase() } returns Result.success(flowOf(cartItems))

        // Re-init to trigger init block with new mock behavior
        val vm = CartViewModel(
            observeCartUseCase,
            addToCartUseCase,
            observeCartQuantityUseCase,
            observeTotalPriceUseCase,
            clearCartItemUseCase,
            incrementCartItemUseCase,
            decrementCartItemUseCase,
            removeCartItemUseCase
        )

        assertTrue(vm.uiState.value is CartUiState.Loading)

        advanceUntilIdle()

        assertTrue(vm.uiState.value is CartUiState.Success)
        assertEquals(cartItems, (vm.uiState.value as CartUiState.Success).cartItems)
    }

    @Test
    fun `total price is updated`() = runTest {
        coEvery { observeTotalPriceUseCase() } returns Result.success(flowOf(150.0))

        val vm = CartViewModel(
            observeCartUseCase,
            addToCartUseCase,
            observeCartQuantityUseCase,
            observeTotalPriceUseCase,
            clearCartItemUseCase,
            incrementCartItemUseCase,
            decrementCartItemUseCase,
            removeCartItemUseCase
        )

        advanceUntilIdle()
        assertEquals(150.0, vm.totalPrice.value, 0.0)
    }
}
