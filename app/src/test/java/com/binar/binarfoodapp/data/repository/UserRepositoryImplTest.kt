package com.binar.binarfoodapp.data.repository

import app.cash.turbine.test
import com.binar.binarfoodapp.data.network.firebase.auth.FirebaseAuthDataSource
import com.binar.binarfoodapp.utils.ResultWrapper
import com.google.firebase.auth.FirebaseUser
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class UserRepositoryImplTest {
    @MockK
    lateinit var firebaseAuthDataSource: FirebaseAuthDataSource

    private lateinit var userRepo: UserRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        userRepo = UserRepositoryImpl(firebaseAuthDataSource)
    }

    @Test
    fun `doLogin, result loading`() {
        coEvery { firebaseAuthDataSource.doLogin(any(), any()) } returns true
        runTest {
            userRepo.doLogin("email", "password").map {
                delay(100)
                it
            }.test {
                delay(101)
                val data = expectMostRecentItem()
                println(data)
                TestCase.assertTrue(data is ResultWrapper.Loading)
                coVerify { firebaseAuthDataSource.doLogin(any(), any()) }
            }
        }
    }

    @Test
    fun `doLogin, result success`() {
        coEvery { firebaseAuthDataSource.doLogin(any(), any()) } returns true
        runTest {
            userRepo.doLogin("email", "password").map {
                delay(100)
                it
            }.test {
                delay(201)
                val data = expectMostRecentItem()
                println(data)
                TestCase.assertTrue(data is ResultWrapper.Success)
                coVerify { firebaseAuthDataSource.doLogin(any(), any()) }
            }
        }
    }

    @Test
    fun `doLogin, result error`() {
        coEvery { firebaseAuthDataSource.doLogin(any(), any()) } throws IllegalStateException()
        runTest {
            userRepo.doLogin("email", "password").map {
                delay(100)
                it
            }.test {
                delay(201)
                val data = expectMostRecentItem()
                println(data)
                TestCase.assertTrue(data is ResultWrapper.Error)
                coVerify { firebaseAuthDataSource.doLogin(any(), any()) }
            }
        }
    }

    @Test
    fun `doRegister, result loading`() {
        coEvery { firebaseAuthDataSource.doRegister(any(), any(), any()) } returns true
        runTest {
            userRepo.doRegister("name", "email", "password").map {
                delay(100)
                it
            }.test {
                delay(101)
                val data = expectMostRecentItem()
                println(data)
                TestCase.assertTrue(data is ResultWrapper.Loading)
                coVerify { firebaseAuthDataSource.doRegister(any(), any(), any()) }
            }
        }
    }

    @Test
    fun `doRegister, result success`() {
        coEvery { firebaseAuthDataSource.doRegister(any(), any(), any()) } returns true
        runTest {
            userRepo.doRegister("name", "email", "password").map {
                delay(100)
                it
            }.test {
                delay(201)
                val data = expectMostRecentItem()
                println(data)
                TestCase.assertTrue(data is ResultWrapper.Success)
                coVerify { firebaseAuthDataSource.doRegister(any(), any(), any()) }
            }
        }
    }

    @Test
    fun `doRegister, result error`() {
        coEvery { firebaseAuthDataSource.doRegister(any(), any(), any()) } throws IllegalStateException()
        runTest {
            userRepo.doRegister("name", "email", "password").map {
                delay(100)
                it
            }.test {
                delay(201)
                val data = expectMostRecentItem()
                println(data)
                TestCase.assertTrue(data is ResultWrapper.Error)
                coVerify { firebaseAuthDataSource.doRegister(any(), any(), any()) }
            }
        }
    }

    @Test
    fun `update profile, result loading`() {
        coEvery { firebaseAuthDataSource.updateProfile(any(), any()) } returns true
        runTest {
            userRepo.updateProfile("email", null).map {
                delay(100)
                it
            }.test {
                delay(101)
                val data = expectMostRecentItem()
                println(data)
                TestCase.assertTrue(data is ResultWrapper.Loading)
                coVerify { firebaseAuthDataSource.updateProfile(any(), any()) }
            }
        }
    }

    @Test
    fun `update profile, result success`() {
        coEvery { firebaseAuthDataSource.updateProfile(any(), any()) } returns true
        runTest {
            userRepo.updateProfile("email", null).map {
                delay(100)
                it
            }.test {
                delay(201)
                val data = expectMostRecentItem()
                println(data)
                TestCase.assertTrue(data is ResultWrapper.Success)
                coVerify { firebaseAuthDataSource.updateProfile(any(), any()) }
            }
        }
    }

    @Test
    fun `update profile, result error`() {
        coEvery { firebaseAuthDataSource.updateProfile(any(), any()) } throws IllegalStateException()
        runTest {
            userRepo.updateProfile("email", null).map {
                delay(100)
                it
            }.test {
                delay(201)
                val data = expectMostRecentItem()
                println(data)
                TestCase.assertTrue(data is ResultWrapper.Error)
                coVerify { firebaseAuthDataSource.updateProfile(any(), any()) }
            }
        }
    }

    @Test
    fun `test logout`() {
        coEvery { firebaseAuthDataSource.doLogout() } returns true
        runTest {
            val result = userRepo.doLogout()
            assertEquals(result, true)
            coVerify { firebaseAuthDataSource.doLogout() }
        }
    }

    @Test
    fun `test is logged in`() {
        coEvery { firebaseAuthDataSource.isLoggedIn() } returns true
        runTest {
            val result = userRepo.isLoggedIn()
            assertEquals(result, true)
            coVerify { firebaseAuthDataSource.isLoggedIn() }
        }
    }

    @Test
    fun `get current user`() {
        val mockUser = mockk<FirebaseUser>()
        coEvery { firebaseAuthDataSource.getCurrentUser() } returns mockUser
        coEvery { firebaseAuthDataSource.getCurrentUser()?.displayName } returns "name"
        coEvery { firebaseAuthDataSource.getCurrentUser()?.email } returns "email"
        coEvery { firebaseAuthDataSource.getCurrentUser()?.photoUrl } returns mockk(relaxed = true)
        runTest {
            val name = userRepo.getCurrentUser()?.fullName
            val email = userRepo.getCurrentUser()?.email
            assertEquals(name, "name")
            assertEquals(email, "email")
            coVerify { firebaseAuthDataSource.getCurrentUser() }
        }
    }
}
