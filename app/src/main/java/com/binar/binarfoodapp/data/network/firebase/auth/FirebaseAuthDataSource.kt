package com.binar.binarfoodapp.data.network.firebase.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.userProfileChangeRequest
import kotlinx.coroutines.tasks.await

interface FirebaseAuthDataSource {
    fun isLoggedIn() : Boolean
    fun getCurrentUser(): FirebaseUser?
    fun doLogout(): Boolean
    suspend fun doRegister(fullName: String, email: String, password: String) : Boolean
    suspend fun doLogin(email: String, password: String) : Boolean
}


class FirebaseAuthDataSourceImpl(private val firebaseAuth: FirebaseAuth): FirebaseAuthDataSource {
    override fun isLoggedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }

    override fun getCurrentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    override fun doLogout(): Boolean {
        firebaseAuth.signOut()
        return true
    }

    override suspend fun doRegister(fullName: String, email: String, password: String): Boolean {
        val registerResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
        registerResult.user?.updateProfile(
            userProfileChangeRequest {
                displayName = fullName
//                photoUri = ""
            }
        )?.await()
        return registerResult.user != null
    }

    override suspend fun doLogin(email: String, password: String): Boolean {
        val loginResult = firebaseAuth.signInWithEmailAndPassword(email,password).await()
        return loginResult.user != null
    }

}