package com.binar.binarfoodapp.presentation.checkout

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.binar.binarfoodapp.core.ViewHolderBinder
import com.binar.binarfoodapp.databinding.ItemCartCheckoutSummaryBinding
import com.binar.binarfoodapp.model.Cart
import com.binar.binarfoodapp.presentation.adapter.viewholder.CheckoutSumarryListViewHolder

class CheckoutSummaryAdapter() : RecyclerView.Adapter<ViewHolder>() {

    private val differ = AsyncListDiffer(
        this,
        object : DiffUtil.ItemCallback<Cart>() {
            override fun areItemsTheSame(oldItem: Cart, newItem: Cart): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Cart, newItem: Cart): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }
        }
    )
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return CheckoutSumarryListViewHolder(
            ItemCartCheckoutSummaryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder as ViewHolderBinder<Cart>).bind(differ.currentList[position])
    }

    fun setData(cartData: List<Cart>) {
        differ.submitList(cartData)
    }
}
