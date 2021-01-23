package com.example.liverpooldirectory.internet.yandex_api

import android.app.Application
import com.yandex.metrica.YandexMetrica
import com.yandex.metrica.YandexMetricaConfig


class AppMetrica: Application() {

    private val YANDEX_API_KEY = "85be1141-c651-4689-86f6-400b45b41289"

    override fun onCreate() {
        super.onCreate()
        // Creating an extended library configuration.
        val config = YandexMetricaConfig.newConfigBuilder(YANDEX_API_KEY).build()

        // Initializing the AppMetrica SDK.
        YandexMetrica.activate(applicationContext, config)

        // Automatic tracking of user activity.
        YandexMetrica.enableActivityAutoTracking(this)
    }
}