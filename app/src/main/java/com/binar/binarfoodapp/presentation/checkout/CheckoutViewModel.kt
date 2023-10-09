package com.binar.binarfoodapp.presentation.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.binar.binarfoodapp.data.repository.CartRepository
import kotlinx.coroutines.Dispatchers

class CheckoutViewModel(cartRepository: CartRepository) : ViewModel() {
    val cartList = cartRepository.getCartData().asLiveData(Dispatchers.IO)
}