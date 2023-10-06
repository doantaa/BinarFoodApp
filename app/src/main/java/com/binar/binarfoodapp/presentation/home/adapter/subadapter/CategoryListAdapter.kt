package com.binar.binarfoodapp.presentation.home.adapter.subadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.binar.binarfoodapp.core.ViewHolderBinder
import com.binar.binarfoodapp.databinding.ItemCategoryBinding
import com.binar.binarfoodapp.model.Category
import com.binar.binarfoodapp.presentation.home.adapter.viewholder.CategorySectionViewHolder

class CategoryListAdapter(
    private val onItemClick: (Category) -> Unit
) : RecyclerView.Adapter<CategorySectionViewHolder>() {

    private val differ = AsyncListDiffer(this, object: DiffUtil.ItemCallback<Category>(){
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    })


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategorySectionViewHolder {
        return CategorySectionViewHolder(
            binding = ItemCategoryBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), onItemClick
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: CategorySectionViewHolder, position: Int) {
        (holder as ViewHolderBinder<Category>).bind(differ.currentList[position])
    }

    fun setData(categoryData: List<Category>){
        differ.submitList(categoryData)
    }

    fun refreshList(){
        notifyItemRangeChanged(0, differ.currentList.size)
    }
}