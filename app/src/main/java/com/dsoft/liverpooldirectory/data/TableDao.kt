package com.dsoft.liverpooldirectory.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dsoft.liverpooldirectory.model.CloseGames
import com.dsoft.liverpooldirectory.model.Table

@Dao
interface TableDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTable(table: Table)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCloseGames(closeGames: CloseGames)

    @Query("SELECT * FROM close_games_table")
    fun readAllCloseGamesData(): LiveData<List<CloseGames>>

    @Query("SELECT * FROM epl_table ORDER BY position ASC")
    fun readAllEplData(): LiveData<List<Table>>

    @Query("DELETE FROM close_games_table")
    suspend fun deleteAllCloseGamesData()

    @Query("DELETE FROM epl_table")
    suspend fun deleteAllTableData()
}