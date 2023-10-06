package com.binar.binarfoodapp.presentation.home.adapter.subadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.binar.binarfoodapp.core.ViewHolderBinder
import com.binar.binarfoodapp.databinding.ItemGridFoodBinding
import com.binar.binarfoodapp.databinding.ItemLinearFoodBinding
import com.binar.binarfoodapp.model.Food
import com.binar.binarfoodapp.presentation.home.adapter.viewholder.GridFoodItemListViewHolder
import com.binar.binarfoodapp.presentation.home.adapter.viewholder.LinearFoodItemListViewHolder

class FoodListAdapter(
    var adapterLayoutMode: AdapterLayoutMode,
    private val onItemClick: (Food) -> Unit
) : RecyclerView.Adapter<ViewHolder>() {

    private val differ = AsyncListDiffer(this, object: DiffUtil.ItemCallback<Food>(){
        override fun areItemsTheSame(oldItem: Food, newItem: Food): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Food, newItem: Food): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    })
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            AdapterLayoutMode.GRID.ordinal -> {
                GridFoodItemListViewHolder(
                    binding = ItemGridFoodBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    ), onItemClick
                )
            }
            else -> {
                LinearFoodItemListViewHolder(
                    binding = ItemLinearFoodBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    ), onItemClick
                )
            }
        }
    }


    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder as ViewHolderBinder<Food>).bind(differ.currentList[position])
    }

    override fun getItemViewType(position: Int): Int {
        return adapterLayoutMode.ordinal
    }

    fun setData(foodData: List<Food>) {
        differ.submitList(foodData)
    }

    fun refreshList() {
        notifyItemRangeChanged(0, differ.currentList.size)
    }


}