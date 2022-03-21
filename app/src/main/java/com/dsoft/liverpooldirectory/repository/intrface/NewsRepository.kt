package com.dsoft.liverpooldirectory.repository.intrface

import androidx.lifecycle.LiveData
import com.dsoft.liverpooldirectory.model.NewsData

interface NewsRepository {
    fun getAllNews(): LiveData<List<NewsData>>
    fun downloadNews()

    suspend fun deleteAllNews()
}