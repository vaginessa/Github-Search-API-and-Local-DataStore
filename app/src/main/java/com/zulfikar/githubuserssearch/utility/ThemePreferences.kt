package com.zulfikar.githubuserssearch.utility

import android.content.Context
import android.content.SharedPreferences

class ThemePreferences(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("theme_prefs", Context.MODE_PRIVATE)

    fun isDarkModeActive(): Boolean {
        return sharedPreferences.getBoolean("is_dark_mode_active", false)
    }

    fun setDarkModeActive(isDarkModeActive: Boolean) {
        sharedPreferences.edit().putBoolean("is_dark_mode_active", isDarkModeActive).apply()
    }

}