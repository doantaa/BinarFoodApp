package com.binar.binarfoodapp.presentation.authentication.register

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.binar.binarfoodapp.R
import com.binar.binarfoodapp.databinding.ActivityRegisterBinding
import com.binar.binarfoodapp.presentation.authentication.login.LoginActivity
import com.binar.binarfoodapp.presentation.main.MainActivity
import com.binar.binarfoodapp.utils.highLightWord
import com.binar.binarfoodapp.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity : AppCompatActivity() {

    private val binding: ActivityRegisterBinding by lazy {
        ActivityRegisterBinding.inflate(layoutInflater)
    }

    private val viewModel: RegisterViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupForm()
        setClickListener()
        observeResult()
    }

    private fun observeResult() {
        viewModel.registerResult.observe(this) {
            it.proceedWhen(doOnSuccess = {
                binding.btnRegister.isVisible = true
                binding.btnRegister.isEnabled = false
                binding.pbLoading.isVisible = false
                navigateToMain()
            }, doOnLoading = {
                binding.btnRegister.isVisible = false
                binding.pbLoading.isVisible = true
            }, doOnError = {
                binding.btnRegister.isVisible = true
                binding.btnRegister.isEnabled = true
                binding.pbLoading.isVisible = false
                Toast.makeText(
                    this,
                    "Register Failed ${it.exception?.message.orEmpty()}",
                    Toast.LENGTH_SHORT
                ).show()
            })
        }
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
    }

    private fun setClickListener() {
        binding.btnRegister.setOnClickListener {
            doRegister()
        }

        binding.tvNavToRegister.highLightWord(getString(R.string.text_clickable_to_login)) {
            navigateToLogin()
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        startActivity(intent)
    }

    private fun isFormValid(): Boolean {
        val fullName = binding.layoutForm.etName.text.toString().trim()
        val email = binding.layoutForm.etEmail.text.toString().trim()
        val password = binding.layoutForm.etPassword.text.toString().trim()
        val confirmPassword = binding.layoutForm.etPassword.text.toString().trim()

        return checkNameValidation(fullName) && checkEmailValidation(email) && checkPasswordValidation(
            password
        ) && checkPasswordValidation(confirmPassword) && checkPasswordAndConfirmation(
            password, confirmPassword
        )
    }

    private fun checkNameValidation(fullName: String): Boolean {
        return if (fullName.isEmpty()) {
            binding.layoutForm.tilName.isErrorEnabled = true
            binding.layoutForm.tilName.error = getString(R.string.text_hint_name_empty)
            false
        } else {
            binding.layoutForm.tilName.isErrorEnabled = false
            true
        }
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
        } else if (password.length < 8) {
            binding.layoutForm.tilPassword.isErrorEnabled = true
            binding.layoutForm.tilPassword.error = getString(R.string.text_hint_password_length)
            false
        } else {
            binding.layoutForm.tilPassword.isErrorEnabled = false
            true
        }
    }

    private fun checkPasswordAndConfirmation(password: String, confirmPassword: String): Boolean {
        return if (password != confirmPassword) {
            binding.layoutForm.tilConfirmPassword.isErrorEnabled = true
            binding.layoutForm.tilConfirmPassword.error =
                getString(R.string.text_hint_password_not_match)

            binding.layoutForm.tilPassword.isErrorEnabled = true
            binding.layoutForm.tilPassword.error = getString(R.string.text_hint_password_not_match)
            false
        } else {
            binding.layoutForm.tilConfirmPassword.isErrorEnabled = false
            binding.layoutForm.tilPassword.isErrorEnabled = false
            true
        }
    }


    private fun doRegister() {
        if (isFormValid()) {
            val fullName = binding.layoutForm.etName.text.toString().trim()
            val email = binding.layoutForm.etEmail.text.toString().trim()
            val password = binding.layoutForm.etPassword.text.toString().trim()
            viewModel.doRegister(fullName, email, password)
        }
    }

    private fun setupForm() {
        binding.layoutForm.tilName.isVisible = true
        binding.layoutForm.tilEmail.isVisible = true
        binding.layoutForm.tilPassword.isVisible = true
        binding.layoutForm.tilConfirmPassword.isVisible = true

    }
}