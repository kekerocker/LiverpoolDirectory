package com.example.liverpooldirectory.fragments.mainmenu

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.liverpooldirectory.R
import com.example.liverpooldirectory.model.TableViewModel
import kotlinx.android.synthetic.main.fragment_main_menu.*
import kotlinx.android.synthetic.main.fragment_main_menu.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

class MainMenuFragment : Fragment() {

    private lateinit var mTableViewModel: TableViewModel

    private var positionList = mutableListOf<String>()
    private var clubList = mutableListOf<String>()
    private var matchesList = mutableListOf<String>()
    private var winList = mutableListOf<String>()
    private var drawList = mutableListOf<String>()
    private var loseList = mutableListOf<String>()
    private var pointsList = mutableListOf<String>()

    private var teamName1List = mutableListOf<String>()
    private var teamName2List = mutableListOf<String>()
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

        view.button_news.setOnClickListener {
            findNavController().navigate(R.id.action_mainMenuFragment_to_newsFragment)
        }

        view.button_history.setOnClickListener {
            findNavController().navigate(R.id.action_MainMenuFragment_to_historyFragment)
        }

        view.button_players.setOnClickListener {
            findNavController().navigate(R.id.action_MainMenuFragment_to_playersFragment)
        }

        view.button_social.setOnClickListener {
            findNavController().navigate(R.id.action_MainMenuFragment_to_socialFragment)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mTableViewModel = ViewModelProvider(this).get(TableViewModel::class.java)

        downloadTableData()
        downloadCloseGameData()
    }

    private fun setUpViewPager() {
        view_pager2.adapter = ViewPagerAdapter(teamName1List, teamName2List, scoreList, dateList, tournamentLogoList, teamLogo1List, teamLogo2List, matchTypeList)
        view_pager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        val indicator = view?.indicator
        indicator?.setViewPager(view_pager2)
    }

    private fun setUpRecyclerViewTable() {
        rv_recyclerView_table.layoutManager = LinearLayoutManager(requireContext())
        rv_recyclerView_table.adapter = RecyclerAdapterTable(positionList, clubList, matchesList, winList, drawList, loseList, pointsList)
    }


    private fun downloadCloseGameData() {

        GlobalScope.launch(Dispatchers.IO) {
            try {
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

                getInfo(3, teamName1List)
                getInfo(5, teamName2List)
                getInfo(4, scoreList)
                getInfo(6, dateList)
                getBlockTitle(0, matchTypeList)
                getPicsInfo(0, teamLogo1List)
                getPicsInfo(1, teamLogo2List)
                getTournamentsPicsInfo(0, tournamentLogoList)

                withContext(Dispatchers.Main) {
                    setUpViewPager()
                }
            } catch (e: Exception) {
                Log.e("downloadTableData", e.toString())
            }
        }
    }

    private fun downloadTableData() {
        progressBar_table.visibility = View.VISIBLE

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val doc = Jsoup.connect(urlTable).get()
                val td = doc
                    .select("tbody tr td")

                fun getInfo(startPositionInTable: Int, list: MutableList<String>) {
                    var a = startPositionInTable
                    var y: String
                    val columnCount = 9
                    do {
                        y = td[a].text()
                        list.add(y)
                        a += columnCount
                    } while (a < td.size)
                }

                getInfo(2, positionList)
                getInfo(3, clubList)
                getInfo(4, matchesList)
                getInfo(5, winList)
                getInfo(6, drawList)
                getInfo(7, loseList)
                getInfo(10, pointsList)

                withContext(Dispatchers.Main) {
                    setUpRecyclerViewTable()
                    progressBar_table.visibility = View.GONE
                }
            } catch (e: Exception) {
                Log.e("downloadTableData", e.toString())
            }
        }
    }
}