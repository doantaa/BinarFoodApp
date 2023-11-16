package com.binar.binarfoodapp.data.network.api.datasource

import com.binar.binarfoodapp.data.network.api.model.category.CategoriesResponse
import com.binar.binarfoodapp.data.network.api.model.menu.MenusResponse
import com.binar.binarfoodapp.data.network.api.model.order.OrderRequest
import com.binar.binarfoodapp.data.network.api.model.order.OrderResponse
import com.binar.binarfoodapp.data.network.api.service.RestaurantService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class RestaurantApiDataSourceTest {

    @MockK
    lateinit var service: RestaurantService

    private lateinit var dataSource: RestaurantDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        dataSource = RestaurantApiDataSource(service)
    }

    @Test
    fun getMenus() {
        runTest {
            // mocking
            val mockResponse = mockk<MenusResponse>(relaxed = true)
            coEvery { service.getMenus(any()) } returns mockResponse

            // actual
            val response = dataSource.getMenus("pie")
            coVerify { service.getMenus(any()) }

            assertEquals(response, mockResponse)
        }
    }

    @Test
    fun `get menus without category`() {
        runTest {
            // mocking
            val mockResponse = mockk<MenusResponse>(relaxed = true)
            coEvery { service.getMenus() } returns mockResponse

            // actual
            val response = dataSource.getMenus()
            coVerify { service.getMenus() }
            assertEquals(response, mockResponse)
        }
    }

    @Test
    fun getCategories() {
        runTest {
            val mockResponse = mockk<CategoriesResponse>(relaxed = true)
            coEvery { service.getCategories() } returns mockResponse

            val response = dataSource.getCategories()
            coVerify { service.getCategories() }

            assertEquals(response, mockResponse)
        }
    }

    @Test
    fun createOrder() {
        runTest {
            val mockResponse = mockk<OrderResponse>(relaxed = true)
            val mockRequest = mockk<OrderRequest>(relaxed = true)
            coEvery { service.createOrder(any()) } returns mockResponse

            val response = dataSource.createOrder(mockRequest)
            coVerify { service.createOrder(any()) }
            assertEquals(response, mockResponse)
        }
    }
}
