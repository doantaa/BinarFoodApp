package com.binar.binarfoodapp.data.local.database.mapper

import com.binar.binarfoodapp.data.local.database.entity.CartEntity
import com.binar.binarfoodapp.model.Cart

fun CartEntity?.toCart() = Cart(
    id = this?.id ?: 0,
    menuId = this?.menuId ?: 0,
    itemQuantity = this?.itemQuantity ?: 0,
    menuName = this?.menuName.orEmpty(),
    menuPrice = this?.menuPrice ?: 0,
    menuImgUrl = this?.menuImgUrl.orEmpty(),
    orderNotes = this?.orderNotes.orEmpty()
)

fun Cart.toCartEntity() = CartEntity(
    id = this.id ?: 0,
    menuId = this.menuId,
    itemQuantity = this.itemQuantity,
    menuName = this.menuName,
    menuPrice = this.menuPrice,
    menuImgUrl = this.menuImgUrl,
    orderNotes = this.orderNotes.orEmpty()
)

fun List<CartEntity?>.toCartList() = this.map { it.toCart() }
