package com.example.liverpooldirectory.data.epltable

import androidx.lifecycle.LiveData
import com.example.liverpooldirectory.model.Table

class TableRepository(private val tableDao: TableDao) {

    val readAllData: LiveData<List<Table>> = tableDao.readAllData()

    suspend fun addTable(table: Table) {
        tableDao.addTable(table)
    }

    suspend fun deleteAllTableData() {
        tableDao.deleteAllTableData()
    }
}