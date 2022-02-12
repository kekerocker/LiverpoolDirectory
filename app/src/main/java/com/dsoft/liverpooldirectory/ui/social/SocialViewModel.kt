package com.dsoft.liverpooldirectory.ui.social

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.*
import com.dsoft.liverpooldirectory.Interactor
import com.dsoft.liverpooldirectory.model.VKCommentData
import com.dsoft.liverpooldirectory.model.VKWallData
import com.dsoft.liverpooldirectory.repository.SocialRepository
import com.dsoft.liverpooldirectory.utility.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class SocialViewModel @Inject constructor(
    private val socialRepository: SocialRepository,
    val interactor: Interactor
) : ViewModel(), LifecycleObserver {

    private var _listOfComments = MutableLiveData<List<VKCommentData>>()
    val listOfComments: LiveData<List<VKCommentData>> get() = _listOfComments

    private var _listOfWall = MutableLiveData<List<VKWallData>>()
    val listOfWall: LiveData<List<VKWallData>> get() = _listOfWall

    val socialStatus: MutableLiveData<Resource<VKWallData>> = MutableLiveData()

    var isExpired = true

    val appPreferences = socialRepository.appPreferences

    var count = 15

    fun safeCall() {
        viewModelScope.launch {
            socialStatus.postValue(Resource.Loading())
            try {
                //if (isOnline) {
                    val response = socialRepository.fetchWallFromPublic(count)
                    if (response.isNotEmpty()) {
                        _listOfWall.value = response
                        socialStatus.postValue(Resource.Success(response.first()))
                        count += 15
                    }
                /*} else {
                    socialStatus.postValue(Resource.Error("No internet connection"))
                }*/
            } catch (t: Throwable) {
                when (t) {
                    is IOException -> socialStatus.postValue(Resource.Error("Network Failure"))
                    else -> {
                        socialStatus.postValue(Resource.Error("Conversion Error"))
                        t.printStackTrace()
                    }
                }
            }
        }
    }

    fun getComments(postId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val comments = socialRepository.getComments(postId)
            withContext(Dispatchers.Main) {
                _listOfComments.value = comments
            }
        }
    }

    private fun postComment(postId: String, message: String) {
        viewModelScope.launch(Dispatchers.IO) {
            socialRepository.postComment(postId, message)
        }
    }

    fun checkTokenActuality() {
        val currentTime = System.currentTimeMillis()
        val tokenTime = socialRepository.appPreferences.getTokenTime()

        if (tokenTime != 0L) {
            if (currentTime > tokenTime + 86400000) {
                isExpired = true
            } else if (currentTime < tokenTime + 86400000) {
                isExpired = false
            }
        }
    }

    fun sendMessage(comment: String, context: Context, postId: String) {
        postComment(postId, comment)
        Toast.makeText(context, "Комментарий отправлен!", Toast.LENGTH_SHORT).show()
    }

}