package com.dsoft.liverpooldirectory.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dsoft.liverpooldirectory.model.CloseGames
import com.dsoft.liverpooldirectory.model.Comments
import com.dsoft.liverpooldirectory.model.News
import com.dsoft.liverpooldirectory.model.Table

@Database(entities = [Table::class, CloseGames::class, News::class, Comments::class], version = 2, exportSchema = false)

abstract class LFCDatabase : RoomDatabase() {

    abstract fun tableDao(): TableDao
    abstract fun newsDao(): NewsDao
    abstract fun commentsDao(): CommentsDao

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
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}