package com.dsoft.liverpooldirectory.fragments.news.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.dsoft.liverpooldirectory.databinding.ItemNewsBinding
import com.dsoft.liverpooldirectory.model.News


class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private var newsList = emptyList<News>()

    inner class ViewHolder(binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {

        private val url = "http://www.myliverpool.ru/"

        val itemTitle: TextView = binding.tvTitle
        val itemDescription: TextView = binding.tvDescription
        val itemImage: ImageView = binding.ivImage

        init {
            val position: Int = adapterPosition
            itemView.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url + newsList[position].url)
                ContextCompat.startActivity(itemView.context, intent, null)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = newsList[position]
        holder.itemTitle.text = currentItem.title
        holder.itemDescription.text = currentItem.description

        holder.itemImage.load(currentItem.image)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    fun setData(news: List<News>) {
        this.newsList = news
        notifyDataSetChanged()
    }
}
