package com.binar.binarfoodapp.presentation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.binar.binarfoodapp.data.repository.UserRepository
import com.binar.binarfoodapp.model.User

class ProfileViewModel(private val repository: UserRepository) : ViewModel() {

    private val _userProfile = MutableLiveData<User?>()
    val userProfile: LiveData<User?>
        get() = _userProfile

    fun getCurrentUser() {
        val result = repository.getCurrentUser()
        _userProfile.postValue(result)
    }

    fun doLogout() = repository.doLogout()

}