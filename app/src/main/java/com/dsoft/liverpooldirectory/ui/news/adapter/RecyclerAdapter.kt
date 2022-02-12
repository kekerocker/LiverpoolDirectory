package com.dsoft.liverpooldirectory.ui.news.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.dsoft.liverpooldirectory.databinding.ItemNewsBinding
import com.dsoft.liverpooldirectory.model.NewsData


class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private var newsList = emptyList<NewsData>()

    private val differCallback = object : DiffUtil.ItemCallback<NewsData>() {
        override fun areItemsTheSame(oldItem: NewsData, newItem: NewsData): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: NewsData, newItem: NewsData): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }

    var differ = AsyncListDiffer(this, differCallback)

    inner class ViewHolder(binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {

        private val url = "http://www.myliverpool.ru/"

        val itemTitle: TextView = binding.tvTitle
        val itemDescription: TextView = binding.tvDescription
        val itemImage: ImageView = binding.ivImage

        init {
            itemView.setOnClickListener {
                val position: Int = absoluteAdapterPosition
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url + differ.currentList[position].url)
                startActivity(itemView.context, intent, null)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = differ.currentList[position]
        holder.itemTitle.text = currentItem.title
        holder.itemDescription.text = currentItem.description

        holder.itemImage.load(currentItem.image)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}
