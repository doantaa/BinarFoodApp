package com.binar.binarfoodapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class Food(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val price: Int,
    val imageUrl: String,
    val description: String
): Parcelable
