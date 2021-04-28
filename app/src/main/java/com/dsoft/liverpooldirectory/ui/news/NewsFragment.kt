package com.dsoft.liverpooldirectory.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.dsoft.liverpooldirectory.R
import com.dsoft.liverpooldirectory.databinding.FragmentNewsBinding
import com.dsoft.liverpooldirectory.ui.news.adapter.RecyclerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFragment : Fragment() {

    private val viewModel by viewModels<NewsViewModel>()
    private val binding by viewBinding(FragmentNewsBinding::bind)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_news, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val refreshLayout = binding.refreshLayout
        refreshLayout.setOnRefreshListener {
            viewModel.downloadNews()
            refreshLayout.isRefreshing = false
        }

        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        val adapter = RecyclerAdapter()
        val recyclerView = binding.rvRecyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.readAllNews.observe(viewLifecycleOwner, { news ->
            adapter.setData(news)
        })
    }
}