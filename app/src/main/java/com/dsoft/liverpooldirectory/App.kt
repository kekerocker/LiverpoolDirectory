package com.dsoft.liverpooldirectory

import android.app.Application
import android.content.Context
import android.content.res.Resources
import com.dsoft.liverpooldirectory.utility.LocaleHelper
import com.yandex.metrica.YandexMetrica
import com.yandex.metrica.YandexMetricaConfig
import dagger.hilt.android.HiltAndroidApp
import java.util.*

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        configureYandexMetrica()
        configureLocale()
    }

    private fun configureYandexMetrica() {
        val config = YandexMetricaConfig.newConfigBuilder(BuildConfig.YANDEX_API_KEY).build()
        YandexMetrica.activate(applicationContext, config)
        YandexMetrica.enableActivityAutoTracking(this)
    }

    private fun configureLocale() {
        val sharedPreferences = getSharedPreferences("APP_PREFERENCES", Context.MODE_PRIVATE)
        val loc = Resources.getSystem().configuration.locale.language
        val language = sharedPreferences.getString("Language", loc).toString()
        sharedPreferences.edit().putString("System_language", loc).apply()

        LocaleHelper.setLocale(this, language)
    }
}