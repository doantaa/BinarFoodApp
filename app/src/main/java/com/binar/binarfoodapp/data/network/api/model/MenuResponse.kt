package com.binar.binarfoodapp.data.network.api.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class MenuResponse(
    @SerializedName("code")
    val code: Int?,
    @SerializedName("data")
    val data: List<Data>?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Boolean?
)