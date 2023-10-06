package com.binar.binarfoodapp.presentation.detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.binar.binarfoodapp.databinding.ActivityDetailBinding
import com.binar.binarfoodapp.model.Menu
import com.binar.binarfoodapp.utils.toCurrencyFormat

class DetailActivity : AppCompatActivity() {

    private val binding: ActivityDetailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setClickListener()
    }

    private fun setClickListener() {
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        binding.clLocation.setOnClickListener{
            navigateToMap()
        }



    }

    private fun navigateToMap() {
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://maps.app.goo.gl/h4wQKqaBuXzftGK77")
        )
        startActivity(intent)
    }


    private fun bindFoodInfo(menu: Menu) {
        binding.ivMenuImage.load(menu.imageUrl)
        binding.tvPrice.text = menu.price.toCurrencyFormat()
        binding.tvMenuName.text = menu.name
    }


    companion object {
        private const val FOOD = "FOOD"
        fun startActivity(context: Context, menu: Menu) {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(FOOD, menu)
            context.startActivity(intent)
        }
    }
}