package com.dsoft.liverpooldirectory.usecase

import androidx.lifecycle.LiveData
import com.dsoft.liverpooldirectory.model.NewsData
import com.dsoft.liverpooldirectory.repository.NewsRepository
import javax.inject.Inject

class NewsUseCase @Inject constructor(
    private val repository: NewsRepository
) {

    val readAllNews: LiveData<List<NewsData>> get() = repository.readAllNews

    suspend fun deleteAllNews() {
        repository.deleteAllNews()
    }

    fun downloadNews() {
        repository.downloadNews()
    }

}