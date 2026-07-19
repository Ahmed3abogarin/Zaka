package com.vtol.zaka.data.repository

import androidx.datastore.preferences.core.booleanPreferencesKey

object AppPrefs {
    val ONBOARDING_COMPLETED = booleanPreferencesKey("onboarding_completed")
}