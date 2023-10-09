package com.binar.binarfoodapp.presentation.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.binar.binarfoodapp.R
import com.binar.binarfoodapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupBottomNav()
    }


    private fun setupBottomNav() {
        val navController = findNavController(R.id.main_nav_host_fragment)
        binding.navView.setupWithNavController(navController)
    }
}