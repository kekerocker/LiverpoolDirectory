package com.example.liverpooldirectory.fragments.social

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.liverpooldirectory.R


class SocialRecyclerAdapter(
    private var text: List<String>,
    private var likes: List<String>,
    private var comments: List<String>
) : RecyclerView.Adapter<SocialRecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemTitle: TextView = itemView.findViewById(R.id.tv_social_text)
        val itemLikes: TextView = itemView.findViewById(R.id.tv_likes)
        val itemComments: TextView = itemView.findViewById(R.id.tv_comments)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_social_news, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemTitle.text = text[position]
        holder.itemLikes.text = likes[position]
        holder.itemComments.text = comments[position]
    }

    override fun getItemCount(): Int {
        return text.size
    }
}