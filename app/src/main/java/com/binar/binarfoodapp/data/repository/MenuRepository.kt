package com.binar.binarfoodapp.data.repository

import com.binar.binarfoodapp.data.dummy.DummyCategoryDataSource
import com.binar.binarfoodapp.data.dummy.DummyMenuDataSourceImpl
import com.binar.binarfoodapp.data.local.database.datasource.MenuDataSource
import com.binar.binarfoodapp.data.local.database.mapper.toMenuList
import com.binar.binarfoodapp.model.Category
import com.binar.binarfoodapp.model.Menu
import com.binar.binarfoodapp.utils.ResultWrapper
import com.binar.binarfoodapp.utils.proceed
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

interface MenuRepository {
    fun getCategories():List<Category>
    fun getMenus(): Flow<ResultWrapper<List<Menu>>>
}

class MenuRepositoryImpl(
    private val menuDataSource: MenuDataSource,
    private val dummyCategoryDataSource: DummyCategoryDataSource
): MenuRepository{
    override fun getCategories(): List<Category> {
        return dummyCategoryDataSource.getCategory()
    }

    override fun getMenus(): Flow<ResultWrapper<List<Menu>>> {
        return menuDataSource.getAllMenu().map {
            proceed { it.toMenuList() }
        }.onStart {
            emit(ResultWrapper.Loading())
            delay(2000)
        }
    }

}