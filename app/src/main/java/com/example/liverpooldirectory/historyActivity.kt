package com.example.liverpooldirectory

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.liverpooldirectory.adapters.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_mainmenu.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.relex.circleindicator.CircleIndicator3
import org.jsoup.Jsoup

private val urlCloseGame = "http://www.myliverpool.ru/"

private var teamName1List = mutableListOf<String>()
private var teamName2List = mutableListOf<String>()
private var scoreList = mutableListOf<String>()
private var dateList = mutableListOf<String>()
private var tournamentLogoList = mutableListOf<String>()
private var teamLogo1List = mutableListOf<String>()
private var teamLogo2List = mutableListOf<String>()
private var matchTypeList = mutableListOf<String>()

class historyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        supportActionBar?.hide()

        downloadCloseGameData()
    }

    private fun setUpViewPager() {
        view_pager2.adapter = ViewPagerAdapter(teamName1List, teamName2List, scoreList, dateList, tournamentLogoList, teamLogo1List, teamLogo2List, matchTypeList)
        view_pager2.orientation = ViewPager2.ORIENTATION_VERTICAL
        val indicator = findViewById<CircleIndicator3>(R.id.indicator)
        indicator.setViewPager(view_pager2)
    }

    private fun downloadCloseGameData() {

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val doc = Jsoup.connect(urlCloseGame).get()
                val td = doc.select("tr").select("td")
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

}