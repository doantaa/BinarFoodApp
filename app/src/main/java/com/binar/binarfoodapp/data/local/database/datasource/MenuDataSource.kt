package com.binar.binarfoodapp.data.local.database.datasource

import com.binar.binarfoodapp.data.local.database.dao.MenuDao
import com.binar.binarfoodapp.data.local.database.entity.MenuEntity
import kotlinx.coroutines.flow.Flow

interface MenuDataSource {
    suspend fun insertMenus(menus: List<MenuEntity>)
    suspend fun insertMenu(menu: MenuEntity)
    fun getAllMenu(): Flow<List<MenuEntity>>
    fun getMenuById(menuId: Int): Flow<MenuEntity>
    suspend fun updateMenu(menu: MenuEntity): Int
    suspend fun deleteMenu(menu: MenuEntity): Int
}

class MenuDataSourceImpl(
    private val dao: MenuDao
): MenuDataSource {
    override suspend fun insertMenus(menus: List<MenuEntity>) {
        dao.insertMenus(menus)
    }

    override suspend fun insertMenu(menu: MenuEntity) {
        dao.insertMenu(menu)
    }

    override fun getAllMenu(): Flow<List<MenuEntity>> {
        return dao.getAllMenu()
    }

    override fun getMenuById(menuId: Int): Flow<MenuEntity> {
        return dao.getMenuById(menuId)
    }

    override suspend fun updateMenu(menu: MenuEntity): Int {
        return dao.updateMenu(menu)
    }

    override suspend fun deleteMenu(menu: MenuEntity): Int {
        return dao.deleteMenu(menu)
    }

}
