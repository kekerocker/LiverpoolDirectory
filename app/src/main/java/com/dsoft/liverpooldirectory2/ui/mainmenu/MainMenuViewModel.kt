package com.dsoft.liverpooldirectory2.ui.mainmenu

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dsoft.liverpooldirectory2.model.NewsData
import com.dsoft.liverpooldirectory2.usecase.MainMenuUseCase
import com.dsoft.liverpooldirectory2.utility.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MainMenuViewModel @Inject constructor(
    private val useCase: MainMenuUseCase,
) : ViewModel(), LifecycleObserver {

    init {
        deleteAllNews()
        safeCall()
    }

    val readAllNews: LiveData<List<NewsData>> = useCase.readAllNews
    val newsStatus: MutableLiveData<Resource<NewsData>> = MutableLiveData()

    var count = 10

    private fun deleteAllNews() {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.deleteAllNews()
        }
    }

    fun safeCall() {
        newsStatus.postValue(Resource.Loading())
        try {
            useCase.downloadNews()
            readAllNews.observeForever { list ->
                val someList = if (!list.isNullOrEmpty()) list else return@observeForever
                newsStatus.postValue(Resource.Success(someList.first()))
            }
            count += 10
        } catch (t: Throwable) {
            when (t) {
                is IOException -> newsStatus.postValue(Resource.Error("No internet connection"))
                else -> {
                    t.printStackTrace()
                    newsStatus.postValue(Resource.Error("Conversion Error"))
                }
            }
        }
    }
}