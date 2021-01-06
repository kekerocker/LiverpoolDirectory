package com.example.liverpooldirectory.data.closegames

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.liverpooldirectory.model.CloseGames

@Dao
interface CloseGamesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCloseGames(closeGames: CloseGames)

    @Query("SELECT * FROM close_games_table")
    fun readAllData(): LiveData<List<CloseGames>>

    @Query("DELETE FROM close_games_table")
    suspend fun deleteAllData()
}