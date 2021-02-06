package com.example.liverpooldirectory.fragments.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.liverpooldirectory.R
import com.example.liverpooldirectory.databinding.FragmentNewsBinding
import com.example.liverpooldirectory.viewmodel.NewsViewModel

class NewsFragment : Fragment() {


    private lateinit var binding: FragmentNewsBinding
    private lateinit var viewModel: NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_news, container, false)

        //ViewModel
        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)

        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewsBinding.bind(view)

        setUpRecyclerView()
    }


    private fun fadeInFromBlack() {
        binding.vBlackScreen.animate().apply {
            alpha(0f)
            duration = 3000
        }.start()
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