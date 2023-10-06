package com.binar.binarfoodapp.data.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.binar.binarfoodapp.data.local.database.entity.MenuEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MenuDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMenus(menus: List<MenuEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMenu(menu: MenuEntity)


    @Query("SELECT * FROM MENU")
    fun getAllMenu(): Flow<List<MenuEntity>>

    @Query("SELECT * FROM MENU WHERE id == :menuId")
    fun getMenuById(menuId: Int) : Flow<MenuEntity>

    @Update
    suspend fun updateMenu(menu: MenuEntity): Int

    @Delete
    suspend fun deleteMenu(menu: MenuEntity): Int
}