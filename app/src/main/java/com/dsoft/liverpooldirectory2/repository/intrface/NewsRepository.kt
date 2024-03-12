package com.dsoft.liverpooldirectory2.repository.intrface

import androidx.lifecycle.LiveData
import com.dsoft.liverpooldirectory2.model.NewsData

interface NewsRepository {
    fun getAllNews(): LiveData<List<NewsData>>
    fun downloadNews()

    suspend fun deleteAllNews()
}