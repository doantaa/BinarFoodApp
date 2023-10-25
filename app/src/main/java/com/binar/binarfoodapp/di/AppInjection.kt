package com.binar.binarfoodapp.di

import android.content.Context
import com.binar.binarfoodapp.data.local.database.AppDatabase
import com.binar.binarfoodapp.data.local.database.datasource.CartDataSourceImpl
import com.binar.binarfoodapp.data.local.datastore.UserPreferenceDataSourceImpl
import com.binar.binarfoodapp.data.local.datastore.appDataStore
import com.binar.binarfoodapp.data.network.api.datasource.RestaurantApiDataSource
import com.binar.binarfoodapp.data.network.api.service.RestaurantService
import com.binar.binarfoodapp.data.network.firebase.auth.FirebaseAuthDataSourceImpl
import com.binar.binarfoodapp.data.repository.CartRepositoryImpl
import com.binar.binarfoodapp.data.repository.MenuRepositoryImpl
import com.binar.binarfoodapp.data.repository.UserPreferenceRepository
import com.binar.binarfoodapp.data.repository.UserPreferenceRepositoryImpl
import com.binar.binarfoodapp.data.repository.UserRepositoryImpl
import com.binar.binarfoodapp.presentation.cart.CartViewModel
import com.binar.binarfoodapp.presentation.home.HomeViewModel
import com.binar.binarfoodapp.utils.GenericViewModelFactory
import com.binar.binarfoodapp.utils.PreferenceDataStoreHelperImpl
import com.google.firebase.auth.FirebaseAuth

object AppInjection {
    val menuApiService = RestaurantService.invoke()
    val menuDataSource = RestaurantApiDataSource(menuApiService)
    val firebaseDataSource = FirebaseAuthDataSourceImpl(FirebaseAuth.getInstance())

    //REPO GANG
    fun getMenuRepositoryFactory(): MenuRepositoryImpl {
        return MenuRepositoryImpl(menuDataSource)
    }

    fun getUserRepositoryFactory(): UserRepositoryImpl {
        return UserRepositoryImpl(firebaseDataSource)
    }

    fun getUserPreferenceRepositoryFactory(context: Context): UserPreferenceRepository {
        val dataStore = context.appDataStore
        val dataStoreHelper = PreferenceDataStoreHelperImpl(dataStore)
        val dataSource = UserPreferenceDataSourceImpl(dataStoreHelper)
        return UserPreferenceRepositoryImpl(dataSource)
    }

    fun getCartRepositoryFactory(context: Context): CartRepositoryImpl {
        val database = AppDatabase.getInstance(context)
        val cartDao = database.cartDao()
        val cartDataSource = CartDataSourceImpl(cartDao)
        return CartRepositoryImpl(cartDataSource, menuDataSource)
    }

    // VIEW MODEL GANG
    fun getHomeViewModelFactory(context: Context) = GenericViewModelFactory.create(
        HomeViewModel(
            getMenuRepositoryFactory(),
            getUserPreferenceRepositoryFactory(context),
            getUserRepositoryFactory()
        )
    )

    fun getCartViewModelFactory(context: Context) = GenericViewModelFactory.create(
        CartViewModel(getCartRepositoryFactory(context))
    )


}