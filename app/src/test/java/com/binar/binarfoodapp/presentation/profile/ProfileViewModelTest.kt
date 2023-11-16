package com.binar.binarfoodapp.presentation.profile

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.binar.binarfoodapp.data.repository.UserRepository
import com.binar.binarfoodapp.model.User
import com.binar.binarfoodapp.tools.MainCoroutineRule
import com.binar.binarfoodapp.tools.getOrAwaitValue
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class ProfileViewModelTest {

    @get:Rule
    val testRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    private lateinit var repository: UserRepository

    private lateinit var viewModel: ProfileViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        viewModel = spyk(ProfileViewModel(repository))
        every { repository.getCurrentUser() } returns mockk(relaxed = true)
    }

    @Test
    fun `test logout`() {
        every { repository.doLogout() } returns true
        val result = viewModel.doLogout()
        assertEquals(result, true)
        verify { repository.doLogout() }
    }

    @Test
    fun `get current user live data`() {
        viewModel.getCurrentUser()
        val result = viewModel.userProfile.getOrAwaitValue()
        assertTrue(result is User)
        coVerify { repository.getCurrentUser() }
    }
}
