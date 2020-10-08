package com.example.liverpooldirectory

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_library.fab_stop
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var mp: MediaPlayer? = null
    private var currentSong: MutableList<Int> = mutableListOf(R.raw.ynwa)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        val text = "Welcome, to Republic of Liverpool! Tap on LFC logo."
        val duration = Toast.LENGTH_LONG

        val toast = Toast.makeText(applicationContext, text, duration)
        toast.show()

        val btnLibrary = findViewById<ImageButton>(R.id.imageButton)

        btnLibrary.setOnClickListener(this::onLibraryClick)
        mainSound(currentSong[0])
    }

    private fun onLibraryClick(view: View) {
        val intent = Intent(this, LibraryActivity::class.java)
        startActivity(intent)
    }

    //Запуск аудиозаписи в "Главном меню"
    private fun mainSound(id: Int) {
        if (mp == null) {
            mp = MediaPlayer.create(this, id)
            Log.d("MainActivity", "ID: ${mp!!.audioSessionId}")
        }
        mp?.start()
        Log.d("LibraryActivity", "Duration: ${mp!!.duration / 1000} seconds")

        //Кнопка для остановки аудиозаписи
        fab_stop.setOnClickListener {
            if (mp !== null) {
                mp?.stop()
                mp?.reset()
                mp?.release()
                mp = null
            }
        }

        //Switch для беззвучного режима
        fab_mute.setOnCheckedChangeListener{ _, isChecked ->
            if (isChecked) {
                mp?.setVolume(0.0f, 0.0f)
            } else {
                mp?.setVolume(1f, 1f)
            }
        }
    }
}