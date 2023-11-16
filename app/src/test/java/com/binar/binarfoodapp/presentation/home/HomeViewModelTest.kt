package com.binar.binarfoodapp.presentation.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.binar.binarfoodapp.data.local.datastore.UserPreferenceDataSource
import com.binar.binarfoodapp.data.repository.MenuRepository
import com.binar.binarfoodapp.data.repository.UserRepository
import com.binar.binarfoodapp.model.User
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

class HomeViewModelTest {

    @get:Rule
    val testRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    private lateinit var menuRepository: MenuRepository

    @MockK
    private lateinit var userPreferences: UserPreferenceDataSource

    @MockK
    private lateinit var userRepository: UserRepository

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        // menu
        coEvery { menuRepository.getMenus(any()) } returns flow {
            emit(
                ResultWrapper.Success(
                    listOf(
                        mockk(relaxed = true),
                        mockk(relaxed = true),
                        mockk(relaxed = true)

                    )
                )
            )
        }

        // category
        coEvery { menuRepository.getCategories() } returns flow {
            emit(
                ResultWrapper.Success(
                    listOf(
                        mockk(relaxed = true),
                        mockk(relaxed = true),
                        mockk(relaxed = true),
                        mockk(relaxed = true)

                    )
                )
            )
        }

        coEvery { userRepository.getCurrentUser() } returns User("fullname", "email")

        viewModel = spyk(
            HomeViewModel(
                repo = menuRepository,
                userRepository = userRepository,
                userPreferenceDataSource = userPreferences
            )
        )
    }

    @Test
    fun `get menu live data`() {
        viewModel.getMenus()
        val result = viewModel.menus.getOrAwaitValue()
        assertEquals(result.payload?.size, 3)
        coVerify { menuRepository.getMenus() }
    }

    @Test
    fun `get category live data`() {
        val result = viewModel.categories.getOrAwaitValue()
        println(result.payload)
        assertEquals(result.payload?.size, 4)
    }

    @Test
    fun `get current user`() {
        val result = viewModel.getUserData()
        assertTrue(result is User)
        assertEquals(result?.email, "email")
        coVerify { userRepository.getCurrentUser() }
    }

    @Test
    fun `get user list view live data`() {
        coEvery { userPreferences.getUserListViewModePrefFlow() } returns flow {
            emit(true)
        }
        val result = viewModel.getUserListViewLiveData().getOrAwaitValue()
        assertEquals(result, true)
        coVerify { userPreferences.getUserListViewModePrefFlow() }
        println(result)
    }
}
