package com.binar.binarfoodapp.model

data class Cart(
    var id: Int = 0,
    var menuId: Int = 0,
    var itemQuantity: Int = 0,
    var orderNotes: String? = null
)