package com.example.liverpooldirectory.fragments.mainmenu

import android.app.Service
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.liverpooldirectory.R
import com.example.liverpooldirectory.data.closegames.CloseGamesViewModel
import com.example.liverpooldirectory.data.epltable.TableViewModel
import com.example.liverpooldirectory.databinding.FragmentMainMenuBinding
import com.example.liverpooldirectory.model.CloseGames
import com.example.liverpooldirectory.model.Table
import kotlinx.android.synthetic.main.fragment_main_menu.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup


class MainMenuFragment : Fragment() {

    private lateinit var binding: FragmentMainMenuBinding

    var connectivity: ConnectivityManager? = null
    var info: NetworkInfo? = null

    private lateinit var mTableViewModel: TableViewModel
    private lateinit var mCloseGamesViewModel: CloseGamesViewModel

    private var positionList = mutableListOf<Int>()
    private var clubList = mutableListOf<String>()
    private var matchesList = mutableListOf<String>()
    private var winList = mutableListOf<String>()
    private var drawList = mutableListOf<String>()
    private var loseList = mutableListOf<String>()
    private var pointsList = mutableListOf<String>()

    private var teamNameHomeList = mutableListOf<String>()
    private var teamNameAwayList = mutableListOf<String>()
    private var scoreList = mutableListOf<String>()
    private var dateList = mutableListOf<String>()
    private var tournamentLogoList = mutableListOf<String>()
    private var teamLogo1List = mutableListOf<String>()
    private var teamLogo2List = mutableListOf<String>()
    private var matchTypeList = mutableListOf<String>()

    private var urlTable = "https://www.sports.ru/epl/table"
    private var urlCloseGame = "http://www.myliverpool.ru"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main_menu, container, false)

        //EPL Table ViewModel
        mTableViewModel = ViewModelProvider(this).get(TableViewModel::class.java)

        //CloseGames ViewModel
        mCloseGamesViewModel = ViewModelProvider(this).get(CloseGamesViewModel::class.java)

        binding.buttonNews.setOnClickListener {
            findNavController().navigate(R.id.action_mainMenuFragment_to_newsFragment)
        }

        binding.buttonHistory.setOnClickListener {
            findNavController().navigate(R.id.action_MainMenuFragment_to_historyFragment)
        }

        binding.buttonPlayers.setOnClickListener {
            findNavController().navigate(R.id.action_MainMenuFragment_to_playersFragment)
        }

        binding.buttonSocial.setOnClickListener {
            findNavController().navigate(R.id.action_MainMenuFragment_to_socialFragment)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainMenuBinding.bind(view)

        connectivity =
            requireContext().getSystemService(Service.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (connectivity != null) {
            info = connectivity!!.activeNetworkInfo

            if (info != null) {
                if (info!!.state == NetworkInfo.State.CONNECTED) {
                    //Do when online
                    mCloseGamesViewModel.deleteAllData()
                    mTableViewModel.deleteAllTableData()
                    downloadDataFromInternet()
                }
            } else {
                //Do when offline
                Toast.makeText(requireContext(), "Нет соединения с интернетом.", Toast.LENGTH_SHORT).show()
                setUpViewPager()
                setUpRecyclerViewTable()
                val handler = Handler()
                handler.postDelayed({
                    hideLoadingScreen(750)
                }, 1500)
            }
        }
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

        mCloseGamesViewModel.readAllData.observe(viewLifecycleOwner, { closeGames ->
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

        mTableViewModel.readAllData.observe(viewLifecycleOwner, Observer { table ->
            adapter.setData(table)
        })
    }

    private fun downloadDataFromInternet() {

        GlobalScope.launch(Dispatchers.IO) {
            try {
                //Downloading CloseGames Data
                val doc = Jsoup.connect(urlCloseGame).get()

                val td = doc.select("tr td")
                val teamImgInfo = doc.select(".embl img")
                val tournamentImgInfo = doc.select(".score img")
                val blockTitleInfo = doc.getElementsByClass("blocktitle2")

                fun getInfo(startPosition: Int, list: MutableList<String>) {
                    var a = startPosition
                    var y: String
                    val columnCount = 7
                    do {
                        y = td[a].text()
                        list.add(y)
                        a += columnCount
                    } while (a < 21)
                }

                fun getBlockTitle(startPosition: Int, list: MutableList<String>) {
                    var a = startPosition
                    var y: String
                    val columnCount = 1
                    do {
                        y = blockTitleInfo[a].text()
                        list.add(y)
                        a += columnCount
                    } while (a < 3)
                }

                fun getTournamentsPicsInfo(startPosition: Int, list: MutableList<String>) {
                    var y: String
                    var a = startPosition
                    val columnCount = 1
                    do {
                        y = tournamentImgInfo[a].attr("src")
                        list.add(y)
                        a += columnCount
                    } while (a < tournamentImgInfo.size)
                }

                fun getPicsInfo(startPosition: Int, list: MutableList<String>) {
                    var y: String
                    var a = startPosition
                    val columnCount = 2
                    do {
                        y = teamImgInfo[a].attr("src")
                        list.add(y)
                        a += columnCount
                    } while (a < teamImgInfo.size)
                }

                fun addCloseGamesInfoToDatabase() {
                    var a = 0
                    do {
                        val closeGames1 = CloseGames(
                            null,
                            teamNameHomeList[a],
                            teamNameAwayList[a],
                            scoreList[a],
                            dateList[a],
                            matchTypeList[a],
                            tournamentLogoList[a]
                        )
                        mCloseGamesViewModel.addCloseGames(closeGames1)
                        a++
                    } while (a < 3)
                }

                getInfo(3, teamNameHomeList)
                getInfo(5, teamNameAwayList)
                getInfo(4, scoreList)
                getInfo(6, dateList)
                getBlockTitle(0, matchTypeList)
                getPicsInfo(0, teamLogo1List)
                getPicsInfo(1, teamLogo2List)
                getTournamentsPicsInfo(0, tournamentLogoList)

                addCloseGamesInfoToDatabase()

                //Downloading Table Data

                val tdTable = Jsoup.connect(urlTable).get()
                    .select("tbody tr td")

                fun getPosition(startPositionInTable: Int, list: MutableList<Int>) {
                    var a = startPositionInTable
                    var y: Int
                    val columnCount = 9
                    do {
                        y = tdTable[a].text().toInt()
                        list.add(y)
                        a += columnCount
                    } while (a < tdTable.size)
                }

                fun getTableInfo(startPositionInTable: Int, list: MutableList<String>) {
                    var a = startPositionInTable
                    var y: String
                    val columnCount = 9
                    do {
                        y = tdTable[a].text()
                        list.add(y)
                        a += columnCount
                    } while (a < tdTable.size)
                }

                fun addInfoToDatabase() {
                    var a = 0
                    do {
                        val table1 = Table(
                            positionList[a],
                            clubList[a],
                            matchesList[a],
                            winList[a],
                            drawList[a],
                            loseList[a],
                            pointsList[a]
                        )
                        mTableViewModel.addTable(table1)
                        a++
                    } while (a < positionList.size)
                }

                getPosition(2, positionList)
                getTableInfo(3, clubList)
                getTableInfo(4, matchesList)
                getTableInfo(5, winList)
                getTableInfo(6, drawList)
                getTableInfo(7, loseList)
                getTableInfo(10, pointsList)

                addInfoToDatabase()


                withContext(Dispatchers.Main) {
                    setUpViewPager()
                    setUpRecyclerViewTable()
                    val handler = Handler()
                    handler.postDelayed({
                        hideLoadingScreen(750)
                    }, 1500)
                }
            } catch (e: Exception) {
                Log.e("downloadTableData", e.toString())
            }
        }
    }
}