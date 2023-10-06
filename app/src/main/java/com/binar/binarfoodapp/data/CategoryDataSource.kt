package com.binar.binarfoodapp.data

import com.binar.binarfoodapp.model.Category


interface CategoryDataSource {
    fun getCategory() : List<Category>
}

class CategoryDataSourceImpl(): CategoryDataSource {
    override fun getCategory(): List<Category> {
        return mutableListOf(
            Category(
                name = "Rice",
                imageUrl = "https://raw.githubusercontent.com/doantaa/BinarFoodApp-Resource/main/ic_category_rice.png"
            ),
            Category(
                name = "Noodle",
                imageUrl = "https://raw.githubusercontent.com/doantaa/BinarFoodApp-Resource/main/ic_category_noodle.png"
            ),
            Category(
                name = "Snack",
                imageUrl = "https://raw.githubusercontent.com/doantaa/BinarFoodApp-Resource/main/ic_category_snack.png"
            ),
            Category(
                name = "Snack",
                imageUrl = "https://raw.githubusercontent.com/doantaa/BinarFoodApp-Resource/main/ic_category_snack.png"
            ),
            Category(
                name = "Snack",
                imageUrl = "https://raw.githubusercontent.com/doantaa/BinarFoodApp-Resource/main/ic_category_snack.png"
            ),
            Category(
                name = "Drink",
                imageUrl = "https://raw.githubusercontent.com/doantaa/BinarFoodApp-Resource/main/ic_category_drink.png"
            )
        )
    }

}