package com.dsoft.liverpooldirectory.ui.settings

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.dsoft.liverpooldirectory.R
import com.dsoft.liverpooldirectory.data.AppPreferences
import com.dsoft.liverpooldirectory.databinding.FragmentSettingsBinding
import com.dsoft.liverpooldirectory.service.NightModeService
import com.dsoft.liverpooldirectory.utility.LocaleHelper
import com.jakewharton.processphoenix.ProcessPhoenix
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private val binding by viewBinding(FragmentSettingsBinding::bind)

    @Inject
    lateinit var nightModeService: NightModeService

    @Inject
    lateinit var sharedPreferences: AppPreferences

    @Inject
    lateinit var localeHelper: LocaleHelper

    companion object {
        private const val LANGUAGE_ENGLISH = "en"
        private const val LANGUAGE_ENGLISH_COUNTRY = "US"
        private const val LANGUAGE_RUSSIAN = "ru"
        private const val LANGUAGE_RUSSIAN_COUNTRY = "RU"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        setCurrentLanguageCheck()
        setAndRemoveListenerForLanguage(true)
        setCurrentNightModeCheck()
        setAndRemoveListenerForNightMode(true)

    }

    private fun setAndRemoveListenerForNightMode(needSet: Boolean) {
        binding.rgTheme.setOnCheckedChangeListener(
            if (!needSet) null else RadioGroup.OnCheckedChangeListener { _: RadioGroup, _: Int -> showAlertDarkMode() }
        )
    }

    private fun showAlertDarkMode() {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle(R.string.app_theme)
        builder.setMessage(R.string.app_theme_restart)
        builder.setPositiveButton(R.string.yes)
        { _: DialogInterface, _: Int -> nightModeService.saveNightMode(
            getNightModeForRadioButtonId(binding.rgTheme.checkedRadioButtonId)
        )
        restart()
        }
        builder.setNegativeButton(R.string.no)
        { dialog: DialogInterface, _: Int ->
            setAndRemoveListenerForNightMode(false)
            setCurrentNightModeCheck()
            setAndRemoveListenerForNightMode(true)
            dialog.dismiss()
        }
        builder.setCancelable(false)
        val alert = builder.create()
        alert.show()
    }

    private fun getNightModeForRadioButtonId(radioButtonId: Int): NightModeService.NightModes {
        return when (radioButtonId) {
            getRadioButtonIdForNightMode(2) -> NightModeService.NightModes.NIGHT
            getRadioButtonIdForNightMode(1) -> NightModeService.NightModes.LIGHT
            getRadioButtonIdForNightMode(-1) -> NightModeService.NightModes.FOLLOW_SYSTEM
            else -> NightModeService.NightModes.FOLLOW_SYSTEM
        }
    }

    private fun setCurrentNightModeCheck() {
        binding.rgTheme.check(getRadioButtonIdForNightMode(nightModeService.getSavedNightMode()))
    }

    private fun getRadioButtonIdForNightMode(nightMode: Int): Int {
        return when (nightMode) {
            2 -> R.id.rb_dark_theme
            1 -> R.id.rb_light_theme
            -1 -> R.id.rb_follow_system
            else -> R.id.rb_follow_system
        }
    }

    private fun restart() {
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = context?.applicationContext?.packageManager?.getLaunchIntentForPackage(requireActivity().applicationContext.packageName)
            intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                ?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                ?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            ProcessPhoenix.triggerRebirth(requireContext(), intent)
        }, 150)
    }

    private fun showAlertLanguage() {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle(resources.getString(R.string.app_language))
        builder.setMessage(resources.getString(R.string.app_language_restart))
        builder.setPositiveButton(
            resources.getString(R.string.yes)
        ) { _: DialogInterface, _: Int ->
            val language: String =
                getLanguageForRadioButtonId(binding.rgLanguage.checkedRadioButtonId)
            val country: String = getCountryForLanguage(language)
            localeHelper.setLocale(requireContext(), country)
            setLocale(language, country)
            restart()
        }
        builder.setNegativeButton(resources.getString(R.string.no)) { dialog: DialogInterface, _: Int ->
            setAndRemoveListenerForLanguage(false)
            setCurrentLanguageCheck()
            setAndRemoveListenerForLanguage(true)
            dialog.dismiss()
        }
        builder.setCancelable(false)
        val alert = builder.create()
        alert.show()
    }

    private fun setAndRemoveListenerForLanguage(needSet: Boolean) {
        binding.rgLanguage.setOnCheckedChangeListener(
            if (!needSet) null else RadioGroup.OnCheckedChangeListener { _: RadioGroup, _: Int -> showAlertLanguage() }
        )
    }

    private fun setLocale(language: String, country: String) {
        localeHelper.setLocale(requireContext(), language)
        sharedPreferences.data.edit().putString("Language", language)
            .putString("Country", country).apply()
    }

    private fun getCountryForLanguage(language: String): String {
        return when (language) {
            LANGUAGE_RUSSIAN -> LANGUAGE_RUSSIAN_COUNTRY
            LANGUAGE_ENGLISH -> LANGUAGE_ENGLISH_COUNTRY
            else -> LANGUAGE_ENGLISH_COUNTRY
        }
    }

    private fun getLanguageForRadioButtonId(radioButtonId: Int): String {
        return if (radioButtonId == getRadioButtonIdForLanguage(LANGUAGE_RUSSIAN)) {
            LANGUAGE_RUSSIAN
        } else {
            LANGUAGE_ENGLISH
        }
    }

    private fun setCurrentLanguageCheck() {
        val systemLang = sharedPreferences.data.getString("System_language", "").toString()
        val languageFromPrefs = sharedPreferences.data.getString("Language", "").toString()

        if (sharedPreferences.data.getString("Language", "") == "") {
            binding.rgLanguage.check(getRadioButtonIdForLanguage(systemLang))
        } else {
            binding.rgLanguage.check(getRadioButtonIdForLanguage(languageFromPrefs))
        }
    }

    private fun getRadioButtonIdForLanguage(language: String): Int {
        return when (language) {
            LANGUAGE_RUSSIAN -> R.id.rb_russian
            LANGUAGE_ENGLISH -> R.id.rb_english
            else -> R.id.rb_russian
        }
    }


}