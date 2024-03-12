package com.dsoft.liverpooldirectory2.usecase

import androidx.lifecycle.LiveData
import com.dsoft.liverpooldirectory2.model.NewsData
import com.dsoft.liverpooldirectory2.repository.NewsRepositoryImpl
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