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
import kotlinx.android.synthetic.main.activity_mainmenu.*


class MainMenuActivity : AppCompatActivity() {

    private var mp: MediaPlayer? = null
    private var currentSong: MutableList<Int> = mutableListOf(R.raw.ynwa)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mainmenu)
        supportActionBar?.hide()
        val text = "Welcome, to Republic of Liverpool! Tap on LFC logo."
        val duration = Toast.LENGTH_LONG

        val toast = Toast.makeText(applicationContext, text, duration)
        toast.show()

        val btnHistory = findViewById<Button>(R.id.button4)
        btnHistory.setOnClickListener(this::onHistoryClick)
        val btnPlayers = findViewById<Button>(R.id.button5)
        btnPlayers.setOnClickListener(this::onPlayersClick)
        val btnTrophies = findViewById<Button>(R.id.button6)
        btnTrophies.setOnClickListener(this::onTrophiesClick)
        val btnNews = findViewById<Button>(R.id.button7)
        btnNews.setOnClickListener(this::onNewsClick)

        controlSound(currentSong[0])
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
        Log.d("MainActivity", "ID: ${mp!!.audioSessionId}")
        initialiseSeekBar()
        mp?.start()
        fab_play.setOnClickListener {
            if (mp == null) {
                mp = MediaPlayer.create(this, id)
                Log.d("LibraryActivity", "ID: ${mp!!.audioSessionId}")

                initialiseSeekBar()
            }
            mp?.start()
            Log.d("LibraryActivity", "Duration: ${mp!!.duration / 1000} seconds")
        }

        fab_stop.setOnClickListener {
            if (mp !== null) {
                mp?.stop()
                mp?.reset()
                mp?.release()
                mp = null
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
