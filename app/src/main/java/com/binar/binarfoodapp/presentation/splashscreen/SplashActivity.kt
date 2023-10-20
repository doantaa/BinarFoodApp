package com.binar.binarfoodapp.presentation.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.binar.binarfoodapp.data.network.firebase.auth.FirebaseAuthDataSourceImpl
import com.binar.binarfoodapp.data.repository.UserRepositoryImpl
import com.binar.binarfoodapp.databinding.ActivitySplashBinding
import com.binar.binarfoodapp.presentation.authentication.login.LoginActivity
import com.binar.binarfoodapp.presentation.main.MainActivity
import com.binar.binarfoodapp.utils.GenericViewModelFactory
import com.google.firebase.auth.FirebaseAuth

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private val binding: ActivitySplashBinding by lazy {
        ActivitySplashBinding.inflate(layoutInflater)
    }

    private val viewModel: SplashViewModel by viewModels {
        val firebaseAuth = FirebaseAuth.getInstance()
        val dataSource = FirebaseAuthDataSourceImpl(firebaseAuth)
        val repository = UserRepositoryImpl(dataSource)
        GenericViewModelFactory.create(SplashViewModel(repository))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        checkUserLogin()
    }

    private fun checkUserLogin() {
        if (viewModel.isUserLoggedIn()) {
            navigateToMain()
        } else {
            navigateToLogin()
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
    }
}