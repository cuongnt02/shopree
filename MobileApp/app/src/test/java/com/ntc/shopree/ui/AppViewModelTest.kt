package com.ntc.shopree.ui

import com.ntc.shopree.feature.auth.domain.CheckSessionUseCase
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
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AppViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var checkSessionUseCase: CheckSessionUseCase

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        checkSessionUseCase = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is Loading and transitions to Authenticated when session is valid`() = runTest {
        coEvery { checkSessionUseCase() } returns Result.success(true)

        val viewModel = AppViewModel(checkSessionUseCase)

        assertTrue(viewModel.state.value is AppState.Loading)

        advanceUntilIdle()

        assertTrue(viewModel.state.value is AppState.Authenticated)
    }

    @Test
    fun `initial state is Loading and transitions to Unauthenticated when session is invalid`() = runTest {
        coEvery { checkSessionUseCase() } returns Result.success(false)

        val viewModel = AppViewModel(checkSessionUseCase)

        assertTrue(viewModel.state.value is AppState.Loading)

        advanceUntilIdle()

        assertTrue(viewModel.state.value is AppState.Unauthenticated)
    }
}
