package com.dsoft.liverpooldirectory

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.dsoft.liverpooldirectory.databinding.ActivityMainmenuBinding
import com.dsoft.liverpooldirectory.ui.social.SocialViewModel
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import com.yandex.metrica.YandexMetrica
import com.yandex.metrica.YandexMetricaConfig
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainMenuActivity : AppCompatActivity() {

    private val currentTimeMillis = System.currentTimeMillis()

    private val viewModel by viewModels<SocialViewModel>()

    private lateinit var binding: ActivityMainmenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainmenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        setupActionBarWithNavController(findNavController(R.id.fragment))


        val config = YandexMetricaConfig.newConfigBuilder(BuildConfig.YANDEX_API_KEY).build()
        YandexMetrica.activate(applicationContext, config)
        YandexMetrica.enableActivityAutoTracking(application)
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
                viewModel.appPreferences.run {
                    saveToken(accessToken)
                    saveTokenTime(currentTimeMillis)
                }
            }

            override fun onLoginFailed(errorCode: Int) {
                Toast.makeText(
                    applicationContext, "Неудачная попытка авторизации", Toast.LENGTH_LONG).show()
            }
        }
        if (data == null || !VK.onActivityResult(requestCode, resultCode, data, callback)) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}