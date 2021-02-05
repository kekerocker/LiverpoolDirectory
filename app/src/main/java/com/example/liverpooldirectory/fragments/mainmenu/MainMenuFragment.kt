package com.example.liverpooldirectory.fragments.mainmenu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.liverpooldirectory.R
import com.example.liverpooldirectory.databinding.FragmentMainMenuBinding
import com.example.liverpooldirectory.viewmodel.ViewModel
import kotlinx.android.synthetic.main.fragment_main_menu.*


class MainMenuFragment : Fragment() {

    private lateinit var binding: FragmentMainMenuBinding
    private lateinit var viewModel: ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main_menu, container, false)

        //ViewModel
        viewModel = ViewModelProvider(this).get(ViewModel::class.java)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainMenuBinding.bind(view)

        binding.buttonNews.setOnClickListener {
            findNavController().navigate(R.id.action_mainMenuFragment_to_newsFragment)
        }

        binding.buttonSocial.setOnClickListener {
            findNavController().navigate(R.id.action_MainMenuFragment_to_socialFragment)
        }

        setUpViewPager()
        setUpRecyclerViewTable()
    }

    private fun fadeInFromBlack(view: View, timer: Long) {
        view.animate().apply {
            alpha(0f)
            duration = timer
        }.start()
    }

    private fun hideLoadingScreen(timer: Long) {
        fadeInFromBlack(binding.mainLoadingScreen, timer)
    }

    private fun setUpViewPager() {
        val viewPager2 = binding.viewPager2
        val adapter = ViewPagerAdapter()
        viewPager2.adapter = adapter
        viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        binding.indicator.setViewPager(viewPager2)
        adapter.registerAdapterDataObserver(indicator.adapterDataObserver)

        viewModel.readAllCloseGamesData.observe(viewLifecycleOwner, { closeGames ->
            adapter.setData(
                closeGames
            )
        })
    }

    private fun setUpRecyclerViewTable() {
        val adapter = RecyclerAdapterTable()
        val recyclerView = binding.rvRecyclerViewTable
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.readAllEplData.observe(viewLifecycleOwner, { table ->
            adapter.setData(table)
        })
    }
}