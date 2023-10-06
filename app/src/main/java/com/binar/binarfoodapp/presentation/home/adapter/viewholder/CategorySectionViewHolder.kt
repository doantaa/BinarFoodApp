package com.binar.binarfoodapp.presentation.home.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.binar.binarfoodapp.core.ViewHolderBinder
import com.binar.binarfoodapp.databinding.ItemCategoryBinding
import com.binar.binarfoodapp.model.Category

class CategorySectionViewHolder(
    private val binding: ItemCategoryBinding,
    onItemClick: (Category) -> Unit
): RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Category> {
    override fun bind(item: Category) {
        binding.ivCategoryImage.load(item.imageUrl)
        binding.tvCategoryName.text = item.name
    }
}
