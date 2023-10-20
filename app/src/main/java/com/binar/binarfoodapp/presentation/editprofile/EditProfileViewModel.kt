package com.binar.binarfoodapp.presentation.editprofile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binar.binarfoodapp.data.repository.UserRepository
import com.binar.binarfoodapp.utils.ResultWrapper
import kotlinx.coroutines.launch

class EditProfileViewModel(
    private val repository: UserRepository
) : ViewModel() {
    fun getCurrentUser() = repository.getCurrentUser()

    private val _updateProfileResult = MutableLiveData<ResultWrapper<Boolean>>()
    val updateProfileResult: LiveData<ResultWrapper<Boolean>>
        get() = _updateProfileResult

    fun updateProfile(fullName: String){
        viewModelScope.launch {
            repository.updateProfile(fullName).collect() { result ->
                _updateProfileResult.postValue(result)
            }
        }
    }
}