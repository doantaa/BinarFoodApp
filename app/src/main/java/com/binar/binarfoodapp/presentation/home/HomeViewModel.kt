package com.binar.binarfoodapp.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.binar.binarfoodapp.data.repository.MenuRepository
import com.binar.binarfoodapp.model.Category
import com.binar.binarfoodapp.model.Menu
import com.binar.binarfoodapp.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers

class HomeViewModel(private val repo: MenuRepository) : ViewModel() {
    val menuData: LiveData<ResultWrapper<List<Menu>>>
        get() = repo.getMenus().asLiveData(Dispatchers.Main)

}