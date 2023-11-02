package com.binar.binarfoodapp.data.network.api.model.category

import androidx.annotation.Keep
import com.binar.binarfoodapp.model.Category
import com.google.gson.annotations.SerializedName

@Keep
data class CategoryResponse(
    @SerializedName("image_url")
    val imageUrl: String?,
    @SerializedName("nama")
    val name: String?
)

fun CategoryResponse.toCategory() = Category(
    name = this.name.orEmpty(),
    imageUrl = this.imageUrl.orEmpty()
)

fun Collection<CategoryResponse>.toCategoryList() = this.map { it.toCategory() }
