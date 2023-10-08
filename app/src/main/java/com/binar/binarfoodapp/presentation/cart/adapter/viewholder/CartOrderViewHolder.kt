package com.binar.binarfoodapp.presentation.cart.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.binar.binarfoodapp.core.ViewHolderBinder
import com.binar.binarfoodapp.databinding.ItemCartBinding
import com.binar.binarfoodapp.model.CartMenu

class CartOrderViewHolder(
    private val binding: ItemCartBinding,
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<CartMenu> {
    override fun bind(item: CartMenu) {
        setCartData(item)
        setCartNotes(item)
    }

    private fun setCartData(item: CartMenu) {
        with(binding) {
        }
    }

    private fun setCartNotes(item: CartMenu) {
//        binding.tvNotes.text = item.cart.itemNotes
    }

}