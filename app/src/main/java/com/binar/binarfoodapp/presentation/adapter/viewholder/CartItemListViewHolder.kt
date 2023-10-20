package com.binar.binarfoodapp.presentation.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.binar.binarfoodapp.core.ViewHolderBinder
import com.binar.binarfoodapp.databinding.ItemCartBinding
import com.binar.binarfoodapp.model.Cart
import com.binar.binarfoodapp.presentation.adapter.CartListener
import com.binar.binarfoodapp.utils.doneEditing
import com.binar.binarfoodapp.utils.toCurrencyFormat


class CartItemListViewHolder(
    private val binding: ItemCartBinding,
    private val cartListener: CartListener? = null
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Cart> {
    override fun bind(item: Cart) {
        setCartData(item)
        setCartNotes(item)
        setClickListeners(item)
    }

    private fun setCartData(item: Cart) {
        with(binding) {
            binding.ivMenuImage.load(item.menuImgUrl) {
                crossfade(true)
            }
            tvCounter.text = item.itemQuantity.toString()
            tvMenuName.text = item.menuName
            tvCountPrice.text = (item.itemQuantity * item.menuPrice).toCurrencyFormat()
        }
    }

    private fun setClickListeners(menu: Cart) {
        with(binding){
            btnMinus.setOnClickListener{ cartListener?.onMinusTotalItemCartClicked(menu)}
            btnPlus.setOnClickListener{cartListener?.onPlusTotalItemCartClicked(menu)}
            btnRemove.setOnClickListener{cartListener?.onRemoveCartClicked(menu)}
        }
    }

    private fun setCartNotes(item: Cart) {
        binding.etOrderNote.setText(item.orderNotes)
        binding.etOrderNote.doneEditing {
            binding.etOrderNote.clearFocus()
            val newItem = item.copy().apply {
                orderNotes = binding.etOrderNote.text.toString().trim()
            }
            cartListener?.onUserDoneEditingNotes(newItem)
        }
    }
}



