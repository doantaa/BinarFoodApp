package com.binar.binarfoodapp.data.repository

import com.binar.binarfoodapp.data.network.api.datasource.RestaurantDataSource
import com.binar.binarfoodapp.data.network.api.model.category.toCategoryList
import com.binar.binarfoodapp.data.network.api.model.menu.toMenuList
import com.binar.binarfoodapp.model.Category
import com.binar.binarfoodapp.model.Menu
import com.binar.binarfoodapp.utils.ResultWrapper
import com.binar.binarfoodapp.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface MenuRepository {
    fun getCategories(): Flow<ResultWrapper<List<Category>>>
    fun getMenus(category: String? = null): Flow<ResultWrapper<List<Menu>>>
}

class MenuRepositoryImpl(
    private val apiDataSource: RestaurantDataSource
) : MenuRepository {
    override fun getCategories(): Flow<ResultWrapper<List<Category>>> {
        return proceedFlow {
            apiDataSource.getCategories().data?.toCategoryList() ?: emptyList()
        }
    }

    override fun getMenus(category: String?): Flow<ResultWrapper<List<Menu>>> {
        return proceedFlow {
            apiDataSource.getMenus(category).data?.toMenuList() ?: emptyList()
        }
    }
}
