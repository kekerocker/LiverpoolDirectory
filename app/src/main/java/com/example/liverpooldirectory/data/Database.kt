package com.example.liverpooldirectory.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.liverpooldirectory.model.CloseGames
import com.example.liverpooldirectory.model.News
import com.example.liverpooldirectory.model.Table

@Database(entities = [Table::class, CloseGames::class, News::class], version = 1, exportSchema = false)

abstract class LFCDatabase : RoomDatabase() {

    abstract fun tableDao(): TableDao
    abstract fun newsDao(): NewsDao

    companion object {
        @Volatile
        private var INSTANCE: LFCDatabase? = null

        fun createDatabase(context: Context): LFCDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LFCDatabase::class.java,
                    "LFC_database")
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}