package com.binar.binarfoodapp.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.binarfoodapp.R
import com.binar.binarfoodapp.data.CategoryDataSource
import com.binar.binarfoodapp.data.CategoryDataSourceImpl
import com.binar.binarfoodapp.data.FoodDataSource
import com.binar.binarfoodapp.data.FoodDataSourceImpl
import com.binar.binarfoodapp.databinding.FragmentHomeBinding
import com.binar.binarfoodapp.model.Category
import com.binar.binarfoodapp.model.Food
import com.binar.binarfoodapp.presentation.detail.DetailActivity
import com.binar.binarfoodapp.presentation.home.adapter.subadapter.AdapterLayoutMode
import com.binar.binarfoodapp.presentation.home.adapter.subadapter.CategoryListAdapter
import com.binar.binarfoodapp.presentation.home.adapter.subadapter.FoodListAdapter

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val foodDataSource: FoodDataSource by lazy {
        FoodDataSourceImpl()
    }
    private val categoryDataSource : CategoryDataSource by lazy {
        CategoryDataSourceImpl()
    }


    private val foodListAdapter: FoodListAdapter by lazy {
        FoodListAdapter(
            adapterLayoutMode = AdapterLayoutMode.GRID,
            onItemClick = { navigateToActivityDetail(it) }
        )
    }

    private val categoryListAdapter: CategoryListAdapter by lazy {
        CategoryListAdapter(
            onItemClick = {}
        )

    }

    private fun navigateToActivityDetail(it: Food) {
        DetailActivity.startActivity(requireContext(), it)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupSwitch()

    }

    private fun setupSwitch() {
        binding.ivSwitchLayout.setOnClickListener {
            val isGridView = foodListAdapter.adapterLayoutMode == AdapterLayoutMode.GRID
            val icon = if (isGridView) R.drawable.ic_grid else R.drawable.ic_linear

            (binding.rvFoods.layoutManager as GridLayoutManager).spanCount =
                if (isGridView) 1 else 2
            foodListAdapter.adapterLayoutMode =
                if (isGridView) AdapterLayoutMode.LINEAR else AdapterLayoutMode.GRID
            binding.ivSwitchLayout.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    icon
                )
            )
            foodListAdapter.refreshList()
        }
    }

    private fun setupRecyclerView() {
        setupCategoryRecyclerView()
        setupMenuRecyclerView()
    }

    private fun setupMenuRecyclerView(){
        val span = if (foodListAdapter.adapterLayoutMode == AdapterLayoutMode.LINEAR) 1 else 2
        binding.rvFoods.adapter = foodListAdapter
        binding.rvFoods.layoutManager = GridLayoutManager(requireContext(), span)
        foodListAdapter.setData(foodDataSource.getFoodData())
    }

    private fun setupCategoryRecyclerView(){
        binding.rvCategory.adapter = categoryListAdapter
        categoryListAdapter.setData(categoryDataSource.getCategory())

    }


}