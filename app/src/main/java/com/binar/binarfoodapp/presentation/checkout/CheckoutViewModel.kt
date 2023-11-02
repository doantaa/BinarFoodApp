package com.binar.binarfoodapp.presentation.checkout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.binar.binarfoodapp.data.repository.CartRepository
import com.binar.binarfoodapp.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CheckoutViewModel(private val cartRepository: CartRepository) : ViewModel() {
    val cartList = cartRepository.getCartData().asLiveData(Dispatchers.IO)

    fun cleanCart() {
        viewModelScope.launch(Dispatchers.IO) {
            cartRepository.cleanCart()
        }
    }

    private val _checkoutResult = MutableLiveData<ResultWrapper<Boolean>>()
    val checkoutResult: LiveData<ResultWrapper<Boolean>>
        get() = _checkoutResult

    fun createOrder() {
        viewModelScope.launch(Dispatchers.IO) {
            val carts = cartList.value?.payload?.first ?: return@launch // tidak membuat order ketika listCartnya null
            cartRepository.order(carts).collect {
                _checkoutResult.postValue(it)
            }
        }
    }
}
