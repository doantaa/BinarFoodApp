package com.binar.binarfoodapp.presentation.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.binarfoodapp.data.local.database.AppDatabase
import com.binar.binarfoodapp.data.local.database.datasource.CartDataSourceImpl
import com.binar.binarfoodapp.data.repository.CartRepositoryImpl
import com.binar.binarfoodapp.databinding.FragmentCartBinding
import com.binar.binarfoodapp.model.Cart
import com.binar.binarfoodapp.presentation.cart.adapter.CartListAdapter
import com.binar.binarfoodapp.utils.GenericViewModelFactory


class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding

    private val adapter: CartListAdapter by lazy {
        CartListAdapter()
    }

    private val viewModel: CartViewModel by viewModels {
        val database = AppDatabase.getInstance(requireContext())
        val cartDao = database.cartDao()
        val cartDataSource = CartDataSourceImpl(cartDao)
        val repo = CartRepositoryImpl(cartDataSource)
        GenericViewModelFactory.create(CartViewModel(repo))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.rvCartList.adapter = adapter
        binding.rvCartList.layoutManager = LinearLayoutManager(requireContext())
    }
}