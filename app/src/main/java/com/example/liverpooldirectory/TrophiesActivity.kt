package com.example.liverpooldirectory

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragment

class TrophiesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trophies)
        supportActionBar?.hide()

        var lfctrophies =
            supportFragmentManager.findFragmentById(R.id.youtube_fragment1) as YouTubePlayerSupportFragment
        lfctrophies.initialize("AIzaSyAPZiaOhFJVyMh9BoXgRxlN38itYcSgCm4",
            object : YouTubePlayer.OnInitializedListener {
                override fun onInitializationSuccess(
                    provider: YouTubePlayer.Provider?,
                    player: YouTubePlayer?,
                    wasRestored: Boolean
                ) {
                    if (player == null) return
                    if (wasRestored)
                        player.play()
                    else {
                        player.cueVideo("yT4AEIcAMz4")
                        player.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT)
                    }
                }

                override fun onInitializationFailure(
                    p0: YouTubePlayer.Provider?,
                    p1: YouTubeInitializationResult?
                ) {
                }
            })
    }
}