package com.binar.binarfoodapp.data.local.database.datasource

import com.binar.binarfoodapp.data.local.database.dao.CartDao
import com.binar.binarfoodapp.data.local.database.entity.CartEntity
import com.binar.binarfoodapp.data.local.database.relation.CartMenuRelation
import kotlinx.coroutines.flow.Flow

interface CartDataSource {
    suspend fun insertCart(cart: CartEntity)
    suspend fun insertCarts(carts: List<CartEntity>)
    fun getAllCarts(): Flow<List<CartEntity>>
    fun getCartById(cartId: Int): Flow<CartEntity>
    suspend fun updateCart(cart: CartEntity): Int
    suspend fun deleteCart(cart: CartEntity): Int
}

class CartDataSourceImpl(
    private val dao: CartDao
) : CartDataSource {
    override suspend fun insertCart(cart: CartEntity) {
        dao.insertCart(cart)
    }

    override suspend fun insertCarts(carts: List<CartEntity>) {
        dao.insertCarts(carts)
    }

    override fun getAllCarts(): Flow<List<CartEntity>> {
        return dao.getAllCarts()
    }

    override fun getCartById(cartId: Int): Flow<CartEntity> {
        return dao.getCartById(cartId)
    }

    override suspend fun updateCart(cart: CartEntity): Int {
        return dao.updateCart(cart)
    }

    override suspend fun deleteCart(cart: CartEntity): Int {
        return dao.deleteCart(cart)
    }

}