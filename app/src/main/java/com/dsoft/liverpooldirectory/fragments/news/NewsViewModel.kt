package com.dsoft.liverpooldirectory.fragments.news

import android.app.Application
import android.app.Service
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.dsoft.liverpooldirectory.data.LFCDatabase
import com.dsoft.liverpooldirectory.model.News
import com.dsoft.liverpooldirectory.repository.NewsRepository
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

        connectivity = application.getSystemService(Service.CONNECTIVITY_SERVICE) as ConnectivityManager
        Toast.makeText(application, "Нажмите на новость, чтобы прочитать подробнее.", Toast.LENGTH_LONG).show()

        checkInternet()
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
        viewModelScope.launch(Dispatchers.IO) {
            if (connectivity != null) {
                info = connectivity!!.activeNetworkInfo

                if (info != null) {
                    if (info!!.state == NetworkInfo.State.CONNECTED) {
                        //Do when online0
                        deleteAllNews()
                        downloadNews()
                    }
                }
            }
        }
    }
}