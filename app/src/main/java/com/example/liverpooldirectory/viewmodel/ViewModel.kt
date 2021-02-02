package com.example.liverpooldirectory.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.liverpooldirectory.data.LFCDatabase
import com.example.liverpooldirectory.model.CloseGames
import com.example.liverpooldirectory.model.Table
import com.example.liverpooldirectory.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModel(application: Application): AndroidViewModel(application) {
    val readAllEplData: LiveData<List<Table>>
    val readAllCloseGamesData: LiveData<List<CloseGames>>
    private val repository: Repository

    init {
        val dataDao = LFCDatabase.getTableDatabase(application).dataDao()
        repository = Repository(dataDao)
        readAllEplData = repository.readAllEplData
        readAllCloseGamesData = repository.readAllCloseGamesData
    }

    fun addTable(table: Table) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTable(table)
        }
    }

    fun deleteAllTableData() {
        viewModelScope.launch (Dispatchers.IO) {
            repository.deleteAllTableData()
        }
    }

    fun addCloseGames(closeGames: CloseGames) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addCloseGames(closeGames)
        }
    }

    fun deleteAllCloseGamesData() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllCloseGamesData()
        }
    }
}