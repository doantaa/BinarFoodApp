package com.binar.binarfoodapp.presentation.cart

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.binar.binarfoodapp.data.repository.CartRepository
import com.binar.binarfoodapp.tools.MainCoroutineRule
import com.binar.binarfoodapp.tools.getOrAwaitValue
import com.binar.binarfoodapp.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class CartViewModelTest {

    @get:Rule
    val testRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    private lateinit var repository: CartRepository

    private lateinit var viewModel: CartViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        coEvery { repository.getCartData() } returns flow {
            emit(
                ResultWrapper.Success(
                    Pair(
                        listOf(
                            mockk(relaxed = true),
                            mockk(relaxed = true),
                            mockk(relaxed = true),
                            mockk(relaxed = true)
                        ),
                        10_000
                    )
                )
            )
        }

        viewModel = spyk(CartViewModel(repository))

        val updateResultMock = flow {
            emit(ResultWrapper.Success(true))
        }

        coEvery { repository.decreaseCart(any()) } returns updateResultMock
        coEvery { repository.increaseCart(any()) } returns updateResultMock
        coEvery { repository.deleteCart(any()) } returns updateResultMock
        coEvery { repository.setOrderNotes(any()) } returns updateResultMock
    }

    @Test
    fun `get cart list live data`() {
        val result = viewModel.cartList.getOrAwaitValue()
        assertEquals(result.payload?.first?.size, 4)
        assertEquals(result.payload?.second, 10000)
    }

    @Test
    fun `decrease cart`() {
        viewModel.decreaseCart(mockk())
        coVerify { repository.decreaseCart(any()) }
    }

    @Test
    fun `increase cart`() {
        viewModel.increaseCart(mockk())
        coVerify { repository.increaseCart(any()) }
    }

    @Test
    fun `remove cart`() {
        viewModel.removeCart(mockk())
        coVerify { repository.deleteCart(any()) }
    }

    @Test
    fun `set cart notes`() {
        viewModel.setCartNotes(mockk())
        coVerify { repository.setOrderNotes(any()) }
    }
}
