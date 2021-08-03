package com.dsoft.liverpooldirectory.data

import android.content.Context
import android.content.SharedPreferences

class AppPreferences (context: Context) {
    var data: SharedPreferences = context.getSharedPreferences("APP_PREFERENCES", Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        data.edit().putString("TOKEN", token).apply()
    }

    fun savePostId(position: String) {
        data.edit().putString("POST_ID", position).apply()
    }

    fun saveTokenTime(time: Long) {
        data.edit().putLong("TIME", time).apply()
    }

    fun getPostId(): String {
        return data.getString("POST_ID", "").toString()
    }

    fun getTokenTime(): Long {
        return data.getLong("TIME", 0)
    }

    fun getToken(): String? {
        return data.getString("TOKEN", null)
    }
}