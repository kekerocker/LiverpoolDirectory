package com.dsoft.liverpooldirectory.ui.mainmenu

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dsoft.liverpooldirectory.model.CloseGames
import com.dsoft.liverpooldirectory.model.Table
import com.dsoft.liverpooldirectory.repository.MainMenuRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainMenuViewModel @Inject constructor(
    private val mainMenuRepository: MainMenuRepository,
    @ApplicationContext context: Context
) : ViewModel(), LifecycleObserver {

    private val isOnline = mainMenuRepository.isOnline
    val readAllEplData: LiveData<List<Table>> = mainMenuRepository.readAllEplData
    val readAllCloseGamesData: LiveData<List<CloseGames>> = mainMenuRepository.readAllCloseGamesData

    init {
        if (isOnline) {
            deleteAllCloseGamesData()
            deleteAllTableData()
            downloadDataFromInternet()
        } else {
            Toast.makeText(context, "No Internet connection.", Toast.LENGTH_SHORT).show()
            Log.d("TestInternet", "No internet connection")
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