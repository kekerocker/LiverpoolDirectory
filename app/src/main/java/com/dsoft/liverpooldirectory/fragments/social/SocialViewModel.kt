package com.dsoft.liverpooldirectory.fragments.social

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.dsoft.liverpooldirectory.data.LFCDatabase
import com.dsoft.liverpooldirectory.model.Comments
import com.dsoft.liverpooldirectory.repository.SocialRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SocialViewModel(application: Application): AndroidViewModel(application) {

    val readComments: LiveData<List<Comments>>
    private val socialRepository: SocialRepository

    init {
        val commentsDao = LFCDatabase.createDatabase(application).commentsDao()
        socialRepository = SocialRepository(commentsDao)
        readComments = socialRepository.readComments

    }

    fun deleteAllComments() {
        viewModelScope.launch(Dispatchers.IO) {
            socialRepository.deleteAllComments()
        }
    }

    fun addComments(comments: Comments) {
        viewModelScope.launch(Dispatchers.IO) {
            socialRepository.addComments(comments)
        }
    }

}