package com.dsoft.liverpooldirectory2.ui.news

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dsoft.liverpooldirectory2.databinding.FragmentNewsBinding
import com.dsoft.liverpooldirectory2.other.Constants
import com.dsoft.liverpooldirectory2.ui.news.adapter.RecyclerAdapter
import com.dsoft.liverpooldirectory2.utility.Resource
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFragment : Fragment() {

    private val viewModel by viewModels<NewsViewModel>()

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!

    private lateinit var rvAdapter: RecyclerAdapter

    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        with(binding) {
            refreshLayout.setOnRefreshListener {
                viewModel.safeCall()
                refreshLayout.isRefreshing = false
            }
        }
        observeStatus()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeStatus() {
        viewModel.newsStatus.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        val totalCount =
                            rvAdapter.differ.currentList.size / Constants.NEWS_PAGE_SIZE
                        isLastPage = viewModel.count == totalCount
                        if (isLastPage) {
                            binding.rvRecyclerView.setPadding(0, 0, 0, 0)
                        }
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Snackbar.make(
                            requireView(),
                            "An error occured: $message",
                            Snackbar.LENGTH_SHORT
                        )
                            .show()
                    }
                }
                is Resource.Loading -> {
                    Log.d("Test222", "Loading data...")
                    showProgressBar()
                }
            }
        }
    }

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = binding.rvRecyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= Constants.NEWS_PAGE_SIZE
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

        viewModel.readAllNews.observe(viewLifecycleOwner) {
            it?.let { list ->
                rvAdapter.differ.submitList(list)
            }
        }
    }

    private fun hideProgressBar() {
        binding.paginatingProgressbar.visibility = View.INVISIBLE
        isLoading = false
    }

    private fun showProgressBar() {
        binding.paginatingProgressbar.visibility = View.VISIBLE
        isLoading = true
    }

    private fun setUpRecyclerView() {
        rvAdapter = RecyclerAdapter()
        val recyclerView = binding.rvRecyclerView
        recyclerView.adapter = rvAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.addOnScrollListener(this.scrollListener)

        viewModel.readAllNews.observe(viewLifecycleOwner) { news ->
            rvAdapter.differ.submitList(news)
        }
    }
}