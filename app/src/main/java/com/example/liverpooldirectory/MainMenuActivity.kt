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
import com.example.liverpooldirectory.adapters.RecyclerAdapterTable
import kotlinx.android.synthetic.main.activity_mainmenu.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

private var positionList = mutableListOf<String>()
private var clubList = mutableListOf<String>()
private var gamesList = mutableListOf<String>()
private var winList = mutableListOf<String>()
private var drawList = mutableListOf<String>()
private var loseList = mutableListOf<String>()
private var pointsList = mutableListOf<String>()


@Suppress("DEPRECATION")
class MainMenuActivity : AppCompatActivity() {

    private var URL_TABLE = "https://liverpoolfc.ru/season/premer-liga"

    private var mp: MediaPlayer? = null
    private var currentSong: MutableList<Int> = mutableListOf(R.raw.ynwa)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mainmenu)
        supportActionBar?.hide()

        Toast.makeText(applicationContext, "Welcome, to Republic of Liverpool! Tap on LFC logo.", Toast.LENGTH_LONG).show()

        val btnHistory = findViewById<Button>(R.id.button4)
        btnHistory.setOnClickListener(this::onHistoryClick)
        val btnPlayers = findViewById<Button>(R.id.button5)
        btnPlayers.setOnClickListener(this::onPlayersClick)
        val btnTrophies = findViewById<Button>(R.id.button6)
        btnTrophies.setOnClickListener(this::onTrophiesClick)
        val btnNews = findViewById<Button>(R.id.button7)
        btnNews.setOnClickListener(this::onNewsClick)

        controlSound(currentSong[0])
        downloadTableData()
    }

    private fun setUpRecyclerView() {
        rv_recyclerView_table.layoutManager = LinearLayoutManager(this)
        rv_recyclerView_table.adapter = RecyclerAdapterTable(positionList, clubList, gamesList, winList, drawList, loseList, pointsList)
    }


    private fun downloadTableData() {

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val doc = Jsoup.connect(URL_TABLE).get()
                val td = doc
                    .select("tbody")
                    .select("tr")
                    .select("td")

                fun getInfo(startPositionInTable: Int, list: MutableList<String>) {
                    var a = startPositionInTable
                    var y: String
                    val columnCount = 9
                    do {
                        y = td.get(a).text()
                        list.add(y)
                        a += columnCount
                    } while (a < td.size)
                }

                getInfo(0, positionList)
                getInfo(1, clubList)
                getInfo(2, gamesList)
                getInfo(3, winList)
                getInfo(4, drawList)
                getInfo(5, loseList)
                getInfo(8, pointsList)

                withContext(Dispatchers.Main) {
                    setUpRecyclerView()
                }
            } catch (e: Exception) {
                Log.e("NewsActivity", e.toString())
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


    private fun onTrophiesClick(view: View) {
        val intent = Intent(this, TrophiesActivity::class.java)
        startActivity(intent)
    }

    private fun onNewsClick(@Suppress("UNUSED_PARAMETER") view: View) {
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