package com.example.liverpooldirectory.fragments.news

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.liverpooldirectory.R
import com.example.liverpooldirectory.model.News
import kotlinx.android.synthetic.main.item_news.view.*


class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private var newsList = emptyList<News>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val url = "http://www.myliverpool.ru/"

        init {
            itemView.setOnClickListener {
                val position: Int = adapterPosition

                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url + newsList[position].url)
                ContextCompat.startActivity(itemView.context, intent, null)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = newsList[position]
        holder.itemView.tv_title.text = currentItem.title
        holder.itemView.tv_description.text = currentItem.description

        holder.itemView.iv_image.load(currentItem.image)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    fun setData(news: List<News>) {
        this.newsList = news
        notifyDataSetChanged()
    }
}
