package com.binar.binarfoodapp.model

data class Cart(
    var id: Int? = null,
    var menuId: Int = 0,
    val menuName: String,
    val menuPrice: Int,
    val menuImgUrl: String,
    var itemQuantity: Int = 0,
    var orderNotes: String? = null
)
