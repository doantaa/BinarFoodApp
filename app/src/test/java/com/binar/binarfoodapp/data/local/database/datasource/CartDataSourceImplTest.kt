package com.binar.binarfoodapp.data.local.database.datasource

import com.binar.binarfoodapp.data.local.database.dao.CartDao
import com.binar.binarfoodapp.data.local.database.entity.CartEntity
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class CartDataSourceImplTest {

    @MockK
    lateinit var dao: CartDao

    private lateinit var dataSource: CartDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        dataSource = CartDataSourceImpl(dao)
    }

    @Test
    fun insertCart() {
        runTest {
            val cartEntityMock = mockk<CartEntity>()
            coEvery { dao.insertCart(any()) } returns 1

            val result = dataSource.insertCart(cartEntityMock)
            coVerify { dao.insertCart(any()) }
            assertEquals(result, 1)
        }
    }

    @Test
    fun insertCarts() {
        runTest {
            val cartEntityMock1 = mockk<CartEntity>()
            val cartEntityMock2 = mockk<CartEntity>()
            val cartListMock = listOf<CartEntity>(cartEntityMock1, cartEntityMock2)

            coEvery { dao.insertCarts(any()) } returns Unit
            val result = dataSource.insertCarts(cartListMock)
            coVerify { dao.insertCarts(any()) }
            assertEquals(result, Unit)
        }
    }

    @Test
    fun getAllCarts() {
        val cartEntityMock1 = mockk<CartEntity>()
        val cartEntityMock2 = mockk<CartEntity>()
        val cartListMock = listOf<CartEntity>(cartEntityMock1, cartEntityMock2)
        val cartListMockFlow = flow {
            emit(cartListMock)
        }
        every { dao.getAllCarts() } returns cartListMockFlow
        val result = dataSource.getAllCarts()
        verify { dao.getAllCarts() }
        assertEquals(result, cartListMockFlow)
    }

    @Test
    fun getCartById() {
        val cartEntityMock = mockk<CartEntity>()
        val cartEntityMockFlow = flow { emit(cartEntityMock) }
        every { dao.getCartById(any()) } returns cartEntityMockFlow
        val result = dataSource.getCartById(1)
        verify { dao.getCartById(any()) }
        assertEquals(result, cartEntityMockFlow)
    }

    @Test
    fun updateCart() {
        runTest {
            val cartEntityMock = mockk<CartEntity>()
            coEvery { dao.updateCart(any()) } returns 1
            val result = dataSource.updateCart(cartEntityMock)
            coVerify { dao.updateCart(any()) }
            assertEquals(result, 1)
        }
    }

    @Test
    fun deleteCart() {
        runTest {
            val cartEntityMock = mockk<CartEntity>()
            coEvery { dao.deleteCart(any()) } returns 1
            val result = dataSource.deleteCart(cartEntityMock)
            coVerify { dao.deleteCart(any()) }
            assertEquals(result, 1)
        }
    }

    @Test
    fun deleteAllCart() {
        coEvery { dao.deleteAllCart() } returns 1
        val result = dataSource.deleteAllCart()
        coVerify { dao.deleteAllCart() }
        assertEquals(result, 1)
    }
}
