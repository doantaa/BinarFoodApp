package com.binar.binarfoodapp.presentation.adapter.viewholder

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.binar.binarfoodapp.R
import com.binar.binarfoodapp.core.ViewHolderBinder
import com.binar.binarfoodapp.databinding.ItemCartCheckoutBinding
import com.binar.binarfoodapp.databinding.ItemCartCheckoutSummaryBinding
import com.binar.binarfoodapp.model.CartMenu
import com.binar.binarfoodapp.utils.toCurrencyFormat

class CheckoutListViewHolder(
    private val binding: ItemCartCheckoutBinding,
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<CartMenu> {
    override fun bind(item: CartMenu) {
        setCartData(item)
        setCartNotes(item)
    }

    private fun setCartData(item: CartMenu) {
        with(binding) {
            binding.ivMenuImage.load(item.menu.imageUrl)
            binding.tvPrice.text = (item.cart.itemQuantity * item.menu.price).toCurrencyFormat()
            binding.tvMenuName.text = item.menu.name
            binding.tvQuantity.text = itemView.rootView.context.getString(R.string.total_quantity, item.cart.itemQuantity)
        }
    }

    private fun setCartNotes(item: CartMenu) {
        if(item.cart.orderNotes != ""){
            binding.tvOrderNote.text = item.cart.orderNotes
        } else {
            binding.tvNoteTitle.isVisible = false
            binding.tvOrderNote.isVisible = false
        }
    }

}

class CheckoutSumarryListViewHolder(
    private val binding: ItemCartCheckoutSummaryBinding
): RecyclerView.ViewHolder(binding.root), ViewHolderBinder<CartMenu>{
    override fun bind(item: CartMenu) {
        setCheckoutSummary(item)
    }

    private fun setCheckoutSummary(item: CartMenu) {
        with(binding){
            binding.tvItemQuantity.text = itemView.rootView.context.getString(R.string.total_quantity, item.cart.itemQuantity)
            binding.tvMenuName.text = item.menu.name
            binding.tvPrice.text = (item.cart.itemQuantity * item.menu.price).toCurrencyFormat()
        }
    }

}