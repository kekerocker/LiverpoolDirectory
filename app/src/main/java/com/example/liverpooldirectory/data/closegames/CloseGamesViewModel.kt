package com.example.liverpooldirectory.data.closegames

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.liverpooldirectory.data.CloseGamesDatabase
import com.example.liverpooldirectory.model.CloseGames
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CloseGamesViewModel(application: Application): AndroidViewModel(application) {
    val readAllData: LiveData<List<CloseGames>>
    private val repository: CloseGamesRepository

    init {
        val closeGamesDao = CloseGamesDatabase.getDatabase(application).closeGamesDao()
        repository = CloseGamesRepository(closeGamesDao)
        readAllData = repository.readAllData
    }

    fun addCloseGames(closeGames: CloseGames) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addCloseGames(closeGames)
        }
    }

    fun deleteAllData() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllData()
        }
    }
}