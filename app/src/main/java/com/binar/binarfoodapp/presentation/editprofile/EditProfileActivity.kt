package com.binar.binarfoodapp.presentation.editprofile

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.binar.binarfoodapp.R
import com.binar.binarfoodapp.databinding.ActivityEditProfileBinding
import com.binar.binarfoodapp.utils.proceedWhen
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditProfileActivity : AppCompatActivity() {

    private val binding: ActivityEditProfileBinding by lazy {
        ActivityEditProfileBinding.inflate(layoutInflater)
    }

    private val viewModel: EditProfileViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupForm()
        setUserData()
        setClickListener()
        observeResult()
    }

    private fun observeResult() {
        viewModel.updateProfileResult.observe(this) {
            it.proceedWhen(
                doOnSuccess = {
                    Toast.makeText(this, "Profile Berhasil Diubah", Toast.LENGTH_SHORT).show()
                    finish()
                }
            )
        }
    }

    private fun setClickListener() {
        binding.btnSave.setOnClickListener {
            updateProfile()
        }
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun updateProfile() {
        if (checkNameValidation()) {
            lifecycleScope.launch {
                val fullName = binding.layoutForm.etName.text.toString()
                viewModel.updateProfile(fullName)

            }
        }
    }

    private fun checkNameValidation(): Boolean {
        val name = binding.layoutForm.etName.text.toString().trim()
        val currentName = viewModel.getCurrentUser()?.fullName.orEmpty()
        return if (name.isEmpty()) {
            binding.layoutForm.tilName.isErrorEnabled = true
            binding.layoutForm.tilName.error = getString(R.string.text_hint_name_empty)
            false
        } else if (name == currentName) {
            Toast.makeText(this, "Name cannot be changed", Toast.LENGTH_SHORT).show()
            false
        } else {
            true
        }
    }

    private fun setUserData() {
        val name = viewModel.getCurrentUser()?.fullName.orEmpty()
        val email = viewModel.getCurrentUser()?.email.orEmpty()

        binding.layoutForm.etName.setText(name)
        binding.layoutForm.etEmail.setText(email)
    }

    private fun setupForm() {
        binding.layoutForm.tilName.isVisible = true
        binding.layoutForm.tilEmail.isVisible = true
        binding.layoutForm.tilEmail.isEnabled = false
    }
}