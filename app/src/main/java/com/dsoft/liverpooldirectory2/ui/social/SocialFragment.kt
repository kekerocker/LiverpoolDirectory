package com.dsoft.liverpooldirectory2.ui.social

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dsoft.liverpooldirectory2.databinding.FragmentSocialBinding
import com.dsoft.liverpooldirectory2.other.Constants.CODE_TOKEN_ERROR_IP
import com.dsoft.liverpooldirectory2.other.Constants.QUERY_PAGE_SIZE
import com.dsoft.liverpooldirectory2.ui.social.adapter.SocialRecyclerAdapter
import com.dsoft.liverpooldirectory2.utility.Resource
import com.google.android.material.snackbar.Snackbar
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class SocialFragment : Fragment() {

    private var _binding: FragmentSocialBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<SocialViewModel>()

    private lateinit var adapter: SocialRecyclerAdapter

    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentSocialBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val token = viewModel.appPreferences.getToken()
        viewModel.checkTokenActuality()
        lifecycleScope.launchWhenStarted {
            setupVkSuccessListener()
        }

        viewModel.listOfWall.observe(viewLifecycleOwner) { list ->
            if (list.first().errorCode == CODE_TOKEN_ERROR_IP) {
                makeAuth()
            }
        }

        if (token == null || viewModel.isExpired) {
            makeAuth()
        } else {
            Log.d("VKLogin", "Authentication went successful")
            viewModel.safeCall()
            setUpRecyclerView()
            removeLoginViews()
            observeStatus()
        }

        binding.socialRecyclerView.setOnClickListener {
            val dialog = DialogSendCommentFragment()
            dialog.show(parentFragmentManager, "customDialog")
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun makeAuth() {
        Snackbar.make(requireView(), "Требуется авторизация!", Snackbar.LENGTH_SHORT).show()
        VK.login(requireActivity(), arrayListOf(VKScope.WALL, VKScope.GROUPS, VKScope.EMAIL))
    }

    private fun observeStatus() {
        viewModel.socialStatus.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        val totalCount = adapter.differ.currentList.size / QUERY_PAGE_SIZE
                        isLastPage = viewModel.count == totalCount
                        if (isLastPage) {
                            binding.socialRecyclerView.setPadding(0, 0, 0, 0)
                        }
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Toast.makeText(activity, "An error happened: $message", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        }
    }

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = binding.socialRecyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= QUERY_PAGE_SIZE
            val shouldPaginate =
                isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning && isTotalMoreThanVisible && isScrolling

            if (shouldPaginate) {
                paginatePictures()
                isScrolling = false
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }
    }

    private fun paginatePictures() {
        viewModel.safeCall()

        viewModel.listOfWall.observe(viewLifecycleOwner) {
            it?.let { list ->
                adapter.differ.submitList(list)
            }
        }
    }

    private fun setUpRecyclerView() {
        adapter = SocialRecyclerAdapter()
        val recyclerView = binding.socialRecyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.addOnScrollListener(this@SocialFragment.scrollListener)

        viewModel.listOfWall.observe(viewLifecycleOwner) {
            it?.let { list ->
                adapter.differ.submitList(list)
            }
        }
    }

    private fun hideProgressBar() {
        binding.progressBarPaginate.visibility = View.INVISIBLE
        isLoading = false
    }

    private fun showProgressBar() {
        binding.progressBarPaginate.visibility = View.VISIBLE
        isLoading = true
    }

    private fun removeLoginViews() {
        binding.tvVkText.visibility = View.GONE
        binding.ivVk.visibility = View.GONE
        binding.tvSocialTitle.visibility = View.VISIBLE
        binding.recyclerLayout.visibility = View.VISIBLE
    }

    private suspend fun setupVkSuccessListener() {
        viewModel.getAuthFlow().collectLatest {
            viewModel.safeCall()
            setUpRecyclerView()
            removeLoginViews()
        }
    }
}