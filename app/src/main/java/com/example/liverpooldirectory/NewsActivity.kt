package com.example.liverpooldirectory

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_news.*
import org.jsoup.Jsoup
import kotlin.concurrent.thread

class NewsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        supportActionBar?.hide()
        retrieveWebInfo()
    }

    private fun retrieveWebInfo() {
        thread {
            val doc = Jsoup.connect("https://www.google.com/search?q=%D0%BB%D0%B8%D0%B2%D0%B5%D1%80%D0%BF%D1%83%D0%BB%D1%8C&rlz=1C1ASUM_enRU912RU912&sxsrf=ALeKk0019DlnyaAVE8QUX37sZRNZ3BL9Nw:1600696748534&source=lnms&tbm=nws&sa=X&ved=2ahUKEwiwjduItPrrAhWBtYsKHSluCDYQ_AUoAXoECB4QAw&biw=1305&bih=927")
                .get()

            val imageElements = doc.getElementsByClass("img id")
            val textElements = doc.getElementsByClass("I1HL6b nDgy9d")

            val imageUrl = imageElements[0].absUrl("src")

            this.runOnUiThread{

                row_tv_title.text = textElements[0].text()
                Picasso.get().load(imageUrl).into(row_img)
            }
        }

    }

}