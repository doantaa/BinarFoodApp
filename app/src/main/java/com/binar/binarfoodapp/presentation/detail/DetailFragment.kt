package com.binar.binarfoodapp.presentation.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.load
import com.binar.binarfoodapp.databinding.FragmentDetailBinding
import com.binar.binarfoodapp.model.Food


class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    val food : Food? by lazy {
        DetailFragmentArgs.fromBundle(arguments as Bundle).food
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showFoodData()
        setClickListener()
    }

    private fun setClickListener() {
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

    private fun showFoodData() {
        binding.tvMenuName.text = food?.name
        binding.tvMenuPrice.text = food?.price.toString()
        binding.ivMenuImage.load(food?.imageUrl)
        binding.tvMenuDescription.text = food?.description
    }

}