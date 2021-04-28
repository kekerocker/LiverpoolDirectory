package com.dsoft.liverpooldirectory.ui.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dsoft.liverpooldirectory.model.News
import com.dsoft.liverpooldirectory.repository.NewsRepository
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

    init {
        if (isOnline) {
            deleteAllNews()
            downloadNews()
        }
    }

    private fun deleteAllNews() {
        viewModelScope.launch(Dispatchers.IO) {
            newsRepository.deleteAllNews()
        }
    }

    fun downloadNews() {
        viewModelScope.launch(Dispatchers.IO) {
            newsRepository.downloadNews()
        }
    }
}