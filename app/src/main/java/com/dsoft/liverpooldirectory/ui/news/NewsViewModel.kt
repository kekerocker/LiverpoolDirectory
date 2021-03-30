package com.dsoft.liverpooldirectory.ui.news

import android.app.Service
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dsoft.liverpooldirectory.model.News
import com.dsoft.liverpooldirectory.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    @ApplicationContext context: Context
) : ViewModel() {

    var connectivity: ConnectivityManager? = null
    var info: NetworkInfo? = null

    val readAllNews: LiveData<List<News>> = newsRepository.readAllNews

    init {

        connectivity = context.getSystemService(Service.CONNECTIVITY_SERVICE) as ConnectivityManager
        Toast.makeText(context, "Нажмите на новость, чтобы прочитать подробнее.", Toast.LENGTH_LONG).show()

        checkInternet()
    }

    fun deleteAllNews() {
        viewModelScope.launch(Dispatchers.IO) {
            newsRepository.deleteAllNews()
        }
    }

    fun downloadNews() {
        viewModelScope.launch(Dispatchers.IO) {
            newsRepository.downloadNews()
        }
    }

    fun checkInternet() {
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