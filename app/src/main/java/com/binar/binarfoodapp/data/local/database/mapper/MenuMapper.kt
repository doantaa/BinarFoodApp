package com.binar.binarfoodapp.data.local.database.mapper

import com.binar.binarfoodapp.data.local.database.entity.MenuEntity
import com.binar.binarfoodapp.model.Menu


fun MenuEntity?.toMenu() = Menu(
    id = this?.id ?: 0,
    name = this?.name.orEmpty(),
    price = this?.price ?: 0,
    imageUrl = this?.imageUrl.orEmpty(),
    description = this?.description.orEmpty()

)

fun Menu?.toMenuEntity() = MenuEntity(
    name = this?.name.orEmpty(),
    price = this?.price ?: 0,
    imageUrl = this?.imageUrl.orEmpty(),
    description = this?.description.orEmpty()

).apply {
    this@toMenuEntity?.id?.let {
        this.id = this@toMenuEntity.id
    }
}

fun List<MenuEntity?>.toMenuList() = this.map { it.toMenu() }
fun List<Menu?>.toMenuEntity() = this.map { val toMenuEntity = it.toMenuEntity()
    toMenuEntity
}
