package com.dsoft.liverpooldirectory.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dsoft.liverpooldirectory.model.CloseGames
import com.dsoft.liverpooldirectory.model.News
import com.dsoft.liverpooldirectory.model.Table

@Database(
    entities = [Table::class, CloseGames::class, News::class],
    version = 2,
    exportSchema = false
)
abstract class LFCDatabase : RoomDatabase() {
    abstract fun tableDao(): TableDao
    abstract fun newsDao(): NewsDao
}