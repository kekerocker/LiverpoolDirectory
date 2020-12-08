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
import com.example.liverpooldirectory.fragments.social.SocialRecyclerAdapter
import com.example.liverpooldirectory.vk.VKAPIRequest
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mainmenu)
        supportActionBar?.hide()
        setupActionBarWithNavController(findNavController(R.id.fragment))
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val callback = object : VKAuthCallback {
            override fun onLogin(token: VKAccessToken) {
                val tor = token.accessToken
                Toast.makeText(applicationContext, "Авторизация прошла успешно: Welcome to Republic of Liverpool!", Toast.LENGTH_LONG).show()
                makeVKAPIRequest(tor)
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

                for (item in response.response.items.map {
                    addList(
                        it.text,
                        it.comments.count.toString(),
                        it.likes.count.toString(),
                        it.views.count.toString() )
                })

                    withContext(Dispatchers.Main) {
                        removeLoginViews()
                        setUpRecyclerView()
                    }
            } catch (e: Exception) {
                Log.e("NewsActivity", e.toString())
            }
        }
    }

    private fun setUpRecyclerView() {
        social_recycler_view.layoutManager = LinearLayoutManager(applicationContext)
        social_recycler_view.adapter =
            SocialRecyclerAdapter(textList, likesList, commentsList, viewsList)
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
        social_recycler_view.visibility = View.VISIBLE
    }
}