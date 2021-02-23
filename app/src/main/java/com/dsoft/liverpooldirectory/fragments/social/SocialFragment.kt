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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class SocialFragment : Fragment() {

    private var listOfWall: List<Item> = emptyList()
    private var component = DaggerComponent.create()
    private var isExpired = true
    private lateinit var binding: FragmentSocialBinding
    private var appPreferences: AppPreferences? = null
    private lateinit var viewModel: SocialViewModel

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
        checkTokenActuality()

        //Вызов аутентификации в ВК, если токен просрочен
        if (isExpired) {
            Toast.makeText(requireContext(), "Требуется авторизация!", Toast.LENGTH_SHORT).show()
            VK.login(requireActivity(), arrayListOf(VKScope.WALL, VKScope.GROUPS, VKScope.EMAIL))
        } else {
            Toast.makeText(requireContext(), "Добро пожаловать!", Toast.LENGTH_SHORT).show()
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

    private fun checkTokenActuality() {
        val currentTime = System.currentTimeMillis()
        val tokenTime = appPreferences?.getTokenTime()!!

        if (currentTime > tokenTime + 86400000) {
            isExpired = true
            Log.d("TESTTOKEN", "TEST: Token is expired = $isExpired")
        } else {
            isExpired = false
            Log.d("TESTTOKEN", "TEST: Token is expired = $isExpired")
        }
    }

    private fun fetchWallFromPublic(token: String) {
        val api = component.getRetrofit().api

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = api.getWall(token)
                withContext(Dispatchers.Main) {
                    listOfWall = response.response.items
                    removeLoginViews()
                    setUpRecyclerView()
                    Log.d("TestRetrofit", listOfWall.toString())
                }
            } catch (e: HttpException) {
                Log.e("Social95", e.toString())
            } catch (e: Exception) {
                    Log.e("Social95", e.toString())
                }
            }
        }


    private fun setUpRecyclerView() {
        val adapter = SocialRecyclerAdapter(requireContext())
        val recyclerView = binding.socialRecyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter.list = listOfWall
    }

    private fun removeLoginViews() {
        tv_vk_text.visibility = View.GONE
        iv_vk.visibility = View.GONE
        tv_social_title.visibility = View.VISIBLE
        recycler_layout.visibility = View.VISIBLE
    }
}