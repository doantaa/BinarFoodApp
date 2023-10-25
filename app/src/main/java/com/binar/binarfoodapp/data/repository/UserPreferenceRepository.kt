package com.binar.binarfoodapp.data.repository

import com.binar.binarfoodapp.data.local.datastore.UserPreferenceDataSourceImpl
import kotlinx.coroutines.flow.Flow

interface UserPreferenceRepository{
    fun getUserListViewModePrefFlow(): Flow<Boolean>
    suspend fun setUserListViewModePreference(isLinear : Boolean)
    suspend fun getUserListViewModePreference(): Boolean
}

class UserPreferenceRepositoryImpl(
    private val dataSource: UserPreferenceDataSourceImpl
) : UserPreferenceRepository {
    override fun getUserListViewModePrefFlow(): Flow<Boolean> {
        return dataSource.getUserListViewModePrefFlow()
    }

    override suspend fun setUserListViewModePreference(isLinear: Boolean) {
        return dataSource.setUserListViewModePreference(isLinear)
    }

    override suspend fun getUserListViewModePreference(): Boolean {
        return dataSource.getUserListViewModePreference()
    }

}