package com.binar.binarfoodapp.presentation.home

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.binar.binarfoodapp.databinding.ItemListFoodBinding
import com.binar.binarfoodapp.model.Food

class FoodItemListViewHolder (
    private val binding: ItemListFoodBinding,
    private val onItemClick: (Food) -> Unit
): RecyclerView.ViewHolder(binding.root){
    fun bind(item: Food){
        binding.root.setOnClickListener{
            onItemClick.invoke(item)
        }

        binding.ivMenuImage.load(item.imageUrl)
        binding.tvMenuName.text = item.name
        binding.tvMenuPrice.text = "Rp ${item.price}"
    }
}