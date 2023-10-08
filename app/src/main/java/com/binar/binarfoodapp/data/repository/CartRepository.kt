package com.binar.binarfoodapp.data.repository

import com.binar.binarfoodapp.data.local.database.datasource.CartDataSource
import com.binar.binarfoodapp.data.local.database.datasource.CartDataSourceImpl
import com.binar.binarfoodapp.data.local.database.entity.CartEntity
import com.binar.binarfoodapp.data.local.database.mapper.toCartEntity
import com.binar.binarfoodapp.data.local.database.mapper.toCartList
import com.binar.binarfoodapp.data.local.database.mapper.toCartMenuList
import com.binar.binarfoodapp.model.Cart
import com.binar.binarfoodapp.model.CartMenu
import com.binar.binarfoodapp.model.Menu
import com.binar.binarfoodapp.utils.ResultWrapper
import com.binar.binarfoodapp.utils.proceed
import com.binar.binarfoodapp.utils.proceedFlow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import java.lang.IllegalStateException

interface CartRepository {
    fun getCartData(): Flow<ResultWrapper<Pair<List<CartMenu>,Int>>>
    suspend fun createCart(menu: Menu, totalQuantity: Int) : Flow<ResultWrapper<Boolean>>
    suspend fun decreaseCart(item: Cart) : Flow<ResultWrapper<Boolean>>
    suspend fun increaseCart(item: Cart) : Flow<ResultWrapper<Boolean>>
    suspend fun setOrderNotes(item: Cart) : Flow<ResultWrapper<Boolean>>
    suspend fun deleteCart(item: Cart): Flow<ResultWrapper<Boolean>>
}

class CartRepositoryImpl(
    private val dataSource: CartDataSource
): CartRepository{
    override fun getCartData(): Flow<ResultWrapper<Pair<List<CartMenu>, Int>>> {
        return dataSource.getAllCarts()
            .map {
                proceed {
                    val result = it.toCartMenuList()
                    val totalPrice = result.sumOf{
                        val pricePerItem = it.menu.price
                        val itemQuantity = it.cart.itemQuantity
                        pricePerItem * itemQuantity
                    }
                    Pair(result,totalPrice)
                }
            }.map {
                if(it.payload?.first?.isEmpty() == true)
                    ResultWrapper.Empty(it.payload)
                else
                    it
            }
            .onStart {
                emit(ResultWrapper.Loading())
                delay(2000)
            }
    }

    override suspend fun createCart(menu: Menu, totalQuantity: Int): Flow<ResultWrapper<Boolean>> {
        return menu.id?.let { menuId ->
            proceedFlow {
                val affectedRow = dataSource.insertCart(
                    CartEntity(menuId = menuId, itemQuantity = totalQuantity)
                )
                affectedRow > 0
            }
        } ?: flow {
                emit(ResultWrapper.Error(IllegalStateException("Menu Id not found")))
            }
        }

    override suspend fun decreaseCart(item: Cart): Flow<ResultWrapper<Boolean>> {
        val modifiedCart = item.copy().apply {
            itemQuantity -= 1
        }
        return if (modifiedCart.itemQuantity <= 0) {
            proceedFlow { dataSource.deleteCart(modifiedCart.toCartEntity()) > 0 }
        } else {
            proceedFlow { dataSource.updateCart(modifiedCart.toCartEntity()) > 0 }
        }
    }

    override suspend fun increaseCart(item: Cart): Flow<ResultWrapper<Boolean>> {
        val modifiedCart = item.copy().apply {
            itemQuantity += 1
        }
        return proceedFlow { dataSource.updateCart(modifiedCart.toCartEntity()) > 0 }

    }

    override suspend fun setOrderNotes(item: Cart): Flow<ResultWrapper<Boolean>> {
        return proceedFlow { dataSource.updateCart(item.toCartEntity()) > 0 }
    }

    override suspend fun deleteCart(item: Cart): Flow<ResultWrapper<Boolean>> {
        return proceedFlow { dataSource.deleteCart(item.toCartEntity()) > 0}
    }

}