package com.example.liverpooldirectory.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.liverpooldirectory.data.TableDatabase
import com.example.liverpooldirectory.data.epltable.TableRepository
import com.example.liverpooldirectory.model.Table
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TableViewModel(application: Application): AndroidViewModel(application) {
    val readAllData: LiveData<List<Table>>
    private val repository: TableRepository

    init {
        val tableDao = TableDatabase.getTableDatabase(application).tableDao()
        repository = TableRepository(tableDao)
        readAllData = repository.readAllData
    }

    fun addTable(table: Table) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTable(table)
        }
    }

    fun deleteAllTableData() {
        viewModelScope.launch (Dispatchers.IO) {
            repository.deleteAllTableData()
        }
    }
}