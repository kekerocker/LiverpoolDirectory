package com.example.liverpooldirectory.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Table::class], version = 1, exportSchema = false)
abstract class TableDatabase : RoomDatabase() {

    abstract fun tableDao(): TableDao

    companion object {
        @Volatile
        private var INSTANCE: TableDatabase? = null

        fun getDatabase(context: Context): TableDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TableDatabase::class.java,
                    "EPL_table"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}