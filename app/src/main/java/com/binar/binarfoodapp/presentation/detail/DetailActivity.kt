package com.binar.binarfoodapp.presentation.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.binar.binarfoodapp.databinding.ActivityDetailBinding
import com.binar.binarfoodapp.model.Food
import com.binar.binarfoodapp.utils.toCurrencyFormat

class DetailActivity : AppCompatActivity() {

    private val binding: ActivityDetailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setOnclickListener()
    }

    private fun setOnclickListener() {
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }


    private fun bindFoodInfo(food: Food) {
        binding.ivMenuImage.load(food.imageUrl)
        binding.tvPrice.text = food.price.toCurrencyFormat("IDR")
        binding.tvMenuName.text = food.name
    }


    companion object {
        private const val FOOD = "FOOD"
        fun startActivity(context: Context, food: Food) {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(FOOD, food)
            context.startActivity(intent)
        }
    }
}