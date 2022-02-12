package com.dsoft.liverpooldirectory.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dsoft.liverpooldirectory.model.NewsData

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNews(news: NewsData)

    @Query("SELECT * FROM news_table")
    fun readAllNews(): LiveData<List<NewsData>>

    @Query("DELETE FROM news_table")
    suspend fun deleteAllNews()
}