package com.binar.binarfoodapp.presentation.detail

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binar.binarfoodapp.data.repository.CartRepository
import com.binar.binarfoodapp.model.Menu
import com.binar.binarfoodapp.utils.ResultWrapper
import kotlinx.coroutines.launch

class DetailViewModel(
    private val extras: Bundle?,
    private val cartRepository: CartRepository
) : ViewModel() {
    val menu = extras?.getParcelable<Menu>(DetailActivity.EXTRA_FOOD)

    val priceLiveData = MutableLiveData<Int>().apply {
        postValue(0)
    }

    val menuCountLiveData = MutableLiveData<Int>().apply {
        postValue(1)
    }

    private val _addToCartResult = MutableLiveData<ResultWrapper<Boolean>>()
    val addToCartResult: LiveData<ResultWrapper<Boolean>>
        get() = _addToCartResult


    fun add() {
        val count = (menuCountLiveData.value ?: 0) + 1
        val menuPrice = menu?.price ?: 0
        menuCountLiveData.postValue(count)
        priceLiveData.postValue(count * menuPrice)
    }

    fun minus() {
        if ((menuCountLiveData.value ?: 0) > 1) {
            val count = (menuCountLiveData.value ?: 0) - 1
            val menuPrice = menu?.price ?: 0
            menuCountLiveData.postValue(count)
            priceLiveData.postValue(count * menuPrice)
        }

    }

    fun addToCart() {
        viewModelScope.launch {
            val quantity =
                if ((menuCountLiveData.value ?: 0) <= 0) 1 else menuCountLiveData.value ?: 0
            menu?.let {
                cartRepository.createCart(it, quantity).collect {
                    _addToCartResult.postValue(it)
                }
            }
        }
    }

}

