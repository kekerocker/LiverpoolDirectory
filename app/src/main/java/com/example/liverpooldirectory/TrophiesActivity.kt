package com.example.liverpooldirectory

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.jsoup.Jsoup
import kotlin.concurrent.thread

const val url = "http://www.myliverpool.ru"

class TrophiesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trophies)
        supportActionBar?.hide()

        val tvView = findViewById<TextView>(R.id.textexample)
        val imgView = findViewById<ImageView>(R.id.imageView2)
        val button = findViewById<Button>(R.id.button)

        button.setOnClickListener {
            thread {
                val doc = Jsoup.connect(url).get()

                val textElement = doc.getElementsByClass("titlenews")
                //val imageUrl = url + doc
                    //.select(".short_img img")
                    //.first()
                    //.attr("src")

                this.runOnUiThread {
                    //Log.d("TAGG", imageUrl)
                    //Picasso.get()
                        //.load(imageUrl)
                        //.into(imgView)
                    tvView.text = textElement[0].text()
                }
            }
        }
    }
}