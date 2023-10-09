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
import androidx.recyclerview.widget.GridLayoutManager
import com.binar.binarfoodapp.R
import com.binar.binarfoodapp.data.dummy.DummyCategoryDataSource
import com.binar.binarfoodapp.data.dummy.DummyCategoryDataSourceImpl
import com.binar.binarfoodapp.data.local.database.AppDatabase
import com.binar.binarfoodapp.data.local.database.datasource.MenuDataSourceImpl
import com.binar.binarfoodapp.data.local.datastore.UserPreferenceDataSourceImpl
import com.binar.binarfoodapp.data.local.datastore.appDataStore
import com.binar.binarfoodapp.data.repository.MenuRepository
import com.binar.binarfoodapp.data.repository.MenuRepositoryImpl
import com.binar.binarfoodapp.databinding.FragmentHomeBinding
import com.binar.binarfoodapp.model.Menu
import com.binar.binarfoodapp.presentation.detail.DetailActivity
import com.binar.binarfoodapp.presentation.home.adapter.subadapter.AdapterLayoutMode
import com.binar.binarfoodapp.presentation.home.adapter.subadapter.CategoryListAdapter
import com.binar.binarfoodapp.presentation.home.adapter.subadapter.FoodListAdapter
import com.binar.binarfoodapp.utils.GenericViewModelFactory
import com.binar.binarfoodapp.utils.PreferenceDataStoreHelperImpl
import com.binar.binarfoodapp.utils.proceedWhen

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val viewModel: HomeViewModel by viewModels {
        val database = AppDatabase.getInstance(requireContext())
        val menuDao = database.menuDao()
        val menuDataSource = MenuDataSourceImpl(menuDao)
        val repo: MenuRepository = MenuRepositoryImpl(menuDataSource, categoryDataSource)

        val dataStore = this.requireContext().appDataStore
        val dataStoreHelper = PreferenceDataStoreHelperImpl(dataStore)
        val userPreferenceDataSource = UserPreferenceDataSourceImpl(dataStoreHelper)
        GenericViewModelFactory.create(HomeViewModel(repo, userPreferenceDataSource))
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
        viewModel.menuData.observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = { result ->
                    binding.rvFoods.isVisible = true
                    binding.layoutState.tvError.isVisible = false
                    binding.layoutState.pbLoading.isVisible = false
                    result.payload?.let { menu ->
                        foodListAdapter.setData(menu)
                    }
                },
                doOnLoading = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = true
                    binding.rvFoods.isVisible = false

                },
                doOnError = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = true
                    binding.layoutState.tvError.text = it.exception?.message.orEmpty()
                    binding.rvFoods.isVisible = false
                }
            )
        }
    }

    private fun setupSwitch() {
        viewModel.getUserListViewLiveData().observe(viewLifecycleOwner) { isUsingList ->
            val icon = if (isUsingList) R.drawable.ic_linear else R.drawable.ic_grid
            (binding.rvFoods.layoutManager as GridLayoutManager).spanCount =
                if (isUsingList) 1 else 2
            foodListAdapter.adapterLayoutMode =
                if (isUsingList) AdapterLayoutMode.LINEAR else AdapterLayoutMode.GRID
            binding.ivSwitchLayout.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    icon
                )
            )
            foodListAdapter.refreshList()
            Toast.makeText(requireContext(), isUsingList.toString(), Toast.LENGTH_SHORT).show()
            setSwitchClickListener(isUsingList)
        }
    }

    private fun setSwitchClickListener(usingList: Boolean) {
        binding.ivSwitchLayout.setOnClickListener {
            if (usingList) {
                viewModel.setUserListViewMode(false)
            } else {
                viewModel.setUserListViewMode(true)
            }
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