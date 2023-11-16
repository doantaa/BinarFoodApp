package com.binar.binarfoodapp.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.binar.binarfoodapp.data.local.datastore.UserPreferenceDataSource
import com.binar.binarfoodapp.data.repository.MenuRepository
import com.binar.binarfoodapp.data.repository.UserRepository
import com.binar.binarfoodapp.model.Menu
import com.binar.binarfoodapp.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repo: MenuRepository,
    private val userPreferenceDataSource: UserPreferenceDataSource,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _menus = MutableLiveData<ResultWrapper<List<Menu>>>()
    val menus: LiveData<ResultWrapper<List<Menu>>>
        get() = _menus

    val categories = repo.getCategories().asLiveData(Dispatchers.IO)

    fun getMenus(category: String? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getMenus(if (category == "all") null else category).collect() { result ->
                _menus.postValue(result)
            }
        }
    }

    fun setUserListViewMode(isList: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            userPreferenceDataSource.setUserListViewModePreference(isList)
        }
    }

    fun getUserListViewLiveData() = userPreferenceDataSource
        .getUserListViewModePrefFlow()
        .asLiveData(Dispatchers.IO)

    fun getUserData() = userRepository.getCurrentUser()
}
