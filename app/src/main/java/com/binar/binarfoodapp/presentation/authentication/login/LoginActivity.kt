package com.binar.binarfoodapp.presentation.authentication.login

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.binar.binarfoodapp.R
import com.binar.binarfoodapp.databinding.ActivityLoginBinding
import com.binar.binarfoodapp.presentation.authentication.register.RegisterActivity
import com.binar.binarfoodapp.presentation.main.MainActivity
import com.binar.binarfoodapp.utils.highLightWord
import com.binar.binarfoodapp.utils.proceedWhen
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {
    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    private val viewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupLoginForm()
        setClickListener()
        observeResult()
    }

    private fun setClickListener() {
        binding.btnLogin.setOnClickListener {
            doLogin()
        }

        binding.tvNavToRegister.highLightWord(getString(R.string.text_clickable_to_register)) {
            navigateToRegister()
        }
    }

    private fun observeResult() {
        viewModel.loginResult.observe(this) {
            it.proceedWhen(
                doOnSuccess = {
                    binding.btnLogin.isVisible = true
                    binding.btnLogin.isEnabled = false
                    binding.pbLoading.isVisible = false
                    Toast.makeText(this, "Berhasil", Toast.LENGTH_SHORT).show()
                    navigateToMain()
                },
                doOnLoading = {
                    binding.btnLogin.isVisible = false
                    binding.pbLoading.isVisible = true
                    Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show()
                },
                doOnError = {
                    binding.btnLogin.isVisible = true
                    binding.btnLogin.isEnabled = true
                    binding.pbLoading.isVisible = false
                    Toast.makeText(
                        this,
                        "Login Failed : ${it.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
        }
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
    }

    private fun navigateToRegister() {
        val intent = Intent(this, RegisterActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        startActivity(intent)
    }


    private fun isFormValid(): Boolean {
        val email = binding.layoutForm.etEmail.text.toString().trim()
        val password = binding.layoutForm.etPassword.text.toString().trim()

        return checkEmailValidation(email)
                && checkPasswordValidation(password)

    }

    private fun checkEmailValidation(email: String): Boolean {
        return if (email.isEmpty()) {
            binding.layoutForm.tilEmail.isErrorEnabled = true
            binding.layoutForm.tilEmail.error = getString(R.string.text_hint_email_empty)
            false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.layoutForm.tilEmail.isErrorEnabled = true
            binding.layoutForm.tilEmail.error = getString(R.string.text_hint_email_format_invalid)
            false
        } else {
            binding.layoutForm.tilEmail.isErrorEnabled = false
            true
        }
    }

    private fun checkPasswordValidation(password: String): Boolean {
        return if (password.isEmpty()) {
            binding.layoutForm.tilPassword.isErrorEnabled = true
            binding.layoutForm.tilPassword.error = getString(R.string.text_hint_password_empty)
            false
        } else {
            binding.layoutForm.tilPassword.isErrorEnabled = false
            true
        }
    }


    private fun doLogin() {
        if (isFormValid()) {
            lifecycleScope.launch {
                val email = binding.layoutForm.etEmail.text.toString().trim()
                val password = binding.layoutForm.etPassword.text.toString().trim()
                viewModel.doLogin(email, password)
            }
        }
    }

    private fun setupLoginForm() {
        binding.layoutForm.tilEmail.isVisible = true
        binding.layoutForm.tilPassword.isVisible = true
    }
}