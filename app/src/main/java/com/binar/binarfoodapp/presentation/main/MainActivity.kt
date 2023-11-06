package com.binar.binarfoodapp.presentation.main

import android.os.Bundle
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
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
        val popupMenu = PopupMenu(this, null)
        popupMenu.inflate(R.menu.bottom_nav_menu)
        val menu: android.view.Menu = popupMenu.menu
        val navController = findNavController(R.id.main_nav_host_fragment)
        binding.navView.setupWithNavController(menu, navController)
    }
}
