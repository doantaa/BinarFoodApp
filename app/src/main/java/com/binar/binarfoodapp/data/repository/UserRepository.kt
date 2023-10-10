package com.binar.binarfoodapp.data.repository

import com.binar.binarfoodapp.data.network.firebase.auth.FirebaseAuthDataSource
import com.binar.binarfoodapp.model.User
import com.binar.binarfoodapp.model.toUser
import com.binar.binarfoodapp.utils.ResultWrapper
import com.binar.binarfoodapp.utils.proceedFlow
import kotlinx.coroutines.flow.Flow


interface UserRepository {
    fun isLoggedIn(): Boolean
    fun getCurrentUser(): User?
    fun doLogout(): Boolean
    suspend fun doRegister(
        fullName: String, email: String, password: String
    ): Flow<ResultWrapper<Boolean>>

    suspend fun doLogin(email: String, password: String): Flow<ResultWrapper<Boolean>>
}

class UserRepositoryImpl(private val dataSource: FirebaseAuthDataSource) : UserRepository {
    override fun isLoggedIn(): Boolean {
        return dataSource.isLoggedIn()
    }

    override fun getCurrentUser(): User? {
        return dataSource.getCurrentUser().toUser()
    }

    override fun doLogout(): Boolean {
        return dataSource.doLogout()
    }

    override suspend fun doRegister(
        fullName: String, email: String, password: String
    ): Flow<ResultWrapper<Boolean>> {
        return proceedFlow { dataSource.doRegister(fullName, email, password) }
    }

    override suspend fun doLogin(email: String, password: String): Flow<ResultWrapper<Boolean>> {
        return proceedFlow { dataSource.doLogin(email, password) }
    }
}