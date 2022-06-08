package com.dsoft.liverpooldirectory.repository.intrface

import com.dsoft.liverpooldirectory.model.CloseGamesData
import com.dsoft.liverpooldirectory.model.TableData
import kotlinx.coroutines.flow.Flow

interface MainMenuRepository {

    fun getAllCloseGamesData(): Flow<List<CloseGamesData>>
    fun getAllEplData(): Flow<List<TableData>>
    fun downloadDataFromInternet()

    suspend fun deleteAllCloseGamesData()
    suspend fun deleteAllTableData()
}