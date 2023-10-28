package com.binar.binarfoodapp.presentation.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.binar.binarfoodapp.R
import com.binar.binarfoodapp.data.network.firebase.auth.FirebaseAuthDataSourceImpl
import com.binar.binarfoodapp.data.repository.UserRepositoryImpl
import com.binar.binarfoodapp.databinding.FragmentProfileBinding
import com.binar.binarfoodapp.presentation.authentication.login.LoginActivity
import com.binar.binarfoodapp.presentation.editprofile.EditProfileActivity
import com.binar.binarfoodapp.utils.GenericViewModelFactory
import com.google.firebase.auth.FirebaseAuth


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    private val viewModel: ProfileViewModel by viewModels {
        val firebaseAuth = FirebaseAuth.getInstance()
        val dataSource = FirebaseAuthDataSourceImpl(firebaseAuth)
        val repo = UserRepositoryImpl(dataSource)
        GenericViewModelFactory.create(ProfileViewModel(repo))
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupForm()
        getData()
        setUserData()
        setClickListener()
    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    private fun getData() {
        viewModel.getCurrentUser()
    }

    private fun setClickListener() {
        binding.btnEditProfile.setOnClickListener {
            navigateToEditProfile()
        }

        binding.btnLogout.setOnClickListener {
            doLogout()
        }
    }

    private fun doLogout() {
        AlertDialog.Builder(requireContext())
            .setMessage(
                getString(R.string.text_logout_confirmation)
            )
            .setPositiveButton(getString(R.string.text_yes)) { _, _ ->
                viewModel.doLogout()
                navigateToLogin()
            }
            .setNegativeButton(getString(R.string.text_no)) { _, _ ->
                //do nothing
            }.create().show()

    }

    private fun navigateToLogin() {
        val intent = Intent(requireContext(), LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
    }

    private fun navigateToEditProfile() {
        val intent = Intent(requireContext(), EditProfileActivity::class.java)
        startActivity(intent)
    }

    private fun setUserData() {
        viewModel.userProfile.observe(viewLifecycleOwner) {
            val name = it?.fullName.orEmpty()
            val email = it?.email.orEmpty()

            binding.layoutForm.etName.setText(name)
            binding.layoutForm.etEmail.setText(email)
        }

    }

    private fun setupForm() {
        binding.layoutForm.tilName.isVisible = true
        binding.layoutForm.tilName.isEnabled = false
        binding.layoutForm.tilEmail.isVisible = true
        binding.layoutForm.tilEmail.isEnabled = false
    }


}