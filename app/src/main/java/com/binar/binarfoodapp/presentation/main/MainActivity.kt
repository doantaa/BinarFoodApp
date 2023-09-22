package com.binar.binarfoodapp.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.binar.binarfoodapp.R
import com.binar.binarfoodapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}