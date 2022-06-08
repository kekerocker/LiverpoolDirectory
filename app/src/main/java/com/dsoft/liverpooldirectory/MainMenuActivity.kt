package com.dsoft.liverpooldirectory

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.dsoft.liverpooldirectory.databinding.ActivityMainmenuBinding
import com.dsoft.liverpooldirectory.ui.social.SocialViewModel
import com.dsoft.liverpooldirectory.utility.ConnectionLiveData
import com.dsoft.liverpooldirectory.utility.LocaleHelper
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainMenuActivity : AppCompatActivity() {

    @Inject lateinit var connectionLiveData: ConnectionLiveData
    @Inject lateinit var localeHelper: LocaleHelper
    private val viewModel by viewModels<SocialViewModel>()

    private val currentTimeMillis = System.currentTimeMillis()

    private var _binding: ActivityMainmenuBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainmenuBinding.inflate(layoutInflater)
        configureLocale()
        setContentView(binding.root)
        supportActionBar?.hide()
        setupActionBarWithNavController(findNavController(R.id.fragment))

        connectionLiveData.observe(this) { isNetworkAvailable ->
            if (isNetworkAvailable == null) return@observe

        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val callback = object : VKAuthCallback {
            override fun onLogin(token: VKAccessToken) {
                val accessToken = token.accessToken
                Log.d("Token", "Access token is $accessToken")
                with(viewModel) {
                    appPreferences.saveToken(accessToken)
                    appPreferences.saveTokenTime(currentTimeMillis)
                    interactor.isSuccess = true
                }
            }

            override fun onLoginFailed(errorCode: Int) {
                Toast.makeText(
                    applicationContext, "Неудачная попытка авторизации", Toast.LENGTH_SHORT).show()
            }
        }
        if (data == null || !VK.onActivityResult(requestCode, resultCode, data, callback)) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun configureLocale() {
        val sharedPreferences = this.getSharedPreferences("APP_PREFERENCES", Context.MODE_PRIVATE)
        val loc = Resources.getSystem().configuration.locale.language
        val language = sharedPreferences.getString("Language", loc).toString()
        sharedPreferences.edit().putString("System_language", loc).apply()

        localeHelper.setLocale(this, language)
    }
}