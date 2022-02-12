package com.dsoft.liverpooldirectory.ui.mainmenu

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dsoft.liverpooldirectory.model.CloseGamesData
import com.dsoft.liverpooldirectory.model.TableData
import com.dsoft.liverpooldirectory.repository.MainMenuRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainMenuViewModel @Inject constructor(
    private val mainMenuRepository: MainMenuRepository,
) : ViewModel(), LifecycleObserver {

    val readAllEplData: Flow<List<TableData>> = mainMenuRepository.readAllEplData
    val readAllCloseGamesData: Flow<List<CloseGamesData>> = mainMenuRepository.readAllCloseGamesData

    init {
        deleteAllCloseGamesData()
        deleteAllTableData()
        downloadDataFromInternet()
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