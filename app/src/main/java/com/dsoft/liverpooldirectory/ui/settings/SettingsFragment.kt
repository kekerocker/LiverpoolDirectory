package com.dsoft.liverpooldirectory.ui.settings

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.dsoft.liverpooldirectory.R
import com.dsoft.liverpooldirectory.databinding.FragmentSettingsBinding
import com.dsoft.liverpooldirectory.service.NightModeService
import javax.inject.Inject

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private val binding by viewBinding(FragmentSettingsBinding::bind)

    @Inject
    lateinit var nightModeService: NightModeService

    @Inject
    lateinit var sharedPreferences: SharedPreferences

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

    }

    private fun restart() {
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = context?.applicationContext?.packageManager?.getLaunchIntentForPackage(requireActivity().applicationContext.packageName)
            intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                ?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                ?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }, 100)
    }

    private fun showAlertLanguage() {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle(resources.getString(R.string.app_language))
        builder.setMessage(resources.getString(R.string.language_change))
        builder.setPositiveButton(
            resources.getString(R.string.yes)
        ) { dialog: DialogInterface, which: Int ->
            val language: String =
                getLanguageForRadioButtonId(binding.rgLanguage.checkedRadioButtonId)
            val country: String = getCountryForLanguage(language)
            LocaleHelper.setLocale(requireContext(), country)
            setLocale(language, country)
            restart()
        }
        builder.setNegativeButton(resources.getString(R.string.no)) { dialog: DialogInterface, which: Int ->
            setAndRemoveListenerForLanguage(false)
            setCurrentLanguageCheck()
            setAndRemoveListenerForLanguage(true)
            dialog.dismiss()
        }
        builder.setCancelable(false)
        val alert = builder.create()
        alert.show()
    }

    private fun getLanguageForRadioButtonId(radioButtonId: Int): String {
        return if (radioButtonId == getRadioButtonIdForLanguage(LANGUAGE_RUSSIAN)) {
            LANGUAGE_RUSSIAN
        } else {
            LANGUAGE_ENGLISH
        }
    }

    private fun setCurrentLanguageCheck() {
        val systemLang = sharedPreferences.getString("System_language", "").toString()
        val languageFromPrefs = sharedPreferences.getString("Language", "").toString()

        if (sharedPreferences.getString("Language", "") == "") {
            binding.rgLanguage.check(getRadioButtonIdForLanguage(systemLang))
        } else {
            binding.rgLanguage.check(getRadioButtonIdForLanguage(languageFromPrefs))
        }
    }
}