package com.dsoft.liverpooldirectory.usecase

import androidx.lifecycle.LiveData
import com.dsoft.liverpooldirectory.model.NewsData
import com.dsoft.liverpooldirectory.repository.NewsRepositoryImpl
import javax.inject.Inject

class NewsUseCase @Inject constructor(
    private val repository: NewsRepositoryImpl
) {

    val readAllNews: LiveData<List<NewsData>> get() = repository.getAllNews()

    suspend fun deleteAllNews() {
        repository.deleteAllNews()
    }

    fun downloadNews() {
        repository.downloadNews()
    }

}