package com.binar.binarfoodapp.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.binar.binarfoodapp.data.local.datastore.UserPreferenceDataSource
import com.binar.binarfoodapp.data.repository.MenuRepository
import com.binar.binarfoodapp.model.Menu
import com.binar.binarfoodapp.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repo: MenuRepository,
    private val userPreferenceDataSource: UserPreferenceDataSource
) : ViewModel() {
    val menuData: LiveData<ResultWrapper<List<Menu>>>
        get() = repo.getMenus().asLiveData(Dispatchers.Main)

    fun setUserListViewMode(isList: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            userPreferenceDataSource.setUserListViewModePreference(isList)
        }
    }

    fun getUserListViewLiveData() = userPreferenceDataSource
        .getUserListViewModePrefFlow()
        .asLiveData(Dispatchers.IO)
}