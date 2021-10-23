package com.dsoft.liverpooldirectory.utility

import android.content.Context
import android.net.ConnectivityManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class InternetConnection @Inject constructor(@ApplicationContext val context: Context) {

    fun isOnlineDeprecated(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val wifiConnection = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        val mobileConnection = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)

        return wifiConnection != null && wifiConnection.isConnected || mobileConnection != null && mobileConnection.isConnected
    }

}