package com.dsoft.liverpooldirectory.ui.social

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dsoft.liverpooldirectory.model.vk.comments.ItemComments
import com.dsoft.liverpooldirectory.model.vk.wall.Item
import com.dsoft.liverpooldirectory.repository.SocialRepository
import com.dsoft.liverpooldirectory.utility.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class SocialViewModel @Inject constructor(private val socialRepository: SocialRepository) :
    ViewModel() {

    private var _listOfComments = MutableLiveData<List<ItemComments>>()
    val listOfComments: LiveData<List<ItemComments>> get() = _listOfComments

    private var _listOfWall = MutableLiveData<List<Item>>()
    val listOfWall: LiveData<List<Item>> get() = _listOfWall

    val socialStatus: MutableLiveData<Resource<Item>> = MutableLiveData()

    val isOnLine = socialRepository.isOnline
    var isExpired = true

    val appPreferences = socialRepository.appPreferences

    var count = 10

    fun safeCall() {
        viewModelScope.launch {
            socialStatus.postValue(Resource.Loading())
            try {
                if (isOnLine) {
                    val response = socialRepository.fetchWallFromPublic(count).response
                    if (response != null) {
                        _listOfWall.value = response.items
                        socialStatus.postValue(Resource.Success(response.items.first()))
                        count += 10
                    }
                } else {
                    socialStatus.postValue(Resource.Error("No internet connection"))
                }
            } catch (t: Throwable) {
                when (t) {
                    is IOException -> socialStatus.postValue(Resource.Error("Network Failure"))
                    else -> socialStatus.postValue(Resource.Error("Conversion Error"))

                }
            }
        }
    }

    fun getComments(postId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val comments = socialRepository.getComments(postId).response.items
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

        if (tokenTime != null) {
            if (currentTime > tokenTime + 86400000) {
                isExpired = true
                Log.d("TESTTOKEN", "TEST: Token is expired = $isExpired")
            } else if (currentTime < tokenTime + 86400000) {
                isExpired = false
                Log.d("TESTTOKEN", "TEST: Token is expired = $isExpired")
            }
        }
    }

    fun sendMessage(comment: String, context: Context) {
        val postId = appPreferences.getPostId()
        postComment(postId, comment)
        Toast.makeText(context, "Комментарий отправлен!", Toast.LENGTH_SHORT).show()
    }

}