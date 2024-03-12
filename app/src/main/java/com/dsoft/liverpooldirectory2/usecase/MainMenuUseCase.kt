package com.dsoft.liverpooldirectory2.usecase

import androidx.lifecycle.LiveData
import com.dsoft.liverpooldirectory2.model.NewsData
import com.dsoft.liverpooldirectory2.repository.NewsRepositoryImpl
import javax.inject.Inject

class MainMenuUseCase @Inject constructor(
    private val newsRepository: NewsRepositoryImpl
) {

    val readAllNews: LiveData<List<NewsData>> get() = newsRepository.getAllNews()

    suspend fun deleteAllNews() {
        newsRepository.deleteAllNews()
    }

    fun downloadNews() {
        newsRepository.downloadNews()
    }

}