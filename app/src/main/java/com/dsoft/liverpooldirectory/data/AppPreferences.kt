package com.dsoft.liverpooldirectory.data

import android.content.Context
import android.content.SharedPreferences

class AppPreferences (private val context: Context) {

    val data: SharedPreferences get() = context.getSharedPreferences("APP_PREFERENCES", Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        data.edit().putString("TOKEN", token).apply()
    }

    fun saveTokenTime(time: Long) {
        data.edit().putLong("TIME", time).apply()
    }

    fun getTokenTime(): Long {
        return data.getLong("TIME", 0)
    }

    fun getToken(): String? {
        return data.getString("TOKEN", null)
    }
}