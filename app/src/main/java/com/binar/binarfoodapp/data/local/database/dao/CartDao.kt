package com.binar.binarfoodapp.data.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.binar.binarfoodapp.data.local.database.entity.CartEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCart(cart: CartEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCarts(carts: List<CartEntity>)

    @Query("SELECT * FROM CART")
    fun getAllCarts(): Flow<List<CartEntity>>

    @Query("SELECT * FROM CART WHERE id == :cartId")
    fun getCartById(cartId: Int): Flow<CartEntity>

    @Update
    suspend fun updateCart(cart: CartEntity): Int

    @Delete
    suspend fun deleteCart(cart: CartEntity): Int

    @Query("DELETE FROM CART")
    fun deleteAllCart(): Int
}
