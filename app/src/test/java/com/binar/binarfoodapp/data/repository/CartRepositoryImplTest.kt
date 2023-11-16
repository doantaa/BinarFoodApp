package com.binar.binarfoodapp.data.repository

import app.cash.turbine.test
import com.binar.binarfoodapp.data.local.database.datasource.CartDataSource
import com.binar.binarfoodapp.data.local.database.entity.CartEntity
import com.binar.binarfoodapp.data.network.api.datasource.RestaurantDataSource
import com.binar.binarfoodapp.data.network.api.model.order.OrderResponse
import com.binar.binarfoodapp.model.Cart
import com.binar.binarfoodapp.model.Menu
import com.binar.binarfoodapp.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class CartRepositoryImplTest {

    @MockK
    lateinit var localDataSource: CartDataSource

    @MockK
    lateinit var apiDataSource: RestaurantDataSource

    private lateinit var repository: CartRepository

    private val fakeMenuItemCart1 = CartEntity(
        menuName = "menu name",
        menuPrice = 100000,
        menuImgUrl = "url"
    )

    private val fakeMenuItemCart2 = CartEntity(
        id = 2,
        menuName = "menu name",
        menuPrice = 100000,
        menuImgUrl = "url",
        itemQuantity = 2,
        orderNotes = "notes"
    )

    private val mockCart = Cart(
        id = 1,
        menuId = 2,
        menuName = "menu name",
        menuPrice = 1000,
        menuImgUrl = "url",
        itemQuantity = 1,
        orderNotes = "notes"
    )

    private val fakeCartList = listOf<CartEntity>(fakeMenuItemCart1, fakeMenuItemCart2)

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = CartRepositoryImpl(localDataSource, apiDataSource)
    }

    @Test
    fun `get cart data, result success`() {
        runTest {
            every { localDataSource.getAllCarts() } returns flow {
                emit(fakeCartList)
            }

            repository.getCartData()
                .map {
                    delay(100)
                    it
                }
                .test {
                    delay(2201)
                    val data = expectMostRecentItem()
                    assertTrue(data is ResultWrapper.Success)
                    verify { localDataSource.getAllCarts() }
                }
        }
    }

    @Test
    fun `get cart data, result loading`() {
        every { localDataSource.getAllCarts() } returns flow {
            emit(fakeCartList)
        }

        runTest {
            repository.getCartData().map {
                delay(100)
                it
            }
                .test {
                    delay(101)
                    val data = expectMostRecentItem()
                    assertTrue(data is ResultWrapper.Loading)
                    verify { localDataSource.getAllCarts() }
                }
        }
    }

    @Test
    fun `get cart data, result empty`() {
        every { localDataSource.getAllCarts() } returns flow {
            emit(listOf())
        }

        runTest {
            repository.getCartData().map {
                delay(100)
                it
            }
                .test {
                    delay(201)
                    val data = expectMostRecentItem()
                    assertTrue(data is ResultWrapper.Empty)
                    verify { localDataSource.getAllCarts() }
                }
        }
    }

    @Test
    fun `get cart data, result error`() {
        every { localDataSource.getAllCarts() } returns flow {
            throw IllegalStateException("Mock Error")
        }

        runTest {
            repository.getCartData().map {
                delay(100)
                it
            }
                .test {
                    delay(201)
                    val data = expectMostRecentItem()
                    assertTrue(data is ResultWrapper.Error)
                    verify { localDataSource.getAllCarts() }
                }
        }
    }

    @Test
    fun `create cart loading, menu id not null`() {
        val mockMenuRequest = mockk<Menu>(relaxed = true)
        coEvery { localDataSource.insertCart(any()) } returns 1
        runTest {
            repository.createCart(mockMenuRequest, 1)
                .map {
                    delay(100)
                    it
                }
                .test {
                    delay(101)
                    val result = expectMostRecentItem()
                    assertTrue(result is ResultWrapper.Loading)
                    coVerify { localDataSource.insertCart(any()) }
                }
        }
    }

    @Test
    fun `create cart success, menu id not null`() {
        val mockMenuRequest = mockk<Menu>(relaxed = true)
        coEvery { localDataSource.insertCart(any()) } returns 1
        runTest {
            repository.createCart(mockMenuRequest, 1)
                .map {
                    delay(100)
                    it
                }
                .test {
                    delay(201)
                    val result = expectMostRecentItem()
                    assertTrue(result is ResultWrapper.Success)
                    assertEquals(result.payload, true)
                    coVerify { localDataSource.insertCart(any()) }
                }
        }
    }

    @Test
    fun `create cart error, menu id not null`() {
        val mockMenuRequest = mockk<Menu>(relaxed = true)
        coEvery { localDataSource.insertCart(any()) } throws IllegalStateException("Error")
        runTest {
            repository.createCart(mockMenuRequest, 1)
                .map {
                    delay(100)
                    it
                }
                .test {
                    delay(201)
                    val result = expectMostRecentItem()
                    assertTrue(result is ResultWrapper.Error)
                    assertEquals(result.payload, null)
                    coVerify { localDataSource.insertCart(any()) }
                }
        }
    }

    @Test
    fun `decrease cart when qty less than or equal 1, delete`() {
        coEvery { localDataSource.deleteCart(any()) } returns 1
        coEvery { localDataSource.updateCart(any()) } returns 1

        runTest {
            repository.decreaseCart(mockCart)
                .map {
                    delay(100)
                    it
                }
                .test {
                    delay(201)
                    val result = expectMostRecentItem()
                    assertEquals(result.payload, true)
                    coVerify(atLeast = 1) { localDataSource.deleteCart(any()) }
                    coVerify(atLeast = 0) { localDataSource.updateCart(any()) }
                }
        }
    }

    @Test
    fun `decrease cart when qty more than 1, update`() {
        val mockCart = Cart(
            id = 1,
            menuId = 2,
            menuName = "menu name",
            menuPrice = 1000,
            menuImgUrl = "url",
            itemQuantity = 2,
            orderNotes = "notes"
        )
        coEvery { localDataSource.deleteCart(any()) } returns 1
        coEvery { localDataSource.updateCart(any()) } returns 1

        runTest {
            repository.decreaseCart(mockCart)
                .map {
                    delay(100)
                    it
                }
                .test {
                    delay(201)
                    val result = expectMostRecentItem()
                    assertEquals(result.payload, true)
                    coVerify(atLeast = 0) { localDataSource.deleteCart(any()) }
                    coVerify(atLeast = 1) { localDataSource.updateCart(any()) }
                }
        }
    }

    @Test
    fun `increse cart`() {
        coEvery { localDataSource.updateCart(any()) } returns 1
        runTest {
            repository.increaseCart(mockCart)
                .map {
                    delay(100)
                    it
                }
                .test {
                    delay(201)
                    val result = expectMostRecentItem()
                    assertEquals(result.payload, true)
                    coVerify { localDataSource.updateCart(any()) }
                }
        }
    }

    @Test
    fun `set order notes`() {
        coEvery { localDataSource.updateCart(any()) } returns 1
        runTest {
            repository.setOrderNotes(mockCart)
                .map {
                    delay(100)
                    it
                }
                .test {
                    delay(201)
                    val result = expectMostRecentItem()
                    assertEquals(result.payload, true)
                    coVerify { localDataSource.updateCart(any()) }
                }
        }
    }

    @Test
    fun `delete item cart`() {
        coEvery { localDataSource.deleteCart(any()) } returns 1
        runTest {
            repository.deleteCart(mockCart)
                .map {
                    delay(100)
                    it
                }
                .test {
                    delay(201)
                    val result = expectMostRecentItem()
                    assertEquals(result.payload, true)
                    coVerify { localDataSource.deleteCart(any()) }
                }
        }
    }

    @Test
    fun `clean cart`() {
        coEvery { localDataSource.deleteAllCart() } returns 1
        runTest {
            val result = repository.cleanCart()
            coVerify { localDataSource.deleteAllCart() }
            assertEquals(result.payload, 1)
        }
    }

    @Test
    fun `test order`() {
        val fakeOrderRequest = listOf<Cart>(
            Cart(
                id = 1,
                menuId = 2,
                menuName = "menu name",
                menuPrice = 1000,
                menuImgUrl = "url",
                itemQuantity = 1,
                orderNotes = "notes"
            ),
            Cart(
                id = 2,
                menuId = 3,
                menuName = "menu name",
                menuPrice = 1000,
                menuImgUrl = "url",
                itemQuantity = 1,
                orderNotes = "notes"
            )
        )

        val mockOrderResponse = OrderResponse(
            code = 201,
            status = true,
            message = "Created"
        )

        coEvery { apiDataSource.createOrder(any()) } returns mockOrderResponse

        runTest {
            repository.order(fakeOrderRequest)
                .map {
                    delay(100)
                    it
                }
                .test {
                    delay(201)
                    val result = expectMostRecentItem()
                    assertTrue(result is ResultWrapper.Success)
                }
        }
    }
}
