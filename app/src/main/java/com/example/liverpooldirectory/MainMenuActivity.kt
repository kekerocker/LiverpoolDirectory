package com.example.liverpooldirectory

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.liverpooldirectory.adapters.RecyclerAdapterTable
import com.example.liverpooldirectory.adapters.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_mainmenu.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.relex.circleindicator.CircleIndicator3
import org.jsoup.Jsoup

private var positionList = mutableListOf<String>()
private var clubList = mutableListOf<String>()
private var gamesList = mutableListOf<String>()
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


class MainMenuActivity : AppCompatActivity() {

    private var urlTable = "https://www.sports.ru/epl/table"
    private var urlCloseGame = "http://www.myliverpool.ru/"

    private var mp: MediaPlayer? = null
    private var currentSong: MutableList<Int> = mutableListOf(R.raw.ynwa)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mainmenu)
        supportActionBar?.hide()

        Toast.makeText(applicationContext, "Welcome, to Republic of Liverpool! Tap on LFC logo.", Toast.LENGTH_LONG).show()

        val btnHistory = findViewById<Button>(R.id.history)
        btnHistory.setOnClickListener(this::onHistoryClick)
        val btnPlayers = findViewById<Button>(R.id.players)
        btnPlayers.setOnClickListener(this::onPlayersClick)
        val btnNews = findViewById<Button>(R.id.news)
        btnNews.setOnClickListener(this::onNewsClick)

        controlSound(currentSong[0])
        downloadTableData()
        downloadCloseGameData()
    }

    private fun setUpViewPager() {
        view_pager2.adapter = ViewPagerAdapter(teamName1List, teamName2List, scoreList, dateList, tournamentLogoList, teamLogo1List, teamLogo2List, matchTypeList)
        view_pager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        val indicator = findViewById<CircleIndicator3>(R.id.indicator)
        indicator.setViewPager(view_pager2)
    }

    private fun setUpRecyclerViewTable() {
        rv_recyclerView_table.layoutManager = LinearLayoutManager(this)
        rv_recyclerView_table.adapter = RecyclerAdapterTable(positionList, clubList, gamesList, winList, drawList, loseList, pointsList)
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

    private fun downloadTableData() {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val doc = Jsoup.connect(urlTable).get()
                val td = doc
                    .select("tbody")
                    .select("tr")
                    .select("td")

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
                getInfo(4, gamesList)
                getInfo(5, winList)
                getInfo(6, drawList)
                getInfo(7, loseList)
                getInfo(10, pointsList)

                withContext(Dispatchers.Main) {
                    setUpRecyclerViewTable()
                }
            } catch (e: Exception) {
                Log.e("downloadTableData", e.toString())
            }
        }
    }

    private fun onHistoryClick(view: View) {
        val intent = Intent(this, historyActivity::class.java)
        startActivity(intent)
    }


    private fun onPlayersClick(view: View) {
        val intent = Intent(this, PlayersActivity::class.java)
        startActivity(intent)
    }

    private fun onNewsClick(view: View) {
        val intent = Intent(this, NewsActivity::class.java)
        startActivity(intent)
    }

    private fun controlSound(id: Int) {
        mp = MediaPlayer.create(this, id)
        initialiseSeekBar()
        mp?.start()
        switchPic()
        fab_play.setOnClickListener {
            when {
                mp == null -> {
                    mp?.start()
                    switchPic()
                }
                mp?.isPlaying!! -> {
                    mp?.pause()
                    fab_play.setImageResource(R.drawable.ic_baseline_play_arrow_24)
                }
                else -> {
                    mp?.start()
                    switchPic()
                }
            }
        }

        if (mp?.isPlaying == false) {
            mp?.seekTo(0)
        }

        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) mp?.seekTo(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }

    private fun switchPic() {
        if (mp !== null) {
            fab_play.setImageResource(R.drawable.ic_baseline_pause_24)
        } else {
            fab_play.setImageResource(R.drawable.ic_baseline_play_arrow_24)
        }
    }

    private fun initialiseSeekBar() {
        seekbar.max = mp!!.duration

        val handler = Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {
                try {
                    seekbar.progress = mp!!.currentPosition
                    handler.postDelayed(this, 1000)
                } catch (e: Exception) {
                    seekbar.progress = 0
                }
            }
        }, 0)
    }
}