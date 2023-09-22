package com.binar.binarfoodapp.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import coil.decode.DataSource
import com.binar.binarfoodapp.data.FoodDataSource
import com.binar.binarfoodapp.data.FoodDataSourceImpl
import com.binar.binarfoodapp.databinding.FragmentHomeBinding
import com.binar.binarfoodapp.model.Food

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val dataSource: FoodDataSource by lazy {
        FoodDataSourceImpl()
    }

    private val adapter: FoodListAdapter by lazy {
        FoodListAdapter{
            navigateToFragmentDetail(it)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

    }

    private fun setupRecyclerView() {
        binding.rvFoods.adapter = adapter
        binding.rvFoods.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter.setData(dataSource.getFoodData())
    }


    private fun navigateToFragmentDetail(food: Food? = null) {
        val action = HomeFragmentDirections.navigateToDetail(food)
        findNavController().navigate(action)
    }

}