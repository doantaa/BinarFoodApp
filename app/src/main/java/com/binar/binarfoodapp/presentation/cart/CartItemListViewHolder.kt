package com.binar.binarfoodapp.presentation.cart

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.binar.binarfoodapp.R
import com.binar.binarfoodapp.core.ViewHolderBinder
import com.binar.binarfoodapp.databinding.ItemCartBinding
import com.binar.binarfoodapp.model.Food
import com.binar.binarfoodapp.utils.toCurrencyFormat


class CartItemListViewHolder(
    private val binding: ItemCartBinding
): RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Food>{
    override fun bind(item: Food) {
        binding.tvMenuName.text = item.name
        binding.tvCountPrice.text = item.price.toCurrencyFormat()
        binding.ivMenuImage.load(item.imageUrl)
    }

}
