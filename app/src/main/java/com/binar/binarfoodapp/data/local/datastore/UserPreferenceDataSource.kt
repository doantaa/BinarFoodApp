package com.binar.binarfoodapp.data.local.datastore

import androidx.datastore.preferences.core.booleanPreferencesKey
import com.binar.binarfoodapp.utils.PreferenceDataStoreHelper
import kotlinx.coroutines.flow.Flow

interface UserPreferenceDataSource {
    fun getUserListViewModePrefFlow(): Flow<Boolean>
    suspend fun setUserListViewModePreference(isLinear : Boolean)
    suspend fun getUserListViewModePreference(): Boolean

}

class UserPreferenceDataSourceImpl(
    private val dataStoreHelper: PreferenceDataStoreHelper
) : UserPreferenceDataSource {
    override fun getUserListViewModePrefFlow(): Flow<Boolean> {
        return dataStoreHelper.getPreference(PREF_USER_LIST_VIEW, false)
    }

    override suspend fun setUserListViewModePreference(isLinear: Boolean) {
        dataStoreHelper.putPreference(PREF_USER_LIST_VIEW, isLinear)
    }

    override suspend fun getUserListViewModePreference(): Boolean {
        return dataStoreHelper.getFirstPreference(PREF_USER_LIST_VIEW, false)
    }

    companion object {
        val PREF_USER_LIST_VIEW =  booleanPreferencesKey("PREF_USER_LIST_VIEW")
    }

}