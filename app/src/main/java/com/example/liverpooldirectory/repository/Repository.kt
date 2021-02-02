package com.example.liverpooldirectory.repository

import androidx.lifecycle.LiveData
import com.example.liverpooldirectory.data.DataDao
import com.example.liverpooldirectory.model.CloseGames
import com.example.liverpooldirectory.model.Table

class Repository(private val dataDao: DataDao) {

    val readAllCloseGamesData: LiveData<List<CloseGames>> = dataDao.readAllCloseGamesData()
    val readAllEplData: LiveData<List<Table>> = dataDao.readAllEplData()

    suspend fun addCloseGames(closeGames: CloseGames) {
        dataDao.addCloseGames(closeGames)
    }

    suspend fun deleteAllCloseGamesData() {
        dataDao.deleteAllCloseGamesData()
    }

    suspend fun addTable(table: Table) {
        dataDao.addTable(table)
    }

    suspend fun deleteAllTableData() {
        dataDao.deleteAllTableData()
    }
}