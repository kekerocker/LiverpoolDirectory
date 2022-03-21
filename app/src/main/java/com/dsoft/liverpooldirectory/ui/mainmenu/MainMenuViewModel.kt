package com.dsoft.liverpooldirectory.ui.mainmenu

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dsoft.liverpooldirectory.model.CloseGamesData
import com.dsoft.liverpooldirectory.model.TableData
import com.dsoft.liverpooldirectory.usecase.MainMenuUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainMenuViewModel @Inject constructor(
    private val useCase: MainMenuUseCase,
) : ViewModel(), LifecycleObserver {

    val readAllEplData: Flow<List<TableData>> = useCase.readAllEplData
    val readAllCloseGamesData: Flow<List<CloseGamesData>> = useCase.readAllCloseGamesData

    init {
        deleteAllCloseGamesData()
        deleteAllTableData()
        downloadDataFromInternet()
    }

    private fun downloadDataFromInternet() {
        useCase.downloadDataFromInternet()
    }

    private fun deleteAllTableData() {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.deleteAllTableData()
        }
    }

    private fun deleteAllCloseGamesData() {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.deleteAllCloseGamesData()
        }
    }
}