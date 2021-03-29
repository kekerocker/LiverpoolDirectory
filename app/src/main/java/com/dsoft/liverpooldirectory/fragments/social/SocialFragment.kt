package com.dsoft.liverpooldirectory.fragments.social

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dsoft.liverpooldirectory.R
import com.dsoft.liverpooldirectory.databinding.FragmentSocialBinding
import com.dsoft.liverpooldirectory.fragments.social.adapter.SocialRecyclerAdapter
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKScope
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SocialFragment : Fragment() {

    private lateinit var binding: FragmentSocialBinding
    private val viewModel by viewModels<SocialViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_social, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSocialBinding.bind(view)
        val token = viewModel.appPreferences.getToken()
        viewModel.checkTokenActuality()

        if (token == "" || viewModel.isExpired) {
            Toast.makeText(requireContext(), "Требуется авторизация!", Toast.LENGTH_SHORT).show()
            VK.login(requireActivity(), arrayListOf(VKScope.WALL, VKScope.GROUPS, VKScope.EMAIL))
        } else {
            Log.d("VKLogin", "Authentication went successful")
            viewModel.fetchWallFromPublic()
            setUpRecyclerView()
            removeLoginViews()
        }

        binding.ivVk.setOnClickListener {
            viewModel.fetchWallFromPublic()
        }

        binding.socialRecyclerView.setOnClickListener {
            val dialog = DialogSendCommentFragment()
            dialog.show(parentFragmentManager, "customDialog")
        }
    }

    private fun setUpRecyclerView() {
        val adapter = SocialRecyclerAdapter(requireContext())
        val recyclerView = binding.socialRecyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        viewModel.listOfWall.observe(viewLifecycleOwner) {
            if (it == null) {
                return@observe
            }
            adapter.list = it.filter { it.attachments != null }
        }
    }

    private fun removeLoginViews() {
        binding.tvVkText.visibility = View.GONE
        binding.ivVk.visibility = View.GONE
        binding.tvSocialTitle.visibility = View.VISIBLE
        binding.recyclerLayout.visibility = View.VISIBLE
    }
}