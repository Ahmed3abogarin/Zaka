package com.vtol.zaka.data.local

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import androidx.core.content.edit

class QuotaManager @Inject constructor(
    @param:ApplicationContext private val context: Context,
) {
    private val prefs = context.getSharedPreferences("quota", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_COUNT = "daily_count"
        private const val KEY_DATE = "last_date"
        private const val KEY_BONUS = "bonus_count"    // ← new
        private const val FREE_LIMIT = 3
    }
//
//    fun canStartQuiz(): Boolean {
//        resetIfNewDay()
//        return getCount() < FREE_LIMIT + getBonus()
//    }

    fun getRemainingQuizzes(): Int {
        resetIfNewDay()
        return (FREE_LIMIT + getBonus() - getCount()).coerceAtLeast(0)
    }

    fun grantBonusQuiz() {
        prefs.edit { putInt(KEY_BONUS, getBonus() + 1) }
    }

    private fun getBonus(): Int = prefs.getInt(KEY_BONUS, 0)

    private fun resetIfNewDay() {
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        val lastDate = prefs.getString(KEY_DATE, "") ?: ""
        if (today != lastDate) {
            prefs.edit {
                putInt(KEY_COUNT, 0)
                    .putInt(KEY_BONUS, 0)   // ← reset bonus too
                    .putString(KEY_DATE, today)
            }
        }
    }

    fun incrementCount() {
        prefs.edit { putInt(KEY_COUNT, getCount() + 1) }
    }

    fun getCount(): Int = prefs.getInt(KEY_COUNT, 0)
}