package com.dsoft.liverpooldirectory.ui.social

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dsoft.liverpooldirectory.R
import com.dsoft.liverpooldirectory.databinding.FragmentSocialBinding
import com.dsoft.liverpooldirectory.other.Constants.QUERY_PAGE_SIZE
import com.dsoft.liverpooldirectory.ui.social.adapter.SocialRecyclerAdapter
import com.dsoft.liverpooldirectory.utility.Resource
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKScope
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SocialFragment : Fragment() {

    private lateinit var binding: FragmentSocialBinding
    private val viewModel by viewModels<SocialViewModel>()

    private lateinit var adapter: SocialRecyclerAdapter

    var isLoading = false
    var isLastPage = false
    var isScrolling = false

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
            viewModel.safeCall()
            setUpRecyclerView()
            removeLoginViews()
        }

        binding.ivVk.setOnClickListener {
            viewModel.safeCall()
            setUpRecyclerView()
            removeLoginViews()
        }

        binding.socialRecyclerView.setOnClickListener {
            val dialog = DialogSendCommentFragment()
            dialog.show(parentFragmentManager, "customDialog")
        }

        observeStatus()
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
                            binding.socialRecyclerView.setPadding(0,0,0,0)
                        }
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Toast.makeText(activity, "An error occured: $message", Toast.LENGTH_SHORT).show()
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
            it?.let {
                adapter.differ.submitList(it)
            }
        }
    }

    private fun setUpRecyclerView() {
        adapter = SocialRecyclerAdapter(requireContext())
        val recyclerView = binding.socialRecyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.addOnScrollListener(this@SocialFragment.scrollListener)

        viewModel.listOfWall.observe(viewLifecycleOwner) {
            it?.let { data ->
                adapter.differ.submitList(data.filter { it.attachments != null })
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
}