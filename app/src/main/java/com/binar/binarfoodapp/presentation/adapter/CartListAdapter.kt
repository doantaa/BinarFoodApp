package com.binar.binarfoodapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.binar.binarfoodapp.core.ViewHolderBinder
import com.binar.binarfoodapp.databinding.ItemCartBinding
import com.binar.binarfoodapp.databinding.ItemCartCheckoutBinding
import com.binar.binarfoodapp.model.Cart
import com.binar.binarfoodapp.presentation.adapter.viewholder.CartItemListViewHolder
import com.binar.binarfoodapp.presentation.adapter.viewholder.CheckoutListViewHolder

class CartListAdapter(private val cartListener: CartListener? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<Cart>() {

        override fun areItemsTheSame(oldItem: Cart, newItem: Cart): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Cart, newItem: Cart): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (cartListener != null) CartItemListViewHolder(
            ItemCartBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), cartListener
        ) else {
            CheckoutListViewHolder(
                ItemCartCheckoutBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolderBinder<Cart>).bind(differ.currentList[position])
    }

    fun setData(menuData: List<Cart>) {
        differ.submitList(menuData)
    }
}


interface CartListener {
    fun onPlusTotalItemCartClicked(cart: Cart)
    fun onMinusTotalItemCartClicked(cart: Cart)
    fun onRemoveCartClicked(cart: Cart)
    fun onUserDoneEditingNotes(cart: Cart)
}
