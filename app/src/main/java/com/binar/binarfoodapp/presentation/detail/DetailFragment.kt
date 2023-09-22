package com.binar.binarfoodapp.presentation.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import coil.load
import com.binar.binarfoodapp.R
import com.binar.binarfoodapp.databinding.FragmentDetailBinding
import com.binar.binarfoodapp.model.Food


class DetailFragment : Fragment() {

    private var counter = 1
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
        binding.buttonBack.setOnClickListener{
            findNavController().popBackStack()
        }

        binding.btnMinus.setOnClickListener{
            decreaseCounter()
        }

        binding.btnPlus.setOnClickListener{
            increaseCounter()
        }

    }

    private fun increaseCounter() {
        counter++
        refreshCounterView()
    }

    private fun decreaseCounter() {
        if (counter > 1) counter--
        refreshCounterView()
    }

    private fun refreshCounterView() {
        binding.btnMinus.alpha = if(counter == 1)  0.5F else 1.0F
        binding.tvCounter.text = counter.toString()
        binding.tvPrice.text = getTotalPrice()
    }

    private fun getTotalPrice(): String {
        val totalPrice = counter * (food?.price ?: 0)
        return "RP $totalPrice"
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
        binding.tvMenuPrice.text = "${getString(R.string.rp)} ${food?.price.toString()}"
        binding.ivMenuImage.load(food?.imageUrl)
        binding.tvMenuDescription.text = food?.description
        binding.tvCounter.text = counter.toString()
        binding.tvPrice.text = getTotalPrice()
    }

}