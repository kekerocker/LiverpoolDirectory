package com.dsoft.liverpooldirectory.fragments.social

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dsoft.liverpooldirectory.R
import com.dsoft.liverpooldirectory.data.AppPreferences
import com.dsoft.liverpooldirectory.databinding.FragmentSocialBinding
import com.dsoft.liverpooldirectory.di.DaggerComponent
import com.dsoft.liverpooldirectory.fragments.social.adapter.SocialRecyclerAdapter
import com.dsoft.liverpooldirectory.model.vk.wall.Item
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKScope
import kotlinx.android.synthetic.main.fragment_social.*
import kotlinx.coroutines.*
import okhttp3.internal.wait

private const val CODE_TOKEN_ERROR = 5

class SocialFragment : Fragment() {

    private var listOfWall: List<Item> = emptyList()

    private var component = DaggerComponent.create()
    private var isExpired = true
    private lateinit var binding: FragmentSocialBinding
    private var appPreferences: AppPreferences? = null
    private lateinit var viewModel: SocialViewModel
    private val api = component.getRetrofit().api


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_social, container, false)
        //ViewModel
        viewModel = ViewModelProvider(this).get(SocialViewModel::class.java)
        appPreferences = AppPreferences(requireContext())

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSocialBinding.bind(view)
        val token = appPreferences?.getToken()!!
        checkTokenActuality()

        if (token == "" || isExpired/* || checkForError(token) == "User authorization failed: access_token was given to another ip address."*/) {
            Toast.makeText(requireContext(), "Требуется авторизация!", Toast.LENGTH_SHORT).show()
            VK.login(requireActivity(), arrayListOf(VKScope.WALL, VKScope.GROUPS, VKScope.EMAIL))
        } else {
            Log.d("VKLogin", "Authentication went successful")
            fetchWallFromPublic(appPreferences?.getToken()!!)
        }

        binding.ivVk.setOnClickListener {
            fetchWallFromPublic(appPreferences?.getToken()!!)
        }

        binding.socialRecyclerView.setOnClickListener {
            val dialog = DialogSendCommentFragment()
            dialog.show(parentFragmentManager, "customDialog")
        }
    }

    private fun fetchWallFromPublic(token: String) {
        GlobalScope.launch(Dispatchers.IO) {
            val wall = api.getWall(token)

            if (wall.error?.error_code == CODE_TOKEN_ERROR) {
                VK.login(requireActivity(), arrayListOf(VKScope.WALL, VKScope.GROUPS, VKScope.EMAIL))
                return@launch
            }
            val response = wall.response!!

            withContext(Dispatchers.Main) {
                listOfWall = response.items
                removeLoginViews()
                setUpRecyclerView()
                Log.d("Token", listOfWall.toString())
            }
        }
    }

    private fun checkTokenActuality() {
        val currentTime = System.currentTimeMillis()
        val tokenTime = appPreferences?.getTokenTime()!!

        if (currentTime > tokenTime + 86400000) {
            isExpired = true
            Log.d("TESTTOKEN", "TEST: Token is expired = $isExpired")
        } else if (currentTime < tokenTime + 86400000) {
            isExpired = false
            Log.d("TESTTOKEN", "TEST: Token is expired = $isExpired")
        }
    }

    private fun setUpRecyclerView() {
        val adapter = SocialRecyclerAdapter(requireContext())
        val recyclerView = binding.socialRecyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter.list = listOfWall.filter { it.attachments != null }
    }

    private fun removeLoginViews() {
        tv_vk_text.visibility = View.GONE
        iv_vk.visibility = View.GONE
        tv_social_title.visibility = View.VISIBLE
        recycler_layout.visibility = View.VISIBLE
    }
}