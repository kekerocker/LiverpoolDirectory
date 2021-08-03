package com.dsoft.liverpooldirectory

import android.app.Application
import android.content.Context
import android.content.res.Resources
import com.dsoft.liverpooldirectory.utility.LocaleHelper
import com.yandex.metrica.YandexMetrica
import com.yandex.metrica.YandexMetricaConfig
import dagger.hilt.android.HiltAndroidApp
import java.util.*
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        configureYandexMetrica()
    }

    private fun configureYandexMetrica() {
        val config = YandexMetricaConfig.newConfigBuilder(BuildConfig.YANDEX_API_KEY).build()
        YandexMetrica.activate(applicationContext, config)
        YandexMetrica.enableActivityAutoTracking(this)
    }
}