package com.dsoft.liverpooldirectory.data

import android.content.Context
import android.content.SharedPreferences

class AppPreferences(context: Context) {
    var data: SharedPreferences = context.getSharedPreferences("APP_PREFERENCES", Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        data.edit().putString("TOKEN", token).apply()
    }

    fun savePosition(position: String) {
        data.edit().putString("POSITION", position).apply()
    }

    fun getToken(): String {
        return data.getString("TOKEN", "").toString()
    }

    fun getPosition(): String {
        return data.getString("POSITION", "").toString()
    }
}