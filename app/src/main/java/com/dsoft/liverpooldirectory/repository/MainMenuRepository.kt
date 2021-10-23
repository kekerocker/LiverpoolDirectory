package com.dsoft.liverpooldirectory.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.dsoft.liverpooldirectory.data.TableDao
import com.dsoft.liverpooldirectory.model.CloseGames
import com.dsoft.liverpooldirectory.model.Table
import com.dsoft.liverpooldirectory.other.Constants.CLOSE_GAME_URL
import com.dsoft.liverpooldirectory.other.Constants.TABLE_URL
import com.dsoft.liverpooldirectory.utility.InternetConnection
import kotlinx.coroutines.*
import org.jsoup.Jsoup
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainMenuRepository @Inject constructor(
    private val tableDao: TableDao,
    internetConnection: InternetConnection,
) {

    val isOnline = internetConnection.isOnlineDeprecated()

    private val positionList = mutableListOf<Int>()
    private val clubList = mutableListOf<String>()
    private val matchesList = mutableListOf<String>()
    private val winList = mutableListOf<String>()
    private val drawList = mutableListOf<String>()
    private val loseList = mutableListOf<String>()
    private val pointsList = mutableListOf<String>()

    private val tableList = mutableListOf<Table>()

    private val teamNameHomeList = mutableListOf<String>()
    private val teamNameAwayList = mutableListOf<String>()
    private val scoreList = mutableListOf<String>()
    private val dateList = mutableListOf<Long>()
    private val tournamentLogoList = mutableListOf<String>()
    private val teamLogo1List = mutableListOf<String>()
    private val teamLogo2List = mutableListOf<String>()
    private val matchTypeList = mutableListOf<String>()

    val readAllCloseGamesData: LiveData<List<CloseGames>> = tableDao.readAllCloseGamesData()
    val readAllEplData: LiveData<List<Table>> = tableDao.readAllEplData()

    private suspend fun addCloseGames(closeGames: CloseGames) {
        tableDao.addCloseGames(closeGames)
    }

    suspend fun deleteAllCloseGamesData() {
        tableDao.deleteAllCloseGamesData()
    }

    private suspend fun addTable(table: List<Table>) {
        tableDao.addTable(table)
    }

    suspend fun deleteAllTableData() {
        tableDao.deleteAllTableData()
    }

    @DelicateCoroutinesApi
    fun downloadDataFromInternet() {
        GlobalScope.launch (Dispatchers.IO) {
            try {
                //Downloading CloseGames Data
                val doc = Jsoup.connect(CLOSE_GAME_URL).get()

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

                fun getDateInfo(startPosition: Int, list: MutableList<Long>) {
                    var a = startPosition
                    var y: String
                    val columnCount = 7
                    do {
                        y = td[a].text()
                        list.add(convertStringToLong(y))
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
                        GlobalScope.launch(Dispatchers.Default) {
                            addCloseGames(closeGames1)
                        }
                        a++
                    } while (a < 3)
                }

                getInfo(3, teamNameHomeList)
                getInfo(5, teamNameAwayList)
                getInfo(4, scoreList)
                getDateInfo(6, dateList)
                getBlockTitle(0, matchTypeList)
                getPicsInfo(0, teamLogo1List)
                getPicsInfo(1, teamLogo2List)
                getTournamentsPicsInfo(0, tournamentLogoList)

                addCloseGamesInfoToDatabase()

                //Downloading Table Data

                val tdTable = Jsoup.connect(TABLE_URL).get()
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

                        tableList.add(table1)
                        a++
                    } while (a < positionList.size)
                    GlobalScope.launch(Dispatchers.Default) {
                        addTable(tableList)
                    }
                }

                getPosition(2, positionList)
                getTableInfo(3, clubList)
                getTableInfo(4, matchesList)
                getTableInfo(5, winList)
                getTableInfo(6, drawList)
                getTableInfo(7, loseList)
                getTableInfo(10, pointsList)

                addInfoToDatabase()

            } catch (e: Exception) {
                Log.e("downloadTableData", e.toString())
            }
        }
    }

    private fun convertStringToLong(parsedDate: String): Long {
        val sdf = SimpleDateFormat("dd MMMMM yyyy", Locale("ru", "RU"))
        val date = sdf.parse(parsedDate)
        return date.time
    }
}