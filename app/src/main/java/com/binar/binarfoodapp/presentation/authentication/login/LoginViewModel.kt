package com.binar.binarfoodapp.presentation.authentication.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binar.binarfoodapp.data.repository.UserRepository
import com.binar.binarfoodapp.utils.ResultWrapper
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: UserRepository
) : ViewModel() {

    private val _loginResult = MutableLiveData<ResultWrapper<Boolean>>()
    val loginResult: LiveData<ResultWrapper<Boolean>>
        get() = _loginResult

    fun doLogin(email: String, password: String) {
        viewModelScope.launch {
            repository.doLogin(email, password).collect { result ->
                _loginResult.postValue(result)
            }
        }
    }
}
