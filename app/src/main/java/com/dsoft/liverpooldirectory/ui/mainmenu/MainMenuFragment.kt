package com.dsoft.liverpooldirectory.ui.mainmenu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.dsoft.liverpooldirectory.R
import com.dsoft.liverpooldirectory.databinding.FragmentMainMenuBinding
import com.dsoft.liverpooldirectory.ui.mainmenu.adapter.RecyclerAdapterTable
import com.dsoft.liverpooldirectory.ui.mainmenu.adapter.ViewPagerAdapter
import com.dsoft.liverpooldirectory.utility.BaseFragment
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainMenuFragment : BaseFragment(R.layout.fragment_main_menu) {

    private val viewModel by activityViewModels<MainMenuViewModel>()
    private val binding by viewBinding(FragmentMainMenuBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViewPager()
        setUpRecyclerViewTable()

        with(binding) {

            setButtonWidth(buttonNews, false)
            setButtonWidth(buttonSocial, true)
            setButtonWidth(btnSettings, true)
            setButtonWidth(buttonInfo, false)

            buttonNews.setOnClickListener {
                safeCall {
                    findNavController().navigate(R.id.action_mainMenuFragment_to_newsFragment)
                }
            }

            buttonInfo.setOnClickListener {
                val dialog = DialogFragment()
                dialog.show(parentFragmentManager, "customDialog")
            }

            buttonSocial.setOnClickListener {
                safeCall {
                    findNavController().navigate(R.id.action_MainMenuFragment_to_socialFragment)
                }
            }

            btnSettings.setOnClickListener {
                findNavController().navigate(R.id.action_MainMenuFragment_to_settingsFragment)
            }
        }
    }

    private fun setUpViewPager() {
        val viewPager2 = binding.viewPager2
        val adapter = ViewPagerAdapter()
        viewPager2.adapter = adapter
        viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        binding.indicator.setViewPager(viewPager2)
        adapter.registerAdapterDataObserver(binding.indicator.adapterDataObserver)

        lifecycleScope.launch {
            viewModel.readAllCloseGamesData.collectLatest { closeGames ->
                adapter.setData(closeGames)
                binding.shimmerCloseMatches.stopShimmer()
                binding.shimmerCloseMatches.hideShimmer()
                binding.shimmerCloseMatches.visibility = View.GONE
            }
        }
    }

    private fun setUpRecyclerViewTable() {
        val adapter = RecyclerAdapterTable()
        val recyclerView = binding.rvRecyclerViewTable
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        lifecycleScope.launch {
            viewModel.readAllEplData.collectLatest { table ->
                adapter.setData(table)
                hideLoadingScreen()
            }
        }
    }

    private fun setButtonWidth(button: MaterialButton, leftSided: Boolean) {
        val layoutParams =
            LinearLayout.LayoutParams(getScreenWidth(), LinearLayout.LayoutParams.WRAP_CONTENT)
        if (leftSided) layoutParams.setMargins(0, 0, 80, 0)

        button.layoutParams = layoutParams
    }

    private fun hideLoadingScreen() {
        binding.progressBarTable.visibility = View.GONE
        binding.loadingBackground.visibility = View.GONE
    }
}