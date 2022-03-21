package com.dsoft.liverpooldirectory.usecase

import com.dsoft.liverpooldirectory.model.CloseGamesData
import com.dsoft.liverpooldirectory.model.TableData
import com.dsoft.liverpooldirectory.repository.MainMenuRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MainMenuUseCase @Inject constructor(
    private val repository: MainMenuRepository
) {

    val readAllEplData: Flow<List<TableData>> get() = repository.readAllEplData
    val readAllCloseGamesData: Flow<List<CloseGamesData>> get() = repository.readAllCloseGamesData

    fun downloadDataFromInternet() {
        repository.downloadDataFromInternet()
    }

    suspend fun deleteAllTableData() {
        repository.deleteAllTableData()
    }

    suspend fun deleteAllCloseGamesData() {
        repository.deleteAllCloseGamesData()
    }

}