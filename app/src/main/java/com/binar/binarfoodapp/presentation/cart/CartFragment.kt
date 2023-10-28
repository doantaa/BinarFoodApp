package com.binar.binarfoodapp.presentation.cart

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.binarfoodapp.R
import com.binar.binarfoodapp.databinding.FragmentCartBinding
import com.binar.binarfoodapp.di.AppInjection
import com.binar.binarfoodapp.model.Cart
import com.binar.binarfoodapp.presentation.adapter.CartListAdapter
import com.binar.binarfoodapp.presentation.adapter.CartListener
import com.binar.binarfoodapp.presentation.checkout.CheckoutActivity
import com.binar.binarfoodapp.utils.hideKeyboard
import com.binar.binarfoodapp.utils.proceedWhen
import com.binar.binarfoodapp.utils.toCurrencyFormat

class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding

    private val adapter: CartListAdapter by lazy {
        CartListAdapter(object : CartListener {
            override fun onPlusTotalItemCartClicked(cart: Cart) {
                viewModel.increaseCart(cart)
            }

            override fun onMinusTotalItemCartClicked(cart: Cart) {
                viewModel.decreaseCart(cart)
            }

            override fun onRemoveCartClicked(cart: Cart) {
                viewModel.removeCart(cart)
            }

            override fun onUserDoneEditingNotes(cart: Cart) {
                viewModel.setCartNotes(cart)
                hideKeyboard()

            }
        })
    }

    private val viewModel: CartViewModel by viewModels {
        AppInjection.getCartViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeData()
        setClickListener()
    }

    private fun setClickListener() {
        binding.btnCheckout.setOnClickListener {
            context?.startActivity(Intent(requireContext(), CheckoutActivity::class.java))
        }
    }

    private fun observeData() {
        viewModel.cartList.observe(viewLifecycleOwner) {
            it.proceedWhen(doOnSuccess = { result ->
                binding.layoutState.root.isVisible = false
                binding.layoutState.pbLoading.isVisible = false
                binding.layoutState.tvError.isVisible = false
                binding.rvCartList.isVisible = true
                result.payload?.let { (carts, totalPrice) ->
                    adapter.setData(carts)
                    binding.tvTotalPrice.text = totalPrice.toCurrencyFormat()
                }
            }, doOnLoading = {
                binding.layoutState.root.isVisible = true
                binding.layoutState.pbLoading.isVisible = true
                binding.layoutState.tvError.isVisible = false
                binding.rvCartList.isVisible = false
            }, doOnError = { err ->
                binding.layoutState.root.isVisible = true
                binding.layoutState.pbLoading.isVisible = false
                binding.layoutState.tvError.isVisible = true
                binding.layoutState.tvError.text = err.exception?.message.orEmpty()
                binding.rvCartList.isVisible =  false
            }, doOnEmpty = { data ->
                binding.layoutState.root.isVisible = true
                binding.layoutState.pbLoading.isVisible = false
                binding.layoutState.tvError.isVisible = true
                binding.layoutState.tvError.text = getString(R.string.text_cart_is_empty)
                binding.cvSectionCheckout.isVisible = false
                data.payload?.let { (_, totalPrice) ->
                    binding.tvTotalPrice.text = totalPrice.toCurrencyFormat()
                }
                binding.rvCartList.isVisible = false
            })
        }
    }


    private fun setupRecyclerView() {
        binding.rvCartList.adapter = adapter
        binding.rvCartList.layoutManager = LinearLayoutManager(requireContext())
    }


}