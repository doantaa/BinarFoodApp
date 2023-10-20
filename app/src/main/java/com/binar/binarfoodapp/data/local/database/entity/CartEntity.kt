package com.binar.binarfoodapp.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "cart")
data class CartEntity (
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,

    @ColumnInfo("menu_id")
    var menuId : Int = 0,

    @ColumnInfo("menu_name")
    val menuName: String,

    @ColumnInfo("menu_price")
    val menuPrice: Int,

    @ColumnInfo("menu_img_url")
    val menuImgUrl: String,

    @ColumnInfo(name = "item_quantity")
    var itemQuantity: Int = 0,

    @ColumnInfo(name = "order_notes")
    var orderNotes: String? = null,
)