package com.binar.binarfoodapp.presentation.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.binar.binarfoodapp.data.repository.CartRepository
import kotlinx.coroutines.Dispatchers

class CartViewModel(
    private val repo: CartRepository
) : ViewModel(){
    val cartList = repo.getCartData().asLiveData(Dispatchers.IO)

}