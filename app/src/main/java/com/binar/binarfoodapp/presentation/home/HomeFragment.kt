package com.binar.binarfoodapp.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.binar.binarfoodapp.R
import com.binar.binarfoodapp.data.dummy.DummyCategoryDataSource
import com.binar.binarfoodapp.data.dummy.DummyCategoryDataSourceImpl
import com.binar.binarfoodapp.data.local.database.AppDatabase
import com.binar.binarfoodapp.data.local.database.datasource.MenuDataSourceImpl
import com.binar.binarfoodapp.data.repository.MenuRepository
import com.binar.binarfoodapp.data.repository.MenuRepositoryImpl
import com.binar.binarfoodapp.databinding.FragmentHomeBinding
import com.binar.binarfoodapp.model.Menu
import com.binar.binarfoodapp.presentation.detail.DetailActivity
import com.binar.binarfoodapp.presentation.home.adapter.subadapter.AdapterLayoutMode
import com.binar.binarfoodapp.presentation.home.adapter.subadapter.CategoryListAdapter
import com.binar.binarfoodapp.presentation.home.adapter.subadapter.FoodListAdapter
import com.binar.binarfoodapp.utils.GenericViewModelFactory
import com.binar.binarfoodapp.utils.proceedWhen

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val viewModel: HomeViewModel by viewModels {
        val categoryDataSource = DummyCategoryDataSourceImpl()
        val database= AppDatabase.getInstance(requireContext())
        val menuDao = database.menuDao()
        val menuDataSource = MenuDataSourceImpl(menuDao)
        val repo: MenuRepository = MenuRepositoryImpl(menuDataSource,categoryDataSource)
        GenericViewModelFactory.create(HomeViewModel(repo))
    }



    private val categoryDataSource: DummyCategoryDataSource by lazy {
        DummyCategoryDataSourceImpl()
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

    private fun navigateToActivityDetail(it: Menu) {
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
        fetchData()
        setupSwitch()

    }

    private fun fetchData() {
        viewModel.menuData.observe(viewLifecycleOwner){
            it.proceedWhen(
                doOnSuccess = {result ->
                    binding.rvFoods.isVisible = true
                    result.payload?.let {menu ->
                        foodListAdapter.setData(menu)
                    }
                    binding.tvListTitle.text = "Success"
                },
                doOnLoading = {
                    binding.tvListTitle.text = ("Loading")
                }
            )
        }
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

    private fun setupMenuRecyclerView() {
        val span = if (foodListAdapter.adapterLayoutMode == AdapterLayoutMode.LINEAR) 1 else 2
        binding.rvFoods.adapter = foodListAdapter
        binding.rvFoods.layoutManager = GridLayoutManager(requireContext(), span)
    }

    private fun setupCategoryRecyclerView() {
        binding.rvCategory.adapter = categoryListAdapter
        categoryListAdapter.setData(categoryDataSource.getCategory())

    }


}