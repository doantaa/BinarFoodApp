package com.binar.binarfoodapp.presentation.home.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.binar.binarfoodapp.ViewHolderBinder
import com.binar.binarfoodapp.databinding.ItemGridFoodBinding
import com.binar.binarfoodapp.databinding.ItemLinearFoodBinding
import com.binar.binarfoodapp.model.Food

class GridFoodItemListViewHolder (
    private val binding: ItemGridFoodBinding,
    private val onItemClick: (Food) -> Unit
): RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Food>{
     override fun bind(item: Food){
        binding.root.setOnClickListener{
            onItemClick.invoke(item)
        }

        binding.ivMenuImage.load(item.imageUrl)
        binding.tvMenuName.text = item.name
        binding.tvMenuPrice.text = "Rp ${item.price}"
    }
}

class LinearFoodItemListViewHolder (
    private val binding: ItemLinearFoodBinding,
    private val onItemClick: (Food) -> Unit
): RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Food>{
    override fun bind(item: Food){
        binding.root.setOnClickListener{
            onItemClick.invoke(item)
        }

        binding.ivMenuImage.load(item.imageUrl)
        binding.tvMenuName.text = item.name
        binding.tvMenuPrice.text = "Rp ${item.price}"
    }
}