package com.binar.binarfoodapp.data.network.api.datasource

import com.binar.binarfoodapp.data.network.api.model.category.CategoriesResponse
import com.binar.binarfoodapp.data.network.api.model.menu.MenusResponse
import com.binar.binarfoodapp.data.network.api.model.order.OrderRequest
import com.binar.binarfoodapp.data.network.api.model.order.OrderResponse
import com.binar.binarfoodapp.data.network.api.service.RestaurantService

interface RestaurantDataSource {
    suspend fun getMenus(category: String? = null) : MenusResponse
    suspend fun getCategories() : CategoriesResponse
    suspend fun createOrder(orderRequest: OrderRequest) : OrderResponse

}


class RestaurantApiDataSource(
    private val service: RestaurantService
): RestaurantDataSource {
    override suspend fun getMenus(category: String?): MenusResponse {
        return service.getMenus(category)
    }

    override suspend fun getCategories(): CategoriesResponse {
        return service.getCategories()
    }

    override suspend fun createOrder(orderRequest: OrderRequest): OrderResponse {
        return service.createOrder(orderRequest)
    }
}