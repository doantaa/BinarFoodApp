package com.binar.binarfoodapp.presentation.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.binar.binarfoodapp.core.ViewHolderBinder
import com.binar.binarfoodapp.databinding.ItemCartBinding
import com.binar.binarfoodapp.model.CartMenu
import com.binar.binarfoodapp.presentation.adapter.CartListener
import com.binar.binarfoodapp.utils.doneEditing
import com.binar.binarfoodapp.utils.toCurrencyFormat


class CartItemListViewHolder(
    private val binding: ItemCartBinding,
    private val cartListener: CartListener? = null
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<CartMenu> {
    override fun bind(item: CartMenu) {
        setCartData(item)
        setCartNotes(item)
        setClickListeners(item)
    }

    private fun setCartData(item: CartMenu) {
        with(binding) {
            binding.ivMenuImage.load(item.menu.imageUrl) {
                crossfade(true)
            }
            tvCounter.text = item.cart.itemQuantity.toString()
            tvMenuName.text = item.menu.name
            tvCountPrice.text = (item.cart.itemQuantity * item.menu.price).toCurrencyFormat()
        }
    }

    private fun setClickListeners(menu: CartMenu) {
        with(binding){
            btnMinus.setOnClickListener{ cartListener?.onMinusTotalItemCartClicked(menu.cart)}
            btnPlus.setOnClickListener{cartListener?.onPlusTotalItemCartClicked(menu.cart)}
            btnRemove.setOnClickListener{cartListener?.onRemoveCartClicked(menu.cart)}
        }
    }

    private fun setCartNotes(item: CartMenu) {
        binding.etOrderNote.setText(item.cart.orderNotes)
        binding.etOrderNote.doneEditing {
            binding.etOrderNote.clearFocus()
            val newItem = item.cart.copy().apply {
                orderNotes = binding.etOrderNote.text.toString().trim()
            }
            cartListener?.onUserDoneEditingNotes(newItem)
        }
    }
}



