package com.binar.binarfoodapp.presentation.home.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.binar.binarfoodapp.core.ViewHolderBinder
import com.binar.binarfoodapp.databinding.ItemGridFoodBinding
import com.binar.binarfoodapp.databinding.ItemLinearFoodBinding
import com.binar.binarfoodapp.model.Menu
import com.binar.binarfoodapp.utils.toCurrencyFormat

class GridFoodItemListViewHolder(
    private val binding: ItemGridFoodBinding,
    private val onItemClick: (Menu) -> Unit
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Menu> {
    override fun bind(item: Menu) {
        binding.root.setOnClickListener {
            onItemClick.invoke(item)
        }

        binding.ivMenuImage.load(item.imageUrl) {
            crossfade(true)
        }
        binding.tvMenuName.text = item.name
        binding.tvMenuPrice.text = item.price.toCurrencyFormat()
    }
}

class LinearFoodItemListViewHolder(
    private val binding: ItemLinearFoodBinding,
    private val onItemClick: (Menu) -> Unit
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Menu> {
    override fun bind(item: Menu) {
        binding.root.setOnClickListener {
            onItemClick.invoke(item)
        }

        binding.ivMenuImage.load(item.imageUrl)
        binding.tvMenuName.text = item.name
        binding.tvMenuPrice.text = item.price.toCurrencyFormat()
    }
}
