package com.example.liverpooldirectory

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class NewsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        supportActionBar?.hide()
    }
}