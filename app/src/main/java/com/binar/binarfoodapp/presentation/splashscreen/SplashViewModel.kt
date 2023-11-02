package com.binar.binarfoodapp.presentation.splashscreen

import androidx.lifecycle.ViewModel
import com.binar.binarfoodapp.data.repository.UserRepository

class SplashViewModel(
    private val repository: UserRepository
) : ViewModel() {
    fun isUserLoggedIn() = repository.isLoggedIn()
}
