package com.binar.binarfoodapp.utils

import android.icu.text.NumberFormat
import android.icu.util.Currency

fun Int.toCurrencyFormat(): String {
    val price = NumberFormat.getCurrencyInstance()
    price.currency = Currency.getInstance("IDR")
    price.maximumFractionDigits = 0

    return price.format(this)
}
