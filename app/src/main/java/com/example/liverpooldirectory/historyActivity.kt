package com.example.liverpooldirectory

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

private val urlCloseGame = "http://www.myliverpool.ru/"

private var teamName1List = mutableListOf<String>()
private var teamName2List = mutableListOf<String>()
private var scoreList = mutableListOf<String>()
private var dateList = mutableListOf<String>()
private var tournamentLogoList = mutableListOf<String>()
private var teamLogo1List = mutableListOf<String>()
private var teamLogo2List = mutableListOf<String>()
private var matchTypeList = mutableListOf<String>()


class HistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        supportActionBar?.hide()
    }
}