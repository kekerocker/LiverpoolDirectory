package com.example.liverpooldirectory.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.liverpooldirectory.data.Table
import com.example.liverpooldirectory.data.TableDatabase
import com.example.liverpooldirectory.data.TableRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TableViewModel(application: Application): AndroidViewModel(application) {
    private val readAllData: LiveData<List<Table>>
    private val repository: TableRepository

    init {
        val tableDao = TableDatabase.getDatabase(application).tableDao()
        repository = TableRepository(tableDao)
        readAllData = repository.readAllData
    }

    fun addTable(table: Table) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTable(table)
        }
    }

}