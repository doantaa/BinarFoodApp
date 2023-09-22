package com.binar.binarfoodapp.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.binar.binarfoodapp.databinding.ItemListFoodBinding
import com.binar.binarfoodapp.model.Food

class FoodListAdapter(private val onItemClick: (Food) -> Unit) : RecyclerView.Adapter<FoodItemListViewHolder>() {

    private val differ = AsyncListDiffer(this, object: DiffUtil.ItemCallback<Food>(){
        override fun areItemsTheSame(oldItem: Food, newItem: Food): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Food, newItem: Food): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    })
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodItemListViewHolder {
        return FoodItemListViewHolder(
            binding = ItemListFoodBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            onItemClick = onItemClick
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: FoodItemListViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    fun setData(foodData: List<Food>) {
        differ.submitList(foodData)
        notifyItemRangeChanged(0,foodData.size)
    }


}