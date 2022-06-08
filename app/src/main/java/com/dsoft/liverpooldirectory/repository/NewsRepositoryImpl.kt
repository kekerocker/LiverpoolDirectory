package com.dsoft.liverpooldirectory.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.dsoft.liverpooldirectory.data.NewsDao
import com.dsoft.liverpooldirectory.model.NewsData
import com.dsoft.liverpooldirectory.other.Constants.NEWS_URL
import com.dsoft.liverpooldirectory.repository.intrface.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepositoryImpl @Inject constructor(
    private val newsDao: NewsDao,
) : NewsRepository {

    private var titleList = mutableListOf<String>()
    private var descList = mutableListOf<String>()
    private var imagesList = mutableListOf<String>()
    private var linksList = mutableListOf<String>()

    private var pageNumber = 1

    override fun getAllNews(): LiveData<List<NewsData>> = newsDao.readAllNews()

    override suspend fun deleteAllNews() {
        newsDao.deleteAllNews()
    }

    override fun downloadNews() {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val doc = Jsoup.connect(NEWS_URL + pageNumber).get()
                val allEntries = doc.getElementById("allEntries")
                val title = allEntries
                    .getElementsByClass("titlenews")
                    .select("a")
                val images = allEntries.getElementsByClass("short_img")
                    .select("img")
                val description = allEntries.getElementsByClass("eMessage")
                val url = allEntries.getElementsByClass("titlenews")
                    .select("a")


                fun getImages(list: MutableList<String>) {
                    val originUrl = "http://www.myliverpool.ru"
                    var realUrl: String
                    var y: String
                    var a = 0
                    do {
                        y = images[a].attr("src")
                        realUrl = originUrl + y
                        list.add(realUrl)
                        a += 1
                    } while (a < images.size)
                }

                fun getUrl(list: MutableList<String>) {
                    var y: String
                    var a = 0
                    do {
                        y = url[a].attr("href")
                        list.add(y)
                        a += 1
                    } while (a < url.size)
                }

                fun getTitle(list: MutableList<String>) {
                    var a = 0
                    var y: String
                    do {
                        y = title[a].text()
                        list.add(y)
                        a += 1
                    } while (a < title.size)
                }

                fun getDescription(list: MutableList<String>) {
                    var a = 0
                    var y: String
                    do {
                        y = description[a].text()
                        list.add(y)
                        a += 1
                    } while (a < description.size)
                }

                fun addNewsToDatabase() {
                    var a = 0
                    do {
                        val news = NewsData(
                            null,
                            titleList[a],
                            descList[a],
                            imagesList[a],
                            linksList[a],
                        )
                        GlobalScope.launch(Dispatchers.Default) {
                            addNews(news)
                        }
                        a++
                    } while (a < titleList.size)
                }

                getImages(imagesList)
                getUrl(linksList)
                getTitle(titleList)
                getDescription(descList)

                addNewsToDatabase()

                //Clear lists
                imagesList.clear()
                linksList.clear()
                titleList.clear()
                descList.clear()

                pageNumber++
            } catch (e: Exception) {
                Log.e("Error", e.toString())
            }
        }
    }

    private suspend fun addNews(news: NewsData) {
        newsDao.addNews(news)
    }

}