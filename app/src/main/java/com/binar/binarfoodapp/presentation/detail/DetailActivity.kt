package com.binar.binarfoodapp.presentation.detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.binar.binarfoodapp.data.local.database.AppDatabase
import com.binar.binarfoodapp.data.local.database.datasource.CartDataSourceImpl
import com.binar.binarfoodapp.data.network.api.datasource.RestaurantApiDataSource
import com.binar.binarfoodapp.data.network.api.service.RestaurantService
import com.binar.binarfoodapp.data.repository.CartRepository
import com.binar.binarfoodapp.data.repository.CartRepositoryImpl
import com.binar.binarfoodapp.databinding.ActivityDetailBinding
import com.binar.binarfoodapp.model.Menu
import com.binar.binarfoodapp.utils.GenericViewModelFactory
import com.binar.binarfoodapp.utils.proceedWhen
import com.binar.binarfoodapp.utils.toCurrencyFormat

class DetailActivity : AppCompatActivity() {

    private val binding: ActivityDetailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }

    private val viewModel: DetailViewModel by viewModels {
        val database = AppDatabase.getInstance(this)
        val cartDao = database.cartDao()
        val cartDataSource = CartDataSourceImpl(cartDao)
        //API
        val service = RestaurantService.invoke()
        val apiDataSource = RestaurantApiDataSource(service)


        val repo: CartRepository = CartRepositoryImpl(cartDataSource, apiDataSource)
        GenericViewModelFactory.create(DetailViewModel(intent?.extras, repo))

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setClickListener()
        bindMenu(viewModel.menu)
        observeCounter()
    }

    private fun observeCounter() {
        viewModel.menuCountLiveData.observe(this) {
            binding.tvCounter.text = it.toString()
        }

        viewModel.priceLiveData.observe(this) {
            binding.tvPrice.text = it.toCurrencyFormat()
        }

        viewModel.addToCartResult.observe(this) {
            it.proceedWhen(
                doOnSuccess = {
                    Toast.makeText(
                        this,
                        "${this.viewModel.menu?.name} added to cart",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                },
                doOnError = {

                }
            )
        }
    }

    private fun bindMenu(menu: Menu?) {
        menu?.let { menuItem ->
            binding.ivMenuImage.load(menuItem.imageUrl)
            binding.tvMenuName.text = menuItem.name
            binding.tvMenuPrice.text = menuItem.price.toCurrencyFormat()
            binding.tvMenuDescription.text = menuItem.description

        }

    }

    private fun setClickListener() {
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        binding.clLocation.setOnClickListener {
            navigateToMap()
        }

        binding.btnPlus.setOnClickListener {
            viewModel.add()
        }

        binding.btnMinus.setOnClickListener {
            viewModel.minus()
        }

        binding.cvAddToCart.setOnClickListener {
            viewModel.addToCart()
        }
    }


    private fun navigateToMap() {
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://maps.app.goo.gl/h4wQKqaBuXzftGK77")
        )
        startActivity(intent)
    }


    companion object {
        const val EXTRA_FOOD = "EXTRA_FOOD"
        fun startActivity(context: Context, menu: Menu) {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(EXTRA_FOOD, menu)
            context.startActivity(intent)
        }
    }
}