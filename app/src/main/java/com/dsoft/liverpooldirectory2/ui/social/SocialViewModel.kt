package com.dsoft.liverpooldirectory2.ui.social

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.*
import com.dsoft.liverpooldirectory2.usecase.SocialUseCase
import com.dsoft.liverpooldirectory2.model.VKCommentData
import com.dsoft.liverpooldirectory2.model.VKWallData
import com.dsoft.liverpooldirectory2.utility.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class SocialViewModel @Inject constructor(
    private val useCase: SocialUseCase
) : ViewModel(), LifecycleObserver {

    private var _listOfComments = MutableLiveData<List<VKCommentData>>()
    val listOfComments: LiveData<List<VKCommentData>> get() = _listOfComments

    private var _listOfWall = MutableLiveData<List<VKWallData>>()
    val listOfWall: LiveData<List<VKWallData>> get() = _listOfWall

    val socialStatus: MutableLiveData<Resource<VKWallData>> = MutableLiveData()

    var isExpired = true

    val appPreferences get() = useCase.getAppPreferences()

    var count = 15

    fun getAuthFlow(): StateFlow<Boolean> {
        return useCase.vkSuccessConnection
    }

    fun safeCall() {
        viewModelScope.launch {
            socialStatus.postValue(Resource.Loading())
            try {
                val response = useCase.fetchWallFromPublic(count)
                if (response.isNotEmpty()) {
                    _listOfWall.value = response
                    socialStatus.postValue(Resource.Success(response.first()))
                    count += 15
                }
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
            val comments = useCase.getComments(postId)
            withContext(Dispatchers.Main) {
                _listOfComments.value = comments
            }
        }
    }

    private fun postComment(postId: String, message: String) {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.postComment(postId, message)
        }
    }

    fun checkTokenActuality() {
        val currentTime = System.currentTimeMillis()
        val tokenTime = useCase.getAppPreferences().getTokenTime()

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