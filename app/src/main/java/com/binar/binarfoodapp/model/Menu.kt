package com.binar.binarfoodapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class Menu(
    val id: Int? = null,
    val name: String,
    val price: Int,
    val imageUrl: String,
    val description: String
): Parcelable
