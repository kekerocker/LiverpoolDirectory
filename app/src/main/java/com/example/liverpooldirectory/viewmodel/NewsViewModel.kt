package com.example.liverpooldirectory.viewmodel

import android.app.Application
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.liverpooldirectory.data.LFCDatabase
import com.example.liverpooldirectory.model.News
import com.example.liverpooldirectory.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel(application: Application) : AndroidViewModel(application) {

    var connectivity: ConnectivityManager? = null
    var info: NetworkInfo? = null

    val readAllNews: LiveData<List<News>>
    private val newsRepository: NewsRepository

    init {
        val newsDao = LFCDatabase.createDatabase(application).newsDao()
        newsRepository = NewsRepository(newsDao)
        readAllNews = newsRepository.readAllNews

        deleteAllNews()
        downloadNews()
    }

    private fun deleteAllNews() {
        viewModelScope.launch(Dispatchers.IO) {
            newsRepository.deleteAllNews()
        }
    }

    private fun downloadNews() {
        viewModelScope.launch(Dispatchers.IO) {
            newsRepository.downloadNews()
        }
    }

    private fun checkInternet() {
        if (connectivity != null) {
            info = connectivity!!.activeNetworkInfo

            if (info != null) {
                if (info!!.state == NetworkInfo.State.CONNECTED) {
                    //Do when online0
                    deleteAllNews()
                    //downloadNews()
                    Toast.makeText(getApplication(), "Тест", Toast.LENGTH_SHORT).show()
                }
            } else {
                //Do when offline
                Toast.makeText(getApplication(), "Нет соединения с интернетом.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}