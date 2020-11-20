                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             package com.example.liverpooldirectory

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.liverpooldirectory.adapters.RecyclerAdapter
import kotlinx.android.synthetic.main.activity_news.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "http://newsapi.org/"

class NewsActivity : AppCompatActivity() {

    private var titleList = mutableListOf<String>()
    private var descList = mutableListOf<String>()
    private var contList = mutableListOf<String>()
    private var imagesList = mutableListOf<String>()
    private var linksList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        supportActionBar?.hide()

        makeAPIRequest()
    }

    private fun fadeInFromBlack() {
        v_blackScreen.animate().apply {
            alpha(0f)
            duration = 3000
        }.start()
    }

    private fun setUpRecyclerView() {
        rv_recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        rv_recyclerView.adapter = RecyclerAdapter(titleList, descList, contList, imagesList, linksList)
    }

    private fun addToList(title: String, description: String, content: String, image: String, link: String) {
        titleList.add(title)
        descList.add(description)
        contList.add(content)
        imagesList.add(image)
        linksList.add(link)
    }

    private fun makeAPIRequest() {
        progressBar.visibility = View.VISIBLE

        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIRequest::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = api.getNews()

                for (article in response.articles) {
                    addToList(article.title, article.description, article.content, article.urlToImage, article.url)
                }
                withContext(Dispatchers.Main){
                    setUpRecyclerView()
                    fadeInFromBlack()
                    progressBar.visibility = View.GONE
                }
            } catch (e: Exception) {
                Log.e("NewsActivity", e.toString())
            }
        }
    }
}

