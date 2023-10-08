package com.binar.binarfoodapp.data.local.database.mapper

import com.binar.binarfoodapp.data.local.database.entity.CartEntity
import com.binar.binarfoodapp.data.local.database.relation.CartMenuRelation
import com.binar.binarfoodapp.model.Cart
import com.binar.binarfoodapp.model.CartMenu


fun CartEntity?.toCart() = Cart(
    id = this?.id ?: 0,
    menuId = this?.menuId ?: 0,
    itemQuantity = this?.itemQuantity ?: 0,
    orderNotes = this?.orderNotes.orEmpty()
)


fun Cart.toCartEntity() = CartEntity(
    id = this?.id ?: 0,
    menuId = this?.menuId ?: 0,
    itemQuantity = this?.itemQuantity ?: 0,
    orderNotes = this?.orderNotes.orEmpty()
)


fun List<CartEntity?>.toCartList() = this.map { it.toCart() }

fun CartMenuRelation.toCartMenu() = CartMenu(
    cart = this.cart.toCart(),
    menu = this.menu.toMenu()
)

fun List<CartMenuRelation>.toCartMenuList() = this.map {it.toCartMenu()}