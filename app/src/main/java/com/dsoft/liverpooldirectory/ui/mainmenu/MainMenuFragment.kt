package com.dsoft.liverpooldirectory.ui.mainmenu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.dsoft.liverpooldirectory.R
import com.dsoft.liverpooldirectory.databinding.FragmentMainMenuBinding
import com.dsoft.liverpooldirectory.ui.mainmenu.adapter.RecyclerAdapterTable
import com.dsoft.liverpooldirectory.ui.mainmenu.adapter.ViewPagerAdapter
import com.dsoft.liverpooldirectory.utility.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainMenuFragment : BaseFragment(R.layout.fragment_main_menu) {

    private val viewModel by activityViewModels<MainMenuViewModel>()
    private val binding by viewBinding(FragmentMainMenuBinding::bind)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return getPersistentView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!hasInitializedRootView) {
            setUpViewPager()
            setUpRecyclerViewTable()
            hasInitializedRootView = true
        }

        binding.buttonNews.setOnClickListener {
            findNavController().navigate(R.id.action_mainMenuFragment_to_newsFragment)
        }

        binding.buttonInfo.setOnClickListener {
            val dialog = DialogFragment()
            dialog.show(parentFragmentManager, "customDialog")
        }

        binding.buttonSocial.setOnClickListener {
            findNavController().navigate(R.id.action_MainMenuFragment_to_socialFragment)
        }

        binding.btnSettings.setOnClickListener {
            findNavController().navigate(R.id.action_MainMenuFragment_to_settingsFragment)
        }
    }

    private fun setUpViewPager() {
        val viewPager2 = binding.viewPager2
        val adapter = ViewPagerAdapter()
        viewPager2.adapter = adapter
        viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        binding.indicator.setViewPager(viewPager2)
        adapter.registerAdapterDataObserver(binding.indicator.adapterDataObserver)

        viewModel.readAllCloseGamesData.observe(viewLifecycleOwner) { closeGames ->
            adapter.setData(closeGames)
            binding.shimmerCloseMatches.stopShimmer()
            binding.shimmerCloseMatches.hideShimmer()
            binding.shimmerCloseMatches.visibility = View.GONE
        }
    }

    private fun setUpRecyclerViewTable() {
        val adapter = RecyclerAdapterTable()
        val recyclerView = binding.rvRecyclerViewTable
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.readAllEplData.observe(viewLifecycleOwner) { table ->
            if (table.isEmpty()) {
                return@observe
            } else {
                adapter.setData(table)
                hideLoadingScreen()
            }
        }
    }

    private fun hideLoadingScreen() {
        binding.progressBarTable.visibility = View.GONE
        binding.loadingBackground.visibility = View.GONE
    }
}