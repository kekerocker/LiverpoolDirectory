package com.dsoft.liverpooldirectory

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.dsoft.liverpooldirectory.data.AppPreferences
import com.dsoft.liverpooldirectory.databinding.ActivityMainmenuBinding
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import com.yandex.metrica.YandexMetrica
import com.yandex.metrica.YandexMetricaConfig


class MainMenuActivity : AppCompatActivity() {

    private val currentTimeMillis = System.currentTimeMillis()

    private val yandexApiKey = "85be1141-c651-4689-86f6-400b45b41289"
    private var appPreferences: AppPreferences? = null
    private lateinit var binding: ActivityMainmenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainmenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        setupActionBarWithNavController(findNavController(R.id.fragment))
        appPreferences = AppPreferences(this)


        val config = YandexMetricaConfig.newConfigBuilder(yandexApiKey).build()
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
                appPreferences?.saveToken(accessToken)
                appPreferences?.saveTokenTime(currentTimeMillis)
            }

            override fun onLoginFailed(errorCode: Int) {
                Toast.makeText(applicationContext, "Неудачная попытка авторизации", Toast.LENGTH_LONG).show()
            }
        }
        if (data == null || !VK.onActivityResult(requestCode, resultCode, data, callback)) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}