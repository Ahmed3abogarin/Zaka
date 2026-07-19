package com.vtol.zaka.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.vtol.zaka.domain.repository.UserDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserDataStoreImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
): UserDataStore {
    override fun isOnboardingCompleted(): Flow<Boolean> =
        dataStore.data.map {
            it[AppPrefs.ONBOARDING_COMPLETED] ?: false
        }

    override suspend fun setOnboardingCompleted(completed: Boolean) {
        dataStore.edit {
            it[AppPrefs.ONBOARDING_COMPLETED] = completed
        }
    }
}