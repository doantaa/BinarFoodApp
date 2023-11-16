package com.binar.binarfoodapp.data.repository

import app.cash.turbine.test
import com.binar.binarfoodapp.data.network.api.datasource.RestaurantDataSource
import com.binar.binarfoodapp.data.network.api.model.category.CategoriesResponse
import com.binar.binarfoodapp.data.network.api.model.category.CategoryResponse
import com.binar.binarfoodapp.data.network.api.model.menu.MenuItemResponse
import com.binar.binarfoodapp.data.network.api.model.menu.MenusResponse
import com.binar.binarfoodapp.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class MenuRepositoryImplTest {

    @MockK
    lateinit var apiDataSource: RestaurantDataSource

    private lateinit var repository: MenuRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = MenuRepositoryImpl(apiDataSource)
    }

    @Test
    fun `get categories, result loading`() {
        val mockResponse = mockk<CategoriesResponse>()
        runTest {
            coEvery { apiDataSource.getCategories() } returns mockResponse
            repository.getCategories().map {
                delay(100)
                it
            }.test {
                delay(110)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                coVerify { apiDataSource.getCategories() }
            }
        }
    }

    @Test
    fun `get categories, result success`() {
        val fakeCategoryResponse = CategoryResponse(
            imageUrl = "url",
            name = "name"
        )

        val fakeCategoriesResponse = CategoriesResponse(
            code = 200,
            data = listOf(fakeCategoryResponse),
            message = "message",
            status = true
        )

        runTest {
            coEvery { apiDataSource.getCategories() } returns fakeCategoriesResponse
            repository.getCategories().map {
                delay(100)
                it
            }.test {
                delay(201)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                assertEquals(data.payload?.size, 1)
                assertEquals(data.payload?.get(0)?.name, "name")
                coVerify { apiDataSource.getCategories() }
            }
        }
    }

    @Test
    fun `get categories, result empty`() {
        val fakeCategoriesResponse = CategoriesResponse(
            code = 2,
            message = "message",
            data = emptyList(),
            status = true
        )
        runTest {
            coEvery { apiDataSource.getCategories() } returns fakeCategoriesResponse
            repository.getCategories().map {
                delay(100)
                it
            }.test {
                delay(201)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Empty)
                assertEquals(data.payload?.size, 0)
                coVerify { apiDataSource.getCategories() }
            }
        }
    }

    @Test
    fun `get categories, result error`() {
        runTest {
            coEvery { apiDataSource.getCategories() } throws IllegalStateException()
            repository.getCategories().map {
                delay(100)
                it
            }.test {
                delay(201)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify { apiDataSource.getCategories() }
            }
        }
    }

    @Test
    fun `get menu, result loading`() {
        val menuResponseMock = mockk<MenusResponse>()
        runTest {
            coEvery { apiDataSource.getMenus(any()) } returns menuResponseMock
            repository.getMenus("kentang").map {
                delay(100)
                it
            }.test {
                delay(101)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                coVerify { apiDataSource.getMenus(any()) }
            }
        }
    }

    @Test
    fun `get menu with category, result success`() {
        val fakeMenuItemResponse = MenuItemResponse(
            restaurantAddress = "address",
            description = "desc",
            price = 1,
            formattedPrice = "1",
            imageUrl = "string",
            name = "bakwan"
        )

        val fakeMenusResponse = MenusResponse(
            code = 11,
            data = listOf(fakeMenuItemResponse),
            message = "message",
            status = true

        )
        runTest {
            coEvery { apiDataSource.getMenus(any()) } returns fakeMenusResponse
            repository.getMenus("makanan").map {
                delay(100)
                it
            }.test {
                delay(201)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                assertEquals(data.payload?.size, 1)
                assertEquals(data.payload?.get(0)?.name, "bakwan")
                coVerify { apiDataSource.getMenus(any()) }
            }
        }
    }

    @Test
    fun `get menu with null category, result success`() {
        val fakeMenuItemResponse1 = MenuItemResponse(
            restaurantAddress = "address",
            description = "desc",
            price = 1,
            formattedPrice = "1",
            imageUrl = "string",
            name = "bakwan"
        )

        val fakeMenuItemResponse2 = MenuItemResponse(
            restaurantAddress = null,
            description = "desc",
            price = 1,
            formattedPrice = null,
            imageUrl = "string",
            name = "bakwan"
        )

        val fakeMenusResponse = MenusResponse(
            code = 11,
            data = listOf(fakeMenuItemResponse1, fakeMenuItemResponse2),
            message = "message",
            status = true

        )
        runTest {
            coEvery { apiDataSource.getMenus() } returns fakeMenusResponse
            repository.getMenus().map {
                delay(100)
                it
            }.test {
                delay(201)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                assertEquals(data.payload?.size, 2)
                assertEquals(data.payload?.get(0)?.name, "bakwan")
                coVerify { apiDataSource.getMenus() }
            }
        }
    }

    @Test
    fun `get menu, result error`() {
        runTest {
            coEvery { apiDataSource.getMenus(any()) } throws IllegalStateException()
            repository.getMenus("bakwan").map {
                delay(100)
                it
            }.test {
                delay(201)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify { apiDataSource.getMenus(any()) }
            }
        }
    }

    @Test
    fun `get menu, result empty`() {
        val fakeMenusResponse = MenusResponse(
            code = 11,
            data = emptyList(),
            message = "message",
            status = true
        )
        runTest {
            coEvery { apiDataSource.getMenus(any()) } returns fakeMenusResponse
            repository.getMenus("makanan").map {
                delay(100)
                it
            }.test {
                delay(201)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Empty)
                coVerify { apiDataSource.getMenus(any()) }
            }
        }
    }
}
