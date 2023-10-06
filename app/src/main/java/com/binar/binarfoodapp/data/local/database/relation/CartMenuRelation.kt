package com.binar.binarfoodapp.data.local.database.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.binar.binarfoodapp.data.local.database.entity.CartEntity
import com.binar.binarfoodapp.data.local.database.entity.MenuEntity

data class CartMenuRelation(
    @Embedded
    val cart: CartEntity,

    @Relation(parentColumn = "menu_id", entityColumn = "id")
    val menu: MenuEntity
)
