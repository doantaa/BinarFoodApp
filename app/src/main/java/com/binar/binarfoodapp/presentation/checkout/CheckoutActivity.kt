package com.binar.binarfoodapp.presentation.checkout

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.binar.binarfoodapp.R
import com.binar.binarfoodapp.data.local.database.AppDatabase
import com.binar.binarfoodapp.data.local.database.datasource.CartDataSource
import com.binar.binarfoodapp.data.local.database.datasource.CartDataSourceImpl
import com.binar.binarfoodapp.data.repository.CartRepository
import com.binar.binarfoodapp.data.repository.CartRepositoryImpl
import com.binar.binarfoodapp.databinding.ActivityCheckoutBinding
import com.binar.binarfoodapp.presentation.adapter.CartListAdapter
import com.binar.binarfoodapp.utils.GenericViewModelFactory
import com.binar.binarfoodapp.utils.proceedWhen
import com.binar.binarfoodapp.utils.toCurrencyFormat

class CheckoutActivity : AppCompatActivity() {

    private val viewModel: CheckoutViewModel by viewModels {
        val database = AppDatabase.getInstance(this)
        val cartDao = database.cartDao()
        val cartDataSource: CartDataSource = CartDataSourceImpl(cartDao)
        val repo: CartRepository = CartRepositoryImpl(cartDataSource)
        GenericViewModelFactory.create(CheckoutViewModel(repo))
    }

    private val binding: ActivityCheckoutBinding by lazy {
        ActivityCheckoutBinding.inflate(layoutInflater)
    }

    private val cartAdapter: CartListAdapter by lazy {
        CartListAdapter()
    }

    private val summaryAdapter: CheckoutSummaryAdapter by lazy {
        CheckoutSummaryAdapter()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupList()
        observeData()
        setClickListener()
    }

    private fun setClickListener() {
        binding.ivBack.setOnClickListener{
            onBackPressed()
        }

        binding.btnCheckout.setOnClickListener{
            Toast.makeText(this, "Pesanan anda berhasil", Toast.LENGTH_SHORT).show()
            viewModel.cleanCart()
            finish()
        }
    }


    private fun setupList() {
        binding.layoutContent.rvCart.adapter = cartAdapter
        binding.layoutContent.rvShoppingSummary.adapter = summaryAdapter
    }

    private fun observeData() {
        viewModel.cartList.observe(this) {
            it.proceedWhen(doOnSuccess = { result ->
                binding.layoutState.root.isVisible = false
                binding.layoutState.pbLoading.isVisible = false
                binding.layoutState.tvError.isVisible = false
                binding.layoutContent.root.isVisible = true
                binding.layoutContent.rvCart.isVisible = true
                binding.cvSectionOrder.isVisible = true
                result.payload?.let { (carts, totalPrice) ->
                    cartAdapter.setData(carts)
                    summaryAdapter.setData(carts)
                    binding.tvTotalPrice.text = totalPrice.toCurrencyFormat()
                }
            }, doOnLoading = {
                binding.layoutState.root.isVisible = true
                binding.layoutState.pbLoading.isVisible = true
                binding.layoutState.tvError.isVisible = false
                binding.layoutContent.root.isVisible = false
                binding.layoutContent.rvCart.isVisible = false
                binding.cvSectionOrder.isVisible = false
            }, doOnError = { err ->
                binding.layoutState.root.isVisible = true
                binding.layoutState.pbLoading.isVisible = false
                binding.layoutState.tvError.isVisible = true
                binding.layoutState.tvError.text = err.exception?.message.orEmpty()
                binding.layoutContent.root.isVisible = false
                binding.layoutContent.rvCart.isVisible = false
                binding.cvSectionOrder.isVisible = false
            }, doOnEmpty = { data ->
                binding.layoutState.root.isVisible = true
                binding.layoutState.pbLoading.isVisible = false
                binding.layoutState.tvError.isVisible = true
                binding.layoutState.tvError.text = getString(R.string.text_cart_is_empty)
                data.payload?.let { (_, totalPrice) ->
                    binding.tvTotalPrice.text = totalPrice.toCurrencyFormat()
                }
                binding.layoutContent.root.isVisible = false
                binding.layoutContent.rvCart.isVisible = false
                binding.cvSectionOrder.isVisible = false
            })
        }
    }

}