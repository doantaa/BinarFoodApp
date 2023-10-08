package com.binar.binarfoodapp.presentation.cart.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.binar.binarfoodapp.core.ViewHolderBinder
import com.binar.binarfoodapp.databinding.ItemCartBinding
import com.binar.binarfoodapp.model.Menu
import com.binar.binarfoodapp.utils.toCurrencyFormat


class CartItemListViewHolder(
    private val binding: ItemCartBinding
): RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Menu>{
    override fun bind(item: Menu) {
        binding.tvMenuName.text = item.name
        binding.tvCountPrice.text = item.price.toCurrencyFormat()
        binding.ivMenuImage.load(item.imageUrl)
    }

}
