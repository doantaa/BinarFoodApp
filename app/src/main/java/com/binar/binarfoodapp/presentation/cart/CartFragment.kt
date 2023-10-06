package com.binar.binarfoodapp.presentation.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.binarfoodapp.data.FoodDataSource
import com.binar.binarfoodapp.data.FoodDataSourceImpl
import com.binar.binarfoodapp.databinding.FragmentCartBinding


class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    private val dataSource: FoodDataSource by lazy {
        FoodDataSourceImpl()
    }

    private val adapter: CartListAdapter by lazy {
        CartListAdapter()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
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
        adapter.setData(dataSource.getFoodData())
    }
}