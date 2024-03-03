package com.dsoft.liverpooldirectory.usecase

import androidx.lifecycle.LiveData
import com.dsoft.liverpooldirectory.model.NewsData
import com.dsoft.liverpooldirectory.repository.NewsRepositoryImpl
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