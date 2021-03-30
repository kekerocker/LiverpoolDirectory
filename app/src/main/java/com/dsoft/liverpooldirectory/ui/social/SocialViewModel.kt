package com.dsoft.liverpooldirectory.ui.social

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dsoft.liverpooldirectory.MainMenuActivity
import com.dsoft.liverpooldirectory.model.vk.comments.ItemComments
import com.dsoft.liverpooldirectory.model.vk.wall.Item
import com.dsoft.liverpooldirectory.other.Constants.CODE_TOKEN_ERROR
import com.dsoft.liverpooldirectory.repository.SocialRepository
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SocialViewModel @Inject constructor(private val socialRepository: SocialRepository) :
    ViewModel() {

    private var _listOfComments = MutableLiveData<List<ItemComments>>()
    val listOfComments: LiveData<List<ItemComments>> get() = _listOfComments

    private var _listOfWall = MutableLiveData<List<Item>>()
    val listOfWall: LiveData<List<Item>> get() = _listOfWall

    var isExpired = true

    val appPreferences = socialRepository.appPreferences

    fun getComments(postId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val comments = socialRepository.getComments(postId).response

            withContext(Dispatchers.Main) {
                _listOfComments.value = comments.itemComments
            }
        }
    }

    private fun postComment(postId: String, message: String) {
        viewModelScope.launch(Dispatchers.IO) {
            socialRepository.postComment(postId, message)
        }
    }

    fun fetchWallFromPublic() {
        viewModelScope.launch(Dispatchers.IO) {
            val wall = socialRepository.fetchWallFromPublic()

            if (wall.error?.error_code == CODE_TOKEN_ERROR) {
                VK.login(activity = MainMenuActivity(), arrayListOf(VKScope.WALL, VKScope.GROUPS, VKScope.EMAIL))
                return@launch
            }
            val response = wall.response!!

            withContext(Dispatchers.Main) {
                _listOfWall.value = response.items
            }
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
        val postId = appPreferences.getPosition()
        postComment(postId, comment)
        Toast.makeText(context, "Комментарий отправлен!", Toast.LENGTH_SHORT).show()
    }

}