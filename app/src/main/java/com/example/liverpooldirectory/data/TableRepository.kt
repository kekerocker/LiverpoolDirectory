package com.example.liverpooldirectory.data

import androidx.lifecycle.LiveData

class TableRepository(private val tableDao: TableDao) {

    val readAllData: LiveData<List<Table>> = tableDao.readAllData()

    suspend fun addTable(table: Table) {
        tableDao.addTable(table)
    }
}