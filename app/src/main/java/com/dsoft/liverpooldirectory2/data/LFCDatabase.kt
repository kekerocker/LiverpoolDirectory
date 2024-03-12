package com.dsoft.liverpooldirectory2.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dsoft.liverpooldirectory2.model.CloseGamesData
import com.dsoft.liverpooldirectory2.model.NewsData
import com.dsoft.liverpooldirectory2.model.TableData

@Database(
    entities = [TableData::class, CloseGamesData::class, NewsData::class],
    version = 2,
    exportSchema = false
)
abstract class LFCDatabase : RoomDatabase() {
    abstract fun tableDao(): TableDao
    abstract fun newsDao(): NewsDao
}