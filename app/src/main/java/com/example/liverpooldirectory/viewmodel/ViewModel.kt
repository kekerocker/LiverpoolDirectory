package com.example.liverpooldirectory.viewmodel

import android.app.Application
import android.app.Service
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.liverpooldirectory.data.LFCDatabase
import com.example.liverpooldirectory.model.CloseGames
import com.example.liverpooldirectory.model.Table
import com.example.liverpooldirectory.repository.MainMenuRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModel(application: Application) : AndroidViewModel(application) {

    var connectivity: ConnectivityManager? = null
    var info: NetworkInfo? = null

    val readAllEplData: LiveData<List<Table>>
    val readAllCloseGamesData: LiveData<List<CloseGames>>
    private val mainMenuRepository: MainMenuRepository

    init {
        val tableDao = LFCDatabase.getTableDatabase(application).tableDao()
        mainMenuRepository = MainMenuRepository(tableDao)
        readAllEplData = mainMenuRepository.readAllEplData
        readAllCloseGamesData = mainMenuRepository.readAllCloseGamesData

        connectivity = application.getSystemService(Service.CONNECTIVITY_SERVICE) as ConnectivityManager

        checkInternet()
    }

    private fun checkInternet() {
        if (connectivity != null) {
            info = connectivity!!.activeNetworkInfo

            if (info != null) {
                if (info!!.state == NetworkInfo.State.CONNECTED) {
                    //Do when online0
                    deleteAllCloseGamesData()
                    deleteAllTableData()
                    downloadDataFromInternet()
                }
            } else {
                //Do when offline
                Toast.makeText(getApplication(), "Нет соединения с интернетом.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun downloadDataFromInternet() {
        viewModelScope.launch(Dispatchers.IO) {
            mainMenuRepository.downloadDataFromInternet()
        }
    }

    private fun deleteAllTableData() {
        viewModelScope.launch(Dispatchers.IO) {
            mainMenuRepository.deleteAllTableData()
        }
    }

    private fun deleteAllCloseGamesData() {
        viewModelScope.launch(Dispatchers.IO) {
            mainMenuRepository.deleteAllCloseGamesData()
        }
    }
}