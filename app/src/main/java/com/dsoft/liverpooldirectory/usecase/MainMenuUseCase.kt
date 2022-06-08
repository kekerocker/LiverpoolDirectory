package com.dsoft.liverpooldirectory.usecase

import com.dsoft.liverpooldirectory.model.CloseGamesData
import com.dsoft.liverpooldirectory.model.TableData
import com.dsoft.liverpooldirectory.repository.MainMenuRepositoryImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MainMenuUseCase @Inject constructor(
    private val repository: MainMenuRepositoryImpl
) {

    val readAllEplData: Flow<List<TableData>> get() = repository.getAllEplData()
    val readAllCloseGamesData: Flow<List<CloseGamesData>> get() = repository.getAllCloseGamesData()

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