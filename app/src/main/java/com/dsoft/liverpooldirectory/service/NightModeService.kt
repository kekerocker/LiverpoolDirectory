package com.dsoft.liverpooldirectory.service

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import com.dsoft.liverpooldirectory.data.AppPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NightModeService @Inject constructor(
    val sharedPrefs: AppPreferences,
    @ApplicationContext val context: Context
) {

    companion object {
        private const val PREF_KEY_NIGHT_MODE = "NightModeService_NIGHT_MODE"
    }

    var intValue: Int = 0

    fun saveNightMode(nightMode: NightModes) {
        intValue = when (nightMode) {
            NightModes.NIGHT -> 2
            NightModes.LIGHT -> 1
            NightModes.FOLLOW_SYSTEM -> -1
        }
        saveNightModeInSharedPreferences(intValue)
    }

    fun setCurrentNightMode() {
        AppCompatDelegate.setDefaultNightMode(getSavedNightMode())
    }

    fun getSavedNightMode(): Int {
        val defaultValue = getDefaultNightMode()
        return when (sharedPrefs.data.getInt(PREF_KEY_NIGHT_MODE, 0)) {
            AppCompatDelegate.MODE_NIGHT_YES -> 2
            AppCompatDelegate.MODE_NIGHT_NO -> 1
            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM -> -1
            else -> defaultValue
        }
    }

    private fun getDefaultNightMode(): Int {
        return when (AppCompatDelegate.getDefaultNightMode()) {
            AppCompatDelegate.MODE_NIGHT_YES -> 2
            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM -> -1
            AppCompatDelegate.MODE_NIGHT_UNSPECIFIED -> {
                when (context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                    Configuration.UI_MODE_NIGHT_YES -> return 2
                    Configuration.UI_MODE_NIGHT_NO, Configuration.UI_MODE_NIGHT_UNDEFINED -> {
                    }
                    else -> {
                    }
                }
                1
            }
            AppCompatDelegate.MODE_NIGHT_NO, AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY -> 1
            else -> 1
        }
    }

    enum class NightModes(modeNightNo: Int) {
        LIGHT(AppCompatDelegate.MODE_NIGHT_NO),
        NIGHT(AppCompatDelegate.MODE_NIGHT_YES),
        FOLLOW_SYSTEM(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
    }

    private fun saveNightModeInSharedPreferences(nightMode: Int) {
        sharedPrefs.data.edit().putInt(PREF_KEY_NIGHT_MODE, nightMode).apply()
    }
}