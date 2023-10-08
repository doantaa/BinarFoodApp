package com.binar.binarfoodapp.presentation.detail

import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.binar.binarfoodapp.data.repository.CartRepository
import com.binar.binarfoodapp.data.repository.MenuRepository

class DetailViewModel (
    private val extras: Bundle?,
    private val cartRepository: CartRepository
): ViewModel() {

}

