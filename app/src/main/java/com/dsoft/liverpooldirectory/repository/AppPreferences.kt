package com.dsoft.liverpooldirectory.repository

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class AppPreferences @Inject constructor(context: Context) {
    var data: SharedPreferences = context.getSharedPreferences("APP_PREFERENCES", Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        data.edit().putString("TOKEN", token).apply()
    }

    fun savePosition(position: String) {
        data.edit().putString("POSITION", position).apply()
    }

    fun saveTokenTime(time: Long) {
        data.edit().putLong("TIME", time).apply()
    }

    fun getPosition(): String {
        return data.getString("POSITION", "").toString()
    }

    fun getTokenTime(): Long? {
        return data.getLong("TIME", 0)
    }

    fun getToken(): String? {
        return data.getString("TOKEN", null)
    }

    fun deleteToken() {
        data.edit().remove("TOKEN").apply()
    }
}