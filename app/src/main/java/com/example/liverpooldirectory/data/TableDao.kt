package com.example.liverpooldirectory.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TableDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTable(table: Table)

    @Query("SELECT * FROM epl_table ORDER BY position ASC")
    fun readAllData(): LiveData<List<Table>>

}