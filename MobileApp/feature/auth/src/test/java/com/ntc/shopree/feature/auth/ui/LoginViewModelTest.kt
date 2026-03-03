package com.ntc.shopree.feature.auth.ui

import com.ntc.shopree.feature.auth.domain.CheckCurrentUserUseCase
import com.ntc.shopree.feature.auth.domain.CheckSessionUseCase
import com.ntc.shopree.feature.auth.domain.LoginUseCase
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
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var checkCurrentUserUseCase: CheckCurrentUserUseCase
    private lateinit var checkSessionUseCase: CheckSessionUseCase
    private lateinit var loginUseCase: LoginUseCase
    private lateinit var viewModel: LoginViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        checkCurrentUserUseCase = mockk()
        checkSessionUseCase = mockk()
        loginUseCase = mockk()
        viewModel = LoginViewModel(
            checkCurrentUserUseCase,
            checkSessionUseCase,
            loginUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is correct`() {
        assertFalse(viewModel.authenticated.value)
        assertEquals(LoginFormUiState(), viewModel.inputs.value)
        assertTrue(viewModel.loginUiState.value is LoginUiState.Idle)
    }

    @Test
    fun `updateEmailOrPhone updates state`() {
        val email = "test@example.com"
        viewModel.updateEmailOrPhone(email)
        assertEquals(email, viewModel.inputs.value.emailOrPhone)
    }

    @Test
    fun `updatePassword updates state`() {
        val password = "password123"
        viewModel.updatePassword(password)
        assertEquals(password, viewModel.inputs.value.password)
    }

    @Test
    fun `logUserIn success updates authenticated and uiState`() = runTest {
        val username = "test@example.com"
        val password = "password123"
        coEvery { loginUseCase(username, password) } returns Result.success(Unit)

        viewModel.logUserIn(username, password)

        assertTrue(viewModel.loginUiState.value is LoginUiState.Loading)

        advanceUntilIdle()

        assertTrue(viewModel.authenticated.value)
        assertTrue(viewModel.loginUiState.value is LoginUiState.Success)
    }

    @Test
    fun `logUserIn failure updates uiState`() = runTest {
        val username = "test@example.com"
        val password = "password123"
        coEvery { loginUseCase(username, password) } returns Result.failure(Exception("Login failed"))

        viewModel.logUserIn(username, password)

        assertTrue(viewModel.loginUiState.value is LoginUiState.Loading)

        advanceUntilIdle()

        assertFalse(viewModel.authenticated.value)
        assertTrue(viewModel.loginUiState.value is LoginUiState.Error)
    }

    @Test
    fun `checkCurrentUser authenticated success`() = runTest {
        coEvery { checkSessionUseCase() } returns Result.success(true)

        viewModel.checkCurrentUser()
        advanceUntilIdle()

        assertTrue(viewModel.authenticated.value)
    }
}
