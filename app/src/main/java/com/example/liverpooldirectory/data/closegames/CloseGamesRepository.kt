package com.example.liverpooldirectory.data.closegames

import androidx.lifecycle.LiveData
import com.example.liverpooldirectory.model.CloseGames

class CloseGamesRepository(private val closeGamesDao: CloseGamesDao) {
    val readAllData: LiveData<List<CloseGames>> = closeGamesDao.readAllData()

    suspend fun addCloseGames(closeGames: CloseGames) {
        closeGamesDao.addCloseGames(closeGames)
    }

    suspend fun deleteAllData() {
        closeGamesDao.deleteAllData()
    }
}