package com.dsoft.liverpooldirectory.fragments.social.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dsoft.liverpooldirectory.databinding.ItemSocialNewsBinding


class SocialRecyclerAdapter(
    private var text: List<String>,
    private var likes: List<String>,
    private var comments: List<String>,
    private var views: List<String>
) : RecyclerView.Adapter<SocialRecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(binding: ItemSocialNewsBinding) : RecyclerView.ViewHolder(binding.root) {
        val itemTitle: TextView = binding.tvSocialText
        val itemLikes: TextView = binding.tvLikes
        val itemComments: TextView = binding.tvComments
        val itemViews: TextView = binding.tvViews
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(ItemSocialNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemTitle.text = text[position]
        holder.itemLikes.text = likes[position]
        holder.itemComments.text = comments[position]
        holder.itemViews.text = views[position]
    }

    override fun getItemCount(): Int {
        return text.size
    }
}