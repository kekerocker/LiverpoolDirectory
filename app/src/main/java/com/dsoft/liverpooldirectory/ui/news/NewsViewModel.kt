package com.dsoft.liverpooldirectory.ui.news

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dsoft.liverpooldirectory.model.News
import com.dsoft.liverpooldirectory.repository.NewsRepository
import com.dsoft.liverpooldirectory.utility.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    private val isOnline = newsRepository.isOnline
    val readAllNews: LiveData<List<News>> = newsRepository.readAllNews

    //val newsStatus: MutableLiveData<Resource<News>> = MutableLiveData()
    val isLoadingNews = MutableLiveData<Boolean>()
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
        viewModelScope.launch(Dispatchers.IO) {
            isLoadingNews.postValue(true)
            try {
                newsRepository.downloadNews()
                val news = readAllNews.value
                Resource.Success(news?.first()!!).let { isLoadingNews.postValue(false) }
                count += 10
            } catch (t: Throwable) {
                Log.e("NewsViewModel", t.toString())
                isLoadingNews.postValue(false)
            }
        }
    }
}