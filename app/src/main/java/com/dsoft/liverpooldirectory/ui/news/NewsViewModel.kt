package com.dsoft.liverpooldirectory.ui.news

import android.util.Log
import androidx.lifecycle.*
import com.dsoft.liverpooldirectory.model.News
import com.dsoft.liverpooldirectory.repository.NewsRepository
import com.dsoft.liverpooldirectory.utility.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel(), LifecycleObserver {

    private val isOnline = newsRepository.isOnline
    val readAllNews: LiveData<List<News>> = newsRepository.readAllNews
    val newsStatus: MutableLiveData<Resource<News>> = MutableLiveData()

    var count = 10

    init {
        if (isOnline) {
            deleteAllNews()
            safeCall()
        }
    }

    private fun deleteAllNews() {
        viewModelScope.launch(Dispatchers.IO) {
            newsRepository.deleteAllNews()
        }
    }

    fun safeCall() {
        newsStatus.postValue(Resource.Loading())
        try {
            newsRepository.downloadNews()
            readAllNews.observeForever { list ->
                val someList = if (!list.isNullOrEmpty()) list else return@observeForever
                newsStatus.postValue(Resource.Success(someList.first()))
            }
            count += 10
        } catch (t: Throwable) {
            when (t) {
                is IOException -> newsStatus.postValue(Resource.Error("No internet connection"))
                else -> {
                    t.printStackTrace()
                    newsStatus.postValue(Resource.Error("Conversion Error"))
                }
            }
        }
    }
}