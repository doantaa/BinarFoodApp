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
import com.binar.binarfoodapp.model.Menu
import com.binar.binarfoodapp.presentation.home.adapter.viewholder.GridFoodItemListViewHolder
import com.binar.binarfoodapp.presentation.home.adapter.viewholder.LinearFoodItemListViewHolder

class FoodListAdapter(
    var adapterLayoutMode: AdapterLayoutMode,
    private val onItemClick: (Menu) -> Unit
) : RecyclerView.Adapter<ViewHolder>() {

    private val differ = AsyncListDiffer(this, object: DiffUtil.ItemCallback<Menu>(){
        override fun areItemsTheSame(oldItem: Menu, newItem: Menu): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Menu, newItem: Menu): Boolean {
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
        (holder as ViewHolderBinder<Menu>).bind(differ.currentList[position])
    }

    override fun getItemViewType(position: Int): Int {
        return adapterLayoutMode.ordinal
    }

    fun setData(menuData: List<Menu>) {
        differ.submitList(menuData)
    }

    fun refreshList() {
        notifyItemRangeChanged(0, differ.currentList.size)
    }


}