package com.binar.binarfoodapp.data.repository

import com.binar.binarfoodapp.data.local.database.datasource.CartDataSource
import com.binar.binarfoodapp.data.local.database.entity.CartEntity
import com.binar.binarfoodapp.data.local.database.mapper.toCartEntity
import com.binar.binarfoodapp.data.local.database.mapper.toCartList
import com.binar.binarfoodapp.data.network.api.datasource.RestaurantApiDataSource
import com.binar.binarfoodapp.data.network.api.model.order.OrderItemRequest
import com.binar.binarfoodapp.data.network.api.model.order.OrderRequest
import com.binar.binarfoodapp.model.Cart
import com.binar.binarfoodapp.model.Menu
import com.binar.binarfoodapp.utils.ResultWrapper
import com.binar.binarfoodapp.utils.proceed
import com.binar.binarfoodapp.utils.proceedFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

interface CartRepository {
    fun getCartData(): Flow<ResultWrapper<Pair<List<Cart>, Int>>>
    suspend fun createCart(menu: Menu, totalQuantity: Int): Flow<ResultWrapper<Boolean>>
    suspend fun decreaseCart(item: Cart): Flow<ResultWrapper<Boolean>>
    suspend fun increaseCart(item: Cart): Flow<ResultWrapper<Boolean>>
    suspend fun setOrderNotes(item: Cart): Flow<ResultWrapper<Boolean>>
    suspend fun deleteCart(item: Cart): Flow<ResultWrapper<Boolean>>
    suspend fun cleanCart()
    suspend fun order(carts: List<Cart>): Flow<ResultWrapper<Boolean>>
}

class CartRepositoryImpl(
    private val dataSource: CartDataSource,
    private val restaurantApiDataSource: RestaurantApiDataSource
) : CartRepository {
    override fun getCartData(): Flow<ResultWrapper<Pair<List<Cart>, Int>>> {
        return dataSource.getAllCarts()
            .map {
                proceed {
                    val result = it.toCartList()
                    val totalPrice = result.sumOf {
                        val pricePerItem = it.menuPrice
                        val itemQuantity = it.itemQuantity
                        pricePerItem * itemQuantity
                    }
                    Pair(result, totalPrice)
                }
            }.map {
                if (it.payload?.first?.isEmpty() == true)
                    ResultWrapper.Empty(it.payload)
                else
                    it
            }
            .onStart {
                emit(ResultWrapper.Loading())
            }
    }

    override suspend fun createCart(menu: Menu, totalQuantity: Int): Flow<ResultWrapper<Boolean>> {
        return menu.name.let {
            proceedFlow {
                val affectedRow = dataSource.insertCart(
                    CartEntity(
                        itemQuantity = totalQuantity,
                        menuName = menu.name,
                        menuImgUrl = menu.imageUrl,
                        menuPrice = menu.price
                    )
                )
                affectedRow > 0
            }
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
        return proceedFlow { dataSource.deleteCart(item.toCartEntity()) > 0 }
    }

    override suspend fun cleanCart() {
        proceed { dataSource.deleteAllCart() }
    }

    override suspend fun order(carts: List<Cart>): Flow<ResultWrapper<Boolean>> {
        return proceedFlow {
            val orderItems = carts.map {
                OrderItemRequest(it.menuName, it.itemQuantity, it.orderNotes, it.menuPrice)
            }
            val orderRequest = OrderRequest(
                username = "username",//username nanti diganti
                total = orderItems.map { it.qty?.times((it.price ?: 0)) ?: 0 }.sum(),
                orders = orderItems
            )
            restaurantApiDataSource.createOrder(orderRequest).status == true
        }
    }
}