package com.example.liverpooldirectory.fragments.news

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.liverpooldirectory.R
import com.example.liverpooldirectory.retrofit.APIRequest
import kotlinx.android.synthetic.main.fragment_news.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "http://newsapi.org/"
class NewsFragment : Fragment() {

    private var titleList = mutableListOf<String>()
    private var descList = mutableListOf<String>()
    private var contList = mutableListOf<String>()
    private var imagesList = mutableListOf<String>()
    private var linksList = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_news, container, false)

        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        makeAPIRequest()
    }

    private fun fadeInFromBlack() {
        v_blackScreen.animate().apply {
            alpha(0f)
            duration = 3000
        }.start()
    }

    private fun setUpRecyclerView() {
        rv_recyclerView.layoutManager = LinearLayoutManager(requireContext())
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