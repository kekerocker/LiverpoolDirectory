package com.example.liverpooldirectory.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.liverpooldirectory.data.closegames.CloseGamesDao
import com.example.liverpooldirectory.data.epltable.TableDao
import com.example.liverpooldirectory.model.CloseGames
import com.example.liverpooldirectory.model.Table

@Database(entities = [Table::class], version = 1, exportSchema = false)
abstract class TableDatabase : RoomDatabase() {

    abstract fun tableDao(): TableDao

    companion object {
        @Volatile
        private var INSTANCE: TableDatabase? = null

        fun getTableDatabase(context: Context): TableDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TableDatabase::class.java,
                    "EPL_table"
                )
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}

@Database(entities = [CloseGames::class], version = 1, exportSchema = false)
abstract class CloseGamesDatabase : RoomDatabase() {

    abstract fun closeGamesDao(): CloseGamesDao

    companion object {
        @Volatile
        private var INSTANCE: CloseGamesDatabase? = null

        fun getDatabase(context: Context): CloseGamesDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CloseGamesDatabase::class.java,
                    "CloseGames_table"
                ).build()
                INSTANCE = instance
                return instance
            }
        }

    }
}