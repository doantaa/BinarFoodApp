package com.binar.binarfoodapp.presentation.adapter.viewholder

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.binar.binarfoodapp.R
import com.binar.binarfoodapp.core.ViewHolderBinder
import com.binar.binarfoodapp.databinding.ItemCartCheckoutBinding
import com.binar.binarfoodapp.databinding.ItemCartCheckoutSummaryBinding
import com.binar.binarfoodapp.model.Cart
import com.binar.binarfoodapp.utils.toCurrencyFormat

class CheckoutListViewHolder(
    private val binding: ItemCartCheckoutBinding,
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Cart> {
    override fun bind(item: Cart) {
        setCartData(item)
        setCartNotes(item)
    }

    private fun setCartData(item: Cart) {
        with(binding) {
            binding.ivMenuImage.load(item.menuImgUrl)
            binding.tvPrice.text = (item.itemQuantity * item.menuPrice).toCurrencyFormat()
            binding.tvMenuName.text = item.menuName
            binding.tvQuantity.text =
                itemView.rootView.context.getString(R.string.total_quantity, item.itemQuantity)
        }
    }

    private fun setCartNotes(item: Cart) {
        if (item.orderNotes != "") {
            binding.tvOrderNote.text = item.orderNotes
        } else {
            binding.tvNoteTitle.isVisible = false
            binding.tvOrderNote.isVisible = false
        }
    }

}

class CheckoutSumarryListViewHolder(
    private val binding: ItemCartCheckoutSummaryBinding
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Cart> {
    override fun bind(item: Cart) {
        setCheckoutSummary(item)
    }

    private fun setCheckoutSummary(item: Cart) {
        with(binding) {
            binding.tvItemQuantity.text =
                itemView.rootView.context.getString(R.string.total_quantity, item.itemQuantity)
            binding.tvMenuName.text = item.menuName
            binding.tvPrice.text = (item.itemQuantity * item.menuPrice).toCurrencyFormat()
        }
    }

}