package com.dsoft.liverpooldirectory.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dsoft.liverpooldirectory.model.CloseGamesData
import com.dsoft.liverpooldirectory.model.TableData
import kotlinx.coroutines.flow.Flow

@Dao
interface TableDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTable(table: List<TableData>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCloseGames(closeGames: CloseGamesData)

    @Query("SELECT * FROM close_games_table ORDER BY date ASC")
    fun readAllCloseGamesData(): Flow<List<CloseGamesData>>

    @Query("SELECT * FROM epl_table ORDER BY position ASC")
    fun readAllEplData(): Flow<List<TableData>>

    @Query("DELETE FROM close_games_table")
    suspend fun deleteAllCloseGamesData()

    @Query("DELETE FROM epl_table")
    suspend fun deleteAllTableData()
}