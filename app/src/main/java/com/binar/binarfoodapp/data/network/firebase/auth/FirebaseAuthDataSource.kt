package com.binar.binarfoodapp.data.network.firebase.auth

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

interface FirebaseAuthDataSource {
    fun isLoggedIn() : Boolean
    fun getCurrentUser(): FirebaseUser?
    fun doLogout(): Boolean
    @Throws(exceptionClasses = [Exception::class])
    suspend fun doRegister(fullName: String, email: String, password: String) : Boolean

    @Throws(exceptionClasses = [Exception::class])
    suspend fun doLogin(email: String, password: String) : Boolean
    suspend fun updateProfile(
        fullName: String? = null,
        photoUri: Uri? = null
    ): Boolean
}


class FirebaseAuthDataSourceImpl(private val firebaseAuth: FirebaseAuth): FirebaseAuthDataSource {
    override fun isLoggedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }

    override fun getCurrentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    override fun doLogout(): Boolean {
        Firebase.auth.signOut()
        return true
    }

    @Throws(exceptionClasses = [Exception::class])
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

    @Throws(exceptionClasses = [Exception::class])
    override suspend fun doLogin(email: String, password: String): Boolean {
        val loginResult = firebaseAuth.signInWithEmailAndPassword(email,password).await()
        return loginResult.user != null
    }

    override suspend fun updateProfile(fullName: String?, photoUri: Uri?): Boolean {
        getCurrentUser()?.updateProfile(
            userProfileChangeRequest {
                fullName?.let { displayName = fullName }
                photoUri?.let { this.photoUri = it }
            }
        )?.await()
        return true
    }

}