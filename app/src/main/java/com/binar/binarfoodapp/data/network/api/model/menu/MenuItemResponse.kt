package com.binar.binarfoodapp.data.network.api.model.menu

import androidx.annotation.Keep
import com.binar.binarfoodapp.model.Menu
import com.google.gson.annotations.SerializedName

@Keep
data class MenuItemResponse(
    @SerializedName("alamat_resto")
    val restaurantAddress: String?,
    @SerializedName("detail")
    val description: String?,
    @SerializedName("harga")
    val price: Int?,
    @SerializedName("harga_format")
    val formattedPrice: String?,
    @SerializedName("image_url")
    val imageUrl: String?,
    @SerializedName("nama")
    val name: String?
)

fun MenuItemResponse.toMenu() = Menu(
    name = this.name.orEmpty(),
    price = this.price ?: 0,
    imageUrl = this.imageUrl.orEmpty(),
    description = this.description.orEmpty()
)

fun Collection<MenuItemResponse>.toMenuList() = this.map { it.toMenu() }
