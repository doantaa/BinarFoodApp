package com.binar.binarfoodapp.data

import com.binar.binarfoodapp.model.Food

interface FoodDataSource {
    fun getFoodData() : List<Food>
}

class FoodDataSourceImpl(): FoodDataSource {
    override fun getFoodData(): List<Food> {
        return mutableListOf(
            Food(
                name = "Ayam Goreng",
                price = 50000,
                description = "Ini adalah ayam yang digoreng dengan minyak pilihan",
                imageUrl = "https://raw.githubusercontent.com/doantaa/BinarFoodApp-Resource/main/ayam_goreng.png"
            ),

            Food(
                name = "Ayam Bakar",
                price = 45000,
                description = "Ini adalah ayam bakar yang dibakar dengan api pilihan",
                imageUrl = "https://raw.githubusercontent.com/doantaa/BinarFoodApp-Resource/main/ayam_bakar.png"
            ),

            Food(
                name = "Ayam Geprek",
                price = 30000,
                description = "Ini adalah ayam goreng yang digeprek dengan batu pilihan",
                imageUrl = "https://raw.githubusercontent.com/doantaa/BinarFoodApp-Resource/main/ayam_geprek.png"
            ),

            Food(
                name = "Bakso",
                price = 44000,
                description = "Ini adalah bakso yang digiling dengan daging pilihan",
                imageUrl = "https://raw.githubusercontent.com/doantaa/BinarFoodApp-Resource/main/bakso.png"
            ),

            Food(
                name = "Nasi Campur",
                price = 80000,
                description = "Ini adalah Nasi yang dicampur pakai beras pilihan",
                imageUrl = "https://raw.githubusercontent.com/doantaa/BinarFoodApp-Resource/main/nasi_campur.png"
            ),

            Food(
                name = "Sate Usus",
                price = 15000,
                description = "Ini adalah Sate usus yang ditusuk pakai lidi pilihan",
                imageUrl = "https://raw.githubusercontent.com/doantaa/BinarFoodApp-Resource/main/sate_usus.png"
            ),
        )
    }
}