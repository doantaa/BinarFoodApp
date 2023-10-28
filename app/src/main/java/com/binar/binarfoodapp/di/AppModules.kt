package com.binar.binarfoodapp.di

import com.binar.binarfoodapp.data.local.database.AppDatabase
import com.binar.binarfoodapp.data.local.database.datasource.CartDataSource
import com.binar.binarfoodapp.data.local.database.datasource.CartDataSourceImpl
import com.binar.binarfoodapp.data.local.datastore.UserPreferenceDataSource
import com.binar.binarfoodapp.data.local.datastore.UserPreferenceDataSourceImpl
import com.binar.binarfoodapp.data.local.datastore.appDataStore
import com.binar.binarfoodapp.data.network.api.datasource.RestaurantApiDataSource
import com.binar.binarfoodapp.data.network.api.datasource.RestaurantDataSource
import com.binar.binarfoodapp.data.network.api.service.RestaurantService
import com.binar.binarfoodapp.data.network.firebase.auth.FirebaseAuthDataSource
import com.binar.binarfoodapp.data.network.firebase.auth.FirebaseAuthDataSourceImpl
import com.binar.binarfoodapp.data.repository.CartRepository
import com.binar.binarfoodapp.data.repository.CartRepositoryImpl
import com.binar.binarfoodapp.data.repository.MenuRepository
import com.binar.binarfoodapp.data.repository.MenuRepositoryImpl
import com.binar.binarfoodapp.data.repository.UserRepository
import com.binar.binarfoodapp.data.repository.UserRepositoryImpl
import com.binar.binarfoodapp.presentation.authentication.login.LoginViewModel
import com.binar.binarfoodapp.presentation.authentication.register.RegisterViewModel
import com.binar.binarfoodapp.presentation.cart.CartViewModel
import com.binar.binarfoodapp.presentation.checkout.CheckoutViewModel
import com.binar.binarfoodapp.presentation.detail.DetailViewModel
import com.binar.binarfoodapp.presentation.editprofile.EditProfileViewModel
import com.binar.binarfoodapp.presentation.home.HomeViewModel
import com.binar.binarfoodapp.presentation.profile.ProfileViewModel
import com.binar.binarfoodapp.utils.PreferenceDataStoreHelper
import com.binar.binarfoodapp.utils.PreferenceDataStoreHelperImpl
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

object AppModules {
    private val localmodule = module {
        // Database
        single { AppDatabase.getInstance(androidContext()) }
        single { get<AppDatabase>().cartDao() }

        // Preference Datastore
        single { androidContext().appDataStore }
        single<PreferenceDataStoreHelper> { PreferenceDataStoreHelperImpl(get()) }
    }

    private val networkModule = module {
        single { ChuckerInterceptor(androidContext()) }
        single { RestaurantService.invoke(get()) }
        single { FirebaseAuth.getInstance() }
    }

    private val dataSourceModule = module {
        // Database
        single<CartDataSource> { CartDataSourceImpl(get()) }

        //User Preference
        single<UserPreferenceDataSource> { UserPreferenceDataSourceImpl(get()) }

        //Menu Data Source / RestaurantApi DataSource
        single<RestaurantDataSource> { RestaurantApiDataSource(get()) }

        //Firebase
        single<FirebaseAuthDataSource> { FirebaseAuthDataSourceImpl(get()) }
    }

    private val repositoryModule = module {
        // Database + Api
        single<CartRepository> { CartRepositoryImpl(get(), get()) }

        // Menu Repository
        single<MenuRepository> { MenuRepositoryImpl(get()) }

        // User Repository
        single<UserRepository> { UserRepositoryImpl(get()) }
    }

    private val viewModelModule = module {
        viewModelOf(::HomeViewModel)
        viewModelOf(::CartViewModel)
        viewModelOf(::LoginViewModel)
        viewModelOf(::RegisterViewModel)
        viewModelOf(::CheckoutViewModel)
        viewModelOf(::DetailViewModel)
        viewModelOf(::EditProfileViewModel)
        viewModelOf(::ProfileViewModel)
    }
    val modules = listOf(
        localmodule,
        networkModule,
        dataSourceModule,
        repositoryModule,
        viewModelModule
    )
}