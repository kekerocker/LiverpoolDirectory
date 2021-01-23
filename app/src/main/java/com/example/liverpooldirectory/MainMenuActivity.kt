package com.example.liverpooldirectory

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.liverpooldirectory.databinding.ActivityMainmenuBinding
import com.example.liverpooldirectory.fragments.social.SocialRecyclerAdapter
import com.example.liverpooldirectory.internet.socialapi.VKAPIRequest
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import com.yandex.metrica.YandexMetrica
import com.yandex.metrica.YandexMetricaConfig
import kotlinx.android.synthetic.main.fragment_social.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val VK_BASE_URL = "https://api.vk.com/method/"

class MainMenuActivity : AppCompatActivity() {

    private var textList = mutableListOf<String>()
    private var likesList = mutableListOf<String>()
    private var commentsList = mutableListOf<String>()
    private var viewsList = mutableListOf<String>()

    private val yandexApiKey = "85be1141-c651-4689-86f6-400b45b41289"

    private lateinit var binding: ActivityMainmenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainmenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        setupActionBarWithNavController(findNavController(R.id.fragment))

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
                Log.d("TOKEN", "Access token is $accessToken")
                Toast.makeText(applicationContext, "Авторизация прошла успешно: Welcome to Republic of Liverpool!", Toast.LENGTH_LONG).show()
                makeVKAPIRequest(accessToken)
            }

            override fun onLoginFailed(errorCode: Int) {
                Toast.makeText(applicationContext, "Неудачная попытка авторизации", Toast.LENGTH_LONG).show()
            }
        }
        if (data == null || !VK.onActivityResult(requestCode, resultCode, data, callback)) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun makeVKAPIRequest(token: String) {
        val api = Retrofit.Builder()
            .baseUrl(VK_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(VKAPIRequest::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = api.getWall(token)

                for (item in response.response.items) {
                    addList(
                        item.text,
                        item.likes.count.toString(),
                        item.comments.count.toString(),
                        item.views.count.toString()
                    )
                }
                withContext(Dispatchers.Main) {
                    removeLoginViews()
                    setUpRecyclerView()
                }
            } catch (e: Exception) {
                Log.e("Social95", e.toString())
            }
        }
    }

    private fun setUpRecyclerView() {
        social_recycler_view.layoutManager = LinearLayoutManager(applicationContext)
        social_recycler_view.adapter = SocialRecyclerAdapter(textList, likesList, commentsList, viewsList)
    }

    private fun addList(text: String, likes: String, comments: String, views: String) {
        textList.add(text)
        likesList.add(likes)
        commentsList.add(comments)
        viewsList.add(views)
    }

    private fun removeLoginViews() {
        tv_vk_text.visibility = View.GONE
        iv_vk.visibility = View.GONE
        tv_social_title.visibility = View.VISIBLE
        recycler_layout.visibility = View.VISIBLE
    }
}