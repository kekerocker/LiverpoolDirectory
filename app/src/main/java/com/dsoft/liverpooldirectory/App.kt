package com.dsoft.liverpooldirectory

import android.app.Application
import com.yandex.metrica.YandexMetrica
import com.yandex.metrica.YandexMetricaConfig
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        configureYandexMetrica()
    }

    fun configureYandexMetrica() {
        val config = YandexMetricaConfig.newConfigBuilder(BuildConfig.YANDEX_API_KEY).build()
        YandexMetrica.activate(applicationContext, config)
        YandexMetrica.enableActivityAutoTracking(this)
    }
}